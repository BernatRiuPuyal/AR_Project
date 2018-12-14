package com.appcompanion.bernatriupuyal.appcompanion

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.appcompanion.bernatriupuyal.appcompanion.models.TWStreamResponse
import com.appcompanion.bernatriupuyal.appcompanion.network.ApiService
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Main2Activity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        getApiData()

    }
    fun getApiData(){
        ApiService.service.getStreams().enqueue(object : Callback<TWStreamResponse> {


            override fun onResponse(call: Call<TWStreamResponse>, response: Response<TWStreamResponse>) {

                response.body()?.data?.let {streams ->

                    // Iterate Streams
                    for(stream in streams){
                        Log.e("MainActivity","Stream with title ${stream.title} and thumbnail ${stream.thumbnailUrl}")
                    }

                } ?: kotlin.run{
                    //ERROR

                }
            }

            override fun onFailure(call: Call<TWStreamResponse>, t: Throwable) {

                Log.e("MainActivity", "Error getting streams")
            }

        })
    }
}
