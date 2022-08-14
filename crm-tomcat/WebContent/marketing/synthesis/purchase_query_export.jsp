<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

ContractLocal contract = EJBFactory.getContract();
ContractLocal history = EJBFactory.getContract();

int firstFlag = Utility.parseInt(request.getParameter("firstFlag"),0);
String sQuery = request.getParameter("sQuery");
IPageList pageList = null;
List list = null;//保存查询结果集
Map map = new HashMap();

String[] paras = Utility.splitString(sQuery + " ", "$");
ContractVO vo = new ContractVO();
   String cust_name = Utility.trimNull(request.getParameter("cust_name"));
   String productName=request.getParameter("product_name");
   Integer service_man=Utility.parseInt(request.getParameter("service_man"),new Integer(0));
   String city_name = Utility.trimNull(request.getParameter("city_name"));
   String cust_no = Utility.trimNull(request.getParameter("cust_no"));
   String card_id = Utility.trimNull(request.getParameter("card_id"));
   String query_contract_sub_bh = Utility.trimNull(request.getParameter("query_contract_sub_bh"));
   Integer cust_type = Utility.parseInt(request.getParameter("cust_type"),new Integer(0));
   String prov_level = Utility.trimNull(request.getParameter("prov_level"));
   BigDecimal min_rg_money = Utility.parseDecimal(request.getParameter("min_rg_money"), null);
   BigDecimal max_rg_money = Utility.parseDecimal(request.getParameter("max_rg_money"), null);
   Integer productId=Utility.parseInt(request.getParameter("productId"),new Integer(0));
   Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));

   vo.setBook_code(input_bookCode);
   vo.setInput_man(input_operatorCode);
   vo.setProduct_id(productId);
   vo.setContract_sub_bh(query_contract_sub_bh);
   vo.setCust_name(cust_name);
   vo.setCust_no(cust_no);
   vo.setCard_id(card_id);
   vo.setOnly_thisproduct(new Integer(0));
   vo.setCust_type(cust_type);
   vo.setProv_level(prov_level);
   vo.setMin_rg_money(min_rg_money);
   vo.setMax_rg_money(max_rg_money);
   vo.setProduct_name(productName);
   vo.setService_man(service_man);
   vo.setCity_name(city_name);
   vo.setSub_product_id(sub_product_id);
pageList = contract.listAll(vo, _page, pagesize);
   	list = pageList.getRsList();

if(paras.length>1){
  
   query_contract_sub_bh = Utility.trimNull(paras[1].trim());
   cust_type = Utility.parseInt(paras[2].trim(),new Integer(0));
   cust_name = Utility.trimNull(paras[3].trim());
   card_id = Utility.trimNull(paras[4].trim());
   cust_no = Utility.trimNull(paras[6].trim());
   min_rg_money = Utility.parseDecimal(paras[8].trim(), null);
   max_rg_money = Utility.parseDecimal(paras[9].trim(), null);
   service_man=Utility.parseInt(paras[10].trim(),new Integer(0));
   city_name = Utility.trimNull(paras[11].trim());
   productName=Utility.trimNull(paras[14].trim());
   prov_level = Utility.trimNull(request.getParameter("prov_level"));
   productId = Utility.parseInt(paras[13].trim(),new Integer(0));
   sub_product_id = Utility.parseInt(paras[20].trim(),new Integer(0));
   
    vo.setBook_code(input_bookCode);
    vo.setInput_man(input_operatorCode);
    vo.setProduct_id(productId);
    vo.setContract_sub_bh(query_contract_sub_bh);
    vo.setCust_name(cust_name);
    vo.setCust_no(cust_no);
    vo.setCard_id(card_id);
    vo.setOnly_thisproduct(new Integer(0));
    vo.setCust_type(cust_type);
    vo.setProv_level(prov_level);
    vo.setMin_rg_money(min_rg_money);
    vo.setMax_rg_money(max_rg_money);
    vo.setProduct_name(productName);
    vo.setService_man(service_man);
    vo.setCity_name(city_name);
    vo.setSub_product_id(sub_product_id);
    vo.setCell_flag(Utility.parseInt(paras[15].trim(),new Integer(1)));
    vo.setCell_id(Utility.parseInt(paras[16].trim(),new Integer(0)));
    vo.setDepart_id(Utility.parseInt(paras[17].trim(),new Integer(0)));
    vo.setSq_date_start(Utility.parseInt(paras[18].trim(),null));
    vo.setSq_date_end(Utility.parseInt(paras[19].trim(),null));
 
	pageList = contract.listAll(vo, _page, pagesize);
   	list = pageList.getRsList();
}
  
sUrl = sUrl+ "&firstFlag=1&sQuery=" + sQuery;

String viewStr = "CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NO$CUST_NAME$RG_MONEY$QS_DATE$HT_STATUS_NAME$TRANS_FLAG_NAME";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr("38022",input_operatorCode);
//如果该员工没有设置菜单，则为默认菜单
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}
//将拥有的菜单与设置的所有菜单进行匹配
Map fieldsMap = Argument.getMenuViewMap("38022",viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/audit.js"></SCRIPT>
<BODY>
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
</form>
<script type="text/javascript">
function exportFiles()
{
	var obj = document.theform.DCellWeb1;
	var name = obj.GetCellString(1,1,0);	
	showModalDialog('/webreport/export.jsp?name='+ name,obj,'dialogTop:120px;dialogLeft:500px;dialogWidth:360px;dialogHeight:250px;status:0;help:0');			
}

function printFiles(){
	setPrint(document.theform.DCellWeb1,1);
}    
<%
String[] fieldsArr =Utility.splitString(viewStr,"$");
%>
var cellObj = document.getElementById("DCellWeb1");
cellObj.SetSheetLabel(0,"委托人认购明细表");
cellObj.ShowSheetLabel(1,0);
cellObj.SetCols(<%=fieldsArr.length+1%>,0);//设置列数
cellObj.SetRows(6+<%=list.size()%>,0);
for(i=3;i<=4+<%=list.size()%>;i++)
{
	cellObj.SetCellBorder(1, i, 0, 0, 3);	
	for(j=1;j<=<%=fieldsArr.length+1%>;j++){
		cellObj.SetCellBorder(j, i, 0, 2, 2);	
		cellObj.SetCellBorder(j, i, 0, 3, 2);	
	}
	cellObj.SetCellBorder(<%=fieldsArr.length%>, i, 0, 2, 3);
}
for(i=1;i<=<%=fieldsArr.length+1%>;i++)
{
	cellObj.SetCellBorder(i, 3, 0, 1, 3);
	cellObj.SetCellBorder(i, 4+<%=list.size()%>, 0, 3, 3);
}
cellObj.WorkbookReadonly = true;
cellObj.MergeCells(1,1,<%=fieldsArr.length+1%>,1);
cellObj.s(1,1,0,"委托人认购明细表");
cellObj.SetRowHeight(1,60,1,0);
cellObj.SetCellAlign (1,1,0,32+4);
cellObj.SetCellFontSize (1,1,0,25);
cellObj.MergeCells(1,2,<%=fieldsArr.length+1%>,2);
cellObj.s(1,2,0,"产品名称:<%=Argument.getProductName(productId)%>");

<%
for(int i=0;i<fieldsArr.length;i++){%>
    cellObj.s(<%=i+1%>,3,0,"<%=((Map)fieldsMap.get(fieldsArr[i])).get("FIELD_NAME")%>");    
<%
}
%>
for(i=1;i<=<%=fieldsArr.length+1%>;i++){
	cellObj.SetCellAlign (i,3,0,32+4);
}

<%
BigDecimal sum = new BigDecimal(0.000);
for(int i=0; i<list.size();i++){
    map = (Map)list.get(i);
	BigDecimal rg_money = (BigDecimal)map.get("RG_MONEY");
	if (rg_money != null)
		sum = sum.add(rg_money);
    for(int j=0;j<fieldsArr.length;j++){
        int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
		switch (iType){
		case 1:
%>		
            cellObj.s(<%=j+1%>,<%=4+i%>,0,'<%=fieldsArr[j].equals("PRODUCT_NAME") ? Utility.trimNull(map.get("LIST_NAME")).equals("") ? Utility.trimNull(map.get(fieldsArr[j])) : Utility.trimNull(map.get(fieldsArr[j])) + "(" + Utility.trimNull(map.get("LIST_NAME")) + ")" : Utility.trimNull(map.get(fieldsArr[j]))%>');
<%
		    break;
		case 2:
%>
            cellObj.s(<%=j+1%>,<%=4+i%>,0,'<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j])))))%>');		
<%
		    break;
		case 3:
%>		
            cellObj.s(<%=j+1%>,<%=4+i%>,0,'<%=Format.formatDateLine(((Integer)map.get(fieldsArr[j])))%>');
<%	
		    break;
		case 4:
%>
            cellObj.s(<%=j+1%>,<%=4+i%>,0,'<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), null))%>');
<%		
		    break;
		case 5:
%>
            cellObj.s(<%=j+1%>,<%=4+i%>,0,'<%=Utility.trimNull(Argument.getTintegerparamValue(Utility.parseInt(Utility.trimNull((((Map)fieldsMap.get(fieldsArr[j])).get("PARAM_TYPE_ID"))), new Integer(0)),Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>');
<%
		    break;
		default:
		    break;
		}
    }
}
%>
cellObj.s(1,<%=list.size()+4%>,0,'合计 <%=pageList.getTotalSize()%> 项');
<%
for(int j=1;j<fieldsArr.length;j++){
    if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("RG_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null){%>
        cellObj.s(<%=1+j%>,<%=list.size()+4%>,0,'<%=Format.formatMoney(sum)%>');
<%
    }
}    
%>
cellObj.MergeCells(1,<%=list.size()+5%>,2,<%=list.size()+5%>);
cellObj.MergeCells(3,<%=list.size()+5%>,4,<%=list.size()+5%>);
cellObj.MergeCells(cellObj.GetCols(0)-2,<%=list.size()+5%>,cellObj.GetCols(0)-1,<%=list.size()+5%>);
cellObj.s(1,<%=list.size()+5%>,0,'制表员:<%=Argument.getOPERNAME(input_bookCode,input_operatorCode)%>')
cellObj.s(3,<%=list.size()+5%>,0,'复核员:             经理复核员:');
cellObj.s(cellObj.GetCols(0)-2,<%=list.size()+5%>,0,'打印时间:<%=Format.formatDateCn(new Integer(Utility.getCurrentDate()))%>')
for(cols=1;cols<=cellObj.GetCols(0);cols++){
    cellObj.SetColWidth(1,cellObj.GetColBestWidth(cols),cols,0)	
}
//cellObj.PrintSetTopTitle(2,2);
cellObj.style.visibility = "visible";
</script>
</BODY>
</HTML>
<%history.remove();
contract.remove();
}catch(Exception e){throw e;}
%>



