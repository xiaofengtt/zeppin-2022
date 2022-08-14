<%@ page contentType="text/html; charset=GBK" import="java.math.*,java.util.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.contractManage.*,enfo.crm.vo.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer direction = Utility.parseInt(request.getParameter("direction"),new Integer(0));
String productname = Utility.trimNull(request.getParameter("productname"));
String custname = Utility.trimNull(request.getParameter("custname"));
//Integer checkflag = Utility.parseInt(request.getParameter("checkflag"),new Integer(0));
String directionName="";
enfo.crm.callcenter.CallCenterLocal callcenter=EJBFactory.getCallCenter();
CCVO vo=new CCVO();
	vo.setSerial_no(new Integer(0));
	vo.setDirection(direction);
	vo.setManagerID(input_operatorCode);
	//vo.setExtension("");
	vo.setInput_man(input_operatorCode);
	IPageList pagelist =callcenter.listCallRecords(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
	List list=null;
	Map map=new HashMap();
	if (pagelist!=null){
		list=pagelist.getRsList();
	}
callcenter.remove();
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
			+'&direction=' + document.theform.direction.value;
}
</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" action="videorecording_list.jsp">
	<input type="hidden" name="menu_id" value="">
<div class="page-title">
	<font color="#215dc6"><b>话务记事列表</b></font>
</div>
<br/>
<div id="queryCondition">
	&nbsp;<font size="2">话务方向</font><select name="direction"><option value="0">请选择</option>
		<option value="1" <%if (direction.intValue()==1) out.print(" selected"); %>>被叫接听</option>
		<option value="2" <%if (direction.intValue()==2) out.print(" selected"); %>>主叫拨打</option>
	</select>
	<input type="button" value="查询" class="xpbutton3" onclick="javascript:StartQuery();">
</div>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
	<tr class="trh">
		<td align="center">拨打时间</td>
		<td align="center">话务方向</td>
		<td align="center">分机号码</td>
		<td align="center">外呼号码</td>
		<td align="center">记事内容</td>
		<td align="center">客户名称</td>
	</tr>
<%
int iCount = 0;
int iCurrent = 0;
if (list!=null && list.size()>0){
	Integer chgflag=new Integer(0);
	String chgflagName="";
	for (int i=0;i<list.size();i++){
		map=(Map)list.get(i);
		direction=(Integer)map.get("Direction");
		if (direction==null) directionName="";
		else if (direction.intValue()==1) directionName="被叫接听";
		else if (direction.intValue()==2) directionName="主叫拨打";
 %>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td align="center"><%=Utility.trimNull(map.get("CallTime"))%></td>
			<td align="center"><%=directionName %></td>
			<td align="center"><%=Utility.trimNull(map.get("Extension")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("PhoneNumber")) %></td>
			<td align="center"><%=Utility.trimNull(map.get("Content")) %></td>
			<td align="center"></td>
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
		</tr>
	<%}
	}%>
</table>
<div class="page-link"><%if (list!=null){
	out.print(pagelist.getPageLink(sUrl));
} %>
</div>

</form>
</BODY>
</HTML>
