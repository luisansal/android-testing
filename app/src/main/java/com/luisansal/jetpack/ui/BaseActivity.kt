package com.luisansal.jetpack.ui

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.luisansal.jetpack.R

/**
 * Created by Luis on 23/2/2016.
 */
class BaseActivity : AppCompatActivity() {

    @JvmOverloads
    fun customActionBar(titulo: String? = null, subtitulo: String? = null) {
        val mActionBar = supportActionBar
        mActionBar!!.setDisplayShowHomeEnabled(false)
        mActionBar.setDisplayShowTitleEnabled(false)
        //        LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        val mInflater = LayoutInflater.from(this)
        val customView = mInflater.inflate(R.layout.actionbar_item_row, null)
        val back = customView.findViewById<View>(R.id.back) as ImageView
        back.setOnClickListener { onBackPressed() }
        //        back.setVisibility(View.INVISIBLE);
        val textTitulo = customView.findViewById<View>(R.id.titulo) as TextView
        textTitulo.text = titulo

        mActionBar.customView = customView

        mActionBar.setDisplayShowCustomEnabled(true)

        val parent = customView.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)
    }
}
