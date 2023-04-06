package com.kevinmedia.submission1githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.kevinmedia.submission1githubuser.GithubUser
import com.kevinmedia.submission1githubuser.database.UserDao
import com.kevinmedia.submission1githubuser.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUsersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUsersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<GithubUser>> = mUsersDao.getAllUsers()
    fun insert(user: GithubUser) {
        executorService.execute { mUsersDao.insert(user) }
    }

    fun delete(user: GithubUser) {
        executorService.execute { mUsersDao.delete(user) }
    }
}