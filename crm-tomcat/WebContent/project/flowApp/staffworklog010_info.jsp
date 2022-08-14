<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ʼ��������ֵ
String interfaceType_code = "StaffLog010";
Integer identity_code = Utility.parseInt(request.getParameter("object_id"),new Integer("0"));
Integer object_id = Utility.parseInt(request.getParameter("object_id"), new Integer(0));
String object_type = Utility.trimNull(request.getParameter("object_type"));
String edit_right = Utility.trimNull(request.getParameter("edit_right"));
String showFlag = Utility.trimNull(request.getParameter("ShowFlag"));
String logType = Utility.trimNull(request.getParameter("logType"));
ConfigLocal configLocal = EJBFactory.getConfig();
if("1".equals(logType)){
	interfaceType_code = "StaffLog010";
}else if("2".equals(logType)){
	interfaceType_code = "StaffLog020";
}else if("3".equals(logType)){
	interfaceType_code = "StaffLog030";
}else if("4".equals(logType)){
	interfaceType_code = "StaffLog040";
}else if("5".equals(logType)){
	interfaceType_code = "StaffLog050";
}
//ͨ��ҳ�������Ҹñ���Ӧ�������ֶ�
List catalogList = ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;
//��ȡ����ID
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));

List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//��ȡҳ�����е���ЧԪ�ؼ���
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//��ȡҳ���������ص���ЧԪ�ؼ���
if(identity_code.intValue() != 0) fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"INPUT_MAN","READONLY_FLAG","2");
if(identity_code.intValue() != 0) fieldAllList=ConfigCheck.setFieldValue(fieldAllList,"INPUT_MAN","UPDATE_FLAG","2");
if(!edit_right.equals("yes")){
	fieldShowList = ConfigCheck.setAllField(fieldShowList,"READONLY_FLAG","2");
}
if(request.getMethod().equals("POST")){
	if(identity_code.intValue() == 0){//����
		ConfigUtil.addInfo(table_code,fieldAllList,request);//����ҳ������
	}else{
		ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//�޸�ҳ������
	}
	bSuccess = true;
}

List modShowDataList = new ArrayList();//�޸�ҳ����ʾ����
if(identity_code.intValue() != 0){//�޸���ʾȡֵ
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
}
%>
<html>
<head>
<%if(identity_code.intValue() ==0){%>
<title>��������</title>
<%}else{%>
<title>�޸�����</title>
<%}%>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	alert("������־����ɹ���");
	window.returnValue = 1;
	window.close();
<%}%>

//----------�ύʱ������֤------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	
	if(!(document.theform.DATE ==undefined) && (<%=identity_code.intValue()==0%>)){//����ʱ��ֵ
		document.theform.DATE.value='<%=ConfigUtil.getToday("","yyyy-MM-dd")%>';
	}
	if(!(document.theform.INPUT_MAN ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_MAN.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.REIMBURSE_MAN ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.REIMBURSE_MAN.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.IF_COMPLETE ==undefined) && (<%=identity_code.intValue()!=0%>)){
		document.theform.IF_COMPLETE.value="2";
	}
<% if("1".equals(logType)){%>

	var g = /[^\x00-\xff]{8,}/; 
	var workend = document.theform.WORK_END.value; 
	var worknextday = document.theform.WORK_NEXTDAY.value; 
	if(g.test(workend)){
	}else{
		alert("����д����8�������ַ�!");
		document.theform.WORK_END.focus() ;
		return false;
	}
	if(g.test(worknextday)){
	}else{
		alert("����д����8�������ַ�!");
		document.theform.WORK_NEXTDAY.focus();
		return false;
	} 
		//if(document.theform.WORK_END.value)
<%}%>
	document.theform.submit();
}

</script>

<%
	//-----------------------��ʼ������-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>
<input name="edit_right" type="hidden" value="<%=edit_right%>" />
<input name="object_type" type="hidden" value="<%=object_type%>" />
<input name="object_id" type="hidden" value="<%=object_id%>" />
<input name="logType" type="hidden" value="<%=logType%>" />

<%
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title">
				<font color="#215dc6"><b>������־</b></font>
			</td>
		</tr>
</table>
<br/>
<%
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("modShowDataList",modShowDataList);
%>
<jsp:include page="/system/config/basicdetail_show.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" >
	<tr>
		<td align="right">
		<%if(edit_right.equals("yes")){%>
		<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">����(<u>S</u>)</button>
		&nbsp;&nbsp;
		<%} %>
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>
