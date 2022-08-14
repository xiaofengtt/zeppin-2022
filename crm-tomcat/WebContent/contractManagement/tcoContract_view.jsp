<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ȡ��Ʒ��������������ͬҪ��
TcoProductLocal tcoProductLocal = EJBFactory.getTcoProduct();
TcoProductVO tcoProductVO = new TcoProductVO();
Map map = null;
List rsList = null;
tcoProductVO.setInput_man(input_operatorCode);
tcoProductVO.setCoProduct_id(new Integer(0));
tcoProductVO.setCoProduct_name("");
rsList = tcoProductLocal.listProcAllExt(tcoProductVO);	
	
TcoContractLocal tcoContractLocal = EJBFactory.getTcoContract();
TcoContractVO tcoContractVO = new TcoContractVO();
TcoContractPayPlanLocal tcoContractPayPlanLocal = EJBFactory.getTcoContractPayPlan();
TcoContractPayPlanVO tcoContractPayPlanVO = new TcoContractPayPlanVO();
TcoContractPointsLocal tcoContractPointsLocal = EJBFactory.getTcoContractPoints();
TcoContractPointsVO tcoContractPointsVO = new TcoContractPointsVO();
Integer coContract_id=Utility.parseInt(request.getParameter("coContract_id"),new Integer(0));
tcoContractVO.setCocontract_id(coContract_id);
tcoContractVO.setInput_man(input_operatorCode);
List tcoContractList=tcoContractLocal.queryByList(tcoContractVO);
Map tcoContractMap=(Map)tcoContractList.get(0);
String cust_name=Utility.trimNull(tcoContractMap.get("CUST_NAME"));
String cocontract_sub_bh=Utility.trimNull(tcoContractMap.get("COCONTRACT_SUB_BH"));
String ht_money=Utility.trimNull(tcoContractMap.get("HT_MONEY"));
String payment_type=Utility.trimNull(tcoContractMap.get("PAYMENT_TYPE"));
String cocontract_type=Utility.trimNull(tcoContractMap.get("COCONTRACT_TYPE"));
String sign_date=Utility.trimNull(tcoContractMap.get("SIGN_DATE"));
String start_date=Utility.trimNull(tcoContractMap.get("START_DATE"));
String end_date=Utility.trimNull(tcoContractMap.get("END_DATE"));
String main_end_date=Utility.trimNull(tcoContractMap.get("MAIN_END_DATE"));

//modi 20111029
String comment = Utility.trimNull(tcoContractMap.get("SUMMARY"));

tcoContractPointsVO.setCoContract_id(coContract_id);
tcoContractPointsVO.setSubcoContract_id(new Integer(0));
tcoContractPointsVO.setInput_man(input_operatorCode);
List tcoContractPointsList=tcoContractPointsLocal.queryByList(tcoContractPointsVO);
String tcoContractPointsJson=JsonUtil.list2json(tcoContractPointsList);

tcoContractPayPlanVO.setCocontract_id(coContract_id);
tcoContractPayPlanVO.setPayPlan_id(new Integer(0));
tcoContractPayPlanVO.setPay_summary("");
tcoContractPayPlanVO.setInput_man(input_operatorCode);
List tcoContractPayPlanList=tcoContractPayPlanLocal.queryByList(tcoContractPayPlanVO);
String tcoContractPayPlanJson=JsonUtil.list2json(tcoContractPayPlanList);

//������ر���
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(coContract_id);
attachmentVO.setDf_talbe_id(new Integer(101053));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);
boolean bSuccess = false;
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	alert("�޸ĳɹ�");
	window.location.href="tcocontractset.jsp";
<%}%>
window.onload = function(){
	//initQueryCondition();
	//initQueryCondition1();
};


/*�ͻ���Ϣ*/
function getMarketingCustomer(prefix,readonly){	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');	
		
	if (v!=null){

		document.theform.cust_name.value = v[0]
		document.theform.cust_id.value = v[7];
	}	
	
	return (v != null);
}


function validateForm(form)
{
	if(!sl_checkDate(document.theform.sign_date_picker,"ǩ������"))return false;//ǩ������
	syncDatePicker(document.theform.sign_date_picker, document.theform.sign_date);	

	if(!sl_checkDate(document.theform.start_date_picker,"��ʼ����"))return false;//��ʼ����
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	
	if(!sl_checkDate(document.theform.end_date_picker,"��������"))return false;//��������
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);

	if(!sl_checkDate(document.theform.main_end_date_picker,"���ά��������"))return false;//���ά��������
	syncDatePicker(document.theform.main_end_date_picker, document.theform.main_end_date);	

	return sl_check_update();
}


function addContractPoints(){
	//document.theform.action="tco_updateContractPoints.jsp?action=add";	
	//document.theform.submit();
	var url = "<%=request.getContextPath()%>/contractManagement/tco_updateContractPoints_new.jsp?coContract_id="+<%=coContract_id%>;
	if(showModalDialog(url,'','dialogWidth:360px;dialogHeight:300px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}

function delContractPoints(obj){
	if(confirmRemove(document.theform.subcoContract_id)){
		document.theform.action="tco_updateContractPoints.jsp?action=del";	
		document.theform.submit();
	}
}
function addContractPayPlan(){
	//if(!sl_checkDate(document.theform.exp_date_picker,"Ԥ�Ƹ�������"))return false;//Ԥ�Ƹ�������
	//syncDatePicker(document.theform.exp_date_picker, document.theform.exp_date);
	//document.theform.action="tco_updatePayPlan.jsp?action=add";	
	//document.theform.submit();
	var url = "<%=request.getContextPath()%>/contractManagement/tco_updatePayPlan_new.jsp?coContract_id="+<%=coContract_id%>;
	if(showModalDialog(url,'','dialogWidth:360px;dialogHeight:400px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
function delContractPayPlan(){
	if(confirmRemove(document.theform.payPlan_id)){
		document.theform.action="tco_updatePayPlan.jsp?action=del";	
		document.theform.submit();
	}
}
function updateContractInfo(){
	validateForm(document.theform);
	document.theform.action="tco_updateContractInfo.jsp";	
	document.theform.submit();
}
function closeAction(){
	//window.location.href="tcocontractset.jsp";
	history.back();
}
//��Ӷ������,��������
function addAttachment()
{
	document.getElementById('btn_addAttachment').innerText = "������Ӹ���";
	document.getElementById("btn_saveAttachment").style.display="";
	tb = document.getElementById('attachments');   
	newRow = tb.insertRow();                                                                                                                                                                                     
	newRow.insertCell().innerHTML = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name='File' size='50' type='file'>&nbsp;&nbsp;<input type=button value='ɾ��' class='mailInput' onclick='delAttachment(this.parentElement.parentElement.rowIndex)'>"; 
}
//ɾ����ҳ�����¼ӵĸ���
function delAttachment(index)
{
	document.getElementById('attachments').deleteRow(index);
	tb = document.getElementById('attachments');
	tb.rows.length >1?document.getElementById('btn_addAttachment').innerText = "������Ӹ���":document.getElementById('btn_addAttachment').innerText = "��Ӹ���";
	tb.rows.length >1?document.getElementById("btn_saveAttachment").style.display="":document.getElementById("btn_saveAttachment").style.display="none";
} 
/*�鿴��������*/
function DownloadAttachment(attachmentId){
	var url = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;		
	window.location.href = url;	
}
//ɾ�����ݿ������и����ķ���
function deleteDbAttachment(attachmentId,save_name,coContract_id){
    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //ȷ��ɾ��������
		window.location.href="tco_updateAttachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name+"&coContract_id="+coContract_id;	
	}
}
//�����ݿ�����Ӹ���
function saveDbAttachment(){
	document.theform.action="tco_updateAttachment.jsp?action=add";	
	document.theform.submit();
}
/*��ʾ����Ǯ��*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		ht_money_cn.innerText = "(Ԫ)";
	else
		ht_money_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" ENCTYPE="multipart/form-data">
<input type="hidden" id="coProduct_ids" name="coProduct_ids"/>
<input type="hidden" id="coProduct_names" name="coProduct_names"/>
<input type="hidden" id="sub_ht_moneys" name="sub_ht_moneys"/>
<input type="hidden" id="point_summarys" name="point_summarys"/>
<input type="hidden" id="pay_nums" name="pay_nums"/>
<input type="hidden" id="pay_num_names" name="pay_num_names"/>
<input type="hidden" id="exp_dates" name="exp_dates"/>
<input type="hidden" id="pay_rates" name="pay_rates"/>
<input type="hidden" id="pay_moneys" name="pay_moneys"/>
<input type="hidden" id="pay_summarys" name="pay_summarys"/>
<input type="hidden" id="coContract_id" name="coContract_id" value="<%=coContract_id%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>��ͬ�޸�</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="68%" cellspacing="3">
	<tr>
		<td align="right">�ͻ�����: </td><!--�ͻ�����-->
		<td align="left" colspan="3">
			<input type="hidden" id="cust_id" name="cust_id"/>
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" size="60" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">
		</td>
	</tr>	
	<tr>
		<td  align="right">��ͬ���: </td><!--��ͬ���-->
		<td  align="left"><input type="text" name="cocontract_sub_bh" size="25" readonly="readonly" value="<%=cocontract_sub_bh%>" onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right">��ͬ���: </td><!--�ͻ�����-->
		<td align="left" >
			<input type="text" name="ht_money" size="25" readonly="readonly" value="<%=ht_money%>" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.ht_money,'��ͬ���',13,3,0);showCnMoney(this.value)">
			<span id="ht_money_cn" class="span">&nbsp;(Ԫ)</span>
		</td>
	</tr>
	<tr>	
		<td align="right">���ʽ: </td>
		<td align="left" >
			<select name="payment_type" style="width: 145px;" disabled="disabled">
				<%=Argument.getDictParamOptions(5003,payment_type) %>
			</select>
		</td>
		<td align="right">��ͬ����: </td>
		<td align="left" >
			<select name="cocontract_type" style="width: 145px;" disabled="disabled">
				<%=Argument.getDictParamOptions(5004,cocontract_type) %>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="right">ǩ������:</td>
		<td>
			<INPUT TYPE="text" NAME="sign_date_picker"  size="25" readonly="readonly"  class=selecttext value="<%=sign_date%>" >
			<INPUT TYPE="hidden" NAME="sign_date" id="sign_date"   value="">
		</td>
		<td align="right">��ʼ����:</td>
		<td>
			<INPUT TYPE="text" NAME="start_date_picker"  size="25" readonly="readonly" class=selecttext value="<%=start_date%>" >
			<INPUT TYPE="hidden" NAME="start_date"    value="">
		</td>
	</tr>
	<tr>	
		<td align="right">��������:</td>
		<td>
			<INPUT TYPE="text" NAME="end_date_picker" size="25" readonly="readonly"  class=selecttext value="<%=end_date%>" >
			<INPUT TYPE="hidden" NAME="end_date"   value="">
		</td>
		<td align="right">���ά��������:</td>
		<td>
			<INPUT TYPE="text" NAME="main_end_date_picker"  size="25" readonly="readonly" class=selecttext value="<%=main_end_date%>" >
			<INPUT TYPE="hidden" NAME="main_end_date"   value="">
		</td>
	</tr>
	<tr>
		<td align="right">��ע:</td>
		<td rowspan="3">
			<TEXTAREA rows="5" cols="50" name="comment"><%=comment %></TEXTAREA>
		</td>
	</tr>
</table>
<table>
	<%
		for(int i=0;i<attachmentList.size();i++){
			attachmentMap=(Map)attachmentList.get(i);
			attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
			origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
		   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
	%>
	<tr>
		<td  align="right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;���и���:</td> <!-- ���и��� �� -->
		<td  align="left" colspan="3">
			<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly"/>&nbsp;&nbsp;&nbsp;&nbsp;
           	<button type="button" class="xpbutton3"  id="btn_DownDbAttachment" name="btn_DownDbAttachment" onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></button><!-- �鿴���� -->&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
	<%	}
	 %>
</table>

<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="����"><b>��ͬҪ����ϸ</b></font></td>
				</tr>
			</table>
			<table id="table1" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">				
				<tr class=trtagsort>
					<td width="5%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.subcoContract_id);">&nbsp;</td>
					<td>��Ʒ����</td>
					<td>��ͬ���</td>
					<td>Ҫ����ϸ˵��</td>
				</tr>
				<%for(int i=0;i<tcoContractPointsList.size();i++){
					Map tcoContractPointsMap=(Map)tcoContractPointsList.get(i);
				 %>
				<tr class="tr<%=i%>">
					<td><input type="checkbox" name="subcoContract_id" value="<%=Utility.trimNull(tcoContractPointsMap.get("SUBCOCONTRACT_ID"))%>"/></td>
					<td><%=Utility.trimNull(tcoContractPointsMap.get("COPRODUCT_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractPointsMap.get("SUB_HT_MONEY"))%></td>
					<td><%=Utility.trimNull(tcoContractPointsMap.get("POINT_SUMMARY"))%></td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
	<tr><td colspan="4">&nbsp;</td></tr>
</table>

<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="����"><b>����ƻ�</b></font></td>
				</tr>
			</table>
			<table id="table2" border="0" cellspacing="1" cellpadding="2" 
					class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<td width="5%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.payPlan_id);">&nbsp;</td>
					<td>����</td>
					<td>����˵��</td>
					<td>Ԥ�Ƹ�������</td>
					<td>�������</td>
					<td>������</td>
					<td>��������˵��</td>
				</tr>
				<%for(int i=0;i<tcoContractPayPlanList.size();i++){
					Map tcoContractPayPlanMap=(Map)tcoContractPayPlanList.get(i);
				 %>
				<tr class="tr<%=i%>">
					<td><input type="checkbox" id="payPlan_id" name="payPlan_id" value="<%=Utility.trimNull(tcoContractPayPlanMap.get("PAYPLAN_ID"))%>"/></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("EXP_DATE"))%></td>
					<td><%=Utility.parseDecimal(Utility.trimNull(tcoContractPayPlanMap.get("PAY_RATE")),new BigDecimal(0)).multiply(new BigDecimal(100)).setScale(2).toString()+"%"%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_MONEY"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_SUMMARY"))%></td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="3">
	<tr>
		<td colspan="4" width="100%">
			<table  width="100%">
				<tr>
					<td  colspan="2"><font color='red' face="����"><b>��Ʊ��Ϣ</b></font></td>
				</tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" 
					class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<td>����</td>
					<td>����˵��</td>
					<td>��Ʊʱ��</td>
					<td>����</td>
					<td>�����ʺ�</td>
					<td>�ʻ�����</td>
					<td>��Ʊ���</td>
					<td>��ע</td>
				</tr>
				<%for(int i=0;i<tcoContractPayPlanList.size();i++){
					Map tcoContractPayPlanMap=(Map)tcoContractPayPlanList.get(i);
				 %>
				<tr class="tr<%=i%>">
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("PAY_NUM_NAME"))%></td>
					<td><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(tcoContractPayPlanMap.get("INVOICE_TIME")),new Integer(0)))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("BANK_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("BANK_ACCT"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("ACCT_NAME"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("INVOICE_MONEY"))%></td>
					<td><%=Utility.trimNull(tcoContractPayPlanMap.get("INVOICE_SUMMARY"))%></td>
				</tr>
				<%}%>
			</table>
		</td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="closeAction()">����</button>
		&nbsp;&nbsp;<!--�رձ�ҳ-->
		</td>
	</tr>
</table>
</form>
</body>
<%
tcoContractLocal.remove();
tcoContractPayPlanLocal.remove();
tcoContractPointsLocal.remove();
 %>
</html>
