package com.example.nextgrowthlabstask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nextgrowthlabstask.data.local.entity.RepoEntity
import com.example.nextgrowthlabstask.databinding.ItemRepoBinding
import com.example.nextgrowthlabstask.utils.CountFormatUtil.toCountFormat
import com.example.nextgrowthlabstask.utils.DateFormatUtil.getTimeAgo
import com.example.nextgrowthlabstask.utils.LanguageColorUtil.setLeftDrawableColor
import com.example.nextgrowthlabstask.utils.TextLoader.loadData

class RepoAdapter(private val callback: OnRepoCallback
): ListAdapter<RepoEntity, RepoAdapter.RepoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val dataRepo = getItem(position)
        holder.bind(dataRepo)
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoEntity) {
            binding.apply {
                tvLanguage.setLeftDrawableColor(itemView.context, repo.language)
                tvTitle.loadData(repo.name)
                tvVisibility.loadData(repo.visibility?.replaceFirstChar { it.uppercase() })
                tvDescription.loadData(repo.description ?: "-")
                tvLanguage.loadData(repo.language ?: "-")
                tvStars.loadData(repo.stargazersCount?.toCountFormat())
                tvUpdatedAt.loadData(repo.updatedAt?.getTimeAgo())
                itemView.setOnClickListener { callback.onItemClicked(repo) }
            }
        }
    }

    interface OnRepoCallback {
        fun onItemClicked(repoEntity: RepoEntity)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                when {
                    oldItem.name != newItem.name -> false
                    oldItem.description != newItem.description -> false
                    oldItem.language != newItem.language -> false
                    oldItem.owner != newItem.owner -> false
                    oldItem.stargazersCount != newItem.stargazersCount -> false
                    else -> true
                }
        }
    }
}