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
	<font color="#215dc6"><b>双录视频列表</b></font>
</div>
<br/>
<div id="queryCondition">
	&nbsp;<font size="2">合同编号</font><input name="contractBH" value='<%=contractBH%>' size="18">
	&nbsp;<font size="2">产品名称</font><input name="productname" value='<%=productname%>' size="18">
	&nbsp;<font size="2">客户名称</font><input name="custname" value='<%=custname%>' size="18">
	&nbsp;<font size="2">审核状态</font><select name="checkflag"><option value="0">请选择</option>
		<option value="1">未审核</option>
		<option value="2" <%if (checkflag.intValue()==2) out.print(" selected"); %>>审核通过</option>
		<option value="3" <%if (checkflag.intValue()==3) out.print(" selected"); %>>未审否决</option>
	</select>
	<input type="button" value="查询" class="xpbutton3" onclick="javascript:StartQuery();">
</div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
	<tr class="trh">
		<td align="center">产品名称</td>
		<td align="center">客户名称</td>
		<td align="center">合同编号</td>
		<td align="center">认购金额</td>
		<td align="center">视频上传时间</td>
		<td align="center">视频上传人</td>
		<td align="center">审核状态</td>
		<td align="center">审核人</td>
		<td align="center">审核时间</td>
		<td align="center">查看</td>
		<td align="center">审核</td>
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
		else if (chgflag.intValue()==1) chgflagName="未审核";
		else if (chgflag.intValue()==2) chgflagName="审核通过";
		else if (chgflag.intValue()==3) chgflagName="审核否决";
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
					<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='查看' />
				</a>
			</td>
			<td align="center"><% if (chgflag.intValue()==1){ %>
				<a href="javascript:checkInfo('<%=Utility.trimNull(map.get("ContractID"))%>')" >审核</a>
				<%}else{ %>
				<a href="javascript:checkInfo('<%=Utility.trimNull(map.get("ContractID"))%>')" >审核撤消</a>
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
