package com.example.shift87375

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.shift87375.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backhome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        val firstName = intent.getStringExtra("firstName")
        val lastName = intent.getStringExtra("lastName")
        val city = intent.getStringExtra("city")
        val country = intent.getStringExtra("country")
        val phone = intent.getStringExtra("phone")
        val avatar = intent.getStringExtra("avatar")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")
        val gender = intent.getStringExtra("gender")
        val age = intent.getIntExtra("age", -1)
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")

        binding.tvEmail.text = email
        binding.tvPassword.text = password
        binding.tvGender.text = gender
        binding.tvAge.text = if (age >= 0) age.toString() else "-"
        binding.tvName.text = "$firstName $lastName"
        binding.tvCity.text = "$city, $country"
        binding.tvPhone.text = phone
        binding.tvCoordinates.text = listOfNotNull(latitude, longitude).joinToString(", ")

        binding.ivAvatar.load(avatar) {
            crossfade(true)
            placeholder(R.drawable.random_avatar)
            error(R.drawable.random_avatar)
        }

        binding.tvEmail.setOnClickListener {
            val mail = binding.tvEmail.text?.toString().orEmpty()
            if (mail.isNotBlank()) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$mail")
                }
                startActivitySafe(intent)
            }
        }

        binding.tvPhone.setOnClickListener {
            val number = binding.tvPhone.text?.toString().orEmpty()
            if (number.isNotBlank()) {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$number")
                }
                startActivitySafe(intent)
            }
        }

        binding.tvCoordinates.setOnClickListener {
            val lat = latitude
            val lng = longitude
            val label = "$city, $country"

            val uri = if (!lat.isNullOrBlank() && !lng.isNullOrBlank()) {
                Uri.parse("geo:$lat,$lng?q=$lat,$lng($label)")
            } else {
                Uri.parse("geo:0,0?q=${Uri.encode(label)}")
            }

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivitySafe(intent)
        }
    }

    private fun startActivitySafe(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Нет приложения для обработки", Toast.LENGTH_SHORT).show()
        }
    }
}