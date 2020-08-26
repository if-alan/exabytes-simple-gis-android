package id.alan.exabytes.simplegis.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ReportRepository(private val reportDao: ReportDao) {

    val allReports: LiveData<List<Report>> = reportDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(report: Report) {
        reportDao.insert(report)
    }
}