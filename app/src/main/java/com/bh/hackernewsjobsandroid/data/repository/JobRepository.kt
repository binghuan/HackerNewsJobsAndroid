package com.bh.hackernewsjobsandroid.data.repository

import android.util.Log
import com.bh.hackernewsjobsandroid.data.local.Job
import com.bh.hackernewsjobsandroid.data.local.JobDao
import com.bh.hackernewsjobsandroid.data.remote.JobApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException

class JobRepository(private val jobDao: JobDao, private val apiService: JobApiService) {

    // 从 API 获取 Job 数据并存储到本地数据库，包括网页内容
    suspend fun fetchJobStories() {
        val response = apiService.getJobStories()
        if (response.isSuccessful) {
            response.body()?.forEach { jobId ->
                val jobResponse = apiService.getJobDetails(jobId)
                if (jobResponse.isSuccessful) {
                    jobResponse.body()?.let { jobDto ->
                        // 从 URL 获取网页内容
                        val webpageContent = fetchWebContent(jobDto.url)

                        // 构造 Job 实体
                        val job = Job(
                            id = jobDto.id,
                            title = jobDto.title,
                            url = jobDto.url ?: "",
                            score = jobDto.score,
                            by = jobDto.by,
                            time = jobDto.time,
                            text = jobDto.text ?: "",
                            webpageContent = webpageContent
                        )

                        Log.d("JobRepository", "Fetched job: $job")

                        // 存储到本地数据库
                        jobDao.insert(job)
                    }
                }
            }
        }
    }

    // 从 URL 获取网页内容，处理 404 和其他 HTTP 状态错误
    private suspend fun fetchWebContent(url: String?): String? = withContext(Dispatchers.IO) {
        try {
            url?.let {
                val document = Jsoup.connect(it).get()
                return@withContext document.body().text()  // 提取网页的文本内容
            }
        } catch (e: HttpStatusException) {
            // 处理 HTTP 状态错误，例如 404 错误
            println("Error fetching URL: ${e.statusCode} for $url")
        } catch (e: IOException) {
            // 处理其他 I/O 错误
            e.printStackTrace()
        }
        return@withContext null
    }

    suspend fun getAllJobs() = jobDao.getAllJobs()

    suspend fun searchJobs(keyword: String): List<Job> {
        return jobDao.searchJobs(keyword)
    }
}
