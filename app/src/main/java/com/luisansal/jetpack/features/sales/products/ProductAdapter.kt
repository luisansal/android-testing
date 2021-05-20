package com.luisansal.jetpack.features.sales.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luisansal.jetpack.databinding.ItemProductListBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var binding: ItemProductListBinding
    var dataSet = emptyList<Model>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(binding: ItemProductListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: Model) {
            binding.model = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    data class Model(val title: String, val descripcion: String, val price: String)
}