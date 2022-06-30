name := """the-freshly-store"""
organization := "com.thefreshlystore"

version := "1.0-SNAPSHOT"

import play.ebean.sbt.PlayEbean

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "mysql" % "mysql-connector-java" % "8.0.11",
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.amazonaws" % "aws-java-sdk" % "1.11.213",
  "commons-codec" % "commons-codec" % "1.10",
  "com.razorpay" % "razorpay-java" % "1.3.1",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.9.5",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.5",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.5",
  "com.google.inject" % "guice" % "4.2.0",
  guice,
  "org.json" % "json" % "20180130",
   "nl.martijndwars" % "web-push" % "3.1.0",
   "javax.persistence" % "javax.persistence-api" % "2.2",
   "com.mashape.unirest" % "unirest-java" % "1.4.9",
   "software.amazon.awssdk" % "sqs" %"2.17.220",
   "software.amazon.awssdk" % "regions" %"2.17.220"
)