<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
int from_flag_benifitor=Utility.parseInt(request.getParameter("from_flag_benifitor"),Utility.parseInt(request.getParameter("from_flag"),1));
Integer contract_id = Utility.parseInt(request.getParameter("contract_id"), null);

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
String disabled = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
List list_contract = null;
Map map_contract = null;
List list_ben = null;
Map map_ben = null;

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&sQuery="+sQuery;
tempUrl = tempUrl+"&from_flag_benifitor="+from_flag_benifitor;
tempUrl = tempUrl+"&contract_id="+contract_id;
sUrl = sUrl + tempUrl;

//声明合同信息
String contract_bh = "";
String ht_status = "";
Integer product_id = null;
Integer check_flag = new Integer(0);

//获得对象及结果集
BenifitorLocal benifitor = EJBFactory.getBenifitor();
ContractLocal contract = EJBFactory.getContract();

BenifitorVO vo_ben = new BenifitorVO();
ContractVO vo_cont = new ContractVO();

//获得合同信息
vo_cont.setSerial_no(contract_id);
list_contract = contract.load(vo_cont);

if(list_contract.size()>0){
	map_contract = (Map)list_contract.get(0);
}

if(map_contract!=null){
	contract_bh = Utility.trimNull(map_contract.get("CONTRACT_BH"));
	product_id = Utility.parseInt(Utility.trimNull(map_contract.get("PRODUCT_ID")),new Integer(0));
	check_flag =  Utility.parseInt(Utility.trimNull(map_contract.get("CHECK_FLAG")),new Integer(0));
	ht_status = Utility.trimNull(map_contract.get("HT_STATUS"));
	
	if(check_flag.intValue()!=1 || !ht_status.equals("120101")){
		disabled = " disabled ";
	}	
}

//获得受益人信息
vo_ben.setProduct_id(product_id);
vo_ben.setContract_bh(contract_bh);
vo_ben.setInput_man(input_operatorCode);
vo_ben.setBook_code(input_bookCode);
vo_ben.setFlag(new Integer(1));

list_ben = benifitor.query(vo_ben);
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.benefiterCheckList",clientLocale)%> </TITLE>
<!--受益人列表-->
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
/*新建*/
function newInfo(){
	var contract_bh = document.getElementsByName("contract_bh")[0].value;
	var product_id = document.getElementsByName("product_id")[0].value;		
	var url = 'benifiter_add.jsp?check_flag=1&contract_bh='+contract_bh+'&product_id='+product_id;
	
	if(showModalDialog(url, '', 'dialogWidth:650px;dialogHeight:450px;status:0;help:0') != null){
		sl_update_ok();
		location.reload();
	}
}
/*编辑*/
function showInfo(serialno, checkflag){
	var contract_bh = document.getElementsByName("contract_bh")[0].value;
	var product_id = document.getElementsByName("product_id")[0].value;	
	var url = 'benifiter_add.jsp?serial_no='+serialno+'&contract_bh='+contract_bh+'&product_id='+product_id+'&check_flag='+checkflag;
	
	if(showModalDialog(url, '', 'dialogWidth:650px;dialogHeight:450px;status:0;help:0') != null){
		sl_update_ok();
		location.reload();
	}
	
	showWaitting(0);
}

/*删除*/
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action = "benifiter_del.jsp";
		document.theform.submit();
	}
}

/*刷新*/
function refreshPage(){
	StartQuery();
}

/*查询*/
function StartQuery(){	
	location = 'benifiter.jsp?contract_id=<%=contract_id%>&page=1&pagesize=' + document.theform.pagesize.value+ '&from_flag_benifitor='+'<%=from_flag_benifitor%>' + '&sQuery='+'<%=sQuery%>';
}

/*返回*/
function op_return(){
	tempArray = '<%=sQuery%>'.split('$');
	location = 'subscribe_list.jsp?page=1&q_productId='+tempArray[0]+'&q_productCode='+tempArray[1]+'&q_cust_name='+tempArray[2]+'&query_contract_bh='+tempArray[3]+'&q_pre_flag='+tempArray[4]+'&pagesize='+tempArray[5]+'&q_check_flag='+tempArray[6];
}

function getTransactionCustomer1(cust_id,readonly){
    var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:620px;status:0;help:0;');
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type=hidden name="contract_id" value="<%=contract_id%>">
<input type=hidden name="sQuery" value="<%=sQuery%>">
<input type=hidden name="from_flag_benifitor" value="<%=from_flag_benifitor%>">
<input type=hidden name="contract_bh" value="<%=contract_bh%>">
<input type=hidden name="product_id" value="<%=product_id%>">

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
 		<%if (input_operator.hasFunc(menu_id, 100)){%>
		<button type="button"   class="xpbutton3" accessKey=n name="btnNew" <%=disabled%> title="<%=LocalUtilis.language("message.new",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)
		</button>&nbsp;&nbsp;&nbsp;<!--新建-->
		<%}%>
		<%if (input_operator.hasFunc(menu_id, 101)){%>
		<button type="button"   class="xpbutton3" accessKey=d name="btnCancel" <%=disabled%> title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:delInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)
		</button>&nbsp;&nbsp;&nbsp;<!--删除-->
		<%}%>
		<button type="button"   class="xpbutton3" accessKey=b name="btnCancel" title="<%=LocalUtilis.language("message.back",clientLocale)%> " onclick="javascript:disableAllBtn(true);op_return();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
		</button>&nbsp;&nbsp;&nbsp;<!--返回-->			
		<br/>		
	</div>
</div>
<br/>
<div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class="trh">
		<td align="center" height="25"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);"><%=LocalUtilis.language("class.name",clientLocale)%> 
		</td><!--名称-->
		<td align="center" height="25"><%=LocalUtilis.language("class.serialNO",clientLocale)%> </td><!--受益序号-->
		<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.benAmount",clientLocale)%> </td><!--受益金额-->					
		<td align="center" height="25"><%=LocalUtilis.language("class.benStart&EndDate",clientLocale)%> </td><!--受益起/止日期-->
		<td align="center" height="25"><%=LocalUtilis.language("class.paymentBank",clientLocale)%> </td><!--缴款银行-->
	    <td align="center" height="25"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
		<td align="center" height="25"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->						
	</tr>
<%
//声明显示参数
Integer serial_no = null;
Integer cust_id = new Integer(0);
String cust_name ="";
String b_contract_bh = "";
String list_id = "";
Integer ben_date = new Integer(0);
Integer ben_end_date = new Integer(0);
String bank_name = "";
String bank_sub_name = "";
String bank_acct ="";
BigDecimal ben_amount = new BigDecimal("0.000");
BigDecimal to_amount = new BigDecimal("0.000");
BigDecimal ben_amount_total = new BigDecimal("0.000");
BigDecimal to_amount_total = new BigDecimal("0.000");

Iterator iterator = list_ben.iterator();

while(iterator.hasNext()){
	iCount++;
	map_ben = (Map) iterator.next();
	
	serial_no = Utility.parseInt(Utility.trimNull(map_ben.get("SERIAL_NO")),null);
	b_contract_bh = Utility.trimNull(map_ben.get("CONTRACT_BH"));
	list_id = Utility.trimNull(map_ben.get("LIST_ID"));
	cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),null);
	ben_date = Utility.parseInt(Utility.trimNull(map_ben.get("BEN_DATE")),null);
	ben_end_date = Utility.parseInt(Utility.trimNull(map_ben.get("BEN_END_DATE")),null);	
	cust_name = Utility.trimNull(map_ben.get("CUST_NAME"));
	bank_name = Utility.trimNull(map_ben.get("BANK_NAME"));
	bank_sub_name = Utility.trimNull(map_ben.get("BANK_SUB_NAME"));
	bank_acct = Utility.trimNull(map_ben.get("BANK_ACCT"));
	ben_amount = Utility.parseDecimal(Utility.trimNull(map_ben.get("BEN_AMOUNT")),new BigDecimal(0));
	ben_amount_total =ben_amount_total.add(ben_amount);
	to_amount = new BigDecimal(Utility.parseDecimal(Utility.trimNull(map_ben.get("TO_AMOUNT")),new BigDecimal(0)).intValue()+to_amount.intValue());
	to_amount_total = to_amount_total.add(to_amount);
%>		
	<tr class="tr<%=(iCount % 2)%>">
		<td class="tdh" align="center" height="25">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
					<td width="90%" align="left">
						<a href='#' onclick="javascript:getTransactionCustomer1('<%=cust_id%>','1');return false;"><%=cust_name%></a>
					</td>
				</tr>
			</table>
		</td>
		<td align="center" height="25"><%=b_contract_bh%>-<%=list_id%></td>
		<td align="center" height="25"><%=Format.formatMoney(ben_amount)%></td>						
		<td align="center" height="25">	<%=Format.formatDateCn(ben_date)%>/	
		<%if(ben_end_date!=null &&ben_end_date.intValue()>=20990101) out.print(enfo.crm.tools.LocalUtilis.language("message.noFixDeadline",clientLocale)); else out.print(Format.formatDateCn(ben_end_date));%>
		</td><!--无固定期限-->
		<td align="center" height="25"><%=bank_name%><%=bank_sub_name%></td>
		<td align="center" height="25"><%=bank_acct%></td>
		<td align="center" height="25">
		<%if (input_operator.hasFunc(menu_id, 102)){%>
			<!--<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<!%=serial_no%>,<!%=check_flag%>);">&gt;&gt;</button>-->
			<a href="javascript:showInfo(<%=serial_no%>,<%=check_flag%>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
           	</a>
		<%}%>					
		</td>
	</tr>
<%}%>	
<%for(;iCount< 8; iCount++){%>
	<tr class="tr<%=(iCount % 2)%>">
		<td class="tdh" align="center" height="25"></td>						
		<td align="center" height="25"></td>
		<td align="center" height="25"></td>						
		<td align="center" height="25"></td>
		<td align="center" height="25"></td>
		<td align="center" height="25"></td>
		<td align="center" height="25"></td>					
	</tr>
<%}%>
	<tr class="trbottom">
		<td class="tdh" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=list_ben.size()%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>		
		<td align="center" height="25">-</td>
		<td align="center" height="25"><%=Format.formatMoney(to_amount)%></td>		
		<td align="center" height="25">-</td>
		<td align="center" height="25">-</td>
		<td align="center" height="25">-</td>
		<td align="center" height="25">-</td>		
	</tr>		
</table>
</div>

<%
benifitor.remove();
contract.remove();
%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>