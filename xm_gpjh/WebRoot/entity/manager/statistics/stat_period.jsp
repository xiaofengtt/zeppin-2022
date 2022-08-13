
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Date"%>
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
	String batch_id = request.getParameter("batch_id");
	String search = request.getParameter("search1");
	String sDate  = request.getParameter("sDate");
	String eDate  = request.getParameter("eDate");
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "";
	
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>

<html>
   <head>
      <title>学习时段分布</title>
   </head>
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="../js/calender.js"></script>	
</script>
<body>
<br><br>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
 <tr>
  <%
  	 sql = "select b.name as batch_name  from pe_bzz_batch b where b.id ='"+batch_id+"'";
  	 //System.out.println(sql);
  	 rs = db.executeQuery(sql);
		while(rs!=null&&rs.next())
		{ 
		   String batch_name = fixnull(rs.getString("batch_name"));
		   
  %>
    <td  class="text" align="center"><strong><%=batch_name %>学期学习时段分布情况</strong> &nbsp;  &nbsp; </td>
  </tr>
  	<tr><td align="center"><font font size="3" color="red"><%=sDate%></font>至<font size="3" color="red"><%=eDate%></font></td></tr>
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
		   var chart = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Line.swf", "ChartId", "600", "300", "0", "0");
		   var d = Math.random();
		   var temp = "<%=batch_id%>";
		   var sdate ="<%=sDate%>"+"|";
		   var edate ="<%=eDate%>"+"|";
		   var did = temp+"|"+sdate+edate+d;
		   chart.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_period.jsp?d="+did);		   
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
 
  <tr>
    <td class="text" align="center"><a href="#" onclick="javascript:window.history.back()">返回</a></td>
  </tr>
</table>

</body>
</html>

