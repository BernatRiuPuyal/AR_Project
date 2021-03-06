package com.socialwall.bernatriupuyal.socialwall.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.socialwall.bernatriupuyal.socialwall.R
import com.socialwall.bernatriupuyal.socialwall.fragments.HomeFragment
import com.socialwall.bernatriupuyal.socialwall.fragments.NewsFragment
import com.socialwall.bernatriupuyal.socialwall.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabs.setOnNavigationItemSelectedListener { tab ->

            lateinit var fragment: Fragment
            when(tab.itemId){
                R.id.tab_home ->{
                    fragment = HomeFragment();
                }
                R.id.tab_news ->{
                    fragment = NewsFragment();
                }
                R.id.tab_profile ->{
                    fragment = ProfileFragment();
                }
                else ->{
                    fragment = HomeFragment()
                    Log.w("suau","suau");
                }
            }


            val fragmentManager = supportFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer,fragment)
            fragmentTransaction.commit()

            true

        }
            tabs.selectedItemId = R.id.tab_home
    }
}
