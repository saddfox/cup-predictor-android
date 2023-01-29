package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService

class Scoreboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        val cupID = intent.getIntExtra("cupID", 0)


        val recyclerview = findViewById<RecyclerView>(R.id.scoreboardRecycler)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("auth_token", "")

        RestApiService().fetchScoreboard(token!!, cupID) {
            Log.d("cup", it!![0].score.toString())
            val adapter = ScoreboardAdapter(it)
            recyclerview.adapter = adapter

        }
    }
}