<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<script>
	var msg = '<s:property value="msg"/>';
	
	if(msg.indexOf("sucess")>=0){
		alert("修改成功");
		window.navigate("peTeacher_teacherInfo.action");
	}else{
		alert('修改失败');
		window.history.back();
	}
</script>