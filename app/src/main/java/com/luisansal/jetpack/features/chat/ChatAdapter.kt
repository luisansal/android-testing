package com.luisansal.jetpack.features.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.luisansal.jetpack.R
import kotlinx.android.synthetic.main.item_chat.view.*

class ChatAdapter(var data: MutableList<ChatModel> = mutableListOf()) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: ChatModel) = with(itemView) {
            if (model.self) {
                itemView.tvMessageRight?.text = model.message
                itemView.tvMessageRight?.visibility = View.VISIBLE
                itemView.tvMessageLeft?.visibility = View.GONE
                itemView.cvWrap?.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.success))
            } else {
                itemView.tvMessageLeft?.text = model.message
                itemView.tvMessageLeft?.visibility = View.VISIBLE
                itemView.tvMessageRight?.visibility = View.GONE
                itemView.cvWrap?.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.blue))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addItem(model: ChatModel) {
        data.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data[position]
        holder.bind(model)
    }
}