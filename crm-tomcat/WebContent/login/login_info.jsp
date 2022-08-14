<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%
	Integer book_code = Utility.parseInt(request.getParameter("book_code"),null);
	Integer input_opCode = Utility.parseInt(request.getParameter("input_opCode"),null);
	
	boolean bSuccess = false;
	String errorMsg = "";
	
	DictparamVO dictVO = new DictparamVO();
	OperatorVO opVO = new OperatorVO();
	
	DictparamLocal dict = EJBFactory.getDictparam();
	OperatorLocal op = EJBFactory.getOperator();
	
	LoginService logS = new LoginService();
	
	List dictList = null;
	List opInfoList = null;
	
	Map dictMap = null;
	Map opMap = null;
	
if(request.getMethod().equals("POST"))
{
	//op.updateBookCode(input_opCode,book_code);
	//登陆后查询信息
	
	opVO.setOp_code(input_opCode);
	opInfoList = op.listOpAll(opVO);
	
	opMap = (Map)opInfoList.get(0);
	
	//初始化系统开关所需的参数
	dictVO.setFlag_type("DB00001");
	//dictVO.setIs_modi(new Integer(0));
			
	//查询系统开关参数
	dictList = dict.listSysControlValue(dictVO);
		
	if(dictList.size() == 0)
	{
		errorMsg = "系统开关参数不存在，请检查数据库！";
		throw new Exception("系统开关参数不存在，请检查数据库！");
	}
			
	dictMap = (Map)dictList.get(0);
	Integer dictValue = Utility.parseInt(Utility.trimNull(dictMap.get("VALUE")),null);
	//获取inputMan
	InputMan OPERATOR = logS.loginInputMan(opVO);
	
	//向Session中赋值
	session.setAttribute("OPERATOR",OPERATOR);
	session.setAttribute("LOG0001",new Integer(Argument.getSyscontrolValue("LOG0001")));
	session.setAttribute("WEBFLAG","XTWEB");
	session.setAttribute("DBDRIVER",dictValue);

	bSuccess = true;
}
%>
<html>
<head>
<title>选择帐套</title>
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
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	return true;
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="../login/login_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="input_opCode" value="<%=input_opCode%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td colspan="2"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%="请选择帐套"%></b></td>
				</tr>
				<tr>
					<td colspan="2">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
				<tr>
					<td  align="center" valign="bottom" height="35">
						<select size="1" name="book_code" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
								<%=Argument.getOpBook_Code(input_opCode,book_code)%>
						</select>
					</td>
				</tr>
</table>
<table border="0" width="100%">
					<tr>
						<td align="right" valign="bottom" height="70">
						<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:disableAllBtn(true);if(document.theform.onsubmit()) document.theform.submit();">保存(<u>S</u>)</button>
						&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);window.returnValue=null;window.close();">取消(<u>C</u>)</button>
						&nbsp;&nbsp;
						</td>
					</tr>
</table>
</form>
</body>
</html>