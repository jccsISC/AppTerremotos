package com.jccsisc.earthaquakemonitor.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jccsisc.earthaquakemonitor.DetailsActivity
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.R
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
                    Toast.makeText(this, "No hay conexión a internet \uD83E\uDD7A", Toast.LENGTH_SHORT).show()
                }
            }
        })

        adapter.onItemClickListener = {
            val intent = Intent(applicationContext, DetailsActivity::class.java)
            intent.putExtra("model", it)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when(itemId) {
            R.id.main_menu_sort_magnitude -> {
                viewModel.reloadEarthquakes(true)
            }
            R.id.main_menu_sort_time -> {
                viewModel.reloadEarthquakes(false)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun emptyView(it: MutableList<EarthquakeModel>) {
        if (it.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else binding.eqEmptyView.visibility = View.GONE
    }
}