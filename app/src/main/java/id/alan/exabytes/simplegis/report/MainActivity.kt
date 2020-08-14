package id.alan.exabytes.simplegis.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.alan.exabytes.simplegis.R
import id.alan.exabytes.simplegis.createreport.CreateReportActivity

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    val LOCATION = LatLng(-6.2235847, 106.8356867)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMap()

        handleButton()
    }

    private fun setMap(){
        val mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return
        with(map){
            moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 10f))
        }
    }

    private fun handleButton(){
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