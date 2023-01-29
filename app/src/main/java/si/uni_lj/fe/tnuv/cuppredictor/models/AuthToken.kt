package si.uni_lj.fe.tnuv.cuppredictor.models

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("Auth_token") val auth_token: String?,
    @SerializedName("Expiry") val expiry: Int?,
)
