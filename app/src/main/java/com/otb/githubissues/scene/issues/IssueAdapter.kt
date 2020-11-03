package com.otb.githubissues.scene.issues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.otb.githubissues.databinding.ItemIssueBinding

/**
 * Created by Mohit Rajput on 3/11/20.
 */
class IssueAdapter : PagedListAdapter<IssuesModels.Issue, IssueAdapter.IssueViewHolder>(DiffUtilCallBack()) {

    inner class IssueViewHolder(private val binding: ItemIssueBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(issue: IssuesModels.Issue) {
            binding.issue = issue
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val itemBinding: ItemIssueBinding = ItemIssueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<IssuesModels.Issue>() {
    override fun areItemsTheSame(oldItem: IssuesModels.Issue, newItem: IssuesModels.Issue) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: IssuesModels.Issue, newItem: IssuesModels.Issue): Boolean {
        return oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.number == newItem.number
    }
}