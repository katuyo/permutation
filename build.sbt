scalaVersion := "2.12.8"

organization := "org.featx.katuyo"
name := """permutation"""
version := "1.0-SNAPSHOT"

maintainer := "Excets <excepts@featx.org>"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

enablePlugins(DockerPlugin)
enablePlugins(DockerSpotifyClientPlugin)

libraryDependencies += guice

//Docker
import com.typesafe.sbt.packager.docker._
packageName in Docker := "featx/" + name
version in Docker := version.value
dockerExposedPorts ++= Seq(9000, 9443)
dockerRepository := Some("registry.cn-shanghai.aliyuncs.com")


dockerCommands := Seq(
  Cmd("FROM", "openjdk:8u212-jre-alpine"),
  Cmd("LABEL", s"""MAINTAINER="${maintainer.value}""""),
  Cmd("COPY", "opt/docker /usr/local/permutation"),
  Cmd("RUN", s"""apk add -U bash && \\
    addgroup -g 1000 featx && \\
    adduser -G featx -u 1000 -s /sbin/nologin -D -H featx && \\
    chown -R featx:featx /usr/local/permutation
  """),
  Cmd("WORKDIR", "/usr/local/permutation"),
  Cmd("EXPOSE", "9000", "9443"),
  Cmd("USER", "1000"),
  ExecCmd("ENTRYPOINT", "/usr/local/permutation/bin/permutation"),
  ExecCmd("CMD", "")
)