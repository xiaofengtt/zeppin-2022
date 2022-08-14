<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
	//-----------------初始化，主键值----------------
	Integer identity_code = Utility.parseInt(request.getParameter("identity_code"),new Integer("0"));
	String table_code = Utility.trimNull(request.getParameter("table_code"));
	String interfaceType_code = Utility.trimNull(request.getParameter("interfacetype_code_type"));
	String thisinterfacetype_code = Utility.trimNull(request.getParameter("thisinterfacetype_code"));
	ConfigLocal configLocal = EJBFactory.getConfig();
	
	//-----------------获取部门ID----------------
	String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
	String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));
	String thisinterfacetype_name =ConfigUtil.getPropertyNameById("interface_catalog","interfacetype_name","interfacetype_code",String.valueOf(thisinterfacetype_code));
	//-----------------通过页面编码查找该表对应的主键字段----------------
	String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
	boolean bSuccess = false;
	
	//-----------------获取页面所有的元素集合----------------
	List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//获取页面所有的有效元素集合
	List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//获取页面所有显示的有效元素集合
	List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//获取页面所有隐藏的有效元素集合
	
	//------------------新增与修改操作----------------
	if(request.getMethod().equals("POST")){
		if(identity_code.intValue() == 0){//新增
			ConfigUtil.addInfo(table_code,fieldAllList,request);//新增页面数据
		}else{
			ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//修改页面数据
		}
		bSuccess = true;
	}

	//------------------修改数据显示操作----------------
	List modShowDataList = new ArrayList();//修改页面显示数据
	if(identity_code.intValue() != 0){//修改显示取值
		modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
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

//-------------------提交--------------------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}
	
	var show_type =document.theform.SHOW_TYPE.value;//显示格式
	if(show_type ==6){//图标类型时的校验
		var visibled_flag = document.getElementsByName('VISIBLED_FLAG');//是否可见
		var key_flag = document.getElementsByName('KEY_FLAG');//是否主键
		var update_flag =document.getElementsByName('UPDATE_FLAG');//是否更新
		if(key_flag[0].checked){
			alert('图标类型不能当主键');
			return false;
		}
		if(update_flag[0].checked){
			alert('图标类型不能更新');
			return false;
		}
		if(visibled_flag[0].checked || visibled_flag[1].checked || visibled_flag[2].checked){
			alert('图标类型不能可见');
			return false;
		}
	}
	
	var edit_type = document.theform.EDIT_TYPE.value;//编辑类型
	if(edit_type == 2){//编辑类型为下拉框时选择项来源方式和选择项来源内容都不能为空
		var value_type =document.theform.VALUE_TYPE.value;//选择项来源方式
		var value_content =document.theform.VALUE_CONTENT.value;//选择项来源内容
		if(value_type == ''){
			alert('选择项来源方式不能为空');
			document.theform.VALUE_TYPE.focus();
			return false;
		}
		if(value_content == ''){
			alert('选择项来源内容不能为空');
			document.theform.VALUE_CONTENT.focus();
			return false;
		}
	}
	
	var query_flag = document.getElementsByName('QUERY_FLAG')[0].checked;
	var condition_type  =document.getElementsByName('CONDITION_TYPE');
	var checkedNum =0;
	var conditionValue = '';
	for(var i=0;i<condition_type.length;i++){//查询条件勾选个数
		if(condition_type[i].checked){
			checkedNum++;
			conditionValue = conditionValue + condition_type[i].value +',';
		}
	}
	if(query_flag && checkedNum==0){//字段可查询时查询条件不能无
		alert("查询条件必须至少选择一个以上");
		return false;
	}
	if(query_flag){
	conditionValue =conditionValue.substring(0,conditionValue.length-1);
	if(edit_type==2 || edit_type ==3){
		if(conditionValue != 1){
			alert('该编辑类型查询条件只能包含等于');
			return false;
		}
	}
	if(edit_type == 5){
		if(conditionValue.indexOf('2')!=-1){
			alert('日期框查询条件不能包括包含');
			return false;
		}
		if(conditionValue.indexOf('8')!=-1){
			alert('日期框查询条件不能包括匹配开头');
			return false;
		}
		if(conditionValue.indexOf('9')!=-1){
			alert('日期框查询条件不能包括匹配结尾');
			return false;
		}
	}
	if(edit_type==6 || edit_type==7 || edit_type==8 || edit_type==4){
		if(conditionValue != ''){
			alert('该编辑类型不能包含查询条件');
			return false;
		}
	}
	}
	<%if(identity_code.intValue() == 0){%>
		var fieldCodeName = document.getElementsByName('INTERFACETYPE_CODE')[0].value;
		var catalogCodeName = document.getElementsByName('INTERFACEFIELD_CODE')[0].value;
		var returnValue=conValidate("ConfigUtil","isFieldExistContent",fieldCodeName+"@@"+catalogCodeName);	
		if(returnValue == '0'){//该页面类型的字段的编码已存在
			alert('该字段编码存在,请重新输入');
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

//界面初始化
function initValue(){
	if("<%=identity_code%>"==0){
		document.theform.INTERFACETYPE_CODE.value=trim('<%=thisinterfacetype_code%>');
		document.theform.INTERFACETYPE_NAME.value=trim('<%=thisinterfacetype_name%>');
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
<input type="hidden" name="interfacetype_code_type" value="<%=interfaceType_code%>" />
<input type="hidden" name="identity_code" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>

<%
	//-----------------------初始化参数-----------------------
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td>
				<img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>字段配置详情界面</b></font>
			</td>
		</tr>

		<tr>
			<td><hr noshade color="#808080" size="1"></td>
		</tr>
		<tr>
			<td align="right">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">保存(<u>S</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
				&nbsp;&nbsp;
			</td>
		</tr>
</table>
<%
	//-----------------------初始化参数-----------------------
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
