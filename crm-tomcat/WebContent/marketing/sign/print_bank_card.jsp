<%@ page contentType="text/html; charset=GBK" import="java.io.File,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
File n_file = new File("D:\\CARD");
if(!n_file.exists())
	 n_file.mkdirs();
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
		cust_name = Utility.trimNull(cust_map.get("CUST_NAME"));
	}
}

String file_name1 = "D:\\CARD\\CardImage_"+premoneydetail_serial_no+"_1.jpg";
String file_name2 = "D:\\CARD\\CardImage_"+premoneydetail_serial_no+"_2.jpg";
String file_name3 = "D:\\CARD\\CardImage_"+premoneydetail_serial_no+"_3.jpg";
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>缴款打印 </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<style media="print"> 
.noPrint{
	display:none;
}
</style> 
<style>
.tablePrint{
	border-collapse:collapse;
	border:solid #000000;
	border-width:1px 1px 1px 1px;
} 
.tablePrint td{border:solid #000000;border-width:1px 1px 0px 0px;padding:1px 2px 1px 2px;font-size:15px;}

.tableNoPrint{
	border-collapse:collapse;
	border:solid #000000;
	border-width:0px 0px 0px 0px;
} 
.tableNoPrint td{border:0;border-width:0px 0px 0px 0px;}
</style>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
//扫描识别函数
function FnTcScanOcr(card_flag){
    try {
        var ocxObj = document.getElementById('TcCardOcr');
		var cad_image = "D:\\CARD\\CardImage_<%=premoneydetail_serial_no%>_"+ card_flag +".jpg";
        ocxObj.TcScanOcr( 'D:\\CARD\\BackValue.txt','D:\\CARD\\ScanSource.jpg',cad_image,'D:\\CARD\\HeadImage.jpg','D:\\CARD\\Result.txt');
        var rt = ocxObj.TcGetBackValue();
        var rm = ocxObj.TcGetBackMessage();
        if( rt == 0 ) {
            var rr = ocxObj.TcGetResultFile();
        } else {
            alert( "扫描失败，扫描仪返回值:" + rt + ";返回信息:" + rm );
        }
    } catch (e) {
        alert("证件扫描失败，请检查扫描仪驱动或IE浏览器安全设置！");
        return false;
    }
    location.reload();
}
</script>
</head>
<BODY class="BODY body-nox">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" onsubmit="javascript:return validateForm(this);" >
<input type="hidden" id="bSuccess" value=""/>
<OBJECT
  name=TcCardOcr
  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
  codebase="/includes/card/TcIdCard5.ocx"
  width=0
  height=0
  align=middle
  hspace=0
  vspace=0></OBJECT>
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<BR/>
<BR/>
<table border="0" width="100%" align="center" class="product-list">
<tr>
	<td align="right" width="100%">	
		<table border="0" width="90%" align="right" class="noprint">
			<tr>
				<td width="100%" align="right" colspan="4">
					<button class="xpbutton4" onclick="javascript:FnTcScanOcr(1);">读取银行卡1</button>
					&nbsp;&nbsp;&nbsp; 
					<button class="xpbutton4" onclick="javascript:FnTcScanOcr(2);">读取银行卡2</button>
					&nbsp;&nbsp;&nbsp; 
					<button class="xpbutton4" onclick="javascript:FnTcScanOcr(3);">读取银行卡3</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
					&nbsp;&nbsp;&nbsp; 
					<button class="xpbutton3" accessKey=p id="btnSave" name="btnSave" onclick="javascript:window.print();">打印(<u>P</u>)</button>
					&nbsp;&nbsp;
					<!--返回-->
		            <button class="xpbutton3" accessKey=b id="btnRepeat" name="btnCancel" onclick="javascript:window.close();">关闭</button>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<TABLE height="90%" cellSpacing=0 cellPadding=2 align="center" border="0" style="width:210mm;height:297mm;">
			<tr><td colspan="4" align=center><font size="4"><b><%=product_name%>-缴款证明文件</b></font></td>
			</tr>
			<tr>
				<td align="center" width="30%">&nbsp;</td>
				<td align="center" width="40%"><%=Format.formatDateCn(jk_date.intValue())%></td>
				<td colspan="2" width="30%" align="center">合同编号:<%=contract_sub_bh %></td>
			</tr>
			<TR>
				<TD vAlign=top align=center width="100%" colspan="4">
				<TABLE height="100%" cellSpacing=1 cellPadding=4 width="100%" align="center" border=1 class="tablePrint">
					<tr>
						<td colspan="2" width="13%" align=center>委托人名称</td>
						<td colspan="1" width="37%" >&nbsp;<%=cust_name%></td>
						<td colspan="1" width="10%" align=center>认购金额</td>
						<td colspan="1" width="40%" >&nbsp;<%=Format.formatMoney(jk_money) %></td>
						
					</tr>
					<tr>
						<td colspan="2" align=center>证件类型</td>
						<td colspan="1">&nbsp;<%=card_type_name %></td>
						<td colspan="1" align=center>证件号码</td>
						<td colspan="1">&nbsp;<%=card_id %></td>
					</tr>
					<tr>
						<td colspan="1" rowspan="3" width="5%">
							<TABLE width="100%" border="0" class="tableNoPrint"><tr><td align=center> 缴</td></tr>
									<tr><TD align=center> 款 </TD></tr>
									<tr><TD align=center> 银 </TD></tr>
									<tr><TD align=center> 行 </TD></tr>
									<tr><TD align=center> 卡 </TD></tr>
									<tr><TD align=center> 复 </TD></tr>
									<tr><TD align=center> 印 </TD></tr>
									<tr><TD align=center> 件 </TD></tr>
							</TABLE>
						</td>
						<td colspan="4" style="padding-left:10mm">
							<table class="tableNoPrint" cellPadding="4" cellSpacing="10">
								<tr>
									<td height="250px;" >
										<img src="<%=file_name1%>" style="height:80mm;" onerror="this.style.display='none'">
									</td>
								</tr>
								<tr>
									<td height="250px;">
										<img src="<%=file_name2%>" style="height:80mm;" onerror="this.style.display='none'">
									</td>
								</tr>
								<tr>
									<td height="250px;">
										<img src="<%=file_name3%>" style="height:80mm;" onerror="this.style.display='none'">
									</td>
								</tr>
							</table>		
						</td>
					</tr>
					
				</TABLE>
				</TD>
			</TR>
			<tr><TD colspan="4">经办人：</TD>
			</tr>
		</TABLE>
	</td>
</tr>
<tr>
	<td>		
		<table border="0" width="100%" class="noprint">
			<tr>
				<td align="right">
					
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
</form>
<%//@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
customer_local.remove();
 %>
