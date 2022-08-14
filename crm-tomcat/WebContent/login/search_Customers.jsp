<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<% 
String key_word = Utility.trimNull(request.getParameter("key_word"));




CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setKey_word(key_word);
vo.setFlag(new Integer(0));
vo.setInput_man(input_operatorCode);



IPageList pageList  = null;
Iterator it = null;


pageList = local.SearchByCustomers(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));  
 it = pageList.getRsList().iterator();

sUrl +=  "&key_word="+key_word;

local.remove();

System.out.println("----------"+key_word+"--------------");


%>



<html>
<head>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language="javascript">
	/*function matchAction(){

	var tel = document.getElementById("tel").value;
	var telnum = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
	
	if(tel.length==0){
		sl_alert('请输入匹配电话！');
		return false;
	}*/
	/*else if(!telnum.test(tel)){
		//您输入的电话号码  不符合规则，请检查后重新输入
        sl_alert('<fmt:message key="index.callcenter.yourTel"/>'+tel+'<fmt:message key="index.callcenter.confirmRuls"/>');
		return false;
	}
	//if(!sl_checkNum(document.getElementById("tel"), "电话号码",20,7))	return false;//手机号码
	document.getElementById("theform").action = "search_Customers.jsp?flag=1";
	document.getElementById("theform").submit();
}*/

function refreshPage(){
	
	location.search ="?page=1&pagesize=" + document.theform.pagesize.value
						+"&key_word="+document.theform.key_word.value;
}

function queryCustomerDetail(cust_id)	
{
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+cust_id;
	
	showModalDialog(url,'','dialogWidth:990px;dialogHeight:530px;status:0;help:0');

}

</script>
</head>
<body class="body">
<form id="theform" name="theform" method="post">
<INPUT type="hidden" id="key_word" name="key_word" value="<%=key_word %>">

	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b>客户快速检索</b></font><!--客户快速检索-->
	</div>	
	<hr noshade color="#808080" size="1" width="100%"">

	
	
	<div style="margin-top:5px;" align="center">
	<table id="table3" border="0" cellspacing="1" cellpadding="2"
			class="tablelinecolor" width="100%" style="margin-top:5px">
			<tr class="trh">
				<td align="center" width=""> 名称 </td>
				<td align="center" width="">证件类别</td>
				<td align="center" width="">证件号码</td>
				<td align="center" width="">联系地址</td>
				<td align="center" width="">邮政编码</td>
				<td align="center" width="">电话</td>
				<td align="center" width="">手机</td>
				<td align="center" width=""><fmt:message key="class.accountManager"/></td><!--客户经理-->
				
		     </tr>
<%

int iCurrent = 0;
int iCount=0;


while (it.hasNext()) {
	Map map = (Map)it.next();
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	Integer service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
	Integer encrypt =Utility.parseInt(Utility.trimNull(map.get("ENCRYPT")),new Integer(0));
%>
			<tr class="tr<%=(iCurrent % 2)%>" >
				
				<td width="" align="left">
				<%if(encrypt.intValue()==0){%>
					<a href="javascript:queryCustomerDetail('<%=cust_id %>')">
					<%=Utility.trimNull(map.get("CUST_NAME"))%>
					</a>
				<%}else{
					out.println(Utility.trimNull(map.get("CUST_NAME")));
				} %>
				</td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("POST_CODE"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("CUST_TEL"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("MOBILE"))%></td>
				<td width="" align="left"><%=Utility.trimNull(map.get("SERVICE_MAN_NAME"))%></td>
			</tr>
<%
	iCurrent++;
	iCount++;	
}
for (; iCurrent<pageList.getBlankSize(); iCurrent++){ %>
			<tr class="tr<%=(iCurrent % 2)%>">
				<td class="tdh" align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
				<td align="center" ></td>
		</tr>
<%} %>
			<tr class="trbottom">
				<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
			</tr>

		</table>	
		<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
		</table>
	</div>
</form>
</body>
</html>