<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临生殖健康咨询师培训网</title>
<script language="JavaScript" src="js/frame.js"></script>
<script type="text/javascript">
var times = 0;
function ForceWindow ()
{
  this.r = document.documentElement;
  this.f = document.createElement("FORM");
  this.f.target = "_blank";
  this.f.method = "post";
  this.r.insertBefore(this.f, this.r.childNodes[0]);
}
ForceWindow.prototype.open = function (sUrl)
{
  this.f.action = sUrl;
  this.f.submit();
}

var oPopup = window.createPopup(); 
function showMenu() 
{
	var oPopBody = oPopup.document.body; 
	oPopBody.style.backgroundColor = "lightyellow"; 
	oPopBody.style.border = "solid black 1px"; 
	str="<iframe src='computetime.jsp?times=10'>"; 
	str+="</iframe>"; 
	oPopBody.innerHTML=str; 
	oPopup.show(50, 50, 180, 50, document.body); 
}
//showMenu() ;
function setTime(time)
{
	times = parseInt(time);
}
function window.onunload(){
      var myWindow = new ForceWindow(); 
	  window.navigate("computetime.jsp?times="+times);
}

</script>
</head>
<frameset rows="70,*,50" frameborder="no" border="0" framespacing="0" name="TCB">
	<frame src="work/top.htm" name="banner" scrolling="NO" noresize>
	<frameset cols="212,*" frameborder="no" border="0" framespacing="0" name="TC">
		<frameset rows="300,*" frameborder="no" border="0" framespacing="0" name="TH">
			<frame src="work/tree.jsp" name="tree" scrolling="no" noresize>
			<frame src="work/help.jsp" name="help" scrolling="no" noresize>
	  </frameset>
		<frameset rows="0,*" frameborder="no" border="0" framespacing="0" name="MC">
			<frame src="work/menu.jsp" name="menu" scrolling="no"  noresize>
			<frame src="work/content.htm" name="content" scrolling="no" noresize>
	  <frame src="UntitledFrame-1"></frameset>
	</frameset>
	<frame src="work/bottom.htm" name="bottom" scrolling="NO" noresize>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
