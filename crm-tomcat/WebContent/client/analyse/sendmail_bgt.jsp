<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.intrust.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.web.*,javax.mail.Session"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得发送邮件参数
String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");
Integer startdate = Utility.parseInt(Utility.trimNull(request.getParameter("startdate")), new Integer(0));
Integer enddate = Utility.parseInt(Utility.trimNull(request.getParameter("enddate")), new Integer(0));

CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
//页面辅助变量
Integer cust_id = new Integer(0);
List list = new ArrayList();
Map map = new HashMap();
List ben_list = new ArrayList();
Map ben_map = new HashMap();
SendMail2 sendEmail = new SendMail2();
String period_unit="";
String Email = "";
boolean bSuccess = false;

if(request.getMethod().equals("POST")){	
	map = Argument.getMailMessage();
	String strSMTPHost = Utility.trimNull(map.get("SMTP_SERVER"));
	String mailFrom = Utility.trimNull(map.get("MAIL_FROM"));
	String user = Utility.trimNull(map.get("SMTP_USER"));
	String password = Utility.trimNull(map.get("SMTP_USERPWD"));
	sendEmail.setSMTPHost(strSMTPHost,"","");
	sendEmail.setFromAddress(mailFrom);
	Session session1 = sendEmail.contectServer("true",user,password);
	for(int i=0; i<cust_items.length; i++){	
		
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));
		list = local.queryEmail(cust_id,new Integer(1));
		map = (Map)list.get(0);
		Email = Utility.trimNull(map.get("EMAIL"));
		String title = Utility.trimNull(map.get("TITLE"));
		String content = Utility.trimNull(map.get("CONTENT"));
		sendEmail.setAddress(Email,sendEmail.TO);
		sendEmail.setSubject(title);
		sendEmail.setHtmlBody(content);
		sendEmail.sendMail(session1,input_operatorCode);
		
		local.modiEmail(cust_id,input_operatorCode);
		
	}
	local.remove();
	bSuccess = true;	
}
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<style media=print>
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT language="javascript">

/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		//sl_update_ok();	
		alert("发送成功");
		//window.returnValue=1;	
		window.close();
	}
};

	function sendEmail(){	
		document.getElementById("theform").submit();
	}
</SCRIPT>
</HEAD>
<BODY class="BODY" topmargin="8" rightmargin="8"
	onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" id="theform" action="sendmail_bgt.jsp" method="post">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="cust_id" name="cust_id" value="<%=scust_id%>"/>
<input type="hidden" id="startdate" name="startdate" value="<%=startdate%>"/>
<input type="hidden" id="enddate" name="enddate" value="<%=enddate%>"/>
<table border="0" width="90%" cellspacing="1" cellpadding="4" id=table99 align="center" class="Noprint">
	<tr>
		<td>
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>	
					<button type="button"  class="xpbutton3" accessKey=p name="btnSend" title="发送邮件" onclick="javascript:sendEmail();">发送(<u>S</u>)</button>	 
					&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">关闭(<u>B</u>)</button>
					&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%
if(cust_items != null && cust_items.length > 0)
{	
	
	String str = "";
	int j =0;
	for(int i=0; i<cust_items.length; i++){
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));
		StringBuffer sb = new StringBuffer();
		if(!(cust_id.equals(new Integer(0))))
		{
			vo.setCust_id(cust_id);//循环获得客户ID的相关信息
			list = local.listProcAll(vo);
			Iterator it = list.iterator();
			while(it.hasNext()){
				map = (Map)it.next();
					String postCode = Utility.trimNull(map.get("POST_CODE"));
					String postAddress = Utility.trimNull(map.get("POST_ADDRESS"));
					String custName = Utility.trimNull(map.get("CUST_NAME"));
					Email = Utility.trimNull(map.get("E_MAIL"));%>
					<div class='Noprint'>
					<%if(j!=0){%><div style="page-break-after:always">&nbsp;</div><%}%>
					<table border='0' width='100%' cellspacing='0' cellpadding='4'>
					<tr><td align='right' style='height:20mm;'></td></tr>
					<tr><td width='6%' align='right' style='height:8mm;'></td><td width='40%' align='left'><font size='3' face='宋体'><b>
					<%=postCode %>
					</b></font></td><td align='left' >&nbsp;</td><td width='45%'  align='left'></td></tr>
					<tr><td width='6%' align='right' style='height:8mm;'></td><td width='40%' align='left'><font size='3' face='宋体'>
					<%= postAddress%>
					</font></td><td align='left'>&nbsp;</td><td width='45%' align='left'></td></tr>
					<tr><td width='6%' align='right' style='height:8mm;'></td><td width='40%' align='left'><font size='3' face='宋体'><b>
					<%= custName%>
					</b>&nbsp;&nbsp;收</font></td><td>&nbsp;</td><td>邮编地址：<%= Email%></td></tr>
					<tr><td align='right' style='height:40mm;'></td></tr></table>
<%
					sb.append("<div align='center' style='height:7mm;'><font size='3'><b>信托份额对账单</b></font></div>");
					sb.append("<table border='1' cellspacing='0' cellpadding='2' width='90%' align='center' style='border-collapse:collapse;'  bordercolor='black'>");
					sb.append("<tr><td align='center' style='height:9mm;'>产品名称</td>");
					sb.append("<td align='center'>认购金额</td>");
					sb.append("<td align='center'>存量金额</td>");
					sb.append("<td align='center'>受益起始日</td>");
					sb.append("<td align='center'>已分配收益（元）</td>");
					sb.append("<td align='center'>受益开户银行</td>");
					sb.append("<td align='center'>受益账户</td></tr>");

					BigDecimal amount = new BigDecimal("0.000");
					BigDecimal to_amount = new BigDecimal("0.000");
					BigDecimal sy_money = new BigDecimal("0.000");
					int iCount = 0;
					int iCurrent = 0;
					Integer serial_no=new Integer(0);
					BenifitorLocal ben_local = EJBFactory.getBenifitor();//受益人
					BenifitorVO ben_vo = new BenifitorVO();
					ben_vo.setProduct_id(new Integer(0));
					ben_vo.setBook_code(new Integer(1));
					ben_vo.setCust_id(cust_id);
					ben_vo.setProduct_status("110203");//只查正常期产品
					ben_list = ben_local.QueryBenifitor(ben_vo);
					Iterator ben_it = ben_list.iterator();
					String transferName="";
					while(ben_it.hasNext())
					{
						ben_map = (Map)ben_it.next();
							if(ben_map.get("TO_AMOUNT") != null)
								to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT"))));			
							if(ben_map.get("BEN_AMOUNT") != null)
								amount = amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));
							if(ben_map.get("SY_MONEY") != null)
								sy_money = sy_money.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("SY_MONEY"))));

					String productName = Utility.trimNull(ben_map.get("PRODUCT_NAME"));
					String toAmount = Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT")))));
					String benAmount = Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT")))));
					String benDate = Format.formatDateCn(Utility.parseInt(Utility.trimNull(ben_map.get("BEN_DATE")), new Integer(0)));
					String syMoney = Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("SY_MONEY"))));
					String bankName = Utility.trimNull(ben_map.get("BANK_NAME"));
					String bankSubName = Utility.trimNull(ben_map.get("BANK_SUB_NAME"));
					String bankAcct = Utility.trimNull(ben_map.get("BANK_ACCT"));
					
					sb.append("<tr><td align='left' style='height:9mm'>");
					sb.append(productName);
					sb.append("</td><td align='right'>");
					sb.append(toAmount);
					sb.append("</td><td align='right'>");
					sb.append(benAmount);
					sb.append("</td><td align='center'>");
					sb.append(benDate);
					sb.append("</td><td align='right'>");
					sb.append(syMoney);
					sb.append("</td><td>");
					sb.append(bankName);
					sb.append(bankSubName);
					sb.append("</td><td>");
					sb.append(bankAcct);
					sb.append("</td></tr>");

					iCurrent++;
					iCount++;
					}
					local.remove();
					ben_local.remove();

					sb.append("<tr><td align='center'  style='height:9mm'><b>合计 ");
					sb.append(iCount);
					sb.append(" 项</b></td><td align='right'>");
						String to_Amount = Format.formatMoney(to_amount);
						String Amount = Format.formatMoney(amount);
						String sy_Money = Format.formatMoney(sy_money);
					sb.append(to_Amount);
					sb.append("</td><td align='right'>");
					sb.append(Amount);
					sb.append("</td><td align='center'>-</td><td align='right'>");
					sb.append(sy_Money);
					sb.append("</td><td align='center'>-</td><td align='center'>-</td></tr></table><br>");
					sb.append("<table border='0' cellspacing='0' cellpadding='2' width='90%' align='center'>");
					sb.append("<tr><td><font size='3'><b>交易明细</b></font></td><td align='right'>起止日期：");
					sb.append(startdate);
					sb.append("至");
					sb.append(enddate);
					sb.append("</td></tr></table>");
					sb.append("<table border='1' cellspacing='0' cellpadding='2' width='90%' align='center' style='border-collapse:collapse;'  bordercolor='black'>");
					sb.append("<tr><td  align='center' style='height:9mm'>产品名称</td>");
					sb.append("<td  align='center'>日期</td><td  align='center'>交易摘要</td>");
					sb.append("<td  align='center'>金额（元）</td><td  align='center'>备注</td></tr>");

					MoneyDetailLocal money_datail = EJBFactory.getMoneyDetail();
					MoneyDetailVO money_vo = new MoneyDetailVO();
					money_vo.setBook_code(new Integer(1));
					money_vo.setCust_id(cust_id);
					money_vo.setStart_date(startdate);
					money_vo.setEnd_date(enddate);
					List money_list = money_datail.listTradeDetail(money_vo);
					Map money_map = new HashMap();
					Iterator money_it = money_list.iterator();
					while(money_it.hasNext()){
						money_map = (Map)money_it.next();
					
					String productName = Utility.trimNull(money_map.get("PRODUCT_NAME"));
					String tradeDate = Format.formatDateCn(Utility.parseInt(Utility.trimNull(money_map.get("TRADE_DATE")), new Integer(0)));
					String tradeType = Utility.trimNull(money_map.get("TRADE_TYPE"));
					String tradeMoney = Format.formatMoney(Utility.stringToDouble(Utility.trimNull(money_map.get("TRADE_MONEY"))));

					sb.append("<tr><td align='left' style='height:9mm'>&nbsp;");
					sb.append(productName);
					sb.append("</td><td align='left'>&nbsp;");
					sb.append(tradeDate);
					sb.append("</td><td align='left'>&nbsp;");
					sb.append(tradeType);
					sb.append("</td><td align='right'>");
					sb.append(tradeMoney);
					sb.append("</td><td align='left'>&nbsp;</td></tr>");
					}
					sb.append("</table>");
					sb.append("<table border='0' cellspacing='0'  align='center'  width='90%'>");
					sb.append("<tr><td style='height:9mm' >注：本对账单所提供的信息仅供参考，最终确认结果以我公司系统记录为准。</td></tr></table><br>");
					sb.append("<hr class='Noprint'>");
					j++;
					str = sb.toString();	
					out.print(str);
					String title = custName+"对账单";
					if(user_id.intValue() == 2)
						title = "北京信托-"+title;
					if(!bSuccess){
						local.addEmail(cust_id,Email,title,str,input_operatorCode);
					}				
			}
				
		}
	}
	
    
}
				%>
		</div>
	</form>
<br>

</HTML>
