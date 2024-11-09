package com.bh.hackernewsjobsandroid.data.local

import android.content.Context
import androidx.room.*

@Dao
interface JobDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(job: Job)

    @Query("SELECT * FROM jobs ORDER BY time DESC")
    suspend fun getAllJobs(): List<Job>

    @Query("""
        SELECT * FROM jobs 
        WHERE title LIKE '%' || :keyword || '%' 
        OR webpageContent LIKE '%' || :keyword || '%'
        ORDER BY time DESC
    """)
    suspend fun searchJobs(keyword: String): List<Job>
}

@Database(entities = [Job::class], version = 1)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, JobDatabase::class.java, "job_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
