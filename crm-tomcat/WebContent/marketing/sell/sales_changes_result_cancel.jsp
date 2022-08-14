<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
SalesResultForStatisticVO vo = new SalesResultForStatisticVO();
AttachmentToCrmLocal attach = EJBFactory.getAttachmentToCrm();
AttachmentVO atta_vo = new AttachmentVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer pre_product_type = Utility.parseInt(request.getParameter("pre_product_type"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
BigDecimal dz_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("dz_money")),new BigDecimal(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
String dz_time = Utility.trimNull(request.getParameter("dz_date"));
Integer is_onway = Utility.parseInt(request.getParameter("is_onway"), new Integer(0));
Integer statusDri = Utility.parseInt(request.getParameter("statusDri"),new Integer(-2));
boolean bSuccess = false;
Map map = new HashMap();
Map atta_map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	
	statusDri = Utility.parseInt(file.getParameter("statusDri"),new Integer(-2));
	
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	vo.setStatus(statusDri);
	
	local.cancelSalesResultForStatistic(vo);

	bSuccess = true;

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		vo.setPre_product_type(pre_product_type);
		List list = local.querySalesResultForStatistic(vo);
		if (list.size() > 0) map = (Map)list.get(0);

		atta_vo.setDf_talbe_id(new Integer(3));
		atta_vo.setDf_serial_no(serial_no);
		atta_vo.setInput_man(input_operatorCode);
		List atta_list = attach.load(atta_vo);
		if (atta_list.size()>0) atta_map = (Map)atta_list.get(0);
	}
}
//��������ܻ����

local.remove();
attach.remove();
%>
<html>
<head>
<title>���˴���</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self"/>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form){
	if(!sl_check(form.rg_product_name, "��Ʒ���� ", 100, 1))	return false;//��Ʒ����
	if(!sl_checkChoice(document.theform.rg_product_type,"��Ʒ���� ")) return false;//��Ʒ����
	if(!sl_check(form.rg_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//�ͻ�����
	if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")) return false;//�ͻ�����
	if (!sl_checkChoice(form.rg_service_man, "�ͻ�����")) return false;
	if (! sl_check(document.getElementById('dz_date'), "����ʱ��", 23, 1)) return false;
	if (!sl_checkDecimal(document.theform.dz_money, "���˽��", 13, 3, 1)) return false;
	if(document.theform.dz_money.value <= 0){
		sl_alert("���˽���С�ڵ����㣡");
		return false;
	}

	if (!sl_checkChoice(form.jk_type, "�ɿʽ")) return false;
	return sl_check_update();
}

function _checked(obj) {
	document.getElementById("onway_attach").style.display = obj.checked?"":"none";
}

function confirmRemoveAttach(serial_no){
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>��'))	{
		var updatehref = 'attach_crm_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		document.getElementById("divattach").style.display = "none";
		document.theform.onway_attach.style.display = "";		
	}
}
var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' class='xpbutton3' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='�Ƴ�'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}
/*�鿴��������*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

//ɾ�����ݿ������и����ķ���
function deleteDbAttachment(attachmentId,save_name){
    if (confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //ȷ��ɾ��������
		var url = "sales_changes_result_attachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name
			+"&serial_no=<%=serial_no%>&pre_product_type=<%=pre_product_type%>";
		document.getElementById("go-link").href = url;
		document.getElementById("go-link").click();	
    }
}
function op_check(s)
{
	if (s==0)
		str="��ȷ����������������";
			
	if(confirm(str)) {
		disableAllBtn(true);
		document.theform.statusDri.value=s;
		document.theform.submit();
	}
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<a id="go-link"></a>
<form name="theform" method="post" action="#"  enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="pre_money" value="<%=pre_money%>">
<input type=hidden name="rg_money" value="<%=rg_money%>">
<input type=hidden name="statusDri" value="-2">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>���˴���</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right"><font color="red">*</font>��Ʒ����:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="rg_product_name" class="edline" size="30" value="<%=Utility.trimNull(map.get("RG_PRODUCT_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>��Ʒ����:</td>
		<td align="left">
			<select name="rg_product_type" disabled="disabled" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getSalecChangesResultProductTypeOptions(Utility.trimNull(map.get("PRE_PRODUCT_TYPE"))) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>�ͻ�����:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="rg_cust_name" class="edline" size="30" value="<%=Utility.trimNull(map.get("RG_CUST_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><font color="red">*</font>�ͻ�����:</td>
		<td width="25%">
			<select name="rg_cust_type" disabled="disabled" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">��ѡ��</option>
				<option value="1" <%=Utility.parseInt(Utility.trimNull(map.get("RG_CUST_TYPE")), 0) == 1 ? "selected" : ""%>>����</option>
				<option value="2" <%=Utility.parseInt(Utility.trimNull(map.get("RG_CUST_TYPE")), 0) == 2 ? "selected" : ""%>>����</option>
			</select>
			<input readonly="readonly" class="edline" name="input_value2" size="27" style="display: none;" id="customerType">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>�ͻ�����:</td>
		<td align="left">
			<select name="rg_service_man" disabled="disabled" style="width: 147px">
				<%=Argument.getManager_Value(Utility.parseInt(Utility.trimNull(map.get("RG_SERVICE_MAN")),null)) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>����ʱ��:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="dz_date" id="dz_date" size="30" onclick="javascript:setday(this);" readOnly value="<%=Utility.trimNull(map.get("DZ_TIME"))%>"/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>���˽��:</td>
		<td align="left">
			<input type="text" readonly="readonly" name="dz_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,dz_money_cn)" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1") %>">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>�ɿʽ:</td>
		<td align="left">
			<select name="jk_type" disabled="disabled" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1114, Utility.trimNull(map.get("JK_TYPE"))) %>
			</select>
		</td>
	</tr>
<%
AttachmentToCrmLocal attachmentLocal = EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO = new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(1102)); // 
attachmentVO.setInput_man(input_operatorCode);
List attachmentList =attachmentLocal.load(attachmentVO);
for (int i=0; i<attachmentList.size(); i++) {
	Map attachmentMap = (Map)attachmentList.get(i);
	String attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
	String origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
   	String save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME")); %>
	<tr>
		<td align="right">����<%=i+1%>��</td>
		<td align="left">
			<a href="javascript:DownloadAttachment(<%=attachmentId%>);" style="width:200px;text-align:right"><%=origin_name%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
<%} 
attachmentLocal.remove();%>	
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton4" accessKey=s id="btnSave" name="btnSave" onclick="javascript:op_check(0);">����(<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>