package com.example.shift87375.data

import com.example.shift87375.api.ApiClient
import com.example.shift87375.db.UserDao
import com.example.shift87375.model.User
import com.example.shift87375.model.toEntity
import com.example.shift87375.model.toModel

class UsersRepository(
    private val userDao: UserDao
) {
    private val api = ApiClient.api

    suspend fun getCachedUsers(): List<User> =
        userDao.getAll().map { it.toModel() }

    suspend fun refreshUsers(count: Int = 26): Result<List<User>> =
        try {
            val response = api.getUsers(results = count)
            val users = response.results
            userDao.clear()
            userDao.insertAll(users.map { it.toEntity() })
            Result.success(users)
        } catch (t: Throwable) {
            Result.failure(t)
        }
}