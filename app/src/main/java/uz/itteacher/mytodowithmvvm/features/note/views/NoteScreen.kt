package uz.itteacher.mytodowithmvvm.features.note.views

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


import uz.itteacher.mytodowithmvvm.database.Notes
import uz.itteacher.mytodowithmvvm.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

import java.util.Locale

import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController, viewModel: NoteViewModel, id: Int) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val myColor = Color(124, 255, 100)
    val colorInt = myColor.toArgb()
    var note = Notes(0, "", "", "", colorInt, "", "")
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    if (id != -1) {
        val notes by viewModel.notes.collectAsState(initial = emptyList())

        notes.forEach {
            if (it.id == id) {
                note = it
            }
        }
        title = note.title
        description = note.description
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Note") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background

                        )
                    }
                },
                actions = {
                    TextButton(onClick = {
                        val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(
                            java.util.Date()
                        )
                        val red = Random.nextInt(100, 255)
                        val green = Random.nextInt(100, 255)
                        val blue = Random.nextInt(100, 255)
                        myColor.toArgb()
                        val noteColor = Color(red = red, green = green, blue = blue)
                        val noteColorInt = noteColor.toArgb()

                        if (id != -1) {
                            viewModel.updateNote(
                                Notes(
                                    id = id,
                                    title = title,
                                    description = description,
                                    date = date,
                                    color = noteColorInt,
                                    time = selectedTime,
                                    dateTime = selectedDate
                                )
                            )
                        } else {

                            viewModel.addNote(

                                Notes(
                                    title = title,
                                    description = description,
                                    date = date,
                                    color = noteColorInt,
                                    time = selectedTime,
                                    dateTime = selectedDate
                                )
                            )
                        }
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Save",
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background
                )
            )

        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, modifier = Modifier
                .fillMaxWidth()
                .padding(13.5.dp), maxLines = 1, placeholder = { Text(text = "Title") })
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.5.dp),
                minLines = 6,
                placeholder = { Text(text = "Description") })


            Button(onClick = {
                val calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDate = "$dayOfMonth/${month + 1}/$year"
                                            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePicker.show()
            }) {
                Text("Pick Date")
            }

            Button(onClick = {

                val calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        selectedTime = "$hour:$minute"
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePicker.show()
            }) {
                Text("Pick Time")
            }
        }
    }
}