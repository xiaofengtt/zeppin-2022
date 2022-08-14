<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.*,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//初始化，主键值
Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
String table_code = Utility.trimNull(request.getParameter("table_code"));
String interfaceType_code = Utility.trimNull(request.getParameter("interfacetype_code"));
ConfigLocal configLocal = EJBFactory.getConfig();

String form_code = Utility.trimNull(request.getParameter("form_code"));
String form_name = Utility.trimNull(request.getParameter("form_name"),"");

//通过页面编码查找该表对应的主键字段
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;
//获取部门ID
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));

List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//获取页面所有的有效元素集合
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//获取页面所有显示的有效元素集合
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//获取页面所有隐藏的有效元素集合

if(request.getMethod().equals("POST")){
	if(identity_code.intValue() == 0){//新增
		ConfigUtil.addInfo(table_code,fieldAllList,request);//新增页面数据
	}else{
		ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//修改页面数据
	}
	bSuccess = true;
}

List modShowDataList = new ArrayList();//修改页面显示数据
if(identity_code.intValue() != 0){//修改显示取值
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
	modShowDataList=ConfigCheck.replaceFieldValue(modShowDataList,"SHOW_NAME","&nbsp;","nbsp;");
}
%>
<html>
<head>
<%if(identity_code.intValue() ==0){%>
<title>新增详情</title>
<%}else{%>
<title>修改详情</title>
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

//----------提交时必输验证------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	//获得显示字段名
	showName=document.theform.SHOW_NAME.value;
	showName=showName.replaceAll("nbsp;","&nbsp;");
	document.theform.SHOW_NAME.value=showName;
	if(!(document.theform.INPUT_TIME ==undefined) && (<%=identity_code.intValue()==0%>)){//新增时赋值
		document.theform.INPUT_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	if(!(document.theform.INPUT_USER ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_USER.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.INPUT_DEPT ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_DEPT.value='<%=depart_id%>';
	}
	document.theform.submit();
}

//界面初始化
function initValue(){
	if("<%=identity_code%>"==0){
		document.theform.FORM_CODE.value=trim('<%=form_code%>');
		document.theform.FORM_NAME.value=trim('<%=form_name%>');
	}
}

</script>

<%
	//-----------------------初始化参数-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code.toString()%>">
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
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>表单目录</b></font>
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

<jsp:include page="/system/config/basicdetail_show.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include> 

<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">保存(<u>S</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
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

