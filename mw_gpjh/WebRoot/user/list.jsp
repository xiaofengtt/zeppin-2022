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

        <link rel="stylesheet" href="css/style.css">
        
    </head>
    <body>
		<div class="wrapper">
			<div class="header">
				用户列表
			</div>
			
			<div class="fliter">
				<form action="user_list.action" method="get">
					<fieldset>
					<div class="group">
						<span>项目名称：
							<select name="project" style="width:260px">
							<option value="0">所有项目</option>
							<s:set name="a" value="#parameters.project"/>
							<s:iterator value="projects">
								<option value="<s:property value="id"/>" <s:if test="%{id==project}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
						<span>承训单位：
							<select name="unit" style="width:260px">
							<option value="0">所有承办单位</option>
							<s:iterator value="units" id="unit">
								<option value="<s:property value="id"/>" <s:if test="%{id == unit}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
					</div>
					<div class="group" style="margin-right:20px">
						<span>学科：
							<select name="subject" style="width:230px;">
							<option value="0">所有学科</option>
							<s:iterator value="subjects" id="subject">
								<option value="<s:property value="id"/>" <s:if test="%{id == subject}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
					</div>
					<div class="search-btn group" style="width:150px;margin-left:280px">
						<input type="submit" value="筛选" style="margin-right:10px" name="submit"><a href="user_add.action"><input type="button" name="submit" value="添加"></a>
					</div>
					</fieldset>	
				</form>
			</div>
			
			<div class="content">
				
				<div class="ctt_bd">
					
					<div class="section clearfix" style="border:0;padding:0;">
						<h2 style="color:#2084ca;margin-left:10px">共有<s:property value="pageInfo.totalRow"/>人</h2>
						<!-- <a class="export-btn" href="#">数据导出</a> -->
						<table class="table">
							<thead>
								<tr>
									<th width="92px">登录码</th>
									<th width="295px">项目</th>
									<th width="116px">承训单位</th>
									<th width="92px">学科</th>
									<th width="133px">提交时间</th>
									<th width="47px">操作</th>
								</tr>
							</thead>
							<tbody>
							<s:set name="flag" value="1" />
							<s:iterator value="loginkeys" id="loginkey">
							<s:if test="id != 'xjGPJH58801324'">
							<s:if test="%{#flag%2 == 0}"><tr class="even"></s:if><s:else><tr></s:else>
									<td><s:property value="id"/></td>
									<td><s:property value="#loginkey.project.name"/></td>
									<td><s:property value="#loginkey.trainingUnit.name"/></td>
									<td><s:property value="#loginkey.subject.name"/></td>
									<td><s:date name="#loginkey.updateTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td><a href="user_modify.action?id=<s:property value="id"/>">修改</a></td>
								</tr>
								<s:set name="flag" value="#flag+1"/>
							</s:if>
							</s:iterator>
							</tbody>
						</table>
					</div>
					
					<div class="pagiation">共<s:property value="pageInfo.totalPage"/>页 |<a href="user_list.action?subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=1">>首页</a> < <a href="user_list.action?&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.getPreviousPage()"/>">上一页</a>

					<s:iterator value="pageInfo.pageList()" id="pa">
						<s:if test="%{pageInfo.currentPage == #pa}">
						<s:property value="pa"/>
						</s:if>
						<s:else>
						<a href="user_list.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pa"/>"><s:property value="pa"/></a>
						</s:else>
					</s:iterator>
					
					<a href="user_list.action?&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.getNextPage()"/>">下一页</a> > <a href="user_list.action?&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.totalPage"/>">尾页</a>>|</div>
					
					
				</div>
				
			</div>
		</div>
    </body>
</html>