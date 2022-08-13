<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<% response.setHeader("expires", "0"); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网-学生工作室</title>

<script>
var tempBigMenuID;
function openPage(url,objID)
{
	if(tempBigMenuID)
	{
		changeBorder(objID);
	}
	else
	{
		if(self.frames["submenu"] && self.frames["submenu"].document.getElementById(objID))
			self.frames["submenu"].document.getElementById(objID).className = "tab_menu1";
	}
	tempBigMenuID = objID;
	self.frames["subcontent"].location = url;
}

function changeBorder(objID)
{
	if(self.frames["submenu"] && self.frames["submenu"].document.getElementById(objID))
	{
		if(self.frames["submenu"].document.getElementById(tempBigMenuID))
			self.frames["submenu"].document.getElementById(tempBigMenuID).className  = "tab_menu2";
		self.frames["submenu"].document.getElementById(objID).className = "tab_menu1";
	}
	else
	{
		return;
	}
}
</script>

</head>
<frameset rows="47,*" frameborder="no" border="0" framespacing="0">
	<frame src="/entity/workspaceStudent/prExamReserver_toExamReserverMenu.action" name="submenu" scrolling="no" noresize>
	<frame src="/entity/workspaceStudent/prExamReserver_courseList.action" name="subcontent" scrolling="auto" noresize>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
