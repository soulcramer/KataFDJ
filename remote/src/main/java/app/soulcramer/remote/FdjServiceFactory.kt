package app.soulcramer.remote

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.time.Duration


object FdjServiceFactory {

    fun makeFdjService(isDebug: Boolean, apiKey: String): FdjService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(isDebug)
        )
        val apiVersion = if (apiKey != "1") "v2" else "v1"

        return makeFdjService(okHttpClient, makeMoshi(), apiKey, apiVersion)
    }

    private fun makeFdjService(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        apiKey: String,
        apiVersion: String
    ): FdjService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/$apiKey/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create()
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(Duration.ofMinutes(2))
            .readTimeout(Duration.ofMinutes(2))
            .build()
    }

    private fun makeMoshi(): Moshi {
        return Moshi.Builder().run {
            build()
        }
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}