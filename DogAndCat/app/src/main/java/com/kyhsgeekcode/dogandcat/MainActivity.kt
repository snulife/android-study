package com.kyhsgeekcode.dogandcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kyhsgeekcode.dogandcat.ui.theme.DogAndCatTheme
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogAndCatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: CatFactViewModel = viewModel()
                    val facts = viewModel.facts.collectAsState().value
                    val dogUrl = viewModel.dogUrl.collectAsState().value
                    val refreshing = viewModel.isRefreshing.collectAsState(false).value

                    LaunchedEffect(Unit) {
                        viewModel.fetchFacts()
                        viewModel.fetchDog()
                    }
                    val state = rememberPullRefreshState(refreshing, onRefresh = {
                        viewModel.fetchFacts()
                        viewModel.fetchDog()
                    })
                    val scrollState = rememberScrollState()

                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .pullRefresh(state)
                                .verticalScroll(scrollState)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                facts.forEach {
                                    Text(text = it.fact)
                                }
                            }
                            Column {
                                Text(text = "URL: $dogUrl")
                                GlideImage(
                                    { dogUrl },
                                    modifier = Modifier.clip(
                                        RoundedCornerShape(10.dp)
                                    ),
                                    loading = {
                                        Box(
                                            modifier = Modifier.matchParentSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                )
                            }
                        }
                        if (refreshing) {
                            CircularProgressIndicator(
                                color = Color(0xFF6200EE),
                                modifier = Modifier
                                    .size(50.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DogAndCatTheme {
        Greeting("Android")
    }
}