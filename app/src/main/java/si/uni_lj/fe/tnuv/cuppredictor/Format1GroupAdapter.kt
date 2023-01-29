package si.uni_lj.fe.tnuv.cuppredictor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class Format1GroupAdapter(private var teams: MutableList<String>) :
    RecyclerView.Adapter<Format1GroupAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.format1_group, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val groupLookup = arrayOf("A", "B", "C", "D", "E", "F", "G", "H")
//        val indexLookup = arrayOf("1.", "2.", "3.", "4.")


        // sets the text to the textview from our itemHolder class
        holder.teamName.text = teams[position]


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        // val limit = 4
        return teams.size
    }

    fun moveItem(from: Int, to: Int) {
        val temp = teams[from]
        teams.removeAt(from)
        teams.add(to, temp)

    }

    fun updateTeams(newTeams: MutableList<String>) {
        teams = newTeams
        notifyDataSetChanged()
    }

    fun getTeamOrder(): MutableList<String> {
        return teams
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val childRecycler: RecyclerView = itemView.findViewById(R.id.format1GroupChild)
        //val textView: TextView = itemView.findViewById(R.id.format1GroupName)
        //val ctx: Context = itemView.context
        val teamName: TextView = itemView.findViewById(R.id.format1TeamName)
//        val teamPlace: TextView = itemView.findViewById(R.id.format1TeamPlace)


    }
}
