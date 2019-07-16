package com.jonvallet.events.service

import com.jonvallet.events.repository.NotificationRepository
import com.jonvallet.events.repository.UserRepository

data class Notification(val user: String, val message: String, val title: String = "Title")

class NotificationService(
        private val notificationRepository: NotificationRepository,
        private val mailService: MailService,
        private val userRepository: UserRepository,
        private val slackService: SlackService
){
    fun sendNotification (notification: Notification) {
        notificationRepository.save(notification)
        val user = userRepository.findByUsername(notification.user)
        if(notification.user == "admin" && user != null ) {
            mailService.send(to = user.email, subject = notification.title, body = "message")
        }
        if(user != null && user.slackNotifications) {
            slackService.send(user = notification.user, message = notification.message)
        }
    }
}