<%@ page contentType="text/html; charset=GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer repeatflag = Utility.parseInt(request.getParameter("repeatflag"), new Integer(2)); // 默认是客户名
Integer repeat_time = Utility.parseInt(request.getParameter("repeat_time"), new Integer(1));// 重复超过次数
String must_contain = Utility.trimNull(request.getParameter("must_contain")).trim(); // 身份证号/姓名必须包含的   
String loosely_match = Utility.trimNull(request.getParameter("loosely_match")).trim(); // 相似匹配
Integer max_diff = Utility.parseInt(request.getParameter("max_diff"), new Integer(0));// 相似匹配允许身份证号/姓名中不同出现的次数
if (max_diff.intValue()<0) 
	max_diff = new Integer(0);

CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();

cust_vo.setRg_times(repeat_time);
cust_vo.setRepaeat_flag(repeatflag);
cust_vo.setInput_man(input_operatorCode);
cust_vo.setMust_contain(must_contain);
cust_vo.setLoosely_match(loosely_match);
cust_vo.setMax_diff(max_diff);

IPageList pagelist = cust_local.queryRepeatCustomers(cust_vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pagelist.getRsList();
Iterator it = list.iterator();

String queryString = Utility.getQueryString(request, 
						new String[]{"repeatflag", "repeat_time", "must_contain", "loosely_match", "max_diff"});
sUrl += "&"+queryString;
cust_local.remove();
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
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script language=javascript>
var page = <%=Utility.parseInt(sPage, 1)%>;

window.onload = function() {
		initQueryCondition();
	};

//查看当前客户信息
function showInfo(custid) {
	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
 	if(showModalDialog(url,'','dialogWidth:1140px;dialogHeight:550px;status:0;help:0')!=null) {
	}
}

function StartQuery() {
	refreshPage(1);
}

function refreshPage(_page) {
	disableAllBtn(true);
	location = 'client_unite_list.jsp?page="+(_page!=null?_page:page) +"&pagesize='+ document.theform.pagesize.value 
					+ '&must_contain=' + document.theform.must_contain.value+ '&repeat_time=' + document.theform.repeat_time.value
					+'&repeatflag='+document.theform.repeatflag.value+ '&loosely_match=' + document.theform.loosely_match.value
					+ '&max_diff=' + document.theform.max_diff.value;
}

function validateForm(form) {
	//if(!sl_check(form.from_cust_id, "<%=LocalUtilis.language("class.custID",clientLocale)%> 1", 100, 1))	return false;//客户ID1
	//if(!sl_check(form.to_cust_id, "<%=LocalUtilis.language("class.custID",clientLocale)%> 2", 100, 1))	return false;//客户ID2
	if (form.cust_id==null || form.cust_id.length==null)  {
		sl_alert("当前页面没有客户可以合并！");
		return false;
	} else {
		var selected = 0;
		for (var i=0; i<form.cust_id.length; i++)
			if (form.cust_id[i].checked)
				selected ++;

		if (selected < 2) {
			sl_alert("请选择要合并的两位客户！");
			return false;
		} else if (selected > 2) {
			sl_alert("一次合并只能选择两位客户！");
			return false;
		}
	}
	
	return true;
}

function hbInfo(){
	//location = 'client_unite_info.jsp?from_cust_id='+document.theform.from_cust_id.value+"&to_cust_id="+document.theform.to_cust_id.value;
	var url = "client_unite_info.jsp";

	var cust_ids = document.theform.cust_id;
	for (var i=0; i<cust_ids.length; i++)
		if (cust_ids[i].checked)
			if (url.indexOf("?")<0)
				url += "?cust1_id=" + cust_ids[i].value;
			else
				url += "&cust2_id=" + cust_ids[i].value;

	url += "&<%=queryString%>";
	location.href = url;
}

//判断选择项
function setTitleValue(value) {
	if (value==2) 
		custnametd.innerText='<%=LocalUtilis.language("class.customerName",clientLocale)%>中包含 :';//客户名称		
	else
		custnametd.innerText='<%=LocalUtilis.language("class.customerCardID",clientLocale)%>中包含 :';//证件号码

	document.theform.must_contain.value="";
}

function isTooMany() {
	var limit = 2;

	if (document.theform.cust_id.length==null) // 单个
		return;

	var cust_ids = document.theform.cust_id;
	var selected = 0;
	for (var i=0; i<cust_ids.length; i++) 
		if (cust_ids[i].checked)
			selected ++;

	if (selected==limit) 	
		for (var i=0; i<cust_ids.length; i++) {
			if (! cust_ids[i].checked)
				cust_ids[i].disabled = true;
		}
	else if (selected<limit) 
		for (var i=0; i<cust_ids.length; i++) {
			if (! cust_ids[i].checked)
				cust_ids[i].disabled = false;
		}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="client_unite_info.jsp" onsubmit="javascript: return validateForm(this);">

<div id="queryCondition" class="qcMain" style="display:none;width:440px;">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table  width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.repeatFlag",clientLocale)%> :</td><!--重复类别-->
		<td >
			<select size="1" name="repeatflag" onchange="javascript:setTitleValue(this.value)" onkeydown="javascript:nextKeyPress(this)" >
				<%=Argument.getRepeatCustomerOptions(repeatflag)%>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("message.repeatMoreThan",clientLocale)%> :</td><!--重复次数大于-->
		<td align=left>
			<input type="text" name="repeat_time" onkeydown="javascript:nextKeyPress(this)" value="<%=repeat_time%>" size="5">
		</td>					
	</tr>
	<tr>
		<td id="custnametd" align=right><%=repeatflag.intValue()==1?LocalUtilis.language("class.customerCardID",clientLocale):LocalUtilis.language("class.customerName",clientLocale)%>中包含 :</td>
		<td colspan=3>
			<input type="text" name="must_contain" size="45" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(must_contain)%>">
		</td>
	</tr>
	<tr>
		<td align=right>近似匹配 :</td>
		<td colspan=3>
			<input type="text" name="loosely_match" size="45" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(loosely_match)%>">
		</td>
	</tr>
	<tr>
		<td align=right>近似匹配允许不同次数 :</td>
		<td colspan=3>
			<input type="text" name="max_diff" size="2" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(max_diff)%>"/>(<font color="red">最好不要超过4，否则会很慢</font>)
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>	  
			<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
		</td><!--确定-->
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
						<td colspan=2 class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					
                   <tr>
                    <%--!--从客户ID1--><!--合并到客户ID2-->
                   	<td align="right">
                   	<%=LocalUtilis.language("message.mergeFrom",clientLocale)%>
                   	<font color="red">(潜在客户/事实客户)</font>
                   	<INPUT type="text" name="from_cust_id" size="20" onkeydown="javascript:nextKeyPress(this)">
                   	<%=LocalUtilis.language("message.mergeTo",clientLocale)%> 
                   	<font color="red">(事实客户)</font>
                   	<INPUT type="text" name="to_cust_id" size="20" onkeydown="javascript:nextKeyPress(this)"></td--%>              	 
					 <td align="right">
					 <div class="btn-wrapper">
						<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!--查询-->	
							&nbsp;&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 100)) { // 合并%>
						<button type="button"  class="xpbutton3" accessKey=m title="<%=LocalUtilis.language("message.merger",clientLocale)%> "
						    onclick="javascript:if(document.theform.onsubmit()){disableAllBtn(true);hbInfo();}"><%=LocalUtilis.language("message.merger",clientLocale)%> (<u>M</u>)<!--合并--></button>
						<%}%>
					</div>
					 </td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" ><%=LocalUtilis.language("class.custID",clientLocale)%> </td><!--客户ID-->
						<td align="center" ><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
						<td align="center" ><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align="center">客户等级 </td><!--客户等级-->
<%}else{ %>
						<td align="center"><%=LocalUtilis.language("class.custType",clientLocale)%> </td><!--客户类型-->
<%} %>
						<td align="center" ><%=LocalUtilis.language("class.cardTypeName",clientLocale)%> </td><!--证件类别-->
						<td align="center" ><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
						<td align="center" ><%=LocalUtilis.language("class.theInfo",clientLocale)%> </td><!--当前信息-->
					</tr>
				<%
				int iCurrent = 0;
				while (it.hasNext()) {
					Map map = (Map)it.next();
					Integer cust_id = (Integer)map.get("CUST_ID");
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20%"><input type="checkbox" style="BORDER: #849ED6 0px solid;" name="cust_id" onclick="javascript:isTooMany()" value="<%=cust_id%>"/></td>
								<td width="80%" align="center"><%=cust_id%></td>
							</tr>
						</table>
						</td>
						<td align="left" ><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
						<td align="center" ><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
<%if(user_id.intValue() == 21){/*金汇CRM要求显示客户等级*/ %>
						<td align=center><%=Utility.trimNull(map.get("CUST_LEVEL_NAME"))%></td>
<%}else{ %>
						<td align=center><%=Utility.trimNull(map.get("IS_DEAL_NAME"))%></td>
<%} %>
						<td align="center" ><%=Utility.trimNull(map.get("CARD_TYPE_NAME"))%></td>
						<td align="left" ><%=Utility.trimNull(map.get("CARD_ID"))%></td>
						<td align="center" >
							<!--该客户属于登陆的客户经理时才能查看详细-->
							<%if(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")), new Integer(0)).equals(input_operatorCode)){%>
							<a href="javascript:#" onclick="javascript:showInfo(<%=cust_id%>);return false; ">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title="<%=LocalUtilis.language("menu.viewInfo",clientLocale)%> " />
								<!--查看当前信息-->
							</a>
							<%}%>
						</td>
					</tr>
				<%
					iCurrent++;
				}

				for (; iCurrent < pagelist.getPageSize(); iCurrent++) { %>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
				<%}
				%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="7" >
						    <b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<!--合计--><%=pagelist.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
						</td>
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pagelist.getPageLink(sUrl,clientLocale)%></td>
						<td align="right">
					
						
						<!--<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="返回上一页" onclick="javascript:history.back();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;--></td>
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
