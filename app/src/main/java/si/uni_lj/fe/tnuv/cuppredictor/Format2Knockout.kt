package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService
import si.uni_lj.fe.tnuv.cuppredictor.models.createFormat2payload

class Format2Knockout : AppCompatActivity() {
    var currentKnockout = 0
    var cupID = -1
    lateinit var recyclerview: RecyclerView
    lateinit var adapter: Format1KnockoutAdapter
    lateinit var knockoutName: TextView
    val knockoutLookup =
        arrayOf("Round 1", "Round 2", "Round 3", "Round 4", "Quarterfinals", "Semifinals", "Final")
    val noOfGamesLookup = arrayOf(64, 32, 16, 8, 4, 2, 1)
    var knockoutResults = arrayListOf<String>()
    lateinit var previousWinners: List<String>
    lateinit var teams: MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format1_knockout)

        cupID = intent.getIntExtra("cupID", 0)
        Log.d("cup", "activity started with cupID: " + cupID)

        recyclerview = findViewById(R.id.format1KnockoutParent)
        knockoutName = findViewById(R.id.format1KnockoutName)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("auth_token", "")

        RestApiService().fetchCup(token!!, cupID) {
            Log.d("cup", it.toString())
            teams = it?.teams?.toMutableList()!!

            adapter = Format1KnockoutAdapter(teams, noOfGamesLookup[currentKnockout])

            knockoutName.text = knockoutLookup[currentKnockout]
            recyclerview.adapter = adapter

        }
    }

    fun format1NextKnockout(view: View) {
        val roundWinners = adapter.getRoundWinners()
        if (roundWinners == null) {
            Toast.makeText(this, "Select all winners!", Toast.LENGTH_SHORT).show()
            return
        } else {
            knockoutResults.addAll(roundWinners)
            currentKnockout += 1
            if (currentKnockout < 7) {
                knockoutName.text = knockoutLookup[currentKnockout]


                previousWinners = roundWinners.toList()

                adapter.updateTeams(roundWinners, noOfGamesLookup[currentKnockout], false)

            } else {
                Log.d("cup", "final result " + knockoutResults + teams)

                val result =
                    createFormat2payload(knockoutResults, teams as ArrayList<String>, cupID)
                Log.d("cup", "final object" + result)

                // Submit prediction
                val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
                val token = sharedPreference.getString("auth_token", "")

                RestApiService().submitPrediction2(token!!, result) {
                    Log.d("cup", "SERVER response: " + it)
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