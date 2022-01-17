ThisBuild / scalaVersion := "2.13.8"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.8",
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-explaintypes",
    "-feature",
    "-unchecked",
    "-Ywarn-unused",
    "-Xlint",
  )
)

lazy val hello = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    scalaJSUseMainModuleInitializer := true,
  )

val awsSdkVersion = "2.892.0"
val awsSdkScalajsFacadeVersion = s"0.33.0-v${awsSdkVersion}"

lazy val lambda = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, UniversalPlugin)
  .settings(
    commonSettings,
    useYarn := true,
    webpack / version := "4.46.0",
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),
    scalaJSLinkerConfig ~= {
      _.withSourceMap(false)
    },
    libraryDependencies ++= Seq(
      "net.exoego" %%% "scala-js-nodejs-v14" % "0.14.0",
      "net.exoego" %%% "aws-lambda-scalajs-facade" % "0.11.0",
      "net.exoego" %%% "aws-sdk-scalajs-facade-dynamodb" % awsSdkScalajsFacadeVersion
    ),
    // aws-sdk is already provided on AWS
    // Compile / npmDependencies ++= Seq("aws-sdk" -> awsSdkVersion),
    Compile / npmDevDependencies ++= Seq("esbuild-loader" -> "2.18.0"),
    topLevelDirectory := None,
    maintainer := "None",
    Universal / mappings ++= (Compile / fullOptJS / webpack).value.map { f =>
      // remove the bundler suffix from the file names
      f.data -> f.data.getName.replace("-opt-bundle", "")
    }
  )

lazy val cdk = project
  .enablePlugins()
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.burkard" %% "aws-cdk-scala" % "0.9.1"
    )
  )
