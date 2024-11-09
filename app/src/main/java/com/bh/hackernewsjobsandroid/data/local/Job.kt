package com.bh.hackernewsjobsandroid.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jobs")
data class Job(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val url: String,
    val score: Int,
    val by: String,
    val time: Long,
    val text: String?,
    val webpageContent: String?
)