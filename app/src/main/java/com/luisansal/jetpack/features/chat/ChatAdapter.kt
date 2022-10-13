package com.luisansal.jetpack.features.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.luisansal.jetpack.R
import com.luisansal.jetpack.databinding.ItemChatBinding

class ChatAdapter(var data: MutableList<ChatModel> = mutableListOf()) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ChatModel) {
            if (model.self) {
                binding.tvMessageRight?.text = model.message
                binding.tvMessageRight?.visibility = View.VISIBLE
                binding.tvMessageLeft?.visibility = View.GONE
                binding.cvWrap?.apply{
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.success))
                }
            } else {
                binding.tvMessageLeft?.text = model.message
                binding.tvMessageLeft?.visibility = View.VISIBLE
                binding.tvMessageRight?.visibility = View.GONE
                binding.cvWrap?.apply{
                    setBackgroundColor(ContextCompat.getColor(this.context, R.color.blue))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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