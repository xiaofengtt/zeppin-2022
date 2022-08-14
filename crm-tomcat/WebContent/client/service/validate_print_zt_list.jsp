<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%

ValidateprintLocal validate = EJBFactory.getValidateprint();
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
session.removeAttribute("capitalinfolist120301");	
session.removeAttribute("capitalfieldlist120301");	

String product_id=request.getParameter("productid");

if(product_id==null || product_id.equals("null"))
	product_id="";
	
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
//String card_id = Utility.trimNull(request.getParameter("card_id"));
String contract_sub_bh= Utility.trimNull(request.getParameter("contract_sub_bh"));
Integer sproduct_id = Utility.parseInt(request.getParameter("sproduct_id"),overall_product_id);
Integer start_date = Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentDate()));
Integer end_date = Utility.parseInt(request.getParameter("end_date"), new Integer(Utility.getCurrentDate()));
Integer chg_type = Utility.parseInt(request.getParameter("chg_type"),null);
String product_name = Utility.trimNull(request.getParameter("product_name"));
int print_flag = Utility.parseInt(request.getParameter("print_flag"),0);

validate.setInput_man(input_operatorCode);
validate.setProduct_id(sproduct_id);
validate.setContract_sub_bh(contract_sub_bh);
validate.setCust_name(cust_name);
validate.setStart_date(start_date);
validate.setEnd_date(end_date);
validate.setChg_type(chg_type);
validate.setProduct_name(product_name);
validate.setPrint_flag(new Integer(print_flag));
IPageList pageList = validate.listAll1(Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

String sUrl = "validate_print_zt_list.jsp?pagesize="
								+ sPagesize +"&productid="+product_id
								+"&sproduct_id=" + sproduct_id
								+"&cust_name="+cust_name
								+"&end_date="+ end_date
								+ "&start_date="+ start_date
								+"&chg_type="+chg_type
								+"&product_name="+product_name
								+"&print_flag="+print_flag;



%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language=javascript>
window.onload = function(){
initQueryCondition();

}
function StartQuery()
{
   //if(!sl_checkDate(document.theform.start_date_picker,"日期"))	return false;
	//if(!sl_checkDate(document.theform.end_date_picker,"日期"))	return false;
	syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
    
    start_date=document.theform.start_date.value;
	end_date=document.theform.end_date.value;
	
    disableAllBtn(true);

	location = 'validate_print_zt_list.jsp?page=1&pagesize=' + document.theform.pagesize.value +
										'&productid='+document.theform.productid.value+ 
										'&sproduct_id='+ document.theform.sproduct_id.value +
										'&cust_name='+document.theform.cust_name.value+
										'&contract_sub_bh='+ document.theform.contract_sub_bh.value+
										"&end_date="+ end_date+ 
										"&start_date="+ start_date+
										"&chg_type="+document.theform.chg_type.value+
										"&product_name="+document.theform.product_name.value+
										"&print_flag="+document.theform.print_flag.value
														
}

function refreshPage()
{	
	StartQuery();
}

function showBenifiter(serial_no,product_id,contract_sub_bh)
{
	
	location='validate_print_zt_list_single.jsp?product_id='+product_id+'&contract_sub_bh='+contract_sub_bh + '&serial_no='+serial_no;
}

function printUpdateFlag(serial_no,print_flag)
{
	var printInfo = checkedValue(document.theform.printInfo);
	var url = 'print_flag1.jsp?serial_no='+serial_no+'&print_flag='+print_flag+'&printInfo='+printInfo;
	

	if(showModalDialog(url, '', 'dialogWidth:380px;dialogHeight:240px;status:0;help:0') != null)
	{	
		disableAllBtn(false);
		sl_update_ok();
		location.reload();
	}
	disableAllBtn(false);
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.sproduct_id.options.length;i++)
		{
			if(document.theform.sproduct_id.options[i].text.substring(0,j)==value)
			{
				document.theform.sproduct_id.options[i].selected=true;
				prodid = document.theform.sproduct_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.sproduct_id.value="";
			document.theform.sproduct_id.options[0].selected=true;	
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
		for(i=0;i<document.theform.sproduct_id.options.length;i++)
		{
			if(document.theform.sproduct_id.options[i].text.substring(0,j)==value)
			{
				document.theform.sproduct_id.options[i].selected=true;
				prodid = document.theform.sproduct_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.sproduct_id.value="";
			document.theform.sproduct_id.options[0].selected=true;	
		}
		document.theform.sproduct_id.focus();					
	}	
}

//确认单打印
function printVailidateInfo(){
	if(checkedCount(document.theform.printInfo) == 0){
		sl_alert("请选定要打印的确认单的记录！");
		return false;
	}
		//alert();
		var printInfo = checkedValue(document.theform.printInfo);
		disableAllBtn(true);
		//location = 'validate_print_2.jsp?flag=2&printInfo='+printInfo;
		document.theform.printInfos.value = printInfo ;
		//alert(document.theform.printInfos.value);
		document.theform.action='validate_print_zt_list_batch.jsp';
		document.theform.submit();
}

</script>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="POST" >
<input type="hidden" name="printInfos" value="">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td align="right">产品编号:</td>
		<td align="left" >
			<input type="text" maxlength="16" name="productid" value="<%=Utility.trimNull(product_id)%>" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td>业务类别:</td>
		<td>
			<select size="1" name="chg_type" onkeydown="javascript:nextKeyPress(this)" class="chg_type" style="WIDTH: 120px">
				<%=Argument.getChgType1(chg_type)%>
			</select>
		</td>
		</tr>
		<tr>
		<td  align="right">
			产品选择: 
		</td>
			<td  colspan="3" align="left">
			<select size="1" name="sproduct_id" onkeydown="javascript:nextKeyPress(this)" class="productname" >
			<%=Argument.getProductListOptions(new Integer(1), sproduct_id, "", input_operatorCode,17)%></select>
		</td>
		</tr>
		<tr>
		<td  align="right">
		产品名称: 
		</td>
		<td align="left">
			<input type="text" name="product_name" value="<%=Utility.trimNull(product_name)%>" onkeydown="javascript:nextKeyPress(this);" size="20">
		</td>
		<td  align="right">
		客户名称:
		</td>
			<td align="left">
		<input type="text"  name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="20">
		</td>
		</tr>
		<tr>
			<td  align="right">
			合同编号: 
			</td>
			<td align="left">
			<input type="text" maxlength="10" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="20" value="<%=contract_sub_bh%>">&nbsp;&nbsp;
			</td>
			<td align="right">打印标志</td>
			<td>
				<select name="print_flag" style="width:125px;">
					<option value="0" <%if(print_flag==0) out.print("selected");%>>全&nbsp;部</option>
					<option value="1" <%if(print_flag==1) out.print("selected");%>>已打印</option>
					<option value="2" <%if(print_flag==2) out.print("selected");%> >未打印</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">时间范围:</td>
			<td align="left"><INPUT type="text" name="start_date_picker"  class="selecttext" <%if(start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" <%}else{%> value="<%=Format.formatDateLine(start_date)%>" <%}%>> <INPUT type="button" value="▼" class="selectbtn" onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13"> <INPUT type="hidden" name="start_date" value=""></td>
			<td align="right">到:</td>
			<td align="left"><INPUT type="text" name="end_date_picker"  class="selecttext" <%if(end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>" <%}else{%> value="<%=Format.formatDateLine(end_date)%>" <%}%>> <INPUT type="button" value="▼" class="selectbtn" onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13"> <INPUT type="hidden" name="end_date" value="">		
			</td>
		</tr>
		<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button>
		</td>
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
						<td colspan="5"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
						</tr>
				    <tr><td align=right>
				    	<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
				    	&nbsp;&nbsp;&nbsp; <%if ( input_operator.hasFunc(menu_id, 100)){%>
						<button type="button"  class="xpbutton3" accessKey=n name="btnNew" title="新建" onclick="javascript:disableAllBtn(true);newInfo();">新建(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}%> <%if ( input_operator.hasFunc(menu_id, 101)){%>
						<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="删除" onclick="javascript:if(confirmRemove(document.theform.serial_no)) {disableAllBtn(true);document.theform.submit();}">删除(<u>D</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}%>
						<button type="button"  class="xpbutton3" accessKey=p id="printButton" name="printButton" onclick="javascript: printVailidateInfo();">打印(<u>P</u>)</button>
						&nbsp;&nbsp;&nbsp; 
				    </td></tr>
					<tr>
						<td colspan="5">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td width="22;">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
								   onclick="javascript:selectAllBox(document.theform.printInfo,this);">
						</td>
						<td align="center" >合同编号</td>
						<td align="center" >产品名称</td>
						<td align="center" >客户名称</td>
						<td align="center" >变动金额</td>
						<td align="center">业务类别</td>
						<td align="center" >变动日期</td>
						<td align="center" >打印标志</td>
						<td align="center" >打印</td>
					</tr>
<%
BigDecimal chg_money = null;
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
int readonly=0;
String printInfo = "";

List list = pageList.getRsList();
for(int i=0;i<list.size();i++)
{	
	Map map = (Map)list.get(i);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	validate.setContract_sub_bh(Utility.trimNull(map.get("CONTRACT_SUB_BH")));
	validate.setCust_name(Utility.trimNull(map.get("CUST_NAME")));
    chg_type=Utility.parseInt(Utility.trimNull(map.get("BD_BUSI_ID")),null);

	if(chg_type.intValue()==1){
		validate.setChg_type_name("认购");
	}else if(chg_type.intValue()==2){
		validate.setChg_type_name("申购");
	}else{
		validate.setChg_type_name("");
	}

	chg_money=Utility.parseDecimal(Utility.trimNull(map.get("ADD_MONEY")),null);
	validate.setSq_date(Utility.parseInt(Utility.trimNull(map.get("TRADE_DATE")),null));
	validate.setProduct_id(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),null));
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
	
	

	

	validate.setChg_money(Utility.parseDecimal(Utility.trimNull(map.get("CHG_MONEY")),null));
	if(chg_money!=null)
		chg_money = chg_money.setScale(2,2);
	String print_value = "";
	print_flag = Utility.parseInt(Utility.trimNull(map.get("PRINT_FLAG")),0);	
	if(print_flag==1)
		print_value = "已打印";
	else
		print_value = "未打印";	

	printInfo = serial_no + "|" + validate.getProduct_id() + "|" + validate.getContract_sub_bh();
	%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh">
							<input type="checkbox" name="printInfo" value="<%=printInfo%>" class="flatcheckbox">
						</td>
						<td class="tdh" align="center" ><%=contract_sub_bh%></td>
						<td align="left" ><%=product_name%></td>
						<td align="left" ><%=Utility.trimNull(validate.getCust_name())%></td>
						<td align="right" ><%=Utility.trimNull(Format.formatMoney0(chg_money))%></td>
						<td align="center" ><%=Utility.trimNull(validate.getChg_type_name())%></td>
						<td align="center" ><%=Format.formatDateCn(validate.getSq_date())%></td>
						<td align="center" >
							<!--打印标志-->
							<button type="button"  class="xpbutton2" name="btnBenifitor" title="<%=print_value%>" onclick="javascript:disableAllBtn(true); printUpdateFlag(<%=serial_no%>,<%=print_flag %>);" ><%=print_value %></button>
						</td>
						<td align="center" >
							<%if(print_flag!=1){ %>
							<button type="button"  class="xpbutton2" name="btnBenifitor" title="打印" onclick="javascript:disableAllBtn(true); showBenifiter(<%=serial_no%>,<%=validate.getProduct_id()%>,'<%=validate.getContract_sub_bh()%>');" >&gt;&gt;</button>
							<%} %>
						</td>
					</tr>
<%iCurrent++;
iCount++;
}
for (; iCurrent < pageList.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" ></td>
						<td class="tdh" align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<td align="center" >-</td>
						<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize() %> 项</b></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="right" ><%//=Format.formatMoney0(amount)%></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%>
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
<%validate.remove();
%>
