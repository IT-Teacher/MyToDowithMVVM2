package uz.itteacher.mytodowithmvvm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<Notes>>

    @Insert
    suspend fun addNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Int): Notes

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE dateTime == :today")
    fun sortbytoday(today: String): Flow<List<Notes>>




}