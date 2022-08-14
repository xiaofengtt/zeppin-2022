<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String giftName = Utility.trimNull(request.getParameter("giftName"));
Integer giftId = Utility.parseInt(request.getParameter("giftId"),new Integer(0));


CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
vo.setGift_id(giftId);
vo.setGift_name(giftName);
//页面变量
int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
IPageList pageList = first_flag!=1? customer.listGifiAll(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8))
								  : new JdbcPageList();
Iterator it = first_flag!=1? pageList.getRsList().iterator(): new ArrayList().iterator();

sUrl += "&giftName=" + giftName ;
boolean bSuccess1 = false;
if (request.getMethod().equals("POST") ){
	boolean bSuccess = false;
	String[] s = request.getParameterValues("giftId");
	Integer serial_no;
	if (s != null){
		for(int i = 0;i < s.length; i++){
			serial_no = Utility.parseInt(s[i], null);
			if(serial_no != null){
				vo.setGift_id(serial_no);
				vo.setInput_man(input_operatorCode);	
				customer.deleteGifi(vo);
				bSuccess1 = true;
			}
		}
	}
}
customer.remove();

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
alert("删除成功！");
location="gift_info_list.jsp";
<%}%>

window.onload = function(){
		initQueryCondition();
};

function StartQuery() {
	refreshPage();
}

function refreshPage() {	
	disableAllBtn(true);
	location.search = 'page=1&pagesize=' + document.theform.pagesize.value 
				+'&giftName=' + document.theform.giftName.value ;
}
function showInfo(gifi_id){
	var ret = 0;
	var URL="gift_info_add.jsp?giftId="+gifi_id;
	ret = showModalDialog(URL,'','dialogWidth:550px;dialogHeight:350px;status:0;help:0');	
	window.location.reload();
}

function newInfo(){
 showModalDialog('gift_info_add.jsp','','dialogWidth:550px;dialogHeight:350px;status:0;help:0');
	window.location.reload();	
}
function inDetail(gid){
	location='<%=request.getContextPath()%>/client/clientinfo/gift_putin_list.jsp?gift_id='+gid;
}
function outDetail(gid,gnumber){
	location='<%=request.getContextPath()%>/client/clientinfo/gift_moveout_list.jsp?gift_id='+gid+'&gift_number='+gnumber;
}

function moveoutGift(gift_id,gift_name,gift_number,detail_info){
	if(gift_number==0){
		alert("库存不足，不能领用!");
		return false;
	}
	
	var url="gift_moveout_add.jsp?gift_id="+gift_id+"&giftName="+gift_name+"&giftNumbers="+gift_number+"&detailInfo="+detail_info;
	var ret = showModalDialog(url,'','dialogWidth:550px;dialogHeight:350px;status:0;help:0');	
	if (ret != null){
		location.href= "gift_to_cust_add.jsp?move_out_id="+ret;
	}
	
}

</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="gift_info_list.jsp">

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

					<button class="xpbutton3" accessKey=q id="queryButton"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
								&nbsp;&nbsp;&nbsp;
	                <%if (input_operator.hasFunc(menu_id, 130)) {%><button class="xpbutton3" accessKey=n name="inDetail" title='入库明细 '
								onclick="javascript:inDetail();">入库明细 </button>&nbsp;&nbsp;&nbsp;
					<%} %>
					<%if (input_operator.hasFunc(menu_id, 102)) {%><button class="xpbutton3" accessKey=n name="btnNew" title='新增加礼品种类'
								onclick="javascript:newInfo();">新建 </button>
								&nbsp;&nbsp;&nbsp;
					<%} %>
	
			</td>
		</tr>
		<tr>
			<td colspan=2>
			<hr noshade color="#808080" size="1">
			</td>
					
		<tr>
			<TD>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
							<td align="center">
								名称
						    </td>
							<td align="center">库存数</td>
							<td align="center">详细描述</td>
							<td align="center">备注</td>
							<td align="center">明细</td>
							<td align="center">编辑</td>
							<td align="center">领用</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
while(it.hasNext()){
	Map map=(Map)it.next();	

 %>
					<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center">
							 	<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(map.get("GIFT_NAME"))%></td>
									</tr>
								</table>
							</td>
							<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) %></td>
							<td align="center"><%=Utility.trimNull(map.get("DETAIL_INFO")) %></td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_SUMMARY")) %></td>
							<td align="center">
				<%if (input_operator.hasFunc(menu_id, 102)) {%>
								<a href="javascript:#" onclick="javascript:inDetail(<%=map.get("GIFT_ID")%>);return false; ">入库</a>&nbsp;&nbsp;
								<a href="javascript:#" onclick="javascript:outDetail(<%=map.get("GIFT_ID")%>,<%=map.get("GIFT_NUMBER") %>);return false; ">出库</a>
				<%} %>
							</td>
							<td align="center">
				<%if (input_operator.hasFunc(menu_id, 102)) {%>
								<a href="javascript:#" onclick="javascript:showInfo(<%=map.get("GIFT_ID")%>);return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--编辑-->
								</a>
				<%} %>
							</td>
								<td align="center">
								<a href="javascript:#" onclick="javascript:moveoutGift('<%=map.get("GIFT_ID")%>','<%=map.get("GIFT_NAME") %>','<%=map.get("GIFT_NUMBER") %>','<%=map.get("DETAIL_INFO") %>');return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='领用' /><!--领用-->
								</a>
							</td>
					</tr>
<%		iCurrent++;
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
					</tr>
			<%} %>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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
