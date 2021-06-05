package com.study.app_animal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.study.app_animal.service.AnimalService
import com.study.app_animal.viewmodel.AnimalViewModel
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

        viewModel.currentImageURL.observe(this, Observer {
            Picasso.get().load(it).into(iv_album)
        })

        viewModel.currentInfo.observe(this, Observer {
            tv_info.text = it.toString()
        })

        GlobalScope.launch {
            val path = resources.getString(R.string.path)
            viewModel.animals = AnimalService(path).getAnimals()
            val n = Random.nextInt(viewModel.animals!!.size)
            viewModel.animal = viewModel.animals!![n]
            runOnUiThread {
                viewModel.currentImageURL.value = viewModel.animal!!.album_file
                viewModel.currentInfo.value = viewModel.animal.toString()
            }
        }

        iv_album.setOnClickListener {
            val n = Random.nextInt(viewModel.animals!!.size)
            viewModel.animal = viewModel.animals!![n]
            viewModel.currentImageURL.value = viewModel.animal!!.album_file
            viewModel.currentInfo.value = viewModel.animal.toString()
        }

    }
}