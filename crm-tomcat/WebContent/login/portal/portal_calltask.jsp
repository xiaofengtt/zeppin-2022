<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>

<script language="javascript">
function callAaction(tel){
	if(sl_confirm("确定要拨打电话")){//您要拨打的电话号码是
		document.getElementById("phoneNumber").value = tel;
		var url = "<%=request.getContextPath()%>/callcenter/callingRecord.jsp";
		document.theform.action = url;
		document.theform.submit();
	}
}
</script>

<input type="hidden" name="phoneNumber" id="phoneNumber" value='' />
<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
	<tr class="tr0" bgcolor="#E0EEEE">
		<td align="center" width="*">序号</td>
		<td align="center" width="*">客户名称</td>
		<td align="center" width="*">来电号码</td>
		<td align="center" width="*">拨打时间</td>
		<td align="center" width="*">...</td>
		<td align="center" width="*">回拨</td>
	</tr>
</table>
<div align="right"><a href="#" class="a2" onclick="javascript:void(0);">更多>></a></div>