<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer begin_date = Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentDate()));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(Utility.getCurrentDate()));
Integer q_stat_scope =Utility.parseInt(request.getParameter("q_stat_scope"), new Integer(0));

CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
CustomerStatVO vo = new CustomerStatVO();
vo.setStartTime(begin_date);
vo.setEndTime(end_date);
vo.setStatScope(q_stat_scope);
vo.setInputMan(input_operatorCode);
List list = custStatLocal.getAgeDistribution(vo);
custStatLocal.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.rgMoneyAnalyse",clientLocale)%></TITLE><!--客户认购金额分析-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
function query() {
	if(!sl_checkDate(document.theform.start_date_picker,"截止日期"))	return false;//起始日期
//	if(!sl_checkDate(document.theform.end_date_picker,"结束日期"))	return false;//结束日期
	syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
//	syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
	document.theform.submit();
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" >
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>
<table  border="0"  width="100%" cellspacing="1" cellpadding="2">
	<tr>
		<td>
			截止日期:
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(begin_date)%>"> 
			<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
						onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);"> 
			<INPUT TYPE="hidden" NAME="start_date" value="">
			&nbsp;&nbsp;&nbsp;
			<input type="radio" class="flatcheckbox" name="q_stat_scope" value="0" <%=q_stat_scope.intValue()==0?"checked":""%> />全部&nbsp;&nbsp;&nbsp;
			<input type="radio" class="flatcheckbox" name="q_stat_scope" value="1" <%=q_stat_scope.intValue()==1?"checked":""%> />本人&nbsp;&nbsp;&nbsp;
			<input type="radio" class="flatcheckbox" name="q_stat_scope" value="2" <%=q_stat_scope.intValue()==2?"checked":""%> />本部门
<!-- 
			&nbsp;&nbsp;结束日期:
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%//=Format.formatDateLine(end_date)%>"> 
			<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
						onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"> 
			<INPUT TYPE="hidden" NAME="end_date" value="">
 -->
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" onclick="javascript:query();">查询(<u>Q</u>)</button>	
		</td>
</table>
<div id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;">
<%
String[] ItemValue1 = new String[list.size()];
String[] ItemName1 = new String[list.size()];
String[] PercentageValue1 = new String[list.size()];

String[] ItemValue2 = new String[list.size()];
String[] ItemName2 = new String[list.size()];
String[] PercentageValue2 = new String[list.size()];

String fileName1 = "年龄段分布-人数占比";
String fileName2 = "年龄段分布-金额占比";
for (int i=0;i<list.size();i++) {
	Map map = (Map)list.get(i);
	
	if("0".equals(Utility.trimNull(map.get("END_AGE")))) {
		ItemName1[i] = Utility.trimNull("");
		ItemValue2[i] = Utility.trimNull("");
	}else if("100".equals(Utility.trimNull(map.get("END_AGE")))) {
		ItemName1[i] = Utility.trimNull("60岁以上");
		ItemValue2[i] = Utility.trimNull("60岁以上");
	}else{
		ItemName1[i] = Utility.trimNull("("+map.get("BEGIN_AGE"));
		ItemValue1[i] = Utility.trimNull(map.get("END_AGE")+")");
		ItemName2[i] = Utility.trimNull("("+map.get("BEGIN_AGE"));
		ItemValue2[i] = Utility.trimNull(map.get("END_AGE")+")");
	}	
	
	PercentageValue1[i] = Utility.trimNull(map.get("TOTAL"));		
	PercentageValue2[i] = Utility.trimNull(map.get("RG_MONEY"));
}

FusionCharts Chart = new FusionCharts();
FusionChartsGanerate ChartCreator = new FusionChartsGanerate();

String XMLStr1 = ChartCreator.ganeratePie3D(ItemName1,ItemValue1,PercentageValue1,fileName1,"","");

String XMLStr2 = ChartCreator.ganeratePie3D(ItemName2,ItemValue2,PercentageValue2,fileName2,"","");

//String XMLStr3 = ChartCreator.gridComponents();

int height = 31*10 + 27; 
String chartHTMLCode1 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr1,"ENFO",440,height,false);
String chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr2,"ENFO",440,height,false);

//String chartHTMLCode3 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/SSGrid.swf.swf","",XMLStr3,"ENFO",440,height,false);
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td align="left"><%=chartHTMLCode1%></td>
		<td><%=chartHTMLCode2%></td>
	</tr>
	<tr>
		<td>
			<table  border="0"  width="440" cellspacing="1" cellpadding="2"	class="tablelinecolor" height="">
				<tr class="trh">
					<td><%=LocalUtilis.language("class.AgeGroup",clientLocale)%></td><!--年龄组--> 
					<td><%=LocalUtilis.language("class.CustomerNuber",clientLocale) %></td><!--客户数-->  
					<td><%=LocalUtilis.language("class.ratio2",clientLocale) %></td><!-- 比例  -->					
				</tr>
<%
Iterator iterator1 = list.iterator();
int inCount = 0;
while(iterator1.hasNext()){		
	Map map = (Map)iterator1.next();				
%>	
				<tr class="tr<%=(inCount % 2)%>">
					<td>
<%	if("0".equals(Utility.trimNull(map.get("END_AGE")))) {
		out.print(" 无 ");
	}else if("100".equals(Utility.trimNull(map.get("END_AGE")))) {
		out.print("60岁以上");
	}else{
		out.print(Utility.trimNull(map.get("BEGIN_AGE")+" 至 "+map.get("END_AGE")));
	}%>
					</td>
					<td><%=Utility.trimNull(map.get("TOTAL")) %></td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(map.get("NUMBER_RATE")),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2,2)%>%</td>
				</tr>			
<%inCount++;} %>
			</table>		
		</td>
		<td>
			<table border="0" width="440" cellspacing="1" cellpadding="2" class="tablelinecolor" height="">
				<tr class="trh">
					<td><%=LocalUtilis.language("class.AgeGroup",clientLocale)%></td><!--年龄组--> 
					<td><%=LocalUtilis.language("class.rg_money",clientLocale) %></td><!--认购金额-->  
					<td><%=LocalUtilis.language("class.ratio2",clientLocale) %></td><!-- 比例  -->					
				</tr>
<%Iterator iterator = list.iterator();
inCount = 0;
while(iterator.hasNext()){
	Map map = (Map)iterator.next();				
%>	
				<tr class="tr<%=(inCount % 2)%>">
					<td>
<%	if("0".equals(Utility.trimNull(map.get("END_AGE")))) {
		out.print(" 无 ");
	}else if("100".equals(Utility.trimNull(map.get("END_AGE")))) {
		out.print("60岁以上");

	}else{
		out.print(Utility.trimNull(map.get("BEGIN_AGE")+" 至 "+map.get("END_AGE")));
	}%>					
					</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),null))%></td>
					<td align="right"><%=Utility.parseDecimal(Utility.trimNull(map.get("RG_RATE")),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2,2)%>%</td>
				</tr>			
<%} %>
			</table>		
		</td>
	</tr>
</table>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>