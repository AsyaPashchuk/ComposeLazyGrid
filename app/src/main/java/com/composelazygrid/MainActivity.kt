package com.composelazygrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells

import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composelazygrid.ui.theme.ComposeLazyGridTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLazyGridTheme {
                val lazyState = rememberLazyListState(
                    initialFirstVisibleItemIndex = 3
                )
                LazyVerticalGrid(
//                    cells = GridCells.Fixed(5),
                    cells = GridCells.Adaptive(100.dp),
                    state = lazyState,
                    content = {
                        items(100) { i ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Item $i",
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(2.dp)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = "cell description",
                                        modifier = Modifier
                                            .size(68.dp)
                                            .padding(2.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}