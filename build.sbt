name := "PSZT"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.1"

libraryDependencies += "io.reactivex" %% "rxscala" % "0.26.5"

val Http4sVersion = "0.17.0-M2"
//resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
  "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
  "ch.qos.logback" %  "logback-classic"     % "1.2.1",
  "org.http4s"     %% "http4s-circe"        % Http4sVersion
)

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  //  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)