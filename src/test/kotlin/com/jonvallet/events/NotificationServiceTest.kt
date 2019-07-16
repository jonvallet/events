package com.jonvallet.events

import com.jonvallet.events.repository.NotificationRepository
import com.jonvallet.events.repository.UserRepository
import com.jonvallet.events.repository.UserRepository.User
import com.jonvallet.events.service.MailService
import com.jonvallet.events.service.Notification
import com.jonvallet.events.service.NotificationService
import com.jonvallet.events.service.SlackService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotificationServiceTest {

    @Mock
    private lateinit var notificationRepository: NotificationRepository
    @Mock
    private lateinit var mailService: MailService
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var slackService: SlackService
    private lateinit var notificationService: NotificationService;

    @Before
    fun setUp() {
        notificationService = NotificationService(
                notificationRepository = notificationRepository,
                mailService = mailService,
                userRepository = userRepository,
                slackService = slackService)
    }

    @Test
    fun sendNotificationShouldSaveIntoTheRepository() {
        val notification = Notification(user = "user", message = "message")

        notificationService.sendNotification(notification)

        verify(notificationRepository).save(notification)
    }

    @Test
    fun sendNotificationToAdminShouldSendAnEmail() {
        val notification = Notification(user = "admin", message = "message", title = "Subject")
        val to = "admin@admin.com"
        `when`(userRepository.findByUsername(notification.user)).thenReturn(User(userName = notification.user, email = to))

        notificationService.sendNotification(notification)

        verify(mailService).send(to = to, subject = notification.title, body = notification.message)
    }

    @Test
    fun sendNotificationWithSlackEnableShouldSendASlackMessage() {
        val notification = Notification(user = "user", message = "message")
        `when`(userRepository.findByUsername(notification.user))
                .thenReturn(User(userName = notification.user, email = "user@admin.com", slackNotifications = true))

        notificationService.sendNotification(notification)

        verify(slackService).send(user = notification.user, message = notification.message)
    }
}