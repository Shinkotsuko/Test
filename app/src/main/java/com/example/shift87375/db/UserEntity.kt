package com.example.shift87375.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uuid: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val country: String,
    val phone: String,
    val avatar: String,
    val email: String,
    val gender: String,
    val lat: String,
    val lng: String,
    val password: String,
    val age: Int
)