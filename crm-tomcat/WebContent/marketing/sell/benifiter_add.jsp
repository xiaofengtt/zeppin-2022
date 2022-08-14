<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//主键
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), new Integer(0));

Integer check_flag = Utility.parseInt(request.getParameter("check_flag"), new Integer(0));
String contract_bh = request.getParameter("contract_bh");//合同编号
int action_flag = Utility.parseInt(request.getParameter("action_flag"), 0);

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助变量
boolean bSuccess = false;
StringBuffer list = Argument.newStringBuffer();   //客户名称列表
int readonly=0;
String strButton=enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale);//请选择
String period_unit=enfo.crm.tools.LocalUtilis.language("message.monthes",clientLocale);//月
String provChecked = "";
String show="";
String show1="";

if(check_flag.intValue()==2){
	show = "readonly class='edline'";
	show1="disabled";
}

//客户信息
String cust_no = "";
String cust_name = "";
String cust_type_name = "";
String card_id = "";
Integer cust_type = new Integer(0);
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
//------------------------------------
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
String post_code = "";
String post_address = "";
String legal_man = "";
String contact_man = "";
Integer is_link = new Integer(0);
//------------------------------------

//产品信息
Integer open_flag = new Integer(0);
Integer hiddenFlag = new Integer(0);
Integer period_unit_value = new Integer(0);

//受益人信息
String prov_level = "";
String jk_type = "";
String bank_id = "";
String bank_sub_name = "";
String bank_acct = "";
String bank_acct_type = "";
String cust_acct_name = "";
Integer ben_date = new Integer(0);
Integer ben_end_date = new Integer(0);
Integer bonus_flag = new Integer(0);//收益分配标志
BigDecimal ben_amount = new BigDecimal("0.000");

//获取对象
BenifitorLocal benifitor = EJBFactory.getBenifitor();
CustomerLocal customer = EJBFactory.getCustomer();//客户
ProductLocal product=EJBFactory.getProduct();//产品
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象

BenifitorVO vo_ben = new BenifitorVO();
CustomerVO cust_vo = new CustomerVO();
ProductVO p_vo = new ProductVO();
CustomerVO c_vo = new CustomerVO();//同步用

//设置受益人信息
if(serial_no.intValue()>0){
	readonly=1;
	strButton=enfo.crm.tools.LocalUtilis.language("message.view",clientLocale);//查看
	
	List rsList_ben = null;
	Map map_ben = null;
	
	vo_ben.setSerial_no(serial_no);
	rsList_ben = benifitor.load(vo_ben);
	
	if(rsList_ben.size()>0){
		map_ben = (Map)rsList_ben.get(0);
	}
	
	if(map_ben!=null){
		product_id = Utility.parseInt(Utility.trimNull(map_ben.get("PRODUCT_ID")),null);	
		cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),null);	
		ben_amount = Utility.parseDecimal(Utility.trimNull(map_ben.get("BEN_AMOUNT")),new BigDecimal(0));
		prov_level =  Utility.trimNull(map_ben.get("PROV_LEVEL"));
		jk_type  =  Utility.trimNull(map_ben.get("JK_TYPE"));
		ben_date = Utility.parseInt(Utility.trimNull(map_ben.get("BEN_DATE")),null);
		ben_end_date = Utility.parseInt(Utility.trimNull(map_ben.get("BEN_END_DATE")),null);
		bank_id = Utility.trimNull(map_ben.get("BANK_ID"));
		bank_sub_name = Utility.trimNull(map_ben.get("BANK_SUB_NAME"));
		bank_acct = Utility.trimNull(map_ben.get("BANK_ACCT"));
		bank_acct_type = Utility.trimNull(map_ben.get("BANK_ACCT_TYPE"));
		cust_acct_name = Utility.trimNull(map_ben.get("CUST_ACCT_NAME"));
		bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("BONUS_FLAG")),null);	
	}
}

//设置客户信息
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;
	
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listProcAll(cust_vo);
	
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}
	
	if(map_cust!=null){
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
	}
}
	

if (request.getMethod().equals("POST")&& action_flag==1){
	BenifitorVO s_vo_ben = new BenifitorVO();

	Integer s_customer_cust_id = Utility.parseInt(request.getParameter("customer_cust_id"), new Integer(0));
	String s_jk_type = Utility.trimNull(request.getParameter("jk_type"));
	BigDecimal s_ben_amount = Utility.parseDecimal(request.getParameter("ben_amount1"),new BigDecimal(0));
	Integer s_bonus_flag = Utility.parseInt(Utility.trimNull(request.getParameter("bonus_flag")),null);
	String s_prov_level = Utility.trimNull(request.getParameter("prov_level"));
	String s_bank_id = Utility.trimNull(request.getParameter("bank_id"));
	String s_bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name"));
	String s_bank_acct = Utility.trimNull(request.getParameter("bank_acct"));
	Integer s_ben_date = Utility.parseInt(Utility.trimNull(request.getParameter("ben_date")),ben_date);
	Integer s_ben_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("ben_end_date")),ben_end_date);
	Integer s_valid_period = Utility.parseInt(request.getParameter("valid_period"),new Integer(0));
	String s_bank_acct_type = Utility.trimNull(request.getParameter("bank_acct_type"));
	String s_cust_acct_name = Utility.trimNull(request.getParameter("cust_acct_name"));
	String s_contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
	Integer s_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));
	
	//同步客户信息
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(mobile);
	c_vo.setE_mail(e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	
	customer_inst.cope_customers(c_vo);	
	
	s_vo_ben.setInput_man(input_operatorCode);
	s_vo_ben.setBook_code(input_bookCode);
	s_vo_ben.setCust_id(s_customer_cust_id);
	s_vo_ben.setJk_type(s_jk_type);
	s_vo_ben.setBen_amount(s_ben_amount);
	s_vo_ben.setBonus_flag(s_bonus_flag);
	s_vo_ben.setProv_level(s_prov_level);
	s_vo_ben.setBank_id(s_bank_id);
	s_vo_ben.setBank_sub_name(s_bank_sub_name);
	s_vo_ben.setBank_acct(s_bank_acct);	
	s_vo_ben.setBen_date(s_ben_date);
	s_vo_ben.setBen_end_date(s_ben_end_date);
	s_vo_ben.setValid_period(s_valid_period);
	s_vo_ben.setBank_acct_type(s_bank_acct_type);
    s_vo_ben.setAcct_user_name(s_cust_acct_name);	
    s_vo_ben.setContract_bh(s_contract_bh);
    s_vo_ben.setProduct_id(s_product_id);
	
	if (serial_no.intValue()==0){
		benifitor.append(s_vo_ben);
	}
	else{
		s_vo_ben.setSerial_no(serial_no);
		benifitor.save(s_vo_ben);
	}
		
	bSuccess = true;
}
else{
	//设置产品信息
	if(product_id!=null&&product_id.intValue()>0){
		List rsList_product = null;
		Map map_product = null;
		
		p_vo.setProduct_id(product_id);
		rsList_product = product.load(p_vo);
		
		if(rsList_product.size()>0){
			map_product = (Map)rsList_product.get(0);
		}
		
		if(map_product!=null){
			open_flag = Utility.parseInt(Utility.trimNull(map_product.get("CARD_ID")),new Integer(0));
			hiddenFlag = Utility.parseInt(Utility.trimNull(map_product.get("VALID_PERIOD")),new Integer(0));
			period_unit_value = Utility.parseInt(Utility.trimNull(map_product.get("PERIOD_UNIT")),new Integer(0));
			period_unit = Argument.getProductUnitName(period_unit_value);
			
			if(serial_no.intValue()==0){
				ben_date = Utility.parseInt(Utility.trimNull(map_product.get("START_DATE")),new Integer(0));
				ben_end_date = Utility.parseInt(Utility.trimNull(map_product.get("END_DATE")),new Integer(0));
			}
		}
	}
}

%>


<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.benefiterAdd",clientLocale)%> </TITLE>
<!--受益人新增-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action_flag.value = 1;
		document.theform.action="benifiter_add.jsp";
		document.theform.submit();
	}
}

/*验证*/
function validateForm(form){
	if(form.serial_no.value==""&&form.customer_cust_id.value==""){		
		sl_alert("<%=LocalUtilis.language("message.chooseBenefiter",clientLocale)%> ！");//请选择受益人
		return false;			
	}
	
	if(theform.prov_level.value==""){
		 alert("<%=LocalUtilis.language("message.chooseProvLevel",clientLocale)%> ！");//请选择受益级别
		 return false;
	}
	
	if(!sl_checkDecimal(form.ben_amount, "<%=LocalUtilis.language("class.benAmount",clientLocale)%> ", 13, 3, 1))	return false;//受益金额
	if(!sl_checkChoice(form.jk_type, "<%=LocalUtilis.language("class.moneyType",clientLocale)%> "))return false;//受益付款方式
	if(!sl_checkChoice(form.bank_id, "<%=LocalUtilis.language("class.bankSubName",clientLocale)%> "))return false;//受益付款银行
	if(!sl_check(form.bank_acct, "<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ", 30, 0))return false;//银行帐号
	if(!sl_checkNum(form.valid_period, "<%=LocalUtilis.language("class.benPeriod2",clientLocale)%> ", 10, 1))return false;//受益期限
	if(!sl_checkDate(form.ben_date_picker,"<%=LocalUtilis.language("class.benDate2",clientLocale)%> "))	return false;//受益起始日期
	//if(!sl_checkDate(form.ben_end_date_picker,"<%=LocalUtilis.language("class.benEndDate",clientLocale)%> "))	return false;//受益终止日期
	
	form.ben_amount1.value=sl_parseFloat(form.ben_amount.value);
	syncDatePicker(form.ben_date_picker, form.ben_date);
	syncDatePicker(form.ben_end_date_picker, form.ben_end_date);
	return sl_check_update();
}

/**************************************************************************************************************************/

/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:800px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		showMarketingCustomer(prefix, v);
	}	
	
	return (v!=null);
}
	
/*用于销售管理里*/
function showMarketingCustomer(prefix, v){	
	getElement(prefix, "cust_name").value = v[0];
	getElement(prefix, "cust_type_name").value = v[1];
	getElement(prefix, "card_id").value = v[2];
	
	if(getElement(prefix, "h_tel")!=null)
		getElement(prefix, "h_tel").value = v[3];	
	if(getElement(prefix, "mobile")!=null)
		getElement(prefix, "mobile").value = v[4];		
	if(getElement(prefix, "post_address")!=null)
		getElement(prefix, "post_address").value = v[5];		
	if(getElement(prefix, "post_code")!=null)
		getElement(prefix, "post_code").value = v[6];	
	getElement(prefix, "cust_id").value = v[7];		
	
	if(getElement(prefix, "service_man")!=null)
		getElement(prefix,"service_man").value=v[8];
	if(getElement(prefix, "service_man_value")!=null)
		getElement(prefix,"service_man_value").value=v[9];
	if(getElement(prefix, "o_tel")!=null)
		getElement(prefix, "o_tel").value = v[10];
	if(getElement(prefix, "bp")!=null)
		getElement(prefix, "bp").value = v[11];
		
	if(getElement(prefix, "is_link")!=null){
		getElement(prefix, "is_link").value = v[12];
		
		if(v[12]==1)
			getElement(prefix, "is_link").checked = true;
		else
			getElement(prefix, "is_link").checked = false;	
	}	
	
	getElement(prefix, "cust_type").value = v[13];
	//getElement(prefix, "cust_on").value = v[14];	
	//getElement(prefix, "card_type").value = v[15];
	//getElement(prefix, "legal_man").value = v[16];
	//getElement(prefix, "contact_man").value = v[17];
	//if(getElement(prefix, "e_mail")!=null)
	//	getElement(prefix, "e_mail").value = v[18];	
		
	if(getElement(prefix, "gain_acct")!=null)
		getElement(prefix, "gain_acct").value = v[0];  
		 
	/*prodid=0;		
	for(i=0;i<document.theform.link_man.options.length;i++){
	
		if(document.theform.link_man.options[i].value==v[9]){
		
			document.theform.link_man.options[i].selected=true;
			prodid = document.theform.link_man.options[i].value;
			break;
	    }	
	}
	
	if (prodid==0){
		document.theform.link_man.options[0].selected=true;	
	}*/
}

/**************************************************************************************************************************/

function showAcctNum(value){	
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type=hidden name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="product_id" value="<%=product_id%>"/>
<input type="hidden" name="action_flag" value="<%=action_flag%>"/>
<input type="hidden" name="check_flag" value="<%=check_flag%>"/>
<input type="hidden" name="ben_amount1" value="<%=ben_amount%>"/>

<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="customer_cust_no" value="<%=cust_no%>"/>
<input type="hidden" name="customer_cust_type" value=""/>


<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> </b></font><!--受益人信息-->
	<span id="cust_message" style="display:none;color:red;"><%=LocalUtilis.language("message.noCustomerInfo",clientLocale)%> </span><!--客户信息不存在-->
	<br/>
</div>

<div>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right" height="27"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td height="27"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="contract_bh" value="<%=contract_bh%>"></td>
	</tr>
	<tr>
		<td align="right"><b><%=LocalUtilis.language("message.custInfo",clientLocale)%> </b></td><!--受益人信息-->
		<td><button type="button"   class="xpbutton4" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " 
		    onclick="javascript:getMarketingCustomer('customer','<%=readonly%>');"><%=strButton%></button>
		</td><!--客户信息-->
	<tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.custName2",clientLocale)%> :</td><!--受益人名称-->
		<td colspan="3">
			<input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.custTypeName",clientLocale)%> :</td><!--受益人类别-->
		<td >
			<input readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);">
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td>
			<input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);" maxlength="40" value="<%=card_id%>" size="20">
		</td>
	</tr>
	<tr>
		<td align="right" height="22">*<%=LocalUtilis.language("class.benAmount",clientLocale)%> :</td><!--受益金额-->
		<td height="22" colspan="3">
			<input type="text" <%=show%> name="ben_amount" size="20" value="<%=Utility.trimNull(Format.formatMoney(ben_amount.doubleValue(),2))%>"
				   onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,ben_amount_cn)"/>
			<span id="ben_amount_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right" height="24"><font color=red>*</font><%=LocalUtilis.language("class.provLevel",clientLocale)%> :</td><!--受益优先级别-->
		<td height="24">
			<select <%=show1%> size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getProvlevelOptions(prov_level)%>
			</select>
		</td>
		<td align="right" height="29"><font color=red>*</font><%=LocalUtilis.language("class.moneyType",clientLocale)%> :</td><!--受益付款方式-->
		<td height="29">
			<select <%=show1%> size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getJkTypeOptions(jk_type)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right" height="26"><font color=red>*</font><%=LocalUtilis.language("class.bankSubName",clientLocale)%> :</td><!--受益付款银行-->
		<td height="26">
			<select <%=show1%> size="1" name="bank_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
				<%=Argument.getBankOptions(bank_id)%>
			</select>
		</td>
		<td height="18" align="right"><%=LocalUtilis.language("class.bankSubName3",clientLocale)%> :</td><!--支行-->
		<td height="18">
			<input type="text" <%=show%> onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=bank_sub_name%>">
		</td>
	</tr>
	<tr>
		<td align="right" height="21"><font color=red></font><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</td><!--银行账号-->
		<td height="21">
			<input type="text" <%=show%> onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" name="bank_acct" size="20" value="<%=bank_acct%>">
			<span id="bank_acct_num" class="span"></span>
		</td>
		<td align="right"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.bonusFlag",clientLocale)%> :<%}%></td><!--收益分配方式-->
		<td>
			<%if(open_flag.intValue()==1){%>
				<select size="1" name="bonus_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getBonus_flag(bonus_flag)%>
				</select>
			<%}%>			
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.bankAccountType",clientLocale)%> :</td><!--银行账号类型-->
		<td colspan="3">
			<select size="1" name="bank_acct_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
				<%=Argument.getBankAcctType(bank_acct_type)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color=red>*</font><%=LocalUtilis.language("class.benDate2",clientLocale)%> :</td><!--受益起始日期-->
		<td>
			<input type="text" NAME="ben_date_picker"  <%=show%> value="<%=Utility.trimNull(Format.formatDateLine(ben_date))%>" onkeydown="javascript:nextKeyPress(this)">
			<%if(check_flag.intValue()==1){%>
			<input  TYPE="button"   value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.ben_date_picker,theform.ben_date_picker.value,this);" tabindex="13">
			<input  NAME="ben_date" type="hidden" value="<%=Utility.trimNull(ben_date)%>">
			<%}%>
		</td>
		
		<td align="right"><%=LocalUtilis.language("class.benEndDate",clientLocale)%> :</td><!--受益终止日期-->
		<td>
			<input  name="ben_end_date_picker" readonly class="edline" size="10"   value="<%if(ben_end_date.intValue()>=20990101) out.print(enfo.crm.tools.LocalUtilis.language("message.noFixDeadline",clientLocale)); else out.print(Format.formatDateCn(ben_end_date));%>"><!--无固定期限-->
			<input  type="hidden" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.ben_end_date_picker,theform.ben_end_date_picker.value,this);" tabindex="13">
			<input type="hidden" name="ben_end_date"   value="<%=Utility.trimNull(ben_end_date)%>">
		</td>
	</tr>
	<tr>  
		<td align="right" height="24"><%=LocalUtilis.language("class.tempAcctName",clientLocale)%> :</td><!--受益人银行帐户名称-->
		<td height="24">
			<input <%=show%>  name="cust_acct_name" type="text" onkeydown="javascript:nextKeyPress(this)" size=25  value=<%=cust_acct_name%>>  
		</td>
		<td align="right" height="22"><font color=red>*</font><%=LocalUtilis.language("class.benPeriod2",clientLocale)%> :</td><!--受益期限-->
		<td height="22">
			<INPUT  NAME="valid_period" <%=show%> size="10" <%if(hiddenFlag.intValue()==0) out.print("type='hidden'");%> value="<%=Utility.trimNull(hiddenFlag)%>"><%=Utility.trimNull(period_unit)%>
		</td>
	</tr>
	<tr>

	</tr>
</table>
</div>


<div align="right">
	<br>
	<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--关闭-->
</div>

</form>
<script language=javascript>
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_cust_no = document.getElementsByName("customer_cust_no")[0].value;serial_no
	var serial_no = document.getElementsByName("serial_no")[0].value;
	
	if(v_cust_no==""&serial_no>0){
		document.getElementById("cust_message").style.display="";
	}
	
	if(v_bSuccess=="true"){		
		sl_update_ok();
		window.returnValue = 1;
		window.close();
	}
}
</script>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>