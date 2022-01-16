import net.exoego.facade.aws_lambda._

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.Promise
import scala.scalajs.js.annotation.JSExportTopLevel

object Handler {
  @JSExportTopLevel(name = "handler")
  def handleRequest(request: APIGatewayProxyEventV2, context: Context): Promise[APIGatewayProxyResult] = {
    println(js.JSON.stringify(request))
    Future(APIGatewayProxyResult(body = s"hello ${request.body}", statusCode = 200)).toJSPromise
  }
}