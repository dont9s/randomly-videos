package com.top.github.trendingrepo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.top.github.R
import com.top.github.data.Result
import com.top.github.di.Injectable
import com.top.github.di.injectViewModel
import com.top.github.ui.GridItemDecoration
import javax.inject.Inject

class TrendingRepoFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TrendingRepoViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var binding: FragmentRepoBinding
    private lateinit var adapter: TrendingRepoAdapter

    private val COLUMN_COUNT = 2

    companion object {
        fun getInstance() = TrendingRepoFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentRepoBinding.inflate(inflater, container, false)
        context ?: return binding.root

        adapter = TrendingRepoAdapter(activity!!)
        layoutManager = GridLayoutManager(context, COLUMN_COUNT)

        binding.rvRepos.addItemDecoration(
                GridItemDecoration(resources.getDimension(R.dimen.dimen_20).toInt(), true))
        binding.rvRepos.layoutManager = layoutManager
        binding.rvRepos.adapter = adapter


        subscribeUi(binding, adapter)
        setHasOptionsMenu(true)


        return binding.root
    }


    private fun subscribeUi(binding: FragmentRepoBinding, adapter: TrendingRepoAdapter) {
        viewModel.trendingRepos.observe(viewLifecycleOwner, Observer { result ->
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