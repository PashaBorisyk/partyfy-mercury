package webapp

import org.scalajs.dom._
import webapp.animator.PointsAnimator
import webapp.html._
import webapp.socket.SocketStreamConnection

object Main {


   def main(args: Array[String]): Unit = {
      println("initializing...")
      document.body.style.backgroundColor = "#E7ECEF"
      
      val rootDiv = Creator.createRootDiv()
      val canvas = Creator.createCanvas()
      val image = Creator.createEarthImage()
      val usernameInput = Creator.createInputText("Type username")
      val passwordInput = Creator.createInputText("Type password")
      
      val inputsDiv = Creator.createInputsDiv()(usernameInput,passwordInput)
      
      Creator.drawCanvasUponImage(image, canvas)
      
      val animator = PointsAnimator(canvas)
      
      animator.initDrawing()
      
      rootDiv.appendChild(image)
      rootDiv.appendChild(canvas)
      rootDiv.appendChild(inputsDiv)
      
      document.body.appendChild(rootDiv)
      
      SocketStreamConnection.addCallback(animator.createEventPoint)
      SocketStreamConnection.createSocketConnection()
      
      println("initializing finished!")
      
   }

}
