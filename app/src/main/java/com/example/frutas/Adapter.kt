package com.example.frutas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FruitAdapter : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    private var fruits: List<Fruit> = emptyList()
    lateinit var onItemClickListener: (String) -> Unit



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgFruit: ImageView = itemView.findViewById(R.id.imgFruit)

        fun bind(fruit: Fruit) {

            itemView.findViewById<TextView>(R.id.txtFruitName).text = fruit.name

            Picasso.get().load(fruit.imageUrl).into(imgFruit)

            itemView.setOnClickListener{
                onItemClickListener(fruit.name)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruits[position]
        holder.bind(fruit)
    }

    override fun getItemCount(): Int {
        return fruits.size
    }

    fun setFruits(fruits: List<Fruit>) {
        this.fruits = fruits
        notifyDataSetChanged()
    }

}

