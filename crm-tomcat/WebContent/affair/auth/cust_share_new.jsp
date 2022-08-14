<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
boolean isSameLevelManagerExist=true;
AuthorizationVO authorizationVO = new AuthorizationVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();
AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
String shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
Integer sourceManagerID = Utility.parseInt(request.getParameter("sourceManagerID"),new Integer(0));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),input_operatorCode);
Integer input_man = input_operatorCode;
Integer authorizationManagerID= Argument.getAuthorizationManagerID(ca_id);

authorizationVO.setManagerID(managerID);
authorizationVO.setInput_man(input_man);
List authorizationList=authorizationLocal.list_query(authorizationVO);
List sourceManagersList= tcustmanagers_Bean.getManagerList_sortByAuth(input_man);
List tagertManagersList = tcustmanagers_Bean.getManagerList_sameLevel(input_man);

//保存信息
if(request.getMethod().equals("POST")){
	authorizationShareVO.setShareType(new Integer(1));
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setManagerID(managerID);
	authorizationShareVO.setSourceManagerID(sourceManagerID);
	String message = "";
	try{
		authorizationShareLocal.append(authorizationShareVO);
		
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
<title><%=LocalUtilis.language("index.menu.addShare",clientLocale)%> </title><!-- 新增共享 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
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
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*取消*/
function CancelAction(){
	window.returnValue=null;
	window.close();
}

/*设置目标客户经理*/
function setManagerMan(obj)
{
	if(obj.value != "")
	{
		CustomerInfo.queryManagerManBySourceManagerId(obj.value,listCallBack)
	}else{
		DWRUtil.removeAllOptions(document.theform.managerID);
		DWRUtil.addOptions(document.theform.managerID,{'':'请选择'});
	}
}

/*回调函数*/
function listCallBack(data){
	if(data != null)
	{
		var obj_op = document.theform.managerID;   
	    DWRUtil.removeAllOptions(obj_op);   
	    DWRUtil.addOptions(obj_op,{'':'请选择'});
	    DWRUtil.addOptions(obj_op,data);
        document.getElementById("managerIdTip").innerHTML = "";
        document.getElementById("btnSave").disabled="";
	}else{
		document.getElementById("managerIdTip").innerHTML = "<%=LocalUtilis.language("message.noSameLevelManagerExist",clientLocale)%>";
		DWRUtil.removeAllOptions(document.theform.managerID);
		DWRUtil.addOptions(document.theform.managerID,{'':'请选择'});
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/cust_share_new.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
		<tr>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.sourceManager",clientLocale)%> ：</td><!-- 源客户经理 -->
			<td>
				<select name="sourceManagerID" style="width: 110px;" onclick="javascript:setManagerMan(this)">
					<%=Argument.getManagerListFromTCustManagers(input_man,sourceManagersList)%>
				</select>
			</td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.targetManager",clientLocale)%> ：</td><!-- 目标客户经理 -->
			<td>
				<select name="managerID" style="width: 110px;">
					<%=Argument.getManagerListFromTCustManagerTree(null,tagertManagersList)%>
				</select>                                                                              
				<span id="managerIdTip"><%if (!(tagertManagersList != null && tagertManagersList.size() != 0)) {out.print(LocalUtilis.language("message.noSameLevelManagerExist",clientLocale));isSameLevelManagerExist=false;}%></span>
			</td>
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
	<button type="button" <%if(!isSameLevelManagerExist){ %> disabled <%}%> class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
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