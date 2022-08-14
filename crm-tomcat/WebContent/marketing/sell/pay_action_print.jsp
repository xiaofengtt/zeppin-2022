<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//ҳ�洫�ݲ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
//Integer list_id = Utility.parseInt(request.getParameter("list_id"), null);
String contract_bh = request.getParameter("contract_bh");
//��������
//��������
Integer cust_id = new Integer(0);
Integer print_count = new Integer(0); //��ӡ����
String print_no = ""; //���
String product_name = ""; //��Ʒ����
String contract_sub_bh = "";  //��ͬ���
Integer jk_date = new Integer(0); //�ɿ�����
String cust_name = ""; //�ͻ�����
String cust_type_name = ""; //�ͻ���������
String card_type_name = ""; //�ͻ�֤������
String card_id = ""; //֤������
BigDecimal jk_money = new BigDecimal(0.0); //�ɿ���
String jk_money_string = ""; //�ɿ����ַ�
String currency_name = ""; //����
String jk_type_name = ""; //�ɿʽ����
String jk_xh = "";//�ɿ����
String extension = "";
char[] s_money  = null;
boolean bSuccess = false;
//��ö���
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO md_vo = new MoneyDetailVO();
CustomerLocal customer_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
Map md_map = null;
//�ύ����
if(request.getMethod().equals("POST")){
	bSuccess = true;
}
//��ѯ�ɿ���ϸ��Ϣ
if(serial_no.intValue()>0){
	md_vo = new MoneyDetailVO();
	md_vo.setSerial_no(serial_no);
	List md_list = moneyDetailLocal.load(md_vo);
	if(md_list.size()>0){
		md_map = (Map)md_list.get(0);

		product_id = Utility.parseInt(Utility.trimNull(md_map.get("PRODUCT_ID")),new Integer(0));
		contract_bh = Utility.trimNull(md_map.get("CONTRACT_BH"));
		cust_id = Utility.parseInt(Utility.trimNull(md_map.get("CUST_ID")),new Integer(0));
		contract_sub_bh = Utility.trimNull(md_map.get("CONTRACT_SUB_BH"));
		product_name = Utility.trimNull(md_map.get("PRODUCT_NAME"));
		jk_type_name = Utility.trimNull(md_map.get("JK_TYPE_NAME"));
    	jk_date = Utility.parseInt(Utility.trimNull(md_map.get("DZ_DATE")),new Integer(0));
		cust_id = Utility.parseInt(Utility.trimNull(md_map.get("CUST_ID")),new Integer(0));
		jk_money = Utility.parseDecimal(Utility.trimNull(md_map.get("TO_MONEY")),new BigDecimal(0));
		jk_money_string = Utility.numToChinese(Utility.trimNull(jk_money));
		StringBuffer print_money= new StringBuffer("��"+Format.formatMoneyPrint(jk_money.doubleValue(),0));
		s_money = print_money.reverse().toString().toCharArray();
		print_count = Utility.parseInt(Utility.trimNull(md_map.get("PRINT_COUNT")),new Integer(0));
		print_count = new Integer(print_count.intValue()+1);
		Integer list_id = Utility.parseInt(Utility.trimNull(md_map.get("LIST_ID")),new Integer(0));
		if(list_id.intValue() < 10)
			jk_xh = "0"+ list_id;
		else 
			jk_xh= Utility.trimNull(list_id);

		TcustmanagersVO vo = new TcustmanagersVO();
		enfo.crm.affair.TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();
		vo.setManagerid(input_operatorCode);
		String[] totalColumn = new String[0];
		List rsList =tcustmanagers_Bean.pagelist_query(vo,totalColumn,1,-1).getRsList();
		
		if(rsList!=null&&rsList.size()==1){
		    Map operatorMap = (Map)rsList.get(0);
		    extension = Utility.trimNull(operatorMap.get("Extension"));
		}
		String op_xh = extension.length()==4?extension.substring(extension.length()-2,extension.length()):"__";
		if(print_count.intValue()<10) {
			print_no = Utility.getCurrentYear() + contract_sub_bh.substring(contract_sub_bh.length()-3,contract_sub_bh.length())
						+jk_xh + op_xh + "0" + print_count;
		} else {
			print_no = Utility.trimNull(md_map.get("PRINT_PREFIX")) + print_count;
		}
	}
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	List custList = customer_local.listCustomerLoad(cust_vo);
	if(custList.size()>0) {
		Map cust_map = (Map)custList.get(0);
		cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
	    cust_type_name = Utility.trimNull(cust_map.get("CUST_TYPE_NAME"));
	    card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
	    card_id = Utility.trimNull(cust_map.get("CARD_ID"));
	}	
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title>
<!--media=print ������Կ����ڴ�ӡʱ��Ч-->
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<style>
.STYLE1 {
	font-size: 17px;
	font-weight: bold;
}
td {
    font-family: Arial, Tahoma, Verdana;
	font-size: 17px;

	height:33px;
}
.tdlefttop {
	border-left:1px solid #000000;
	border-top:1px solid #000000;
	border-right:1px solid #000000;
	border-bottom:1px solid #000000;
}
.tdtop {
	border-top:1px solid #000000;
	border-right:1px solid #000000;
	border-bottom:1px solid #000000;
}
.tdleft {
	border-left:1px solid #000000;
	border-right:1px solid #000000;
	border-bottom:1px solid #000000;
}
.tdrightbottom {
	border-right:1px solid #000000;
	border-bottom:1px solid #000000;
}
.hrGray {height:1px;border:none;border-top:1px dashed #666666;width:100%;margin-top:10px;margin-bottom:10px;}
</style>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
var n = 1;
/*���淽��*/
function saveAction(){
	if(document.theform.onsubmit()){ 
		disableAllBtn(true);document.theform.submit();
	}
}
function doPrint(){	
	window.open("pay_action_print_do.jsp?serial_no=<%=serial_no%>");	
}
function validateForm(form){
	//return sl_check_update();
}
//��ӡ
function printOK(serial_no) {
	//if(confirm('ȷ��Ҫ��ӡ��ȷ�Ϻ��ӡ��������1��')) {
		//��¼��ӡ����
		//utilityService.savePreMoneyDetailPrintCount(serial_no);
		window.print();
	//}
}
</script>
</head>
<BODY class="BODY body-nox">
<%//@ include file="/includes/waiting.inc"%>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<form name="theform" method="post" action="pay_action_print.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="bSuccess" value=""/>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="printOK(<%=serial_no%>)">ֱ�Ӵ�ӡ(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:window.close();">�ر�(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0 " align="center">
<tr>
<td valign="top" align="center">
<%
//����������ͬʹ��ѭ������ӡ
for(int i=0;i<3;i++) {
%>
    <table style="width:210mm;"border="0" cellpadding="4" cellspacing="0" align="center">
        <tr><td colspan="13">
            <div align="center">
                <span><font size="5px"><b><%=application.getAttribute("COMPANY_NAME")%>&nbsp;&nbsp;&nbsp;&nbsp;</b></font></span>
                <span></span>
            </div>
        </td></tr>
        <tr><td align="center" colspan="13"><font size="5px"><b><u><%=product_name%></u>&nbsp;-&nbsp;�ɿ�ƾ֤<b></font></td></tr>
	</table><br>
	<table style="width:205mm;"border="0" cellpadding="4" cellspacing="0" align="center">	
        <tr>
            <td colspan="2">��ͬ��ţ�<%=contract_sub_bh%></td>
            <td colspan="4">���ڣ�<%=Format.formatDateCn(jk_date.intValue())%></td>
            <td align="right" colspan="6">��ţ�<%=print_no%></td>
        </tr>
        <tr>
            <td class="tdlefttop"  width="15%">ί��������</td>
            <td class="tdtop" width="25%"><%=Utility.trimNull(cust_name)%></td>
            <td class="tdtop" width="15%">ί��������</td>
            <td class="tdtop" colspan="9"><%=Utility.trimNull(cust_type_name)%></td>
            <td rowspan="5" width="6px">
<%
	if(i==0) {
		out.print("��һ��<br>��������������");
	} else if (i==1) {
		out.print("�ڶ���<br>���տ���������");
	} else if (i==2) {
		out.print("������<br>��ί����������");
	}
%>
			</td>
        </tr>
        <tr>
            <td class="tdleft">֤������</td>
            <td class="tdrightbottom"><%=Utility.trimNull(card_type_name)%></td>
            <td class="tdrightbottom">֤������</td>
            <td class="tdrightbottom" colspan="9"><%=Utility.trimNull(card_id)%>&nbsp;</td>
        </tr>
        <tr>
            <td class="tdleft" rowspan="2">����д��</td>
            <td class="tdrightbottom" rowspan="2"><%=jk_money_string%></td>
            <td class="tdrightbottom" rowspan="2">��Сд��</td>
            <td class="tdrightbottom" align="center">��</td>
            <td class="tdrightbottom" align="center">ǧ</td>
            <td class="tdrightbottom" align="center">��</td>
            <td class="tdrightbottom" align="center">ʮ</td>
            <td class="tdrightbottom" align="center">��</td>
            <td class="tdrightbottom" align="center">ǧ</td>
            <td class="tdrightbottom" align="center">��</td>
            <td class="tdrightbottom" align="center">ʮ</td>
            <td class="tdrightbottom" align="center">Ԫ</td>
        </tr>
        <tr>
            <td class="tdrightbottom" align="center"><%if(s_money.length >=9) {out.print(s_money[8]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 8) {out.print(s_money[7]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 7) {out.print(s_money[6]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 6) {out.print(s_money[5]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 5) {out.print(s_money[4]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 4) {out.print(s_money[3]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 3) {out.print(s_money[2]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 2) {out.print(s_money[1]);}else{out.print("&nbsp;");} %></td>
            <td class="tdrightbottom" align="center"><%if(s_money.length >= 1) {out.print(s_money[0]);}else{out.print("&nbsp;");} %></td>
        </tr>
        <tr>
            <td class="tdleft">��������</td>
            <td class="tdrightbottom">�����</td>
            <td class="tdrightbottom">�ɿ���ʽ</td>
            <td class="tdrightbottom" colspan="9"><%=jk_type_name%></td>
        </tr>
        <tr>
            <td align="center">�����ˣ�</td>
            <td><%if(user_id.intValue()!=2){out.print(input_operatorName);}/*��������Ҫ��Ҫ����������ˡ�*/%></td>
            <td colspan="10" align="right">�����ʽ��ѽ����������տλ��������ȷ��</td>
            <td>&nbsp;</td>
        </tr>
    </table>
<%
	if(i<2) {
%>
    <hr class="Noprint">
	<table style="page-break-after:always">
		<tr><td >&nbsp;</td></tr>
	</table><br>
<%
	}
} //for
%>
</td>
</tr>
</table>
</form>
<%//@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
moneyDetailLocal.remove();
customer_local.remove();
 %>
