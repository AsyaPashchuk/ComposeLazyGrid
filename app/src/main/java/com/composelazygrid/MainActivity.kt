package com.composelazygrid

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells

import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    //Added for testing opening DeepLink from one app in another (link with ComposeDeepLinkingGuide app)
                    Button(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://insta.com/456")
                            )
                            val pendingIntent = TaskStackBuilder.create(applicationContext)
                                .run {
                                    addNextIntentWithParentStack(intent)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                                        )
                                    } else {
                                        getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                        )
                                    }
                                }
                            pendingIntent.send()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Magenta,
                            contentColor = Color.White
                        ),
                        border = BorderStroke(2.dp, Color.LightGray),
                        modifier = Modifier.size(170.dp, 90.dp)
                    ) {
                        Text(
                            text = "Open another app, trigger deeplink",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                    //end Added for testing opening DeepLink from one app in another (link with ComposeDeepLinkingGuide app)

                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
//                    cells = GridCells.Fixed(5),
                        cells = GridCells.Adaptive(100.dp),
                        state = lazyState,
                        content = {
                            items(30) { i ->
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
}