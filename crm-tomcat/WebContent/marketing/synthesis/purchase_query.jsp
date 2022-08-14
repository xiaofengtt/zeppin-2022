<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

ContractLocal contract = EJBFactory.getContract();
ContractLocal history = EJBFactory.getContract();

int firstFlag = Utility.parseInt(request.getParameter("firstFlag"),0);
String sQuery = request.getParameter("sQuery");

IPageList pageList = null;
List list = null;//保存查询结果集

String[] paras = Utility.splitString(sQuery + " ", "$");

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
String admin_manager = Utility.trimNull(request.getParameter("admin_manager"));

ContractVO vo = new ContractVO();
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
vo.setAdmin_manager(admin_manager);
pageList = contract.listAll(vo, _page, pagesize);
//获得结果集
list = pageList.getRsList();

if (paras.length > 1) {  
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
   admin_manager = Utility.trimNull(paras[22].trim());

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
	vo.setAdmin_manager(admin_manager);
    pageList = contract.listAll(vo, _page, pagesize);
	//获得结果集
	list = pageList.getRsList();
}
  
sUrl = sUrl+ "&firstFlag=1&sQuery=" + sQuery;

String viewStr = "CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NO$CUST_NAME$RG_MONEY$QS_DATE$HT_STATUS_NAME$TRANS_FLAG_NAME";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr(menu_id/*"38022"*/,input_operatorCode); 
//如果该员工没有设置菜单，则为默认菜单
if(tempView!=null && !tempView.equals(""))
	viewStr = tempView;

//将拥有的菜单与设置的所有菜单进行匹配
Map fieldsMap = Argument.getMenuViewMap(menu_id, viewStr);
if (fieldsMap == null || fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script language=javascript>
function showInfo(serialno,product_id1,cust_name1,contract_bh1,card_id1) {
	 var a=showModalDialog( 'purchase_query_info.jsp?serial_no='+serialno+'&page=<%=sPage%>&pagesize=' +
	 		document.theform.pagesize.value, '', 'dialogWidth:800px;dialogHeight:540px;status:0;help:0');
}

function newInfo()
{
	location = 'purchase_info.jsp?page=1&pagesize=' + document.theform.pagesize.value;
}

function setiteminfor(tr10,tablePro,flagdisplay,imagex)
{
    i= flagdisplay.value;

    if(i==0)
    {
      tablePro.style.display="";
      tr10.style.display="";
      flagdisplay.value=1;
      imagex.src='/images/up_enabled.gif';

    }
    else if(i==1)
    {
       tablePro.style.display="none";
        tr10.style.display="none";
      flagdisplay.value=0;
      imagex.src='/images/down_enabled.gif';
    }
}
function printInfo(value)
{
	location ="purchase_info_print.jsp?serial_no="+value;
}
function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function advancedQuery() {

	var ret = showModalDialog('purchase_query_condition.jsp?rets=<%=sQuery%>&product_id=<%=overall_product_id%>', '', 'dialogWidth:650px;dialogHeight:390px;status:0;help:0');
	if (ret != null) {
	    disableAllBtn(true);
	    
	   	location = 'purchase_query.jsp?firstFlag=1&pagesize=' + document.theform.pagesize.value+'&sQuery='+ret ;
	}
	//window.open('purchase_query_condition.jsp?rets=<%=sQuery%>&product_id=<%=overall_product_id%>', '', 'resizable=1,width=650,height=390');
}

function StartQuery()
{
  	location = 'purchase_query.jsp?firstFlag=1&page=1&pagesize=' + document.theform.pagesize.value +'&sQuery='+document.theform.sQuery.value;
}

function refreshPage()
{
	StartQuery();
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}

function exportAndPrint(){
    window.open('purchase_query_export.jsp?firstFlag=1&page=1&pagesize=0&sQuery='+document.theform.sQuery.value,'打印与导出','resizable=1,width=800,height=600');   
}

/*导出数据
 *传入参数说明：
 *1、BOOK_CODE 帐套
 *2、INPUT_MAN 操作员
 *3、MENU_ID 菜单ID
 *4、viewStr 该操作员所拥有的列
 *5、sQuery 查询条件
*/
function exportInfo() {
	if(sl_confirm("导出数据")) {
	  	setWaittingFlag(false);
	 	var url = 'set_export_info.jsp?flag=0&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=<%=menu_id%>&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>';
	 	location = url;
	}
}
</script>
</HEAD>

<BODY class="BODY" <%=firstFlag==0?"onload='javascript:advancedQuery();'":""%> >
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">
<input type="hidden" name="sQuery" value="<%=sQuery%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<%//int  showTabInfo =  showTabInfoTop.intValue();
					//if(showTabInfo != 1){ %>
				  	<tr>
						<td colspan="4"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<%//} %>
					<tr>
						<td align="right">&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 108)) {%>
						<button type="button"  class="xpbutton4" name="btnQuery" accessKey=f onClick="javascript:advancedQuery();">查询(<u>F</u>)</button> &nbsp;&nbsp;&nbsp;  
						<%}%>
                        <%if (input_operator.hasFunc(menu_id, 161)) {%>
						<button type="button"  class="xpbutton5" name="btnExtport" accessKey=e onClick="javascript:exportAndPrint();">打印与导出(<u>E</u>)</button> &nbsp;&nbsp;&nbsp;  
						<%}%>
						<button type="button"  class="xpbutton3" onclick="javascript:setView()" title="显示设定">显示设定</button>&nbsp;&nbsp;&nbsp; 
						<%if (input_operator.hasFunc(menu_id, 160)) {%>
						<button type="button"  class="xpbutton4" name="exportButton" onclick="javascript:exportInfo();" title="导出Excel数据">导出数据</button>&nbsp;&nbsp;&nbsp;
						<%} %>
					</td>
					</tr>
					<tr>
						<td>
							<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table sort="multi" id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<!--显示该操作员拥有的字段-->
					<%
					String[] fieldsArr =Utility.splitString(viewStr,"$");
					for (int i=0; i<fieldsArr.length; i++) {
						int field_type = Utility.parseInt(Utility.trimNull(((Map)fieldsMap.get(fieldsArr[i])).get("FIELD_TYPE")), 1);
					%>
						<td align="center" <%=(field_type==2 || field_type==4)?"sort='num'":""%>><%=((Map)fieldsMap.get(fieldsArr[i])).get("FIELD_NAME")%></td>
					<%}%>
						<td align="center" >变更明细</td>
						<td align="center" >查看</td>
					</tr>
				<%
				int iCurrent = 0;
				BigDecimal sum = new BigDecimal(0.000);
				for (int i=0; i<list.size(); i++) {
					iCurrent++;
					Map map = (Map)list.get(i);
				 	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));
					BigDecimal rg_money = (BigDecimal)map.get("RG_MONEY");
					if (rg_money != null)
						sum = sum.add(rg_money);
				%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<!--显示该操作员拥有的字段内容-->
					<%
					for (int j=0; j<fieldsArr.length; j++) {
						if (j == 0) {%>
						<td class="tdh" align="center" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><!--<%if(Utility.parseInt(Utility.trimNull("CHECK_FLAG"), 0)==1 && Utility.trimNull(map.get("HT_STATUS")).equals("120101")){%> <input  type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"> <%}%>--></td>
								<td width="90%" align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
							</tr>
						</table>
						</td>	
						<%
						} else {
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							switch (iType) {
							case 1:
							%>
							<td align="left" ><%=fieldsArr[j].equals("PRODUCT_NAME") ? Utility.trimNull(map.get("LIST_NAME")).equals("") ? Utility.trimNull(map.get(fieldsArr[j])) : Utility.trimNull(map.get(fieldsArr[j])) + "(" + Utility.trimNull(map.get("LIST_NAME")) + ")" : Utility.trimNull(map.get(fieldsArr[j]))%></td>	
							<%
								break;
								case 2:
							%>
							<td align="right" >
								<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j])))))%>
							</td>	
							<%			
								break;
								case 3:
							%>
							<td align="center" ><%=Format.formatDateLine(((Integer)map.get(fieldsArr[j])))%></td>	
							<%				
								break;
								case 4:
							%>
							<td>
								<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), null))%>
							</td>
							<%
								break;
								case 5:
							%>
							<td>
								<%=Utility.trimNull(Argument.getTintegerparamValue(Utility.parseInt(Utility.trimNull((((Map)fieldsMap.get(fieldsArr[j])).get("PARAM_TYPE_ID"))), new Integer(0)),Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</td>
							<%
								break;
								case 7:
							%>
							<td>
								<%=Utility.trimNull(Argument.getIntrustOpName(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</td>
							<%
								break;
								default:
								break;
							}
						}
					}

					List _list = history.listHistory(serial_no);
					if (_list.size()<=0) {%>
						<td align="center">&nbsp;</td>	
						<%
					} else {%>				
						<td align="center" >	
							<button type="button"  class="xpbutton2" id="<%=iCurrent%>" name="btnsetinital" onclick="javascript:setiteminfor(tr<%=iCurrent%>,tablePro<%=iCurrent%>,document.theform.flagdisplay<%=iCurrent%>,document.theform.image<%=iCurrent%>);"><IMG id="image<%=iCurrent%>" src="/images/down_enabled.gif" align="center" width="7" height="9"></button>
							<input type="hidden" name="flagdisplay<%=iCurrent%>" value="0">
						</td>
					<%
					}%>
						<td align="center">
							<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<%=serial_no%>);">&gt;&gt;</button>
						</td>
					</tr>
					<!-- strat -->
					<tr id="tr<%=iCurrent%>" style="display: none">
						<td bgcolor="#FFFFFF" colspan="12">
						<table id="tablePro<%=iCurrent%>" class="tablelinecolor" style="display: none" border="0" cellpadding="2" cellspacing="1" width="100%">
							<tr>
								<td bgcolor="ffffff" align="center">变更类别</td>
								<td bgcolor="ffffff" align="center">操作员</td>
								<td bgcolor="ffffff" align="center">操作时间</td>
								<td bgcolor="ffffff" align="center">审核员</td>
								<td bgcolor="ffffff" align="center">审核时间</td>
								<td bgcolor="ffffff" align="center">备注</td>			
							</tr>
						<%
						int jCurrent = 0;

						for (int k=0; k<_list.size(); k++) {
							jCurrent++;
							Map _map = (Map)_list.get(k);
							String strCheckman="";
							Integer check_man = (Integer)_map.get("CHECK_MAN");
							if (check_man!=null && check_man.intValue()>0)
								strCheckman=Argument.getIntrustOpName((Integer)_map.get("CHECK_MAN"));
						%>
							<tr>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(_map.get("HT_STATUS_NAME"))%></td>							
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(Argument.getIntrustOpName((Integer)_map.get("INPUT_MAN")))%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(_map.get("INPUT_TIME"))%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(strCheckman)%></td>
								<td bgcolor="ffffff" align="center"><%=Utility.trimNull(_map.get("CHECK_TIME"))%></td>
								<td bgcolor="ffffff" align="left"><%=Utility.trimNull(_map.get("SUMMARY"))%></td>
								
							</tr>
						<%							
						}%>
						</table>
						</td>
					</tr>
					<!--end-->
				<%						
					}

					for (; iCurrent < pageList.getPageSize(); iCurrent++) { %>
					<tr class="tr<%=(iCurrent % 2)%>">
					<%
						for (int j=0; j<fieldsArr.length; j++){
					%>
						<td align="center" ></td>
					<%
						}
					%>
						<td></td>
						<td></td>
					</tr>
					<%
					}%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
					<%
					for (int j=1; j<fieldsArr.length; j++) {
						if (((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("RG_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null){%>
							<td align="right" ><%=Format.formatMoney(sum)%></td>
						<%
						} else { %>
							<td align="center" >-</td>
					<%
						}
					}%>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
						<td align="right">
							
							<!--<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="返回上一页" onclick="javascript:history.back();">返回(<u>B</u>)</button>
							&nbsp;&nbsp;&nbsp;-->
						</td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%history.remove();
contract.remove();
%>



