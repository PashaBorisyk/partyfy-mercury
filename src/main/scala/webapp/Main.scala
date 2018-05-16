package webapp

import com.sun.org.apache.xerces.internal.xni.parser.XMLPullParserConfiguration
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
      document.body.style.backgroundColor="#101B29"
      
      createOnResizeListener()
      
      val rootDiv = createRootDiv
      val canvas = createCanvas
      val image = createEarthImage
      
      drawImageOnCanvas(image,canvas)
      
      rootDiv.appendChild(image)
      rootDiv.appendChild(canvas)
      document.body.appendChild(rootDiv)
      
   }
   
   def createRootDiv : Div = {
      val rootDiv = document.createElement("div").asInstanceOf[Div]
      rootDiv.align = "center"
      rootDiv.style.width="100%"
      rootDiv.style.width="relative"
      followDimensions{ (_, h)=>
         rootDiv.style.minHeight = (h*0.98).px
      }
      rootDiv
   }
   
   def createEarthImage: Image = {
      
      val image = document.createElement("img").asInstanceOf[Image]
      image.src="../img/earth.svg"
      image.style.position = "absolute"
      image
   }
   
   def createCanvas : Canvas = {
      val canvas = document.createElement("canvas").asInstanceOf[Canvas]
      canvas.style.position = "absolute"
      canvas
   }
   
   def followDimensions(function:(Double,Double) => Unit): Unit ={
      function(window.innerWidth,window.innerHeight)
      htmlElementsDims+=function
   }
   
   def drawImageOnCanvas(image: Image,canvas: Canvas): Unit ={
      image.onload =  _ =>{
         followDimensions((w,h) =>{
            if(w > h) {
               image.attributes.removeNamedItem("width")
               image.height = (h * 0.8).toInt
            }
            else {
               image.attributes.removeNamedItem("height")
               image.width = (w * 0.8).toInt
            }
            canvas.width = image.clientWidth
            canvas.height = image.clientHeight
            canvas.style.right = ((w - canvas.width)/ 2f).px
            canvas.style.top = ((h - canvas.height) /2f).px
            image.style.right = ((w - image.width)/ 2f).px
            image.style.top = ((h - image.height) /2f).px
   
         })
      }
   }
   
   def createOnResizeListener(): Unit ={
      dom.window.onresize = (_)=>{
         htmlElementsDims.foreach(_(window.innerWidth,window.innerHeight))
         
      }
   }
   
}
