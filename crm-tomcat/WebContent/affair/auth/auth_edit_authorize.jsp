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
Integer serial_no=Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
Integer sourceManagerID=Utility.parseInt(request.getParameter("sourceManagerID"),new Integer(0));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),input_operatorCode);
Integer input_man = input_operatorCode;
Integer authorizationManagerID= Argument.getAuthorizationManagerID(ca_id);
Integer shareStatus=Utility.parseInt(request.getParameter("shareStatus"),new Integer(2));
authorizationVO.setManagerID(managerID);
authorizationVO.setInput_man(input_man);
List authorizationList=authorizationLocal.list_query(authorizationVO);
List custmanagersList = tcustmanagers_Bean.getManagerList_nextLevel(input_man);

//保存信息
if(request.getMethod().equals("POST")){
	authorizationShareVO.setSerial_no(serial_no);
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setSourceManagerID(sourceManagerID);
	authorizationShareVO.setCa_id(ca_id);
	authorizationShareVO.setManagerID(managerID);
	authorizationShareVO.setInput_man(input_man);
	String message = "";
	try{
		authorizationShareLocal.modi(authorizationShareVO);
		
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
	var oState = {};
	oState = window.dialogArguments||{};
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
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*取消*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}
    //用来取值,设值的 hesl 2011-05-18
	function onloadInfo(){
		try{
			var _ca_id = document.getElementsByName("ca_id")[0];
			var _serial_no = document.getElementsByName("serial_no")[0];
			var _shareDescription = document.getElementsByName("shareDescription")[0];
			var _managerID = document.getElementsByName("managerID")[0];
			var _sourceManagerID = document.getElementsByName("sourceManagerID")[0];
			var _shareStatus = document.getElementsByName("shareStatus")[0];
			selectIndex(_managerID,oState.data.managerID);
			selectIndex(_ca_id,oState.data.ca_id);
			_serial_no.setAttribute("value",oState.data.serial_no);
			_sourceManagerID.setAttribute("value",oState.data.sourceManagerID);
			_shareDescription.setAttribute("value",oState.data.shareDescription);
			_shareStatus.setAttribute("value",oState.data.shareStatus);
		}catch(e){

		}
	}
	//用来设置select的值
	function selectIndex(obj,value){
		var _obj = obj;
		for(var i=0;i< _obj.length;i++){
			if(_obj[i].getAttribute("value") == value){
				_obj.selectedIndex = i;
			}
		}
	}

</script>
</HEAD>
<BODY class="BODY body-nox" onload="javascript:onloadInfo();">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/auth_edit_authorize.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="serial_no" />
<input type="hidden" name="sourceManagerID" />
<input type="hidden" name="shareStatus" />
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center" >
	<table border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list">
		<tr>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.CA_Name",clientLocale)%> ：</td><!-- 授权集名称 -->
			<td>
				<select name="ca_id" style="width: 110px;">
					<%=Argument.getAuthorization_Value(ca_id,authorizationList)%>
				</select>
				<span><%if (!(authorizationList != null && authorizationList.size() != 0)) out.print(LocalUtilis.language("message.noAuthorizationExist",clientLocale));%></span>
			</td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.targetManager",clientLocale)%> ：</td><!-- 目标客户经理 -->
			<td>
				<select name="managerID" style="width: 110px;">
					<%=Argument.getManagerListFromTCustManagerTree(input_man,custmanagersList)%>
				</select>
				<span><%if (!(custmanagersList != null && custmanagersList.size() != 0)) out.print(LocalUtilis.language("message.noNextLevelManagerExist",clientLocale));%></span>
			</td>
			 
			<!-- 成立日期 代码已经删除 team_new.jsp-->
			
		</tr>
		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.shareDescription",clientLocale)%> ：</td><!-- 授权描述 -->
			<td colspan="3">
				<textarea rows="3" name="shareDescription" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
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
<%authorizationLocal.remove();
authorizationShareLocal.remove(); %>
</BODY>
</HTML>