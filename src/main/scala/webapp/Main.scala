package webapp

import org.scalajs.dom
import org.scalajs.dom._
import org.scalajs.dom.html.Div

import scala.collection._
import ext.extensions._


object Main {
   
   private val htmlElementsDims = mutable.Set[(Any,Any)=>Unit]()
   
   def main(args: Array[String]): Unit = {
      println("main inited")
      createOnResizeListener()
      document.body.appendChild(createRootDiv)
   }
   
   def createRootDiv : Div = {
      val rootDiv = document.createElement("div").asInstanceOf[Div]
      rootDiv.align = "center"
      rootDiv.style.width="100%"
      followDimensions{ (_, h)=>
         rootDiv.style.height = h.px
      }
      rootDiv
   }
   
   def followDimensions(function:(Any,Any) => Unit): Unit ={
      function(window.innerWidth,window.innerHeight)
      htmlElementsDims+=function
   }
   
   def createOnResizeListener(): Unit ={
      dom.window.onresize = (_)=>{
         htmlElementsDims.foreach(_(window.innerWidth,window.innerHeight))
         
      }
   }
   
}
