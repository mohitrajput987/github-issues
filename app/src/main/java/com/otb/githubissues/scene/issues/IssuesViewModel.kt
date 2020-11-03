package com.otb.githubissues.scene.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.otb.githubissues.common.ProgressStatus

class IssuesViewModel(private val repository: IssuesContract.Repository) : ViewModel(), IssuesContract.ViewModel {
    private val issuesDataSourceFactory: IssuesDataSourceFactory = IssuesDataSourceFactory(repository, viewModelScope)
    private val progressLoadStatus: LiveData<ProgressStatus>
    private val issues: LiveData<PagedList<IssuesModels.Issue>>

    init {
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build()

        issues = LivePagedListBuilder<Int, IssuesModels.Issue>(issuesDataSourceFactory, pagedListConfig)
                .build()

        progressLoadStatus = Transformations.switchMap(issuesDataSourceFactory.liveData, IssuesDataSource::getProgressLiveStatus)
    }

    override fun getIssues() = issues

    override fun getProgressStatus() = progressLoadStatus

    override fun searchIssues(githubOwner: String, repoName: String, state: String) {
        issuesDataSourceFactory.githubOwner = githubOwner
        issuesDataSourceFactory.repoName = repoName
        issuesDataSourceFactory.state = state
        issues.value?.dataSource?.invalidate()
    }
}