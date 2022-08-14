<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//产品编号
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//合同编号
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//合同原受益人的编号
BigDecimal moneytype2 = Utility.parseDecimal(request.getParameter("moneytype2"), null);//转让份额

Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")), new Integer(0)); //受让方客户ID
int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);//输入标记
String bank_id = Utility.trimNull(request.getParameter("bank_id"));//银行名称ID
String from_cust_id = Utility.trimNull(request.getParameter("from_cust_id"));
String exchangetype = Utility.trimNull(request.getParameter("exchangetype"));//转让方式
java.math.BigDecimal exchange_amount =
	Utility.parseDecimal(Utility.trimNull(request.getParameter("contractsum1")), new java.math.BigDecimal(0));//转让金额
String sx_fee = Utility.trimNull(request.getParameter("peoplenum1"));//转让手续费
String check_man = Utility.trimNull(request.getParameter("check_man"));
String Summary = Utility.trimNull(request.getParameter("Summary"));
String bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name"));
String bank_acct_name = Utility.trimNull(request.getParameter("bank_acct_name"));
Integer project_id = Utility.parseInt(request.getParameter("project_id"),new Integer(0));
Integer problem_id = new Integer(0);

String defaultOptions = "<option value=\"\" selected>选项默认值</option>";//选项默认值 下拉不用国际化
//"<option value=\"\" selected>请选择</option>"
String contract_bh_list = defaultOptions;//合同列表
String from_cust_id_list = defaultOptions;//受益人列表
String trans_flagName_list = defaultOptions;//转让方式
int readonly=0;//0表示新增受益权转让
String preCodeOptions = defaultOptions;//银行账号列表
String strButton=enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale);//请选择
String jk_type ="";
boolean is_succes_flag = false;
Integer from_list_id=new Integer(0);

BenifitorLocal benifitor_local = EJBFactory.getBenifitor();//受益人份额Bean
CustomerLocal cust_local = EJBFactory.getCustomer();//客户Bean
BenChangeLocal change_local = EJBFactory.getBenChanage();//受益权转让Bean

Map cust_map = new HashMap();

//选择产品获得相应的合同编号
if (product_id.intValue() != 0) {
	//1、合同编号不为空时即表示选择合同编号，则获得该产品的所有合同编号并选中该合同编号；2、获得原受益人信息
	if (! "".equals(contract_bh)) {
		contract_bh_list = Argument.getContract(new Integer(1), product_id, contract_bh,input_operatorCode);
		//原受益人的编号不为空时即表示选择受益人信息，则获得原受益人可转让份额
		if (serial_no.intValue() != 0){ 
			BenifitorVO benifitor_vo = new BenifitorVO();
			benifitor_vo.setSerial_no(serial_no);
			List benifitor_list = benifitor_local.load(benifitor_vo);
			if (! benifitor_list.isEmpty())	{
				Map benifitor_map = (Map)benifitor_list.get(0);
				moneytype2 = Utility.stringToDouble(Utility.trimNull(benifitor_map.get("BEN_AMOUNT")));
				from_cust_id = Utility.trimNull(benifitor_map.get("CUST_ID"));
				jk_type=Utility.trimNull(benifitor_map.get("JK_TYPE"));
				from_list_id =Utility.parseInt(Utility.trimNull(benifitor_map.get("LIST_ID")), new Integer(0));
			}
		}
		//如果未选择受益人信息，则获得受益人列表
		from_cust_id_list = Argument.getFromCustIdOptions(new Integer(1), product_id, contract_bh, serial_no,input_operatorCode);
	}else
	{
		//合同编号为空时获得该产品的所有合同编号
		contract_bh_list = Argument.getContract(new Integer(1), product_id, "110203",input_operatorCode);
	}
}

//选择受让方信息后，则获得受让方信息
if (customer_cust_id.intValue() != 0) {
	CustomerVO cust_vo = new CustomerVO();
	cust_vo.setCust_id(customer_cust_id);
	cust_vo.setInput_man(input_operatorCode);
	List cust_list = cust_local.listProcAll(cust_vo);
	if (! cust_list.isEmpty()) {
		cust_map = (Map)cust_list.get(0);
		//选择银行名称时获得账号
		preCodeOptions = Argument.getCustBankAcctOptions(Utility.parseInt(Utility.trimNull(cust_map.get("CUST_ID")),new Integer(0))
						,bank_id,Utility.trimNull(cust_map.get("CARD_ID")),"");
	}
}

//银行账号没有时，则该为输入框
if(preCodeOptions.equals("<option value=\"\" selected>请选择</option>"))
////"<option value=\"\" selected>请选择</option>"
	inputflag=2;
	
//保存受益权转让信息
if(request.getMethod().equals("POST")) {
	BenChangeVO change_vo = new BenChangeVO();
	change_vo.setProduct_id(product_id);
	change_vo.setInput_man(input_operatorCode);
	change_vo.setInput_time(Utility.getCurrentTimestamp());
	change_vo.setContract_bh(contract_bh);
	if("".equals(from_cust_id)){
		from_cust_id = "0";
	}
	change_vo.setFrom_cust_id(new Integer(from_cust_id));
	change_vo.setJk_type(jk_type);
	change_vo.setTrans_flag(exchangetype);
	change_vo.setTo_amount(exchange_amount);
	change_vo.setTo_cust_id(customer_cust_id);
	if(user_id.intValue()==5){
		change_vo.setSx_fee(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee1(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee1")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee2(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee2")),new java.math.BigDecimal(0)));
		change_vo.setSx_fee3(Utility.parseDecimal(Utility.trimNull(request.getParameter("sxfee3")),new java.math.BigDecimal(0)));
	}else{
		if (sx_fee != null && sx_fee.length() != 0)
			change_vo.setSx_fee(new java.math.BigDecimal(sx_fee));
	}
	change_vo.setChange_date(new Integer(request.getParameter("change_date")));
	change_vo.setCheck_man(new Integer(Utility.parseInt(check_man, 0)));
	change_vo.setCheck_flag(new Integer(1));
	change_vo.setSummary(Summary);
	change_vo.setBank_id(bank_id);
	change_vo.setBank_sub_name(bank_sub_name);
	change_vo.setBank_acct(request.getParameter(inputflag==1?"bank_acct2":"bank_acct"));
	change_vo.setForm_list_id(from_list_id);
	change_vo.setFx_change_flag(Utility.parseInt(request.getParameter("fx_change_flag"),new Integer(2)));
	change_vo.setSy_change_flag(Utility.parseInt(request.getParameter("sy_change_flag"),new Integer(2)));
	change_vo.setTrans_type(Utility.trimNull(request.getParameter("trans_type")));
	change_vo.setChange_qs_date(new Integer(request.getParameter("change_qs_date")));
	change_vo.setProjectid(project_id);
	change_vo.setBank_acct_name(bank_acct_name);
	try{
		problem_id = change_local.append(change_vo);
	}catch(Exception e){
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">location = 'beneficial_transfer_list.jsp?firstFlag=1&contract_bh="+contract_bh+"&product_id="+product_id+"'"+";</script>");
		return;
	}
	
	
	is_succes_flag = true;
}

benifitor_local.remove();
cust_local.remove();
change_local.remove();
%>
<HTML>
<HEAD>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script>
//成功之后返回到列表页面
<%if(is_succes_flag){%>
	sl_alert("<%=LocalUtilis.language("message.beneficiaryTranSaveOK",clientLocale)%> ！");//受益权转让保存成功
	//操作员拥有事物管理权限时自动调整到事物页面
	<%if(projectAccessPower.intValue() == 1 && problem_id.intValue() != 0 && project_id.intValue() != 0){ %>
		showprojectinfo(<%=problem_id%>,<%=project_id%>,0,0);
	<%}else{ %>		
		location = "beneficial_transfer_list.jsp?firstFlag=1&contract_bh=<%=contract_bh%>&product_id=<%=product_id%>";  
	<%} %>       
<%}%>

//展示事务
function showprojectinfo(problem_id,project_id,flag,cust_id){
	//layout.getRegion('west').hide();
	location.href = '<%=Utility.trimNull(application.getAttribute("INTRUST_ADDRESS"))%>/k2/logintrust.jsp?uid=<%=input_operator.getLogin_user()%>?problem_id='
						+problem_id;
}

//选择产品、合同编号、原受益人信息、银行名称
function selectOthers() {		
	form=document.theform;
	product_id=form.product_id.value;//产品ID
	contract_bh=form.contract_bh.value;//合同ID
	serial_no =  form.serial_no.value;//转让方ID
	customer_cust_id = form.customer_cust_id.value;//受让方ID
	bank_id=form.bank_id.value;//银行名称ID
	moneytype2 = form.moneytype2.value;//可转让份额
	location.search = "?product_id="+product_id+"&contract_bh="+contract_bh
				+ "&serial_no=" + serial_no + "&customer_cust_id=" + customer_cust_id
				+ "&bank_id=" + bank_id + "&moneytype2=" + moneytype2;
}

//选择受让方客户信息
function getTransactionCustomer(prefix,readonly) {
	var cust_id = getElement(prefix, "cust_id").value;//获得默认客户ID(customer_cust_id)
	var v = showModalDialog('<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix=' + prefix + '&cust_id=' + cust_id + '&readonly='+readonly,'','dialogWidth:700px;dialogHeight:638px;status:0;help:0;');
	if (v) {
		showTransactionCustomer(prefix, v);
		selectOthers();
	}	
	return v != null;
}

//选择银行名称
function selectBank(value) {
	if (value != "") selectOthers();
}

//银行账号选择或输入
function changeInput(obj) {
	if(document.theform.inputflag.value==1)	{
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//选择
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	} else {
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//输入
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//选择转让方式获得转让金额
function exchage() {
	if(document.theform.exchangetype.value == '111501')//全部转让
	{	
		document.theform.contractsum.disabled = true;	
		document.theform.contractsum.value = <%=moneytype2%>;
	}
	else
	{
		document.theform.contractsum.disabled = false;	
	}
	showCnMoney(document.theform.contractsum.value,document.getElementById("contractsum_cn"));
}

//输入银行账号时显示位数
function showAcctNum(value) {		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

//数据验证
function validateForm(theform) { 	
	if(!sl_checkChoice(theform.product_id,"<%=LocalUtilis.language("class.productName",clientLocale)%> "))//产品名称
		return false;
	if(!sl_checkChoice(theform.contract_bh,"<%=LocalUtilis.language("class.contractID",clientLocale)%> "))//合同编号
		return false;
		
	if(theform.customer_cust_id.value==0) {
		sl_alert("<%=LocalUtilis.language("message.chooseAssigneeTip",clientLocale)%> ！");//请选择受让方
		return false;
	}
	if (theform.customer_cust_id.value == theform.from_cust_id.value) {
		sl_alert("<%=LocalUtilis.language("message.assigneeError",clientLocale)%> ！");//受让方不能和受益人相同		
		return false;
	}

	if(!sl_checkChoice(theform.exchangetype,"<%=LocalUtilis.language("class.exchangeType",clientLocale)%> "))//转让方式
		return false;
	if(sl_parseFloat(theform.contractsum.value)>sl_parseFloat(theform.moneytype2.value)){
		sl_alert("<%=LocalUtilis.language("message.TransferableSharesLack",clientLocale)%> !");//可转让份额不足
		return false;
	}
	if(!sl_checkDecimal(theform.contractsum, "<%=LocalUtilis.language("class.contractSum",clientLocale)%> ", 13,3,1))	//转让份额
		return false;		
	theform.contractsum1.value=sl_parseFloat(theform.contractsum.value);

	var userid= <%=user_id.intValue()%>;
	if(userid==2 && theform.sfpj.value != 1){
		sl_alert("请对客户进行评级处理!");//请选择客户进行评级
		return false;
	}
	if(userid==5){
		if(!sl_checkDecimal(theform.sxfee,"<%=LocalUtilis.language("class.sxFee",clientLocale)%> ", 13,3,0))	//原受益人变更费
			return false;
		if(!sl_checkDecimal(theform.sxfee1,"<%=LocalUtilis.language("class.sxFee1",clientLocale)%> ", 13,3,0))//现受益人变更费	
			return false;
		if(!sl_checkDecimal(theform.sxfee2,"<%=LocalUtilis.language("class.sxFee2",clientLocale)%> ", 13,3,0))//原受益人受益权代转让费	
			return false;
		if(!sl_checkDecimal(theform.sxfee3,"<%=LocalUtilis.language("class.sxFee3",clientLocale)%> ", 13,3,0))//现受益人受益权代转让费	
			return false;
	}else{
		if(!sl_checkDecimal(theform.peoplenum,"<%=LocalUtilis.language("class.transFee",clientLocale)%> ", 13,3,0))//转让手续费	
			return false;
		theform.peoplenum1.value=sl_parseFloat(theform.peoplenum.value);
	}
	
	if(!sl_checkDate(document.theform.change_date_picker,"<%=LocalUtilis.language("class.transDate",clientLocale)%> "))	return false;//转让日期
		syncDatePicker(document.theform.change_date_picker, document.theform.change_date);
	if(!sl_checkDate(document.theform.change_qs_date_picker,"<%=LocalUtilis.language("class.changeQSDate",clientLocale)%> "))	return false; //协议签署日期 
	syncDatePicker(document.theform.change_qs_date_picker, document.theform.change_qs_date);	
		
	if(!sl_checkChoice(theform.trans_type,"<%=LocalUtilis.language("class.transType",clientLocale)%> "))//转让类别
		return false;
	if(!sl_check(theform.Summary,"<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ",200,0))//备注
		return false;
	
	if(theform.fx_change_flag.checked)
		theform.fx_change_flag.value=2;
		
	if(document.theform.fx_change_flag.checked)
		document.theform.fx_change_flag.value="1";		
	if(!sl_checkChoice(theform.sy_change_flag,"<%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> "))//转让收益
			return false;
	return sl_check_update();
} 

/*搜索产品*/
function searchProduct(value){
	if (event.keyCode == 13) searchProduct2(value);
}

/*搜索产品2*/
function searchProduct2(value){
	var prodid=0;
	if( value != ""){
        var j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.indexOf(value) >= 0)	{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.product_id.options[0].selected=true;
		}else{
			selectOthers();
		}
	}
	
}

/*搜索合同*/
function searchContract(value){
	if (event.keyCode == 13) searchContract2(value);
}

/*搜索合同*/
function searchContract2(value){
	var contract_bh = "";
	if( value != ""){
        var j = value.length;

		for(i=0;i<document.theform.contract_bh.options.length;i++){
			if(document.theform.contract_bh.options[i].text.indexOf(value) >= 0)
			{
				document.theform.contract_bh.options[i].selected=true;
				contract_bh = document.theform.contract_bh.options[i].value;
				break;
			}
		}
		if (contract_bh == ""){
			sl_alert('<%=LocalUtilis.language("message.contractNumNotExist",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.contract_bh.options[0].selected=true;
		}else{
			selectOthers();
		}
	}
}

//评级
function newInfo(cust_id){
	if(cust_id <= 0)
		sl_alert('请选择客户之后再进行评级');
	else{
		if (showModalDialog('/marketing/cust_grade_new.jsp?cust_id='+cust_id, '', 'dialogWidth:700px;dialogHeight:600px;status:0;help:0')){
			sl_alert("<%=LocalUtilis.language("message.rating",clientLocale)%><%=LocalUtilis.language("message.success",clientLocale)%> ！");
			document.theform.sfpj.value = 1;
		}
	}
}

var acct_arr = [];

function autoSetAcctInfo(sel) {
	DWREngine.setAsync(false); // sync
	utilityService.getBankAcctOption(theform.customer_cust_id.value,'','','',{callback:function(data){
		var json = eval(data);
		acct_arr = json;
	}});
	DWREngine.setAsync(true); // async
	if (sel.value=="") {
		document.theform.bank_sub_name.value = "";		
	} else {
		var i;
		for (i=0; i<acct_arr.length; i++) 
			if (acct_arr[i].BANK_ACCT==sel.value) {				
				document.theform.bank_sub_name.value = acct_arr[i].SUB_BANK_NAME;
				var j;
				for (j=0; j<document.theform.bank_id.options.length; j++) 
					if (document.theform.bank_id.options[j].value == acct_arr[i].BANK_ID) {
						document.theform.bank_id.options.selectedIndex = j;
						break;
					}
				if (j==document.theform.bank_id.options.length) 
					document.theform.bank_id.options.selectedIndex = 0;

				break;
			}		
		if (i==acct_arr.length) {
			document.theform.bank_sub_name.value = "";
		}		
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" action="beneficial_transfer_add.jsp" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="customer_cust_id" value="<%=customer_cust_id%>">
<input type="hidden" name="inputflag" value="<%=inputflag%>">
<input type="hidden" name="from_cust_id" value="<%=from_cust_id%>">
<input type="hidden" name="contractsum1" value="0"> 
<input type="hidden" name="peoplenum1" value="0">
<input type="hidden" name="sfpj" value="0">
<table border="0" width="100%" cellpadding="0" cellspacing="0">
<tr>
	<td>
		<table border="0" width="100%" cellpadding="4" cellspacing="0">
			<tr>
				<td class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>
			</tr>
		</table>
		<br/>
		<table border="0" width="100%" cellspacing="1" cellpadding="4" class="product-list">
			<tr>
				<td align="right">产品搜索 :</td>
				<td align="left" width="180px;">
					<input type="text" name="product_code" value=""  size="25" onKeyDown="javascript:searchProduct(this.value);" size="10">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProduct2(document.theform.product_code.value);"></button>
				</td>
				<td align="right">合同搜索 :</td>
				<td align="left" width="180px;">
					<input type="text" name="contract_sub_bh" value=""  size="25" onKeyDown="javascript:searchContract(this.value);" size="10">&nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchContract2(document.theform.contract_sub_bh.value);"></button>
				</td>
			</tr>
			<!--start 转让方信息-->
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--产品选择-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" class=productname onChange="javascript:selectOthers();">
						<%=Argument.getProductListOptions(new Integer(1), product_id, "", input_operatorCode, 2)%>
					</select>
				</td>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="contract_bh"  onChange="javascript:selectOthers();">
						<%=contract_bh_list%>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4"><b><%=LocalUtilis.language("menu.fromCustomerInfo",clientLocale)%> </b></td><!--原受益人信息-->
			</tr>
			<tr>
				<td align="right" ><%=LocalUtilis.language("message.custInfo",clientLocale)%> :</td><!--受益人信息-->
				<td colspan="3">
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="serial_no" onChange="javascript:selectOthers();">
						<%=from_cust_id_list%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.moneyType2",clientLocale)%> :</td><!--可转让份额-->
				<td>
					<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="moneytype2" size="25" value="<%=Utility.trimNull(Format.formatMoney(moneytype2))%>">
				</td>
			</tr>
			<tr>
				<td ><b><%=LocalUtilis.language("menu.assigneeInfo",clientLocale)%> </b></td><!--受让方信息-->
				<td><button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.customerInfomation",clientLocale)%> " onclick="javascript:getTransactionCustomer('customer','<%=readonly%>');"><%=strButton%></button></td>
				<!--客户信息-->
			<tr>
			<!--end 转让方信息-->

			<!--start 受让方信息-->
		<%if(user_id.intValue() ==2){//2北国投 %>
			<tr>
				<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("menu.customerRating",clientLocale)%> :</td>
				<td align=left>
					<button type="button"  class="xpbutton3"  title='<%=LocalUtilis.language("message.newRating",clientLocale)%>' onclick="javascript:newInfo(document.theform.customer_cust_id.value);"><%=LocalUtilis.language("message.rating",clientLocale)%> </button>
				</td>		
			</tr>
		<%} %>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerCustName",clientLocale)%> :</td><!--受让方名称-->
				<td colspan=3><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerCustTypeName",clientLocale)%> :</td><!--受让方类别-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("CARD_ID"))%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.telephone",clientLocale)%> :</td><!--住宅电话-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("CUST_TEL"))%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=Utility.trimNull(cust_map.get("MOBILE"))%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=Utility.trimNull(cust_map.get("POST_CODE"))%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			<!--end 受让方信息-->
			<!--strat 受益权转让信息-->
			<tr>
				<td align="right"><%=LocalUtilis.language("class.bankName3",clientLocale)%> :</td><!--银行名称-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectBank(this.value);"  name="bank_id" style="width: 120">
						<%=Argument.getBankOptions(bank_id)%>
					</select>
					<input type="text" onkeydown="javascript:nextKeyPress(this)" name="bank_sub_name" size="20" value="<%=Utility.trimNull(bank_sub_name)%>">
				</td>
				<td align="right"><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> :</td><!--银行帐号-->
				<td>
					<input <%if(inputflag==1) out.print("style=display:none");%> type="text" name="bank_acct" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="30">
					<select <%if(inputflag==2) out.print("style=display:none");%> size="1" name="bank_acct2"  onkeydown="javascript:nextKeyPress(this)"  
						onchange="javascript:autoSetAcctInfo(this);" style="WIDTH: 200px">
						<%=preCodeOptions%>
					</select>&nbsp;&nbsp;										
					<%if(inputflag==1){%><button type="button"  class="xpbutton2" accessKey=x name="btnSelect" title="<%=LocalUtilis.language("message.input",clientLocale)%> " 
					  onclick="javascript:changeInput(this);"><%=LocalUtilis.language("message.input",clientLocale)%> (<u>X</u>)</button><!--输入--><%}%>
					<span id="bank_acct_num" class="span"></span>
				</td>												
			</tr>
			<tr>
				<td align="right">银行账户名称 :</td>
				<td>
					<input type="text" name="bank_acct_name" value="<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>" size="25"/>
					<!-- <input type="text" name="bank_acct_name" value="<%=Utility.trimNull(bank_acct_name)%>" size="25"/>-->
				</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.exchangeType",clientLocale)%> :</td><!--转让方式-->
				<td width="35%">
					<select size="1" onchange="javascript:exchage()" onkeydown="javascript:nextKeyPress(this)" name="exchangetype" style="width: 150">
						<%=Argument.getDictParamOptions_intrust(1115,null)%>
					</select>
				</td>
				<td align="right" width="15%"><%=LocalUtilis.language("class.contractSum",clientLocale)%> :</td><!--转让份额-->
				<td width="35%">
					<input onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,contractsum_cn)" name="contractsum" size="25" value=""><span id="contractsum_cn" class="span">&nbsp;<%=LocalUtilis.language("message.10000",clientLocale)%> </span>
				</td><!--万-->
			</tr>
		<%if(user_id.intValue()==5){%>
			<tr>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee",clientLocale)%> :</td><!--原受益人变更费-->
					<td><input onkeydown="javascript:nextKeyPress(this)" name="sxfee" size="25" value="" tabindex="10"></td>
					<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee1",clientLocale)%> :</td><!--现受益人变更费-->
				<td><input onkeydown="javascript:nextKeyPress(this)" name="sxfee1" size="25" value="" tabindex="10"></td>
			</tr>
			<tr>
				<td  align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee2",clientLocale)%> :</td><!--原受益人受益权代转让费-->
				<td ><input onkeydown="javascript:nextKeyPress(this)" name="sxfee2" size="25" value="" tabindex="10"></td>
				<td align="right" onkeydown="javascript:onKeyDown(this)"><%=LocalUtilis.language("class.sxFee3",clientLocale)%> :</td><!--现受益人受益权代转让费-->
				<td ><input onkeydown="javascript:nextKeyPress(this)" name="sxfee3" size="25" value="" tabindex="10"></td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--转让生效日期-->
				<td><INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="change_date"   value=""></td>
				<td align="right"></td><td></td>
			</tr>
		<%}else{%>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.transFee",clientLocale)%> :</td><!--转让手续费-->
				<td><input onkeydown="javascript:nextKeyPress(this)" name="peoplenum" size="25" value=""></td>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--转让生效日期-->
				<td><INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="change_date"   value="">
				</td>
			</tr>
		<%}%>
			<tr>
				<td align="right"  width="15%"><%=LocalUtilis.language("class.transType",clientLocale)%> :</td><!--转让类别-->
					<td width="35%"><select size="1"  onkeydown="javascript:nextKeyPress(this)" name="trans_type" style="width: 150">
						<%=Argument.getTransTypeOptions("")%>
					</select></td>
					<td align="right"><%=LocalUtilis.language("class.changeQSDate",clientLocale)%> :</td><!--协议签署日期-->
					<td><INPUT TYPE="text" NAME="change_qs_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
					<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_qs_date_picker,theform.change_qs_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="change_qs_date"   value="">
				</td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.fxChangeFlag",clientLocale)%> :</td><!--转让发行期利息-->
				<td width="35%"><input class="flatcheckbox" type="checkbox" name="fx_change_flag"  value=""></td>
				<td align="right" width="15%"><%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> :</td><!--转让收益-->
				<td width="35%">
				<select size="1"  onkeydown="javascript:nextKeyPress(this)" name="sy_change_flag" style="width: 150">
					<option  value="2"><%=LocalUtilis.language("message.syChangeFlag3",clientLocale)%> </option><!--收益按时间分拆转让-->
					<option   value="1"><%=LocalUtilis.language("message.syChangeFlag1",clientLocale)%> </option><!--转让未分配收益-->
					</select>
				</td>
			</tr>
			<!-- 操作员拥有事物管理权限时自动添加事物 -->
		<%if(projectAccessPower.intValue() == 1){ %>
			<tr>
				<td align="right" noWrap><%=LocalUtilis.language("menu.projectApprovalCategory",clientLocale)%> </td><!--项目审批类别-->
				<td><select size="1" name="project_id"
					onkeydown="javascript:nextKeyPress(this)" style="width: 200px;">
					<%=PJArgument.getProjects(new Integer(31), new Integer(4), input_bookCode)%>
				</select></td>
			</tr>
		<%} %>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
				<td colspan="3"><input onkeydown="javascript:nextKeyPress(this)" name="Summary" size="80"></td>
			</tr>
			<!--end 受益权转让信息-->	
		</table>
		<table border="0" width="100%" cellspacing="1" cellpadding="4">
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.method='post';document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
							</button><!--保存-->
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:location='beneficial_transfer_list.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
							</button><!--返回-->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>		
	<td>
</tr>
</table>
</form>
</BODY>
</HTML>