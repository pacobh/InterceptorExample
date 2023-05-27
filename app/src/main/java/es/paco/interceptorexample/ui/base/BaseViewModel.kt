package es.paco.interceptorexample.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.paco.interceptorexample.data.repository.pokemon.DataProviderPokemon
import es.paco.interceptorexample.data.repository.sharedpreferences.EncryptedSharedPreferencesManager
import es.paco.interceptorexample.data.session.DataUserSession
import es.paco.interceptorexample.extension.TAG

abstract class BaseViewModel(
    val savedStateHandle: SavedStateHandle,
    val dataProviderPokemon: DataProviderPokemon,
    val dataUserSession: DataUserSession,
    val encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager
) :
    ViewModel() {

    enum class ViewModelSavedStateHandleKey(val key: String) {
        SAVED_STATE_HANDLE_KEY_TOKEN_IB("saveStateHandleKeyTokenIb"),
    }

    var loadingMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    var errorMutableLiveData: MutableLiveData<es.paco.interceptorexample.data.domain.model.error.ErrorModel> = MutableLiveData()
    var checkErrorNeedFinishSession401MutableLiveData: MutableLiveData<es.paco.interceptorexample.data.domain.model.error.ErrorModel> = MutableLiveData()


    open fun getKeysNeedSaveStateHandler(): ArrayList<ViewModelSavedStateHandleKey> {
        return arrayListOf(
            ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB,
        )
    }

    open fun saveDataViewModelCouldBeDestroyed(keyList: ArrayList<ViewModelSavedStateHandleKey>) {
        if (keyList.isNotEmpty()) {
            keyList.forEach { key ->
                when (key) {
                    ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB -> {
                        savedStateHandle?.set(ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB.key, dataUserSession.tokenIb)
                    }
                    else -> Unit
                }
            }
        }
    }

    open fun restoreDataViewModelIfExists(keyList: ArrayList<ViewModelSavedStateHandleKey>) {
        if (keyList.isNotEmpty() && savedStateHandle != null) {
            keyList.forEach { key ->
                when (key) {
                    ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB -> {
                        if (savedStateHandle.contains(ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB.key)) {
                            dataUserSession.tokenIb = (savedStateHandle.get<String>(
                                ViewModelSavedStateHandleKey.SAVED_STATE_HANDLE_KEY_TOKEN_IB.key)!!)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "l> onCleared")
    }
}