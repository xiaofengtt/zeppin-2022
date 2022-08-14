<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int bzjFlag = Argument.getSyscontrolValue("BZJCLFS");//保证金开关 1有,2没有
ContractLocal contract = EJBFactory.getContract();
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
BenifitorLocal benifitor = EJBFactory.getBenifitor();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);

String service=request.getParameter("service");

ContractVO conVo = new ContractVO();
conVo.setSerial_no(serial_no);
Map conMap = (Map)contract.load(conVo).get(0);

String period_unit="月";
ProductLocal product=EJBFactory.getProduct();
ProductVO pvo = new ProductVO();
pvo.setProduct_id((Integer)conMap.get("PRODUCT_ID"));
Map pMap = (Map)product.load(pvo).get(0);

Integer subProductId = (Integer)conMap.get("SUB_PRODUCT_ID");
if (subProductId!=null && subProductId.intValue()>0) {
	pvo.setSub_product_id(subProductId);
	pMap = (Map)product.listSubProduct(pvo).get(0);
	period_unit=Argument.getProductUnitName((Integer)pMap.get("PERIOD_UNIT"));
}else{
	period_unit=Argument.getProductUnitName((Integer)pMap.get("PERIOD_UNIT"));
}	
		
BenifitorVO bvo = new BenifitorVO();
bvo.setContract_bh((String)conMap.get("CONTRACT_BH"));
bvo.setProduct_id((Integer)conMap.get("PRODUCT_ID"));
List bList = benifitor.listBenifitorbyht(bvo);

CustomerInfoVO cusVo = new CustomerInfoVO();
cusVo.setCust_id((Integer)conMap.get("CUST_ID"));
cusVo.setInput_man(input_operatorCode);
Map cusMap = (Map)customer.load(cusVo).get(0);

String strproduct_id=Utility.trimNull(request.getParameter("product_id"));

/*
LogListLocal log = EJBFactory.getLogList();	//用户查看客户认购信息时保存LOG信息20050114
log.setBusi_flag(new Integer(8888));
log.setBusi_name("查看客户认购信息");
log.setSummary("查看客户认购信息，客户编号："+ contract.getCust_no()+"，合同编号："+contract.getContract_bh());
log.append(input_operatorCode);
log.remove();*/

String sQuery=strproduct_id+"$ $ $";
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script language=javascript>
function validateForm(form) {
	if(!sl_checkChoice(form.service_man, "客户经理"))	return false;
	return sl_check_update();
}
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="purchase_info_check.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=Utility.trimNull(conMap.get("SERIAL_NO"))%>">
<input type="hidden" name="sQuery" value="<%=sQuery%>">
<input type="hidden" name="product_id" value="<%=Utility.trimNull(request.getParameter("product_id"))%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
			<tr>
				<td><img border="0" src="/images/ico_area.gif" width="32" height="32"><b>认购信息</b></td>
			</tr>
		</TABLE>
	
		<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
<%if(input_operator.hasFunc("20204", 106)||input_operator.hasFunc("20201", 106)) { %>						
			<tr>
				<td align="right">客户编号:</td>
				<td><INPUT readonly class="edline" name="cust_no" size="20" value="<%=conMap.get("CUST_NO")%>"></td>
				<td align="right">客户名称:</td>
				<td><INPUT readonly class="edline" name="cust_name" size="20" value="<%=Utility.trimNull(conMap.get("CUST_NAME"))%>"></td>
			</tr>
			<tr>
				<td align="right">手机:</td>
				<td><INPUT readonly class="edline" name="mobile" size="20" value="<%=Utility.trimNull(cusMap.get("MOBILE"))%>"></td>
				<td align="right">客户类别:</td>
				<td><input readonly class="edline" name="cust_type" size="20" value="<%=Utility.trimNull(cusMap.get("CUST_TYPE_NAME"))%>"></td>
			</tr>
<%	if (((Integer)cusMap.get("CUST_TYPE")).intValue()==2) {%>			
			<tr>
				<td align="right">法人姓名:</td>
				<td><input readonly name="legal_man" size="20" value="<%=Utility.trimNull(cusMap.get("LEGAL_MAN"))%>" class="edline"></td>
				<td align="right">联系地址:</td>
				<td><input readonly name="legal_address" size="60" value="<%=Utility.trimNull(cusMap.get("LEGAL_ADDRESS"))%>" class="edline"></td>
			</tr>
<%	}%>			
			<tr>
				<td align="right">证件类型:</td>
				<td><input readonly class="edline" name="card_type" size="20" value="<%=Utility.trimNull(Argument.getDictContent((String)cusMap.get("CARD_TYPE")))%>"></td>
				<td align="right">证件号码:</td>
				<td><input readonly class="edline" name="card_id" size="20" value="<%=Utility.trimNull(cusMap.get("CARD_ID"))%>"></td>
			</tr>
			<tr>
				<td align="right">单位电话:</td>
				<td><input readonly class="edline" name="o_tel" size="20" value="<%=Utility.trimNull(cusMap.get("O_TEL"))%>"></td>
				<td align="right">家庭电话:</td>
				<td><input readonly class="edline" name="h_tel" size="20" value="<%=Utility.trimNull(cusMap.get("H_TEL"))%>"></td>
			</tr>
			<tr>
				<td align="right">邮寄地址:</td>
				<td><input readonly class="edline" name="post_address" size="50" value="<%=Utility.trimNull(cusMap.get("POST_ADDRESS"))%>"></td>
				<td align="right">邮政编码:</td>
				<td><input readonly class="edline" name="post_code" size="20" maxlength="6" value="<%=Utility.trimNull(cusMap.get("POST_CODE"))%>"></td>
			</tr>
			<tr>
				<td align="right">备用地址:</td>
				<td><input readonly class="edline" name="post_address2" size="50" value="<%=Utility.trimNull(cusMap.get("POST_ADDRESS2"))%>"></td>
				<td align="right">邮政编码:</td>
				<td><input readonly class="edline" name="post_code2" size="20" maxlength="6" value="<%=Utility.trimNull(cusMap.get("POST_CODE2"))%>"></td>
			</tr>
<%}%>			
			<tr>
			<td colspan="4"><hr size="1"></td>
			</tr>
			<tr>
				<td align="right">合同编号:</td>
				<td><INPUT readonly class="edline" name="contract_bh0" size="20" value="<%=conMap.get("CONTRACT_SUB_BH")%>"></td>
				<td align="right"></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">产品名称:</td>
				<td><input readonly class="edline" name="product_name" size="30" readonly value="<%=conMap.get("PRODUCT_NAME")%>"></td>
				<td align="right">预约编号:</td>
				<td><input readonly class="edline" name="pre_code" size="20" value="<%=Utility.trimNull(conMap.get("PRE_CODE"))%>"></td>
			</tr>
			<tr>
				<td align="right">预约金额:</td>
				<td><input readonly class="edline" name="pre_money" size="20" value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)conMap.get("PRE_MONEY")))%>"></td>
				<td align="right">认购金额:</td>
				<td><input readonly class="edline" name="rg_money" size="20" value="<%=Utility.trimNull(Format.formatMoney((BigDecimal)conMap.get("RG_MONEY")))%>"> </td>
			</tr>
<%if (bzjFlag == 1) {%>
			<tr>	
				<td align="right">是否为保证金:</td>
				<td colspan="3">
					是:<input type="radio" name="bzj_flag" value="1" <%=Utility.trimNull(conMap.get("BZJ_FLAG"),"2").equals("1") ? "checked" : "disabled"%> class="flatcheckbox">&nbsp;&nbsp;
					否:<input type="radio" name="bzj_flag" value="2" <%=Utility.trimNull(conMap.get("BZJ_FLAG"),"2").equals("2") ? "checked" : "disabled"%> class="flatcheckbox">
				</td>
			</tr>
<%}%>
			<tr>
				<td colspan="3"></td>
				<td><span id="rg_money_cn" class="span">(<%=Utility.numToChinese(conMap.get("RG_MONEY").toString())%>)</span></td>
			</tr>
			<tr>	
				<td align="right">缴款方式:</td>
				<td><input readonly class="edline" name="jk_type_name" size="20" value="<%=Utility.trimNull(conMap.get("JK_TYPE_NAME"))%>">
				</td>
				<td align="right">合同期限:</td>
				<td><input readonly class="edline" type="text" name="valid_period" size="20" value="<%=Utility.trimNull(conMap.get("VALID_PERIOD"))%>"><%=Utility.trimNull(period_unit)%></td>
				
			</tr>
			<tr>	
				<td align="right">客户经理:</td>
				<td>
				<input readonly class="edline" name="service_man" size="20" value="<%=Utility.trimNull(Argument.getIntrustOpName((Integer)conMap.get("SERVICE_MAN")))%>"></td>
				<td align="right">合同联系人员:</td>
				<td>
				<input readonly class="edline" name="link_man" size="20" value="<%=Utility.trimNull(Argument.getIntrustOpName((Integer)conMap.get("LINK_MAN")))%>"></td>
				</tr>
				<tr>	
				<td align="right">开始日期:</td>
				<td>
				<input readonly class="edline" name="start_date" size="20" value="<%=Utility.trimNull(Format.formatDateCn((Integer)conMap.get("START_DATE")))%>"></td>
				<td align="right">结束日期:</td>
				<td>
					<input readonly class="edline" name="end_date" size="20" 
						value="<%=(conMap.get("END_DATE")!=null && ((Integer)conMap.get("END_DATE")).intValue()>=20990101)?"无固定期限":Format.formatDateCn((Integer)conMap.get("END_DATE"))%>">
				</td>
				</tr>
			<tr>
				<td align="right">签署日期:</td>
				<td><input readonly class="edline" name="rg_money" size="20" value="<%=Utility.trimNull(Format.formatDateCn((Integer)conMap.get("QS_DATE")))%>"></td>
				<td align="right">备注:</td>
				<td><input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(conMap.get("SUMMARY"))%>" size="40"></td>
			</tr>
			<%if (conMap.get("CITY_NAME")!=null) {%>
			<tr>
				<td align="right">合同推介地:</td>
				<td><input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(conMap.get("CITY_NAME"))%>" size="50"></td>
				</tr> 
			<%}%>
			
<!--*************************************附件添加 2010-03-08*************************************************-->
		<%
		AttachmentLocal attachment = EJBFactory.getAttachment();
		if (serial_no!=null) {
			AttachmentVO avo = new AttachmentVO();
			avo.setDf_talbe_id(new Integer(5));
          	avo.setDf_serial_no(serial_no);
			List aList = attachment.load(avo);
		%>
		<tr id="reader1" style="display:">
          	<td align="right"><%if (aList.size() > 0){%>附件:<%}%></td>
            <td colspan="3">
			<%
           for (int i=0; i<aList.size(); i++) {
				Map aMap = (Map)aList.get(i);
            %>
            	<div id="divattach<%=aMap.get("ATTACHMENTID")%>" style="display:">
            	<a title="查看附件" href="/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll((String)aMap.get("SAVE_NAME"),"\\","/")%>&name=<%=aMap.get("ORIGIN_NAME")%>" ><%=aMap.get("ORIGIN_NAME")%></a>
            	</div><br>
			<%}	%>
            </td>	
         </tr> 		
		<%
		}
		attachment.remove();
		%>		
<!--*****************************************************************************************************************-->
			
			<tr>
			<td colspan="4">
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="25">受益人名称</td>
						<td align="center" height="25">合同编号</td>
						<td align="center" height="25" sort="num">原受益份额</td>
						<TD align="center" height="25" sort="num">现受益份额</TD>
						<td align="center" height="25">受益级别</td>
						
						<td align="center" height="25">受益银行</td>
						<td align="center" height="25">银行账号</td>
						<td align="center" height="25">受益状态</td>
					</tr>
<%
BigDecimal ben_amount = new BigDecimal(0.000);
BigDecimal to_amount = new BigDecimal(0.000);
int iCount = 0;
int iCurrent = 0;

for (int i=0; i<bList.size(); i++) {
	Map bMap = (Map)bList.get(i);
	BigDecimal benAmount = (BigDecimal)bMap.get("BEN_AMOUNT");
	if (benAmount != null)
		ben_amount = ben_amount.add(benAmount);
	BigDecimal remainAmount = (BigDecimal)bMap.get("REMAIN_AMOUNT");
	if (remainAmount != null)
		to_amount = to_amount.add(remainAmount);
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25"><%=Utility.trimNull(bMap.get("CUST_NAME"))%></td>
						<td align="center" height="25"><%=Utility.trimNull(bMap.get("CONTRACT_SUB_BH"))%> - <%=Utility.trimNull(bMap.get("LIST_ID"))%></td>
						<td align="right" height="25"><%=Format.formatMoney(remainAmount)%></td>
						<TD align="right" height="25"><%=Format.formatMoney(benAmount)%></TD>
						<td align="center" height="25"><%=Utility.trimNull(bMap.get("PROV_LEVEL_NAME"))%></td>
						
						<td align="left" height="25"><%=Utility.trimNull(bMap.get("BANK_NAME"))%></td>
						<td align="left" height="25"><%=Format.formatBankNo(Utility.trimNull(bMap.get("BANK_ACCT")))%></td>
						<td align="center" height="25"><%=Utility.trimNull(bMap.get("BEN_STATUS_NAME"))%></td>
					</tr>
<%
	iCurrent++;
	iCount++;
}%>

					<tr class="trbottom">
						<td class="tdh" align="center" height="25"><b>合计 <%=bList.size()%> 项</b></td>
						<td align="center" height="25">-</td>
						<td align="right" height="25"><%=Format.formatMoney(to_amount)%></td>
						<TD align="right" height="25"><%=Format.formatMoney(ben_amount)%></TD>
						<td align="center" height="25">-</td>
						
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
					</tr>
				</table>
			</td>
			</tr>
			<tr>
				<td colspan="4">
				<table border="0" width="100%">
					<tr>
						<td align="right">
						<button type="button"  class="xpbutton3" accessKey=c id="btnBack" name="btnBack" onclick="javascript:window.returnValue=null;window.close();">关闭(<u>C</u>)</button>
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
<%contract.remove();
customer.remove();
benifitor.remove();
product.remove();
%>
