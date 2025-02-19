package uz.itteacher.mytodowithmvvm.common.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uz.itteacher.mytodowithmvvm.features.home.views.HomeScreen
import uz.itteacher.mytodowithmvvm.features.note.views.NoteScreen
import uz.itteacher.mytodowithmvvm.viewModel.NoteViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: NoteViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.name,
        modifier = modifier
    ) {
        composable(Routes.Home.name) {
            HomeScreen(navController, vm)
        }
        composable("noteScreen/{id}") {
            val id = it.arguments?.getString("id")
            NoteScreen(navController, vm, id!!.toInt())

        }
    }

}