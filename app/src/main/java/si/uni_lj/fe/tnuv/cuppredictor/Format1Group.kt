package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_DRAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.api.RestApiService

class Format1Group : AppCompatActivity() {
    var cupID = -1
    var currentGroup = 0
    val groupLookup = arrayOf("A", "B", "C", "D", "E", "F", "G", "H")
    lateinit var recyclerview: RecyclerView
    lateinit var groupName: TextView
    lateinit var teams: MutableList<String>
    lateinit var currentGroupTeams: MutableList<String>
    lateinit var adapter: Format1GroupAdapter
    var groupResults = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_format1_group)
        cupID = intent.getIntExtra("cupID", 0)



        Log.d("cup", "activity started with cupID: " + cupID)


        recyclerview = findViewById(R.id.format1groupParent)
        val recyclerviewPlace = findViewById<RecyclerView>(R.id.format1groupPlace)
        groupName = findViewById(R.id.format1GroupName)
        recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerviewPlace.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val sharedPreference = getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val token = sharedPreference.getString("auth_token", "")

        RestApiService().fetchCup(token!!, cupID) {
            Log.d("cup", it.toString())
            teams = it?.teams?.toMutableList()!!
            currentGroupTeams =
                teams.slice(currentGroup * 4..currentGroup * 4 + 3) as MutableList<String>
            groupName.text = "Group " + groupLookup[currentGroup]
            adapter = Format1GroupAdapter(currentGroupTeams)

            recyclerview.adapter = adapter
            itemTouchHelper.attachToRecyclerView(recyclerview)
            recyclerviewPlace.adapter = Format1GroupAdapter(mutableListOf("1.", "2.", "3.", "4."))

        }
    }

    private val itemTouchHelper by lazy {  // 1. Note that I am specifying all 4 directions.
        //    Specifying START and END also allows
        //    more organic dragging than just specifying UP and DOWN.
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
            0
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val adapter = recyclerView.adapter as Format1GroupAdapter
                val from = viewHolder.bindingAdapterPosition
                val to =
                    target.bindingAdapterPosition      // 2. Update the backing model. Custom implementation in
                //    MainRecyclerViewAdapter. You need to implement
                //    reordering of the backing model inside the method.
                adapter.moveItem(from, to)      // 3. Tell adapter to render the model update.
                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder, direction: Int
            ) {
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)

                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.6f
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                viewHolder?.itemView?.alpha = 1.0f

            }

        }
        ItemTouchHelper(simpleItemTouchCallback)
    }

    fun format1NextGroup(view: View) {
        groupResults.addAll(adapter.getTeamOrder())
        Log.d("cup", "new group order: " + adapter.getTeamOrder().toString())
        Log.d("cup", "new teams order: " + groupResults.toString())
        Log.d("cup", "old group order: " + teams.toString())

        if (currentGroup < 7) {
            currentGroup += 1
            groupName.text = "Group " + groupLookup[currentGroup]
            currentGroupTeams =
                teams.slice(currentGroup * 4..currentGroup * 4 + 3) as MutableList<String>
            //adapter = Format1GroupParentAdapter(teams.slice(currentGroup * 4..currentGroup * 4 + 3) as MutableList<String>)
            adapter.updateTeams(currentGroupTeams)
            Log.d("cup", currentGroupTeams.toString())
        } else {
            val intent = Intent(this, Format1Knockout::class.java)
            intent.putExtra("groupResult", groupResults)
            intent.putExtra("original", teams as ArrayList<String>)
            intent.putExtra("cupID", cupID)

            startActivity(intent)
            finish()
        }

    }
}