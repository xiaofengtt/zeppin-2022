<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
IntrustContractLocal contract = EJBFactory.getIntrustContract();
session.removeAttribute("capitalinfolist120301");	
session.removeAttribute("capitalfieldlist120301");	

String product_code=request.getParameter("productid");

if(product_code==null || product_code.equals("null"))
	product_code="";
	
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String query_contract_sub_bh= Utility.trimNull(request.getParameter("query_contract_sub_bh"));
//添加product_id hesl 20110512
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")), overall_product_id);
input_bookCode=new Integer(1);
contract.setBook_code(input_bookCode);
contract.setInput_man(input_operatorCode);
contract.setProduct_id(product_id);
contract.setContract_sub_bh(query_contract_sub_bh);
contract.setCust_name(cust_name);
contract.setCheck_flag(new Integer(0));
if(UNQUERY.equals(new Integer(0)))
contract.queryEntityContract();

sUrl = sUrl +"&productid="+product_code + "&product_id=" + product_id+"&cust_name="+cust_name+"&card_id="+card_id;
contract.gotoPage(sPage, sPagesize);

boolean bNewable = true;
boolean bRemoveable = false;
String product_name="";
if (product_id!=null)
{
	IntrustProductLocal product = EJBFactory.getIntrustProduct();
	if(product_id.intValue()>0)
	{
		product.setProduct_id(product_id);
		product.load();
		product_name=product.getProduct_name();
		if ("110202".equals(product.getProduct_status())) //推介期
		{
			bNewable = true;
			bRemoveable = true;
		}
	}	
	product.remove();
}
else
{
	bNewable = true;
}

String options = Argument.getProductListOptions(input_bookCode, product_id, "", input_operatorCode,13);
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
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

</HEAD>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
initQueryCondition()};

function showInfo(serialno, checkflag, bank_id)
{
	var sQuery = document.theform.productid.value + "$" +document.theform.product_id.value + "$" + document.theform.cust_name.value + "$" + document.theform.query_contract_sub_bh.value  + "$" + "$" + document.theform.pagesize.value;
   
	location = 'purchase_info3.jsp?firstflag=1&serial_no='+serialno+'&check_flag='+checkflag+'&bank_id='+bank_id+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value + '&product_id='+ document.theform.product_id.value +'&cust_name='+document.theform.cust_name.value+'&query_contract_sub_bh='+ document.theform.query_contract_sub_bh.value+'&sQuery='+sQuery;
}

function newInfo()
{
	var sQuery = document.theform.productid.value + "$" +document.theform.product_id.value + "$" + document.theform.cust_name.value + "$" + document.theform.query_contract_sub_bh.value  + "$" + "$" + document.theform.pagesize.value;
   
	location = 'purchase_info3.jsp?firstflag=1&page=1&pagesize=' + document.theform.pagesize.value + '&product_id=' + document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value+'&query_contract_sub_bh='+ document.theform.query_contract_sub_bh.value+'&sQuery='+sQuery;;
}

function StartQuery()
{
    disableAllBtn(true);
	location = 'purchase3.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value+ '&product_id='+ document.theform.product_id.value +'&cust_name='+document.theform.cust_name.value+'&query_contract_sub_bh='+ document.theform.query_contract_sub_bh.value;
}

function refreshPage()
{
	StartQuery();
}

function showBenifiter(contract_id)
{
   var sQuery = document.theform.productid.value + "$" +document.theform.product_id.value + "$" + document.theform.cust_name.value + "$" + document.theform.query_contract_sub_bh.value  + "$" + "$" + document.theform.pagesize.value;
   location = 'benifiter.jsp?from_flag_benifitor=2&contract_id='+contract_id+'&page=1&sQuery=' + sQuery;
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

function showCapitalDeatil(capital_use,product_id,contract_bh,busi_id,readonly,cust_id,start_date,end_date){
	location='../capital.jsp?busi_id=120388&capital_use='+capital_use+'&readonly='+readonly+'&contract_bh='+contract_bh+'&product_id='+product_id+'&busi_id='+busi_id+'&cust_id='+cust_id+'&start_date='+start_date+'&end_date='+end_date;
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

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="POST" action="purchase_remove3.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
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
		<td align="left">
			<input type="text" maxlength="16" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td  align="right">产品名称 :</td>
		<td>
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td  align="right">产品名称:</td>
		<td  colspan="3" align="left" id="select_id">
			<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
				<%=Argument.getProductListOptions(input_bookCode, product_id, "", input_operatorCode,13)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right">客户名称:</td>
		<td align="left">
			<input type="text" maxlength="16" name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
		</td>
		<td  align="right">合同编号:</td>
		<td align="left">
			<input type="text" maxlength="10" onkeydown="javascript:nextKeyPress(this)" name="query_contract_sub_bh" size="10" value="<%=query_contract_sub_bh%>">&nbsp;&nbsp;
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
						<td colspan="5" class="page-title"><b><%=menu_info%></b></td>
						</tr>
				    <tr><td align=right>
				    <div class="btn-wrapper">
				    <button type="button"  class="xpbutton3" accessKey=q  id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
				    	&nbsp;&nbsp;&nbsp; <%if (bNewable && input_operator.hasFunc(menu_id, 100))
{%>
						<button type="button"  class="xpbutton3"  accessKey=n name="btnNew" title="新建" onclick="javascript:disableAllBtn(true);newInfo();">新建(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}%> <%if (bRemoveable && input_operator.hasFunc(menu_id, 101))
{%>
						<button type="button"  class="xpbutton3"  accessKey=d name="btnCancel" title="删除" onclick="javascript:if(confirmRemove(document.theform.serial_no)) {disableAllBtn(true);document.theform.submit();}">删除(<u>D</u>)</button>
						<%}%>
						</div>
						<br/>
				    </td></tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh">
						<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;合同编号</td>
						<td align="center" >产品名称</td>
						<td align="center" >客户编号</td>
						<td align="center" >客户名称</td>
						<td align="center"  sort="num">合同金额</td>
						<td align="center" >签署日期</td>
						<td align="center" >状态</td>
						<td align="center" >审核状态</td>
						<td align="center" >财产信息</td>
						<td align="center" >编辑</td>
						<td align="center" >受益人</td>
					</tr>
<%
BigDecimal amount = new BigDecimal("0.000");
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
int readonly=0;
while(contract.getNext3() && iCurrent < contract.getPageSize())
{
	serial_no = contract.getSerial_no();
	if(contract.getRg_money() != null)
		amount = amount.add(contract.getRg_money());		
	readonly=0;
	if(contract.getCheck_flag()!=null)
		if(contract.getCheck_flag().intValue()==2)	
			readonly=1;
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%">
								<%if(contract.getCheck_flag().intValue()==1 &&contract.getHt_status().equals("120101")){%> <input  type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"> <%}%>
								</td>
								<td width="90%" align="left"><%=Utility.trimNull(contract.getContract_sub_bh())%></td>
							</tr>
						</table>
						</td>
						<td align="left" ><%=Argument.getProductName(contract.getProduct_id())%></td>
						<td align="center" ><%=Utility.trimNull(contract.getCust_no())%></td>
						<td align="left" ><%=Utility.trimNull(contract.getCust_name())%></td>
						<td align="right" ><%=Utility.trimNull(Format.formatMoney0(contract.getRg_money()))%></td>
						<td align="center" ><%=Format.formatDateCn(contract.getQs_date())%></td>
						<td align="center" ><%=Utility.trimNull(contract.getHt_status_name())%></td>
						<td align="center" ><%=Utility.trimNull(contract.getCheck_flag_name())%></td>
						<td align="center" >
						<%if (input_operator.hasFunc(menu_id, 102)){%>
							<button type="button"  class="xpbutton2"  name="btnBenifitor" title="查看财产信息信息" onclick="javascript:disableAllBtn(true);
							showCapitalDeatil('信托资产','<%=contract.getProduct_id()%>','<%=contract.getContract_bh()%>','120388','<%=readonly%>','<%=contract.getDf_cust_id()%>','<%=contract.getStart_date()%>','<%=contract.getEnd_date()%>')">&gt;&gt;</button>
						<%}%>
						</td>
						<td align="center" >
<%if (input_operator.hasFunc(menu_id, 102)) {
if(contract.getBank_id().equals("")){%>

						<button type="button"  class="xpbutton2"  name="btnEdit" onclick="javascript:disableAllBtn(true);showInfo(<%=serial_no%>,<%=contract.getCheck_flag()%>);">&gt;&gt;</button>
						<%}else{
							if (contract.getCheck_flag().intValue()==2) { %>
								-
						<%	} else {
						%>	
						<button type="button"  class="xpbutton2" name="btnEdit" 
							onclick="javascript:disableAllBtn(true);showInfo(<%=serial_no%>,<%=contract.getCheck_flag()%>,<%=contract.getBank_id()%>);">&gt;&gt;</button>
						<% } %>
<%}}%>						
						</td>

						<td align="center" >
<%if (input_operator.hasFunc(menu_id, 102)){%>
						<button type="button"  class="xpbutton2" name="btnBenifitor"  title="查看受益人信息" onclick="javascript:disableAllBtn(true);
						showBenifiter(<%=contract.getSerial_no()%>);">&gt;&gt;</button>
						<%}%></td>
					</tr>
					<%iCurrent++;
iCount++;
}

for (; iCurrent < contract.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
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
						<td class="tdh" align="center" ><b>合计 <%=contract.getRowCount()%> 项</b></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="right" ><%=Format.formatMoney0(amount)%></td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=contract.getPageLink(sUrl)%></td>

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
<%contract.remove();
%>
