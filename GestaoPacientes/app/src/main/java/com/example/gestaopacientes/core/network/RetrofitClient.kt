import com.example.gestaopacientes.core.session.SessionManager
import com.example.gestaopacientes.core.network.AuthInterceptor
import com.example.gestaopacientes.core.session.AppSessionExpiredHandler
import com.example.gestaopacientes.core.session.SessionExpiredHandler
import com.example.gestaopacientes.features.home.data.remote.PatientsApi
import com.example.gestaopacientes.features.login.data.remote.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val NLOG_BASE_URL = "http://192.168.1.108:8080/"
    private const val BASE_URL = "http://192.168.1.108:8080/api/"

    // 🔓 CLIENT PÚBLICO
    private val publicClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    // 🔓 RETROFIT PÚBLICO
    private val publicRetrofit = Retrofit.Builder()
        .baseUrl(NLOG_BASE_URL)
        .client(publicClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 🔐 RETROFIT LOGADO
    fun authenticatedRetrofit(
        sessionManager: SessionManager,
        sessionExpiredHandler: SessionExpiredHandler
    ): Retrofit {

        val authClient = OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor(sessionManager, sessionExpiredHandler)
            )
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(authClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🔓 LOGIN API
    val authApi: AuthApi =
        publicRetrofit.create(AuthApi::class.java)

    // 🔐 PATIENTS API
    fun patientsApi(
        sessionManager: SessionManager,
        sessionExpiredHandler: AppSessionExpiredHandler
    ): PatientsApi {

        return authenticatedRetrofit(sessionManager, sessionExpiredHandler)
            .create(PatientsApi::class.java)
    }
}