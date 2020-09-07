package id.alan.exabytes.simplegis.report

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import id.alan.exabytes.simplegis.data.AppDatabase
import id.alan.exabytes.simplegis.data.Report
import id.alan.exabytes.simplegis.data.ReportRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReportRepository

    val allWords: LiveData<List<Report>>

    init {
        val wordsDao = AppDatabase.getDatabase(application, viewModelScope).reportDao()
        repository = ReportRepository(wordsDao)
        allWords = repository.allReports
    }
}
