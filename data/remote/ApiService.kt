package com.bh.hackernewsjobsandroid.data.remote

import com.bh.hackernewsjobsandroid.data.local.Job

interface ApiService {
    suspend fun fetchJobStories(): List<Job>
}
