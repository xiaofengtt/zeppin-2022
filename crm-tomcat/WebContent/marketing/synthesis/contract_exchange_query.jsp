<%@ page contentType="text/html; charset=GBK"   import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

String product_code=Utility.trimNull(request.getParameter("productid"));
IPageList pageList = null;
List list = null;

Integer date_begin=Utility.parseInt(request.getParameter("date_begin"),null);
Integer date_end = Utility.parseInt(request.getParameter("date_end"),null);
String from_cust_name = Utility.trimNull(request.getParameter("from_cust_name"));
String trans_type = Utility.trimNull(request.getParameter("trans_type"));
String contract_sub_bh=Utility.trimNull(request.getParameter("contract_sub_bh"));
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));

String Url = sUrl +"&from_cust_name="+Utility.trimNull(from_cust_name)+ "&product_id=" + Utility.trimNull(overall_product_id)
				+"&contract_sub_bh="+Utility.trimNull(contract_sub_bh)+"&sub_product_id="+sub_product_id;

BenChangeLocal query = EJBFactory.getBenChange();
BenChangeVO vo = new BenChangeVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);

vo.setProduct_id(overall_product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setTrans_date_begin(date_begin);
vo.setTrans_date_end(date_end);
vo.setTrans_type(trans_type);
vo.setFrom_cust_name(from_cust_name);
vo.setSub_product_id(sub_product_id);

if (UNQUERY.equals(new Integer(0))) {
	pageList = query.listAll(vo, _page, pagesize);
	list = pageList.getRsList();
}

status=2;

String viewStr = "CONTRACT_SUB_BH$PRODUCT_ID$FROM_CUST_NAME$TO_CUST_NAME$TRANS_FLAG_NAME$TO_AMOUNT$CHANGE_DATE$CHECK_FLAG$SUMMARY";//默认列
//获得该员工已拥有的菜单
String tempView = Argument.getMyMenuViewStr(menu_id/*"38027"*/,input_operatorCode);
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
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
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

window.onload = function(){
initQueryCondition()
productChange(<%=overall_product_id%>);
};
function showInfo(serial_no,product_id)
{
	location = "contract_exchange_query_info.jsp?Query=<%=Url%>&page=<%=sPage%>&pagesize=<%=sPagesize%>&serial_no=" + serial_no+"&product_id="+product_id;
}
function refreshPage()
{
    document.theform.btnQuery.disabled = true;
	//陶然 2005-8-2 begin
	if(document.theform.exans_date_begin.value!="")
	{
		syncDatePicker(document.theform.exans_date_begin, document.theform.date_begin);
	}
	if(document.theform.exans_date_end.value!="")
	{
		syncDatePicker(document.theform.exans_date_end, document.theform.date_end);
	}
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	var sQuery = document.theform.trans_type.value+" $ "+document.theform.from_cust_name.value+" $ "+document.theform.date_begin.value
					+" $ "+document.theform.date_end.value+" $ "+document.theform.contract_sub_bh.value+" $ "+document.theform.product_id.value+" $ "+sub_product_id;
	if(document.theform.date_begin.value > document.theform.date_end.value )
	{
	    sl_alert("输入时间错误，请重新输入！")
	    location = 'contract_exchange_query.jsp?page=<%=sPage%>'
	+ '&pagesize=' + document.theform.pagesize.value
	+'&productid='+document.theform.productid.value+"&product_id="
	+document.theform.product_id.value+"&contract_sub_bh="
	+document.theform.contract_sub_bh.value
	+"&from_cust_name="
	+document.theform.from_cust_name.value
	+"&trans_type="
	+document.theform.trans_type.value+"&sQuery="+sQuery
    +"&sub_product_id="+sub_product_id;
	
	}
	else{
	//end
        if(document.theform.product_id.value==null ||document.theform.product_id.value.length==0)
        location = 'contract_exchange_query.jsp?page=<%=sPage%>'
	    + '&pagesize=' + document.theform.pagesize.value
     	+'&productid='+document.theform.productid.value+"&product_id="
	   +document.theform.product_id.value+"&contract_sub_bh="
	   +document.theform.contract_sub_bh.value +"&date_begin="+ document.theform.date_begin.value 
	   +"&date_end="+document.theform.date_end.value
	   +"&from_cust_name="
	   +document.theform.from_cust_name.value
	   +"&trans_type="
	   +document.theform.trans_type.value+"&sQuery="+sQuery
       +"&sub_product_id="+sub_product_id;
       else
       location = 'contract_exchange_query.jsp?page=<%=sPage%>'
	   + '&pagesize=' + document.theform.pagesize.value
	   +'&productid='+document.theform.productid.value+"&product_id="
	   +document.theform.product_id.value+"&contract_sub_bh="
	   +document.theform.contract_sub_bh.value+"&date_begin="+ document.theform.date_begin.value 
	   +"&date_end="+document.theform.date_end.value
	   +"&from_cust_name="
	   +document.theform.from_cust_name.value
	   +"&trans_type="
	   +document.theform.trans_type.value+"&sQuery="+sQuery
       +"&sub_product_id="+sub_product_id;
 	}
}

function StartQuery()
{
    refreshPage();
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
	 	var url = 'set_export_info.jsp?flag=5&book_code=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&menu_id=<%=menu_id%>&viewStr=<%=viewStr%>&sQuery=<%=sQuery%>';
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
<form name="theform" method="post" >
<div id="queryCondition" class="qcMain" style="display:none;width:440px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>			
		<td align="right">转让类别:</td>
		<td  align="left"> 
		   <SELECT  size="1" name="trans_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
			<%=Argument.getDictParamOptions(1815,trans_type)%>
			</SELECT>						
		</td>
		<td  align="right">
		受益人名称:
		</td>
		<td align="left">
		 <input type="text" onkeydown="javascript:nextKeyPress(this)" name="from_cust_name" size="25" value="<%=from_cust_name%>">&nbsp;&nbsp;						
		</td>
		</tr>
		<tr>		
			<td align=right height="23">开始日期: </td>
			<td align="left" height="23"><INPUT TYPE="text" NAME="exans_date_begin" class=selecttext 
			<%if(date_begin!=null){%> value="<%=Format.formatDateLine(date_begin)%>"<%}%>>
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exans_date_begin,theform.exans_date_begin.value,this);" tabindex="13">
			<input type ="hidden" value="" name="date_begin">
			</td>
			<td align=right height="23">结束日期:</td><td align="left" height="23"><INPUT TYPE="text" NAME="exans_date_end" class=selecttext 
			<%if(date_end!=null){%> value="<%=Format.formatDateLine(date_end)%>"<%}%> size="20">
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exans_date_end,theform.exans_date_end.value,this);" tabindex="13">
			<input type="hidden" value="" name="date_end">
			</td>
		</tr>
		<tr>
			<td align="right" height="23">
			 合同编号: </td><td align="left" height="23"><input type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="20" value="<%=Utility.trimNull(contract_sub_bh)%>" >
						 </td>			
			<td align="right">产品编号:</td >
			<td align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td >
		</tr>
		<tr>
			<td align="right">产品选择:</td >
			<td align="left" colspan=3>
			<SELECT name="product_id" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,0)%></SELECT>
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

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left width="100%">
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
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
						<td colspan="6" height="18">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				
				<table border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
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
						<td align="center" >查看</td>
					</tr>
					<%
					int index = 0;
					int iCount = 0;
					int iCurrent = 0;
					int check_flag=1;
					BigDecimal to_amount = new BigDecimal("0.000");
					
					for (int i=0; i<list.size(); i++) {
						Map map = (Map)list.get(i);

				  		if(Utility.trimNull(map.get("CHECK_FLAG")) != null)
							check_flag =	Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), 0);
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<!--显示该操作员拥有的字段内容-->
						<%
						for(int j=0;j<fieldsArr.length;j++){
							//表示有金额存在
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		//转让份额
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{
						  			to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(map.get(fieldsArr[j]))));
						  		}
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
								case 6:
							%>
							<td>
								<%=Utility.trimNull(Argument.getProductName(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</td>
							<%
								break;
								case 7:
							%>
							<td>
								<%=Utility.trimNull(Argument.getIntrustOpName(Utility.parseInt(Utility.trimNull(map.get(fieldsArr[j])), new Integer(0))))%>
							</d>
							<%
								break;
								case 8:
							%>
							<td>
								<%=Utility.trimNull(map.get(fieldsArr[j]))%><%=Utility.trimNull(map.get("BANK_SUB_NAME"))%>
							</td>
							<%
								break;
								default:
								break;
								}
							}%>
							<td align="center" >
								<button type="button"  class="xpbutton3" name="btnSelectAll" onclick="javascript:disableAllBtn(true);showInfo('<%=Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),0)%>');">&gt;&gt;</button>
							</td>
						</tr>
					<%
						iCurrent++;
						iCount++;
					}
					
					for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=iCurrent % 2%>">
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
							//表示有金额存在
						  	if(((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue() == 2)
						  	{
						  		//转让份额
						  		if(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD").equals("TO_AMOUNT") && ((Map)fieldsMap.get(fieldsArr[j])).get("FIELD") != null)
						  		{%>
						  			<td align="right" ><%=Format.formatMoney(to_amount)%></td>
						  		<%}
						  	}else{
						  %>
							<td align="center" >-</td>
						<%}
						}
						%>
						<td align="center" >-</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(Url)%></td>
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
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%query.remove();
%>
