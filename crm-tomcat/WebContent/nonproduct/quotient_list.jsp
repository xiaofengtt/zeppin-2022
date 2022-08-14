<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer q_serialNo = Utility.parseInt(request.getParameter("q_serialNo"),new Integer(0));
Integer q_nonproductId = Utility.parseInt(request.getParameter("q_nonproductId"),new Integer(0));
String q_nonproductName = Argument.getNonProductNameByCustId(Utility.parseInt(request.getParameter("q_nonproductName"),new Integer(0)));
String q_custName = Utility.trimNull(request.getParameter("q_custName"));
String q_subscribeBh = Utility.trimNull(request.getParameter("q_subscribeBh"));
String q_status = Utility.trimNull(request.getParameter("q_status"));
Integer q_custId = Utility.parseInt(request.getParameter("q_custId"),new Integer(0));

//页面辅助参数
String tempUrl = "";
String[] totalColumn = new String[0];
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
Map map = null;
List custlist = null;
Map custmap =null;
Iterator iterator = null;
Integer n_nonproductName = Utility.parseInt(request.getParameter("q_nonproductName"),new Integer(0));
//url设置
sUrl = sUrl + "&q_serialNo=" +q_serialNo
            + "&q_nonproductId=" +q_nonproductId
            + "&q_nonproductName=" +n_nonproductName
            + "&q_custName=" +q_custName
            + "&q_subscribeBh=" +q_subscribeBh
            + "&q_status=" +q_status
            + "&q_custId=" +q_custId;

//获得对象及结果集
QuotientAffirmLocal quotientaffirm = EJBFactory.getQuotientAffirmLocal();
QuotientAffirmVO vo = new QuotientAffirmVO();
CustomerVO custvo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();

vo.setSerial_no(q_serialNo);
vo.setNonproduct_id(q_nonproductId);
vo.setNonproduct_name(q_nonproductName);
vo.setCust_name(q_custName);
vo.setSubscribe_bh(q_subscribeBh);
vo.setStatus(q_status);
IPageList pageList = quotientaffirm.queryQuotient(vo,totalColumn,input_operatorCode,t_sPage,t_sPagesize);
list = pageList.getRsList();

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.subscription",clientLocale)%> </TITLE><!--认购-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
/*启动加载*/
window.onload = function(){
initQueryCondition()};

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);		
	var url = "quotient_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_nonproductId=' + document.theform.q_nonproductId.value;
	var url = url + '&q_nonproductName=' + document.theform.q_nonproductName.value;
	var url = url + '&q_custName=' + document.theform.q_custName.value;
	var url = url + '&q_subscribeBh=' + document.theform.q_subscribeBh.value;
	var url = url + '&q_status=' + document.theform.q_status.value;
	
	location = url;	
}
/*查看客户详细*/
function showInfo(custid)
{
 	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
	//showModalDialog(url,'','dialogWidth:960px;dialogHeight:450px;status:0;	help:0');		
	location = url;
}
/*打印信封EMS*/
function printEms()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printEnvelopesTip",clientLocale)%> ！");//请选定要打印信封的记录
		return false;
	}
	<%if(user_id.intValue() == 2){%>//北国投格式
		window.open('/client/analyse/print_ems_bgt.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}else{%>
		window.open('print_ems.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
	<%}%>
}
/*打印信封*/
function printInfo()
{
	if(checkedCount(document.theform.cust_id) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.printEnvelopesTip",clientLocale)%> ！");//请选定要打印信封的记录
		return false;
	}
	window.open('/client/analyse/print.jsp?cust_id='+ checkedValue(document.theform.cust_id) ,'','left=250, top=250,width=638px,height=300px,scrollbars=yes,resizable=no, ');
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get"  action="quotient_list.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:520px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
  	 <button class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
   </td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td align="left">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_nonproductId" size="20" value="<%=q_nonproductId%>">
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td align=left>
			<select style="WIDTH:180px" size="1" name="q_nonproductName" onkeydown="javascript:nextKeyPress(this)" class="q_nonproductName">
				<!--请选择-->
                   <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
				<%=Argument.getNonproductName(q_nonproductId) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td colspan="3">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_custName" size="62" value="<%=q_custName%>">
		</td>
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> :</td><!--状态-->
		<td  align="left" >
			<select size="1" name="q_status" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 170px">
				<%=Argument.getQuotientStatusOptions(q_status)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td align="left">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_subscribeBh" size="20" value="<%=q_subscribeBh%>">
		</td>
	</tr>
	<tr>						
		<td  align="center" colspan=4>
		<button class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)<!--查询--></button>	&nbsp;&nbsp;
		<!--打印信封-->
		<button type="button" class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" title="<%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%> " onClick="javascript:printInfo();"><%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%> (<u>P</u>)</button>
		&nbsp;&nbsp;
        <button type="button" class="xpbutton4"id="btnPrint" name="btnPrint" title="<%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%> " onClick="javascript:printEms();"><%=LocalUtilis.language("message.printingEnvelopes",clientLocale)%> (EMS)</button>&nbsp;&nbsp;
	</div>
	<br/>
</div>
<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh" >
		<!--合同编号-->
        <td align="center" width="*">
	        <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
	        <%=LocalUtilis.language("class.productName",clientLocale)%> 
        </td><!--产品名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
		<td align="center" width="*"><%=LocalUtilis.language("class.benNum",clientLocale)%> </td><!--受益份额-->
		<td align="center" width="*"><%=LocalUtilis.language("class.benMoney",clientLocale)%> </td><!-- 受益金额 -->
		<td align="center" width="*"><%=LocalUtilis.language("class.startDate",clientLocale)%></td><!-- 起始日期-->
		<td align="center" width="*"><%=LocalUtilis.language("class.endDate",clientLocale)%></td><!-- 终止日期 -->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称--> 
		<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%></td><!-- 帐号 -->
		<td align="center" width="60px"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--认购合同状态-->
		
	</tr>
<%
//声明变量
Integer q_subscribeId = new Integer(0);
BigDecimal q_changeAmount;
BigDecimal q_changeMoney;
Integer q_startDate;
Integer q_endDate;
String q_statusName;
String q_bankId;
String q_bankName;
String q_bankSubName;
String q_bankAcct;
BigDecimal total_amount = new BigDecimal(0);
BigDecimal total_money = new BigDecimal(0);
iterator = list.iterator();

while(iterator.hasNext()){
	map = (Map)iterator.next();
	q_serialNo = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	q_subscribeId = Utility.parseInt(Utility.trimNull(map.get("SUBSCRIBE_ID")),new Integer(0));
	q_custId = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	q_custName = Utility.trimNull(map.get("CUST_NAME"));
	q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),new Integer(0));
	q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
	q_subscribeBh = Utility.trimNull(map.get("SUBSCRIBE_BH"));
	q_changeAmount = Utility.parseDecimal(Utility.trimNull(map.get("QUOTIENT_AMOUNT")),new BigDecimal(0),2,"1");
	q_changeMoney = Utility.parseDecimal(Utility.trimNull(map.get("QUOTIENT_MONEY")),new BigDecimal(0),2,"1");
	q_startDate = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
	q_endDate = Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0));
	q_status = Utility.trimNull(map.get("STATUS"));
	q_bankId = Utility.trimNull(map.get("BANK_ID"));
	q_bankName = Utility.trimNull(map.get("BANK_NAME"));
	q_bankSubName = Utility.trimNull(map.get("BANK_SUB_NAME"));
	q_bankAcct = Utility.trimNull(map.get("BANK_ACCT"));
	q_statusName = Utility.trimNull(map.get("STATUS_NAME"));
	total_amount = total_amount.add(q_changeAmount);
	total_money = total_money.add(q_changeMoney);
	//查询客户信息
	if(q_custId.intValue() != 0){
		custvo.setCust_id(q_custId);
		custvo.setInput_man(new Integer(888));
		custlist = cust.listCustomerLoad(custvo);
		custmap = (Map)custlist.get(0);

		if(custmap!= null){
		    q_custName = Utility.trimNull(custmap.get("CUST_NAME"));
		}
	}
%>
	<tr class="tr<%=(iCurrent % 2)%>" onDBlclick="javascript:showInfo('<%=custmap.get("CUST_ID")%>');">
		<td align="left">
			<input type="checkbox" name="cust_id" value="<%=custmap.get("CUST_ID")%>" class="flatcheckbox">
			<input type="hidden" name="q_custId" value=<%=q_custId%>>
			<input type="hidden" name="q_subscribeId" value=<%=q_subscribeId%>>
			<%=q_nonproductName%>
		</td>
		<td align="left"><%=q_custName%></td>
		<td align="left">&nbsp;&nbsp;<%=q_subscribeBh%></td>
		<td align="right"><%=Format.formatMoney(q_changeAmount)%></td>
		<td align="right"><%=Format.formatMoney(q_changeMoney)%></td>
		<td align="center"><%=q_startDate%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_endDate%>&nbsp;&nbsp;</td>
		<td align="left"><%=q_bankName%>&nbsp;&nbsp;</td>
		<td align="left"><%=q_bankSubName%>&nbsp;&nbsp;</td>
		<td align="left"><%=q_bankAcct%>&nbsp;&nbsp;</td>
		<td align="center"><%=q_statusName%>&nbsp;&nbsp;</td>
	</tr>
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
      <tr class="tr<%=i%2%>">
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
         <td align="center"></td>  
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="3"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		<td align="right"><%=Format.formatMoney(total_amount)%></td>
		<td align="right"><%=Format.formatMoney(total_money)%></td>
		<td align="center" colspan="6"></td>  </tr>		
	</tr>
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%quotientaffirm.remove();
cust.remove();
%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
