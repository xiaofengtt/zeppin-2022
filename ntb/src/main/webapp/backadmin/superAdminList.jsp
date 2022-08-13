<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="stylesheet" href="css/adminList.css" />
		<script id="queboxTpl" type="text/template">
			<div class="list-item">
				<div class="list-item-hd">
					<table>
						<tr>
							<td class="td-left" width="50%">{{:roleName}}<span class="realname">{{:realname}}</span></td>
							<td width="50%" class="text-right">
								<a class="btn callOff" href="superAdminEdit.jsp?uuid={{:uuid}}">修改</a>
								<a class="btn" onclick="deleteThis(this)" data-url="../rest/backadmin/superAdmin/delete?uuid={{:uuid}}">删除</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="list-item-bd">
					<table class="text-center">
						<tr>
							<td class="td-left" width="33.33%">
								<div class="list-item-key">用户名：</div>
								<div class="list-item-value">{{if name!=''}}{{:name}}{{else}}未填写{{/if}}</div>
								<div class="list-item-key">状态：</div>
								<div class="list-item-value">{{:statusCN}}</div>
							</td>
							<td width="33.33%">
								<div class="list-item-key">手机：</div>
								<div class="list-item-value">{{if mobile!=''}}{{:mobile}}{{else}}未填写{{/if}}</div>
								<div class="list-item-key">邮箱：</div>
								<div class="list-item-value">{{if email!=''}}{{:email}}{{else}}未填写{{/if}}</div>
							</td>
							<td width="33.33%">
								<div class="list-item-key">创建者：</div>
								<div class="list-item-value">{{:creatorName}}</div>
								<div class="list-item-key">创建时间：</div>
								<div class="list-item-value">{{:createtimeCN}}</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100011" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a class="current">系统管理员管理</a></div>
					<a class="btn-add add addNew" href="../backadmin/superAdminAdd.jsp" style="margin-top:0px;">+&ensp;系统管理员</a>
					<div class="clear"></div>
				</div>
				<div class="main-contain">
					<div class="topDiv">
						<div class="leftDiv">
							<span>系统管理员（<span id="totalCount"></span>）</span>
						</div>
						<div class="rightDiv">
							<div class="input-group">
								<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
								<label class="input-group-addon" onclick="searchBtn()"></label>
							</div>
						</div>
					</div>
					<div id="queboxCnt"></div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/superAdminList.js" ></script>
		
	</body>
</html>
