<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ȡҳ�洫�ݱ���
Integer cust_id = Utility.parseInt(request.getParameter("customer_cust_id"),new Integer(0));//�ͻ�ID 
BigDecimal reg_money = Utility.parseDecimal(request.getParameter("s_money"), new BigDecimal(0),2,"1");
String invest_type=Utility.trimNull(request.getParameter("s_invest_type"));
String summary = Utility.trimNull(request.getParameter("s_summary"));
Integer reg_data = Utility.parseInt(request.getParameter("s_date"),new Integer(0));
Integer reg_valid_days = Utility.parseInt(request.getParameter("s_valid_days"),new Integer(0));
String invest_type_name = Utility.trimNull(request.getParameter("invest_type_name"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
//ҳ�渨������
boolean bSuccess = false;
StringBuffer list = Argument.newStringBuffer();
input_bookCode = new Integer(1);//������ʱ����
//��ȡ����
PreContractLocal preContract = EJBFactory.getPreContract();//Ԥ�Ǽ���Ϣ
CustomerLocal customer = EJBFactory.getCustomer();//�ͻ�
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
PreContractVO pre_vo = new PreContractVO();
CustomerVO c_vo = new CustomerVO();
//��������
//--2.�ͻ���Ϣ
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
String cust_tel = Utility.trimNull(request.getParameter("cust_tel"));
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
String card_type_name = "";
String service_man_mobile = ""; //�ͻ��������ֻ�����
String smsContent = "��������";
List rsList_cust = new ArrayList();
Map map_cust = new HashMap();
//�ͻ���Ϣ��ϸ
if(cust_id.intValue()>0){
	//�ͻ�����ֵ
	c_vo.setCust_id(cust_id);
	c_vo.setInput_man(input_operatorCode);
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
		card_type_name = Utility.trimNull(map_cust.get("CARD_TYPE_NAME"));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		cust_tel = Utility.trimNull(map_cust.get("CUST_TEL"));
		//������Ϣ
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
//�������
if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();
	pre_vo = new PreContractVO();
	if(cust_id.intValue() == 0){
		//�����ͻ���Ϣ
		String customer_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"));
		Integer is_deal_picker = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal_picker")), new Integer(0));
		cust_tel = Utility.trimNull(request.getParameter("cust_tel"));
		String card_types = Utility.trimNull(request.getParameter("card_type"));
		if("".equals(card_types) || card_types.equals("0"))
			card_types=  "110801";//Ĭ������֤
		String card_ids = Utility.trimNull(request.getParameter("card_id"));
		if("".equals(card_ids))
			card_ids = " ";//Ĭ�Ͽ�ֵ
		c_vo.setCust_name(customer_cust_name);
		c_vo.setCust_type(is_deal_picker);
		c_vo.setCust_tel(cust_tel);
		c_vo.setCard_type(card_types);
		c_vo.setCard_id(card_ids);
		c_vo.setInput_man(input_operatorCode);
		c_vo.setService_man(link_man);
		c_vo.setComplete_flag(new Integer(2)); //�ͻ���Ϣ������
		cust_id =  customer.append(c_vo);
	
		//�ͻ���Ϣ��ϸ
		if(cust_id.intValue()>0){
			//�ͻ�����ֵ
			c_vo.setCust_id(cust_id);
			c_vo.setInput_man(input_operatorCode);
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
				cust_tel = Utility.trimNull(map_cust.get("CUST_TEL"));
				//������Ϣ
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
	}
	//�ͻ���Ϣ���ݴ��
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
	//ͬ���ͻ���Ϣ
	customer_inst.cope_customers(c_vo);
	//��ȡԤԼ��Ϣ
	reg_money = Utility.parseDecimal(request.getParameter("reg_money"), new BigDecimal(0));
	invest_type = Utility.trimNull(request.getParameter("invest_type"));
	invest_type_name = Utility.trimNull(request.getParameter("invest_type_name"));
	summary = Utility.trimNull(request.getParameter("summary"));
	reg_data = Utility.parseInt(request.getParameter("reg_data"),new Integer(0));
	reg_valid_days = Utility.parseInt(request.getParameter("reg_valid_days"),new Integer(0));
	customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
	//Ԥ�Ǽ���Ϣ���ݴ��
	pre_vo.setBook_code(input_bookCode);
	pre_vo.setCust_id(cust_id);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setReg_money(reg_money);
	pre_vo.setSummary(summary);
	pre_vo.setReg_date(reg_data);
	pre_vo.setReg_valid_days(reg_valid_days);
	pre_vo.setInvest_type(invest_type);
	pre_vo.setInvest_type_name(invest_type_name);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setLink_man(link_man);
	//Ԥ�Ǽ���Ϣ����
	preContract.append_reginfo(pre_vo);

	//��ѯ�ͻ�������Ϣ
	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),new Integer(0));
	if(link_man.intValue() != 0){
		List op_list = Argument.getOperatorInfoByOpCode(link_man);
		Map op_map = new HashMap();
		if(op_list != null && op_list.size() != 0){
			op_map = (Map)op_list.get(0);
			service_man_mobile = Utility.trimNull(op_map.get("MOBILE"));
			smsContent = Utility.trimNull(op_map.get("OP_NAME")) + "���ã��ͻ���" + cust_name + "�Ѿ�Ԥ�Ǽǳɹ�����ϵ�绰��" +cust_tel+ "���Ǽǽ�" + Format.formatMoney(reg_money) + "Ԫ���뾡����ϵ��";
		}
	}
	
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.checkinAdd",clientLocale)%> </TITLE>
<!--Ԥ�Ǽ�����-->
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
/*����*/
function SaveAction(){
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="checkin_simple_add.jsp"
		document.theform.submit();
	}
}
/*�����������ͻ�ʱ�õ��ĺ���*/
function validateForm(form){
	//if(form.customer_cust_id.value==""||form.customer_cust_id.value==0){
		//sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��");//��ѡ��ͻ�
		//return false;
	//}
	<%if(cust_id.intValue() == 0){%>
	//�ͻ���Ϣ��֤
	if(!sl_check(form.customer_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//�ͻ�����
	if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")){return false;}//�ͻ�����
	if(!sl_checkNum(form.cust_tel, "��ϵ�绰", 20, 1))	return false;//��ϵ�绰
	<%}%>
	if(!sl_checkDecimal(form.reg_money,"<%=LocalUtilis.language("class.regMoney",clientLocale)%> ",13,3,1))return false;//�ǼǶ��
	if(!sl_checkNum(form.reg_valid_days,"<%=LocalUtilis.language("class.validDays",clientLocale)%> ",8,1))return false;//��Ч����
	if(!sl_checkDate(form.reg_date_picker,"<%=LocalUtilis.language("class.regDate",clientLocale)%> "))return false;//�Ǽ�����
	if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))return false;//�ͻ���Դ
	if(!sl_check(form.invest_type_name, "<%=LocalUtilis.language("class.investType",clientLocale)%> ",60,1))return false;//Ԥ��Ͷ��
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//��ϵ��

	syncDatePicker(form.reg_date_picker, form.reg_date);
	return sl_check_update();
}
/*��ʾ����Ǯ��*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		reg_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//��
	else
		reg_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
/*ˢ��ҳ��*/
function refreshPage(){
	var url = 'checkin_add.jsp?s_summary='+ document.theform.summary.value;
	var url = url + '&s_money=' + document.theform.reg_money.value;
	var url = url + '&s_invest_type=' + document.theform.invest_type.value;
	var url = url + '&s_valid_days=' + document.theform.reg_valid_days.value;
	var url = url + '&s_date=' + document.theform.reg_date_picker.value;
	var url = url + '&customer_cust_id=' + document.theform.customer_cust_id.value;
	location = url;
}
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;

	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;

	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
	if (v != null){
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}
	return (v != null);
}
/*��ѡͶ��*/
function chooseAction(){
	var url = "<%=request.getContextPath()%>/marketing/invest_type_check.jsp"
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');

	if(ret!=null){
		document.getElementById("invest_type").value = ret[0];
		document.getElementById("invest_type_name").value = ret[1];
	}
}

//��ȡ�ֵ����Ŷ���ϵ��
function getTeamInfoByRegMoney(reg_money){
	var str = "";
	contract.getTeamInfoByRegMoney('<%=input_bookCode%>',reg_money,{callback: function(data){
		str = "<font color='red'>(ע�⣺��ǰӦ�÷�����Ŷӣ�"+data[0]+"[�����ˣ�"+data[2]+"])</font>";
		document.getElementById("teaminfo").innerHTML = str;
		
		//ѡ����ϵ��
		var items = document.theform.link_man;
		for(var i=0; i<items.options.length; i++){
			if(items[i].value == data[1]){
				items[i].selected = true;
				break;
			}
		}
	}});
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript: return validateForm(this);">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="customer_cust_id" name="customer_cust_id" value="<%=cust_id%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b>���۹���>>Ԥ�Ǽ�</b></font>
</div>
<!--�ͻ�ѡ��-->
<div vAlign=top align=left class="direct-panel">
<fieldset>
<legend><b style="color: blue;">�ͻ���Ϣ</b></legend>
<table cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
	<%if(cust_id.intValue() == 0){%>
	<tr>
		<td align="right">*�ͻ�����:</td>
		<td colspan="3">
			<input maxlength="100" name="customer_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name %>">
		</td>
	</tr>
	<tr>
		<td align="right" width="13%">*�ͻ�����:</td>
		<td width="39%">
			<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">��ѡ��</option>
				<option value="1" <%=cust_type.intValue() == 1 ? "selected" : ""%>>����</option>
				<option value="2" <%=cust_type.intValue() == 2 ? "selected" : ""%>>����</option>
			</select>
		</td>
		<td align="right" width="13%">*��ϵ�绰:</td>
		<td width="35%">
			<input maxlength="100" name="cust_tel" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_tel %>">
		</td>
	</tr>
	<tr>
		<td align="right">֤������:</td>
		<td >
			<select onkeydown="javascript:nextKeyPress(this)"name="card_type" style="WIDTH: 160px">
				<%=Argument.getCardTypeOptions2(cust_type+"")%>
			</select>
		</td>
		<td align="right">֤������:</td>
		<td >
			<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=card_id %>">
		</td>
	</tr>
	<%}else{ %>
	<tr>
		<td align="right">�ͻ�����:</td>
		<td colspan="3">
			<input size="27" class="edline" readonly="readonly" value="<%=cust_name %>">
		</td>
	</tr>
	<tr>
		<td align="right" width="13%">�ͻ�����:</td>
		<td width="39%">
			<input size="27" class="edline" readonly="readonly" value="<%=cust_type_name %>">
		</td>
		<td align="right" width="13%">��ϵ�绰:</td>
		<td width="35%">
			<input size="27" class="edline" readonly="readonly" value="<%=cust_tel %>">
		</td>
	</tr>
	<tr>
		<td align="right">֤������:</td>
		<td >
			<input size="27" class="edline" readonly="readonly" value="<%=card_type_name %>">
		</td>
		<td align="right">֤������:</td>
		<td >
			<input size="27" class="edline" readonly="readonly" value="<%=card_id %>">
		</td>
	</tr>
	<%} %>
</table>
</fieldset>
<fieldset>
<legend><b style="color: blue;">Ԥ�Ǽ���Ϣ</b></legend>
	<table cellSpacing=2 cellPadding=2 width="100%" border=0 class="product-list">
		
<!--Ԥ�Ǽ���Ϣ-->
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--�ǼǶ��-->
			<td>
				<input name="reg_money" size="27" value="<%=reg_money%>" onblur="javascript:getTeamInfoByRegMoney(this.value);" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.reg_money,'<%=enfo.crm.tools.LocalUtilis.language("class.regMoney",clientLocale) %>',13,3,0);showCnMoney(this.value)"/>
				<!--�ǼǶ��-->
				<span id="reg_money_cn" class="span">&nbsp;</span><!--��-->
			</td>
			<td align="right">*<%=LocalUtilis.language("class.investType",clientLocale)%> :</td><!--Ԥ��Ͷ��-->
			<td>
				<!--
				<select size="1" name="invest_type"	onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getDictParamOptions_intrust(1131,invest_type)%>
				</select>
				-->
				<input type="text" name="invest_type_name" id="invest_type_name" size="27"  value="<%=invest_type_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="invest_type" id="invest_type" value="" />
				<!-- ѡ�� -->&nbsp;&nbsp;
                <button type="button"  class="xpbutton2" id="btnChoInvestType" name="btnChoInvestType" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
			</td>
		</tr>
		<tr>
			<td align="right" width="13%">*<%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--��Ч����-->
			<td width="39%"><input name="reg_valid_days" type="text" size="27" value="<%=reg_valid_days%>"/>&nbsp;&nbsp;<%=LocalUtilis.language("message.days",clientLocale)%> <!--��--></td>
			<td align="right" width="13%">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--�ͻ���Դ-->
			<td width="35%">
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 160px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.regDate",clientLocale)%> :</td><!--�Ǽ�����-->
			<td >
			<input onkeydown="javascript:nextKeyPress(this)" size="27" name="reg_date_picker" class=selecttext
					<%if (reg_data.intValue() == 0|| reg_data == null) {%>
					value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}
					else {%>value="<%=Format.formatDateLine(reg_data)%>"<%}%>  />
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.reg_date_picker,theform.reg_date_picker.value,this);" tabindex="13">
			<input type="hidden" name="reg_date" value="">
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
			<td colspan="3">
			    <select name="link_man" style="width:160px">
			        <%=Argument.getManager_Value(link_man)%>				    
			    </select>
				&nbsp;<span id="teaminfo"></span>
			</td>
		</tr>
		<tr>
			<td align="right" vAlign="top"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
			<td colspan="3"><textarea cols="100" rows="6" name="summary"><%=summary%></textarea></td>
		</tr>
	</table>
</fieldset>
</div>

<div align="right" style="margin-right:5px">
	<br>
	<button type="button"  class="xpbutton3" id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
	<button type="button"  style="display: none;" class="xpbutton3" id="btnCancel" name="btnCancel" onclick="javascript:location='checkin_list.jsp'"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--����-->
</div>

<%
preContract.remove();
customer_inst.remove();
%>

</form>
<%@ include file="/includes/foot.inc"%>
<script language=javascript>
window.onload = function(){
	getTeamInfoByRegMoney(0);
	var v_bSuccess = document.getElementById("bSuccess").value;

	if(v_bSuccess=="true"){
		var messsage = "�ƶ�����Ϊ�ջ��ʽ����ȷ�����ܼ�ʱ���Ͷ����뾡��ά����";
		//1.���Ͷ���֪ͨ�ͻ�����
		<%if(service_man_mobile != "" && service_man_mobile.length() == 11){%>
		ccService.sendSMSSimple("<%=input_operatorCode%>","<%=service_man_mobile%>","<%=smsContent%>","1","����",0,"<%=input_operatorCode%>",{callback:function(data){
			var arr = data.split("|");
			message = arr[1];//sl_alert(arr[1]);
		}});
		<%}%>
		
		//2.Ԥ�Ǽ���Ϣ����ɹ�
		sl_alert("<%=LocalUtilis.language("message.checkinInfoSaveOK",clientLocale)%> ��");
		location = "checkin_list.jsp";
	}
}
</script>
</BODY>
</HTML>