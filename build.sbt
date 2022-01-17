val scala3 = "3.1.0"

ThisBuild / scalaVersion := scala3

lazy val commonSettings = Seq(
  scalaVersion := scala3,
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked"
  )
)

lazy val hello = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    scalaJSUseMainModuleInitializer := true
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
      ("net.exoego" %%% "scala-js-nodejs-v14" % "0.14.0")
        .cross(CrossVersion.for3Use2_13),
      ("net.exoego" %%% "aws-lambda-scalajs-facade" % "0.11.0")
        .cross(CrossVersion.for3Use2_13),
      ("net.exoego" %%% "aws-sdk-scalajs-facade-dynamodb" % awsSdkScalajsFacadeVersion)
        .cross(CrossVersion.for3Use2_13),
      "org.scalameta" %%% "munit" % "1.0.0-M1" % Test
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
