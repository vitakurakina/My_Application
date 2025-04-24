//package com.example.myapplication.ui.theme.screens
//
//@Composable
//fun ScheduleScreen() {
//    var selectedFaculty by remember { mutableStateOf("Физмат") }
//    var selectedCourse by remember { mutableStateOf("1 курс") }
//    var selectedGroup by remember { mutableStateOf("101") }
//    var selectedDay by remember { mutableStateOf("Понедельник") }
//
//    val days = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб")
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)) {
//
//        // Выпадающие списки
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            DropdownSelector("Факультет", selectedFaculty, listOf("Физмат", "Биофак", "Истфак")) {
//                selectedFaculty = it
//            }
//            DropdownSelector("Курс", selectedCourse, listOf("1 курс", "2 курс", "3 курс", "4 курс")) {
//                selectedCourse = it
//            }
//            DropdownSelector("Группа", selectedGroup, listOf("101", "102", "103")) {
//                selectedGroup = it
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Переключение дней
//        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            items(days) { day ->
//                val isSelected = day == selectedDay
//                Button(
//                    onClick = { selectedDay = day },
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = if (isSelected) Color.Blue else Color.LightGray,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(day)
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Список пар (заглушка)
//        val exampleSchedule = listOf(
//            Lesson("08:30–10:00", "Математика", "Иванов И.И.", "ауд. 301"),
//            Lesson("10:10–11:40", "Физика", "Петров П.П.", "ауд. 302")
//        )
//
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//            items(exampleSchedule) { lesson ->
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    elevation = 4.dp
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(lesson.time, fontWeight = FontWeight.Bold)
//                        Text(lesson.subject)
//                        Text("Преподаватель: ${lesson.teacher}")
//                        Text("Аудитория: ${lesson.room}")
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun DropdownSelector(
//    label: String,
//    selectedItem: String,
//    options: List<String>,
//    onItemSelected: (String) -> Unit
//) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Column {
//        Text(text = label, style = MaterialTheme.typography.labelSmall)
//        Box {
//            OutlinedButton(onClick = { expanded = true }) {
//                Text(selectedItem)
//            }
//            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
//                options.forEach { option ->
//                    DropdownMenuItem(onClick = {
//                        onItemSelected(option)
//                        expanded = false
//                    }) {
//                        Text(option)
//                    }
//                }
//            }
//        }
//    }
//}
//
//data class Lesson(
//    val time: String,
//    val subject: String,
//    val teacher: String,
//    val room: String
//)
