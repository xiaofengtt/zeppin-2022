 <%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer contract_type = Utility.parseInt(request.getParameter("contract_type"), new Integer(-1));  
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(-1));  
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(0));  
String prov_level = Utility.trimNull(request.getParameter("prov_level")).trim();  
String channel_coopertype = Utility.trimNull(request.getParameter("channel_coopertype")).trim();  
Integer valid_period = Utility.parseInt(request.getParameter("valid_period"), null); 
Integer period_unit = Utility.parseInt(request.getParameter("period_unit"), null); ;
Integer with_bank_flag = Utility.parseInt(request.getParameter("with_bank_flag"), new Integer(0)); 
Integer with_security_flag = Utility.parseInt(request.getParameter("with_security_flag"), new Integer(0)); 
Integer with_private_flag = Utility.parseInt(request.getParameter("with_private_flag"), new Integer(0)); 
String ht_bank_id = Utility.trimNull(request.getParameter("ht_bank_id")).trim();  
String ht_bank_sub_name = Utility.trimNull(request.getParameter("ht_bank_sub_name")).trim();  
String jg_wtrlx2 = Utility.trimNull(request.getParameter("jg_wtrlx2")).trim();  
Integer city_serial_no = Utility.parseInt(request.getParameter("city_serial_no"), null);
int check_flag = Utility.parseInt(request.getParameter("check_flag"), 0);
Integer spot_deal = null;
int selfbenflag = 0;
String is_ykgl = null;
// 返回用到的原查询条件	

String sRetQuery = Utility.getQueryString(request, new String[] {"page", "pagesize", "product_id", "productid", "product_name", "cust_name", "query_contract_bh"});

ContractVO vo = new ContractVO();
CustomerInfoVO custvo = new CustomerInfoVO();
ProductVO productvo = new ProductVO();
ContractLocal contract = EJBFactory.getContract();
ApplyreachLocal apply = EJBFactory.getApplyreach();
ApplyreachVO appvo = new ApplyreachVO();
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
ProductLocal product = EJBFactory.getProduct();

BigDecimal rg_money;
String strButton=enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale);//请选择


String preCodeOptions = null;
//contract.setProduct_id(product_id);
Integer open_flag = new Integer(1);

boolean bSuccess = false;


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
List attachmentList = null;
Integer product_id = new Integer(0);
Integer sub_product_id = new Integer(0);
String product_name = "";
String sub_product_name = "";
String bank_city = "",bank_province = "";
Integer recommend_man = new Integer(0);


if ("POST".equals(request.getMethod())) {

	vo.setContract_type(contract_type);
	vo.setSerial_no(serial_no);
	vo.setProv_flag(prov_flag);
	vo.setProv_level(prov_level);
	vo.setChannel_cooperation(channel_coopertype);
	vo.setWith_bank_flag(with_bank_flag);
	vo.setHt_bank_id(ht_bank_id);
	vo.setHt_bank_sub_name(ht_bank_sub_name);
	vo.setWith_secuity_flag(with_security_flag);
	vo.setWith_private_flag(with_private_flag);
	vo.setCity_serialno(city_serial_no);
	vo.setJg_wtrlx2(jg_wtrlx2);
	vo.setValid_period(valid_period);
	vo.setPeriod_unit(period_unit);
	vo.setInput_man(input_operatorCode);
	contract.modiContractFor2ndEdit(vo);
	if(check_flag == 1){
		//认购审核
		if(contract_type.intValue() == 1)
			contract.contractCheck(vo);
		//申购审核
		if(contract_type.intValue() == 2){
			appvo.setSerial_no(serial_no);
			appvo.setInput_man(input_operatorCode);
			apply.checkApplyreachContract(appvo);
		}
	}
	bSuccess = true;
}
if (serial_no.intValue()>0 && contract_type.intValue()>0){
	vo.setBook_code(new Integer(1));
	vo.setSerial_no(serial_no);
	vo.setContract_type(contract_type);
	vo.setInput_man(input_operatorCode);
	List contractList = contract.listContractFor2ndEdit(vo);
	if(contractList!=null&&contractList.size()==1)
		contractMap = (Map)contractList.get(0);

	custvo.setCust_id((Integer)contractMap.get("CUST_ID"));
	custvo.setInput_man(new Integer(888));
	List custList = customer.load(custvo);
	if(custList!=null&&custList.size()==1)
		custMap = (Map)custList.get(0);
	cust_id = Utility.parseInt(Utility.trimNull(contractMap.get("CUST_ID")),null);
	product_id = Utility.parseInt(Utility.trimNull(contractMap.get("PRODUCT_ID")),new Integer(0));
	sub_product_id = Utility.parseInt(Utility.trimNull(contractMap.get("SUB_PRODUCT_ID")),new Integer(0));
	prov_flag = Utility.parseInt(Utility.trimNull(contractMap.get("PROV_FLAG")),new Integer(0));
	prov_level = Utility.trimNull(contractMap.get("PROV_LEVEL"));
	productvo.setProduct_id(product_id);
	product_name = Utility.trimNull(contractMap.get("PRODUCT_NAME"));
	sub_product_name = Utility.trimNull(contractMap.get("SUB_PRODUCT_NAME"),"");
	recommend_man = Utility.parseInt(Utility.trimNull(
					contractMap.get("RECOMMEND_MAN")),new Integer(0));		
	bank_province = Utility.trimNull(contractMap.get("BANK_PROVINCE_NAME"));
	bank_city = Utility.trimNull(contractMap.get("BANK_CITY_NAME"));
	contact_id = Utility.parseInt(Utility.trimNull(contractMap.get("CONTACT_ID")),null);

	with_bank_flag = (Integer)contractMap.get("WITH_BANK_FLAG");
	with_security_flag = (Integer)contractMap.get("WITH_SECURITY_FLAG");
	with_private_flag = (Integer)contractMap.get("WITH_PRIVATE_FLAG");
	ht_bank_id = (String)contractMap.get("HT_BANK_ID");
	ht_bank_sub_name = Utility.trimNull(contractMap.get("HT_BANK_SUB_NAME"));

	valid_period = (Integer)contractMap.get("VALID_PERIOD");	
	period_unit = (Integer)contractMap.get("PERIOD_UNIT");
	if(!"".equals(sub_product_name))
		product_name += "("+sub_product_name+")";
	List productList = product.load(productvo);
	if(productList!=null&&productList.size()>=1){
		productMap = (Map)productList.get(0);
		if(period_unit == null)
		{
			valid_period = (Integer)productMap.get("VALID_PERIOD");	
			period_unit = (Integer)productMap.get("PERIOD_UNIT");
		}
	}
	selfbenflag = Utility.parseInt(Utility.trimNull(contractMap.get("ZY_FLAG")),1);
	spot_deal = Utility.parseInt(Utility.trimNull(contractMap.get("SPOT_DEAL")),new Integer(2));
	is_ykgl = Utility.trimNull(contractMap.get("IS_YKGL"));
}
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
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
var success = <%=bSuccess%>;
var returl = "contract_2nd_edit_list.jsp?<%=sRetQuery%>";

window.onload = function() {
		if (success) {
			sl_alert("保存成功！");
			location.href = returl;
		}
		if (document.getElementById("jg_wtrlx2").style.display!="none")
			document.theform.jg_wtrlx2.focus();
		else 
			document.theform.valid_period.focus();
	};

function save(flag) {
	if(document.theform.with_bank_flag.checked){
		document.theform.with_bank_flag.value = 1;
	}
	if(document.theform.with_security_flag.checked){
		document.theform.with_security_flag.value = 1;
	}
	if(document.theform.with_private_flag.checked){
		document.theform.with_private_flag.value = 1;
	}

	if (sl_confirm("保存修改"))	
	    document.theform.submit();
}
function saveCheck() {
	if (! (document.theform.prov_level.options.length==0
			|| document.theform.prov_level.options.length==1 && document.theform.prov_level.options[0].value=="")
		&& document.theform.prov_level.value=="") {
		sl_alert("请选择收益级别！");
		return;
	}
	
	if(document.theform.with_bank_flag.checked){
		document.theform.with_bank_flag.value = 1;
	}
	if(document.theform.with_security_flag.checked){
		document.theform.with_security_flag.value = 1;
	}
	if(document.theform.with_private_flag.checked){
		document.theform.with_private_flag.value = 1;
	}
	document.theform.check_flag.value = 1;
	if (sl_confirm("保存修改并审核通过"))	{
		document.theform.action = "contract_2nd_check_action.jsp";
	    document.theform.submit();
	}
}
function showHtyh(){
	if(document.theform.with_bank_flag.checked){
		document.getElementById("htyhmc_v").style.display = "";
	}else{
		document.getElementById("htyhmc_v").style.display = "none";
	}
}
function provFlagChange(product_id,sub_product_id,prov_flag,prov_level_id){
	var _prodcut_id = product_id;
	var _sub_product_id = sub_product_id;
	var _prov_flag = prov_flag;
	var prov_level;
	
	prov_level = document.getElementById(prov_level_id);
	DWRUtil.removeAllOptions(prov_level);
	
	utilityService.getProvLevelJson(product_id,_sub_product_id,prov_flag,function(__data){
		var __json = eval(__data);
		DWRUtil.addOptions(prov_level,{"":"请选择"});
		for(i=0;i<__json.length;i++){
			DWRUtil.addOptions(prov_level,__json[i]);
		}		
	});
}
</script>

<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" name="contract_type" value="<%=contract_type%>">
<input type="hidden" name="check_flag" value="0">
<input type="hidden" name="serial_no" value="<%=serial_no%>">
<input type="hidden" name="sRetQuery" value="<%=sRetQuery%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td class="page-title"><b><%=LocalUtilis.language("menu.applyInfo",clientLocale)%> </b></td>
			</tr><!--申购信息-->
			<tr>
				<td>
			<br/>
				</td>
			</tr>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0 class="product-list">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
				<td  align="LEFT">
						<input type="text" readonly class="edline" maxlength="16" value="<%=productMap!=null?Utility.trimNull(productMap.get("PRODUCT_CODE")):""%>" size="20">
				</td>
				<td  align="right"></td>
				<td  align="right"></td>
			</tr>
			<tr>
				<td align="right"><b>*<%=LocalUtilis.language("class.productName",clientLocale)%> :</b></td><!--产品名称-->
				<td align=left colspan=3>
					<input type="text" readonly class="edline" maxlength="16" value="<%=Utility.trimNull(product_name)%>" size="75">
				</td>
			</tr>
			<tr>
				<td align="right"><b><%=LocalUtilis.language("class.constractBH",clientLocale)%> </b></td><!--合同序号--> 
				<td ><input readonly class="edline" name="contract_bh" size="20" maxlength=20  value="<%=Utility.trimNull(contractMap.get("CONTRACT_BH"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
				<td ><input readonly class="edline" name="contract_sub_bh" size="40" maxlength=40  value="<%=Utility.trimNull(contractMap.get("CONTRACT_SUB_BH"))%>"></td>
			</tr>
			<tr>
				<td ><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--客户信息-->
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50"  value="<%=Utility.trimNull(custMap.get("CUST_NAME"))%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50"  value="<%=Utility.trimNull(Argument.getOpName((Integer)custMap.get("SERVICE_MAN")))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=custMap.get("CUST_TYPE_NAME")%>" ></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td><input readonly class='edline' name="customer_card_id"   maxlength="40" value="<%=Utility.trimNull(custMap.get("CARD_ID"))%>" size="20"></td>
			</tr>
			<tr id="jg_wtrlx2" style="display:<%=((Integer)custMap.get("CUST_TYPE")).intValue()==1?"none":""%>">
				<td align="right">客户类型 :</td>
				<td colspan="3">
					<select name="jg_wtrlx2"><%=Argument.getJgWtrlx2List(Utility.trimNull(contractMap.get("JG_WTRLX2")))%></select>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td> <!--住宅电话--> 
				<td><input readonly class='edline' name="customer_h_tel"   maxlength="40" value="<%=Utility.trimNull(custMap.get("H_TEL"))%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
				<td><input readonly class='edline' name="customer_mobile"   maxlength="40" value="<%=Utility.trimNull(custMap.get("MOBILE"))%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=Utility.trimNull(custMap.get("POST_ADDRESS"))%>" ></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=Utility.trimNull(custMap.get("POST_CODE"))%>" ></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--关联方标志-->
				<td><input type="checkbox" disabled name="customer_is_link" value="<%=custMap.get("IS_LINK")%>" <%if(custMap.get("IS_LINK")!=null){if(((Integer)custMap.get("IS_LINK")).intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
				<td align="right">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
			
			<tr>
				<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td> <!--合同信息--> 
			</tr>			
			<tr>
				<td align="right" ><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--银行名称-->
				<td >
					<input  class="edline" readonly="readonly" type="text" name="bank_sub_name" size="50"  value="<%=Utility.trimNull(contractMap.get("BANK_NAME"))+Utility.trimNull(contractMap.get("BANK_SUB_NAME"))%>">
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--银行帐号-->
				<td><input  class="edline" readonly="readonly" type="text" name="bank_acct" value="<%=Utility.ShowBankAcct(Utility.replaceAll(Utility.trimNull(contractMap.get("BANK_ACCT"))," ",""))%>" size="30">
				</td>
				<td align="right"><%=LocalUtilis.language("class.customerGainAcct",clientLocale)%> </td><!--银行帐号户名-->
				<td ><input  class="edline" readonly="readonly" name="customer_gain_acct" size="20" value="<%if(serial_no!=null) {out.print(Utility.trimNull(contractMap.get("GAIN_ACCT"))); }else {out.print(Utility.trimNull(custMap.get("CUST_NAME")));}%>"></td>
			</tr>
			<tr>	
				<td align="right">开户行所在地 :</td><!--开户行所在地-->
				<td><input readonly class="edline" name="bank_province" size="40" value="<%=Utility.trimNull(bank_province)%><%=Utility.trimNull(bank_city)%>"></td>
				<td align="right">推荐人 :</td><!--推荐人-->
				<td><input readonly class="edline" name="recommend_man" size="20" value="<%=Argument.getInturstOperatorName(recommend_man) %>">
				</td>			
			</tr>
			<tr>
			<td align="right">*<%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--缴款方式-->
				<td>
					<input  class="edline" readonly="readonly" type="text" name="jk_type_name" size="20"  value="<%=Utility.trimNull(contractMap.get("JK_TYPE_NAME"))%>">	
				</td>
				<td align="right"><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :</td><!--收益分配方式-->
				<td>
					<select size="1" disabled name="bonus_flag"  style="WIDTH: 120px">
						<%=Argument.getBonus_flag((Integer)contractMap.get("BONUS_FLAG"))%>
					</select>		
				</td>
			</tr>
			
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.rgMoney2",clientLocale)%> :</td><!--申购金额-->
				<td><input readonly="readonly" name="rg_money" size="20" class="edline" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("MONEY")),new BigDecimal(0)))%>"  onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"> <span id="rg_money_cn" class="span"><%//=rg_money_cn%></span></td>
				<td align="right"><%=LocalUtilis.language("class.feeJKType",clientLocale)%> :</td><!--申购扣缴方式-->
				<td >
					<input class="edline" readonly="readonly" type="text" size="20"  value="<%if(contractMap.get("FEE_JK_TYPE")!=null&&((Integer)contractMap.get("FEE_JK_TYPE")).intValue()==0){out.print("无需缴纳");}else if(contractMap.get("FEE_JK_TYPE")!=null&&((Integer)contractMap.get("FEE_JK_TYPE")).intValue()==1){out.print("从本金扣除");}else{out.print("额外缴纳");}%>">					
				</td>
			</tr>
			<tr>
				<td align="right">*<%=LocalUtilis.language("class.qsDate",clientLocale)%> :</td><!--签署日期-->
				<td>
					<INPUT class="edline" readonly="readonly" TYPE="text" NAME="qs_date" value="<%=Format.formatDateLine((Integer)contractMap.get("QS_DATE"))%>">
				</td>
				<td align="right">*<%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--缴款日期-->
				<td>					
					<INPUT  class="edline" readonly="readonly" TYPE="text" NAME="jk_date" value="<%=Format.formatDateLine((Integer)contractMap.get("JK_DATE"))%>">
				</td>			
			</tr>
			<tr>
				<td  width="100px" align="right">渠道名称:&nbsp;&nbsp;</td> 
				<td><input readonly class="edline" value="<%=Argument.getDictContent(Utility.trimNull(contractMap.get("CHANNEL_TYPE")))%>-<%=Argument.getChannel(Utility.parseInt(Utility.trimNull(contractMap.get("CHANNEL_ID")),new Integer(0)))%>"/></td>
				<td align="right">渠道费用 :</td><!--渠道来源-->
				<td><input readonly class="edline" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(contractMap.get("MARKET_MONEY")),new BigDecimal(0)))%>"/></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>:</td> <!--渠道类型-->
				<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_TYPE_NAME"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>:</td><!--渠道名称-->
				<td><INPUT  class="edline" readonly="readonly" TYPE="text" NAME="channel_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_NAME"))%>"></td>
			</tr>
			<tr>
				<td align="right">渠道附加信息:</td>
				<td><INPUT class="edline" readonly="readonly" TYPE="text" NAME="channel_type_name" value="<%=Utility.trimNull(contractMap.get("CHANNEL_MEMO"))%>"></td>
				<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%> :</td><!--合同销售人员-->
				<td>
					<input readonly class="edline" name="link_man" size="20" value="<%=Argument.getOpName(serial_no!=null?(Integer)contractMap.get("LINK_MAN"):(Integer)custMap.get("SERVICE_MAN"))%>">
				</td>
			</tr>
			<tr>
				<td align="right">合同联系人:</td>
				<td>
					<select disabled size="1" name="cust_message">
					<%=Argument.getCustOptions(cust_id,contact_id) %>
					</select>
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
				<td colspan="3"><input  class="edline" readonly="readonly" type="text" name="summary2" size="58"  value="<%=Utility.trimNull(contractMap.get("SUMMARY"))%>"></td>
			</tr>
 
			<tr>
				<td align="right" >合同期限 :</td>
				<td >
					<input type="text" name="valid_period" size="5" value="<%=Utility.trimNull(valid_period)%>">
					<select size="1" id="period_unit" name="period_unit" style="WIDTH: 70px">
						<%=Argument.getPeriodValidUnitOptions(period_unit)%>
					</select>
				</td>
				<td align="right"><%=LocalUtilis.language("class.channelCooperation",clientLocale)%>:</td><!-- 渠道合作方式 -->
				<td><select name="channel_coopertype"><%=Argument.getChannelCooperationOptions(Utility.trimNull(contractMap.get("CHANNEL_COOPERTYPE")))%></select></td>
				
			</tr>
			<tr>		
				<td align="right"><%=LocalUtilis.language("class.provLevel",clientLocale)%>:</td><!-- 受益优先级 -->
				<td>					
					<select name="prov_flag" style="WIDTH: 125px; " onchange="javascript:provFlagChange(<%=product_id%>,<%=sub_product_id%>,this.value,'prov_level');" onkeydown="javascript:nextKeyPress(this)">
						<%=Argument.getProductProvFlag(product_id,sub_product_id,prov_flag)%>
					</select>
				</td>		
				<td align="right">*<%=LocalUtilis.language("class.incomeLevel",clientLocale)%>:</td><!-- 收益级别 -->
				<td>
					<select name="prov_level" style="width:125px;">
						<%=Argument.getProductProvLevel(product_id,sub_product_id,prov_flag,prov_level)%>
					</select>	
				</td>
				
			</tr>
<%if(((Integer)contractMap.get("PRODUCT_FROM")).intValue()==2) { // 异地 %>
			<%--if(productMap!= null && productMap.get("PRODUCT_FROM")!=null&&((Integer)productMap.get("PRODUCT_FROM")).intValue()==2){--%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.citySerialNO",clientLocale)%> :</td><!--合同推介地-->
				<td>
					<select size="1" name="city_serial_no"  style="WIDTH: 120px">
					<%=Argument.getCitynameOptions((Integer)productMap.get("PRODUCT_ID"),(Integer)contractMap.get("CITY_SERIAL_NO"))%>
				</select>
				</td>
			</tr>
<%}else{%>	
			<tr>
				<td align="right">推介地:</td>
				<td><select name="city"><option value="1" selected>本地</option></select></td>
			</tr> 
<%} %>
			<tr>
				<td align="right"><%=LocalUtilis.language("message.cooperationWay",clientLocale)%>:</td><!--合作方式-->
				<td colspan="3">
					<input <%if (with_bank_flag != null && with_bank_flag.intValue()==1)out.print("checked");%>
							onkeydown="javascript:nextKeyPress(this)" type="checkbox" class="flatcheckbox" value="<%=with_bank_flag %>" name="with_bank_flag" onclick="javascript:showHtyh();">
							<%=LocalUtilis.language("intrsut.home.silvertrustcooper",clientLocale)%>&nbsp;&nbsp;&nbsp;&nbsp;<!--银信合作-->
					<span id="htyhmc_v" <%if(with_bank_flag==null || with_bank_flag.intValue()!=1) out.print("style='display:none'");%>>
						<%=LocalUtilis.language("class.withBankId",clientLocale)%>:	<!-- 合作银行 -->
						<select size="1" name="ht_bank_id" style="WIDTH: 220px">
							<%=Argument.getBankOptions(Utility.trimNull(ht_bank_id))%>
						</select>
						<input type="text" name="ht_bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(ht_bank_sub_name)%>">
					</span>
					<input <%if (with_security_flag != null && with_security_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"
						class="flatcheckbox" value="<%=with_security_flag %>" name="with_security_flag"
						><%=LocalUtilis.language("message.cooperation2",clientLocale)%> &nbsp;&nbsp;<!--证信合作-->
					<input <%if (with_private_flag != null && with_private_flag.intValue()==1)out.print("checked");%>
						onkeydown="javascript:nextKeyPress(this)" type="checkbox"
						class="flatcheckbox" value="<%=with_private_flag %>" name="with_private_flag"
						><%=LocalUtilis.language("class.cooperation4",clientLocale)%> &nbsp;&nbsp;<!--私募基金合作-->
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.selfBenFlag",clientLocale)%>:</td><!--自益标志-->
				<td><input name="self_ben_flag" class="flatcheckbox" disabled type="checkbox" value="" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"></td>	
			</tr>
			<tr>
				<td width="120px" align="right">用款方关联:</td><!--用款方关联-->
				<td>   
					<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="1" <%if(is_ykgl.equals("1")) out.print("checked");%> disabled>是
					<INPUT type="radio" class="flatcheckbox" id="is_ykgl" name="is_ykgl" value="0" <%if(!is_ykgl.equals("1")) out.print("checked");%> disabled>否
				</td>
				<td width="120px" align="right">非现场交易:</td>
				<td>   
					<input type="checkbox" class="flatcheckbox" name="spot_deal" value="1" <%=spot_deal.intValue() == 1 ? "checked" : ""%> disabled>
				</td>
			</tr>
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
							<%if(user_id.intValue() ==2 ){%>	
							<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:save()">
								<%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
							</button>&nbsp;&nbsp;&nbsp;<!--保存-->					
							<button type="button"  class="xpbutton4" accessKey=c id="btnSave" name="btnSave" onclick="javascript:saveCheck()">
								保存并审核	
							</button>&nbsp;&nbsp;&nbsp;<!--审核通过-->
							<%}else{ %>
							<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:save()">
								<%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
							</button>&nbsp;&nbsp;&nbsp;<!--保存-->
							<%} %>
							<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
							</button>&nbsp;&nbsp;&nbsp;<!--返回-->
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
<script language=javascript>
//window.onload = function(){
//	provFlagChange(<%=product_id%>,<%=sub_product_id%>,document.theform.prov_flag.value,'prov_level');
//}
</script>
</BODY>
</HTML>
<%contract.remove();
apply.remove();
customer.remove();
product.remove();
%>