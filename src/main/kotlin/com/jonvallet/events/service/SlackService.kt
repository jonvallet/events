package com.jonvallet.events.service

interface SlackService {
    fun send(user: String, message: String)
}
