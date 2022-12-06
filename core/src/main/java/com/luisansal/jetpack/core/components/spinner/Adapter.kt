package com.luisansal.jetpack.core.components.spinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.luisansal.jetpack.core.R

class Adapter<T>(
    context: Context,
    resource: Int =  androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
    private val items: List<T>
) : ArrayAdapter<T>(context, resource, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model = getItem(position)
        val label = super.getView(position, convertView, parent) as TextView
        label.text = model?.toString()
        if (position == 0) {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            label.setTextColor(ContextCompat.getColor(context, R.color.gray))
        } else {
            label.setTextColor(ContextCompat.getColor(context, R.color.green_400))
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.green_200))
        }
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model = items[position]
        val label = super.getView(position, convertView, parent) as TextView
        label.text = model.toString()
        return label
    }

    override fun getItem(position: Int): T? {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }
}