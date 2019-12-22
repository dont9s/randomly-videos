package com.instory.latest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.instory.latest.trending.viral.news.databinding.ActivityMainBinding
import com.instory.latest.trendingrepo.ui.TrendingRepoFragment
import com.instory.latest.trending.viral.news.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.fl_fragment, TrendingRepoFragment.getInstance()).commit()

    }


}