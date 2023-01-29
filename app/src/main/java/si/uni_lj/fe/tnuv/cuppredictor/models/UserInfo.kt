package si.uni_lj.fe.tnuv.cuppredictor.models

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("Name") val userName: String? = null,
    @SerializedName("Email") val userEmail: String?,
    @SerializedName("Password") val userPwd: String?,
)

data class RegisterResponse(
    @SerializedName("Id") val userId: Int?,
    @SerializedName("Name") val userName: String?,
)