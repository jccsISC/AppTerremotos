package com.jccsisc.earthaquakemonitor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.api.StatusResponse
import com.jccsisc.earthaquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(application)).get(MainviewModel::class.java)
    }
    private val adapter = EqAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.eqList.observe(this, Observer { eqList ->
            binding.rvEarth.adapter = adapter
            adapter.submitList(eqList)
            adapter.notifyDataSetChanged()
            //mostrar el emptyView si la lista está vacia
            emptyView(eqList)
        })

        viewModel.status.observe(this, Observer {
            when {
                it == StatusResponse.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                it == StatusResponse.DONE -> {
                    binding.progressCircular.visibility = View.GONE
                }
                it == StatusResponse.NOT_INTERNET_CONNECTION -> {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
                }
            }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun emptyView(it: MutableList<EarthquakeModel>) {
        if (it.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else binding.eqEmptyView.visibility = View.GONE
    }
}