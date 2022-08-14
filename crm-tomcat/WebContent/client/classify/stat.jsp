<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//调用SP_QUERY_TCustClassDefine 0,'',1,0,0,0,0,2查询所有1级分类
CustClassDefineLocal custClassDefineLocal= null;
List custClassDefineList=null;
CustClassDefineVO custClassDefineVO=new CustClassDefineVO();
CustClassifyVO custClassifyVO=new CustClassifyVO();

custClassDefineVO.setClass_define_id(new Integer(0));
custClassDefineVO.setClass_define_name("");
custClassDefineVO.setParent_id(new Integer(0));
custClassDefineVO.setParent_value(new Integer(0));
custClassDefineVO.setCanmodi(new Integer(0));
custClassDefineVO.setInput_man(new Integer(0));
custClassDefineVO.setCD_no(new Integer(2));//为了测试数据可以设为1，实际应用前改为2

custClassDefineLocal= EJBFactory.getCustClassDefine();
custClassDefineList=custClassDefineLocal.query_custClassDefine(custClassDefineVO);

//原有代码
CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
//把custClassDefineList的第一条记录作为默认值
Map tempMap=null;
Integer defaultClassDefineID=new Integer(0);
if(custClassDefineList.size()>0){
	tempMap=(Map)custClassDefineList.get(0);
	defaultClassDefineID= Utility.parseInt(Utility.trimNull(tempMap.get("CLASSDEFINE_ID")), new Integer(0));
}

Integer classDefineID = Utility.parseInt(Utility.trimNull(request.getParameter("classDefineID")), defaultClassDefineID);
custClassifyVO.setClassDefineID(classDefineID); 
custClassifyVO.setBook_code(new Integer(1));
custClassifyVO.setInput_man(input_operatorCode);
List list=custClassDefineLocal.query_custClassify(custClassifyVO);
Map map = null;
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.rgMoneyAnalyse",clientLocale)%> </TITLE><!--客户认购金额分析-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
function queryInfo(obj)
{
	if(obj.value != "")
		location = "stat.jsp?classDefineID="+obj.value;
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" >
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>
<div id="headDiv" style="margin-left:20px;margin-right:20px;margin-top:5px;">
		<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
				<tr style="background:F7F7F7;">
					<td>
						<select size="1" colspan="3"name="classDefineID" id="classDefineID" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:queryInfo(this)">					
							<%=Argument.getCustClassDefine(classDefineID,custClassDefineList) %>
						</select>
					</td>
				</tr> 
		</table>
</div>
<div  id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;">
<%
String[] ItemValue1 = new String[list.size()];
String[] ItemName1 = new String[list.size()];
String[] PercentageValue1 = new String[list.size()];

String[] ItemValue2 = new String[list.size()];
String[] ItemName2 = new String[list.size()];
String[] PercentageValue2 = new String[list.size()];

String fileName1 = LocalUtilis.language("class.custClassStaticsOfPopulation",clientLocale);//客户分类统计-人数占比
String fileName2 = LocalUtilis.language("class.custClassStaticsOfMoney",clientLocale);//客户分类统计-金额占比
for(int i=0;i<list.size();i++)
{
	map = (Map)list.get(i);
	

	ItemName1[i] = Utility.trimNull(map.get("CLASSDETAIL_NAME"));
	ItemValue1[i] = "";
	ItemName2[i] = Utility.trimNull(map.get("CLASSDETAIL_NAME"));
	ItemValue2[i] = "";
	PercentageValue1[i] = Utility.trimNull(map.get("NUM"));
    PercentageValue2[i] = Utility.trimNull(map.get("RG_MONEY"));
}

FusionCharts Chart = new FusionCharts();
FusionChartsGanerate ChartCreator = new FusionChartsGanerate();

String XMLStr1 = ChartCreator.ganeratePie3D(ItemName1,ItemValue1,PercentageValue1,fileName1,"","");

String XMLStr2 = ChartCreator.ganeratePie3D(ItemName2,ItemValue2,PercentageValue2,fileName2,"","");


int height = 31*10 + 27; 
String chartHTMLCode1 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr1,"ENFO",440,height,false);
String chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Pie3D.swf","",XMLStr2,"ENFO",440,height,false);
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr>
		<td align="left">
			<%=chartHTMLCode1%>
		</td>
		<td >
			<%=chartHTMLCode2 %>
		</td>
	</tr>
	<tr>
		<td>
			<table  border="0"  width="440" cellspacing="1" cellpadding="2"	class="tablelinecolor" height="">
				<tr class="trh">
					<td><%=LocalUtilis.language("class.customerType",clientLocale)%></td><!-- 客户类别 -->
					<td><%=LocalUtilis.language("class.CustomerNuber",clientLocale)%></td><!-- 客户数 -->
					<td><%=LocalUtilis.language("class.percentage",clientLocale)%></td><!-- 百分比 -->
				</tr>
<%Iterator iterator1 = list.iterator();
int iCurrent = 0;
while(iterator1.hasNext()){
		map = (Map)iterator1.next();				
%>	
				<tr class="tr<%=(iCurrent % 2)%>">
					<td>
<%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%>
					</td>
					<td><%=Utility.trimNull(map.get("NUM")) %></td>
					<td><%=Utility.trimNull(map.get("NUM_RATE")+"%") %></td>
				</tr>			
<%iCurrent++;}if(map!=null){%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td>
                    <%=LocalUtilis.language("message.total",clientLocale)%>
					</td>
					<td><%=Utility.trimNull(map.get("SUM_NUM")) %></td>
					<td>100%</td>
				</tr>		
<%}%>
			</table>		
		</td>
		<td>
			<table  border="0"  width="440" cellspacing="1" cellpadding="2"	class="tablelinecolor" height="">
				<tr class="trh">
					<td><%=LocalUtilis.language("class.customerType",clientLocale)%></td><!-- 客户类别 -->
					<td><%=LocalUtilis.language("class.rg_money",clientLocale)%></td> <!-- 认购金额 -->
					<td><%=LocalUtilis.language("class.percentage",clientLocale)%></td><!-- 百分比 -->
				</tr>
<%Iterator iterator = list.iterator();
iCurrent = 0;
while(iterator.hasNext()){
		map = (Map)iterator.next();				
%>	
				<tr class="tr<%=(iCurrent % 2)%>">
					<td>
<%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%>				
					</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),null))%></td>
					<td><%=Utility.trimNull(map.get("MONEY_RATE")+"%") %></td>
				</tr>			
<%iCurrent++;} if(map!=null){%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td>
                    <%=LocalUtilis.language("message.total",clientLocale)%>
					</td>
					<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("SUM_MONEY")),null))%></td>
					<td>100%</td>
				</tr>
<%}%>	
			</table>		
		</td>
	</tr>
</table>
</div>
<br>
<%custStatLocal.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
