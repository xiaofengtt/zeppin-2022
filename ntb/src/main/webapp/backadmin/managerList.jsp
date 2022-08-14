<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="css/paging.css">
		<link rel="stylesheet" href="css/managerList.css" />

		<script id="queboxTpl" type="text/template">
			<div class="list-manager">
				<div class="list-manager-left">
					<div class="list-manager-photo"><span>
						{{if photo==''}}
							<img class="manager-icon" src="img/fundManagement.jpg" />
						{{else}}
							<img class="manager-icon" src="..{{:photoUrl}}" />
						{{/if}}
					</span></div>
					<div class="list-manager-name">{{:name}}</div>
					<div class="list-manager-star">
						<span class="common-rating"><span class="rate-stars"></span></span>
					</div>
					<div class="list-manager-button">
						<a class="editBtn btn-edit" href="managerEdit.jsp?uuid={{:uuid}}">修改</a>
						<a class="deleteBtn btn-remove" onclick="deleteThis(this)" data-url="../rest/backadmin/manager/delete?uuid={{:uuid}}">删除</a>
					</div>
				</div>
				<div class="list-manager-right">
					<div class="list-manager-top">
						<span class="manager-mobile">{{if mobile==''}}未填写{{else}}{{:mobile}}{{/if}}</span>
						<span class="manager-graduation">{{if graduation==''}}未填写{{else}}{{:graduation}}{{/if}}</span>
						<span class="manager-education">{{if education==''}}未填写{{else}}{{:education}}{{/if}}</span>
						<span class="manager-email">{{if email==''}}未填写{{else}}{{:email}}{{/if}}</span>
						<span class="manager-workage">{{if workage==''}}未填写{{else}}{{:workage}}年{{/if}}</span>
					</div>
					<div class="list-manager-info">
						<ul>
							<li>类型：{{:type}}</li>
							<li>信息登记人：{{:creatorName}}</li>
							<li>身份证号：{{:idcard}}</li>
							<li>信息登记时间：{{:createtimeCN}}</li>
						</ul>
					</div>
					<div class="list-manager-resume">
						个人简历：<p class="resume">{{:resume}}</p>
					</div>
					<a class="resume_open" onclick="resumeOpen(this)">更多</a>
				</div>
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100014" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a class="current">主理人信息管理</a></div>
					<a class="btn-add add addNew" href="../backadmin/managerAdd.jsp" style="margin-top:0px;">+&ensp;添加主理人</a>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<div class="topDiv">
						<div class="leftDiv">
							<span>主理人用户（<span id="totalCount">0</span>）</span>
						</div>
						<div class="rightDiv">
							<div class="input-group">
								<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
								<label class="input-group-addon" onclick="searchBtn()"></label>
							</div>
						</div>
					</div>
					<div class="list-content" id="queboxCnt" style="padding-top:35px;">

					</div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/managerList.js" ></script>

	</body>
</html>
