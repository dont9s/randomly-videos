package com.randomly.videos.trendingrepo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.randomly.videos.repodetail.ui.RepoDetailFragment
import com.randomly.videos.R
import com.randomly.videos.databinding.ListItemRepoBinding
import com.randomly.videos.trendingrepo.data.Post
import com.randomly.videos.trendingrepo.data.User

class PostAdapter(val activity: FragmentActivity) : ListAdapter<Post, PostAdapter.ViewHolder>(DiffCallback()) {


    class ViewHolder(
            val binding: ListItemRepoBinding
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
        return ViewHolder(ListItemRepoBinding.inflate(
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
                                      binding: ListItemRepoBinding): View.OnClickListener {

        return View.OnClickListener {
/*
            activity.supportFragmentManager.beginTransaction().replace(R.id.fl_fragment,
                    RepoDetailFragment.getInstance(user))
                    .addToBackStack(null)
                    .commit()*/

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