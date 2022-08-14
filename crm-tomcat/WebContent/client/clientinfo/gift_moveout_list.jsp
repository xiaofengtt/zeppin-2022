<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String giftName = Utility.trimNull(request.getParameter("giftName"));
String deleteflag=Utility.trimNull(request.getParameter("deleteflag"));
Integer moveoutId = Utility.parseInt(request.getParameter("moveoutId"),new Integer(0));
Integer gift_id = Utility.parseInt(request.getParameter("gift_id"),new Integer(0));
Integer gift_number = Utility.parseInt(request.getParameter("gift_number"),new Integer(0)); //�����

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
vo.setGift_name(giftName);
vo.setGift_id(gift_id);

IPageList pageList = customer.listGifimoveoutAll(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
Iterator it = pageList.getRsList().iterator(); 

sUrl += "&giftName=" + giftName +"&gift_id="+gift_id+"&gift_number="+gift_number;

boolean bSuccess1 = false;
if (request.getMethod().equals("POST") &&"1".equals(deleteflag)){
	vo.setMove_out_id(Utility.parseInt(request.getParameter("serial_no"),new Integer(0)));
	vo.setInput_man(input_operatorCode);	
	customer.deleteGifimove(vo);
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
alert("ɾ���ɹ���");
location="gift_moveout_list.jsp?gift_id=<%=gift_id%>";
<%}%>


function StartQuery() {
	refreshPage();
}
function refreshPage() {	
	disableAllBtn(true);
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value 
				+'&giftName=' + document.theform.giftName.value
				+'&gift_id=' + document.theform.gift_id.value
				+'&gift_number=' + document.theform.gift_number.value;
}
function toshow(move_id,gift_name,gift_number){//alert(<%=menu_id%>);
	var ret = 0;
	var URL="gift_moveout_add.jsp?gift_number=<%=gift_number%>&move_id="+move_id+"&giftName="+gift_name+"&giftNumbers="+gift_number;
	ret = showModalDialog(URL,'','dialogWidth:580px;dialogHeight:450px;status:0;help:0');	
	if (ret==1) location.reload();
}
function gifttocust(move_out_id,gift_name,gift_number){
	 location="gift_to_cust_add.jsp?move_out_id="+move_out_id+"&giftName="+gift_name+"&giftNumber="+gift_number;
}
//ɾ������
function deletego(gid){
	if (!confirm("ɾ���󽫲��ɻָ���ȷ��Ҫɾ����")){
		return false;
	}
	theform.deleteflag.value="1";
	theform.serial_no.value=gid;
	disableAllBtn(true);
	theform.submit();
}
//��˳���
function checkgo(gid,modi_flag){
	location="gift_to_checked.jsp?gift_id=<%=gift_id%>&move_out_id="+gid+"&modi_flag="+modi_flag+"&lnk_from=gift_manage";
}
//������Ʒ�б�ҳ��
function giftlist(){
	 location="gift_info_list.jsp?giftName=<%=giftName%>";
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="POST" action="gift_moveout_list.jsp">
<input type="hidden" name="deleteflag" value="0">
<input type="hidden" name="serial_no" value="">
<input type="hidden" name="gift_id" value="<%=gift_id %>">
<input type="hidden" name="gift_number" value="<%=gift_number %>">
<div id="queryCondition" class="qcMain"	style="display: none; width: 225px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
		<td align="right">
		<button class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right">��������</td>
		<td>
			<input name="giftName" value='<%=giftName%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
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
				<button class="xpbutton3" name="btnCancel" onclick="javascript:giftlist();">����</button>
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
								���
						    </td>
							<td align="center">����</td>
							<td align="center">��������</td>
							<td align="center">���ﱸע</td>
							<td align="center">��ȡ��</td>
							<td align="center">����</td>
							<td align="center">����</td>
							<td align="center">�༭</td>
							<td align="center">����</td>
					</tr>
<% 
int iCount = 0;
int iCurrent = 0;
Integer number=new Integer(0);
String checkflag1="";
String checkflag2="";
int checkflag=0;
int checkflags=0;
Map map=null;
while(it.hasNext()){
	map=(Map)it.next();	
	number = Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) ;
	//����״̬
	checkflag=Utility.parseInt((Integer)map.get("CHECK_FLAG1"),new Integer(0)).intValue();
	switch (checkflag){
		case 0:checkflag1="δ���";break;
		case 1:checkflag1="δ���";break;
		case 2:checkflag1="�����ͨ��";break;
		case 3:checkflag1="����˲���";break;
		default:checkflag1="δ֪";break;
	}
	//����״̬
	checkflags=Utility.parseInt((Integer)map.get("CHECK_FLAG2"),new Integer(0)).intValue();
	switch (checkflags){
		case 0:checkflag2="δ���";break;
		case 1:checkflag2="δ���";break;
		case 2:checkflag2="�����ͨ��";break;
		case 3:checkflag2="����˲���";break;
		default:checkflag2="δ֪";break;
	}
%>				
					<tr class="tr<%=(iCurrent % 2)%>">
							<td align="center">
							 	<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(map.get("MOVE_OUT_ID"))%></td>
									</tr>
								</table>
							</td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_NAME")) %> </td>
							<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("GIFT_NUMBER")),new Integer(0)) %></td>
							<td align="center"><%=Utility.trimNull(map.get("GIFT_SUMMARY")) %></td>
							<td align="center"><%=Utility.trimNull(map.get("INPUT_MAN_NAME")) %></td>
							<td align="center"><div title="<%=Utility.trimNull(map.get("CHECK_MAN1_EXPLAIN")) %>"><%=checkflag1 %>
								<%if (input_operator.hasFunc(menu_id, 103) && checkflag<2){%>
								&nbsp;&nbsp;<a href="javascript:#" onclick="javascript:checkgo('<%=map.get("MOVE_OUT_ID")%>',1); "><img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title='��� ' /></a>
								<%} %></div></td>
							<td align="center"><div title="<%=Utility.trimNull(map.get("CHECK_MAN2_EXPLAIN")) %>"><%=checkflag2 %>
								<%if (input_operator.hasFunc(menu_id, 104) && checkflag==2 && checkflags<2){%>
								&nbsp;&nbsp;<a href="javascript:#" onclick="javascript:checkgo('<%=map.get("MOVE_OUT_ID")%>',2); "><img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title='��� ' /></a>
								<%} %></div></td>
							<td align="center">
								<%if (input_operator.hasFunc(menu_id, 102)) {%>		
								<a href="javascript:#" onclick="javascript:toshow('<%=map.get("MOVE_OUT_ID")%>','<%=map.get("GIFT_NAME") %>','<%=map.get("GIFT_NUMBER") %>');return false; ">
									<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--�༭--></a>
								<%} %>
								<%if (input_operator.hasFunc(menu_id, 101)) {%>
								&nbsp;&nbsp;<a href="javascript:#" onclick="javascript:deletego('<%=map.get("MOVE_OUT_ID")%>'); "><img src="<%=request.getContextPath()%>/images/delete.gif" width="16" height="16" title='ɾ�� ' /></a>
								<%} %>
							</td>
				<%if (input_operator.hasFunc(menu_id, 100)) {		
						if(number.intValue()>0){ %>
								<td align="center">
									<a href="javascript:#" onclick="javascript:gifttocust('<%=map.get("MOVE_OUT_ID")%>','<%=map.get("GIFT_NAME") %>','<%=map.get("GIFT_NUMBER") %>');return false; ">
										<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='������Ʒ ' />
									</a>
								</td>
						<%}else{ %>	
							<td align="center" title="�����治�㣬���ܷ���">
								
							</td>	
						<%} 
					
				} %>
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
						<!--�ϼ�--><!--��-->
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
