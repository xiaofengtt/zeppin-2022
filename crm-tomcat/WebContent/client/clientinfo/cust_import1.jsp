<%@ page language="java" pageEncoding="GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.vo.*,java.util.*,java.sql.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
Integer q_product_id = Utility.parseInt(request.getParameter("q_product_id"), new Integer(0));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
Integer q_serv_man = Utility.parseInt(request.getParameter("q_serv_man"), new Integer(0));

String editStr = Argument.getMyMenuViewStr(menu_id, input_operatorCode);
if (editStr.equals("")) 
	editStr = "CARD_TYPE$CARD_ID$MOBILE$O_TEL$FAX$POST_ADDRESS$POST_CODE$E_MAIL$SERVICE_MAN"; // Argument.getFieldDimList(menu_id);

String[] editArray = editStr.split("\\$");
Map editMap = new HashMap();
for (int i=0; i<editArray.length; i++)
	if (! "".equals(editArray[i]))
		editMap.put(editArray[i], new Boolean(true));

CustomerLocal local = EJBFactory.getCustomer();

boolean success = false;
if (request.getMethod().equals("POST")) {
	String[] cust_id = request.getParameterValues("cust_id");
	String[] card_type = request.getParameterValues("card_type");
	String[] card_type_name = request.getParameterValues("card_type_name");
	String[] card_id = request.getParameterValues("card_id");
	String[] mobile = request.getParameterValues("mobile");
	String[] o_tel = request.getParameterValues("o_tel");
	String[] fax = request.getParameterValues("fax");
	String[] post_address = request.getParameterValues("post_address");
	String[] post_code = request.getParameterValues("post_code");
	String[] e_mail = request.getParameterValues("e_mail");
	String[] service_man = request.getParameterValues("service_man");

	if (cust_id != null) {            
		for (int i=0; i<cust_id.length; i++) {
			CustomerVO vo = new CustomerVO();
			vo.setCust_id(Utility.parseInt(cust_id[i], new Integer(0)));

			if (card_type!=null && card_type[i]!=null) vo.setCard_type(card_type[i]);				
			if (card_id!=null && card_id[i]!=null) vo.setCard_id(card_id[i]);
			if (mobile!=null && mobile[i]!=null) vo.setMobile(mobile[i]);			
			if (o_tel!=null && o_tel[i]!=null) vo.setO_tel(o_tel[i]);	
			if (fax!=null && fax[i]!=null) vo.setFax(fax[i]);	
			if (post_address!=null && post_address[i]!=null) vo.setPost_address(post_address[i]);
			if (post_code!=null && post_code[i]!=null) vo.setPost_code(post_code[i]);
			if (e_mail!=null && e_mail[i]!=null) vo.setE_mail(e_mail[i]);
			if (service_man!=null && service_man[i]!=null) vo.setService_man(Utility.parseInt(service_man[i], new Integer(0)));

			vo.setInput_man(input_operatorCode);
			local.batchModiCustInfo(vo);
		}	
	}
	success = true;
}

IPageList pageList = new JdbcPageList();
List list = new ArrayList();
if (q_product_id.intValue()>0 || !q_cust_name.equals("") || q_serv_man.intValue()>0) {
	CustomerVO vo = new CustomerVO();
	vo.setCust_name(q_cust_name);
	vo.setProduct_id(q_product_id);
	vo.setService_man(q_serv_man);
	vo.setInput_man(input_operatorCode);
	vo.setExport_flag(new Integer(101));
	pageList = local.listProcAllExt(vo, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
	list = pageList.getRsList();
} 

sUrl += "&"+ Utility.getQueryString(request, new String[]{"q_product_id", "q_cust_name", "q_serv_man"});

local.remove();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<title>客户信息更新</title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet />
<script type="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript">
var success = <%=success%>;

window.onload = function() {
		if (success) 
			sl_alert("保存成功！");
		
		initQueryCondition();
	};

var j$ = jQuery.noConflict();
function filterProduct(product_name){
    if(event.keyCode==13){    
        j$("[name='q_product_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='q_product_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='q_product_id']").children(":not([text*='"+product_name+"'])").remove();
    }else{
        return false;    
    }           
}

function productFilter(product_name){    
        j$("[name='q_product_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='q_product_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='q_product_id']").children(":not([text*='"+product_name+"'])").remove();      
}

function StartQuery(){
	refreshPage();
}

function refreshPage(){
	location.search = "?page=1&pagesize=" + document.theform.pagesize.value +
		"&q_cust_name=" + document.theform.q_cust_name.value + "&q_product_id=" + document.theform.q_product_id.value 
		+ "&q_serv_man=" + document.theform.q_serv_man.value;
}

function validateForm() {
	var form = document.theform;
	var card_id= form.card_id;
	if (card_id == null)
		return true;

	if (card_id.length)
		for (var i=0; i<card_id.length; i++) {
			if (form.card_type[i].value=="110801") {
				if (! sl_check(card_id[i],"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15)) return false;//证件号码
				if (! (card_id[i].value.length==15 || card_id[i].value.length==18) ) {
					sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
					card_id[i].focus();
					return false;
				}
			}
		}
	else 
		if (form.card_type.value=="110801") {
			if (! sl_check(card_id,"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15)) return false;//证件号码
			if (! (card_id.value.length==15 || card_id.value.length==18) ) {
				sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
				card_id.focus();
				return false;
			}
		}
	return true;
}

function batchEdit() {
	if (document.theform.cust_id == null) {
		sl_alert("空页面，没有东西可以保存！");
		return;
	}
		
	if (validateForm() && sl_confirm("保存修改")) {
		document.theform.submit();
	}
}

function selectEditableFields() {
	if (showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>&edit=true'
							,'','dialogWidth:800px;dialogHeight:580px;status:0;help:0'))
		 location.reload();
}

function getIndex(elem) {
	var elems = document.getElementsByName(elem.name);
	for (var i=0; i<elems.length; i++)
		if (elems[i]==elem) return i;

	return -1;
}
</script>
</head>

<body class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="cust_import1.jsp?page=<%=sPage%>">

<div id="queryCondition" class="qcMain" style="display:none;width:530px;">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	</td>
  </tr>
</table>

<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td align="right">客户名称 :</td>
		<td>
			<input name="q_cust_name" value="<%=q_cust_name%>"/>
		</td>
	</tr>
	<tr>
		<td align="right">产品名称 :</td>
		<td>
			<input type="text" name="product_name" size="35" onkeydown="javascript:filterProduct(this.value);nextKeyPress(this)" >
			<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:productFilter(document.theform.product_name.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right"></td>
		<td>
			<select size="1" name="q_product_id" id="q_productId" onkeydown="javascript:nextKeyPress(this)" style="width: 440px;">					
				<%=Argument.getProductListOptions(input_bookCode,q_product_id,"",input_operatorCode,0)%>
			</select>
			<SELECT name="all_product_id" style="display:none" style="width: 440px;">
				<%=Argument.getProductListOptions(input_bookCode,q_product_id,"",input_operatorCode,0)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="right">客户经理 :</td>
		<td>
			<select name="q_serv_man">
				<%=Argument.getManagerListAuth(input_operatorCode, q_serv_man, new Integer(1))%>	
			</select>
		</td>		
	</tr>
	<tr>
		<td colspan="4" align="right">
			<button type="button"   class="xpbutton3" name="btnQuery" accesskey=o onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
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
						<td colspan=2><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
					
                   <tr>
					 <td align="right">
						<button type="button"  class="xpbutton3" id="queryButton" name="queryButton" accessKey=q>查询 (<u>Q</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" onclick="javascript:selectEditableFields()" accessKey=e>编辑设定 (<u>E</u>)</button>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="button"  class="xpbutton5" onclick="javascript:batchEdit()" accessKey=s>保存修改 (<u>S</u>)</button>
					 </td>
					</tr>
					<tr>
						<td colspan=2>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>

<%
	double width = 10.0;
	if (editMap.containsKey("CARD_TYPE")) width += 10.0;
	if (editMap.containsKey("CARD_ID")) width += 15.0;
	if (editMap.containsKey("MOBILE")) width += 10.0;
	if (editMap.containsKey("O_TEL")) width += 10.0;
	if (editMap.containsKey("FAX")) width += 10.0;
	if (editMap.containsKey("POST_ADDRESS")) width += 15.0;
	if (editMap.containsKey("POST_CODE")) width += 5.0;
	if (editMap.containsKey("E_MAIL")) width += 8.0;
	if (editMap.containsKey("SERVICE_MAN")) width += 7.0;

	double scale = 100.0/width;
 %>
		<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="<%=width%>%" style="margin-top:15px;table-layout:fixed">
			<tr class="trh" width="100%">
				<td align="center" width="10.0%">客户名称</td>
				<td align="center" width="10.0%" <%=editMap.containsKey("CARD_TYPE")?"":"style='display:none'"%> >证件类型</td><!--证件类型-->
				<td align="center" width="15.0%" <%=editMap.containsKey("CARD_ID")?"":"style='display:none'"%> >证件号码</td><!--证件号码-->
				<td align="center" width="10.0%" <%=editMap.containsKey("MOBILE")?"":"style='display:none'"%> >手机</td><!--手机-->
				<td align="center" width="10.0%" <%=editMap.containsKey("O_TEL")?"":"style='display:none'"%> >固定电话</td><!--固定电话-->
				<td align="center" width="10.0%" <%=editMap.containsKey("FAX")?"":"style='display:none'"%> >传真</td><!--传真-->	
				<td align="center" width="15.0%" <%=editMap.containsKey("POST_ADDRESS")?"":"style='display:none'"%> >联系地址</td><!--联系地址-->	
				<td align="center" width="5.0%" <%=editMap.containsKey("POST_CODE")?"":"style='display:none'"%> >邮编</td><!--邮编-->	
				<td align="center" width="8.0%" <%=editMap.containsKey("E_MAIL")?"":"style='display:none'"%> >email</td><!--e-mail-->		
 				<td align="center" width="7.0%" <%=editMap.containsKey("SERVICE_MAN")?"":"style='display:none'"%> >客户经理</td><!--客户经理-->
			</tr>
<%	
	int i = 0;	

	for (; i < list.size(); i++) {
		Map map = (Map) list.get(i);
		Integer cust_type = (Integer)map.get("CUST_TYPE");
		String card_type = (String)map.get("CARD_TYPE");
%>
			<tr class="tr<%=(i % 2)%>" width="100%">		
				<td width="<%=10.0*scale%>%" align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%><input type="hidden" name="cust_id" value="<%=map.get("CUST_ID")%>"/></td>
				<td <%=editMap.containsKey("CARD_TYPE")?"width='"+(10.0*scale)+"%'":"width='0%' style='display:none'"%> align="center">
					<select name="card_type" style="width:100%">
						<%=cust_type.intValue()==1?Argument.getCardTypeOptions2(card_type):Argument.getCardTypeJgOptions2(card_type)%>
					</select>	
				</td>				
				<td <%=editMap.containsKey("CARD_ID")?"width='"+(15.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="card_id" type="text" size="25" value="<%=Utility.trimNull(map.get("CARD_ID"))%>"/></td>
				<td <%=editMap.containsKey("MOBILE")?"width='"+(10.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="mobile" type="text" size="15" value="<%=Utility.trimNull(map.get("MOBILE"))%>"/></td>
				<td <%=editMap.containsKey("O_TEL")?"width='"+(10.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="o_tel" type="text" size="15" value="<%=Utility.trimNull(map.get("O_TEL"))%>"/></td>
				<td <%=editMap.containsKey("FAX")?"width='"+(10.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="fax" type="text" size="15" value="<%=Utility.trimNull(map.get("FAX"))%>"/></td>
				<td <%=editMap.containsKey("POST_ADDRESS")?"width='"+(15.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="post_address" type="text" size="30" value="<%=Utility.trimNull(map.get("POST_ADDRESS"))%>"/></td>
				<td <%=editMap.containsKey("POST_CODE")?"width='"+(5.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="post_code" type="text" size="6" value="<%=Utility.trimNull(map.get("POST_CODE"))%>"/></td>
				<td <%=editMap.containsKey("E_MAIL")?"width='"+(8.0*scale)+"%'":"width='0%' style='display:none'"%> align="center"><input name="e_mail" type="text" size="12" value="<%=Utility.trimNull(map.get("E_MAIL"))%>"/></td>
				<td <%=editMap.containsKey("SERVICE_MAN")?"width='"+(7.0*scale)+"%'":"width='0%' style='display:none'"%> align="center">
					<select name="service_man">
						<%=Argument.getManagerListAuth(input_operatorCode, (Integer)map.get("SERVICE_MAN"), new Integer(1))%>	
					</select>
				</td>
			</tr>
<%
	}
	for(; i < pageList.getPageSize(); i++) {
%>
			<tr class="tr<%=(i % 2)%>" width="100%">
				<td>&nbsp;</td>
				<td <%=editMap.containsKey("CARD_TYPE")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("CARD_ID")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("MOBILE")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("O_TEL")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("FAX")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("POST_ADDRESS")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("POST_CODE")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("E_MAIL")?"":"style='display:none'"%> >&nbsp;</td>
				<td <%=editMap.containsKey("SERVICE_MAN")?"":"style='display:none'"%> >&nbsp;</td>
			</tr>
<%
	}
%>
		</table>
		<table border="0" width="100%">
			<tr valign="top">
				<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
			</tr>
		</table>
				

				</TD>
			</TR>
		</TABLE>
	</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>