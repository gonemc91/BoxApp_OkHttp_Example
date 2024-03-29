package com.example.http.sources

import com.example.http.app.Const
import com.example.http.app.Singletons
import com.example.http.app.model.SourcesProvider
import com.example.http.app.model.settings.AppSettings
import com.example.http.sources.base.OkHttpConfig
import com.example.http.sources.base.OkHttpSourcesProvider
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object SourcesProvideHolder {


    val sourcesProvider: SourcesProvider by lazy<SourcesProvider>{
        val config = OkHttpConfig(
            baseUrl = Const.BASE_URL,
            client = createOkHttpClient(),
            gson = Gson()
        )
        OkHttpSourcesProvider(config)

    }

    private fun createOkHttpClient(): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor(Singletons.appSettings))
            .addInterceptor(createLoggingInterceptor())
            .build()
    }

    private fun createAuthorizationInterceptor(settings: AppSettings): Interceptor {
        return Interceptor { chain -> // chain это цепочка
            val newBuilder = chain.request().newBuilder()
            val token = settings.getCurrentToken()
            if(null != token){
                newBuilder.addHeader("Authorization", token)
            }
            return@Interceptor chain.proceed(newBuilder.build()) // метод proceed передает запрос далее
        }
    }
    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY) // максимальный уровень логирования

    }

}