package com.bh.hackernewsjobsandroid.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JobApiService {
    @GET("jobstories.json")
    suspend fun getJobStories(): Response<List<Int>>

    @GET("item/{id}.json")
    suspend fun getJobDetails(@Path("id") jobId: Int): Response<JobDto>
}
