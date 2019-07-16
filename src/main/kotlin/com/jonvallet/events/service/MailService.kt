package com.jonvallet.events.service

interface MailService {
    fun send(to: String, subject: String, body: String)
}
