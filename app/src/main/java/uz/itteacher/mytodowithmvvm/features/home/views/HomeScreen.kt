package uz.itteacher.mytodowithmvvm.features.home.views


import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavHostController
import uz.itteacher.mytodowithmvvm.R
import uz.itteacher.mytodowithmvvm.database.Notes

import uz.itteacher.mytodowithmvvm.viewModel.NoteViewModel
import java.util.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: NoteViewModel
) {
    var query by remember { mutableStateOf("") }
    val queryNotes by viewModel.sortbytoday().collectAsState(initial = emptyList())
    val allNotes by viewModel.notes.collectAsState(initial = emptyList())
    var flag by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background
                )
            )

        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { text ->
                        query = text
                        if (text.length > 2) {
                            viewModel.search(query)
                        }
                    },
                    label = { Text(text = "Search") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)

                )

                Row(modifier = Modifier.fillMaxWidth().padding(end = 15.dp), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = {
                        flag = !flag
                    })
                    { Text(text = "View All", fontSize = 16.sp, color = Color.Blue) }
                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    if (flag){
                        items(allNotes) {
                            ItemNote(it, viewModel, navController)
                        }
                    }
                    else {
                        items(queryNotes) {
                            ItemNote(it, viewModel, navController)
                        }
                    }

                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("noteScreen/-1")

            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}

@Composable
fun ItemNote(note: Notes, viewModel: NoteViewModel, navController: NavHostController) {
    val color = Color(note.color)

    Card(
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {


                Text(
                    text = if (note.title.length > 15) note.title.substring(0, 10) else note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.width(70.dp)) {
                    IconButton(onClick = { viewModel.deleteNote(note.id) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color(0xFF2A2929)
                        )
                    }
                    IconButton(onClick = { navController.navigate("noteScreen/${note.id}") }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color(0xFF2A2929)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (note.description.length > 150) note.description.substring(
                        0,
                        150
                    ) + "..." else note.description, color = Color.White
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp), horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${note.dateTime} ${note.time}", modifier = Modifier
                        .border(width = 1.dp, shape = RoundedCornerShape(8.dp), color = Color.Black)
                        .padding(4.dp), fontSize = 12.sp
                )
            }
        }
    }
}
