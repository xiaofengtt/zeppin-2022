
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@ page contentType="text/html;charset=UTF-8"%>

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
	String id = request.getParameter("id");
	String batch_id = request.getParameter("id");
	String search = request.getParameter("search1");
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql = "";
	
	sql = "select b.name as batch_name,tc.name as course_name  from pr_bzz_tch_opencourse t,pe_bzz_batch b,pe_bzz_tch_course tc where t.id='"+id+"' and t.fk_course_id = tc.id  and b.id = t.fk_batch_id ";
	
%>
<script language="JavaScript" src="<%=request.getContextPath() %>/entity/manager/statistics/Data/FusionCharts.js"></script>
<html>
   <head>
      <title>课程评估结果</title>
   </head>
   <br> <br> <br> 
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">  
<tr>
	<%
		 rs = db.executeQuery(sql);
		 while(rs!=null&&rs.next())
		{
			String batch_name = fixnull(rs.getString("batch_name"));
			String course_name = fixnull(rs.getString("course_name"));
			
	%>
	<td valign="top" class="text" align="center" ><strong><%=batch_name %>学期&nbsp;<%=course_name %>课程评估结果</strong></td>
	<%}
	  db.close(rs); %>
</tr>
</table>
<br>
<br>
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">  
  <tr> 
    <td  class="text" align="center"> <div id="chartdiv1" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart1 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart1.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access1.jsp?d="+d);		   
		   chart1.render("chartdiv1");
		</script> </td>
	  <td  class="text" align="center"> <div id="chartdiv2" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart2 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		   var d ="<%=id%>|"+Math.random();
		   chart2.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access2.jsp?d="+d);		   
		   chart2.render("chartdiv2");
		</script> </td>
	<td  class="text" align="center"> <div id="chartdiv3" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart3 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart3.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access3.jsp?d="+d);		   
		   chart3.render("chartdiv3");
		</script> </td></tr>
		<tr>
  <td class="text" align="center"><strong><font size = 2>1、教师在讲课思路清晰和重点突出方面</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>2、教师在课堂控制力和引导学员思考方面</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>3、授课内容的实用性</font></strong></td>
  </tr>
    <tr>
  <td class="text" align="center" colspan=3><br></td>
  </tr>
</table>	
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
 <tr> 
   <td  class="text" align="center"> <div id="chartdiv4" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart4 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart4.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access4.jsp?d="+d);		   
		   chart4.render("chartdiv4");
		</script> </td>
	  <td  class="text" align="center"> <div id="chartdiv5" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart5 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart5.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access5.jsp?d="+d);		   
		   chart5.render("chartdiv5");
		</script> </td>
	<td  class="text" align="center"> <div id="chartdiv6" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart6 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart6.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access6.jsp?d="+d);		   
		   chart6.render("chartdiv6");
		</script> </td>
		
  </tr>
   <tr>
  <td class="text" align="center"><strong><font size = 2>4、授课内容的启发性</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>5、讲义对本门课程学习的帮助</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>6、您对本门课程的整体满意度</font></strong></td>
  </tr>
    <tr>
  <td class="text" align="center" colspan=3><br></td>
  </tr>
  </table>		

<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
  
  <tr> 
    <td  class="text" align="center"> <div id="chartdiv7" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart7 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart7.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access7.jsp?d="+d);		   
		   chart7.render("chartdiv7");
		</script> </td>
	  <td  class="text" align="center"> <div id="chartdiv8" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart8 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart8.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access8.jsp?d="+d);		   
		   chart8.render("chartdiv8");
		</script> </td>
	<td  class="text" align="center"> <div id="chartdiv9" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart9 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		   var d ="<%=id%>|"+Math.random();
		   chart9.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access9.jsp?d="+d);		   
		   chart9.render("chartdiv9");
		</script> </td>
		
  </tr>
   <tr>
  <td class="text" align="center"><strong><font size = 2>7、本门课程设置的必要性（不考虑教师的授课水平）</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>8、本门课程设置时间的长短</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>9、本门课程的深浅程度</font></strong></td>
  </tr>
   <tr>
  <td class="text" align="center" colspan=3><br></td>
  </tr>
  </table>
  
  
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
  
  <tr> 
    <td  class="text" align="center"> <div id="chartdiv10" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart10 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		   var d ="<%=id%>|"+Math.random();
		   chart10.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access10.jsp?d="+d);		   
		   chart10.render("chartdiv10");
		</script> </td>
	  <td  class="text" align="center"> <div id="chartdiv11" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart11 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		    var d ="<%=id%>|"+Math.random();
		   chart11.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access11.jsp?d="+d);		   
		   chart11.render("chartdiv11");
		</script> </td>
	<td  class="text" align="center"> <div id="chartdiv12" align="center"> 
        </div>
      <script type="text/javascript">
		   var chart12 = new FusionCharts("<%=request.getContextPath() %>/entity/manager/statistics/fusioncharts/Pie2D.swf", "ChartId", "300", "200", "0", "0");
		   var d ="<%=id%>|"+Math.random();
		   chart12.setDataURL("<%=request.getContextPath() %>/entity/manager/statistics/chart/chart_access12.jsp?d="+d);		   
		   chart12.render("chartdiv12");
		</script> </td>
		
  </tr>
   <tr>
  <td class="text" align="center"><strong><font size = 2>10、本门课程是否适合您的岗位</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>11、课件形式是否适合课程学习</font></strong></td>
   <td class="text" align="center"><strong><font size = 2>12、作业与自测的难易程度</font></strong></td>
  </tr>
  </table>
  
<table width="100%" border="0" cellspacing="0" cellpadding="3" align="center">  
  <tr>
    <td valign="top" class="text" align="center">&nbsp;</td>
  </tr>
 
  <tr>
    <td class="text" align="center"><a href="javascript:history.back(-1)" >返回</a></td>
  </tr>
</table>
</body>
</html>

