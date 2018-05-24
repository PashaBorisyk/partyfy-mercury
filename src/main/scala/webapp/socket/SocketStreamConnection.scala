package webapp.socket

import org.scalajs.dom._
import upickle.default.{macroRW, ReadWriter => RW, _}
import webapp.model.HipeEvent

import scala.collection.mutable


object SocketStreamConnection {
   private type Callback = (HipeEvent) => Unit
   
   private implicit def rw: RW[HipeEvent] = macroRW
   
   private final val SERVICE_ADDRESS = "ws://localhost:8081/"
   
   private val callbacks = mutable.Set[Callback]()
   
   def createSocketConnection(): Unit = {
      
      println("Creating socket connection...")
      val socket = new WebSocket(SERVICE_ADDRESS)
      
      socket.onopen = _ => {
         println("Connection successfully opened")
         socket.send("Handshake")
      }
      
      socket.onmessage = messageEvent => {
         callbacks.foreach(_(read[HipeEvent](messageEvent.data.toString)))
      }
      
      socket.onclose = e => {
         println(s"Connection closed by remote $e")
      }
      
      socket.onerror = e => {
         println(s"Connection error $e")
      }
      
   }
   
   def addCallback(callback: Callback): Unit = callbacks += callback
   
   def removeCallback(callback: Callback): Unit = callbacks -= callback
   
}

