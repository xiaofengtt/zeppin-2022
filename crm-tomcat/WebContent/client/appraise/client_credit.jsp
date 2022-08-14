<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<!--
��������:����ĳЩ������ѡ��Ŀͻ�������������
����ģ��:�ͻ�����>>�ͻ���������
����:zhangmy
����:20091202
-->
<%
//��ò���
String sCust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));

String cust_sort = Utility.trimNull(request.getParameter("cust_sort"));//�ͻ����
String cust_type = Utility.trimNull(request.getParameter("cust_type"));//�ͻ�����
String cust_level = Utility.trimNull(request.getParameter("cust_level"));//�ͻ��ȼ�
String service_man = Utility.trimNull(request.getParameter("service_man"));//�ͻ�����
String cust_source = Utility.trimNull(request.getParameter("cust_source"));//�ͻ���Դ
String earning_low = Utility.trimNull(request.getParameter("earning_low"));//�������ֵ
String earning_high = Utility.trimNull(request.getParameter("earning_high"));//�������ֵ

//��ö���
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setCust_id(new Integer(0));
vo.setCust_no(sCust_no);
vo.setCust_name(cust_name);
vo.setCheck_flag(new Integer(0));
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setProv_level("");

//ҳ�����
Map map = null;
IPageList pageList =
	customer.listProcAll(
		vo,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

//url����
sUrl =
	sUrl
		+ "&cust_name="
		+ cust_name
		+ "&cust_no="
		+ sCust_no
		+ "&product_id="
		+ product_id;
%>
<HTML>
<HEAD>
<TITLE></TITLE><!--client_credit.jsp-->
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
initQueryCondition()};
/**�ͻ���Ϣ�༭**/
function showInfo(cust_id,cust_type)
{
	var iQuery = document.theform.cust_no.value 
					+ "@" + document.theform.cust_name.value 
					+ "@" + document.theform.product_id.value 
					+ "@" + "<%=sPage%>" + "@" + document.theform.pagesize.value;
	
		if(cust_type == 1)							
			location = '<%=request.getContextPath()%>/client/clientinfo/client_info_new.jsp?cust_id=' + cust_id + '&iQuery=' + iQuery;
		else if(cust_type == 2)
			location = '<%=request.getContextPath()%>/client/clientinfo/clinet_info_new_end.jsp?cust_id=' + cust_id + '&iQuery=' + iQuery;
}

//�ͻ���������
function assessLeve()
{	
	if(confirmAudit(document.theform.cust_id))
	{
	//����ѡ��Ŀͻ�ѭ����������
	//�������õ����ݲ�ѯ����
		sl_alert('<%=LocalUtilis.language("message.assessedLevel",clientLocale)%> ');//����ѡ�ͻ�����Ϊĳ����
	}
}

/**�ͻ���Ϣ���**/
function newInfo(cust_type)
{
	var iQuery = document.theform.cust_no.value 
					+ "$" + document.theform.cust_name.value 
					+ "$" + document.theform.product_id.value 
					+ "$" + "<%=sPage%>" + "$" + document.theform.pagesize.value;
	
	//����ǻ����ͻ�
	if(cust_type == 2)						
		location = '<%=request.getContextPath()%>/client/clientinfo/clinet_info_new_end.jsp?iQuery=' + iQuery;
	else if(cust_type == 1)
		location = '<%=request.getContextPath()%>/client/clientinfo/client_info_new.jsp?iQuery=' + iQuery;
}

function StartQuery()
{
	{	
		disableAllBtn(true);
		var url = 'client_credt.jsp?page=1&pagesize=' + document.theform.pagesize.value 
						+'&cust_name=' + document.theform.cust_name.value 
						+ '&cust_no=' + document.theform.cust_no.value 
						+ '&product_id=' + document.theform.product_id.value;
		location = url;
	}	
}

function refreshPage()
{
	disableAllBtn(true);
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_info_list.jsp?page=1&pagesize=' + document.theform.pagesize.value 
				+'&cust_name=' + document.theform.cust_name.value 
				+ '&cust_no=' + document.theform.cust_no.value
				+ '&product_id=' + document.theform.product_id.value ;
	location = url;				
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="<%=request.getContextPath()%>/client/clientinfo/customer_remove.jsp">
<div id="queryCondition" class="qcMain"
	style="display: none; width: 620px">
<table id="qcTitle" class="qcTitle" align="center" width="99%"
	cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
			onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--�ͻ����-->
		<td align="left"><select size="1" name="cust_sort"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getJghyOptions2("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.custType",clientLocale)%> :</td><!--�ͻ�����-->
		<td align="left"><select size="1" name="cust_type"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getJghyOptions2("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> :</td><!--�ͻ�����-->
		<td align="left"><select size="1" name="cust_level"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getQualityLevelOptions("")%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
		<td align="left" colspan="3"><input type="text" name="cust_name"
			onkeydown="javascript:nextKeyPress(this)" size="65"
			value="<%=cust_name%>"></td>
		<td align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
		<td align="left"><input type="text" name="cust_no"
			onkeydown="javascript:nextKeyPress(this)" size="15"
			value="<%=Utility.trimNull(sCust_no.trim())%>"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--�ͻ�����-->
		<td align="left"><select size="1" name="service_man"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getOperator_Value(new Integer(0))%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.custCredit",clientLocale)%> :</td><!--�ͻ����ö�-->
		<td align="left"><select size="1" name=""
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCreditLevelOptions("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.satisfaction",clientLocale)%> :</td><!--�ͻ������-->
		<td align="left"><select size="1" name=""
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCreditLevelOptions("")%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerSource",clientLocale)%> :</td><!--�ͻ���Դ-->
		<td align="left" colspan="3"><select size="1" name="cust_source"
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCustomerSourceOptions("")%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.liveness",clientLocale)%> :</td><!--�ͻ���Ծ��-->
		<td align="left"><select size="1" name=""
			onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
			<%=Argument.getCustomerSourceOptions("")%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.earning",clientLocale)%> :</td><!--����ˮƽ-->
		<td align="left"><input type="text" name="earning_low"
			onkeydown="javascript:nextKeyPress(this)" size="15">
		</td>
		<td align="left"><input type="text" name="earning_high"
			onkeydown="javascript:nextKeyPress(this)" size="15">
		</td>
	</tr>
	<tr>
		<td align="right" colspan="6">
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
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
						<td colspan=3><img src="<%=request.getContextPath()%>/images/member.gif" align="absBottom" border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td colspan="3">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
					<tr>
						<td><%=LocalUtilis.language("class.gradeLevel2",clientLocale)%> :</td><!--��������Ϊ-->
						<td><select size="1" name="grade_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
								<%=Argument.getCreditLevelOptions("")%>
							</select>
						</td>
						<td align="right"><%if (input_operator.hasFunc(menu_id, 108)) {%>
						<!--  ��ѯ -->
                        <button type="button"  class="xpbutton3" accessKey=f id="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}
						if (input_operator.hasFunc(menu_id, 108)) {%>
						<!--����-->
                        <button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew"title='<%=LocalUtilis.language("message.assess",clientLocale)%>' onclick="javascript:assessLeve();"><%=LocalUtilis.language("message.assess",clientLocale)%> (<u>A</u>)</button>
						&nbsp;&nbsp;&nbsp;<%}%></td>
					</tr>
					<tr>
						<td colspan=3>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center"><input type="checkbox" name="btnCheckbox"
							class="selectAllBox"
							onclick="javascript:selectAllBox(document.theform.cust_id,this);"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--���-->
						<td align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--�ͻ�����-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--�ͻ����-->
						<td align="center"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> </td><!--�ͻ�����-->
						<td><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!--�ͻ�����-->
						<td><%=LocalUtilis.language("class.liveness",clientLocale)%> </td><!--�ͻ���Ծ��-->
						<td><%=LocalUtilis.language("class.customerSource",clientLocale)%> </td><!--�ͻ���Դ-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						Integer cust_id = new Integer(0);
						while (it.hasNext()) {
							map = (Map) it.next();
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="cust_id"
									value="<%=map.get("CUST_ID")%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
							</tr>
						</table>
						</td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="right"><%=Utility.trimNull(map.get("CUST_LEVEL_NAME"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("SERVICE_MAN"))%></td>
						<td align="left"><%=Utility.trimNull(map.get(""))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>
					</tr>
					<%iCurrent++;
					iCount++;
					}%>

					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--�ϼ�--><!--��-->
                        <td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%><%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>

					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();
%>
