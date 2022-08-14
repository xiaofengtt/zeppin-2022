<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/base.css" />
	<link rel="stylesheet" href="css/safetyCenter.css" />
	<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>	
	
	<script src="js/jquery-1.11.1.js"></script>
	<script type="text/javascript" src="js/url.min.js"></script>
	<script src="./layer-v3.0.1/layer/layer.js"></script>
	<script>
		var type = url('?type');
		var uuid = url('?uuid');
		$(function(){
			if(type!="login"){
				getToken();
			}else{
				getLoginToken();
			}
			
		});
		var number="";
		if(type=="payroll"){
			number = "1";//发放薪资
		}else if(type=="bind"){
			number = "2";//绑定微信
		}else if(type=="remove"){
			number = "3";//解除绑定微信
		}else if(type=="login"){
			number = "4";//登录
		}
		/* 绑定微信取接口获取token */
		function getToken(){
			$.ajax({
		        url: '../rest/qcb/admin/bindWechatToken',
		        type: 'get',
		        async:false,
		        data: {
		        	"type":type,
		        	"qcbCompanyPayroll":uuid,
		            "timestamp":new Date().getTime() 
		        }
		    }).done(function(r) {
		    	if(r.status=="SUCCESS"){
		    		window.location.href="https://open.weixin.qq.com/connect/qrconnect?appid=wxaa2f0493bea2784c&redirect_uri=https://account.qicaibao.cc/qcb/safetyCenterWechat.jsp&response_type=code&scope=snsapi_login"+"&login_type=jssdk&self_redirect=default&state="+number
		    				+r.data;
		    	}else{
		    		layer.msg(r.message);
		    	}
		    }).fail(function(r){
		    	layer.msg("error", {
		            time: 2000
		        });
		    });
		}
		
		/* 登录微信取接口获取token */
		function getLoginToken(){
			$.ajax({
		        url: '../rest/qcb/admin/loginWechatToken',
		        type: 'get',
		        async:false,
		        data: {
		            "timestamp":new Date().getTime() 
		        }
		    }).done(function(r) {
		    	if(r.status=="SUCCESS"){
		    		window.location.href="https://open.weixin.qq.com/connect/qrconnect?appid=wxaa2f0493bea2784c&redirect_uri=https://account.qicaibao.cc/qcb/safetyCenterWechat.jsp&response_type=code&scope=snsapi_login"+
		    				"&login_type=jssdk&self_redirect=default&state="+number+r.data;
		    	}else{
		    		layer.msg(r.message);
		    	}
		    }).fail(function(r){
		    	layer.msg("error", {
		            time: 2000
		        });
		    });
		}

	</script>
</body>
</html>