<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得地址栏参数
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer cust_type = Utility.parseInt(request.getParameter("cust_type"), new Integer(0));
Integer contactType = Utility.parseInt(request.getParameter("contactType"), new Integer(0));
String iQuery = request.getParameter("iQuery");
//获得客户信息
String[] cust_info = new String[2];
String viewName = "客户名称";
if(contactType.intValue()==1){
	cust_info = Argument.getCustInfoById(cust_id);
	viewName = "渠道名称";
}
else if(contactType.intValue()==2){
	cust_info = Argument.getPartnerInfoById(cust_id);	
	viewName = "媒体名称";
}else if(contactType.intValue()==3){
	cust_info = Argument.getChannelInfoById(cust_id);	
	viewName = "渠道名称";
}

String cust_name = cust_info[0];
if(cust_type.intValue()==0){
	cust_type = Utility.parseInt(cust_info[1], new Integer(0));
}

//获得查询参数
String contactor = Utility.trimNull(request.getParameter("contactor"));
String phone = Utility.trimNull(request.getParameter("phone"));
String address = Utility.trimNull(request.getParameter("address"));
String contactWay = Utility.trimNull(request.getParameter("contactWay"));
int receiveContactWay = Utility.parseInt(request.getParameter("receiveContactWay"), 0);
int receiveServices = Utility.parseInt(request.getParameter("receiveServices"), 0);

//获得对象
CustomerContactLocal local = EJBFactory.getCustomerContact();
CustomerContactVO vo = new CustomerContactVO();
vo.setContactId(new Integer(0));
vo.setCust_id(cust_id);
vo.setContactor(contactor);
vo.setPhoneHome(phone);
vo.setAddress(address);
vo.setContactWay(contactWay);
vo.setReceiveContactWay(new Integer(receiveContactWay));
vo.setReceiveService(new Integer(receiveServices));
vo.setInsertMan(input_operatorCode);
vo.setContactType(contactType);

//获得联系人信息
IPageList pageList = local.queryCustomerContact(vo, Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

//声明参数
String tempUrl = "";
Map map = null;

//url设置
tempUrl = "&cust_id=" + cust_id + "&cust_type=" + cust_type + "&contactor=" + contactor + "&phone=" + phone 
			+ "&address=" + address + "&contactWay=" + contactWay + "&receiveContactWay=" 
			+ receiveContactWay + "&receiveServices=" + receiveServices;
sUrl = sUrl + tempUrl;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

/**创建、编辑新联系人**/
function newInfo(contact_id) {
	var contactType = document.getElementById("contactType").value;
	var url = "client_contact_new.jsp?cust_id="+<%=cust_id%>+"&cust_type="+<%=cust_type%>+"&contact_id="+contact_id+"&contactType="+contactType;
	if(showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0')){
		sl_update_ok();
		location.reload();
	}
}

/**在分页时组合查询条件以get的方式传值**/
function refreshPage() {
	var str = selected();
	var tempArray = str.split('#');
	disableAllBtn(true);
	location = 'client_contacts_list.jsp?page=1' 
					+ '&cust_id=' + document.theform.cust_id.value
					+ '&cust_type=' + document.theform.cust_type.value
					+ '&contactor=' + document.theform.contactor.value
					+ '&phone=' + document.theform.phone.value
					+ '&address=' + document.theform.address.value
					+ '&contactType=' + document.getElementById("contactType").value
					+ '&contactWay=' + document.theform.contactWay.value
					+ '&iQuery=' + document.getElementById("iQuery").value
					+ '&receiveContactWay=' + tempArray[0]
					+ '&receiveServices=' + tempArray[1];					
}
	
/**确定查询条件时条用**/
function StartQuery() {
	refreshPage();
}
	
/**返回**/
function onBack() {
	var contractType = document.getElementById("contactType").value;
	
	if(contractType==1){
		var tempArray = '<%=iQuery%>'.split('$');
		location.href = "client_info_list.jsp?no=" + tempArray[0] + "&name=" + tempArray[1] + "&product_id=" + tempArray[2]
						+ "&is_link=" + tempArray[3] + "&card_id=" + tempArray[4]
						+ "&page="+ tempArray[5] + "&pagesize=" + tempArray[6]+"&is_deal="+ tempArray[7]+"&cust_type="+ tempArray[8]
						+ "&true_flag="+ tempArray[9] + "&serv_man="+ tempArray[10]
						+ "&min_times="+ tempArray[11] + "&max_times=" + tempArray[12]
						+ "&min_total_money="+ tempArray[13] + "&max_total_money=" + tempArray[14]
						+ "&rg_start_date="+ tempArray[15] + "&rg_end_date=" + tempArray[16];
		
	} else if (contractType==2) {
		var cust_type = document.getElementsByName("cust_type")[0].value;
		if (cust_type==1){
			location = "<%=request.getContextPath()%>/client/partnerInfo/channel_info_list.jsp";
		} else if (cust_type==2){
			location = "<%=request.getContextPath()%>/client/partnerInfo/media_info_list.jsp";
		}
		
	} else if(contractType==3){
		location = "<%=request.getContextPath()%>/client/channel/channel_list.jsp";
	} else if(contractType==4){
		var tempArray = '<%=iQuery%>'.split('$');
		location = "<%=request.getContextPath()%>/prototype/client_cust_info_detail.jsp?cust_id=<%=cust_id%>&cust_level_name="+tempArray[0]
					+"&is_deal_name="+tempArray[1];
	}
}

/**获得选择的联系方式、服务的值**/
function selected() {
	var receiveContactWay = 0;
	var item_way = document.theform.receiveContactWay;
	for(var i=0;i<item_way.length;i++) {
		if(item_way[i].checked)
			receiveContactWay += parseInt(item_way[i].value);
	}
	
	var receiveServices = 0;
	var item_service = document.theform.receiveServices;
	for(var i=0;i<item_service.length;i++) {
		if(item_service[i].checked)
			receiveServices += parseInt(item_service[i].value);
	}
	return receiveContactWay+"#"+receiveServices;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="client_contact_remove.jsp">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="cust_type" value="<%=cust_type%>">
<input type="hidden" name="receiveContactWay" value="<%=receiveContactWay%>">
<input type="hidden" name="receiveServices" value="<%=receiveServices%>">
<input type="hidden" name="contactType" id="contactType" value="<%=contactType%>">
<input type="hidden" name="iQuery" id="iQuery" value="<%=iQuery%>">
<div id="queryCondition" class="qcMain"
	style="display: none; width: 630px;">
<table id="qcTitle" class="qcTitle" align="center" width="99%"
	cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
			onclick="javascript:cancelQuery();return false;"></button>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> :</td><!--联系人-->
		<td align="left">
			<input name="contactor" maxlength="100" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=contactor%>">
		</td>
		<td align="right"><%=LocalUtilis.language("class.phone",clientLocale)%> :</td><!--联系人电话-->
		<td align="left">
			<input name="phone" maxlength="100" size="25" onkeydown="javascript:nextKeyPress(this)" value="<%=phone%>">
		</td>
		
	</tr>
	<tr>
		<td colspan="6" align="left">
			<table>
				<tr>
					<td align="right"><%=LocalUtilis.language("class.receiveContactWay",clientLocale)%> :</td><!--接受的联系方式-->
					<td><%=Argument.getContactCheckBox(receiveContactWay)%></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="left" >
			<table>
				<tr>
					<td align="right" vAlign="top"><%=LocalUtilis.language("class.receiveServices",clientLocale)%> :</td><!--当前客户接收的服务类别-->
					<td><%=Argument.getServiceCheckBox(receiveServices)%></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.contactWay",clientLocale)%> :</td><!--首选联系方式-->
		<td align="left">
			<select name="contactWay" style="width:120px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getContactOption(new Integer(1109), contactWay)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.address3",clientLocale)%> :</td><!--联系人地址-->
		<td align="left">
			<input name="address" maxlength="100" size="35" onkeydown="javascript:nextKeyPress(this)" value="<%=address%>">
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();return false;"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确认-->
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=2 class="page-title"><font color="#215dc6"><b><%=Utility.trimNull(menu_info)%>>><%=LocalUtilis.language("class.contact",clientLocale)%> </b></font></td>
					</tr><!--联系人-->
					<tr>
						<td  align=right>
						<div class="btn-wrapper">
						<font color="#215dc6"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=Utility.trimNull(viewName)%> ：&nbsp;&nbsp;<%=cust_name %></b></font><!--客户名称-->
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton"
							name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button><!--查询-->
							&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.newContact",clientLocale)%> '
							onclick="javascript:newInfo(0);return false;"><%=LocalUtilis.language("message.append",clientLocale)%> (<u>N</u>)</button><!--创建新联系人--><!--新增-->
						&nbsp;&nbsp;&nbsp;
						<!--删除所选记录--><!--删除-->
                        <button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title='<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%>' onclick="javascript:if(confirmRemove(document.theform.selectbox)) document.theform.submit();return false;"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
						&nbsp;&nbsp;&nbsp;
                        <!--返回-->
						<button type="button"  class="xpbutton3" name="btnReturn" title='<%=LocalUtilis.language("message.back",clientLocale)%> '
							onclick="javascript:disableAllBtn(true);onBack();return false;"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>R</u>)</button>
						</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						
						<td align="center">
						<input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.contactor",clientLocale)%> </td><!--姓名-->
						<td align="center"><%=LocalUtilis.language("class.oTel",clientLocale)%> </td><!--办公电话-->
						<td align="center"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> </td><!--手机-->
						<td align="center"><%=LocalUtilis.language("class.email",clientLocale)%> </td><!--电子邮件-->
						<td align="center"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> </td><!--邮寄地址-->
						<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
					</tr>
					<%
				int iCount = 0;
				int iCurrent = 0;
				while(it.hasNext())
				{ 
					map = (Map)it.next();
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="selectbox"
									value="<%=map.get("ContactID")%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=Utility.trimNull(map.get("Contactor"))%></td>
							</tr>
						</table>
						</td>
					<td align="center"><%=Utility.trimNull(map.get("PhoneOffice"))%></td>
					<td align="center"><%=Utility.trimNull(map.get("Moblie"))%></td>
					<td align="center"><%=Utility.trimNull(map.get("Email"))%></td>
					<td align="center"><%=Utility.trimNull(map.get("Address"))%></td>
					<td align="center">
						<a href="javascript:#" onclick="javascript:newInfo(<%=Utility.trimNull(map.get("ContactID"))%>);return false; ">
							<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--编辑-->
						</a>
					</td>
				</tr>
				<%
					iCurrent++;
					iCount++;
				}
				for (; iCurrent < 9; iCurrent++){
				%>
				<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>		
				</tr>
				<%}
				%>
				<tr class="trbottom">
					<!--合计--><!--项-->
                    <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
				</tr>
				
			</table>
			<br>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
