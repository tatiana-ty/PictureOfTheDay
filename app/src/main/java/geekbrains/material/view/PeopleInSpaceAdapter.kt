package geekbrains.material.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import geekbrains.material.R

class PeopleInSpaceAdapter(private val names: List<String>) :
        RecyclerView.Adapter<PeopleInSpaceAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null

        init {
            name = itemView.findViewById(R.id.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.people_in_space_item, parent, false)
        println(names.size)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name?.text = names[position]
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return names.size
    }
}