import canoe.api._
import canoe.models.Channel
import canoe.models.messages.TextMessage
import canoe.syntax._
import cats.effect.{IO, IOApp}
import fs2.Stream

import scala.concurrent.duration._

object BotApp extends IOApp.Simple {

	val token: String = "token"

	val chat = Channel(
		id = -1001795303736L,
		title = Some("Important News"),
		username = Some("really_important_news"))

	val checkPoint = 10216L

	override def run: IO[Unit] =
		Stream
			.resource(TelegramClient[IO](token))
			.flatMap(implicit client =>
				Stream
					.awakeEvery[IO](7.seconds)
					.evalMap(_ => tryToFindPost)
			)
			.compile
			.drain

	private def tryToFindPost[F[_] : TelegramClient]: F[TextMessage] =
		chat.send(getContent.markdownOld)

	private def getContent: String =
		NewsFormatter.get(GreenCity54.getNews(checkPoint))
}