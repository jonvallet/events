package com.jonvallet.events.subscribers

import com.jonvallet.events.repository.UserRepository
import com.jonvallet.events.service.Notification
import com.jonvallet.events.service.SlackService
import reactor.core.publisher.Flux

class NotificationSlackSubscriber(
        stream: Flux<Notification>,
        userRepository: UserRepository,
        slackService: SlackService) {
    init {
        stream
                .map { notification -> Pair(notification, userRepository.findByUsername(notification.user)) }
                .filter { pair -> pair.second != null }
                .filter { pair -> pair.second!!.slackNotifications }
                .map { pair -> slackService.send(pair.first.user, pair.first.message) }
                .subscribe()

        stream
                .filter { it.title == "Sold" }
                .map {
                    slackService.send(user = "rob@scibite.com", message = "${it.user} has sold!")
                }
                .subscribe()
    }
}