package com.otb.githubissues.scene.issues

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.otb.githubissues.R
import com.otb.githubissues.common.Constants
import kotlinx.android.synthetic.main.activity_issues.*

class IssuesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)
        init()
    }

    private fun init() {
        val githubOwner = intent.getStringExtra(Constants.IntentKeys.GITHUB_OWNER_NAME)
        val repoName = intent.getStringExtra(Constants.IntentKeys.GITHUB_REPOSITORY_NAME)
        setupTabs(githubOwner, repoName)
    }

    private fun setupTabs(githubOwner: String, repoName: String) {
        val issueStatus = resources.getStringArray(R.array.issue_status)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = ViewPagerAdapter(this, githubOwner, repoName)

        TabLayoutMediator(tabIssues, viewPager, TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
            tab.text = issueStatus[position]
        }).attach()
    }
}