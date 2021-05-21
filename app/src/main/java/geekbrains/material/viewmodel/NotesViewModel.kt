package geekbrains.material.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import geekbrains.material.model.NoteRepository
import geekbrains.material.model.NotesDB
import geekbrains.material.model.NotesDao
import geekbrains.material.model.NotesDataBase
import javax.inject.Inject

class NotesViewModel()
: ViewModel() {

    lateinit var noteRepository : NoteRepository

    fun insert(note: NotesDB, application: Application) {
        val db = Room.databaseBuilder(
            application.applicationContext,
            NotesDataBase::class.java, "Note"
        ).build()
        val notesDao = db.notesDao()

        noteRepository = NoteRepository(notesDao)
        return noteRepository.insert(note)
    }

    fun delete(note: NotesDB, application: Application) {
        val db = Room.databaseBuilder(
            application.applicationContext,
            NotesDataBase::class.java, "Note"
        ).build()
        val notesDao = db.notesDao()

        noteRepository = NoteRepository(notesDao)
        noteRepository.delete(note)
    }

    fun getAllNotes(application: Application): LiveData<List<NotesDB>> {
        val db = Room.databaseBuilder(
            application.applicationContext,
            NotesDataBase::class.java, "Note"
        ).build()
        val notesDao = db.notesDao()

        noteRepository = NoteRepository(notesDao)
        Log.e("DEBUG", "View model getallnotes")
        return noteRepository.getAllNotes()
    }
}