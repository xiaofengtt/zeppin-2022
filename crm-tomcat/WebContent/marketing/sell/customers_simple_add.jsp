<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<fieldset>
<legend>�ͻ���Ϣ</legend>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
	<tr>
		<td align="right">*�ͻ�����:</td>
		<td colspan="3">
			<input maxlength="100" name="customer_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
	<tr>
		<td align="right" width="13%">*�ͻ�����:</td>
		<td width="46%">
			<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">��ѡ��</option>
				<option value="1">����</option>
				<option value="2">����</option>
			</select>
		</td>
		<td align="right" width="13%">*��ϵ�绰:</td>
		<td width="28%">
			<input maxlength="100" name="cust_moble" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
	<tr>
		<td align="right">֤������:</td>
		<td >
			<select onkeydown="javascript:nextKeyPress(this)"name="card_type" style="WIDTH: 160px">
				<%=Argument.getCardTypeOptions2(String.valueOf(""))%>
			</select>
		</td>
		<td align="right">֤������:</td>
		<td >
			<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
		</td>
	</tr>
</table>
</fieldset>
