package com.randomly.videos.trendingrepo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.randomly.videos.databinding.ListItemPostBinding
import com.randomly.videos.trendingrepo.data.Post

class PostAdapter() : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback()) {


    class ViewHolder(
            val binding: ListItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Post) {
            binding.apply {
                clickListener = listener
                post = item
                executePendingBindings()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.apply {
            bind(createOnClickListener(post, holder.binding), post)
            itemView.tag = post


        }
    }

    private fun createOnClickListener(post: Post,
                                      binding: ListItemPostBinding): View.OnClickListener {

        return View.OnClickListener {


        }
    }


    fun sort(sortBy: Int) {

        when (sortBy) {
            VideoPostsFragment.BY_DATE -> {

                val list = currentList.toMutableList()


                list.sortByDescending { it.eventDate }

                submitList(list)
            }
            VideoPostsFragment.BY_LIKES -> {

                val list = currentList.toMutableList()


                list.sortByDescending { it.likes }

                submitList(list)
            }
            VideoPostsFragment.BY_SHARES -> {

                val list = currentList.toMutableList()


                list.sortByDescending { it.shares }

                submitList(list)


            }
            VideoPostsFragment.BY_VIEWS -> {

                val list = currentList.toMutableList()


                list.sortByDescending { it.views }

                submitList(list)


            }
        }

    }
}


private class DiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.eventDate == newItem.eventDate
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}