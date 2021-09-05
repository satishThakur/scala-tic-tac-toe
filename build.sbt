val scala3Version = "3.0.1"
val zioVersion   = "2.0.0-M2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "tictactoe-scala3-zio",
    version := "1.0.0",

    scalaVersion := scala3Version,

    //ZIO dependency
    libraryDependencies += ("dev.zio" %% "zio" % zioVersion).cross(CrossVersion.for3Use2_13),
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4" % "test",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"

  )
