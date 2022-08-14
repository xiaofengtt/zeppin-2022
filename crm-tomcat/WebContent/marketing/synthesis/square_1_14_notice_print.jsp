<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
try{
String strValues[]=request.getParameterValues("selectbox");
%>
<html> 
<head>  
<title>打印付款通知单</title> 
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style media="print">
.noPrint {
	display: none;
}
</style> 
<style>
 TR, TD, HR, INPUT, BUTTON, SELECT, TEXTAREA {
	font-family: 宋体, 仿宋_gb2312, 仿宋, tahoma, verdana, arial, @方正姚体;
	font-size: 10pt;
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
</head>
<body topmargin="13" leftmargin="45">
<form name="theform">  
<span class="noPrint"><font color="red">*红色字体部分可以手工录入</font></span>
<%
DeployLocal deploy = EJBFactory.getDeploy();
ProductLocal product=EJBFactory.getProduct();

for(int j=0;j<strValues.length;j++)
{
	Integer serial_no = Utility.parseInt(strValues[j], null);

	String strRate = request.getParameter("rate"+serial_no);
	BigDecimal rate = Utility.parseDecimal(strRate,null);
	if(rate != null){
		rate = rate.multiply(new BigDecimal("100")).setScale(1);
	}
	
	String strArray[] = new String[20];
	if (serial_no!=null && serial_no.intValue()>0) {
		Map dMap = (Map)deploy.load(serial_no).get(0);

		Integer date = (Integer)dMap.get("SY_DATE");
		String print_date = String.valueOf(date); 
		BigDecimal money = ((BigDecimal)dMap.get("SY_MONEY")).abs();
		StringBuffer print_money= new StringBuffer("￥"+Format.formatMoneyPrint(money.doubleValue(),2));

		char[] s_money = print_money.reverse().toString().toCharArray();
		ProductVO pvo = new ProductVO();
		pvo.setProduct_id((Integer)dMap.get("PRODUCT_ID"));
		Map pMap = (Map)product.load(pvo).get(0);

		BenifitorLocal benifitor = EJBFactory.getBenifitor();
		BenifitorVO bvo = new BenifitorVO();
		bvo.setBook_code(input_bookCode);
		bvo.setProduct_id((Integer)dMap.get("PRODUCT_ID"));
		bvo.setContract_bh((String)dMap.get("CONTRACT_BH"));
		bvo.setCust_id((Integer)dMap.get("CUST_ID"));	
		bvo.setList_id((Integer)dMap.get("LIST_ID"));
		Map bMap = (Map)benifitor.queryDetail(bvo).get(0);
	%>
<%if(j != 0){%>		
	<div style="page-break-after:always">&nbsp;</div>
<%}%>	
<TABLE BORDER=0  cellSpacing=0 cellPadding=0 border=0 align="center">
	<TBODY>
		<TR> 
			<TD align="center"> 
				
				<table id="table3" align="center" border="0" cellspacing="0" cellpadding="0" style="width:173mm;">
					<tr>
						<td colspan="4" align="center" height="30"><b><font size="+1" ><%=application.getAttribute("COMPANY_NAME")%></font></b></td>
					</tr>
					<tr>
						<td colspan="4" align="center" height="25"><b><font size="+2" >信托业务付款通知单</font></b></td>
					</tr>
					<tr>
						<td align="right" height="20" width="10%">项目名称:</td>
						<td><font style="FONT-SIZE: 9pt;"><%=pMap.get("PRODUCT_CODE")%><%=pMap.get("PRODUCT_NAME")%></font></td>
						<td align="left"width="40%">
						    <%=print_date.substring(0,4)%>年
							<%=print_date.substring(4,6)%>月
							<%=print_date.substring(6,8)%>日
						</td>
						<td align="right">币种:<%=Utility.trimNull(Argument.getCurrencyName((String)pMap.get("CURRENCY_ID")))%>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				<table  id="printtable" align="center"  border="1" style="width:173mm;border-collapse:collapse;"  bordercolor="black"  cellspacing="0" cellpadding="0">
					<tr>
						<td style="height:9mm" colspan="1"><span  >合同编号</span></td>
						<td align="left" colspan="3">&nbsp;&nbsp;<%=dMap.get("CONTRACT_SUB_BH")%></td>
						<td align="center"><span  >信托专户</span></td>
						<td align="left"colspan="13"  style="padding-left:11px"><%=pMap.get("TG_BANK_NAME")%><%=Utility.trimNull(pMap.get("TG_BANK_SUB_NAME"))%>:<%=Utility.trimNull(pMap.get("TG_BANK_ACCT"))%></td>
					</tr>
					<tr>
						<td style="height:9mm" colspan="2" ><span >付款单位名称</span></td>
						<td colspan="16">&nbsp;&nbsp;<%=dMap.get("CUST_ACCT_NAME")%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:9mm;" ><span  >开户银行</span></td>
						<td colspan="2" style="padding-left:11px;"><%=bMap.get("BANK_NAME")%></td>
						<td align="center" nowrap><span >银行账号</span></td>
						<td align="left" colspan="13">&nbsp;&nbsp;<%=bMap.get("BANK_ACCT")%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:14mm" nowrap rowspan="2"><span >付款金额(大写)</span></td>
						<td align="left" colspan="4" rowspan="2">&nbsp;&nbsp;<%=Utility.amountToChinese(money.doubleValue())%></td>
						<td align="center"style="height:8mm"><span >十</span></td>
						<td align="center"><span >亿</span></td>
						<td align="center"><span >千</span></td>
						<td align="center"><span >百</span></td>
						<td align="center"><span >十</span></td>
						<td align="center"><span >万</span></td>
						<td align="center"><span >千</span></td>
						<td align="center"><span >百</span></td>
						<td align="center"><span >十</span></td>
						<td align="center"><span >元</span></td>
						<td align="center"><span >角</span></td>
						<td align="center"><span >分</span></td>
					</tr>
					<tr>
						<td align="center" style="height:6mm"><%if(s_money.length >= 13)out.print(s_money[12]);%></td>
						<td align="center"><%if(s_money.length >= 12)out.print(s_money[11]);%></td>
						<td align="center"><%if(s_money.length >= 11)out.print(s_money[10]);%></td>
						<td align="center"><%if(s_money.length >= 10)out.print(s_money[9]);%></td>
						<td align="center"><%if(s_money.length >= 9)out.print(s_money[8]);%></td>
						<td align="center"><%if(s_money.length >= 8)out.print(s_money[7]);%></td>
						<td align="center"><%if(s_money.length >= 7)out.print(s_money[6]);%></td>
						<td align="center"><%if(s_money.length >= 6)out.print(s_money[5]);%></td>
						<td align="center"><%if(s_money.length >= 5)out.print(s_money[4]);%></td>
						<td align="center"><%if(s_money.length >= 4)out.print(s_money[3]);%></td>
						<td align="center"><%if(s_money.length >= 3)out.print(s_money[1]);%></td>
						<td align="center" ><%if(s_money.length >= 2)out.print(s_money[0]);%></td>
					</tr>
					<tr>
						<td colspan="2" style="height:9mm" align="right"><span  ><font color="red">收益率:</font></span>
							<input name ="rate"  value="<%=Utility.trimNull(rate) %>"  class="ednoline" size="5" maxlength="5" style="text-align:right;">%&nbsp;
						</td>
						<td align="center"><span  ><font color="red">期限</font></span></td>
						<td align="center"><input name ="period" value=""  class="ednoline" size="10" maxlength="10"></td>
						<td align="center"><span  ><font color="red">用途</font></span></td>
						<td align="center" colspan="13"><input name ="purpose"  value=""  class="ednoline" size="44" maxlength="44"></td>
					</tr>			
					<tr>
						<td style="height:27mm" align="center"><span  ><font color="red"><b>备注</b></font></span></td>
						<td align="left" valign="middle" colspan="17" style="padding:11px;line-height:20px;">
							 <textarea rows="5" name="remark" cols="60" style="overflow-y:hidden;padding:4px;line-height:20px;" onscroll="javascript:alert('只能输入3行');" class="ednoline"></textarea>	
						</td>
					</tr>
					<tr>
						<td style="height:0mm;width:17mm;"></td>
						<td style="width:14mm;"></td>
						<td style="width:14mm;"></td>
						<td style="width:28mm;"></td>
						<td style="width:16mm;"></td>
						<td style="width:37mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
						<td style="width:4mm;"></td>
					</tr>
				</table>
<table width="100%">
            				    <tr>
            					    <td >分管领导：</td>
            					    <td>财务部：</td>
            					    <td>项目负责人：</td>
            					    <td>经办人：</td>
            					</tr>
            					<tr>
            		                <td align="center" colspan="4">
            		                    <br/>
            		                    第一联(白): 信托财务部: 第二联(粉): 业务部
            		                </td>    
            		            </tr>
            				</table>
				
				</TD>
		</TR>
	</TBODY>
</TABLE>
<br>
<%
		benifitor.remove();	
	}
}%>
<br />
<table id="printbtn" style="display: " class="noPrint"  border="0" width="90%" >
	<tr>
		<td align="right">
		<object  id="WebBrowser"  classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"  height="0"  width="0" viewastext="VIEWASTEXT"></object>
	    <button type="button"  class="xpbutton2" accesskey="p" id="btnPrint" name="btnPrint" onclick="javascript:window.print();">打印(<u>P</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accesskey="a" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accesskey="c" id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  style="display: " class="xpbutton3" accesskey="b" id="btnBack" name="btnBack" onclick="javascript:history.back();">返回(<u>B</u>)</button>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%
deploy.remove();
product.remove();
}catch(Exception e){
	throw e;
}
%>