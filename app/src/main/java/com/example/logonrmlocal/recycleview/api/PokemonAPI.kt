package com.example.logonrmlocal.recycleview.api


import com.example.logonrmlocal.recycleview.model.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonAPI {

    @GET("/api/pokemon")
    fun buscar(@Query("size")size: Int): Observable<PokemonResponse>
}