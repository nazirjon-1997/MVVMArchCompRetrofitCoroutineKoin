package com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.api

import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.entity.GithubUser
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface UserApi {

    @GET("users")
    fun getAllAsync(): Deferred<List<GithubUser>>
}