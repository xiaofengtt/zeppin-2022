
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
	String pageInt = request.getParameter("pageInt1");
	String batch_id = request.getParameter("id");
	String search = request.getParameter("search1");
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "";
	
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>
<html>
   <head>
      <title>性别分布</title>
   </head>
   <br> <br> <br> 
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
 <tr>
  <%
  	 sql = "select b.name as batch_name  from pe_bzz_batch b where b.id ='"+batch_id+"'";
  	 System.out.println(sql);
  	 rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{ 
		   String batch_name = fixnull(rs.getString("batch_name"));
		   
  %>
    <td  class="text" align="center"><strong><%=batch_name %>学期&nbsp;&nbsp;&nbsp;性别分布情况</strong></td>
    
  </tr>
  <%}
         db.close(rs);
   %>
  
   </table>
    <br>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">


  <tr> 
    <td  class="text" align="center"> <div id="chartdiv" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "400", "300", "0", "0");
		    var d = "<%=batch_id%>|"+Math.random();
		   chart.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_xingbie.jsp?d="+d);		   
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
 
  <tr>
    <td class="text" align="center"><a href="/entity/manager/statistics/stat_student.jsp?pageInt1=<%=pageInt%>&id=<%=batch_id%>&search1=<%=search%>">返回</a></td>
  </tr>
</table>
</body>
</html>

