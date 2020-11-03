package com.otb.githubissues.scene.issues

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(private val fragmentActivity: FragmentActivity, private val githubOwner: String, private val repoName: String) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        const val POS_OPEN_ISSUES = 0
        const val POS_CLOSED_ISSUES = 1
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        POS_OPEN_ISSUES -> IssuesFragment.newInstance(githubOwner, repoName, IssueState.OPEN)
        else -> IssuesFragment.newInstance(githubOwner, repoName, IssueState.CLOSE)
    }

    override fun getItemCount() = 2
}