<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*"  %>
<%@ include file="/includes/operator.inc" %>

<%String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();

String contract_sub_bh = request.getParameter("contract_sub_bh");

String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
int prov_level = Utility.parseInt(request.getParameter("prov_level"),2);
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);
vo.setBook_code(input_bookCode);
vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setInput_man(input_operatorCode);
vo.setCheck_flag(new Integer(2));
IPageList pageList = benifitor.QueryBenifitorProv(vo,Utility.parseInt(sPage,0),Utility.parseInt(sPagesize,8));


String sUrl = "benifiter_level_check.jsp?pagesize=" + sPagesize + "&product_id=" + product_id +"&contract_sub_bh="+ contract_sub_bh+"&cust_name="+cust_name+"&prov_level="+prov_level+"&cust_no="+cust_no;

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,0);
options = options.replaceAll("\"", "'");
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

window.onload = function(){
initQueryCondition()};



function refreshPage()
{
	StartQuery();
}

function StartQuery()
{
	disableAllBtn(true);
	location = 'benifiter_level_check.jsp?contract_sub_bh='+document.theform.contract_sub_bh.value+'&page=1&pagesize='+
	 document.theform.pagesize.value+'&product_id='+document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value+
	 '&cust_no='+document.theform.cust_no.value;
}

function op_check(s)
{
		if(checkedCount(document.theform.s_id) == 0)
	{
		sl_alert("请选定要审核的记录！");
		return false;
	}
	if (s==1)
		str="你确定选定记录审核通过吗？";
	else 
		str="你确定选定记录审核未通过吗？"; 
			
	if(confirm(str)) {
		disableAllBtn(true);
		document.theform.s_action.value=s;
		document.theform.submit();
	}
}
/*搜索产品*/
function searchProduct(value){
	if(event.keyCode == 13){
		searchProduct2(value);
	}
}
/*搜索产品2*/
function searchProduct2(value){
	var prodid=0;
	if( value != ""){
        var j = value.length;

		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.indexOf(value) >= 0)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.product_id.options[0].selected=true;
		}
	}
	
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)' style='width:345px;'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox"><%@ include file="/includes/waiting.inc"%> 
<form name="theform"  action="benifiter_level_check_info.jsp" method="post">
<input type="hidden" name="s_action" value ="0">

<div id="queryCondition" class="qcMain" style="display:none;width:490px;">
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
		<td align="left" width="180px;">
			<input type="text" name="product_code" value="" onKeyDown="javascript:searchProduct(this.value);" size="10">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct2(document.theform.product_code.value);"></button>
		</td>
		<td  align="right">产品名称 :</td>
		<td  align="left" >
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
						<td align="right">产品名称:</td>
						<td colspan="3" align="left" id="select_id">
							<SELECT size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class=productname style="width:345px;">
								<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,0)%>
							</SELECT> 
						</TD>
                       </tr><tr>
						<td  align="right">
						合同编号: 
						</td>
						<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" style="width: 120px" value="<%=Utility.trimNull(contract_sub_bh)%>">&nbsp;&nbsp;</td>
						<td  align="right">
						受益人编号: 
						</td>
						<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="cust_no" style="width: 120px" value="<%=Utility.trimNull(cust_no)%>">&nbsp;&nbsp;</td>
						</tr>
						<tr>
						<td  align="left">受益人名称: </td>
						<td align="left" colspan="3">
							<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" style="width: 120px">
						</td>
                         </tr>
                         <tr>
						<td align=center colspan=4>
						<button type="button"  class="xpbutton3" accesskey="o" name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</BUTTON>
						</td>
						</tr>
</table>

</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=8 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr><td align=right> 
					<div class="btn-wrapper">
						<%if (input_operator.hasFunc(menu_id,108) ){%>
						<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}if (input_operator.hasFunc(menu_id,103) ){%>
						<button type="button"  class="xpbutton4"  onclick="javascript:op_check(1)">审核通过</button>
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton4"  onclick="javascript:op_check(2)">审核不通过</button>
						<%}%>
						</div><br/>
					</td></tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%" >
					<tr class="trh">
						<TD align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.s_id,this);">产品名称</TD>
						<td align="center"  noWrap>合同编号</td>
						<td align="center"  >受益人编号</td>
						<td align="center" >受益人名称</td>
						<td align="center" >受益份额</td>
						<td align="center" >受益日期</td>
						<td align="center" >受益优先级</td>
						<td align="center" width="10%">调整后受益优先级</td>
						<td align="center" >收益级别</td>
						<td align="center" >调整后收益级别</td>
					</tr>
<%

int iCount = 0;
int iCurrent = 0;
Integer serial_no;
int iBenflag=0;
List list = pageList.getRsList();
for (;iCount<list.size();)
{
	Map map = (Map)list.get(iCount);
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
//	sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String list_id = Utility.trimNull(map.get("LIST_ID"));
	cust_no = Utility.trimNull(map.get("CUST_NO"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	BigDecimal ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0.00));
	Integer ben_date = Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0));
	String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	String prov_level_name_temp = Utility.trimNull(map.get("PROV_LEVEL_NAME_TEMP"));
	Integer prov_flag = Utility.parseInt(Utility.trimNull(map.get("PROV_FLAG")),new Integer(1));
	Integer prov_flag_temp = Utility.parseInt(Utility.trimNull(map.get("PROV_FLAG_TEMP")),new Integer(1));
	String sProv_flag  = null;
	if(prov_flag.intValue() == 2)
		sProv_flag ="一般";
	else if(prov_flag.intValue() == 3)
		sProv_flag ="劣后";
	else
		sProv_flag ="优先";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left" >
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
	
								<tr>
									<td width="10%"><input class="flatcheckbox" type="checkbox" name="s_id" value="<%=serial_no%>"></td>
									<td width="90%"><%=Utility.trimNull(product_name)%></td>
								</tr>
							</table>
						</td>
						<td align="left"><%=Utility.trimNull(contract_sub_bh)%>-<%=Utility.trimNull(list_id)%></td>
						<td class="tdh" align="left"><%=Utility.trimNull(cust_no)%></td>
						<TD align="left"  noWrap><%=Utility.trimNull(cust_name)%></TD>
						<td align="right"><%=Format.formatMoney(ben_amount)%></td>
						<td align="center"><%=Format.formatDateCn(ben_date)%></td>
						<td align="center"><%=Utility.trimNull(sProv_flag)%></td>
						<td align="center"><%=Argument.getTintegerparamValue(new Integer(3003),prov_flag_temp)%></td>
						<td align="center" width="10%"><%=Utility.trimNull(prov_level_name)%></td>
						<td align="center" width="10%"><%=Utility.trimNull(prov_level_name_temp)%></td>	
					</tr>
					<%iCurrent++;
iCount++;
}

for (; iCurrent < pageList.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<TD align="center" ></TD>
						<TD align="center" ></TD>
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
						<td class="tdh" align="center"><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
						<TD align="center">-</TD>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
						
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
<%benifitor.remove();%>
