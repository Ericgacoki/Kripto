package com.ericg.kripto.di

import com.ericg.kripto.data.repository.DataKriptoRepository
import com.ericg.kripto.domain.repository.KriptoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDataRepository(kriptoRepository: DataKriptoRepository): KriptoRepository
}
