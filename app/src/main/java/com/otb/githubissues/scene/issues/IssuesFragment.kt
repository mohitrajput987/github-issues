package com.otb.githubissues.scene.issues

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.otb.githubissues.R
import com.otb.githubissues.common.Constants
import com.otb.githubissues.common.ProgressStatus
import com.otb.githubissues.common.longToast
import com.otb.githubissues.databinding.FragmentIssuesBinding
import kotlinx.android.synthetic.main.fragment_issues.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

val issuesModule = module {
    factory { IssuesRepository(get(), get()) as IssuesContract.Repository }

    viewModel { IssuesViewModel(get()) }
}

class IssuesFragment : Fragment(R.layout.fragment_issues) {
    private val issuesViewModel: IssuesContract.ViewModel by viewModel<IssuesViewModel>()
    private lateinit var issueState: String
    private lateinit var githubOwner: String
    private lateinit var repoName: String
    private lateinit var binding: FragmentIssuesBinding

    companion object {
        fun newInstance(githubOwner: String, repoName: String, state: IssueState): IssuesFragment {
            val issueFragment = IssuesFragment()
            val bundle = Bundle()
            bundle.putString(Constants.IntentKeys.GITHUB_OWNER_NAME, githubOwner)
            bundle.putString(Constants.IntentKeys.GITHUB_REPOSITORY_NAME, repoName)
            bundle.putString(Constants.IntentKeys.ISSUE_STATE, state.key)
            issueFragment.arguments = bundle
            return issueFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIssuesBinding.bind(view)
        binding.rvIssues.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIssues.adapter = IssueAdapter()
        setupViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(issuesModule)
        githubOwner = arguments!!.getString(Constants.IntentKeys.GITHUB_OWNER_NAME, "")
        repoName = arguments!!.getString(Constants.IntentKeys.GITHUB_REPOSITORY_NAME, "")
        issueState = arguments!!.getString(Constants.IntentKeys.ISSUE_STATE, IssueState.OPEN.key)
    }

    private fun setupViewModel() {
        issuesViewModel.getIssues().observe(this, Observer {
            (rvIssues.adapter as IssueAdapter).submitList(it)
        })

        issuesViewModel.getProgressStatus().observe(this, Observer {
            when (it) {
                is ProgressStatus.Loading -> displayLoading()
                is ProgressStatus.Success -> hideLoading()
                is ProgressStatus.Error -> displayError(it.errorMessage)
            }
        })
        issuesViewModel.searchIssues(githubOwner, repoName, issueState)
    }

    private fun displayLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun displayError(errorMessage: String) {
        hideLoading()
        requireContext().longToast(errorMessage)
    }

    override fun onDestroy() {
        unloadKoinModules(issuesModule)
        super.onDestroy()
    }
}