<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*, enfo.crm.marketing.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String sQuery = request.getParameter("sQuery");
String q_nonproductName = Utility.trimNull(request.getParameter("q_nonproductName"));
String q_investment_direction = Utility.trimNull(request.getParameter("q_investment_direction"));
Integer q_validPriodUnit = Utility.parseInt(Utility.trimNull(request.getParameter("q_validPriodUnit")),new Integer(0));
Integer q_validPriod = Utility.parseInt(Utility.trimNull(request.getParameter("q_validPriod")),new Integer(0));
Integer startDate = Utility.parseInt(Utility.trimNull(request.getParameter("startDate")),new Integer(0));
Integer endDate = Utility.parseInt(Utility.trimNull(request.getParameter("endDate")),new Integer(0));
BigDecimal q_expectMoney = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_expectMoney")), new BigDecimal(0), 2,"1");
BigDecimal q_expectRate1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_expectRate1")), new BigDecimal(0),2,"1");
BigDecimal q_expectRate2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("q_expectRate2")), new BigDecimal(0),2,"1");
String q_investmentManager = Utility.trimNull(request.getParameter("q_investmentManager"));
Integer q_outNonproductId = Utility.parseInt(Utility.trimNull(request.getParameter("q_outNonproductId")),new Integer(0));
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer customer_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
String q_investment_direction_name = Argument.getNonProductValue(q_investment_direction);
String q_description  = Utility.trimNull(request.getParameter("q_description"));
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
CustomerVO cust_vo = new CustomerVO();
CustomerVO c_vo = new CustomerVO();//同步用
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal customer = EJBFactory.getCustomer();//客户
NonProductVO vo = new NonProductVO();
NonProductLocal nonproduct = EJBFactory.getNonProductLocal();

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

//保存产品信息
if(request.getMethod().equals("POST")){
	vo.setNonproduct_name(q_nonproductName);
	vo.setInvestment_direction(q_investment_direction);
	vo.setInvestment_direction_name(q_investment_direction_name);
	vo.setValid_priod_unit(q_validPriodUnit);
	vo.setValid_priod(q_validPriod);
	vo.setStart_date(startDate);
	vo.setEnd_date(endDate);
	vo.setExpect_money(q_expectMoney);
	vo.setExpect_rate1(q_expectRate1);
	vo.setExpect_rate2(q_expectRate2);
	vo.setInvestment_manager(q_investmentManager);
	vo.setPartner_cust_id(customer_id);
	vo.setOut_nonproduct_id(q_outNonproductId);
	vo.setDescription(q_description);
	nonproduct.append(vo,input_operatorCode);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.addSubscriptionInfo",clientLocale)%>（<%=LocalUtilis.language("message.withoutBooked",clientLocale)%> ）</TITLE><!--认购信息新增--><!--未预约-->
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
	syncDatePicker(theform.start_date_picker, theform.startDate);
	syncDatePicker(theform.end_date_picker, theform.endDate);
	var temp="";
	var temp = temp + "&cust_id="+ document.theform.customer_cust_id.value;	
	var temp = temp + "&q_custName="+ document.theform.customer_cust_name.value;
	var temp = temp + "&q_custName="+ document.theform.customer_cust_name.value;
	var temp = temp + '&q_nonproductName=' + document.theform.q_nonproductName.value;
	var temp = temp + '&q_investment_direction=' + document.theform.q_investment_direction.value;
	var temp = temp + '&q_validPriod=' + document.theform.q_validPriod.value;
	var temp = temp + '&startDate=' + document.theform.startDate.value;
	var temp = temp + '&endDate=' + document.theform.endDate.value;
	var temp = temp + '&q_expectMoney=' + document.theform.q_expectMoney.value;
	var temp = temp + '&q_expectRate1=' + document.theform.q_expectRate1.value;
	var temp = temp + '&q_expectRate2=' + document.theform.q_expectRate2.value;
	var temp = temp + '&q_investmentManager=' + document.theform.q_investmentManager.value;
	var temp = temp + '&q_description=' + document.theform.q_description.value;
	var temp = temp + "&sQuery=<%=sQuery%>";
	return temp;
}

/*设置产品及客户信息*/
function selectProductAndCustomer(){	
	var url = "noproduct_new.jsp?page=<%=sPage%>" + getLastOptions();
	window.location.href = url;
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
	
/*保存*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}
}
	
/*返回*/
function CancelAction(){
	location = 'noproduct_list.jsp'
}

/*下面是新增认购信息时用到的函数*/
function validateForm(form){
	if(form.customer_cust_id.value == 0){
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ！");//请选择客户
			return false;
	}	
	if(!sl_check(theform.q_nonproductName, "<%=LocalUtilis.language("class.productName",clientLocale)%> ", 60, 1))		return false;//非信托产品名称
	if(!sl_check(theform.start_date_picker, "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//开始时间
	syncDatePicker(theform.start_date_picker, theform.startDate);
	
	if(!sl_check(theform.end_date_picker, "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//结束时间
	syncDatePicker(theform.end_date_picker, theform.endDate);
	
	if(theform.end_date_picker.value<theform.start_date_picker.value){
		sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//结束日期不能比开始日期早
		return false;
	}
	
	alert(typeof theform.q_expectRate1.value);
	alert(typeof theform.q_expectRate2.value);
	if(form.q_expectRate1.value > form.q_expectRate2.value){
		sl_alert("<%=LocalUtilis.language("message.expectedReturnError",clientLocale)%> ");//预期收益上限不能小于预期收益下限
		return false;
	}
	return sl_check_update();
}

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	
	if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//保存成功
		   	CancelAction();//产品列表
	}
}
/*显示中文*/
function showCnMoney(value){
	temp = value;	
	q_expectMoney_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="noproduct_new.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<!--客户信息-->
<input type="hidden" name="cust_name" value="<%=cust_name%>">
<input type="hidden" name="cust_type" value="<%=cust_type%>">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>">
<input type="hidden" name="card_type" value="<%=card_type%>">
<input type="hidden" name="card_type_value" value="<%=card_type%>">

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<!--产品选择-->
<div>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>   
		<td ><b><%=LocalUtilis.language("menu.prodInfo",clientLocale)%> </b></td><!--产品信息-->
		<td></td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.productName",clientLocale)%> : </td><!--名称-->
		<td align="left" >
		<input type="text" name="q_nonproductName" size="40" value="<%=q_nonproductName %>" onkeydown="javascript:nextKeyPress(this)"></td>

		<td align="right"><%=LocalUtilis.language("class.toward",clientLocale)%> :</td><!--投向-->
		<td align="left" >
		<select size="1" name="q_investment_direction" onkeydown="javascript:nextKeyPress(this);" style="WIDTH: 140px">
			<%=Argument.getNonProductValue("q_investment_direction") %>
		</select>
		</td>
	</tr>
	<tr>
		<td align="right" noWrap><%=LocalUtilis.language("class.period",clientLocale)%> :</td><!--期限--> 
		<td align="left">
			<input type="text" name="q_validPriod" value="<%=q_validPriod %>" onkeydown="javascript:nextKeyPress(this)" />
			<select size="1" name="q_validPriodUnit" onkeydown="javascript:nextKeyPress(this)" style="width: 80px" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getPeriodValidUnitOptions1(q_validPriodUnit)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.startTime",clientLocale)%> ：</td><!--开始时间-->
		<td>
			<INPUT TYPE="text" NAME="start_date_picker" class=selecttext
			<%if(startDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(startDate)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,start_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="startDate" value="<%=startDate%>">
		</td>	

		<td align="right">*<%=LocalUtilis.language("class.endTime",clientLocale)%> ：</td><!--结束时间-->
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" class=selecttext
			<%if(endDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(endDate)%>"<%}%> >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,end_date_picker.value,this);" tabindex="14">
			<INPUT TYPE="hidden" NAME="endDate" value="<%=endDate%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.expectMoney",clientLocale)%> : </td><!--预期产品金额-->
		<td align="left" >
			<input type="text" name="q_expectMoney" size="20" value="<%=q_expectMoney %>" onkeydown="javascript:nextKeyPress(this)"  onkeyup="javascript:showCnMoney(this.value)">
			<span id="q_expectMoney_cn" class="span"></span>
		</td>
		<td align="right"><%=LocalUtilis.language("class.investmentManager",clientLocale)%> : </td><!--投资管理人-->
		<td align="left" ><input type="text" name="q_investmentManager" size="20" value="<%=q_investmentManager %>" onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.exceptRateLower",clientLocale)%> : </td><!--预期收益率下限-->
		<td align="left" ><input type="text" name="q_expectRate1" size="20" 
			value="<%=q_expectRate1 %>" 
			onkeydown="javascript:nextKeyPress(this)">%</td>

		<td align="right"><%=LocalUtilis.language("class.exceptRateLimit",clientLocale)%> : </td><!--预期收益率上限-->
		<td align="left" ><input type="text" name="q_expectRate2" size="25" 
			value="<%=q_expectRate2 %>" 
			onkeydown="javascript:nextKeyPress(this)">%</td>
	</tr>
</table>
<br>
<!--简介-->
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
	<tr>   
		<td valign="top"><b><%=LocalUtilis.language("message.productIntroduction",clientLocale)%>：</b></td><!--产品信息简介-->
		<td colspan="3"><textarea  rows="10" name="q_description" cols="120"></textarea></td>
	</tr>
	
</table>

<br>
<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue;margin-top:5px;">
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
</div>

<div align="right">
	<br>
	<!--保存-->
    <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--返回-->
    <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
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
