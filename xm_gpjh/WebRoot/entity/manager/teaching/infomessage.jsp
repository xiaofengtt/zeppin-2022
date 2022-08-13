<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>生殖健康咨询师培训网</title>
		<link href="/entity/bzz-students/css.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript"
			src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script language="javascript">
		Ext.onReady(function(){
				var pare = new Ext.ProgressBar({
					id:"par",
					renderTo:"div3",
					width:260,
					text:"数据处理中请等待。。。。"
				});
				pare.wait({
					interval: 100,
					increment: 50
				});
				Ext.Ajax.request({
					url:'<s:property value='message'/>',
					params: {   
                        id:'<s:property value='id'/>',   
                        status:'submit'
                   }, 
                   success: function(response,options){
                //	Ext.fly("par").update('处理完成!');
                   			 var responseArray = Ext.decode(response.responseText) ;
								    if(responseArray.success=='true'){
                   			 		Ext.MessageBox.show({   
					title:'信息提示',
					msg:'开课成功！！',
					buttons:{ok:'关闭'},
					fn:function(ok){
						if(ok=='ok'){
							window.history.back();
						}
					}
				});
                   			 		
                   	 }
                   } 
				});
			});
</script>
	</head>
	<body style="margin: 200px">
		<table align="center" border="0">
			<tr>
					<td>
						<div id="div3"></div>
					</td>
			</tr>
		</table>
	</body>
</html>