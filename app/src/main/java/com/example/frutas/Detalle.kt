package com.example.frutas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Detalle : AppCompatActivity() {

    private lateinit var tv_calories: TextView
    private lateinit var tv_fat: TextView
    private lateinit var tv_sugar: TextView
    private lateinit var tv_carbohydrates: TextView
    private lateinit var tv_protein: TextView
    private lateinit var tv_name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        tv_calories = findViewById(R.id.calories)
        tv_fat = findViewById(R.id.fat)
        tv_sugar = findViewById(R.id.sugar)
        tv_carbohydrates = findViewById(R.id.carbohydrates)
        tv_protein = findViewById(R.id.protein)
        tv_name = findViewById(R.id.tv_namefruta)

        val bundle = intent.extras
        val fruta = bundle?.getString("fruta")


        getFrutaByName(fruta)
    }

    private fun getFrutaByName(fruta: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(FruitApi:: class.java)
                .getJokeByCategory("fruit/$fruta")
            val response = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    response.let {fruta ->
                        tv_name.text = "Nombre: " + fruta?.name.toString()
                        tv_calories.text = "calories: " + fruta?.nutritions?.calories.toString()
                        tv_fat.text = "fat: " + fruta?.nutritions?.fat.toString()
                        tv_sugar.text = "sugar: " + fruta?.nutritions?.sugar.toString()
                        tv_carbohydrates.text = "carbohydrates: " + fruta?.nutritions?.carbohydrates.toString()
                        tv_protein.text = "protein: " + fruta?.nutritions?.protein.toString()
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