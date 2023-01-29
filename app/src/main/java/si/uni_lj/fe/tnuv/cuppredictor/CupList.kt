package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService

class CupList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cup_list)

        val recyclerview = findViewById<RecyclerView>(R.id.cupListView)
        recyclerview.layoutManager = LinearLayoutManager(this)

        val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("auth_token", "")

        RestApiService().fetchAllCups(token!!) {
            Log.d("cup", it!![0].cupName.toString())
            val adapter = CupListAdapter(it)
            recyclerview.adapter = adapter
        }

    }
}