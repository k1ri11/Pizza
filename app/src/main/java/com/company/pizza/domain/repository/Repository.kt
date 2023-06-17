package com.company.pizza.domain.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.company.pizza.R
import com.company.pizza.data.api.PizzaApi
import com.company.pizza.data.models.MenuModel
import com.company.pizza.domain.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Repository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val api: PizzaApi
) {

    private val _menu = MutableLiveData<Resource<MenuModel>>(Resource.Loading())
    val menu: LiveData<Resource<MenuModel>> = _menu


    suspend fun getMenu() {
        if (hasInternetConnection()) {
            val response = api.getMenuModel()
            if (response.isSuccessful) {
                _menu.postValue(Resource.Success(response.body()!!))
            }
            when (response.code()) {
                400 -> _menu.postValue(Resource.Error(context.resources.getString(R.string.incorrect_request)))
                401 -> _menu.postValue(Resource.Error(context.resources.getString(R.string.incorrect_authorization)))
                404 -> _menu.postValue(Resource.Error(context.resources.getString(R.string.element_not_found)))
                500 -> _menu.postValue(Resource.Error(context.resources.getString(R.string.server_error)))
            }
        } else {
            _menu.postValue(Resource.Error(context.getString(R.string.no_connection)))
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}