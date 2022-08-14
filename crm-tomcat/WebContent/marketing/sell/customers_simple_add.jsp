<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<fieldset>
<legend>客户信息</legend>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
	<tr>
		<td align="right">*客户名称:</td>
		<td colspan="3">
			<input maxlength="100" name="customer_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
	<tr>
		<td align="right" width="13%">*客户类型:</td>
		<td width="46%">
			<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">请选择</option>
				<option value="1">个人</option>
				<option value="2">机构</option>
			</select>
		</td>
		<td align="right" width="13%">*联系电话:</td>
		<td width="28%">
			<input maxlength="100" name="cust_moble" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
	<tr>
		<td align="right">证件类型:</td>
		<td >
			<select onkeydown="javascript:nextKeyPress(this)"name="card_type" style="WIDTH: 160px">
				<%=Argument.getCardTypeOptions2(String.valueOf(""))%>
			</select>
		</td>
		<td align="right">证件号码:</td>
		<td >
			<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
</table>
</fieldset>
