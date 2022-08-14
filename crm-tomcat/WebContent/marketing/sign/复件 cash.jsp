<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.marketing.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//根据EFCRM..TPREMONEYDETAIL.SERIAL_NO取缴款数据打印
//变量定义
Integer cust_id = new Integer(0);
Integer print_count = new Integer(0); //打印次数
String print_no = ""; //编号
String product_name = ""; //产品名称
String contract_sub_bh = "";  //合同编号
Integer jk_date = new Integer(0); //缴款日期
String cust_name = ""; //客户名称
String cust_type_name = ""; //客户类型名称
String card_type_name = ""; //客户证件名称
String card_id = ""; //证件号码
BigDecimal jk_money = new BigDecimal(0.0); //缴款金额
String jk_money_string = ""; //缴款金额字符
String currency_name = ""; //币种
String jk_type_name = ""; //缴款方式名称

//查询参数
Integer premoneydetail_serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("q_premoneydetail_serial_no")), new Integer(1));
CustomerLocal customer_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
List custList = null;
Map cust_map = null;

PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();
List listAll = null;
Map map = null;
vo.setSerial_no(premoneydetail_serial_no);
vo.setInput_man(input_operatorCode);
listAll = local.queryPreMoneyDetail(vo);
if(listAll.size()>0){
	map = (Map)listAll.get(0);

	jk_money = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")),new BigDecimal(0));
	jk_money_string = Utility.numToChinese(Utility.trimNull(jk_money));
	print_count = Utility.parseInt(Utility.trimNull(map.get("PRINT_COUNT")),new Integer(0));
	print_count = new Integer(print_count.intValue()+1);
	if(print_count.intValue()<10) {
		print_no = Utility.trimNull(map.get("PRINT_PREFIX")) + "0" + print_count;
	} else {
		print_no = Utility.trimNull(map.get("PRINT_PREFIX")) + print_count;
	}
    contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
    product_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
	jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
    jk_date = Utility.parseInt(Utility.trimNull(map.get("DZ_DATE")),new Integer(0));
	cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	custList = customer_local.listCustomerLoad(cust_vo);
	if(custList.size()>0) {
		cust_map = (Map)custList.get(0);
	    cust_type_name = Utility.trimNull(cust_map.get("CUST_TYPE_NAME"));
	    card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
	    card_id = Utility.trimNull(cust_map.get("CARD_ID"));
	}
	StringBuffer print_money= new StringBuffer("￥"+Format.formatMoneyPrint(jk_money.doubleValue(),0));
	char[] s_money = print_money.reverse().toString().toCharArray();

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>打印缴款凭证</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<!--media=print 这个属性可以在打印时有效-->
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
function printreturn(){
	//返回
	location = "../signed_spot.jsp"
}

//打印
function printOK(serial_no) {
	if(confirm('确认要打印吗？确认后打印次数将加1。')) {
		//记录打印次数
		utilityService.savePreMoneyDetailPrintCount(serial_no);
		window.print();
	}
}
</script>

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

</head>

<body>
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="printOK(<%=premoneydetail_serial_no%>)">直接打印(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button class="xpbutton3" accessKey=b name="btnReturn" onclick="javascript:history.back();">返回(<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;
		
			</td>
		</tr>
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0 " align="center">
<tr>
<td valign="top" align="center">
<%
//三联内容相同使用循环来打印
for(int i=0;i<3;i++) {
%>
    <table style="width:210mm;"border="0" cellpadding="4" cellspacing="0" align="center">
        <tr><td colspan="13">
            <div align="center">
                <span><font size="+2"><b><%=application.getAttribute("COMPANY_NAME")%>&nbsp;&nbsp;&nbsp;&nbsp;</b></font></span>
                <span></span>
            </div>
        </td></tr>
        <tr><td align="center" colspan="13"><font size="+2"><b><u><%=product_name%></u>&nbsp;-&nbsp;缴款凭证<b></font></td></tr>
	</table><br>
	<table style="width:210mm;"border="0" cellpadding="4" cellspacing="0" align="center">	
        <tr>
            <td colspan="2">合同编号：<%=contract_sub_bh%></td>
            <td colspan="4">日期：<%=Format.formatDateCn(jk_date.intValue())%></td>
            <td colspan="6" align="right">编号：<%=print_no%></td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td class="tdlefttop">委托人名称</td>
            <td class="tdtop" width="25%"><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
            <td class="tdtop">委托人类型</td>
            <td class="tdtop" colspan="9"><%=Utility.trimNull(cust_map.get("CUST_TYPE_NAME"))%></td>
            <td rowspan="5" width="6px">
<%
	if(i==0) {
		out.print("第一联<br>：受托人留存联");
	} else if (i==1) {
		out.print("第二联<br>：收款人留存联");
	} else if (i==2) {
		out.print("第三联<br>：委托人留存联");
	}
%>
			</td>
        </tr>
        <tr>
            <td class="tdleft">证件名称</td>
            <td class="tdrightbottom"><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
            <td class="tdrightbottom">证件号码</td>
            <td class="tdrightbottom" colspan="9"><%=Utility.trimNull(cust_map.get("CARD_ID"))%>&nbsp;</td>
        </tr>
        <tr>
            <td class="tdleft" rowspan="2">金额（大写）</td>
            <td class="tdrightbottom" rowspan="2"><%=jk_money_string%></td>
            <td class="tdrightbottom" rowspan="2">金额（小写）</td>
            <td class="tdrightbottom" align="center">亿</td>
            <td class="tdrightbottom" align="center">千</td>
            <td class="tdrightbottom" align="center">百</td>
            <td class="tdrightbottom" align="center">十</td>
            <td class="tdrightbottom" align="center">万</td>
            <td class="tdrightbottom" align="center">千</td>
            <td class="tdrightbottom" align="center">百</td>
            <td class="tdrightbottom" align="center">十</td>
            <td class="tdrightbottom" align="center">元</td>
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
            <td class="tdleft">币种类型</td>
            <td class="tdrightbottom">人民币</td>
            <td class="tdrightbottom">缴款形式</td>
            <td class="tdrightbottom" colspan="9"><%=jk_type_name%></td>
        </tr>
		<%if(user_id.intValue()==22){%>
		<tr>
			<td class="tdleft">备注</td>
			<td class="tdrightbottom" colspan="11">&nbsp;</td>
		</tr>
		<%}%>		
        <tr>
            <td align="center">经办人：</td>
            <td></td>
            <td colspan="10" align="right">信托资金已交付，兹由收款单位盖章予以确认</td>
            <td>&nbsp;</td>
        </tr>
    </table>

<%
	if(i<2) {
%>
    <br><hr class="Noprint">
	<table style="page-break-after:always">
		<tr><td >&nbsp;</td></tr>
	</table>
<%
	}
} //for
%>
</td>
</tr>
</table>

</form>
</body>
</html>
<%
}
local.remove();
customer_local.remove();
%>