<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*, enfo.crm.marketing.*, enfo.crm.customer.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO benifitor_vo = new BenifitorVO();

List benifitor_list = null;
Map benifitor_map = null;

String post_code = "";
String post_address = "";
String cust_name = "";
String contract_sub_bh = "";
String product_name = "";
String card_type_name = "";
String card_id = "";
String H_tel = "";
String mobile = "";
String e_mail = "";
String bank_acct = "";
String bank_name = "";
String prov_flag_name = "";
String bank_sub_name = "";
Integer start_date = new Integer(0);
Integer fact_num = new Integer(0);
Integer qs_date = new Integer(0);
Integer ben_date = new Integer(0);
Integer dz_date = new Integer(0);
Integer valid_period = new Integer(0);
Integer period_unit = new Integer(0);
BigDecimal fact_money = new BigDecimal(0);
BigDecimal to_amount = new BigDecimal(0);
BigDecimal rg_money = new BigDecimal(0);
BigDecimal to_money = new BigDecimal(0);


benifitor_vo.setSerial_no(serial_no);
benifitor_vo.setInput_man(input_operatorCode);
benifitor_list = benifitor.queryBenifitorbyhtPrint(benifitor_vo);
System.out.println();
if(benifitor_list.size()>0){
	benifitor_map = (Map)benifitor_list.get(0);
	post_code = Utility.trimNull(benifitor_map.get("POST_CODE"));
	post_address = Utility.trimNull(benifitor_map.get("POST_ADDRESS"));
	cust_name = Utility.trimNull(benifitor_map.get("CUST_NAME"));
	contract_sub_bh = Utility.trimNull(benifitor_map.get("CONTRACT_SUB_BH"));
	product_name = Utility.trimNull(benifitor_map.get("PRODUCT_NAME"));
	start_date = Utility.parseInt(Utility.trimNull(benifitor_map.get("START_DATE")),new Integer(0));
	fact_money = Utility.parseDecimal(Utility.trimNull(benifitor_map.get("FACT_MONEY")),new BigDecimal(0),2,"1");
	fact_num = Utility.parseInt(Utility.trimNull(benifitor_map.get("FACT_NUM")),new Integer(0));
	card_type_name = Utility.trimNull(benifitor_map.get("CARD_TYPE_NAME"));
	card_id = Utility.trimNull(benifitor_map.get("CARD_ID"));
	H_tel = Utility.trimNull(benifitor_map.get("H_TEL"));
	mobile = Utility.trimNull(benifitor_map.get("MOBILE"));
	e_mail = Utility.trimNull(benifitor_map.get("E_MAIL"));
	to_amount = Utility.parseDecimal(Utility.trimNull(benifitor_map.get("TO_AMOUNT")),new BigDecimal(0),2,"1");
	rg_money = Utility.parseDecimal(Utility.trimNull(benifitor_map.get("RG_MONEY")),new BigDecimal(0),2,"1");
	bank_acct = Utility.trimNull(benifitor_map.get("BANK_ACCT"));
	ben_date = Utility.parseInt(Utility.trimNull(benifitor_map.get("BEN_DATE")),new Integer(0));
	dz_date = Utility.parseInt(Utility.trimNull(benifitor_map.get("DZ_DATE")),new Integer(0));
	bank_name = Utility.trimNull(benifitor_map.get("BANK_NAME"));
	bank_sub_name = Utility.trimNull(benifitor_map.get("BANK_SUB_NAME"));
	to_money = Utility.parseDecimal(Utility.trimNull(benifitor_map.get("TO_MONEY")),new BigDecimal(0),2,"1");
	valid_period = Utility.parseInt(Utility.trimNull(benifitor_map.get("VALID_PERIOD")),new Integer(0));
	period_unit = Utility.parseInt(Utility.trimNull(benifitor_map.get("PERIOD_UNIT")),new Integer(0));
	Integer prov_flag = Utility.parseInt(Utility.trimNull(benifitor_map.get("PROV_FLAG")),new Integer(0));
	if(prov_flag.intValue() == 1){
		prov_flag_name ="���ȼ�";
	}else if(prov_flag.intValue() == 2){
		prov_flag_name ="һ�㼶";
	}else if(prov_flag.intValue() == 3){
		prov_flag_name ="�Ӻ�";
	}
}
%>
<html>
	<head>
		<title></title>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">

		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/print1.css" type="text/css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet">
		<style type="text/css"> 
			@media print {.noprint{display: none;}}
		</style>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
	</head>

	<body>
		<form name="theform" method="post">
			<input type="hidden" name="product_id" value="">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0">
				<tr>
					<td vAlign="top" align="left">	
						<table id="blankid" cellSpacing="0" cellPadding="0" width="100%" border="0">
							<tr>
								<td align="center" height=0></td>
							</tr>
						</table>
						<table cellSpacing="1" cellPadding="1" width="100%" border="0">
							<tr>
								<td align="left" height="25">&nbsp;&nbsp;&nbsp;</td>
								<td colspan="2">
									<%if(user_id.intValue()==5){ %>
									<img src="<%=request.getContextPath()%>/images/zhongtai/ztxtlogo.jpg" width="280" height="50" >
									<%}%>
									<hr <%if(user_id.intValue()!=5){%>class=noprint <%} %>color="#000000"  size="1">
								</td>
							</tr>
							<tr>
								<td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font class="font16"><%=post_code %></font></td>
							</tr>
							<tr>
								<td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font class="font16"><%=post_address %></font></td>
							</tr>
							<tr>
							    <td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font face="����" size="2"><%=cust_name %>(��)</font></td>
							</tr>
							<tr>
							    <td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font class="font12"><%=contract_sub_bh %></font></td><td>
							</tr>
							<tr>
							    <td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left"></td>
								<td align="left"></td>
							</tr>
							<tr>
							    <td align="left" height=25>&nbsp;&nbsp;&nbsp;</td>
								<td align="left"></td>
								<td align="left"></td>
							</tr>
							<tr>
								<td align="center" colspan=3><H2>������֤��</H2></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan="2" rowspan="5"><font class="font14">&nbsp;&nbsp;&nbsp;&nbsp;��������֤�����������ļ���Լ�����ɱ����мƻ��������˳��У��ǽ���ת�á���Ѻ���̳к���ʧ�Ǽǵ����д��ֵĻ������ݣ���ǰ���������д�����������������˴�����������������Ч��������Ӧ�������ļ���Լ��������Ȩ�����������񡣱����мƻ���<%=application.getAttribute("COMPANY_NAME")%>��Ϊ�����˹������вƲ���������ר�����������޹ء���������ϸ�˶Ա�������֤�����صĸ������ϡ�</font></td>
							</tr>
							<tr>
								<td align="left"></td>
							</tr>
							<tr>
								<td align="left"></td></tr>
							<tr>
								<td align="left"></td></tr>
							<tr>
								<td align="left"></td></tr>
							<tr>
							<td align="left"></td>
								<td align="left" height=25 colspan=2></td>
							</tr>
							<tr>
								<td align="left">&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan=3>���мƻ�:<%=product_name %></td>
							</tr>
							<tr>
								<td align="left">&nbsp;&nbsp;&nbsp;</td>
								<td align="left" colspan=2>���ڳ�����:<font class=date><%=Utility.trimNull(Format.formatDateCn(start_date.intValue()))%></font></td>
							</tr>
							<tr>
								<td align="left">&nbsp;&nbsp;&nbsp;</td>
								<td align="left"  colspan=2>���ڳ���:<font class=date><%=fact_money %></font>Ԫ</td>
								
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>����������ʼ��:<font class=date><%=Utility.trimNull(Format.formatDateCn(ben_date.intValue())) %></font></td>
								
							</tr>
							<tr>	
								<td align="left"></td>
								<td align="left" colspan=2>��ͬ����:<font class=date><%=fact_num %></font>��</td>
								
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" height=25 colspan=2></td>
							</tr>
							<tr>	
								<td align="left"></td>
								<td align="left" colspan=2>������:<font face="����"><%=cust_name %></font></td>
							</tr>
							<tr>	
								<td align="left"></td>
								<td align="left" colspan=2>�ʱ�:<font class=date><%=post_code %></font></td>
								
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>��ϵ��ַ:<%=post_address %></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>֤������:[<%=card_type_name %>]<font class=date><%=card_id %></font></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>�̶��绰:<font class=date><%=H_tel %></font></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>�ƶ��绰:<font class=date><%=mobile %></font></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>�����ʼ�:<%=e_mail %></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td colspan=2 align="left">&nbsp;</td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>���к�ͬ���:<font class=date><%=contract_sub_bh %></font></td>
							</tr>
							<tr> 
								<td align="left"></td>
								<td align="left" colspan=2>�ݶ�:<font class=date><%=to_amount %></font>��</td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>��ֵ������ң�:<font class=date><%=Utility.trimNull(Format.formatMoney(rg_money)) %></font>Ԫ</td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>���������ʻ�:[<%=bank_name%><%=bank_sub_name%>]<font class=date><%=bank_acct %></font></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>����:<font class=date><%=valid_period%><%=Argument.getProductUnitName(period_unit)%></font></td>
							</tr>
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>���漶��:<font class=date><%=Utility.trimNull(prov_flag_name)%></font></td>
							</tr>	
				
							<tr>
								<td align="left"></td>
								<td align="left" colspan=2>
									�ʽ�����:<font class=date><%=Utility.trimNull(Format.formatDateCn(dz_date.intValue())) %></font>&nbsp;&nbsp;&nbsp;
									���˽��:<font class=date><%=Utility.trimNull(Format.formatMoney(to_money)) %></font>
								</td>
							</tr>
							<tr>
								<td align="left"></td>
								<td colspan=2 align="left">&nbsp;</td>
							</tr>
							<tr>
							<td align="left"></td>
								<td align="left" colspan=2>��ת�á��ع�����Ѻ����ʧ����������Ǽ�:</td>
							</tr>
							<tr>
							<td align="left"></td>
								<td colspan=2 align="center" height=50></td>
							</tr>
							<tr>
							<td align="left"></td>
								<td align="right" colspan=2>�����ˣ�<%=application.getAttribute("COMPANY_NAME")%></td>
							</tr>
							<tr>
							<td align="left"></td>
								<td align="right" colspan=2><font class=date><%=Format.formatDateCn(Utility.getCurrentDate())%></font></td>
							</tr>
							<tr>
							<td align="left"></td>
								<td colspan=2></td>
							</tr>
							<tr>
								<td align="left" height=25></td>	
								<td colspan=2>
									<hr color="#000000"  size="1">
									<table width="100%">
										<tr>
											<td><font size="2">��ַ���Ϻ����л�·1600�Ż������Ĵ���18¥</td>
											<td align="right"><font size="2">�ʱ�:200021</font></td>
										</tr>
										<tr>
											<td><font size="2">�Ƹ����ߣ�4008218788</td>
											<td align="right"><font size="2">��ַ�� www.zhongtaitrust.com</font></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<hr class=noprint style="border:dashed #e9e9e9;">	   
				   		<table id=printbtn border="0" width="100%">
							<tr>
								<td align=center class=noprint>
									<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>
									<button class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:document.all.WebBrowser.ExecWB(6,6);">��ӡ(<u>P</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button style="display:" class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:disableAllBtn(true);history.back();">����(<u>B</u>)</button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<%benifitor.remove();%>
	</body>
</html>