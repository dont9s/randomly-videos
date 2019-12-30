package com.randomly.videos.repodetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.randomly.videos.di.Injectable
import com.randomly.videos.databinding.FragmentRepoDetailBinding
import com.randomly.videos.trendingrepo.data.User

class RepoDetailFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentRepoDetailBinding

    companion object {
        const val KEY_USER = "key_user"
        fun getInstance(user: User): RepoDetailFragment {

            var fragment = RepoDetailFragment()

            val args = Bundle()
            args.putSerializable(KEY_USER, user)

            fragment.arguments = args
            return fragment

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        binding = FragmentRepoDetailBinding.inflate(inflater, container, false)
        context ?: return binding.root


        binding.user = arguments?.getSerializable(KEY_USER) as User?

        setHasOptionsMenu(true)


        return binding.root
    }


}