package geekbrains.material.model

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import javax.inject.Inject

class NoteRepository @Inject constructor(val noteDao: NotesDao) {

    fun insert(note: NotesDB) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insert(note)
        }
    }

    fun delete(note: NotesDB) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
    }

    fun deleteById(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.deleteById(id)
        }
    }

    fun update(note: NotesDB) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.update(note)
            Log.e("DEBUG", "update is called in repo")

        }
    }

    fun getAllNotes(): LiveData<List<NotesDB>>{
        return noteDao.getAllNotes()
    }
}