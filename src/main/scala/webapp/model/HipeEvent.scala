package webapp.model


case class HipeEvent(    nickname:String ="",
                         photoUrl:String="",
                         text:String ="",
                         x: Double = 0.0,
                         y: Double = 0.0,
                         value: Double = 0.0
                      )

object HipeEventAnimation {
   
   final val MAX_CIRCLE_RADIUS = 1f
   final val MAX_CIRCLE_STROKE = 3f
   final val RADIUS_DRAWING_TIME = 5000
   final val STROKE_COLOR = "#F05D5E"
   
}