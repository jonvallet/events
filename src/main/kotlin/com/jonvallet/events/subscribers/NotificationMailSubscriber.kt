package com.jonvallet.events.subscribers

import com.jonvallet.events.repository.UserRepository
import com.jonvallet.events.service.MailService
import com.jonvallet.events.service.Notification
import reactor.core.publisher.Flux

class NotificationMailSubscriber(stream: Flux<Notification>,
                                 mailService: MailService,
                                 userRepository: UserRepository) {
    init {
        stream
                .filter { notification -> notification.user == "admin" }
                .map { notification -> Pair(userRepository.findByUsername(notification.user), notification) }
                .filter { pair -> pair.first != null }
                .map { pair ->
                    mailService.send(to = pair.first!!.email, subject = pair.second.title, body = "message")
                }
                .subscribe()
    }
}
