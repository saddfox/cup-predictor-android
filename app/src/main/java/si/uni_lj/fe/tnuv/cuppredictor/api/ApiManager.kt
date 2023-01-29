package si.uni_lj.fe.tnuv.cuppredictor.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import si.uni_lj.fe.tnuv.cuppredictor.models.*
import si.uni_lj.fe.tnuv.cuppredictor.network.ApiBuilder

class RestApiService {
    fun registerUser(userData: UserInfo, result: (RegisterResponse?) -> Unit) {
        val query = ApiBuilder.retrofitService.postRegisterUser(userData)
        query.enqueue(
            object : Callback<RegisterResponse> {
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    result(null)
                }

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val addedUser = response.body()
                    result(addedUser)
                }
            }
        )
    }

    fun loginUser(userData: UserInfo, result: (AuthToken?) -> Unit) {
        val query = ApiBuilder.retrofitService.postLoginUser(userData)
        Log.d("cup", query.toString())
        query.enqueue(
            object : Callback<AuthToken> {
                override fun onFailure(call: Call<AuthToken>, t: Throwable) {
                    Log.d("cup", "Login FAIL: ", t)
                    result(null)
                }

                override fun onResponse(call: Call<AuthToken>, response: Response<AuthToken>) {
                    val token = response.body()
                    Log.d("cup", token.toString())
                    result(token)
                }
            }
        )
    }

    fun fetchAllCups(token: String, result: (Array<CupListData>?) -> Unit) {
        val query = ApiBuilder.retrofitService.getAllCups(token)
        query.enqueue(
            object : Callback<Array<CupListData>?> {
                override fun onFailure(call: Call<Array<CupListData>?>, t: Throwable) {
                    result(null)
                }

                override fun onResponse(
                    call: Call<Array<CupListData>?>,
                    response: Response<Array<CupListData>?>
                ) {
                    val array = response.body()
                    result(array)
                }
            }
        )
    }


    fun fetchCup(token: String, cupID: Int, result: (CupTeamNames?) -> Unit) {
        val query = ApiBuilder.retrofitService.getCup(token, cupID)
        query.enqueue(
            object : Callback<CupTeamNames> {
                override fun onFailure(call: Call<CupTeamNames>, t: Throwable) {
                    result(null)
                }

                override fun onResponse(
                    call: Call<CupTeamNames>,
                    response: Response<CupTeamNames>
                ) {
                    val array = response.body()
                    result(array)
                }
            }
        )
    }

    fun fetchScoreboard(token: String, cupID: Int, result: (Array<CupResult>?) -> Unit) {
        val query = ApiBuilder.retrofitService.getResults(token, cupID)
        query.enqueue(
            object : Callback<Array<CupResult>?> {
                override fun onFailure(call: Call<Array<CupResult>?>, t: Throwable) {
                    result(null)
                }

                override fun onResponse(
                    call: Call<Array<CupResult>?>,
                    response: Response<Array<CupResult>?>
                ) {
                    val array = response.body()
                    result(array)
                }
            }
        )
    }


    fun submitPrediction1(
        token: String,
        prediction: Format1Result,
        result: (StatusResponse?) -> Unit
    ) {
        val query = ApiBuilder.retrofitService.postPrediction1(token, prediction)
        query.enqueue(
            object : Callback<StatusResponse> {
                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                    result(null)
                }

                override fun onResponse(
                    call: Call<StatusResponse>,
                    response: Response<StatusResponse>
                ) {
                    if (response.isSuccessful) {
                        result(response.body())

                    } else {
                        result(
                            StatusResponse(
                                error = "failed submitting prediction",
                                status = null
                            )
                        )
                    }
                }
            }
        )
    }

    fun submitPrediction2(
        token: String,
        prediction: Format2Result,
        result: (StatusResponse?) -> Unit
    ) {
        val query = ApiBuilder.retrofitService.postPrediction2(token, prediction)
        query.enqueue(
            object : Callback<StatusResponse> {
                override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                    Log.d("cup", "submit result: ", t)
                    result(null)
                }

                override fun onResponse(
                    call: Call<StatusResponse>,
                    response: Response<StatusResponse>
                ) {
                    if (response.isSuccessful) {
                        result(response.body())

                    } else {
                        result(
                            StatusResponse(
                                error = "failed submitting prediction",
                                status = null
                            )
                        )
                    }
                }
            }
        )
    }
}