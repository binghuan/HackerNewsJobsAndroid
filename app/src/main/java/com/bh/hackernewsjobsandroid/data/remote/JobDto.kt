package com.bh.hackernewsjobsandroid.data.remote

data class JobDto(
    val id: Long,
    val title: String,
    val url: String?,
    val score: Int,
    val by: String,
    val time: Long,
    val text: String?,
    val webpageContent: String?
)