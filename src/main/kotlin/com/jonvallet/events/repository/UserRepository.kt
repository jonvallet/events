package com.jonvallet.events.repository

interface UserRepository {

 data class User(val userName: String, val email: String, val slackNotifications: Boolean = false)

 fun findByUsername(userName: String): User?
}
