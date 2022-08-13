<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<!--
/*******************************************************************************
**
** Filename:  LMSMenu.jsp
**
** File Description: This file displays the main menu.
**
** Author: ADL Technical Team
**
** Contract Number:
** Company Name: CTC
**
** Module/Package Name:
** Module/Package Description:
**
** Design Issues:
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
** References: ADL SCORM
**
/*******************************************************************************
**
** Concurrent Technologies Corporation (CTC) grants you ("Licensee") a non-
** exclusive, royalty free, license to use, modify and redistribute this
** software in source and binary code form, provided that i) this copyright
** notice and license appear on all copies of the software; and ii) Licensee does
** not utilize the software in a manner which is disparaging to CTC.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-
** INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND ITS LICENSORS SHALL NOT BE LIABLE
** FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
** DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS
** LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
** INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
** CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
** OR INABILITY TO USE SOFTWARE, EVEN IF CTC  HAS BEEN ADVISED OF THE POSSIBILITY
** OF SUCH DAMAGES.
**
*******************************************************************************/
-->
<head>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<title>ADL Sample Run-Time Environment Version 1.2.1 Menu Page</title>
<script language=javascript>
/**************************************************************************
*  function to confirm that user really wants to clear the database
*
***************************************************************************/
function confirmClearDatabase()
{
    if( confirm("Do you really want to remove all the course information?") )
    {
       window.parent.frames[3].document.location.href = "clearDatabase.jsp";
    }
    else
    {
    }
}

/**************************************************************************/


// Hide or display the relevant controls
if( document.all != null )
{
   window.parent.frames[0].document.forms[0].logout.style.visibility="visible";
   window.parent.frames[1].document.location.href = "code.jsp";
   window.top.frames[0].document.forms[0].next.style.visibility = "hidden";
   window.top.frames[0].document.forms[0].previous.style.visibility = "hidden";
   window.top.frames[0].document.forms[0].quit.style.visibility = "hidden";
}
</script>
</head>

<body >

<% 
   // Get the user's information and remove any course IDs
   String userid = (String)session.getAttribute( "USERID" );
   String admin = (String)session.getAttribute( "RTEADMIN" );
   session.removeAttribute( "COURSEID" );
%>      
    <p>
    <font face="tahoma" size="3"><b>
    请选择某个选项:
    </b></font>
    </p>
    
    <br>
    
    <table width="200">
    <tr>
        <td bgcolor="#5E60BD">
           <font face="tahoma" size="2" color="#ffffff"><b>
              &nbsp;用户相关操作
           </b></font>
        </td>
    </tr>
    <tr>
       <td>
          <a href="courseRegister.jsp">
             选课
          </a>
       </td>
    </tr>
    <tr>
       <td>
          <a href="viewCourses.jsp">查看所选课程</a>
       </td>
    </tr>
    <tr>
       <td>
          <a href="changePwd.jsp">修改密码</a>
       </td>
    </tr> 
        <tr>
       <td>
          <i><a href="readme.htm" target="_blank">Sample RTE 1.2.1 Readme</a></i>
       </td>
    </tr> 
    </table>
    
 <% if ( (! (admin == null)) && ( admin.equals("true")) ) 
    { %>
    <br>

    <table width="200">
    <tr>
        <td bgcolor="#5E60BD">
           <font face="tahoma" size="2" color="#ffffff"><b>
              &nbsp;管理员选项:
           </b></font>
        </td>
    </tr>
    <tr>
       <td>
          <a href="importCourse.jsp">
             导入课程
          </a>
       </td>
    </tr>
    <tr>
       <td>
         <a href="deleteCourse.jsp">删除课程</a> 
       </td>
    </tr>
    <tr>
       <td>
          <a href="newUser.jsp">添加用户</a>
       </td>
    </tr>
    <tr>
       <td>
          <a href="javascript:confirmClearDatabase()">清空数据库</a>
       </td>
    </tr>
    <tr>
       <td>
         <a href="deleteUser.jsp">删除用户</a>
       </td>
    </tr>
    
    </table>
    
    <% } %>
       
</body>
</html>
