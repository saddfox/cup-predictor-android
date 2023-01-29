package si.uni_lj.fe.tnuv.cuppredictor.models

import com.google.gson.annotations.SerializedName

data class CupListData(
    @SerializedName("id") val cupID: Int? = 0,
    @SerializedName("name") val cupName: String? = "",
    @SerializedName("format") val format: Int? = 0,
    @SerializedName("active") val active: Boolean? = false,
    @SerializedName("results") val results: Boolean? = false,
    @SerializedName("submitted") val submitted: Boolean? = false,
    @SerializedName("logo") val logo: Int = 0
)

data class CupTeamNames(
    @SerializedName("teams") val teams: Array<String>? = null,
)

data class CupResult(
    @SerializedName("Username") val user: String,
    @SerializedName("Score") val score: Int,
)