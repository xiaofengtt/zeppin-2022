<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

	ScoreRuleVo vo = new ScoreRuleVo();
	ScoreRuleBean scoreRuleBean = new ScoreRuleBean();

	Integer rule_id = new Integer(0);
	Integer more_amount = new Integer(0);
	BigDecimal unitscore = new BigDecimal(0);
	BigDecimal dayscore = new BigDecimal(0);
	String summary = "";

	String[] s = request.getParameterValues("rule_id");
	//若选中不为空
	if (s != null){
		for(int i = 0;i < s.length; i++){
			if(s[i] != "0"){
				vo.setRule_id(new Integer(s[i]));
				scoreRuleBean.delScoreRule(vo);
			}
		}
	}

	IPageList pageList = null;
	Map map = null;
	pageList = scoreRuleBean.queryScoreRule(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
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
			/*启动加载
			window.onload = function(){
					initQueryCondition();
			};

			function QueryAction(){
				disableAllBtn(true);
				document.theform.submit();
			}*/

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'scoreRule.jsp?page=1&pagesize=' + document.theform.pagesize.value;
			}

			/*新增方法*/
			function AppendAction(){		
				var url = "addScoreRule.jsp";		
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
			/*修改方法*/
			function modiAction(rule_id,more_amount,unitscore,dayscore,summary){
				var url = "modiScoreRule.jsp?rule_id="+rule_id+"&more_amount="+more_amount+"&unitscore="+unitscore+"&dayscore="+dayscore+"&summary="+summary;		
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
			/*删除方法*/
			function DelAction(){	
				if(confirmRemove(document.theform.rule_id)){
					disableAllBtn(true);
					document.theform.submit();
				}
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="scoreRule.jsp">
			
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<!-- 查询 
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
					&nbsp;&nbsp;&nbsp;-->
					<!-- 新建 -->
					<button class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%>" onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<!-- 删除 -->
					<button class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>" onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.rule_id,this);">
							序号
						</td><!--编号-->
						<td align="center" width="*">认申购金额下限</td>
						<td align="center" width="*">每万元积分值</td>
						<td align="center" width="*">持有每天的积分值</td>
						<td align="center" width="*">说明</td>
						<td align="center" width="30px"> <%=LocalUtilis.language("message.update",clientLocale)%></td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							iCurrent++;
							iCount++;
							rule_id = Utility.parseInt(Utility.trimNull(map.get("RULE_ID")),new Integer(0));
							more_amount = Utility.parseInt(Utility.trimNull(map.get("MORE_AMOUNT")),new Integer(0));
							unitscore = Utility.parseDecimal(Utility.trimNull(map.get("UNITSCORE")), new BigDecimal(0), 2,"1");
							dayscore = Utility.parseDecimal(Utility.trimNull(map.get("DAYSCORE")), new BigDecimal(0), 2,"1");
							summary =  Utility.trimNull(map.get("SUMMARY"));
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="rule_id" value="<%=rule_id%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=iCurrent%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=more_amount%></td>
						<td align="center"><%=unitscore%></td>
						<td align="center"><%=dayscore%></td>
						<td align="center"><%=summary%></td>
						<td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction('<%=rule_id%>','<%=more_amount %>','<%=unitscore %>','<%=dayscore %>','<%=summary %>');"/>
						</td>
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
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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