package geekbrains.material.view

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import geekbrains.material.R
import geekbrains.material.model.NotesDB
import geekbrains.material.viewmodel.DiffCallback
import geekbrains.material.viewmodel.ItemTouchHelperAdapter
import geekbrains.material.viewmodel.ItemTouchHelperViewHolder
import geekbrains.material.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.note_items.view.*

class NotesAdapter(
    private var context: Context,
    private var onItemClickListener: OnItemClickListener,
    noteList: List<NotesDB>,
    private val interaction: Interaction? = null,
    private val dragListener: OnStartDragListener,
    private val notesViewModel: NotesViewModel,
    private val application: Application
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(), ItemTouchHelperAdapter {

    private val notes = mutableListOf<NotesDB>()

    init {
        notes.addAll(noteList)
    }

    inner class NoteViewHolder(view: View, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        fun bind(item: NotesDB) {
            itemView.txtTitle.text = item.title
            itemView.txtDescription.text = item.description

            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition,item)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_items, parent, false)
        return NoteViewHolder(
                view,
                interaction
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notes.removeAt(fromPosition).apply {
            notes.add(if (toPosition > fromPosition) toPosition - 1 else toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        notesViewModel.delete(notes.get(position), application)
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun swap(notes: List<NotesDB>) {
        val diffCallback = DiffCallback(this.notes, notes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.notes.clear()
        this.notes.addAll(notes)
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(data: NotesDB)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: NotesDB)
    }
}