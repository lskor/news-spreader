package db

import cats.implicits.catsSyntaxApply
import doobie._
import doobie.implicits._
object PointRepository {

	val create: ConnectionIO[Int] =
		sql"CREATE TABLE IF NOT EXISTS point (id integer NOT NULL DEFAULT 0, value BIGINT NOT NULL, CONSTRAINT point_pkey PRIMARY KEY (id))"
			.update
			.run

	val default: ConnectionIO[Int] = sql"INSERT INTO point (id, value) VALUES (0, 10209) ON CONFLICT DO NOTHING"
		.update
		.run

	val get: ConnectionIO[Long] = sql"SELECT value FROM point WHERE id = 0"
		.query[Long]
		.unique

	def update(value: Long): ConnectionIO[Int] = sql"UPDATE point SET value = $value WHERE id = 0"
		.update
		.run
}