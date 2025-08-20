package com.example.shift87375.model

data class RandomUserResponse(
    val results: List<User>
)

data class User(
    val name: Name,
    val location: Location,
    val phone: String,
    val login: Login,
    val picture: Picture,
    val email: String,
    val gender: String,
    val dob: Dob
)

data class Name(
    val title: String?,
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val country: String,
    val coordinates: Coordinates
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Dob(
    val date: String,
    val age: Int
)

data class Login(
    val uuid: String,
    val password: String
)

data class Picture(
    val large: String,
    val thumbnail: String
)