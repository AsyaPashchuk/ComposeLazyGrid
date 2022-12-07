package com.composelazygrid.problem2

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.externalmodulewithoutcompose.ExternalUser

@Composable
fun WelcomeView(
    //This class is come outside of compose world from different module
    //and marks as unstable
    //and can produce the issue in compose
    user: ExternalUser
) {
    Text(text = "Welcome $(user.userName)!")
}