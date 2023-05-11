lazy val thisBuildSettings: Seq[Setting[_]] = inThisBuild(
  Seq(
    version := "1.0",
    scalaVersion := "2.13.10"
  )
)

lazy val fs2Version = "3.2.10"
lazy val catsCoreVersion = "2.7.0"
lazy val catsEffectVersion = "3.3.13"
lazy val canoeVersion = "0.6.0"
lazy val http4sVersion = "0.23.18"
lazy val log4CatsVersion = "2.5.0"

lazy val root = project
  .aggregate(
    bot,
    scraper,
  )
  .in(file("."))
  .settings(
    name := "post-spreader",
    thisBuildSettings
  )

lazy val bot = project
  .settings(
    libraryDependencies ++= Seq(
      "org.augustjune" %% "canoe" % canoeVersion,
      "co.fs2" %% "fs2-core" % fs2Version,
      "org.typelevel" %% "cats-core" % catsCoreVersion,
      "org.typelevel" %% "cats-effect" % catsEffectVersion
    )
  )

lazy val scraper = project
  .settings(libraryDependencies ++= Seq(
    "co.fs2" %% "fs2-core" % fs2Version,  // TODO  get rid of a double dependencies
    "org.typelevel" %% "cats-core" % catsCoreVersion,
    "org.typelevel" %% "cats-effect" % catsEffectVersion,

    "org.http4s" %% "http4s-dsl" % http4sVersion, // TODO  get rid of a unnecessary dependencies
    "org.http4s" %% "http4s-ember-server" % http4sVersion,
    "org.http4s" %% "http4s-ember-client" % http4sVersion,
    "org.http4s" %% "http4s-circe" % http4sVersion,
    "org.typelevel" %% "log4cats-slf4j" % log4CatsVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    )
  )