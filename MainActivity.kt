# package com.example.studentrecords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

data class Student(
val id: Int,
val name: String,
val grade: Double
)

class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContent {
StudentRecordsApp()
}
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRecordsApp() {
var students by remember { mutableStateOf(listOf<Student>()) }
var id by remember { mutableStateOf("") }
var name by remember { mutableStateOf("") }
var grade by remember { mutableStateOf("") }
var message by remember { mutableStateOf("") }

Scaffold(  
    topBar = {  
        TopAppBar(title = { Text("Student Records Management") })  
    }  
) { padding ->  
    Column(  
        modifier = Modifier  
            .padding(padding)  
            .padding(16.dp)  
    ) {  
        // Input fields  
        OutlinedTextField(  
            value = id,  
            onValueChange = { id = it },  
            label = { Text("ID") },  
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  
            modifier = Modifier.fillMaxWidth()  
        )  
        OutlinedTextField(  
            value = name,  
            onValueChange = { name = it },  
            label = { Text("Name") },  
            modifier = Modifier.fillMaxWidth()  
        )  
        OutlinedTextField(  
            value = grade,  
            onValueChange = { grade = it },  
            label = { Text("Grade") },  
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  
            modifier = Modifier.fillMaxWidth()  
        )  

        Spacer(modifier = Modifier.height(8.dp))  

        Button(  
            onClick = {  
                val idInt = id.toIntOrNull()  
                val gradeDouble = grade.toDoubleOrNull()  

                if (idInt == null || name.isBlank() || gradeDouble == null) {  
                    message = "Please fill all fields correctly."  
                    return@Button  
                }  

                if (students.any { it.id == idInt }) {  
                    message = "Student with this ID already exists."  
                } else {  
                    students = students + Student(idInt, name, gradeDouble)  
                    id = ""  
                    name = ""  
                    grade = ""  
                    message = "Student added successfully."  
                }  
            },  
            modifier = Modifier.fillMaxWidth()  
        ) {  
            Text("Add Student")  
        }  

        if (message.isNotEmpty()) {  
            Text(text = message, color = MaterialTheme.colorScheme.primary)  
            Spacer(modifier = Modifier.height(8.dp))  
        }  

        Divider()  

        Spacer(modifier = Modifier.height(8.dp))  

        Text("Student List", style = MaterialTheme.typography.titleMedium)  

        if (students.isEmpty()) {  
            Text("No student records found.")  
        } else {  
            LazyColumn {  
                items(students) { student ->  
                    Card(  
                        modifier = Modifier  
                            .fillMaxWidth()  
                            .padding(vertical = 4.dp),  
                        elevation = CardDefaults.cardElevation(4.dp)  
                    ) {  
                        Row(  
                            modifier = Modifier  
                                .padding(12.dp)  
                                .fillMaxWidth(),  
                            horizontalArrangement = Arrangement.SpaceBetween  
                        ) {  
                            Column {  
                                Text("ID: ${student.id}")  
                                Text("Name: ${student.name}")  
                                Text("Grade: ${student.grade}")  
                                Text(  
                                    "Remarks: ${if (student.grade < 60) "FAILED" else "PASSED"}",  
                                    color = if (student.grade < 60) MaterialTheme.colorScheme.error  
                                    else MaterialTheme.colorScheme.primary  
                                )  
                            }  
                            Button(  
                                onClick = {  
                                    students = students.filter { it.id != student.id }  
                                },  
                                colors = ButtonDefaults.buttonColors(  
                                    containerColor = MaterialTheme.colorScheme.error  
                                )  
                            ) {  
                                Text("Delete")  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
}

}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
StudentRecordsApp()
}
