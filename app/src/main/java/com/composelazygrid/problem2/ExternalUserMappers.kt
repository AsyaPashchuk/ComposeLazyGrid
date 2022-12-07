package com.composelazygrid.problem2

import com.externalmodulewithoutcompose.ExternalUser

//To resolve the Problem 2 with external module
//from uncompose world (which is unstable in compose)
//For resolve this we do mapper and if we use primitives
//than all fields will be stable
//Or use the WelcomeViewAlternative if you have minimum parameters from data class
data class User(
    val id: String,
    val email: String,
    val userName: String
)

fun ExternalUser.toUser(): User {
    return User(
        id = id,
        email = email,
        userName = userName
    )
}

fun User.toExternalUser(): ExternalUser {
    return ExternalUser(
        id = id,
        email = email,
        userName = userName
    )
}