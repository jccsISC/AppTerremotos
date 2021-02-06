package com.jccsisc.earthaquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jccsisc.earthaquakemonitor.databinding.ActivityDetailsBinding
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val model = intent.getParcelableExtra<EarthquakeModel>("model")

        binding.apply {
            txtMagnitude.text = baseContext.getString(R.string.magnitude_format, model?.magnintude)
            txtLongitude.text = model?.longitude.toString()
            txtLatitude.text = model?.latitude.toString()
            txtPlace.text = model?.place
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val time = model?.time
            val date = time?.let { Date(it) }
            val formattedString = simpleDateFormat.format(date)
            val myTime = model?.time?.let { Time(it) }
            val result = "$formattedString $myTime"
            txtTime.text = result
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}