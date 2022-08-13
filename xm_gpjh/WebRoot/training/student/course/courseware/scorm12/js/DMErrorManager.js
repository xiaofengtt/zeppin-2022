/**
	DMErrorManager
*/
function DMErrorManager() {
	this.currentErrorCode="0",
	this.errors=[
      ["0", "No Error",
         "The previous LMS API Function call completed successfully."],
      ["101", "General Exception",
         "An unspecified, unexpected exception has occurred."],
      ["201", "Invalid argument error", ""],
      ["202", "Element cannot have children", ""],
      ["203", "Element not an array - cannot have count", ""],
      ["301", "Not initialized", "The LMS is not initialized."],
      ["401", "Not implemented error",
         "The data model element in question was not implemented."],
      ["402", "Invalid set value, element is a keyword",
         "Trying to set a reserved keyword in the data model.  " +
         "Trying to set a keyword (_count, _children, or _version).  " +
         "This is prohibited."],
      ["403", "Element is read only",
         "Data Element is Read Only (Not Writeable).  "+
         "Cannot call LMSSetValue() for the element in question."],
      ["404", "Element is write only",
         "Data Element is Write Only (Not Readable).  "+
         "Cannot call LMSGetValue() for the element in question."],
      ["405", "Incorrect Data Type",
         "Invalid Type being used for setting element.  "+
         "The type being used as the set value argument does not match" +
         " that of the element being set."  ]
   ],
   this.recKeyWordError=function (inElement){
   		 if ( inElement == "_children" )
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to set an _children element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("402");
      }
      else if ( inElement == "_count" )
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to set an _count element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("402");
      }
      else
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to set an _version element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("402");
      }
   },
   this.recGetKeyWordError = function (inElement){
   		if ( inElement == "_children" )
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to get an _children element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("202");
      }
      else if ( inElement == "_count" )
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to get an _count element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("203");
      }
      else
      {
         if ( DebugIndicator.ON )
         {
            alert("Trying to get an _version element.  This" +
                            " action is not permitted!");
         }
         this.setCurrentErrorCode("401");
      }
   },
   this.getCurrentErrorCode=function(){
   		return this.currentErrorCode;
   },
   this.setCurrentErrorCode= function(code){
   		 this.currentErrorCode = code;
   },
   this.clearCurrentErrorCode = function(){
   		this.currentErrorCode = this.errors[0][0];
   },
   this.getErrorDescription = function(code){
   		return getErrorElement(code)[1];
   },
   this.getErrorDiagnostic = function(code){
   		return getErrorElement(code)[2];
   },
   this.getErrorElement = function(code){
   		 for ( var i=0; i<this.errors.length; i++ )
      {
         if ( this.errors[i][0].toLowerCase()== code.toLowerCase() )
            return this.errors[i];
      }
      var tmp = ["","",""];
      return tmp;
   },
   this.isValidErrorCode = function(errorCode){
   	  var result = false;
      var i = 0;

      while ( !result && (i < this.errors.length) )
      {
         if ( this.errors[i][0].toLowerCase()== errorCode.toLowerCase() )
         {
            result = true;
         }
         i++;
      }

      return result;
   }
}