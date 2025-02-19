package uz.itteacher.mytodowithmvvm.util


import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter

class ColorConverter {
    @TypeConverter
    fun fromColor(color: android.graphics.Color): Int {
        return color.toArgb()
    }

    @TypeConverter
    fun toColor(colorInt: Int): Color {
        return Color(colorInt)
    }
}
