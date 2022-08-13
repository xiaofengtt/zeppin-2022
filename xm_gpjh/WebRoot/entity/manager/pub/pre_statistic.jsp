<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>统计项</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">统计项</div>
    <div class="cntent_k">
   	  <div class="k_cc">
   	  <form action="/entity/studentStatus/prStudentOnStatus.action">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" >                           </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 
                                <tr valign="middle"> 
                                  <td width="200" height="90" align="left" class="postFormBox"><span class="name">请选择您要统计的项目：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  	<input type="checkbox" name="checkEdutype" >&nbsp;&nbsp;层次<br>
                                  	<input type="checkbox" name="checkMajor" >&nbsp;&nbsp;专业<br>
                                  	<input type="checkbox" name="checkGrade" >&nbsp;&nbsp;年级<br>
                                  	<input type="checkbox" name="checkSite" >&nbsp;&nbsp;站点<br>
                                  </td>
                           </tr>
                           </tr>
						 
                                <tr valign="middle"> 
                                  <td width="200" height="60" align="left" class="postFormBox"></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  	<input type="submit" value="确定"><br>
                                  	
                                  </td>
                           </tr>
                              
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>

