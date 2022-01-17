import io.burkard.cdk._
import io.burkard.cdk.services.lambda._

object ExampleApp extends CdkApp {
  CdkStack(id = Some("ScalaStack")) { implicit stackCtx =>

    Function(
      internalResourceId = "ScalaHandler",
      code = AssetCode(
        path = "lambda/target/universal/lambda-0.1.0-SNAPSHOT.zip"
      ),
      handler = "lambda.handler",
      runtime = Runtime("nodejs14.x", RuntimeFamily.Nodejs)
    )
  }
}