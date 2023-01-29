package si.uni_lj.fe.tnuv.cuppredictor.models

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("error") val error: String?,
    @SerializedName("status") val status: String?,
)
