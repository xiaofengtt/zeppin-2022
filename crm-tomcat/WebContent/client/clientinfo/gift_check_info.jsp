<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String giftName = Utility.trimNull(request.getParameter("giftName"));
String custName = Utility.trimNull(request.getParameter("custName"));
Integer moveoutId = Utility.parseInt(request.getParameter("moveoutId"),new Integer(0));
Integer check_flag1 = Utility.parseInt(request.getParameter("check_flag1"),new Integer(0));
Integer check_flag2 = Utility.parseInt(request.getParameter("check_flag2"),new Integer(0));


CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
vo.setGift_name(giftName);
vo.setCheck_flag1(check_flag1);
vo.setCheck_flag2(check_flag2);

int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
IPageList pageList = first_flag!=1? customer.listGifimoveoutAll(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8))
								  : new JdbcPageList();
Iterator it = first_flag!=1? pageList.getRsList().iterator(): new ArrayList().iterator(); 

sUrl += "&giftName=" + giftName+"&check_flag1="+check_flag1+"&check_flag2="+check_flag2 ;


 %>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
};
function StartQuery() {
	refreshPage();
}
function refreshPage() {	
	disableAllBtn(true);
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value 
				+'&giftName=' + document.theform.giftName.value 
				+'&check_flag1='+document.theform.check_flag1.value
				+'&check_flag2='+document.theform.check_flag2.value;
}
function tocheck(move_out_id,serial_no,modi_flag){
	var ret = 0;
	var URL="gift_to_checked.jsp?move_out_id="+move_out_id+"&modi_flag="+modi_flag+"&serial_no="+serial_no;
	//ret = showModalDialog(URL,'','dialogWidth:650px;dialogHeight:450px;status:0;help:0');	
	//if (ret==1) location.reload();
	location=URL;
}

</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="gift_check_info.jsp">
<div id="queryCondition" class="qcMain"	style="display: none; width: 525px">
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right">
			<button class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right">礼物名称</td>
		<td colspan="3">
			<input name="giftName" value='<%=giftName%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
	</tr>
	<tr>	
		<td valign="bottom" align="right">初审核标志</td>
		<td>
			<select name="check_flag1">
				<option>请选择</option>
				<option value="<%=new Integer(1) %>">未审核</option>	
				<option value="<%=new Integer(2) %>">审核通过 </option>	
				<option value="<%=new Integer(3) %>">审核不通过 </option>	
			</select>
		</td>
		<td valign="bottom" align="right">审核标志</td>
		<td >
			<select name="check_flag2">
				<option>请选择</option>	
				<option value="<%=new Integer(1) %>">未审核</option>	
				<option value="<%=new Integer(2) %>">审核通过 </option>	
				<option value="<%=new Integer(3) %>">审核不通过 </option>	
			</select>
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
		<tr>
						<td colspan=2><img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
		</tr>
		<tr>
			<td align="right">

				<%if (input_operator.hasFunc(menu_id, 108)) {	%>
					<button class="xpbutton3" accessKey=q id="queryButton"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
								&nbsp;&nbsp;&nbsp;
					<%} %>
			</td>
		</tr>
		<tr>
			<td colspan=2>
			<hr noshade color="#808080" size="1">
			</td>
					
		<tr>
		<tr>
			<TD>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
							<td align="center">名称</td>
							<td align="center">领取数量</td>
							<td align="center">领取备注</td>
							<td align="center">领取人</td>
							<td align="center">申领时间</td>
							<td align="center">初审标志</td>
							<td align="center">复审标志</td>
							<td align="center">初审</td>
							<td align="center">复审</td>
					</tr>
<% 
int iCount = 0;
int iCurrent = 0;
Integer check_flag=new Integer(0);
Integer check_flags=new Integer(0);
String  check_summary="";
while(it.hasNext()){
	Map map=(Map)it.next();	
	check_flag =Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG1")),new Integer(0));
	check_flags =Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG2")),new Integer(0));
	Integer modi_flag=new Integer(0);
	
%>				
					<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center"><%=Utility.trimNull(map.get("GIFT_NAME")) %> </td>
							<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) %></td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_SUMMARY")) %></td>
							<td align="center"><%=Utility.trimNull(map.get("INPUT_MAN_NAME")) %> </td>
							<td align="center"><%=Utility.trimNull(map.get("INPUT_TIME")) %> </td>
							<td align="center"><%=check_flag.intValue()==2?"初审通过":check_flag.intValue()==3?"初审不通过":"未审核" %></td>
							<td align="center"><%=check_flags.intValue()==2?"审核通过":check_flags.intValue()==3?"审核不通过":"未审核" %></td>
							<td align="center">
							<%if (input_operator.hasFunc(menu_id, 103) && check_flag.intValue()<2) { %>
								<a href="javascript:#" onclick="javascript:tocheck(<%=map.get("MOVE_OUT_ID")%>,<%=map.get("SERIAL_NO") %>,1);return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='审核 ' />
								</a>
							<%} %>
							</td>
							<td align="center">
							<%if (input_operator.hasFunc(menu_id, 104) && check_flag.intValue()==2 && check_flags.intValue()<2) { %>
								<a href="javascript:#" onclick="javascript:tocheck(<%=map.get("MOVE_OUT_ID")%>,<%=map.get("SERIAL_NO") %>,2);return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='审核 ' />
								</a>
							<%} %>
							</td>
					</tr>

<% iCurrent++;
 		iCount++;
	}
 				for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){%>
					<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
					</tr>
				<%} %>	
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>

			</TD>
		</tr>

</TABLE>

</form>
</BODY>
</HTML>
