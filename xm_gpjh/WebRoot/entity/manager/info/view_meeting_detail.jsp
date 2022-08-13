<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>国培计划</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">

  </head>
  
  <body>
<div id="main_content">
    <div class="content_title">信息内容</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  		<table align="center" border="0" width="700">
				<tr><td height="25" align="center" class="topTitleText">会议名称:<s:property value="peMeeting.name"/> </td></tr>
				<tr><td   align="left">发布时间：<s:date name="peMeeting.createDate" format="yyyy-MM-dd"/> &nbsp; </td></tr>
				<tr><td   align="left">发布单位：<s:property value="peMeeting.peManager.peUnit.name" /> &nbsp; </td></tr>
				<tr><td   align="left">会议地点：<s:property value="peMeeting.place" /> &nbsp; </td></tr>
				<tr><td   align="left">会议时间：<s:date name="peMeeting.meetingDate" format="yyyy-MM-dd"/> &nbsp; </td></tr>
				<tr><td class="tab_dc_Mid" height="500"> 会议内容：</td><tr><td>&nbsp;&nbsp;&nbsp;</td></tr><tr><td><p><s:property value="peMeeting.note" escape="false"/>

</p></td></tr>
			</table>
	  </div>
	<div align="center"><img src="/entity/student/images/close.gif" title="关闭" style="cursor: hand" onclick="javascript:window.close();" ></div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>
