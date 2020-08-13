package id.alan.exabytes.simplegis.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.alan.exabytes.simplegis.R
import id.alan.exabytes.simplegis.createreport.CreateReportActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivity(
                Intent(
                    this,
                    CreateReportActivity::class.java
                )
            )
        }
    }
}