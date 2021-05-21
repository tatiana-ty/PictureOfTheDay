package geekbrains.material.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NotesDB::class], version = 1, exportSchema = false)
abstract class NotesDataBase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}