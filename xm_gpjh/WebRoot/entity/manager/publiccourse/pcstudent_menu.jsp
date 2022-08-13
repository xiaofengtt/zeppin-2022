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
				<div id="0110" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudent.action?search=true',this.id)" title="学生列表">学生列表</div>
				<div id="0111" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudent_register.action',this.id)" title="学生单个注册">学生单个注册</div>
				<div id="0112" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudent_registerupload.action',this.id)" title="学生注册信息导入">注册导入</div>
				<div id="0113" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudent_elective.action',this.id)" title="代学生选课">代学生选课</div>
				<div id="0113" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudent_registerupload.action',this.id)" title="学生选课列表">学生选课列表</div>
				<div id="0113" class="tab_menu2" onclick="top.openPage('/entity/publiccourse/pcstudentr.action',this.id)" title="学生订座">学生订座</div>
				
				<script>top.openPage('/entity/publiccourse/pcstudent.action?search=true','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>
