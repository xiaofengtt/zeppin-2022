<%@ page contentType="text/html; charset=GBK" import="enfo.crm.dao.*,enfo.crm.tools.*,java.util.*,enfo.crm.cash.*,java.math.BigDecimal,enfo.crm.cash.Format"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	//获取url中的值
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//客户ID
	Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//产品ID
	Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//子产品ID
	String contractBH = request.getParameter("contractBH");//合同编号
	Integer checkFlag = Utility.parseInt(request.getParameter("checkFlag"),new Integer(0));//审核标志1未审核2已审核
	String email =  Utility.trimNull(request.getParameter("email"));//用户邮箱
	//
	String qcustNo = Utility.trimNull(request.getParameter("qcustNo"));
	String qcustName = Utility.trimNull(request.getParameter("qcustName"));
	String qcardId = Utility.trimNull(request.getParameter("qcardId"));//证件号码
	Integer qcustType = Utility.parseInt(request.getParameter("qcustType"), new Integer(0));
	Integer qserviceMan = Utility.parseInt(request.getParameter("qserviceMan"),new Integer(0));
	String qcustTel = Utility.trimNull(request.getParameter("qcustTel"));
	String qaddress = Utility.trimNull(request.getParameter("qaddress"));
	String qUrl = "?custNo=" + qcustNo + "&custName=" + qcustName + "&custType=" + qcustType +  "&serviceMan=" + qserviceMan + "&custTel=" + qcustTel + "&cardId=" + qcardId + "&address=" + qaddress;
	//赎回记录信息
	Integer serialNo = new Integer(0);//赎回记录ID
	String contractSubBH = "";
	Integer transDate = new Integer(0);
	String custName = "";//客户名称
	BigDecimal AFTAmount = null;
	BigDecimal redeemAmount = null;
	BigDecimal redeemMoney = null;
	String bankAcct = "";
	Integer bankId = new Integer(0);
	String bankName = "";
	String productName = "";
	String checkFlagName = "";

	CashBean cash = new CashBean();
	CashVo vo = new CashVo();

	vo.setCustId(custId);
	vo.setInputMan(input_operatorCode);
	vo.setProductId(productId);
	vo.setSubProductId(subProductId);
	vo.setContractBH(contractBH);
	vo.setCheckFlag(checkFlag);

	IPageList pageList = null;
	Map map = null;
	pageList = cash.queryProductRedeemList(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
	Iterator it =  pageList.getRsList()!=null? pageList.getRsList().iterator(): new ArrayList().iterator();

	sUrl += "&custId=" + custId + "&productId=" + productId + "&subProductId=" + subProductId
		 +  "&contractBH=" + contractBH + "&checkFlag=" + checkFlag + "&email=" + email;
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>

		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script type="text/javascript">

			function refreshPage() {
				disableAllBtn(true);
				location.search = '?page=1&pagesize=' + document.theform.pagesize.value
								+ '&custId=' + document.theform.custId.value
								+ '&productId=' + document.theform.productId.value
								+ '&subProductId=' + document.theform.subProductId.value
								+ '&contractBH=' + document.theform.contractBH.value
								+ '&checkFlag=' + document.theform.checkFlag.value
								+ '&email=' + document.theform.email.value;
			}

			function gotoPrint(serialNo,productName) {
				location = 'redeem_report_print.jsp?serialNo=' + serialNo + "&productName=" + productName + "&custId=" +document.theform.custId.value;
			}

			function goback() {
				location = "cash_cust_choose.jsp" + document.theform.qUrl.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="redeem_record_list.jsp">
			<input type="hidden" id="custId" name="custId" value="<%=custId%>" />
			<input type="hidden" id="productId" name="productId" value="<%=productId%>" />
			<input type="hidden" id="subProductId" name="subProductId" value="<%=subProductId%>" />
			<input type="hidden" id="contractBH" name="contractBH" value="<%=contractBH%>" />
			<input type="hidden" id="checkFlag" name="checkFlag" value="<%=checkFlag%>" />
			<input type="hidden" id="email" name="email" value="<%=email%>" />

			<input type="hidden" name="qcustNo" value="<%=qcustNo %>"/>
			<input type="hidden" name="qcustName" value="<%=qcustName %>"/>
			<input type="hidden" name="qcustType" value="<%=qcustType %>"/>
			<input type="hidden" name="qserviceMan" value="<%=qserviceMan %>"/>
			<input type="hidden" name="qcustTel" value="<%=qcustTel %>"/>
			<input type="hidden" name="qcardId" value="<%=qcardId %>"/>
			<input type="hidden" id="qUrl" name="qUrl" value="<%=qUrl%>" />
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:goback();">返回 (<u>B</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							 	<!--  <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">-->
								合同编号
						</td>
						<td align="center">产品名称</td>
						<td align="center">客户名称</td>
						<td align="center">银行名称</td>
						<td align="center">银行帐号</td>
						<td align="center">申请赎回份额</td>
						<td align="center">审核状态</td>
						<td align="center">赎回日期</td>
						<td align="center">打印</td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							serialNo = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
							contractSubBH = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
							redeemAmount = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_AMOUNT")), new BigDecimal(0.00),2,"1");
							redeemMoney = Utility.parseDecimal(Utility.trimNull(map.get("REDEEM_MONEY")), new BigDecimal(0.00),2,"1");
							AFTAmount = Utility.parseDecimal(Utility.trimNull(map.get("AFT_AMOUNT")), new BigDecimal(0.00),2,"1");
							transDate = Utility.parseInt(Utility.trimNull(map.get("TRANS_DATE")),new Integer(0));
							custName = Utility.trimNull(map.get("CUST_NAME"));
							bankAcct = Utility.trimNull(map.get("BANK_ACCT"));
							bankId = Utility.parseInt(Utility.trimNull(map.get("BANK_ID")),new Integer(0));
							bankName = Utility.trimNull(map.get("BANK_NAME"));
							productName = Utility.trimNull(map.get("PRODUCT_NAME"));
							checkFlagName = Utility.trimNull(map.get("CHECK_FLAG_NAME"));
							
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<!--  <input type="checkbox" name="serialNo" value="<%=serialNo%>" class="flatcheckbox">-->
									</td>
									<td width="90%" align="center"><%=contractSubBH%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=productName%></td>
						<td align="center"><%=custName%></td>
						<td align="center"><%=bankName%></td>
						<td align="center"><%=bankAcct%></td>
						<td align="center"><%=Format.formatInt(redeemAmount.intValue())%></td>
						<td align="center"><%=checkFlagName%></td>
						<td align="center"><%=Format.formatDateCn(transDate)%></td>
						<td align="center">
           					<img border="0" src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16" onclick="javascript:gotoPrint('<%=serialNo%>','<%=productName %>');">
						</td>
					</tr>
					<%
							iCurrent++;
							iCount++;
						}
						for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){ 
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>