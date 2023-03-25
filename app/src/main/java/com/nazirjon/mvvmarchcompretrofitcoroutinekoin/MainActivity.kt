package com.nazirjon.mvvmarchcompretrofitcoroutinekoin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.utils.LoadingState
import com.nazirjon.mvvmarchcompretrofitcoroutinekoin.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel.data.observe(this) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                it.forEach { githubUser ->
                    Toast.makeText(baseContext, githubUser.login, Toast.LENGTH_SHORT).show()
                }
            }
        }

        userViewModel.loadingState.observe(this) {
            when (it.status) {
                LoadingState.Status.FAILED -> Toast.makeText(baseContext, it.msg, Toast.LENGTH_SHORT).show()
                LoadingState.Status.RUNNING -> Toast.makeText(baseContext, "Loading", Toast.LENGTH_SHORT).show()
                LoadingState.Status.SUCCESS -> Toast.makeText(baseContext, "Success", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
