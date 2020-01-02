package com.randomly.videos.trendingrepo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.randomly.videos.R
import com.randomly.videos.data.Result
import com.randomly.videos.databinding.FragmentRepoBinding
import com.randomly.videos.di.Injectable
import com.randomly.videos.di.injectViewModel
import com.randomly.videos.ui.GridItemDecoration
import com.randomly.videos.ui.hide
import com.randomly.videos.ui.show
import javax.inject.Inject


class VideoPostsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: VideoPostsViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var binding: FragmentRepoBinding
    private lateinit var adapter: PostAdapter


    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0

    var loading: Boolean = true
    private val COLUMN_COUNT = 2

    companion object {
        fun getInstance() = VideoPostsFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentRepoBinding.inflate(inflater, container, false)
        context ?: return binding.root

        adapter = PostAdapter(activity!!)
        layoutManager = GridLayoutManager(context, COLUMN_COUNT)

        binding.rvRepos.addItemDecoration(
                GridItemDecoration(resources.getDimension(R.dimen.dimen_20).toInt(), true))
        binding.rvRepos.layoutManager = layoutManager
        binding.rvRepos.adapter = adapter


        binding.rvRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false


                        }
                    }
                }
            }
        })

        subscribeUi(binding, adapter)
        setHasOptionsMenu(true)


        return binding.root
    }


    private fun subscribeUi(binding: FragmentRepoBinding, adapter: PostAdapter) {
        viewModel.posts.observe(viewLifecycleOwner, Observer { result ->


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