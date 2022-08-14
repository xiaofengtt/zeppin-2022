<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.Format,enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

	ScoreActivityVo vo = new ScoreActivityVo();
	ScoreActivityBean scoreActivityBean = new ScoreActivityBean();

	Integer activity_id = new Integer(0);
	Integer score = new Integer(0);
	Integer date_begin = new Integer(0);
	Integer date_end = new Integer(0);
	Integer activity_status = new Integer(0);
	Integer df_activity_id = new Integer(0);
	String summary = "";
	Integer Qactivity_status = Utility.parseInt(request.getParameter("Qactivity_status"),new Integer(-1));
	Integer Qdate_begin = Utility.parseInt(request.getParameter("Qdate_begin"),new Integer(0));
	Integer Qdate_end = Utility.parseInt(request.getParameter("Qdate_end"),new Integer(0));

	IPageList pageList = null;
	Map map = null;
	vo.setDate_begin(Qdate_begin);
	vo.setDate_end(Qdate_end);
	vo.setActivity_status(Qactivity_status);

	pageList = scoreActivityBean.queryScoreActivity(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
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
				syncDatePicker(document.theform.date_begin_picker, document.theform.Qdate_begin);
				syncDatePicker(document.theform.date_end_picker, document.theform.Qdate_end);
				disableAllBtn(true);
				document.theform.submit();
			}

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'scoreActivity.jsp?page=1&pagesize=' + document.theform.pagesize.value
								+ '&Qdate_begin=' + document.theform.Qdate_begin.value
								+ '&Qdate_end=' + document.theform.Qdate_end.value
								+ '&Qactivity_status=' + document.theform.Qactivity_status.value;
			}

			/*新增方法*/
			function AppendAction(){		
				var url = "addScoreActivity.jsp";		
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
			/*修改方法*/
			function modiAction(activity_id,score,date_begin,date_end,activity_status,summary){
				var url = "modiScoreActivity.jsp?activity_id="+activity_id+"&score="+score+"&date_begin="+date_begin+"&date_end="+date_end+"&activity_status="+activity_status+"&summary="+summary;		
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="scoreActivity.jsp">
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
						<td align="right">活动开始日期: </td>
						<td>
							<input type="text" name="date_end_picker" value="<%=Format.formatDateLine(date_end)%>"/>
							<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_end_picker,theform.date_end_picker.value,this);" tabindex="13">
							<input type="hidden" name="Qdate_end" id="Qdate_end" value="<%=Qdate_end %>">
						</td>		
					</tr>
					<tr>
						<td align="right">活动结束日期: </td>
						<td align="left">
							<input type="text" name="date_begin_picker" value="<%=Format.formatDateLine(date_begin)%>"/>
							<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_begin_picker,theform.date_begin_picker.value,this);" tabindex="13">
							<input type="hidden" name="Qdate_begin" id="Qdate_begin" value="<%=Qdate_begin %>">
						</td>		
					</tr>
					<tr>
						<td align="right">活动状态: </td>
						<td align="left">
							<select name="Qactivity_status">
								<option value="-1" <%if(Qactivity_status.intValue()==-1){ %>selected="selected"<%} %> >请选择</option>
								<option value="0" <%if(Qactivity_status.intValue()==0){ %>selected="selected"<%} %> >无效</option>
								<option value="1" <%if(Qactivity_status.intValue()==1){ %>selected="selected"<%} %> >有效</option>
							</select>
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
					<!-- 查询 -->
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<!-- 新建 -->
					<button class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="<%=LocalUtilis.language("message.new",clientLocale)%>" onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<!-- 删除 
					<button class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>" onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>-->
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
						<td align="center" width="*">本次活动的奖励积分</td>
						<td align="center" width="*">活动有效日期(起)</td>
						<td align="center" width="*">活动有效日期(止)</td>
						<td align="center" width="*">活动状态</td>
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
							activity_id = Utility.parseInt(Utility.trimNull(map.get("ACTIVITY_ID")),new Integer(0));
							score = Utility.parseInt(Utility.trimNull(map.get("SCORE")),new Integer(0));
							date_begin = Utility.parseInt(Utility.trimNull(map.get("DATE_BEGIN")),new Integer(0));
							date_end = Utility.parseInt(Utility.trimNull(map.get("DATE_END")),new Integer(0));
							activity_status = Utility.parseInt(Utility.trimNull(map.get("ACTIVITY_STATUS")),new Integer(0));
							df_activity_id = Utility.parseInt(Utility.trimNull(map.get("DF_ACTIVITY_ID")),new Integer(0));
							summary =  Utility.trimNull(map.get("SUMMARY"));
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="rule_id" value="<%=activity_id%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=iCurrent%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=score%></td>
						<td align="center"><%=date_begin%></td>
						<td align="center"><%=date_end%></td>
						<td align="center"><%if(activity_status.intValue()==0){%>无效<%}else if(activity_status.intValue()==1){ %>有效<%} %></td>
						<td align="center"><%=summary%></td>
						<td align="center" width="30px">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction('<%=activity_id %>','<%=score %>','<%=date_begin %>','<%=date_end %>','<%=activity_status %>','<%=summary %>');"/>
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
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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