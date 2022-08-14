<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.tools.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% String bats=Utility.trimNull(request.getParameter("bats"));
	String mobile=Utility.trimNull(request.getParameter("mobile"));
	String queryURI=enfo.crm.tools.Argument.getDictParamName(800211,"800215");
	if (queryURI==null || "".equals(queryURI)){
		out.print("���󣺶���ƽ̨�ķ��ͽ����ѯδ��ȷ����");
		return;
	}
	String url=queryURI+"&batch="+bats+"&phone="+mobile;
	String res=SmsClient.doGet(url,"UTF-8");
//out.println(res);
	List list=JsonUtil.parseJSON2ArrayList(res);
	Map map = null;
%>
<html>
<head>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

</head>
<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trtagsort">
		<td>�ֻ�����</td>
		<td>��������</td>
		<td>����ʱ��</td>
		<td>����ʱ��</td>
		<td>����״̬</td>
		<td>ͨ����Ӧ״̬</td>
		<td>ͨ����Ӧʱ��</td>
		<td>����״̬</td>
		<td>���ձ���ʱ��</td>
	</tr>
<%
	if (list==null || list.isEmpty()){
		out.println("</table>");
		return;
	}
	for (int i=0;i<list.size();i++){
		map=(Map)list.get(i);
		if (map==null) continue;
%>
	<tr class="tr<%=i%2%>">
		<td><%=map.get("phoneNumber") %></td>
		<td><%=map.get("messageContent") %></td>
		<td><%=Utility.trimNull(map.get("createTime")) %></td><!-- ����ʱ�� -->
		<td><%=Utility.trimNull(map.get("sendTime")) %></td><!-- ����ʱ�� -->
		<td><%=Utility.trimNull(map.get("sendFlag")) %></td><!-- ����״̬ -->
		<td><%=Utility.trimNull(map.get("spResponseFlag")) %></td><!-- ͨ����Ӧ״̬ -->
		<td><%=Utility.trimNull(map.get("spResponseTime")) %></td><!-- ͨ����Ӧʱ�� -->
		<td><%=Utility.trimNull(map.get("reportFlag")) %></td><!-- ����״̬ -->
		<td><%=Utility.trimNull(map.get("reportTime")) %></td><!-- ���ձ���ʱ�� -->
	</tr>
<%
	}
%>
</table>