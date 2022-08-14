<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
DeployLocal deploy = EJBFactory.getDeploy();
ProductLocal product=EJBFactory.getProduct();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
String print_param = null;
Integer edit_flag = Utility.parseInt(request.getParameter("edit_flag"), new Integer(0));


 %>
<html> 
<head>  
<title>打印付款款通知单</title> 
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style media="print">
.noPrint {
	display: none;
}
table{
	border:none;
}
td {
	border-style: none;
}

</style> 
<style>
 TR, TD, HR, INPUT, BUTTON, SELECT, TEXTAREA {
	font-family: 宋体, 仿宋_gb2312, 仿宋, tahoma, verdana, arial, @方正姚体;
	font-size: 9pt;
}
.ednoline
{
    BACKGROUND-COLOR: transparent;
    BORDER-BOTTOM: medium none;
    BORDER-LEFT: medium none;
    BORDER-RIGHT: medium none;
    BORDER-TOP: medium none;
}
</style>
<script language="vbscript" src="/includes/default.vbs"></script>
<script language="javascript" src="/includes/default.js"></script>
<script language="javascript">
function editInfo(serial_no){
	var print_param = document.theform.print_param.value;
	var print_param = showModalDialog('square_1_14_tt_info.jsp?print_param='+print_param, '', 'dialogWidth:700px;dialogHeight:500px;status:0;help:0')
	if( print_param != null)
	{
		location = 'square_1_14_tt_print.jsp?edit_flag=1&serial_no=' + serial_no  + '&print_param=' + print_param;
	}else{
		disableAllBtn(false);	
	}
}
</script>
</head>
<body topmargin="3" leftmargin="0">
<form name="theform">  
<TABLE BORDER=0  cellSpacing=0 cellPadding=0  align="left" >

<%

if (serial_no!=null) {
	Map dMap = (Map)deploy.load(serial_no).get(0);

	ProductVO pvo = new ProductVO();
	pvo.setProduct_id((Integer)dMap.get("PRODUCT_ID"));
	Map pMap = (Map)product.load(pvo).get(0);

	BenifitorLocal benifitor = EJBFactory.getBenifitor();
	BenifitorVO bvo = new BenifitorVO();
	bvo.setBook_code(input_bookCode);
	bvo.setProduct_id((Integer)dMap.get("PRODUCT_ID"));
	bvo.setContract_bh((String)dMap.get("CONTRACT_BH"));
	bvo.setList_id((Integer)dMap.get("LIST_ID"));
	Map bMap = (Map)benifitor.queryDetail(bvo).get(0);

	//表格中显示的字段
	if(edit_flag.intValue() == 1)
		print_param = Utility.trimNull(request.getParameter("print_param"));
	else 
		print_param =  Utility.trimNull(dMap.get("SY_DATE"))
					+"$" + Utility.trimNull(pMap.get("TG_ACCT_NAME")) 
					+ "$" + Utility.trimNull(bMap.get("CUST_NAME"))
					+"$" + Utility.trimNull(pMap.get("TG_BANK_ACCT"))
				   + "$"+ Utility.trimNull(bMap.get("BANK_ACCT")) 
					+ "$" + Utility.trimNull(pMap.get("TG_BANK_NAME"))+Utility.trimNull(pMap.get("TG_BANK_SUB_NAME")) 
					+"$" + Utility.trimNull(bMap.get("BANK_NAME")) 
					+"$" + ((BigDecimal)dMap.get("SY_MONEY")).abs() + "$";
	String[] param = print_param.split("\\$");
	
	String print_date = String.valueOf((Integer)dMap.get("SY_DATE"));
	
	BigDecimal money = Utility.parseDecimal(param[7],new BigDecimal(0));
	StringBuffer print_money= new StringBuffer("￥"+Format.formatMoneyPrint(money.doubleValue(),2));
	char[] s_money = print_money.reverse().toString().toCharArray();

%>
<TR>
	<TD style="width=12mm;">&nbsp;<input type="hidden" name ="print_param" value="<%=print_param%>"></TD>
	<TD>
		<TABLE BORDER=0  cellSpacing=0 cellPadding=0 border=0 align="center" >
		<TR>
			<TD align="left">
				<table id="table3" align="left" border="0" cellspacing="0" cellpadding="0" style="width:163mm;">
					<tr>
						<td colspan="3" align="center" height="20"><b><font size="+1" class="noPrint">电汇凭证</font></b></td>
					</tr>
					<tr>
						<td height="20" width="35%">&nbsp;</td>
						<td align="left"width="35%">&nbsp;<input type="text" value="<%=param[0].substring(0,4)%>" class="ednoline" size="4" maxlength="4">&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="text" value="<%if("0".equals(param[0].substring(4,5)))out.print(param[0].substring(5,6));else out.print(param[0].substring(4,6));%>"class="ednoline" size="2" maxlength="2">&nbsp;&nbsp;&nbsp;
							<input type="text" value="<%=param[0].substring(6,8)%>"class="ednoline" size="2" maxlength="2"> </td>
						<td align="right"width="30%">&nbsp;</td>
					</tr>
				</table>
			</TD>
		</TR>
		<TR>
			<TD align="left">
				<table  id="printtable" align="left"  border="1" style="width:162mm;border-collapse:collapse;"  bordercolor="black"  cellspacing="0" cellpadding="0">
					<tr>
						<td rowspan="3" style="Writing-mode:tb-rl;Text-align:center"><span class="noPrint">汇款人</span></td>
						<td style="height:6mm" align="center"><span class="noPrint" >全称</span></td>
						<td>&nbsp;<font size="1"><%=param[1]%></font></td>
						<td rowspan="3" style="Writing-mode:tb-rl;Text-align:center"><span class="noPrint">收款人</span></td>
						<td align="center"><span  class="noPrint">全称</span></td>
						<td colspan="12">&nbsp;<%=param[2]%></td>
					</tr>
					<tr>
						<td style="height:6mm" align="center" ><span class="noPrint">账号</span></td>
						<td >&nbsp;<%=param[3]%></td>
						<td align="center"><span  class="noPrint">账号</span></td>
						<td colspan="12">&nbsp;<%=param[4]%></td>
					</tr>
					<tr>
						<td style="height:6mm" align="center"><span class="noPrint">汇出地</span></td>
						<td>&nbsp;&nbsp;<input type="text" value="北京" class="ednoline" size="4">&nbsp;&nbsp;
							<span class="noPrint">省</span>&nbsp;&nbsp;<input type="text" value=" " class="ednoline" size="2">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="noPrint">市</span></td>
						<td align="center"><span  class="noPrint">汇入地</span></td>
						<td colspan="12">&nbsp;&nbsp;<input type="text" value="北京" class="ednoline" size="4">&nbsp;&nbsp;
							<span class="noPrint">省</span>&nbsp;&nbsp;<input type="text" value=" " class="ednoline" size="2">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="noPrint">市</span></td>
					</tr>	
					<tr>
						<td style="height:6mm" align="center" colspan="2"><span class="noPrint">汇出行名称</span></td>
						<td>&nbsp;<%=param[5]%></td>
						<td align="center" colspan="2"><span  class="noPrint">汇入行名称</span></td>
						<td colspan="12">&nbsp;<%=param[6]%></td>
					</tr>
					
					<tr>
						<td style="height:10mm" nowrap rowspan="2" style="Writing-mode:tb-rl;Text-align:center"><span class="noPrint">金额</span></td>
						<td align="left" colspan="5" rowspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=Utility.amountToChinese(money.doubleValue())%></td>
						<td align="center"style="height:5mm"><span class="noPrint">亿</span></td>
						<td align="center"><span class="noPrint">千</span></td>
						<td align="center"><span class="noPrint">百</span></td>
						<td align="center"><span class="noPrint">十</span></td>
						<td align="center"><span class="noPrint">万</span></td>
						<td align="center"><span class="noPrint">千</span></td>
						<td align="center"><span class="noPrint">百</span></td>
						<td align="center"><span class="noPrint">十</span></td>
						<td align="center"><span class="noPrint">元</span></td>
						<td align="center"><span class="noPrint">角</span></td>
						<td align="center"><span class="noPrint">分</span></td>
					</tr>
					<tr>
						<td align="center" style="height:5mm" ><%if(s_money.length >= 12)out.print(s_money[11]);%></td>
						<td align="center"><%if(s_money.length >= 11)out.print(s_money[10]);%></td>
						<td align="center"><%if(s_money.length >= 10)out.print(s_money[9]);%></td>
						<td align="center"><%if(s_money.length >= 9)out.print(s_money[8]);%></td>
						<td align="center"><%if(s_money.length >= 8)out.print(s_money[7]);%></td>
						<td align="center"><%if(s_money.length >= 7)out.print(s_money[6]);%></td>
						<td align="center"><%if(s_money.length >= 6)out.print(s_money[5]);%></td>
						<td align="center"><%if(s_money.length >= 5)out.print(s_money[4]);%></td>
						<td align="center"><%if(s_money.length >= 4)out.print(s_money[3]);%></td>
						<td align="center"><%if(s_money.length >= 3)out.print(s_money[1]);%></td>
						<td align="center"><%if(s_money.length >= 2)out.print(s_money[0]);%></td>
					</tr>
					
					<tr>
						<td colspan="3" style="height:30mm" rowspan="2"></td>
						<td colspan="2" style="height:6mm" align="center"><span class="noPrint">支付密码</span></td>
						<td colspan="12">&nbsp;</td>	
					</tr>
					<tr>
						<td colspan="14" style="height:24mm" valign="top">
							<span class="noPrint"><font color="red">附加信息及用途:</font></span><BR>
							<textarea rows="5" name="remark" cols="40"  style="overflow-y:hidden;padding:4px;line-height:20px;" onscroll="javascript:alert('只能输入3行');" class="ednoline"></textarea>
						</td>	
					</tr>
					<tr>
						<td style="height:0mm;width:6mm;"></td>
						<td style="width:13mm;"></td>
						<td style="width:59mm;"></td>
						<td style="width:6mm;"></td>
						<td style="width:12mm;"></td>
						<td style="width:15mm;"></td>
						<td style="width:5mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:5mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:5mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:5mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:5mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
					</tr>

<%	benifitor.remove();	
 }%>					
					
				</table>
				</TD>
			</TR>
		</TABLE>
		<BR>
	</TD>
	</TR>

	<TR>
	<TD colspan="3">
		<table id="printtable" style="display: " border="0" width="90%" class="noprint">
			<tr>
				<td align="right">
				<object  id="WebBrowser"  classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"  height="0"  width="0" viewastext="VIEWASTEXT"></object>
			    <button type="button"  class="xpbutton2" accesskey="p" id="btnPrint" name="btnPrint" onclick="javascript:window.print();">打印(<u>P</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton2" accesskey="p" id="btnPrint" name="btnPrint" onclick="javascript:editInfo(<%=serial_no%>);">编辑信息</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accesskey="a" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accesskey="c" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  style="display: " class="xpbutton3" accesskey="b" id="btnBack" name="btnBack" onclick="javascript:history.back();">返回(<u>B</u>)</button>
				</td>
			</tr>
		</table>
	</TD>
	</TR>
</TABLE>
</form>
</body>
</html>
<%
deploy.remove();
product.remove();
%>