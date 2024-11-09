package com.bh.hackernewsjobsandroid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bh.hackernewsjobsandroid.data.local.JobDatabase
import com.bh.hackernewsjobsandroid.data.remote.RetrofitInstance
import com.bh.hackernewsjobsandroid.data.repository.JobRepository
import com.bh.hackernewsjobsandroid.viewmodel.JobViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = JobDatabase.getDatabase(applicationContext)
        val repository = JobRepository(db.jobDao(), RetrofitInstance.api)
        val viewModel = JobViewModel(repository)

        setContent {
            JobApp(viewModel)
        }
    }
}
