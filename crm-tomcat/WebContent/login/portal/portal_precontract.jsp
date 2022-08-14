<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>

<%
//��������
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();
//���۲�ƷԤԼ��Ϣ
pre_vo.setStatus("110202");
pre_vo.setInput_man(input_operatorCode);
List preList  = preContract.getProductPreList(pre_vo);		 
Map preMap = null;
int pre_len = 5<preList.size()?5:preList.size();

if(pre_len ==0){
%>
	<div align="left" style="margin-left:20px;margin-top:10px;">û�����۲�ƷԤԼ��Ϣ ��</div>
<%}else{%>
	<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
		<tr class="tr0" bgcolor="#E0EEEE">
			<td align="center" width="*">��Ʒ����</td>
			<td align="center" width="*">���й�ģ</td>
			<td align="center" width="*">ԤԼ��ʼʱ��</td>
			<td align="center" width="*">ԤԼ��ֹʱ��</td>
			<td align="center" width="*">���з�ʽ</td>
			<td align="center" width="*">��ԤԼ��ģ</td>
			<td align="center" width="*">���˹�ģ</td>
			<td align="center" width="*">С�����</td>
			<td align="center" width="*">ʣ��ԤԼ��ģ</td>
			<td align="center" width="*">���ԤԼ(��Ч��/����)</td>
			<td align="center" width="*">����</td>
		</tr>
<%
	for(int i=0;i<pre_len;i++){
		preMap = (Map)preList.get(i);
		Integer preproduct_id = Utility.parseInt(Utility.trimNull(preMap.get("PREPRODUCT_ID")),new Integer(0));
		double pre_max_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_MONEY")),new BigDecimal(0),2,"1").doubleValue();
		double pre_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_FACT_MONEY")),new BigDecimal(0),2,"1").doubleValue();
		double rg_money = Utility.parseDecimal(Utility.trimNull(preMap.get("RG_MONEY")),new BigDecimal(0),2,"1").doubleValue();
		double available_money = pre_max_money - pre_money;
		int direct_sale = Utility.parseInt(Utility.trimNull(preMap.get("DIRECT_SALE")),0);
		BigDecimal max_ratio = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(preMap.get("MAX_RATIO"))), new BigDecimal(0)).setScale(6, BigDecimal.ROUND_HALF_UP);
		BigDecimal stand_ratio = new BigDecimal(0.800000);
%>
		<tr class="tr1">
			<td align="center" width="*"><a href="<%=basePath%>/wiki/product_info_view.jsp?preproduct_id=<%=preproduct_id%>" class="a2" title="�鿴��Ʒ��Ϣ"><%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%></a></td>
			<td align="right" width="*"><%=Format.formatMoney(pre_max_money)%></td>
			<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_START_DATE")),new Integer(0)))%></td>
			<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_END_DATE")),new Integer(0)))%></td>
			<td align="center" width="*"><%if(direct_sale == 1) out.print("����");else if(direct_sale == 2) out.print("ֱ��");else if(direct_sale == 3) out.print("ֱ��&����");%></td>
			<td align="right" width="*"><a href="<%=basePath%>/marketing/sell/product_pre_total_team.jsp?pre_product_id=<%=preproduct_id%>&pre_product_name=<%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%>&pre_money=<%=Utility.trimNull(Format.formatMoney(pre_money))%>" class="a2" title="�鿴��ƷԤԼͳ����Ϣ"><font color="<%=max_ratio.compareTo(stand_ratio) == 1 ? "red" : "" %>"><%=Format.formatMoney(pre_money)%></font></a></td>
			<td align="right" width="*"><%=Format.formatMoney(rg_money)%></td>
			<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("LESS300_NUMBER")),new Integer(0))%></td>
			<td align="right" width="*"><%=Format.formatMoney(available_money)%></td>
			<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_VALID_COUNT")),0)%> / <%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_COUNT")),0)%></td>
				<td align="center" width="*">
   					<a href="<%=basePath%>/marketing/sell/direct_reserve_add.jsp?pre_product_id=<%=preproduct_id%>" class="a2" title="��ƷԤԼ">ԤԼ</a>
			</td>
		</tr> 
	<%}%>
	</table>
<%}%>
	<div align="right"><a href="<%=basePath%>/marketing/sell/reserve_list.jsp" class="a2">����>></a></div>
