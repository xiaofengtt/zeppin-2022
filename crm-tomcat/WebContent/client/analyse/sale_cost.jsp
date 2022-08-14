<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ò�ѯ����
Integer showFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(1));
Integer showQueryFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showQueryFlag")),new Integer(0));

Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

//��ö���
CustomerStatLocal custStatLocal = EJBFactory.getCustomerStat();
CustomerVO vo = new CustomerVO();

//ҳ���ø�������
input_bookCode = new Integer(1);
int iCount = 0;
int iCurrent = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalValue = new String[0];
Map map = null;
List listCust = null;
IPageList pageList = null;
IPageList pageListProduct = null;
List listSale = null;
Map mapSale = null;

//URL����
sUrl = sUrl + "&cust_name=" + cust_name
			+ "&group_id=" + group_id
			+ "&product_id=" + product_id
			+ "&cell_id=" + cell_id
			+ "&showFlag="+showFlag
			+ "&showQueryFlag="+showQueryFlag;
			
//�������
String cust_no = "";
Integer cust_id = new Integer(0);
String product_code = "";
String product_name = "";
BigDecimal total_money_all = new BigDecimal(0.00);//�ܶ�
BigDecimal activity_fee_all = new BigDecimal(0.00);//��Ӫ������
BigDecimal precent = new BigDecimal(0.00);//Ӫ������ռ��
BigDecimal total_money = new BigDecimal(0.00);//�ͻ��Ϲ��ܶ�
BigDecimal fact_money = new BigDecimal(0.00);//��Ʒ�����ܶ�
BigDecimal activity_fee = new BigDecimal(0.00);//Ӫ������
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.rgMoneyAnalyse",clientLocale)%> </TITLE><!--�ͻ��Ϲ�������-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
/*��������*/	
window.onload = function(){
	show('<%=showFlag%>');
	initQueryCondition()
};

//�б�ͼ��ѡ�
function show(parm){
   for(i=1;i<3;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display =''");
		 }
	}
}

/*��ѯ�ͻ�*/
function QueryActionCustomer(){
	var cust_name = document.getElementById("cust_name");	
	var group_id = document.getElementById("group_id");	
	
	var url = "sale_cost.jsp?page=1&pagesize="+ document.theform.pagesize.value;
	if(cust_name != null)
		var url = url + "&cust_name=" + cust_name.value;	
	if(group_id !=null)
		var url = url + "&group_id=" + group_id.value;	
	var url = url + "&showFlag=" + document.getElementById("showFlag").value;
	var url = url + "&showQueryFlag=" + document.getElementById("showQueryFlag").value;
	disableAllBtn(true);		
	window.location = url;
}
/*��ѯ��Ʒ*/
function QueryActionProduct(){
	var product_id = document.getElementById("product_id");
	
	var url = "sale_cost.jsp?page=1&pagesize="+ document.theform.pagesize.value;
	if(product_id!=null)
		var url = url + "&product_id=" + product_id.value;	
	var url = url + "&cell_id=" + document.getElementById("cell_id").value;	
	var url = url + "&showFlag=" + document.getElementById("showFlag").value;
	var url = url + "&showQueryFlag=" + document.getElementById("showQueryFlag").value;
	disableAllBtn(true);		
	window.location = url;
}

//ˢ��
function refreshPage(){
	if(document.getElementById("showQueryFlag").value != 1)
		QueryActionCustomer();
	else
		QueryActionProduct();
}
//�ı�Ӫ���б�
function changeCustType(flag){
	document.getElementById("showQueryFlag").value=flag;
	if(flag != 1)
		QueryActionCustomer();
	else
		QueryActionProduct();
}

//ѡ���Ʒ��Ԫ
function openReportTree()
{
	var v = showModalDialog('report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');
	if (v != null)
	{
		document.theform.cell_id.value = v;
	}
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" >
<input type="hidden" name="cell_id" value="">
<input type="hidden" name="showFlag" id="showFlag" value="<%= showFlag%>" />
<input type="hidden" name="showQueryFlag" id="showQueryFlag" value="<%= showQueryFlag%>" />
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br>

<div id="headDiv" style="margin-left:20px;margin-right:20px;">
	<TABLE cellSpacing=0 cellPadding=0 width="95%" border="0" class="edline">
				<TBODY>
					<TR>											
						<!--�б�-->
                        <TD id="d1" style="background-repeat: no-repeat;font-size:13;font-family:΢���ź�" onclick=show(1) vAlign=bottom width=60 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.list",clientLocale)%> </TD>
						<!--ͼ��-->
                        <TD id="d2" style="background-repeat: no-repeat;font-size:13;font-family:΢���ź�" onclick=show(2) vAlign=bottom width=60 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.icon",clientLocale)%> </TD>	
						<TD vAlign=top align="right">&nbsp;
						<!--��ѯ-->
                        <button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--��ѯ-->
						</TD>				
					</TR>
			</TBODY>
	</TABLE>
</div>
<%if(showQueryFlag.intValue() !=1){ %>
<!-- ��ѯ�ͻ� -->
<div id="queryCondition" class="qcMain" style="display:none;width:220px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
				<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
 					<td align="right">
 						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
 					</td>
		</tr>
	</table> 
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<!--�ͻ�����-->
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%>: </td><!--�ͻ�����-->
			<td align="left">
				<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this)" size="20">
			</td>
		</tr>
		<tr>
			<!--�ͻ�Ⱥ��-->
			<td align="right"><%=LocalUtilis.language("class.custGroups",clientLocale)%>: </td>
			<td align="left">
				<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:QueryActionCustomer();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td><!--ȷ��-->
		</tr>			
	</table>
</div>
<%}else{ %>
<!-- ��ѯ��Ʒ -->
<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
				<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
 					<td align="right">
 						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
 					</td>
		</tr>
	</table> 
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<!-- ��Ʒ��Ԫ -->
			<td align="right"><%=LocalUtilis.language("message.PleaseSelectAPU",clientLocale)%>: </td>
			<td align = "left">
				<button type="button"  class="xpbutton3" name="btnQuery1" onclick="javascript:openReportTree();"><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%>...</button><!-- ��ѡ�� -->
			</td>
		</tr>
		<tr>
			<!--��Ʒ����-->
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>: </td>
			<td align = "left">
				<select size="1" name="product_id" id="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">					
					<%=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,0)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:QueryActionProduct();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td><!--ȷ��-->
		</tr>			
	</table>
</div>
<%} %>
<div id="headDiv" style="margin-left:20px;margin-right:20px;margin-top:5px;">
		<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
				<tr style="background:F7F7F7;">
					<!--�ͻ�-->
                    <td width="60px" align="center" id="td0"><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.customer",clientLocale)%> </a></font></td>
					<!--��Ʒ-->
                    <td width="60px" align="center" id="td1"><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeCustType(1);" class="a2"><%=LocalUtilis.language("class.product",clientLocale)%> </a></font></td>
				</tr> 
		</table>
</div>
<div  id="r1"  align="left"  style="display:none;margin-left:20px; margin-top:5px;margin-right:20px;">
	<%if(showQueryFlag.intValue() !=1){ %>
	<!-- �ͻ��б� -->
	<table border="0"  width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*"><%=LocalUtilis.language("class.customerID",clientLocale)%> </td><!--�ͻ����-->
			<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
			<td align="center" width="*"><%=LocalUtilis.language("class.custSubScribeTotal",clientLocale)%> </td><!--�ͻ��Ϲ��ܶ�-->
			<td align="center" width="*"><%=LocalUtilis.language("class.marketingExpenses",clientLocale)%> </td><!--Ӫ������-->
			<td align="center" width="*"><%=LocalUtilis.language("class.marketingExpensesMoreThan",clientLocale)%> </td><!--Ӫ������ռ��-->
		</tr>
		<%
			//���ò���
			vo.setCust_name(cust_name);
			vo.setGroupID(group_id);
			vo.setInput_man(input_operatorCode);
			pageList = custStatLocal.getSaleCostCust(vo,t_sPage,t_sPagesize);
			listCust = pageList.getRsList();
			Iterator iterator = listCust.iterator();
		
			//����ͻ��б�
		while(iterator.hasNext()){
			iCount++;
			map = (Map)iterator.next();
			
			cust_no = Utility.trimNull(map.get("CUST_NO"));
			cust_name = Utility.trimNull(map.get("CUST_NAME"));
			total_money = Utility.parseDecimal( Utility.trimNull(map.get("TOTAL_MONEY")),new BigDecimal(0.00));
			activity_fee = Utility.parseDecimal(Utility.trimNull(map.get("ACTIVITY_FEE")),new BigDecimal(0.00));
			if(total_money.intValue()!=0){
				precent = activity_fee.divide(total_money,2).multiply(new BigDecimal(100));
			}
			
			total_money_all = total_money_all.add(total_money);
			activity_fee_all = activity_fee_all.add(activity_fee);
		%>			
				<tr class="tr<%=iCount%2%>">
				  	 <td align="center" width="*"><%=cust_no%></td>					
					 <td align="left" width="*">&nbsp;<%=cust_name%></td>        
					 <td align="right" width="*"><%= Format.formatMoney(total_money)%>&nbsp;</td>   
					 <td align="right" width="*"><%= Format.formatMoney(activity_fee)%>&nbsp;</td>    
					 <td align="right" width="*"><%= precent%>%&nbsp;</td>
				</tr>
		<%}%>
		<%for(int i=0;i<(8-iCount);i++){%>     	
		         <tr class="tr<%=i%2%>">
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		         </tr>           
		<%}%> 
		
		<tr class="trbottom">
			<!--�ϼ�--><!--��-->
               <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>;&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>		
		</tr>
	</table>
	<br>		
	<div>
	<%=pageList.getPageLink(sUrl,clientLocale)%>
	</div>
	<%}
	else{%>
	<!-- ��Ʒ�б� -->
	<table border="0"  width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*"><%=LocalUtilis.language("class.productID",clientLocale)%> </td><!--��Ʒ���-->
			<td align="center" width="*"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
			<td align="center" width="*"><%=LocalUtilis.language("class.productTotalSales",clientLocale)%> </td><!--��Ʒ�����ܶ�-->
			<td align="center" width="*"><%=LocalUtilis.language("class.marketingExpenses",clientLocale)%> </td><!--Ӫ������-->
			<td align="center" width="*"><%=LocalUtilis.language("class.marketingExpensesMoreThan",clientLocale)%> </td><!--Ӫ������ռ��-->
		</tr>
		<%
			//���ò���
			vo.setProduct_id(product_id);
		    vo.setCell_id(cell_id);
			vo.setInput_man(input_operatorCode);
			pageListProduct = custStatLocal.getSaleCostProduct(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));				
			listSale = pageListProduct.getRsList();
			Iterator iterator = listSale.iterator();
		
			//�����Ʒ�б�
			while(iterator.hasNext()){
			iCount++;
			mapSale = (Map)iterator.next();
			
			product_code = Utility.trimNull(mapSale.get("PRODUCT_CODE"));
			product_name = Utility.trimNull(mapSale.get("PRODUCT_NAME"));
			fact_money = Utility.parseDecimal( Utility.trimNull(mapSale.get("FACT_MONEY")),new BigDecimal(0.00));
			activity_fee = Utility.parseDecimal(Utility.trimNull(mapSale.get("ACTIVITY_FEE")),new BigDecimal(0.00));
			if(fact_money.intValue()!=0){
				precent = activity_fee.divide(fact_money,2).multiply(new BigDecimal(100));
			}
			total_money_all = total_money_all.add(fact_money);
			activity_fee_all = activity_fee_all.add(activity_fee);
		%>			
				<tr class="tr<%=iCount%2%>">
				  	 <td align="center" width="*"><%=product_code%></td>					
					 <td align="left" width="*">&nbsp;<%=product_name%></td>        
					 <td align="right" width="*"><%= Format.formatMoney(fact_money)%>&nbsp;</td>   
					 <td align="right" width="*"><%= Format.formatMoney(activity_fee)%>&nbsp;</td>    
					 <td align="right" width="*"><%= precent%>%&nbsp;</td>
				</tr>
		<%
		}%>
		<%for(int i=0;i<(8-iCount);i++){%>     	
		         <tr class="tr<%=i%2%>">
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		            <td align="center"></td>
		         </tr>           
		<%}%> 
		<tr class="trbottom">
			<!--�ϼ�--><!--��-->
               <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>;&nbsp;<%=pageListProduct.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>		
		</tr>
	</table>
	<br>		
	<div>
		<%=pageListProduct.getPageLink(sUrl,clientLocale)%>
	</div>
	<%}%>
</div>

<!-- ͼ�� -->
<div  id="r2"  align="left"  style="margin-left:20px; margin-top:10px;margin-right:20px;display:none; ">
<%
String[] ItemValue = new String[2];
String[] ItemName = new String[2];


ItemValue[0] = total_money_all.divide(new BigDecimal(1),2).toString();
ItemValue[1] = activity_fee_all.divide(new BigDecimal(1),2).toString();

ItemName[0] = enfo.crm.tools.LocalUtilis.language("intrsut.home.totalmount",clientLocale);//�ܶ�
ItemName[1] = enfo.crm.tools.LocalUtilis.language("class.marketingExpenses",clientLocale);//Ӫ������

FusionCharts Chart = new FusionCharts();
FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
String XMLStr = ChartCreator.ganerateColumn3D(ItemName,ItemValue,"","","","");
int height = 31*10 + 27; 
String chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Column1.swf","",XMLStr,"ENFO",480,height,false);
%>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr bgcolor="white">
		<td align="left">
			<%=chartHTMLCode%>
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
