<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer q_subscribeId = Utility.parseInt(request.getParameter("q_subscribeId"),new Integer(0));
Integer q_nonproductId = Utility.parseInt(request.getParameter("q_nonproductId"),new Integer(0));
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));
Integer q_checkFlag=Utility.parseInt(request.getParameter("q_checkFlag"),new Integer(0));
String q_status = Utility.trimNull(request.getParameter("q_status"));
String q_statusName = Utility.trimNull(request.getParameter("q_statusName"));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
String q_custName = Utility.trimNull(request.getParameter("q_custName"));

if(q_status.equals("")){
	q_status = "120101";
}

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
Iterator custiterator = null;

//url设置
sUrl = sUrl + "&q_subscribeId=" +q_subscribeId
            + "&q_nonproductId=" +q_nonproductId
            + "&q_nonproductName=" +q_nonproductName
            + "&q_custName=" +q_custName
            + "&cust_id=" +cust_id
            + "&q_checkFlag=" +q_checkFlag
            + "&q_status=" +q_status;

//获得对象及结果集
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();
SubscribeVO vo = new SubscribeVO();
CustomerVO custvo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();

vo.setSubscribe_id(q_subscribeId);
vo.setNonproduct_id(q_nonproductId);
vo.setNonproduct_name(q_nonproductName);
vo.setCust_name(q_custName);
vo.setCheck_flag(q_checkFlag);
vo.setStatus(q_status);
IPageList pageList = subscribe.query(vo,totalColumn,input_operatorCode,t_sPage,t_sPagesize);
list = pageList.getRsList();

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.nonProductSet",clientLocale)%>  </TITLE><!--非信托产品认购-->
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

function getSQuery(){
	var sQuery = document.theform.q_subscribeId.value;
	var sQuery = sQuery + "$" +document.theform.q_nonproductId.value;
	var sQuery = sQuery + "$" +document.theform.q_nonproductName.value;
	var sQuery = sQuery + "$" +document.theform.q_custName.value;
	var sQuery = sQuery + "$" +document.theform.cust_id.value;	
	var sQuery = sQuery + "$" +document.theform.pagesize.value;	
	var sQuery = sQuery + "$" +document.theform.q_checkFlag.value;	
	var sQuery = sQuery + "$" +document.theform.q_status.value;
	
	return sQuery;
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*新增*/
function newInfo(){	
	disableAllBtn(true);
	//var sQuery = getSQuery();
	var sQuery ='';
	location = 'subscribe_new.jsp?page=1&sQuery=' + sQuery;
}

/*修改*/
function showInfo(q_subscribeId, cust_id){
	disableAllBtn(true);
	var sQuery = getSQuery();	
	location = 'subscribe_edit.jsp?q_subscribeId='+q_subscribeId+'&cust_id='+cust_id+'&page=1&sQuery=' + sQuery;
}

/*删除功能*/
function delInfo()
{
	if(checkedCount(document.getElementsByName("q_subscribeId")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	if(sl_check_remove()){
		var url = 'subscribe_remove.jsp';
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	return false;
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);		
	var url = "subscribe.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_nonproductId=' + document.theform.q_nonproductId.value;
	var url = url + '&q_nonproductName=' + document.theform.q_nonproductName.value;
	var url = url + '&q_custName=' + document.theform.q_custName.value;
	var url = url + '&q_checkFlag=' + document.theform.q_checkFlag.value;
	var url = url + '&q_status=' + document.theform.q_status.value;
	
	location = url;	
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get"  action="subscribe.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
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
		<td  align="left" >
		<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_nonproductName" size="20" value="<%=q_nonproductName%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td colspan="3">
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="q_custName" size="62" value="<%=q_custName%>">
		</td>
	</tr>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_checkFlag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getCheckFlagOptions(q_checkFlag)%>
			</select>
		</td>
		<td  align="right"><%=LocalUtilis.language("class.htStatus",clientLocale)%> :</td><!--合同状态-->
		<td  align="left" >
			<select size="1" name="q_status" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getNonproductStatusOptions(q_status)%>
			</select>
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
		<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)<!--查询-->
</button>&nbsp;&nbsp;&nbsp; 		
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
		<!--新建-->
        <button type="button" class="xpbutton3"   accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> 
(<u>N</u>)</button>&nbsp;&nbsp;&nbsp; 
		<%}%>		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<!-- 删除 -->
        <button type="button" class="xpbutton3" accessKey=d name="btnCancel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>'	onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> 
(<u>D</u>)</button>
		<%}%>				
	</div>
	<br/>
</div>

<div style="margin-top:5px">
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<!--合同编号-->
        <td align="center" width="*">
        <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.q_subscribeId,this);">
        <%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
		<td align="center" width="60px"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--客户编号-->
		<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%></td><!--购买合同编号-->
		<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
		<td align="center" width="*"><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!-- 签署日期 -->
		<td align="center" width="*"><%=LocalUtilis.language("class.dzDate",clientLocale)%></td><!-- 缴款日期 -->
		<td align="center" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> </td><!--银行名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.bankBranchesName",clientLocale)%> </td><!--银行支行名称-->
		<td align="center" width="*"><%=LocalUtilis.language("class.htStatus",clientLocale)%> </td><!--认购合同状态-->
		<td align="center" width="30px"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		<td width="30px"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->
	</tr>
<%
//声明变量
String subscribe_bh;
Integer sign_date;
BigDecimal subscribe_money;
Integer pay_date;
String bank_id;
String bank_name;
String bank_sub_name;
String bank_acct;
String acct_name;
Integer check_flag;
String check_flag_name;
BigDecimal total_money = new BigDecimal(0);
Iterator iterator = list.iterator();

while(iterator.hasNext()){
	map = (Map)iterator.next();
	
	q_subscribeId = Utility.parseInt(Utility.trimNull(map.get("SUBSCRIBE_ID")),new Integer(0));
	cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	q_nonproductId = Utility.parseInt(Utility.trimNull(map.get("NONPRODUCT_ID")),new Integer(0));
	q_nonproductName = Utility.trimNull(map.get("NONPRODUCT_NAME"));
	subscribe_bh = Utility.trimNull(map.get("SUBSCRIBE_BH"));
	sign_date = Utility.parseInt(Utility.trimNull(map.get("SIGN_DATE")),new Integer(0));
	subscribe_money = Utility.parseDecimal(Utility.trimNull(map.get("SUBSCRIBE_MONEY")),new BigDecimal(0),2,"1");
	pay_date = Utility.parseInt(Utility.trimNull(map.get("PAY_DATE")),new Integer(0));
	bank_id = Utility.trimNull(map.get("BANK_ID"));
	bank_name = Utility.trimNull(map.get("BANK_NAME"));
	bank_sub_name = Utility.trimNull(map.get("BANK_SUB_NAME"));
	bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
	acct_name = Utility.trimNull(map.get("ACCT_NAME"));
	check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
	check_flag_name = Argument.getCheckFlagName(check_flag);
	q_statusName = Utility.trimNull(map.get("STATUS_NAME"));
	total_money = total_money.add(subscribe_money);
	//查询客户信息
	if(cust_id.intValue() != 0){
		custvo.setCust_id(cust_id);
		custvo.setInput_man(new Integer(888));
		custlist = cust.listCustomerLoad(custvo);
		custmap = (Map)custlist.get(0);

		if(custmap!= null){
		    q_custName = Utility.trimNull(custmap.get("CUST_NAME"));
		}
	}
	iCurrent++;
	iCount++;
%>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td align="left">
			<input type="checkbox" name="q_subscribeId" value="<%=q_subscribeId%>" class="flatcheckbox">
			<input type="hidden" name="cust_id" value=<%=cust_id%>>
		<%=q_nonproductName%></td>
		<td align="center"><%=cust_id%></td>
		<td align="left"><%=q_custName%></td>
		<td align="left"><%=subscribe_bh%></td>
		<td align="right"><%=Format.formatMoney(subscribe_money)%></td>
		<td align="right"><%=Format.formatDateCn(sign_date)%>&nbsp;</td>
		<td align="right"><%=Format.formatDateCn(pay_date)%>&nbsp;</td>
		<td align="left"><%=bank_name%></td>
		<td align="left"><%=bank_sub_name%></td>					
		<td align="center"><%=q_statusName%></td>			
		<td align="center"> 
		<%if(input_operator.hasFunc(menu_id, 102)){%>
			<a href="#" onclick="javascript:showInfo(<%=q_subscribeId%>,<%=cust_id %>)">
				<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("message.edit",clientLocale)%> " /><!--编辑-->
			</a>
		<%}%>	
		</td>
		<td align="center">
			<%if(input_operator.hasFunc(menu_id, 101)){%>
			<a href="#" onclick="javascript:delInfo()">
				<img src="<%=request.getContextPath()%>/images/recycle.gif" width="16" height="16" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " /><!--删除-->
			</a>
			<%}%>
		</td>		
	</tr>
<%

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
         <td align="center"></td>  
      </tr>           
<%}%>    	
	<tr class="trbottom">
		<td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		<td align="right"><%=Format.formatMoney(total_money)%></td>
		<td align="center" colspan="7"></td>  
	</tr>	
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%subscribe.remove();
cust.remove();
%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
