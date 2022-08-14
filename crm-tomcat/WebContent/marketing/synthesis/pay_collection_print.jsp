<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
String product_code = Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));
String bank_name = Utility.trimNull(request.getParameter("bank_name"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
BigDecimal money = Utility.parseDecimal(Utility.trimNull(request.getParameter("money")),new BigDecimal(0));
String currency_id = Utility.trimNull(request.getParameter("currency_id"));
String bank_acct = Utility.trimNull(request.getParameter("bank_acct"));

String print_date = String.valueOf(new Integer(Utility.getCurrentDate()));

StringBuffer print_money= new StringBuffer("��"+Format.formatMoneyPrint(money.doubleValue(),2));
char[] s_money = print_money.reverse().toString().toCharArray(); 

String flag = Utility.trimNull(request.getParameter("flag"));
%>
<HTML> 
<HEAD>  
<TITLE>��ӡ�տ�֪ͨ��</TITLE> 
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<style media="print"> 
.noPrint{
	display:none;
}
</style> 
<style>
TABLE, TR, TD, HR, INPUT, BUTTON, SELECT, TEXTAREA {
	FONT-FAMILY: ����, ����_GB2312, ����, Tahoma, Verdana, Arial, @����Ҧ��;
	FONT-SIZE: 10pt;
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

</HEAD>
<BODY topMargin=37 leftMargin=10>
<form name="theform">  
<TABLE BORDER=0  cellSpacing=0 cellPadding=0 border=0  align="center">
	<TBODY>
		<TR> 
			<TD align="center" style="width:15mm;">&nbsp;<TD>
			<TD align="center"> 
				<TABLE BORDER=0  cellSpacing=0 cellPadding=0 border=0 align="center">
					<tr>	
						<td>
							<table id="table3" align="center" border="0" cellspacing="0" cellpadding="0" style="width:173mm">
								<tr>
									<td colspan="4" align="center" height="8">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4" align="center" height="30"><b><font size="+1" ><%=application.getAttribute("COMPANY_NAME")%></font></b></td>
								</tr>
								<tr>
									<td colspan="4" align="center" height="25"><b><font size="+2" >����ҵ���տ�֪ͨ��</font></b></td>
								</tr>
								<tr>
									<td align="right" height="25" width="10%">��Ŀ����:</td>
									<td height="25" width=28%"><font style="FONT-SIZE: 9pt;"><%=product_code%>-<%=product_name%></font></td>
									<td align="left"width="40%">
									    <%=print_date.substring(0,4)%>��
										<%=print_date.substring(4,6)%>��
										<%=print_date.substring(6,8)%>��
								    </td>
									<td align="right">����:<%=Utility.trimNull(Argument.getCurrencyName(currency_id))%>&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>		
						<TD>	
						<table  id="printtable" align="center" border="1" style="width:173mm;border-collapse:collapse;" bordercolor="black" cellspacing="0" cellpadding="0">
							<tr>
								<td style="height:9mm;"><span >��ͬ���</span></td>
								<td align="left"  colspan="2">
								<%if(flag.equals("1")){ %>
									<input  value=""  class="ednoline" size="13" maxlength="25">
								<%}else{ %>
									<%=contract_sub_bh%>
								<%} %>
								</td>
								<td align="center" style="width:15mm;"><span  >����ר��</span></td>
								<td align="left" colspan="15" style="padding-left:5px"><%=bank_name%><%="".equals(bank_acct)?"":"��"%><%=bank_acct%></td>
							</tr>
							<tr>
								<td  style="height:9mm;" colspan="2"><span > ���λ���� </span></td>
								<td colspan="17">&nbsp;&nbsp;<%=cust_name%>
								<%if(flag.equals("1")){ %>
									<input  value=""  class="ednoline" size="70" maxlength="80">
								<%} %>
								</td>
							</tr>
							<tr>
								<td style="height:13mm" rowspan="2" colspan="2"><span  ><span >�տ���(��д)</span></td>
								<td align="left" colspan="3" rowspan="2">&nbsp;&nbsp;<%=Utility.amountToChinese(money.doubleValue())%></td>
								<td align="center" style="height:5mm"><span >ǧ</span></td>
								<td align="center" style="height:5mm"><span >��</span></td>
								<td align="center" style="height:5mm"><span >ʮ</span></td>
								<td align="center"><span >��</span></td>
								<td align="center"><span >ǧ</span></td>
								<td align="center"><span >��</span></td>
								<td align="center"><span >ʮ</span></td>
								<td align="center"><span >��</span></td>
								<td align="center"><span >ǧ</span></td>
								<td align="center"><span >��</span></td>
								<td align="center"><span >ʮ</span></td>
								<td align="center"><span >Ԫ</span></td>
								<td align="center"><span >��</span></td>
								<td align="center"><span >��</span></td>
							</tr>
							<tr>
								<td align="center" style="height:8mm"><font size="2"><%if(s_money.length >= 15)out.print(s_money[14]);%></font></td>
								<td align="center" style="height:8mm"><font size="2"><%if(s_money.length >= 14)out.print(s_money[13]);%></font></td>
								<td align="center" style="height:8mm"><font size="2"><%if(s_money.length >= 13)out.print(s_money[12]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 12)out.print(s_money[11]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 11)out.print(s_money[10]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 10)out.print(s_money[9]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 9)out.print(s_money[8]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 8)out.print(s_money[7]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 7)out.print(s_money[6]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 6)out.print(s_money[5]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 5)out.print(s_money[4]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 4)out.print(s_money[3]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 2)out.print(s_money[1]);%></font></td>
								<td align="center"><font size="2"><%if(s_money.length >= 1)out.print(s_money[0]);%></font></td>
							</tr>
							<tr>
								<td style="height:27mm;"><span >��ע</span></td>
								<td align="left" valign="middle" colspan="18" style="padding:11px;line-height:20px;">
									 <textarea rows="4" name="remark" cols="60" style="overflow-y:hidden;padding:4px;line-height:20px;" onscroll="javascript:alert('ֻ������3��');" class="ednoline"></textarea>	
								</td>
							</tr>
							<tr>
								<td style="height:0mm;width:17mm;"></td>
								<td style="width:14mm;"></td>
								<td style="width:42mm;"></td>
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
            					    <td >�ֹ��쵼��</td>
            					    <td>���񲿣�</td>
            					    <td>��Ŀ�����ˣ�</td>
            					    <td>�����ˣ�</td>
            					</tr>
            					<tr>
            		                <td align="center" colspan="4">
            		                    <br/>
            		                    ��һ��(��): ���в���: �ڶ���(��): ҵ��
            		                </td>    
            		            </tr>
            				</table>
					</td>
				</tr>		
			</table>						
			</TD>
		</TR>
	</TBODY>
</TABLE>
<br><br>
<table id=printbtn  border="0" class="noPrint" align="left" width="80%">
	<tr>
		<td align=right>
		<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
	    <button type="button"  class="xpbutton2" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:printtable.style.borderStyle = 'none';window.print();">��ӡ(<u>P</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  class="xpbutton3" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
		&nbsp;&nbsp;
		<button type="button"  style="display:" class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:history.back();">����(<u>B</u>)</button>
		</td>
	</tr>
</table>
</form>
</BODY>
</HTML>
