package es.paco.interceptorexample.ui.base

import android.app.Application
import android.util.Log
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import es.paco.interceptorexample.data.constants.SharedPreferencesKeys.Companion.SHARED_PREFERENCES_KEY_FIREBASE_UUID
import es.paco.interceptorexample.data.repository.sharedpreferences.EncryptedSharedPreferencesManager
import es.paco.interceptorexample.extension.TAG
import javax.inject.Inject


@HiltAndroidApp
class InterceptorExampleApplication @Inject constructor() : Application() {

    @Inject
    lateinit var encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
        configFirebase()
    }

    private fun configFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        FirebaseInstallations.getInstance().id.addOnCompleteListener { idResult ->
            var uuid = ""
            if (!idResult.result.isNullOrEmpty()) {
                uuid = idResult.result.toString()
            }
            encryptedSharedPreferencesManager.set(SHARED_PREFERENCES_KEY_FIREBASE_UUID, uuid)
            Log.d(TAG, "l> uuid: $uuid")
        }
    }

}