<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//声明对象
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
String[] ItemValue = null;
String[] ItemName = null;
String[] ItemValue2 = null;
String[] ItemName2 = null;
BigDecimal ben_amount = new BigDecimal(0);
BigDecimal rg_amount = new BigDecimal(0);
//图表
FusionCharts Chart = null;
FusionChartsGanerate ChartCreator = null;
String chartHTMLCode = "";//存量客户排名
String chartHTMLCode2 = "";//客户经理业绩排名
String XMLStr = "";

//查询结果
List efficientCustList = local.listEfficientCust(input_operatorCode);
List custManagerList = local.listCustManagersRank(input_operatorCode);
HashMap rowMap = new HashMap();
HashMap rowMap2 = new HashMap();
int len = 0;
int len2 = 0;

if(efficientCustList!=null){
	len = efficientCustList.size();
}

if(custManagerList!=null){
	len2 = custManagerList.size();
}

if(len>0){
	ItemValue = new String[len];
	ItemName = new String[len];

	for(int i=0;i<len;i++){
		rowMap = (HashMap)efficientCustList.get(i);
		ben_amount = Utility.parseDecimal( Utility.trimNull(rowMap.get("BEN_AMOUNT")),new BigDecimal(0.00));
		ItemValue[i] = ben_amount.toString();
		ItemName[i] = Utility.trimNull(rowMap.get("CUST_NAME"));
		
	}

	Chart = new FusionCharts();
	ChartCreator = new FusionChartsGanerate();
	XMLStr = ChartCreator.ganerateColumn3D(ItemName,ItemValue,"","","我的存量客户排名","");
	int height = 250; 
	chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr,"ENFO",350,height,false);
}

if(len2>0){
	ItemValue2 = new String[len2];
	ItemName2 = new String[len2];

	for(int i=0;i<len2;i++){
		rowMap2 = (HashMap)custManagerList.get(i);
		rg_amount = Utility.parseDecimal( Utility.trimNull(rowMap2.get("RG_MONEY")),new BigDecimal(0.00));
		ItemValue2[i] = rg_amount.toString();
		ItemName2[i] = Utility.trimNull(rowMap2.get("OP_NAME"));		
	}

	Chart = new FusionCharts();
	ChartCreator = new FusionChartsGanerate();
	XMLStr = ChartCreator.ganerateColumn3D(ItemName2,ItemValue2,"","","客户经理业绩排名","");
	int height = 250; 
	chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column3D.swf","",XMLStr,"ENFO",350,height,false);
}
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr bgcolor="white">
		<td align="left">
			<%=chartHTMLCode%>
		</td>
		<td align="left">
			<%=chartHTMLCode2%>
		</td>
	</tr>
</table>
