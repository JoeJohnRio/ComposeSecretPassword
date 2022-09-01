package com.example.composecodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composecodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {

    private var isPasswordCorrects = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        var isSecretKeyTrue by rememberSaveable { mutableStateOf(false) }
        var isPasswordCorrect by rememberSaveable { mutableStateOf(false) }
        var key by rememberSaveable { mutableStateOf(mutableListOf(false, false, true)) }

        if (isSecretKeyTrue) {
            if (isPasswordCorrect) {
                Greetings()
            } else {
                SecretCodeScreen(onContinueClicked = {
                    isPasswordCorrect = isPasswordCorrects
                })
            }
        } else {
            SecretButtonScreen(onContinueClicked = {
                isSecretKeyTrue = key.all { it == true }
            }, key)
        }
    }

    @Composable
    fun SecretButtonScreen(onContinueClicked: () -> Unit, key: MutableList<Boolean>) {
        Surface {
            Box(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primary)
            ) {
                Button(
                    modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    onClick = {key.set(0, !key.get(0))},
                ) {}

                Button(
                    modifier = Modifier.size(40.dp).align(Alignment.BottomStart),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    onClick = {key.set(1, !key.get(1))},
                ) {}

                Button(
                    modifier = Modifier.size(40.dp).align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    onClick = {key.set(2, !key.get(2))},
                ) {}

                Button(
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    onClick = onContinueClicked,
                ) {}
            }
        }
    }

    @Composable
    fun SecretCodeScreen(onContinueClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SearchBar()
                ButtonReveal(onContinueClicked)
            }
        }
    }

    @Composable
    fun Greetings(names: List<String> = List(1000) { getRandomString(32) }) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {

            Column() {
                Text(text = "List Password", modifier = Modifier.padding(24.dp))
                LazyColumn {
                    items(names) { name ->
                        Greeting(name)
                    }
                }
            }
        }
    }

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    @Composable
    fun Greeting(name: String) {
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = name)
                }
            }
        }
    }

    @Composable
    fun ButtonReveal(onContinueClicked: () -> Unit) {
        Surface {
            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                onClick = onContinueClicked,
            ) {
                Text("Continue")
            }
        }
    }

    @Composable
    fun SearchBar() {
        var value by rememberSaveable { mutableStateOf("") }
        Surface {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = {
                    // it is crucial that the update is fed back into BasicTextField in order to
                    // see updates on the text
                    value = it
                    isPasswordCorrects = value.equals("password")
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                },
                placeholder = {
                    Text(text = "Password Manager")
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    textColor = Color.White,
                    placeholderColor = Color.White
                ),
            )
        }
    }

    @Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    fun SecretCodePreview() {
        BasicsCodelabTheme {
            SecretButtonScreen({}, mutableListOf())
        }
    }
    //@Composable
//fun MyApp(){
//    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
//
//    if (shouldShowOnboarding){
//        OnboardingScreen(onContinueClicked = {
//            shouldShowOnboarding = false
//        })
//    }else{
//        Greetings()
//    }
//@Composable
//fun Greetings(names: List<String> = List(1000) {"$it"}){
//    // A surface container using the 'background' color from the theme
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colors.background
//    ) {
//            LazyColumn{
//                items(names){ name ->
//                    Greeting(name)
//                }
//            }
//    }
//}
//
//@Composable
//fun Greeting(name: String) {
//    var isExpanded by rememberSaveable {
//        mutableStateOf(false)
//    }
//    val extraPadding by animateDpAsState(
//        targetValue = if (isExpanded) 48.dp else 0.dp,
//        animationSpec = tween(2000)
//    )
//    Surface(color = MaterialTheme.colors.primary,
//    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)){
//        Row(modifier = Modifier
//            .padding(24.dp)
//            .padding(bottom = extraPadding)) {
//            Column(modifier = Modifier.weight(1f)){
//                Text(text = "Hello,")
//                Text(text = name)
//            }
//            OutlinedButton(onClick = { isExpanded = !isExpanded }) {
//                Text(if (isExpanded) "Show less"
//                else "Show more")
//            }
//        }
//    }
//}
//
//@Composable
//fun OnboardingScreen(onContinueClicked: () -> Unit) {
//    // TODO: This state should be hoisted
//
//    Surface {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("Welcome to the Basics Codelab!")
//            Button(
//                modifier = Modifier.padding(vertical = 24.dp),
//                onClick = onContinueClicked
//            ) {
//                Text("Continue")
//            }
//        }
//    }
}

//}

//}
//
//@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)
//@Composable
//fun OnboardingPreview() {
//    BasicsCodelabTheme {
//        OnboardingScreen(onContinueClicked = {})
//    }
//}
//
//@Preview(showBackground = true, widthDp = 320)
//@Composable
//fun DefaultPreview() {
//    BasicsCodelabTheme {
//        Greetings()
//    }
//}