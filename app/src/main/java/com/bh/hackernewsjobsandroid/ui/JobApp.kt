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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bh.hackernewsjobsandroid.data.local.Job
import com.bh.hackernewsjobsandroid.data.local.JobDao
import com.bh.hackernewsjobsandroid.data.remote.JobApiService
import com.bh.hackernewsjobsandroid.data.remote.JobDto
import com.bh.hackernewsjobsandroid.data.repository.JobRepository
import com.bh.hackernewsjobsandroid.viewmodel.JobViewModel
import retrofit2.Response

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
                JobItemView(job)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJobApp() {
    val dummyJobs = listOf(
        Job(id = 1, title = "Android Developer", url = "https://example.com", score = 100, by = "user", time = System.currentTimeMillis() / 1000, text = "Job description", webpageContent = ""),
        Job(id = 2, title = "Backend Developer", url = "https://example.com", score = 100, by = "user", time = System.currentTimeMillis() / 1000, text = "Job description", webpageContent = "")
    )
    val dummyJobDao = object : JobDao {
        override suspend fun insert(job: Job) {
            TODO("Not yet implemented")
        }

        override suspend fun getAllJobs() = dummyJobs
        override suspend fun searchJobs(keyword: String) = dummyJobs.filter { it.title.contains(keyword, true) }
    }
    val dummyApiService = object : JobApiService {
        override suspend fun getJobStories(): Response<List<Int>> {
            return Response.success(listOf(1, 2))
        }
        override suspend fun getJobDetails(jobId: Int): Response<JobDto> {
            return Response.success(JobDto(id = 1, title = "Android Developer", url = "https://example.com", score = 100, by = "user", time = System.currentTimeMillis() / 1000, text = "Job description", webpageContent = ""))
        }
    }
    val dummyRepository = JobRepository(dummyJobDao, dummyApiService)
    val dummyViewModel = JobViewModel(dummyRepository).apply {
        _jobsList.value = dummyJobs
    }
    JobApp(viewModel = dummyViewModel)
}