<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="ctl00_Head1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>待回答的问卷</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<link type="text/css" rel="stylesheet" href="../css/que/NewDefault.css" />
	<link href="../css/que/q_12px.css" rel="stylesheet" type="text/css" />
	<link href="../css/que/newsolid_38.css" rel="stylesheet"
		type="text/css" />
	<script src="../js/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>

<body>

	<div id="divNotRun"
		style="height:100px; text-align:center; display:none;"></div>
	<div id="jqContent" class="" style="text-align: left; ">
		<div class="que-header2">
			<div class="inn">
				<h1>问卷调查系统</h1>
			</div>
		</div>
		<div id="headerCss"
			style="overflow-x: hidden; overflow-y: hidden; height:15px;">
			<div id="ctl00_header"></div>
		</div>

		<div id="mainCss">
			<div id="mainInner">
				<s:if test="%{onSubmitList.size()>0}">
					<s:iterator value="onSubmitList" id="ttr">
					
						<a href="../paper/paper_choosePaper.action?id=<s:property value="#ttr.getId()" />"><s:property
								value="#ttr.getProject().getName()	" /></a>
					</s:iterator>
				</s:if>
				<s:else>
					<h1 style="text-align:center;padding:100px 50px;font-size:30px;margin:0;">当前还没有测评问卷！</h1>
				</s:else>
			</div>
		</div>
		<div id="footercss">
			<div id="footerLeft"></div>
			<div id="footerCenter"></div>
			<div id="footerRight"></div>
		</div>
		<div style="clear: both; height: 10px;"></div>
		<div style="height: 20px;">&nbsp;</div>
	</div>

	<div style="clear:both;"></div>

	</div>


</body>
</html>