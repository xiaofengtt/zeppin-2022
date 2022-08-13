<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TabMenu</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!--内容区-->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="6" bgcolor="278abe" style="background-color:#278abe;"></td>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0111" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitscoreline.action',this.id)" title="平台操作">平台操作</div>
				<div id="0110" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitscoreline_turnToAddSiteScoreList.action',this.id)" title="短信管理">短信管理</div>
				<div id="0112" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitscoreline_turnToCourseScoreList.action',this.id)" title="基础数据">基础数据</div>
				<div id="0113" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitmanage_turnToExamRecSearch.action',this.id)" title="招生管理">招生管理</div>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitmanage_turnToRecPublish.action',this.id)" title="学籍管理">学籍管理</div>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitmanage_turnToRecPublish.action',this.id)" title="学籍管理">学籍管理</div>
				<div id="0114" class="tab_menu2" onClick="top.openPage('/entity/recruit/recruitmanage_turnToRecPublish.action',this.id)" title="学籍管理">学籍管理</div>
				<script>top.openPage('/entity/recruit/recruitscoreline.action','0111');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>
