package com.easychat.data

data class UserProfile(
    val uid: String,
    val email: String,
    val phone: String,
    val emailVerified: Boolean
)
