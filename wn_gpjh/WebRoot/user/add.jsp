<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>用户列表</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
		<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
		
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
		<div class="wrapper">
			<div class="header">
				<s:if test="loginkey == null">添加用户</s:if>
				<s:else>修改用户</s:else>
			</div>
			<s:if test="msg != null">
			<div class="fliter">
				<font color="red"><s:property value="msg"/></font>
			</div>
			</s:if>
			<div class="fliter">
				<form id="user_form" action="user_save.action" method="post">
					<fieldset>
					<div class="group">
						<span>项目名称：
							<select id="project" name="project" style="width:260px" <s:if test="loginkey != null">disabled</s:if>>
							<option value="0">所有项目</option>
							<s:set name="a" value="#parameters.project"/>
							<s:iterator value="projects">
								<option value="<s:property value="id"/>" <s:if test="%{id==project}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
						<span>承训单位：
							<select id="unit" name="unit" style="width:260px" <s:if test="loginkey != null">disabled</s:if>>
							<option value="0">所有承办单位</option>
							<s:iterator value="units" id="unit">
								<option value="<s:property value="id"/>" <s:if test="%{id == unit}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
					</div>
					<div class="group">
						<span>学科：
							<select id="subject" name="subject" style="width:260px;" <s:if test="loginkey != null">disabled</s:if>>
							<option value="0">所有学科</option>
							<s:iterator value="subjects" id="subject">
								<option value="<s:property value="id"/>" <s:if test="%{id == subject}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
						<span>计划人数：
							<input type="text" id = "plansum" name="plansum" value="<s:property value="loginkey.planSum"/>" style="width:240px" />
						</span>
					</div>
					
					<div class="group" style="margin-right:20px">
						<span>实到人数：
							<input type="text" id = "realsum" name="realsum" value="<s:property value="loginkey.realSum"/>" style="width:260px" / >
						</span>
					</div>
					<input type="hidden" name="id" value="<s:property value="loginkey.id"/>"/>
					<div class="search-btn group" style="width:150px;margin-left:300px">
						<s:if test="loginkey == null">
						<input type="hidden" name="action" value="add"/>
						<input type="submit" name="submit" value="添加">
						</s:if>
						<s:else>
						<input type="hidden" name="action" value="modify"/>
						<input type="submit" name="submit" value="修改">
						</s:else>
					</div>
					</fieldset>
				</form>
			</div>
		
		</div>
        <script language="javascript">
        	$( document ).ready(function() {

        		$("#user_form").submit(function(e){
	        		var project = $("#project").val();
	        		var unit = $("#unit").val();
	        		var subject = $("#subject").val();
	        		var plansum = $("#plansum").val();
	        		var realsum = $("#realsum").val();
	        		
	        		if(project == 0){
	        			alert("请选择项目");
	        			return false;
					}
	        		if(subject == 0){
	        			alert("请选择科目");
	        			return false;
	        		}
	        		if(unit == 0){
	        			alert("请选择承办单位");
	        			return false;
	        		}
	        		var strP=/^\d+(\.\d+)?$/; 
	        		if(!strP.test(plansum)){
	        		 	alert("计划人数不能为空且必须为数字");
	        			return false;
	        		} 
					if(!strP.test(realsum)){
	        		 	alert("实到人数不能为空且必须为数字");
	        			return false;
	        		}

	        		return true;

        		});
        		
        	});
        </script>
    </body>
</html>