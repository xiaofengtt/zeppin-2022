<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.intrust.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ô�ӡ����
String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");
Integer startdate = Utility.parseInt(Utility.trimNull(request.getParameter("startdate")), new Integer(0));
Integer enddate = Utility.parseInt(Utility.trimNull(request.getParameter("enddate")), new Integer(0));

CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

//ҳ�渨������
Integer cust_id = new Integer(0);
List list = new ArrayList();
Map map = new HashMap();
List ben_list = new ArrayList();
Map ben_map = new HashMap();
String period_unit="";

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
.Noprint{display:none;}
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY class="BODY" topmargin="8" rightmargin="8"
	onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" action="#" method="post">
<table border="0" width="90%" cellspacing="1" cellpadding="4" id=table99 align="center" class="Noprint">
	<tr>
		<td>
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0 VIEWASTEXT></OBJECT>	
					<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:window.print();">��ӡ(<u>P</u>)</button>	 
					&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
					&nbsp;&nbsp;&nbsp; 
					<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">�ر�(<u>B</u>)</button>
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
	int j =0;
	for(int i=0; i<cust_items.length; i++){
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));
		if(!(cust_id.equals(new Integer(0))))
		{
			vo.setCust_id(cust_id);//ѭ����ÿͻ�ID�������Ϣ
			list = local.listProcAll(vo);
			Iterator it = list.iterator();
			while(it.hasNext()){
				map = (Map)it.next();
				Integer print_deply_bill = Utility.parseInt(Utility.trimNull(map.get("PRINT_DEPLOY_BILL")),new Integer(0));
				
				%>
				<div <%if(print_deply_bill.intValue()==0){%>class="Noprint"<% }%>>
					<%if(j!=0){%><div style="page-break-after:always">&nbsp;</div><%}%>
					<table border="0" width="100%" cellspacing="0" cellpadding="4">
					<tr>		
						<td align="right" style="height:20mm;"></td>
					</tr>	
					<tr>		
						<td width="6%" align="right" style="height:8mm;"></td>
						<td width="40%" align="left"><font size="3" face="����"><b><%=Utility.trimNull(map.get("POST_CODE"))%></b></font></td>
						<td align="left" >&nbsp;</td>
						<td width="45%"  align="left"></td>
					</tr>
					<tr>		
						<td width="6%" align="right" style="height:8mm;"></td>
						<td width="40%" align="left"><font size="3" face="����"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></font></td>
						<td align="left">&nbsp;</td>
						<td width="45%" align="left"></td>
					</tr>
					<tr >		
						<td width="6%" align="right" style="height:8mm;"></td>
						<td width="40%" align="left"><font size="3" face="����"><b><%=Utility.trimNull(map.get("CUST_NAME"))%></b>&nbsp;&nbsp;��</font></td>
						<td>&nbsp;</td>
						<td>��������ϵ��ʽ�����仯���뼰ʱ����ͷ�<br>���ߣ�400-610-8899��010-59680800�����и��¡�</td>
					</tr>
					<tr>		
						<td align="right" style="height:40mm;"></td>
					</tr>	
					</table>
					<div align="center" style="height:7mm;"><font size="3"><b>���зݶ���˵�<%if(print_deply_bill.intValue()==0){%> (ֻ�鿴����ӡ)<% }%></b></font></div>		
					<!--strat ���зݶ���˵�--> 
					<table border="1" cellspacing="0" cellpadding="2" width="90%" align="center" style="border-collapse:collapse;"  bordercolor="black">
					<tr>
						<td align="center" style="height:9mm;">��Ʒ����</td>
						<td align="center">�Ϲ����</td>
						<td align="center">�������</td>
						<td align="center">������ʼ��</td>
						<td align="center">�ѷ������棨Ԫ��</td>
						<td align="center">���濪������</td>
						<td align="center">�����˻�</td>		
					</tr>
					<%
					BigDecimal amount = new BigDecimal("0.000");
					BigDecimal to_amount = new BigDecimal("0.000");
					BigDecimal sy_money = new BigDecimal("0.000");
					int iCount = 0;
					int iCurrent = 0;
					Integer serial_no=new Integer(0);
					BenifitorLocal ben_local = EJBFactory.getBenifitor();//������
					BenifitorVO ben_vo = new BenifitorVO();
					ben_vo.setProduct_id(new Integer(0));
					ben_vo.setBook_code(new Integer(1));
					ben_vo.setCust_id(cust_id);
					ben_vo.setProduct_status("110203");//ֻ�������ڲ�Ʒ
					ben_list = ben_local.QueryBenifitor(ben_vo);
					Iterator ben_it = ben_list.iterator();
					String transferName="";
					String ben_amount_show="";
					String sy_money_show="";
					while(ben_it.hasNext())
					{
						ben_map = (Map)ben_it.next();
						ben_amount_show=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT")))));
						if ("".equals(ben_amount_show))
							ben_amount_show="0.00";
						sy_money_show=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("SY_MONEY"))));
						if ("".equals(sy_money_show)) sy_money_show="0.00";
							if(ben_map.get("TO_AMOUNT") != null)
								to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT"))));			
							if(ben_map.get("BEN_AMOUNT") != null)
								amount = amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));
							if(ben_map.get("SY_MONEY") != null)
								sy_money = sy_money.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("SY_MONEY"))));
					%>
					<tr>
						<td align="left"  style="height:9mm"><%=Utility.trimNull(ben_map.get("PRODUCT_NAME"))%></td>
						<td align="right"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT")))))%></td>
						<td align="right"><%=ben_amount_show%></td>
						<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(ben_map.get("BEN_DATE")), new Integer(0)))%></td>
						<td align="right"><%=sy_money_show%></td>
						<td><%=Utility.trimNull(ben_map.get("BANK_NAME"))%><%=Utility.trimNull(ben_map.get("BANK_SUB_NAME"))%></td>
						<td><%=Utility.trimNull(ben_map.get("BANK_ACCT"))%></td>
					</tr>
					<%
						iCurrent++;
						iCount++;
					}
					local.remove();
					ben_local.remove();
					%>
					<tr>
						<td align="center"  style="height:9mm"><b>�ϼ� <%=iCount%> ��</b></td>
						<td align="right"><%=Format.formatMoney(to_amount)%></td>
						<td align="right"><%="".equals(Format.formatMoney(amount))?"0.00":Format.formatMoney(amount)%></td>
						<td align="center">-</td>
						<td align="right" ><%="".equals(Format.formatMoney(sy_money))?"0.00":Format.formatMoney(sy_money)%></td>
						<td align="center">-</td>
						<td align="center" >-</td>
					</tr>
					</table>
					<br>
					<!--end ���зݶ���˵�--> 
					<!--start ������ϸ-->
					<table border="0" cellspacing="0" cellpadding="2" width="90%" align="center">
					<tr>
						<td><font size="3"><b>������ϸ</b></font></td>
						<td align="right">��ֹ���ڣ�<%=startdate %>��<%=enddate %></td>	
					</tr>
					</table>
					<table border="1" cellspacing="0" cellpadding="2" width="90%" align="center" style="border-collapse:collapse;"  bordercolor="black">
					<tr>
						<td  align="center" style="height:9mm">��Ʒ����</td>
						<td  align="center">����</td>
						<td  align="center">����ժҪ</td>
						<td  align="center">��Ԫ��</td>
						<td  align="center">��ע</td>
					</tr>	
					<%
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
					%>
					<tr>
						<td align="left" style="height:9mm">&nbsp;<%=Utility.trimNull(money_map.get("PRODUCT_NAME"))%></td>
						<td align="left">&nbsp;<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(money_map.get("TRADE_DATE")), new Integer(0)))%></td>
		        		<td align="left">&nbsp;<%=Utility.trimNull(money_map.get("TRADE_TYPE"))%></td>
						<td align="right"><%=Format.formatMoney(Utility.stringToDouble(Utility.trimNull(money_map.get("TRADE_MONEY"))))%></td>
						<td align="left">&nbsp;</td>
					</tr>
					<%
					}
					%>
					</table>
					<table border="0" cellspacing="0"  align="center"  width="90%">
						<tr>
							<td style="height:9mm" >ע�������˵����ṩ����Ϣ�����ο�������ȷ�Ͻ�����ҹ�˾ϵͳ��¼Ϊ׼��</td>
						</tr>	
					</table>
					<br>	
					<hr class="Noprint">
				</div>	
			<%	if(print_deply_bill.intValue() == 1) {
					j++;
				}
			}
		}
	}
}
%>
<br>

</HTML>
