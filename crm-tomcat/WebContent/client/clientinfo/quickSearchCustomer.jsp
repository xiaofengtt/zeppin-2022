<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_type = Utility.trimNull(request.getParameter("card_type"));
String card_no 	 = Utility.trimNull(request.getParameter("card_no"));
String email 	 = Utility.trimNull(request.getParameter("email"));
String tel		 = Utility.trimNull(request.getParameter("tel"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
Integer cust_type   = Utility.parseInt(request.getParameter("cust_type"),new Integer(1));

sUrl = sUrl + "&cust_name="+cust_name+"&card_type="+card_type
		+"&card_no="+card_no+"&email="+email+"&tel="+tel
		+"&product_name+"+product_name+"&cust_type="+cust_type;

int flag = Utility.parseInt(request.getParameter("flag"),0);

CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setCust_name(cust_name);
vo.setCust_tel(tel);
vo.setCust_type(cust_type);
vo.setCard_id(card_no);
vo.setCard_type(card_type);
vo.setE_mail(email);
vo.setProduct_name(product_name);
vo.setInput_man(input_operatorCode);
List list = null;
if(flag==1)
	list =  local.quickSearchByAll(vo);

%>



<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>客户快速检索</title><!--客户快速检索-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
function matchAction(){
	var tel = document.getElementById("tel").value;
	var telnum = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	/*
	if(tel.length==0){
		sl_alert('请输入匹配电话！');
		return false;
	}*/
	/*else if(!telnum.test(tel)){
		//您输入的电话号码  不符合规则，请检查后重新输入
        sl_alert('<fmt:message key="index.callcenter.yourTel"/>'+tel+'<fmt:message key="index.callcenter.confirmRuls"/>');
		return false;
	}*/
	//if(!sl_checkNum(document.getElementById("tel"), "电话号码",20,7))	return false;//手机号码
	document.getElementById("theform").action = "quickSearchCustomer.jsp?flag=1";
	document.getElementById("theform").submit();
}

function queryCustomerDetail(cust_id)	
{
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+cust_id;
	
	showModalDialog(url,'','dialogWidth:990px;dialogHeight:530px;status:0;help:0');

}

</script>
</head>
<body class="body body-nox">
<form id="theform" name="theform" method="post">
	<div align="left" class="page-title">
		<font color="#215dc6"><b>客户快速检索</b></font><!--客户快速检索-->
	</div>	
<br/>
	<div class="product-list"><font size=3>
		&nbsp;
		客户类别:<select name="cust_type" style="width: 150px;"><%=Argument.getCustTypeOptions(cust_type) %></select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		客户名称:<input type="text" id="cust_name" name="cust_name" value="<%=cust_name%>" size="25"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		电话号码:
		<input type="text" id="tel" name="tel" value="<%=tel%>" size="25"/><br>
		&nbsp;
		证件类型:<select name="card_type" style="width: 150px;"><%=Argument.getCardType(card_type) %></select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		证件号码:<input type="text" id="card_no" name="card_no" value="<%=card_no%>" size="25"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:
		<input type="text" id="email" name="email" value="<%=email%>" size="25"/><br>
		&nbsp;
		产品名称:<input type="text" id="product_name" name="product_name" value="<%=product_name%>" size="125"/>
        &nbsp;&nbsp;<button type="button"  class="xpbutton2" id="btnMatch" onclick="javascript:matchAction();">检&nbsp;索</button>		
	</font></div>
	
	<div style="margin-top:5px;" align="center">
		<table border="0" width="900px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
			<tr class="trtagsort">
				<td align="center" width=""><fmt:message key="class.ID"/></td><!--编号-->
				<td align="center" width=""><fmt:message key="class.name"/></td><!--名称-->
				<td align="center" width=""><fmt:message key="class.custType"/></td><!--客户类型-->
				<td align="center" width="">证件类别</td>
				<td align="center" width="">证件号码</td>
				<td align="center" width=""><fmt:message key="class.accountManager"/></td><!--客户经理-->
				<td align="center" width="">详细</td><!--详细-->
		     </tr>
<%
if(flag==1){
int iCount = 0;
for(int i=0;i<list.size();i++,iCount++){
	Map map = (Map)list.get(i);
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	Integer service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
%>
			<tr class="tr<%=(iCount % 2)%>" <%if(input_operatorCode.intValue()==service_man.intValue()){%>ondblclick="javascript:queryCustomerDetail(<%=cust_id %>)" style="cursor: hand;"<%}%> >
				<td width="" align="left"><%=Utility.trimNull(map.get("CUST_NO"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
				<td width="" align="left"><%=Argument.getOperatorName(service_man)%></td>
				<td width="4%" align="center">
					<%if(input_operatorCode.intValue()==service_man.intValue()){%>
					<a href="#" onclick="javascript:javascript:queryCustomerDetail(<%=cust_id %>);">
						<img src="<%=request.getContextPath()%>/images/azsy.gif" width="16" height="16" title="详情" />
					</a>
					<%}%>
				</td>
			</tr>
<%}%>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			</tr>
<%} %>
		</table>	

	</div>
</form>
</body>
</html>