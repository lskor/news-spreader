import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.typelevel.log4cats.slf4j.Slf4jLogger

object ScraperApp extends IOApp {

	private val logger = Slf4jLogger.getLogger[IO]

	private var point = 10216L

	private val helloRoutes = HttpRoutes.of[IO] {

		case GET -> Root / "post" => {
			tryToFindPost.flatMap(post => Ok(post))
		}
	}

	private val httpApp = Seq(helloRoutes).reduce(_ <+> _).orNotFound

	override def run(args: List[String]): IO[ExitCode] =
		greeting *> EmberServerBuilder
			.default[IO]
			.withHost(ipv4"127.0.0.1")
			.withPort(port"9001")
			.withHttpApp(httpApp)
			.build
			.useForever

	private def greeting: IO[Unit] = logger.info(s"Running Scraper ...")

	private def tryToFindPost: IO[String] = for {
		_ <- logger.debug(s"Start trying to find new post [point=$point]")
		newPoint <- IO(point + 1)
		post <- getContent(newPoint)
		_ <- savePointIfPostExist(newPoint, post)
	} yield post

	private def getContent(from: Long): IO[String] =
		IO(NewsFormatter.get(GreenCity54.getNews(from)))

	private def savePointIfPostExist(newPoint: Long, post: String): IO[Unit] = post match {
		case post if post.nonEmpty => {
			point = newPoint
			logger.debug(s"New post was found [new point = $newPoint, post= ${post.substring(0, 32)}]")
		}
		case _ => logger.debug(s"New post was not found [point=$point]")
	}
}