import com.example.gestaopacientes.core.SessionManager
import com.example.gestaopacientes.core.network.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    .build()
}

fun provideAuthInterceptor(
    sessionManager: SessionManager
): AuthInterceptor {
    return AuthInterceptor(sessionManager)
}

fun loggedInRetrofit(
    client: OkHttpClient
): Retrofit {

    return Retrofit.Builder()
        .baseUrl("http://192.168.1.106:8080/api")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}