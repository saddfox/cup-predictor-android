package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService
import si.uni_lj.fe.tnuv.cuppredictor.models.createFormat1payload

class Format1Knockout : AppCompatActivity() {
    var currentKnockout = 0
    var cupID = -1
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: Format1KnockoutAdapter
    lateinit var knockoutName: TextView
    val knockoutLookup = arrayOf("Round of 16", "Quarter-finals", "Semi-finals", "Final")
    val noOfGamesLookup = arrayOf(8, 4, 2, 2)
    var knockoutResults = arrayListOf<String>()
    lateinit var previousWinners: List<String>
    lateinit var groupResults: ArrayList<String>
    lateinit var originalOrder: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format1_knockout)

        groupResults = intent.getStringArrayListExtra("groupResult") as ArrayList<String>
        originalOrder = intent.getStringArrayListExtra("original") as ArrayList<String>
        cupID = intent.getIntExtra("cupID", -1)
        Log.d("cup", "from previous: " + groupResults)
        Log.d("cup", "original order: " + originalOrder)


        recyclerview = findViewById(R.id.format1KnockoutParent)
        knockoutName = findViewById(R.id.format1KnockoutName)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var knockoutTeams = mutableListOf<String>()
        for (i in 0..3) {
            knockoutTeams.add(groupResults[i * 8])
            knockoutTeams.add(groupResults[i * 8 + 5])
        }
        for (i in 0..3) {
            knockoutTeams.add(groupResults[i * 8 + 4])
            knockoutTeams.add(groupResults[i * 8 + 1])
        }
        Log.d("cup", "knockout teams: " + knockoutTeams)
        adapter = Format1KnockoutAdapter(knockoutTeams, noOfGamesLookup[currentKnockout])
        knockoutName.text = knockoutLookup[currentKnockout]
        recyclerview.adapter = adapter


    }

    fun format1NextKnockout(view: View) {
        val roundWinners = adapter.getRoundWinners()
        if (roundWinners == null) {
            Toast.makeText(this, "Select all winners!", Toast.LENGTH_SHORT).show()
            return
        } else {
            knockoutResults.addAll(roundWinners)
            currentKnockout += 1
            if (currentKnockout < 3) {
                knockoutName.text = knockoutLookup[currentKnockout]


                previousWinners = roundWinners.toList()

                adapter.updateTeams(roundWinners, noOfGamesLookup[currentKnockout], false)

            } else if (currentKnockout == 3) {
                knockoutName.text = knockoutLookup[currentKnockout]

                // add semifinal losers to final round, 3rd place match
                for (semi in previousWinners) {
                    if (!roundWinners.contains(semi)) {
                        roundWinners.add(semi)
                    }
                }

                Log.d("cup", "final 4 " + roundWinners)

                adapter.updateTeams(roundWinners, noOfGamesLookup[currentKnockout], true)
            } else {
                var totalResults = groupResults.toMutableList()
                totalResults.addAll(knockoutResults)
                Log.d("cup", "total results: " + totalResults + totalResults.size)
                val result =
                    createFormat1payload(totalResults as ArrayList<String>, originalOrder, cupID)
                Log.d("cup", "final" + result)

                // Submit prediction
                val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                val token = sharedPreference.getString("auth_token", "")

                RestApiService().submitPrediction1(token!!, result) {
                    Log.d("cup", "server response: " + it)
                    if (it != null) {
                        Log.d("cup", "status response: " + it)
                        if (it.error.isNullOrEmpty()) {
                            // success
                            Toast.makeText(this, "Prediction submitted", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, CupList::class.java))
                            finish()

                        } else {
                            // got response, error
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        // got no response
                        Toast.makeText(this, "Server error", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }
}