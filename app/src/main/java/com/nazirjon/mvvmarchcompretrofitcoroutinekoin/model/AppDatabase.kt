package com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.dao.UserDao
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.entity.GithubUser


@Database(entities = [GithubUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}