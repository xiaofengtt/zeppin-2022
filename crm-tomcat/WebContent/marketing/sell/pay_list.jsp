<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//��ò�ѯ����
String q_cust_name = Utility.trimNull(request.getParameter("cust_name"));
String q_cust_no = Utility.trimNull(request.getParameter("cust_no"));
String q_contract_sub_bh = Utility.trimNull(request.getParameter("q_contract_sub_bh"));
java.math.BigDecimal q_min_rg_money=Utility.parseDecimal(request.getParameter("min_rg_money"),null);
java.math.BigDecimal q_max_rg_money=Utility.parseDecimal(request.getParameter("max_rg_money"),null);
Integer q_product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);
Integer q_check_flag = Utility.parseInt(request.getParameter("q_check_flag"), new Integer(1));//��˱�־��1 δ��ˣ�2 ����ˣ�
//ҳ�渨������
input_bookCode = new Integer(1);//������ʱ����
int iCount = 0;
String[] totalColumn = {"TO_MONEY"};
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
List list = null;
Map map = null;
//url����
String tempUrl = "";
tempUrl = tempUrl+"&cust_name="+q_cust_name;
tempUrl = tempUrl+"&cust_no="+q_cust_no;
tempUrl = tempUrl+"&q_contract_sub_bh="+q_contract_sub_bh;
tempUrl = tempUrl+"&q_product_id="+q_product_id;
tempUrl = tempUrl+"&min_rg_money="+q_min_rg_money;
tempUrl = tempUrl+"&max_rg_money="+q_max_rg_money;
sUrl = sUrl + tempUrl;
//���ɶ���
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO vo = new MoneyDetailVO();
//��ѯ��������
vo.setInput_man(input_operatorCode);
vo.setBook_code(input_bookCode);
vo.setCust_name(q_cust_name);
vo.setCust_no(q_cust_no);
vo.setContract_sub_bh(q_contract_sub_bh);
vo.setMin_to_money(q_min_rg_money);
vo.setMax_to_money(q_max_rg_money);
vo.setProduct_id(q_product_id);
vo.setCheck_flag(q_check_flag);
//��ѯ���
IPageList pageList = moneyDetailLocal.query_page(vo,totalColumn,t_sPage,t_sPagesize);
list  = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode, q_product_id, "",input_operatorCode,0);
options = options.replaceAll("\"", "'");
%>
<html>
<head>
<title><%=LocalUtilis.language("menu.paymentManagement",clientLocale)%> </title><!--�ɿ����-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language="javascript">
window.onload = function(){
	initQueryCondition();
}
function newInfo(){
	disableAllBtn(true);		
 	window.location = 'pay_action_new.jsp';
}
function showInfo(serial_no){
	disableAllBtn(true);		
 	window.location = 'pay_action.jsp?serial_no='+serial_no;
}
function removeInfo(){
	if(!confirmRemove(document.theform.serial_no)) return false;
	disableAllBtn(true);
	document.theform.action="pay_remove.jsp";
	document.theform.submit();
}
/*ˢ��*/
function refreshPage(){
	disableAllBtn(true);		

	var url = 'pay_list.jsp?page=1&pagesize='+ document.getElementsByName('pagesize')[0].value;	
	var url = url + '&cust_no=' + document.getElementsByName('cust_no')[0].value;
	var url = url + '&cust_name=' + document.getElementsByName('cust_name')[0].value;
	var url = url + '&min_rg_money=' + document.getElementsByName('min_rg_money')[0].value;
	var url = url + '&max_rg_money=' + document.getElementsByName('max_rg_money')[0].value;
	var url = url + '&contract_sub_bh=' + document.getElementsByName('contract_sub_bh')[0].value;
	var url = url + '&product_id=' + document.getElementsByName('product_id')[0].value;
	var url = url + '&q_check_flag=' + document.getElementsByName('q_check_flag')[0].value;
	window.location = url;
}
function StartQuery(){
 	refreshPage();
}
/**************************************************************************************************************/
/*���ò�Ʒ*/
function setProduct(value){
	var prodid=0;
	if(event.keyCode==13&&value!=""){
        var j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}		
	}
}
/*��ѯ��Ʒ*/
function searchProduct(value){
	var prodid=0;
	if (value != ""){
       var  j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}
function printInfo(serial_no)
{
	var url= 'pay_action_print.jsp?serial_no='+serial_no;;
	window.open(url); 
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</head>

<body class="body">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" action="pay_list.jsp" method="post">
<div id="queryCondition" class="qcMain" style="display:none;width:660px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr> 
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--��ѯ����-->
			<td align="right">
				<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>			
	<table border="0" width="100%" cellspacing="2" cellpadding="2">		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
			<td align="left"><input type="text" name="cust_no" value="<%=Utility.trimNull(q_cust_no)%>" onkeydown="javascript:nextKeyPress(this)" size="15"></td>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
			<td align="left"><input type="text" name="cust_name" value="<%=Utility.trimNull(q_cust_name)%>" onkeydown="javascript:nextKeyPress(this)" size="15"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</td><!--��ͬ���-->
			<!--��-->
            <td align="left"><%=LocalUtilis.language("message.start",clientLocale)%> &nbsp;<input type="text" maxlength="16" name="min_rg_money" value="<%=Utility.trimNull(q_min_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
				<!--��-->
                <%=LocalUtilis.language("message.end",clientLocale)%> &nbsp;<input type="text" maxlength="16" name="max_rg_money" value="<%=Utility.trimNull(q_max_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
			</td>
			<td align="right"><%=LocalUtilis.language("message.checked",clientLocale)%> :</td><!-- ����� -->
			<td align="left"><SELECT name="q_check_flag" width="120px"><%=Argument.getCheckFlagOptions(q_check_flag) %></SELECT></td>
		</tr>
		<tr>	
			<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
			<td ><input type="text" name="contract_sub_bh" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=q_contract_sub_bh%>"></td>						
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
			<td><input type="text" maxlength="16" name="product_code" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.product_code.value);" /></button></td>
			<td  align="right">��Ʒ���� :</td>
			<td>
				<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
				<button type="button"   class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
			</td>
		</tr>
		<tr>	
			<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--��Ʒѡ��-->
			<td align="left" colspan="3" id="select_id">
				<SELECT name="product_id" class="productname"><%=Argument.getProductListOptions(input_bookCode, q_product_id, "",input_operatorCode,0)%></SELECT>
			</td>				
		</tr>
		<tr>
			<td align="center" colspan="4">
				<!--ȷ��-->
                <button type="button"   class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
			</td>
		</tr>					
	</table>				
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" class="btn-wrapper">
		<!--��ѯ-->
        <button type="button"   class="xpbutton3" accessKey=q name="queryButton" id="queryButton" onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
		<%if (input_operator.hasFunc(menu_id, 100)){%>
		<!--�½�-->
        <button type="button"   class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> 
		&nbsp;&nbsp;&nbsp;<%}%> 
		<%if (input_operator.hasFunc(menu_id, 101)){%>
		<!--ɾ��-->
        <button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>' onclick="javascript:removeInfo();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		&nbsp;&nbsp;&nbsp; <%}%>
		<!--��ӡ�վ�-->
        <button type="button"   class="xpbutton4" style="display:none" accessKey=p name="btnRefresh" title='<%=LocalUtilis.language("message.printReceipt",clientLocale)%>' onclick="javascript:void(0);"><%=LocalUtilis.language("message.printReceipt",clientLocale)%> (<u>P</u>)</button>
	</div>	
	<br/>
</div>	

<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" >
		<tr class="trh">
			<!--��Ʒ����-->
            <td align="center" width="*"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> </td>						
			<td align="center" width="*"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--��ͬ���-->
			<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
			<td align="center" >��������</td><!-- �������� -->
			<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--�����ʺ�-->
			<td align="center" >���漶��</td><!--���漶��-->
			<td align="center" >���漶��</td><!--���漶��-->
			<td align="center" width=""><%=LocalUtilis.language("class.toMoney3",clientLocale)%> </td><!--�ɿ���-->
			<td align="center" width="*"><%=LocalUtilis.language("class.feeMoney",clientLocale)%> </td><!--������-->
			<td align="center" width="*"><%=LocalUtilis.language("class.feeType",clientLocale)%> </td><!--�ɿʽ-->			
			<td align="center" width=""><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--�ɿ�����-->
			<td align="center" width=""><%=LocalUtilis.language("class.toBankDate2",clientLocale)%> </td><!--��������-->
			<td align="center" width=""><%=LocalUtilis.language("class.checkFlag",clientLocale)%> </td><!--��˱�־-->
			<td align="center" width="*%"><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--��ע-->
			<td align="center" width="">��ӡ </td><!--��ӡ-->
			<td align="center" width=""><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
		</tr>
<%
//������ʾ����
Integer serial_no = null;
String product_name = "";
String cust_name = "";
String contract_sub_bh = "";
java.math.BigDecimal to_money = null;
java.math.BigDecimal fee_money = null;
String jk_type_name = "";
Integer dz_date = new Integer(0);
Integer to_bank_date = new Integer(0);
Integer check_flag = new Integer(0);
String summary = "";
Integer fee_type = new Integer(0);
String fee_type_name = "";
String data = "";
//���õ�����
Iterator iterator = list.iterator();
//����������
String sub_product_name = "";

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();

	if(map!=null){
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		to_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),new BigDecimal(0));
		fee_money = Utility.parseDecimal(Utility.trimNull(map.get("FEE_MONEY")),new BigDecimal(0));
		jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
		dz_date = Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0));
		to_bank_date = Utility.parseInt(Utility.trimNull(map.get("TO_BANK_DATE")),new Integer(0));
		check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
		summary = Utility.trimNull(map.get("SUMMARY"));
		sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
		String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
		String prov_flag_name = Utility.trimNull(map.get("PROV_FLAG_NAME"));
		String bank_name = Utility.trimNull(map.get("BANK_NAME"));
		String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));

		if(sub_product_name!=null&&!"".equals(sub_product_name))
			sub_product_name = "("+sub_product_name+")";
		
		if(fee_money.intValue()>0){
			fee_type =  Utility.parseInt(Utility.trimNull(map.get("FEE_TYPE")),new Integer(0));
			if(fee_type.intValue()!=0){
				if(fee_type.intValue()==1)
					fee_type_name=enfo.crm.tools.LocalUtilis.language("class.rgFee",clientLocale);//�Ϲ���
				if(fee_type.intValue()==2)
					fee_type_name=enfo.crm.tools.LocalUtilis.language("class.sgFeeAmount",clientLocale);//�깺��
				if(fee_type.intValue()==4)
				fee_type_name=enfo.crm.tools.LocalUtilis.language("class.transFee2",clientLocale);//�ݶ�ת����
			}
		}

		data = serial_no +"_"+check_flag;
 %>
	<tr class="tr<%=(iCount % 2)%>">
		<td class="tdh" align="center" >
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><%if(check_flag.intValue()==1) {%><input type="checkbox" name="serial_no" value="<%=data%>" class="flatcheckbox"><%}%></td>
					<td width="90%" align="left"><%=product_name%><%=sub_product_name %></td>
				</tr>
			</table>
		</td>						
		<td align="left" ><%=contract_sub_bh%></td>
		<td align="left" ><%=cust_name%></td>
		<td align="left" ><%=bank_name%></td>
		<td align="left" ><%=bank_acct%></td>
		<td align="left" ><%=prov_flag_name %></td>
		<td align="left" ><%=prov_level_name %></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(to_money))%></td>
		<td align="left" ><%=Utility.trimNull(fee_type_name)%><%=Utility.trimNull(Format.formatMoney(fee_money))%></td>
		<td align="center" ><%=Utility.trimNull(jk_type_name)%></td>
		<td align="center" ><%=Format.formatDateCn(dz_date)%></td>						
		<td align="center" ><%=Format.formatDateCn(to_bank_date)%></td>
		<td align="center" ><%=Argument.getCheckFlagName(check_flag)%></td>
		<td align="left" ><%=Utility.trimNull(summary)%></td>
		<td><a href="javascript:printInfo(<%=serial_no%>);">
				<img  src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16">
			</a>
		</td>	
		<td align="center" >
		<%if (input_operator.hasFunc(menu_id, 102)&&check_flag.intValue()==1) {%>						
			 <a href="javascript:showInfo(<%=serial_no%>);">
           		<img  src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
           	</a>
		<%}%>
		</td>					
	</tr>
<%}
}%>

<%for(int i=0;i<(8-iCount);i++){%>  
	      <tr class="tr<%=i%2%>">
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>
	         <td align="center">&nbsp;</td>                   
	         <td align="center">&nbsp;</td>      
			 <td align="center">&nbsp;</td>      
			 <td align="center">&nbsp;</td>    
			 <td align="center">&nbsp;</td>                   
	         <td align="center">&nbsp;</td>      
			 <td align="center">&nbsp;</td>      
			 <td align="center">&nbsp;</td>
			 <td align="center">&nbsp;</td>
	      </tr>           
<%}%>   	

<tr class="trbottom">
		<!--�ϼ�--><!--��-->
        <td class="tdh" align="left" colspan="1">
			<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> 
				<%=LocalUtilis.language("message.entries",clientLocale)%> 
			</b>
		</td>
		<td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center">-</td>
	    <td align="center"><%=Format.formatMoney(pageList.getTotalValue("TO_MONEY"))%></td>                   
	    <td align="center">-</td>      
		<td align="center">-</td>      
		<td align="center">-</td>    
		<td align="center">-</td>                   
	    <td align="center">-</td>      
		<td align="center">-</td>      
		<td align="center">-</td>
		<td align="center">-</td>
	</tr>
	</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%//@ include file="/includes/foot.inc"%>
</body>
</html>
<%moneyDetailLocal.remove();%>