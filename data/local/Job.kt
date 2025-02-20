package com.bh.hackernewsjobsandroid.data.local

data class Job(
    val id: Int,
    val title: String,
    val url: String,
    val score: Int,
    val by: String,
    val time: Long,
    val text: String?,
    val webpageContent: String
)
