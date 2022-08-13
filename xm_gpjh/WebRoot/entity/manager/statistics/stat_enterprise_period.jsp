
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
	 String search = fixnull(request.getParameter("search"));	
	String search1 = fixnull(request.getParameter("search1"));
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String c_search = fixnull(request.getParameter("c_search"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String name = fixnull(request.getParameter("name"));
	
	String edate = fixnull(request.getParameter("eDate"));
	String sdate = fixnull(request.getParameter("sDate"));
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "";
	
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>
<html>
   <head>
      <title>学习时段分布</title>
   </head>
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
 <tr>
  <%
  	sql = "select b.name as enterprise_name  from pe_enterprise b where b.id ='"+enterprise_id+"'";
  	 //System.out.println(sql);
  	 rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{ 
		   String enterprise_name = fixnull(rs.getString("enterprise_name"));
		   
  %>
    <td  class="text" align="center"><strong><%=enterprise_name %>&nbsp;&nbsp;&nbsp;学习时段分布情况</strong></td>
  </tr>
  <%}
         db.close(rs);
   %>
   </table>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
  <tr> 
    <td  class="text" align="center"> <div id="chartdiv" align="center"> 
        </div>
     <script type="text/javascript">
		   var chart = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Line.swf", "ChartId", "600", "300", "0", "0");
		    var d = Math.random();
		    var batch_id = "<%=batch_id%>";
		    var enterprise_id = "<%=enterprise_id%>";
		    var ed ="<%=edate%>|";
		    var sd ="<%=sdate%>|";
		    var temp = batch_id+"|"+enterprise_id+"|"+sd+ed+d;
		   chart.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_enterprise_period.jsp?d="+temp);	   
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
  	<tr>
          <td class="text" align="center"><a href="/entity/manager/statistics/stat_enterprise.jsp?pageInt1=<%=pageInt1%>&search1=<%=search1%>&pageInt=<%=c_pageInt%>&search=<%=c_search%>&id=<%=batch_id%>&name=<%=name%>">返回</a></td>
     </tr>
</table>
</body>
</html>

