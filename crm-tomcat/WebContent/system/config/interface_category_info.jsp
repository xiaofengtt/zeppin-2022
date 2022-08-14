<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.Timestamp" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ʼ��������ֵ
Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
String table_code = Utility.trimNull(request.getParameter("table_code"));
String interfaceType_codeone = Utility.trimNull(request.getParameter("interfacetype_codeone"));
ConfigLocal configLocal = EJBFactory.getConfig();

String form_code = Utility.trimNull(request.getParameter("region_code"));
String form_name = Utility.trimNull(request.getParameter("region_name"),"");


String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_codeone));
//ͨ��ҳ�������Ҹñ���Ӧ�������ֶ�
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_codeone);
boolean bSuccess = false;

List modShowDataList = new ArrayList();//�޸�ҳ����ʾ����
List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_codeone,identity_code,"1");//��ȡҳ�����е���ЧԪ�ؼ���
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_codeone,identity_code,"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_codeone,identity_code,"3");//��ȡҳ���������ص���ЧԪ�ؼ���

//�޸���ʾȡֵ
if(identity_code.intValue() != 0){
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
}

if(request.getMethod().equals("POST")){
	if(identity_code.intValue() == 0){//����
		ConfigUtil.addInfo(table_code,fieldAllList,request);//����ҳ������
	}else{
		ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//�޸�ҳ������
	}
	bSuccess = true;
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
	window.returnValue = 1;
	window.close();
<%}%>

//------------����id��nameֵ�Զ�����----------------
function interfaceTypeIdOnchange(returnValue){
	var fieldValue=returnValue.split(",");
	var fieldID=fieldValue[0];
	var fieldName=fieldValue[1];
	var selectName =document.getElementsByName(fieldID)[0].options[document.getElementsByName(fieldID)[0].selectedIndex].text;
	document.getElementsByName(fieldName)[0].value =selectName;
}

//-------------------�ύ--------------------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	if(!(document.theform.INPUT_TIME ==undefined) && (<%=identity_code.intValue() == 0%>)){
		document.theform.INPUT_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	if(!(document.theform.INPUT_USER ==undefined) && (<%=identity_code.intValue() == 0%>)){
		document.theform.INPUT_USER.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.INPUT_DEPT ==undefined) && (<%=identity_code.intValue() == 0%>)){
		document.theform.INPUT_DEPT.value='<%=depart_id%>';
	}
	if(!(document.theform.UPDATE_TIME ==undefined)){
		document.theform.UPDATE_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	document.theform.submit();
}

</script>
<%
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_codeone" value="<%=interfaceType_codeone%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>

<%
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>����Ҫ�����</b></font>
			</td>
		</tr>
		<tr>
			<td ><hr noshade color="#808080" size="1"></td>
		</tr>
</table>

<%
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("modShowDataList",modShowDataList);
%>
<%if(!bSuccess){%>
	<jsp:include page="/system/config/basicdetail_show.jsp">
		<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
	</jsp:include>
<%}%>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">����(<u>S</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>
