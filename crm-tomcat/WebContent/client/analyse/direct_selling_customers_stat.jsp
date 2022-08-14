<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="enfo.crm.customer.CustomerStatLocal"%>
<%@page import="enfo.crm.tools.EJBFactory"%>
<%@page import="enfo.crm.tools.Utility"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="enfo.crm.tools.Format"%>
<%@page import="enfo.crm.tools.FusionChartsGanerate"%>
<%@page import="enfo.crm.tools.FusionCharts"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
CustomerStatLocal local = EJBFactory.getCustomerStat();

Integer begin_date = Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentDate()));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(Utility.getCurrentDate()));
List list = local.queryDircetCustomerStat(begin_date,end_date,input_operatorCode);
List listSelling = local.querySellingStat(begin_date,end_date,input_operatorCode);


String[] totalValues = new String[list.size()];
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	totalValues[i] = Utility.trimNull(map.get("TEST2"));
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>直销客户图表统计分析</title>   
    <meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>
	<style>
		.headdarkbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
		
		.headbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
	</style>
	<script type="text/javascript">
		function show(flag)
		{
			if(flag==0)
			{
				eval("document.getElementById('chartView').style.display = 'none'");
				eval("document.getElementById('tableView').style.display = ''");
				eval("document.getElementById('cellView').style.display = 'none'");
			}
			else if(flag==1)
			{
				eval("document.getElementById('chartView').style.display = ''");
				eval("document.getElementById('tableView').style.display = 'none'");
				eval("document.getElementById('cellView').style.display = 'none'");
			}
			else 
			{
				eval("document.getElementById('chartView').style.display = 'none'");
				eval("document.getElementById('tableView').style.display = 'none'");
				eval("document.getElementById('cellView').style.display = ''");
				loadMenu();
			}
		}
		
		function query()
		{
			if(!sl_checkDate(document.theform.start_date_picker,"起始日期"))	return false;//起始日期
			if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))	return false;//结束日期
			syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
			document.theform.submit();
		}
		
		function loadMenu()
		{
			document.getElementsByName("sonIframe")[0].src="<%=request.getContextPath()%>/webreport/init.jsp?filename=http://<%=request.getServerName()%>:<%=request.getServerPort()%>/webreport/Cells/120.CLL";
		}
		$(document).ready(function(){
			$(".headbutton1").click(function(){
				$(this).addClass("headbutton").removeClass("headdarkbutton").siblings().removeClass("headbutton").addClass("headdarkbutton");
			});
		});
	</script>
  </head>
  
  <body class="BODY">
    	<form name="theform" action="direct_selling_customers_stat.jsp" method="get">
    		<table border="0" width="100%" cellspacing="1" cellpadding="4">
				<tr>
					<td class="page-title">
					    <b>直销客户图表统计</b>
					</td>
				</tr>		
			</table>
    		<table  border="0"  width="100%" cellspacing="1" cellpadding="2" class="table-select">
    			<tr>
    				<td>
    					起始日期:
						<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(begin_date)%>"> 
						<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
									onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);"> 
						<INPUT TYPE="hidden" NAME="start_date" value="">
    					&nbsp;&nbsp;结束日期:
    					<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>"> 
						<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
									onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"> 
						<INPUT TYPE="hidden" NAME="end_date" value="">
    					&nbsp;&nbsp;
    					<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton" onclick="javascript:query();">查询(<u>F</u>)</button>	
    				</td>
    				<td align="right">
    					<button type="button"  id="d0"  name="btnMenu" class="headbutton headbutton1" onclick="javascript:show(0)">&nbsp;表格展示</button><!--基本信息-->			
          				<button type="button"  id="d1"  name="btnMenu" class="headdarkbutton headbutton1" onclick="javascript:show(1)">&nbsp;图表展示</button><!--产品简介-->
          				<button type="button"  id="d1"  name="btnMenu" class="headdarkbutton headbutton1" onclick="javascript:show(2)">&nbsp;Cell展示</button>
          			</td>
    			</tr>
    		</table>
    		<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="popup-table" id="tableView">
				<tr class="tr1">
					<td class="tdh" align="center" width="15%" rowspan="3" >直销客户</td>
					<td class="tdh" width="60%">累计直销客户</td>
					<td class="tdh" width="15%"  align="right"><%=Utility.trimZero(totalValues[0].toString())%></td>
				</tr>
				<tr class="tr1">
					<td >存续直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[1])%></td>
				</tr>
				<tr class="tr1"> 
					<td>存续直销客户占比累计直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[2]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				
				<tr class="tr1">
					<td align="center" rowspan="3" >投资金额</td>
					<td>累计直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[3]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>存续直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[4]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>存续直销客户投资金额占比累计直销客户投资金额</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[5]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				
				<tr class="tr1">
					<td align="center" rowspan="2" >流失客户</td>
					<td>流失客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[6])%></td>
				</tr>
				<tr class="tr1">
					<td>流失客户占比累计直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[7]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				
				<tr class="tr1">
					<td align="center" rowspan="2" >重复认购客户</td>
					<td>重复购买直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[8])%></td>
				</tr>
				<tr class="tr1">
					<td>重复购买直销客户占比累计直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[9]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				
				<tr class="tr1">
					<td align="center" rowspan="4" >机构客户</td>
					<td>机构直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[10])%></td>
				</tr>
				<tr class="tr1">
					<td>机构直销客户占比累计直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[11]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>机构直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[12]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>机构直销客户投资金额占比累计直销客户投资金额</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[13]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				
				<tr class="tr1">
					<td align="center" rowspan="12" >按金额等级划分</td>
					<td>存续金额1000万以上直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[14])%></td>
				</tr>
				<tr class="tr1">
					<td>存续金额1000万以上直销客户占比存续直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[15]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>存续金额1000万以上直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[16]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>存续金额1000万以上直销客户投资金额占比存续直销客户投资金额</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[17]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>存续金额300-1000万直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[18])%></td>
				</tr>
				<tr class="tr1">
					<td>存续金额300-1000万直销客户占比存续直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[19]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>存续金额300-1000万直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[20]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>存续金额300-1000万直销客户投资金额占比存续直销客户投资金额</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[21]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>存续金额100-300万直销客户</td>
					<td align="right"><%=Utility.trimZero(totalValues[22])%></td>
				</tr>
				<tr class="tr1">
					<td>存续金额100-300万直销客户占比存续直销客户</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[23]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
				<tr class="tr1">
					<td>存续金额100-300万直销客户投资金额</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(totalValues[24]),new BigDecimal(0.00))) %></td>
				</tr>
				<tr class="tr1">
					<td>存续金额100-300万直销客户投资金额占比存续直销客户投资金额</td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(totalValues[25]),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2)%>%</td>
				</tr>
    		</table>
    		
    		<!-- 图表显示 -->
 <%
 	String[] ItemName = {"累计直销","存续直销","流失客户","重复认购直销客户"};
 	String[] ItemYear = new String[listSelling.size()];
 	String[][]value = new String[listSelling.size()][4];
 	for(int i=0;i<listSelling.size();i++)
 	{
 		Map mapSelling = (Map)listSelling.get(i);
 		ItemYear[i] = Utility.trimNull(mapSelling.get("SELLING_DATE"));
 		value[i][0] = Utility.trimNull(mapSelling.get("ACCUMULATIVE_TOTAL"));
 		value[i][1] = Utility.trimNull(mapSelling.get("SURVIVING_TOTAL"));
 		value[i][2] = Utility.trimNull(mapSelling.get("LOSS_TOTAL"));
 		value[i][3] = Utility.trimNull(mapSelling.get("REPEAT_TOTAL"));
		
 	}
 	FusionCharts Chart = new FusionCharts();
	FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
	String XMLStr = ChartCreator.generateMutiLine(ItemName,ItemYear,value,"","直销统计图","");
	//Create the chart - Column 3D Chart with data from Data/Data.xml
	int height = 31*10 + 50; 
	String chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/MSLine.swf","ddd",XMLStr,"",1100,height,false);
  %>   		
    		<table  border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" id="chartView" style="display: none">
    			<tr class="tr1">
    				<td width="100%"><%=Utility.trimNull(chartHTMLCode)%></td>
    			</tr>
    		</table>
    		<!-- cell显示 -->
    		<iframe id="cellView" style="display: none" name="sonIframe" width="100%" height="800px">
    			
    		</iframe>
    		<br><br><br>
    	</form>
  </body>
</html>
