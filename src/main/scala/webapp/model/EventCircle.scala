package webapp.model


case class EventCircle(
                         x: Double = 0.0,
                         y: Double = 0.0,
                         value: Double = 0.0
                      )

object EventCircle {
   
   final val MAX_CIRCLE_RADIUS = 1f
   final val MAX_CIRCLE_STROKE = 3f
   final val RADIUS_DRAWING_TIME = 5000
   final val STROKE_COLOR = "#F05D5E"
   
}