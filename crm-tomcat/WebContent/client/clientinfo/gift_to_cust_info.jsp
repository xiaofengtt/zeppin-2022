<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %> 
<%
String giftName = Utility.trimNull(request.getParameter("giftName"));
String custName = Utility.trimNull(request.getParameter("custName"));
Integer moveoutId = Utility.parseInt(request.getParameter("moveoutId"),new Integer(0));
Integer check_flags = Utility.parseInt(request.getParameter("check_flags"),new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),null);
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setGift_name(giftName);
vo.setCust_name(custName);

int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
//IPageList pageList = first_flag!=1? customer.listGifitoCustAll(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8))
//								  : new JdbcPageList();
IPageList pageList = new JdbcPageList();
Iterator it = first_flag!=1? pageList.getRsList().iterator(): new ArrayList().iterator(); 

sUrl += "&giftName=" + giftName+"&custName="+custName ;
boolean bSuccess1 = false;
if(request.getMethod().equals("POST")){
	vo.setMove_out_id(moveoutId);
	vo.setCust_id(cust_id);
	vo.setInput_man(input_operatorCode);
	vo.setCheck_flag(check_flags);
	customer.addGifitoCust(vo);
	bSuccess1 = true;
}

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
<%if(bSuccess1){%>
alert("审核成功！");
location="gift_to_cust_info.jsp";
<%}%>

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
				+'&custName='+ document.theform.custName.value;
}
function tocheck(serino,check_flag,move_out_id,cust_id){
		if(check_flag==2){
			document.theform.check_flags.value=3;
		}else{
			document.theform.check_flags.value=2;
		}
		document.theform.moveoutId.value=move_out_id;
		document.theform.cust_id.value=cust_id;
		disableAllBtn(true);
		location="gift_to_cust_info.jsp";
		document.theform.submit();
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="gift_to_cust_info.jsp">
<INPUT type="hidden" name="check_flags" value="">
<INPUT type="hidden" name="moveoutId" value="">
<INPUT type="hidden" name="cust_id" value="">
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
		<td>
			<input name="giftName" value='<%=giftName%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
		<td valign="bottom" align="right">客户名称</td>
		<td>
			<input name="custName" value='<%=custName%>' onkeydown="javascript:nextKeyPress(this)" size="18">
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
							<td align="center">
								序号
						    </td>
							<td align="center">客户名称</td>
							<td align="center">礼物名称</td>
							<td align="center">发放数量</td>
							<td align="center">礼物备注</td>
							<td align="center">发放日期</td>
							<td align="center">审核标志</td>
							<td align="center">审核</td>
					</tr>
<% 
int iCount = 0;
int iCurrent = 0;
String   provide_dade ;
Integer check_flag=new Integer(0);
while(it.hasNext()){
	Map map=(Map)it.next();
	 provide_dade = Utility.trimNull(map.get("PROVIDE_DADE"));
	 check_flag =Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));	
	%>				
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"><%=Utility.trimNull(map.get("SERIAL_NO"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("GIFT_NAME")) %></td>
						<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) %></td>
						<td align="center"><%=Utility.trimNull(map.get("GIFT_SUMMARY")) %></td>
						<td align="center"><%=provide_dade %></td>	
						<td align="center"><%=check_flag.intValue()==2?"审核通过":check_flag.intValue()==3?"审核不通过":"未审核" %></td>	
						<td align="center">
							<a href="javascript:#" onclick="javascript:tocheck(<%=map.get("SERIAL_NO")%>,<%=check_flag %>,<%=map.get("MOVE_OUT_ID") %>,<%=map.get("CUST_ID") %>);return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='审核 ' />
							</a>
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
					</tr>
				<%} %>
				<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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
