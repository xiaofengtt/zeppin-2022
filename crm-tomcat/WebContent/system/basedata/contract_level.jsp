<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String product_code= Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
Integer start_date=Utility.parseInt(request.getParameter("start_date"),null);
Integer end_date=Utility.parseInt(request.getParameter("end_date"),null);
String product_status=Utility.trimNull(request.getParameter("product_status"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open_flag"),null);
Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),null);

String[] totalColumn = new String[0];
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

vo.setProduct_code(product_code);
vo.setProduct_name(product_name);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setProduct_status(product_status);
vo.setCheck_flag(check_flag);
vo.setOpen_flag(open_flag);
vo.setIntrust_flag1(intrust_flag1);

IPageList pageList = product.productList(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+"&product_code="+product_code
					+"&product_name="+product_name
					+"&product_status="+product_status
					+"&check_flag="+check_flag
					+"&open_flag="+open_flag
					+"&start_date="+start_date
					+"&end_date="+end_date
					+"&intrust_flag1="+intrust_flag1;
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function refreshPage()
{
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	location="contract_level.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_code="+document.theform.product_code.value
					+"&product_name="+document.theform.product_name.value
					+"&product_status="+document.theform.product_status.value
					+"&check_flag="+document.theform.check_flag.value
					+"&open_flag="+document.theform.open_flag.value
					+"&start_date="+document.theform.start_date.value
					+"&end_date="+document.theform.end_date.value
					+"&intrust_flag1="+document.theform.intrust_flag1.value
}

function StartQuery()
{
	refreshPage();
}

function level_setting(product_id){
	location = 'contract_level_setting.jsp?product_id='+product_id;
}

window.onload = function(){
initQueryCondition()};

function batchSet(){
	element = document.theform.product_id;
	if (element == null)
	{
		sl_alert("<%=LocalUtilis.language("message.prodToSetLevelUnSelect",clientLocale)%> ！");//未选定要设置等级的产品
		return false;
	}
	if(checkedCount(element) == 0)
	{
		sl_alert("<%=LocalUtilis.language("message.prodToSetLevelUnSelect",clientLocale)%> ！");//未选定要设置等级的产品
		return false;
	}	
	
	var v = showModalDialog('contract_level_bat_add.jsp', '', 'dialogWidth:500px;dialogHeight:240px;status:0;help:0');
	
	if(v==null){
		return false;	
	}else{
		document.theform.level_id.value = v[0];           
		document.theform.level_value_name.value = v[1];  
		document.theform.min_value.value =  v[2]; 
		document.theform.max_value.value =  v[3]; 
		document.theform.action="contract_level_bat_add_action.jsp";
		document.theform.submit();
	}
}
</script>

</HEAD>

<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="level_id" value="">
<input type="hidden" name="level_value_name" value="">
<input type="hidden" name="min_value" value="">
<input type="hidden" name="max_value" value="">
<div id="queryCondition" class="qcMain" style="display:none;width:550px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
			<td>
				<input type="text" name="product_code" value="<%=product_code%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td colspan="3">
				<input type="text" name="product_name" size="65" value="<%=product_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--开始日期-->
			<td>
				<input TYPE="text" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="start_date" value="">	</td>
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
			<td>
				<input TYPE="text" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="end_date" value="">	</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productStatus",clientLocale)%> :</td><!--产品状态-->
			<td>				
				<select name="product_status">
					<%=Argument.getProductAllStatusOptions(product_status)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.checkFlag2",clientLocale)%> :</td><!--审核状态-->
			<td>				
				<select name="check_flag">
					<%=Argument.getCheckFlagOptions(check_flag)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.openFlag",clientLocale)%> :</td><!--发行方式-->	
			<td>
				<select name="open_flag">
					<%=Argument.getOpenFlag(open_flag)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.intrustFlag",clientLocale)%> :	</td><!--产品类别-->
			<td>
				<select name="intrust_flag1">
					<%=Argument.getIntrust_flag1(intrust_flag1)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();">
			    <%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
			</td>
			<!--确认-->
		</tr>
  </table>	
<br>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>

	<TR>
		<%//@ include file="menu.inc" %>

		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4 class="page-title">		
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>						
					</tr>
					<tr>
					<td align=right ></td>
					<td align=right >
					<div class="btn-wrapper">
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
						<!--查询-->
						<button type="button"  class="xpbutton5" accessKey=b id="batButton" name="batButton" onclick="javascript:batchSet();"><%=LocalUtilis.language("message.batchSet",clientLocale)%> (<u>B</u>)</button>
						<!--批量设置-->
						</div>
						<br/>
					</td>				
				</tr>
				
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					
					<tr class="trh">
						<td align="center"><input type="checkbox" name="" class="flatcheckbox" onclick="javascript:selectAllBox(document.theform.product_id,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.ID",clientLocale)%> </td>
                        <!--编号-->
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.currency_name",clientLocale)%> </td><!--币种-->
						<td align="center"><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
						<td align="center"><%=LocalUtilis.language("class.intrustType1Name",clientLocale)%> </td><!--财产类型-->
						<td align="center"><%=LocalUtilis.language("message.levelSetting",clientLocale)%> </td><!--等级设置-->
					</tr>
<%
int iCurrent = 0;
int iCount=0;
List rsList = pageList.getRsList();
Map rowMap = null;
for(int i=0;i<rsList.size();i++)
{ 
	rowMap = (Map)rsList.get(i);
	Integer product_id = (Integer)rowMap.get("PRODUCT_ID");
	Integer item_id = (Integer)rowMap.get("ITEM_ID");
	product_code = Utility.trimNull(rowMap.get("PRODUCT_CODE"));
	product_name = Utility.trimNull(rowMap.get("PRODUCT_NAME"));
	String currency_name = Utility.trimNull(rowMap.get("CURRENCY_NAME"));
	Integer gr_count = (Integer)rowMap.get("GR_NUM");
	BigDecimal gr_money= (BigDecimal)rowMap.get("GR_MONEY");
	Integer jg_count = (Integer)rowMap.get("JG_NUM");
	BigDecimal jg_money= (BigDecimal)rowMap.get("JG_MONEY");
	Integer fact_num = (Integer)rowMap.get("FACT_NUM");
	BigDecimal fact_money= (BigDecimal)rowMap.get("FACT_MONEY");
	String product_statu_name = Utility.trimNull(rowMap.get("PRODUCT_STATUS_NAME"));
	String intrust_type1_name = Utility.trimNull(rowMap.get("INTRUST_TYPE1_NAME"));
%>

			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh"  align="center" ><input type="checkbox" name="product_id" class="flatcheckbox" value="<%=product_id%>"><%=Utility.trimNull(product_code)%></td>
				<td align="left"><%=Utility.trimNull(product_name)%></td>
				<td align="center"><%=Utility.trimNull(currency_name)%></td>	
				<td align="center"><%=Utility.trimNull(product_statu_name)%></td>
				<td align="center"><%=Utility.trimNull(intrust_type1_name)%></td>
				<td align="center" width=60>
					 <a href="javascript:disableAllBtn(true);level_setting('<%=product_id%>')">
					 		<img border="0" src="<%=request.getContextPath()%>/images/system.gif" width="16" height="16"/>	
					 </a>	
				</td>
			</tr>
<%
			iCurrent++;
			iCount++;
	
}

for (; iCurrent < pageList.getBlankSize(); iCurrent++){
%>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>				
			</tr>
<%
}
%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
                <!--合计--><!--项-->
				<!-- <td align="center" >-</td>
				<td align="center" >-</td>
				<td align="right" ></td>
				<td align="center" >-</td>
				<td align="center" >-</td> -->
			</tr>
		</table>

		

		<table border="0" width="100%" class="page-link">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					<td align="right"></td>
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
<%product.remove();
%>
