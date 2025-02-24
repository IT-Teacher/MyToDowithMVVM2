package uz.itteacher.mytodowithmvvm.database

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime


@Entity
class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,//24.02.2025
    val color: Int,
    val time: String,//12:00
    val dateTime: String//28.02.2025

)