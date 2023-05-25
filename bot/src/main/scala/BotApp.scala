import canoe.api.{TelegramClient, chatApi}
import canoe.models.Channel
import canoe.syntax._
import cats.effect.{IO, IOApp}
import fs2.Stream
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.typelevel.log4cats.slf4j.Slf4jLogger

import scala.concurrent.duration._

object BotApp extends IOApp.Simple {

	private val logger = Slf4jLogger.getLogger[IO]

	val token: String = "token"

	private val url = "http://localhost:9001/post"

	private val chat = Channel(
		id = -1001950191075L,
		title = Some("Tallinn News"),
		username = Some("tallinn_newsss"))

	private val applicationResources = for {
		client <- EmberClientBuilder
					.default[IO]
					.build
		bot <- TelegramClient[IO](token)
	} yield (client, bot)

	override def run: IO[Unit] =
		logger.info(s"Running Bot Service...") *>
			applicationResources
				.use { case (client, bot) =>
					implicit val b = bot
					Stream
						.awakeEvery[IO](7.seconds)
						.evalMap(_ => follow(client))
						.compile
						.drain
				}

	private def follow(client: Client[IO])(implicit tc: TelegramClient[IO]): IO[Unit] = for {
			_ <- logger.debug("Trying to find new post... ")
			_ <- client
					.expect[String](url)
					.flatMap(sendPost)
					.handleErrorWith(err => logger.error(s"Got an error from service $err"))
		} yield ()

	private def sendPost(post: String)(implicit tc: TelegramClient[IO]): IO[Unit] = post match {
		case post if post.nonEmpty => for {
			_ <- logger.info(s"New post was found [post=$post]")
			_ <- chat.send(post.markdownOld)
		} yield ()
		case _ => logger.debug(s"New post was not found")
	}
}