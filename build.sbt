ThisBuild / organization := "org.shishkin"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "3.1.0"

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked"
  )
)

lazy val root = project.in(file("."))

lazy val hello = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    scalaJSUseMainModuleInitializer := true
  )

val awsSdkVersion = "2.892.0"
val awsSdkScalajsFacadeVersion = s"0.33.0-v${awsSdkVersion}"

lazy val lambda = project
  .enablePlugins(ScalaJSPlugin, UniversalPlugin)
  .settings(
    commonSettings,
    scalaJSLinkerConfig ~= {
      _.withSourceMap(false)
        .withModuleKind(ModuleKind.CommonJSModule)
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
    topLevelDirectory := None,
    maintainer := "None",
    Universal / mappings ++= (Compile / fullLinkJS).value.data.publicModules.toSeq
      .map { m =>
        (Compile / fullLinkJS / scalaJSLinkerOutputDirectory).value / m.jsFileName -> m.jsFileName
      }
  )

// TODO: Find a way to pass multiple project references (https://github.com/sbt/sbt/issues/6792)
def generateLambdaAssets(proj: ProjectReference, root: ProjectReference) =
  Def.task {
    val name = (proj / moduleName).value
    val output = (Compile / sourceManaged).value / s"Assets${name}.scala"
    val zip = (proj / Universal / packageBin).value
      .relativeTo((root / baseDirectory).value)
      .get
      .getPath
    val handlers = (proj / Compile / fullLinkJS).value.data.publicModules.toSeq
      .map(_.moduleID)
      .map(m => s"""    val ${m} = "${m}.handler"""")
    val source =
      s"""
       |object ${output.base} {
       |  val assetPath = "${zip}"
       |  object Handlers {
       |${handlers.mkString("\n")}
       |  }
       |}
       |""".stripMargin
    IO.write(output, source)
    Seq(output)
  }

lazy val cdk = project
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      "io.burkard" %% "aws-cdk-scala" % "0.9.1"
    ),
    Compile / sourceGenerators += generateLambdaAssets(
      lambda,
      root = root
    ).taskValue
  )
