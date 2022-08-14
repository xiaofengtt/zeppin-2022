<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//��Ʒ���
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//��ͬ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//��ͬԭ�����˵ı��
BigDecimal moneytype2 = Utility.parseDecimal(request.getParameter("moneytype2"), null);//ת�÷ݶ�

Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")), new Integer(0)); //���÷��ͻ�ID
int inputflag=Utility.parseInt(request.getParameter("inputflag"), 1);//������
String bank_id = Utility.trimNull(request.getParameter("bank_id"));//��������ID
String from_cust_id = Utility.trimNull(request.getParameter("from_cust_id"));
String exchangetype = Utility.trimNull(request.getParameter("exchangetype"));//ת�÷�ʽ
java.math.BigDecimal exchange_amount =
	Utility.parseDecimal(Utility.trimNull(request.getParameter("contractsum1")), new java.math.BigDecimal(0));//ת�ý��
String sx_fee = Utility.trimNull(request.getParameter("peoplenum1"));//ת��������
String check_man = Utility.trimNull(request.getParameter("check_man"));
String Summary = Utility.trimNull(request.getParameter("Summary"));
String bank_sub_name = Utility.trimNull(request.getParameter("bank_sub_name"));
Integer project_id = Utility.parseInt(request.getParameter("project_id"),new Integer(0));
Integer problem_id = new Integer(0);

String defaultOptions = "<option value=\"\" selected>ѡ��Ĭ��ֵ</option>";//ѡ��Ĭ��ֵ �������ù��ʻ�
//"<option value=\"\" selected>��ѡ��</option>"
String contract_bh_list = defaultOptions;//��ͬ�б�
String from_cust_id_list = defaultOptions;//�������б�
String trans_flagName_list = defaultOptions;//ת�÷�ʽ
int readonly=0;//0��ʾ��������Ȩת��
String preCodeOptions = defaultOptions;//�����˺��б�
String strButton=enfo.crm.tools.LocalUtilis.language("message.pleaseSelect",clientLocale);//��ѡ��
String jk_type ="";
boolean is_succes_flag = false;
Integer from_list_id=new Integer(0);

BenifitorLocal benifitor_local = EJBFactory.getBenifitor();//�����˷ݶ�Bean
BenifitorVO benifitor_vo = new BenifitorVO();
CustomerLocal cust_local = EJBFactory.getCustomer();//�ͻ�Bean
CustomerVO cust_vo = new CustomerVO();
BenChangeLocal change_local = EJBFactory.getBenChanage();//����Ȩת��Bean
BenChangeVO change_vo = new BenChangeVO();

List benifitor_list = new ArrayList();
Map benifitor_map = new HashMap();
List cust_list = new ArrayList();
Map cust_map = new HashMap();

//ѡ���Ʒ�����Ӧ�ĺ�ͬ���
if(product_id != null && !product_id.equals(new Integer(0)))
{
	//1����ͬ��Ų�Ϊ��ʱ����ʾѡ���ͬ��ţ����øò�Ʒ�����к�ͬ��Ų�ѡ�иú�ͬ��ţ�2�����ԭ��������Ϣ
	if(contract_bh != null && !"".equals(contract_bh))
	{
		contract_bh_list = Argument.getContract(new Integer(1), product_id, contract_bh,input_operatorCode);
		//ԭ�����˵ı�Ų�Ϊ��ʱ����ʾѡ����������Ϣ������ԭ�����˿�ת�÷ݶ�
		if(serial_no != null && !(serial_no.equals(new Integer(0))))
		{
			benifitor_vo.setSerial_no(serial_no);
			benifitor_list = benifitor_local.load(benifitor_vo);
			if(benifitor_list != null && benifitor_list.size() > 0)
			{
				benifitor_map = (Map)benifitor_list.get(0);
				moneytype2 = Utility.stringToDouble(Utility.trimNull(benifitor_map.get("BEN_AMOUNT")));
				from_cust_id = Utility.trimNull(benifitor_map.get("CUST_ID"));
				jk_type=Utility.trimNull(benifitor_map.get("JK_TYPE"));
				from_list_id =Utility.parseInt(Utility.trimNull(benifitor_map.get("LIST_ID")), new Integer(0));
			}
		}
		//���δѡ����������Ϣ�������������б�
		from_cust_id_list = Argument.getFromCustIdOptions(new Integer(1), product_id, contract_bh, serial_no,input_operatorCode);
	}else
	{
		//��ͬ���Ϊ��ʱ��øò�Ʒ�����к�ͬ���
		contract_bh_list = Argument.getContract(new Integer(1), product_id, "110203",input_operatorCode);
	}
}

//ѡ�����÷���Ϣ���������÷���Ϣ
if(!customer_cust_id.equals(new Integer(0)))
{
	cust_vo.setCust_id(customer_cust_id);
	cust_vo.setInput_man(input_operatorCode);
	cust_list = cust_local.listProcAll(cust_vo);
	if(cust_list != null && cust_list.size() > 0)
	{
		cust_map = (Map)cust_list.get(0);
		//ѡ����������ʱ����˺�
		if(bank_id != null && !("".equals(bank_id)))
			preCodeOptions = Argument.getCustBankAcctOptions(Utility.parseInt(Utility.trimNull(cust_map.get("CUST_ID")),new Integer(0))
							,bank_id,Utility.trimNull(cust_map.get("CARD_ID")),"");
	}
}

//�����˺�û��ʱ�����Ϊ�����
if(preCodeOptions.equals("<option value=\"\" selected>��ѡ��</option>"))
////"<option value=\"\" selected>��ѡ��</option>"
	inputflag=2;
	
//��������Ȩת����Ϣ
if(request.getMethod().equals("POST"))
{
	change_vo.setProduct_id(product_id);
	change_vo.setInput_man(input_operatorCode);
	change_vo.setInput_time(Utility.getCurrentTimestamp());
	change_vo.setContract_bh(contract_bh);
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
	if(inputflag==1)
	{
		change_vo.setBank_acct(request.getParameter("bank_acct2"));
	}
	else
	{
		change_vo.setBank_acct(request.getParameter("bank_acct"));
	}
	change_vo.setForm_list_id(from_list_id);
	change_vo.setFx_change_flag(Utility.parseInt(request.getParameter("fx_change_flag"),new Integer(2)));
	change_vo.setSy_change_flag(Utility.parseInt(request.getParameter("sy_change_flag"),new Integer(2)));
	change_vo.setTrans_type(Utility.trimNull(request.getParameter("trans_type")));
	change_vo.setChange_qs_date(new Integer(request.getParameter("change_qs_date")));
	change_vo.setProjectid(project_id);
	problem_id = change_local.append(change_vo);
	
	is_succes_flag = true;
}
%>
<HTML>
<HEAD>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script>
//�ɹ�֮�󷵻ص��б�ҳ��
<%if(is_succes_flag){%>
	sl_alert("<%=LocalUtilis.language("message.beneficiaryTranSaveOK",clientLocale)%> ��");//����Ȩת�ñ���ɹ�
	//����Աӵ���������Ȩ��ʱ�Զ�����������ҳ��
	<%if(projectAccessPower.intValue() == 1 && problem_id.intValue() != 0 && project_id.intValue() != 0){ %>
		showprojectinfo(<%=problem_id%>,<%=project_id%>,0,0);
	<%}else{ %>		
		location = "beneficial_transfer_list.jsp?firstFlag=1&contract_bh=<%=contract_bh%>&product_id=<%=product_id%>";  
	<%} %>       
<%}%>

//չʾ����
function showprojectinfo(problem_id,project_id,flag,cust_id){
	//layout.getRegion('west').hide();
	var url = '<%=Utility.trimNull(application.getAttribute("INTRUST_ADDRESS"))%>/k2/logintrust.jsp?problem_id='+problem_id+'&uid=<%=input_operator.getLogin_user()%>';
	location = url;
}

//ѡ���Ʒ����ͬ��š�ԭ��������Ϣ����������
function selectOthers()
{		
	form=document.theform;
	product_id=form.product_id.value;//��ƷID
	contract_bh=form.contract_bh.value;//��ͬID
	serial_no =  form.serial_no.value;//ת�÷�ID
	customer_cust_id = form.customer_cust_id.value;//���÷�ID
	bank_id=form.bank_id.value;//��������ID
	moneytype2 = form.moneytype2.value;//��ת�÷ݶ�
	
	location="beneficial_transfer_add.jsp?product_id="+product_id+"&contract_bh="+contract_bh
				+ "&serial_no=" + serial_no + "&customer_cust_id=" + customer_cust_id
				+ "&bank_id=" + bank_id + "&moneytype2=" + moneytype2;
}

//ѡ�����÷��ͻ���Ϣ
function getTransactionCustomer(prefix,readonly)
{
	var cust_id = getElement(prefix, "cust_id").value;//���Ĭ�Ͽͻ�ID(customer_cust_id)
	v = showModalDialog('<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix=' + prefix + '&cust_id=' + cust_id + '&readonly='+readonly,'','dialogWidth:700px;dialogHeight:638px;status:0;help:0;');
	if (v != null)
	{
		showTransactionCustomer(prefix, v);
	}	
	return (v != null);
}

//ѡ����������
function selectBank(value)
{
	if (value != "")
	{	
		selectOthers();
	}
}

//�����˺�ѡ�������
function changeInput(obj)
{
	if(document.theform.inputflag.value==1)
	{
		obj.innerText="<%=LocalUtilis.language("message.choose",clientLocale)%> ";//ѡ��
		document.theform.bank_acct.style.display="";
		document.theform.bank_acct2.style.display="none";
		document.theform.inputflag.value=2;
	}
	else
	{
		obj.innerText="<%=LocalUtilis.language("message.input",clientLocale)%> ";//����
		document.theform.bank_acct.style.display="none";
		document.theform.bank_acct2.style.display="";
		document.theform.inputflag.value=1;
	}
}

//ѡ��ת�÷�ʽ���ת�ý��
function exchage()
{
	if(document.theform.exchangetype.value == '111501')//ȫ��ת��
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

//���������˺�ʱ��ʾλ��
function showAcctNum(value)
{		
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " λ )";
}

//������֤
function validateForm(theform)
{ 	

	 	
	if(!sl_checkChoice(theform.product_id,"<%=LocalUtilis.language("class.productName",clientLocale)%> "))//��Ʒ����
		return false;
	if(!sl_checkChoice(theform.contract_bh,"<%=LocalUtilis.language("class.contractID",clientLocale)%> "))//��ͬ���
		return false;
		
	if(theform.customer_cust_id.value==0)	
	{
		sl_alert("<%=LocalUtilis.language("message.chooseAssigneeTip",clientLocale)%> ��");//��ѡ�����÷�
		return false;
	}
	if (theform.customer_cust_id.value == theform.from_cust_id.value)
	{
		sl_alert("<%=LocalUtilis.language("message.assigneeError",clientLocale)%> ��");//���÷����ܺ���������ͬ		
		return false;
	}

	if(!sl_checkChoice(theform.exchangetype,"<%=LocalUtilis.language("class.exchangeType",clientLocale)%> "))//ת�÷�ʽ
		return false;
	if(sl_parseFloat(theform.contractsum.value)>sl_parseFloat(theform.moneytype2.value)){
		sl_alert("<%=LocalUtilis.language("message.TransferableSharesLack",clientLocale)%> !");//��ת�÷ݶ��
		return false;
	}
	if(!sl_checkDecimal(theform.contractsum, "<%=LocalUtilis.language("class.contractSum",clientLocale)%> ", 13,3,1))	//ת�÷ݶ�
		return false;		
	theform.contractsum1.value=sl_parseFloat(theform.contractsum.value);
	var userid='<%=user_id.intValue()%>';
	if(userid=="5"){
		if(!sl_checkDecimal(theform.sxfee,"<%=LocalUtilis.language("class.sxFee",clientLocale)%> ", 13,3,0))	//ԭ�����˱����
			return false;
		if(!sl_checkDecimal(theform.sxfee1,"<%=LocalUtilis.language("class.sxFee1",clientLocale)%> ", 13,3,0))//�������˱����	
			return false;
		if(!sl_checkDecimal(theform.sxfee2,"<%=LocalUtilis.language("class.sxFee2",clientLocale)%> ", 13,3,0))//ԭ����������Ȩ��ת�÷�	
			return false;
		if(!sl_checkDecimal(theform.sxfee3,"<%=LocalUtilis.language("class.sxFee3",clientLocale)%> ", 13,3,0))//������������Ȩ��ת�÷�	
			return false;
	}else{
		if(!sl_checkDecimal(theform.peoplenum,"<%=LocalUtilis.language("class.transFee",clientLocale)%> ", 13,3,0))//ת��������	
			return false;
		theform.peoplenum1.value=sl_parseFloat(theform.peoplenum.value);
	}
	
	if(!sl_checkDate(document.theform.change_date_picker,"<%=LocalUtilis.language("class.transDate",clientLocale)%> "))	return false;//ת������
		syncDatePicker(document.theform.change_date_picker, document.theform.change_date);
	if(!sl_checkDate(document.theform.change_qs_date_picker,"<%=LocalUtilis.language("class.changeQSDate",clientLocale)%> "))	return false; //Э��ǩ������ 
	syncDatePicker(document.theform.change_qs_date_picker, document.theform.change_qs_date);	
		
	if(!sl_checkChoice(theform.trans_type,"<%=LocalUtilis.language("class.transType",clientLocale)%> "))//ת�����
		return false;
	if(!sl_check(theform.Summary,"<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ",200,0))//��ע
		return false;
	
	if(theform.fx_change_flag.checked)
		theform.fx_change_flag.value=2;
		
	if(document.theform.fx_change_flag.checked)
		document.theform.fx_change_flag.value="1";		
	if(!sl_checkChoice(theform.sy_change_flag,"<%=LocalUtilis.language("message.syChangeFlag",clientLocale)%> "))//ת������
			return false;
	return sl_check_update();
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
			<!--start ת�÷���Ϣ-->
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--��Ʒѡ��-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="product_id" class=productname onChange="javascript:selectOthers();">
						<%=Argument.getProductListOptions(new Integer(1), product_id, "", input_operatorCode, 2)%>
					</select>
				</td>
				<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
				<td>
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="contract_bh"  onChange="javascript:selectOthers();">
						<%=contract_bh_list%>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4"><b><%=LocalUtilis.language("menu.fromCustomerInfo",clientLocale)%> </b></td><!--ԭ��������Ϣ-->
			</tr>
			<tr>
				<td align="right" ><%=LocalUtilis.language("message.custInfo",clientLocale)%> :</td><!--��������Ϣ-->
				<td colspan="3">
					<select size="1" onkeydown="javascript:nextKeyPress(this)" name="serial_no" onChange="javascript:selectOthers();">
						<%=from_cust_id_list%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.moneyType2",clientLocale)%> :</td><!--��ת�÷ݶ�-->
				<td>
					<input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" name="moneytype2" size="25" value="<%=Utility.trimNull(Format.formatMoney(moneytype2))%>">
				</td>
			</tr>
			<tr>
				<td ><b><%=LocalUtilis.language("menu.assigneeInfo",clientLocale)%> </b></td><!--���÷���Ϣ-->
				<td><button type="button"  class="xpbutton3"  accessKey=e name="btnEdit" title="<%=LocalUtilis.language("message.customerInfomation",clientLocale)%> " onclick="javascript:getTransactionCustomer('customer','<%=readonly%>');"><%=strButton%></button></td>
				<!--�ͻ���Ϣ-->
			<tr>
			<!--end ת�÷���Ϣ-->		
			<tr>
				<td align="right"><%=LocalUtilis.language("class.exchangeType",clientLocale)%> :</td><!--ת�÷�ʽ-->
				<td width="35%">
					<select size="1" onchange="javascript:exchage()" onkeydown="javascript:nextKeyPress(this)" name="exchangetype" style="width: 150">
						<%=Argument.getDictParamOptions_intrust(1115,null)%>
					</select>
				</td>
				<td align="right" width="15%"><%=LocalUtilis.language("class.contractSum",clientLocale)%> :</td><!--ת�÷ݶ�-->
				<td width="35%">
					<input onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,contractsum_cn)" name="contractsum" size="25" value=""><span id="contractsum_cn" class="span">&nbsp;<%=LocalUtilis.language("message.10000",clientLocale)%> </span>
				</td><!--��-->
			</tr>
	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.changeDate4",clientLocale)%> :</td><!--ת����Ч����-->
				<td><INPUT TYPE="text" NAME="change_date_picker"  class=selecttext  value="<%=Format.formatDateLine(Utility.getCurrentDate())%>">
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.change_date_picker,theform.change_date_picker.value,this);" tabindex="13">
					<INPUT TYPE="hidden" NAME="change_date"   value=""></td>
				<td align="right"></td><td></td>
			</tr>
	
		</table>
		<br/>
		<table border="0" width="100%" cellspacing="1" cellpadding="4">
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.method='post';document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
							</button><!--����-->
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:location='beneficial_transfer_list.jsp';"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)
							</button><!--����-->
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
