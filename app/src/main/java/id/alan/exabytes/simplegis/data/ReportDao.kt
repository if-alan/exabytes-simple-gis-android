package id.alan.exabytes.simplegis.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReportDao {
    @Query("SELECT * FROM Report")
    fun getAll(): LiveData<List<Report>>

    @Insert
    fun insert(vararg report: Report)
}
