<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
String product_code=request.getParameter("productid");
MoneyDetailLocal detail = EJBFactory.getMoneyDetail();
MoneyDetailVO vo = new MoneyDetailVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
String sQuery = Utility.trimNull(request.getParameter("sQuery"));
overall_product_id =Utility.parseInt(request.getParameter("product_id"),new Integer(-1));

String[] s = request.getParameterValues("selectbox");

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
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

<script language=javascript>
function confirmRemove()
{

	if(checkedCount(document.theform.selectbox) == 0)
	{
		sl_alert("请选定要打印的记录！");
		return false;
	}
	
    document.theform.submit();	
}

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
	document.theform.submit();
}

function refreshPage()
{
	//if (sl_checkChoice(document.theform.product_id, '产品名称'))
		StartQuery();
}

function StartQuery()
{
  	document.theform.btnQuery.disabled = true;
	
	location = 'pay_query.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value+'&sQuery=' + document.theform.product_id.value+' $ '+document.theform.t1.value;
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
		for(i=0;i<document.theform.product_code.options.length;i++)
		{
			if(document.theform.product_code.options[i].text==value)
			{
				prodid=document.theform.product_code.options[i].value;
				break;
			}
		}
		if(prodid!=0)
		{
			for(i=0;i<document.theform.product_id.options.length;i++)
			{
				if(document.theform.product_id.options[i].value==prodid)
				{
					document.theform.product_id.options[i].selected=true;
					break;
				}
			}
		}
		else
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
	if(obj==null)
		return false;
	
	if(checkedCount(obj) == 0)
	{
		sl_alert("请选择要打印的记录！");
		return false;
	}
	document.theform.action="purchanse_card_print.jsp";
	document.theform.submit();
}
</script>

<BODY class="BODY"">
<form name="theform" method="get" action="pay_remove.jsp">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td align="center"><font size="5"><b>客户缴款明细查询</b><font>
						<td  align="right"></td>
					</tr>
					<tr><td>产品名称:<%=Argument.getProductName(overall_product_id)%></td></tr>
					<tr>
						<td colspan="4">
						<hr noshade  size="1">
						</td>
					</tr>

				</table>
			<table  style='border-style:solid;border-width:1px;' width="100%" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0">
					<tr >
						<td align="center" height="25" class="tdall">合同编号</td>
						<td align="center" height="25" class="tdrightbottomtop">客户姓名</td>
						<td align="center" height="25" sort="num" class="tdrightbottomtop">缴款金额</td>
						<td align="center" height="25" class="tdrightbottomtop">缴款日期</td>
						<td align="center" height="25" class="tdrightbottomtop">缴款方式</td>
						<td align="center" height="25" class="tdrightbottomtop">到账日期</td>
						
					</tr>
<%
int iCurrent = 0;
Integer serial_no;
BigDecimal allamounttotal = new BigDecimal(0);
if (s!=null) {
	for(int i = 0;i < s.length; i++) {
		Integer srial_no=Utility.parseInt(s[i],new Integer(0));
		
		vo.setSerial_no(srial_no);
		Map map = (Map)detail.load(vo).get(0);
		serial_no = (Integer)map.get("SERIAL_NO");
	
		BigDecimal to_money = (BigDecimal)map.get("TO_MONEY");
	    if (to_money != null)
	        allamounttotal=allamounttotal.add(to_money);
%>
					<input type="hidden" name="removable_flag" value="<%=(1 == ((Integer)map.get("CHECK_FLAG")).intValue())%>">
					<tr class="tr<%=(iCurrent % 2)%>">
						<td  align="center" height="25" class="tdleftrightbottom">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"></td>
								<td width="90%" align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
							</tr>
						</table>
						</td>
						<td align="center" height="25" class="tdrightbottom"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<td align="right" height="25" class="tdrightbottom"><%=Utility.trimNull(Format.formatMoney(to_money))%></td>
						<td align="center" height="25" class="tdrightbottom"><%=Format.formatDateCn((Integer)map.get("DZ_DATE"))%></td>
						<td align="center" height="25" class="tdrightbottom"><%=Utility.trimNull(map.get("JK_TYPE_NAME"))%></td>
						<td align="center" height="25" class="tdrightbottom"><%=Format.formatDateCn((Integer)map.get("TO_BANK_DATE"))%></td>
						
					</tr>
					<%iCurrent++;
}


%>
					
					<%}
%>
					<tr class="trbottom">
						<td  align="center" height="25" class="tdleftrightbottom"><b>-</b></td>
						<td align="center" height="25" class="tdrightbottom">-</td>
						<td align="right" height="25" class="tdrightbottom"><%=Utility.trimNull(Format.formatMoney(allamounttotal))%></td>
						<td align="center" height="25" class="tdrightbottom"> -</td>
						<td align="center" height="25" class="tdrightbottom">-</td>
						<td align="center" height="25" class="tdrightbottom">-</td>
					</tr>
				</table>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
				    <tr>
				       <td colspan="4" align="left">制表：<%=Utility.trimNull(Argument.getIntrustOpName(input_operatorCode))%></td>
				       <td align="left">复核员：</td>
				       <td align="left">经理复核员：</td>
				       <td align="left">打印时间：<%=Format.formatDateCn(Utility.getCurrentDate())%></td>
				    </tr>
				</table>

				<br>

				<table border="0" width="100%" id=table99>
					<tr valign="top">
						
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="打印" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">打印(<u>P</u>)</button>	 
						&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" title="返回" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
</BODY>
</HTML>
<%detail.remove();
%>
