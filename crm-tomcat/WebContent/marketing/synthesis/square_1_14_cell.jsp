<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer iProduct_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer begin_date =Utility.parseInt(request.getParameter("begin_date"),new Integer(0));
String sy_type1=Utility.trimNull(request.getParameter("sytype1"));
String sy_type2=Utility.trimNull(request.getParameter("sytype2"));
String sy_type3=Utility.trimNull(request.getParameter("sytype3"));
String sy_type4=Utility.trimNull(request.getParameter("sytype4"));
String sy_type5=Utility.trimNull(request.getParameter("sytype5"));
Integer link_man = Utility.parseInt(request.getParameter("link_man"),new Integer(0));
DeployLocal deploy = EJBFactory.getDeploy();
String sy_type =  sy_type1+" $"+sy_type2+" $"+sy_type3+" $"+sy_type4+" $"+sy_type5+" $";
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
</HEAD>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/audit.js"></SCRIPT>
<BODY  class="BODY">
<form name="theform">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="right">							
			<button type="button"  class="xpbutton3" accessKey=s onclick="javascript:exportFiles();">导出(<u>S</u>)</button>
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=p onclick="javascript:printFiles();">打印(<u>P</u>)</button>
			&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();">关闭(<u>C</u>)</button>
			&nbsp;&nbsp;
			</td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<OBJECT id=DCellWeb1 style="visibility:hidden;left:0px;top: 0px" width="100%" height="95%"
	classid=clsid:3F166327-8030-4881-8BD2-EA25350E574A 
	CODEBASE="/includes/CellWeb5.CAB#Version=5,2,4,921">
    <param name="_Version" value="65536">
    <param name="_ExtentX" value="22728">
    <param name="_ExtentY" value="13070">
    <param name="_StockProps" value="0">
	<param name="WMODE" value="transparent">     
</OBJECT>
<script language=javascript>
function exportFiles()
{
	var obj = document.theform.DCellWeb1;
	var name = obj.GetCellString(1,1,0);	
	showModalDialog('export.jsp?name='+ name,obj,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');			
}

function printFiles(){
	setPrint(document.theform.DCellWeb1,1);
}

var cellObj = document.getElementById("DCellWeb1");
<%
String str[] = Argument.getDictparamValue(1204);
%>
cellObj.InsertSheet(0,<%=str.length-1%>);
<%
int iCurrent = 0;
DeployVO vo = new DeployVO();
for (int i=0;i<str.length;i++) {
	vo.setBegin_date(begin_date);
	vo.setEnd_date(Utility.parseInt(request.getParameter("end_date"), new Integer(0)));
	vo.setBook_code(input_bookCode);
	vo.setSy_type(sy_type);
	vo.setInput_man(input_operatorCode);
	vo.setProduct_id(iProduct_id);
	vo.setCust_name(cust_name);
	vo.setContract_sub_bh(contract_sub_bh);
	vo.setProv_level(str[i]);
	vo.setSub_product_id(sub_product_id);
	vo.setLink_man(link_man);
	IPageList pageList = deploy.queryDetail(vo, 1, -1);
	List list = pageList.getRsList();

    if (list.size()==0){
%>
        cellObj.DeleteSheet(<%=str.length-i%>,1);
<%        
        continue;
    }
        
%>
cellObj.SetSheetLabel(<%=i%>,"<%=Argument.getTypeContentByValue(str[i])%>");
cellObj.ShowSheetLabel(1,<%=i%>);
cellObj.SetCols(10,<%=i%>);//设置列数
cellObj.PrintSetTopTitle(2,2);
cellObj.SetRows(6+<%=list.size()%>,<%=i%>);
cellObj.MergeCells(1,1,10,1);
cellObj.MergeCells(1,2,10,2);
cellObj.SetRowHeight(1,60,1,<%=i%>);
cellObj.SetCellAlign (1,1,<%=i%>,32+4);
cellObj.SetCellFontSize (1,1,<%=i%>,25);
cellObj.S(1,1,<%=i%>,"收益分配明细表"); 
cellObj.S(1,2,<%=i%>,"产品名称:<%=Argument.getProductName(iProduct_id)%>   收益级别:<%=Argument.getTypeContentByValue(str[i])%>"); 
cellObj.S(1,3,<%=i%>,"合同编号");
cellObj.S(2,3,<%=i%>,"受益人");
cellObj.S(3,3,<%=i%>,"份额");
cellObj.S(4,3,<%=i%>,"收益类别");
cellObj.S(5,3,<%=i%>,"每股收益");
cellObj.S(6,3,<%=i%>,"本次收益");
cellObj.S(7,3,<%=i%>,"扣税");
cellObj.S(8,3,<%=i%>,"本次实付收益");
cellObj.S(9,3,<%=i%>,"结算日期");
for(i=1;i<=9;i++)
	cellObj.SetCellAlign (i,3,<%=i%>,32+4);
for(i=3;i<=4+<%=list.size()%>;i++)
{
	cellObj.SetCellBorder(1, i, <%=i%>, 0, 3);	
	for(j=1;j<=10;j++){
		cellObj.SetCellBorder(j, i, <%=i%>, 2, 2);	
		cellObj.SetCellBorder(j, i, <%=i%>, 3, 2);	
	}
	cellObj.SetCellBorder(9, i, <%=i%>, 2, 3);
}
for(i=1;i<=10;i++)
{
	cellObj.SetCellBorder(i, 3, <%=i%>, 1, 3);
	cellObj.SetCellBorder(i, 4+<%=list.size()%>, <%=i%>, 3, 3);
}
<%
	BigDecimal sy_amount_sum = new BigDecimal(0.0);
	BigDecimal sy_money_sum = new BigDecimal(0.0);
	BigDecimal bond_tax_sum = new BigDecimal(0.0);
	for (int j=0; j<list.size(); j++) { 
		Map map = (Map)list.get(j);
		BigDecimal sy_amount = (BigDecimal)map.get("SY_AMOUNT");
		if (sy_amount!=null)
			sy_amount_sum = sy_amount_sum.add(sy_amount);

		BigDecimal sy_money = (BigDecimal)map.get("SY_MONEY");
		if (sy_money!=null)
			sy_money_sum = sy_money_sum.add(sy_money);

		BigDecimal bond_tax = (BigDecimal)map.get("BOND_TAX");
		if (bond_tax!=null)
			bond_tax_sum = bond_tax_sum.add(bond_tax);
%>
cellObj.S(1,4+<%=iCurrent%>,<%=i%>,'<%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%>');
cellObj.S(2,4+<%=iCurrent%>,<%=i%>,'<%=Utility.trimNull(map.get("CUST_NAME"))%>');
cellObj.S(3,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(sy_amount)%>');
cellObj.S(4,4+<%=iCurrent%>,<%=i%>,'<%=Utility.trimNull(map.get("SY_TYPE_NAME"))%>');
cellObj.S(5,4+<%=iCurrent%>,<%=i%>,'<%if((sy_amount!=null && sy_amount.doubleValue()!=0 && sy_money!=null && ((String)map.get("SY_TYPE")).equals("111602"))||(sy_amount!=null && sy_amount.doubleValue()!=0 && sy_money!=null && ((String)map.get("SY_TYPE")).equals("111603"))){%><%=Format.formatMoney(sy_money.divide(sy_amount,6))%><%}%>');
cellObj.S(6,4+<%=iCurrent%>,<%=i%>,'<%if(sy_money!=null && bond_tax!=null ){%><%=Format.formatMoney(sy_money.add(bond_tax))%><%}else{%><%=Format.formatMoney(sy_money)%><%}%>');
cellObj.S(7,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoneyPrint(bond_tax==null ? 0 : bond_tax.doubleValue(), 2)%>');
cellObj.S(8,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(sy_money)%>');
cellObj.S(9,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatDateCn((Integer)map.get("SY_DATE"))%>');
cellObj.SetCellAlign (3,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (5,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (6,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (7,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (8,4+<%=iCurrent%>,<%=i%>,32+2);
<% 
    iCurrent++;
}
%>
cellObj.S(1,4+<%=iCurrent%>,<%=i%>,'合计<%=list.size()%>项');
cellObj.S(2,4+<%=iCurrent%>,<%=i%>,'-');
cellObj.S(3,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(sy_amount_sum)%>');
cellObj.S(4,4+<%=iCurrent%>,<%=i%>,'-');
cellObj.S(5,4+<%=iCurrent%>,<%=i%>,'-');
cellObj.S(6,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(sy_money_sum)%>');
cellObj.S(7,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(bond_tax_sum)%>');
cellObj.S(8,4+<%=iCurrent%>,<%=i%>,'<%=Format.formatMoney(sy_money_sum)%>');
cellObj.S(9,4+<%=iCurrent%>,<%=i%>,'-');
cellObj.SetCellAlign (1,4+<%=iCurrent%>,<%=i%>,32+4);
cellObj.SetCellAlign (2,4+<%=iCurrent%>,<%=i%>,32+4);
cellObj.SetCellAlign (3,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (4,4+<%=iCurrent%>,<%=i%>,32+4);
cellObj.SetCellAlign (5,4+<%=iCurrent%>,<%=i%>,32+4);
cellObj.SetCellAlign (6,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (7,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (8,4+<%=iCurrent%>,<%=i%>,32+2);
cellObj.SetCellAlign (9,4+<%=iCurrent%>,<%=i%>,32+4);
cellObj.S(1,5+<%=iCurrent%>,<%=i%>,'制表:<%=Argument.getOpName(input_operatorCode)%>');
cellObj.S(3,5+<%=iCurrent%>,<%=i%>,'复核员:');
cellObj.S(5,5+<%=iCurrent%>,<%=i%>,'经理复核员:');
cellObj.MergeCells(8,5+<%=iCurrent%>,10,5+<%=iCurrent%>);
cellObj.S(8,5+<%=iCurrent%>,<%=i%>,'打印时间:<%=Format.formatDateCn(Utility.getCurrentDate())%>');
<%
iCurrent = 0;
%>
for(cols=1;cols<=cellObj.GetCols(<%=i%>);cols++){
    cellObj.SetColWidth(1,cellObj.GetColBestWidth(cols),cols,<%=i%>)	
}
<%    
}
%>
cellObj.style.visibility = "visible";
</script>
</form>
</BODY>
</HTML>
<%
deploy.remove();
%>
