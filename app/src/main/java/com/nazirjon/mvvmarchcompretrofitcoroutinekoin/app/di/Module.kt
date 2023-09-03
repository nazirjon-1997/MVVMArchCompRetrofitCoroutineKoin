package com.nazirjon.mvvmarchcompretrofitcoroutinekoin.app.di

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.AppDatabase
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.api.UserApi
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.dao.UserDao
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.model.repository.UserRepository
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.viewmodel.UserViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
}

val apiModule = module {
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)
    single { provideUserApi(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder().cache(cache)
        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): UserDao = database.userDao

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(api: UserApi, dao: UserDao): UserRepository = UserRepository(api, dao)
    single { provideUserRepository(get(), get()) }
}