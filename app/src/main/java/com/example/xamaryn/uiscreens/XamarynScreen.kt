package com.example.xamaryn.uiscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.xamaryn.domain.Xamaryn
import com.example.xamaryn.presentation.XamarynViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XamarynScreen (xamarynViewModel: XamarynViewModel){
    var task by remember { mutableStateOf("") }
    val xamarynList by xamarynViewModel.Xamaryn.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Xamaryn - Gestor de tareas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = task,
                    onValueChange = {task = it},
                    modifier = Modifier.weight(1f),
                    placeholder = {Text("Por favor, ingrese una tarea")},
                    shape = RoundedCornerShape(22.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Red,
                        unfocusedIndicatorColor = Color.Red,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Button(
                    onClick = {
                        if (task.isNotBlank()) {
                            xamarynViewModel.addTodo(title = task)
                            task = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Añadir")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.Red)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = xamarynList, key = {it.id}) { Xamaryn ->
                    var isEditing by remember(Xamaryn.id){mutableStateOf(false)}
                    var newTitle by remember(Xamaryn.id){mutableStateOf(Xamaryn.title)}

                   Row(
                       verticalAlignment = Alignment.CenterVertically,
                       modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                       horizontalArrangement = Arrangement.SpaceBetween
                   ) {
                       Row(verticalAlignment = Alignment.CenterVertically) {
                           Checkbox(
                               checked = Xamaryn.isDone,
                               onCheckedChange = {xamarynViewModel.toggleTodoDone(Xamaryn)},
                               colors = CheckboxDefaults.colors(Color.Red)
                           )
                           if (isEditing) {
                              OutlinedTextField(
                                  value = newTitle,
                                  onValueChange = {newTitle = it},
                                  modifier = Modifier.width(160.dp),
                                  shape = RoundedCornerShape(22.dp),
                                  colors = TextFieldDefaults.colors(
                                      focusedIndicatorColor = Color.Red,
                                      unfocusedIndicatorColor = Color.Red,
                                      focusedContainerColor = Color.White,
                                      unfocusedContainerColor = Color.White
                                  )
                              )
                               Button(onClick = {
                                   xamarynViewModel.editTodo(Xamaryn, newTitle)
                                   isEditing = false
                               }, modifier = Modifier.padding(start = 4.dp),
                                   colors = ButtonDefaults.buttonColors(Color.Red)
                               ) {
                                   Text("Guardar")
                               }

                           }else{
                               Text(Xamaryn.title,
                                   modifier = Modifier.padding(start = 8.dp),
                                   style = if (Xamaryn.isDone)
                                       LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                                   else LocalTextStyle.current
                               )
                           }
                       }
                       Row {
                           IconButton(onClick = {
                               if (!isEditing) newTitle = Xamaryn.title
                               isEditing = !isEditing
                           }) {
                               Icon(Icons.Default.Edit,
                                   contentDescription = "Editar",
                                   tint = Color.Red)
                           }
                           IconButton(onClick = {
                               xamarynViewModel.adiosLilyDelete(Xamaryn)
                           }) {
                               Icon(Icons.Default.Delete,
                                   contentDescription = "Eliminar",
                                   tint = Color.Red)
                           }
                       }
                   }
                }
            }
        }
    }
}