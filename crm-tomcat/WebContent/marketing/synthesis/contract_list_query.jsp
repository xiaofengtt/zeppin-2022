<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

ContractLocal contract = EJBFactory.getContract();

String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String ben_status = Utility.trimNull(request.getParameter("ben_status"));
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

IPageList pageList = null;
List list = new ArrayList();//保存查询结果集

ContractVO vo = new ContractVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(overall_product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setHt_status(ben_status);
vo.setSub_product_id(sub_product_id);

if (UNQUERY.intValue() == 0) {
	pageList = contract.listContractList(vo, _page, pagesize);
	list = pageList.getRsList();
}

sUrl += "&product_id=" +overall_product_id +"&ben_status="+ben_status+"&contract_sub_bh="+contract_sub_bh+"&sub_product_id="+sub_product_id;

String viewStr = "CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NAME$HT_STATUS_NAME$INPUT_TIME$SUMMARY";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr(menu_id/*"38026"*/,input_operatorCode);
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
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
window.onload = function(){
initQueryCondition()
productChange(<%=overall_product_id%>);
};
function StartQuery()
{
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	location = 'contract_list_query.jsp?page=1&pagesize=' + document.theform.pagesize.value + '&product_id='+ document.theform.product_id.value 
				+'&contract_sub_bh='+ document.theform.contract_sub_bh.value+'&ben_status='+document.theform.ben_status.value+'&sub_product_id='+sub_product_id
				+'&sQuery='+document.theform.product_id.value+' $ '+document.theform.contract_sub_bh.value+' $ '+document.theform.ben_status.value+'$'+sub_product_id;
}

function refreshPage()
{	
	StartQuery();
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
	 	var url = 'set_export_info.jsp?flag=4&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=<%=menu_id%>&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>';
	 	location = url;
	}
}

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
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
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">



<div id="queryCondition" class="qcMain" style="display:none;width:430px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>					
		<td align="right">合同编号:</td>
		<td align="left">
		 	<input type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="20" value="<%=contract_sub_bh%>">&nbsp;&nbsp;						
		</td>
		<td align="right">受益状态:</td>
		<td align="left"> 
		   <SELECT  size="1" name="ben_status" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
			<%=Argument.getDictParamOptions(1201,ben_status)%>
			</SELECT>						
		</td>
	</tr>
	<tr>			
		<td align="right">产品编号:</td >
		<td align="left" colspan=3>
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td >
	</tr>
	<tr>
		<td align="right">产品选择:</td >
		<td align="left" colspan=3>
			<SELECT name="product_id" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)" style="width: 335px;">
				<%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,0)%>
			</SELECT>
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
	<tr>
		<td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:disableAllBtn(true);StartQuery();">确定(<u>O</u>)</button></td>
	</tr>
</table>
</div>

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
					<td colspan="6"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
				</tr>
				<%//} %>
				<tr>
					<td align=right> 
						<%if (input_operator.hasFunc(menu_id, 108)){%>
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
						<%} %>
						<button type="button"  class="xpbutton3" onclick="javascript:setView()" title="显示设定">显示设定</button>&nbsp;&nbsp;&nbsp; 
						<%if (input_operator.hasFunc(menu_id, 160)){%>
						<button type="button"  class="xpbutton4" name="exportButton" onclick="javascript:exportInfo();" title="导出Excel数据">导出数据</button>&nbsp;&nbsp;&nbsp;
						<%} %>
					</td>
				</tr>
				<tr>
					<td colspan="6">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
				</table>
				<table sort="multi" id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<!--显示该操作员拥有的字段-->
						<%
						String[] fieldsArr =Utility.splitString(viewStr,"$");
						for(int j=0;j<fieldsArr.length;j++){
							int field_type = Utility.parseInt(Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")), 1);
						%>
							<td align="center" <%if(field_type==2 || field_type==4) out.print("sort='num'");%>><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td><!--获得字段的Map对象在活名称-->
						<%
						}
						%>
					</tr>
					<%
					int iCount = 0;
					int iCurrent = 0;
					Integer serial_no;
					
					for (int i=0; i<list.size(); i++) {
						Map map = (Map)list.get(i);
						serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));

				  		String strBentype = "合同变更";
				  		if(Utility.parseInt(Utility.trimNull(map.get("BEM_REC_NO")), 0) > 0)
				  			strBentype = "受益权变更";
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<!--显示该操作员拥有的字段内容-->
						<%
						for(int j=0;j<fieldsArr.length;j++){
							//表示有金额存在
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		
						  	}
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							if(iType == 1){
							%>
							<td align="left" ><%=fieldsArr[j].equals("PRODUCT_NAME") ? Utility.trimNull(map.get("LIST_NAME")).equals("") ? Utility.trimNull(map.get(fieldsArr[j])) : Utility.trimNull(map.get(fieldsArr[j])) + "(" + Utility.trimNull(map.get("LIST_NAME")) + ")" :Utility.trimNull(map.get(fieldsArr[j]))%></td>	
							<%
							}if(iType == 2){			
							%>
							<td align="right" >
								<%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j])))))%>
							</td>	
							<%			
							}if(iType == 3){	
							%>
							<td align="center" ><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0)))%></td>	
							<%				
							}if(iType == 4){	
							%>
							<td>
								<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), null))%>
							</td>
							<%
							}if(iType == 5){	
							%>
							<td>
								<%=Utility.trimNull(Argument.getTintegerparamValue(Utility.parseInt(Utility.trimNull((((Map)fieldsMap.get(fieldsArr[j])).get("PARAM_TYPE_ID"))), new Integer(0)),Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</td>
							<%
							}if(iType ==9){%>
							<td><%=Utility.trimNull(map.get(fieldsArr[j]))%> - <%=Utility.trimNull(map.get("LIST_ID"))%></td>
							<%}%>
						<%}%>
						</tr>
					<%
						iCurrent++;
						iCount++;
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
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<%for(int j=1;j<fieldsArr.length;j++){
						%>
							<td align="center" >-</td>
						<%
						}
						%>
					</tr>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
						<td align="right">
							
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
<%
contract.remove();%>
