package db

import cats.effect.IO
import cats.implicits.catsSyntaxApply
import doobie.implicits._
import org.typelevel.log4cats.slf4j.Slf4jLogger

object PointService {

	private val logger = Slf4jLogger.getLogger[IO]

	def init: IO[Unit] = {
		val xa = DbTransactor.make[IO]
		(PointRepository.create *> PointRepository.default).attemptSql.transact(xa)
			.flatMap {
				case Left(error) => logger.error(s"Failed db initialization attempt[cause= $error]")
				case Right(_) => logger.info(s"Success db initialization")
			}
	}

	def get: IO[Long] = {
		val xa = DbTransactor.make[IO]
		PointRepository
			.get
			.transact(xa) // ToDo logger.debug(s"Current point was found [point=$res]")
	}

	def update(value: Long): IO[Unit] = {
		val xa = DbTransactor.make[IO]
		PointRepository
			.update(value)
			.transact(xa)
			.flatMap(_ => logger.debug(s"Point was updated [point=$value]"))
	}
}
