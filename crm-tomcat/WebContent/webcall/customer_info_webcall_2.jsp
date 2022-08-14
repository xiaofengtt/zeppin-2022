<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<base target="_self">
<style>
.bottomDiv{
	background-color : #A3B9D1;
	position:absolute;
	bottom:0px;
	HEIGHT: 25px;
	width : 100%;
	FONT-FAMILY:微软雅黑;
	font-size ：18pt;
}

.topDiv{
	background-color : #A3B9D1;
	HEIGHT: 25px;
	width : 100%;
	FONT-FAMILY :微软雅黑;
	font-size ：18pt;
}

.middleDiv{
	width : 100%;
	FONT-FAMILY :微软雅黑;
	font-size ：18pt;
	position:absolute;
	bottom:30px;
	top:30px;
	
}
</style>
<script language=javascript>

</script>
</HEAD>
<BODY class="BODY">
<form name="theform">
<div align="center"  class="topDiv"><font color="white">客户搜索</font></div>

<div class="bottomDiv"><%=input_operator.getOp_name()%></div>
</form>
</BODY>
</HTML>