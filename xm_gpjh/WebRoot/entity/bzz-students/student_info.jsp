<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style></head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：个人信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
   <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left"> <strong style="color:red"><s:property value="peTrainee.trueName"/></strong> 的个人信息: </td>
          </tr>
        </table></td>
      </tr> 
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" nowrap="nowrap" class="kctx_zi1"><strong>报 名 号：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peTrainee.userName"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peTrainee.trueName"/> </td>
        </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.enumConstByGender == null ">不详</s:if>
                                  		<s:property value="peTrainee.enumConstByGender.name"/></td>
      </tr> 
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">所属班级：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.peTrainingClass == null ">不详</s:if>
                                  		<s:property value="peTrainee.peTrainingClass.name"/>  </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">培训级别：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.enumConstByTrainingType == null ">不详</s:if>
                                  		<s:property value="peTrainee.enumConstByTrainingType.name"/>  </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">通信地址：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.address == null ">不详</s:if>
                                  		<s:property value="peTrainee.address"/>  </td>
      </tr>
      <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">邮政编码：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.zip == null ">不详</s:if>
                                  		<s:property value="peTrainee.zip"/>  </td>  
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;籍：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.peAreaByFkProvince == null ">不详</s:if>
                                  		<s:else><s:property value="peTrainee.peAreaByFkProvince.name"/>,
                                  		<s:property value="peTrainee.peAreaByFkCity.name"/>,
                                  		<s:property value="peTrainee.peAreaByFkPrefecture.name"/></s:else></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">是否在职：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.enumConstByFlagInJob == null ">不详</s:if>
					<s:property value="peTrainee.enumConstByFlagInJob.name"/></td>
      </tr>
      <tr>                            		
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2"><DIV align="center" class="STYLE1">联系电话：</DIV></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.phoneHome == null ">不详</s:if>
                                  		<s:property value="peTrainee.phoneHome"/>  </td>
     </tr>
     <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">移动电话：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.mobile == null ">不详</s:if>
                                  		<s:property value="peTrainee.mobile"/>  </td>  
      </tr>
      <tr>
       
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><strong>工作单位：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.workPlace == null ">不详</s:if>
                                  		<s:property value="peTrainee.workPlace"/> </td>                        		
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">电子邮件：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.email == null ">不详</s:if>
                                  		<s:property value="peTrainee.email"/>  </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">相&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;&nbsp;&nbsp;<img src='<s:property value="peTrainee.photoLink"/>?dt=<s:property value="time"/>' width="60" height="80" onerror="this.src='/error/d4.jpg'"/></td>
      </tr>	
    </table></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><a href="/entity/workspaceStudent/bzzstudent_toPassword.action"><img src="/entity/bzz-students/images/two/an_03.gif" width="71" height="23" border="0"></a></td>
	<td width="20"></td>
    <td><a href="/entity/workspaceStudent/bzzstudent_toModifyInfo.action"><img src="/entity/bzz-students/images/two/an_03_1.gif"  border="0"></a></td>
  </tr>
</table>
     
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>