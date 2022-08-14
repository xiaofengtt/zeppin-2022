<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);  

// �����õ���ԭ��ѯ����
String sProductId = Utility.trimNull(request.getParameter("product_id")); 
String sCustName = Utility.trimNull(request.getParameter("cust_name"));
String sContractBh= Utility.trimNull(request.getParameter("query_contract_bh"));
//String sPage = Utility.trimNull(request.getParameter("page")); // ��������������parameter.inc
//String sPagesize = Utility.trimNull(request.getParameter("pagesize"));
String sRetQuery = "page="+sPage+"&pagesize="+sPagesize+"&product_id="+sProductId+"&query_contract_bh="+sContractBh+"&cust_name="+sCustName;

ApplyreachVO vo = new ApplyreachVO();
CustomerVO custvo = new CustomerVO();
ProductVO productvo = new ProductVO();
ApplyreachLocal apply = EJBFactory.getApplyreach();
CustomerLocal customer = EJBFactory.getCustomer();
ProductLocal product = EJBFactory.getProduct();

BigDecimal rg_money;
String strButton=enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale);//��ѡ��


String period_unit=enfo.crm.tools.LocalUtilis.language("message.monthes",clientLocale);//��
String preCodeOptions = null;
//contract.setProduct_id(product_id);
Integer open_flag = new Integer(1);

int intrust_flag1=2;
Integer cust_id = null;
Integer contact_id=null;
String bank_id = "";
String product_code = "";

String rg_money_cn = "";
String sg_price_cn= "";
int is_local = 0;
Map contractMap =null;
Map custMap = null;
Map productMap =null;
Integer product_id = new Integer(0);
Integer sub_product_id = new Integer(0);
Integer prov_flag = new Integer(0);
String prov_level = "";
String product_name = "";
String sub_product_name = "";
String bank_city = "",bank_province = "";
Integer recommend_man = new Integer(0);
if(serial_no!=null){
	vo.setBook_code(new Integer(1));
	vo.setSerial_no(serial_no);
	List contractList = apply.listBySql(vo);
	if(contractList!=null&&contractList.size()==1)
		contractMap = (Map)contractList.get(0);
	custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
	custvo.setInput_man(new Integer(888));
	List custList = customer.listCustomerLoad(custvo);
	if(custList!=null&&custList.size()==1)
		custMap = (Map)custList.get(0);
	cust_id = Utility.parseInt(Utility.trimNull(contractMap.get("CUST_ID")),null);
	product_id = Utility.parseInt(Utility.trimNull(contractMap.get("PRODUCT_ID")),new Integer(0));
	sub_product_id = Utility.parseInt(Utility.trimNull(contractMap.get("SUB_PRODUCT_ID")),new Integer(0));
	prov_flag = Utility.parseInt(Utility.trimNull(contractMap.get("PROV_FLAG")),new Integer(0));
	prov_level = Utility.trimNull(contractMap.get("PROV_LEVEL"));
	Utility.debug("guifeng:"+product_id);
	productvo.setProduct_id(product_id);
	product_name = Utility.trimNull(contractMap.get("PRODUCT_NAME"));
	sub_product_name = Utility.trimNull(contractMap.get("SUB_PRODUCT_NAME"),"");
	recommend_man = Utility.parseInt(Utility.trimNull(
						contractMap.get("RECOMMEND_MAN")),new Integer(0));		
	bank_province = Utility.trimNull(contractMap.get("BANK_PROVINCE_NAME"));
	bank_city = Utility.trimNull(contractMap.get("BANK_CITY_NAME"));
	contact_id = Utility.parseInt(Utility.trimNull(contractMap.get("CONTACT_ID")),null);
	if(!"".equals(sub_product_name))
		product_name += "("+sub_product_name+")";
	List productList = product.load(productvo);
	if(productList!=null&&productList.size()>=1)
		productMap = (Map)productList.get(0);

}
//����Ϲ�����
	List attachmentList = null;
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(7));
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

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
function checkinfo(flag)
{
	var str = "";
	if(flag == 1)
		str = "<%=LocalUtilis.language("message.pass",clientLocale)%> "//���ͨ��
	if(sl_confirm(str)){		
	    //document.theform.submit();
		location.href = "apply_purchase_check_action.jsp?serial_no="+document.theform.serial_no.value + "&<%=sRetQuery%>";
    }
}

/*�ͻ���Ϣ*/
function getMarketingCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo_details_view.jsp?cust_id=' + cust_id + '&readonly=1' ;	
	showModalDialog(url,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');
}
</script>
<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="apply_purchase_check_action.jsp" >
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td class="page-title"><b><%=LocalUtilis.language("menu.applyInfo",clientLocale)%></b></td>
			</tr><!--�깺��Ϣ-->
		</TABLE>
		<br/>
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0 class="product-list">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
				<td  align="LEFT">
						<input type="text" readonly class="edline" maxlength="16" name="productid" value="<%=productMap!=null?Utility.trimNull(productMap.get("PRODUCT_CODE")):""%>" onkeydown="javascript:setProduct(this.value);" size="20">
				</td>
				<td  align="right"></td>
				<td  align="right"></td>
			</tr>
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
				<td align=left colspan=3>
					<input type="text" readonly class="edline" maxlength="16" name="productid" value="<%=Utility.trimNull(product_name)%>" onkeydown="javascript:setProduct(this.value);" size="75">
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.constractBH",clientLocale)%> :</td><!--��ͬ���--> 
				<td ><input readonly class="edline" name="contract_bh" size="20" maxlength=20  value="<%=Utility.trimNull(contractMap.get("CONTRACT_BH"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
				<td ><input readonly class="edline" name="contract_sub_bh" size="40" maxlength=40  value="<%=Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"))%>"></td>
			</tr>
			<tr>
				<td ><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--�ͻ���Ϣ-->
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--�ͻ�����-->
				<td>
					<input maxlength="100" readonly class='edline'  name="customer_cust_name" size="25" value="<%=Utility.trimNull(custMap.get("CUST_NAME"))%>">&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('<%=Utility.trimNull(custMap.get("CUST_ID"))%>');">��ϸ</button>
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="15"  value="<%=Utility.trimNull(Argument.getOpName((Integer)custMap.get("SERVICE_MAN")))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=custMap.get("CUST_TYPE_NAME")%>" ></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
				<td><input readonly class='edline' name="customer_card_id"   maxlength="40" value="<%=Utility.trimNull(custMap.get("CARD_ID"))%>" size="20"></td>
			</tr>
			<tr>
			<tr>
				<td align="right">��ͬ��ϵ�� :</td>
				<td colspan="3"><input readonly name="cust_type_name" size="25" value="<%=Utility.trimNull(contractMap.get("HT_CUST_NAME"))%>" class="edline">
					&nbsp;&nbsp;&nbsp;&nbsp;�绰 : <input readonly name="cust_type_name" size="20" value="" class="edline">
					&nbsp;&nbsp;&nbsp;&nbsp;��ַ : <input readonly name="cust_type_name" size="50" value="<%=Utility.trimNull(contractMap.get("HT_CUST_ADDRESS"))%>" class="edline">
				</td>
			</tr>
			
			<tr>
				<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td> <!--��ͬ��Ϣ--> 
			</tr>
			<tr>
				<td align="right" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--��������-->
				<td >
					<input  class="edline" readonly="readonly" type="text" name="bank_sub_name" size="50"  value="<%=Utility.trimNull(contractMap.get("BANK_NAME"))+Utility.trimNull(contractMap.get("BANK_SUB_NAME"))%>">
				</td>
				</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--�����ʺ�-->
				<td><input  class="edline" readonly="readonly" type="text" name="bank_acct" value="<%=Utility.ShowBankAcct(Utility.replaceAll(Utility.trimNull(contractMap.get("BANK_ACCT"))," ",""))%>"  onkeyup="javascript:showAcctNum(this.value)" size="30">
					<span id="bank_acct_num" class="span"></span> 
				</td>
				<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> :</td><!--�����ʺŻ���-->
				<td ><input  class="edline" readonly="readonly" name="customer_gain_acct" size="20" value="<%if(serial_no!=null) {out.print(Utility.trimNull(contractMap.get("GAIN_ACCT"))); }else {out.print(Utility.trimNull(custMap.get("CUST_NAME")));}%>"></td>
			</tr>
			<tr>	
				<td align="right">���������ڵ� :</td><!--���������ڵ�-->
				<td><input readonly class="edline" name="bank_province" size="40" value="<%=Utility.trimNull(bank_province)%><%=Utility.trimNull(bank_city)%>"></td>
				<td align="right">�Ƽ��� :</td><!--�Ƽ���-->
				<td><input readonly class="edline" name="recommend_man" size="20" value="<%=Argument.getInturstOperatorName(recommend_man) %>">
				</td>			
			</tr>
			<tr>
			<td align="right">*<%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--�ɿʽ-->
				<td>
					<input  class="edline" readonly="readonly" type="text" name="jk_type_name" size="20"  value="<%=Utility.trimNull(contractMap.get("JK_TYPE_NAME"))%>">	
				</td>
				<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--������䷽ʽ-->
				<td>
					<select size="1" disabled name="bonus_flag"  style="WIDTH: 120px">
						<%=Argument.getBonus_flag((Integer)contractMap.get("BONUS_FLAG"))%>
					</select>		
				</td>
			</tr>
			
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.rgMoney2",clientLocale)%> :</td><!--�깺���-->
				<td><input readonly="readonly" name="rg_money" size="20" class="edline" value="<%=Utility.trimNull(contractMap.get("SG_MONEY"))%>"  onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"> <span id="rg_money_cn" class="span"><%//=rg_money_cn%></span></td>
				<td align="right"><%=LocalUtilis.language("class.feeJKType",clientLocale)%> :</td><!--�깺�۽ɷ�ʽ-->
				<td >
					<input  class="edline" readonly="readonly" type="text" name="valid_period" size="20"  value="<%if(contractMap.get("FEE_JK_TYPE")!=null&&((Integer)contractMap.get("FEE_JK_TYPE")).intValue()==0){out.print("�������");}else if(contractMap.get("FEE_JK_TYPE")!=null&&((Integer)contractMap.get("FEE_JK_TYPE")).intValue()==1){out.print("�ӱ���۳�");}else{out.print("�������");}%>">					
				</td>
			</tr>
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--ǩ������-->
				<td>
					<INPUT class="edline" readonly="readonly" TYPE="text" NAME="qs_date" value="<%=Format.formatDateLine((Integer)contractMap.get("QS_DATE"))%>">
				</td>
				<td align="right">*<%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
				<td>					
					<INPUT  class="edline" readonly="readonly" TYPE="text" NAME="jk_date" value="<%=Format.formatDateLine((Integer)contractMap.get("JK_DATE"))%>">
				</td>			
			</tr>
			<tr>
			<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%> :</td><!-- �������ȼ� -->
			<td>					
				<select name="prov_flag" style="WIDTH: 125px; " onkeydown="javascript:nextKeyPress(this)" disabled="disabled">
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
				<td align="right">Ԥ�������� :</td>
				<td>
					<input class="edline" readonly="readonly" TYPE="text" name="expect_ror_lower" size="12" 
						value="<%=Utility.trimZero(((BigDecimal)(contractMap.get("EXPECT_ROR_LOWER")==null?new BigDecimal(0):contractMap.get("EXPECT_ROR_LOWER"))).multiply(new BigDecimal(100)))%>" ><font color="red">%</font> �� 
					<input class="edline" readonly="readonly" TYPE="text" name="expect_ror_upper" size="12" 
						value="<%=Utility.trimZero(((BigDecimal)(contractMap.get("EXPECT_ROR_UPPER")==null?new BigDecimal(0):contractMap.get("EXPECT_ROR_UPPER"))).multiply(new BigDecimal(100)))%>" ><font color="red">%</font>
				</td>
			</tr>
			<tr>
				<td  width="100px" align="right">�������� :</td> 
				<td><input readonly class="edline" value="<%=Argument.getDictContent(Utility.trimNull(contractMap.get("CHANNEL_TYPE")))%>-<%=Argument.getChannel(Utility.parseInt(Utility.trimNull(contractMap.get("CHANNEL_ID")),new Integer(0)))%>"/></td>
				<td align="right">�������� :</td><!--������Դ-->
				<td><input readonly class="edline" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("MARKET_MONEY")),new BigDecimal(0)))%>"/></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%> :</td> <!--��������-->
				<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_TYPE_NAME"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> :</td><!--��������-->
				<td><INPUT  class="edline" readonly="readonly" TYPE="text" NAME="channel_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_NAME"))%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.channelCooperation",clientLocale)%> :</td><!-- ����������ʽ -->
				<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_COOPERTYPE_NAME"))%>"></td>
				<td align="right">����������Ϣ :</td>
				<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_MEMO"))%>"></td>
			</tr>
			<tr>
				<td width="120px" align="right">���ֳ�����:</td>
				<td>   
					<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1" disabled <%=Utility.trimNull(contractMap.get("SPOT_DEAL")).equals("1") ? "checked" : ""%>>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--��ͬ������Ա-->
				<td colspan="3">
					<input readonly class="edline" name="link_man" size="20" value="<%=Argument.getOpName(serial_no!=null?(Integer)contractMap.get("LINK_MAN"):(Integer)custMap.get("SERVICE_MAN"))%>">
				</td>
			</tr>
			<%//if(productMap!= null && productMap.get("PRODUCT_FROM")!=null&&((Integer)productMap.get("PRODUCT_FROM")).intValue()==2){%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--��ͬ�ƽ��-->
				<td>
					<select disabled="disabled" size="1" name="city_serialno"  style="WIDTH: 120px">
					<%=Argument.getCitynameOptions((Integer)productMap.get("PRODUCT_ID"),(Integer)contractMap.get("CITY_SERIAL_NO"))%>
				</select>
				</td>
			</tr>
			<%//}%>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
				<td colspan="3"><input  class="edline" readonly="readonly" type="text" name="summary2" size="58"  value="<%=Utility.trimNull(contractMap.get("SUMMARY"))%>"></td>
			</tr>
			<tr id="reader1">
          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>���� :<%}%></td>
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
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:checkinfo(1)"><%=LocalUtilis.language("message.pass",clientLocale)%> (<u>S</u>)
							</button>&nbsp;&nbsp;&nbsp;<!--���ͨ��-->
							<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
							</button>&nbsp;&nbsp;&nbsp;<!--����-->
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%apply.remove();
customer.remove();
product.remove();
%>