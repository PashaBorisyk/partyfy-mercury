package webapp

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.{Canvas, Div, Image}
import webapp.ext.extensions._

import scala.collection._
import scala.util.Random

object Main {
   
   case class EventCircle(
                            var x: Double = 0.0,
                            var y: Double = 0.0,
                            var radius: Double = 0.0,
                            var stroke: Double = 0.0,
                         )
   
   object EventCircle {
      final val MAX_CIRCLE_RADIUS = 1f
      final val MAX_CIRCLE_STROKE = 3f
      final val RADIUS_DRAWING_TIME = 3000
      final val STROKE_COLOR = "#F05D5E"
   }
   
   private final val htmlElementsDims = mutable.Set[(Double, Double) => Unit]()
   private final val circleDrawingQueue = mutable.Set[EventCircle]()
   
   def main(args: Array[String]): Unit = {
      println("initializing...")
      document.body.style.backgroundColor = "#E7ECEF"
      
      createOnResizeListener()
      
      val rootDiv = createRootDiv
      val canvas = createCanvas
      val image = createEarthImage
      drawCanvasUponImage(image, canvas)
      createEventPoint(canvas, 100)
      initDrawing(canvas)
      rootDiv.appendChild(image)
      rootDiv.appendChild(canvas)
      document.body.appendChild(rootDiv)
      
      println("initializing finished!")
   }
   
   def createRootDiv: Div = {
      val rootDiv = document.createElement("div").asInstanceOf[Div]
      rootDiv.align = "center"
      rootDiv.style.width = "100%"
      rootDiv.style.width = "relative"
      followDimensions { (_, h) =>
         rootDiv.style.minHeight = (h * 0.98).px
      }
      rootDiv
   }
   
   def createEarthImage: Image = {
      
      val image = document.createElement("img").asInstanceOf[Image]
      image.src = "../img/earth.svg"
      image.style.position = "absolute"
      image
   }
   
   def createCanvas: Canvas = {
      val canvas = document.createElement("canvas").asInstanceOf[Canvas]
      canvas.style.position = "absolute"
      canvas
   }
   
   def followDimensions(function: (Double, Double) => Unit): Unit = {
      function(window.innerWidth, window.innerHeight)
      htmlElementsDims += function
   }
   
   def drawCanvasUponImage(image: Image, canvas: Canvas): Unit = {
      image.onload = _ => {
         followDimensions((w, h) => {
            if (w > h) {
               image.removeAttribute("width")
               image.height = (h * 0.9).toInt
            }
            else {
               image.removeAttribute("height")
               image.width = (w * 0.9).toInt
            }
            canvas.width = image.clientWidth
            canvas.height = image.clientHeight
            canvas.style.right = ((w - canvas.width) / 2f).px
            canvas.style.top = ((h - canvas.height) / 2f).px
            image.style.right = ((w - image.width) / 2f).px
            image.style.top = ((h - image.height) / 2f).px
            
            createEventPoint(canvas, 1000)
            
         })
      }
   }
   
   def initDrawing(canvas: Canvas): Unit = {
      
      val canvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
      
      var time = System.currentTimeMillis()
      var passed = 0L
      
      window.setInterval(() => {
         
         val delta = System.currentTimeMillis() - time
         passed += delta
         
         val animatedValue = (passed % EventCircle.RADIUS_DRAWING_TIME) / EventCircle.RADIUS_DRAWING_TIME
            .toDouble
         canvasRenderingContext2D.clearRect(0, 0, canvas.width, canvas.height)
         
         for (circle <- circleDrawingQueue) {
            canvasRenderingContext2D.beginPath()
            canvasRenderingContext2D.arc(
               circle.x,
               circle.y,
               ((circle.radius + animatedValue) * EventCircle.MAX_CIRCLE_RADIUS) % EventCircle.MAX_CIRCLE_RADIUS,
               0, Math.PI * 2, anticlockwise = false)
            canvasRenderingContext2D.lineWidth = ((circle.stroke + animatedValue) * EventCircle.MAX_CIRCLE_STROKE) %
               EventCircle.MAX_CIRCLE_STROKE
            canvasRenderingContext2D.stroke()
         }
            canvasRenderingContext2D.strokeStyle = EventCircle.STROKE_COLOR
         
         time = System.currentTimeMillis()
         
      }, 1000 / 60)
      
   }
   
   def createEventPoint(canvas: Canvas, count: Int): Unit = {
      
      circleDrawingQueue.clear()
      for (_ <- 0 to count) {
         
         val strokeRadiusBeginValue = Random.nextDouble()
         circleDrawingQueue += EventCircle(
            x = Random.nextDouble() * canvas.width,
            y = Random.nextDouble() * canvas.height,
            stroke = strokeRadiusBeginValue,
            radius = strokeRadiusBeginValue
         )
      }
      
   }
   
   def createOnResizeListener(): Unit = {
      dom.window.onresize = (_) => {
         htmlElementsDims.foreach(_ (window.innerWidth, window.innerHeight))
      }
   }
   
}
