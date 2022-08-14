<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
	<script src="../js/jquery-1.11.1.js"></script>
	<script src="../js/jquery.cookie.js"></script>
	<script src="../js/jquery-ui.js"></script>
	<script src="../js/url.min.js"></script>
	<script src="../js/jquery.colorbox.js"></script>
	<script src="../js/mustache.js"></script>
<!--<script type="text/javascript" src="http://cdn.webfont.youziku.com/wwwroot/js/wf/youziku.api.min.js"></script>-->
<link rel="stylesheet" href="../css/style.css">
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<style>

	#cboxContent{
		border-radius:10px;
	}
	
</style>
</head>
<body>
	<div id="header">
		<p class="headerTopLeft">新疆师范大学考务管理系统</p>
		<div class="headerTopRight">
			<div>
				<img class="headPortrait photo" alt="头像" src="../img/userBig.png" />
				<p id="loginname" class="teacherName">李康老师</p>
				<img class="triangle" src="../img/triangleBottom.png" />
				<div class="clear"></div>
			</div>
			<ul>
				<li class="modifyPassword"><a  data-fancybox-type="iframe" href="passwordEditiframe.jsp" class="btn-password" style="color:#E0615F;width:100%;height:100%;display:inline-block">修改密码</a></li>
				<li class="logOut"><a>退出登录</a></li>
			</ul>
			
		</div>
		
		<div class="headerTopCenter">
			<a class="WeChat">
				微信公众号
				<img src="../img/QQ20170825-0.jpg"/>
			</a>
		</div>
		
		<div class="clear"></div>
	</div>
	<script type="text/javascript"> 
//		$youziku.load(".teacherName", "c6da775181ca42f7a7c023bb366de971", "PingFangSC_R");
//		$youziku.draw(); 
		$(".headerTopRight").click(function(){
			if($(this).find("ul").css("display")=="none"){
				$(this).find("ul").show();
				$(this).find("img.triangle").attr("src","../img/triangleTop.png");
			}else{
				$(this).find("ul").hide();
				$(this).find("img.triangle").attr("src","../img/triangleBottom.png");
			}
		});
		$(".btn-password").colorbox({
			iframe : true,
			width : "400px",
			height : "320px",
			opacity : '0.1',
			overlayClose : false,
			escKey : true
		});
	</script>
</body>
</html>