<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	ScoreRuleVo vo = new ScoreRuleVo();
	ScoreRuleBean scoreRuleBean = new ScoreRuleBean();
	//页面变量
	
	Integer more_amount = Utility.parseInt(request.getParameter("more_amount"),new Integer(0));
	BigDecimal unitscore = Utility.parseDecimal(Utility.trimNull(request.getParameter("unitscore")),new BigDecimal(0),2,"1");
	BigDecimal dayscore = Utility.parseDecimal(Utility.trimNull(request.getParameter("dayscore")), new BigDecimal(0),2,"1");
	String summary = Utility.trimNull(request.getParameter("summary"));

	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		vo.setMore_amount(more_amount);
		vo.setUnitscore(unitscore);
		vo.setDayscore(dayscore);
		vo.setSummary(summary);
		scoreRuleBean.addScoreRule(vo);
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
		<title>新增积分规则</title>

		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>

		<script language=javascript>
			window.onload = function(){
				var v_bSuccess = document.getElementById("bSuccess").value;
				
				if(v_bSuccess=="true"){		
					window.returnValue = 1;
					window.close();
				}
			}

			function validateForm(form){
				var reg = /^[0-9]*$/;
				if(!reg.test(document.theform.more_amount.value)){
					alert("认证金额下限请输入正整数!");
					document.theform.more_amount.focus();
					return fasle;
				}
				reg = /^[0-9]+(.[0-9]{1,2})?$/;
				if(!reg.test(document.theform.unitscore.value)){
					alert("每万元积分小数点后保留2位!");
					document.theform.unitscore.focus();
					return fasle;
				}
				if(!reg.test(document.theform.dayscore.value)){
					alert("持有每天的积分值小数点后保留2位!");
					document.theform.dayscore.focus();
					return fasle;
				}
				return true;
			}
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="addScoreRule.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--新增积分规则-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>新增积分规则</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right" width="150px">认购金额下限(单位/万元) :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:200px;" name="more_amount" <%if(more_amount.intValue()==0){ %>value=""<%}else{ %>value="<%=more_amount %><%} %>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">每万元积分值 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:200px;" name="unitscore" <%if(unitscore.intValue()==0){ %>value=""<%}else{ %>value="<%=unitscore %><%} %>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">持有每天的积分值 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:200px;" name="dayscore" <%if(dayscore.intValue()==0){ %>value=""<%}else{ %>value="<%=dayscore %><%} %>"/>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">文字说明 :&nbsp;&nbsp;</td>
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