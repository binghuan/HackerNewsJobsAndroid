package com.bh.hackernewsjobsandroid.data.local

interface JobDao {
    suspend fun getAllJobs(): List<Job>
    suspend fun insertJobs(jobs: List<Job>)
    suspend fun searchJobs(keyword: String): List<Job>
}
