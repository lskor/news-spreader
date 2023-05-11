Post Spreader
=============

### The problem
A long time ago I had some web-site with news and a telegram channel with absolutely the same news. I should have posted a new post to the site and telegram every day. This was like that because a bunch of my users don't have Telegram, so they would rather read news using a web-page, despite the fact that they lose the opportunity to get news immediately after posting.
Nowadays, I see many news portals which at the same time have a telegram channel too and I'm sure they made posts to the channel manually. Probably special employees do that every day, like I did.

### First step
At first, I needed a service (some REST API with http4s) which constantly monitors the web-resource with news. It should find a new page, scrape it and put it in the database, for example. I would like to put news to the base because there is an important resource for me, which loves to change existing news but I would like to post those changes as new news to the telegram channel. And second reason: working with Databases from Scala (Doobie). However, that makes it possible to add another useful functionality.
And second, I need a bot  which can use api-service and post news when they appear in the database. Here I need to consider how it will be. For the first step I can develop a bot for a particular news resource, which you should add to your channel and after that it will post news there.

### Stack

* sbt
* cats
* cats-effect
* fs2
* http4s
* Doobie
* circe

???
* akka
* canoe