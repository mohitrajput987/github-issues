package com.otb.githubissues.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otb.githubissues.scene.issues.IssuesModels

@Dao
interface IssuesDao {
    @Query("SELECT * FROM issue WHERE github_owner = :githubOwner AND github_repo_name = :repoName AND state = :state LIMIT :initialItem, :totalItems")
    suspend fun getIssues(githubOwner: String, repoName: String, state: String, initialItem: Int, totalItems: Int): List<IssuesModels.IssueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(issues: List<IssuesModels.IssueEntity>)

    @Query("DELETE FROM issue WHERE github_owner = :githubOwner AND github_repo_name = :repoName AND state = :state")
    suspend fun deleteIssues(githubOwner: String, repoName: String, state: String)

    @Query("DELETE FROM issue")
    suspend fun clear()
}