<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ʼ��������ֵ
String interfaceType_code = "staffreimbs010";
Integer identity_code = Utility.parseInt(request.getParameter("object_id"),new Integer("0"));
Integer object_id = Utility.parseInt(request.getParameter("object_id"), new Integer(0));
String object_type = Utility.trimNull(request.getParameter("object_type"));
String edit_right = Utility.trimNull(request.getParameter("edit_right"));
String showFlag = Utility.trimNull(request.getParameter("ShowFlag"));
ConfigLocal configLocal = EJBFactory.getConfig();

//ͨ��ҳ�������Ҹñ��Ӧ�������ֶ�
List catalogList = ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;
//��ȡ����ID
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));
String endDate =ConfigUtil.getDataBaseValue("end_date","TSTAFFREIMBURSESUM","serail_id=(select MAX(SERAIL_ID) from TSTAFFREIMBURSESUM where serail_id<>"+object_id+" and INPUT_MAN='"+String.valueOf(input_operatorCode)+"')");

System.out.println(endDate);

List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//��ȡҳ�����е���ЧԪ�ؼ���
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//��ȡҳ���������ص���ЧԪ�ؼ���
if(identity_code.intValue() == 0) fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"CUST_TYPE","BEHIND_ROW","1");
if(identity_code.intValue() == 0) fieldShowList=ConfigCheck.setFieldValue(fieldShowList,"CARD_TYPE","BEHIND_ROW","1");
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
<%if (bSuccess && identity_code.intValue() !=0){%>
	alert("����ɹ���");
	location.reload();
<%}else if(bSuccess && identity_code.intValue() ==0){%>
	window.returnValue = 1;
	window.close();
<%}%>

//----------�ύʱ������֤------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	//var STATE_DATE=trim(document.theform.START_DATE.value);
	//var END_DATE=trim(document.theform.END_DATE.value);
	
	//if(END_DATE<STATE_DATE){
	//	 alert("�������ڱ�����ڵ��ڿ�ʼ���ڣ�");
	//     return false;
	//}
	//if(STATE_DATE<'<%= endDate%>'){
	//	alert("���ο�ʼ���ڱ������ϴν�������֮��");
	//	return false;
	//	}
	if(!(document.theform.INPUT_TIME ==undefined) && (<%=identity_code.intValue()==0%>)){//����ʱ��ֵ
		document.theform.INPUT_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	if(!(document.theform.INPUT_MAN ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_MAN.value='<%=input_operatorCode%>';
	}
    if(!(document.theform.INPUT_DEPT==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_DEPT.value='<%=depart_id%>';
	}
	
	document.theform.submit();
}
function compute(){
	var  param=1+"@@"+<%=identity_code%>;
	var result=checkLogic("CRMBusinessCheck","getStaffreimbSum",param);
	document.theform.MONEY_SUM.value=result;
}

</script>

<%
	//-----------------------��ʼ������-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>
<input name="edit_right" type="hidden" value="<%=edit_right%>" />
<input name="object_type" type="hidden" value="<%=object_type%>" />
<input name="object_id" type="hidden" value="<%=object_id%>" />

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
				<font color="#215dc6"><b>���ñ���������Ϣ  <FONT color="red">���ڱ���ʱ��<%=endDate %></FONT></b></font>
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

<table border="0" width="100%">
	<tr>
		<td align="right">
		<%if(edit_right.equals("yes")){%>
		<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">����(<u>S</u>)</button>
		&nbsp;&nbsp;
		<%} %>
		<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>

