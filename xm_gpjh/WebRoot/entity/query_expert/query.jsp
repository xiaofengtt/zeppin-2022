<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教育部  财政部中小学教师国家级培训计划</title>
<link href="/entity/query_expert/css/zjcx.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function chek(){
	//alert('暂时不提供专家查询！');
	//return false;
	
	var u=document.getElementById('expname');
	//var s=document.getElementById('expsub');
	if(u.value==''){
		alert('姓名不能为空');
		u.focus();
		return false;
	}
	/*if(s.value==''){
		alert('学科不能为空');
		u.focus();
		return false;
	}*/
	return true;
}
</script>
</head>

<body style="text-align:center;">
	<div id="wrap">
	  <div id="header"></div>
	  <div id="main">
	 	<div id="main1"></div>
		<div id="main1_b"></div>
		<div id="main2">
			<div id="main2_l"></div>
			<div id="main2_2">
			<form id="queryform" action="/entity/first/userQueryTrainExpertAction_query.action" onsubmit="return chek()" method="post">
				<div id="login">
					<div class="loginbox"><span class="login_text">姓 名：</span>&nbsp;&nbsp;<span id="input"><input type="text" name="name" id='expname'/></span></div>
				
				<!-- 	<div class="loginbox"><span class="login_text">学 科：</span>
					<select name="subject" id='expsub'>
						<option value="">请选择</option>
						<s:iterator value="subjects" id="subject">
							<option value="<s:property value="#subject[0]"/>"><s:property value="#subject[1]"/></option>
						</s:iterator>
				      </select>
				    </div> -->
				    <br/>
					<div class="loginbox"><span class="login_btn">
					  <input type="image" name="imageField" src="/entity/query_expert/images/btn.jpg" />
					</span></div>
					</form>
				</div>
			</div>
			<div id="main2_3"></div>
		</div><div class="clear"></div>
		<div id="main3"></div> 
	  </div>
		<div id="bottom">主管单位：教育部师范教育司 主办单位：国培计划—中小学骨干教师培训项目执行办公室<br />
		  联系电话：010-58800182　 传真：010-58802946　 电子邮箱：xmb@gpjh.cn<br />
		  地址：北京市新街口外大街19号北京师范大学继续教育与教师培训学院<br />
	    京ICP备10031106号</div>
		
	</div>
</body>
<s:if test="msg!=null">
<SCRIPT type="text/javascript">
	alert('<s:property value="msg"/>');
</SCRIPT>
</s:if>
</html>
