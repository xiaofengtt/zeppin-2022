<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

Integer managerid_before = null;
Integer managerid_now = null;
Integer input_man = null;

if(request.getMethod().equals("POST")){
	managerid_before = Utility.parseInt(Utility.trimNull(request.getParameter("managerid_before")),null);
	managerid_now = Utility.parseInt(Utility.trimNull(request.getParameter("managerid_now")),null);
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);
	
	TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
	vo.setManagerid_before(managerid_before);
	vo.setManagerid_now(managerid_now);
	vo.setInput_man(input_man);
	
	try{
		local.append(vo);
		bSuccess = true;

	}catch(Exception e){
		 String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		 out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
	}
	
}

local.remove();
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.addTransfer",clientLocale)%> </title><!-- 新增移交 -->
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
		if(document.getElementsByName("managerid_before")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.beforeTurnOverManagerError",clientLocale)%> ！"); return false;//请选择移交前的客户经理
		}
		
		if(document.getElementsByName("managerid_now")[0].getAttribute("value") == ""){
			alert("<%=LocalUtilis.language("message.afterTurnOverManagerError",clientLocale)%> ！"); return false;//请选择移交后的客户经理
		}
	
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
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/client_handover_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</td><!-- 客户经理 -->
			<td>
				<select name="managerid_before" style="width: 110px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
			<td><span><font color="red"> <%=LocalUtilis.language("message.transferredTo",clientLocale)%> </font></span></td><!-- 移交至 -->
			<td  align="right">*<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</td><!-- 客户经理 -->
			<td>
				<select name="managerid_now" style="width: 110px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
		</tr>
	</table>
</div>
<br>
<div align="right">	
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!-- 保存 -->
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button><!-- 返回 -->
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>