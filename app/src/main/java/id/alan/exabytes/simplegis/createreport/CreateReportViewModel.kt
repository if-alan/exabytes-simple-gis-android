package id.alan.exabytes.simplegis.createreport

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.alan.exabytes.simplegis.data.AppDatabase
import id.alan.exabytes.simplegis.data.Report
import id.alan.exabytes.simplegis.data.ReportRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateReportViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ReportRepository
    val allReports: LiveData<List<Report>>

    init {
        val reportsDao = AppDatabase.getDatabase(application, viewModelScope).reportDao()
        repository = ReportRepository(reportsDao)
        allReports = repository.allReports
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(report: Report) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(report)
    }
}
