package com.luisansal.jetpack.features.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.luisansal.jetpack.R
import androidx.recyclerview.widget.RecyclerView
import com.luisansal.jetpack.databinding.ItemMainListBinding

class FeaturesAdapter(private val onItemClickListener: (model: FeaturesEnum) -> Unit) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    var dataSet: List<FeaturesEnum> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = ItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mView)
    }

    inner class ViewHolder(val binding: ItemMainListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: FeaturesEnum, position: Int) = with(itemView) {
            binding.tvName?.text = model.title
            binding.root.setOnClickListener {
                onItemClickListener.invoke(model)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}


