package com.shubhamdani.todonotes

import android.app.Application
import com.shubhamdani.todonotes.data.services.NetWorkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoApplication : Application() {

    companion object {
         var NETWORK_SERVICE: NetWorkService? = null
    }


    override fun onCreate() {
        super.onCreate()

        NETWORK_SERVICE = Retrofit.Builder()
            .baseUrl(NetWorkService.HTTPS_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetWorkService::class.java)
    }
}
