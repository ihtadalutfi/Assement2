package com.example.konversimatauang.network//package com.example.konversimatauang.network

import com.example.konversimatauang.model.Uang
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "https://raw.githubusercontent.com/ihtadalutfi/uang/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface UangApiService {
    @GET("test.json")
    suspend fun getUang():  List<Uang>

}
object UangApi {
    val service: UangApiService by lazy {
        retrofit.create(UangApiService::class.java)
    }
    enum class ApiStatus { LOADING, SUCCESS, FAILED }

    fun getUangUrl(nama: String): String{
        return "$BASE_URL$nama.jpg"

    }
}