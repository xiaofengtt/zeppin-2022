<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	
	<script src="js/jquery-1.11.1.js"></script>
	<script type="text/javascript" src="js/url.min.js"></script>
	<script src="./layer-v3.0.1/layer/layer.js"></script>
	<script type="text/javascript">

		var state = GetQueryString('state');
		var code = GetQueryString('code');
		var type = state.substring(0,1);
		state = state.substring(1,state.length);
		var typeCN;
		if(type=="1"){
			typeCN = "payroll";//发放薪资
		}else if(type=="2"){
			typeCN = "bind";//绑定微信
		}else if(type=="3"){
			typeCN = "remove";//解除绑定微信
		}else if(type=="4"){
			typeCN = "login";//登录
		}
		if(state!=""&&state){
			if(type!="4"){
				$.ajax({
			        url: '../rest/qcb/admin/bindWechatAuth',
			        type: 'get',
			        async:false,
			        data: {
			        	"token":state,
			        	"code":code,
			            "timestamp":new Date().getTime() 
			        }
			    }).done(function(r) {
			    	if(r.status=="SUCCESS"){
			    		if(type=="1"){
			    			layer.msg("发放成功", {
					            time: 1000
					        },function(){
					        	window.opener = null;
							    window.open('', '_self', '');
							    window.close();
					        });	
			    		}else if(type=="2"){
			    			layer.msg("绑定成功", {
					            time: 1000
					        },function(){
					        	window.close(); 
					        });	
			    		}else if(type=="3"){
			    			layer.msg("解除微信成功，请重新绑定", {
					            time: 1000
					        },function(){
					        	window.close();
					        });	
			    		}
			    			    		
			    	}else{
			    		layer.msg(r.message, {
				            time: 1000
				        },function(){
				        	window.location="../qcb/safetyCenterIframe.jsp?type="+typeCN;
				        });	
			    	}
			    }).fail(function(r){
			    	layer.msg("error", {
			            time: 2000
			        },function(){
			        	window.location="../qcb/safetyCenterIframe.jsp?type="+typeCN;
			        });
			    });
			}else{
				$.ajax({
			        url: '../rest/qcb/admin/loginWechatAuth',
			        type: 'get',
			        async:false,
			        data: {
			        	"token":state,
			        	"code":code,
			            "timestamp":new Date().getTime() 
			        }
			    }).done(function(r) {
			    	if(r.status=="SUCCESS"){			    		
			    			layer.msg("登录成功", {
					            time: 1000
					        },function(){
					        	window.close();
					        });				    			    		
			    	}else{
			    		layer.msg(r.message, {
				            time: 1000
				        },function(){
				        	window.location="../qcb/safetyCenterIframe.jsp?type="+typeCN;
				        });	
			    	}
			    }).fail(function(r){
			    	layer.msg("error", {
			            time: 2000
			        },function(){
			        	window.location="../qcb/safetyCenterIframe.jsp?type="+typeCN;
			        });
			    });
			}
			
		}else{
			layer.msg("鉴权失败");			
		}
		function GetQueryString(name) {
		    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		    var r = window.location.search.substr(1).match(reg);
		    if (r != null) return unescape(r[2]); return null;
		}		
	</script>
</body>
</html>