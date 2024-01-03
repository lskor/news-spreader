Tallinn News Spreader
=============
This project implements opportunity to send new news from web-site to the telegram channel. Now the project sent post from https://lskor.github.io/ to the [Tallinn News](https://t.me/tallinn_newsss)

## The problem
I had dealt with some web-site with news. It was simple and old site this news which was important like power outage or elevator repair time. This site didn't have any notification. And owner didn't care how he informed people about important information. People didn't have opportunity get info as fast as they can.

Nowadays, I see many news portals which at the same time have a telegram channel, and I'm sure they made posts to the channel manually. Probably special employees do that every day.

## What's inside
The project has two main parts. The first one - scraper service with only one endpoint _getPost_. This is http4s server. Then the endpoint is called, service goes to the web-site and tries to find latest news. It uses scraper (Jsoup) and parses HTML of this page. Finds header, body of news, and date. Service formats this message into a bot-friendly format, and as result returns string.

The second one - bot service. Every duration time it calls scraper service endpoint _getPost_ and receives empty or non empty string. Non empty string is sent to custom telegram channel the Tallinn News like string with markdown.

## How it's look like

![Tallinn News](https://github.com/lskor/post-spreader/blob/master/img/tallinn_news.png?raw=true)

## Stack

* sbt
* cats
* cats-effect
* fs2
* http4s
* Doobie
* canoe
* jsoup
