<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String operatorCodes = Utility.trimNull(request.getParameter("operatorCodes"));
String operatorEmails = Utility.trimNull(request.getParameter("operatorEmails"));

//员工信息表
OperatorLocal operator = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
vo.setStatus(new Integer(1));

List operator_list = operator.listOpAll(vo);
Map map = null;
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.operatorEmailChoose",clientLocale)%> </title>
<!--选择员工-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language="javascript">
window.onload = function(){
	var operatorCodes = document.getElementById("operatorCodes").value;
	var op_code = document.getElementsByName("op_code");
	var operators = operatorCodes.split("$");
	var items = new Array();
	var index = 0;
	var v_operaorCode;

	//选择员工
	if(operators.length>0){			
		for(var i = 0;i<operators.length;i++){	
			v_operaorCode = operators[i];
			
			for(var j = 0; j < op_code.length; j++){
					if(op_code[j].value == v_operaorCode){
						op_code[j].checked=true;
					}
			}			
		}
	}
	
}

//拼EMAIL
function selectEmails(){
	var op_code = document.getElementsByName("op_code");
	var op_name = document.getElementsByName("op_name");
	var op_email = document.getElementsByName("op_email");
	var emailGroup = "";
	var retCode = "";
	var retName = "";
	
	for(var j = 0; j < op_code.length; j++){
				if(op_code[j].checked){
					emailGroup += op_email[j].value+';';
					retCode+= op_code[j].value+'$';
					retName += op_name[j].value+',';
				}
	}	
	
	emailGroup=emailGroup.substring(0,emailGroup.length-1);	
	retCode=retCode.substring(0,retCode.length-1);
	retName=retName.substring(0,retName.length-1);
	
	var ret = retCode+"&"+retName+"&"+emailGroup;
	
	window.returnValue=ret;
	window.close();
}

/*取消*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}
</script>
<BODY class="BODY">
<form name="theform" method ="POST" >
<input type="hidden" name="operatorCodes" id="operatorCodes"  value="<%=operatorCodes%>" />
<input type="hidden" name="operatorEmails" id="operatorEmails"  value="<%=operatorEmails%>" />

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.employeeEmailChoose",clientLocale)%> </b></font><!--员工电子邮件选择-->
	<hr noshade color="#808080" size="1">
</div>

<div align="center" >
	<table border="0" width="525px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="20%">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
				onclick="javascript:selectAllBox(document.theform.op_code,this);"><%=LocalUtilis.language("class.opCode",clientLocale)%> 
			</td><!--员工编号-->
			<td align="center" width="30%"><%=LocalUtilis.language("class.opName",clientLocale)%> </td><!--员工姓名-->
			<td align="center" width="*"><%=LocalUtilis.language("class.email",clientLocale)%> </td><!--电子邮件-->
		</tr>
	</table>
	
<span id="tableList" style="overflow-y:auto;height:250;">
	<table border="0" width="525px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
<%
//声明字段
Iterator iterator = operator_list.iterator();
int iCount = 0;
String op_code;
String op_name;
String op_email;

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	op_code = Utility.trimNull(map.get("OP_CODE"));
	op_name= Utility.trimNull(map.get("OP_NAME"));	
	op_email = Utility.trimNull(map.get("EMAIL"));	
%>
			<tr class="tr<%=(iCount % 2)%>">
				<td class="tdh" align="center" width="20%">
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%">
							<%if(op_email.length()>0){%>
								<input type="checkbox" name="op_code" value="<%=op_code%>" class="flatcheckbox">
								<input type="hidden" name="op_email"   value="<%=op_email%>" />
								<input type="hidden" name="op_name"   value="<%=op_name%>" />
							<%}%></td>
							<td width="90%" align="left">&nbsp;&nbsp;<%=op_code%></td>
						</tr>
					</table>
				</td>  
				<td align="center" width="30%"><%= op_name%></td> 
				<td align="left" width="*">&nbsp;&nbsp;<%= op_email%></td> 
			</tr>
<%}%>
	</table>
</span>
</div>
<br>
<div align="right">	
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:selectEmails();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--确定-->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!--关闭-->
</div>

<%  operator.remove();%>
</form>
</BODY>
</HTML>

