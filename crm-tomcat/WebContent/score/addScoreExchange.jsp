<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	ScoreExchangeVo vo = new ScoreExchangeVo();
	ScoreExchangeBean scoreExchangeBean = new ScoreExchangeBean();
	//页面变量
	Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
	Integer score = Utility.parseInt(request.getParameter("score"),new Integer(0));
	String summary = Utility.trimNull(request.getParameter("summary"));

	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		vo.setCust_id(cust_id);
		vo.setScore(score);
		vo.setInput_man(input_operatorCode);
		vo.setSummary(summary);
		scoreExchangeBean.addScoreExchange(vo);
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
		<title>积分兑换</title>

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
				var reg = /^\+?[1-9][0-9]*$/;
				var score = document.theform.score.value;
				if(!reg.test(score)||score==""){
					alert("兑换积分为正整数!");
					document.theform.score.focus();
					return fasle;
				}
				return true;
			}
		
		
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="addScoreExchange.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<input type="hidden" name="cust_id" id="cust_id" value="<%=cust_id %>"/>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--积分兑换-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>积分兑换</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right" width="150px">兑换积分 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:200px;" name="score" <%if(score.intValue()==0){ %>value=""<%}else{ %>value="<%=score %><%} %>"/>
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