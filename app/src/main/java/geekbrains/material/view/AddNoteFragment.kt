package geekbrains.material.view

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import geekbrains.material.R
import geekbrains.material.model.NotesDB
import geekbrains.material.viewmodel.NotesViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddNoteFragment(
): Fragment() {

    lateinit var noteViewModel: NotesViewModel
    lateinit var buttonAdd: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdd = view.findViewById(R.id.btnAdd)
        setupViewModel()
        buttonAdd.setOnClickListener{
            saveNoteToDatabase()
            requireFragmentManager().popBackStack()
        }
    }

    private fun saveNoteToDatabase() {
        if (validations()) {
            Toast.makeText(activity, "Note is saved", Toast.LENGTH_SHORT).show()
            saveNote()
        } else
            Toast.makeText(activity, "Note is Discarded", Toast.LENGTH_SHORT).show()

    }

    private fun saveNote() {
        val note = NotesDB(0,
                addTitle.text.toString(),
                addDescription.text.toString()
        )
        if (addTitle.text.isNullOrEmpty()) {
            note.title = "Empty Title"
            noteViewModel.insert(note, requireActivity().application)
        }else {
            noteViewModel.insert(note, requireActivity().application)
        }
    }

    fun validations(): Boolean {
        return !(addTitle.text.isNullOrEmpty()
                && addDescription.text.isNullOrEmpty())
    }

    private fun setupViewModel() {
        noteViewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }
}