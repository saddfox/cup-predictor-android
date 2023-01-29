package si.uni_lj.fe.tnuv.cuppredictor

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Integer.min


class Format1KnockoutAdapter(private var teams: MutableList<String>, private var noOfGames: Int) :
    RecyclerView.Adapter<Format1KnockoutAdapter.ViewHolder>() {

    var finalResults = arrayListOf<String>()
    var isFinal = false
    val finalTitle = arrayOf("Final", "3rd place")


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("cup", "CREATED VIEW HOLDER")
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.format1_knockout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (finalResults.size < noOfGames) {
            for (i in 0 until noOfGames) {
                finalResults.add("-1")
            }
            //finalResults.add("-1")
        }

        if (isFinal) {
            holder.finalTitle.visibility = View.VISIBLE
            holder.finalTitle.text = finalTitle[position]
        }


        // sets the text to the textview from our itemHolder class
        holder.team1.text = teams[position * 2]
        holder.team2.text = teams[position * 2 + 1]

        // on long recycler lists elements get unloaded. when they load back their
        // color should match the winner selection, if available

        Log.d("cup", position.toString())
        Log.d("cup", teams[position * 2])
        Log.d("cup", finalResults.toString())
        Log.d("cup", finalResults[position])

        if (teams[position * 2] == finalResults[position]) {
            holder.team1.setBackgroundColor(Color.parseColor("#5000FF00"))
            holder.team2.setBackgroundColor(Color.parseColor("#50FF0000"))
        } else if (teams[position * 2 + 1] == finalResults[position]) {
            holder.team2.setBackgroundColor(Color.parseColor("#5000FF00"))
            holder.team1.setBackgroundColor(Color.parseColor("#50FF0000"))
        } else {
            holder.team1.setBackgroundColor(Color.TRANSPARENT)
            holder.team2.setBackgroundColor(Color.TRANSPARENT)
        }

        // listen for clicks and set colors and results
        holder.team1.setOnClickListener {
            holder.team1.setBackgroundColor(Color.parseColor("#5000FF00"))
            holder.team2.setBackgroundColor(Color.parseColor("#50FF0000"))
            finalResults[position] = teams[position * 2]
        }
        holder.team2.setOnClickListener {
            holder.team2.setBackgroundColor(Color.parseColor("#5000FF00"))
            holder.team1.setBackgroundColor(Color.parseColor("#50FF0000"))
            finalResults[position] = teams[position * 2 + 1]
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        // val limit = 4
        return min(teams.size, noOfGames)
    }

    fun updateTeams(newTeams: ArrayList<String>, newNumber: Int, final: Boolean) {
        Log.d("cup", "new teams " + newTeams + newNumber)
        teams = newTeams.toMutableList()
        noOfGames = newNumber
        finalResults.clear()
        isFinal = final
        Log.d("cup", "new teams " + teams + noOfGames)

        notifyDataSetChanged()
    }

    fun getRoundWinners(): ArrayList<String>? {
        Log.d("cup", "results size: " + finalResults)
        if (finalResults.size != noOfGames) return null
        for (result in finalResults) {
            if (result == "-1") return null
        }
        return finalResults
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val team1: TextView = itemView.findViewById(R.id.format1Team1)
        val team2: TextView = itemView.findViewById(R.id.format1Team2)
        val finalTitle: TextView = itemView.findViewById(R.id.format1FinalTitle)


    }
}
