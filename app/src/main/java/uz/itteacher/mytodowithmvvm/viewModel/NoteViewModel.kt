package uz.itteacher.mytodowithmvvm.viewModel


import android.content.Context
import android.util.Log
import androidx.collection.emptyFloatList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import uz.itteacher.mytodowithmvvm.database.NoteDatabase
import uz.itteacher.mytodowithmvvm.database.Notes
import uz.itteacher.mytodowithmvvm.util.scheduleReminder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel(db: NoteDatabase, val context: Context) : ViewModel() {
    private val dao = db.noteDao()
    var notes: Flow<List<Notes>> = dao.getAllNotes()

    fun validate(note: Notes): String {
        val errorText = "success"
        if (note.title.isEmpty()) {
            return "Title is required"
        }
        if (note.description.isEmpty()) {
            return "Description is required"
        }
        if (note.dateTime.isEmpty()) {
            return "Date is required"
        }
        if (note.time.isEmpty()) {
            return "Time is required"
        }
        return errorText
    }


    fun addNote(note: Notes) {
        viewModelScope.launch {
            dao.addNote(note)
            scheduleReminder(context, note)
        }
    }

    fun updateNote(note: Notes) {
        viewModelScope.launch {
            dao.updateNote(note)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            dao.deleteNote(id)
        }
    }

    fun search(query: String): Flow<List<Notes>> {
        return dao.search(query)
    }

    fun sortbytoday(): Flow<List<Notes>> {
        val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        return dao.sortbytoday(today)
    }



}