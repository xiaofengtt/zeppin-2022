<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//��������
OperatorLocal local = EJBFactory.getOperator();
TOperatorVO vo = new TOperatorVO();
String[] ItemValue = null;
String[] ItemName = null;
String[] PercentageValue = null;
List list = null;
Map rowMap = null;
//ͼ��
FusionCharts Chart = null;
FusionChartsGanerate ChartCreator = null;
String chartHTMLCode = "";//�����Ŷӱ���
String XMLStr = "";

//��ѯ
vo.setOp_code(input_operatorCode);
vo.setQuery_date(new Integer(Utility.getCurrentDate()));
list = local.StatTeamRank(vo);

%>

 <table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
	<tr class="tr0" bgcolor="#E0EEEE">
		<td align="center" width="*">����</td>
		<td align="center" width="*">�Ŷ�����</td>
		<td align="center" width="*">�ŶӸ�����</td>
		<td align="center" width="*">�����ۼ�</td>
		<td align="center" width="*">�����ۼ�</td>
	</tr>
	<%
		Iterator it = list.iterator();
		int iCount = 0;
		BigDecimal year_amount = new BigDecimal(0);
		BigDecimal month_amount = new BigDecimal(0);
		ItemValue = new String[list.size()];
		ItemName = new String[list.size()];
		PercentageValue = new String[list.size()];		

		while(it.hasNext()){
			rowMap = (HashMap)it.next();			

			year_amount =  Utility.parseDecimal(Utility.trimNull(rowMap.get("RG_MONEY_CURRYEAR")),new BigDecimal(0));	
			month_amount =  Utility.parseDecimal(Utility.trimNull(rowMap.get("RG_MONEY_CURRMONTH")),new BigDecimal(0));		
			ItemName[iCount] = Utility.trimNull(rowMap.get("TEAM_NAME"));
			ItemValue[iCount] = Utility.trimNull("");
			PercentageValue[iCount] = Utility.trimNull(rowMap.get("RG_MONEY_CURRYEAR"));
			iCount++;	
	%>
	<tr class="tr1" bgcolor="#E0EEEE">
		<td align="center" width="*"><%=iCount%></td>
		<td align="center" width="*"><%=Utility.trimNull(rowMap.get("TEAM_NAME"))%></td>
		<td align="center" width="*"><%=Utility.trimNull(rowMap.get("LEADER_NAME"))%></td>
		<td align="center" width="*"><%=Format.formatMoney(year_amount)%></td>
		<td align="center" width="*"><%=Format.formatMoney(month_amount)%></td>
	</tr>
	<%}

	Chart = new FusionCharts();
	ChartCreator = new FusionChartsGanerate();
	XMLStr = ChartCreator.ganeratePie3D(ItemName,ItemValue,PercentageValue,"�����Ŷӷֲ�","","");
	int height = 200; 
	chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr,"ENFO",450,height,false);
	%>
	<%for(int i=0;i<(5-iCount);i++){%>
         <tr class="tr1">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
         </tr>
	<%}%>
		<tr>
			<td colspan="5" align="center"><%=chartHTMLCode%></td>
		</tr>
 </table>