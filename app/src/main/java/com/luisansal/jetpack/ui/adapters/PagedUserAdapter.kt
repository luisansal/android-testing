package com.luisansal.jetpack.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.luisansal.jetpack.R
import com.luisansal.jetpack.domain.entity.User
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PagedUserAdapter : PagedListAdapter<User, PagedUserAdapter.PagingUserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingUserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_2, parent, false)
        return PagingUserViewHolder(mView)

    }


    override fun onBindViewHolder(holder: PagingUserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bindTo(user)
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear()
        }
    }


    inner class PagingUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvLastName: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvLastName = itemView.findViewById(R.id.tvLastName)
        }

        fun bindTo(user: User) {
            tvName.text = user.name
            tvLastName.text = user.lastName
        }

        fun clear() {
            tvName.text = null
            tvLastName.text = null
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(oldConcert: User, newConcert: User): Boolean {
                return oldConcert.id == newConcert.id
            }

            override fun areContentsTheSame(oldConcert: User,
                                            newConcert: User): Boolean {
                return oldConcert == newConcert
            }
        }
    }
}


