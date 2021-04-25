package com.jccsisc.earthaquakemonitor.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jccsisc.earthaquakemonitor.EarthquakeModel
import com.jccsisc.earthaquakemonitor.R
import com.jccsisc.earthaquakemonitor.databinding.ItemEqBinding
import java.sql.Time

//darle el nombre de la clase a nuestro TAG
private val TAG = EqAdapter::class.java.simpleName
//extendemos de un ListAdapter                                    recibimos el DiffCallback
class EqAdapter(): ListAdapter<EarthquakeModel, EqAdapter.EqViewHolder>(DiffCallback) {

    //click con Lambda al dale clic nos retorna un terremoto
    lateinit var onItemClickListener: (EarthquakeModel) -> Unit

    //Hacemos uso de DiffCallback pa identificar que item se agregó o borró es lo mismo siempre
    companion object DiffCallback: DiffUtil.ItemCallback<EarthquakeModel>() {

        override fun areItemsTheSame(oldItem: EarthquakeModel, newItem: EarthquakeModel): Boolean {
            return oldItem.id == newItem.id //igualamos si el item nuevo con el viejo es el mismo
        }

        override fun areContentsTheSame(oldItem: EarthquakeModel, newItem: EarthquakeModel): Boolean {
            return oldItem == newItem //para igualar modelos debe de ser una data class
        }
    }

    //Aqui pasamos el layout del item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {
        val binding = ItemEqBinding.inflate(LayoutInflater.from(parent.context)) //inflamos el layout
        return EqViewHolder(binding)
    }

    //Pasando el elemento corresponiente para pintarle los datos
    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        val earthguake = getItem(position) //obtenemos el elemento
        holder.bind(earthguake) //mandamos todos los datos al ViewHolder
    }

    //creamos nuesto viewholder esta clase es la que recibe el layout item para poder reciclar
    inner class EqViewHolder(private val binding: ItemEqBinding) : RecyclerView.ViewHolder(binding.root) {
        //creando un metodo par recibir un modelo
        fun bind(earthquake: EarthquakeModel) = with(binding) {
            tvMagnitude.text = root.context.getString(R.string.magnitude_format, earthquake.magnintude)
            tvTime.text = Time( earthquake.time).toString()
            tvPlace.text = earthquake.place

            root.setOnClickListener {
                //si está inicializada la variable landa mandamos llamar el onItemClickListener
                if (::onItemClickListener.isInitialized) {
                    onItemClickListener(earthquake) //llamamos nuestro landa, donde lo usemos le asignamos la accion
                } else {
                    Log.e(TAG, "onItemClickListener no inicializado")
                }
            }
            executePendingBindings() //para prevenir lags en los elementos
        }
    }
}