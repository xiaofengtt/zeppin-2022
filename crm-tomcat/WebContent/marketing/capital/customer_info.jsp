<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
Integer customer_cust_type = Utility.parseInt(request.getParameter("customer_cust_type"), null);
String customer_cust_name = request.getParameter("customer_cust_name");
IntrustEntCustomerLocal cust = EJBFactory.getIntrustEntCustomer();
IntrustEntCustomerLocal customer = EJBFactory.getIntrustEntCustomer();

String sReadonly = "readonly class='edline'";
if ("new".equals(request.getParameter("task"))){
	sReadonly = "";
	customer.setCust_type(customer_cust_type);
	customer.setCust_name(customer_cust_name);
}
	

String prefix = request.getParameter("prefix");
String cust_code = request.getParameter("cust_code");
String cust_name = request.getParameter("cust_name");
if (cust_code != null && cust_name != null)
{
	cust.setCust_id(new Integer(0));
	cust.setCust_code(cust_code);
	cust.setCust_name(cust_name);
	cust.setOperator(input_operatorCode);
	cust.setBook(input_bookCode);
	cust.list();
}

boolean bSuccess = false;

if (request.getMethod().equals("POST"))
{
	customer.setBook(input_bookCode);
	customer.setInput_man(input_operatorCode);
	customer.setProperties(request, "customer");
	customer.addNewEntCustomer();
	cust_id = customer.getCust_id();
	bSuccess = true;
}

if (cust_id != null)
{
	if(cust_id.intValue()>0)
	{
		customer.setCust_id(cust_id);
		customer.load();		
	}	
}
%>

<HTML>
<HEAD>
<TITLE>企业客户选择</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<BASE TARGET="_self">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript">
function validateForm(form)
{
	if (document.theform.task.value == "new")
	{
		if(isNull(document.theform.customer_cust_name.value)) 
			{
				sl_alert("客户名称不能为空!");
				document.theform.customer_cust_name.focus();
				return false;
			}
		if(document.theform.customer_cust_type.value == 1){
			if(!sl_checkChoice(document.theform.customer_card_type, "证件类型"))	return false;
			if(isNull(document.theform.customer_card_code.value)) 
			{
				sl_alert("证件号码不能为空!");
				document.theform.customer_card_code.focus();
				return false;
			}
		}
		if(document.theform.customer_cust_type.value == 2){
			if(!sl_checkChoice(document.theform.customer_ent_type, "企业性质"))	return false;
			if(isNull(document.theform.customer_card_code.value)) 
			{
				sl_alert("证件号码不能为空!");
				document.theform.customer_card_code.focus();
				return false;
			}
		}
		if(!sl_checkChoice(document.theform.customer_bank_id, "银行名称"))	return false;
		if(isNull(document.theform.customer_card_id.value))
		{
			sl_alert("贷款卡号不能为空!");
			document.theform.customer_card_id.focus();
			return false;
		}
		if(!validateCustomer("customer"))	return false;
		return sl_check_update("<%=cust_id%>");
	}
	else
	{
		if(!sl_checkChoice(document.theform.cust_id, "企业客户"))	return false;
		return true;	
	}
		
}

function newInfo()
{
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "new";
	document.theform.submit();
}

function queryInfo()
{
	document.theform.cust_id.value = "";
	document.theform.method = "get";
	document.theform.task.value = "list";
	document.theform.submit();
}

function okInfo()
{
	if(!document.theform.onsubmit()) return false;
	if (document.theform.task.value == "new")
	{
		document.theform.task.value = "";
		document.theform.submit();
	}
	else
	{
		var v = new Array();
		v[0] = document.theform.cust_id.value;
		v[1] = document.theform.customer_cust_name.value;
		window.returnValue = v;
		window.close();
	}
}

function changeCustomer(cust_id)
{
	if (cust_id != "")
	{
		document.theform.method = "get";
		document.theform.task.value = "list";
		document.theform.submit();
	}
}

function optimizeForm()
{
	if (document.theform.task.value == "new")
		document.theform.customer_cust_name.focus();
	else if (document.theform.task.value == "list")
		document.theform.cust_id.focus();
	else
		document.theform.cust_code.focus();
}

function setCustRelation(){
	var cust_id = document.getElementsByName("cust_id")[0].value;
	var cust_name = document.getElementsByName("customer_cust_name")[0].value;
	var url = "customer_relation_info.jsp?cust_id="+cust_id+"&cust_name="+cust_name + "&isDk=0";
	var ret = showModalDialog(url,'','dialogWidth:850px;dialogHeight:450px;status:0;help:0');
	document.getElementById("relation_flag").value = ret;
}

function setTitle(value){
	document.theform.method = "get";
	document.theform.task.value = "new";
	document.theform.submit();
}

<%if (bSuccess){%>
	sl_update_ok();
<%}%>
</script>
<BODY class="body"  onkeydown="javascript:chachEsc(window.event.keyCode)" onload="javascript:optimizeForm()">
<form name="theform" method="post" action="customer_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="prefix" value="<%=Utility.trimNull(prefix)%>">
<input type="hidden" name="task" value="<%=Utility.trimNull(request.getParameter("task"))%>">
<input type="hidden" name="is_dialog" value="1">
<!-- relation_flag 表示是否有股东信息：0表示没有 非零表示股东个数 -->
<input type="hidden" name="relation_flag" id="relation_flag" value="0">

<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0 class="product-list">
	<TBODY>
		<TR>
			<TD>

			<table border="0" width="100%" cellspacing="0" cellpadding="4">
				<tr>
					<td align="right">企业代码:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text" id="keyword" name="cust_code" size="18" value="<%=Utility.trimNull(request.getParameter("cust_code"))%>"></td>
					<td align="right">客户名称:</td>
					<td><input onkeydown="javascript:nextKeyPress(this)" type="text" id="keyword" name="cust_name" size="18" value="<%=Utility.trimNull(request.getParameter("cust_name"))%>"></td>
					<td colspan="2"><button type="button"  class="xpbutton2" onclick="javascript:queryInfo();">查询</button></td>
				</tr>
				<tr <%if (sReadonly.equals("")) out.print("style='display:none'");%>>
					<td align="right">搜索结果:</td>
					<td colspan="5">
					<select onkeydown="javascript:nextKeyPress(this)" size="1" name="cust_id" style="width: 460px" onchange="javascript: changeCustomer(this.value);">
						<option value="">请选择</option>
<%if (!"list".equals(request.getParameter("task")) && cust_id != null){
					if(cust_id.intValue()>0){
						%>
						<option selected value="<%=cust_id%>"><%=Utility.trimNull(customer.getCust_code()) + " - " + Utility.trimNull(customer.getCust_name())%></option>
<%}}%>

<%
while (cust.getNext())
{
%>
						<option <%if(cust.getCust_id().equals(cust_id)) out.print("selected");%> value="<%=cust.getCust_id()%>"><%=Utility.trimNull(cust.getCust_code()) + " - " + Utility.trimNull(cust.getCust_name())%></option>
<%}%>
					</select>
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
				<tr>
					<td align="right">*客户名称:</td>
					<td colspan=3><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_name" size="50" maxlength="60" value="<%=Utility.trimNull(customer.getCust_name())%>"></td>
					<td align="right">*<%=LocalUtilis.language("class.custType",clientLocale)%> :</td><!--客户类型-->
					<td colspan=1>						
						<input <%if (!sReadonly.equals("")) out.print(sReadonly); else out.print("type='hidden'");%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_type_name" size="18" maxlength="20" value="<%if(customer.getCust_type()!=null){if(customer.getCust_type().intValue()==1) out.print("个人");else if(customer.getCust_type().intValue()==2) out.print("机构");}%>">
						<SELECT size="1"  <%if (!sReadonly.equals("")) out.print("style='display:none'");%> onchange='javascript:setTitle(this.value);' name="customer_cust_type" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 120px">
							<%=Argument.getCustTypeOptions2(customer.getCust_type())%>
						</SELECT>	
					</td>
				</tr>
				<%if(Utility.trimNull(customer.getCust_type()).equalsIgnoreCase("1")){ %>
				<tr>	
					<td align="right">证件类型:</td>
					<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_card_type_name" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getCard_type_name())%>">
					<%}else{%>
						<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_card_type" style="width: 110px">
							<%=Argument.getCardType(customer.getEnt_type())%>
						</select>
					<%}%>
					</td>
					<td align="right">证件号码:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_card_code" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getCard_code())%>"></td>					
				</tr>
				<%}else{%>
				<tr>	
					<td align="right">企业代码:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_cust_code" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getCust_code())%>"></td>
					<td align="right">企业性质:</td>
					<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_ent_type_name" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getEnt_type_name())%>">
					<%}else{%>
						<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_ent_type" style="width: 110px">
							<%=Argument.getCompanyTypeOptions(customer.getEnt_type())%>
						</select>
					<%}%>
					</td>				
				</tr>
				<%} %>
				<tr>
					<td align="right">开户银行:</td>
					<td>
					<%if (!sReadonly.equals("")){%>
						<input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_bank_name" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getBank_name())%>">
					<%}else{%>
						<select onkeydown="javascript:nextKeyPress(this)" size="1" name="customer_bank_id" style="width: 110px">
							<%=Argument.getBankOptions(customer.getBank_id())%>
						</select>
					<%}%>
					</td>
					<td align="right">支行名称:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_bank_sub_name" size="18" maxlength="30" value="<%=Utility.trimNull(customer.getBank_sub_name())%>"></td>
				
					<td align="right">银行账号:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_bank_acct" size="18" maxlength="30" value="<%=Utility.trimNull(customer.getBank_acct())%>"></td>
				</tr>
				<tr>
					<td align="right">贷款卡号:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_card_id" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getCard_id())%>"></td>	
			
					<td align="right">注册地址:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_reg_address" size="18" maxlength="60" value="<%=Utility.trimNull(customer.getReg_address())%>"></td>
					<td align="right">邮政编码:</td>
					<td ><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_reg_postcode" size="18" maxlength="6" value="<%=Utility.trimNull(customer.getReg_postcode())%>"></td>
				</tr>
				<tr>
					<td align="right">通讯地址:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_address" size="18" maxlength="60" value="<%=Utility.trimNull(customer.getAddress())%>"></td>
					<td align="right">邮政编码:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_postcode" size="18" maxlength="6" value="<%=Utility.trimNull(customer.getPostcode())%>"></td>
					<td align="right">联系人:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_link_man" size="18" maxlength="10" value="<%=Utility.trimNull(customer.getLink_man())%>"></td>
				</tr>
				<tr>
					<td align="right">电话:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_telphone" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getTelphone())%>"></td>
					<td align="right">传真:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_fax" size="18" maxlength="20" value="<%=Utility.trimNull(customer.getFax())%>"></td>
					<td align="right">Email:</td>
					<td><input <%=sReadonly%> onkeydown="javascript:nextKeyPress(this)" name="customer_email" size="18" maxlength="30" value="<%=Utility.trimNull(customer.getEmail())%>"></td>
				</tr>
				<tr>
					<td valign="top" align="right">备注:</td>
					<td colspan="5"><textarea <%if (!sReadonly.equals("")) out.print("readonly class='edline_textarea'");%> rows="3" name="customer_summary" cols="80"><%=Utility.trimNull(customer.getSummary())%></textarea></td>
				</tr>
				<tr>
					<td align="right" colspan="6">
					<table border="0" width="100%">
						<tr>
							<td align="right">
							<%if("2".equalsIgnoreCase(Utility.trimNull(customer.getCust_type()))&&Utility.parseInt(customer.getCust_id(),new Integer(0)).intValue()>0){%>
							<button type="button"  class="xpbutton5" id="btnSet" name="btnSet" onclick="javascript:setCustRelation();" ><%=LocalUtilis.language("message.partnerSet",clientLocale)%> <!--股东设置--></button>
							&nbsp;&nbsp;
							<%}%>
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:okInfo();">确定(<u>S</u>)</button>
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
							&nbsp;&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
<%
cust.remove();
customer.remove();
%>
