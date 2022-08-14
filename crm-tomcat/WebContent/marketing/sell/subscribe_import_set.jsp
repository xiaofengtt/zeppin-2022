<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
String column=Utility.trimNull(request.getParameter("column"));
String[] col = column.split("\\$");	
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.addActivity",clientLocale)%> </title>
<!--���������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
	var arr = document.getElementsByTagName("INPUT");
	<%for(int i=0;i<col.length;i++){%>
		for(var j=0; j<arr.length; j++){
			if(arr[j].value=="<%=col[i]%>"){
				arr[j].checked=true;
			}
		}
	<%}%>
}

/*����*/
function SaveAction(){
	var arr = document.getElementsByTagName("INPUT");
	var list = [];
	for (var i=0; i<arr.length; i++){
		if (arr[i].checked){
			list.push(arr[i].value);
		}
	}
	window.returnValue = list;
	window.close();
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="subscribe_import_set.jsp" >
<div align="left" class="page-title">
	<font color="#215dc6"><b>�༭��ʾ�� </b></font>
</div>

<div align="center">
	<table border="0" width="98%"  cellspacing="0" cellpadding="4" class="table-popup">
		<tr>
			<td  align="left" width="40%"><input type="checkbox" name="contract_sign_date" value="��ͬǩ������">��ͬǩ������</td>
			<td  align="left" width="40%"><input type="checkbox" name="pay_date" value="�ɿ�����">�ɿ�����</td>
		</tr>		
		<tr>
			<td  align="left"><input type="checkbox" name="contract_end_date" value="��ͬ��ֹ����">��ͬ��ֹ����</td>
			<td  align="left"><input type="checkbox" name="contract_deadline" value="��ͬ����">��ͬ����</td>				
		</tr>		
		<tr>
			<td  align="left"><input type="checkbox" name="contract_money" value="��ͬ���">��ͬ���</td>
			<td  align="left"><input type="checkbox" name="cust_name" value="ί����">ί����</td>	
		</tr>
		
		<tr>
			<td  align="left"><input type="checkbox" name="cust_type" value="ί��������">ί��������</td>
			<td  align="left"><input type="checkbox" name="cust_contact_type" value="ί������ϵ��ʽ">ί������ϵ��ʽ</td>	
		</tr>	
		<tr>
			<td align="left"><input type="checkbox" name="cust_address" value="ί���˵�ַ">ί���˵�ַ</td>
			<td  align="left"><input type="checkbox" name="cust_post_code" value="ί�����ʱ�">ί�����ʱ�</td>
		</tr>	
		<tr>
			<td align="left"><input type="checkbox" name="cust_certificate" value="ί����֤������">ί����֤������</td>
			<td  align="left"><input type="checkbox" name="cust_certificate_num" value="ί����֤�����">ί����֤�����</td>
		</tr>
		<tr>
			<td align="left"><input type="checkbox" name="cust_legal_representative" value="ί���˷��˴���">ί���˷��˴���</td>
			<td align="left"><input type="checkbox" name="cust_line_tel" value="ί���˹̶��绰">ί���˹̶��绰</td>
		</tr>					
		<tr> 
			<td align="left"><input type="checkbox" name="cust_telphone" value="ί�����ֻ�">ί�����ֻ�</td>
			<td align="left"><input type="checkbox" name="cust_email" value="ί���˵����ʼ�">ί���˵����ʼ�</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary" value="������">������</td>
			<td align="left"><input type="checkbox" name="beneficiary_type" value="����������">����������</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_contact_type" value="��������ϵ��ʽ">��������ϵ��ʽ</td>
			<td align="left"><input type="checkbox" name="beneficiary_address" value="�����˵�ַ">�����˵�ַ</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_post_code" value="�������ʱ�">�������ʱ�</td>
			<td align="left"><input type="checkbox" name="beneficiary_certificate" value="������֤������">������֤������</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_certificate_num" value="������֤�����">������֤�����</td>
			<td align="left"><input type="checkbox" name="beneficiary_legal_representative" value="�����˷��˴���">�����˷��˴���</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_line_tel" value="�����˹̶��绰">�����˹̶��绰</td>
			<td align="left"><input type="checkbox" name="beneficiary_telphone" value="�������ֻ�">�������ֻ�</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_email" value="�����˵����ʼ�">�����˵����ʼ�</td>
			<td align="left"><input type="checkbox" name="bank_name" value="������������">������������</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="sub_bank_name" value="֧������">֧������</td>
			<td align="left"><input type="checkbox" name="bank_acct" value="�����˺�">�����˺�</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="acct_name" value="��������">��������</td>
			<td align="left"><input type="checkbox" name="property_category" value="�Ʋ����">�Ʋ����</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="property_name" value="�Ʋ�����">�Ʋ�����</td>
			<td align="left"><input type="checkbox" name="pay_type" value="�ɿʽ">�ɿʽ</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="subscribe_pay_type" value="�Ϲ��ѿ۽ɷ�ʽ">�Ϲ��ѿ۽ɷ�ʽ</td>
			<td align="left"><input type="checkbox" name="beneficiary_level" value="�������ȼ���">�������ȼ���</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="income_level" value="���漶��">���漶��</td>
			<td align="left"><input type="checkbox" name="cust_reserve_address" value="ί���˱��õ�ַ">ί���˱��õ�ַ</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="cust_reserve_post_code" value="ί���˱����ʱ�">ί���˱����ʱ�</td>
			<td align="left"><input type="checkbox" name="new_contract_no" value="�º�ͬ���">�º�ͬ���</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="product_periods" value="��Ʒ����">��Ʒ����</td>
			<td align="left"><input type="checkbox" name="channel_type" value="�������">�������</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="channel_name" value="��������">��������</td>
			<td align="left"><input type="checkbox" name="channel_reserve" value="������ע">������ע</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="province" value="ʡ">ʡ</td>
			<td align="left"><input type="checkbox" name="city" value="��">��</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="cust_manager" value="�ͻ�����">�ͻ�����</td>
			<td align="left"><input type="checkbox" name="money_mark" value="��֤���־">��֤���־</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="channel_cooperation_type" value="����������ʽ">����������ʽ</td>
			<td align="left"><input type="checkbox" name="comment" value="��ע">��ע</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="money_origin_name" value="�ʲ���Դ">�ʲ���Դ</td>
			<td align="left"><input type="checkbox" name="sub_money_origin_name" value="�ʲ���Դϸ��">�ʲ���Դϸ��</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();">ȷ�� </button>
	&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>

</BODY>
</HTML>