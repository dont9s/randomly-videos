package com.instory.latest.topic.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.instory.latest.data.Result
import com.instory.latest.di.Injectable
import com.instory.latest.di.injectViewModel
import com.instory.latest.trending.viral.news.R
import com.instory.latest.trending.viral.news.databinding.FragmentTopicBinding
import com.instory.latest.ui.GridItemDecoration
import com.instory.latest.ui.hide
import com.instory.latest.ui.show
import javax.inject.Inject

class TopicFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TopicViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var binding: FragmentTopicBinding
    private lateinit var adapter: TopicAdapter

    private val COLUMN_COUNT = 2

    companion object {
        fun getInstance() = TopicFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentTopicBinding.inflate(inflater, container, false)
        context ?: return binding.root

        adapter = TopicAdapter(viewModel)
        layoutManager = GridLayoutManager(context, COLUMN_COUNT)

        binding.rvTopics.addItemDecoration(
                GridItemDecoration(resources.getDimension(R.dimen.dimen_20).toInt(), true))
        binding.rvTopics.layoutManager = layoutManager
        binding.rvTopics.adapter = adapter


        subscribeUi(binding, adapter)
        setHasOptionsMenu(true)


        return binding.root
    }


    private fun subscribeUi(binding: FragmentTopicBinding, adapter: TopicAdapter) {
        viewModel.topics.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.loadingShimmer.hide()
                    result.data?.let { adapter.submitList(it) }
                }
                Result.Status.LOADING -> binding.loadingShimmer.show()
                Result.Status.ERROR -> {
                    binding.loadingShimmer.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

}