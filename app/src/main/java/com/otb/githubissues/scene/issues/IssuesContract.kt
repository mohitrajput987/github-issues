package com.otb.githubissues.scene.issues

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.otb.githubissues.common.DataResult
import com.otb.githubissues.common.ProgressStatus

/**
 * Created by Mohit Rajput on 3/11/20.
 */
interface IssuesContract {
    interface ViewModel {
        fun getIssues(): LiveData<PagedList<IssuesModels.Issue>>
        fun getProgressStatus(): LiveData<ProgressStatus>
        fun searchIssues(githubOwner: String, repoName: String, state: String)
    }

    interface Repository {
        suspend fun searchIssues(githubOwner: String, repoName: String, state: String): DataResult<List<IssuesModels.Issue>>
    }
}