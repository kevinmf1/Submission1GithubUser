package com.kevinmedia.submission1githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kevinmedia.submission1githubuser.GithubUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: GithubUser)

    @Delete
    fun delete(user: GithubUser)

    @Query("SELECT * from GithubUser ORDER BY login ASC")
    fun getAllUsers(): LiveData<List<GithubUser>>
}