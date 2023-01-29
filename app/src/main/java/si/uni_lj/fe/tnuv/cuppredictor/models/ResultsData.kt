package si.uni_lj.fe.tnuv.cuppredictor.models

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Format1Result(
    @SerializedName("CupID") val cupID: Int,
    @SerializedName("TeamA1") val teamA1: Int,
    @SerializedName("TeamA2") val teamA2: Int,
    @SerializedName("TeamA3") val teamA3: Int,
    @SerializedName("TeamA4") val teamA4: Int,
    @SerializedName("TeamB1") val teamB1: Int,
    @SerializedName("TeamB2") val teamB2: Int,
    @SerializedName("TeamB3") val teamB3: Int,
    @SerializedName("TeamB4") val teamB4: Int,
    @SerializedName("TeamC1") val teamC1: Int,
    @SerializedName("TeamC2") val teamC2: Int,
    @SerializedName("TeamC3") val teamC3: Int,
    @SerializedName("TeamC4") val teamC4: Int,
    @SerializedName("TeamD1") val teamD1: Int,
    @SerializedName("TeamD2") val teamD2: Int,
    @SerializedName("TeamD3") val teamD3: Int,
    @SerializedName("TeamD4") val teamD4: Int,
    @SerializedName("TeamE1") val teamE1: Int,
    @SerializedName("TeamE2") val teamE2: Int,
    @SerializedName("TeamE3") val teamE3: Int,
    @SerializedName("TeamE4") val teamE4: Int,
    @SerializedName("TeamF1") val teamF1: Int,
    @SerializedName("TeamF2") val teamF2: Int,
    @SerializedName("TeamF3") val teamF3: Int,
    @SerializedName("TeamF4") val teamF4: Int,
    @SerializedName("TeamG1") val teamG1: Int,
    @SerializedName("TeamG2") val teamG2: Int,
    @SerializedName("TeamG3") val teamG3: Int,
    @SerializedName("TeamG4") val teamG4: Int,
    @SerializedName("TeamH1") val teamH1: Int,
    @SerializedName("TeamH2") val teamH2: Int,
    @SerializedName("TeamH3") val teamH3: Int,
    @SerializedName("TeamH4") val teamH4: Int,
    @SerializedName("Round16_1") val round16_1: Int,
    @SerializedName("Round16_2") val round16_2: Int,
    @SerializedName("Round16_3") val round16_3: Int,
    @SerializedName("Round16_4") val round16_4: Int,
    @SerializedName("Round16_5") val round16_5: Int,
    @SerializedName("Round16_6") val round16_6: Int,
    @SerializedName("Round16_7") val round16_7: Int,
    @SerializedName("Round16_8") val round16_8: Int,
    @SerializedName("Quarter1") val quarter1: Int,
    @SerializedName("Quarter2") val quarter2: Int,
    @SerializedName("Quarter3") val quarter3: Int,
    @SerializedName("Quarter4") val quarter4: Int,
    @SerializedName("Semi1") val semi1: Int,
    @SerializedName("Semi2") val semi2: Int,
    @SerializedName("Final") val final: Int,
    @SerializedName("Third") val third: Int,
)

data class Format2Result(
    @SerializedName("CupID") val cupID: Int,
    @SerializedName("Results") val results: Array<Int>,
)

fun createFormat1payload(
    input: ArrayList<String>,
    original: ArrayList<String>,
    id: Int
): Format1Result {
    var order = mutableListOf<Int>()
    // get correct indexes compared to the original list
    for (item in input) {
        order.add(original.indexOf(item) + 1)
    }

    return Format1Result(
        cupID = id,
        teamA1 = order[0],
        teamA2 = order[1],
        teamA3 = order[2],
        teamA4 = order[3],
        teamB1 = order[4],
        teamB2 = order[5],
        teamB3 = order[6],
        teamB4 = order[7],
        teamC1 = order[8],
        teamC2 = order[9],
        teamC3 = order[10],
        teamC4 = order[11],
        teamD1 = order[12],
        teamD2 = order[13],
        teamD3 = order[14],
        teamD4 = order[15],
        teamE1 = order[16],
        teamE2 = order[17],
        teamE3 = order[18],
        teamE4 = order[19],
        teamF1 = order[20],
        teamF2 = order[21],
        teamF3 = order[22],
        teamF4 = order[23],
        teamG1 = order[24],
        teamG2 = order[25],
        teamG3 = order[26],
        teamG4 = order[27],
        teamH1 = order[28],
        teamH2 = order[29],
        teamH3 = order[30],
        teamH4 = order[31],
        round16_1 = order[32],
        round16_2 = order[33],
        round16_3 = order[34],
        round16_4 = order[35],
        round16_5 = order[36],
        round16_6 = order[37],
        round16_7 = order[38],
        round16_8 = order[39],
        quarter1 = order[40],
        quarter2 = order[41],
        quarter3 = order[42],
        quarter4 = order[43],
        semi1 = order[44],
        semi2 = order[45],
        final = order[46],
        third = order[47],
    )
}

fun createFormat2payload(
    input: ArrayList<String>,
    original: ArrayList<String>,
    id: Int
): Format2Result {

    var order = mutableListOf<Int>()
    // get correct indexes compared to the original list
    for (item in input) {
        order.add(original.indexOf(item))
    }
    return Format2Result(
        cupID = id,
        results = order.toTypedArray()
    )
}