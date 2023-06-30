package org.bmsk.deepmedi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bmsk.data.repository.AnalyzeRepositoryImpl
import org.bmsk.domain.repository.AnalyzeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAnalyzeRepository(analyzeRepositoryImpl: AnalyzeRepositoryImpl): AnalyzeRepository
}