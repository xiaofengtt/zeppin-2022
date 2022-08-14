<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//页面传递参数
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer product_id = new Integer(0);
String contract_bh = "";
//辅助变量
//input_bookCode = new Integer(1);//帐套暂时设置
String product_name = "";
Integer cust_id = new Integer(0);
boolean bSuccess = false;
//获得对象
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO md_vo = new MoneyDetailVO();
ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();
Map md_map = null;

//查询缴款明细信息
if(serial_no.intValue()>0){
	md_vo = new MoneyDetailVO();
	md_vo.setSerial_no(serial_no);
	List md_list = moneyDetailLocal.load(md_vo);
	if(md_list.size()>0){
			md_map = (Map)md_list.get(0);

			product_id = Utility.parseInt(Utility.trimNull(md_map.get("PRODUCT_ID")),new Integer(0));
			contract_bh = Utility.trimNull(md_map.get("CONTRACT_BH"));
			cust_id = Utility.parseInt(Utility.trimNull(md_map.get("CUST_ID")),new Integer(0));
	}
	String showline = "readonly class='edline' ";	
}
//查询产品信息
if(product_id.intValue()>0){
	p_vo = new ProductVO();
	p_vo.setProduct_id(product_id);	
	List product_list = product.load(p_vo);
	
	if(product_list.size()>0){
		Map product_map = (Map)product_list.get(0);
		product_name = Utility.trimNull(product_map.get("PRODUCT_NAME"));
		
	}
}
//查询客户证件
CustomerVO cust_vo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();
cust_vo.setCust_id(cust_id);
List cust_list = cust.listProcAll(cust_vo);
Map cust_map = (Map)cust_list.get(0);
String card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
String card_id = Utility.trimNull(cust_map.get("CARD_ID"));
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
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style media="print">
.noprint { display: none }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
var n = 1;

//扫描识别函数
function FnTcScanOcr(){
    var textPath='D:\\Result.txt';
    try {
        var ocxObj = document.getElementById('TcCardOcr');
        ocxObj.TcScanOcr( 'D:\\BackValue.txt','D:\\ScanSource.jpg','D:\\CardImage.jpg','D:\\HeadImage.jpg',textPath);
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
    //location = "card_discern.jsp";       
}

</script>
</head>
<BODY class="BODY body-nox" onload="FnTcScanOcr();print();close();">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="pay_action_print.jsp" >
<BR/>
<BR/>

<TABLE height="90%" cellSpacing=0 cellPadding=0 width="90%" border=0 align=center>
	<tr><td colspan="4" align=center><input readonly class="edline" name="contract_bh" size="40" value="<%=product_name%>"><font size="5">集合资金信托计划缴款证明文件</font></td>
	</tr>
	<tr><td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</td>
		<td colspan="3" align="left">合同编号:<%=contract_bh %></td>
	</tr>
	<TR>
		<TD vAlign=top align=center width="100%" colspan="2">
		<TABLE height="100%" cellSpacing=1 cellPadding=4 width="100%" border=1 align=center>
			<tr>
				<td colspan="1" width="10%" align=center>委托人名称</td>
				<td colspan="1" width="40%" ><%=md_map.get("CUST_NAME") %></td>
				<td colspan="1" width="10%"  align=center>认购金额</td>
				<td colspan="1" width="40%" ><%=md_map.get("TO_MONEY") %></td>
				
			</tr>
			<tr>
				<td colspan="1" align=center>证件类型</td>
				<td colspan="1"><%=card_type_name %></td>
				<td colspan="1" align=center>证件号码</td>
				<td colspan="1"><%=card_id %></td>
				
			</tr>
			<tr>
				<td colspan="1">
					<TABLE width="100%"><tr><td align=center> 缴</td></tr>
							<tr><TD align=center> 款 </TD></tr>
							<tr><TD align=center> 银 </TD></tr>
							<tr><TD align=center> 行 </TD></tr>
							<tr><TD align=center> 卡 </TD></tr>
							<tr><TD align=center> 复 </TD></tr>
							<tr><TD align=center> 印 </TD></tr>
							<tr><TD align=center> 件 </TD></tr>
					</TABLE>
				<td colspan="3" rowspan="10">&nbsp;<OBJECT
				  name=TcCardOcr
				  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
				  codebase="/includes/card/TcIdCard5.ocx"
				  width=0
				  height=0
				  align=middle
				  hspace=0
				  vspace=0
				></OBJECT>
				</td>
			</tr>
			
		</TABLE>
		</TD>
	</TR>
	<tr><TD>经办人：</TD>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>
<%
moneyDetailLocal.remove();
product.remove();
cust.remove();
 %>
