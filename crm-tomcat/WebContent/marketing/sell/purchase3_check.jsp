<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean bSuccess = false;
String product_code=request.getParameter("productid");
session.removeAttribute("capitalinfolist120388");	
session.removeAttribute("capitalfieldlist120388");	

Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);
input_bookCode=new Integer(1);

if(request.getMethod().equals("POST"))
{
	try{
		IntrustContractLocal contract1 = EJBFactory.getIntrustContract();
		String[] s = request.getParameterValues("serial_no");
		if(s == null)
		{
			s = new String[1];
			s[0] = request.getParameter("serial_no");
		}
	
		for(int i = 0; i < s.length; i++)
		{
			int serial_no = Utility.parseInt(s[i], 0);
			if(serial_no != 0)
			{
				contract1.setSerial_no(new Integer(serial_no));
				contract1.setInput_man(input_operatorCode);
				contract1.checkEntity();
			}
		}
		contract1.remove();
		bSuccess = true;
	}catch(BusiException e){
// 		throw new BusiException(e.getMessage());
		out.println("<script type=\"text/javascript\">alert('"+e.getMessage()+"')</script>");
		out.println("<script type=\"text/javascript\">location='purchase3_check.jsp?product_id="+product_id+"';</script>");
		return;
	}
}

IntrustContractLocal contract = EJBFactory.getIntrustContract();
String contract_sub_bh = request.getParameter("contract_sub_bh");
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

contract.setCust_name(cust_name);
contract.setContract_sub_bh(contract_sub_bh);
contract.setProduct_id(product_id);
contract.setBook_code(input_bookCode);
contract.setInput_man(input_operatorCode);
contract.setCheck_flag(new Integer(1));
contract.queryEntityContract();

sUrl = sUrl+ "&product_id="+product_id+"&contract_sub_bh=" + contract_sub_bh+"&cust_name="+cust_name;
contract.gotoPage(sPage, sPagesize);

String options = Argument.getProductListOptions(input_bookCode, product_id,"", input_operatorCode,18);
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

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
initQueryCondition()};
<%if(bSuccess){%>
	sl_check_ok("purchase3_check.jsp?product_id=<%=product_id%>");
<%}%>

function showInfo(serialno)
{
	location = 'purchase_info3_check.jsp?checkflag=1&firstflag=1&serial_no=' + serialno + '&product_id='+document.theform.product_id.options[document.theform.product_id.selectedIndex].value;
}

function refreshPage()
{
	StartQuery();
}

function StartQuery()
{    
    disableAllBtn(true);
	location = 'purchase3_check.jsp?page=1&pagesize=' + document.theform.pagesize.value + '&product_id='+document.theform.product_id.value+'&cust_name=' + document.theform.cust_name.value+'&contract_sub_bh='+ document.theform.contract_sub_bh.value;
}

function op_check()
{
	if(checkedCount(document.theform.serial_no) == 0)
	{
		sl_alert("请选定要审核的记录！");
		return false;
	}
	if (sl_check_pass()){
	disableAllBtn(true);
		document.theform.submit();}
}

function showBenifiter(contract_id)
{
   location = "benifiter_check.jsp?from_flag_benifitor=2&contract_id=" + contract_id + '&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
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

function showCapitalDeatil(capital_use,product_id,contract_bh,busi_id)
{
	location='../capital.jsp?readonly=1&busi_id=120388&capital_use='+capital_use+'&contract_bh='+contract_bh+'&product_id='+product_id+'&busi_id='+busi_id;
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
<form name="theform" method="post" action="purchase3_check.jsp">

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
						<td  align="right">
						产品编号：
						</td>
						<td  align="left">
							<input type="text" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="15">
						&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
						</td>
					<td  align="right">产品名称 :</td>
		<td>
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
						</tr>
						<tr>
						<td  align="right">
						产品名称: 
						</td>
						<td colspan=3 align="left" id="select_id">
						<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
							<%=Argument.getProductListOptions(input_bookCode, product_id,"", input_operatorCode,18)%>
						</select>
						</td>
						</tr>
						<tr>
						<td  align="right">
						客户名称：</td>
						<td  align="left">
						<input type="text" maxlength="16" name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="10">
						</td>
						<td  align="right">
						 合同编号:
						 </td>
						<td  align="left"> <input type="text" name="contract_sub_bh" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(contract_sub_bh)%>" size="15">&nbsp;&nbsp;&nbsp;
						</td>
					
						</tr><tr>
						<td align="center" colspan=4>
						<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
					</tr></table>

</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<%//@ include file="menu.inc" %>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
			<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
						<td colspan="6" class="page-title"><b><%=menu_info%></b></td>
						</tr>
					<tr><td align=right>
					<div class="btn-wrapper">
					 <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
						&nbsp;&nbsp;&nbsp; <%if (input_operator.hasFunc(menu_id, 103))
{%>
					<button type="button"  class="xpbutton4" name="btnCheck" title="审核" onclick="javascript:op_check();">审核通过</button>
					 <%}%>
					</div>
					<br/>
				</td></tr>
			</table>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;合同编号</td>
					<td align="center" >产品名称</td>
					<td align="center" >委托客户名称</td>
					<td align="center" >财产名称</td>
					<td align="center"  sort="num">合同金额</td>
					<td align="center" >财产类别</td>
					<td align="center" >签署日期</td>
					<td align="center" >财产信息</td>
					<td align="center" >受益人</td>
					<td align="center" >审核</td>
				</tr>
<%
int iCurrent = 0;
Integer serial_no;
while (contract.getNext() && iCurrent < contract.getPageSize())
{
	serial_no = contract.getSerial_no();

%>

				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" >
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
							<td width="90%" align="left"><%=contract.getContract_sub_bh()%></td>
						</tr>
					</table>
					</td>
					<td align="left" ><%=Utility.trimNull(contract.getProduct_name())%></td>
					<td align="left" ><%=Utility.trimNull(contract.getCust_name())%></td>
					
					<td align="center" ><%=Utility.trimNull(contract.getEntity_name())%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatMoney0(contract.getRg_money()))%></td>
					<td align="center" ><%=Utility.trimNull(contract.getEntity_type_name())%></td>
					<td align="center" ><%=Format.formatDateCn(contract.getQs_date())%></td>
					<td align="center" >
					<button type="button"  class="xpbutton2"  name="btnBenifitor" title="查看财产信息信息" onclick="javascript:disableAllBtn(true);showCapitalDeatil('信托资产','<%=contract.getProduct_id()%>','<%=contract.getContract_bh()%>','120388')">&gt;&gt;</button>
					</td>
					<td align="center" >
						<button type="button"  class="xpbutton2"  name="btnSelectAll" title="受益人" onclick="javascript:disableAllBtn(true);showBenifiter(<%=contract.getSerial_no()%>)">&gt;&gt;</button>
					</td>
					<td align="center" >
						<button type="button"  class="xpbutton2"  onclick="javascript:disableAllBtn(true);showInfo(<%=serial_no%>)">&gt;&gt;</button>
					</td>
				</tr>
				<%iCurrent++;
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
				</tr>
				<%}
%>
				<tr class="trbottom">
					<td class="tdh" align="center" ><b>合计 <%=contract.getRowCount()%> 项</b></td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="center" >-</td>
					<td align="right" ><%=contract.getTotalUp("RG_MONEY")%></td>
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
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%contract.remove();
%>