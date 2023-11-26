package com.example.frutas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FruitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_frutas)
        adapter = FruitAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = {fruta ->
            val intent = Intent (this, Detalle :: class.java )
            intent.putExtra("fruta", fruta)
            startActivity(intent)
        }

        getFrutas()
    }

    private fun getFrutas() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(FruitApi::class.java).getFruits()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val fruits = response
                    fruits?.let {
                        // Lista de URLs de imágenes
                        val imageUrls = listOf(
                            "https://www.fruityvice.com/images/banana.png",
                            "https://www.fruityvice.com/images/strawberry.png",
                            "https://www.fruityvice.com/images/banana.png",
                            "https://www.fruityvice.com/images/banana.png",
                            "https://www.fruityvice.com/images/pear.png",

                        )
                        for ((index, fruit) in it.withIndex()) {
                            if (index < imageUrls.size) {
                                fruit?.imageUrl = imageUrls[index]
                            } else {
                                // Si te quedas sin URLs, puedes manejarlo de la manera que desees
                                fruit?.imageUrl = "https://www.fruityvice.com/images/banana.png"
                            }
                        }

                        adapter.setFruits(it)
                    }
                }
            }
        }
    }


    private fun getRetrofit(): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object{
        const val BASE_URL = "https://fruityvice.com/api/"
    }

}





