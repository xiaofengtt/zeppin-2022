<%@ page contentType="text/html; charset=GBK"  import="java.text.SimpleDateFormat,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String[] serial_nos = request.getParameterValues("serial_no_c");
String sbf_bank_name = Utility.trimNull(request.getParameter("sbf_bank_name")) + Utility.trimNull(request.getParameter("sbf_sub_bank_name"));//募集户开户行
String sbf_bank_acct = Utility.trimNull(request.getParameter("sbf_bank_acct"));//募集户账号
Integer sbf_serial=Utility.parseInt(request.getParameter("sbf_serial_no"),new Integer(0));


String card_id = "";
String cust_name = "";
String bank_name = "";
String bank_acct_name = "";
BigDecimal dz_money =  new BigDecimal(0);
//String bank_account = "";
String to_bank_account = "";
String to_bank_name="";
String dz_date = "";
String product_name = "";
String ben_account="";

PreMoneyDetailLocal preMoneyDetail = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();

List list = null;
Map map = null;
if (sbf_serial.intValue()>0){
	map=Argument.getBankInfo1021BySerialNo(sbf_serial);
	if (map!=null && !map.isEmpty()){
		sbf_bank_acct=Utility.trimNull((String)map.get("BANK_ACCT"));
		sbf_bank_name=Utility.trimNull((String)map.get("BANK_NAME"))+Utility.trimNull((String)map.get("SUB_BANK_NAME"));
	}
}
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
Date date = null;
%>

<html>
	<head>
		<title>打印资金证实书</title>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<script language="vbscript" src="/includes/default.vbs"></script>
		<script language="javascript" src="/includes/default.js"></script>
	</head>

	<body leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
		<form name="theform" method="post">
			<table cellSpacing=0 cellPadding=0 width="100%" border=0>
				<tr>
					<td align=center></td>
				</tr>
				<tr>
					<td vAlign=top align=left>
						<br><br>
<%if (serial_nos!=null){
	int count=0;
	Integer serial_no=new Integer(0);
	for (int i = 0;i < serial_nos.length; i++){
		count++;
		serial_no=new Integer(Integer.parseInt(serial_nos[i]));
		vo.setSerial_no(serial_no);
		vo.setInput_man(input_operatorCode);
		list = preMoneyDetail.queryMoneyDataSerial(vo);
		if (list==null || list.isEmpty()){
			continue;
		}
		map=(Map)list.get(0);
		if (map==null){
			continue;
		}
		card_id = Utility.trimNull(map.get("CARD_ID"));
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		bank_name = Utility.trimNull(map.get("BANK_NAME"));
		bank_acct_name = Utility.trimNull(map.get("BANK_ACCT_NAME"));
		dz_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")), new BigDecimal(0),0,"1");
		ben_account = Utility.trimNull(map.get("BANK_ACCT"));
		
		dz_date = Format.formatDateLine((Integer)map.get("DZ_DATE"));
		
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		date = simpleDateFormat.parse(dz_date);
		to_bank_account="".equals(sbf_bank_acct)?Utility.trimNull(map.get("TO_BANK_ACCT")):sbf_bank_acct;
		to_bank_name="".equals(sbf_bank_name)?Utility.trimNull(map.get("TO_BANK_NAME")):sbf_bank_name;

 %>
						<table <%if(count<serial_nos.length) out.print("style='page-break-after:always'"); %> cellSpacing=0 cellPadding=0 width="100%" border=0>
							<tr>
								<td width="100%" colspan=5 align=center><font style="font-family: '宋体'; font-size: 20px"><%=bank_acct_name %></font></td>
							</tr>
							<tr>
								<td colspan=5 align=center><font style="font-family: '宋体'; font-size: 20px" ><b>资金证实书</b></font></td>
							</tr>
							<tr>
		    					<td width=10></td>
		    					<td colspan=3 align="right"><font style="font-family: '宋体'; font-size: 14px" ><%=Format.formatDateCn(date)%>&nbsp;&nbsp;</font></td>
		    					<td width=10></td>
							</tr>
							<tr>
		 						<td width=10></td>
		 						<td colspan=3>
									<table style='border-style:solid;border-width:1px;' cellspacing="0" cellpadding="0"  width="100%">
										<tr>
											<td width="8%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' align="center" rowspan="3"><font style="font-family: '宋体'; font-size: 14px" >&nbsp;受&nbsp;<br>&nbsp;托&nbsp;<br>&nbsp;人&nbsp;</font></td>
											<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >全&nbsp;&nbsp;称</font></td>
											<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;'  height=30><font style="font-family: '宋体'; font-size: 14px" ><%=application.getAttribute("COMPANY_NAME")%></font></td>
											<td width="8%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' align="center" rowspan="3"><font style="font-family: '宋体'; font-size: 14px" >&nbsp;委&nbsp;<br>&nbsp;托&nbsp;<br>&nbsp;人&nbsp;</font></td>
											<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >名&nbsp;&nbsp;&nbsp;&nbsp;称</font></td>
											<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:1px;;'  height=30><font style="font-family: '宋体'; font-size: 14px" ><%=cust_name %></font></td>
										</tr>
										<tr>
											<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >帐&nbsp;&nbsp;号</font></td>
							    			<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" ><%=to_bank_account %></font></td>
<%if (user_id.intValue()==5){/*上海中泰*/ %>
							    			<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >账&nbsp;&nbsp;&nbsp;&nbsp;号</font></td>
<%}else{ %>
							    			<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >证照号码</font></td>
<%} if (user_id.intValue()==5){/*上海中泰*/ %>
							    			<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" ><%=ben_account %></font></td>
<%}else{ %>
							    			<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" ><%=card_id %></font></td>
<%} %>
							  			</tr>
										<tr>
											<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >开户行</font></td>
											<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" ><%=to_bank_name %></font></td>
											<td width="10%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" >摘&nbsp;&nbsp;&nbsp;&nbsp;要</font></td>
											<td width="32%" style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:1px;' height=30><font style="font-family: '宋体'; font-size: 14px" ><%=product_name %></font></td>
										</tr>
										<tr>
											<td width="50%" style='border-style:solid;border-left-width:0px;border-right-width:1px;border-top-width:0px;border-bottom-width:1px;' colspan="3" height=30><font style="font-family: '宋体'; font-size: 14px" >人民币(大写)：&nbsp;<input style="font-family: '宋体'; font-size: 14px"  readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="25" name="cname3" value="<%=Utility.numToChinese(dz_money.toString()) %>"></font></td>
											<td width="50%" style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:1px;' colspan="3" height=30><font style="font-family: '宋体'; font-size: 14px" >小写：&nbsp;<input style="font-family: '宋体'; font-size: 14px"  readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="cname3" value="￥<%=Format.formatMoney(dz_money) %>元"></font></td>
										</tr>
										<tr>
											<td style='border-style:solid;border-left-width:0px;border-right-width:0px;border-top-width:0px;border-bottom-width:0px;' colspan=6  height=60>
												<b><font style="font-family: '宋体'; font-size: 14px" >备注：</font></b>
 												<font style="font-family: '宋体'; font-size: 14px" >&nbsp;本资金证实书用于受托人收到委托人资金款项的证实，不作质押凭证之用，具体信托事务的处理以信托合同为准。</font>
											</td>
										</tr>
									</table>
								</td>
								<td width=10></td>
							</tr>   
							<tr>
								<td colspan=5 height=10></td>
							</tr>		
							<tr>
								<td width=10></td>
								<td align=center><font style="font-family: '宋体'; font-size: 14px" >受托人(盖章)：</font></td>
								<td align=center><font style="font-family: '宋体'; font-size: 14px" >复核：</font></td>
								<td align=center><font style="font-family: '宋体'; font-size: 14px" >经办：</font></td>
								<td width=10></td>
							</tr>
						</table>
<%
	}
}
%>
						<table id=printbtn border="0" width="100%" class="noprint">
							<tr>
								<td align=right>
									<object id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 ></object>
									<button class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:printbtn.style.display='none';document.all.WebBrowser.ExecWB(6,6);printbtn.style.display='';">打印(<u>P</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:printbtn.style.display='none';document.WebBrowser.ExecWB(7,1);printbtn.style.display='';">打印预览</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">打印机设置(<u>C</u>)</button>
									&nbsp;&nbsp;&nbsp;
									<button style="display:" class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:window.close();">关闭(<u>B</u>)</button>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>