<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page	import="java.sql.*, java.util.*,com.whaty.platform.standard.scorm.operation.*,org.adl.util.*"%>
<html>
<head>
<%  
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);
%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="0"> 
<title>Menu</title>
<script type="text/javascript" src="mtmcode.js">
</script>
<script type="text/javascript">

/******************************************************************************
* User-configurable options.                                                  *
******************************************************************************/

// Menu table width, either a pixel-value (number) or a percentage value.
var MTMTableWidth = "100%";

// Name of the frame where the menu is to appear.
var MTMenuFrame = "menu";

// variable for determining whether a sub-menu always gets a plus-sign
// regardless of whether it holds another sub-menu or not
var MTMSubsGetPlus = "Never";

// variable that defines whether the menu emulates the behaviour of
// Windows Explorer
var MTMEmulateWE = true;

// Directory of menu images/icons
var MTMenuImageDirectory = "menu-images/";

// Variables for controlling colors in the menu document.
// Regular BODY atttributes as in HTML documents.
var MTMBGColor = "#78B0FF";
var MTMBackground = "";
var MTMTextColor = "black";

// color for all menu items
var MTMLinkColor = "black";

// Hover color, when the mouse is over a menu link
var MTMAhoverColor = "black";

// Foreground color for the tracking & clicked submenu item
var MTMTrackColor ="yellow";
var MTMSubExpandColor = "black";
var MTMSubClosedColor = "black";

// All options regarding the root text and it's icon
var MTMRootIcon = "menu_new_root.gif";
//var MTMRootIcon = "adl_tm_24x16.jpg";
var MTMenuText = "";
var MTMRootColor = "black";
var MTMRootFont = "Arial, Helvetica, sans-serif";
var MTMRootCSSize = "84%";
var MTMRootFontSize = "-1";

// Font for menu items.
var MTMenuFont = "Arial, Helvetica, sans-serif";
var MTMenuCSSize = "84%";
var MTMenuFontSize = "-1";

// Variables for style sheet usage
// 'true' means use a linked style sheet.
var MTMLinkedSS = false;
var MTMSSHREF = "style/menu.css";

// Additional style sheet properties if you're not using a linked style sheet.
// See the documentation for details on IDs, classes & elements used in the menu.
// Empty string if not used.
var MTMExtraCSS = "";

// Header & footer, these are plain HTML.
// Leave them to be "" if you're not using them

var MTMHeader = "";
var MTMFooter = "";

// Whether you want an open sub-menu to close automagically
// when another sub-menu is opened.  'true' means auto-close
var MTMSubsAutoClose = true;   //gaoyuan modify 0707

// This variable controls how long it will take for the menu
// to appear if the tracking code in the content frame has
// failed to display the menu. Number if in tenths of a second
// (1/10) so 10 means "wait 1 second".
var MTMTimeOut = 10;

// Cookie usage.  First is use cookie (yes/no, true/false).
// Second is cookie name to use.
// Third is how many days we want the cookie to be stored.
var MTMUseCookies = false;
var MTMCookieName = "MTMCookie";
var MTMCookieDays = 3;

// Tool tips.  A true/false-value defining whether the support
// for tool tips should exist or not.
var MTMUseToolTips = true;

/******************************************************************************
* User-configurable list of icons.                                            *
******************************************************************************/

var MTMIconList = null;
MTMIconList = new IconList();
MTMIconList.addIcon(new MTMIcon("menu_link_external.gif", "http://", "pre"));
MTMIconList.addIcon(new MTMIcon("menu_link_pdf.gif", ".pdf", "post"));

var menu = new MTMenu();

<%
 //  Get the information needed to build the menu
 Vector title_vector = new Vector();
 Vector id_vector = new Vector();
 Vector type_vector = new Vector();
 Vector level_vector = new Vector();
 Vector parent_vector = new Vector();
 Vector item_number_vector = new Vector();
 String userID = "";
 String courseID = "";
 String control = "";

 int length;
 int current_int_level;
 int current_index;
 int z;
 String previous_level = new String();
 int parent_index;
 String course_title = new String();
 String menu_name = new String();
 int new_level;
 ScormFactory scormFactory=ScormFactory.getInstance();
 ScormManage scormManage=scormFactory.creatScormManage();
 List itemList=null;
 ScormCourse course=null;
 
try
{
	userID = (String)session.getAttribute( "USERID" );
	//courseID = (String)session.getAttribute( "COURSEID" );
	courseID = (String)request.getParameter( "course_id" );
	control = (String)session.getAttribute( "control" );
	//out.println("alert('"+courseID+"')");
	if(courseID == null || courseID.equals(""))
	{
		out.println("alert('参数错误!')");
		return;
	}
	
	if (courseID != "") 
	{
	  	course=scormManage.getScormCourse(courseID);
		control = course.getControlType();
	  	course_title =course.getCourseTitle();
	}
	if ((courseID != "")  && ((control != null) && ((control.equals( "mixed")) || (control.equals( "choice")))))
	{
	 	itemList=scormManage.getCoursesItems(courseID);
	 	for(int j=0;j<itemList.size();j++)
		{
			title_vector.addElement(((ScormItem)itemList.get(j)).getTitle());
			id_vector.addElement(((ScormItem)itemList.get(j)).getId());
			level_vector.addElement(new Integer(((ScormItem)itemList.get(j)).getTheLevel()).toString());
			type_vector.addElement(((ScormItem)itemList.get(j)).getType());
		}
	} 
    
}
catch(Exception e){
	e.printStackTrace();   
}
%>  
    
    
<% // begin menu construction
if ((courseID != "")  && (courseID != null) && ((control != null) && ((control.equals( "mixed")) || (control.equals( "choice")))))
{  
	  int i = 0;
	%>var MTMenuText = "<%=course_title%>";
	  <% String previous_parent = "menu";
	  previous_level = level_vector.elementAt(i).toString();%>
	  // first item is menu root
	  menu.MTMAddItem(new MTMenuItem("<%=title_vector.elementAt(i).toString()%>", "javascript:launchItem('<%=id_vector.elementAt(i).toString()%>')", "code"));
	  <% 
	  
	  parent_index = 0;
	  parent_vector.addElement("menu");
	  length = title_vector.size();
	  item_number_vector.addElement("0");
	  current_index = 0; 
	  i++;
	  while ( i < length )
	  {   
	  		String action = "";
	  		if((String)type_vector.get(i) != null && ((String)type_vector.get(i)).equalsIgnoreCase("sco"))
	  			action = "javascript:launchItem('"+id_vector.elementAt(i).toString()+"')";
	  		else
	  			action = "#";
	  		 
	  // if nesting level of current item is same as that of previous item
	        if (level_vector.elementAt(i).toString().equals(previous_level))
	        { %>
	        
	            <%=parent_vector.elementAt(parent_index).toString()%>.MTMAddItem(new MTMenuItem("<%=title_vector.elementAt(i).toString()%>", "<%=action%>", "code"));
	           <% //increment item_number_vector at current_index so know which item are at
	           Integer inc = new Integer(item_number_vector.elementAt(current_index).toString());
	           new_level = inc.intValue();
	           new_level++;
	           item_number_vector.setElementAt(inc.toString(new_level), current_index);
	           i++;
	        }// end if
	        //if level is greater, get new menu name, add name to 
	        //parent_vector and use as current menu name
	        else if ( (previous_level.compareTo(level_vector.elementAt(i).toString()))<0)
	        {  
	           menu_name = "sub"+parent_index;
	           Integer tempInt = new Integer(item_number_vector.elementAt(current_index).toString()); 
	           int item_number = tempInt.intValue();
	           %>var <%=menu_name%> = new MTMenu();
	           <%= parent_vector.elementAt(parent_index).toString()%>.items[<%=item_number%>].MTMakeSubmenu(<%=menu_name%>);
	           <% parent_vector.addElement(menu_name);
	           parent_index++;
	           item_number_vector.addElement("0");
	           current_index++;%>                 
	           <%=menu_name%>.MTMAddItem(new MTMenuItem("<%=title_vector.elementAt(i).toString()%>", "<%=action%>", "code")); 
	           <%
	           previous_level = level_vector.elementAt(i++).toString();
	        } //end else if
	        else
	          //if level is less
	        { 
	           Integer int1 = new Integer(previous_level);
	           Integer int2 = new Integer(level_vector.elementAt(i).toString());
	           current_int_level = int1.intValue() - int2.intValue(); 
	           for (z = 0; z<current_int_level; z++)
	           {  
	              parent_vector.removeElementAt(parent_index--);
	              item_number_vector.removeElementAt(current_index--);
	           }// end for %>
	                              <%=parent_vector.elementAt(parent_index).toString()%>.MTMAddItem(new MTMenuItem("<%=title_vector.elementAt(i).toString()%>", "<%=action%>", "code")); 
	           <% //increment item_number_vector at current_index so know which item are at
	           Integer inc = new Integer(item_number_vector.elementAt(current_index).toString());
	           new_level = inc.intValue();
	           new_level++;
	           item_number_vector.setElementAt(inc.toString(new_level), current_index);
	           previous_level = level_vector.elementAt(i++).toString();
	        }// end else
	} //end while
} // end menu creation
else
{
%>
parent.LMSMain.cols="0,*";
//alert("courseid:<%=courseID%>,choice:<%=control%>");
<%
}

%>
 
      </script>
</head>

<body onload="MTMStartMenu()" bgcolor="#78B0FF" text="#black"
	link="yellow" vlink="lime" alink="red">
</body>

</html>
