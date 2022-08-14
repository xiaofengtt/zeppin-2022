<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.web.DocumentFile2"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//ҳ�洫�ݲ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer list_id = Utility.parseInt(request.getParameter("list_id"), null);
String contract_bh = request.getParameter("contract_bh");
//���ݱ���
Integer open_flag = new Integer(2);
Integer currency_id = new Integer(0);
Integer dz_date = new Integer(0);
Integer check_flag = new Integer(0);//��˱�־��1 δ��ˣ�2 ����ˣ�
BigDecimal nav_price = new BigDecimal(0);
BigDecimal rg_money = new BigDecimal(0);
BigDecimal old_to_money = new BigDecimal(0);
BigDecimal fee_money = new BigDecimal(0);
BigDecimal to_amount = new BigDecimal(0);
BigDecimal to_money = null;
String contractOptions = Argument.newStringBuffer().toString();
String summary = "";
String jk_type = "";
String currency_name ="";
String product_name = "";
String cust_name = "";
//��������
 input_bookCode = new Integer(1);//������ʱ����
boolean bSuccess = false;
String btnDisabled = "";
String showline = "";
//��ö���
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO md_vo = new MoneyDetailVO();
ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();
//�ύ����
DocumentFile2 file = null;
if(request.getMethod().equals("POST")){

	file = new DocumentFile2(pageContext);
	file.parseRequest();

	md_vo = new MoneyDetailVO();

	BigDecimal to_money2 = Utility.parseDecimal(file.getParameter("to_money"), null);
	fee_money = Utility.parseDecimal(file.getParameter("fee_money"), null);
	
	serial_no = Utility.parseInt(file.getParameter("serial_no"), new Integer(0));
	product_id = Utility.parseInt(file.getParameter("product_id"), new Integer(0));
	list_id = Utility.parseInt(file.getParameter("list_id"), null);
	contract_bh = file.getParameter("contract_bh");
	
	md_vo.setInput_man(input_operatorCode);
	md_vo.setBook_code(input_bookCode);
	md_vo.setProduct_id(Utility.parseInt(file.getParameter("product_id"), new Integer(0)));
	md_vo.setContract_bh(Utility.trimNull(file.getParameter("contract_bh")));
	md_vo.setList_id(Utility.parseInt(file.getParameter("list_id"), new Integer(0)));
	md_vo.setTo_money(to_money2);
	md_vo.setTo_amount(to_money2);
	md_vo.setFee_money(fee_money);
	md_vo.setJk_type(Utility.trimNull(file.getParameter("jk_type")));
	md_vo.setSummary(Utility.trimNull(file.getParameter("summary")));
	md_vo.setDz_date(Utility.parseInt(Utility.trimNull(file.getParameter("jk_date")),new Integer(0)));
	//�������޸�
	try{
		if(serial_no.intValue()>0){
			md_vo.setSerial_no(serial_no);
			moneyDetailLocal.modi(md_vo);
			if(file.uploadAttchment(new Integer(13),serial_no,"",null,null,input_operatorCode))
				bSuccess = true;
		}
		else{
			serial_no = moneyDetailLocal.append(md_vo);
			if(file.uploadAttchment(new Integer(13),serial_no,"",null,null,input_operatorCode))
				bSuccess = true;
			int tableRows = Utility.parseInt(file.getParameter("tableRows"),0);
		}
		bSuccess = true;
	}catch(Exception e){
		//window.location = "pay_list.jsp?q_check_flag=1&product_id="+product_id;
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		bSuccess = false;
	}
}
//��ѯ�༭��Ϣ
List attachmentList = new ArrayList();
if(serial_no.intValue()>0){
	md_vo = new MoneyDetailVO();
	md_vo.setSerial_no(serial_no);

	List md_list = moneyDetailLocal.load(md_vo);
	if(md_list.size()>0){
			Map md_map = (Map)md_list.get(0);

			product_id = Utility.parseInt(Utility.trimNull(md_map.get("PRODUCT_ID")),new Integer(0));
			contract_bh = Utility.trimNull(md_map.get("CONTRACT_BH"));
			list_id = Utility.parseInt(Utility.trimNull(md_map.get("LIST_ID")),new Integer(0));
			to_money = Utility.parseDecimal(Utility.trimNull(md_map.get("TO_MONEY")),new BigDecimal(0));
			fee_money = Utility.parseDecimal(Utility.trimNull(md_map.get("FEE_MONEY")),new BigDecimal(0));
			jk_type = Utility.trimNull(md_map.get("JK_TYPE"));
			dz_date = Utility.parseInt(Utility.trimNull(md_map.get("DZ_DATE")),new Integer(0));
			summary = Utility.trimNull(md_map.get("SUMMARY"));
			check_flag = Utility.parseInt(Utility.trimNull(md_map.get("CHECK_FLAG")),new Integer(0));
			cust_name = Utility.trimNull(md_map.get("CUST_NAME"));
			
			if(check_flag.intValue()==2){
				btnDisabled = "disabled";
			}
	}
	showline = "readonly class='edline' ";	
	
	AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
	AttachmentVO attachment_vo = new AttachmentVO();
	attachment_vo.setDf_talbe_id(new Integer(13));
	attachment_vo.setDf_serial_no(serial_no);
	
	attachmentList = attachmentLocal.load(attachment_vo);
}
//���ò�Ʒ ��ͬ �ɿ�
if(product_id.intValue()>0){	
	p_vo = new ProductVO();
	p_vo.setProduct_id(product_id);	
	List product_list = product.load(p_vo);
	
	if(product_list.size()>0){
		Map product_map = (Map)product_list.get(0);

		product_name = Utility.trimNull(product_map.get("PRODUCT_NAME"));
		currency_id = Utility.parseInt(Utility.trimNull(product_map.get("CURRENCY_ID")),new Integer(0));
		currency_name = Argument.getCurrencyName1(Utility.trimNull(product_map.get("CURRENCY_ID")));
		nav_price = Utility.parseDecimal(Utility.trimNull(product_map.get("NAV_PRICE")),new BigDecimal(0));
		open_flag = Utility.parseInt(Utility.trimNull(product_map.get("OPEN_FLAG")),new Integer(0));

		if(contract_bh != null && contract_bh != ""){
			md_vo = new MoneyDetailVO();
			md_vo.setProduct_id(product_id);
			md_vo.setContract_bh(contract_bh);
			Object[] ret = moneyDetailLocal.queryContractJkBase(md_vo);

			rg_money = (BigDecimal)ret[1];
			old_to_money = (BigDecimal)ret[2];

			if(old_to_money!=null && old_to_money!=null)
				to_amount = rg_money.subtract(old_to_money);
		}

		contractOptions = Argument.getUnJkContractOptions(product_id, contract_bh);		
	}
}

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.paymentManagermentNew",clientLocale)%> </title><!--�ɿ��������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
var n = 1;
/* ��Ʒ��� */
function selectProductItem(){
	window.location = 'pay_action.jsp?product_id='+document.getElementsByName('product_id')[0].value;
}
/* ��ͬ��ű�� */
function selectContractbhItem(){
	var url = 'pay_action.jsp?product_id='+document.getElementsByName('product_id')[0].value+'&contract_bh='+document.getElementsByName('contract_bh')[0].value;
	window.location = url;
}
/* �ɿ��˼����:���*/
function selectListIDItem(){
	window.location = 'pay_action.jsp?product_id='+document.getElementsByName('product_id')[0].value+'&contract_bh='+document.getElementsByName('contract_bh')[0].value+'&list_id='+document.getElementsByName('list_id')[0].value;
}

function showCnMoney(value){
	temp = value;
	to_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney2(value){
	temp = value;
	fee_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function computeMonut(ce){
	if (event.keyCode == 13){
		if(document.theform.nav_price.value != 0 ){
			var v_to_money = document.getElementsByName('to_money')[0].value;
			var v_nav_price = document.getElementsByName('nav_price')[0].value;
			document.getElementsByName('to_amount')[0].value= (v_to_money/ v_nav_price);
		}
	}
}
/*���淽��*/
function saveAction(){
	if(document.theform.onsubmit()){ 
		disableAllBtn(true);document.theform.submit();
	}
}

function validateForm(form){

	var oTrArr = document.getElementById("jkTable").rows;
	document.theform.tableRows.value = oTrArr.length;
	for(i=0;i<oTrArr.length;i++){
	
		if(oTrArr[i].id != null && oTrArr[i].id != ""){
			idStr = oTrArr[i].id;
			idNum = idStr.substring(idStr.lastIndexOf("_")+1,idStr.length);
			var jk_money = document.getElementById("jk_money_"+idNum);
			var jk_date = document.getElementById("jk_date_"+idNum);
			if(!sl_checkDecimal(jk_money, "<%=LocalUtilis.language("class.toMoney3",clientLocale)%> ", 13, 3, 1)) 
				return false;//�ɿ���
			if(!sl_checkNum(jk_date,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> ",8,1))
				return false;
		}
	}

	var rg_money=document.theform.rg_money.value;
	var to_money=document.theform.to_money.value;
	var old_to_money = document.theform.old_to_money.value;
	var serial_no = document.theform.serial_no.value;

	if((serial_no==0)&&(!sl_checkChoice(form.product_id, "<%=LocalUtilis.language("class.productName",clientLocale)%> ")))	return false;//��Ʒ����
	if((serial_no==0)&&(!sl_checkChoice(form.list_id, '<%=LocalUtilis.language("class.listID",clientLocale)%> ')))	return false;//�ɿ��˼����
	if((serial_no==0)&&(!sl_checkChoice(form.contract_bh, "<%=LocalUtilis.language("class.contractID",clientLocale)%> ")))	return false;//��ͬ���
	if(!sl_checkDecimal(form.to_money, "<%=LocalUtilis.language("class.toMoney3",clientLocale)%> ", 13, 3, 1))	return false;//�ɿ���
	if(form.open_flag.value==1){
		if(!sl_checkDecimal(form.fee_money, "<%=LocalUtilis.language("class.feeAmount2",clientLocale)%> ", 13, 3, 0))	return false;//���ý��
	}
	if(!sl_checkChoice(form.jk_type, "<%=LocalUtilis.language("class.feeType",clientLocale)%> "))	return false;//�ɿʽ
	if(!sl_checkDate(form.jk_date_picker,"<%=LocalUtilis.language("class.dzDate",clientLocale)%> "))	return false;//�ɿ�����
	syncDatePicker(form.jk_date_picker, form.jk_date);
	
	var total_money = 0;
	<%if(serial_no.intValue() > 0){%>
		total_money = sl_parseFloat(old_to_money);
	<%}else{%>
		total_money = sl_parseFloat(old_to_money) + sl_parseFloat(to_money);
	<%}%>
	//�ɿ���//����//�Ϲ����//ȷ��Ҫ������      
	if(sl_parseFloat(total_money) > sl_parseFloat(rg_money))
	{
		sl_alert('<%=LocalUtilis.language("class.toMoney3",clientLocale)%>');
		return false;
	}
	return sl_check_update();
}

function op_return(){
	var product_id=document.getElementById("s_product_id").value;
	window.location = "pay_list.jsp?q_check_flag=1&product_id="+product_id;
}

window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var serial_no = document.theform.serial_no.value;		

	if(v_bSuccess=="true"){	
		if(serial_no==0){
			alert("<%=LocalUtilis.language("message.paymentSuccessful",clientLocale)%> ��");//�ɿ�ɹ�
		}	
		else{
			alert("<%=LocalUtilis.language("message.editedSuccessfuly",clientLocale)%> ��");//�༭�ɹ�
		}
		op_return();
	}
}

/*
 *��Ӹ���	
 */
function addline()
{
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n;
	newline.insertCell().innerHTML="<input type='file' name=file_info " + n + " size='60'><button type='button'  onclick='javascript:removeline(this)'><%=LocalUtilis.language("messgae.remove",clientLocale)%> </button>";//�Ƴ�
}


/*
 *ɾ������	
 */
function removeline(obj)
{
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode;
	objTable.lastChild.removeChild(objSourceRow);
}

function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>��'))
	{
		
		var updatehref = 'attach_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		if(obj!=null)
			obj.style.display="none";
	}
}

function InsertRow(){
	var oTrArr = document.getElementById("jkTable").rows;
	var curRowCount;
	document.getElementById("maxID").value = Number(document.getElementById("maxID").value)+1;
	curRowCount = document.getElementById("maxID").value;

	var oTr = document.getElementById("jkTable").insertRow(oTrArr.length);//�ڵ�ǰ������ĩβ-bottom��֮ǰ����һ��
//���ò����е���ʽ
	oTr.setAttribute("id","tr_"+curRowCount);
//	oTr.setAttribute("className","tr0");
	
	var oTdTyp = oTr.insertCell(0);//Tr�еĵ�1����Ԫ��-AATypeno�����
	var oTdDet = oTr.insertCell(1);//Tr�еĵ�2����Ԫ��-AADetailno�����
	var oTdCrB = oTr.insertCell(2);//Tr�еĵ�3����Ԫ��-AAOccurBalance�����
	var oTdDrB = oTr.insertCell(3);//Tr�еĵ�4����Ԫ��-AaOccurAmount�����

	
	//Ϊ5����Ԫ����������
	oTdTyp.setAttribute("align","right");
	oTdTyp.setAttribute("with","60");
	oTdDet.setAttribute("align","left");
	oTdDet.setAttribute("with","880");
	oTdCrB.setAttribute("align","right");
	oTdCrB.setAttribute("width","");
	oTdDrB.setAttribute("align","left");
	oTdDrB.setAttribute("width","");

	
	//д�������Ԫ���ڲ��������
	oTdTyp.innerHTML = "�ɿ���:";
	oTdDet.innerHTML = "<input type='text' id='jk_money_"+curRowCount+"' name=jk_money_"+curRowCount+"' size='25' value='' onkeydown='javascript:nextKeyPress(this)';>";
	oTdCrB.innerHTML = "�ɿ�����:";
	oTdDrB.innerHTML = "<INPUT TYPE='text' id='jk_date_"+curRowCount+"' NAME='jk_date_"+curRowCount+"' value='' size='25'>"
					    +"<a id=\"remove_"+curRowCount+"\" name=\"remove_"+curRowCount+"\" href=\"javascript:removeRow('tr_" + curRowCount + "');\" onkeydown=\"javascript:nextKeyPress(this);\"><img src=\"/images/dev_remove.gif\" width=\"24\" height=\"24\" title=\"ɾ��\"></a>";
}

//ɾ����
function removeRow(ObjID){
	var oTr = document.getElementById(ObjID);
	var oParent = oTr.parentNode;
	oParent.removeChild(oTr);
}
</script>
</head>
<BODY class="BODY body-nox">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="pay_action.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="open_flag" value="<%=open_flag%>" /> 
<input type="hidden" name="old_to_money" value="<%=old_to_money%>" /> 
<input type="hidden" name="serial_no" value="<%=serial_no%>" /> 
<input type="hidden" id="s_product_id" value="<%=product_id%>" /> 
<input type="hidden" id="maxID" name="maxID" value="">
<input type="hidden" name="tableRows" value="">

<table style="width: 100%">
<tr>
				<td colspan="3" class="page-title">
					<!--���۹���--><!--�ɿ�-->
                    <font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>>><%=LocalUtilis.language("message.payment",clientLocale)%> </b></font>
				</td>
			</tr>
			<tr>
			<td>
			<div  class="btn-wrapper">
					<font size=3>���Ӷ�νɿ�:<a href="javascript:InsertRow();"><img src="/images/dev_add.gif" width="24" height="24" title="����"></a>
				</font></div></td>
			</tr>
</table>
<br/>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
	<TR>
		<TD >
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
			
			
			
			<tr><td><br/></td></tr>
			
			<% if(serial_no.intValue()==0){%>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
					<td><select size="1"  name="product_id" onchange="javascript:selectProductItem();" onkeydown="javascript:nextKeyPress(this)" class="productname"> 
							<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,16)%>
					</select> </td>
					<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
					<td><select size="1"  name="contract_bh" onchange="javascript:selectContractbhItem();" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 240px">
							<%=contractOptions%>
					</select></td>
				</tr>
			<%}else{%>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
					<td><input readonly class="edline" name="product_name" size="40" onkeydown="javascript:nextKeyPress(this)" value="<%=product_name%>"></td>
					<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
					<td><input readonly class="edline" name="contract_bh" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=contract_bh%>"></td>
				</tr>
				<%}%>
			<TR>
			<% if(serial_no.intValue()==0){%>
				<td align="right"><%=LocalUtilis.language("class.listID",clientLocale)%> :</td><!--�ɿ��˼����-->
				<td><select size="1"  name="list_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectListIDItem();" style="WIDTH: 300px">
						<%=Argument.getBenifitorOptions(input_bookCode,product_id,contract_bh,list_id)%>
					</select></td>
			<%}else{%>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
				<td><input readonly class="edline" name="cust_name" size="40" onkeydown="javascript:nextKeyPress(this)" value="<%=cust_name%>"></td>
			<%}%>
				<td align="right"><%=LocalUtilis.language("class.rg_money",clientLocale)%> :</td><!--�Ϲ����-->
				<td><input readonly class="edline" name="rg_money" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(Format.formatMoney(rg_money))%>"></td>
			</TR>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.navPrice",clientLocale)%> :</td><!--��ǰ��ֵ-->
				<td><input readonly class="edline" name="nav_price" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%if(nav_price.intValue()>0) {out.print(Format.formatMoney0(nav_price.doubleValue()));}%>"></td>
				
				<td align="right"><%=LocalUtilis.language("class.currency_name",clientLocale)%> :</td><!--����-->
				<td><input readonly class="edline" name="" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=currency_name%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.feeType",clientLocale)%> :</td><!--�ɿʽ-->
				<td ><select size="1" name="jk_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
						<%=Argument.getJkTypeOptions(jk_type)%>
					</select>
				</td>
				<td align="right" width="15%"><%if(open_flag.intValue()==1){%><%=LocalUtilis.language("class.feeAmount2",clientLocale)%> :<%}%></td><!--���ý��-->
				<td width="40%"><%if(open_flag.intValue()==1){%>
						<input name="fee_money" size="20" onkeydown="javascript:computeMonut(this);nextKeyPress(this);" onkeyup="javascript:showCnMoney2(this.value)" value="<%=Utility.trimNull(fee_money)%>" >
						<span id="fee_money_cn" class="span">&nbsp;</span><%}%>
				</td>
			</tr>
			<tr>
				<td align="right" width="15%"><%=LocalUtilis.language("class.toMoney3",clientLocale)%> :</td><!--�ɿ���-->
				<td width="40%">
						<input name="to_money" size="20" onkeydown="javascript:computeMonut(this);nextKeyPress(this);" onkeyup="javascript:showCnMoney(this.value)" value="<%=Utility.trimNull(to_money)%>">
						<span id="to_money_cn" class="span">&nbsp;</span>
				</td>
				<td align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</td><!--�ɿ�����-->
				<td>
					<INPUT TYPE="text" NAME="jk_date_picker" class=selecttext
					<%if(dz_date.intValue()>0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(dz_date)%>"<%}%>  />
					<INPUT TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.jk_date_picker,theform.jk_date_picker.value,this);" tabindex="14">
					<INPUT TYPE="hidden" NAME="jk_date" value="<%=dz_date%>">
			   </td>
			</tr>	
			<tr>
				<td align="right" width="10%"></td>
				<td width="35%"><input style="display:none" name="to_amount" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(to_amount)%>"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> :</td><!--��ע-->
				<td colspan="3"><textarea rows="2" name="summary" cols="70" onkeydown="javascript:nextKeyPress(this)" ><%=summary %></textarea></td>
			</tr>
			<tr id="reader1">
          	<td align="right"><%if(attachmentList!=null&&attachmentList.size()> 0){%>����:<%}%></td>
            <td colspan="3">
			<%
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
            while(attachment_it.hasNext()){
            	attachment_map = (HashMap)attachment_it.next();
            %>
            	<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
            	<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%>" ><%=Utility.trimNull(attachment_map.get("ORIGIN_NAME"))%></a>
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave" 
            		onclick="javascript:confirmRemoveAttach(divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>,'<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">ɾ��</button>
            	</div><br>
			<%}	%>
            </td>	
         </tr> 	
		<tr id="reader2" style="display:">
          	<td class="paramTitle"align="right"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%>:&nbsp;&nbsp;</td><!-- ��Ӹ��� -->
            <td colspan="3">          	
            	<table id="test" width="100%">
            		<tr >
            			<td>
		            	<input type="file" name="file_info" size="60">&nbsp;
		            	<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%> " src="<%=request.getContextPath()%>/images/minihelp.gif"><!--ѡ�񸽼��ļ�-->
		            	</td>
		            </tr>
		        </table>
		        <button type="button"   onclick="addline()"><%=LocalUtilis.language("class.moreMccessories",clientLocale)%> <!--�����˴���Ӹ��฽��--></button>
            </td>
        </tr>	
	</TABLE>
	<TABLE id="jkTable" cellSpacing=0 cellPadding=4 width="100%" border=0>

	</TABLE>
	<br>
	<table border="0" width="100%">
		<tr>
			<td align="right">
			<!--����-->
            <button type="button"  class="xpbutton3" accessKey=s  <%=btnDisabled%> id="btnSave" name="btnSave" onclick="javascript:saveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
			&nbsp;&nbsp;
			<!--����-->
            <button type="button"  class="xpbutton3"  accessKey=b id="btnRepeat" name="btnCancel" onclick="javascript:disableAllBtn(true);op_return();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
			&nbsp;&nbsp;
			</td>
		</tr>
	</table>
	</TD>
</TABLE>
</form>
<%//@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
moneyDetailLocal.remove();
product.remove();
 %>
