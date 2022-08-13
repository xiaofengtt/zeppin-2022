<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>

hello test.jsp!

<s:property value="mystr" />
<%
	out.println(session.getAttribute("mystr").toString());
%>
<br>

<table border=1>
<tr>
	<td colspan=2>A</td>
	<td colspan=2>B</td>
	<td colspan=2>C</td>
	<td colspan=2>D</td>
	<td colspan=2>E</td>
</tr>	
<s:iterator value="list">
<tr>
	<td><s:property value="getId()" /></td>
	<td><s:property value="getName()" /></td>
	<td><s:property value="getB().getId()" /></td>
	<td><s:property value="getB().getName()" /></td>
	<td><s:property value="getB().getC().getId()" /></td>
	<td><s:property value="getB().getC().getName()" /></td>
	<td><s:property value="getD().getId()" /></td>
	<td><s:property value="getD().getName()" /></td>
	<td><s:property value="getD().getE().getId()" /></td>
	<td><s:property value="getD().getE().getName()" /></td>
</tr>
</s:iterator>
</table>

