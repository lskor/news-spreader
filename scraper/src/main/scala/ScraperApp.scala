import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server._
import org.http4s.implicits._

object ScraperApp extends IOApp {

	private val helloRoutes = HttpRoutes.of[IO] {

		// curl "http://localhost:9001/hello/world"
		case GET -> Root / "hello" / name =>
			Ok(s"Hello, $name!")

		// curl -X POST "http://localhost:9001/hello" -d "world"
		case req@POST -> Root / "hello" =>
			Ok(req.as[String].map(name => s"Hello again, $name!"))
	}

	private val httpApp = Seq(helloRoutes).reduce(_ <+> _).orNotFound

	override def run(args: List[String]): IO[ExitCode] =
		EmberServerBuilder
			.default[IO]
			.withHost(ipv4"127.0.0.1")
			.withPort(port"9001")
			.withHttpApp(httpApp)
			.build
			.useForever
}
