<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer q_func_id =Utility.parseInt(request.getParameter("q_func_id"),new Integer(100));
Integer q_stat_scope =Utility.parseInt(request.getParameter("q_stat_scope"), new Integer(0));

sUrl += "&q_func_id="+q_func_id+"&q_stat_scope="+q_stat_scope;

//ҳ���ø�������
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);

CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
CustomerStatVO vo = new CustomerStatVO();

vo.setFunc_id(q_func_id);
vo.setStartTime(new Integer(0));
vo.setStatScope(q_stat_scope);
vo.setInputMan(input_operatorCode);

IPageList pageList = custStatLocal.getStatCustomerLevel_page(vo,totalColumn,t_sPage,t_sPagesize);
List list = pageList.getRsList();

custStatLocal.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.lossCustomer",clientLocale)%></TITLE><!--�ͻ�Ǳ�ڿͻ���ʧ����-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
window.onload = function(){
		var q_func_id = document.getElementById("q_func_id").value;
		var tdId = "td"+q_func_id;
		document.getElementById(tdId).bgColor ="#99FFFF";

		var q_stat_scope = document.getElementById("q_stat_scope").value;
		document.getElementById("td_s"+q_stat_scope).bgColor ="#99FFFF";
	};

//�ı�ͻ����
function changeCustType(flag){
	document.getElementById("q_func_id").value=flag;
	QueryAction();
}

function changeStatScope(scope) {
	document.getElementById("q_stat_scope").value=scope;
	QueryAction();
}

function QueryAction(){
	//if(!sl_checkDate(document.theform.end_date_picker,"��ֹ����"))	return false;//��������
	//syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
	//syncDatePicker(document.theform.end_date_picker,document.theform.end_date);

	var url = "client_level_stat.jsp?page=1&pagesize="+ document.theform.pagesize.value+"&start_date="; //+document.theform.start_date.value;
	url += "&q_func_id="+ document.getElementById("q_func_id").value + "&q_stat_scope="+ document.getElementById("q_stat_scope").value;
	disableAllBtn(true);
	location.href = url;
}

function refreshPage(){
	QueryAction();
}
</SCRIPT>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" >
<input type="hidden" id="q_func_id" name="q_func_id" value="<%=q_func_id%>"/>
<input type="hidden" id="q_stat_scope" name="q_stat_scope" value="<%=q_stat_scope%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<div id="headDiv">
	<table class="table-select">
		<tr>
			<td>
				<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
					<tr style="background:F7F7F7;">
						<!--�ҳϿͻ���ϸ-->
			                  <td width="90px" align="center" id="td100" <%if (q_func_id.intValue()==100) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeCustType(100);" class="a2"><%=LocalUtilis.language("message.loyalCustomersList",clientLocale)%> </a></font></td>
						<!--Ǳ����ʧ�ͻ���ϸ-->
			                  <td width="120px" align="center" id="td200" <%if (q_func_id.intValue()==200) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeCustType(200);" class="a2"><%=LocalUtilis.language("message.defectingCustomersList",clientLocale)%> </a></font></td>
						<!--����ʧ�ͻ���ϸ-->
			                  <td width="110px" align="center" id="td300" <%if (q_func_id.intValue()==300) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeCustType(300);" class="a2"><%=LocalUtilis.language("message.losedCustomersList",clientLocale)%> </a></font></td>
						<!--�����ͻ�-->
			                  <td width="80px" align="center" id="td400" <%if (q_func_id.intValue()==400) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeCustType(400);" class="a2"><%=LocalUtilis.language("message.retentionCust",clientLocale)%> </a></font></td>
					</tr>
				</table>
			</td>
			<td>
				<table cellSpacing="1" cellPadding="2" width="150px" bgcolor="#CCCCCC">
					<tr style="background:F7F7F7;">				
						<td width="60px" align="center" id="td_s0" <%if (q_stat_scope.intValue()==0) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeStatScope(0);" class="a2">ȫ��</a></font></td>
						<td width="60px" align="center" id="td_s1" <%if (q_stat_scope.intValue()==1) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeStatScope(1);" class="a2">����</a></font></td>
						<td width="70px" align="center" id="td_s2" <%if (q_stat_scope.intValue()==2) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="javascript:changeStatScope(2);" class="a2">������</a></font></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="TableDiv" style="margin-top:5px;">
<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trh">
				<td align="center" width="8%"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--�ͻ����-->
				<td align="center" width="15%"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
				<td align="center" width="8%"><%=LocalUtilis.language("message.rg_times",clientLocale)%> </td><!--�Ϲ�����-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--�Ϲ����-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> </td><!--��˾�绰-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.telephone",clientLocale)%> </td><!--סլ�绰-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!--�ֻ�����-->
				<td align="center" width="*"><%=LocalUtilis.language("class.postAddress",clientLocale)%> </td><!--��ϵ��ַ-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.postcode",clientLocale)%> </td><!--��������-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--�ͻ�����-->
			</tr>
<%
			//�����ֶ�			
			Integer cust_id = new Integer(0);
			String cust_no = "";
			String cust_name="";
			Integer rg_times = new Integer(0);
			BigDecimal rg_money = new BigDecimal(0.00);
			String o_tel = "";
			String h_tel = "";
			String mobile = "";
			String post_address = "";
			String post_code = "";
			String service_man = "";

			int i;
			for (i=0; i<list.size(); i++){
				Map map = (Map)list.get(i);

				cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
				cust_no = Utility.trimNull(map.get("CUST_NO"));
				cust_name = Utility.trimNull(map.get("CUST_NAME"));
				rg_times = Utility.parseInt(Utility.trimNull(map.get("RG_TIMES")),new Integer(0));
				rg_money =Utility.parseDecimal( Utility.trimNull(map.get("TOTAL_RG_MONEY")),new BigDecimal(0.00),2,"1");
				o_tel = Utility.trimNull(map.get("O_TEL"));
				h_tel = Utility.trimNull(map.get("H_TEL"));
				mobile = Utility.trimNull(map.get("MOBILE"));
				post_address = Utility.trimNull(map.get("POST_ADDRESS"));
				post_code = Utility.trimNull(map.get("POST_CODE"));
				service_man = Utility.trimNull(map.get("SERVIC_MAN"));
%>
				<tr class="tr<%=i%2%>">
				  	 <td align="center"><%=cust_no%></td>
					 <td align="left"><input type="text" class="ednone" style="width:98%;" value="<%=cust_name%>" readonly></td>
					 <td align="center"><%=rg_times.intValue()>0?rg_times.toString():""%></td>
					 <td align="right"><%= Format.formatMoney(rg_money)%></td>
					 <td align="left"><input type="text" class="ednone" style="width:99%;" value="<%=o_tel%>" readonly></td>
					 <td align="left"><input type="text" class="ednone" style="width:99%;" value="<%=h_tel%>" readonly></td>
					 <td align="left"><input type="text" class="ednone" style="width:99%;" value="<%=mobile%>" readonly></td>
					 <td align="left"><input type="text" class="ednone" style="width:99%;" value="<%=post_address%>" readonly></td>
					 <td align="center"><%=post_code%></td>
					 <td align="center"><%=service_man%></td>
				</tr>
			<%}
			for (; i< pageList.getPageSize(); i++) {%>
		         <tr class="tr0">
		            <td align="center"></td>
		            <td align="center"></td>
				    <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		         </tr>
			<%}%>
			<tr class="trbottom">
				<!--�ϼ�--><!--��-->
                <td class="tdh" align="left" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
			</tr>
	</table>
	<div class="page-link">
		&nbsp;<%=pageList.getPageLink(sUrl,clientLocale)%>
	</div>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>