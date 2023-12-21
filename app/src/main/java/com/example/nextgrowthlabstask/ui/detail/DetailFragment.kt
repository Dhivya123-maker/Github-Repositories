package com.example.nextgrowthlabstask.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.nextgrowthlabstask.R
import com.example.nextgrowthlabstask.data.local.entity.DetailUserEntity
import com.example.nextgrowthlabstask.data.remote.Result
import com.example.nextgrowthlabstask.databinding.FragmentDetailBinding
import com.example.nextgrowthlabstask.databinding.LayoutBottomsheetBinding
import com.example.nextgrowthlabstask.utils.CountFormatUtil.toCountFormat
import com.example.nextgrowthlabstask.utils.ImageLoader.loadImage
import com.example.nextgrowthlabstask.utils.SnackBarExt.showSnackBar
import com.example.nextgrowthlabstask.utils.TabSelector.selectTabOn
import com.example.nextgrowthlabstask.utils.TextLoader.loadData
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setGone
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setInvisible
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailViewModel by viewModels()
    private var username: String? = null
    private var dataUser: DetailUserEntity? = null
    private var stateFavoriteUser: Boolean = false
    private lateinit var tabs: TabLayout
    private var menu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding?.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        setHasOptionsMenu(true)
        setUpViewPager()
        setUpView()
    }

    private fun setUpViewPager() {
        binding?.apply {
            val dataUser = DetailFragmentArgs.fromBundle(arguments as Bundle).username
            if (dataUser != null) viewModel.setUsername(dataUser)
            username = dataUser
            this@DetailFragment.tabs = tabs
            val viewPager = viewPager
            val sectionPagerAdapter = SectionPagerAdapter(childFragmentManager, lifecycle, dataUser)
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) { _, _ -> }.attach()
        }
    }

    private fun setUpView() {
        if (username != null) {
            (activity as AppCompatActivity).supportActionBar?.title = username.toString()
            binding?.header?.root?.setInvisible()
            viewModel.detailUser .observe(viewLifecycleOwner, observer)
        }
    }

    private val observer = Observer<Result<DetailUserEntity>> { result ->
        when (result) {
            is Result.Success -> {
                showContent()
                dataUser = result.data
                dataUser?.let {
                    populateUser(it)
                }
            }
            is Result.Error -> {
                showContent()
                requireActivity().showSnackBar(
                    requireActivity().window.decorView.rootView,
                    result.message)
            }
            else -> {

            }
        }
    }

    private fun showContent() = binding?.apply {
        shimmer.setGone()
        header.root.setVisible()
    }

    private fun populateUser(detailEntity: DetailUserEntity) {
        binding?.header?.apply {
            with(detailEntity) {
                ivUserProfile.loadImage(requireActivity(), avatarUrl, CircleCrop())
                tvRepository.loadData(publicRepos?.toCountFormat())
                tvFollowers.loadData(followers?.toCountFormat())
                tvFollowing.loadData(following?.toCountFormat())
                tvName.loadData(name)
                tvType.loadData(type)
                tvLocation.loadData(location)
                tvCompany.loadData(company)
                tvBio.loadData(bio?.trim())
                tvUrl.loadData(blog)
                repositoryContainer.selectTabOn(0, tabs)
                followersContainer.selectTabOn(1, tabs)
                followingContainer.selectTabOn(2, tabs)
                val verifiedLink = blog?.verify()
                tvUrl.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(verifiedLink))
                    startActivity(intent)
                }
            }
        }
    }

    private fun String.verify(): String {
        val rightLink : Boolean = this.contains("http")
        return if (rightLink) this else "https://$this"
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        viewModel.detailUser.observe(viewLifecycleOwner, observer)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}