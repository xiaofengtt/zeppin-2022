<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer op_code = Utility.parseInt(request.getParameter("op_code"),new Integer(0));
String op_name = Utility.trimNull(request.getParameter("op_name"));
Integer depart_id = Utility.parseInt(request.getParameter("depart_id"),new Integer(0));
Integer role_id = Utility.parseInt(request.getParameter("role_id"),new Integer(0));
String summary = Utility.trimNull(request.getParameter("summary"));
String address = Utility.trimNull(request.getParameter("address"));
String email = Utility.trimNull(request.getParameter("e_mail"));
String o_tel = Utility.trimNull(request.getParameter("o_tel"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String login_user = Utility.trimNull(request.getParameter("login_user"));
String card_type = Utility.trimNull(request.getParameter("cardtype"));
String card_id = Utility.trimNull(request.getParameter("carid"));
Integer card_valid_date = Utility.parseInt(request.getParameter("Trade_date"),new Integer(0));
boolean bSuccess = false;
TOperatorVO vo = new TOperatorVO(); //扩展类
OperatorLocal local = EJBFactory.getOperator();

if (request.getMethod().equals("POST")){
	
	vo.setOp_code(op_code);
    vo.setOp_name(op_name);
    vo.setDepart_id(depart_id);
    vo.setRole_id(role_id);
    vo.setSummary(summary);
    vo.setAddress(address);
    vo.setEmail(email);
    vo.setTelephone(o_tel);
    vo.setMobile(mobile);
    vo.setLogin_user(login_user);
    vo.setCard_type(card_type);
    vo.setCard_id(card_id);
    vo.setCard_valid_date(card_valid_date);
	
	try{
		local.appendOperator(vo,input_operatorCode);
		bSuccess = true;
	}catch(Exception e){
		String saveFail = LocalUtilis.language("message.saveFail",clientLocale);
		out.println("<script language=\"javascript\">alert(\""+saveFail+" ！"+e.getMessage()+"。\");</script>");//保存失败
		bSuccess = false;
	}
	
}
%>
<html>
<head>
<title><%=LocalUtilis.language("menu.operatorNew",clientLocale)%> </title>
<!--新增员工-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

	function validateForm(form){

    	if(!sl_checkNum(form.op_code, "<%=LocalUtilis.language("class.opCode",clientLocale)%> ", 8, 1))	return false;	//员工编号
    	if(form.op_code.value==0) 
        {
            alert("<%=LocalUtilis.language("message.opCodeError",clientLocale)%> ");//员工编号不能为0
            return false;
        }
    	if(form.Trade_date_picker.value!=""){
            if(!sl_checkDate(form.Trade_date_picker,"<%=LocalUtilis.language("class.tradeDate2",clientLocale)%> ")) return false;//证件有效期
        }   
    	syncDatePicker(form.Trade_date_picker, form.Trade_date);
    	if(!sl_check(form.op_name, "<%=LocalUtilis.language("class.opName2",clientLocale)%> ", 10, 1))		return false;//员工名称
    	if(!sl_check(form.login_user, "<%=LocalUtilis.language("class.loginUser",clientLocale)%> ", 20, 1))		return false;//系统登录账号
    	if(!sl_checkChoice(form.depart_id, "<%=LocalUtilis.language("menu.departmentUpdate",clientLocale)%> "))	return false;//部门
    	/*if(document.theform.clearpassword.checked)
    		document.theform.passflag.value=1;
    	else
    	    document.theform.passflag.value=0;*/	
    	return sl_check_update();
	}

</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="operator_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="passflag" value="">
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.operatorNew2",clientLocale)%> </b></font></td>
		</tr><!--新增员工信息-->
    </table>
    <br/>
    <table border="0" width="100%" cellspacing="3" class="product-list">
		<tr>
			<td align="right" noWrap>*<%=LocalUtilis.language("class.opCode",clientLocale)%> :</td><!--员工编号-->
			<td><input name="op_code" onkeydown="javascript:nextKeyPress(this)" maxlength="10" size="25" value=""></td>
			<td align="right" noWrap>*<%=LocalUtilis.language("class.opName",clientLocale)%> :</td><!--员工姓名-->
			<td><input name="op_name" onkeydown="javascript:nextKeyPress(this)" maxlength="10" size="25" value=""></td>
		</tr>
		<tr>
		    <td align="right" noWrap>*<%=LocalUtilis.language("class.loginUser",clientLocale)%> :</td><!--系统登录帐号-->
			<td><INPUT name="login_user" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value=""></td>
			<td align="right" noWrap>*<%=LocalUtilis.language("class.departID3",clientLocale)%> :</td><!--所在部门-->
			<td><select size="1" name="depart_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
				<%=Argument.getDepartOptions1(new Integer(0))%>
			</select></td>
		</tr>
		<tr>
			<td align="right" noWrap><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!--证件类型--> 
			<td><select size="1" name="cardtype" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
				<%=Argument.getCardTypeOptions2("")%>
			</select></td>
			<td align="right" noWrap><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码--> 
			<td><input name="carid" onkeydown="javascript:nextKeyPress(this)" maxlength="18" size="25" value=""></td>
		</tr>	
		<tr>
			<td align="right" noWrap><%=LocalUtilis.language("class.tradeDate",clientLocale)%> :</td><!--证件有效期限--> 
			<td><INPUT TYPE="text" NAME="Trade_date_picker" class=selecttext value="" >
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.Trade_date_picker,theform.Trade_date_picker.value,this);" tabindex="15">
				<INPUT TYPE="hidden" NAME="Trade_date"   value="">
			</td>
		</tr>									
		<tr>
			<td align="right" noWrap><%=LocalUtilis.language("class.address",clientLocale)%> :</td><!--地址-->
			<td colspan="3"><input name="address" size="73" onkeydown="javascript:nextKeyPress(this)"  value=""></td>
		</tr>
		<tr>
           <td align="right" noWrap><%=LocalUtilis.language("class.oTel",clientLocale)%> :</td><!--办公电话-->
           <td><input name="o_tel" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value=""></td>
			<td align="right" noWrap><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
			<td><INPUT name="mobile" onkeydown="javascript:nextKeyPress(this)" maxlength="20" size="25" value=""></td>
		</tr>
		<tr>
			<td align="right" noWrap>Email:</td>
			<td><INPUT name="e_mail" onkeydown="javascript:nextKeyPress(this)" maxlength="30" size="25" value=""></td>
			<!--<td align="right" noWrap>初始化密码:</td>
			<td>
			<input type="checkbox" class="flatcheckbox" name="clearpassword" >
			</td>-->
		</tr>
		<tr>
			<td align="right" valign="top" noWrap><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="73"></textarea></td>
		</tr>
	</table>
	<br/>
    <table border="0" width="100%">
    	<tr>
    		<td align="right">
    		<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
    		&nbsp;&nbsp;<!--保存-->
    		<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
    		&nbsp;&nbsp;<!--取消-->
    		</td>
    	</tr>
    </table>
</form>
</body>
</html>
