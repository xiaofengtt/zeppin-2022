<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递SERIAL_NO
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
//获得对象及结果集
ApplyreachLocal apply_reach = EJBFactory.getApplyreach();
ApplyreachVO apply_vo = new ApplyreachVO();
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
//页面辅助参数
input_bookCode = new Integer(1);//帐套暂时设置
List apply_list = null;
Map apply_map = new HashMap();
List cust_list = null;
Map cust_map = new HashMap();
Integer cust_id = new Integer(0);
BigDecimal sg_money2 = null;
BigDecimal sg_fee_rate = null;
BigDecimal sg_fee_money = null;
BigDecimal jk_total_money = null;
//取值
if(serial_no.intValue()>0){
	apply_vo.setSerial_no(serial_no);
	apply_vo.setBook_code(input_bookCode);
	apply_list = apply_reach.listBySql(apply_vo);
	
	if(apply_list.size()>0){
		apply_map = (Map)apply_list.get(0);
		cust_id = Utility.parseInt(Utility.trimNull(apply_map.get("CUST_ID")),new Integer(0));
		sg_money2 = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_MONEY2")),null);
		sg_fee_rate = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_FEE_RATE")),null);
		sg_fee_money = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_FEE")),null);
		jk_total_money = Utility.parseDecimal(Utility.trimNull(apply_map.get("JK_TOTAL_MONEY")),null);
		
		if(sg_money2!=null)
			sg_money2 = sg_money2.setScale(2,2);		
		if(sg_fee_rate!=null)
			sg_fee_rate = sg_fee_rate.multiply(new BigDecimal(100)).setScale(2,2);		
		if(sg_fee_money!=null)
			sg_fee_money = sg_fee_money.setScale(2,2);		
		if(jk_total_money!=null)
			jk_total_money = jk_total_money.setScale(2,2);
	}
	
	if(cust_id.intValue()>0){
		cust_vo.setCust_id(cust_id);
		cust_list = cust_list = customer.listCustomerLoad(cust_vo);
		
		if(cust_list.size()>0){
			cust_map = (Map)cust_list.get(0);	
		}
	}
}
%>

<HTML>
<HEAD>
<TITLE>申购合同打印</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>

<script language=javascript>
function doPrint(){	
	window.open("apply_purchase_print_do.jsp?serial_no=<%=serial_no%>");	
}
</script>
</HEAD>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<FROM name="theform" method="post" action="purchase_print.jsp">
	<TABLE height="90%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
					<TR>
						<TD align="center" colspan="10">
							<FONT size="6" face="黑体"><B><%=application.getAttribute("COMPANY_NAME")%>信托单位申购单</B></FONT>
						</TD>
					</TR>
					<TR >
						<TD colspan="10">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="黑体"><p class="Noprn">一、投资者基本信息:</p></FONT>
						</TD>
					</TR>
					<TR>
						<TD colspan="10">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT class="Noprn" size="3" face="宋体" >姓名/名称:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px"  size="73" value="<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>">	
						</TD>
					</TR>
					<TR>
						<TD colspan="10">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">法定代表人:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(cust_map.get("LEGAL_MAN")) %>">
							<FONT size="3" face="宋体">联系人:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(cust_map.get("CONTACT_MAN")) %>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">证件类型:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="29" value="<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME")) %>">
							<FONT size="3" face="宋体">证件号码:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="30" value="<%=Utility.trimNull(cust_map.get("CARD_ID")) %>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">通讯地址:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="74" value="<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">邮政编码:</FONT>
							<INPUT class="edline" readonly name="post_code" style="font-size:15px" size="29" value="<%=Utility.trimNull(cust_map.get("POST_CODE"))%>">
							<FONT size="3" face="宋体">移动电话:</FONT>
							<INPUT class="edline" readonly name=mobile"" style="font-size:15px" size="30" value="<%=Utility.trimNull(cust_map.get("MOBILE"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">E-MAIL:</FONT>
							<INPUT class="edline" readonly name="e_mail" style="font-size:15px" size="76.5" value="<%=Utility.trimNull(cust_map.get("E_MAIL"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>二、申购情况:</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">信托计划名称:</FONT>
							<INPUT class="edline" readonly name="product_name" style="font-size:15px" size="69" value="<%=Utility.trimNull(apply_map.get("PRODUCT_NAME"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">申购金额: 人民币
							<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="28"<%if(sg_money2!=null){%>value="<%=Utility.numToChinese(Utility.trimNull(sg_money2))%>"<%}else{%>value=""<%}%>>
							</FONT>
							<FONT size="3" face="宋体">&nbsp;&nbsp;(小写金额￥:</FONT>
							<INPUT class="edline" readonly name="rg_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(sg_money2)%>">
							<FONT size="3" face="宋体">)</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">申购费率:
							<INPUT class="edline" readonly name="fee_rate" style="font-size:15px" size="6" value="<%=Utility.trimNull(sg_fee_rate)%>">
							<FONT size="3" face="宋体">% &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申购费:</FONT>
							<INPUT class="edline" readonly name="fee_amount" style="font-size:15px" size="29" <%if(sg_fee_money!=null){%>value="<%=Utility.numToChinese(Utility.trimNull(sg_fee_money))%>"<%}else{%>value=""<%}%>>							
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">缴款总额: 人民币</FONT>
							<INPUT class="edline" readonly  name="to_money" style="font-size:15px" size="33" <%if(jk_total_money!=null){%>value="<%=Utility.numToChinese(Utility.trimNull(jk_total_money))%>"<%}else{%>value=""<%}%>>
							<FONT size="3" face="宋体">&nbsp;&nbsp;(小写金额￥:</FONT>
							<INPUT class="edline" readonly name="to_money" style="font-size:15px" size="10" value="<%=Utility.trimNull(jk_total_money)%>">
							<FONT size="3" face="宋体">)</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>三、信托利益分配账户:</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="3" face="宋体">投资者指定以下账户用于接受受托人分配的信托利益:</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">账户名:</FONT>
							<INPUT class="edline" readonly name="gain_acct" style="font-size:15px" size="50" value="<%=Utility.trimNull(apply_map.get("GAIN_ACCT"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">账 &nbsp;号:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(apply_map.get("BANK_ACCT"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<FONT size="3" face="宋体">开户行:</FONT>
							<INPUT class="edline" readonly name="" style="font-size:15px" size="50" value="<%=Utility.trimNull(apply_map.get("BANK_NAME"))%>&nbsp;<%=Utility.trimNull(apply_map.get("BANK_SUB_NAME"))%>">
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>四、信托利益分配方式的选择(适用时):</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="3" face="宋体">信托计划存续期间，投资者选择如下方式取得信托利益:</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<INPUT id="t1" type="radio" class="flatcheckbox" checked="checked"  name="t1" >
							<FONT size="3" face="宋体">&nbsp;现金分红方式:</FONT>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				
							<INPUT id="t1" type="radio" class="flatcheckbox"  readonly name="t1" >
							<FONT size="3" face="宋体">&nbsp;分红转投资方式:</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>五、申购不成功时的选择（适用时）</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="3" face="宋体">
							因投资者的申购资金未能在开放日前一个工作日到达受托人账户，或者申购文件未能在开放日前一个工作日送达受托人时或其他情况导致申购不成功时，投资者选择:</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<INPUT id="t2" class="flatcheckbox" type="radio" checked="checked" name="t2" >	
							<FONT size="3" face="宋体">&nbsp;全部申购资金退回投资者</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<INPUT id="t2" class="flatcheckbox" type="radio"  readonly name="t2" >
							<FONT size="3" face="宋体">&nbsp;全部申购资金直接作为下一个开放日的申购资金</FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>特别提示：委托人签署本认/申购单即代表已阅读并签署了所有信托文件。委托人在本认/申购单上签字即代表对本认/申购单填写信息真实性和准确性的确认。
							如需办理退款手续，委托人签署本认购单则代表委托人同意将认购资金退还至上述预留信托利益分配账户。
							本信托单位认/申购单仅作为客户认购凭据，实际认购金额以受托人<%=application.getAttribute("COMPANY_NAME")%>出具的信托单位确认单为准。</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;						
							<FONT size="4" face="宋体"><B>委托人盖章:</B></FONT>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<FONT size="4" face="宋体"><B>受托人盖章:</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
							<INPUT class="edline" readonly name="" size="30">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		
							<INPUT class="edline" readonly name="" size="30">
						</TD>
					</TR>
					<TR>
						<TD align="right">
								
							<FONT size="3" face="宋体"><B>日期:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;日</B></FONT>
						</TD>
					</TR>
					<TR>
						<TD>
							
						</TD>
					</TR>
					<TR class="noprint" id="printtable" style="displag:">
						<TD align="right">
							<table border="0" width="100%">
								<tr>
									<td align="right">													
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrint();">打印(<u>P</u>)</button>&nbsp;&nbsp;     
										<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</TD>
					</TR>
				</TABLE>				
			</TD>
		</TR>
	</TABLE>
</FROM>
</BODY>
</HTML>
<%
apply_reach.remove();
customer.remove();
%>