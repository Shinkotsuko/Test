package com.example.shift87375

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shift87375.adapter.UsersAdapter
import com.example.shift87375.databinding.ActivityMainBinding
import com.example.shift87375.model.User
import com.example.shift87375.ui.UsersViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<UsersViewModel>()

    private val usersAdapter = UsersAdapter { user -> openDetails(user) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = usersAdapter
        binding.perezapusk.setOnClickListener {
            viewModel.refresh(26)
        }

        viewModel.state.observe(this) { state ->
            when (state) {
                is UsersViewModel.UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rcView.visibility = View.GONE
                }
                is UsersViewModel.UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rcView.visibility = View.VISIBLE
                    usersAdapter.submitList(state.users)
                }
                is UsersViewModel.UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rcView.visibility = View.GONE
                    Toast.makeText(this, "Ошибка: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.loadFromCache()
    }

    private fun openDetails(user: User) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("firstName", user.name.first)
            putExtra("lastName", user.name.last)
            putExtra("city", user.location.city)
            putExtra("country", user.location.country)
            putExtra("phone", user.phone)
            putExtra("avatar", user.picture.large)
            putExtra("email", user.email)
            putExtra("password", user.login.password)
            putExtra("gender", user.gender)
            putExtra("age", user.dob.age)
            putExtra("latitude", user.location.coordinates.latitude)
            putExtra("longitude", user.location.coordinates.longitude)
        }
        startActivity(intent)
    }
    @Suppress("DEPRECATION")
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}