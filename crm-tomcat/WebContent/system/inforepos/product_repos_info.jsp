<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String class_type1 = Utility.trimNull(request.getParameter("class_type1"));


ProductInfoReposLocal product = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();

String period_unit = null;
boolean bSuccess = false;	

List listAll = null;
Map map = null;
	
	if(product_id.intValue() != 0){
		vo.setProduct_id(product_id);
		vo.setInput_man(input_operatorCode);
		listAll = product.productList(vo);
		map = (Map)listAll.get(0); 
		period_unit = Utility.trimNull(map.get("PERIOD_UNIT"));
	}

if(request.getMethod().equals("POST")){
	
	vo.setProduct_id(product_id);
	vo.setClass_type1(class_type1);
//	vo.setBzcs(Utility.trimNull(request.getParameter("bzcs")));
	vo.setAnnounce_url(Utility.trimNull(request.getParameter("announce_url")));
	vo.setDxjg(Utility.trimNull(request.getParameter("dxjg")));
	vo.setJjjl(Utility.trimNull(request.getParameter("jjjl")));
	vo.setKfq(Utility.trimNull(request.getParameter("kfq")));
	vo.setSyzh(Utility.trimNull(request.getParameter("syzh")));
	vo.setTzgw(Utility.trimNull(request.getParameter("tzgw")));
	vo.setKfr(Utility.trimNull(request.getParameter("kfr")));
	vo.setInput_man(input_operatorCode);
//	product.setProductInforepos(vo);
	bSuccess = true;
}

%>
<html>
<head>
<title>产品要素维护</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	syncDatePicker(document.theform.kfq_picker,document.theform.kfq);
   	syncDatePicker(document.theform.shsqtjq_picker,document.theform.shsqtjq);
	syncDatePicker(document.theform.kfr_picker,document.theform.kfr);
	return sl_check_update();
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post" action="product_repos_info.jsp" onsubmit="javascript:return validateForm(this);">
<INPUT type="hidden" id="product_id" name="product_id" value="<%=product_id%>">
<INPUT type="hidden" id="class_type1" name="class_type1" value="<%=class_type1%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>产品要素维护</b></font></td>
				</tr>
				<tr>
					<td>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<%if(class_type1 != null && !(class_type1.equals(""))){%>
			<%if(class_type1.equals("111301")){%> 
				<tr>
					<td align="right">信托计划名称：</td><!--产品编号-->
					<td colspan="3">
						<input type="text" style="width:373" readonly="readonly" class="edline" name="product_name" value="<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">资金规模(万元)：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="pre_money" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),null))%>">
					</td>
					<td align="right">成立时间：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="start_date" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%>">
					</td>
				</tr>
				<tr>
					<td align="right">预期收益率：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="exp_rate2" value="<%=Utility.stringToDouble(Utility.trimNull(map.get("EXP_RATE2"))).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP)%>%">
					</td>
					<td align="right">项目责任人：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="admin_manager" value="<%=Argument.getOpNameByOpCode(Utility.parseInt(Utility.trimNull(map.get("ADMIN_MANAGER")),new Integer(0)))%>">
					</td>
				</tr>
				<tr>
					<td align="right">期限：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="valid_period" value="<%=Utility.trimNull(map.get("VALID_PERIOD"))%>&nbsp;<%if(period_unit.equals("1")) out.print("天"); if(period_unit.equals("2")) out.print("月"); if(period_unit.equals("3")) out.print("年");%>">
					</td>
					<td align="right">收益分配时间：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="start_date" value="<%=Utility.trimNull(map.get("BEN_PERIOD"))%>&nbsp;">
					</td>
				</tr>
				<tr>
					<td align="right">资金运用方式：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="intrust_type" value="<%=Utility.trimNull(map.get("INTRUST_TYPE_NAME"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">备注：</td><!--发行方式-->	
					<td colspan="3">
						<textarea rows="4" name="summary" readonly="readonly" style="border-color: black;" onkeydown="javascript:nextKeyPress(this)" cols="60"><%=Utility.trimNull(map.get("SUMMARY"))%></textarea>
					</td>
				</tr>
			<%}else if(class_type1.equals("111302")){//阳光私募类%>
				<tr>
					<td align="right">信托计划名称：</td><!--产品编号-->
					<td colspan="3">
						<input type="text" style="width:373" readonly="readonly" class="edline" name="product_name" value="<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">成立时间：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="start_date" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%>">
					</td>
					<td align="right">保管银行：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="tg_bank_sub_name" value="<%=Utility.trimNull(map.get("TG_BANK_NAME"))%>&nbsp;<%=Utility.trimNull(map.get("TG_BANK_SUB_NAME"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">信托专户：</td><!--发行方式-->	
					<td colspan="3">
						<input type="text" style="width:373" readonly="readonly" class="edline" name="tg_acct_name" value="<%=Utility.trimNull(map.get("TG_ACCT_NAME"))%>">
					</td>
				</tr>
			<%}else if(class_type1.equals("111303")){//PE类%>
				<tr>
					<td align="right">信托计划名称：</td><!--产品编号-->
					<td colspan="3">
						<input type="text" style="width:373" readonly="readonly" class="edline" name="product_name" value="<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">成立时间：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="start_date" value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%>">
					</td>
					<td align="right">资金规模(万元)：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="pre_money" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),null))%>">
					</td>
				</tr>
				<tr>
					<td align="right">收益分配时间：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="ben_period" value="<%=Utility.trimNull(map.get("BEN_PERIOD"))%>">
					</td>
					<td align="right">期限：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" readonly="readonly" class="edline" name="period_unit" value="<%=Utility.trimNull(map.get("BEN_PERIOD"))%>&nbsp;<%if(period_unit.equals("1")) out.print("天"); if(period_unit.equals("2")) out.print("月"); if(period_unit.equals("3")) out.print("年");%>">
					</td>
				</tr>
				<tr>
					<td align="right">项目简介：</td><!--产品编号-->
					<td colspan="3">
						<input type="text" style="width:320" readonly="readonly" class="edline" name="product_info" value="<%=Utility.trimNull(map.get("PRODUCT_INFO"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">备注：</td><!--发行方式-->	
					<td colspan="3">
						<textarea rows="4" name="summary"  onkeydown="javascript:nextKeyPress(this)" cols="60"><%=Utility.trimNull(map.get("SUMMARY"))%></textarea>
					</td>
				</tr>
			<%}%>
		<%}%>
				<tr>
					<td align="right">信息披露地址：</td><!--产品编号-->
					<td colspan="3">
						<input type="text" style="width:373" name="announce_url" value="<%=Utility.trimNull(map.get("ANNOUNCE_URL"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">保障措施：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" name="bzcs" value="<%=Utility.trimNull(map.get("BZCS"))%>">
					</td>
					<td align="right">开放期：</td><!--发行方式-->	
					<td>
						<input TYPE="text" style="width:98" NAME="kfq_picker" class=selecttext value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("KFQ")),new Integer(0)))%>" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.kfq_picker,theform.kfq_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="hidden" NAME="kfq" value="">
					</td>
				</tr>
				<tr>
					<td align="right">赎回申请提交期：</td><!--产品编号-->
					<td>
						<input TYPE="text" style="width:98" NAME="shsqtjq_picker" class=selecttext value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("SHSQTJQ")),new Integer(0)))%>" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.shsqtjq_picker,theform.shsqtjq_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="hidden" NAME="shsqtjq" value="">
					</td>
					<td align="right">开放日：</td><!--发行方式-->	
					<td>
						<input TYPE="text" style="width:98" NAME="kfr_picker" class=selecttext value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("KFR")),new Integer(0)))%>" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.kfr_picker,theform.kfr_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
				        <input TYPE="hidden" NAME="kfr" value="">
					</td>
				</tr>
				<tr>
					<td align="right">代销机构：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" name="dxjg" value="<%=Utility.trimNull(map.get("DXJG"))%>">
					</td>
					<td align="right">投资顾问/受益人代表：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" name="tzgw" value="<%=Utility.trimNull(map.get("TZGW"))%>">
					</td>
				</tr>
				<tr>
					<td align="right">基金经理：</td><!--产品编号-->
					<td>
						<input type="text" style="width:120" name="jjjl" value="<%=Utility.trimNull(map.get("JJJL"))%>">
					</td>
					<td align="right">收益账户：</td><!--发行方式-->	
					<td>
						<input type="text" style="width:120" name="syzh" value="<%=Utility.trimNull(map.get("SYZH"))%>">
					</td>
				</tr>
		
  </table>	
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
			</tr>
</table>
</form>
</body>
</html>
