<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();
CustomerLocal customerLocal = EJBFactory.getCustomer();
String[] serial_no = request.getParameterValues("serial");
String managerName = "";

//保存信息
if(request.getMethod().equals("POST")){		
	String shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
	String invalid_time = request.getParameter("invalid_time");
	String start_time = request.getParameter("start_time");
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setInvalid_time(invalid_time);
	authorizationShareVO.setStart_time(start_time);
	authorizationShareVO.setShareType(new Integer(3));
	authorizationShareVO.setInput_man(input_operatorCode);
	String message = "";
	
	if (serial_no!=null) {
		for (int i=0; i<serial_no.length; i++) {	
			Integer serial  = Utility.parseInt(serial_no[i], new Integer(-1));	
			List authList = authorizationShareLocal.load(serial);
			if (authList.size()>0) {
				Map map = (Map)authList.get(0);
				Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(-1));	
				Integer source_manager_id = Utility.parseInt(Utility.trimNull(map.get("SourceManagerID")),new Integer(0));	
				Integer manager_id = Utility.parseInt(Utility.trimNull(map.get("ManagerID")),new Integer(0));		
 
				authorizationShareVO.setManagerID(manager_id);
				authorizationShareVO.setSourceManagerID(source_manager_id);				
				authorizationShareVO.setCust_id(cust_id);		
				authorizationShareVO.setSerial_no(serial);
	
				try{
					authorizationShareLocal.modi(authorizationShareVO);
					
				}catch(Exception e){
					message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
					out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
				}			
			}		

		}
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

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script language=javascript>
//查看客户信息
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:600px;status:0;help:0;');
}

/*验证数据*/
function validateForm(form){	
	var start_time = document.theform.start_time;

	if (start_time.value == '') {
		sl_alert('未指定授权生效时间！请选择授权生效时间！');
		return false;
	}
	
	var invalid_time = document.theform.invalid_time;

	if (invalid_time.value == '') {
		sl_alert('未指定授权失效时间！请选择授权失效时间！');
		return false;
	}
	
	var startTime = Date.parse(new Date(start_time.value.replace(/-/g,"/")));
	var endTime = Date.parse(new Date(invalid_time.value.replace(/-/g,"/")));
	if(startTime > endTime){
		alert('生效时间不能大于失效时间！');
		return false;
	}
	if(startTime == endTime){
		alert('生效时间不能等于失效时间！');
		return false;
	}

	return sl_check_update();	
}

/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*取消*/
function CancelAction(){
	location="auth_rapid.jsp";
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
		document.getElementById("btnSave").disabled = false;
	}else{
		document.getElementById("managerIdTip").innerHTML = "<%=LocalUtilis.language("message.noSameLevelManagerExist",clientLocale)%>";
		DWRUtil.removeAllOptions(document.theform.managerID);
		DWRUtil.addOptions(document.theform.managerID,{'':'请选择'});
		document.getElementById("btnSave").disabled = true;
	}
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/auth_rapid_batch_edit.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
<font color="#215dc6"><b><%=menu_info%></b></font>
<hr noshade color="#808080" size="1">
<table border="0" width="1000px" cellspacing="5" cellpadding="0">
	<tr>
		<td align="right" width="10%">*<%=LocalUtilis.language("class.sourceManager",clientLocale)%> ：</td><!-- 源客户经理 -->
		<td align="left" width="20%"><%=input_operatorName%><input type="hidden" name="sourceManagerID" value="<%=input_operatorCode%>"/></td>
		<td align="left" width="70%">&nbsp;</td>
	</tr>

	<tr>
		<td align="right">客户 ：</td>
		<td align="left">
			<table width="100%" cellspacing="4" cellpadding="4" style="margin-top:5px;">	
			<%if (serial_no!=null) {
				for (int i=0; i<serial_no.length; i++) {							
					Integer serial  = Utility.parseInt(serial_no[i], new Integer(-1));
					List authList = authorizationShareLocal.load(serial);
					if (authList.size()>0) {
						Map map = (Map)authList.get(0);
						Integer manager_id = Utility.parseInt(Utility.trimNull(map.get("ManagerID")),new Integer(0));	
						managerName = Utility.trimNull(map.get("MANAGER_NAME"));

						Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(-1));%>	
				
						<tr>
							<td align="center" width="60%" style="border-left:1px dashed blue;border-right:1px dashed blue;
							<%if (i==0) {%>
								border-top:1px dashed blue;							
							<%} 
							if (i==serial_no.length-1) { %>
								 border-bottom:1px dashed blue;
							<%}%>"
							>
							<%if (cust_id.intValue()!=0) {
								CustomerVO cust_vo = new CustomerVO();
								cust_vo.setCust_id(cust_id);
								cust_vo.setInput_man(input_operatorCode);
								List customerList = customerLocal.listByControl(cust_vo);						
								//获得客户信息
								if(customerList.size()>0){
									Map cust_map = (Map)customerList.get(0);
									String cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
									//String card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
									//String card_id = Utility.trimNull(map.get("CARD_ID")); 
								%>								
									<a href="javascript:getCustomer(<%=cust_id%>);"><%=cust_name/*+"-"+card_type_name+"-"+card_id*/%></a>
								<%}
							} else { %>
								<%=input_operatorName+"的所有客户"%>
							<%} %>
								<input type="hidden" name="serial" value="<%=serial%>"/>
							</td>
							<td width="5%">&nbsp;</td>
							<td align="center" width="35%" style="border-left:1px dashed blue;border-right:1px dashed blue;
							<%if (i==0) {%>
								border-top:1px dashed blue;							
							<%} 
							if (i==serial_no.length-1) { %>
								 border-bottom:1px dashed blue;
							<%}%>"
							><%=managerName%></td>
						</tr>
						<%}
					}		
				}%>
			</table>
		</td>

		<td align="left"> ：<%=LocalUtilis.language("class.targetManager",clientLocale)%>*</td>
	</tr>
	<tr>
		<td align="right">生效时间 ：</td>
		<td  align="left" colspan="2">
			<input type="text" name="start_time" id="start_time" size="30" onclick="javascript:setday(this);" readOnly value=""/>
		</td>
	</tr>
	<tr>
		<td align="right">失效时间 ：</td>
		<td  align="left" colspan="2">
			<input type="text" name="invalid_time" id="invalid_time" size="30" onclick="javascript:setday(this);" readOnly value=""/> 		
		</td>
	</tr>	
	<tr>
		<td align="right"><%=LocalUtilis.language("class.shareDescription",clientLocale)%> ：</td><!-- 授权描述 -->
		<td align="left" colspan="2">
			<textarea rows="3" name="shareDescription" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
		</td>
	</tr>
</table>

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
<%
customerLocal.remove();
authorizationShareLocal.remove(); %>
<script language=javascript>
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		
		if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
			window.location.href="auth_rapid.jsp"
		}
	}
</script>
</BODY>
</HTML>

