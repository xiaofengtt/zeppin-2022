<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.Format,enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	ScoreActivityVo vo = new ScoreActivityVo();
	ScoreActivityBean scoreActivityBean = new ScoreActivityBean();
	//页面变量
	Integer activity_id = Utility.parseInt(request.getParameter("activity_id"),new Integer(0));
	Integer score = Utility.parseInt(request.getParameter("score"),new Integer(0));
	Integer date_begin = Utility.parseInt(request.getParameter("date_begin"),new Integer(0));
	Integer date_end = Utility.parseInt(request.getParameter("date_end"),new Integer(0));
	Integer activity_status = Utility.parseInt(request.getParameter("activity_status"),new Integer(-1));
	Integer df_activity_id = Utility.parseInt(request.getParameter("df_activity_id"),new Integer(0));
	String summary = Utility.trimNull(request.getParameter("summary"));

	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		vo.setActivity_id(activity_id);
		vo.setScore(score);
		vo.setDate_begin(date_begin);
		vo.setDate_end(date_end);
		vo.setActivity_status(activity_status);
		vo.setDf_activity_id(df_activity_id);
		vo.setSummary(summary);
		scoreActivityBean.modiScoreActivity(vo);
		bSuccess = true;
	}
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<base TARGET="_self">
		<title>修改积分活动</title>

		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/calendar.js"></script>
		<script language=javascript>
			window.onload = function(){
				var v_bSuccess = document.getElementById("bSuccess").value;
				
				if(v_bSuccess=="true"){
					alert("原来参与活动者积分不变");
					window.returnValue = 1;
					window.close();
				}
			}

			function validateForm(form){
				syncDatePicker(form.date_begin_picker, form.date_begin);
				syncDatePicker(form.date_end_picker, form.date_end);
				var date_begin = document.theform.date_begin.value;
				var date_end = document.theform.date_end.value;
				var score = document.theform.score.value;
				var reg = /^\+?[1-9][0-9]*$/;
				if(!reg.test(score)||score==""){
					alert("活动积分为正整数!");
					document.theform.score.focus();
					return fasle;
				}
				if (date_begin==""){
					alert("请选择活动开始时间!");
					return false;
				}
				if (date_end=="") {
					alert("请选择活动结束时间!")
					return false;
				}
				if (date_begin > date_end) {
					alert("请选择预估开始时间小于结束时间!")
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="modiScoreActivity.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="activity_id" id="activity_id" value="<%=activity_id %>"/>
			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--修改积分活动-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>修改积分活动</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right" width="150px">活动奖励积分 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" name="score" value="<%=score %>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">活动开始日期 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" name="date_begin_picker" value="<%=Format.formatDateLine(date_begin)%>"/>
						<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_begin_picker,theform.date_begin_picker.value,this);" tabindex="13">
						<input type="hidden" name="date_begin" id="date_begin" value="<%=date_begin %>">
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">活动结束日期 :&nbsp;&nbsp;</td>
					<td>
						<input type="text" name="date_end_picker" value="<%=Format.formatDateLine(date_end)%>"/>
						<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.date_end_picker,theform.date_end_picker.value,this);" tabindex="13">
						<input type="hidden" name="date_end" id="date_end" value="<%=date_end %>">
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">活动状态 :&nbsp;&nbsp;</td>
					<td align="left">
						<select name="activity_status">
							<option value="0" <%if(activity_status.intValue()==0){ %>selected="selected"<%} %> >无效</option>
							<option value="1" <%if(activity_status.intValue()==1){ %>selected="selected"<%} %> >有效</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">说明 :&nbsp;&nbsp;</td>
					<td align="left">
						<textarea rows="5" style="width:200px;" name="summary"><%=summary %></textarea>
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="center">
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.btnSave.disabled='true'; document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;<!--取消-->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>