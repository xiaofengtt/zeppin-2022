<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String giftName = Utility.trimNull(request.getParameter("giftName"));
Integer gift_id = Utility.parseInt(request.getParameter("gift_id"),new Integer(0));

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
vo.setGift_name(giftName);
vo.setGift_id(gift_id);

IPageList pageList = customer.listGifiPutin(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
Iterator it = pageList.getRsList().iterator(); 

sUrl += "&giftName=" + giftName ;

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

function StartQuery() {
	refreshPage();
}
function refreshPage() {	
	disableAllBtn(true);
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value 
				+'&giftName=' + document.theform.giftName.value ;
}

//返回礼品列表页面
function giftlist(){
	 location="gift_info_list.jsp?giftName=<%=giftName%>";
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="POST" action="gift_moveout_list.jsp">
<input type="hidden" name="serial_no" value="">
<input type="hidden" name="gift_id" value="<%=gift_id %>">
<div id="queryCondition" class="qcMain"	style="display: none; width: 225px">
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
		<td>
			<input name="giftName" value='<%=giftName%>' onkeydown="javascript:nextKeyPress(this)" size="18">
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
				<button class="xpbutton3" name="btnCancel" onclick="javascript:giftlist();">返回</button>
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
							<td align="center">
								序号
						    </td>
							<td align="center">名称</td>
							<td align="center">入库数量</td>
							<td align="center">礼物备注</td>
							<td align="center">入库人</td>
							<td align="center">入库日期</td>
					</tr>
<% 
int iCount = 0;
int iCurrent = 0;
Integer number=new Integer(0);
Map map=null;
while(it.hasNext()){
	map=(Map)it.next();	
	number = Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) ;
	
%>				
					<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center">
							 	<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(map.get("PUT_IN_ID"))%></td>
									</tr>
								</table>
							</td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_NAME")) %> </td>
							<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) %></td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_SUMMARY")) %></td>
							<td align="center"><%=Utility.trimNull(map.get("INPUT_MAN_NAME")) %></td>
							<td align="center"><%=Utility.trimNull(map.get("INPUT_TIME")) %></td>
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
					</tr>
				<%} %>	
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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
