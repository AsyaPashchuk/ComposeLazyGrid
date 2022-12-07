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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composelazygrid.problem1.MainViewModel
import com.composelazygrid.problem1.RgbSelector
import com.composelazygrid.problem3.CustomGrid
import com.composelazygrid.problem3.FeedViewModel
import com.composelazygrid.ui.theme.ComposeLazyGridTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeLazyGridTheme {
                //Problem 1 with recompose (mutable and immutable for recompose)
                //Resolve 2 for the complex lambda fun
                //remember - marks as stable and wouldn't do the recompose
                //so if you use state for parameters in your code it wouldn't recompose all views
                val viewModel1 = viewModel<MainViewModel>()

                val changeColorLambda = remember<(Color) -> Unit> {
                    {
                        viewModel1.changeColor(it)
                    }
                }

                //Problem3 with custom grid layout where every field will be recomposed
                //With shuffling layouts compose doesn't have instrument
                // for custom grid/row/column layouts
                // to compare fields if they change the order
                //So we need to add keys by hand (like in LazyColumn/Row etc)
                val viewModel2 = viewModel<FeedViewModel>()
                val feeds = viewModel2.feeds

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

                    //Problem3 with custom grid layout where every field will be recomposed
                    //With shuffling layouts compose doesn't have instrument
                    // for custom grid/row/column layouts
                    // to compare fields if they change the order
                    //So we need to add keys by hand (like in LazyColumn/Row etc)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CustomGrid(
                            feeds = feeds,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(onClick = { viewModel2::rearrangeFeeds }) {
                            Text(text = "Shuffle feeds")
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    RgbSelector(
                        color = viewModel1.color,

                        //Problem 1 with recompose (mutable and immutable for recompose)
                        // That lambda fun will create an anonymous class,
                        // so compose will recompose all views with this lambda fun
//                        onColorClick = {
//                            viewModel1.changeColor(it)
//                        }
                        //Resolve 1
                        //For resolve it you may do this only if the viewmodel fun except
                        //the parameter that the lambda fun provides
//                        onColorClick = viewModel1::changeColor

                        //Resolve 2
                        onColorClick = changeColorLambda
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    //Added for testing opening DeepLink from one app in another (link with ComposeDeepLinkingGuide app)
                    Button(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
//                                Uri.parse("https://insta.com/456")
                                Uri.parse("https://insta.com/Nika")
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
                            items(18) { i ->
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