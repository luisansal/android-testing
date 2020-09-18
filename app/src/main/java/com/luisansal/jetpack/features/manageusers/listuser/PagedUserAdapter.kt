package com.luisansal.jetpack.features.manageusers.listuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luisansal.jetpack.R
import com.luisansal.jetpack.domain.entity.User
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_user_list_2.view.*

class PagedUserAdapter : PagedListAdapter<User, PagedUserAdapter.PagingUserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingUserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_2, parent, false)
        return PagingUserViewHolder(mView)

    }

    override fun onBindViewHolder(holder: PagingUserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    inner class PagingUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: User) = with(itemView) {
            itemView.tvName?.text = model.name
            itemView.tvLastName?.text = model.lastName
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


