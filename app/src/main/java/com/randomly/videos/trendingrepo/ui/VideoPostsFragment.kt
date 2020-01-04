package com.randomly.videos.trendingrepo.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.randomly.videos.R
import com.randomly.videos.data.Result
import com.randomly.videos.databinding.FragmentPostBinding
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
    private lateinit var binding: FragmentPostBinding
    private lateinit var adapter: PostAdapter


    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0


    private val COLUMN_COUNT = 2

    companion object {
        fun getInstance() = VideoPostsFragment()
        const val BY_NONE = -1
        const val BY_DATE = 0
        const val BY_LIKES = 1
        const val BY_SHARES = 2
        const val BY_VIEWS = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewModel = injectViewModel(viewModelFactory)

        binding = FragmentPostBinding.inflate(inflater, container, false)
        context ?: return binding.root

        adapter = PostAdapter()
        layoutManager = GridLayoutManager(context, COLUMN_COUNT)

        binding.rvRepos.addItemDecoration(
                GridItemDecoration(resources.getDimension(R.dimen.dimen_20).toInt(), true))
        binding.rvRepos.layoutManager = layoutManager
        binding.rvRepos.adapter = adapter




        binding.rvRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (viewModel.loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            viewModel.loading = false

                            viewModel.loadMore()

                        }
                    }
                }
            }
        })

        subscribeUi(binding, adapter)
        setHasOptionsMenu(true)


        return binding.root
    }


    private fun subscribeUi(binding: FragmentPostBinding, adapter: PostAdapter) {
        viewModel.posts.observe(viewLifecycleOwner, Observer { result ->


            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.loadingShimmer.hide()

                    result.data?.let { adapter.submitList(it) }
                    adapter.sort(viewModel.currentOrder)
                }
                Result.Status.LOADING -> binding.loadingShimmer.show()
                Result.Status.ERROR -> {
                    binding.loadingShimmer.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)


        inflater.inflate(R.menu.menu_sort, menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.m_date -> {

                adapter.sort(BY_DATE)
                viewModel.currentOrder = BY_DATE

            }
            R.id.m_likes -> {

                adapter.sort(BY_LIKES)
                viewModel.currentOrder = BY_LIKES
            }
            R.id.m_share -> {

                adapter.sort(BY_SHARES)
                viewModel.currentOrder = BY_SHARES
            }
            R.id.m_views -> {
                adapter.sort(BY_VIEWS)
                viewModel.currentOrder = BY_VIEWS

            }
        }

        return true
    }

}