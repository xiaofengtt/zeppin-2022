<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	ScoreProductVo vo = new ScoreProductVo();
	ScoreProductBean scoreProductBean = new ScoreProductBean();
	//页面变量
	Integer rule_id = Utility.parseInt(request.getParameter("rule_id"),new Integer(0));
	String summary = Utility.trimNull(request.getParameter("summary"));
	Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
	Integer subproduct_id = Utility.parseInt(request.getParameter("subproduct_id"),new Integer(0));
	String product_name = Utility.trimNull(request.getParameter("product_name"));
	String list_name =Utility.trimNull(request.getParameter("list_name"));
	BigDecimal score_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("score_rate")), new BigDecimal(0),2,"1");

	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		vo.setRule_id(rule_id);
		vo.setProduct_id(product_id);
		vo.setSubproduct_id(subproduct_id);
		vo.setScore_rate(score_rate);
		vo.setSummary(summary);
		scoreProductBean.modiScoreProduct(vo);
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
		<title>修改积分规则</title>

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
				return true;
			}
		
		
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="modiScoreProduct.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<input type="hidden" name="rule_id" id="rule_id" value="<%=rule_id %>"/>
			<input type="hidden" name="product_id" id="product_id" value="<%=product_id %>"/>
			<input type="hidden" name="subproduct_id" id="subproduct_id" value="<%=subproduct_id %>"/>
			<table border="0" width="100%">
				<tr><!--修改产品积分倍率-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>修改产品积分倍率</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right">产品名称 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:250px;" name="product_name" value="<%=product_name %>" readonly="readonly" class="edline"/>
					</td>
				</tr>
				<%if(!"".equals(list_name)){ %>
				<tr>
					<td align="right">子产品名称 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:250px;" name="list_name" value="<%=list_name %>" readonly="readonly" class="edline"/>
					</td>
				</tr>
				<%} %>
				<tr>
					<td align="right">增减倍率 :&nbsp;&nbsp;</td>
					<td align="left">
						<input type="text" style="width:250px;" name="score_rate" value="<%=score_rate %>"/>
					</td>
				</tr>
				<tr>
					<td align="right">文字说明 :&nbsp;&nbsp;</td>
					<td align="left">
						<textarea rows="5" style="width:250px;" name="summary"><%=summary %></textarea>
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