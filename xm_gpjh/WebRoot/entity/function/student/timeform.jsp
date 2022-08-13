<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="moni()">
	<script>
	function show()
	{
		showModalDialog("computetime.jsp?times=20");
	}
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
	function showWin()
	{
		//var myWindow = new ForceWindow(); 
	 	void window.open("computetime.jsp?times=10");
	}
	function moni()
	{
		document.getElementById("but").click();
	}
	</script>
	<input id=but type=button value="123" onclick="showWin()">
</body>
</html>