package uz.itteacher.mytodowithmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1)
abstract class NoteDatabase() : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private var instance: NoteDatabase? = null

        fun getInsance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }
    }
}