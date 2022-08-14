<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ò�ѯ����
String sContract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
java.math.BigDecimal min_rg_money=Utility.parseDecimal(request.getParameter("min_rg_money"),null);
java.math.BigDecimal max_rg_money=Utility.parseDecimal(request.getParameter("max_rg_money"),null);
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
//ҳ�渨������
input_bookCode = new Integer(1);//������ʱ����
int iCount = 0;
String[] totalColumn = {"TO_MONEY","TO_AMOUNT"};
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
List list = null;
Map map = null;
//url����
String tempUrl = "";
tempUrl = tempUrl+"&contract_sub_bh="+sContract_sub_bh;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
tempUrl = tempUrl+"&contract_sub_bh="+sContract_sub_bh;
tempUrl = tempUrl+"&product_id="+product_id;
tempUrl = tempUrl+"&min_rg_money="+min_rg_money;
tempUrl = tempUrl+"&max_rg_money="+max_rg_money;
sUrl = sUrl + tempUrl;
//��ö���
ToMoneyAccountLocal local = EJBFactory.getToMoneyAccount();
ToMoneyAccountVO vo = new ToMoneyAccountVO();
//��ѯ��������
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setContract_sub_bh(sContract_sub_bh);
vo.setProduct_name(q_product_name);
vo.setProduct_id(product_id);
//��ѯ���
IPageList pageList = local.listRestoreCheck(vo,totalColumn,t_sPage,t_sPagesize);
list  = pageList.getRsList();
 %>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.financialAuditForMoney",clientLocale)%> </TITLE><!--�ʽ��ʲ������-->
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
}
function recheckInfo(){
		if(checkedCount(document.theform.s_id) == 0){
			sl_alert("<%=LocalUtilis.language("message.restoredTip",clientLocale)%> ��");//��ѡ��Ҫ�ָ��ļ�¼
			return false;
		}
		if(confirm('<%=LocalUtilis.language("message.restoredTip2",clientLocale)%> ��')){//��ȷ��ѡ���ļ�¼���лָ�ȷ����
			disableAllBtn(true);
			document.theform.action="funds_recheck_action.jsp";
			document.theform.submit();
		}
}
/*ˢ��*/
function refreshPage(){
	disableAllBtn(true);		
	var url = 'funds_recheck_list.jsp?page=1&pagesize='+ document.getElementsByName('pagesize')[0].value;	
	var url = url + '&min_rg_money=' + document.getElementsByName('min_rg_money')[0].value;
	var url = url + '&max_rg_money=' + document.getElementsByName('max_rg_money')[0].value;
	var url = url + '&contract_sub_bh=' + document.getElementsByName('contract_sub_bh')[0].value;
	var url = url + '&q_product_name=' + document.getElementsByName('q_product_name')[0].value;
	var url = url + '&product_id=' + document.getElementsByName('product_id')[0].value;
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
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr> 
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--��ѯ����-->
			<td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>	
	<table border="0" width="100%" cellspacing="2" cellpadding="2">	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
				<td align="left">
					<input type="text" name="contract_sub_bh" size="10" onkeydown="javascript:nextKeyPress(this)" value="<%=sContract_sub_bh%>">&nbsp;&nbsp;
				</td> 
				
			</tr>
			<tr>
				<!--��ͬ���--><!--��-->
                <td align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%><%=LocalUtilis.language("message.start",clientLocale)%> :</td>
				<td align="left">
						<input type="text" maxlength="16" name="min_rg_money" value="<%=Utility.trimNull(min_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
						<!--��-->
                        <%=LocalUtilis.language("message.end",clientLocale)%> :<input type="text" maxlength="16" name="max_rg_money" value="<%=Utility.trimNull(max_rg_money)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td ><!--��Ʒ����-->
				<td>
					<INPUT type="text" name="q_product_name" size="20" onkeydown="javascript:nextKeyPress(this)" <%if (q_product_name!=null)%> value=<%=q_product_name%>>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td ><!--��Ʒ���-->
				<td align="left">
					<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="10">&nbsp;
					<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" />
				</td >			
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--��Ʒѡ��-->
				<td align="left" colspan=3>
					<SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname"><%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,status)%></SELECT>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="4">
					<!--ȷ��-->
                    <button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
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
        <button type="button"  class="xpbutton3" accessKey=q name="queryButton" id="queryButton" onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
		<%if (input_operator.hasFunc(menu_id, 103)) {%>
		<!--�ָ�-->
        <button type="button"  class="xpbutton3" name="btnNew" title='<%=LocalUtilis.language("message.restore",clientLocale)%> ' onclick="javascript:recheckInfo();"><%=LocalUtilis.language("message.restore",clientLocale)%> </button>
		<%}%>
	</div>
	<br/>
</div>

<div align="center" >
		<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" >
				<tr class="trh">
					<!--��ͬ���-->
                    <td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.s_id,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.contractID",clientLocale)%> </td>
					<td align="center" ><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
					<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
					<td align="center" >��������</td><!-- �������� -->
					<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--�����ʺ�-->
					<td align="center" >���漶��</td><!--���漶��-->
					<td align="center" >���漶��</td><!--���漶��-->
					<td align="center" ><%=LocalUtilis.language("class.qsDate",clientLocale)%> </td><!--ǩ������-->
					<td align="center" ><%=LocalUtilis.language("class.toBankDate2",clientLocale)%> </td><!--��������-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.toMoney",clientLocale)%> </td><!--���˽��-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.toAmount2",clientLocale)%> </td><!--���˷ݶ�-->
					<td align="center" ><%=LocalUtilis.language("class.feeType",clientLocale)%> </td>	<!--�ɿʽ-->
					<td align="center"><%=LocalUtilis.language("class.description",clientLocale)%> </td><!--��ע-->						
				</tr>
<%
//������ʾ����
Integer serial_no = null;
String product_name = "";
String contract_sub_bh = "";
String cust_name ="";
String jk_type_name = "";
String fee_type_name = "";
String summary = "";
Integer dz_date = new Integer(0);
Integer fee_type = new Integer(0);
Integer check_flag = new Integer(0);
Integer to_bank_date = new Integer(0);
java.math.BigDecimal to_money = null;
java.math.BigDecimal to_account = null;
java.math.BigDecimal fee_money = null;
String sub_product_name = "";
String prov_level_name = "";
String prov_flag_name = "";
String bank_name = "";
String bank_acct = "";
//���õ�����
Iterator iterator = list.iterator();
//����������
while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		fee_type_name ="";

		if(map!=null){
			serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
			contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
			cust_name = Utility.trimNull(map.get("CUST_NAME"));
			jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
			product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
			summary = Utility.trimNull(map.get("SUMMARY"));
			dz_date = Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0));
			to_bank_date = Utility.parseInt(Utility.trimNull(map.get("TO_BANK_DATE")),new Integer(0));
			to_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),new BigDecimal(0));
			to_account = Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),new BigDecimal(0));
			sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
			prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
			prov_flag_name = Utility.trimNull(map.get("PROV_FLAG_NAME"));
			bank_name = Utility.trimNull(map.get("BANK_NAME"));
			bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
			if(sub_product_name!=null&&!"".equals(sub_product_name))
				sub_product_name = "("+sub_product_name+")";
		}
 %>
			<tr class="tr<%=(iCount % 2)%>">
				<td align="left" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" ><input class="flatcheckbox" type="checkbox" name="s_id" value="<%=serial_no%>"></td>
							<td width="90%"><p align="left"><%=contract_sub_bh%></p></td>
						</tr>
				</table>
				</td>
				<td align="left" ><%=cust_name%></td>
				<td align="left" ><%=product_name%><%=sub_product_name %></td>
				<td align="left" ><%=bank_name%></td>
				<td align="left" ><%=bank_acct%></td>
				<td align="left" ><%=prov_flag_name %></td>
				<td align="left" ><%=prov_level_name %></td>
				<td align="center" ><%=Format.formatDateCn(dz_date)%></td>
				<td align="center" ><%=Format.formatDateCn(to_bank_date)%></td>
				<td align="right" ><%=Format.formatMoney(to_money)%></td>
				<td align="right" ><%=Format.formatMoney(to_account)%></td>
				<td align="center" ><%=jk_type_name%></td>	
				<td align="left" ><%=summary%></td>					
			</tr>
<%}%>
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
		      </tr>           
	<%}%>   	

	<tr class="trbottom">
		<!--�ϼ�--><!--��-->
        <td class="tdh" align="left" colspan="1"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>                   
		<td align="center">&nbsp;</td>
		<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("TO_MONEY"),new BigDecimal(0.00)))%></td>
		<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("TO_AMOUNT"),new BigDecimal(0.00)))%></td>
		<td align="center">&nbsp;</td>                   
		<td align="center">&nbsp;</td>
	</tr>
		</table>
</div>
<br>
<div  class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%local.remove();%>
