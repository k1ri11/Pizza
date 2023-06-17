package com.company.pizza.data.api

import com.company.pizza.data.models.MenuModel
import com.company.pizza.domain.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface PizzaApi {
    @Headers(
        "X-RapidAPI-Host: pizza-and-desserts.p.rapidapi.com",
        "X-RapidAPI-Key: $API_KEY"
    )

    @GET("pizzas/")
    suspend fun getMenuModel(): Response<MenuModel>
}