import io.burkard.cdk._
import io.burkard.cdk.services.lambda._
import io.burkard.cdk.services.apigateway._

object ExampleApp extends CdkApp {
  CdkStack(id = Some("ScalaStack")) { implicit stackCtx =>

    val hello = Function(
      internalResourceId = "HelloLambda",
      code = AssetCode(
        path = Assetslambda.assetPath
      ),
      handler = Assetslambda.Handlers.hello,
      runtime = Runtime("nodejs14.x", RuntimeFamily.Nodejs)
    )

    val api = LambdaRestApi("Api", handler = hello)
    CfnOutput("Url", value = api.getUrl)
  }
}