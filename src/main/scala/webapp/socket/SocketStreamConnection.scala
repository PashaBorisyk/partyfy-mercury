package webapp.socket

import org.scalajs.dom._
import org.scalajs.dom.html.Canvas
import webapp.model.EventCircle


object SocketStreamConnection {
   
   private final val SERVICE_ADDRES = "ws://localhost:8081/"
   
   var callback: (Canvas, EventCircle) => Unit = _
   
   def createSocketConnection(): Unit = {
   
      println("Creating socket connection...")
      val socket = new WebSocket(SERVICE_ADDRES)
      
      socket.onopen = _ => {
         println("Connection successfully opened!")
         socket.send("Handshake")
      }
      
      socket.onmessage = messageEvent => {
         println(messageEvent.data + " received")
      }
      
      socket.onclose = e =>{
         println(s"Connection closed by remote")
      }
      
      socket.onerror = e => {
         println(s"Connection error $e")
      }
   
   }
   
}

