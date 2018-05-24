package webapp.animator

import org.scalajs.dom.html.Canvas
import org.scalajs.dom.{CanvasRenderingContext2D, window}
import webapp.model.{HipeEvent, HipeEventAnimation}

import scala.collection.mutable
import scala.util.Random

class PointsAnimator(val canvas: Canvas) {
   
   private final val circleDrawingQueue = mutable.Set[HipeEvent]()
   
   def initDrawing(): Unit = {
      
      val canvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
      
      var time = System.currentTimeMillis()
      var passed = 0L
      
      window.setInterval(() => {
         
         val delta = System.currentTimeMillis() - time
         passed += delta
         
         val animatedValue = (passed % HipeEventAnimation.RADIUS_DRAWING_TIME) / HipeEventAnimation.RADIUS_DRAWING_TIME.toDouble
         canvasRenderingContext2D.clearRect(0, 0, canvas.width, canvas.height)
         
         circleDrawingQueue.foreach(circle => {
            
            val currentAnimatedValue = circle.value + animatedValue
            canvasRenderingContext2D.beginPath()
            canvasRenderingContext2D.arc(
               circle.x*canvas.width,
               circle.y*canvas.height,
               (currentAnimatedValue * HipeEventAnimation.MAX_CIRCLE_RADIUS) % HipeEventAnimation.MAX_CIRCLE_RADIUS,
               0, Math.PI * 2)
            canvasRenderingContext2D.lineWidth = (currentAnimatedValue * HipeEventAnimation.MAX_CIRCLE_STROKE) % HipeEventAnimation.MAX_CIRCLE_STROKE
            canvasRenderingContext2D.stroke()
         })
         
         canvasRenderingContext2D.strokeStyle = HipeEventAnimation.STROKE_COLOR
         
         time = System.currentTimeMillis()
         
      }, 1000 / 60)
      
   }
   
   def createEventPoint(event: HipeEvent = null, count: Int = 1): Unit = {
      
      if (count > 0) {
         circleDrawingQueue.clear()
         (0 to count).foreach(_ => {
            val strokeRadiusBeginValue = Random.nextDouble()
            circleDrawingQueue += HipeEvent(
               x = Random.nextDouble() * canvas.width,
               y = Random.nextDouble() * canvas.height,
               value = strokeRadiusBeginValue
            )
         })
      }
      else
         circleDrawingQueue += event
      
   }
   
   def createEventPoint(event: HipeEvent): Unit = createEventPoint(event, 0)
   
}

object PointsAnimator {
   def apply(canvas: Canvas): PointsAnimator = new PointsAnimator(canvas)
}
