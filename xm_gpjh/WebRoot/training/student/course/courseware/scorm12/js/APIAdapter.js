/**
		apiadapter
*/

String.prototype.trim= function(){  
    // ��������ʽ��ǰ��ո�  
    // �ÿ��ַ����  
    return this.replace(/(^\s*)|(\s*$)/g, "");  
}

//===============  ajax ==========
var request;
var backValue;
function createRequest() {
  try {
    request = new XMLHttpRequest();
  } catch (trymicrosoft) {
    try {
      request = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (othermicrosoft) {
      try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (failed) {
        request = false;
      }
    }
  }
  if (!request)
    alert("Error initializing XMLHttpRequest!");
}

function sendData(dataUrl) {
     var url = dataUrl;
     request.open("GET", url, false);
     request.onreadystatechange = getScormData;
     request.send(null);
   }
   function getScormData() {
      if (request.readyState == 4)
       if (request.status == 200){
       		if(DebugIndicator.ON){
       			alert("get data,Server is done!");
       		}
	   		 var text = request.responseText;
	   		 var list = text.split(";");
	   		 backValue = new Array();
	   		 for(var i = 0;i<list.length;i++){
	   		 	var l = list[i];
	   		 	var llist = l.split("|");
	   		 	backValue[i]= [llist[0],llist[1]];
	   		 }
       }
       else if (request.status == 404)
         alert("Request URL does not exist");
       else
         alert("Error: status code is " + request.status);
   }
   
function getAJAXData(url){
	createRequest();
	sendData(url)
}

function SaveSCODataToPlatform(dmeKey, dmeValue,jessonid){
	 var d =  Math.random();
	var _command = "?d="+d+"&command=putdata&"+jessonid//+"&param="+dmeID+"&param="+dmeValue;
     for (var i=0;i<dmeKey.length;i++ )
     {
      _command += "&dmeID=" + dmeKey[i] + "&dmeValue=" + dmeValue[i];
     }
     
     createRequest();
     var dataUrl = servleturl + _command;
     saveData(dataUrl);
}

function saveSCOID(SCOID){
	 var d =  Math.random();
	var _command = "?d="+d+"&command=setSCOID&SCOID="+SCOID;//+"&param="+dmeID+"&param="+dmeValue;
     
     createRequest();
     var dataUrl = servleturl + _command;
     saveData(dataUrl);
}


function saveData(dataUrl){
	 var url = dataUrl;
     request.open("GET", url, false);
     request.onreadystatechange = saveDataResult;
     request.send(null);
}

function saveDataResult(){
	 if (request.readyState == 4)
       if (request.status == 200){
       		 if(DebugIndicator.ON){
       			alert("put data,Server is done!");
       		 }
       		 var text = request.responseText;
       		 backValue = text;
       }
       else if (request.status == 404)
         alert("Request URL does not exist");
       else
         alert("Error: status code is " + request.status);
}



//========================    apiadapter

var apiAdapter_debug = false;

//out param
var servleturl = "";
//apiurl ΪLMSFrmae2.jspҳ���е�ȫ�ֱ�,��������js��servlet�ĵ�ַ;

if(apiurl == undefined || apiurl == ""){
		var u = document.location.href;
		if(u.indexOf("/training")!= -1){
			 var index = u.indexOf("/training");
			 u = u.substring(0,index);
			 servleturl = u+ "/servlet/lmscmijs";
		}
}
else{
		servleturl = apiurl;
}


APIAdapter = {
	_Debug :apiAdapter_debug,
	lmsErrorManager:null,
	dmErrorManager:null,
	isLMSInitialized:null,
	cmiBooleanFalse:null,
	cmiBooleanTrue:null,
	servletURL:null,
	theSCOData:null,
	
	init : function(){
		if (this._Debug)
      {
         alert("In API::init()(the applet Init method)");
      }

      this.cmiBooleanFalse = "false";
      this.cmiBooleanTrue = "true";
		
      this.lmsErrorManager = new LMSErrorManager();
      this.dmErrorManager = new DMErrorManager();
      this.theSCOData = new SCODataManager();

      this.isLMSInitialized = false;

      try
      {
    	 this.servletURL = servleturl;
         var session = this.getCookie("JSESSIONID");

         if ( this._Debug )
         {
         	alert("session is: " + session);
            alert("servlet url is "+ this.servletURL);
         }
      }
      catch (e)
      {
      	return;
      }
		
	},
	 getCookie: function(cookieName)
   {
      var cookie=null;
      try
      {
         // Note Ms Explorer 4.x doesn't support the Netscape.JSObject
         // so first try to get it using JavaScript on the page
         // All Applet tagsd then should be written with JavaScript
         if ( cookie==null )
         {
            // If all fails try to use Netscape JSObject
            cookie= document.cookie;
            if ( this._Debug )
            {
               alert("Cookies found ="+cookie);
            }
         }
         if ( cookie!=null )
         {
            var tk = cookie.split(";");
            cookie = null;
            for(var i = 0 ;i<tk.length;i++){
            	var token = tk[i];
            	if(token.indexOf(cookieName+"=")!= -1){
            	  cookie=token;
                  break;
            	}
            }
            
            // Decode the cookie
            // We have set the date into the cookie value
            // and used a simplified URLencode to decode it
            if ( cookie!=null )
            {
               //cookie = decodeURL(cookie);
               cookie = cookie;
            }
         }
      }
      catch (e)
      {
         //catching all because this isn't important data
         //just for debuging output the code
         if ( this._Debug )
         {
            alert("Couldn't retrieve the cookie"+e);
         }
      }
      return cookie.trim();
   },
   CheckInitialization:function()
   {
      if ( this.isLMSInitialized != true )
      {
         this.lmsErrorManager.setCurrentErrorCode("301");
      }

      return this.isLMSInitialized;
   },
   
   LMSInitialize: function(param)
   {
      if ( this._Debug )
      {
         alert("In API::LMSInitialize");
      }

      var result;
      result = this.cmiBooleanFalse;  // assume failure


      // Make sure param is empty string "" - as per the API spec
      // Check for "null" is a workaround described in "Known Problems"
      // in the header.
      var  tempParm = param.toString();
      if ( (tempParm == "null" || tempParm == "") != true )
      {
         this.lmsErrorManager.setCurrentErrorCode("201");
         return result;
      }

      // Is LMS already initialized?
      if ( this.isLMSInitialized == true )
      {
         this.lmsErrorManager.setCurrentErrorCode("101");
      }
      else
      {
         if ( this._Debug )
         {
            alert("Trying to get SCO Data from servlet...");
         }

         // Build the local (client-side) LMS data model cache by getting the
         // SCODataManager class instances from the LMS Servlet

         var sessionid = this.getCookie("JSESSIONID");
         var d =  Math.random();
		 var url = this.servletURL + "?d="+d+"&command=getdata&"+sessionid;
		 
	     getAJAXData(url);
	  //   this.theSCODat = null;
	  //   this.theSCOData = new SCODataManager(); 
	     for(var m = 0; m<backValue.length;m++){
	     	 this.theSCOData.setValue(backValue[m][0],backValue[m][1]);
	     }

		
         if ( this._Debug )
         {
            alert("The Core Data for the current SCO contains the following:");
         }

         this.isLMSInitialized = true;

         // No errors were detected

         this.lmsErrorManager.clearCurrentErrorCode();

         result = this.cmiBooleanTrue;  // success
      }

      if ( this._Debug )
      {
         alert("Done Processing LMSInitialize()");
      }

      return result;
   },
   setSCOID : function(param){
   		saveSCOID(param);
   },
   LMSFinish : function(param)
   {
      if ( this._Debug )
      {
         alert("In API::LMSFinish");
      }

      var result = this.cmiBooleanFalse;  // assume failure

      // Make sure param is empty string "" - as per the API spec
      // Check for "null" is a workaround described in "Known Problems"
      // in the header.
      var tempParm = param.toString();
      if ( (tempParm == "null") || (tempParm == "") )
      {
         if ( this.CheckInitialization() == true )
         {
            var sessionTime = this.theSCOData.getValue("cmi.core.session_time");
			if (sessionTime != null) 
			{
				//��Ӷ�ʱ��NaN�Ĵ��?
				if(sessionTime.indexOf("NaN")!= -1){
					alert("session_time format error : find 'NaN' :"+ sessionTime);
					sessionTime = "00:00:00.0";
				}
					
				
				var total_time = this.theSCOData.getValue("cmi.core.total_time");
				//��Ӷ�ʱ��NaN�Ĵ��?
				if(total_time.indexOf("NaN")!= -1){
					alert("total_time format error : find 'NaN' :"+ total_time);
					total_time = "00:00:00.0";
				}
					
				
				//alert("t: "+total_time +" "+"s: "+sessionTime);
				var tem = total_time; // ��ʱ��total_time;
				total_time = this.theSCOData.addTimespan(total_time, sessionTime);
				
				//after compute time;
				if(total_time.indexOf("NaN")!= -1){
					alert("compute total_time format error : find 'NaN' !");
					alert("t_time: "+tem +"|"+"s_time:"+sessionTime+"|=total_time:" +total_time);
					total_time = tem;
				}
				
				
				//alert("total: "+ total_time);
				this.theSCOData.setValue("cmi.core.total_time",total_time);
			}
			
            if(this._Debug)
            {
               alert("session time: "+this.theSCOData.getValue("cmi.core.session_time"))
               alert("\t\tTotal time: " + totalTime.toString());
            }

            // If changes are left uncommitted when LMSFinish
            // is called, the LMS forces an LMSCommit.
             var exit = this.theSCOData.getValue("cmi.core.exit");
            if (exit == "") 
			{
		        this.theSCOData.setValue("cmi.core.entry", "");
		    }
		    else if (exit == "time-out") 
		    {
		        this.theSCOData.setValue("cmi.core.entry", "");
		    } 
		    else if (exit == "suspend")     
		    {
		        this.theSCOData.setValue("cmi.core.entry", "resume");
		    } 
		    else if (exit == "logout") 
		    {
		        this.theSCOData.setValue("cmi.core.entry", "");
		    } 
		    else 
		    {
		        this.theSCOData.setValue("cmi.core.entry", "");
		    }

            if (this.theSCOData.getValue("cmi.core.lesson_status") == "not attempted")
            {
               this.theSCOData.setValue("cmi.core.lesson_status","incomplete");
            }

            result = this.LMSCommit("");

            if ( result != this.cmiBooleanTrue )
            {
               if ( this._Debug )
               {
                  alert("LMSCommit failed causing LMSFinish to fail.");
               }
            }
            else
            {
               this.isLMSInitialized = false;

               // Now call a javascript function that should
               // be present in the window this applet is located in
               // that will change the SCO content frame using the
               // lesson servlet, or close the child window if one
               // was created.

               try
               {
               	  
                  window.eval("changeSCOContent()");
               }
               catch(e)
               {
            	   
               }
               result = this.cmiBooleanTrue;  // successful completion
            }
         }
      }
      else
      {
         this.lmsErrorManager.setCurrentErrorCode("201");
      }

      if ( this._Debug )
      {
         alert("Done Processing LMSFinish()");
      }

      return result;
   },
   LMSGetValue:function(element)
   {
      if ( this._Debug )
      {
         alert("In API::LMSGetValue");
      }

      if ( this.CheckInitialization() != true )
      {
         //LMS is not initialized    
         if(this._Debug)
         {
            alert("LMS Not Initialized");
         }
         var emptyString = new String("");
         return emptyString;
      }

      if(this._Debug)
      {
         alert("Request being processed: LMSGetValue(" + element + ")");
      }

      this.lmsErrorManager.clearCurrentErrorCode();
      this.dmErrorManager.clearCurrentErrorCode();
      var rtnVal = null;
		
	  rtnVal = this.theSCOData.getValue(element);
	  
	  //add test mesage;
	  if("cmi.core.total_time" == element && rtnVal.indexOf("NaN") != -1){
	  	alert("LmsGetValue error: total_time find : "+rtnVal)
	  }
	  if("cmi.core.session_time" == element && rtnVal.indexOf("NaN") != -1){
	  	alert("LmsGetValue error:  session_time find 'NaN': "+rtnVal)
	  }
	  //end test message;

      // Set the LMS Error Manager from the DataModel Manager
      this.lmsErrorManager.setCurrentErrorCode(this.dmErrorManager.getCurrentErrorCode());

      if ( rtnVal != null )
      {
         if ( this._Debug )
         {
            alert("LMSGetValue() found!");
            alert("Returning: "+ rtnVal);
         }
      }
      else
      {
         if ( this._Debug )
         {
            alert("Found the element, but the value was null");
         }
         rtnVal = new String("");
      }

      if ( this._Debug )
      {
         alert("Processing done for API::LMSGetValue");
      }

      return rtnVal;
   },
   LMSSetValue:function(element,value)
   {
   
   	  //add test mesage;
	  if("cmi.core.total_time" == element && value.indexOf("NaN") != -1){
	  	alert("LmsSetValue error: total_time find ': "+value)
	  }
	  if("cmi.core.session_time" == element && value.indexOf("NaN") != -1){
	  	alert("LmsSetValue error:  session_time find 'NaN': "+value)
	  }
	  //end test message;
   
   
   
      var result = this.cmiBooleanFalse;

      if ( this._Debug )
      {
         alert("In API::LMSSetValue");
      }

      this.lmsErrorManager.clearCurrentErrorCode();
      this.dmErrorManager.clearCurrentErrorCode();

      if ( this.CheckInitialization() != true )
      {
         //LMS is not initialized
         return result;
      }

      var setValue;

      // Check for "null" is a workaround described in "Known Problems"
      // in the header.
      var tempValue = String(value);
      if ( tempValue == "null")
      {
         setValue = new String("");
      }
      else
      {
         setValue = tempValue;
      }
      
      var theRequest = element + "," + setValue;
      if(this._Debug)
      {
         alert("Request being processed: LMSSetValue(" + theRequest + ")");
         alert( "Looking for the element " + element );   
      }
      	
    //  if(element == 'cmi.core.session_time')	
	//	alert("set v: "+ element+","+value);
	  
	  
	  rtnVal = this.theSCOData.setValue(element,value);

      // Set the LMS Error Manager from the DataModel Manager
      this.lmsErrorManager.setCurrentErrorCode(this.dmErrorManager.getCurrentErrorCode());
      if ( this.lmsErrorManager.getCurrentErrorCode() == "0" )
      {
         result = this.cmiBooleanTrue; // success
      }

      if ( this._Debug )
      {
         alert("Processing done for API::LMSSetValue");
      }
      return result;
   },
   LMSCommit : function(param)
   {
      if ( this._Debug )
      {
         alert("Processing API::LMSCommit");
      }

      var result = this.cmiBooleanFalse; // assume failure

      // Make sure param is empty string "" - as per the API spec
      // Check for "null" is a workaround described in "Known Problems"
      // in the header.
      var tempParm = String(param);
      if ( (tempParm == ("null") || tempParm == "") == true )
      {
         if ( this.CheckInitialization() != true )
         {
            //LMS is not initialized
            return result;
         }

         if ( this._Debug )
         {
            alert("Saving Data to the Server...");
            alert("The SCO Data Manager for the current SCO contains " +
                               "the following:");
         }
		 
         var sessionid = this.getCookie("JSESSIONID");
         var dmeValue=[];
		 var dmeKey=[];
		 for (var i=0;i<this.theSCOData.dataModel.length ;i++ )
		 {
			//if (this.theSCOData.dataModel[i].isWriteable())
			//{
				dmeKey.push(this.theSCOData.dataModel[i].key);
				dmeValue.push(this.theSCOData.dataModel[i].value);
			//}
		 }
		
        var servletResult = SaveSCODataToPlatform(dmeKey,dmeValue,sessionid);
      //  alert("commit total_time: "+ this.theSCOData.getValue("cmi.core.total_time"));

         if ( servletResult=="OK")
         {
            this.lmsErrorManager.setCurrentErrorCode("101");
            if ( this._Debug )
            {
               alert("Put to Server was NOT successful!");
            }
         }
         else
         {
            this.lmsErrorManager.clearCurrentErrorCode();
            result = this.cmiBooleanTrue; // successful completion
            if ( this._Debug )
            {
               alert("Put to Server succeeded!");
            }
         }
      }
      else
      {
         this.lmsErrorManager.setCurrentErrorCode("201");
      }

      if ( this._Debug )
      {
         alert("Processing done for API::LMSCommit");
      }

      return result;
   },
   LMSGetLastError : function()
   {
      if ( this._Debug )
      {
         alert("In API::LMSGetLastError()");
      }
      return this.lmsErrorManager.getCurrentErrorCode();
   },
   LMSGetErrorString : function(errorCode)
   {
      if ( this._Debug )
      {
         alert("In API::LMSGetErrorString()");
      }
      return this.lmsErrorManager.getErrorDescription(errorCode);
   },
   LMSGetDiagnostic: function(errorCode)
   {
      if ( this._Debug )
      {
         alert("In API::LMSGetDiagnostic()");
      }
      return this.lmsErrorManager.getErrorDiagnostic(errorCode); 
   }
	
}
