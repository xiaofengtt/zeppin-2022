<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>

<%String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
overall_product_id = new Integer(Utility.parseInt(request.getParameter("product_id"), 0));
String pre_Code = request.getParameter("pre_Code");
Integer serial_no = new Integer(Utility.parseInt(request.getParameter("serial_no"), 0));

String cust_name = "";
String card_type_name = "";
String card_id = "";
String cust_no = "";

BigDecimal pre_money = null;
BigDecimal rg_money = null;
Integer cust_id = null;
Integer product_id = null;
String pre_code = "";
Integer pre_date = null;
Integer rg_date = null;
Integer link_man = null;
String pre_type_name = "";
String pre_status_name = "";
Integer end_date = null;
Integer valid_days = null;
Integer input_man = null;
Integer pre_num = null;
Integer rg_num = null;
String summary = "";

PreContractLocal preContract = EJBFactory.getPreContract();
boolean bSuccess = false;
PreContractVO vo = new PreContractVO();
vo.setSerial_no(serial_no);
List list = preContract.load(vo);

if (list!=null && list.size()>0) {
	Map map = (Map)list.get(0);
	pre_money = (BigDecimal)map.get("PRE_MONEY");
	rg_money = (BigDecimal)map.get("RG_MONEY");

	product_id = (Integer)map.get("PRODUCT_ID");
	pre_code = (String)map.get("PRE_CODE");
	pre_date = (Integer)map.get("PRE_DATE");
	rg_date = (Integer)map.get("RG_DATE");
    link_man = (Integer)map.get("LINK_MAN");
	pre_type_name = (String)map.get("PRE_TYPE_NAME");
	pre_status_name = (String)map.get("PRE_STATUS_NAME");
	end_date = (Integer)map.get("END_DATE");
	valid_days = (Integer)map.get("VALID_DAYS");
	input_man = (Integer)map.get("INPUT_MAN");
	pre_num = (Integer)map.get("PRE_NUM");
	rg_num = (Integer)map.get("RG_NUM");
	summary = (String)map.get("SUMMARY");

	cust_id = (Integer)map.get("CUST_ID");
	if (cust_id.intValue() != 0) {
		CustomerInfoLocal customerInfo = EJBFactory.getCustomerInfo();
		CustomerInfoVO _vo = new CustomerInfoVO();
		_vo.setCust_id(cust_id);
		_vo.setInput_man(input_operatorCode);
		List _list = customerInfo.load(_vo);
		if (_list!=null && _list.size()>0) {
			Map _map = (Map)_list.get(0);
			cust_no = (String)_map.get("CUST_NO");
			cust_name = (String)_map.get("CUST_NAME");
			card_type_name = (String)_map.get("CARD_TYPE_NAME");
			card_id = (String)_map.get("CARD_ID");
		}
		customerInfo.remove();
	}
}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel="stylesheet">

<base target="content">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>

<script language="javascript">
function op_return()
{
	location = 'presell_query.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&product_id=<%=overall_product_id%>&pre_Code=<%=pre_Code%>';
}
</script>
<BODY class="BODY">

<%@ include file="/includes/waiting.inc"%>
<form name="theform">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0 >
			<tr>
				<td colspan="4"><img border="0" src="/images/Feichuang6.jpg" width="50" height="50"><b>预约信息</b></td>
			</tr>
			<tr>
				<td colspan="4">
				<hr size="1">
				</td>
			</tr>
			<tr>
				<td align="right">客户编号:</td>
				<td><input readonly class="edline" name="cust_no" size="20" value="<%=cust_no%>"></td>
				<td align="right">客户名称:</td>
				<td><input readonly class="edline" name="cust_name" size="20" value="<%=cust_name%>"></td>
			</tr>
			<tr>
				<td align="right">证件类型:</td>
				<td><input readonly class="edline" name="card_type_name" size="20" value="<%=card_type_name%>"></td>
				<td align="right">证件号码:</td>
				<td><input readonly class="edline" name="card_id" size="20" value="<%=card_id%>"></td>
			</tr>
			<tr>
				<td align="right">产品名称:</td>
				<td><input readonly class="edline" name="product_name" size="48" value="<%=Argument.getProductName(product_id)%>"></td>
				<td align="right">预约编号:</td>
				<td><input readonly class="edline" name="pre_Code" size="20" value="<%=pre_code%>"></td>
			</tr>
			<tr>
				<td align="right">预约金额:</td>
				<td><input readonly class="edline" name="pre_money" size="20" value="<%=Utility.trimNull(Format.formatMoney(pre_money))%>"></td>
				<td align="right">联系人:</td>
				<td><input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(Argument.getIntrustOpName(link_man))%>"></td>
			</tr>
			<tr>
				<td align="right">预约方式:</td>
				<td><input readonly class="edline" name="Pre_type_name" size="20" value="<%=pre_type_name%>"></td>
				<td align="right">预约状态:</td>
				<td><input readonly class="edline" name="valid_days" size="20" value="<%=pre_status_name%>"></td>
			</tr>
			<tr>
				<td align="right">认购金额:</td>
				<td><input readonly class="edline" name="valid_days" size="20" value="<%=Utility.trimNull(Format.formatMoney(rg_money))%>"></td>
				<td align="right">资金到帐日期:</td>
				<td><input readonly class="edline" name="Pre_type_name" size="20" value="<%=Format.formatDateCn(rg_date)%>"></td>
			</tr>
			<tr>
				<td align="right">预约日期:</td>
				<td><input readonly class="edline" name="pre_type_name" size="20" value="<%=Format.formatDateCn(pre_date)%>"></td>
				<td align="right">到期日期:</td>
				<td><input readonly class="edline" name="valid_days" size="20" value="<%=Format.formatDateCn(end_date)%>"></td>
			</tr>
			<tr>
				<td align="right">有效天数:</td>
				<td><input readonly class="edline" name="Pre_type_name" size="20" value="<%=valid_days%>"></td>
				<td align="right">录入人员:</td>
				<td><input readonly class="edline" name="valid_days" size="20" value="<%=Utility.trimNull(Argument.getIntrustOpName(input_man))%>"></td>
			</tr>
			<tr>
				<td align="right">预约份数:</td>
				<td><input readonly class="edline" name="pre_num" size="20" value="<%=Utility.trimNull(pre_num)%>"></td>
				<td align="right">认购份数:</td>
				<td><input readonly class="edline" name="rg_num" size="20" value="<%=Utility.trimNull(rg_num)%>"></td>
			</tr>
			<tr>
				<td align="right">备注:</td>
				<td colspan="3"><input class="edline" readonly name="summary" size="58" value="<%=Utility.trimNull(summary)%>"></td>
			</tr>
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<!--<%if (cust_id != null) {%>
						<button type="button"  class="xpbutton4" accessKey=c id="btnCustomer" name="btnCustomer" onclick="javascript: location='../customer/customer_info.jsp?cust_id=<%=Utility.trimNull(cust_id)%>';">客户信息(<u>C</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>-->
						<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);op_return();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%preContract.remove();
%>