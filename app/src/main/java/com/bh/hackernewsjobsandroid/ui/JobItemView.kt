package com.bh.hackernewsjobsandroid.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bh.hackernewsjobsandroid.data.local.Job
import com.bh.hackernewsjobsandroid.utils.formatTime

@Composable
fun JobItemView(job: Job) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(job.url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(job.title, style = MaterialTheme.typography.titleLarge)
            Text(
                "Posted by ${job.by} on ${formatTime(job.time * 1000)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = AnnotatedString(job.url),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                textDecoration = TextDecoration.Underline // 显示下划线，表示链接
            )
            if (job.text != null) {
                Text(
                    job.text,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewJobItemView() {
    JobItemView(
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
