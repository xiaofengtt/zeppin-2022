<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%
try{
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh")); //
String product_code=request.getParameter("productid");
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));

Integer cell_flag = Utility.parseInt(request.getParameter("cell_flag"),new Integer(1)); 
Integer cell_id = Utility.parseInt(request.getParameter("cell_id"),new Integer(0)); 
Integer depart_id = Utility.parseInt(request.getParameter("depart_id"),new Integer(0)); 
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

Map map = new HashMap();

if (sQuery.equals("")) {
	sQuery = Utility.trimNull(Argument.getFirstProduct(input_bookCode, input_operatorCode,"110202")) + " $ ";
	//Integer product_id = Argument.getFirstProduct(input_bookCode, input_operatorCode,"110202");
	if (overall_product_id == null)
		sQuery = "-1$ ";
	else
		sQuery = overall_product_id + "$ " + "$ " + "$ " + "$ ";
}

MoneyDetailLocal detail = EJBFactory.getMoneyDetail();
MoneyDetailVO vo = new MoneyDetailVO();
vo.setProduct_id(overall_product_id);
vo.setContract_sub_bh(contract_sub_bh); // 
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setCust_name(cust_name);
vo.setCust_no(cust_no);
vo.setCell_flag(cell_flag);
vo.setCell_id(cell_id);
vo.setDepart_id(depart_id);
vo.setSub_product_id(sub_product_id);

IPageList pageList = detail.query_page(vo, null, _page, pagesize);
List list = pageList.getRsList();
String[] sQuerys = Utility.splitString(sQuery + " ", "$");
String sUrl = "pay_query.jsp?pagesize=" + sPagesize + "&productid="+product_code+"&sQuery=" + sQuery+"&cust_name=" + cust_name+"&cust_no=" + cust_no 
		+ "&sub_product_id=" + sub_product_id + "&product_id=" + overall_product_id + "&contract_sub_bh="+contract_sub_bh;

String viewStr = "CONTRACT_SUB_BH$CUST_NO$CUST_NAME$PRODUCT_NAME$TO_MONEY$DZ_DATE$JK_TYPE_NAME$TO_BANK_DATE";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr(menu_id/*"38023"*/,input_operatorCode);
//如果该员工没有设置菜单，则为默认菜单
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}

//将拥有的菜单与设置的所有菜单进行匹配
Map fieldsMap = Argument.getMenuViewMap(menu_id,viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}

//int treeFlag = Argument.getSyscontrolValue("TREE0001");//选择用哪个版本的树 1旧版本,2新版本
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
window.onload = function(){
initQueryCondition();
productChange(<%=overall_product_id%>);
};

function showInfo(serialno)
{
   	location = 'pay_update.jsp?serial_no='+serialno+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&sQuery=<%=sQuery%>';
}

function removeInfo()
{
	var i;

	if(!confirmRemove(document.theform.serial_no))
		return false;

	if(document.theform.serial_no.length == null)
	{
		if(document.theform.removable_flag.value == 'false')
		{
			sl_alert('缴款信息已通过审核，不能删除！');
			return false;
		}
	}
	else
	{
		for(i = 0; i < document.theform.serial_no.length; i++)
			if(document.theform.serial_no[i].checked && (document.theform.removable_flag[i].value == 'false'))
			{
				sl_alert('缴款信息已通过审核，不能删除！');
				return false;
			}
	}
	disableAllBtn(true);
	document.theform.submit();
}

function refreshPage()
{
	StartQuery();
}

function StartQuery()
{
	var product_sq ;
  	if(document.theform.cell_flag.value==1){
		product_sq = document.theform.product_id.value ;
	}else{
		product_sq = 0;
	}
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	disableAllBtn(true);
  	var url = 'pay_query.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value+'&sQuery=' + product_sq +' $ '
					+document.theform.t1.value+' $ '+document.theform.cust_name.value+' $ '+document.theform.cust_no.value + '$' + sub_product_id
                    +'&cust_name='+document.theform.cust_name.value+'&cust_no='+document.theform.cust_no.value+'&cell_flag='+document.theform.cell_flag.value
					+'&cell_id='+document.theform.cell_id.value + '&depart_id='+document.theform.depart_id.value
                    +'&sub_product_id='+sub_product_id + '&product_id='+product_sq+"&contract_sub_bh="+document.theform.t1.value;
	location = url;
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

function printPaycardall(obj)
{

	if(checkedCount(obj) == 0)
	{
		sl_alert("请选择要打印的记录！");
		return false;
	}
	disableAllBtn(true);
	document.theform.action="pay_query_info.jsp";
	document.theform.submit();
}
function searchProduct(value)
{
	prodid=0;
	if (value != "")
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
		document.theform.product_id.focus();					
	}	
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}

/*导出数据
 *传入参数说明：
 *1、BOOK_CODE 帐套
 *2、INPUT_MAN 操作员
 *3、MENU_ID 菜单ID
 *4、viewStr 该操作员所拥有的列
 *5、sQuery 查询条件
*/
function exportInfo()
{
	if(sl_confirm("导出数据"))
	{
	  	setWaittingFlag(false);
	 	var url = 'set_export_info.jsp?flag=2&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=<%=menu_id%>&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>';
	 	location = url;
	}
}

/*收款打印*/
function collectionPrint(product_name, contract_sub_bh, bank_name, cust_name, money, currency_id, bank_acct,product_code)
{
	var url = "pay_collection_print.jsp?product_name="+product_name+"&contract_sub_bh="+contract_sub_bh+"&bank_name="+bank_name+"&cust_name="
					+cust_name+"&money="+money+"&currency_id="+currency_id+"&bank_acct="+bank_acct+"&product_code="+product_code;
	location = url;
}

function SelectCellFlag(value){
	if(value == "1")
	{
		tr1.style.display="";
		tr2.style.display="";
		tr3.style.display="none";
		if(document.theform.data.value==1){
				tr_sub_product_id.style.display="";
		}else{
				tr_sub_product_id.style.display="none";
		}
	}else if(value == "2")
	{
		tr1.style.display="none";
		tr2.style.display="none";
		tr3.style.display="";
        tr_sub_product_id.style.display="none";
	}
	document.theform.cell_flag.value=value;
}

/**  
 * 选择产品单元树 
 */
function openReportTree() {
	var v = showModalDialog('<%=request.getContextPath()%>/client/analyse/report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');
	if (v != null)
		document.theform.cell_id.value = v;
}

<%--
function openReportTree()
{
<%if(treeFlag == 2) {%>
	var v = showModalDialog('report_tree2.jsp', '', 'dialogWidth:550px;dialogHeight:650px;status:0;help:0');
<%}else{%>
	var v = showModalDialog('report_tree.jsp', '', 'dialogWidth:500px;dialogHeight:550px;status:0;help:0');
<%}%>
	if (v != null)
	{
		document.theform.cell_id.value = v;
	}
}--%>

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
    document.theform.data.value = data;
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}
</script>
</HEAD>
<BODY class="BODY"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="POST" action="pay_query_info.jsp">
<input type="hidden" name="cell_id" value="<%=cell_id %>">
<input type="hidden" name="cell_flag"  value="<%=cell_flag.intValue() %>">
<input type="hidden" name="data">
<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td  align="right" width="25%">客户编号:</td>
		<td width="25%"><input type="text" name="cust_no" value="<%=Utility.trimNull(cust_no)%>" onkeydown="javascript:nextKeyPress(this)" size="15">
		</td>
		<td align="right" width="20%">
		客户名称:</td>
		<td width="30%"><input type="text" name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this)" size="15">
		</td>
	
		
	</tr>
		<tr>
		<td align="right">
		 合同编号: </td>
		<td><input type="text" name="t1" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(sQuerys[1].trim())%>" size="15">&nbsp;&nbsp;

		</td>
		<td  align="right">
		设计部门:</td>
		<td> <select name="depart_id" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 100px">	
				 <%=Argument.getDepartOptions(depart_id)%>
			 </select>
		</td></tr>
	<tr >
		<td align=right >产品选择方式:</td>
		<td colspan="3" >
		<input type="radio" value=1 onclick="javascript:SelectCellFlag(this.value);" <%if(cell_flag!=null && cell_flag.intValue()==1) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" checked>独立产品&nbsp;&nbsp;
		<input type="radio" value=2 onclick="javascript:SelectCellFlag(this.value);" <%if(cell_flag!=null && cell_flag.intValue()==2) out.print("checked");%> onkeydown="javascript:nextKeyPress(this)" name="cellflag" class="flatcheckbox" >产品单元

		</td>
	</tr>

	<tr id = "tr1" <%if(cell_flag!=null && cell_flag.intValue()==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right">产品编号:</td >
		<td align="left"  colspan="3">
			<input type="text" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="15">
					&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td >
	</tr>
	<tr id = "tr2" <%if(cell_flag!=null && cell_flag.intValue()==1)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align="right">选择产品:</td >
		<td align="left"  colspan="3">
			 <select size="1" name="product_id" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getProductListOptions(input_bookCode, new Integer(Utility.parseInt(sQuerys[0].trim(), 0)),"", input_operatorCode,0)%>
			 </select>
		</td>
	</tr>
	<tr id="tr_sub_product_id" style="display:none">			
		<td align="right">子产品:</td>
		<td align="left" colspan="3" id="td_sub_product_id">
			<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getSubProductOptions2(overall_product_id, new Integer(0),sub_product_id,0,"")%> 
			</SELECT>
		</td>
	</tr>
	<tr id = "tr3" <%if(cell_flag!=null && cell_flag.intValue()==2)out.print("style='display:'");else out.print("style='display:none'");%>>
		<td align = "right">产品单元:</td >
		<td align="left"  colspan="3">
		<button type="button"  class="xpbutton3" name="btnQuery1" onclick="javascript:openReportTree();">请选择...</button>
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>
</table>
</div>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
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
							<td align=right colspan=4> 
							<%if (input_operator.hasFunc(menu_id, 108)){%>
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
							<%}%>
						 	<%if (input_operator.hasFunc(menu_id, 107)){%>
							<button type="button"  class="xpbutton3" accessKey=p name="btnRefresh" title="打印收据" onclick="javascript:printPaycardall(document.theform.selectbox);">打印(<u>P</u>)</button>&nbsp;&nbsp;&nbsp; 
							<%}%>
							<button type="button"  class="xpbutton3" onclick="javascript:setView()" title="显示设定">显示设定</button>&nbsp;&nbsp;&nbsp; 
							<%if (input_operator.hasFunc(menu_id, 160)){%>
							<button type="button"  class="xpbutton4" name="exportButton" onclick="javascript:exportInfo();" title="导出Excel数据">导出数据</button>&nbsp;&nbsp;&nbsp;
							<%}%>
						</td></tr>
					
					<tr>
						<td colspan="4">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>

				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<!--显示该操作员拥有的字段-->
						<%
						String[] fieldsArr =Utility.splitString(viewStr,"$");
						for(int j=0;j<fieldsArr.length;j++){
							int field_type = Utility.parseInt(Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")), 1);
						%>
							<%if(j == 0){%>
							<td align="center" <%if(field_type==2 || field_type==4) out.print("sort='num'");%>>
								<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.selectbox,this);">
								<%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%>
							</td>
							<%}else{%>
							<td align="center" <%if(field_type==2 || field_type==4) out.print("sort='num'");%>><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td><!--获得字段的Map对象在活名称-->
							<%}%>
						<%
						}
						%>
						<td align="center">收款打印</td>
					</tr>
					<%
					int index = 0;
					int iCurrent = 0;
					Integer serial_no;
					BigDecimal sum = new BigDecimal(0.0);
					for (int i=0; i<list.size(); i++) {
						map = (Map)list.get(i);
						serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));
						BigDecimal to_money = (BigDecimal)map.get("TO_MONEY");
					    if (to_money != null)
					        sum = sum.add(to_money);
					%>
					<input type="hidden" name="removable_flag" value="<%=(1 == Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),0))%>">
					<tr class="tr<%=(iCurrent % 2)%>">
						<!--显示该操作员拥有的字段内容-->
						<%
						for(int j=0;j<fieldsArr.length;j++){
							if(j == 0)
							{%>
								<td class="tdh" align="center" >
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"><input class="flatcheckbox" type="checkbox" name="selectbox" value="<%=serial_no%>"></td>
										<td width="90%" align="left"><%=Utility.trimNull(map.get(fieldsArr[j]))%></td>
									</tr>
								</table>
								</td>
							<%}else{
							//表示有金额存在
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		
						  	}
							
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							switch (iType){
							case 1:
							%>
							<td align="left" ><%=fieldsArr[j].equals("PRODUCT_NAME") ? Utility.trimNull(map.get("LIST_NAME")).equals("") ? Utility.trimNull(map.get(fieldsArr[j])) : Utility.trimNull(map.get(fieldsArr[j])) + "(" + Utility.trimNull(map.get("LIST_NAME")) + ")" :Utility.trimNull(map.get(fieldsArr[j]))%></td>	
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
							<td align="center" ><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0)))%></td>	
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
										default:
										break;
									}
								}
						}%>
						<td align="center">
						<%if (input_operator.hasFunc(menu_id, 107)){%>
							<button type="button"  class="xpbutton2" title="收款打印" onclick="javascript:disableAllBtn(true);collectionPrint('<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>', '<%=Utility.trimNull(map.get("CONTRACT_SUB_BH")) %>', 
								'<%=Utility.trimNull(map.get("TG_BANK_NAME")) %><%=Utility.trimNull(map.get("TG_BANK_SUB_NAME")) %>', '<%=Utility.trimNull(map.get("CUST_NAME")) %>', 
								'<%=Utility.trimNull(map.get("TO_MONEY")) %>', '<%=Utility.trimNull(map.get("CURRENCY_ID")) %>', '<%=Utility.trimNull(map.get("TG_BANK_ACCT")) %>',
								'<%=Utility.trimNull(map.get("PRODUCT_CODE")) %>');">&gt;&gt;</button>
						<%}%>
						</td>
						</tr>
					
					<%iCurrent++;
				}

				for (; iCurrent < pageList.getPageSize(); iCurrent++) {
				%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<%for(int j=0;j<fieldsArr.length;j++){
					%>
						<td align="center" ></td>
					<%
					}
					%>
					<td align="center" ></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<%for(int j=1;j<fieldsArr.length;j++){
						%>
						<%if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2){
					  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
					  		{%>
					  		<!--认购金额-->
							<td align="right" ><%=Format.formatMoney(sum)%></td>
							<%}%>
						<%}else{%>
						<td align="center" >-</td>
						<%}%>
						<%
						}
						%>
						<td align="center" >-</td>
					</tr>
				</table>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
				   
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
					
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%detail.remove();
}catch(Exception e){throw e;}
%>
