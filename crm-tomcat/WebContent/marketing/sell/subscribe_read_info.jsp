<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//���ҳ�洫�ݲ���
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
Integer query_product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));

String object_id = Utility.trimNull(request.getParameter("object_id"));
if(!"".equals(object_id)){
	serial_no=Utility.parseInt(object_id,new Integer(0));
}

List attachmentList = new ArrayList();
//��ö���
ContractLocal contract = EJBFactory.getContract();
CustomerLocal customer = EJBFactory.getCustomer();
ProductLocal product = EJBFactory.getProduct();
ContractVO vo = new ContractVO();
CustomerVO custvo = new CustomerVO();
ProductVO productvo = new ProductVO();

Map contractMap =new HashMap();
Map custMap = new HashMap();
Map productMap = new HashMap();
vo.setSerial_no(serial_no);
vo.setInput_man(input_operatorCode);

List contractList = contract.load(vo);
Integer sub_product_id = new Integer(0);
Integer prov_flag = new Integer(0);
Integer r_channel_id = new Integer(0);
String channel_type = "";
String channel_cooperation = "";
Integer product_id = new Integer(0);
String prov_level = "";
String bank_city = "",bank_province = "";
Integer recommend_man = new Integer(0);
if(contractList!=null&&contractList.size()==1){
	contractMap = (Map)contractList.get(0);
	prov_flag = Utility.parseInt(Utility.trimNull(contractMap.get("PROV_FLAG")), new Integer(0));
	sub_product_id =  Utility.parseInt(Utility.trimNull(contractMap.get("SUB_PRODUCT_ID")),new Integer(0));
	channel_type = Utility.trimNull(contractMap.get("CHANNEL_TYPE"));
	channel_cooperation = Utility.trimNull(contractMap.get("CHANNEL_COOPERTYPE"));
	r_channel_id = Utility.parseInt(Utility.trimNull(contractMap.get("CHANNEL_ID")),new Integer(0));
	product_id =  Utility.parseInt(Utility.trimNull(contractMap.get("PRODUCT_ID")), new Integer(0));	
	prov_level = Utility.trimNull(contractMap.get("PROV_LEVEL"));
	recommend_man = Utility.parseInt(Utility.trimNull(
						contractMap.get("RECOMMEND_MAN")),new Integer(0));		
	bank_province = Utility.trimNull(contractMap.get("BANK_PROVINCE_NAME"));
	bank_city = Utility.trimNull(contractMap.get("BANK_CITY_NAME"));
}
	
//custvo.setCust_id(new Integer(1));
custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
custvo.setInput_man(input_operatorCode);
productvo.setProduct_id((Integer)contractMap.get("PRODUCT_ID"));

List custList = customer.listCustomerLoad(custvo);
List productList = product.load(productvo);
if(custList!=null&&custList.size()==1)
	custMap = (Map)custList.get(0);

if(productList!=null&&productList.size()==1)
	productMap = (Map)productList.get(0);

//����Ϲ�����
AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
AttachmentVO attachment_vo = new AttachmentVO();
attachment_vo.setDf_talbe_id(new Integer(5));
attachment_vo.setDf_serial_no(serial_no);

attachmentList = attachmentLocal.load(attachment_vo);	
%>

<HTML>
<HEAD>
<TITLE></TITLE>
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
function op_check()
{
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function showBenifiter(contract_id)
{
   location = "benifiter_check.jsp?from_flag_benifitor=1&contract_id=" + contract_id + '&page=<%//=sPage%>&pagesize=' + document.theform.pagesize.value;
}
/*�ͻ���Ϣ*/
function getMarketingCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo_details_view.jsp?cust_id=' + cust_id + '&readonly=1' ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');		
	
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="subscribe_check_action.jsp">
<input name="serial_no" type="hidden" value="<%=serial_no%>">
<input name="product_id" type="hidden" value="<%=query_product_id%>">
<input name="contract_bh" type="hidden" value="<%=Utility.trimNull(contract_bh)%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
					<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
						<tr>
							<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
						</tr>
					</TABLE>
					<table border=0 cellSpacing=0 cellPadding=0 width="100%">
						<tr>
							<td>
								<hr size=1>
							</td>
						</tr>
					</table>
					<table border=0 cellSpacing=0 cellPadding=3 width="100%">
						<tr>
							<td align=right><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
							<td><INPUT class=edline value='<%=Utility.trimNull(custMap.get("CUST_NO"))%>' readOnly name=cust_no></td>
							<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :</TD><!--�ͻ�����-->
							<td>
								<input class=edline value='<%=Utility.trimNull(custMap.get("CUST_NAME"))%>'  readOnly size=30 name=cust_name>
								<button type="button"  class="xpbutton3" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('<%=Utility.trimNull(custMap.get("CUST_ID"))%>');"><%=LocalUtilis.language("message.viewInfo",clientLocale)%> </button>
							</td>
						</tr>
						<tr>
							<td align=right><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</TD><!--֤������-->
							<td><input class=edline value='<%=Utility.trimNull(custMap.get("CARD_TYPE_NAME"))%>'  readOnly name=card_type_name></td>
							<td align=right><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--֤������-->
							<td><input class=edline value='<%=Utility.trimNull(custMap.get("CARD_ID"))%>' readOnly size=30 name=card_id></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
							<td><input readonly name="cust_type_name" size="20" value="<%=Utility.trimNull(custMap.get("CUST_TYPE_NAME"))%>" class="edline"></td>
							<td align="right" width="10%"><%=LocalUtilis.language("class.accountManager",clientLocale)%>:</td><!--�ͻ�����-->
							<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" 
								value="<%=Utility.trimNull(Argument.getManager_Name((Integer)contractMap.get("SERVICE_MAN")))%>">
							</td>	
						</tr>	
						<tr>
						<td colspan="4"><hr size="1"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
							<td ><input readonly class="edline" name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
							<td><input readonly class="edline" name="product_name" size="60" readonly value="<%=Utility.trimNull(contractMap.get("PRODUCT_NAME"))%>"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.rg_money",clientLocale)%> :</td><!--�Ϲ����-->
							<td><input readonly class="edline" name="rg_money" size="20" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("RG_MONEY")),new BigDecimal(0)))%>"> <span id="rg_money_cn" class="span">(<%=Utility.numToChinese(Utility.trimNull(contractMap.get("RG_MONEY")))%>)</span></td>
						</tr>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--��������-->
							<td><input readonly class="edline" name="bank_name" size="40" value="<%=Utility.trimNull(contractMap.get("BANK_NAME"))%><%=Utility.trimNull(contractMap.get("BANK_SUB_NAME"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--�ɿʽ-->
							<td><input readonly class="edline" name="jk_type_name" size="20" value="<%=Utility.trimNull(contractMap.get("JK_TYPE_NAME"))%>">
							</td>			
						</tr>
						<tr>	
							<td align="right">���������ڵ� :</td><!--���������ڵ�-->
							<td><input readonly class="edline" name="bank_province" size="40" value="<%=Utility.trimNull(bank_province)%><%=Utility.trimNull(bank_city)%>"></td>
							<td align="right">�Ƽ��� :</td><!--�Ƽ���-->
							<td><input readonly class="edline" name="recommend_man" size="20" value="<%=Argument.getInturstOperatorName(recommend_man) %>">
							</td>			
						</tr>
						<%if(Utility.parseInt(Utility.trimNull(productMap.get("OPEN_FLAG")),new Integer(0)).intValue()==1){%>
						<tr>				
							<td align="right"><%=LocalUtilis.language("class.feeTypeName",clientLocale)%> :</td><!-- �۽ɷ�ʽ -->
							<td >		
								<input readonly class="edline" name="fee_jk_type" size="20" value="<%=Argument.getFeeJkTypeName(contractMap.get("FEE_JK_TYPE")==null?0:((Integer)contractMap.get("FEE_JK_TYPE")).intValue())%>">				
							</td>			
							<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--������䷽ʽ-->
							<td>
								<select size="1" disabled name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
									<%=Argument.getBonus_flag(Utility.parseInt(Utility.trimNull(contractMap.get("BONUS_FLAG")),new Integer(0)))%>
								</select>		
							</td>				
						</tr>
						<%}%>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!-- �����ʺ� -->
							<td>
								<input readonly class="edline" name="bank_acct" size="40" value="<%=Utility.ShowBankAcct(Utility.trimNull(contractMap.get("BANK_ACCT")))%>">
							</td>
							<td align="right"><%=LocalUtilis.language("class.validPeriod",clientLocale)%> :</td><!--��ͬ����-->
							<td>
								<input readonly class="edline" type="text" name="valid_period" size="20" value="<%=Utility.trimNull(contractMap.get("VALID_PERIOD"))%>"><%=Utility.trimNull(Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0))))%>
							</td>
						</tr>
						<tr>	
							<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--��ͬ������Ա-->
							<td>
								<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(Argument.getOpName((Integer)contractMap.get("LINK_MAN")))%>">
							</td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--ǩ������-->
							<td>
								<input readonly class="edline" name="qs_date" size="20" value="<%=Utility.trimNull(Format.formatDateCn((Integer)contractMap.get("QS_DATE")))%>">
							</td>
							<td align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
							<td><input readonly class="edline"  size="40" value="<%=Utility.trimNull(Format.formatDateCn((Integer)contractMap.get("JK_DATE")))%>"></td>
						</tr>
						<tr>
							<td align="right">�������ȼ� :</td>
							<td>					
								<select name="prov_flag" style="WIDTH: 100px" disabled="disabled" onkeydown="javascript:nextKeyPress(this)">
									<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag)%>
								</select>
							</td>
							<td align="right">*<%=LocalUtilis.language("class.incomeLevel",clientLocale)%> :</td><!-- ���漶�� -->
							<td>
								<select name="prov_level" style="width:125px;" disabled>
									<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level)%>
								</select>	
							</td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--��ͬ�ƽ��-->
							<td><input readonly class="edline" name="bank_acct" size="40" value="<%=Utility.trimNull(contractMap.get("CITY_NAME"))%>"></td>
							<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
							<td><input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(contractMap.get("SUMMARY"))%>" size="40"></td>
						</tr>
						<tr>
							<td align="right">�ÿ���� :</td>
							<td><input readonly class="edline" name="is_ykgl" size="40" value="<%if(Utility.trimNull(contractMap.get("IS_YKGL")).equals("1")){out.print("��");}else{out.print("��");}%>"></td>
							<td align="right">��ͬԤ�������� :</td><!--��ͬԤ��������-->
							<td><input readonly class="edline" type="text" name="xthtyjsyl" value="<%=Utility.trimNull(contractMap.get("XTHTYJSYL"))%>" size="40"></td>
						</tr>
						<tr>
							<td align="right">��ͬ��ϵ�� :</td>
							<td>
								<select disabled="disabled" size="1" name="cust_message">
								<%=Argument.getCustOptions(Utility.parseInt(Utility.trimNull(contractMap.get("CUST_ID")), null),Utility.parseInt(Utility.trimNull(contractMap.get("CONTACT_ID")), null)) %>
								</select>
							</td>
							<td align="right">Ԥ�������� :</td>
							<td><input readonly="readonly" class="edline" type="text" name="exp_rate" 
									value="<%=(contractMap.get("EXPECT_ROR_LOWER")==null?"":((BigDecimal)contractMap.get("EXPECT_ROR_LOWER")).multiply(new BigDecimal(100)).setScale(2).toString())+"% - "
												+(contractMap.get("EXPECT_ROR_LOWER")==null?"":((BigDecimal)contractMap.get("EXPECT_ROR_UPPER")).multiply(new BigDecimal(100)).setScale(2).toString())+"%"%>" size="40"></td>
						</tr>						
						<tr id="reader1">
	          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>����:<%}%></td>
	            <td colspan="3">
				<%
				Iterator attachment_it = attachmentList.iterator();
				HashMap attachment_map = new HashMap();
	            while(attachment_it.hasNext()){
	            	attachment_map = (HashMap)attachment_it.next();
	            %>
	            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
	            	<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
	            	&nbsp;&nbsp;&nbsp;&nbsp;
	            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave" 
	            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">ɾ��</button>
	            	</div><br>
				<%}	%>
	            </td>	
	         </tr>
				
	</TABLE>
	</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%contract.remove();
customer.remove();
product.remove();
%>