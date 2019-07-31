package com.jonvallet.events.service

import com.jonvallet.events.repository.NotificationRepository
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux

data class Notification(val user: String, val message: String, val title: String = "Title")

class NotificationService(private val notificationRepository: NotificationRepository) {
    private val processor: DirectProcessor<Notification> = DirectProcessor.create()

    fun sendNotification(notification: Notification) {
        notificationRepository.save(notification)
        //Emit new notification
        processor.sink().next(notification)
    }

    fun notificationsStream(): Flux<Notification> {
        return processor
    }
}