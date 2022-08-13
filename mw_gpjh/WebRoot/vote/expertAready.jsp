<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!doctype html>
<html>
<head>

<title>已经分配的专家</title>

<link rel="stylesheet" href="css/stylejudg.css">
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-i18n.js" type="application/x-javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="application/x-javascript"></script>

<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script type="text/javascript">
	function quxiao(id){
		
		$.post("exp_expertQXZhuanJ.action",{"id":id},function(r){
			if(r){
				window.location.reload();
			}
			else{
				alert("专家已经打分，不能取消");
			}
		});
	}
</script>
</head>

<body>

	<form>
		<div id="container">
			<div class="header"></div>
			<div class="main">

				<div class="table table-list">
					<h1 class="list-title">已经分配专家</h1>
					<table>
						<tbody>
							<tr>
								<td><strong>登陆Id</strong></td>
								<td><strong>姓名</strong></td>
								<td><strong>电话</strong></td>
								<td><strong>邮件</strong></td>
								<td><strong>操作</strong></td>
							</tr>
							<s:iterator value="alreadyList" id="yij">
								<tr>
									<td><s:property value="#yij[5]" /></td>
									<td><s:property value="#yij[0]" /></td>
									<td><s:property value="#yij[1]" /></td>
									<td><s:property value="#yij[2]" /></td>
									<td><a href="javascript:void(0)" onclick="quxiao('<s:property value="#yij[3]" />')"
										style="color: blue;">取消专家</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>

				</div>
			</div>
			<div class="footer">版权所有&copy;国培计划项目办</div>
		</div>
		<input type="hidden" name="edit" value="<s:property value="message"/>" />
		<input type="hidden" name="expid" value="<s:property value="expid"/>" />
	</form>
</body>
</html>
