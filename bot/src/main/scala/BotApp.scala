import canoe.api._
import canoe.models.Channel
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

	val testPost =
		"The gala concert of the Tallinn Talent music competition is open to the public \n\n" +
		"On Sunday 21 May at 2 pm, the festive closing concert of the Tallinn Talent 2023 youth music competition will " +
		"be held at the Estonia Concert Hall, where its initiator and patron Mayor Mihhail Kõlvart will acknowledge " +
		"the winners and present them with city scholarships. The free gala concert is open to the public.\n\n " +
		"16. May 2023 "

	override def run: IO[Unit] =
		Stream
			.resource(TelegramClient[IO](token))
			.flatMap(implicit client =>
				Stream
					.awakeEvery[IO](7.seconds)
					.evalMap(_ => chat.send(testPost))
			)
			.compile
			.drain
}