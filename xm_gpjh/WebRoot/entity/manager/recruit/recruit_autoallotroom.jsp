<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>自动分配考场</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style><script>
	 function sub()
  {
   info.innerHTML='正在自动分配考场，请等待...';
   return true;
  }
</script>	

</head>
<!-- JavaScript functions -->
<body>
<form action="/entity/recruit/examstucourse_autoallotroom.action" onsubmit ="return sub();">
<!--内容区-->
<div id="main_content">
    <div class="content_title">自动分配考场</div>
    <div class="cntent_k">
   	  <div class="k_cc"  id="info">
	  	自动分配考场将会覆盖之前已分配的结果，例如：考场号，座位号等，确认分配吗？
		<br />
		<center><input type="submit" value="确认" /></center>
		
		
	  </div>
    </div>
</div>
<div class="clear"></div>
<div>
  <div> </div>
</div>
<!-- JavaScript onload -->
</form>
</body>
</html>
