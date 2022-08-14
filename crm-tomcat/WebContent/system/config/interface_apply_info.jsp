<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//������ʼ��
Integer identity_code = Utility.parseInt(request.getParameter("identityCode"),new Integer("0"));
String catalog_code = Utility.trimNull(request.getParameter("catalog_code"));
Integer record_id = Utility.parseInt(request.getParameter("record_id"),new Integer("0"));
boolean bSuccess = false;
ConfigLocal configLocal = EJBFactory.getConfigCatalog();
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(identity_code));

List elementTdList = configLocal.listElementOrderNO(catalog_code,record_id,"1");//����Ԫ��
//ҳ�������޸Ĳ���
if(request.getMethod().equals("POST")){
	if(record_id == 0){//����
			String guid = Argument.randomUUID();
			ConfigUtil.applyRecordAdd(configLocal,catalog_code,elementTdList,input_operatorCode,guid);
			ConfigUtil.applyDataAdd(configLocal,elementTdList,catalog_code,guid,request);
		bSuccess = true;
	}else{//�޸�
		ConfigUtil.applyDataModify(configLocal,elementTdList,catalog_code,request,record_id);
		bSuccess = true;
	}
}
List elementTdShowList = configLocal.listElementOrderNO(catalog_code,record_id,"2");//����ҳ����ʾ��Ԫ��
List elementTdHideList = configLocal.listElementOrderNO(catalog_code,record_id,"3");//����ҳ�����ص�Ԫ��
List dataShowList =new ArrayList();
List dataHideList = new ArrayList();
	//ҳ���ʼ����ֵ
if(record_id != 0){
	String relation_id = ConfigUtil.getPropertyNameById("config_record","relation_id","record_id",String.valueOf(record_id));
	elementTdShowList =configLocal.listExistDataValues(relation_id,"1",catalog_code);//�޸�ʱ���Ҷ�Ӧ�Ѵ��ڵ���ʾԪ��
	elementTdHideList =configLocal.listExistDataValues(relation_id,"2",catalog_code);//�޸�ʱ���Ҷ�Ӧ�Ѵ��ڵ�����Ԫ��
	dataShowList = configLocal.listDataValues(relation_id,"1");//�����Ѵ��ڵ����ñ�����
	dataHideList = configLocal.listDataValues(relation_id,"2");
}
%>
<html>
<head>
<%if(record_id == 0){%>
<title>����Ҫ��Ӧ��</title>
<%}else{%>
<title>�༭Ҫ��Ӧ��</title>
<%}%>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/system/configstyle.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function elementOnchange(returnValue){
	var fieldValue=returnValue.split(",");
	var fieldID=fieldValue[0];
	var fieldName=fieldValue[1];
	var selectName =document.getElementsByName(fieldID)[0].options[document.getElementsByName(fieldID)[0].selectedIndex].text;
	document.getElementsByName(fieldName)[0].value =selectName;
}
	
function sucSubmint(){
	if(applySubmitValidate()){
		document.theform.submit();
	}
}
</script>

<%
	request.setAttribute("elementTdShowList",elementTdShowList);
%>
<jsp:include page="/system/config/assistdetail_js.jsp"></jsp:include>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="catalog_code" value="<%=catalog_code%>" />
<input type="hidden" name="record_id" value="<%=record_id%>">

<%request.setAttribute("elementTdHideList",elementTdHideList);%>
<jsp:include page="/system/config/assistdetail_hidden.jsp"></jsp:include>

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
	request.setAttribute("elementTdShowList",elementTdShowList);
	request.setAttribute("dataShowList",dataShowList);
%>
<%if(!bSuccess){%>
<jsp:include page="/system/config/assistdetail_show.jsp">
	<jsp:param name="catalog_code" value="<%=catalog_code%>"/>
	<jsp:param name="record_id" value="<%=record_id%>"/>
</jsp:include>
<%}%>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmint();">����(<u>S</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
		&nbsp;&nbsp;
		</td>
	</tr>
</table>
</form>
</body>
</html>
