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
	String search1 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search1")),"UTF-8"));
	String pageInt1= fixnull(request.getParameter("pageInt1"));
	String batch_id = fixnull(request.getParameter("id"));
	String batch_name = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("name")),"UTF-8"));
%>

<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>
<html>
   <head>
      <title>基础课程学习进度统计</title>
   </head>
   <br> <br> <br> 
<body>
<table width="98%" border="0" cellspacing="0" cellpadding="3" align="center">
<tr>
    <td valign="top" class="text" align="center"><strong><%=batch_name %>学期&nbsp;&nbsp;&nbsp;基础课程学习进度统计情况</strong></td>
  </tr>

  <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Column3D.swf",  "ChartId", "600", "350", "0", "0");
		   var d = "<%=batch_id%>|"+Math.random();
		   chart.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_progress_jichu.jsp?d="+d);		   
		   chart.render("chartdiv");
		</script> </td>
  </tr>
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
  <tr>
    <td class="text" align="center"><a href="/entity/manager/statistics/stat_progress.jsp?pageInt=<%=pageInt1%>&id=<%=batch_id%>&search=<%=java.net.URLEncoder.encode(search1,"UTF-8") %>&name=<%=java.net.URLEncoder.encode(batch_name,"UTF-8") %>">返回</a></td>
  </tr>
</table>
</body>
</html>