<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo_ben = new BenifitorVO();

List rsList_ben = null;
Map map_ben = null;

boolean bSuccess = false;
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer modi_time2 = Utility.parseInt(request.getParameter("modi_time"),new Integer(Utility.getCurrentDate()));
Integer open_flag = new Integer(2);
Integer bonus_flag = new Integer(0);//ԭ������䷽ʽ
Integer temp_bonus_flag  = new Integer(0);//��������䷽ʽ
String contract_sub_bh = "";
String cust_no = "";
String cust_name ="";
String card_id = "";
String bank_id = "";
String bank_sub_name = "";
String bank_acct ="";
String cust_acct_name ="";
String acct_chg_reason = "";
String temp_bank_acct ="";
String temp_bank_id ="";
String temp_bank_sub_name ="";
String temp_acct_name ="";
String bank_city = "",bank_province = "";
Integer recommend_man = new Integer(0);
String jk_type = "";
Integer df_product_id = new Integer(0);
String df_contract_bh = "";
Integer cust_id = new Integer(0);
List attachmentList = new ArrayList();
String change_times="";
Integer product_id=new Integer(0);


if(serial_no.intValue()>0){

	vo_ben.setSerial_no(serial_no);
	vo_ben.setModi_bank_date(modi_time2);
	vo_ben.setBook_code(input_bookCode);

rsList_ben = benifitor.loadFromCRM(vo_ben);
	if(rsList_ben.size()>0){
		map_ben = (Map)rsList_ben.get(0);
	}
	
if(map_ben!=null){
			bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("BONUS_FLAG")),new Integer(0));
			product_id = Utility.parseInt(Utility.trimNull(map_ben.get("PRODUCT_ID")),new Integer(0));
			contract_sub_bh = Utility.trimNull(map_ben.get("CONTRACT_SUB_BH"));
			cust_no =  Utility.trimNull(map_ben.get("CUST_NO"));
			cust_name = Utility.trimNull(map_ben.get("CUST_NAME"));
			card_id = Utility.trimNull(map_ben.get("CARD_ID"));
			bank_id = Utility.trimNull(map_ben.get("BANK_ID"));
			bank_sub_name = Utility.trimNull(map_ben.get("BANK_SUB_NAME"));
			bank_acct = Utility.trimNull(map_ben.get("BANK_ACCT"));
			cust_acct_name = Utility.trimNull(map_ben.get("CUST_ACCT_NAME"));
			acct_chg_reason = Utility.trimNull(map_ben.get("ACCT_CHG_REASON"));
			temp_bank_acct = Utility.trimNull(map_ben.get("TEMP_BANK_ACCT"),bank_acct);
			temp_bank_id = Utility.trimNull(map_ben.get("TEMP_BANK_ID"),bank_id);
			temp_bank_sub_name = Utility.trimNull(map_ben.get("TEMP_BANK_SUB_NAME"),bank_sub_name);
			temp_acct_name = Utility.trimNull(map_ben.get("TEMP_ACCT_NAME"),cust_acct_name);
			temp_bonus_flag = Utility.parseInt(Utility.trimNull(map_ben.get("TEMP_BONUS_FLAG")),new Integer(0));
			recommend_man = Utility.parseInt(Utility.trimNull(map_ben.get("RECOMMEND_MAN")),new Integer(0));
					
			bank_province = Utility.trimNull(map_ben.get("BANK_PROVINCE"));
			bank_city = Utility.trimNull(map_ben.get("BANK_CITY"));
			jk_type = Utility.trimNull(map_ben.get("JK_TYPE"));
			df_product_id = Utility.parseInt(Utility.trimNull(map_ben.get("DF_PRODUCT_ID")),new Integer(0));
			df_contract_bh = Utility.trimNull(map_ben.get("DF_CONTRACT_BH"));
			cust_id = Utility.parseInt(Utility.trimNull(map_ben.get("CUST_ID")),new Integer(0));
			change_times=Utility.trimNull(map_ben.get("CHANGE_TIMES"));
		}
}

%>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY>
<form name="theform" method="post" action="contract_exchange_list.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=serial_no%>"> 

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="10" >
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td align="center"><font size="6" face="����"><b><%=Argument.getCompanyName(user_id)%></b></font></td>
					</tr>
					<tr>	
						<td align="center"><font size="6" face="����"><b>�������������ϱ���Ǽǻ�ִ</b></font></td>
					</tr>	
				</table>

				<table bordercolor="#000000" border ="1" cellSpacing=0 cellPadding=5 width="100%">
					<tr>
						<td align="center" class="tdrightbottom"  width="25%"><font size="2"><b>����������</b></font></td>
						<td class="tdrightbottom" colspan="2" width="25%"><%=cust_name %></td>						
						<td align="center" class="tdrightbottom" width="25%"><font size="2"><b>���к�ͬ���</b></font></td>
						<td class="tdbottom" width="25%">&nbsp;<%=contract_sub_bh %></td>
					</tr>					
					<tr>
						<td align="center"  class="tdrightbottom"><font size="2"><b>���мƻ�����</b></font></td>
			 			<td class="tdbottom" colspan="4">&nbsp;<%=Utility.trimNull(Argument.getProductName(product_id)) %></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><font size="2"><b>���֤������</b></font></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull(Argument.getCardTypeName(card_id)) %></td>						
						<td class="tdrightbottom" align="center"><font size="2"><b>���֤����</b></font></td>
						<td class="tdbottom">&nbsp;<%=card_id %></td>
					</tr>					
					<tr>
						<td class="tdrightbottom" align="center"><font size="2"><b>����������</b></font></td>
						<td class="tdbottom"colspan="4">&nbsp;<b>Ԥ�������˺ű��</b> &nbsp;</td>		
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" height="140px"><font size="2"><b>������ԭ��</b></font></td>
						<td class="tdbottom"colspan="4">&nbsp;<%=acct_chg_reason %></td>		
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="5"><font size="2"><b>Ԥ�������˺ű��</b></font></td>
						<td class="tdrightbottom" align="center" width="15%"><font size="2"><b>���֤����</b></font></td>
						<td class="tdbottom" colspan="3">&nbsp;&nbsp;<%=card_id %></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="2"><font size="2"><b>ԭ�ʺ�</b></font></td>
						<td class="tdrightbottom" align="center" colspan="2"><font size="2"><b>������</b></font></td>
						<td class="tdbottom" align="center" class="tdrightbottom"><font size="2"><b>�����˺�</b></font></td>
					</tr>
					<tr>	
						<td class="tdrightbottom" align="center" colspan="2">&nbsp;<%=Argument.getBankName(bank_id) %><%=bank_sub_name %>&nbsp;</td>
						<td class="tdbottom" align="center" class="tdrightbottom">&nbsp;<%=bank_acct %>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="2"><font size="2"><b>������ʺ�</b></font></td>
						<td class="tdrightbottom" align="center" colspan="2"><font size="2"><b>������</b></font></td>
						<td class="tdbottom" align="center" class="tdrightbottom"><font size="2"><b>�����˺�</b></font></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" colspan="2">&nbsp;<%=Argument.getBankName(temp_bank_id) %><%=temp_bank_sub_name %>&nbsp;</td>
						<td class="tdbottom" align="center" class="tdrightbottom">&nbsp;<%=temp_bank_acct %>&nbsp;</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="4"><font size="2"><b>��������ݱ��</b></font></td>
						<td class="tdrightbottom" align="center" rowspan="2"><font size="2"><b>ԭ���֤��</b></font></td>
						<td class="tdrightbottom" align="center"><font size="2"><b>����</b></font></td>
						<td class="tdrightbottom" align="center"><font size="2"><b>֤������</b></font></td>
						<td class="tdbottom" align="center"><font size="2"><b>֤����</b></font></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center">&nbsp;</td>
						<td class="tdrightbottom" align="center">&nbsp;</td>
						<td class="tdbottom" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="2"><font size="2"><b>��������֤��</b></font></td>
						<td class="tdrightbottom" align="center"><font size="2"><b>����</b></font></td>
						<td class="tdrightbottom" align="center"><font size="2"><b>֤������</b></font></td>
						<td class="tdbottom" align="center"><font size="2"><b>֤����</b></font></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center">&nbsp;</td>
						<td class="tdrightbottom" align="center">&nbsp;</td>
						<td class="tdbottom" align="center">&nbsp;</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" height="160px">
							<font size="2"><b>����Ǽ���<br>��ǩ������ӡ)</b></font></td>
						<td class="tdrightbottom" colspan="2" >&nbsp;</td>
						<td class="tdrightbottom" align="center">
							<font size="2"><b>�Ǽ���<br>��ӡ��)</b></font></td>
						<td class="tdbottom">&nbsp;</td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="5">
						<center><font size="2"><b>�Ǽ�Լ��</b></font></center><font size="2" face="����_GB2312">
						&nbsp;&nbsp;&nbsp;1����<%=Argument.getCompanyName(user_id)%>�Ӹ�����ҵ��ר����ȷ�Ϻ󣬼���ʾ<%=Argument.getCompanyName(user_id)%>�ѶԸñ�����к�ͬ��Ӧ���������������Ͻ����˱���Ǽǡ�<br>
						&nbsp;&nbsp;&nbsp;2��������Ǽ����ڱ���ǩ�֡�����Ϊͬ�Ȿ�Ǽ�Լ�����ݣ����ܱ��Ǽ�Լ������Լ��<br>
						</font>
						</td>	
					</tr>
					<tr>
						<td class="tdright" align="center" colspan="3"><font size="2"><b>�Ǽ�����</b></font></td>
						<td class="tdnoall" align="center" colspan="2"><font size="2"><b>
							</b></font></td>
					</tr>
					</table>	
					
					<table border="0" width="100%" cellspacing="1" cellpadding="4" id=table99>
					<tr>
						<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">��ӡ(<u>P</u>)</button>	 
								&nbsp;&nbsp;&nbsp;
								<button class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();">����(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</TD>
</TABLE>
</form>
</BODY>
</HTML>
<%benifitor.remove();
%>