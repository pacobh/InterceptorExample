package es.paco.interceptorexample.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.paco.interceptorexample.data.repository.pokemon.DataProviderPokemon
import es.paco.interceptorexample.data.repository.pokemon.DataSourceCallbacksPokemon
import es.paco.interceptorexample.data.repository.remote.pokemon.RetrofitSuspendResponsePokemon
import es.paco.interceptorexample.data.repository.sharedpreferences.EncryptedSharedPreferencesManager
import es.paco.interceptorexample.data.session.DataUserSession
import es.paco.interceptorexample.extension.TAG
import es.paco.interceptorexample.ui.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dataProviderPokemon: DataProviderPokemon,
    dataUserSession: DataUserSession,
    encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager
) :
    BaseViewModel(
        savedStateHandle,
        dataProviderPokemon,
        dataUserSession,
        encryptedSharedPreferencesManager
    ) {

    private val _channelError =
        Channel<es.paco.interceptorexample.data.domain.model.error.ErrorModel>()
    val channelError = _channelError.receiveAsFlow()
    private val _stateImage = MutableStateFlow("")
    val stateImage = _stateImage.asStateFlow()
    private val _stateName = MutableStateFlow("")
    val stateName = _stateName.asStateFlow()

    var listPokemonResponseModel: es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonModel? =
        null

    fun loadPokemonList() {
        loadingMutableLiveData.postValue(true)
        dataProviderPokemon.getListPokemon(object :
            DataSourceCallbacksPokemon.GetListPokemonCallback {
            override fun onGetListPokemonCallbackSuccess(getListPokemonResponseModel: es.paco.interceptorexample.data.domain.model.pokemon.GetListPokemonModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(
                    TAG,
                    "l> onGetListPokemonCallbackSuccess getListPokemonResponseModel size: ${getListPokemonResponseModel.count}"
                )
                listPokemonResponseModel = getListPokemonResponseModel
                emitRandomName()
            }

            override fun onGetListPokemonCallbackUnsuccess(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(TAG, "l> onGetListPokemonCallbackUnsuccess.")
                viewModelScope.launch {
                    _channelError.send(errorModel)
                }
            }

            override fun onGetListPokemonCallbackFailure(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(TAG, "l> onGetListPokemonCallbackFailure.")
                viewModelScope.launch {
                    _channelError.send(errorModel)
                }
            }
        }, 100, 50)
    }

    fun emitRandomName() {
        listPokemonResponseModel?.let {
            if (it.results.isNotEmpty()) {
                viewModelScope.launch {
                    val pokemonName = it.results.random().name
                    _stateName.emit(pokemonName)
                    loadPokemonImage(pokemonName)
                }
            }
        }
    }

    private suspend fun loadPokemonImage(pokemonName: String) {
        loadingMutableLiveData.postValue(true)
        val pokemonByNameResponse = dataProviderPokemon.getPokemonByName(pokemonName)
        RetrofitSuspendResponsePokemon.analizeGetPokemonbyNameCallback(object :
            DataSourceCallbacksPokemon.GetPokemonByNameCallback {
            override fun onPokemonByNameCallbackSuccess(pokemonInfoModel: es.paco.interceptorexample.data.domain.model.pokemon.PokemonInfoModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(
                    TAG,
                    "l> onPokemonByNameCallbackSuccess url frontDefault: ${pokemonInfoModel.pokemonInfoSpritesModel.frontDefault}"
                )
                viewModelScope.launch {
                    _stateImage.emit(pokemonInfoModel.pokemonInfoSpritesModel.frontDefault)
                }
            }

            override fun onPokemonByNameCallbackUnsuccess(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(TAG, "l> onPokemonByNameCallbackUnsuccess.")
                viewModelScope.launch {
                    _channelError.send(errorModel)
                }
            }

            override fun onPokemonByNameCallbackFailure(errorModel: es.paco.interceptorexample.data.domain.model.error.ErrorModel) {
                loadingMutableLiveData.postValue(false)
                Log.d(TAG, "l> onPokemonByNameCallbackFailure.")
                viewModelScope.launch {
                    _channelError.send(errorModel)
                }
            }
        }, pokemonByNameResponse)
    }

}