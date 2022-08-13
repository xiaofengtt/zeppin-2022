<%
  String admin = (String)session.getValue("admin");
  if(admin!=null&&admin.equals("1")){
  }else{
  %>
  <script language="javascript">
        window.parent.location.href="<%=request.getContextPath()%>/sso/admin/admin_adminLogin.action";
  </script>
  <%
  }
%>