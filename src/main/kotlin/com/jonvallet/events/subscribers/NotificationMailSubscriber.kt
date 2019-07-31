package com.jonvallet.events.subscribers

import com.jonvallet.events.repository.UserRepository
import com.jonvallet.events.service.MailService
import com.jonvallet.events.service.Notification
import reactor.core.publisher.Flux

class NotificationMailSubscriber(stream: Flux<Notification>,
                                 mailService: MailService,
                                 userRepository: UserRepository) {
    init {
        stream.subscribe {
            notification ->
            val user = userRepository.findByUsername(notification.user)
            if(notification.user == "admin" && user != null ) {
                mailService.send(to = user.email, subject = notification.title, body = "message")
            }
        }
    }
}
