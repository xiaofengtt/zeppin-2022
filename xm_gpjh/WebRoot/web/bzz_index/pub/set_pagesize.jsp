<%@ page contentType="text/html;charset=gb2312" %>
<%
	String temp_pagesize = request.getParameter("pagesize");
	String path = request.getParameter("path");
	
	if (temp_pagesize == null || temp_pagesize.length() == 0 || temp_pagesize.equals("null"))
	{
		temp_pagesize = "50";
	}
	
	session.putValue("pagesize", temp_pagesize);
%>
<script language="javascript">
<%
	String temp_pagesize2 = (String)session.getValue("pagesize");
	if (temp_pagesize.equals(temp_pagesize2))
	{
%>
	alert("���óɹ���");
	window.location='<%=path%>';
<%
	}
	else
	{
%>
	alert("����ʧ�ܣ�");
	history.back();
<%
	}
%>
</script>