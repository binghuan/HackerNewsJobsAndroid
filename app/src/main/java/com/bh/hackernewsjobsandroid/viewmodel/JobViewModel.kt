package com.bh.hackernewsjobsandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bh.hackernewsjobsandroid.data.local.Job
import com.bh.hackernewsjobsandroid.data.repository.JobRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JobViewModel(private val repository: JobRepository) : ViewModel() {
    private val _jobsList = MutableStateFlow<List<Job>>(emptyList())
    val jobsList = _jobsList.asStateFlow()

    val isFetching = MutableStateFlow(false)

    init {
        // 初始化时加载所有的 Job 项目
        loadAllJobs()
    }

    private fun loadAllJobs() {
        viewModelScope.launch {
            _jobsList.value = repository.getAllJobs()
        }
    }

    fun fetchAndStoreJobs() {
        viewModelScope.launch {
            isFetching.value = true
            repository.fetchJobStories()
            isFetching.value = false
            loadAllJobs()  // 数据更新后重新加载所有 Job
        }
    }

    fun searchJobs(keyword: String) {
        viewModelScope.launch {
            _jobsList.value = if (keyword.isEmpty()) {
                repository.getAllJobs()  // 关键字为空时显示所有 Job
            } else {
                repository.searchJobs(keyword)
            }
        }
    }
}
