<%
	/*
	LMSFrameJs.jsp;
	本文件为JS版Scorm APIAdpter 加载的页面；与LMSFrame.jsp 加载applet不同；
	本页面只是加载了JS 中的API 从而不使用applet;
	*/
 %>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<title>ADL Sample Run-Time Environment Version 1.2.1</title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 %>
 <script type="text/javascript">
 	//配置js版servlet的地址;
 	var apiurl = "<%=basePath%>servlet/lmscmijs";
 </script>
<script type="text/javascript" src="js/DebugIndicator.js" ></script>
<script type="text/javascript" src="js/DMErrorManager.js" ></script>
<script type="text/javascript" src="js/LMSErrorManager.js"></script>
<script type="text/javascript" src="js/SCORM1.2.js"></script>
<script type="text/javascript" src="js/APIAdapter.js"></script>

<script language=javascript>
function LMSIsInitialized()
{
   // Determines if the API (LMS) is in an initialized state.
   // There is no direct method for determining if the LMS API is initialized
   // for example an LMSIsInitialized function defined on the API so we'll try
   // a simple LMSGetValue and trap for the LMS Not Initialized Error
   
   var value = API.LMSGetValue("cmi.core.student_name");
   var errCode = API.LMSGetLastError().toString();
   
   if (errCode == 301)
   {
  
      return false;
   }
   else
   {
      return true;
   }
}

/****************************************************************************
**
** Function: login_onclick()
** Input:   none
** Output:  none
**
** Description:  This function changes the content frame to the login page,
**               as "hides" the login button.
**
***************************************************************************/
function login_onclick() 
{
   window.parent.frames[3].document.location.href = "LMSLogin.htm";
   if ( document.layers != null )
   {
      swapLayers();
   }
   else if ( document.all != null )
   {
      window.document.forms[0].login.style.visibility = "hidden";
   }
   else
   {
      //Niether IE nor Netscape is being used
      alert("your browser may not be supported");
   }
}

/****************************************************************************
**
** Function: logout_onclick()
** Input:   none
** Output:  none
**
** Description:  This function redirects to logout.jsp, but first checks
**               to see if there is a course executing.
**
***************************************************************************/
function logout_onclick() 
{
   // two known potential difficulties exist with having the logout
   // button in this frame...   The first is that the user may not
   // have exited the lesson before attempting to log out.  The second
   // problem is that a child window may be open containing the lesson
   // if the user has not exited.   To deal with these two cases, we'll
   // force the user to exit the lesson before we allow a logout.

   if (LMSIsInitialized() == true)
   {
      // we're making an assumtion that the user is trying
      // to log out without first exiting the lesson via the
      // appropriate means - because if the user had exited
      // the lesson, the LMS would not still be initialized.
      var  mesg = "You must exit the lesson before you logout";
      alert(mesg);
   }
   else
   {
      window.parent.frames[3].location.href="logout.jsp";
   }

   return;
}

/****************************************************************************
**
** Function: changeSCOContent()
** Input:   none
** Output:  none
**
** Description:  This function enables the appropriate controls so that
**               the user can progress to the next item.
**
***************************************************************************/
function changeSCOContent()
{
   //This function is called by the APIAdapterApplet during 
   //LMSFinish.
   if ( document.layers != null )
   {
     swapLayers();
   }
   else if ( document.all != null )
   {
     ctrl = window.document.forms[0].control.value; 
     
     if ( ctrl == "mixed" || ctrl == "flow" || ctrl == "" || ctrl == null )
     {
         window.top.frames[0].document.forms[0].next.style.visibility = "visible"; 
         window.top.frames[0].document.forms[0].previous.style.visibility = "visible";
         window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
         window.top.frames[0].document.forms[0].next.disabled = false;
         window.top.frames[0].document.forms[0].previous.disabled = false;
     }
     else if ( ctrl == "choice" )
      { 
         window.top.frames[0].document.forms[0].next.style.visibility = "hidden"; 
         window.top.frames[0].document.forms[0].previous.style.visibility = "hidden"; 
         window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
      }  
      else if ( ctrl = "auto" )
      {
         window.top.contentWindow.document.location.href = "sequencingEngine.jsp";
      }
   }
        
   else
   {
     //Neither IE nor Netscape is being used
     alert("your browser may not be supported");
   }


}

/****************************************************************************
**
** Function: nextSCO()
** Input:   none
** Output:  none
**
** Description:  This function is called when the user clicks the "next"
**               button.  The Sequencing Engine is called, and all relevant
**               controls are affected. 
**
***************************************************************************/
function  nextSCO()
{
   // This is the launch line for the next SCO...
   // The Sequencing Engine determines which to launch and
   // serves it up into the LMS's content frame or child window - depending
    //on the method that was used to launch the content in the first place.
   var scoWinType = typeof(window.parent.frames[3].scoWindow);
   var theURL = "pleaseWait.jsp?button=next";
  
   if (scoWinType != "undefined" && scoWinType != "unknown")
   {
      if (window.parent.frames[3].scoWindow != null)
      {
         // there is a child content window so display the sco there.
         window.parent.frames[3].scoWindow.document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
      else
      {
         window.parent.frames[3].document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
   }
   else
   {
      window.parent.frames[3].document.location.href = theURL;
      window.parent.frames[2].document.location.href = "code.jsp";
   }
   if ( document.layers != null )
   {
      swapLayers();
   }
   else if ( document.all != null )
   {
     // window.top.frames[0].document.forms[0].next.disabled = true;
     // window.top.frames[0].document.forms[0].previous.disabled = true;
   }
   else
   {
      //Neither IE nor Netscape is being used
      alert("your browser may not be supported");
   }  
}


/****************************************************************************
**
** Function: previousSCO()
** Input:   none
** Output:  none
**
** Description:  This function is called when the user clicks the "previous"
**               button.  The Sequencing Engine is called, and all relevant
**               controls are affected. 
**
***************************************************************************/
function  previousSCO()
{

   // This function is called when the "Previous" button is clicked.
   // The LMSLesson servlet figures out which SCO to launch and
   // serves it up into the LMS's content frame or child window - depending
   //on the method that was used to launch the content in the first place.

   var scoWinType = typeof(window.parent.frames[3].scoWindow);
   var theURL = "pleaseWait.jsp?button=prev";
   
   if (scoWinType != "undefined" && scoWinType != "unknown")
   {
      if (window.parent.frames[3].scoWindow != null)
      {
         // there is a child content window so display the sco there.
         window.parent.frames[3].scoWindow.document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
      else
      {
         window.parent.frames[3].document.location.href = theURL;
         window.parent.frames[2].document.location.href = "code.jsp";
      }
   }
   else
   {
      window.parent.frames[3].document.location.href = theURL;
      window.parent.frames[2].document.location.href = "code.jsp";

      //  scoWindow is undefined which means that the content frame
      //  does not contain the lesson menu at this time.
   }
   if ( document.layers != null )
   {
      swapLayers();
   }
   else if ( document.all != null )
   {
     // window.document.forms[0].next.disabled = true;
     // window.document.forms[0].previous.disabled = true;
   }
   else
   {
     //Neither IE nor Netscape is being used
      alert("your browser may not be supported");
   }
  
}

/****************************************************************************
**
** Function: closeSCOContent()
** Input:   none
** Output:  none
**
** Description:  This function exits out of the current lesson and presents
**               the RTE menu. 
**
***************************************************************************/
function closeSCOContent()
{
   var scoWinType = typeof(window.parent.frames[3].window);
   
   ctrl = window.document.forms[0].control.value;
   
   if ( ctrl == "auto" )
   {
      window.parent.frames[2].document.location.href = "code.jsp";
      window.top.frames[3].location.href = "LMSMenu.jsp"
      window.top.contentWindow.close();
   }
   else
   {
      window.parent.frames[2].document.location.href = "code.jsp";   
      if (scoWinType != "undefined" && scoWinType != "unknown")
      {
         if (window.parent.frames[3].scoWindow != null)
         {      
            // there is a child content window so close it.
            window.parent.frames[3].scoWindow.close();
            window.parent.frames[3].scoWindow = null;
         }
         window.parent.frames[3].document.location.href = "LMSMenu.jsp";
      }
      else
      {
         //  scoWindow is undefined which means that the content frame
         //  does not contain the lesson menu so do nothing...
      }
   }   
}

/****************************************************************************
**
** Function: swapLayers()
** Input:   none
** Output:  none
**
** Description:  This function is used to swap the login and logout buttons
**
***************************************************************************/
function swapLayers()
{
   if ( document.loginLayer.visibility == "hide" )
   {
      document.logoutLayer.visibility = "hide";
      document.loginLayer.visibility = "show";
   }
   else
   {
      document.loginLayer.visibility = "hide";
      document.logoutLayer.visibility = "show";
   }
}

/****************************************************************************
**
** Function: init()
** Input:   none
** Output:  none
**
** Description:  This function sets the API variable and hides the
**               the navigation buttons
**
***************************************************************************/
function init()
{
   APIAdapter.init();
   API = APIAdapter;
   
   if(window.top){
   	window.top.API = API;
   }else{
   	 if(window.opener){
   	 	window.opener.API = API;
   	 }else{
   	 	window.parent.API = API;
   	 }
   }
  // parent.API = API;

   window.top.frames[0].document.forms[0].next.style.visibility = "hidden"; 
   window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
}

/****************************************************************************
**
** Function: doConfirms()
** Input:   none
** Output:  none
**
** Description:  This function prompts the user that they may lose
**               data if they exit the course
**
***************************************************************************/
function doConfirm()
{
    if( confirm("确实要退出吗!") )
    {
       window.parent.close();
    }
    else
    {
    }
}


</script>
</head>

<body onload="init();" topmargin="0">

<!--  For MS IE Use the Java 1.3 JRE Plug-in instead of the Browser's JVM
      Netscape 4.x can't use the plug-in because it's liveconnect doesn't work with the Plug-in
-->
<form name="buttonform">
	<input type="hidden" name="control" value="" /> 
   
    <table width="100%">
    <tr valign="top"> 
       <td align="center">   
          <font color="purple" face="Tahoma" size="2"><b>
            欢迎使用<br>
           </b></font>
       </td>
    </tr>
    </table> 
    
     
              
    
       
    <!--NOLAYER-->
    <table width="600" align="left" cellspacing=0 border=0>
    <tr>
       
       <TD ALIGN="center">
             <INPUT type="button" ALIGN = "right" VALUE="退出" name="quit" language="javascript"
                ONCLICK="doConfirm();" STYLE="visibility: hidden">
       </TD>
       <td align="left">
          <input type="button" align ="left" value="Glossary" id="glossary" name="glossary"  language="javascript"
                 onclick="return nextSco();"  style="visibility: hidden" disabled>&nbsp; 
       </td>
       <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       <td align="center"> 
          
          <input type="button" align ="right" value="<- 上一节" id="previous" name="previous"  language="javascript"
                      onclick="return previousSCO();"  style="visibility: hidden"> 
          
       </td>
       <td align="center">
             
             <input type="button" align ="right" value="下一节 ->" id="next" name="next"  language="javascript"
                    onclick="return nextSCO();" style="visibility: hidden">   
       </td>
    </tr>
</table>
    
<!--/NOLAYER-->
</form>
</body>
</html>
