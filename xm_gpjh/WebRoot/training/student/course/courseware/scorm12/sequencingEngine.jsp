<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page
	import="java.sql.*, java.util.*,com.whaty.platform.standard.scorm.operation.*,
	org.adl.util.*,
	com.whaty.platform.courseware.config.CoursewareConfig,
	com.whaty.platform.training.*,
	com.whaty.platform.training.user.*"%>
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant,com.whaty.platform.sso.web.servlet.UserSession;"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession==null)
{
%>
<script type="text/javascript">
	alert("请您登录后再学习课件！");
	window.location="<%=basePath1%>";
</script>
<%
	return;
}
 %>
<%
//  Booleans for a completed course and request type
boolean courseComplete = false;
boolean wasAMenuRequest = false;
boolean wasANextRequest = false;
boolean wasAPrevRequest = false;
boolean wasFirstSession = false;
boolean empty_block = false;


//  The type of controls shown
String control = new String();
//  The next item that will be launched
String nextItemToLaunch = new String();
//  The type of button request if its a button request
String buttonType = new String();
//  whether the launched unit is a sco or an asset
String type = new String();
// is the item a block with no content
String item_type = new String();
//is the identifier column 
String identifier = new String();

// The courseID is passed as a parameter on initial launch of a course
String courseID = (String)request.getParameter( "courseID" );
//  Get the requested sco if its a menu request
String requestedSCO = (String)request.getParameter( "scoID" );
//  Get the button that was pushed if its a button request
buttonType = (String)request.getParameter( "button" );
/*
out.print("courseID="+courseID+"<br>");
out.print("requestedSCO="+requestedSCO+"<br>");
out.print("buttonType="+buttonType+"<br>");
*/
// Set boolean for the type of navigation request
if ( ((requestedSCO != null)) && (! requestedSCO.equals("") ))
{
   wasAMenuRequest = true;
}
else if ( (! (buttonType == null) ) && ( buttonType.equals("next") ) )
{
   wasANextRequest = true;
}
else if ( (! (buttonType == null) ) && ( buttonType.equals("prev") ) )
{
   wasAPrevRequest = true;
}
else
{
   // First launch of the course in this session.
   wasFirstSession = true;
}
//  If the course has not been launched
if ( courseID != null )
{
   //  set the course ID
   session.setAttribute( "COURSEID", courseID );
}
else // Not the initial launch of course, use session data
{
   courseID = (String)session.getAttribute( "COURSEID" );
}




//try
//{
   	ScormFactory scormFactory=ScormFactory.getInstance();
	ScormManage scormManage=scormFactory.creatScormManage();
	ScormCourse scormCourse=scormManage.getScormCourse(courseID);
	String lessonLocation = "";
	
	//课件导航则不使用lession_location;
	String navigate = scormCourse.getNavigate();
	
	if(wasFirstSession)
	{
		Object priv = session.getAttribute("traininguser_priv");
		String userId = "";
		if(priv instanceof TrainingManagerPriv)
			userId = ((TrainingManagerPriv)priv).getManagerId();
		else if(priv instanceof TrainingStudentPriv)
			userId = ((TrainingStudentPriv)priv).getStudentId();
		
		 
		
		ScormStudentPriv userPriv = ScormFactory.getInstance().creatScormStudentPriv();
		userPriv.setStudentId(userId);
		userPriv.learnCourse=1;
		ScormStudentManage man = ScormFactory.getInstance().creatScormStudentManage(userPriv);
		UserCourseData userCourse = man.getUserCourseData(courseID);
		if(userCourse != null)
			lessonLocation = userCourse.getLessonLocation();
	}
	
	
	List userScos=null;
	List courseItems=null;
	control=scormCourse.getControlType();
	session.setAttribute( "control", control );
   
   //  Get the user's id
    String userID = (String)session.getAttribute( "USERID" );
   userScos=scormManage.getUserScos(userID,courseID);
   /*  out.print("userScos's num="+userScos.size()+"<br>");
  

  out.print("courseComplete="+courseComplete+"<br>");
out.print("wasAMenuRequest="+wasAMenuRequest+"<br>");
out.print("wasANextRequest="+wasANextRequest+"<br>");
out.print("wasAPrevRequest="+wasAPrevRequest+"<br>");
out.print("wasFirstSession="+wasFirstSession+"<br>");
out.print("control="+control+"<br>");
  */
  //  Get the session exit flag to see if its a logout request
   String exitFlag = (String)session.getAttribute( "EXITFLAG" );
  
  
       
   if ( exitFlag != null && exitFlag.equals( "true" ) )
   {
      //  It is a logout, so redirect to the logout page
      session.removeAttribute( "EXITFLAG" );
      response.sendRedirect("logout.jsp");
   }
   else  // It is a navigation request
   {
       // Initialize variables that help with sequencing
      String scoID = new String();
      String lessonStatus = new String();
      String launch = new String();
      
      //  If the user selected a menu option, handle appropriately
      if (wasAMenuRequest)
      {
      //   out.print("wasAMenuRequest="+wasAMenuRequest+"<br>");
         courseItems=scormManage.getCoursesScos(courseID);
         // Move into the first record in the record set
         for(int i=0;i<courseItems.size();i++)
         {
            //  Get the TYPE column
            item_type =((ScormItem)courseItems.get(i)).getType();
            identifier =((ScormItem)courseItems.get(i)).getId();
            launch=((ScormItem)courseItems.get(i)).getLaunch();
            // the item is not an asset or sco, it is a contain block
            if  (!(item_type.equals("")) && ( identifier.equals(requestedSCO)))
            {
               // Launch the next sco or item that is the first child
               // of the block item.
               requestedSCO =((ScormItem)courseItems.get(i)).getId();
               empty_block = true;
            }
               if ( empty_block)
               break;
         }
		nextItemToLaunch = launch;
        // Handle appropriately for a menu request
        //  Get the last sco id that was taken
        String lastScoID = (String)session.getAttribute("SCOID");
        session.setAttribute( "SCOID", requestedSCO );
        //  Loop through to find the next one to launch
       // while ( userSCORS.next() )
        for(int i=0;i<userScos.size();i++)
        {
            scoID =((UserScoData)userScos.get(i)).getSystemId();
            lessonStatus = ((UserScoData)userScos.get(i)).getCore().lesson_status.getValue();
            launch =((UserScoData)userScos.get(i)).getLaunch();
            type = ((UserScoData)userScos.get(i)).getType();
            
            if ( requestedSCO.equals(scoID) ) 
            {
               nextItemToLaunch = launch;
               courseComplete = false;
               break;
            }
         }

         // insert the correct values in stmtUpdateUserSCO
         // If it is an asset, execute the query, marking the asset as completed.
         if ( (! (type == null) ) && type.equals("asset") )
         {
           int flag=scormManage.updateUserScoStatus(userID,courseID,scoID,"completed");
         }

       
      }
      else // It was a next request, previous request, or first launch of session (or auto)
      {

		
          
          //  If its auto or first session
         if (wasFirstSession||(control.equals("auto")))
         {
            //  Launch the first item that is not in a completed state
            
            //用户已经拥有学习记录
            if(userScos.size()<1)
	         //用户还没有任何学习记录
	         {
	         	courseItems=scormManage.getCoursesScos(courseID);
	         	nextItemToLaunch=((ScormItem)courseItems.get(0)).getLaunch();
	         	courseComplete = false;
	         	session.setAttribute( "SCOID", ((ScormItem)courseItems.get(0)).getId());
	         }
	         else
            {
            	String start = request.getParameter("start");
            	if(start!=null&&start.equals("begin"))
            	{
            		lessonLocation = "";
            	}
            	if(ScormConstant.SCORM_NAVIGATE_COURSEWARE.equals(navigate) ||lessonLocation == null || lessonLocation.equals("") || lessonLocation.equals("null"))
            	{
            		scoID =((UserScoData)userScos.get(0)).getSystemId();
		            lessonStatus = ((UserScoData)userScos.get(0)).getCore().lesson_status.getValue();
		            launch =((UserScoData)userScos.get(0)).getLaunch();
		            type = ((UserScoData)userScos.get(0)).getType();
		            nextItemToLaunch = launch;
		            courseComplete = false;
		            session.setAttribute( "SCOID", scoID );
		           
            	}
            	else
            	{
		            for(int i=0;i<userScos.size();i++)
		            {
		               	scoID =((UserScoData)userScos.get(i)).getSystemId();
			            lessonStatus = ((UserScoData)userScos.get(i)).getCore().lesson_status.getValue();
			            launch =((UserScoData)userScos.get(i)).getLaunch();
			            type = ((UserScoData)userScos.get(i)).getType();
			            
			            /*
		               	// Set nextItemToLaunch to the next incomplete sco or asset
		               	if (lessonStatus==null ||( lessonStatus!=null && ! (lessonStatus.equalsIgnoreCase( "completed" ) ) &&
		                    ! (lessonStatus.equalsIgnoreCase( "passed" ) ) &&
		                    ! (lessonStatus.equalsIgnoreCase( "failed" ) ) ))
		                  */
		                 if( lessonLocation.equalsIgnoreCase(scoID))   
		               	{
		                  nextItemToLaunch = launch;
		                  courseComplete = false;
		                  session.setAttribute( "SCOID", scoID );
		                  break;
		               }
		              
		            }
	            }
	         }
         }  //  Ends if it was the first time in for the session
         else if ( wasANextRequest )// Its a next request
         {
        
            // Handle the next request
            //  Get the last sco id that was taken
            String lastScoID = (String)session.getAttribute("SCOID");
            //  Boolean to trigger the correct sco to launch
            boolean timeToLaunch = false;
            
            //  Loop through to find the next one to launch
            for(int i=0;i<userScos.size();i++)
            {
               	// Launch the next sequential sco
               	scoID =((UserScoData)userScos.get(i)).getSystemId();
				lessonStatus = ((UserScoData)userScos.get(i)).getCore().lesson_status.getValue();
				launch =((UserScoData)userScos.get(i)).getLaunch();
				type = ((UserScoData)userScos.get(i)).getType();
               	if ( timeToLaunch )
               	{

                  	nextItemToLaunch = launch;
                  	courseComplete = false;
                  	session.setAttribute( "SCOID", scoID );
                  	break;
               	}
                
               	if ( lastScoID.equals(scoID) )
               	{
                  	timeToLaunch = true;
               	}
             }
             // insert the correct values in stmtUpdateUserSCO
                          
             // Execute the query, marking the asset as completed.
            
             if ( (! (type == null) ) && type.equals("asset") )
             { 
                int flag=scormManage.updateUserScoStatus(userID,courseID,scoID,"completed");
             }

         }  //  Ends if its a next request
         else if ( wasAPrevRequest )// Its a previous request
         {
            // Handle the previous request
            String lastScoID = (String)session.getAttribute("SCOID");
            String prevScoID = new String();
            String prevScoLaunch = new String();
            boolean timeToLaunch = false;
            int count = 0;
           	int j=0;
           	for(j=0;j<userScos.size();j++)
            {
               if ( timeToLaunch )
               {
                  // Launch the previous sequential sco or asset
                  nextItemToLaunch = prevScoLaunch;
                  courseComplete = false;
                  session.setAttribute( "SCOID", prevScoID );
                  break;
               }
               //  Get the previous scoID and launch
               prevScoID = scoID;
               prevScoLaunch = launch;
               
               //  Get the new info
              scoID =((UserScoData)userScos.get(j)).getSystemId();
				lessonStatus = ((UserScoData)userScos.get(j)).getCore().lesson_status.getValue();
				launch =((UserScoData)userScos.get(j)).getLaunch();
				type = ((UserScoData)userScos.get(j)).getType();
               count++;//the first time through the loop, check to see if
                      // the request was made by the first sco in the course
               if ( lastScoID.equals(scoID) )
               {
                  if ( count == 1)
                  {  prevScoID = scoID;
                     prevScoLaunch = launch;
                  }
                  timeToLaunch = true;
               }//end if
               }//end while
               
               if (j==userScos.size()-1)
                 {// Launch the previous sequential sco or asset
                  nextItemToLaunch = prevScoLaunch;
                  courseComplete = false;
                  session.setAttribute( "SCOID", prevScoID );
                  } 
                       
         }//end previous
         
        

      }  // Ends if it was a button request
      
   }  //  Ends if it was a navigation request

   //  If the course is complete redirect to the course
   //  complete page
   if ( courseComplete )
   {
      session.removeAttribute( "COURSEID" );
      response.sendRedirect("courseComplete.jsp"); 
   }
   else
   {
	   CoursewareConfig coursewareConfig = (CoursewareConfig)application.getAttribute("coursewareConfig");
   	
   	String baseUrl = request.getContextPath() + coursewareConfig.getCoursewareBaseUrl();
   	 
%>

<!-- ****************************************************************
**   Build the html 'please wait' page that sets the client side 
**   variables and refreshes to the appropriate course page
*******************************************************************-->
<html>
	<head>
		<title>Sample Run-Time Environment - Sequencing Engine</title>
		<!-- **********************************************************
   **  This value is determined by the JSP database queries
   **  that are located above in this file
   **  Refresh the html page to the next item to launch  
   ***************************************************************-->
		

		<script language="JAVASCRIPT">
		 var ctrl = "<%=control%>";
               function updateControlStatus()
               {
               
	               // Hide the next and previous buttons if it is of type "choice".
	               
	               
	               		 	if (ctrl == "choice")
			               { 
			               	  
			              //    window.top.frames[0].document.forms[0].next.style.visibility = "hidden";
			             //     window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
			             //     window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
			               }
			               else
			               {
			                  // Make the buttons visible
			             //     window.top.frames[0].document.forms[0].next.style.visibility = "visible";
			            //      window.top.frames[0].document.forms[0].previous.style.visibility = "visible";
			             //     window.top.frames[0].document.forms[0].quit.style.visibility = "visible";
			               }
	               
	              
               }
          var _parent = window.top?window.top:(window.opener?window.opener:window.parent);    
         function initLMSFrame()
         {
         	if(!window.top.frames[0].document.forms[0] || !window.top.frames[0].document.forms[0].control)
	               		setTimeout("initLMSFrame()",300);
            // Set the type of control for the course in the LMS Frame 
            try{
            if(!_parent)
            {
            	alert("未知错误");
            	return;
            }
            if ( window.opener == null )
            {
            	 
               window.top.frames[0].document.forms[0].control.value = "<%=control%>";
            }
            else //  Set up control type in the window opener (if its auto mode)
            {
               // The sequencingEngine.jsp file runs in the opened window if it is auto
               // mode so special cases exist
               window.opener.top.frames[0].document.forms[0].control.value = "<%=control%>";
            }
            updateControlStatus();
            }
            catch(e)
            {
            	setTimeout("initLMSFrame()",300);
            	return;
            }
            tryJumpToItem();
            
         }
         function tryJumpToItem()
         {
         	//alert("1");
         	if(_parent.API){
         		//alert("2");
         		//alert("<%=baseUrl%>");
         		//alert("3");
         		//alert("<%=nextItemToLaunch%>");
         		//alert("4");
         		//alert("<%=baseUrl + nextItemToLaunch%>");
         		window.location.href="<%=baseUrl + nextItemToLaunch%>";
         		
         	}
         		
         	else
         		setTimeout("tryJumpToItem()",2000);	
         }
      </script>
	</head>

	<body bgcolor="#FFFFFF" onload="initLMSFrame()">
		<%
				//  If control is not auto. work in this window. 
				if (!control.equals("auto")) {
		%>
		<script language="javascript">
               
            </script>


		<p align="center">
			<font size="4">正在加载课件, 请等待.... </font>
		</p>
	</body>
</html>
<%
} // Ends if its not auto sequencing... then configure controls
%>

<%
	} // Ends else display the please wait page

	//}
	//catch ( Exception e )
	//{ 
	//   out.println("!! Exception Occurred: " + e + " !!");
	//}
%>
