<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>

<%@ include file="pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str) {
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
int i=0;
String teachclass_id=fixnull(request.getParameter("teachclass_id"));
String status=fixnull(request.getParameter("status"));
String item=fixnull(request.getParameter("item"));



if(status.equals("1")){status="0";}else {
status="1";


}


//out.println("item="+item);
//out.println("status="+status);
//out.println("teachclass_id="+teachclass_id);

	i=teacherOperationManage.updateTeachItemStatus(item,status,teachclass_id);
%>


<%
	if(i>0){
%>
	<script>
		alert("修改成功!");
		window.navigate("teacher_courseItem.jsp?teachclass_id=<%=teachclass_id%>");
	</script>		
<%
	}
	else{
%>
<script>
		alert("修改不成功!");
		window.close();
	</script>


<%}%>