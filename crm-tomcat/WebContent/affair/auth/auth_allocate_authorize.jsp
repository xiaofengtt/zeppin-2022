<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

AuthorizationVO authorizationVO = new AuthorizationVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();
AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
String shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),input_operatorCode);
Integer input_man = input_operatorCode;
Integer authorizationManagerID= Argument.getAuthorizationManagerID(ca_id);
boolean isAuthorationExist=true;
boolean isNextLevelManagerExist=true;

authorizationVO.setManagerID(managerID);
authorizationVO.setInput_man(input_man);
List authorizationList=authorizationLocal.list_query(authorizationVO);
List custmanagersList = tcustmanagers_Bean.getManagerList_nextLevel(input_man);

//??????Ϣ
if(request.getMethod().equals("POST")){
	authorizationShareVO.setShareType(new Integer(2));
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setCa_id(ca_id);
	authorizationShareVO.setManagerID(managerID);
	authorizationShareVO.setSourceManagerID(authorizationManagerID);
	String message = "";
	try{
		authorizationShareLocal.append(authorizationShareVO);
		
	}catch(Exception e){
		message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //????ʧ??
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
<title><%=LocalUtilis.language("index.menu.addAuthorize",clientLocale)%> </title><!-- ??????Ȩ -->
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

	/*??֤????*/
	function validateForm(form){		
		return sl_check_update();	
	}

	/*????*/
	function SaveAction(){
		
		if(!sl_check(document.getElementsByName("ca_name")[0], "<%=LocalUtilis.language("class.CA_Name",clientLocale)%> ", 128, 1)) return false;// ??Ȩ??????
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*ȡ??*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/auth_allocate_authorize.jsp" onsubmit="javascript:return validateForm(this);">
<!--?????ɹ???־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
		<tr>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.CA_Name",clientLocale)%> ??</td><!-- ??Ȩ?????? -->
			<td>
				<select name="ca_id" style="width: 110px;">
					<%=Argument.getAuthorization_Value(ca_id,authorizationList)%>
				</select>
				<span><%if (!(authorizationList != null && authorizationList.size() != 0)){ 
                       out.print(LocalUtilis.language("message.noAuthorizationExist",clientLocale));
						isAuthorationExist=false;
					  }%>
				</span>
			</td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.targetManager",clientLocale)%> ??</td><!-- Ŀ???ͻ????? -->
			<td>
				<select name="managerID" style="width: 110px;">
					<%=Argument.getManagerListFromTCustManagerTree(input_man,custmanagersList)%>
				</select>
				<span><%if (!(custmanagersList != null && custmanagersList.size() != 0)){
						 out.print(LocalUtilis.language("message.noNextLevelManagerExist",clientLocale));
						 isNextLevelManagerExist=false;
                       }%>
				</span>
			</td>
			 
			<!-- ???????? ?????Ѿ?ɾ?? team_new.jsp-->
			
		</tr>
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.shareDescription",clientLocale)%> ??</td><!-- ??Ȩ???? -->
			<td colspan="3">
				<textarea rows="3" name="shareDescription" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
    <!-- ???? -->
	<button type="button" <%if (!isAuthorationExist || !isNextLevelManagerExist){%> disabled <%} %>
 class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- ???? -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
<%authorizationLocal.remove();
authorizationShareLocal.remove(); %>
</BODY>
</HTML>