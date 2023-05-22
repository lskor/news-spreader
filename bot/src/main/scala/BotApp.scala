import canoe.api.{TelegramClient, chatApi}
import canoe.models.Channel
import canoe.models.messages.TextMessage
import canoe.syntax._
import cats.effect.{IO, IOApp}
import fs2.Stream
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.typelevel.log4cats.slf4j.Slf4jLogger

import scala.concurrent.duration._

object BotApp extends IOApp.Simple {

	val logger = Slf4jLogger.getLogger[IO]

	val token: String = "token"

	val chat = Channel(
		id = -1001795303736L,
		title = Some("Important News"),
		username = Some("really_important_news"))

	override def run: IO[Unit] =
		EmberClientBuilder
			.default[IO]
			.build
			.use { client =>
				Stream
					.resource(TelegramClient[IO](token))
					.flatMap(implicit bot =>
						Stream
							.awakeEvery[IO](7.seconds)
							.evalMap(_ => tryToFindPost(client))
					)
					.compile
					.drain
			}

	private def tryToFindPost[F[_] : TelegramClient](client: Client[IO]): F[TextMessage] =
		client
			.expect[String]("http://localhost:9001/post")
			.map(post => for {
				_ <- logger.debug(s"New post was found [post=${post}]")
				_ <- chat.send(post.markdownOld)
				} yield ()
			)
}