package si.uni_lj.fe.tnuv.cuppredictor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import si.uni_lj.fe.tnuv.cuppredictor.models.CupResult

//class ScoreboardAdapter {
//}

class ScoreboardAdapter(private val scoreboard: Array<CupResult>) :
    RecyclerView.Adapter<ScoreboardAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scoreboard_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = scoreboard[position]

        holder.placement.text = (position + 1).toString()
        holder.name.text = itemsViewModel.user
        holder.points.text = itemsViewModel.score.toString()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return scoreboard.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val placement: TextView = itemView.findViewById(R.id.scorePlace)
        val name: TextView = itemView.findViewById(R.id.scoreName)
        val points: TextView = itemView.findViewById(R.id.scorePoints)
    }
}
