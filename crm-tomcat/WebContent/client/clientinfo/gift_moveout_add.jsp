<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%

Integer gift_id = Utility.parseInt(Utility.trimNull(request.getParameter("gift_id")),new Integer(0));
String giftName = Utility.trimNull(request.getParameter("giftName"));
Integer gift_number = Utility.parseInt(Utility.trimNull(request.getParameter("gift_number")),new Integer(0));
Integer giftNumbers = Utility.parseInt(Utility.trimNull(request.getParameter("giftNumbers")),new Integer(0));//库存数
Integer giftNumber = Utility.parseInt(Utility.trimNull(request.getParameter("giftNumber")),new Integer(0));
String giftSummary = Utility.trimNull(request.getParameter("giftSummary"));
String detailInfo = Utility.trimNull(request.getParameter("detailInfo"));	
Integer move_out_id=new Integer(0);
boolean bSuccess = false;
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
if (request.getMethod().equals("POST")){
	vo.setGift_id(gift_id);	
	vo.setGift_summary(giftSummary);
	vo.setInput_man(input_operatorCode);

	if(gift_id.intValue()!=0){
		vo.setGift_number(giftNumber);
		move_out_id = customer.addGifiMove(vo);
	}
		
	bSuccess = true;
}

 %>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	sl_alert("保存成功！");
	//toaddcust(<%=move_out_id%>);
    window.returnValue=<%=move_out_id%>;
	window.close();
<%}%>

function toaddcust(move_out_id){
	var a = document.createElement("a");
  	a.href = "<%=request.getContextPath()%>/client/clientinfo/gift_to_cust_add.jsp?move_out_id="+move_out_id;
    document.body.appendChild(a);
    a.click();
}

/*保存*/
function SaveAction(){
	if(!sl_checkNum(document.theform.giftNumber, "领取数量 ", 11, 1))	return false;
	if (! sl_check(document.theform.giftSummary, "礼物备注", 100, 1)) return false;

	if (sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")){ //保存输入信息
		disableAllBtn(true);
		document.theform.submit();
	}
}


/*取消*/
function CancelAction(){
	window.close();
}
</script>
</HEAD>
<BODY class="BODY">
<form id="theform" name="theform" method="POST" action="gift_moveout_add.jsp" >
<input type="hidden" name="gift_id" value="<%=gift_id %>">
		<div>
			<img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font>
			<hr noshade color="#808080" size="1">
		</div>
		<table border="0" width="95%" cellspacing="2" cellpadding="2">
				<tr>
					<td  width="100px" align="right">礼物名称:</td>
			 		<td  width="*">
			 			<input name="giftName" disabled onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftName %>" />
			 		</td>
					<td  width="100px" align="right">库存数量:</td>
					<td >
						<input name="giftNumbers" disabled onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftNumbers %>" />
			 		</td>
				</tr>
				<tr>
					<td  width="100px" align="right">详细信息:</td>
					<td colspan="3">
						<TEXTAREA name="detailInfo" disabled cols="72" rows="5"><%=detailInfo %></TEXTAREA>
			 		</td>
				</tr>
		</table>
		<hr noshade color="#808080" size="1">
		<table border="0" width="95%" cellspacing="2" cellpadding="2">
				<tr>
					
					<td  width="100px" align="right">领取数量:</td>
					<td >
						<input name="giftNumber"  onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="" />
			 		</td>
				</tr>
				<tr>
					<td  width="100px" align="right">领取备注:</td>
					<td colspan="3">
						<TEXTAREA name="giftSummary" cols="72" rows="5"><%=giftSummary %></TEXTAREA>
			 		</td>
				</tr>
		</table>
		<br>
		
			<div align="right" style="margin-right:5px">
				<!-- 保存 -->
			    <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%>(<u>S</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- 关闭 -->
			    <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%>(<u>C</u>)</button>
			</div>
		
</form>
</BODY>
</HTML>
