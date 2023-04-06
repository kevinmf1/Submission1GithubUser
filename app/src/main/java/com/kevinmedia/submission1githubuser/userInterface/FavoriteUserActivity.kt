package com.kevinmedia.submission1githubuser.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinmedia.submission1githubuser.GithubUser
import com.kevinmedia.submission1githubuser.R
import com.kevinmedia.submission1githubuser.UsersAdapter
import com.kevinmedia.submission1githubuser.databinding.ActivityFavoriteUserBinding
import com.kevinmedia.submission1githubuser.viewModels.UserFavoriteViewModel
import com.kevinmedia.submission1githubuser.viewModels.UserFavoriteViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {
    private var _activityFavUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        actionBar?.title = resources.getString(R.string.favoriteUser)

        binding?.rvUsersFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvUsersFavorite?.setHasFixedSize(true)

        val favoriteUserViewModel = obtainViewModel(this)
        favoriteUserViewModel.getAllUsers().observe(this) { userList ->
            if (userList.isNotEmpty()) {
                setListData(userList)
            } else {
                binding?.rvUsersFavorite?.visibility = View.GONE
                binding?.tvNonedata?.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun obtainViewModel(activity: AppCompatActivity): UserFavoriteViewModel {
        val factory = UserFavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[UserFavoriteViewModel::class.java]
    }

    private fun setListData(githubList: List<GithubUser>) {
        val adapter = UsersAdapter(githubList)
        binding?.rvUsersFavorite?.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                sendSelectedUser(data)
            }
        })
    }

    private fun sendSelectedUser(data: GithubUser) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(intent)
    }

}