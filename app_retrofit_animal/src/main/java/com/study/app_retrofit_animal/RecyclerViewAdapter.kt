package com.study.app_retrofit_animal

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.study.app_retrofit_animal.model.Animal
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var items: List<Animal> = ArrayList<Animal>()
    fun setListData(items: List<Animal>) {
        this.items = items
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val kind = view.tv_animal_kind
        val place = view.tv_animal_place
        val album:ImageView = view.iv_album_file
        fun bind(animal: Animal, context: Context) {
            kind.text = animal.animal_kind
            place.text = animal.animal_place
            try {
                Picasso.get().load(animal.album_file).into(album)
            } catch (e: Exception) {
                // 沒有圖片
            }
            album.setOnClickListener {
                val iv: ImageView = ImageView(context)
                Picasso.get().load(animal.album_file).into(iv)
                AlertDialog.Builder(context)
                    .setTitle("Photo")
                    .setView(iv)
                    .setPositiveButton("Close", null)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position], holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}