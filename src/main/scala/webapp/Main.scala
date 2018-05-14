package webapp

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.{Canvas, Div, Image}

import scala.collection._
import ext.extensions._

import scala.scalajs.js


object Main {
   
   private val htmlElementsDims = mutable.Set[(Double,Double)=>Unit]()
   
   def main(args: Array[String]): Unit = {
      println("main inited")
      createOnResizeListener()
      document.body.style.backgroundColor="#101B29"
      val rootDiv = createRootDiv
      val image = createEarthImage
      val canvas = createCanvas
      
      rootDiv.appendChild(canvas)
      document.body.appendChild(rootDiv)
      drawImage(canvas,image)
   }
   
   def createRootDiv : Div = {
      val rootDiv = document.createElement("div").asInstanceOf[Div]
      rootDiv.align = "center"
      rootDiv.style.width="100%"
      followDimensions{ (_, h)=>
         rootDiv.style.minHeight = (h*0.98).px
      }
      rootDiv
   }
   
   def createEarthImage: Image = {
      
      val earthImage = document.createElement("img").asInstanceOf[Image]
      earthImage.style.display="none"
      earthImage.style.width = 90.ps
      earthImage.style.height = 80.ps
      earthImage.src="../img/worldIndiaLow.svg"
      earthImage
   }
   
   def createCanvas : Canvas = {
      
      val canvas = document.createElement("canvas").asInstanceOf[Canvas]
      canvas.style.width = 90.ps
      canvas.style.height = 80.ps
      canvas
   }
   
   def drawImage(canvas: Canvas,image: Image): Unit ={
   
      val context2D = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
      image.onload = (_) =>{
         context2D.drawImage(
            image,
            0.0,
            0.0,10,10
         )
      }
      
   }
   
   def followDimensions(function:(Double,Double) => Unit): Unit ={
      function(window.innerWidth,window.innerHeight)
      htmlElementsDims+=function
   }
   
   def createOnResizeListener(): Unit ={
      dom.window.onresize = (_)=>{
         htmlElementsDims.foreach(_(window.innerWidth,window.innerHeight))
         
      }
   }
   
}
