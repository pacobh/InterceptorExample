package es.paco.interceptorexample.ui.base

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.HiltAndroidApp
import es.paco.interceptorexample.R
import es.paco.interceptorexample.data.constants.SharedPreferencesKeys.Companion.SHARED_PREFERENCES_KEY_FIREBASE_UUID
import es.paco.interceptorexample.data.repository.sharedpreferences.EncryptedSharedPreferencesManager
import es.paco.interceptorexample.extension.TAG
import javax.inject.Inject


@HiltAndroidApp
class InterceptorExampleApplication @Inject constructor() : Application() {
    companion object {
        private lateinit var appContext: Context
        fun getAppContext(): Context {
            return appContext
        }
    }
    @Inject
    lateinit var encryptedSharedPreferencesManager: EncryptedSharedPreferencesManager

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        configFirebase()
        configRemoteConfigFirebase()
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
    private fun configRemoteConfigFirebase(){
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(ContentValues.TAG, "Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this,"version: ${remoteConfig.getString("min_version_app")}", Toast.LENGTH_LONG).show()
            }

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate : ConfigUpdate) {
                Log.d(ContentValues.TAG, "Updated keys: " + configUpdate.updatedKeys);

                if (configUpdate.updatedKeys.contains("min_version_app")) {
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d("l>","valor de update: ${remoteConfig.getString("min_version_app")}")
                    }
                }
                if (configUpdate.updatedKeys.contains("message_user")) {
                    remoteConfig.activate().addOnCompleteListener {
                        Log.d("l>","valor de update: ${remoteConfig.getString("message_user")}")
                    }
                }
            }

            override fun onError(error : FirebaseRemoteConfigException) {
                Log.w(ContentValues.TAG, "Config update error with code: " + error.code, error)
            }
        })
    }

}