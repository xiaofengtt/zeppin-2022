<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%
String sPage = request.getParameter("page");
String sQuery = request.getParameter("sQuery");
Integer contract_id = Utility.parseInt(request.getParameter("serial_no"),null);

IntrustContractLocal contract = EJBFactory.getIntrustContract();
IntrustCustomerInfoLocal customer = EJBFactory.getIntrustCustomerInfo();

int contract_bh_flag = Argument.getSyscontrolValue_intrust("C30902"); //C30902	认购合同的合同序号自动获取标志	1	1	1: 自动获取 2: 界面输入

StringBuffer list = Argument.newStringBuffer();
boolean bSuccess = false;
int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);
int selfbenflag = Utility.parseInt(request.getParameter("selfbenflag"), 1);

Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
input_bookCode=new Integer(1);
Integer cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), null);
customer.setCust_id(cust_id);
String bank_id = Utility.trimNull(request.getParameter("bank_id"));
String product_code=request.getParameter("product_code");
BigDecimal rg_money;
String strButton="请选择";

if(request.getParameter("rg_money")!=null)
 rg_money=new java.math.BigDecimal(request.getParameter("rg_money"));
 else{rg_money = new java.math.BigDecimal(0);}
int readonly=0;
String period_unit="月";
String preCodeOptions = null;
contract.setProduct_id(product_id);
IntrustProductLocal product = EJBFactory.getIntrustProduct();
if (contract_id == null && product_id != null)
{
	
	product.setProduct_id(product_id);
	if(product_id.intValue()>0)
	{
		product.load();
		period_unit=Argument.getProductUnitName(product.getPeriod_unit());
	}
	product_code=product.getProduct_code();
	contract.setValid_period(product.getValid_period());
	
	
	if(cust_id!=null && bank_id!=null)
	{
		customer.setCust_name(Utility.trimNull(request.getParameter("customer_cust_name")));
		customer.setMobile(Utility.trimNull(request.getParameter("customer_mobile")));
		customer.setCard_id(Utility.trimNull(request.getParameter("customer_card_id")));
		customer.setCust_type_name(Utility.trimNull(request.getParameter("customer_cust_type_name")));
		customer.setH_tel(Utility.trimNull(request.getParameter("customer_h_tel")));
		customer.setPost_code(Utility.trimNull(request.getParameter("customer_post_code")));
		customer.setPost_address(Utility.trimNull(request.getParameter("customer_post_address")));
		
		preCodeOptions = Argument.getCustBankAcctOptions(cust_id,bank_id,customer.getCard_id(),contract.getBank_acct());
	}
}

String busi_id = "120388";

contract.setSerial_no(contract_id);
if (contract_id != null)
{
	contract.load();
	product_id=contract.getProduct_id();
	product.setProduct_id(product_id);
	product.load();
	period_unit=Argument.getProductUnitName(product.getPeriod_unit());
	
	product_code = contract.getProduct_code();
	customer.setCust_id(contract.getCust_id());
	customer.setInput_man(input_operatorCode);
	customer.load();
	readonly=1;
	strButton="查看";
	
	if(preCodeOptions==null)
	{
		preCodeOptions =Argument.getCustBankAcctOptions(contract.getCust_id(),bank_id,contract.getCard_id(),contract.getBank_acct());
	}
	if(!"".equals(bank_id))
		contract.setBank_id(bank_id);
		
	if (contract.getCheck_flag()!=null&&contract.getCheck_flag().intValue() == 2)
		response.sendRedirect("purchase_info_check.jsp?checkflag=2&service=1&propertyFlag=1&serial_no=" + contract_id+"&product_id="+product_id);
		
	int firstflag = Utility.parseInt(request.getParameter("firstflag"), 0);
}
	
if (request.getMethod().equals("POST"))
{
	contract.setBook_code(input_bookCode);
	contract.setCust_id(Utility.parseInt(request.getParameter("customer_cust_id"), null));
	contract.setProduct_id(product_id);
	contract.setLink_man(Utility.parseInt(request.getParameter("link_man"), null));
	contract.setRg_money(Utility.parseDecimal(request.getParameter("rg_money"),new java.math.BigDecimal(0)));
	
	contract.setJk_type(Utility.trimNull(request.getParameter("jk_type"),"111401"));
	contract.setBank_id(request.getParameter("bank_id"));
	contract.setBank_sub_name(Utility.trimNull(request.getParameter("bank_sub_name")));
	contract.setEntity_name(request.getParameter("entity_name"));
	contract.setEntity_type(request.getParameter("entity_type"));
	contract.setEntity_price(Utility.parseDecimal(request.getParameter("entity_price"),new java.math.BigDecimal(0)));
	contract.setContract_sub_bh(request.getParameter("contract_sub_bh"));
	
	if(inputflag==1)
	{
		contract.setBank_acct(request.getParameter("bank_acct2"));
	}
	else
	{
		contract.setBank_acct(request.getParameter("bank_acct"));
	}
	contract.setService_man(Utility.parseInt(request.getParameter("service_man"), null));
	contract.setSummary(request.getParameter("summary2"));
	contract.setContract_bh(request.getParameter("contract_bh"));
	contract.setValid_period(Utility.parseInt(request.getParameter("valid_period"), null));
	contract.setInput_man(input_operatorCode);
	contract.setQs_date(Utility.parseInt(request.getParameter("qs_date"), new Integer(0)));
	contract.setJk_date(Utility.parseInt(request.getParameter("qs_date"), new Integer(0)));
	contract.setCheck_man(Utility.parseInt(request.getParameter("check_man"), new Integer(0)));
	contract.setInput_man(input_operatorCode);
	contract.setCity_serialno(Utility.parseInt(request.getParameter("city_serialno"), new Integer(0)));
	contract.setStart_date(Utility.parseInt(request.getParameter("start_date"),null));
	contract.setEnd_date(Utility.parseInt(request.getParameter("end_date"),null));
	contract.setBonus_flag(Utility.parseInt(request.getParameter("bonus_flag"),new Integer(1)));
	if (contract_id == null)
	{
		contract.setSelf_ben_flag(selfbenflag);
		contract.appendNoPre();
		contract_id=contract.getSerial_no();
	}
	else
	{
		contract.save();
	}
	
	bSuccess = true;
}
else if (contract_id == null)
{
	contract.setContract_bh(request.getParameter("contract_bh"));
	contract.setJk_type(request.getParameter("jk_type"));
	contract.setBank_id(request.getParameter("bank_id"));
	if (contract.getValid_period() == null)
		contract.setValid_period(Utility.parseInt(request.getParameter("valid_period"), null));
	if (contract.getService_man() == null)
		contract.setService_man(input_operatorCode);
	contract.setQs_date(Utility.parseInt(request.getParameter("qs_date"), null));
	contract.setLink_man(input_operatorCode);
	contract.setService_man(input_operatorCode);
	contract.setEntity_name(request.getParameter("entity_name"));
	contract.setEntity_type(request.getParameter("entity_type"));
	contract.setEntity_price(Utility.parseDecimal(request.getParameter("entity_price"),new java.math.BigDecimal(0)));
	contract.setRg_money(Utility.parseDecimal(request.getParameter("rg_money"),new java.math.BigDecimal(0)));
	contract.setContract_sub_bh(request.getParameter("contract_sub_bh"));
	
}
if (customer.getCust_id() == null)
{
	customer.setCust_level("111101");
	customer.setCust_level_name("未签约客户");
	customer.setCust_name(Utility.trimNull(request.getParameter("customer_cust_name")));
	customer.setMobile(Utility.trimNull(request.getParameter("customer_mobile")));
	customer.setCard_id(Utility.trimNull(request.getParameter("customer_card_id")));
	customer.setCust_type_name(Utility.trimNull(request.getParameter("customer_cust_type_name")));
	customer.setH_tel(Utility.trimNull(request.getParameter("customer_h_tel")));
	customer.setPost_code(Utility.trimNull(request.getParameter("customer_post_code")));
	customer.setPost_address(Utility.trimNull(request.getParameter("customer_post_address")));
	
	contract.setEntity_name(request.getParameter("entity_name"));
	contract.setEntity_type(request.getParameter("entity_type"));
	contract.setEntity_price(Utility.parseDecimal(request.getParameter("entity_price"),new java.math.BigDecimal(0)));
	contract.setRg_money(Utility.parseDecimal(request.getParameter("rg_money"),new java.math.BigDecimal(0)));
	contract.setContract_sub_bh(request.getParameter("contract_sub_bh"));
	
}

String entity_price_cn = "";
if (contract.getEntity_price() != null)
	entity_price_cn = "(" + Utility.numToChinese(contract.getEntity_price().toString()) + ")";
String rg_money_cn = "";
if (contract.getRg_money() != null)
	rg_money_cn = "(" + Utility.numToChinese(contract.getRg_money().toString()) + ")";
	

	
String wt_flag_checked = "";
if (customer.getCust_id() != null)
{
	if(customer.getWt_flag()!=null)
		if(customer.getWt_flag().intValue() == 1)
			wt_flag_checked = " checked ";
}
if (preCodeOptions == null)
	preCodeOptions = Argument.newStringBuffer().toString();
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
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>
<%
if (bSuccess && (Utility.parseInt(request.getParameter("serial_no"),new Integer(0)).intValue()==0))
{
	if(selfbenflag==0)
	{%>
	if (confirm("签约成功！合同编号为：<%=contract.getContract_bh()%>，需要进行该合同受益人维护吗？"))
		location = "benifiter.jsp?from_flag=2&contract_id=<%=contract_id%>&sQuery=<%=sQuery%>";
	else
		location = "purchase_info3.jsp?page=<%=sPage%>&sQuery=<%=sQuery%>&product_id=<%=product_id%>&jk_type=<%=request.getParameter("jk_type")%>&valid_period=<%=request.getParameter("valid_period")%>&qs_date=<%=request.getParameter("qs_date")%>";
<%}else{%>
	if(confirm("签约成功！是否继续新增合同？")){
		location ="purchase_info3.jsp?page=<%=sPage%>&sQuery=<%=sQuery%>&product_id=<%=product_id%>&jk_type=<%=request.getParameter("jk_type")%>&valid_period=<%=request.getParameter("valid_period")%>&qs_date=<%=request.getParameter("qs_date")%>";	
	}
	else{
		location="purchase3.jsp?product_id=<%=product_id%>";
	}
		
<%}
}%>

<%if (bSuccess && (Utility.parseInt(request.getParameter("serial_no"),new Integer(0)).intValue()>0))
{
%>
	sl_update_ok();
	op_return();
<%}%>

function op_return()
{	
	tempArray = '<%=sQuery%>'.split('$');
	location = 'purchase3.jsp?page=1&pagesize=' + tempArray[5] +'&productid='+tempArray[0]+ '&product_id='+ tempArray[1] +'&cust_name='+tempArray[2]+'&query_contract_bh='+ tempArray[3]+'&card_id='+tempArray[4] ;
}

function getLastOptions()
{
	if(!sl_checkDate(document.theform.qs_date_picker,"签署日期"))	return false;
	syncDatePicker(document.theform.qs_date_picker, document.theform.qs_date);
	
	return "&product_id=" + document.theform.product_id.value 
			+ "&valid_period=" + document.theform.valid_period.value 
			+ "&qs_date=" + document.theform.qs_date.value 
			+ "&contract_bh="+document.theform.contract_bh.value
			+"&contract_sub_bh="+document.theform.contract_sub_bh.value
			+ "&customer_cust_name="+ document.theform.customer_cust_name.value
			+ "&customer_cust_type_name="+ document.theform.customer_cust_type_name.value
			+ "&customer_card_id="+ document.theform.customer_card_id.value
			+ "&customer_post_code="+ document.theform.customer_post_code.value
			+ "&customer_post_address="+ document.theform.customer_post_address.value
			+"&customer_h_tel="+ document.theform.customer_h_tel.value
			+"&customer_cust_id="+ document.theform.customer_cust_id.value
			+"&customer_mobile="+ document.theform.customer_mobile.value+"&sQuery=<%=sQuery%>";
}

function selectBank(value)
{
	if (value != "")
	{	
		<%if(contract_id!=null)
		{%>
			location = 'purchase_info3.jsp?page=<%=sPage%>&bank_id=' + value+'&serial_no=<%=contract_id%>' 
					+'&entity_price='+document.theform.entity_price.value 
					+'&entity_type='+document.theform.entity_type.value 
					+'&rg_money='+document.theform.rg_money.value 
					+'&entity_name='+document.theform.entity_name.value 
					+ getLastOptions();
		<%}else
		{%> 	
			location = 'purchase_info3.jsp?page=<%=sPage%>&bank_id=' + value
			+'&entity_price='+document.theform.entity_price.value 
			+'&entity_type='+document.theform.entity_type.value 
			+'&rg_money='+document.theform.rg_money.value 
			+'&entity_name='+document.theform.entity_name.value 
			+ getLastOptions();
		<%}%>
	}
}

function selectProductItem(){
	location.replace('purchase_info3.jsp?page=<%=sPage%>' + getLastOptions());
}

/*黑名单筛查*/
function checkBlack(){	
	var button_save = document.getElementById("btnSave");
	var button_check_black = document.getElementById("btnCheckBlack");

	//var cust_name = document.theform.cust_name.value;	
	var   cust_name=document.getElementById("kehumingcheng");
	if(cust_name.value==""){
	alert("必须选择客户名称才能进行黑名单筛查");
	return false;
	}
	//alert(cust_name.value)	;
	button_save.style.visibility="visible";
	var ret = showModalDialog('<%=request.getContextPath()%>/marketing/purchase_info_check_black.jsp?cust_name=' + cust_name.value, '', 'dialogWidth:1200px;dialogHeight:800px;status:0;help:0');
}


function validateForm(form){
	if(!sl_checkChoice(form.product_id, "产品名称"))	return false;
	
	<%if(contract_bh_flag!=1){%>
		if(!sl_check(form.contract_bh, "合同序号", 16,3))	return false;
	<%}%>
	if(!sl_checkDecimal(form.entity_price, "财产名义价格", 13, 3, 1))	return false;
	if(form.entity_price.value==0){
		sl_alert("请检查财产名义价格！");
		return false;		
	}
	if(!sl_checkChoice(form.entity_type, "财产类别"))	return false;
	if(!sl_checkDecimal(form.rg_money, "合同金额", 13, 3, 1))	return false;
	if(form.rg_money.value==0){
		sl_alert("请检查合同金额！");
		return false;		
	}
	
	if(form.bank_id.value != "")
	{
		if(form.bank_acct.value==""&&form.bank_acct2.value==""){sl_alert("请选择银行账号"); return false;}
	}
	if(form.bank_acct.value != "")
	{
		if(!sl_checkChoice(form.bank_id, "银行名称"))	return false;
	}
	
	if(form.serial_no.value=='null' || form.serial_no.value==null || form.serial_no.value=='')
	{
		if(form.self_ben_flag.checked==true)
			form.selfbenflag.value='1';
		else
			form.selfbenflag.value='0';
			
		if(form.customer_cust_id.value=="" || form.customer_cust_id.value=="0"||form.customer_cust_id.value=="null")	
		{
			sl_alert("请选择客户！");
			return false;
		}	
	}
	
	
	if(!sl_checkNum(form.valid_period, "合同期限", 10, 1))	return false;
	if(form.valid_period.value==0){
		sl_alert("请检查合同期限！");
	}
	
	if(!sl_checkDate(form.qs_date_picker,"签署日期"))	return false;
	if(!sl_check(form.summary2, "合同备注", 100, 0))	return false;
	
	syncDatePicker(form.qs_date_picker, form.qs_date);
	syncDatePicker(form.start_date_picker, form.start_date);
	syncDatePicker(form.end_date_picker, form.end_date);
	if(form.start_date.value =="" || form.start_date.value==null)
	{	
		
	}else{
		if(!sl_checkDate(form.start_date_picker,"开始日期"))	return false;
		if(form.start_date.value<form.qs_date.value)
		{
			sl_alert("开始日期不能早于签署日期");
			return false;
		}
	}
	
	if(form.end_date.value =="" || form.end_date.value==null)
	{	
		
	}else{
		if(!sl_checkDate(form.end_date_picker,"结束日期"))	return false;
		if(form.end_date.value<form.start_date.value)
		{
			sl_alert("结束日期不能早于开始日期");
			return false;
		}
	}
		
	return sl_check_update();
}

function showAcctNum(value)
{		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				location.replace('purchase_info3.jsp?page=<%=sPage%>' + getLastOptions());
				//break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在或者该产品不在推介期！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}
function changeInput(obj)
{
	if(document.theform.inputflag.value==1)
	{
		obj.innerText="选择";
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else
	{
		obj.innerText="输入";
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}
function openQuery(product_id,item_id)
{	
	if((product_id != 0) && (product_id != 'null'))
	showModalDialog('../project/product_query_info2.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:720px;dialogHeight:580px;status:0;help:0');
}

function getTransactionCustomer2(prefix,readonly,cust_flag)
{
    
	cust_id = getElement(prefix, "cust_id").value;
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
	if(cust_flag == 2) 
	 	url = '<%=request.getContextPath()%>/marketing/customer_info_simple.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
	v = showModalDialog(url,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');

	if (v != null)
	{
		showTransactionCustomer2(prefix, v);
	}	
	return (v != null);
}
</script>
<BODY class="BODY body-nox" >

<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="purchase_info3.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<input type="hidden" name="serial_no" value="<%=contract_id%>">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="selfbenflag" value="">
<input type="hidden" name="customer_cust_id" value=<%=customer.getCust_id()%>>
<input type="hidden" name="period_unit" value="<%=product.getPeriod_unit()%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td class="page-title"><b>财产信托签约信息</b></td>
			</tr>
		</TABLE>
		<br/>
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0 class="product-list">
			<tr>
				<td  align="right">产品编号:</td>
				<td  colspan=3 align="LEFT">
						<input type="text" <%if (contract_id != null) out.print("readonly class='edline'");%> maxlength="16" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="20">
				</td>
			</tr>
			<tr>
				<td align="right">*产品选择:</td>
				<td colspan=3 align=left>
					<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProductItem();" <%if (contract_id == null){%> onchange="javascript:selectProductItem();" <%} else out.print("disabled");%>  class="productname">
						<%=Argument.getProductListOptions(input_bookCode, product_id, "", input_operatorCode,18)%>
					</select>
					<button type="button"  name="show_info" title="查看产品信息" onclick="javascript:openQuery('<%=product.getProduct_id()%>','<%=product.getItem_id()%>');"<%if(product.getProduct_id().intValue()==0) out.print("style='display:none;'");%>>查看</button>
				</td>
			</tr>
			<tr>
				<td align="right" <%if(contract_bh_flag==1){out.print("style='display:none'");}%>><b>*合同序号:</b></td>
				<td colspan=3 <%if(contract_bh_flag==1){out.print("style='display:none'");}%>><input name="contract_bh" size="20" maxlength=16 onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract.getContract_bh())%>"></td>
			</tr>
			<tr>
				<td align="right">合同编号:</td>
				<td colspan=3><input name="contract_sub_bh" size="60" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract.getContract_sub_bh())%>"></td>
			</tr>
			<tr>
				<td >客户信息:</td>
				<td><button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="客户信息" onclick="javascript:getTransactionCustomer2('customer','<%=readonly%>','<%=Argument.getSyscontrolValue("CUSTINFO")%>');"><%=strButton%></button></td>
			<tr>
			<tr>
				<td align="right">客户名称:</td>
				<td colspan=3><input maxlength="100" readonly class='edline' id="kehumingcheng"  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(customer.getCust_name())%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right">客户类别:</td>
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=customer.getCust_type_name()%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right">证件号码:</td>
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(customer.getCard_id())%>" size="20"></td>
			</tr>
			<tr>
				<td align="right">住宅电话:</td>
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(customer.getCust_tel())%>" size="20"></td>
				<td align="right">手机:</td>
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(customer.getMobile())%>" size="20"></td>
			</tr>
			<tr>
				<td align="right">联系地址:</td>
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=Utility.trimNull(customer.getPost_address())%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right">邮政编码:</td>
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=Utility.trimNull(customer.getPost_code())%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			
			<tr>
				<td><b>合同信息</b></td>
			<tr>
			<tr>
				<td align="right">*财产名义价格:</td>
				<td><input name="entity_price" size="20" value="<%=Utility.trimNull(contract.getEntity_price())%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,entiry_price_cn);document.theform.rg_money.value=this.value;showCnMoney(this.value,rg_money_cn);"> <span id="entiry_price_cn" class="span"><%=Utility.trimNull(entity_price_cn)%></span></td>
				<td align="right">*财产类别:</td>
				<td><select size="1" name="entity_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Argument.getDictParamOptions_intrust(1140,contract.getEntity_type())%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">*合同金额:</td>
				<td><input name="rg_money" size="20" value="<%=Utility.trimNull(contract.getRg_money())%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)"> <span id="rg_money_cn" class="span"><%=rg_money_cn%></span></td>
				
				<td align="right">财产名称:</td>
				<td align="left"><input name="entity_name" size="40" value="<%=Utility.trimNull(contract.getEntity_name())%>" onkeydown="javascript:nextKeyPress(this)" > </td>
			</tr>
			<tr>
				<td align="right" >银行名称:</td>
				<td ><select size="1" name="bank_id" onchange="javascript:selectBank(this.value);" style="WIDTH: 180px">
					<%=Argument.getBankOptions(contract.getBank_id())%>
				</select>
				<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract.getBank_sub_name())%>">
				</td>
				<td align="right">银行帐号:</td>
				<td><input <%if(inputflag==1) out.print("style=display:none");%>  type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30">

					<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 200px">
						<%=preCodeOptions%>
					</select>&nbsp;&nbsp;										
					<button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="输入" onclick="javascript:changeInput(this);"><%if(inputflag==1) out.print("输入");else out.print("选择");%>(<u>X</u>)</button>
					<span id="bank_acct_num" class="span"></span>
				</td>
			</tr>
			
			<tr>
				<td align="right">*合同期限:</td>
				<td><input type="text" name="valid_period" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract.getValid_period())%>"> <%=Utility.trimNull(period_unit)%></td>
				<td align="right">*签署日期:</td>
				<td>
					<INPUT TYPE="text" NAME="qs_date_picker" class=selecttext
					<%if(contract.getQs_date()==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(contract.getQs_date())%>"<%}%> >
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.qs_date_picker,theform.qs_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="qs_date" value="<%=contract.getQs_date()%>">
				</td>
			</tr>
			
			<tr>
				<td align="right">合同销售人员:</td>
				<td><select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getRoledOperatorOptions_intrust(input_bookCode, 2, contract.getLink_man())%>
				</select></td>
				<td align="right">客户经理:</td>
				<td><select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getRoledOperatorOptions_intrust(input_bookCode, 2, contract.getService_man())%>
				</select></td>
			</tr>
			<tr>
				<td align="right">开始日期:</td>
				<td>
					<INPUT TYPE="text" NAME="start_date_picker" class=selecttext
					<%if(contract.getStart_date()==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(contract.getStart_date())%>"<%}%> >
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="start_date" value="<%=contract.getStart_date()%>">
				</td>
				<td align="right">结束日期:</td>
				<td>
					<INPUT TYPE="text" NAME="end_date_picker" class=selecttext
					<%if(contract.getEnd_date()==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(contract.getEnd_date())%>"<%}%> >
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="end_date" value="<%=contract.getEnd_date()%>">				
				</td>
			</tr>
			<tr>
				<td colspan="3"></td>
				<td><font color="red" >注:合同期限与结束日期(如果输入结束日期,则以结束日期为准)</font></td>
			</tr>
			<tr>
				<td align="right">合同推介地:</td>
				<td><select size="1" name="city_serialno" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getCitynameOptions(product_id==null?new Integer(0):product_id,contract.getCity_serialno()==null?new Integer(0):contract.getCity_serialno())%>
				</select></td>
				<td align="right"><%if (contract_id == null){%> 自益标志:<%}%></td>
				<td><%if (contract_id == null){%><input name="self_ben_flag" class="flatcheckbox" type="checkbox" value="<%=selfbenflag%>" <%if(selfbenflag==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)"><%}%></td>
			</tr>
				
			<tr>
				<td align="right">备注:</td>
				<td colspan="3"><input type="text" name="summary2" size="58" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract.getSummary())%>"></td>
			</tr>
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">

						<% if(user_id.intValue()==2){%><!--北京实际的那边的值是2 -->
						<button  class="xpbutton3" id="btnCheckBlack" name="btnCheckBlack" onclick="javascript:checkBlack();">黑名单筛查</button>
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);
						document.theform.submit();}" style="visibility: hidden">保存(<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}else{%>
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);
						document.theform.submit();}">保存(<u>S</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<button class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);
						op_return();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;</td>
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
<%contract.remove();
customer.remove();
%>