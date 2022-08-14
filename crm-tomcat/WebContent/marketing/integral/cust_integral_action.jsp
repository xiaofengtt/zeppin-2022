<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//声明辅助变量
boolean bSuccess = false;
String cust_id_array = Utility.trimNull(request.getParameter("cust_id_array"));
Integer change_flag = Utility.parseInt(Utility.trimNull(request.getParameter("change_flag")),new Integer(1));
Integer change_integral = Utility.parseInt(Utility.trimNull(request.getParameter("change_integral")),new Integer(0));
String remark = Utility.trimNull(request.getParameter("remark"));
Integer cust_id = new Integer(0);

if(request.getMethod().equals("POST")){
	String[] cust_id_array_str = Utility.splitString(cust_id_array,"|");
	CustIntegralLocal local = EJBFactory.getCustIntegral();
	CustIntegralVO vo = new CustIntegralVO();
	
	Integer ad_integral = new Integer(change_flag.intValue()*change_integral.intValue());
	vo.setRemark(remark);
	vo.setAd_integral(ad_integral);
	vo.setBusi_type(new Integer(1));
	vo.setInput_man(input_operatorCode);
	
	for(int i=0;i<cust_id_array_str.length;i++){
		cust_id = Utility.parseInt(cust_id_array_str[i],new Integer(0));
		
		vo.setCust_id(cust_id);
		local.addCustIntegralByHand(vo);		
	}
	bSuccess = true;
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--客户积分卡修改-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*验证数据*/
function validateForm(form){
	if(!sl_checkNum(document.theform.change_integral, "变更积分",30,1)){return false;}//变更积分
	if(!sl_check(document.theform.remark, "变更原因",200,1)){return false;}//变更原因
	return sl_check_update();
}
/*取消*/
function CancelAction(){
	window.close();
}
/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}
/*初始化*/
window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	if(v_bSuccess=="true"){	
		sl_update_ok();	
		window.returnValue=1;	
		window.close();
	}
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<!--修改成功标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="cust_id_array" name="cust_id_array" value="<%=cust_id_array%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif"  width="32" height="28">
	<font color="#215dc6"><b>客户积分卡>>客户积分修改</b></font>
</div>
<hr noshade color="#808080" size="1" width="98%">

<div align="left" style=" height:100px; margin-left:5px;">
	<table cellSpacing="1" cellPadding="2" width="99%"  bgcolor="#CCCCCC">
		<tr style="background:F7F7F7;">
	 		<td colspan="2" align="left"><font size="4" face="微软雅黑"><b>客户积分修改</b></font></td>
	 	</tr>
	 	<tr style="background:F7F7F7;">
            <td  width="100px" align="right"><font size="2" face="微软雅黑">*变更积分:</font>&nbsp;&nbsp;</td>
			<td  width="*">
				<select name="change_flag">
					<option value="1" <%if(change_flag.intValue()==1){out.print("selected");}%>>加</option>
					<option value="-1" <%if(change_flag.intValue()==-1){out.print("selected");}%>>减</option>
				</select>	
				<input name="change_integral" type="text" value="<%=Math.abs(change_integral.intValue())%>" size="8"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td  width="100px" align="right" vAlign="top"><font size="2" face="微软雅黑">*变更原因:</font>&nbsp;&nbsp;</td>
			<td  width="*">
				<textarea rows="3" name="remark" onkeydown="javascript:nextKeyPress(this)" cols="70"><%=remark%></textarea>			
			</td>
		</tr>	
	</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<!-- 保存 -->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!-- 关闭 -->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>