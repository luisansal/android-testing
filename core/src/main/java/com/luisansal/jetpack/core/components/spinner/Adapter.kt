package com.luisansal.jetpack.core.components.spinner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import pe.com.luisansal.core.R

class Adapter(
    context: Context,
    resource: Int
) : ArrayAdapter<JetpackSpinner.Model>(context, resource) {

    var dataSet: List<JetpackSpinner.Model> = mutableListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model = dataSet[position]
        val label = super.getView(position, convertView, parent) as TextView
        label.text = model.value
        if (position == dataSet.size - 1) {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            label.setTextColor(ContextCompat.getColor(context, R.color.gray))
        } else {
            label.setTextColor(ContextCompat.getColor(context, R.color.green_light))
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.green_light_less1))
        }
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model = dataSet[position]
        val label = super.getView(position, convertView, parent) as TextView
        label.text = model.value
        return label
    }

    override fun getItem(position: Int): JetpackSpinner.Model? {
        return dataSet[position]
    }

    override fun getCount(): Int {
        return if (dataSet.isNotEmpty()) dataSet.size - 1 else dataSet.size
    }
}