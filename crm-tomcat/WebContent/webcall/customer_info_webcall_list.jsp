<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String queryCondition = Utility.trimNull(request.getParameter("queryCondition"));
String webcallId = Utility.trimNull(request.getParameter("webcallId"));
//参数设置
String cust_name = "";
String cust_no = "";
Integer cust_type = new Integer(0);
String card_type = "";
String card_id = "";
String telephone = "";
Integer accountManager = new Integer(0);

if(queryCondition.length()>0){
	 String[] conditionArray = Utility.splitString(queryCondition,"|");
	 cust_name = conditionArray[1];
	 cust_no = conditionArray[2];
	 cust_type = Utility.parseInt(conditionArray[3],new Integer(0));
	 card_type = conditionArray[4];
	 card_id = conditionArray[5]; 
	 telephone = conditionArray[6]; 
	 accountManager =  Utility.parseInt(conditionArray[7],new Integer(0));
}
//查询
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
IPageList pageList  = null;
Iterator it = null;
int iCount = 0;
Map map = new HashMap();

vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setCust_type(cust_type);
vo.setCard_type(card_type);
vo.setCard_id(card_id);
vo.setTelephone(telephone);
vo.setService_man(accountManager);
vo.setInput_man(input_operatorCode);

pageList = local.listProcAllExt(vo,1,-1);
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/styles/common.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/webcall/webcall.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_zh_CN.js"></SCRIPT>

<script language=javascript>
function showInfo(cust_id){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_detail.jsp?queryCondition=<%=queryCondition%>&webcallId=<%=webcallId%>&cust_id="+cust_id;
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
function backAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_query.jsp?webcallId=<%=webcallId%>&queryCondition=<%=queryCondition%>";
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
</script>
</HEAD>
<BODY class="BODY2" style="overflow-y: auto;">
<form name="theform">
<div align="center"  class="topDiv">
	<font color="#208020" size="3">客户搜索结果</font>&nbsp;&nbsp;
	<button class="xpbutton2"  id="btnBack" name="btnBack" onclick="javascript:backAction();">返回</button>
</div>
<table class="problemInfoTable" cellSpacing="0" cellPadding="2" width="100%" align="center">
	<tr class="trh">
		<td  width="60px">客户编号</td>
		<td >客户名称</td>
	</tr>
</table>

<div align="left" class="div_scroll">
	<table class="problemInfoTable" cellSpacing="0" cellPadding="2" width="100%" align="center">
	<%
	if(pageList != null)
		it = pageList.getRsList().iterator();
	
	while(it!=null&&it.hasNext()){
		iCount++;
		map = (Map)it.next();	
	 %>
		<tr class="tr<%=iCount%2%>" title="双击查看" style="cursor:hand;"onDBlclick="javascript:showInfo(<%=map.get("CUST_ID")%>);">
			 <td align="center" width="60px"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
			 <td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
		</tr>
	<%}%>
	<%for(int i=0;i<(8-iCount);i++){%>
	     <tr class="tr0">
	        <td align="center" width="60px">&nbsp;</td>
	        <td align="center">&nbsp;</td>
	     </tr>
	<%}%>
	</table>
</div>
<div class="bottomDiv">&nbsp;&nbsp;<font size="2">您好！<%=input_operator.getOp_name()%></font></div>
</form>
</BODY>
</HTML>