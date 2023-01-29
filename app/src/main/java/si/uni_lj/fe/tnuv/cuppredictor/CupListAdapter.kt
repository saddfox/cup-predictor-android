package si.uni_lj.fe.tnuv.cuppredictor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import si.uni_lj.fe.tnuv.cuppredictor.models.CupListData


class CupListAdapter(private val cupList: Array<CupListData>) :
    RecyclerView.Adapter<CupListAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cup_list_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = cupList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.load("https://cup.saferutar.com/api/getLogo/" + itemsViewModel.cupID) {
            placeholder(R.drawable.ic_launcher_background)
        }

        if (itemsViewModel.active == true) {
            if (itemsViewModel.submitted == true) {
                holder.statusView.text = "submitted, waiting for results"
            } else {
                holder.statusView.text = "submit prediction"
            }

        } else if (itemsViewModel.results == true) {
            holder.statusView.text = "view results"
        } else {
            holder.statusView.text = "wait for results"
        }

        // sets the text to the textview from our itemHolder class
        holder.textView.text = itemsViewModel.cupName

        holder.itemView.setOnClickListener {
            Log.d("cup", itemsViewModel.cupName.toString())

            val context = holder.itemView.context
            if (itemsViewModel.results == true) {
                // show scoreboard
                val intent = Intent(context, Scoreboard::class.java)
                intent.putExtra("cupID", itemsViewModel.cupID!!)
                context.startActivity(intent)

            } else if (itemsViewModel.active == true && itemsViewModel.submitted != true) {
                when (itemsViewModel.format) {
                    1 -> {
                        val intent = Intent(context, Format1Group::class.java)
                        intent.putExtra("cupID", itemsViewModel.cupID!!)
                        context.startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(context, Format2Knockout::class.java)
                        intent.putExtra("cupID", itemsViewModel.cupID!!)
                        context.startActivity(intent)
                    }
                }

            }

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return cupList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cupLogo)
        val textView: TextView = itemView.findViewById(R.id.cupName)
        val statusView: TextView = itemView.findViewById(R.id.cupStatus)
    }
}
