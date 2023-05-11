import canoe.api._
import canoe.syntax._
import cats.effect.{IO, IOApp}
import cats.syntax.all._
import fs2.Stream

object BotApp extends IOApp.Simple {

  val token: String = "token"

  def run: IO[Unit] =
    Stream
      .resource(TelegramClient[IO](token))
      .flatMap(implicit client => Bot.polling[IO].follow(greetings))
      .compile
      .drain

  def greetings[F[_] : TelegramClient]: Scenario[F, Unit] =
    for {
      chat <- Scenario.expect(command("hi").chat)
      _ <- Scenario.eval(chat.send("Hello. What's your name?"))
      name <- Scenario.expect(text)
      _ <- Scenario.eval(chat.send(s"Nice to meet you, $name"))
    } yield ()
}