package com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.repository

import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.api.UserApi
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.dao.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userApi: UserApi, private val userDao: UserDao) {

    val data = userDao.findAll()

    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val users = userApi.getAllAsync().await()
            userDao.add(users)
        }
    }
}