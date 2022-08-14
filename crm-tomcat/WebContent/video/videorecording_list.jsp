<%@ page contentType="text/html; charset=GBK" import="java.math.*,java.util.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.contractManage.*,enfo.crm.vo.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String contractBH = Utility.trimNull(request.getParameter("contractBH"));
String productname = Utility.trimNull(request.getParameter("productname"));
String custname = Utility.trimNull(request.getParameter("custname"));
Integer checkflag = Utility.parseInt(request.getParameter("checkflag"),new Integer(0));

VideoRecordingBean vr = new VideoRecordingBean();
VideoRecordingVO vo = new VideoRecordingVO();
vo.setContract_BH(contractBH);
vo.setProductName(productname);
vo.setCustName(custname);
vo.setCheckFlag(checkflag);
vo.setInputMan(new Integer(0));
IPageList pagelist=vr.getVideoRecordingList(vo, Utility.parseInt(sPage,1), Utility.parseInt(sPagesize,10));
List list=null;
Map map=new HashMap();
if (pagelist!=null){
	list=pagelist.getRsList();
}
//sUrl += "&giftName=" + giftName ;
 %>
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script language=javascript>
function StartQuery() {
	refreshPage();
}

function refreshPage() {	
	//disableAllBtn(true);
	location.search = 'page=1&pagesize=' + document.theform.pagesize.value 
			+'&contractBH=' + document.theform.contractBH.value
			+'&productname=' + document.theform.productname.value
			+'&custname=' + document.theform.custname.value
			+'&checkflag=' + document.theform.checkflag.value;
}
function showInfo(contract_id){
	location='videorecording_check_info.jsp?&from=list&contract_id='+contract_id
			+'&menu_id='+ document.theform.menu_id.value
			+'page=1&pagesize=' + document.theform.pagesize.value 
			+'&contractBH=' + document.theform.contractBH.value
			+'&productname=' + document.theform.productname.value
			+'&custname=' + document.theform.custname.value
			+'&checkflag=' + document.theform.checkflag.value;
}
function checkInfo(contract_id){
	location='videorecording_check_info.jsp?&from=list&contract_id='+contract_id
			+'&menu_id='+ document.theform.menu_id.value
			+'page=1&pagesize=' + document.theform.pagesize.value 
			+'&contractBH=' + document.theform.contractBH.value
			+'&productname=' + document.theform.productname.value
			+'&custname=' + document.theform.custname.value
			+'&checkflag=' + document.theform.checkflag.value;
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="videorecording_list.jsp">
	<input type="hidden" name="menu_id" value="">
<div class="page-title">
	<font color="#215dc6"><b>˫¼��Ƶ�б�</b></font>
</div>
<br/>
<div id="queryCondition">
	&nbsp;<font size="2">��ͬ���</font><input name="contractBH" value='<%=contractBH%>' size="18">
	&nbsp;<font size="2">��Ʒ����</font><input name="productname" value='<%=productname%>' size="18">
	&nbsp;<font size="2">�ͻ�����</font><input name="custname" value='<%=custname%>' size="18">
	&nbsp;<font size="2">���״̬</font><select name="checkflag"><option value="0">��ѡ��</option>
		<option value="1">δ���</option>
		<option value="2" <%if (checkflag.intValue()==2) out.print(" selected"); %>>���ͨ��</option>
		<option value="3" <%if (checkflag.intValue()==3) out.print(" selected"); %>>δ����</option>
	</select>
	<input type="button" value="��ѯ" class="xpbutton3" onclick="javascript:StartQuery();">
</div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
	<tr class="trh">
		<td align="center">��Ʒ����</td>
		<td align="center">�ͻ�����</td>
		<td align="center">��ͬ���</td>
		<td align="center">�Ϲ����</td>
		<td align="center">��Ƶ�ϴ�ʱ��</td>
		<td align="center">��Ƶ�ϴ���</td>
		<td align="center">���״̬</td>
		<td align="center">�����</td>
		<td align="center">���ʱ��</td>
		<td align="center">�鿴</td>
		<td align="center">���</td>
	</tr>
<%
int iCount = 0;
int iCurrent = 0;
if (list!=null && list.size()>0){
	Integer chgflag=new Integer(0);
	String chgflagName="";
	for (int i=0;i<list.size();i++){
		map=(Map)list.get(i);
		chgflag=(Integer)map.get("CheckFlag");
		if (chgflag==null) chgflagName="";
		else if (chgflag.intValue()==1) chgflagName="δ���";
		else if (chgflag.intValue()==2) chgflagName="���ͨ��";
		else if (chgflag.intValue()==3) chgflagName="��˷��";
 %>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td align="center"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td>
			<td align="center"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("RG_MONEY")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("InputTime")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("InputManName")) %></td>
			<td align="center"><%=chgflagName %></td>
			<td align="center"><%=Utility.trimNull(map.get("CheckManName")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("CheckTime")) %></td>
			<td align="center">
				<a href="javascript:showInfo('<%=Utility.trimNull(map.get("ContractID"))%>')" >
					<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='�鿴' />
				</a>
			</td>
			<td align="center"><% if (chgflag.intValue()==1){ %>
				<a href="javascript:checkInfo('<%=Utility.trimNull(map.get("ContractID"))%>')" >���</a>
				<%}else{ %>
				<a href="javascript:checkInfo('<%=Utility.trimNull(map.get("ContractID"))%>')" >��˳���</a>
				<%} %>
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
	<%}
	}%>
</table>
<div><%if (list!=null){
	out.print(pagelist.getPageLink(sUrl));
} %>
</div>

</form>
</BODY>
</HTML>
