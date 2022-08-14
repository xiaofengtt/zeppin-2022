<%
String file_name = pageContext.getRequest().getParameter("file_name");
file_name = "R:\\"+file_name;
%>
<object
classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95">
<param name="FileName" value="/system/basedata/downloadattach.jsp?file_name=<%=file_name%>&name=1.wav" />
</object>
