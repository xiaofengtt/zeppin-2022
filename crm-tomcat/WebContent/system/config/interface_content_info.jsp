<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
	//-----------------��ʼ��������ֵ----------------
	Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
	String table_code = Utility.trimNull(request.getParameter("table_code"));
	String interfaceType_code = Utility.trimNull(request.getParameter("interfacetype_code_type"));
	String thisinterfacetype_code = Utility.trimNull(request.getParameter("thisinterfacetype_code"));
	ConfigLocal configLocal = EJBFactory.getConfig();
	
	//-----------------��ȡ����ID----------------
	String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
	String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));
	String thisinterfacetype_name =ConfigUtil.getPropertyNameById("interface_catalog","interfacetype_name","interfacetype_code",String.valueOf(thisinterfacetype_code));
	//-----------------ͨ��ҳ�������Ҹñ��Ӧ�������ֶ�----------------
	String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
	boolean bSuccess = false;
	
	//-----------------��ȡҳ�����е�Ԫ�ؼ���----------------
	List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//��ȡҳ�����е���ЧԪ�ؼ���
	List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//��ȡҳ��������ʾ����ЧԪ�ؼ���
	List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//��ȡҳ���������ص���ЧԪ�ؼ���
	
	//------------------�������޸Ĳ���----------------
	if(request.getMethod().equals("POST")){
		if(identity_code.intValue() == 0){//����
			ConfigUtil.addInfo(table_code,fieldAllList,request);//����ҳ������
		}else{
			ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//�޸�ҳ������
		}
		bSuccess = true;
	}

	//------------------�޸�������ʾ����----------------
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
<LINK href="/system/configstyle.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<style type="">

</style>
</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>
//alert("1");
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

//-------------------�ύ--------------------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	
	var show_type =document.theform.SHOW_TYPE.value;//��ʾ��ʽ
	if(show_type ==6){//ͼ������ʱ��У��
		var visibled_flag = document.getElementsByName('VISIBLED_FLAG');//�Ƿ�ɼ�
		var key_flag = document.getElementsByName('KEY_FLAG');//�Ƿ�����
		var update_flag =document.getElementsByName('UPDATE_FLAG');//�Ƿ����
		if(key_flag[0].checked){
			alert('ͼ�����Ͳ��ܵ�����');
			return false;
		}
		if(update_flag[0].checked){
			alert('ͼ�����Ͳ��ܸ���');
			return false;
		}
		if(visibled_flag[0].checked || visibled_flag[1].checked || visibled_flag[2].checked){
			alert('ͼ�����Ͳ��ܿɼ�');
			return false;
		}
	}
	
	var edit_type = document.theform.EDIT_TYPE.value;//�༭����
	if(edit_type == 2){//�༭����Ϊ������ʱѡ������Դ��ʽ��ѡ������Դ���ݶ�����Ϊ��
		var value_type =document.theform.VALUE_TYPE.value;//ѡ������Դ��ʽ
		var value_content =document.theform.VALUE_CONTENT.value;//ѡ������Դ����
		if(value_type == ''){
			alert('ѡ������Դ��ʽ����Ϊ��');
			document.theform.VALUE_TYPE.focus();
			return false;
		}
		if(value_content == ''){
			alert('ѡ������Դ���ݲ���Ϊ��');
			document.theform.VALUE_CONTENT.focus();
			return false;
		}
	}
	
	var query_flag = document.getElementsByName('QUERY_FLAG')[0].checked;
	var condition_type  =document.getElementsByName('CONDITION_TYPE');
	var checkedNum =0;
	var conditionValue = '';
	for(var i=0;i<condition_type.length;i++){//��ѯ������ѡ����
		if(condition_type[i].checked){
			checkedNum++;
			conditionValue = conditionValue + condition_type[i].value +',';
		}
	}
	if(query_flag && checkedNum==0){//�ֶοɲ�ѯʱ��ѯ����������
		alert("��ѯ������������ѡ��һ������");
		return false;
	}
	if(query_flag){
	conditionValue =conditionValue.substring(0,conditionValue.length-1);
	if(edit_type==2 || edit_type ==3){
		if(conditionValue != 1){
			alert('�ñ༭���Ͳ�ѯ����ֻ�ܰ�������');
			return false;
		}
	}
	if(edit_type == 5){
		if(conditionValue.indexOf('2')!=-1){
			alert('���ڿ��ѯ�������ܰ�������');
			return false;
		}
		if(conditionValue.indexOf('8')!=-1){
			alert('���ڿ��ѯ�������ܰ���ƥ�俪ͷ');
			return false;
		}
		if(conditionValue.indexOf('9')!=-1){
			alert('���ڿ��ѯ�������ܰ���ƥ���β');
			return false;
		}
	}
	if(edit_type==6 || edit_type==7 || edit_type==8 || edit_type==4){
		if(conditionValue != ''){
			alert('�ñ༭���Ͳ��ܰ�����ѯ����');
			return false;
		}
	}
	}
	<%if(identity_code.intValue() == 0){%>
		var fieldCodeName = document.getElementsByName('INTERFACETYPE_CODE')[0].value;
		var catalogCodeName = document.getElementsByName('INTERFACEFIELD_CODE')[0].value;
		var returnValue=conValidate("ConfigUtil","isFieldExistContent",fieldCodeName+"@@"+catalogCodeName);	
		if(returnValue == '0'){//��ҳ�����͵��ֶεı����Ѵ���
			alert('���ֶα������,����������');
			document.getElementsByName('INTERFACEFIELD_CODE')[0].focus();
			return false;
		}
	<%}%>
	if(!(document.theform.INPUT_USER ==undefined) && <%=identity_code.intValue()==0%>){
		document.theform.INPUT_USER.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.INPUT_DEPT ==undefined) && <%=identity_code.intValue()==0%>){
		document.theform.INPUT_DEPT.value='<%=depart_id%>';
	}
	if(!(document.theform.UPDATE_TIME ==undefined)){
		document.theform.UPDATE_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	document.theform.submit();
}

//�����ʼ��
function initValue(){
	if("<%=identity_code%>"==0){
		document.theform.INTERFACETYPE_CODE.value=trim('<%=thisinterfacetype_code%>');
		document.theform.INTERFACETYPE_NAME.value=trim('<%=thisinterfacetype_name%>');
	}
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
<input type="hidden" name="interfacetype_code_type" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>

<%
	//-----------------------��ʼ������-----------------------
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>�ֶ������������</b></font>
			</td>
		</tr>

		<tr>
			<td><hr noshade color="#808080" size="1"></td>
		</tr>
		<tr>
			<td align="right">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">����(<u>S</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">����(<u>C</u>)</button>
				&nbsp;&nbsp;
			</td>
		</tr>
</table>
<%
	//-----------------------��ʼ������-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("modShowDataList",modShowDataList);
%>
<jsp:include page="/system/config/basicdetail_show.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

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
<script language=javascript>
	initValue();
</script>
</body>
</html>
