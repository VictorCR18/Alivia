package com.example.alivia.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val FIREBASE_BASE_URL = "https://alivia-180e8-default-rtdb.firebaseio.com/"

    private val gson = GsonBuilder()
        .serializeNulls()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(FIREBASE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: FirebaseApi by lazy {
        retrofit.create(FirebaseApi::class.java)
    }
}