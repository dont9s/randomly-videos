package com.instory.latest.topic.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.instory.latest.trending.viral.news.databinding.ListItemTopicBinding
import com.instory.latest.topic.data.Topic

class TopicAdapter(val viewModel: TopicViewModel) : ListAdapter<Topic, TopicAdapter.ViewHolder>(DiffCallback()) {


    class ViewHolder(
            val binding: ListItemTopicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Topic) {
            binding.apply {
                clickListener = listener
                topic = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemTopicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = getItem(position)
        holder.apply {
            bind(createOnClickListener(topic.topic, topic.name, topic.isSelected, holder.binding), topic)
            itemView.tag = topic
        }
    }

    private fun createOnClickListener(topic: String, name: String?, isSelected: Boolean,
                                      binding: ListItemTopicBinding): View.OnClickListener {

        return View.OnClickListener {
            /* val direction = TopicFragmentDirections.actionThemeFragmentToSetsFragment(id, name)
             it.findNavController().navigate(direction)*/

            /*  if (binding.flSelect.visibility == View.GONE)
                  binding.flSelect.visibility = View.VISIBLE
              else
                  binding.flSelect.visibility = View.GONE*/

            viewModel.repository.updateTopicIsSeleceted(topic, !isSelected)
        }
    }
}


private class DiffCallback : DiffUtil.ItemCallback<Topic>() {

    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.topic == newItem.topic
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem == newItem
    }
}