package com.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.study.viewmodel.OpenWeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: OpenWeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(OpenWeatherViewModel::class.java)

        viewModel.currentImageURL.observe(this , Observer {
            Picasso.get().load(it).into(iv_icon)
        })

        viewModel.currentLog.observe(this, Observer {
            tv_log.text = it.toString()
        })
    }

    fun changeOpenWeather(view: View) {

    }

}