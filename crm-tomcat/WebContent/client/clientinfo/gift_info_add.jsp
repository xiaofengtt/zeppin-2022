<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer giftId = Utility.parseInt(Utility.trimNull(request.getParameter("giftId")),new Integer(0));
String giftName = "";
BigDecimal giftPrice =  new BigDecimal(0.0);
Integer giftNumber = new Integer(0);
String giftSummary = "";
String detailInfo = "";

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

List list=new ArrayList();
Map giftmap=new HashMap();
if(giftId.intValue()!=0){
	vo.setGift_id(giftId);
	list = customer.listGifiAll(vo);
	if (!list.isEmpty()) {
		giftmap = (Map)list.get(0);
		giftName = Utility.trimNull(giftmap.get("GIFT_NAME"));
		giftPrice = Utility.parseDecimal(Utility.trimNull(giftmap.get("GIFT_PRICE")),new BigDecimal(0.0));
		giftNumber = Utility.parseInt(Utility.trimNull(giftmap.get("GIFT_NUMBER")),new Integer(0));
		giftSummary =  Utility.trimNull(giftmap.get("GIFT_SUMMARY"));
		detailInfo = Utility.trimNull(giftmap.get("DETAIL_INFO"));
	}
}

boolean buss=false;
if (request.getMethod().equals("POST")){
giftName = Utility.trimNull(request.getParameter("giftName"));
giftPrice = Utility.parseDecimal(request.getParameter("giftPrice"), new BigDecimal(0.0));
giftNumber = Utility.parseInt(Utility.trimNull(request.getParameter("giftNumber")),new Integer(1));
giftSummary = Utility.trimNull(request.getParameter("giftSummary"));
detailInfo = Utility.trimNull(request.getParameter("detailInfo"));	

vo.setGift_name(giftName);
vo.setGift_price(giftPrice);
vo.setGift_number(giftNumber);
vo.setGift_summary(giftSummary);
vo.setInput_man(input_operatorCode);
vo.setDetail_info(detailInfo);

	if(giftId.intValue()!=0){
		vo.setGift_id(giftId);
		customer.updateGifi(vo);
		buss=true;
		
	}else{
		customer.addGifi(vo);
		buss=true;
	}
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<%if(buss){%>
	sl_alert("保存成功！");
	window.close();	
<%}%>

/*保存*/
function SaveAction(){
		if (!sl_check(document.theform.giftName, "礼物名称", 100, 1)) return false;
		if(!sl_checkNum(document.theform.giftNumber, "礼物数量 ", 11, 0))	return false;
		if (! sl_check(document.theform.detailInfo, "详细信息不能为空", 100, 1)) return false;	

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
<form id="theform" name="theform" method="post" action="gift_info_add.jsp" >
	<input type="hidden" name="giftId" value="<%=giftId%>"/>
		<table border="0" width="95%" cellspacing="2" cellpadding="2">
				<tr>
					<td  width="100px" align="right">名称:</td>
			 		<td  width="*">
			 			<input name="giftName" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftName%>" />
			 		</td>
			 		<td  width="100px" align="right">价格:</td>
					<td>
						<input name="giftPrice" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftPrice%>" />
			 		</td>
				</tr>
				<tr>
			 		<td  width="100px" align="right">数量:</td>
					<td colspan="3">
						<input name="giftNumber" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftNumber%>" />
			 		</td>
				</tr>
				<tr>
					
					<td  width="100px" align="right">备注:</td>
					<td colspan="3">
						<TEXTAREA name="giftSummary" cols="20" rows="3"><%=giftSummary %></TEXTAREA>
			 		</td>
				</tr>
				<tr>
					<td  width="100px" align="right">详细信息:</td>
					<td colspan="3">
						<TEXTAREA name="detailInfo" cols="50" rows="5"><%=detailInfo %></TEXTAREA>
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
