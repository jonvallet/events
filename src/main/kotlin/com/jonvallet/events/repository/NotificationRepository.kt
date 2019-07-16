package com.jonvallet.events.repository

import com.jonvallet.events.service.Notification

interface NotificationRepository {
    fun save(notification: Notification)
    fun findAllByUser(user: String): List<Notification>
}