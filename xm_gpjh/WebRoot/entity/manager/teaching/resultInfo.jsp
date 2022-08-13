<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<s:if test="flag==false">
<script language="javascript">
		Ext.onReady(function(){
			
				Ext.MessageBox.show({   
					title:'信息提示',
					msg:'评估尚未开始或者已经结束!',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.history.back();
						}
					}
				});
			});
</script>
</s:if>
<s:else>
<script language="javascript">
	var url = "<%=basePath%>/entity/teaching/prBzzTchOpenCourseDetail.action?id=<s:property value='id'/>";
	window.location=url;
</script>
</s:else>
</head>
<body>
</body>
</html>