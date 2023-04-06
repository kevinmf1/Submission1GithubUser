package com.kevinmedia.submission1githubuser.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kevinmedia.submission1githubuser.GithubUser
import com.kevinmedia.submission1githubuser.repository.UserRepository

class UserFavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUsers(): LiveData<List<GithubUser>> = mUserRepository.getAllUsers()
}