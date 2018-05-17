package webapp.socket

import org.scalajs.dom.html.Canvas
import org.scalajs.dom._

import scala.scalajs.js.JSON


case class EventCircle(
                         x: Double = 0.0,
                         y: Double = 0.0,
                         radius: Double = 0.0,
                         stroke: Double = 0.0,
                      )

object EventCircle {

   final val MAX_CIRCLE_RADIUS = 1f
   final val MAX_CIRCLE_STROKE = 3f
   final val RADIUS_DRAWING_TIME = 3000
   final val STROKE_COLOR = "#F05D5E"

}

object SocketStreamConnection {

   final val SERVICE_ADDRES = ""

   val callback: (Canvas, EventCircle) => Unit = null

   val socket = new WebSocket(SocketStreamConnection.SERVICE_ADDRES)

   socket.onopen = _ =>{
      println("Socket successfully opened!")
   }

   socket.onmessage = messageEvent =>{

      val pointJson = String.valueOf(messageEvent.data)

   }

   socket.onerror = _ =>{
      println("Socket closed with error!")
   }

}

