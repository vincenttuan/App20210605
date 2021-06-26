package com.study.app_databinding_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.study.app_databinding_recyclerview.model.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class PostAdapter(private var list: List<Post>):
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

        inner class ViewHolder(var dataBinding: ViewDataBinding):
            RecyclerView.ViewHolder(dataBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.recyclerview_row, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding: ViewDataBinding = holder.dataBinding
            // 数据绑定库在該 App Module 中生成一个名为 BR 的类，其中包含用于数据绑定的资源的 ID。
            binding.setVariable(BR.post, list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

}