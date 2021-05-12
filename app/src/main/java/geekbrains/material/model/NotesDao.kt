package geekbrains.material.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NotesDB): Long

    @Update
    fun update(note: NotesDB)

    @Query("delete from Notes where id = :id")
    fun deleteById(id: Int)

    @Delete
    fun delete(note: NotesDB)

    @Query("select * from Notes")
    fun getAllNotes(): LiveData<List<NotesDB>>
}

@Entity (tableName = "Notes"
)
data class NotesDB(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") var id: Long,
    @ColumnInfo(name = "Title") var title: String,
   // @ColumnInfo(name = "Date") var date: Date,
    @ColumnInfo(name = "Description") var description: String
)