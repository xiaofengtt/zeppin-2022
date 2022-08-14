<%@ page contentType="text/html; charset=GBK" import=" enfo.crm.dao.*,java.util.*,enfo.crm.cash.*,enfo.crm.tools.*,enfo.crm.cash.Argument"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	//获取url中的值
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//客户ID
	Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//产品ID
	Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//子产品ID
	
	String custNo = Utility.trimNull(request.getParameter("custNo"));
	String custName = Utility.trimNull(request.getParameter("custName"));
	String cardId = Utility.trimNull(request.getParameter("cardId"));//证件号码
	Integer custType = Utility.parseInt(request.getParameter("custType"), new Integer(0));
	Integer serviceMan = Utility.parseInt(request.getParameter("serviceMan"),new Integer(0));
	String custTel = Utility.trimNull(request.getParameter("custTel"));
	String address = Utility.trimNull(request.getParameter("address"));

	String custTypeName = "";
	int encpypt = 0;
	Integer cardType = new Integer(0);
	String cardTypeName = "";
	String servicManName = "";

	CashVo vo = new CashVo();
	CashBean cash = new CashBean();
	vo.setAddress(address);
	vo.setCardId(cardId);
	vo.setCustId(custId);
	vo.setCustName(custName);
	vo.setCustNo(custNo);
	vo.setCustTel(custTel);
	vo.setCustType(custType);
	vo.setInputMan(input_operatorCode);
	vo.setProductId(productId);
	vo.setServicemen(serviceMan);
	vo.setSubProductId(subProductId);
	IPageList pageList = null;
	Map map = null;
	pageList = cash.queryCust(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
	Iterator it =  pageList.getRsList()!=null? pageList.getRsList().iterator(): new ArrayList().iterator();
	
	String qUrl = "&qcustNo=" + custNo + "&qcustName=" + custName + "&qcustType=" + custType +  "&qserviceMan=" + serviceMan + "&qcustTel=" + custTel + "&qcardId=" + cardId + "&qaddress=" + address;
	sUrl += "&custNo=" + custNo + "&custName=" + custName + "&custType=" + custType +  "&serviceMan=" + serviceMan + "&custTel=" + custTel + "&cardId=" + cardId + "&address=" + address;
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
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/My97DatePicker/WdatePicker.js"></script>
	
		<script type="text/javascript">
			/*启动加载*/
			window.onload = function(){
					initQueryCondition();
			};

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'cash_cust_choose.jsp?page=1&pagesize=' + document.theform.pagesize.value
								+ '&custNo=' + document.theform.custNo.value
								+ '&custName=' + document.theform.custName.value
								+ '&custType=' + document.theform.custType.value
								+ '&serviceMan=' + document.theform.serviceMan.value
								+ '&custTel=' + document.theform.custTel.value
								+ '&cardId=' + document.theform.cardId.value
								+ '&address=' + document.theform.address.value;
			}

			function queryCust(){
				disableAllBtn(true);
				document.theform.submit();
			}

			function queryCustYield(custId){
				location = 'cash_cust_yield.jsp?custId=' + custId + document.theform.qUrl.value;
			}

			function gotoUnitProfit(custId){
				
				var url = "<%=request.getContextPath()%>/cash/cash_choose_date.jsp";
				var date = showModalDialog(url,'', 'dialogWidth:450px;dialogHeight:300px;status:0;help:0');
				if (date != null) {
					location = 'net_value_disclosures_print.jsp?custId=' + custId + document.theform.qUrl.value + date ;
				}
			}

			function gotoRedeemRecord(custId){
				location = 'redeem_record_list.jsp?custId=' + custId + document.theform.qUrl.value;
			}

			function gotoFundFlow(custId) {
				location = 'cash_fundflow.jsp?cust_id=' + custId + '&product_id=' + document.theform.productId.value
						 + '&sub_product_id=' + document.theform.subProductId.value
						 + document.theform.qUrl.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="cash_cust_choose.jsp">
			<input type="hidden" id="productId" name="productId" value="<%=productId%>" />
			<input type="hidden" id="subProductId" name="subProductId" value="<%=subProductId%>" />
			<input type="hidden" id="qUrl" name="qUrl" value="<%=qUrl%>" />
			<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
				<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
			  		<tr>
					   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
					   <td align="right">
			   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
						</td>
					</tr>
				</table>

				<table>
					<tr>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
						<td valign="bottom" align="left">
							<input name="custNo" value="<%=custNo %>" onkeydown="javascript:nextKeyPress(this)">
						</td>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
						<td valign="bottom" align="left">
							<input name="custName" value="<%=custName %>" onkeydown="javascript:nextKeyPress(this)" maxlength="100">
						</td>
					</tr>
					<tr>
						<td align="right">客户类别 :</td>
						<td>
							<select size="1" name="custType" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getCustTypeOptions(custType)%>
							</select>
						</td>
						<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td> <!-- 客户经理 -->
						<td>
							<select size="1" name="serviceMan" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getManager_Value(serviceMan)%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">电话 :</td>
						<td>
							<input name="custTel" value="<%=custTel %>" onkeydown="javascript:nextKeyPress(this)"/>
						</td>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
						<td valign="bottom" align="left">
							<input name="cardId" value="<%=cardId %>" onkeydown="javascript:nextKeyPress(this)"/>
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
						<td colspan="3"><input name="address" value="<%=address %>" onkeydown="javascript:nextKeyPress(this)" size="63"></td>
					</tr>
					<tr>
						<td align="center" colspan="4">
							<button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:queryCust();">确定(<u>O</u>)</button>
						</td>
					</tr>
				</table>
			</div>

			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">客户查询(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trtagsort">
						<td align="center" width="100">
							<!-- <input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);"> -->
							<%=LocalUtilis.language("class.ID",clientLocale)%>
						</td><!--编号-->
						<td align="center">客户名称 </td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
						<td align="center">证件类型 </td><!--证件类型-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
						<td align="center">客户经理 </td><!--客户经理-->
						<td align="center">收益率</td>
						<td align="center">单位净值</td>
						<td align="center">赎回记录</td>
						<td align="center">交易明细</td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							custId = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
							encpypt = Utility.parseInt(Utility.trimNull(map.get("ENCRYPT")),0);
							custNo = Utility.trimNull(map.get("CUST_NO"));
							custName = Utility.trimNull(map.get("CUST_NAME"));
							custType = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
							custTypeName = Utility.trimNull(map.get("CUST_TYPE_NAME"));
							cardType = Utility.parseInt(Utility.trimNull(map.get("CARD_TYPE")),new Integer(0));
							cardTypeName = Utility.trimNull(map.get("CARD_TYPE_NAME"));
							cardId = Utility.trimNull(map.get("CARD_ID"));
							serviceMan = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MEN")),new Integer(0));
							servicManName = Utility.trimNull(map.get("SERVICE_MAN_NAME"));
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<!--  <input type="checkbox" name=custId value="<%=custId%>" class="flatcheckbox">-->
									</td>
									<td width="90%" align="center"><%=custNo%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=custName%></td>
						<td align="center"><%=custTypeName%></td>
						<td align="center"><%=cardTypeName%></td>
						<td align="center"><%=cardId%></td>
						<td align="center"><%=servicManName%></td>
						<td align="center">
							<a href="#" onclick="javascript:queryCustYield('<%=custId %>');">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="收益" /><!--收益-->
							</a>
						</td>
						<td align="center">
							<a href="#" onclick="javascript:gotoUnitProfit('<%=custId %>');">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="单位净值" /><!--单位净值-->
							</a>
						</td>
						<td align="center">
							<a href="#" onclick="javascript:gotoRedeemRecord('<%=custId %>');">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="赎回记录" /><!--赎回记录-->
							</a>
						</td>
						<td align="center">
							<a href="#" onclick="javascript:gotoFundFlow('<%=custId %>');">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="交易明细" /><!--交易明细-->
							</a>
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
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="11"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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