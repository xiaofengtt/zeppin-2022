<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

AuthorizationVO vo = new AuthorizationVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();

Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
String ca_description = Utility.trimNull(request.getParameter("ca_description"));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),new Integer(0));
Integer input_man = input_operatorCode;

//保存信息
if(request.getMethod().equals("POST")){
	vo.setCa_name(ca_name);
	vo.setCa_description(ca_description);
	vo.setManagerID(managerID);
	vo.setInput_man(input_man);
	
	String message = "";
	try{
		authorizationLocal.append(vo);
		
	}catch(Exception e){
		message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
	}
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.addAuthorization",clientLocale)%> </title><!-- 新增授权集 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

	<% if(bSuccess){ %>
		window.returnValue = true;
		window.close();
	<%}%>

	/*验证数据*/
	function validateForm(form){		
		return sl_check_update();	
	}

	/*保存*/
	function SaveAction(){
		
		if(!sl_check(document.getElementsByName("ca_name")[0], "<%=LocalUtilis.language("class.CA_Name",clientLocale)%> ", 128, 1)) return false;// 授权集名称
		if(!sl_checkChoice(document.getElementsByName("managerID")[0], "<%=LocalUtilis.language("class.custManager",clientLocale)%>")) return false;//所属客户经理
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/auth_new.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
		<tr>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.CA_Name",clientLocale)%> ：</td><!-- 授权集名称 -->
			<td ><input type="text" style="width: 120px;" name="ca_name"></td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.custManager",clientLocale)%> ：</td><!-- 所属客户经理 -->
			<td>
				<select name="managerID" style="width: 110px;">
					<%=Argument.getManager_Value(input_man)%>
				</select>
			</td>
			 
			<!-- 成立日期 代码已经删除 team_new.jsp-->
			
		</tr>
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.CA_DESCRIPTION",clientLocale)%> ：</td><!-- 授权集描述 -->
			<td colspan="3">
				<textarea rows="3" name="ca_description" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
    <!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 返回 -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
<%authorizationLocal.remove(); %>
</BODY>
</HTML>