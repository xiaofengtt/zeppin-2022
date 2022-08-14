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
String sg_money2_string = "";
String jk_total_money_string = "";
//取值
if(serial_no.intValue()>0){
	apply_vo.setSerial_no(serial_no);
	apply_vo.setBook_code(input_bookCode);
	apply_list = apply_reach.listBySql(apply_vo);
	
	if(apply_list.size()>0){
		apply_map = (Map)apply_list.get(0);
		cust_id = Utility.parseInt(Utility.trimNull(apply_map.get("CUST_ID")),new Integer(0));
		sg_money2 = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_MONEY2")),null);
		if(sg_money2!=null)
			sg_money2 = sg_money2.setScale(2,2);
			sg_money2_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(sg_money2)));
		sg_fee_rate = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_FEE_RATE")),null);
		if(sg_fee_rate!=null)
			sg_fee_rate = sg_fee_rate.multiply(new BigDecimal(100)).setScale(2,2);
		sg_fee_money = Utility.parseDecimal(Utility.trimNull(apply_map.get("SG_FEE")),null);
		if(sg_fee_money!=null)
			sg_fee_money = sg_fee_money.setScale(2,2);
		jk_total_money = Utility.parseDecimal(Utility.trimNull(apply_map.get("JK_TOTAL_MONEY")),null);
		if(jk_total_money!=null)
			jk_total_money = jk_total_money.setScale(2,2);
		jk_total_money_string = Utility.numToChinese(Utility.trimNull(Argument.getMoneyType(jk_total_money)));
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
<TITLE>认购合同打印</TITLE>
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
</HEAD>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0 onload="javascript:print();close();">
<FROM name="theform" method="post">
	<TABLE height="90%" cellSpacing=0 cellPadding=0 width="710px" border=0  >
		<TR>
			<TD vAlign=top align=left>
				<TABLE cellSpacing=0 cellPadding=2 width="704px" height="955px" align=center border=0>
					<tr>
						<td align="center">
						</td>
					</tr>
					<tr>
						<td  style="font-size:16px;" height="120px">
						</td>
					</tr>		
					<tr>
						<td>
							<table  cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="160px">
								<tr>
										<td>
										<table>
											<tr><td style="font-size:16px" width="60px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px" width="80px"></td>
												<td  style="font-size:16px" width="140px"><%=Utility.trimNull(cust_map.get("LEGAL_MAN")) %></td>
												<td  style="font-size:16px" width="140px"></td>
												<td  style="font-size:16px"><%=Utility.trimNull(cust_map.get("CONTACT_MAN")) %></td>
											</tr>
										</table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td  style="font-size:16px" width="140px"><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME")) %></td><td  style="font-size:16px" width="140px"></td><td  style="font-size:16px"><%=Utility.trimNull(cust_map.get("CARD_ID")) %></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td style="font-size:15px"><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
											</tr>	
										</table>	
									</td>
									
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0><tr><td style="font-size:16px" height="16px"width="50px"></td><td  style="font-size:16px" width="140px"><%=Utility.trimNull(cust_map.get("POST_CODE"))%></td><td  style="font-size:16px" width="140px"></td><td  style="font-size:16px"><%=Utility.trimNull(cust_map.get("MOBILE"))%></td></tr></table>	
									</td>
								</tr>
								<tr>
									<td>
										<table>
											<tr>
												<td style="font-size:16px" height="16px"width="60px"></td><td style="font-size:16px"><%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
											</tr>	
										</table>	
									</td>
								</tr>
							</table>
						</td>
					</tr>		
					<tr>
						<td style="font-size:16px" height="18px">
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0 height="90px">
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="100px"></td>
												<td style="font-size:16px" height="16px"><%=Utility.trimNull(apply_map.get("PRODUCT_NAME"))%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="150px"></td>
												<td style="font-size:16px" width="120px"><%=sg_money2_string.substring(0,sg_money2_string.indexOf("元"))%></td>
												<td  width="160px"></td>
												<td style="font-size:16px"><%=Utility.trimNull(sg_money2)%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="90px"></td>
												<td style="font-size:16px" height="16px"  width="120px"><%=Utility.trimNull(sg_fee_rate)%></td>
												<td width="140px"></td>
												<td style="font-size:16px"><%if(sg_fee_money ==null){%><%}else{%><%=Utility.numToChinese(Utility.trimNull(sg_fee_money))%><%}%></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td>
										<table cellSpacing=0 cellPadding=0>
											<tr>
												<td width="130px"></td>
												<td style="font-size:16px"  width="120px"><%=jk_total_money_string.substring(0,jk_total_money_string.indexOf("元"))%></td>
												<td  width="140px"></td><td style="font-size:16px"><%=Utility.trimNull(jk_total_money)%></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>	
					</tr>	
					<tr>
						<td style="font-size:16px" height="18px">
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="110px">
								<tr>
									<td  style="font-size:16px" colspan="2" height="16px"></td>
								</tr>
								<tr>
									<td style="font-size:16px"width="60px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(apply_map.get("GAIN_ACCT"))%></td>	
								</tr>
								<tr>
									<td style="font-size:16px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(apply_map.get("BANK_ACCT"))%></td>	
								</tr>
								<tr>
									<td style="font-size:16px" height="16px"></td><td style="font-size:16px"><%=Utility.trimNull(apply_map.get("BANK_NAME"))%>&nbsp;<%=Utility.trimNull(apply_map.get("BANK_SUB_NAME"))%></td>	
								</tr>
							</table>	
						</td>
					</tr>	
					<tr>
						<td style="font-size:16px">
						</td>
					</tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0  height="70px">
								<tr>
									<td style="font-size:16px" colspan="2"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="font-size:16px">
							
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0  height="120px">
								<tr>
									<td style="font-size:16px" colspan="2"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td>
								</tr>
								<tr>
									<td style="font-size:16px"></td>
								</tr>								
							</table>
						</td>
					</tr>	
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="110px">
								<tr>
									<td style="font-size:16px;font-weight:bold"></td>
								</tr>						
							</table>
						</td>
					</tr>		
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0 height="80px">
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>	
								</tr>	
								<tr>
									<td></td>
									<td></td>
								</tr>
							</table>
						</td>
					</tr>			
					<tr><td align="right" style="font-size:18px"></td></tr>
					<tr>
						<td>
							<table cellSpacing=0 cellPadding=2 width="100%" align=center border=0>
								<tr>
									<td style="font-size:16px"></td><td style="font-size:16px"></td>	
								</tr>	
							</table>
						</td>
					</tr>																																																																										
				</table>
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