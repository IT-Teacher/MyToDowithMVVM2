package uz.itteacher.mytodowithmvvm.viewModel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import uz.itteacher.mytodowithmvvm.database.NoteDatabase
import uz.itteacher.mytodowithmvvm.database.Notes
import uz.itteacher.mytodowithmvvm.util.scheduleReminder

class NoteViewModel(db: NoteDatabase, val context: Context):ViewModel() {
    private val dao = db.noteDao()
    var notes: Flow<List<Notes>> = dao.getAllNotes()


    fun addNote(note: Notes){
        viewModelScope.launch {
            dao.addNote(note)
            scheduleReminder(context, note)
        }
    }

    fun updateNote(note: Notes){
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }

    fun deleteNote(id: Int){
        viewModelScope.launch {
            dao.deleteNote(id)
        }
    }

    fun search(query : String): Flow<List<Notes>> {
        return dao.search(query)
    }
}