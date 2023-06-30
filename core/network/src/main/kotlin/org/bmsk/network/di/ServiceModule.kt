package org.bmsk.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bmsk.network.service.DeepMediService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideFileUploadService(
        retrofit: Retrofit
    ): DeepMediService {
        return retrofit.create(DeepMediService::class.java)
    }
}