# Event Driven Programming
A small example of how to refactor a code base that is written on an imperitive style to reactive, 
using the event driven paradigm.

## Bulding the project
You need to have at least java 8 installed.
Clone the repository and build it using the gradle wrapper.

```bash
git clone git@github.com:jonvallet/events.git
cd events
bash gradlew
```

## The challenge
[NotificationService](src/main/kotlin/com/jonvallet/events/service/NotificationService.kt) has grown since it was first wrote
and now there as quite a lot going on everytime that a new notification is pushed.
There is now a new `fun notificationsStream(): Flux<Notification>` that gives you a stream of notifications. Making use of it
you can now decouple code from the NotificationService.

### MailService decoupling
Create a new `MailServiceNotificationConsumer` that has a dependency to a notification stream 
(`notificationsStream: Flux<Notification`)

```kotlin
class NotificationMailSubscriber(stream: Flux<Notification>) {
    init {
        TODO("Work in progress.")
    }
}
```

Now move all the code that deals with sending emails when a notification is pushed to the init block. You may need to add
more dependencies to `NotificationMailSubscriber`, like the `MailService`.
Once you are happy, run the unit tests (`bash gradlew`). What happens? Without changing the unit tests and 
just changing the setUp of [NotificationServiceTest](src/test/kotlin/com/jonvallet/events/NotificationServiceTest.kt),
are you able to make the unit test pass?

**Bonus:** Can you remove all the if statements making use of `filter` and `map` transformations?

### Slack Service decoupling
Now do the same for the code that deals with slack notifications

### Adding a new requirement
There is a new requirement that we need to implement. Everytime a notification is pushed that has the title value of `Sold`,
we want to send and email to rob@sciencefy.com with the good news.

Start writing the unit test then make the it pass adding the new behaviour.

