<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer begin_date = Utility.parseInt(request.getParameter("begin_date"), new Integer(0));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(0)); 
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"),new Integer(0));
Integer query_type = Utility.parseInt(request.getParameter("query_type"),  new Integer(0) );
Integer input_man = Utility.parseInt(request.getParameter("input_man"),new Integer(0));
String customer_messager = Utility.trimNull(request.getParameter("customer_messager"));
String team_name = Utility.trimNull(request.getParameter("team_name"));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&begin_date="+begin_date;
tempUrl = tempUrl+"&end_date="+end_date;
tempUrl = tempUrl+"&cust_type="+cust_type;
tempUrl = tempUrl+"&query_type="+query_type;
tempUrl = tempUrl+"&input_man="+input_man;
tempUrl = tempUrl+"&customer_messager="+customer_messager;
tempUrl = tempUrl+"&team_name="+team_name;
sUrl = sUrl + tempUrl;
//辅助变量
input_bookCode = new Integer(1);
//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
vo.setCust_type(cust_type);
vo.setQuery_type(query_type);
vo.setInput_man(input_man);
vo.setCustomer_messager(customer_messager);
vo.setTeam_name(team_name);
IPageList pageList = customer.listNewCustAll(vo,t_sPage,t_sPagesize);
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
<title>新增客户信息</title><!--新增客户信息-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}
/*查看客户信息*/
/*查看客户详细*/
function showCustInfo(custid) {
	location.href = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
}

/*查询功能*/
function QueryAction(){		
	syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	var _pagesize = document.getElementsByName("pagesize")[0];
	//url 组合
	var url = "newcust_info_list.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&customer_messager=" + document.getElementById("customer_messager").getAttribute("value");
	var url = url + "&team_name=" + document.getElementById("team_name").getAttribute("value");
	var url = url + "&begin_date=" + document.getElementById("begin_date").getAttribute("value");
	var url = url + "&end_date=" + document.getElementById("end_date").getAttribute("value");
	window.location = url;
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:550px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right" colspan="3">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right">客户经理: </td>
			<td  align="left">
				<input name="customer_messager" id="customer_messager" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=customer_messager%>" />
			</td>		
		</tr>
		<tr>
			<td  align="right">团队名称: </td>
			<td  align="left">
				<input name="team_name" id="team_name" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=team_name%>" />
			</td>		
		</tr>
		<tr>
			<td align="right">开始日期 :</td>
			<td>
				<input TYPE="text" style="width:120" id="begin_date_picker" NAME="begin_date_picker" class=selecttext value="<%=Format.formatDateLine(begin_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="begin_date" id="begin_date" value="">	</td>
			<td align="right">结束日期 :</td>
			<td>
				<input TYPE="text" style="width:120" id="end_date_picker" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="end_date" id="end_date" value="">	</td>
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
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
	</div>
	<hr noshade color="#808080" size="1" width="100%">
</div>

<div valign="middle" align="center">
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*">新增日期</td>
			<td align="center" width="*">客户名称</td>
			<td align="center" width="*">收益金额 </td>
			<td align="center" width="*">首次认购日期</td>
			<td align="center" width="*">客户经理</td>
			<td align="center" width="*">推荐人</td>
			<td align="center" width="*">所属团队</td>
		</tr>
		<%
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="*"><%=Utility.trimNull(map.get("INPUT_DATE")) %> </td>
			<td align="left" width="*">
				<a href="javascript:showCustInfo(<%=map.get("CUST_ID") %>);"><%=Utility.trimNull(map.get("CUST_NAME")) %></a>
			</td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("TRADE_MONEY"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("START_DATE"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("SERVICE_MAN"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("RECOMMENDEN"))%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("TEAM_NAME")) %></td>
		</tr>
		<%}%>
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>       	
	         <tr class="tr0">
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>      
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>        
	            <td align="center"></td>  
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="11" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</TABLE>
</div>
<br>
<div>
	&nbsp;&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
