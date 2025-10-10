package cc.worldmandia.webshoppersonal.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cc.worldmandia.webshoppersonal.Greeting
import cc.worldmandia.webshoppersonal.models.MainPageViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin
import webshoppersonal.composeapp.generated.resources.Res
import webshoppersonal.composeapp.generated.resources.compose_multiplatform

@Composable
fun MainPageContent(viewModel: MainPageViewModel = getKoin().get()) {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }

    var input by remember { mutableStateOf("") }
    var displayed by remember { mutableStateOf("—") }

    Column(Modifier.padding(16.dp)) {
        TextField(value = input, onValueChange = { input = it }, label = { Text("Value") })
        Spacer(Modifier.height(8.dp))
        Row {
            Button(onClick = {
                viewModel.save("myKey", input)
                displayed = viewModel.data
            }) {
                Text("Save")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                viewModel.load("myKey")
                displayed = viewModel.data
            }) {
                Text("Load")
            }
        }
        Spacer(Modifier.height(12.dp))
        Text(text = "Value: $displayed")
    }
}