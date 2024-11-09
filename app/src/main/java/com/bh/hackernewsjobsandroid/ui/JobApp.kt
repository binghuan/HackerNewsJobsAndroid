package com.bh.hackernewsjobsandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bh.hackernewsjobsandroid.viewmodel.JobViewModel

@Composable
fun JobApp(viewModel: JobViewModel) {
    var searchKeyword by remember { mutableStateOf("") }
    val jobsList by viewModel.jobsList.collectAsState()
    val isFetching by viewModel.isFetching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(value = searchKeyword, onValueChange = { newKeyword ->
                searchKeyword = newKeyword
                viewModel.searchJobs(newKeyword) // 实时搜索，关键字为空时显示所有 Job
            }, label = { Text("Search jobs...") }, modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Fetch Icon Button
            IconButton(onClick = { viewModel.fetchAndStoreJobs() }) {
                Icon(
                    imageVector = Icons.Default.Refresh, contentDescription = "Fetch Jobs"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isFetching) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Found ${jobsList.size} jobs",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(jobsList) { job ->
                JobItem(job)
            }
        }
    }
}