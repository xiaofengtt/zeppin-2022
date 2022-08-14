<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*, enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String sQuery = request.getParameter("sQuery");
Integer q_subscribeId = Utility.parseInt(request.getParameter("q_subscribeId"),new Integer(0));
Integer q_nonproductId = Utility.parseInt(request.getParameter("q_nonproductId"),null);
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));
String q_custName = Utility.trimNull(request.getParameter("q_custName"));
Integer q_checkFlag = Utility.parseInt(request.getParameter("q_checkFlag"),new Integer(0));
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer customer_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
String subscribe_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer sign_date = Utility.parseInt(request.getParameter("sign_date"),new Integer(0));
BigDecimal subscribe_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0),2,"1");
Integer pay_date = Utility.parseInt(request.getParameter("pay_date"),new Integer(0));
String bank_id = Utility.trimNull(request.getParameter("bank_id")); //银行账户ID
String bank_name = Utility.trimNull(request.getParameter("bank_name"));
String bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name")); 
String bank_acct = Utility.trimNull(request.getParameter("bank_acct")); 
String acct_name = Utility.trimNull(request.getParameter("acct_name")); 
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(1));
String check_flag_name;
//客户信息
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
//------------------------------------
String re_contract_bh = "";
String nonproductID = "";

Map map_cust = null;
List rsList_cust = null;

//获得对象
SubscribeVO vo = new SubscribeVO();
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//同步用
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal customer = EJBFactory.getCustomer();//客户
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();

boolean bSuccess = false;

//客户信息显示
if(cust_id.intValue()>0){
	
	//客户单个值		
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(cust_vo);
			
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
nonproductID = Utility.trimNull(request.getParameter("q_nonproductName"));
//保存认购信息 
if(request.getMethod().equals("POST")){
	vo.setNonproduct_id(q_nonproductId);
	vo.setCust_id(customer_id);
	vo.setSubscribe_bh(subscribe_bh);
	vo.setSign_date(sign_date);
	vo.setPay_date(pay_date);
	vo.setSubscribe_money(subscribe_money);
	vo.setBank_id(bank_id);
	vo.setBank_sub_name(bank_sub_name);
	vo.setBank_acct(bank_acct);
	subscribe.append(vo,input_operatorCode);
	bSuccess = true;
	
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>（<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ）</TITLE><!--认购信息新增--><!--未预约-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>

<script language="javascript">

/*拼后缀参数*/
function getLastOptions(){	
	if(!sl_checkDate(document.theform.sign_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> "))return false;
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);
	var temp="";
	var temp = temp + "&q_nonproductId="+ document.theform.q_nonproductName.value;	
	var temp = temp + "&cust_id="+ document.theform.customer_cust_id.value;	
	var temp = temp + "&q_custName="+ document.theform.customer_cust_name.value;	
	var temp = temp + "&bank_id="+ document.theform.bank_id.value;	
	var temp = temp + "&sign_date="+ document.theform.sign_date.value;	
	var temp = temp + "&pay_date="+ document.theform.pay_date.value;
	var temp = temp + "&q_nonproductName="+ document.theform.q_nonproductName.value;	
	var temp = temp + '&bank_sub_name=' + document.theform.bank_sub_name.value;
	var temp = temp + '&rg_money=' + document.theform.rg_money.value;
	var temp = temp + '&bank_acct=' + document.theform.bank_acct.value;
	var temp = temp + '&contract_sub_bh=' + document.theform.contract_sub_bh.value;
	//var temp = temp + '&q_checkFlag=' + document.theform.q_checkFlag.value;
	
	var temp = temp + "&sQuery=<%=sQuery%>";
	return temp;
}

/*设置产品及客户信息*/
function selectProductAndCustomer(){	
	var url = "subscribe_new.jsp?page=<%=sPage%>" + getLastOptions();
	window.location.href = url;
}

/*选择银行*/
function selectBank(value){
	if (value!=""){	
		location = 'subscribe_new.jsp?page=<%=sPage%>&bank_id=' + value+ getLastOptions();
	}
}

/**************************************************************************************************************************/

/*打开产品查询页面*/
function openQuery(q_nonproductId,item_id){	
	if(q_nonproductId!=0){
		alert("<%=LocalUtilis.language("message.prodViewError",clientLocale)%> ！");//产品查询页面未移
	}
}

/*显示账号位数*/
function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

/**************************************************************************************************************************/
	
/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	
	var cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:730px;status:0;help:0;');		

	if (v != null){
		/*showMarketingCustomer(prefix, v)*/
		getElement(prefix, "cust_id").value = v[7];
		selectProductAndCustomer();
	}	
	
	return (v != null);
}
	
/**************************************************************************************************************************/
/*验证合同编号*/
function check(){
	$("contractDIV").innerHTML = "";
	if(!sl_check(document.theform.contract_sub_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> "))return false; //合同编号
	
	var book_code=DWRUtil.getValue("book_code");
	var contract_type=new Number(1);
	var product_id=DWRUtil.getValue("q_nonproductId");
	var contract_sub =DWRUtil.getValue("contract_sub_bh");
	if(product_id==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdTip",clientLocale)%> !");//请先选择产品
		return false;
	}
	contract.findIfExistSameBH(book_code,contract_type,product_id,contract_sub,callContractSubBHCallBack);
}

/*结果返回*/
function callContractSubBHCallBack(result){	
	if(result!=""){
		$("contractDIV").innerHTML = result;
	}	
}
/**************************************************************************************************************************/
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}
	
/*返回*/
function CancelAction(){
	location = 'subscribe.jsp';
}

/*下面是新增认购信息时用到的函数*/
function validateForm(form){
	if(!sl_checkChoice(form.q_nonproductName,"<%=LocalUtilis.language("class.productName",clientLocale)%> ")){return false;}//产品名称
	if(form.q_nonproductName.value==0){
		sl_alert("<%=LocalUtilis.language("message.chooseProdName",clientLocale)%> !");//请选择产品名称
		return false;
	}
	//认购合同编号开关
	<%if(Argument.getSyscontrolValue_intrust("C30902")!=1){%>
	    var contract_bh = form.contract_bh;
		if(!sl_check(contract_bh, '<%=LocalUtilis.language("class.constractBH",clientLocale)%> ', 20,0)){return false;}
		if(contract_bh.value!= ""&&contract_bh.value!=null){
		  var s=contract_bh.value;
		  var len=contract_bh.value.length;            
		  if(len<3){
		    sl_alert('<%=LocalUtilis.language("message.inputContractBHTip",clientLocale)%> ');//输入的合同序号至少为3位
		    return false;
		  }
		  else{		     
		     if((s.charCodeAt(len-1)<48 || s.charCodeAt(len-1)>57) || (s.charCodeAt(len-2)<48 || s.charCodeAt(len-2)>57)|| (s.charCodeAt(len-3)<48 || s.charCodeAt(len-3)>57)){
		        sl_alert("<%=LocalUtilis.language("messgae.contractSerialError",clientLocale)%> ！");//合同序号最后三位必须是数字，请您重新填写！
		        return false;
		     }   
		  }
		}
	<%}%>
	if(form.contract_sub_bh.value==""){
		sl_alert("<%=LocalUtilis.language("message.contractNumNotExist",clientLocale)%> ！");//请输入合同编号
		return false;
	}
	if(form.customer_cust_id.value==0){
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
			return false;
	}	
	
    if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')){return false;}//银行名称
    
	if(form.bank_id.value != ""){
			if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//银行帐号
	}
	
	if(!sl_checkDecimal(form.rg_money, '<%=LocalUtilis.language("class.rg_money",clientLocale)%> ', 13, 3, 1)){return false;}//认购金额	
	if(!sl_checkDate(form.sign_date_picker,"<%=LocalUtilis.language("class.qsDate",clientLocale)%> ")){return false;}//签署日期
	if(!sl_checkDate(form.pay_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> ")){return false;}//缴款日期
	
	syncDatePicker(form.sign_date_picker, form.sign_date);
	syncDatePicker(form.pay_date_picker, form.pay_date);

	if(form.pay_date.value < form.sign_date.value)	{
        //缴款日期//不能小于//签署日期
		sl_alert("<%=LocalUtilis.language("class.dzDate'/><fmt:message key='message.cannotLess'/><fmt:message key='class.qsDate",clientLocale)%> ");
		return false;
	}
	
	return sl_check_update();
}

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.subscriptionSucc",clientLocale)%> ");//认购成功
		   	CancelAction();//返回认购列表
	}
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="subscribe_new.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="re_contract_bh" id="re_contract_bh" value="<%=re_contract_bh%>">

<!--客户信息-->
<input type="hidden" name="cust_name" value="<%=cust_name%>">
<input type="hidden" name="cust_type" value="<%=cust_type%>">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>">
<input type="hidden" name="card_type" value="<%=card_type%>">
<input type="hidden" name="card_type_value" value="<%=card_type%>">

<input type="hidden" name="org_bal" value=""><!-- 300W 金额 -->
<input type="hidden" name="contact_num" value=""><!-- 合同上限 -->
<input type="hidden" name="current_num" value=""><!-- 已有合同 -->

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<!--产品选择-->
<div>
<table  border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list">
	<tr>   
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></td><!--产品信息-->
		<td></td>
	</tr>
	<tr>
		<td align="right" width="120px"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
		<td align="LEFT">
			<input readonly type="text" maxlength="16" name="q_nonproductId" value="<%=nonproductID%>" size="20">
		</td>
		<td>&nbsp;&nbsp;</td>
		<td>&nbsp;&nbsp;</td>
	</tr>
	<tr>	
		<td align="right" width="120px"><b>*<%=LocalUtilis.language("class.productNumber",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--产品选择-->
		<td align=left colspan=3>
			<select style="WIDTH:220px" size="1" name="q_nonproductName" onkeydown="javascript:nextKeyPress(this)" class="q_nonproductName" onchange="javascript:selectProductAndCustomer();">
					<!--请选择-->
                    <option><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option>
					<%=Argument.getNonproductName(q_nonproductId) %>
			</select>
		</td>
	</tr>

	<tr>
		<!--合同序号-->
        <td align="right" width="120px" <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>><b><%=LocalUtilis.language("class.constractBH",clientLocale)%> </b>:&nbsp;&nbsp;</td> 
		<td <%if(Argument.getSyscontrolValue_intrust("C30902")==1){out.print("style='display:none'");}%>><input name="contract_bh" size="20" maxlength=20 onkeydown="javascript:nextKeyPress(this)" value=""></td>
		<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%>  :&nbsp;&nbsp;</td><!--合同编号--><!--验证编号-->
		<td ><input name="contract_sub_bh" size="40" maxlength=40 onkeydown="javascript:nextKeyPress(this)" value="<%=subscribe_bh%>">&nbsp;&nbsp;<INPUT type="button" class="xpbutton3" onclick="javascript:check();" value='<%=LocalUtilis.language("messgae.CheckBH",clientLocale)%> '><div id="contractDIV"></div></td>
	</tr>
</table>
<br>
<table  border="0" width="100%" cellspacing="0" cellpadding="3"  class="product-list">
	<!--客户选择-->
	<tr>   
		<td ><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td><!--客户信息-->
		<!--请选择-->
        <td><button class="xpbutton3" accessKey=E name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " onclick="javascript:getMarketingCustomer('customer','0');"><%=LocalUtilis.language("message.select2",clientLocale)%> </button></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
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
		<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="50"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--公司电话-->
		<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
		<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--家庭电话-->
		<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
		<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--电子邮件-->
		<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="20"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
		<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
		<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.isLink3",clientLocale)%> :</td><!--关联方标志-->
		<td><input type="checkbox" disabled name="customer_is_link" value="<%=is_link%>" <%if(is_link!=null){if(is_link.intValue()==1) out.print("checked");}%> class="flatcheckbox"></td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>	
</table>
<br>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" class="product-list">	
	<!--认购合同信息-->
	<tr>
		<td><b><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </b></td>  <!--合同信息-->
	</tr>
	<tr>
		<td align="right"><b>*<%=LocalUtilis.language("class.rg_money",clientLocale)%></b> :&nbsp;&nbsp;</td><!--认购金额-->
		<td><input name="rg_money" size="20" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" onblur="javascript:checkSellInfo();"> 
			<span id="rg_money_cn" class="span"></span>
		</td>
		<td align="right"><b>*<%=LocalUtilis.language("class.qsDate",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--签署日期-->
		<td>
			<INPUT TYPE="text" NAME="sign_date_picker" class=selecttext
			<%if(sign_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(sign_date)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn 
			onclick="javascript:CalendarWebControl.show(theform.sign_date_picker,theform.sign_date_picker.value,this);"
			tabindex="13">
			<INPUT TYPE="hidden" NAME="sign_date" value="<%=sign_date%>">
		</td>
	</tr>
	<tr>
		<td align="right"><b>*<%=LocalUtilis.language("class.bankName5",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--银行-->
		<td >
			<select size="1" name="bank_id"  style="WIDTH:180px">
				<%=Argument.getBankID(Utility.trimNull(bank_id))%>
			</select>
			<input type="text" name="bank_sub_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=bank_sub_name%>">
		</td>		
	</tr>			
	<tr>
		<td align="right"><b>*<%=LocalUtilis.language("class.bandAcct4",clientLocale)%></b> :&nbsp;&nbsp;</td><!--银行帐号-->
		<td>
			<input type="text" name="bank_acct" id="bank_acct"  style="WIDTH: 250px" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" size="1"  value="<%=bank_acct%>">
			<span id="bank_acct_num" class="span"></span> 
		</td>	
		<td align="right"><b>*<%=LocalUtilis.language("class.dzDate",clientLocale)%> </b> :&nbsp;&nbsp;</td><!--缴款日期-->
		<td>
			<INPUT TYPE="text" NAME="pay_date_picker" class=selecttext
			<%if(pay_date.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(pay_date)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pay_date_picker,theform.pay_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="pay_date" value="<%=pay_date%>">
		</td>
	</tr>
</table>
</div>

<div align="right">
	<br>
	<!--保存-->
    <button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<p>
<%
customer.remove();
customer_inst.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
