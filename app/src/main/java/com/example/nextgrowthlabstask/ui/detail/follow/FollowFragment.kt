package com.example.nextgrowthlabstask.ui.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextgrowthlabstask.data.local.entity.UserEntity
import com.example.nextgrowthlabstask.data.remote.Result
import com.example.nextgrowthlabstask.databinding.FragmentFollowBinding
import com.example.nextgrowthlabstask.ui.adapters.UserAdapter
import com.example.nextgrowthlabstask.ui.detail.DetailFragment
import com.example.nextgrowthlabstask.ui.detail.DetailFragmentDirections
import com.example.nextgrowthlabstask.ui.detail.DetailViewModel
import com.example.nextgrowthlabstask.utils.NavControllerHelper.safeNavigate
import com.example.nextgrowthlabstask.utils.SnackBarExt.showSnackBar
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setGone
import com.example.nextgrowthlabstask.utils.ViewVisibilityUtil.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowFragment : Fragment(), UserAdapter.UserClickCallback {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val index: Int? = arguments?.getInt(ARG_SECTION_NUMBER, 1)
            val username = arguments?.getString(EXTRA_USERNAME)
            viewModel.setUsername(username.toString())
            if (index != null) {
                when (index) {
                    1 -> viewModel.getFollowers.observe(viewLifecycleOwner, observer)
                    2 -> viewModel.getFollowing.observe(viewLifecycleOwner, observer)
                }
            }
            userAdapter = UserAdapter(this)
            binding?.rvFollower?.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                setHasFixedSize(true)
                adapter = userAdapter
            }
        }
    }

    private val observer = Observer<Result<List<UserEntity>>> { result ->
        when (result) {
            is Result.Success -> {
                hideLoading()
                result.data?.let {
                    if (it.isNotEmpty()) userAdapter.submitList(it) else
                        binding?.noUsers?.root?.setVisible()
                }
            }
            is Result.Error -> {
                hideLoading()
                with(requireActivity()) {
                    showSnackBar(this.window.decorView.rootView, result.message)
                }
            }
        }
    }

    private fun hideLoading() = binding?.apply {
        shimmer.root.setGone()
        noUsers.root.setGone()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(user: UserEntity) {
        val toDetail = DetailFragmentDirections.actionDetailFragmentSelf()
        toDetail.username = user.login
        safeNavigate(toDetail, DetailFragment::class.java.name)
    }

    companion object {
        const val EXTRA_USERNAME = "username"
        const val ARG_SECTION_NUMBER = "section_number"
    }
}