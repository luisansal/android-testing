package com.luisansal.jetpack.features.manageusers.listuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.luisansal.jetpack.databinding.ItemUserListBinding
import com.luisansal.jetpack.domain.entity.User

class PagedUserAdapter : PagedListAdapter<User, PagedUserAdapter.PagingUserViewHolder>(DIFF_CALLBACK) {
    private lateinit var binding : ItemUserListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingUserViewHolder {
        binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PagingUserViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PagingUserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.bind(it) }
    }

    inner class PagingUserViewHolder(binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: User) = with(binding) {
            binding.tvName.text = model.names
            binding.tvLastName.text = model.lastNames
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


