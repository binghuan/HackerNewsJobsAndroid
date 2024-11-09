package com.bh.hackernewsjobsandroid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bh.hackernewsjobsandroid.data.local.Job
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy, HH:mm", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

@Composable
fun JobItem(job: Job) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(job.title, style = MaterialTheme.typography.titleLarge)
        Text(
            "Posted by ${job.by} on ${formatTime(job.time * 1000)}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            job.url,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium
        )
        if (job.text != null) {
            Text(
                job.text,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview
@Composable
fun PreviewJobItem() {
    JobItem(
        Job(
            title = "Job Title",
            url = "https://example.com",
            score = 100,
            by = "user",
            time = System.currentTimeMillis() / 1000,
            text = "Job description",
            id = 1,
            webpageContent = ""
        )
    )
}