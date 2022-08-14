<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
//获取页面传递变量
Integer pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("pre_product_id")),new Integer(0));
Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
Integer cc_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cc_cust_id")),new Integer(0));
Integer cust_id = null;
if(cc_cust_id.intValue()>0){
	cust_id = cc_cust_id;
}
else{
	cust_id = customer_cust_id;
}
//声明变量
//--1.预约信息
BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
Integer	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
String	pre_type = Utility.trimNull(request.getParameter("pre_type"));
Integer	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(1));
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String	summary = Utility.trimNull(request.getParameter("summary"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));//客户来源
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_fare = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("channel_fare"))), null);
String per_code = Utility.trimNull(request.getParameter("per_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal huge_money = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("huge_money"))), new BigDecimal(0));

//--2.客户信息
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
String smsContent = "";
//获取对象
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//客户
PreContractCrmVO pre_vo = new PreContractCrmVO();
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();

//客户信息详细
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	//客户单个值		
	c_vo.setCust_id(cust_id);
	//c_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(c_vo);
			
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);
	}

	if(map_cust!=null){	
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		//保密信息
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));
	}		
}

if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();
	//客户信息数据打包
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	c_vo.setCard_type(card_type.toString());
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	//同步客户信息
	customer_inst.cope_customers(c_vo);
	//预约信息打包
	pre_vo.setPre_product_id(pre_product_id);
	pre_vo.setCust_id(cust_id);
	pre_vo.setPre_money(pre_money);
	pre_vo.setLink_man(link_man);
	pre_vo.setValid_days(valid_days);
	pre_vo.setPre_type(pre_type);
	pre_vo.setPre_num(pre_num);
	pre_vo.setSummary(summary);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setPre_date(preDate);
	pre_vo.setExp_reg_date(expRegDate);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setTest_code(Utility.parseInt(per_code,null)); 
	pre_vo.setChannel_type(channel_type);
	pre_vo.setChannel_fare(channel_fare);

	//保存预约信息	 
	preContract.append(pre_vo);

	//北京信托-预约金额大于产品的最大预约金额时发生短信
	if(pre_money.compareTo(huge_money) >= 1){
		//客服主管角色
	 	int role_id = Argument.getSyscontrolValue("AROLE98");
		if(role_id != 0){
			//角色下的员工循环发送短信
			Map map = new HashMap();
			List list = Argument.getOperatorListByRoleId(new Integer(role_id));
			if(list != null && list.size() != 0){
				for(int j=0; j<list.size(); j++){
					map = (Map)list.get(j);
					smsContent = Utility.trimNull(map.get("OP_NAME")) + "您好！客户：" + cust_name + "已经预约成功；产品名称："+product_name+"；产品大额预约金额："+Format.formatMoney(huge_money)+"；预约金额：" + Format.formatMoney(pre_money) + "元；联系电话：" +cust_tel+"。请予以关注！";
					
					if(!"".equals(Utility.trimNull(map.get("MOBILE"))) && Utility.trimNull(map.get("MOBILE")).length() == 11){
						Argument.sendSMSSimple(input_operatorCode+"",Utility.trimNull(map.get("MOBILE")),smsContent,new Integer(1),"待发",new Integer(0),input_operatorCode);
					}
				}
			}
		}
	}
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakAdd",clientLocale)%> </TITLE>
<!--预约新增-->
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language=javascript>
/*保存功能*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="reserve_add.jsp";
		document.theform.method="post";
		document.theform.submit();
	}
}
/*返回*/
function CancelAction(){
	location = 'reserve_list.jsp' ;
}
/*下面是新增客户时用到的函数*/
function validateForm(form){
	if(form.customer_cust_id.value == "" || form.customer_cust_id.value == 0)	{
		sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
		return false;
	}
	if(!sl_checkChoice(form.pre_product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> "))return false;//产品
	if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1))return false;//预约金额
	if(!sl_checkDecimal(form.product_sy_money, "", 13, 3, 0))return false;
	if(form.pre_money.value - form.product_sy_money.value > 0){
		sl_alert("预约金额不能大于可预约规模!");
		return false;
	}
	if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//预约份数
	if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//预约方式
	if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))	return false;//客户来源
	if(!sl_checkDecimal(document.theform.channel_fare, "渠道费用", 13, 3, 0))return false;
	if(document.theform.channel_fare.value != "")
		if(!sl_checkChoice(form.channel_type, "渠道类别"))	return false;
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//联系人
	if(!sl_checkDate(document.theform.pre_date_picker,"<%=LocalUtilis.language("class.preDate",clientLocale)%> ")){//预约日期
		return false;
	}
	syncDatePicker(form.pre_date_picker, form.pre_date);
	
	if(document.theform.exp_reg_date_picker.value!=""){
		if(!sl_checkDate(document.theform.exp_reg_date_picker,"预计认购日期")){//预计认购日期
			return false;
		}
		syncDatePicker(form.exp_reg_date_picker, form.exp_reg_date);
	}
	if(form.exp_reg_date.value > form.pre_end_date.value){
		sl_alert("预计认购日期不能晚于预约截止日期!");
		return false;
	}
	if(form.pre_micro_num.value == 50 && form.cust_type.value ==1 && form.pre_money.value < 3000000 ){
		sl_alert("该产品小额预约份数超过50份!");
		return false;
	}

	//北京信托预约配额控制
	var pre_salemoney = document.getElementById("pre_salemoney").value;
	var quotamoney = document.getElementById("quotamoney").value;
	var pre_qualifiednum = document.getElementById("pre_qualifiednum").value;
	var quota_qualified_num = document.getElementById("quota_qualified_num").value;
	var pre_money = document.getElementById("pre_money").value;
	var current_money = document.getElementById("current_money").value;
	var current_num = document.getElementById("current_num").value;

	//1.预约金额+已预约金额<=团队金额配额
	if(pre_money > current_money){
		sl_alert("预约金额不能大于当前可预约金额！");
		return null;
	}
	//1.预约份数+已预约小额份数<=合同投资人数量配额
	if((parseInt("1") + parseInt("pre_qualifiednum")) > parseInt("quota_qualified_num")){
		sl_alert("预约份数不能大于当前可预约份数！");
		return null;
	}
	
	//if(form.channel_type.value.substring(0,6) != "550003")
		//document.theform.channel_fare.value = "";
	var choose = document.getElementById("pre_product_id");
	document.getElementById("product_name").value = choose.options[choose.selectedIndex].text;
	return sl_check_update();
}
/*刷新页面*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.pre_date_picker, document.theform.pre_date);
	document.theform.action="reserve_add.jsp";
	document.theform.method="get";
	document.theform.submit();
}
/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		//showMarketingCustomer(prefix, v);
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}
	return (v != null);
}
/*显示中文*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pre_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//万
	else
		pre_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney1(value,name)
{
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese(value) + ")";
}

/*页面初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){		
		sl_alert("<%=LocalUtilis.language("message.bespeakInfoSaveOK",clientLocale)%> ！");//预约信息保存成功
		location = "reserve_list.jsp";
	}else{
		if(document.theform.pre_product_id.value > 0){
			showPreinfo(document.theform.pre_product_id.value);
		}
	}
}
/*获取已预约数目 已预约金额*/
function showPreinfo(pre_product_id){
	//产品信息
	utilityService.getPreInfoByPreId(pre_product_id,0,{callback: function(data){
		document.theform.product_total_money.value = data[0];	
		document.theform.pre_micro_num.value = data[1];
		document.theform.pre_total_num.value = data[1]+" / "+data[2];
		document.theform.pre_total_money.value = data[2];
		document.theform.pre_end_date.value = data[3];	
		document.theform.pre_total_money.value = data[4];
		document.theform.product_sy_money.value = data[5];
		document.theform.huge_money.value = data[6];
	}});
	//配额信息
	getTempMountByProductIdAndServerMan();
}

/*根据产品ID和预约登记人获取改团队的配额信息*/
function getTempMountByProductIdAndServerMan(){
	var pre_product_id = document.theform.pre_product_id.value;
	var link_man = document.theform.link_man.value;
	if(pre_product_id != 0 && link_man != ""){
		utilityService.getTempMountByProductIdAndServerMan(pre_product_id,link_man,{callback: function(data){
			var num1 = data.PRE_SALEMONEY_STR;
			if(num1 == "")
				num1 = "0.00";
			var num2 = data.QUOTAMONEY_STR;
			if(num2 == "")
				num2 = "0.00";
			document.getElementById("input_info1").value = num1+"/"+num2;
			document.getElementById("input_info2").value = data.PRE_QUALIFIEDNUM+"/"+data.QUOTA_QUALIFIED_NUM;
			document.getElementById("input_info3").value = data.CURRENT_MONEY_STR;
			document.getElementById("input_info4").value = data.CURRENT_NUM;
		
			document.getElementById("pre_salemoney").value = data.PRE_SALEMONEY;
			document.getElementById("quotamoney").value = data.QUOTAMONEY;
			document.getElementById("pre_qualifiednum").value = data.PRE_QUALIFIEDNUM;
			document.getElementById("quota_qualified_num").value = data.QUOTA_QUALIFIED_NUM;
			document.getElementById("current_money").value = data.CURRENT_MONEY;
			document.getElementById("current_num").value = data.CURRENT_NUM;
		}});
	}
}

//设置渠道类别
function setChannelType(obj){
	var channel_type = obj.value;
	if(channel_type != ""){
		if(channel_type.substring(0,6) == "550003"){
			document.getElementById("channel_td1").style.display = "";
			document.getElementById("channel_td2").style.display = "";
		}else{
			document.getElementById("channel_td1").style.display = "none";
			document.getElementById("channel_td2").style.display = "none";
		}
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" onsubmit="javascript: return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--新增成功标志-->
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="pre_micro_num" value="">

<input type="hidden" name="pre_salemoney" id="pre_salemoney" value="">
<input type="hidden" name="quotamoney" id="quotamoney" value="">
<input type="hidden" name="pre_qualifiednum" id="pre_qualifiednum" value="">
<input type="hidden" name="quota_qualified_num" id="quota_qualified_num" value="">
<input type="hidden" name="current_money" id="current_money" value="">
<input type="hidden" name="current_num" id="current_num" value="">
<input type="hidden" name="huge_money" id="huge_money" value="">
<input type="hidden" name="product_name" id="product_name" value="">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--销售管理>>预约-->
</div>
<br/>
<!--客户选择-->
<div vAlign=top align=left>
<table cellSpacing=0 cellPadding=3 width="100%" border=0 class="product-list">
			<tr>
				<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--客户信息-->			
				<!--button class="xpbutton3" accessKey=e name="btnEdit" title="客户信息" onclick="javascript:getTransactionCustomer2('customer','<!%=readonly%>','<!%=Argument.getSyscontrolValue("CUSTINFO")%>');"><!%=strButton%></button-->
				<td align="left">
					<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.select2",clientLocale)%> 
					</button><!--客户信息--><!--请选择-->
				</td>
			<tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
				<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
				</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--手机1-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机2-->
				<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=bp%>" size="20"></td>
			
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			
			<tr>
				<td colspan="4"><hr size="1"></td>
			</tr> 
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td>
				<select size="1" id="pre_product_id" name="pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onChange="javascript:showPreinfo(this.value);">
					<%=Argument.getPreProductListOptions(pre_product_id,"","110202",input_operatorCode)%>
				</select>
			</td>
 			<td align="right">产品总规模 :</td>
			<td><input readonly class="edline" name="product_total_money" size="27" value=""></td>
		</tr>
		<tr>
			<td align="right">小额份数/累计份数 :</td>
			<td><input readonly class="edline" name="pre_total_num"  size="27" value=""></td>
			<td align="right">截止日期 :</td>
			<td><input readonly class="edline" name="pre_end_date"  size="27" value=""></td>
		</tr>	
		<tr>
			<td align="right">已预约规模 :</td>
			<td><input readonly class="edline" name="pre_total_money" size="27" value=""/></td>
			<td align="right">可预约规模 </td>
			<td><input readonly class="edline" name="product_sy_money" size="27" value=""/></td>
		</tr>
		<tr>
			<td colspan="4"><hr size="1"></td>
		</tr> 
		<tr>
			<td align="right">已预约金额/团队金额配额:</td>
			<td><input readonly class="edline" name="input_info1" id="input_info1" size="27" value=""/></td>
			<td align="right">已预约小额份数/合同投资人数量配额:</td>
			<td><input readonly class="edline" name="input_info2" id="input_info2" size="27" value=""/></td>
		</tr>
		<tr>
			<td align="right">当前可预约金额:</td>
			<td><input readonly class="edline" name="input_info3" id="input_info3" size="27" value=""/></td>
			<td align="right">当前可预约份数:</td>
			<td><input readonly class="edline" name="input_info4" id="input_info4" size="27" value=""/></td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--预约金额-->
			<td><input name="pre_money" id="pre_money" size="27" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value)"><span id="pre_money_cn" class="span">&nbsp;</span></td>
<!--万-->
			<td align="right">*<%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--预约份数-->
			<td><input readonly class="edline"  name="pre_num" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=pre_num%>"/></td>
			<!-- <td>
				<select name="pre_num" >
					<%=Argument.getPreNumOptions(pre_num)%>
				</select>
			 -->
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--预约方式-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(pre_type)%>
				</select>
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--客户来源-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 160px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--预约日期-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" class=selecttext size="23"
			<%if(preDate==null||preDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date_picker,theform.pre_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
			<td align="right">预计认购日期:</td><!--预计认购日期-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" class=selecttext size="23"
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_reg_date_picker,theform.exp_reg_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			
		</tr>
		<tr>
			<td align="right">渠道类别:</td>
			<td>
				<select size="1" name="channel_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getDictParamOptions(5500, channel_type)%>
				</select>
			</td>
			<td align="right" id="channel_td1">渠道费用:</td>
			<td id="channel_td2">
				<input type="text" name="channel_fare" value="<%=Format.formatMoney(channel_fare)%>" size="20" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney1(this.value,channel_fare_cn)"><span id="channel_fare_cn" class="span">&nbsp;</span>
			</td>
		</tr>
		<tr>
			
			<td align="right">*预约登记人 :</td><!--预约登记人-->
			<td ><select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px" onclick="javascript:getTempMountByProductIdAndServerMan();">
				<%=Argument.getManager_Value(link_man)%>
			</select></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--返回-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
preContract.remove();
customer_inst.remove();
customer.remove();
%>