package com.example.shift87375.model

import com.example.shift87375.db.UserEntity
import com.example.shift87375.model.*

fun User.toEntity(): UserEntity =
    UserEntity(
        uuid = login.uuid,
        firstName = name.first,
        lastName = name.last,
        city = location.city,
        country = location.country,
        phone = phone,
        avatar = picture.large,
        email = email,
        gender = gender,
        lat = location.coordinates.latitude,
        lng = location.coordinates.longitude,
        password = login.password,
        age = dob.age
    )

fun UserEntity.toModel(): User =
    User(
        name = Name(title = null, first = firstName, last = lastName),
        location = Location(
            city = city,
            country = country,
            coordinates = Coordinates(latitude = lat, longitude = lng)
        ),
        phone = phone,
        login = Login(uuid = uuid, password = password),
        picture = Picture(large = avatar, thumbnail = avatar),
        email = email,
        gender = gender,
        dob = Dob(date = "", age = age)
    )