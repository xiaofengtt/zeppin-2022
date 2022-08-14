<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//客户ID
	String custName = Utility.trimNull(request.getParameter("qCustName"));
	
	String custNo = "";
	Integer custType = new Integer(0);
	String custTypeName = "";
	String years = "";
	String summary = "";
	Integer score = new Integer(0);
	Integer exchangeScore = new Integer(0);

	ScoreVo vo = new ScoreVo();
	ScoreBean scoreBean = new ScoreBean();

	vo.setCustId(custId);
	vo.setCustName(custName);

	IPageList pageList = null;
	Map map = null;
	pageList = scoreBean.queryCustScore(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
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
	
		<script type="text/javascript">
			/*启动加载*/
			window.onload = function(){
					initQueryCondition();
			};

			function QueryAction(){
				disableAllBtn(true);
				document.theform.submit();
			}

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'custScore.jsp?page=1&pagesize=' + document.theform.pagesize.value
								+ '&qCustName=' + document.theform.qCustName.value;
			}

			function addScoreExchange(custId) {
				var url = "addScoreExchange.jsp?cust_id=" +custId;
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
			
			/*积分兑换详情*/
			function toScoreExchange(custId) {
				disableAllBtn(true);
				location= "queryScoreExchange.jsp?cust_id=" +custId+"&qCustName=" + document.theform.qCustName.value;
			}

			/*活动奖励详情*/
			function toCustActivity(custId) {
				disableAllBtn(true);
				location= "custActivity.jsp?cust_id=" +custId+"&qCustName=" + document.theform.qCustName.value;
			}

			function addCustActivity() {
				if(checkedCount(document.theform.custId)==0){
					sl_alert("请选定客户！");
					return false;
				}else{
					var custId = "";
					var select = document.getElementsByName("custId");
					for(i = 0;i<select.length;i++){
						if(select[i].checked==true){
							if(custId!=""){
								custId += "|";
							}
							custId += select[i].value;
						}
					}
					var url = "addCustActivity.jsp?custId="+custId;
					if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
						sl_update_ok();
						location.reload();
					}
					showWaitting(0);
				}
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="custScore.jsp">
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
						<td align="right">客户名称: </td>
						<td align="left">
							<input type="text" name="qCustName" id="qCustName" value="<%=custName %>"
								onkeydown="javascript:nextKeyPress(this)" style="width:120px"/>
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
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=a id="btnAppend" name="btnQue" title="参加活动" onclick="javascript:addCustActivity();">参加活动(<u>A</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.custId,this);">
							客户编号
						</td><!--编号-->
						<td align="center" width="*">客户名称</td>
						<td align="center" width="*">积分年份</td>
						<td align="center" width="*">客户积分</td>
						<td align="center" width="*">已兑换积分</td>
						<td align="center" width="*">剩余积分</td>
						<td align="center" width="*">积分兑换</td>
						<td align="center" width="*">兑换详情</td>
						<td align="center" width="*">奖励积分详情</td>
						<!--  <td align="center" width="30px"> <%=LocalUtilis.language("message.update",clientLocale)%></td>-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							custId = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
							custName = Utility.trimNull(map.get("CUST_NAME"));
							custNo = Utility.trimNull(map.get("CUST_NO"));
							custType = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
							custTypeName = Utility.trimNull(map.get("CUST_TYPE_NAME"));
							years = Utility.trimNull(map.get("YEARS"));
							score = Utility.parseInt(Utility.trimNull(map.get("SCORE")),new Integer(0));
							exchangeScore = Utility.parseInt(Utility.trimNull(map.get("EXCHANGE_SCORE")),new Integer(0));
							summary = Utility.trimNull(map.get("SUMMARY"));
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="custId" value="<%=custId%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=custNo%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=custName%></td>
						<td align="center"><%=years%></td>
						<td align="center"><%=score%></td>
						<td align="center"><%=exchangeScore%></td>
						<td align="center"><%=score.intValue()-exchangeScore.intValue()%></td>
						<td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:addScoreExchange(<%=custId%>);"/>
						</td>
						<td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:toScoreExchange(<%=custId%>);"/>
						</td>
						<td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:toCustActivity(<%=custId%>);"/>
						</td>
						<!-- <td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction(<%=custId%>);"/>
						</td>-->
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