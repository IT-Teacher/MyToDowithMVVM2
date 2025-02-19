package uz.itteacher.mytodowithmvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import uz.itteacher.mytodowithmvvm.common.navigation.AppNavHost
import uz.itteacher.mytodowithmvvm.database.NoteDatabase
import uz.itteacher.mytodowithmvvm.ui.theme.MyToDowithMVVMTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import uz.itteacher.mytodowithmvvm.database.NoteDao
import uz.itteacher.mytodowithmvvm.viewModel.NoteViewModel


class MainActivity : ComponentActivity() {
    private lateinit var db: NoteDatabase
    private lateinit var viewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            db = NoteDatabase.getInsance(this)
            viewModel = NoteViewModel(db, this)
            MyToDowithMVVMTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    vm = viewModel
                )
            }
        }
    }
}

