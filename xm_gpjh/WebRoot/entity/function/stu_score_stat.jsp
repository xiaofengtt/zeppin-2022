<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="java.net.URLEncoder" %>
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>
<%@ include file="./pub/commonfuction.jsp"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String stuName = fixnull(request.getParameter("name"));
String stuReg_No = fixnull(request.getParameter("stureg_no"));
%>

<%

try{
	BasicActivityManage basicActivityManage = teacherOperationManage.createBasicActivityManage();	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="./images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="/entity/function/images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %></br> 学生成绩统计</td>
                            <td background="/entity/function/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
         <td></td> 
         <tr>
		 
            <tr> 
          <td valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center" bgcolor="#4582B1"> 
                <td align="center" class="title">学期</td>
                <td align="center" class="title">总人数</td>
                <td align="center" class="title">&lt;60</td>
                <td align="center" class="title">60-70</td>
                <td align="center" class="title">70-80</td>
                <td align="center" class="title">80-90</td>                
                <td align="center" class="title">90-100</td>
                <td align="center" class="title">及格率</td>                
                <td align="center" class="title">优秀率</td>                                                                
              </tr>
		<s:iterator value="list" status="status">
			<tr>
                <td align="center" class="td2"><s:property value="list[#status.index][0]"/></td>
                <td align="center" class="td2"><s:property value="list[#status.index][1]"/></td>
                <td align="center" class="td2"><s:property value="list[#status.index][2]"/></td>
                <td align="center" class="td2"><s:property value="list[#status.index][3]"/></td>
                <td align="center" class="td2"><s:property value="list[#status.index][4]"/></td>                
                <td align="center" class="td2"><s:property value="list[#status.index][5]"/></td>  
                <td align="center" class="td2"><s:property value="list[#status.index][6]"/></td>      
                <td align="center" class="td2"><s:property value="list[#status.index][7]"/>%</td>  
                <td align="center" class="td2"><s:property value="list[#status.index][8]"/>%</td>   
            </tr>  
         </s:iterator>    
            </table></td>
                    
	  </td>
  </tr>
</table>
</body>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>
</html>
