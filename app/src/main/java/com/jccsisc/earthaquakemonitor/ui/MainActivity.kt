package com.jccsisc.earthaquakemonitor.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jccsisc.earthaquakemonitor.data.model.EarthquakeModel
import com.jccsisc.earthaquakemonitor.databinding.ActivityMainBinding
import com.jccsisc.earthaquakemonitor.ui.adapter.EqAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eqList = mutableListOf<EarthquakeModel>()
        eqList.add(EarthquakeModel("1", "Buenos Aires", 4.3, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("2", "Lima", 2.9, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("3", "New York", 6.0, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("4", "Bogotá", 1.8, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("5", "Caracas", 3.5, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("6", "Madrid", 0.6, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("7", "Acia", 5.1, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("8", "Bogotá", 1.8, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("9", "Caracas", 3.5, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("10", "Madrid", 0.6, 273846152L, -102.4756, 28.47365))
        eqList.add(EarthquakeModel("11", "Acia", 5.1, 273846152L, -102.4756, 28.47365))

        val adapter = EqAdapter()
        binding.rvEarth.adapter = adapter
        adapter.submitList(eqList)

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

        //mostrar el emptyView si la lista está vacia
        if (eqList.isEmpty()) binding.eqEmptyView.visibility = View.VISIBLE else binding.eqEmptyView.visibility = View.GONE
    }
}