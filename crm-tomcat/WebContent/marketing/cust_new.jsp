<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String op = Utility.trimNull(request.getParameter("op"));
Integer cust_id = Utility.parseInt(request.getParameter("id"), new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_type")), new Integer(1));

//声明单一客户信息变量
String cust_type_name ="";
String cust_source=  Utility.trimNull(request.getParameter("customer_cust_source"), "111005");
String cust_source_name = "";
String vip_card_id="";
String hgtzr_bh="";
Integer vip_date= new Integer(0);
Integer service_man= input_operatorCode;//new Integer(0);客户经理默认改成当前操作员
String card_type_name="";
Integer card_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_card_type")), new Integer(110801)); 
String card_id= Utility.trimNull(request.getParameter("customer_card_id"));
String legal_man="";
String contact_man="";
Integer birthday= new Integer(0);
Integer age= new Integer(0);
Integer sex= new Integer(0);
String o_tel = Utility.trimNull(request.getParameter("customer_o_tel"));
String h_tel = Utility.trimNull(request.getParameter("customer_h_tel"));
String mobile = Utility.trimNull(request.getParameter("customer_mobile"));
String bp = Utility.trimNull(request.getParameter("customer_bp"));
String fax = Utility.trimNull(request.getParameter("customer_fax"));
String e_mail = Utility.trimNull(request.getParameter("customer_e_mail"));
String post_code=Utility.trimNull(request.getParameter("customer_post_code"));
String post_address=Utility.trimNull(request.getParameter("customer_post_address"));
String post_code2= Utility.trimNull(request.getParameter("customer_post_code2"));
String post_address2= Utility.trimNull(request.getParameter("customer_post_address2"));
String touch_type= Utility.trimNull(request.getParameter("customer_touch_type"), "110905");
String touch_type_name="";
Integer is_link = new Integer(0);
String summary="";

String sex_name="";
Integer print_deploy_bill = new Integer(0);
Integer print_post_info = new Integer(0);
Integer is_deal = new Integer(1); //该客户能否进行交易，1.可交易 事实客户；2 不可交易，潜在客户；由用户设定
String country = "9997CHN";//国别 默认中国
String money_source = "";//资金来源
String money_source_name = "";
String card_valid_date = "";
String recommended = "";

String voc_type="";
String voc_type_name="";
String jg_type = "";
String jg_wtrlx2 = "";

//获取对象
boolean bSuccess = false;
CustomerLocal customer = EJBFactory.getCustomer();
List rsList = new ArrayList();
if ("GET".equals(request.getMethod())) {
  	if ("check".equals(op) || "detail".equals(op)) {
		rsList = customer.listSameNameCustomers(q_cust_name);	
	}
} else {
	CustomerVO s_vo = new CustomerVO();

	Integer r_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_type")), new Integer(0));
	//String r_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"));
	Integer r_cust_voc_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_zyhy_type")), new Integer(0));
	String r_cust_no = Utility.trimNull(request.getParameter("customer_cust_no"));
	Integer r_cust_source = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_source")), new Integer(0));
	String r_vip_card_id = Utility.trimNull(request.getParameter("customer_vip_card_id"));
	String r_hgtzr_bh = Utility.trimNull(request.getParameter("customer_hgtzr_bh"));
	Integer r_vip_date = Utility.parseInt(Utility.trimNull(request.getParameter("customer_vip_date")), null);
	Integer r_service_man = Utility.parseInt(Utility.trimNull(request.getParameter("customer_service_man")), null);
	Integer r_card_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_card_type")), null);
	String r_card_id = Utility.trimNull(request.getParameter("customer_card_id"));
	Integer r_card_valid_date = Utility.parseInt(Utility.trimNull(request.getParameter("customer_card_valid_date")), null);
	String r_legal_man = Utility.trimNull(request.getParameter("customer_legal_man"));
	String r_contact_man = Utility.trimNull(request.getParameter("customer_contact_man"));
	Integer r_birthday = Utility.parseInt(Utility.trimNull(request.getParameter("customer_birthday")), null);
	Integer r_age= Utility.parseInt(Utility.trimNull(request.getParameter("customer_age")), null);
	Integer r_sex= Utility.parseInt(Utility.trimNull(request.getParameter("customer_sex")), null);
	String r_h_tel = Utility.trimNull(request.getParameter("customer_h_tel"));
	String r_o_tel = Utility.trimNull(request.getParameter("customer_o_tel"));
	String r_mobile = Utility.trimNull(request.getParameter("customer_mobile"));
	String r_bp = Utility.trimNull(request.getParameter("customer_bp"));
	String r_fax = Utility.trimNull(request.getParameter("customer_fax"));
	String r_e_mail = Utility.trimNull(request.getParameter("customer_e_mail"));
	String r_post_code= Utility.trimNull(request.getParameter("customer_post_code"));
	String r_post_address= Utility.trimNull(request.getParameter("customer_post_address"));
	String r_post_code2= Utility.trimNull(request.getParameter("customer_post_code2"));
	String r_post_address2= Utility.trimNull(request.getParameter("customer_post_address2"));
	Integer r_touch_type = Utility.parseInt(Utility.trimNull(request.getParameter("customer_touch_type")),  new Integer(0));
	Integer r_is_link = Utility.parseInt(Utility.trimNull(request.getParameter("customer_is_link")),  new Integer(0));
	String r_summary = Utility.trimNull(request.getParameter("customer_summary"));
	Integer r_print_deploy_bill = Utility.parseInt(Utility.trimNull(request.getParameter("customer_print_deploy_bill")),  new Integer(0));
	Integer r_print_post_info = Utility.parseInt(Utility.trimNull(request.getParameter("customer_print_post_info")),  new Integer(0));
	Integer r_is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0)); //该客户能否进行交易，1.可交易 事实客户；2 不可交易，潜在客户；由用户设定
	String r_country = Utility.trimNull(request.getParameter("country"));
	String r_money_source = Utility.trimNull(request.getParameter("money_source"));
	String r_money_source_name = Utility.trimNull(request.getParameter("money_source_name"));
	String r_recommended = Utility.trimNull(request.getParameter("recommended"));
	jg_type = Utility.trimNull(request.getParameter("jg_type"));
	jg_wtrlx2 = Utility.trimNull(request.getParameter("jg_wtrlx2"));
	s_vo.setCust_type(r_cust_type);
	s_vo.setCust_name(q_cust_name);
	s_vo.setVoc_type(Utility.trimNull(r_cust_voc_type));
	s_vo.setCust_no(r_cust_no);
	s_vo.setCust_source(Utility.trimNull(r_cust_source));
	s_vo.setVip_card_id(r_vip_card_id);
	s_vo.setHgtzr_bh(r_hgtzr_bh);
	s_vo.setVip_date(r_vip_date);
	s_vo.setService_man(r_service_man);
	s_vo.setCard_type(Utility.trimNull(r_card_type));
	s_vo.setCard_id(r_card_id);
	s_vo.setCard_valid_date(r_card_valid_date);
	s_vo.setLegal_man(r_legal_man);
	s_vo.setContact_man(r_contact_man);
	s_vo.setBirthday(r_birthday);
	s_vo.setAge(r_age);
	s_vo.setSex(r_sex);
	s_vo.setH_tel(r_h_tel);
	s_vo.setO_tel(r_o_tel);
	s_vo.setMobile(r_mobile);
	s_vo.setBp(r_bp);
	s_vo.setFax(r_fax);
	s_vo.setE_mail(r_e_mail);
	s_vo.setPost_code(r_post_code);
	s_vo.setPost_address(r_post_address);
	s_vo.setPost_code2(r_post_code2);
	s_vo.setPost_address2(r_post_address2);
	s_vo.setTouch_type(Utility.trimNull(r_touch_type));
	s_vo.setIs_link(r_is_link);
	s_vo.setSummary(r_summary);
	s_vo.setPrint_deploy_bill(r_print_deploy_bill);
	s_vo.setPrint_post_info(r_print_post_info);
	s_vo.setInput_man(input_operatorCode);
	s_vo.setCountry(r_country);
	s_vo.setMoney_source(r_money_source);
	s_vo.setMoney_source_name(r_money_source_name);
	s_vo.setRecommended(r_recommended);
	s_vo.setEast_jg_type(jg_type);
	s_vo.setJg_wtrlx2(jg_wtrlx2);

	cust_id = customer.append(s_vo);			

	bSuccess = true;
}

customer.remove();
%>

<HTML>
<HEAD>
<TITLE>客户新建</TITLE>
<BASE TARGET="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	
<SCRIPT LANGUAGE="javascript">
var op = "<%=op%>";
var noSameCust = <%=rsList.isEmpty()%>;
var success = <%=bSuccess%>;

window.onload = function () {
		//控制父窗口 转换子窗口	
		if (success){		
			sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
			window.returnValue = getCustomerDetailsInfo();		
			window.close();
		}

		if (op=="check" || op=="detail") {
			if (document.theform.id)
				document.theform.id.focus();
			if (noSameCust) {				
				document.getElementById("detail").style.display = "none";
				document.getElementById("input").style.display = "block";		
				if (op=="check") {
					window.dialogHeight = "500px";
					sl_alert("不存在同名的客户！");					
				}		
			} else {
				document.getElementById("detail").style.display = "block";
				document.getElementById("input").style.display = "none";
				window.dialogHeight = "700px";
			}			
		} else if (op=="init") {
			document.getElementById("detail").style.display = "block";
			//document.getElementById("detail").style.display = "none";
			document.getElementById("input").style.display = "none";
		}
	};

/*查询信息*/
function queryInfo(){
	document.theform.method = "get";
	document.theform.op.value = "check";
	document.theform.action = "cust_new.jsp";
	disableAllBtn(true);
	document.theform.submit();
}

function changeCustomer(){	
	var dataValue = document.theform.id.value;
	//document.theform.cust_id.value = dataValue;
	document.theform.method = "get";
	document.theform.op.value = "detail";
	document.theform.action = "cust_new.jsp";
	disableAllBtn(true);
	document.theform.submit();
}

/*保存信息*/
function saveAction(){
	var v = document.theform.resultValue.value;
	var v_array = v.split(",");
	
	window.returnValue = v_array;		
	window.close();
}

function validateForm(){		
	form = document.theform;
	if (trim(form.q_cust_name.value)=="") {
		sl_alert("客户名不能为空!");
		form.q_cust_name.focus();
		return false;
	}

	if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))return false;//客户来源
	
	if(!sl_checkChoice(form.customer_card_type, "<%=LocalUtilis.language("class.customerCardType",clientLocale)%> "))return false;//证件类型	
	if(!sl_check(form.customer_card_id, "<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",40,1))return false;//证件号码
	
	if(form.customer_card_type.value=="110801"){
		if(!sl_check(form.customer_card_id,"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15))return false;//证件号码
		if(!(form.customer_card_id.value.length==15||form.customer_card_id.value.length==18)){
			sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
			form.customer_card_id.focus();
			return false;
		}
	}
	
	if(form.customer_e_mail.value.length!=0)
		if(!isEmail(form.customer_e_mail,"Email"))return false;
	
	
	if(!sl_checkChoice(form.customer_touch_type, "<%=LocalUtilis.language("class.serviceWay",clientLocale)%> "))return false;//联系方式
	
	if(form.customer_touch_type.value=="110901"){		
		if(form.customer_h_tel.value.length==0&&form.customer_o_tel.value.length==0&&form.customer_mobile.value.length==0&&form.customer_bp.value.length==0){
			sl_alert("<%=LocalUtilis.language("message.telMoreThanOne",clientLocale)%> ");//请至少填一个联系电话
			return false;
		}
		
		if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;//手机号码
		if(!sl_checkNum(form.customer_bp, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 0))	return false;	//手机号码
	}
	
	if(form.customer_touch_type.value=="110902") {
		if(!sl_check(form.customer_post_address, "<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ", 60, 1))return false;//邮寄地址	  
		if(!sl_checkNum(form.customer_post_code, "<%=LocalUtilis.language("class.postcode",clientLocale)%> ", 6, 0))return false;//邮政编码
	}

	if(form.customer_touch_type.value=="110903")
		if(!sl_check(form.customer_e_mail, "Email", 30, 1))	return false;		
		
	if(form.customer_touch_type.value=="110904")
		if(!sl_check(form.customer_fax, "<%=LocalUtilis.language("class.fax",clientLocale)%> ", 60, 1))return false;//传真
		
	if(form.customer_touch_type.value=="110905")
		if(!sl_checkNum(form.customer_mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 11, 1)) return false;//手机号码

	return true;
}

/*保存数据*/
function save2Action(form){
	if(!sl_check(form.customer_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))return false;//客户名称
	if(!sl_checkChoice(form.customer_zyhy_type, "<%=LocalUtilis.language("class.industry",clientLocale)%>/<%=LocalUtilis.language("class.profession",clientLocale)%>"))return false;
	//职业/行业	

	if(validateForm()){			
		if(sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){//保存输入信息
			form.submit();
		}
	}	
}

//新建
function newCust(){
	if (document.getElementById("select"))
		document.getElementById("select").style.display = "none";

	if (document.getElementById("detail"))
		document.getElementById("detail").style.display = "none";
	document.getElementById("input").style.display = "block";
	window.dialogHeight = "500px";
}

//改变客户类型
function changeCustType(value) { // 1个人；2机构
	document.getElementById("prof").innerText = value==1?"职业":"行业";
	document.getElementById("org").style.display = value==1?"none":"block";
	document.getElementById("prof_select").innerHTML = 
		"<select size='1' onkeydown='javascript:nextKeyPress(this)' name='customer_zyhy_type' style='WIDTH: 238px'>"  
		+ (value==1?'<%=Argument.getGrzyOptions2(voc_type)%>':'<%=Argument.getJghyOptions2(voc_type)%>')
		+ "</select>";
	document.getElementById("card").innerHTML = 
			'<select id="customer_card_type" size="1" onkeydown="javascript:nextKeyPress(this)"'
			+' onchange="javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;"'
			+' name="customer_card_type" style="WIDTH: 120px">'
			+ (value==1?'<%=Argument.getCardTypeOptions2(card_type.toString())%>':'<%=Argument.getCardTypeJgOptions2(card_type)%>')
			+ "</select>";				
}

function showAcctNum(value){		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

//通过证件自动识别 新建
function newInfo2(){
	document.getElementsByName("cust_id")[0].value= "" ;
	if(document.getElementById("btnUpdate")!=null){
		document.getElementById("btnUpdate").style.visibility="hidden";
	}
	<%--document.getElementsByName("sonIframe")[0].src="customerInfo_details_action.jsp?select_flag=<%=select_flag%>&pos_flag=<%=pos_flag%>";--%>
}	

/*取得客户信息数组*/
function getCustomerDetailsInfo(){
	var v = new Array();
	
	v[0] = document.theform.q_cust_name.value;
	v[1] = document.getElementsByName("customer_cust_type")[0].value==1?"个人":"机构";//document.theform.customer_cust_type_name.value;
	v[2] = document.theform.customer_card_id.value;
	v[3] = document.theform.customer_h_tel.value;
	v[4] = document.theform.customer_mobile.value;
	v[5] = document.theform.customer_post_address.value;
	v[6] = document.theform.customer_post_code.value;
	v[7] = document.theform.v_cust_id.value;	
	v[8] = "";//document.getElementsByName("customer_service_man_value")[0].value;	
	v[9] = "";//document.getElementsByName("customer_service_man")[0].value;
	v[10]=document.theform.customer_o_tel.value;
	v[11]=document.theform.customer_bp.value;
	
	//if(document.theform.customer_is_link.checked){
	//		v[12] = 1;
	//}
	//else{
		v[12] = 0;
	//}
	
	v[13] = document.getElementsByName("customer_cust_type")[0].value;
	//v[14] = document.getElementsByName("customer_cust_on")[0].value;
	v[15] = document.getElementsByName("customer_card_type")[0].value;	
	v[16] = "";//document.getElementById("customer_legal_man").value;	
	v[17] = "";//document.getElementById("customer_contact_man").value;
	v[18] = document.getElementsByName("customer_e_mail")[0].value;
	v[19] = 1;//document.getElementById("customer_is_deal").value;
	
	return v;
}
</SCRIPT>
</HEAD>

<BODY>
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="cust_new.jsp">
<input name="op" type="hidden"/>
<fieldset style="border:2px groove #f00; background-color:;">
	<legend style="background-color:white; margin-left:20px;">重名检查</legend>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">				
		<tr>
			<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
			<td>			
				<input onkeydown="javascript:nextKeyPress(this);" type="text" name="q_cust_name" size="25" value="<%=q_cust_name%>"/>
			</td>
			<td colspan="2" align="left">
				<button type="button"  class="xpbutton4" accessKey=q onclick="javascript:queryInfo();">重名检查(<u>Q</u>)</button>	
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button"  class="xpbutton4" accessKey=n onclick="javascript:newCust();">直接新建(<u>N</u>)</button>	
			</td>
		</tr>
	</table>	
</fieldset>

<br/>
<% if (! rsList.isEmpty()) { %>
<fieldset id="select" style="border:2px groove #f00;">
	<legend style="background-color:white; margin-left:20px;">已存在的同名客户 </legend>
<div align="left" style="margin-left:40px;margin-top:10px;">			
		<select onkeydown="javascript:nextKeyPress(this)" name="id" style="width:550px;" onchange="javascript:changeCustomer();">
			<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
				<%
				Iterator iterator = rsList.iterator();					
				while (iterator.hasNext()) {	
					Map map = (Map)iterator.next();										
					Integer r_cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")), new Integer(0)); 
					String r_cust_name = Utility.trimNull(map.get("CUST_NAME"));
					String r_card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
					String r_card_id = Utility.trimNull(map.get("CARD_ID"));
				%>
				<option value="<%=r_cust_id%>" <%=r_cust_id.intValue()==cust_id.intValue()?"selected":""%>>							
					<%= (r_cust_name+"-"+r_card_type_name+"-"+r_card_id)%>	
				</option>
				<%
				}					
				%>
		</select>	
</div>
</fieldset>	
<% } else if (! "init".equals(op)) { %>
	<%--不存在同名的客户！--%>
<% } %>

<br/>
<fieldset id="detail" style="border:2px groove #f00;">
	<legend style="background-color:white; margin-left:20px;">同名客户信息</legend>
<!--子窗口-->
<iframe src ="customerInfo_details_view.jsp?cust_id=<%=cust_id%>&select_flag=<%=/*select_flag*/"1"%>" name="sonIframe" frameborder="no" border="0" height="550" width="700" scrolling="no"></iframe>
</fieldset> 

<fieldset id="input" style="border:2px groove #f00;">
	<legend style="background-color:white; margin-left:20px;">新客户信息录入</legend>
<!--客户信息-->
<div>
<table border="0" width="100%" cellspacing="0" cellpadding="1">
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类别-->
		<td >
			<SELECT size="1" onchange="javascript:changeCustType(this.value);"  name="customer_cust_type" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH:120px">
				<%=Argument.getCustTypeOptions2(cust_type)%>
			</SELECT>	
		</td>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.custType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类型-->	
		<td>
			<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:120px" disabled>	
				<%=Argument.getWTCustOptions(new Integer(1))%>
			</select>
			<input name="is_deal" id="is_deal"  type="hidden" value="1"/>		
		</td>
	</tr>
	
	<tr>
		<td align="right">	
			<span style="color:red">*</span><span id="prof">职业</span>:&nbsp;&nbsp;				
		</td>
		<td colspan="3" id="prof_select">
			<select size="1" onkeydown="javascript:nextKeyPress(this)" name="customer_zyhy_type" style="WIDTH: 238px">
			   <%=Argument.getGrzyOptions2(voc_type)%>	
			</select>								
		</td>
	</tr>

	<tr id="org" style="display:none">
		<td align="right"><span style="color:red">*</span>机构类型 :&nbsp;&nbsp;</td><!--机构类型-->	
		<td>
			<select name="jg_type" id="jg_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
				<%=Argument.getDictParamOptions(2109,Utility.trimNull(jg_type))%>
			</select>		
		</td>
		<td align="right"><span style="color:red">*</span>委托人类型 :&nbsp;&nbsp;</td><!--委托人类型-->	
		<td>
			<select name="jg_wtrlx2" id="jg_wtrlx2" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<%=Argument.getInstitutionsOptions(Utility.parseInt(Utility.trimNull(jg_wtrlx2),new Integer(0)))%>
			</select>		
		</td>
	</tr>

	<tr>	
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--客户来源-->
		<td colspan="3">
			<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
				<%=Argument.getCustomerSourceOptions(cust_source)%>
			</select>
		</td>	
	</tr>
	
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :&nbsp;&nbsp;</td><!--证件类型-->
		<td>		   			
			<span id="card">
			<select id="customer_card_type" size="1" onkeydown="javascript:nextKeyPress(this)" 
				onchange="javascript:document.theform.customer_card_type_name.value=document.theform.customer_card_type.options[document.theform.customer_card_type.selectedIndex].text;" 
				name="customer_card_type" style="WIDTH: 120px">
				<%=Argument.getCardTypeOptions2(card_type.toString())%>
			</select>
			</span>	
			<input type="hidden" name="customer_card_type_name" value="<%=card_type_name%>"/>
		</td>	
		
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :&nbsp;&nbsp;</td><!--证件号码-->
		<td>
			<input onkeydown="nextKeyPress(this)" onkeyup="javascript:showAcctNum(this.value)" name="customer_card_id" size="25" maxlength="30" value="<%=card_id%>">
			<span id="bank_acct_num" class="span"></span>
		</td>	
	</tr>
			
	<tr>
		<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :&nbsp;&nbsp;</td><!--联系方式-->
		<td colspan="3">
			<select size="1" name="customer_touch_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getTouchTypeOptions(touch_type)%>
			</select>
		</td>		
	</tr>

	<tr>	
		<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :&nbsp;&nbsp;</td><!--家庭电话-->
		<td><input type="text" name="customer_h_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=h_tel%>"></td>
		<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :&nbsp;&nbsp;</td><!--公司电话-->
		<td><INPUT type="text" name="customer_o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value="<%=o_tel%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :&nbsp;&nbsp;</td><!--手机-->
		<td><input type="text" name="customer_mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value="<%=mobile%>"></td>
		<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:&nbsp;&nbsp;</td><!--手机2-->
		<td><INPUT type="text" name="customer_bp" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=bp%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> :&nbsp;&nbsp;</td><!--传真-->
		<td><input  name="customer_fax" onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="30" value="<%=fax%>"></td>
		<td align="right">Email:&nbsp;&nbsp;</td>
		<td><INPUT  name="customer_e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="40" value="<%=e_mail%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :&nbsp;&nbsp;</td><!--邮政编码-->
		<td ><input  onkeydown="javascript:nextKeyPress(this)" name="customer_post_code" size="25" maxlength="6" value="<%=post_code%>"></td>	
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> :&nbsp;&nbsp;</td><!--邮寄地址-->
		<td ><input  onkeydown="javascript:nextKeyPress(this)" name="customer_post_address" size="40" maxlength="60" value="<%=post_address%>"></td>
	</tr>
	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2:&nbsp;&nbsp;</td><!--邮政编码2-->
		<td ><input  onkeydown="javascript:nextKeyPress(this)" name="customer_post_code2" size="25" maxlength="6" value="<%=post_code2%>"></td>
		
		<td align="right"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2:&nbsp;&nbsp;</td><!--邮寄地址2-->
		<td ><input  onkeydown="javascript:nextKeyPress(this)" name="customer_post_address2" size="40" maxlength="60" value="<%=post_address2%>"></td>
	</tr>
</table>
</div>

<br>
<div align="right">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:save2Action(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--关闭-->
</div>
</fieldset>

<!--子窗口 和 父窗口 的 联系节点-->
<input type="button" style="visibility:hidden;" name="saveButton" onclick="javascript:saveAction()"/>
<input type="button" style="visibility:hidden;" name="changeButton" onclick="javascript:changeAction()"/>
<input type="hidden" name="resultValue" value=""/>
<input type="hidden" name="v_cust_id" value="<%=cust_id%>"/>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
