<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/priv.jsp"%>
 <%!
//判断字符串为空的话，赋值为"不详"
String fixnull(String str)
{
    if(str == null || str.equals("") || str.equals("null"))
		str = "";
		return str;

}
%>	
<%
	String pageInt = fixnull(request.getParameter("pageInt"));
	String title = fixnull(request.getParameter("title"));
	String type = fixnull(request.getParameter("type"));
	String paperId = fixnull(request.getParameter("paperId"));
	String policyId = fixnull(request.getParameter("policyId"));
	try	{
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		int count = testManage.deletePaperPolicy(policyId);
	
	if (count < 1) {
%>
<script>
	alert("删除失败");
	window.history.back(-1);
</script>
<%
	} else {
%>
<script>
	alert("删除成功");
	window.navigate("testpaperpolicy_list.jsp?pageInt=<%=pageInt%>&title=<%=title%>&type=<%=type%>&paperId=<%=paperId%>");
</script>
<%
	}
	} catch(Exception e) { 
		out.print(e.getMessage());
		return;
	}
%>