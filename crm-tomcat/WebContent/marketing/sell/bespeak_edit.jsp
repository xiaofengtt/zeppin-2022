<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫�ݱ���
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
//����ҳ�����
input_bookCode = new Integer(1);//������ʱ����
boolean bSuccess = false;
Integer pre_num = new Integer(0);
//��������
Integer cust_id = new Integer(0);
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String card_id = "";
Integer card_type = new Integer(0);
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
String post_code = "";
String post_address = "";
String summary = "";
BigDecimal reg_money = new BigDecimal(0);
BigDecimal pre_money2  = new BigDecimal(0);
Integer reg_data = new Integer(0);
Integer reg_valid_days = new Integer(0);
String legal_man = "";
String contact_man = "";
Integer service_man = new Integer(0);
Integer service_man_value = new Integer(0);
Integer productid = new Integer(0);
Integer preproduct_id = new Integer(0);
Integer sub_product_id = new Integer(0);
BigDecimal pre_money = new BigDecimal(0);
Integer link_man = new Integer(0);
Integer valid_days = new Integer(0);
String product_name = "";
String pre_type = "";
String cust_type_name = "";
String invest_type = "";
String pre_code = "";
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String per_code = Utility.trimNull(request.getParameter("per_code"));
//��ö���
PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO pre_vo = new PreContractVO();

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO c_vo = new CustomerVO();

if(request.getMethod().equals("POST")){
	//��������	
	cust_id  = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
	pre_money = Utility.parseDecimal(request.getParameter("pre_money"), null);
	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),new Integer(0));
	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
	pre_type = Utility.trimNull(request.getParameter("pre_type"));
	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(0));
	summary = Utility.trimNull(request.getParameter("summary"));
	customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));
	expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
	
	pre_vo.setSerial_no(serial_no);	
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
	pre_vo.setPer_code(per_code);
	preContract.save(pre_vo);
	bSuccess = true;
}
else{
	//ԤԼ��Ϣ���ݼ�
	List list = null;
	Map map = null;
	//�ͻ�����ֵ���ݼ�
	Map map_single = null;
	List rsList_single = null;
	//�õȵ����ݼ�
	pre_vo.setSerial_no(serial_no);	
	list = preContract.load(pre_vo);	
	
	if(list.size()>0){
		map = (Map)list.get(0);
		
		if(map!=null){
			//���ֶθ�ֵ
			productid = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
			preproduct_id = Utility.parseInt(Utility.trimNull(map.get("PREPRODUCT_ID")),new Integer(0));
			product_name =(String)map.get("PRODUCT_NAME");
			sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0));
			cust_id  = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
			pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), null);
			link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
			valid_days = Utility.parseInt(Utility.trimNull(map.get("VALID_DAYS")),new Integer(0));
			pre_type = Utility.trimNull(map.get("PRE_TYPE"));
			pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
			summary = Utility.trimNull(map.get("SUMMARY"));
			invest_type = Utility.trimNull(map.get("PRE_TYPE"));
			pre_code = Utility.trimNull(map.get("PRE_CODE"));
			preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
			expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
			customer_cust_source = Utility.trimNull(map.get("CUST_SOURCE"));
			
			//�ٲ��ҿͻ���Ϣ����ֵ������ֶ�
			c_vo.setCust_id(cust_id);
			c_vo.setInput_man(input_operatorCode);
		
			rsList_single = customer.listByControl(c_vo);
			
			if(rsList_single.size()>0){		
		
				map_single = (Map)rsList_single.get(0);
			
				if(map_single!=null){
					cust_name = Utility.trimNull(map_single.get("CUST_NAME"));
					service_man	= Utility.parseInt(Utility.trimNull(map_single.get("SERVICE_MAN")),new Integer(0));
					card_id = Utility.trimNull(map_single.get("CARD_ID"));
					o_tel =  Utility.trimNull(map_single.get("O_TEL"));
					h_tel =  Utility.trimNull(map_single.get("H_TEL"));
					mobile  =  Utility.trimNull(map_single.get("MOBILE"));
					bp  =  Utility.trimNull(map_single.get("BP"));
					post_code =  Utility.trimNull(map_single.get("POST_CODE"));
					post_address =  Utility.trimNull(map_single.get("POST_ADDRESS"));	
					card_type  = Utility.parseInt(Utility.trimNull(map_single.get("CARD_TYPE")),new Integer(0));
					cust_type = Utility.parseInt(Utility.trimNull(map_single.get("CUST_TYPE")),new Integer(0));		
				}
			}
		}		
	}
}

//�Ϲ�������Ĵ���
String reg_money_cn = "";
if (pre_money.intValue()!= 0){
	reg_money_cn =  Utility.numToChinese(pre_money.toString());
}
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakEdit",clientLocale)%> </TITLE>
<!--ԤԼ�༭-->
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
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>
//�޸ĳɹ���־
var bSuccess = <%=bSuccess%>;
var preproduct_id = <%=preproduct_id%>;

//��ʼ��
window.onload = function(){
		showPreinfo();
		
		if (bSuccess) {		
			sl_alert("<%=LocalUtilis.language("message.bespeakInfoUpdateOK",clientLocale)%> ��");//ԤԼ��Ϣ �޸ĳɹ�
			location.href = "bespeak_list.jsp";
		}
	};

/*����*/
function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action = "bespeak_edit.jsp";
		document.theform.submit();
	}
}
/*����*/
function CancelAction(){
	window.location.href="bespeak_list.jsp";
}
/*�����������ͻ�ʱ�õ��ĺ���*/
function validateForm(form){
	if(form.customer_cust_id.value=="")	{
			sl_alert("<%=LocalUtilis.language("message.chooseCustomer",clientLocale)%> ��");//��ѡ��ͻ�
			return false;
	}
	//if(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> "))return false;//��Ʒ
	if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1))return false;//ԤԼ���
	if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//ԤԼ����
	if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//ԤԼ��ʽ
	if(!sl_checkNum(form.valid_days, "<%=LocalUtilis.language("lass.validDays",clientLocale)%> ", 10, 1))return false;//��Ч����
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//��ϵ��
	if(!sl_checkDate(document.theform.pre_date_picker,"<%=LocalUtilis.language("class.preDate",clientLocale)%> ")){//ԤԼ����
		return false;
	}
	syncDatePicker(form.pre_date_picker, form.pre_date);
	
	if(document.theform.exp_reg_date_picker.value!=""){
		if(!sl_checkDate(document.theform.exp_reg_date_picker,"Ԥ���Ϲ�����")){//Ԥ���Ϲ�����
			return false;
		}
		syncDatePicker(form.exp_reg_date_picker, form.exp_reg_date);
	}
	
	if(form.pre_date.value>form.pre_end_date.value&&(!confirm("<%=LocalUtilis.language("message.bespeakAddTip",clientLocale)%> ")))return false;//ԤԼ���ڴ����ƽ鵽�����ڣ�Ҫ������	
	return sl_check_update();
}
/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;
	
	var url = '<%=request.getContextPath()%>/marketing/customer_info2.jsp?prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v != null){
		showMarketingCustomer(prefix, v);
	}	
	
	return (v != null);
}
/*�������۹�����*/
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
	getElement(prefix, "cust_on").value = v[14];		
	getElement(prefix, "card_type").value = v[15];			
	getElement(prefix, "legal_man").value = v[16];	
	getElement(prefix, "contact_man").value = v[17];
	if(getElement(prefix, "e_mail")!=null)
		getElement(prefix, "e_mail").value = v[18];	
		
		
	if(getElement(prefix, "gain_acct")!=null)
		getElement(prefix, "gain_acct").value = v[0];  
		 
	prodid=0;		
	for(i=0;i<document.theform.link_man.options.length;i++){
	
		if(document.theform.link_man.options[i].value==v[9]){
		
			document.theform.link_man.options[i].selected=true;
			prodid = document.theform.link_man.options[i].value;
			break;
	    }	
	}
	
	if (prodid==0){
		document.theform.link_man.options[0].selected=true;	
	}
}

function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pre_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//��
	else
		pre_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

/*�޸���ԤԼ��Ŀ ��ԤԼ���*/
function showPreinfo(){
	var product_id = document.theform.product_id.value;

	utilityService.getPreInfoById(preproduct_id, product_id,{callback: function(data){
	document.theform.fact_pre_num.value = data[0];
	document.theform.fact_pre_money_view.value = formatNum(data[1]);
	document.theform.pre_end_date.value = data[2];
	}});	
}
//�鿴�ͻ���Ϣ
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript: return validateForm(this);">
<input type="hidden" name="pre_end_date" value="">
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<input type='hidden' name="customer_cust_type"  value="<%= cust_type%>"/>
<input type='hidden' name="customer_card_type" value="<%=card_type%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--���۹���>>ԤԼ-->
	<hr noshade color="#808080" size="1">
</div>

<!--�ͻ�ѡ��-->
<div align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
		<tr>
			<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--�ͻ���Ϣ-->					
			<td align="left">
				<button type="button"  class="xpbutton3" accessKey=e name="btnEdit" title="<%=LocalUtilis.language("menu.customerInformation",clientLocale)%> " 
				    onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
				</button><!--�ͻ���Ϣ--><!--�鿴-->
			</td>
		<tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
			<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
			</td>
			<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
			<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="20" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getOpName(service_man))%>">&nbsp;&nbsp;&nbsp;
			</td>
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
			<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%= cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
			<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= card_id%>" size="25"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--��ͥ�绰-->
			<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= h_tel%>" size="20"></td>
			<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--��˾�绰-->
			<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= o_tel%>" size="20"></td>
			</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--�ֻ�1-->
			<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= mobile%>" size="20"></td>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--�ֻ�2-->
			<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%= bp%>" size="20"></td>
		
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
			<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%= post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
			<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--��������-->
			<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%= post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
		</tr>
		
		<tr>
			<td colspan="4"><hr size="1"></td>
		</tr> 
		<!--ԤԼ��Ϣ-->
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
			<td>
				<input type="hidden" name="product_id" value="<%=productid%>"/>
				<input readonly class="edline" name="product_name" size="60" value="<%=Utility.trimNull(product_name)%>"/>	
			</td>
			<td align="right"><%=LocalUtilis.language("class.preCode2",clientLocale)%> :</td><!--ԤԼ���-->
			<td><input  name="per_code" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=pre_code%>"/></td>				
		</tr>
		<tr id="sub_product_id" style="display:<%=sub_product_id.intValue()==0?"none":""%>">
			<td align="right">�Ӳ�Ʒ :</td>
			<td id="tdSubProductId" colspan="3">
				<input type="hidden" name="sub_product_id" value="<%=sub_product_id%>"/>
				<input readonly class="edline" name="product_name" size="60" 
					value="<%=Utility.trimNull(Argument.getSubProductName(productid, sub_product_id))%>"/>			
			</td>			
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum3",clientLocale)%> :</td><!--��ԤԼ����-->
			<td><input readonly class="edline" name="fact_pre_num" onkeydown="javascript:nextKeyPress(this)" size="10" value=""></td>
			<td align="right"><%=LocalUtilis.language("class.factPreMoney",clientLocale)%> :</td><!--��ԤԼ�ܽ��-->
			<td><input readonly class="edline" name="fact_pre_money_view" onkeydown="javascript:nextKeyPress(this)" size="27" value=""><input type="hidden" name="fact_pre_money" onkeydown="javascript:nextKeyPress(this)" size="27" value=""></td>
		</tr>		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.factPreNum2",clientLocale)%> :</td><!--ԤԼ���-->
			<td><input name="pre_money" size="27" value="<%=Format.formatMoney(pre_money)%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value)">
				<span id="pre_money_cn" class="span">&nbsp;</span>
				 <%if (pre_money!= null){%>
				<span id="pre_money_cn" class="span">(<%=reg_money_cn%>)</span>
    		    <%}
    			else {%>
    				<span id="pre_money_cn" class="span">&nbsp;</span><!--��-->
    			<%}%>
			</td><!-- �� -->
			<td align="right"><%=LocalUtilis.language("class.preNum2",clientLocale)%> :</td><!--ԤԼ����-->
			<td><select name="pre_num" >
			<%=Argument.getPreNumOptions(pre_num)%></select></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preType",clientLocale)%> :</td><!--ԤԼ��ʽ-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(invest_type)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.validDays",clientLocale)%> :</td><!--��Ч����-->
			<td><input name="valid_days" size="27" value="<%=valid_days%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr>
			<td align="right">Ԥ���Ϲ�����:</td><!--Ԥ���Ϲ�����-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" class=selecttext 
				<%if(expRegDate==null||expRegDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(expRegDate)%>"<%}%> >
				<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exp_reg_date_picker,theform.exp_reg_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			<td align="right">*<%=LocalUtilis.language("class.customerSource",clientLocale)%> :&nbsp;&nbsp;</td><!--�ͻ���Դ-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_cust_source" style="width: 110px">
					<%=Argument.getCustomerSourceOptions(customer_cust_source)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--��ϵ��-->
			<td >
				<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getManager_Value(link_man)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%> :</td><!--ԤԼ����-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" class=selecttext 
			<%if(preDate==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.pre_date_picker,theform.pre_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--����-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
preContract.remove();
customer.remove();
%>