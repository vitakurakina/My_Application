package com.example.myapplication.ui.theme.screens

//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Note
//import androidx.compose.material.icons.filled.Schedule
//
//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                BottomNavigationItem(
//                    selected = true,
//                    onClick = { navController.navigate("schedule") },
//                    icon = { Icon(Icons.Default.Schedule, contentDescription = null) },
//                    label = { Text("Расписание") }
//                )
//                BottomNavigationItem(
//                    selected = false,
//                    onClick = { navController.navigate("notes") },
//                    icon = { Icon(Icons.Default.Note, contentDescription = null) },
//                    label = { Text("Заметки") }
//                )
//            }
//        }
//    ) { innerPadding ->  // Используем innerPadding
//        NavHost(
//            navController = navController,
//            startDestination = "schedule",
//            modifier = Modifier.padding(innerPadding)  // Применяем отступы
//        ) {
//            composable("schedule") { StubScreen("Расписание") } // Заглушка для экрана расписания
//            composable("notes") { StubScreen("Заметки") } // Заглушка для экрана заметок
//        }
//    }
//}
//
//@Composable
//fun StubScreen(title: String) {
//    Surface {
//        Text(text = "$title экрана пока нет", style = MaterialTheme.typography.h5)
//    }
//}
