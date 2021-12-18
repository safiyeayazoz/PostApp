package com.example.postapp.di

import android.content.Context
import com.example.postapp.BaseApplication
import com.example.postapp.data.PostApi
import com.example.postapp.data.PostApiService
import com.example.postapp.repository.DefaultPostRepository
import com.example.postapp.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideApiService(
        postApi: PostApi,
    ): PostApiService = PostApiService(postApi = postApi)

    @Singleton
    @Provides
    fun providePostRepository(
        postApiService: PostApiService,
    ): PostRepository = DefaultPostRepository(postApiService)

}