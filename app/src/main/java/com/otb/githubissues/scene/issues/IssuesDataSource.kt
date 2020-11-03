package com.otb.githubissues.scene.issues

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.otb.githubissues.common.DataResult
import com.otb.githubissues.common.ProgressStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * Created by Mohit Rajput on 3/11/20.
 */

class IssuesDataSource(private val repository: IssuesContract.Repository, private val scope: CoroutineScope, private val githubOwner: String, private val repoName: String, private val state: String) : PageKeyedDataSource<Int, IssuesModels.Issue>() {
    private val progressLiveStatus = MutableLiveData<ProgressStatus>()

    fun getProgressLiveStatus() = progressLiveStatus

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, IssuesModels.Issue>) {
        scope.launch {
            progressLiveStatus.postValue(ProgressStatus.Loading)
            when (val dataResult = repository.searchIssues(githubOwner, repoName, state)) {
                is DataResult.DataSuccess -> {
                    progressLiveStatus.postValue(ProgressStatus.Success)
                    callback.onResult(dataResult.data, null, 2)
                }
                is DataResult.DataError -> progressLiveStatus.postValue(ProgressStatus.Error(dataResult.errorMessage))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, IssuesModels.Issue>) {

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, IssuesModels.Issue>) {

    }
}