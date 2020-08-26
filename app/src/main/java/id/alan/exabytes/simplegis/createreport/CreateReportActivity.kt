package id.alan.exabytes.simplegis.createreport

import android.app.Activity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import id.alan.exabytes.simplegis.R
import id.alan.exabytes.simplegis.data.Report
import kotlinx.android.synthetic.main.activity_create_report.*
import java.util.*

class CreateReportActivity : AppCompatActivity() {
    private lateinit var createReportViewModel: CreateReportViewModel

    private lateinit var report: Report

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_report)
        createReportViewModel = ViewModelProvider(this).get(CreateReportViewModel::class.java)

        clickListener()
    }

    private fun clickListener() {
        ivEvent.setOnClickListener { takeImageFromGallery() }

        btnReport.setOnClickListener {
            createReportViewModel.insert(report)
            finish()
        }
    }

    private fun takeImageFromGallery() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(pickPhoto, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri? = data?.data
                val realPath = getRealPath(selectedImage)
                val latlng = getImageLocation(realPath)

                if (latlng != null) {
                    handleImageData(selectedImage, latlng)
                    ivEvent.isEnabled = false
                } else {
                    handleWhenNoImageData()
                }
            }
            2 -> {
            }
            3 -> {
            }
        }
    }

    private fun handleImageData(selectedImage: Uri?, latlng: LatLng) {
        val address = getAddress(latlng)

        ivEvent.setImageURI(selectedImage)
        tvCordinate.text = latlng.toString()
        tvAddress.text = address

        //Save data to Model
        report = Report(
            image = selectedImage!!.path!!,
            lat = latlng.latitude.toString(),
            lng = latlng.longitude.toString(),
            address = address
        )
    }

    private fun handleWhenNoImageData() {
        Toast.makeText(
            this,
            "Gambar tidak ada data lokasi",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getRealPath(uri: Uri?): String? {
        return uri?.let {
            contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )?.use { cursor ->
                cursor.moveToFirst()
                val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

                return cursor.getString(column_index)
            }
        }
    }

    private fun getImageLocation(path: String?): LatLng? {
        val exifInterface = ExifInterface(path)
        val latLong = FloatArray(2)
        val hasLatLong = exifInterface.getLatLong(latLong)
        return if (hasLatLong) {
            LatLng(latLong[0].toDouble(), latLong[1].toDouble())
        } else {
            null
        }
    }

    private fun getAddress(latLng: LatLng): String {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())

        addresses = geocoder.getFromLocation(
            latLng.latitude,
            latLng.longitude,
            1
        )

        return addresses[0]
            .getAddressLine(0)
    }
}