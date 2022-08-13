<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>原始数据页</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
		<div class="wrapper">
			<div class="header">
				<s:property value="paper.title"/>
			</div>
			<div class="nav">
				<ul class="clearfix">
					<li class="cur"><a href="#">原始数据</a></li>
					<li><a href="report_paper.action?pid=<s:property value="#parameters.pid"/>">每题统计</a></li>
					<li><a href="report_huizong.action?pid=<s:property value="#parameters.pid"/>">汇总统计</a></li>
					<li><a href="report_duibi.action?pid=<s:property value="#parameters.pid"/>">对比统计</a></li>
					<li><a href="report_wanchenglv.action?pid=<s:property value="#parameters.pid"/>">完成率分析</a></li>
				</ul>
			</div>
			<div class="fliter">
				<form action="report_summary.action" method="get">
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
						<input type="hidden" name="pid" value="<s:property value="#parameters.pid"/>"/>
						<div class="search-btn group" style="width:70px;margin-top:30px"><input type="submit" name="submit" value="筛选"></div>
					</fieldset>	
				</form>
			</div>	
			<div class="content">
				
				<div class="ctt_bd">
					
					<div class="section clearfix" style="border:0;padding:0;">
						<h2 style="color:#2084ca;margin-left:10px">共有<s:property value="pageInfo.totalRow"/>人次参与了此问卷</h2>
						<!-- <a class="export-btn" href="#">数据导出</a> -->
						<table class="table">
							<thead>
								<tr>
									<th width="133px">提交时间</th>                                                                
									<th width="92px">登录码</th>
									<th width="312px">项目</th>
									<th width="106px">承训单位</th>
									<th width="92px">学科</th>
									<th width="82px">IP</th>
									<th width="40px">详细</th>
								</tr>
							</thead>
							<tbody>
							<s:set name="flag" value="1" />
							<s:iterator value="submits" id="submit">
							<s:if test="%{#flag%2 == 0}"><tr class="even"></s:if><s:else><tr></s:else>
									<td><s:date name="#submit.creatTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td><s:property value="loginkey.id.trim()"/></td>
									
									<td><s:property value="infoDic.get(loginkey.id.trim())[0]"/></td>
									<td><s:property value="infoDic.get(loginkey.id.trim())[2]"/></td>
									<s:set name="sub" value="infoDic.get(loginkey.id.trim())[1]" />
									<td><s:if test="#sub.length()>6"><s:property value="#sub.substring(0,6)"/></s:if><s:else><s:property value="#sub"/></s:else></td>
									
									<td><s:property value="ip"/></td>
									<td><a href="report_detail.action?submit=<s:property value="id"/>&pid=<s:property value="#submit.paper.id"/>" target="_blank">查看</a></td>
								</tr>
								<s:set name="flag" value="#flag+1"/>
							</s:iterator>
							</tbody>
						</table>
					</div>
	
					
					<div class="pagiation">共<s:property value="pageInfo.totalPage"/>页 |<a href="report_summary.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=1"><首页</a> < <a href="report_summary.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.getPreviousPage()"/>">上一页</a>

					<s:iterator value="pageInfo.pageList()" id="pa">
						<s:if test="%{pageInfo.currentPage == #pa}">
						<s:property value="pa"/>
						</s:if>
						<s:else>
						<a href="report_summary.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pa"/>"><s:property value="pa"/></a>
						</s:else>
					</s:iterator>
					
					<a href="report_summary.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.getNextPage()"/>">下一页</a> > <a href="report_summary.action?pid=<s:property value="#parameters.pid"/>&subject=<s:property value="#parameters.subject"/>&project=<s:property value="#parameters.project"/>&unit=<s:property value="#parameters.unit"/>&page=<s:property value="pageInfo.totalPage"/>">尾页</a>>|</div>
					
					
				</div>
				
			</div>
		</div>
        
    </body>
</html>
