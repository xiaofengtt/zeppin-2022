<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>

<html class="no-js">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>每题意见汇总</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<link rel="stylesheet" href="css/style.css">


</head>
<body>
	<div class="wrapper">
		<div class="header">
			<s:property value="paper.title" />
		</div>


		<div class="content">
			<div class="ctt_hd">
				<s:property value="paper.name" />
			</div>
			<div class="ctt_bd">
				<h1>
					问卷名称：
					<s:property value="paper.title" />
				</h1>

				<s:iterator value="rcountList" id="r">
					<div class="section clearfix"><Lable enable="false" style="width=750px;height=60px"><s:property value="#r"/></Lable></div>
				</s:iterator>
</body>
</html>
