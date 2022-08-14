<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer gift_id = Utility.parseInt(request.getParameter("gift_id"),new Integer(0));
String lnk_from=Utility.trimNull(request.getParameter("lnk_from"));
Integer modi_flag = Utility.parseInt(request.getParameter("modi_flag"),new Integer(0));//1.未初审，要初审  2.已初审，要复审
Integer move_out_id = Utility.parseInt(request.getParameter("move_out_id"),new Integer(0));
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String giftName = Utility.trimNull(request.getParameter("giftName"));
Integer giftNumber = Utility.parseInt(Utility.trimNull(request.getParameter("giftNumber")),new Integer(0));
String giftSummary = Utility.trimNull(request.getParameter("giftSummary"));
Integer check_man  = Utility.parseInt(request.getParameter("check_man"),new Integer(0));
String check_man_explain = Utility.trimNull(request.getParameter("check_man_explain"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));

String check_man1_explain = "";

boolean temp =false;
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
List list=null;
Map giftmap=null;
if(move_out_id.intValue()!=0){
	vo.setMove_out_id(move_out_id);
	//取出库记录
	list = customer.listGifimoveoutAll(vo);
	if (!list.isEmpty()) {
		giftmap = (Map)list.get(0);
		giftName = Utility.trimNull(giftmap.get("GIFT_NAME"));
		giftNumber = Utility.parseInt(Utility.trimNull(giftmap.get("GIFT_NUMBER")),new Integer(0));
		giftSummary =  Utility.trimNull(giftmap.get("GIFT_SUMMARY"));
	}
}
if (request.getMethod().equals("POST") ){
	vo.setMove_out_id(move_out_id);
	vo.setGift_modi_flag(modi_flag);
	vo.setCheck_flag(check_flag);
    vo.setCheck_man1_explain(check_man_explain);
    vo.setInput_man(input_operatorCode);
	customer.checkGiftMoveout(vo);
	temp = true;
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
	if(<%=temp%>){
		window.returnValue = true;
		sl_alert("审核成功！");
		location ="gift_check_info.jsp";
	}
//审核
function tochecked(check_flag){
	document.theform.check_flag.value=check_flag;
	if (! sl_check(document.theform.check_man_explain, "审核意见", 100, 1)) return false;

	if (sl_confirm("审核")) {//保存输入信息
		disableAllBtn(true);
		document.theform.submit();
	}
}

function CancelAction(){
	<%if ("gift_manage".equals(lnk_from)){ %>
		location ="gift_moveout_list.jsp?gift_id=<%=gift_id%>";
	<%}else{%>	
		location ="gift_check_info.jsp";
	<%}%>
}
</script>

</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="gift_to_checked.jsp">
<input type="hidden" name="move_out_id" value="<%=move_out_id %>">
<input type="hidden" name="check_flag" value="">
<input type="hidden" name="serial_no" value="<%=serial_no %>">
<input type="hidden" name="modi_flag" value="<%=modi_flag %>">
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
						<td colspan=2><img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
		</tr>
		<tr>
			<td colspan="4">
				<hr noshade color="#808080" size="1">
			</td>
		<tr>
		<tr>
			<td width="100px" align="right" colspan="4"></td>
		</tr>
		<tr>
			<td  width="100px" align="right">礼物名称:</td>
			<td  width="*">
			 	<input name="giftName" disabled="disabled"  maxlength="6" size="20" value="<%=giftName %>" />
			</td>
			<td  width="100px" align="right">数量:</td>
			<td >
				<input name="giftNumber" disabled="disabled" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=giftNumber %>" />
			</td>
		</tr>
		<tr>
			<td  width="100px" align="right">礼物备注:</td>
			<td colspan="3">
				<TEXTAREA name="giftSummary" disabled="disabled" cols="80" rows="5"><%=giftSummary %></TEXTAREA>
			</td>
		</tr>
	</table>
		<div>
				<hr noshade color="#808080" size="1">
		</div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
		<tr class="trh">
			<td align="center">编号</td>
			<td align="center">客户名称</td>
			<td align="center">证件类型</td>
			<td align="center">证件号码</td>
			<td align="center">联系电话</td>
			<td align="center">发放数量</td>
			<td align="center">发放方式</td>
			<td align="center">发放日期</td>
			<td align="center">发放地</td>
		</tr>

<%
int iCount = 0;
int iCurrent = 0;
//取礼品分发给客户明细
list=customer.listGifiToCust(vo);
if (list!=null){
	Iterator it = list.iterator();
	while(it.hasNext()){
		Map map=(Map)it.next();	
 %>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td align="center"><%=Utility.trimNull(map.get("SERIAL_NO"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("MOBILE"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("GIFT_NUMBER"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("PROVIDE"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("PROVIDE_DADE"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
		</tr>
<%				iCurrent++;
				iCount++;
	}
	for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){

 %>
	<tr class="tr<%=(iCurrent % 2)%>">
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
<%	}
} %>
		<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
		</tr>
	</table>
	<table border="0" width="100%">
		<tr>
			<td  width="100px" align="right">审核意见:</td>
			<td ><TEXTAREA name="check_man_explain" cols="190" rows="5"><%=check_man_explain %></TEXTAREA>
			</td>
		</tr>
	</table>

		<div align="right" style="margin-right:5px">
				 <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:tochecked('2');">审核通过</button>
				 &nbsp;&nbsp;
				 <button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:tochecked('3');">审核驳回</button>
				 &nbsp;&nbsp;
				 <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">返回(<u>C</u>)</button>
		</div>
</form>
</BODY>
</HTML>
