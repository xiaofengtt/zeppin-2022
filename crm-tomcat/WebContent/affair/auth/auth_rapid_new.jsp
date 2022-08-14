<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc"%>
<%@ include file="/includes/operator.inc"%>
<%
boolean bSuccess = false;
boolean isSameLevelManagerExist=true;
AuthorizationVO authorizationVO = new AuthorizationVO();
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
AuthorizationShareLocal authorizationShareLocal = EJBFactory.getAuthorizationShareLocal();
Integer sel_all  = Utility.parseInt(request.getParameter("sel_all"),new Integer(0));
String customers = Utility.trimNull(request.getParameter("customers"));
Integer ca_id  = Utility.parseInt(request.getParameter("ca_id"),new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
String start_time = request.getParameter("start_time");
String invalid_time = request.getParameter("invalid_time");
String shareDescription = Utility.trimNull(request.getParameter("shareDescription"));
Integer sourceManagerID = Utility.parseInt(request.getParameter("sourceManagerID"),new Integer(0));
Integer managerID = Utility.parseInt(request.getParameter("managerID"),input_operatorCode);
Integer input_man = input_operatorCode;
Integer authorizationManagerID= Argument.getAuthorizationManagerID(ca_id);

authorizationVO.setManagerID(managerID);
authorizationVO.setInput_man(input_man);
List authorizationList=authorizationLocal.list_query(authorizationVO);
List sourceManagersList= tcustmanagers_Bean.getManagerList_sortByAuth(input_man);
List tagertManagersList = tcustmanagers_Bean.getManagerList_sortByAuth(new Integer(888));

//保存信息
if(request.getMethod().equals("POST")){	
	AuthorizationShareVO authorizationShareVO = new AuthorizationShareVO();
	authorizationShareVO.setShareType(new Integer(3));
	authorizationShareVO.setShareDescription(shareDescription);
	authorizationShareVO.setManagerID(managerID);
	authorizationShareVO.setSourceManagerID(sourceManagerID);
	authorizationShareVO.setInput_man(input_operatorCode);	
	authorizationShareVO.setInvalid_time(invalid_time);
	authorizationShareVO.setStart_time(start_time);
	
	if (sel_all.intValue()==1) { 
		Integer cust_id = new Integer(0);
		authorizationShareVO.setCust_id(cust_id);
	
		try {
			bSuccess = false;
			authorizationShareLocal.append(authorizationShareVO);
			bSuccess = true;
		} catch (Exception e){
			String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
			out.println("<script language=\"javascript\">alert('"+message+","+e.getMessage()+"');location.href='"+request.getContextPath()+"/affair/auth/auth_rapid_new.jsp';</script>");
		}		
	} else if (customers!=null) {
		String[] custIdString = customers.split(",");
		for (int i=0; i<custIdString.length; i++) {		
			Integer custId = Utility.parseInt(custIdString[i], new Integer(-1));
			authorizationShareVO.setCust_id(custId);
	
			try {
				bSuccess = false;
				authorizationShareLocal.append(authorizationShareVO);
				bSuccess = true;
			} catch (Exception e) {
				String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
				out.println("<script language=\"javascript\">alert('"+message+","+e.getMessage()+"');location.href='"+request.getContextPath()+"/affair/auth/auth_rapid_new.jsp';</script>");
			}
		}
	}
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.addShare", clientLocale)%></title>
<!-- 新增共享 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css"
	type=text/css rel=stylesheet>
<LINK
	href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css"
	type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="javascript"
	SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript"
	SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript"
	SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/CustomerInfo.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script language=javascript>
<% if(bSuccess){ %>
	location="<%=request.getContextPath()%>/affair/auth/auth_rapid.jsp";
<%}%>

var selected_customers = [];

function isExisting(arr, elem) {
	for (var i=0; i<arr.length; i++) {
		if (arr[i].cust_id == elem.cust_id) {
			return true;
		}
	}
	return false;
}

function checkAll(checkbox, chkboxArrName, win) {
	var aChkbox;
	if (win != null) 
		aChkbox = win.document.getElementsByName(chkboxArrName);
	else
		aChkbox = document.getElementsByName(chkboxArrName);

	for (var i=0; i<aChkbox.length; i++) 
		aChkbox[i].checked = checkbox.checked;		
}

function addAllCustomer() {
	selected_customers = [];

	var tbl = document.all.selected_customers;
	var row_count = tbl.rows.length; 	
	for (var i=0; i<row_count; i++)	
		tbl.deleteRow(0);	

	var row = tbl.insertRow(0);
	row.className = 'tr1';
	var cell = row.insertCell(0);
	cell.align = 'center';
	cell.colSpan = 3;
	cell.innerHTML = '本客户经理的所有客户';

	document.all.selected_list.style.height = '';
	document.all.selected_list.style.overflowY = '';
	document.theform.sel_all.value = '1';
}

function addCustomer() {
	var url = 'batch_add_customer.jsp';				
	var retval = showModalDialog(url,'', 'dialogWidth:650px;dialogHeight:700px;status:0;help:0');
	if (retval == null) return;

	var tbl = document.all.selected_customers;

	for (var i=0; i<retval.length; i++) {		
		if (! isExisting(selected_customers, retval[i])) {
			if (selected_customers.length==0) {
				tbl.deleteRow(0);
			}
			if (selected_customers.length==7) {
				document.all.selected_list.style.height = '185px';
				document.all.selected_list.style.overflowY = 'auto';
			}
			selected_customers.push(retval[i]);			
			var row = tbl.insertRow(tbl.rows.length);
			row.className = 'tr1';
			var cell = row.insertCell(0);
			cell.align = 'left';
			cell.width = '35%';
			cell.innerHTML = '<input type="checkbox" name="cust_id" class="flatcheckbox" value="'+retval[i].cust_id+'"/>&nbsp;&nbsp;' + retval[i].cust_no;
			cell = row.insertCell(1);
			cell.align = 'left';
			cell.width = '47%';
			cell.innerHTML = retval[i].cust_name; 
			cell = row.insertCell(2);
			cell.align = 'center';
			cell.width = '18%';
			cell.innerHTML = retval[i].cust_type_name; 			
		}
	}
	if (selected_customers.length>0) {
		document.theform.sel_all.value = '0';
	}
}

function delCustomer() {
	var index = [];
	var check = document.getElementsByName("cust_id");
	for(var i=0; i != check.length; i++ ){
		if(check[i].checked){
			index.push(i);
		}
	}

	if (index.length==0) {
		alert("<%=LocalUtilis.language("message.deleteDate",clientLocale)%> ！");//请选择要删除的数据
		return;
	}

	if (confirm("<%=LocalUtilis.language("message.confirmDelete",clientLocale)%> ？")){//确认删除
		var tbl = document.all.selected_customers;
		for (var i=0; i<index.length; i++) {			
			selected_customers.splice(index[i]-i, 1);
			tbl.deleteRow(index[i]-i);
		}
		if (selected_customers.length==0) {
			var row = tbl.insertRow(0);
			row.className = 'tr1';
			var cell = row.insertCell(0);
			cell.align = 'center';
			cell.colSpan = 3;
			cell.innerHTML = '空';
		}
		if (selected_customers.length<=7) {
			document.all.selected_list.style.height = '';
			document.all.selected_list.style.overflowY = '';
		}
	}	
}

/*验证数据*/
function validateForm(form){	
	var dest_custman = document.theform.managerID;
	var invalid_time = document.theform.invalid_time;
	var start_time = document.theform.start_time;
	var errmsg = '';

	var cust_id = document.getElementsByName('cust_id');
	var startTime = Date.parse(new Date(start_time.value.replace(/-/g,"/")));
	var endTime = Date.parse(new Date(invalid_time.value.replace(/-/g,"/")));
	document.theform.customers.value = '';
	for (var i=0; i<cust_id.length; i++) {
		if (i==0)
			document.theform.customers.value += cust_id[i].value;	
		else 
			document.theform.customers.value += ','+cust_id[i].value;
	}

	var sel_all = document.theform.sel_all.value;
	if (sel_all=='0' && cust_id.length==0) {
		errmsg += '未选定任何客户！请选择要授权的客户！\n';
	}
	if (dest_custman.value == '') {
		errmsg += '未选定目标客户经理！请选择要授权给的目标客户经理！\n';
	}
	if (start_time.value == '') {
		errmsg += '未指定授权生效时间！请选择授权生效时间！\n';
	}
	if (invalid_time.value == '') {
		errmsg += '未指定授权失效时间！请选择授权失效时间！\n';
	}
	if(startTime > endTime){
		errmsg += '生效时间不能大于失效时间！\n';
	}
	if(startTime == endTime){
		errmsg += '生效时间不能等于失效时间！\n';
	}
	if (errmsg!='') {
		sl_alert(errmsg);
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
<BODY class="BODY body-nox">
<%if (!(tagertManagersList != null && tagertManagersList.size() != 0)) {%>
<script language="javascript">
	//sl_alert('您没有可以授权的同级别客户经理，所以不能授权！');
	//location.href = 'auth_rapid.jsp';
</script>
<%} %>

<form name="theform" method="post"
	action="<%=request.getContextPath()%>/affair/auth/auth_rapid_new.jsp"
	onsubmit="javascript:return validateForm(this);"><!--新增成功标志--> <input
	type="hidden" id="bSuccess" value="<%=bSuccess%>" /> <input
	type="hidden" name="customer_cust_id" value="" /> 

<div class="page-title"> <font color="#215dc6"><b><%=menu_info%></b></font></div>
<br/>
<table border="0" width="1000px" cellspacing="5" cellpadding="0" class="product-list">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.sourceManager", clientLocale)%>
		：</td>
		<!-- 源客户经理 -->
		<td align="left"><input style="width:80px;" class="edline"
			value="<%=input_operatorName%>" readonly="readonly" /> <input
			type="hidden" name="sourceManagerID" value="<%=input_operatorCode%>" />
		</td>
	</tr>

	<tr>
		<td align="right">客户选择 ：</td>
		<td align="left">
			<input type="hidden" name="sel_all" value="0"/>
			<button type="button"  class="xpbutton3" accessKey=a title="添加当前客户经理所有的客户" onclick="javascript:addAllCustomer();">所有客户(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;&nbsp;			
			<button type="button"  class="xpbutton3" accessKey=n title="<%=LocalUtilis.language("message.addCustomer",clientLocale)%> " onclick="javascript:addCustomer();"><%=LocalUtilis.language("message.add",clientLocale)%>(<u>N</u>)</button><!-- 添加客户 -->
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=k title="<%=LocalUtilis.language("message.deleteCustomer",clientLocale)%> " onclick="javascript:delCustomer();"><%=LocalUtilis.language("message.delete",clientLocale)%>(<u>K</u>)</button><!-- 删除客户 -->	
		</td>		
	</tr>

	<tr>
		<td align="right">*已选择客户 ：</td>
		<td align="left">
			<input type="hidden" name="customers"/>
			<table width="489px" cellspacing="1" cellpadding="2">
				<tr class="trh">
					<td align="center" width="35%">
						<input type="checkbox" class="selectAllBox"	
						onclick="javascript:checkAll(this, 'cust_id');"><%=LocalUtilis.language("class.customerID",clientLocale)%> <!-- 客户编号 -->
					</td>
					<td align="center" width="47%"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- 客户名称 -->
					<td align="center" width="18%"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!-- 客户类别 -->
				</tr>  
			</table>
			<span id="selected_list">
				<table id="selected_customers" border="0" width="489px" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC">					
					<tr class="tr1"><td align="center" colspan="3">空</tr>
				</table>
			</span>
		</td>		
	</tr>

	<tr>
		<td align="right">*<%=LocalUtilis.language("class.targetManager", clientLocale)%>
		：</td>
		<!-- 目标客户经理 -->
		<td align="left"><select name="managerID" style="width: 110px;">
				<%=Argument.getManager_Value(new Integer(0))%>
		</select></td>
	</tr>
	<tr>
		<td align="right">*生效时间 ：</td>
		<td align="left"><input type="text" name="start_time"
			id="start_time" size="30" readOnly /></td>
	</tr>
	<tr>
		<td align="right">*失效时间 ：</td>
		<td align="left"><input type="text" name="invalid_time"
			id="invalid_time" size="30" readOnly /></td>
	</tr>
	<script language="javascript">
		laydate({elem:'#start_time',format:'YYYY-MM-DD hh:mm:ss',istime:true});
		laydate({elem:'#invalid_time',format:'YYYY-MM-DD hh:mm:ss',istime:true});
	</script>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.shareDescription", clientLocale)%>
		：</td>
		<!-- 授权描述 -->
		<td align="left"><textarea rows="3" name="shareDescription"
			onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea></td>
	</tr>
</table>

<div align="right"><br>
<!-- 保存 -->
<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave"
	onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save", clientLocale)%>
(<u>S</u>)</button>
&nbsp;&nbsp;&nbsp;&nbsp; <!-- 返回 -->
<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel"
	onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back", clientLocale)%>
(<u>C</u>)</button>
&nbsp;&nbsp;</div>
</form>
<%
		authorizationLocal.remove();
		authorizationShareLocal.remove();
	%>
</BODY>
</HTML>
