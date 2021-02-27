package com.luisansal.jetpack.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.R
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_list.view.*

class FeaturesAdapter(private val onItemClickListener: (model: FeaturesEnum) -> Unit) : RecyclerView.Adapter<FeaturesAdapter.ViewHolder>() {

    var dataSet : List<FeaturesEnum> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return ViewHolder(mView)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: FeaturesEnum,position:Int) = with(itemView) {
            itemView.tvName?.text = model.title
            itemView.setOnClickListener {
                onItemClickListener.invoke(model)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]
        holder.bind(data,position)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }


}


