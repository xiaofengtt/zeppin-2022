<%@ page language="java"  pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人资料</title>
<link href="/entity/teacher/css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">教师<s:property value="peTeacher.getTrueName()"/>的工作室</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" colspan="2" align="center" valign="middle" >您的个人资料</td>
                          </tr>
                          
						 
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getTrueName()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getEnumConstByGender().getName()"/></td>
                                </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    身&nbsp;&nbsp;份&nbsp;&nbsp;证：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getIdCard()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    电子邮件：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getEmail()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">固定电话(办公)：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getPhoneOffice()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getMobilephone()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">工作单位：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getWorkplace()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getEnumConstByFlagZhicheng().getName()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getNote()"/></td>
                                  <!-- <textarea name=note cols="40" class=selfScale disabled><s:property value = "peTeacher.getNote()"/></textarea></td> -->
                                </tr>
                           <tr>
                            <td height="50" align="center" colspan="2">
                             <a href="teacher_toEdit.action" ><img src="/entity/teacher/images/xg.gif" width="106" height="19" border="0"></a><a href="/entity/workspaceTeacher/teacher_toPassword.action"><img src="/entity/teacher/images/mm.gif" width="106" height="19" border="0"></a></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
