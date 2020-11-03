package com.otb.githubissues.scene.input

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.otb.githubissues.R
import com.otb.githubissues.common.Constants
import com.otb.githubissues.common.shortToast
import com.otb.githubissues.scene.issues.IssuesActivity
import kotlinx.android.synthetic.main.activity_repo_input.*

class RepoInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_input)
        init()
    }

    private fun init() {
        btnSubmit.setOnClickListener {
            onSubmitBtnClicked()
        }
    }

    private fun onSubmitBtnClicked() {
        val githubOwnerName = etGitHubOwner.text.toString()
        val repositoryName = etGitHubRepository.text.toString()

        if (githubOwnerName.isBlank()) {
            shortToast(getString(R.string.enter_github_owner_name))
            return
        }

        if (repositoryName.isBlank()) {
            shortToast(getString(R.string.enter_repository_name))
            return
        }

        val intent = Intent(this, IssuesActivity::class.java)
        intent.putExtra(Constants.IntentKeys.GITHUB_OWNER_NAME, githubOwnerName)
        intent.putExtra(Constants.IntentKeys.GITHUB_REPOSITORY_NAME, repositoryName)
        startActivity(intent)
    }
}