import net.exoego.facade.aws_lambda.*

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.*

object HelloLambda {

  @JSExportTopLevel(moduleID = "hello", name = "handler")
  def handler(
      request: APIGatewayProxyEventV2,
      context: Context
  ): Promise[APIGatewayProxyResult] = {
    println(js.JSON.stringify(request))
    Future(
      APIGatewayProxyResult(body = s"hello ${request.body}", statusCode = 200)
    ).toJSPromise
  }
}
