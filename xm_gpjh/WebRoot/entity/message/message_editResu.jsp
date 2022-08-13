<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师网络培训课堂</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="javascript" src="/js/JSKit.js"></script>
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
</style>
<script language="javascript" >
	var jk=new JSKit("/js/");
	jk.Import("Region");
	jk.Run(function(){
		var region=new Region();
		/*三级联动*/
		region.create({
			"name":"region3",
			"level":"3",
			"provinceObj":document.getElementById("sltProvince3"),
			"cityObj":document.getElementById("sltCity3"),
			"countryCityObj":document.getElementById("sltCountryCity3"),
			"defaultValue":"<s:property value="peTrainee.peAreaByFkProvince.name"/>|<s:property value="peTrainee.peAreaByFkCity.name"/>|<s:property value="peTrainee.peAreaByFkPrefecture.name"/>"
		});
		region.load("region3");
	});
	
	function show(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "block";
	}
	
	function hide(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "none";
	}
	
</script>
<script type="text/javascript">
<s:if test="succ">
alert('<s:property value="msg"/>');
window.close();
</s:if>
<s:else>
alert('<s:property value="msg"/>');
window.locations.href="/entity/basic/messageBoardAction_getMsgDetail.action?ids=<s:property value="ids"/>";
</s:else>
</script>
</head>
<body bgcolor="#E0E0E0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>&nbsp;</td>
      </tr>
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td>
</body>

</html>