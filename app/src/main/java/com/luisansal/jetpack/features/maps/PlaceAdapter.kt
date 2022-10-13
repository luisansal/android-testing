package com.luisansal.jetpack.features.maps

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.luisansal.jetpack.R
import com.luisansal.jetpack.databinding.LayoutMapRowBinding
import com.luisansal.jetpack.domain.entity.Place

class PlaceAdapter(context: Context, private val resourceId: Int = R.layout.layout_map_row) :
    ArrayAdapter<Place>(context, resourceId) {

    override fun isEnabled(position: Int): Boolean {
        return (items[position].id != MapsFragment.MARKER_ON_MAP_NO_RESULTS_ID)
    }

    var selected: Place? = null
    var items: List<Place> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = (context as Activity).layoutInflater
        val view = LayoutMapRowBinding.inflate(inflater, parent, false)
        val place: Place = getItem(position)

        view.tvName?.text = place.name

        if (place.id == MapsFragment.MARKER_ON_MAP_ID) {
            view.imgMarker?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_choose_map_marker))
        } else {
            view.imgMarker?.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_autocomplete_marker))
        }
        if (place.id == MapsFragment.MARKER_ON_MAP_NO_RESULTS_ID) {
            view.imgMarker?.visibility = View.GONE
            view.tvName.gravity = Gravity.CENTER_HORIZONTAL
        }

        if (place.district.isNotEmpty())
            view.tvDistrict?.text = place.district
        else
            view.tvDistrict?.visibility = View.GONE
        return view.root
    }

    override fun getItem(position: Int): Place {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getFilter(): Filter {
        return placeFilter
    }

    private val placeFilter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults = FilterResults()
        override fun convertResultToString(resultValue: Any): CharSequence {
            val place: Place = resultValue as Place
            return place.name
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) = Unit
    }
}