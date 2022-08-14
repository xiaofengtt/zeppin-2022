<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	ScoreProductVo vo = new ScoreProductVo();
	ScoreProductBean scoreProductBean = new ScoreProductBean();
	//页面变量
	Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
	Integer subproduct_id = Utility.parseInt(request.getParameter("subproduct_id"),new Integer(0));
	BigDecimal score_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("score_rate")), new BigDecimal(0),2,"1");
	String summary = Utility.trimNull(request.getParameter("summary"));
	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		vo.setProduct_id(product_id);
		vo.setSubproduct_id(subproduct_id);
		vo.setScore_rate(score_rate);
		vo.setSummary(summary);
		scoreProductBean.addScoreProduct(vo);
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
		<title>新增积分产品倍率</title>

		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
		<script language=javascript>
			window.onload = function(){
				var bSuccess = <%=bSuccess%>;
				if (bSuccess) {
					window.returnValue = 1;
					window.close();
				}
				var productId = <%=product_id%>;
				if (productId > 0) {
					getSubProductOptions(document.theform.product_id.value,<%=subproduct_id%>,0);
				} else {
					document.getElementById("sub_product_id").style.display = "none";
				}
			}

			function validateForm(form){
				if(document.theform.product_id.value==0){
					alert("请选择产品");
					return false;
				}
				if(document.theform.subproduct_id.style.display != "none"){
					if(document.theform.subproduct_id.value==0){
						alert("请选择子产品");
						return false;
					}
				}
				var score_rate = document.theform.score_rate.value;
				var reg = /^[0-9]*$/;
				if(!reg.test(score_rate)||score_rate==""){
					alert("增减倍率不正确!");
					document.theform.score_rate.focus();
					return fasle;
				}
				return true;
			}
			
			//查询条件 加载对应产品的子产品
			function getSubProductOptions(value1,value2) {
				if(value1!=0){
					utilityService.getSubProductOptionS(value1,value2,{callback: function(data){
						if(data=='<option value="">请选择</option>'){
							document.getElementById('subProduct_style').style.display = 'none';			
						}else{ 		
							document.getElementById('subProduct_style').style.display = ''; 
						}			
						$("subProductOptions").innerHTML = "<select name='subproduct_id' style='width:280px;'>"+data+"</select>"
					}});
				}else{
					document.getElementById('subProduct_style').style.display = 'none';
				}
			}
			
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="addScoreProduct.jsp" onsubmit="javascript:return validateForm(this);">

			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--新增积分产品倍率-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>新增积分产品倍率</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right">产品选择 :</td >
					<td align="left" colspan="3" id="select_id">
						<select  name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:getSubProductOptions(this.value,'');" style="width: 280px;">
							<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48)%>
						</select>
					</td>
				</tr>
				<tr id="subProduct_style" style="display:none;">
					<td align="right">子产品名称 :</td>
					<td colspan="3" id="subProductOptions">
				
					</td>
				</tr>
				<tr>
					<td align="right">增减倍率 :</td>
					<td align="left">
						<input type="text" name="score_rate" <%if(score_rate.intValue()==0){ %>value=""<%}else{ %>value="<%=score_rate %><%} %>"/>
					</td>
				</tr>
				<tr>
					<td align="right">文字说明 :</td>
					<td align="left">
						<textarea rows="5" style="width: 280px;" name="summary"><%=summary %></textarea>
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