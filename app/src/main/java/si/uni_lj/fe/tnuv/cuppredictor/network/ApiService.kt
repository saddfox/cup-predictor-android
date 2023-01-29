package si.uni_lj.fe.tnuv.cuppredictor.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import si.uni_lj.fe.tnuv.cuppredictor.models.*

private const val BASE_URL = "https://cup.saferutar.com"

var mHttpLoggingInterceptor: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

var client: OkHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(mHttpLoggingInterceptor)
    .build()


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

object ApiBuilder {
    val retrofitService: RestApi by lazy { retrofit.create(RestApi::class.java) }
}

interface RestApi {
    @Headers("Content-Type: application/json")
    @POST("api/register")
    fun postRegisterUser(@Body userData: UserInfo): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("api/login")
    fun postLoginUser(@Body userData: UserInfo): Call<AuthToken>

    @Headers("Content-Type: application/json")
    @GET("api/getAllCups")
    fun getAllCups(@Header("Authorization") token: String): Call<Array<CupListData>?>

    @Headers("Content-Type: application/json")
    @GET("api/getCup/{cupID}")
    fun getCup(
        @Header("Authorization") token: String,
        @Path("cupID") cupID: Int
    ): Call<CupTeamNames>

    @Headers("Content-Type: application/json")
    @GET("api/getResults/{cupID}")
    fun getResults(
        @Header("Authorization") token: String,
        @Path("cupID") cupID: Int
    ): Call<Array<CupResult>>

    @Headers("Content-Type: application/json")
    @POST("api/submitPrediction/1")
    fun postPrediction1(
        @Header("Authorization") token: String,
        @Body prediction: Format1Result
    ): Call<StatusResponse>

    @Headers("Content-Type: application/json")
    @POST("api/submitPrediction/2")
    fun postPrediction2(
        @Header("Authorization") token: String,
        @Body prediction: Format2Result
    ): Call<StatusResponse>
}

