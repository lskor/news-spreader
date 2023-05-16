import canoe.api._
import canoe.syntax._
import cats.effect.{IO, IOApp}
import cats.syntax.all._
import fs2.Stream

object BotApp extends IOApp.Simple {

	val token: String = "token"

	override def run: IO[Unit] =
		Stream
			.resource(TelegramClient[IO](token))
			.flatMap(implicit client => Bot.polling[IO].follow(greetings))
			.compile
			.drain

	def greetings[F[_] : TelegramClient]: Scenario[F, Unit] = {

		val post: String = "Вниманию жителей дома по ул. Лескова, 23! " +
			"Отключение горячей воды в связи с проведением работ по подготовке дома к отопительному сезону " +
			"15 мая 2023"

		for {
			chat <- Scenario.expect(command("start").chat)
			_ <- Scenario.eval(chat.send(post))
		} yield ()
	}
}