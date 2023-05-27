package es.paco.interceptorexample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.paco.interceptorexample.data.repository.remote.pokemon.ApiServicesPokemon
import es.paco.interceptorexample.data.repository.remote.pokemon.RetrofitClientPokemon
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InjectionDI {
    @Singleton
    @Provides
    fun provideApiServicesPokemon(retrofitClientPokemon: RetrofitClientPokemon): ApiServicesPokemon {
        return retrofitClientPokemon.retrofit.create(ApiServicesPokemon::class.java)
    }
}