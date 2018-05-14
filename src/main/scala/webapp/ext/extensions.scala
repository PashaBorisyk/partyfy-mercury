package webapp.ext

object extensions {
   
   implicit class _ext(val any: Any){
      
      def px:String = String.valueOf(any) + "px"
      def ps:String = String.valueOf(any) + "%"
      def em:String = String.valueOf(any) + "em"
      
   }
}
