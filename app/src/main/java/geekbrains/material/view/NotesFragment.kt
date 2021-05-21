package geekbrains.material.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import geekbrains.material.R
import geekbrains.material.model.NotesDB
import geekbrains.material.viewmodel.ItemTouchHelperCallback
import geekbrains.material.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : Fragment(),
    NotesAdapter.Interaction {

    private lateinit var notesAdapter: NotesAdapter

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var recyclerView: RecyclerView

    lateinit var allNotes: List<NotesDB>

    lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        allNotes = mutableListOf()
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.notesRecyclerView)
        setupViewModel()
        initRecyclerView()
        observerLiveData()
        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
            val manager = childFragmentManager
            manager.let {
                manager.beginTransaction()
                        .add(R.id.listContainer, AddNoteFragment())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
            }
            notesAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            notesAdapter = NotesAdapter(
                context,
                object : NotesAdapter.OnItemClickListener {
                    override fun onItemClick(data: NotesDB) {
                        Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
                    }
                },
                allNotes,
                this@NotesFragment,
                object : NotesAdapter.OnStartDragListener {
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                        itemTouchHelper.startDrag(viewHolder)
                        observerLiveData()
                    }
                },
                notesViewModel,
                requireActivity().application
        )
            layoutManager = LinearLayoutManager(this@NotesFragment.context)
            adapter = notesAdapter
        }
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(notesAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun observerLiveData() {
        notesViewModel.getAllNotes(requireActivity().application).observe(viewLifecycleOwner, Observer { lisOfNotes ->
            lisOfNotes?.let {
                allNotes = it
                notesAdapter.swap(it)
            }
        })
    }

    private fun setupViewModel() {
        notesViewModel =
            ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    override fun onItemSelected(position: Int, item: NotesDB) {

    }
}
