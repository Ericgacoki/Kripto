package com.ericg.kripto.di

import android.app.Application
import androidx.room.Room
import com.ericg.kripto.data.local.database.KriptoDatabase
import com.ericg.kripto.data.remote.api.ApiService
import com.ericg.kripto.domain.repository.KriptoRepository
import com.ericg.kripto.domain.use_case.get_coin_details.GetCoinDetailsUseCase
import com.ericg.kripto.domain.use_case.get_coins.GetCoinsUseCase
import com.ericg.kripto.domain.use_case.get_conversion.GetConversionUseCase
import com.ericg.kripto.domain.use_case.get_exchanges.GetExchangesUseCase
import com.ericg.kripto.domain.use_case.search_coin.SearchCoinUseCase
import com.ericg.kripto.domain.use_case.search_exchange.SearchExchangeUseCase
import com.ericg.kripto.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesCoinsUseCase(repository: KriptoRepository): GetCoinsUseCase {
        return GetCoinsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesSearchCoinsUseCase(repository: KriptoRepository): SearchCoinUseCase {
        return SearchCoinUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesCoinDetailsUseCase(repository: KriptoRepository): GetCoinDetailsUseCase {
        return GetCoinDetailsUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesExchangesUseCase(repository: KriptoRepository): GetExchangesUseCase {
        return GetExchangesUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesSearchExchangesUseCase(repository: KriptoRepository): SearchExchangeUseCase {
        return SearchExchangeUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesGetConversionUseCase(repository: KriptoRepository): GetConversionUseCase {
        return GetConversionUseCase(repository)
    }

    @Provides
    @Singleton
    fun providesKriptoDatabase(application: Application): KriptoDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            KriptoDatabase::class.java,
            "kripto_database"
        ).fallbackToDestructiveMigration().build()
    }
}
