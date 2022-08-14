<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,enfo.crm.customer.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(-1));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
String shareDescription = "";
Integer ManagerID = new Integer(0);
Integer SourceManagerID = new Integer(0);
String start_time = "";
String invalid_time = "";
String cust_name = "";
String card_id = "";
String o_tel = "";
String h_tel = "";
String mobile = "";
String post_code = "";
String post_address = "";
String cust_type_name = "";
String card_type = "";
String service_man_name = "";
String e_mail = "";
String ManagerName = "";
String SourceManagerName = "";
//查询信息
AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();
List authList = null;
Map map = null;
CustomerLocal customer = EJBFactory.getCustomer();//客户
CustomerVO cust_vo = new CustomerVO();
List customerList = new ArrayList();
authList = authorizationShareLocal.load(serial_no);
if (authList.size()>0) {
	map = (Map)authList.get(0);
	cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(-1));
	shareDescription = Utility.trimNull(map.get("SharDescription"));
	start_time = Utility.trimNull(map.get("START_TIME"));
	invalid_time = Utility.trimNull(map.get("INVALID_TIME"));
	ManagerID = Utility.parseInt(Utility.trimNull(map.get("ManagerID")),new Integer(0));
	SourceManagerID = Utility.parseInt(Utility.trimNull(map.get("SourceManagerID")),new Integer(0));
	ManagerName = Utility.trimNull(map.get("MANAGER_NAME"));
	SourceManagerName = Utility.trimNull(map.get("SOURCE_MANAGER_NAME"));
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	customerList = customer.listByControl(cust_vo);
	//获得客户信息
	if(customerList.size()>0){
		Map cust_map = (Map)customerList.get(0);
		cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
		card_id = Utility.trimNull(cust_map.get("CARD_ID"));
		o_tel = Utility.trimNull(cust_map.get("O_TEL"));
		h_tel = Utility.trimNull(cust_map.get("H_TEL"));
		mobile = Utility.trimNull(cust_map.get("MOBILE"));
		post_code = Utility.trimNull(cust_map.get("POST_CODE"));
		post_address = Utility.trimNull(cust_map.get("POST_ADDRESS"));
		cust_type_name = Utility.trimNull(cust_map.get("CUST_TYPE_NAME"));
		card_type = Utility.trimNull(cust_map.get("CARD_TYPE"));
		service_man_name = Utility.trimNull(cust_map.get("SERVICE_MAN_NAME"));
		e_mail = Utility.trimNull(cust_map.get("E_MAIL"));
	}
}
//保存信息
if(request.getMethod().equals("POST")){
	shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
	ManagerID = Utility.parseInt(request.getParameter("manager_id"),new Integer(0));
	SourceManagerID = Utility.parseInt(request.getParameter("source_manager_id"),new Integer(0));
	start_time = request.getParameter("start_time");
	invalid_time = request.getParameter("invalid_time");
	authorizationShareVO.setSerial_no(serial_no);
	authorizationShareVO.setShareType(new Integer(3));
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setManagerID(ManagerID);
	authorizationShareVO.setSourceManagerID(SourceManagerID);
	authorizationShareVO.setInput_man(input_operatorCode);
	authorizationShareVO.setCust_id(cust_id);
	authorizationShareVO.setInvalid_time(invalid_time);
	authorizationShareVO.setStart_time(start_time);
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
<title><%=LocalUtilis.language("index.menu.addShare",clientLocale)%> </title><!-- 新增共享 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
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
	
	return sl_check_update();	
}

/*保存*/
function SaveAction(){
	var invalid_time = document.theform.invalid_time;
	var start_time = document.theform.start_time;
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

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/auth/auth_rapid_edit.jsp" onsubmit="javascript:return validateForm(this);">
<!--新增成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="cust_id" value="<%=cust_id%>" />
<input type="hidden" name="manager_id" value="<%=ManagerID%>"/>
<input type="hidden" name="source_manager_id" value="<%=SourceManagerID%>"/>
<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
<div class="page-title">
<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<div class="btn-wrapper"><b><font size=3>*<%=LocalUtilis.language("class.sourceManager",clientLocale)%> ： <%=SourceManagerName%></font></b></div>
<table border="0" width="100%" cellspacing="5" cellpadding="0" class="table-popup">
	<tr>
		<%if (cust_id.intValue()==0) {%>
			<td align="right">*客户 ：</td>
			<td align="left"><%=input_operatorName%>的所有客户</td>
		<%} else {%>
		<td colspan="2">		
		<table  border="0" width="100%" cellspacing="4" cellpadding="4" class="table-popup">
			<!--客户选择-->
			<tr>
				<td align="right"><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> :</td><!--客户信息-->
				<!--请选择-->
		        <td colspan="3">
					<button type="button" id ="cust_button" class="xpbutton3" accessKey=e name="btnEdit" 
						title='<%=LocalUtilis.language("message.customerInfomation",clientLocale)%>' 
						onclick="javascript:getCustomer(<%=cust_id%>);"><%=LocalUtilis.language("message.view",clientLocale)%> 
					</button><!--客户信息--><!--查看-->
				</td>
			<tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td> <!--客户名称-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=service_man_name%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="50" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="50"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td>  <!--公司电话-->
				<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="50"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</td><!--手机-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="50"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td>  <!--家庭电话-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="50"></td>
				<td align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</td> <!--电子邮件-->
				<td><input readonly class='edline' name="customer_email" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=e_mail%>" size="50"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="50" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
		</table>
		</td>
		<%} %>
	</tr>
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.targetManager",clientLocale)%> ：</td><!-- 目标客户经理 -->
		<td align="left"><%=ManagerName%></td>
	</tr>
	<tr>
		<td align="right">生效时间 ：</td>
		<td  align="left">
			<input type="text" name="start_time" id="start_time" size="30" readOnly value="<%=start_time%>"/>
		</td>
	</tr>	
	<tr>
		<td align="right">失效时间 ：</td>
		<td  align="left">
			<input type="text" name="invalid_time" id="invalid_time" size="30" readOnly value="<%=invalid_time%>"/>
		</td>
	</tr>
	<script language="javascript">
		laydate({elem:'#start_time',format:'YYYY-MM-DD hh:mm:ss',istime:true});
		laydate({elem:'#invalid_time',format:'YYYY-MM-DD hh:mm:ss',istime:true});
	</script>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.shareDescription",clientLocale)%> ：</td><!-- 授权描述 -->
		<td align="left">
			<textarea rows="3" name="shareDescription" onkeydown="javascript:nextKeyPress(this)" cols="62"><%=shareDescription%></textarea>			
		</td>
	</tr>
</table>
<br/>
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
authorizationShareLocal.remove();
customer.remove();
%>
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