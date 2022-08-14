<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//声明对象
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();

IPageList pageList = local.listCustDue(input_operatorCode,1,5);
//分页辅助参数
List list = pageList.getRsList();
Map map = null;
%>
<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
	<tr class="tr0" bgcolor="#E0EEEE">
		<td align="center" width="*">客户名称</td>
		<td align="center" width="*">存量金额</td>
		<td align="center" width="*">到期产品</td>
		<td align="center" width="*">到期日期</td>
		<td align="center" width="*">累计认购金额</td>
	</tr>
	<%
		Iterator it = list.iterator();
		BigDecimal ben_amount = new BigDecimal(0);
		BigDecimal rg_money = new BigDecimal(0);
		int ben_end_date = 0;
		int iCount = 0;

		while(it.hasNext()){
			map = (HashMap)it.next();
			ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0));			
			ben_end_date = Utility.parseInt(Utility.trimNull(map.get("BEN_END_DATE")),0);
			rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0));
			iCount++;
			if(iCount>5){
				break;
			}
	%>
		<tr class="tr0" bgcolor="#E0EEEE">
			<td align="center" width="*"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center" width="*"><%=Format.formatMoney(ben_amount)%></td>
			<td align="center" width="*"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td>
			<td align="center" width="*"><%=Format.formatDateCn(ben_end_date)%></td>
			<td align="center" width="*"><%=Format.formatMoney(rg_money)%></td>
		</tr>
	<%}%>
 </table>
<div align="right"><a href="#" class="a2" onclick="javascript:showCustDue();">更多>></a></div>

<script language="javascript">
function showCustDue(){
	var url = "<%=request.getContextPath()%>/login/portal/portal_custdue_list.jsp";
	showModalDialog(url,'', 'dialogWidth:850px;dialogHeight:450px;status:0;help:0');	
}
</script>
