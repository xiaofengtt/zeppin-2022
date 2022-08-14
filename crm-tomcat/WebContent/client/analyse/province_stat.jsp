<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer begin_date = Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentYear()+"0101"));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(Utility.getCurrentDate()));

CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
List list = custStatLocal.getProvinceStat(begin_date,end_date);
Map map = null;
%>
<HTML>
<HEAD>
<TITLE>客户按省级地区分布</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
function query()
{
	//if(!sl_checkDate(document.theform.start_date_picker,"起始日期"))	return false;//起始日期
	if(!sl_checkDate(document.theform.end_date_picker,"截止日期"))	return false;//结束日期
	//syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
	document.theform.submit();
}

</SCRIPT>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="province_stat.jsp">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>
<table  border="0"  width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td>
		<div class="btn-wrapper">
		<!-- 
			起始日期:
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%//=Format.formatDateLine(begin_date)%>"> 
			<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
						onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);"> 
			<INPUT TYPE="hidden" NAME="start_date" value="">
			&nbsp;&nbsp;-->截止日期:
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>"> 
			<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
						onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"> 
			<INPUT TYPE="hidden" NAME="end_date" value="">
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" onclick="javascript:query();">查询(<u>Q</u>)</button>	
		</div>
		</td>
</table>

<div  id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;">
<%
String[] xValue = new String[list.size()];
String[] ytemName = new String[list.size()];
String[] xValue2 = new String[list.size()];
for(int i=0;i<list.size();i++)
{	
	map = (Map)list.get(i);
	xValue[i] = Utility.trimNull(map.get("ACOUNT"));
	ytemName[i] = Utility.trimNull(map.get("TYPE_CONTENT"));
	xValue2[i] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0)).setScale(2));
}

FusionCharts Chart = new FusionCharts();
FusionChartsGanerate ChartCreator = new FusionChartsGanerate();

String XMLStr1 = ChartCreator.ganerateColumn3D(ytemName,xValue,"","","各地区总人数(单位:个)","");
String XMLStr2 = ChartCreator.ganerateColumn3D(ytemName,xValue2,"","","各地区总认购金额(单位:万)","");
//String XMLStr1 = ChartCreator.generateMutiColumnAndLine("","","","");

int height = 31*10 + 50; 
String chartHTMLCode1 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr1,"ENFO",900,height,false);
String chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr2,"",900,height,false);
//String chartHTMLCode1 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/MSColumnLine3D.swf","",XMLStr1,"ENFO",900,height,false);
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td align="left">
			<%=chartHTMLCode1%>
		</td>
	</tr>

	<tr>
		<td align="left">
			<%=chartHTMLCode2%>
		</td>
	</tr>
</table>
</div>
<br>
<%custStatLocal.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
