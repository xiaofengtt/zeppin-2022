<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
int counts=0;
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

String sy_type = Utility.trimNull(request.getParameter("sy_type"));
Integer sy_date = Utility.parseInt(request.getParameter("sy_date"), new Integer(0));
int unquery=Utility.parseInt(request.getParameter("UNQUERY"),0);
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

IPageList pageList = null;
List list = new ArrayList();//保存查询结果集
Map map = new HashMap();

ProductLocal local = EJBFactory.getProduct();
GainTotalVO vo = new GainTotalVO();
vo.setBook_code(input_bookCode);
vo.setProduct_id(overall_product_id);
vo.setSy_type(sy_type);
vo.setSy_date(sy_date);
vo.setInput_man(input_operatorCode);
vo.setSub_product_id(sub_product_id);
if (unquery != 1) {
	pageList = local.listGainTotal(vo, _page, pagesize);
	list = pageList.getRsList();
	counts = list.size();
}
String sUrl = "square_1_9.jsp?pagesize=" + pagesize + "&product_id=" + overall_product_id 
	+ "&sy_type="+sy_type+"&sy_date="+sy_date+"&sub_product_id="+sub_product_id; // 换页用

status=2;

String viewStr = "PRODUCT_CODE$PRODUCT_NAME$SY_TYPE_NAME$PROV_LEVEL_NAME$BEFORE_PRICE$SY_RATE$SY_MONEY$AFTER_PRICE$SY_DATE";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr(menu_id/*"38028"*/,input_operatorCode);
//如果该员工没有设置菜单，则为默认菜单
if (tempView!=null && !tempView.equals(""))
	viewStr = tempView;

//将拥有的菜单与设置的所有菜单进行匹配
Map fieldsMap = Argument.getMenuViewMap(menu_id,viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap(); 
	viewStr = "";
}
%>

<HTML>
<HEAD>
<TITLE>收益分配汇总查询</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
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
	if(document.theform.sy_date_picker.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker,"分配日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker, document.theform.sy_date);
	}
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	disableAllBtn(true);
	var sQuery = document.theform.product_id.value+" $ "+document.theform.sy_type.value+" $ "+document.theform.sy_date.value+" $ "+sub_product_id;
	location = "square_1_9.jsp?product_id="+document.theform.product_id.value + "&sy_type=" + document.theform.sy_type.value + "&sy_date=" + document.theform.sy_date.value + "&sub_product_id=" + sub_product_id + "&sQuery="+sQuery;
}

function refreshPage()
{
	document.theform.btnQuery.disabled = true;
	if(document.theform.sy_date_picker.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker,"分配日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker, document.theform.sy_date);
	}
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    	sub_product_id = document.theform.sub_product_id.value;
	var sQuery = document.theform.product_id.value+" $ "+document.theform.sy_type.value+" $ "+document.theform.sy_date.value+" $ "+sub_product_id;
	location = 'square_1_9.jsp?page=1&pagesize=' + document.theform.pagesize.value+"&product_id="+document.theform.product_id.value + "&sy_type=" + document.theform.sy_type.value + "&sy_date=" + document.theform.sy_date.value + "&sub_product_id=" + sub_product_id + "&sQuery="+sQuery;
}

function writetxtfile()
{
	if(checkedCount(document.theform.serial_no) == 0)
	{
		sl_alert("请选定要导出选择的记录！");
		return false;
	}
	if(sl_confirm("确认要导出数据"))
		document.theform.submit();
}

function Detail(product_id,sy_date,sy_type,prov_level)
{
	location = 'square_1_11.jsp?product_id='+product_id+'&sy_type='+sy_type+'&sy_date='+sy_date+'&prov_level='+prov_level+'&pagesize='+document.theform.pagesize.value;
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
	 	var url = 'set_export_info.jsp?flag=6&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=<%=menu_id%>&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>';
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
<BODY  class="BODY">
<form name="theform" action="download.jsp" method="get">

<INPUT TYPE="hidden" NAME="book_code"   value="<%=input_bookCode%>">
<INPUT TYPE="hidden" NAME="outporttype"   value="4">

<div id="queryCondition" class="qcMain" style="display:none;width:390px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td align=right>
			类型:</td>
		<td  align="left"><SELECT style="width: 70" onkeydown="javascript:nextKeyPress(this)" size="1" name="sy_type">
			<%=Argument.getDictParamOptions(1116, sy_type)%></select>
			</td>
		<td  align="right">分配日期:</td>
		<td  align="left"><INPUT TYPE="text" NAME="sy_date_picker" class=selecttext 
			<%if(sy_date!=null){%> value="<%=Format.formatDateLine(sy_date)%>"<%}%>>
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.sy_date_picker,theform.sy_date_picker.value,this);" tabindex="13">
			<INPUT TYPE="hidden" NAME="sy_date"   value="">&nbsp;&nbsp;
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
		<SELECT name="product_id" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,status)%></SELECT>
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
		<td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>
</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<%//int  showTabInfo =  showTabInfoTop.intValue();
					//if(showTabInfo != 1){ %>
					<tr>
						<td width="79%" colspan=5><IMG height=28 src="/images/member.gif" width=32 align=absMiddle border=0><b><%=menu_info%></b>
						<td align="right" width="21%"></td>
					</tr>
					<%//} %>
					<tr>
						<td align=right colspan=6> 
							<%if (input_operator.hasFunc(menu_id, 108)){%>
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
							<%} %>
							<%if (input_operator.hasFunc(menu_id, 100)) {%>
							<button type="button"  class="xpbutton5" <%=counts==0?"disabled":""%> accessKey=w name="btnOk" title="生成XSL文件并导出" onclick="javascript:writetxtfile();">导出数据(<u>W</u>)</button>
							&nbsp;&nbsp;&nbsp; <%}%>
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
						<td align="center" >明细</td>
					</tr>
					<%
					int iCurrent = 0;
					Integer serial_no;
					//本次分配金额
					java.math.BigDecimal sy_money=new java.math.BigDecimal(0.00);
					for (int i=0; i<list.size(); i++) {
						map = (Map)list.get(i);
						serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")), new Integer(0));
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<!--显示该操作员拥有的字段内容-->
						<%
						for(int j=0;j<fieldsArr.length;j++){
							int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
							if(j == 0) {%>
								<td class="tdh" align="center" >
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%">
											<input  type="checkbox" name="serial_no" value="<%=input_bookCode%>$<%=Utility.trimNull(map.get("PRODUCT_ID"))%>$<%=Utility.trimNull(map.get("SY_TYPE"))%>$<%=Utility.trimNull(map.get("SY_DATE"))%>$<%=Utility.trimNull(map.get("PROV_LEVEL"))%>$<%=Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0))%>" class="flatcheckbox">
										</td>
										<td width="90%" align="left">
										<%if(iType == 6){%>
										<%=Argument.getProductCode(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0)))%>
										<%}else if(iType == 7){%>
										<%=Argument.getProductName(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0)))%>
										<%}else{%>
										<%=Utility.trimNull(map.get(fieldsArr[j]))%>
										<%}%>
										</td>
									</tr>
								</table>
								</td>
							<%}else{
							//表示有金额存在
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		//本次分配金额
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("SY_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{
						  			sy_money = sy_money.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
						  		}
						  	}
							
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
								case 6:
							%>
							<td>
								<%=Argument.getProductCode(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0)))%>
							</td>
							<%
								break;
								case 8:
							%>
								<td align="left" >
									<%=Argument.getProductName(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0)))%>
								</td>
							<%
										break;
										default:
										break;
									}
								}
							}%>
						<td align="center" >
							<button type="button"  class="xpbutton2" name="btnDetail" onclick="javascript:disableAllBtn(true);Detail(<%=Utility.trimNull(map.get("PRODUCT_ID"))%>,<%=Utility.trimNull(map.get("SY_DATE"))%>,<%=Utility.trimNull(map.get("SY_TYPE"))%>,<%=Utility.trimNull(map.get("PROV_LEVEL"))%>);">&gt;&gt;</button>
						</td>
						</tr>
						<%
						iCurrent ++;

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
					<%}%>
					<tr class="trbottom">
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<%for(int j=1;j<fieldsArr.length;j++){
						%>
						<%if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2){
					  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("SY_MONEY") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null) {%>
					  		<!--本次分配金额-->
							<td align="right" ><%=Format.formatMoney(sy_money)%></td>
							<%}else{%>
							<td align="center" ></td>
							<%}%>
						<%}else{%>
						<td align="center" ></td>
						<%}%>
						<%
						}
						%>			
						<td align="center" ></td>
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
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%local.remove();%>
