import sbt._
import Keys._

object JrubyScalaCollections extends Build {
  lazy val dist = TaskKey[Unit](
    "dist", "Copies files from target to dist."
  )
  lazy val distTask =
    dist <<= (
      name, scalaVersion, version, update, crossTarget,
      packageBin in Compile
    ).map {
      case (
        projectName, scalaVersion, projectVersion, updateReport, out,
        _
      ) =>
        val dist = (out / ".." / ".." / "dist").getAbsoluteFile
        // Clean up dist dir.
        IO.delete(dist)

        // Copy packaged jar.
        IO.copyFile(
          out / "%s_%s-%s.jar".format(
            projectName.toLowerCase, CrossVersion.binaryScalaVersion(scalaVersion), projectVersion
          ),
          dist / "collections.jar"
        )
    }

  val writeClasspath = TaskKey[Unit]("write-classpath")
  val writeClasspathTask = writeClasspath <<= (target, fullClasspath in Runtime) map { (target, cp) =>
    val f = (target / ".classpath").asFile
    IO.write(f, cp.map(_.data).mkString(":"))
  }

  lazy val spaceMule = Project(
    "jruby-scala-collections",
    file("."),
    /*_*/
    settings =
      Defaults.defaultSettings ++
      Seq(
        name                := "jruby-scala-collections",
        organization        := "com.tinylabproductions",
        version             := "1.0",
        scalaVersion        := "2.10.2",
        scalacOptions       := Seq("-deprecation", "-unchecked"),
        autoCompilerPlugins := true,
        resolvers           := Seq(
        ),
        libraryDependencies := Seq(
          "org.scala-lang" % "scala-library" % "2.10.2",
          "org.jruby" % "jruby-complete" % "1.7.4"
        ),
        distTask,
        writeClasspathTask
      )
    /*_*/
  )
}