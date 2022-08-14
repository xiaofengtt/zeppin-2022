<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
String q_cust_name =Utility.trimNull(request.getParameter("q_cust_name"));
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));
Integer q_log_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_log_type")),new Integer(1));

//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//获得对象
IntegralCalLocal local = EJBFactory.getIntegralCal();
IntegralCalVO vo = new IntegralCalVO();

vo.setStartDate(q_start_date);
vo.setEndDate(q_end_date);
vo.setCust_name(q_cust_name);
vo.setLog_type(q_log_type);

IPageList pageList = local.queryIntegralLog(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
int iCount = 0;
Map map = null;
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--积分日志表-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
	var q_log_type = document.getElementById("q_log_type").value;
	var tdId = "td"+q_log_type;
	
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*查询功能*/
function QueryAction(){		
	//开始日期
    if((document.getElementById("q_start_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%>"))){
		return false;
	}	
	//结束日期
    if((document.getElementById("q_end_date").getAttribute("value")!="")&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%>"))){
		return false;			
	}	

	syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);			
	syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);	
	
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url 组合
	var url = "integral_log_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&q_cust_name=" + document.getElementById("q_cust_name").getAttribute("value");
	var url = url + "&q_start_date=" + document.getElementById("q_start_date").getAttribute("value");
	var url = url + "&q_end_date=" + document.getElementById("q_end_date").getAttribute("value");
	var url = url + "&q_log_type=" + document.getElementById("q_log_type").getAttribute("value");

	window.location = url;
}

function changeLogType(flag){
	document.getElementById("q_log_type").value=flag;
	refreshPage();
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="q_log_type" name="q_log_type" value="<%=q_log_type%>" />
<div id="queryCondition" class="qcMain" style="display:none;width:480px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right">客户名称：</td>
			<td  align="left">
				<input type="text" name="q_cust_name" id="q_cust_name" value="<%= q_cust_name%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%>：</td><!-- 开始日期 -->
			<td  align="left">
				<input type="text" name="q_start_date_picker"  class=selecttext  id="q_start_date_picker"
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%>： </td><!-- 结束日期 -->
			<td  align="left">
				<input type="text" name="q_end_date_picker" class=selecttext   id="q_end_date_picker"
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
					<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
	</div>
	<hr noshade color="#808080" size="1" width="98%">
</div>
<div id="headDiv" style="margin-top:5px;margin-left:10px;">
	<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
			<tr style="background:F7F7F7;">
				<td width="100px" align="center" id="td1"><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeLogType(1);" class="a2">合同积分</a></font></td>
				<!--合同积分-->
				<td width="100px" align="center" id="td2"><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeLogType(2);" class="a2">基础积分</a></font></td>
				<!--基础积分-->
	</table>
</div>

<div align="center" style="margin-top:5px">
	<table border="0" <%if(q_log_type.intValue()==1) out.print("style='display:'");else out.print("style='display:none';");%> width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*">客户编号</td>
			<td align="center" width="*">客户名称</td>
			<td align="center" width="*">认购金额</td>
			<td align="center" width="*">变更积分</td>
			<td align="center" width="*">应用规则</td>
		</tr>
		<%
			Iterator iterator = list.iterator();
			BigDecimal amount = new BigDecimal(0);
			
			if(q_log_type.intValue()==1){
				while(iterator.hasNext()){
					iCount++;
					map = (Map)iterator.next();
					amount = Utility.parseDecimal(Utility.trimNull(map.get("AMOUNT")),new BigDecimal(0),2,"1");
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center" width="*"><%=Format.formatMoney(amount)%>&nbsp;&nbsp;</td>
			<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(map.get("AD_INTEGAL")),new Integer(0))%>&nbsp;&nbsp;</td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("RULE_NAME"))%></td>
		</tr>
		<%}
		}%>
		
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  	
	         <tr class="tr0">
	            <td align="center" width="*"></td>
	            <td align="center" width="*"></td>
	            <td align="center" width="*"></td>    
	            <td align="center" width="*"></td>   
	            <td align="center" width="*"></td>   
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="5" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</table>
	
	<table border="0" <%if(q_log_type.intValue()==2) out.print("style='display:'");else out.print("style='display:none';");%> width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*">客户编号</td>
			<td align="center" width="*">客户名称</td>
			<td align="center" width="*">变更积分</td>
			<td align="center" width="*">变更原因</td>
		</tr>
		<%			
		if(q_log_type.intValue()==2){
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
				amount = Utility.parseDecimal(Utility.trimNull(map.get("AMOUNT")),new BigDecimal(0),2,"1");
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(map.get("AD_INTEGAL")),new Integer(0))%>&nbsp;&nbsp;</td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("REMARK"))%></td>
		</tr>
		<%}
		}%>
		
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  	
	         <tr class="tr0">
	            <td align="center" width="*"></td>
	            <td align="center" width="*"></td>
	            <td align="center" width="*"></td>    
	            <td align="center" width="*"></td>   
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="4" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</table>
</div>
<br>
<div >
	&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>