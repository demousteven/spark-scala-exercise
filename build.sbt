ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "steven_project_twitter_streaming"
  )
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.2" % "provided"
libraryDependencies += "com.danielasfregola" %% "twitter4s" % "7.0"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.2"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.2.2"

libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.8"




