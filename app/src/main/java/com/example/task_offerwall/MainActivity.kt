package com.example.task_offerwall

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.task_offerwall.data.repositories.ApiRepository
import com.example.task_offerwall.network.RetrofitInstance
import com.example.task_offerwall.ui.theme.Task_OfferWallTheme
import com.example.task_offerwall.viewmodel.MainViewModel
import com.example.task_offerwall.viewmodel.MainViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Task_OfferWallTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val apiRepository = ApiRepository(RetrofitInstance.apiService)

                    val viewModel: MainViewModel = viewModel(
                        factory = MainViewModelFactory(apiRepository)
                    )

                    val objectType = viewModel.objectType.collectAsState().value
                    val message = viewModel.message.collectAsState().value
                    val url = viewModel.url.collectAsState().value

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.5f)
                                .fillMaxWidth()
                        ) {
                            when (objectType) {
                                "text" -> TextScreen(message ?: "No message")
                                "webview" -> WebViewScreen(url ?: "")
                                "image" -> ImageScreen(url ?: "")
                                else -> Text("No data available")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), // Додати відступи
                            contentAlignment = Alignment.Center // Центрувати кнопку
                        ) {
                            Button(onClick = { viewModel.nextObject() }) {
                                Text("Next Object")
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun TextScreen(message: String) {
    Text(text = message, modifier = Modifier.fillMaxSize())
}

@Composable
fun WebViewScreen(url: String) {
    val context = LocalContext.current
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    })
}


@Composable
fun ImageScreen(url: String) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}