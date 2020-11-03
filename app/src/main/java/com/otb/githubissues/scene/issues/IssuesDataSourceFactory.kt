package com.otb.githubissues.scene.issues

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Mohit Rajput on 3/11/20.
 */
class IssuesDataSourceFactory(private val repository: IssuesContract.Repository, private val scope: CoroutineScope) : DataSource.Factory<Int, IssuesModels.Issue>() {
    var githubOwner: String = ""
    var repoName: String = ""
    var state: String = ""

    val liveData = MutableLiveData<IssuesDataSource>()
    lateinit var issuesDataSource: IssuesDataSource

    override fun create(): DataSource<Int, IssuesModels.Issue> {
        issuesDataSource = IssuesDataSource(repository, scope, githubOwner, repoName, state)
        liveData.postValue(issuesDataSource)
        return issuesDataSource
    }
}