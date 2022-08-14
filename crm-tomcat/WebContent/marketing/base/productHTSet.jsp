<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.cont.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
String product_name = Utility.trimNull(request.getParameter("product_name")); 
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String[] service_man =request.getParameterValues("service_man");
String[] flag_s =request.getParameterValues("ht_status");
String[] summary_s = request.getParameterValues("summary");

ContractManagementLocal contractM = EJBFactory.getContractManagement();
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	int tableRoles = Utility.parseInt(request.getParameter("maxID"),0);
	for(int i=1;i<=tableRoles;i++ ){
		int ht_no = Utility.parseInt(request.getParameter("ht_no_"+tableRoles),0);
		int flag = Utility.parseInt(request.getParameter("ht_status"+i),0);
		int ser = Utility.parseInt(request.getParameter("ht_status"+i),0);
		Integer serial_no = Utility.parseInt(request.getParameter("serial_no"+i),new Integer(0));
		Integer service_man_I = Utility.parseInt(service_man[i-1],new Integer(0));
		contractM.addContractStat(product_id,service_man_I,new Integer(flag),input_operatorCode,summary_s[i-1],serial_no);
		System.out.println(ht_no);
	}
	bSuccess = true;
}
List list = contractM.queryContractStat(product_id);
Map contractM_map = null;

%>
<html>
<head>
<title><%=LocalUtilis.language("menu.roleNew",clientLocale)%> </title>
<!--������ɫ-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	alert("����ɹ�");
<%}%>

function InsertRow(){
	var number = document.theform.number.value;
	var oTrArr = document.getElementById("htTable").rows;
	for(i=1;i<=number;i++){
		var curRowCount;
		document.getElementById("maxID").value = Number(document.getElementById("maxID").value)+1;
		curRowCount = document.getElementById("maxID").value;
	
		var oTr = document.getElementById("htTable").insertRow(oTrArr.length);//�ڵ�ǰ������ĩβ-bottom��֮ǰ����һ��
	//���ò����е���ʽ
		oTr.setAttribute("id","tr_"+curRowCount);
		oTr.setAttribute("className","tr0");	
		var oTdTyp = oTr.insertCell(0);//Tr�еĵ�1����Ԫ��-AATypeno�����
		var oTdDet = oTr.insertCell(1);//Tr�еĵ�2����Ԫ��-AADetailno�����
		var oTdCrB = oTr.insertCell(2);//Tr�еĵ�3����Ԫ��-AAOccurBalance�����
		var oTdDrB = oTr.insertCell(3);//Tr�еĵ�4����Ԫ��-AaOccurAmount�����	
		
		//Ϊ5����Ԫ����������
		oTdTyp.setAttribute("align","center");
		//oTdTyp.setAttribute("with","60");
		oTdDet.setAttribute("align","center");
		//oTdDet.setAttribute("with","880");
		oTdCrB.setAttribute("align","center");
		//oTdCrB.setAttribute("width","");
		oTdDrB.setAttribute("align","center");
		//oTdDrB.setAttribute("width","");
	
		
		//д�������Ԫ���ڲ��������
		oTdTyp.innerHTML = "<p>"+curRowCount+"</p><input type='hidden' id='ht_no_'"+curRowCount+" name='ht_no_'"+curRowCount+" value='"+curRowCount+"' >";
		oTdDet.innerHTML = "<input type='radio' class='flatcheckbox' id='ht_status_"+curRowCount+"' name='ht_status"+curRowCount+"' value='1' checked='checked'/>�հ�&nbsp;&nbsp;"
						  +"<input type='radio' class='flatcheckbox' id='ht_status_"+curRowCount+"' name='ht_status"+curRowCount+"' value='2'/>����ȡ&nbsp;&nbsp;"
						  +"<input type='radio' class='flatcheckbox' id='ht_status_"+curRowCount+"' name='ht_status"+curRowCount+"' value='3'/>��ǩ&nbsp;&nbsp;"
						  +"<input type='radio' class='flatcheckbox' id='ht_status_"+curRowCount+"' name='ht_status"+curRowCount+"' value='4'/>δ�ջ�&nbsp;&nbsp;"
						  +"<input type='radio' class='flatcheckbox' id='ht_status_"+curRowCount+"' name='ht_status"+curRowCount+"' value='5'/>������";
		oTdCrB.innerHTML = "<SELECT name='service_man' id='service_man_'"+curRowCount+"  class='t_aera' style='width: 120px'><%=Argument.getManager_Value(new Integer(0)).replace('"','\'')%></SELECT>";
		oTdDrB.innerHTML = "<TEXTAREA  cols='65' rows='3' name='summary'></TEXTAREA>";
	}
}

function validateForm(form)
{
	//if(!sl_checkNum(form.role_id, "<%=LocalUtilis.language("class.qRoleID",clientLocale)%> ", 3, 1))	return false;//��ɫ���


	//if(!sl_check(form.summary, "<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ", 200, 0))		return false;//��ע

	return sl_check_update();
}
function toProductList(){
	location = 'product_list.jsp';
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="productHTSet.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="maxID" name="maxID" value="<%=list.size() %>" size="5">
<input type="hidden" id="product_id" name="product_id" value="<%=product_id%>">
<input type="hidden" id="product_name" name="product_name" value="<%=product_name%>">
	
	<div align="left">
		<b><font color="red" size="3"><%=product_name %></font></b>
	</div>
	<div align=right>
		<font size=3>���κ�ͬ��:</font><input type="text" name="number" value="1" size="5">
		<a href="javascript:InsertRow();"><img src="/images/dev_add.gif" width="20" height="20" title="����"></a>
	
	</div>
	<TABLE id="htTable" id="table3" border="0" cellspacing="0" cellpadding="0" class="tablelinecolor" width="100%">
		<TR class="trh">
			<TD align="center">��ͬ��</TD>
			<TD align="center">��ͬ״̬</TD>
			<TD align="center">�ͻ�����</TD>
			<TD align="center">��ע</TD>
		</TR>
<% 	for (int i=0;i<list.size(); i++){
		contractM_map = (Map)list.get(i);
		int flag = ((Integer)contractM_map.get("FLAG")).intValue();
%>
		<TR class="tr0">
			<TD align="center"><%=Utility.trimNull(contractM_map.get("SERIAL_NO")) %><input type="hidden" name="serial_no<%=i+1 %>" value="<%=Utility.trimNull(contractM_map.get("SERIAL_NO")) %>"></TD>
			<TD align="center"><input type='radio' class='flatcheckbox' id='ht_status_<%=i+1 %>' name='ht_status<%=i+1 %>' value='1' <%if (flag==1) out.print("checked"); %>/>�հ�&nbsp;&nbsp;
				<input type='radio' class='flatcheckbox' id='ht_status_<%=i+1 %>' name='ht_status<%=i+1 %>' value='2' <%if (flag==2) out.print("checked"); %>/>����ȡ&nbsp;&nbsp;
				<input type='radio' class='flatcheckbox' id='ht_status_<%=i+1 %>' name='ht_status<%=i+1 %>' value='3' <%if (flag==3) out.print("checked"); %>/>��ǩ&nbsp;&nbsp;
				<input type='radio' class='flatcheckbox' id='ht_status_<%=i+1 %>' name='ht_status<%=i+1 %>' value='4' <%if (flag==4) out.print("checked"); %>/>δ�ջ�&nbsp;&nbsp;
				<input type='radio' class='flatcheckbox' id='ht_status_<%=i+1 %>' name='ht_status<%=i+1 %>' value='5' <%if (flag==5) out.print("checked"); %>/>������&nbsp;&nbsp;
			</TD>
			<TD align="center"><SELECT name='service_man' id='service_man_<%=i+1 %>' class='t_aera' style='width: 120px'><%=Argument.getManager_Value((Integer)contractM_map.get("SERVICE_MAN"))%></SELECT></TD>
			<TD align="center"><TEXTAREA  cols='65' rows='3' name='summary'><%=Utility.trimNull(contractM_map.get("SUMMARY")) %></TEXTAREA></TD>
		</TR>
<%	}
%>
	</TABLE>
	<br/>
	<TABLE width="99%">
		<TR>
			<TD align="right">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--����-->
				<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:toProductList();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
				&nbsp;&nbsp;<!--ȡ��-->
			</TD>
		</TR>
	</TABLE>
</form>
<%contractM.remove(); %>
</body>
</html>
