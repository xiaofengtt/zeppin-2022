<!-- 打印学生证 -->
<%@page import="java.io.*" pageEncoding="UTF-8"%>
<html>
  <body>
    <%
    DataOutput output = new DataOutputStream(response.getOutputStream());
  	byte[] bytes=(byte[])session.getAttribute("bytes");
  	response.setContentType("application/pdf");
	response.reset();
  	for(int k=0; k<bytes.length; k++){
		output.writeByte(bytes[k]);
	}
   %>
  </body>
</html>