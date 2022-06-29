package com.sdsol.paginationsample.di

import android.util.Base64
import com.sdsol.paginationsample.BuildConfig
import com.sdsol.paginationsample.network.BackEndApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseURL() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    @Named("urlCredentials")
    fun getCredentials(): String {
        val credentials = "${BuildConfig.API_USERNAME}:${BuildConfig.API_PASSWORD}"
        return "Basic ${Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)}"
    }
    @Singleton
    @Provides
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named("urlCredentials") credentials: String,
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .writeTimeout(120, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", credentials)
                .header("Accept", "application/json")
                .header("User-Agent", "Android")
                .header("LanguageCode", Locale.getDefault().language)
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): BackEndApi {
        val retrofitClient = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient).build()

        return retrofitClient.create(BackEndApi::class.java)
    }
}