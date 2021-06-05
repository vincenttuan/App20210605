package com.study.app_transservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.study.app_transservice.service.AnimalService
import com.study.app_transservice.viewmodel.AnimalViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AnimalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)

        viewModel.currentImageURL.observe(this , Observer {
            Picasso.get().load(it).into(iv_album)
        })

        viewModel.currentInfo.observe(this, Observer {
            tv_info.text = it.toString()
        })

        GlobalScope.launch {
            val path  = resources.getString(R.string.path)
            viewModel.animals = AnimalService(path).getAnimals()
            viewModel.randomIdx = Random.nextInt(viewModel.animals!!.size)
            runOnUiThread {
                viewModel.currentImageURL.value = viewModel.animals!!.get(viewModel.randomIdx).album_file
                viewModel.currentInfo.value = viewModel.animals!!.get(viewModel.randomIdx).toString()
            }
        }

        iv_album.setOnClickListener {
            viewModel.randomIdx = Random.nextInt(viewModel.animals!!.size)
            viewModel.currentImageURL.value = viewModel.animals!!.get(viewModel.randomIdx).album_file
            viewModel.currentInfo.value = viewModel.animals!!.get(viewModel.randomIdx).toString()
        }

        iv_album.setOnLongClickListener OnLongClickListener@{
            //Toast.makeText(applicationContext, tv_info.visibility.toString(), Toast.LENGTH_SHORT).show()
            if(tv_info.visibility == View.VISIBLE) {
                tv_info.visibility = View.GONE
            } else {
                tv_info.visibility = View.VISIBLE
            }
            return@OnLongClickListener true
        }

    }
}