<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//String product_code= Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
//Integer start_date=Utility.parseInt(request.getParameter("start_date"),null);
Integer end_date=Utility.parseInt(request.getParameter("end_date"),null);
Integer deploy_date=Utility.parseInt(request.getParameter("deploy_date"),null);
String product_status=Utility.trimNull(request.getParameter("product_status"), "110202");
//Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),null);
//Integer open_flag = Utility.parseInt(request.getParameter("open_flag"),null);
//Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),new Integer(0));
/*if(Argument.getSyscontrolValue("SINGLE")==0){
	intrust_flag1 = new Integer(2);
}*/

String[] totalColumn = new String[0];
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

//vo.setProduct_code(product_code);
vo.setProduct_name(product_name);
//vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setProduct_status(product_status);
//vo.setCheck_flag(check_flag);
//vo.setOpen_flag(open_flag);
//vo.setIntrust_flag1(intrust_flag1);

IPageList pageList = product.productList(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

/*sUrl = sUrl+"&product_code="+product_code
					+"&product_name="+product_name
					+"&product_status="+product_status
					+"&check_flag="+check_flag
					+"&open_flag="+open_flag
					+"&start_date="+start_date
					+"&end_date="+end_date; */
String qs = Utility.getQueryString(request, new String[]{"product_name", "product_status", "end_date"});
sUrl += "&"+qs;

product.remove();
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
function showInfo(product_id)
{	
	location = '/marketing/base/product_query_info.jsp?product_id=' + product_id;
}


function editInfo(product_id)
{	
	location = '/marketing/base/product_task_list.jsp?product_id=' + product_id;
}

function showManager(product_id)
{
	if(showModalDialog('/marketing/base/product_manager_update.jsp?product_id=' + product_id,'','dialogWidth:360px;dialogHeight:300px;status:0;help:0')!=null)
	{	
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ");//保存成功
		location.reload();
	}
}

function refreshPage() {
	syncDatePicker(document.theform.deploy_date_picker, document.theform.deploy_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	var url ="product_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&product_name="+document.theform.product_name.value
					+"&product_status="+document.theform.product_status.value
					+"&deploy_date="+document.theform.deploy_date.value
					+"&end_date="+document.theform.end_date.value;
					
	//if(document.theform.intrust_flag1)
    //		url = url + "&intrust_flag1="+document.theform.intrust_flag1.value;
	
	location.href = url;
}

function StartQuery()
{
	refreshPage();
}

function openQuery(product_id,item_id)
{	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}

function fxLevel(product_id)
{
	if(showModalDialog('/marketing/base/fx_level.jsp?product_id='+product_id,'','dialogWidth:330px;dialogHeight:240px;status:0;help:0') != null)
	{
		sl_update_ok();
	}
}

function htSet(product_id,product_name)
{
	location = '/marketing/base/productHTSet.jsp?product_id='+product_id+'&product_name='+product_name;
}

window.onload = function(){
		initQueryCondition();

		var product_status = document.theform.product_status.value;
		if (product_status=="110202")
			show(1);
		else if (product_status=="110203")
			show(2);
		else
			show(3);
	};

function changeLabel(parm) {
	if (parm==1)
		document.theform.product_status.value = "110202";
	else if (parm==2)
		document.theform.product_status.value = "110203";
	else
		document.theform.product_status.value = "110204";

	refreshPage();
}

function show(parm){	
   for (var i=1;i<=3;i++) {  
	     if (i!= parm) {	     	
	      	document.getElementById('d' + i).background = '<%=request.getContextPath()%>/images/headdark_00_01.gif';
	      	if (document.getElementById("r"+i))
			 	document.getElementById('r' + i ).style.display = 'none';
		 } else {
		   	document.getElementById('d'+i).background = '<%=request.getContextPath()%>/images/head_00_01.gif';		   
		    if (document.getElementById("r"+i))
			  	document.getElementById('r' + i).style.display = '';
		 } 
	}
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<input type="hidden" name="product_status" value='<%=product_status%>'/>

<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName4",clientLocale)%> :</td><!--产品名称-->
			<td colspan="3">
				<input type="text" name="product_name" size="66" value="<%=product_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
			<td>
				<input TYPE="text" style="width:120" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        		<input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        		<input TYPE="hidden" NAME="end_date" value="">
			</td>
			<td align="right">分配日期 :</td>
			<td>
				<input TYPE="text" style="width:120" NAME="deploy_date_picker" class=selecttext value="<%=Format.formatDateLine(deploy_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
		        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.deploy_date_picker,theform.deploy_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
		        <input TYPE="hidden" NAME="deploy_date" value="">
			</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->(<u>O</u>)</button></td>
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
					<tr>
						<td colspan=4>
							<IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><!--基本管理-->
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td><!--基本管理>>产品管理-->						
					</tr>
					<tr>
						<td align=right ><button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button></td>				
					</tr>
					<tr><td>&nbsp;&nbsp;&nbsp; </td></tr>
				</table>

				<div align="left"  style="margin-left:10px;margin-bottom:10px;">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
						<TR>							
							<TD vAlign=top>&nbsp;</TD>					
				            <TD id="d1" style="background-repeat: no-repeat" onclick=changeLabel(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;推介期产品 </TD>
				            <TD id="d2" style="background-repeat: no-repeat" onclick=changeLabel(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;存续期产品 </TD>						
				            <TD id="d3" style="background-repeat: no-repeat" onclick=changeLabel(3) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;已结束产品 </TD>					
						</TR>
					</TABLE>
				</div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">					
					<tr class="trh">
						<td align="center" rowspan=2 ><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
						<td align="center" rowspan=2 ><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center" rowspan=2 ><%=LocalUtilis.language("class.currency_name",clientLocale)%> </td><!--币种-->
						<td align="center" colspan=2 ><%=LocalUtilis.language("class.jgMoney",clientLocale)%> </td><!--实际规模-->
						<td align="center" rowspan=2 ><%=LocalUtilis.language("class.intrustType1Name",clientLocale)%> </td><!--财产类型-->
					</tr>
					<tr class="trh">	
						<td align="center" ><%=LocalUtilis.language("class.factNum",clientLocale)%> </td><!--份数-->
						<td align="center" ><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--金额-->						
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
	String product_code = Utility.trimNull(rowMap.get("PRODUCT_CODE"));
	product_name = Utility.trimNull(rowMap.get("PRODUCT_NAME"));
	String currency_name = Utility.trimNull(rowMap.get("CURRENCY_NAME"));
	Integer gr_count = (Integer)rowMap.get("GRHT_NUM");
	BigDecimal gr_money= (BigDecimal)rowMap.get("GR_MONEY");
	Integer jg_count = (Integer)rowMap.get("JGHT_NUM");
	BigDecimal jg_money= (BigDecimal)rowMap.get("JG_MONEY");
	Integer fact_num = (Integer)rowMap.get("FACTHT_NUM");
	BigDecimal fact_money= (BigDecimal)rowMap.get("FACT_MONEY");
	String product_statu_name = Utility.trimNull(rowMap.get("PRODUCT_STATUS_NAME"));
	String intrust_type1_name = Utility.trimNull(rowMap.get("INTRUST_TYPE1_NAME"));
%>
            <tr class="tr<%=(iCurrent % 2)%>" <%if(product_id!=null){%> style="cursor:hand;" onDBlclick="javascript:openQuery('<%=product_id%>','<%=item_id%>');"<%}%>>
				<td class="tdh"  align="center" ><%=Utility.trimNull(product_code)%>-<%=Utility.trimNull(product_id)%></td>
				<td align="left"><%=Utility.trimNull(product_name)%></td>
				<td align="center"><%=Utility.trimNull(currency_name)%></td>
				<td align="right"><%=Utility.trimNull(fact_num)%></td>
				<td align="right"><%=Utility.trimNull(Format.formatMoney(fact_money))%></td>
				<td align="center"><%=Utility.trimNull(intrust_type1_name)%></td>
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
				<td class="tdh" align="left" colspan="6">
					<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;
					<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
				</td>
			</tr>
		</table>

		<br>

		<table border="0" width="100%">
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