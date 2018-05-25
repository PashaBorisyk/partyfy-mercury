package webapp.html

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas, Div, Element, Image}
import org.scalajs.dom.raw.{HTMLInputElement, HTMLLabelElement}
import org.scalajs.dom.{document, window}
import webapp.ext.extensions._

import scala.collection.mutable

object Creator {
   
   private final val htmlElementsDims = mutable.Set[(Double, Double) => Unit]()
   
   def createRootDiv(): Div = {
      val rootDiv = document.createElement("div").asInstanceOf[Div]
      rootDiv.align = "center"
      rootDiv.style.width = "100%"
      rootDiv.style.width = "relative"
      followDimensions { (_, h) =>
         rootDiv.style.minHeight = (h * 0.97).px
      }
      rootDiv
   }
   
   def createInputsDiv(width:Float=400)(inputs:Element*): Div = {
      
      val inputRootDiv = document.createElement("div").asInstanceOf[Div]
      inputRootDiv.style.width = width.px
      inputRootDiv.style.position = "absolute"
      
      inputs.foreach(el => inputRootDiv.appendChild(el))
      
      followDimensions((w,h)=>{
         println(inputRootDiv.clientHeight)
         inputRootDiv.style.right = ((w - width) / 2f).px
         inputRootDiv.style.top = ((h- inputRootDiv.clientHeight)/2f).px
      })
      
      inputRootDiv
   }
   
   def createEarthImage(): Image = {
      
      val image = document.createElement("img").asInstanceOf[Image]
      image.src = "../img/earth.svg"
      image.style.position = "absolute"
      image
   }
   
   def createCanvas(): Canvas = {
      val canvas = document.createElement("canvas").asInstanceOf[Canvas]
      canvas.style.position = "absolute"
      canvas
   }
   
   def followDimensions(function: (Double, Double) => Unit): Unit = {
      function(window.innerWidth, window.innerHeight)
      htmlElementsDims += function
   }
   
   def createInputText(labelText:String):Div = {
      
      val root = document.createElement("div").asInstanceOf[Div]
      root.classList.add("Wrapper")
      root.style.width = 100.ps
      
      val header = document.createElement("h1")
      header.classList.add("Title")
      root.appendChild(header)
      
      val inputRoot = document.createElement("div")
      root.appendChild(inputRoot)
      
      val input = document.createElement("input").asInstanceOf[HTMLInputElement]
      input.`type` = "text"
      input.id = "input"
      input.classList.add("Input-text")
      input.placeholder = labelText
      input.style.background = "rgba(255,255,255,0.9)"
      inputRoot.appendChild(input)
      
      val label = document.createElement("label").asInstanceOf[HTMLLabelElement]
      label.htmlFor = "Input-label"
      label.classList.add("Input-label")
      label.textContent = labelText
      inputRoot.appendChild(label)
      
      root
      
   }
   
   def drawCanvasUponImage(image: Image, canvas: Canvas): Unit = {
      image.onload = _ => {
         followDimensions((w, h) => {
            if (w >= h) {
               image.removeAttribute("width")
               image.height = h.toInt
            }
            else {
               image.removeAttribute("height")
               image.width = w.toInt
            }
            canvas.width = image.clientWidth
            canvas.height = image.clientHeight
            canvas.style.right = ((w - canvas.width) / 2f).px
            canvas.style.top = ((h - canvas.height) / 2f).px
            image.style.right = ((w - image.width) / 2f).px
            image.style.top = ((h - image.height) / 2f).px
            
         })
      }
   }
   
   dom.window.onresize = _ => {
      htmlElementsDims.foreach(_ (window.innerWidth, window.innerHeight))
   }
   document.body.onloadstart = _ =>{
      htmlElementsDims.foreach(_ (window.innerWidth, window.innerHeight))
   }
   
   private object Elements{
      
      final val inputText = """<div class="Wrapper">
                                <h1 class="Title">CSS Only Floated Labels!</h1>
                                <div class="Input">
                                  <input type="text" id="input" class="Input-text" placeholder="Enter your name!">
                                  <label for="input" class="Input-label">Name</label>
                                </div>
                              </div>"""
     
   }
   
}
