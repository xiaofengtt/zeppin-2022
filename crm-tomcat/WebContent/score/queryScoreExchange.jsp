<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.Format,enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));//客户ID
	Integer exchange_date = Utility.parseInt(request.getParameter("exchange_date"),new Integer(0));//兑换日期
	String qCustName = Utility.trimNull(request.getParameter("qCustName"));
	Integer list_id = new Integer(0);
	Integer score = new Integer(0);
	Integer input_man = new Integer(0);
	String summary = "";

	ScoreExchangeVo vo = new ScoreExchangeVo();
	ScoreExchangeBean scoreExchangeBean = new ScoreExchangeBean();

	vo.setCust_id(cust_id);
	vo.setExchange_date(exchange_date);

	IPageList pageList = null;
	Map map = null;
	pageList = scoreExchangeBean.queryScoreExchange(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
	Iterator it =  pageList.getRsList()!=null? pageList.getRsList().iterator(): new ArrayList().iterator();
	
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
		<script language="javascript" src="<%=request.getContextPath()%>/includes/calendar.js"></script>
		<script type="text/javascript">
			/*启动加载*/
			window.onload = function(){
					initQueryCondition();
			};

			function QueryAction(){
				disableAllBtn(true);
				syncDatePicker(document.theform.exchange_date_picker, document.theform.exchange_date);
				document.theform.submit();
			}

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'queryScoreExchange.jsp?page=1&pagesize=' + document.theform.pagesize.value
								+ '&cust_id=' + document.theform.cust_id.value
								+ '&exchange_date=' + document.theform.exchange_date.value;
			}

			function goback(){
				disableAllBtn(true);
				location = "custScore.jsp?qCustName=" + document.theform.qCustName.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="queryScoreExchange.jsp">
			<input type="hidden" name="cust_id" id="cust_id" value="<%=cust_id %>">
			<input type="hidden" name="qCustName" id="qCustName" value="<%=qCustName %>">
			<div id="queryCondition" class="qcMain" style="display:none;width:300px;height:90px;">
				<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
			  		<tr>
						<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
			   			<td align="right">
			   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			   			</td>
					</tr>		
				</table> 
				<!-- 要加入的查询内容 -->
				<table border="0" width="100%" cellspacing="2" cellpadding="2">
					<tr>
						<td align="right">兑换日期: </td>
						<td align="left">
							<input type="text" name="exchange_date_picker" value="<%=Format.formatDateLine(exchange_date)%>"/>
							<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.exchange_date_picker,theform.exchange_date_picker.value,this);" tabindex="13">
							<input type="hidden" name="exchange_date" id="exchange_date" value="<%=exchange_date %>">
						</td>		
					</tr>
					<tr>
						<td align="center" colspan="4">
							<button class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
								<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)
							</button><!--确定-->	 				
						</td>
					</tr>
				</table>
			</div>
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b>基本管理>>积分兑换</b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:goback();">返回 (<u>B</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
							序号
						</td><!--编号-->
						<td align="center" width="*">兑换日期</td>
						<td align="center" width="*">兑换积分</td>
						<td align="center" width="*">客户兑换详情</td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							exchange_date = Utility.parseInt(Utility.trimNull(map.get("EXCHANGE_DATE")),new Integer(0));
							list_id = Utility.parseInt(Utility.trimNull(map.get("LIST_ID")),new Integer(0));
							score = Utility.parseInt(Utility.trimNull(map.get("SCORE")),new Integer(0));
							summary = Utility.trimNull(map.get("SUMMARY"));
							iCurrent++;
							iCount++;
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="remove" value="<%=exchange_date+"|"+list_id%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=iCurrent%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=Format.formatDateLine(exchange_date)%></td>
						<td align="center"><%=score%></td>
						<td align="center"><%=summary%></td>
					</tr>
					<%
						}
						for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){ 
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="4"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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