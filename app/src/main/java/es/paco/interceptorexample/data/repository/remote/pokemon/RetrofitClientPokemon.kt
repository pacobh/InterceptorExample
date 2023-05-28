package es.paco.interceptorexample.data.repository.remote.pokemon

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import es.paco.interceptorexample.BuildConfig
import es.paco.interceptorexample.R
import es.paco.interceptorexample.data.constants.GeneralConstants.Companion.RETROFIT_TIMEOUT_IN_SECOND
import es.paco.interceptorexample.ui.base.InterceptorExampleApplication
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier


@Singleton
class RetrofitClientPokemon @Inject constructor() {

    val retrofit: Retrofit

    init {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val certificatePinner = CertificatePinner.Builder()
            .add("pokeapi.co", "sha256/9Yo5DIhM434OpBbqe2YOTJEt4+cTVf1OLs4mqhipC4o=")
            .build()
        httpClient.certificatePinner(certificatePinner)

        httpClient
            .connectTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .readTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            .writeTimeout(RETROFIT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

        httpClient.hostnameVerifier(HostnameVerifier { hostname, session -> true })

        if (BuildConfig.DEBUG) {
            // Creamos un interceptor y le indicamos el log level a usar
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.addInterceptor(logging)
        }

        httpClient.interceptors().add(
            ConnectivityInterceptor(
                InterceptorExampleApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
                InterceptorExampleApplication.getAppContext().getString(R.string.error_connectivity_message)
            )
        )

        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_POKEMON)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .build()
    }
}