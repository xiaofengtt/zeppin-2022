<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String file_name = Utility.trimNull(request.getParameter("file_name"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer is_link = Utility.parseInt(request.getParameter("is_link"),new Integer(0));//是否关联方
int input_flag = Utility.parseInt(request.getParameter("input_flag"),0);
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
Integer group_id = Utility.parseInt(Utility.trimNull(request.getParameter("group_id")),new Integer(0));
String cust_source = Utility.trimNull(request.getParameter("cust_source"));
String q_country = Utility.trimNull(request.getParameter("q_country"));
String q_money_source = Utility.trimNull(request.getParameter("q_money_source"));
String q_money_source_name = Utility.trimNull(request.getParameter("q_money_source_name"));
String invest_field = Utility.trimNull(request.getParameter("invest_field"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String post_address = Utility.trimNull(request.getParameter("post_address"));

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");

//-----------------------导入客户数据--------------------------
if(request.getMethod().equals("POST")){
	enfo.crm.web.DocumentFile file = new enfo.crm.web.DocumentFile(pageContext);
	file.parseRequest();
	if (file.uploadFile("c:\\temp")){
	    if (file.uploadFile("c:\\temp")){	
			input_flag = file.readExcel(pageContext,"c:\\temp",3000,input_operatorCode);	
		}
	}	
}
//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
//设置参数
vo.setCust_no(no);
vo.setCust_name(name);
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setIs_link(is_link);
vo.setCard_id(card_id);
vo.setCust_type(cust_type);
vo.setIs_deal(is_deal);
vo.setGroupID(group_id);
vo.setCust_source(cust_source);
vo.setMoney_source(q_money_source);
vo.setMoney_source_name(q_money_source_name);
vo.setCountry(q_country);
vo.setInvest_field(invest_field);
vo.setH_tel(mobile);
vo.setPost_address(post_address);

if(user_id.intValue()==1){
	vo.setOrderby("A.CUST_ID DESC");	
}
//页面变量
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = new JdbcPageList();;
Iterator it = null;
Map map = new HashMap();
if(first_flag != 1)
	pageList = customer.listProcAllExt(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));

if(first_flag != 1)
	it = pageList.getRsList().iterator();
else 
	it = new ArrayList().iterator();

String tempUrl = "&name=" + name + "&no=" + no + "&product_id=" + product_id + "&is_link=" + is_link + "&card_id=" + card_id + "&is_deal=" + is_deal + "&cust_type=" + cust_type;
//url设置
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
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
var user_id = <%=user_id%>;

window.onload = function(){
	//initQueryCondition();

	var input_flag = document.getElementById("input_flag").value;
	if(input_flag>0){
		//导入成功  //共导入  //条数据
        alert('<%=LocalUtilis.language("message.importOk",clientLocale)+"！"+LocalUtilis.language("message.totalImport",clientLocale)%> '+input_flag+'<%=LocalUtilis.language("message.data",clientLocale)%> ！');
		document.getElementById("input_flag").value = 0;
	}
};

/*查询*/
function advancedQuery(){
	var v = showModalDialog('/client/analyse/client_query_all.jsp?','','dialogWidth:650px;dialogHeight:680px;status:0;help:0');
	if(v!=null)
	{
		location = 'client_query_list.jsp?cust_no=' +v[0]
								+'&cust_name='      +v[1]
								+'&cust_source='    +v[2]
								+'&cust_type='      +v[3]
								+'&card_type='      +v[4]
								+'&card_id='        +v[5]
								+'&vip_card_id='    +v[6]
								+'&hgtzr_bh='       +v[7]
								+'&birthday='       +v[8]
								+'&cust_level='     +v[9]
								+'&start_rg_times=' +v[10]
								+'&end_rg_times='   +v[11]
								+'&min_total_money='+v[12]
								+'&max_total_money='+v[13]
								+'&ben_amount_min=' +v[14]
								+'&ben_amount_max=' +v[15]
								+'&touch_type='     +v[16]
								//+'&cust_tel='
								+'&post_address='   +v[17]
								+'&product_id='     +v[18]
								+'&is_link='        +v[19]
								+'&onlyemail='      +v[20]
								+'&family_name='    +v[21]
								+'&sort_name='      +v[22]
								+'&prov_level='     +v[23]
								+'&wtr_flag='       +v[24]
								+'&group_id='       +v[25]
								+'&q_money_source=' +v[26]
								+'&q_country='      +v[27]
								+'&is_deal='        +v[28]
								+'&accountManager=' +v[29]
								+'&referee='        +v[30]
								+'&classEs='        +v[31]
								+'&productTo='		+v[32]
								+'&start_age='		+v[33]
								+'&end_age='		+v[34]
								+'&telephone='		+v[35]
								+'&cell_id='		+v[36]
								+'&cell_flag='		+v[37]
								+'&birthday_end='   +v[38]
								+'&rg_begin_date='	+v[39]
								+'&rg_end_date='	+v[40]
								+'&region='			+v[41]
								+'&channel='		+v[42]
								+'&sub_product_id=' +v[43]
								+'&city_name='      +v[44];
	}
}

function getCondition()
{
	if(!document.theform.is_link.checked)
		document.theform.is_link.value = 0;//是否关联方未选中为0
	//组合查询条件
	var iQuery = document.theform.no.value 
					+ "$" + document.theform.name.value 
					+ "$" + document.theform.product_id.value
					+ "$" + document.theform.is_link.value
					+ "$" + document.theform.card_id.value
					+ "$" + "<%=sPage%>" 
					+ "$" + document.theform.pagesize.value
					+ "$" + document.theform.is_deal.value;
					//+ "$" + document.theform.cust_type.value;
	return iQuery;
}

function getCondition2() {
	if(!document.theform.is_link.checked)
		document.theform.is_link.value = 0;//是否关联方未选中为0
	if(document.theform.pagesize != null)	
		var pagesize = document.theform.pagesize.value;

	return 'page=<%=sPage%>&pagesize=' + pagesize 
				+'&name=' + document.theform.name.value 
				+ '&no=' + document.theform.no.value
				+ '&product_id=' + document.theform.product_id.value
				+ '&product_name=' + document.theform.product_name.value
				+ '&is_link=' + document.theform.is_link.value
				+ "&card_id=" + document.theform.card_id.value
				+ "&is_deal=" + document.theform.is_deal.value
				//+ "&cust_type=" + document.theform.cust_type.value
				//+ "&group_id=" + document.theform.group_id.value
				+ "&q_money_source=" + document.theform.q_money_source.value
				+ "&q_money_source_name=" + document.theform.q_money_source_name.value
				+ "&q_country=" + document.theform.q_country.value
				//+ "&cust_source=" + document.theform.cust_source.value
				+ "&invest_field=" + document.theform.invest_field.value
				+ "&mobile=" + document.theform.mobile.value
				+ "&post_address=" + document.theform.post_address.value;			
}

/**客户信息编辑**/
function showInfo(cust_id,cust_type) {
	var iQuery = getCondition();
	var ret = 0;

	if (user_id==22/*金汇21*/) {
		if (cust_type==1) {
			location.href = "customer_info_jh_modi.jsp?cust_id="+cust_id+"&"+getCondition2();
		} else {
			location.href = "customer_info_ent_jh_modi.jsp?cust_id="+cust_id+"&"+getCondition2();
		}
		return; // 不可少，否则会执行后续语句
	}
	
	if(cust_type == 2){
		ret = showModalDialog('clinet_info_new_end.jsp?cust_id=' + cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	}else if(cust_type == 1){
		ret = showModalDialog('client_info_new.jsp?cust_id=' + cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	}
	
	if(ret==1){
		window.location.reload();
	}
	
}
/**客户信息添加**/
function newInfo(cust_type) { //1个人 2机构
	var iQuery = getCondition();

	if (user_id==22/*金汇21*/) {
		if (cust_type==1) {
			location.href = "customer_info_jh_add.jsp?"+getCondition2();
		} else {
			location.href = "customer_info_ent_jh_add.jsp?"+getCondition2();
		}
		return; // 不可少，否则会执行后续语句
	}

	var ret = 0;
	
	if (cust_type == 1) {
		ret = showModalDialog('client_info_new.jsp','','dialogWidth:850px;dialogHeight:650px;status:0;help:0');	

	} else if (cust_type == 2){
		ret = showModalDialog('clinet_info_new_end.jsp','','dialogWidth:850px;dialogHeight:650px;status:0;help:0');	
	}
	
	if(ret==1){
		window.location.reload();
	}
}
/**联系人**/
function contactInfo(cust_id, cust_type)
{
	var iQuery = getCondition();
	location = "client_contacts_list.jsp?cust_id="+cust_id+"&cust_type="+cust_type+"&contactType=1"+"&iQuery=" + iQuery;
}

function StartQuery()
{
	refreshPage();
}

function refreshPage()
{
	if(!document.theform.is_link.checked)
		document.theform.is_link.value = 0;//是否关联方未选中为0
	disableAllBtn(true);
	if(document.theform.pagesize != null)	
		var pagesize = document.theform.pagesize.value;
	var url = 'client_info_list.jsp?page=<%=sPage%>&pagesize=' + pagesize 
				+'&name=' + document.theform.name.value 
				+ '&no=' + document.theform.no.value
				+ '&product_id=' + document.theform.product_id.value
				+ '&product_name=' + document.theform.product_name.value
				+ '&is_link=' + document.theform.is_link.value
				+ "&card_id=" + document.theform.card_id.value
				+ "&is_deal=" + document.theform.is_deal.value
				//+ "&cust_type=" + document.theform.cust_type.value
				//+ "&group_id=" + document.theform.group_id.value
				+ "&q_money_source=" + document.theform.q_money_source.value
				+ "&q_money_source_name=" + document.theform.q_money_source_name.value
				+ "&q_country=" + document.theform.q_country.value
				//+ "&cust_source=" + document.theform.cust_source.value
				+ "&invest_field=" + document.theform.invest_field.value
				+ "&mobile=" + document.theform.mobile.value
				+ "&post_address=" + document.theform.post_address.value;
	location = url;				
}

function setProduct(value)
{
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value)
{
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id'	class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}
}
/*打印客户信息*/
function printInfo(cust_id, cust_type)
{	
	if(cust_type == 1)
	{							
		location = 'person_info_print.jsp?cust_id=' + cust_id;
	}else
	{
		location = 'ent_info_print.jsp?cust_id=' + cust_id;
	}
}
/*导入功能*/
function inputAction(){		

	var result = showModalDialog('client_info_import.jsp','','dialogWidth:600px;dialogHeight:500px;status:0;help:0');

	if(result!=null){
		document.theform.fieldDatas.value = result[0];
		document.theform.fieldMethod.value = result[1];
		document.theform.fieldDataType.value = result[2];
	    
	    document.theform.action = "client_info_import_action.jsp";
	    document.theform.submit();
	}
}
function callCust(target_custid){
	//var url = "/affair/sms/cust_tel.jsp?target_custid="+target_custid;
	//showModalDialog(url,window,'dialogWidth:420px;dialogHeight:300px;status:0;help:0');
	<%if(Argument.getSyscontrolValue("DT_CALL")!=0){%>	
	document.parentWindow.parent.document.getElementById("target_custid").value = target_custid; 
	document.parentWindow.parent.document.getElementById("callTalkType").value = 1;
	document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
	<%}%>
}
function joinGroup(target_custid){
	var url = "client_info_group.jsp?cust_id="+target_custid;
	showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');	
}
function changeCustType(flag){
	document.getElementById("cust_type").value=flag;
	refreshPage();
}
		
/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "detailsTr"+serial_no;
	var v_table = "detailsTable"+serial_no;
	var v_flag = "detailsFlag_display"+serial_no;
	var v_div = "detailsDiv"+serial_no;
	var v_image = "detailsImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
/*多选资金来源*/
function chooseAction(){
	var money_source = document.getElementById("q_money_source").value;
	var url = "<%=request.getContextPath()%>/client/money_source_check.jsp?money_source="+money_source;
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
	
	if(ret!=null){
		document.getElementById("q_money_source").value = ret[0];
		document.getElementById("q_money_source_name").value = ret[1];
	}
}

function showStatistics(custId) {
	showModalDialog("<%=request.getContextPath()%>/client/clientinfo/cust_contract_stat.jsp?custId="+custId, 
					'','dialogWidth:800px;dialogHeight:600px;status:0;help:0');
}

/*查看客户详细*/
function showCustInfo(custid)
{
 	var url = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
	location = url;
}

</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="customer_remove.jsp">
<input type="hidden" name="input_flag" id="input_flag" value="<%= input_flag%>" />
<input type="hidden" name="file_name" id="file_name"value="">
<input type="hidden" name="fieldDatas" value="">
<input type="hidden" name="fieldMethod" value="">
<input type="hidden" name="fieldDataType" value="">
<input type="hidden" id="cust_type" name="cust_type" value="<%=cust_type%>" />
<div id="queryCondition" class="qcMain"
	style="display: none; width: 475px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--产品编号-->
		<td valign="bottom" align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td valign="bottom" align="right">产品名称 :</td>
		<td valign="bottom" align="left">
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--产品选择-->
		<td align="left" colspan=3 id="select_id">
			<SELECT name="product_id"	class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left">
			<input name="no" value='<%=no%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%> :</td><!-- 国别 -->
		<td>
			<select size="1"  name="q_country"  id="q_country" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(9997,q_country)%>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td valign="bottom" align="left">
			<input name="card_id" value='<%=card_id%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale)%> :</td><!-- 资金来源 -->
		<td colspan="3">
			<input type="text" name="money_source_name" id="q_money_source_name" size="27"  value="<%=q_money_source_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
			<input type="hidden" name="money_source" id="q_money_source" value="<%=q_money_source%>" /> 
			<!-- 选择 -->&nbsp;&nbsp;
            <button type="button"  class="xpbutton2" id="btnChoMoneySource" name="btnChoMoneySource" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
		</td>
	</tr>
	<tr>
		<td valign="bottom"  align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--是否关联方-->
		 <td valign="bottom"  align="left">
		 	<input type="checkbox" name="is_link" value="1" <%if(is_link.intValue()==1) out.print("checked");%> class="flatcheckbox">
		 </td>
		<td align="right">CRM投资领域 :</td>
		<td>
			<select size="1" name="invest_field" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1139,invest_field)%>
			</select>
		</td>
	</tr>
	
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
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
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=2><img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" onclick="javascript:advancedQuery()"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
								&nbsp;&nbsp;&nbsp;
							<%if (input_operator.hasFunc(menu_id, 100)) {%>
							<!--新建机构-->
                            <button type="button"  class="xpbutton5" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.newOrganization",clientLocale)%> '
								onclick="javascript:newInfo(2);"><%=LocalUtilis.language("message.newOrganization",clientLocale)%> </button>
								&nbsp;&nbsp;&nbsp;<%}%>							
							<%if (input_operator.hasFunc(menu_id, 100)) {%>
                            <!--新建个人-->
							<button type="button"  class="xpbutton5" accessKey=n name="btnNew" title=<%=LocalUtilis.language("message.newPersonal",clientLocale)%> 
								onclick="javascript:newInfo(1);"><%=LocalUtilis.language("message.newPersonal",clientLocale)%> </button>
							&nbsp;&nbsp;&nbsp;<%}%>
							<!--导入客户--><!-- 导入 -->
                            <button type="button"  class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.importCust",clientLocale)%>'	
                            		onclick="javascript:inputAction();"><%=LocalUtilis.language("message.import",clientLocale)%> </button>
							&nbsp;&nbsp;&nbsp;
							<%if (input_operator.hasFunc(menu_id, 101)) {%>
							<!--注销-->
                            <button type="button"  class="xpbutton3" name="btnCancel" title='<%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> '
								onclick="javascript:if(confirmCancel(document.theform.cust_id)){ disableAllBtn(true); document.theform.submit();}">
								<%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> </button><%}%>
						</td>												
					</tr>
					<tr>
						<td colspan=2>
							<hr noshade color="#808080" size="1">
							<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
								<tr style="background:F7F7F7;" style="margin-top:5px">
									<td align="center" id="td3">
										<select name="is_deal" id="is_deal" style="width:120px">	
											<%=Argument.getWTCustOptions(is_deal)%>
										</select>							
									</td>
									<td align="center" id="td4">
										<%=LocalUtilis.language("class.customerName",clientLocale)%>:
										<input type="text" name="name" size="18" value="<%=name%>" onkeydown="javascript:nextKeyPress(this)"/>
									</td>
									<td align="center" id="td4">
										电话:
										<input type="text" name="mobile" size="15" value="<%=mobile%>" onkeydown="javascript:nextKeyPress(this)"/>
									</td>
									<td align="center" id="td5">
										地址:
										<input type="text" name="post_address" size="30" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this)"/>
										&nbsp;&nbsp;&nbsp;&nbsp;<button type="button"  class="searchbutton" onclick="javascript:javascript:StartQuery();" />
									</td>
									
								</tr> 
							</table>
						</td>
					</tr>
				</table>				
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
								<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
								<%=LocalUtilis.language("class.ID",clientLocale)%>
						 </td><!--编号-->
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
						<td align="center"><%=LocalUtilis.language("class.custType",clientLocale)%> </td><!--客户类型-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
						<td align="center"><%=LocalUtilis.language("class.customerSource",clientLocale)%></td><!--客户来源-->
						<td align="center"><%=LocalUtilis.language("class.moneySource",clientLocale)%></td><!--资金来源-->
						<td align="center"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> </td><!--风险等级-->	
						<td align="center"><%=LocalUtilis.language("class.countryName",clientLocale)%></td><!--国别-->		
						<td align="center">累计购买金额</td>
						<td align="center">累计购买次数</td>
						<td align="center">平均购买金额</td>
						<td align="center"><%=LocalUtilis.language("message.contactInfo",clientLocale)%></td><!--联系信息-->			
						<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						<td align="center"><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--联系人-->
						<td align="center"><%=LocalUtilis.language("class.custGroups",clientLocale)%></td><!--客户群组-->
						<!--td align="center">打印</td-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						Integer cust_id = new Integer(0);
						String is_deal_name = "";
						Integer is_deal2;
						int modi_flag = 0;
						BigDecimal totalMoneyAll = null;
						long totalCount = 0;
						long avgMoney = 0;
						while (it!= null &&it.hasNext()) {
							map = (Map) it.next();
							is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
							is_deal_name = Argument.getWTName(is_deal2);
							int auth_flag = Utility.parseInt(Utility.trimNull(map.get("AUTH_FLAG")),0);
							if(auth_flag!=2){
								modi_flag = Utility.parseInt(Utility.trimNull(map.get("MODI_FLAG")),0);
							}else{
								modi_flag = -1;
							}	
							totalMoneyAll = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY_ALL")),new BigDecimal(0));
							totalCount = Utility.parseInt(Utility.trimNull(map.get("TOTAL_COUNT")),0);
							if(totalCount!=0){
								avgMoney = totalMoneyAll.longValue()/10000/totalCount;
							}else{
								avgMoney = 0;
							}
							
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><%if(modi_flag==1){%><input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>" class="flatcheckbox" ><%} %></td>
								<td width="90%" align="left">&nbsp;&nbsp;<%=Utility.trimNull(map.get("CUST_NO"))%></td>
							</tr>
						</table>
						</td>
						<td align="left" width="120px">
							<a href="javascript:showCustInfo(<%=map.get("CUST_ID")%>);"><%=Utility.trimNull(map.get("CUST_NAME"))%></a>
						</td>
						<td align="left"><input type="text" class="ednone" style="direction:rtl" style="width:100px" value="<%=Utility.trimNull(map.get("CARD_ID"))%>" readonly></td>
						<td align="center"><%=is_deal_name%></td>
						<td align=center><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>
						<td align="center"><input type="text" class="ednone" style="width:80px" value="<%=Utility.trimNull(map.get("MONEY_SOURCE_NAME"))%>" readonly></td>
						<td align="center">&nbsp;<%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>	
						<td align="left"><%=Utility.trimNull(map.get("COUNTRY_NAME"))%></td>		
						<td align="center">
							<%if(user_id.intValue()==25){%>
							<a href="javascript:showStatistics(<%=map.get("CUST_ID")%>)"><%=totalMoneyAll.longValue()/10000%> 万</a>
							<%}else{
								out.print(totalMoneyAll.longValue()/10000+" 万");
							}%>
						</td>	
						<td align="center"><%=totalCount%></td>
						<td align="center"><%=avgMoney %> 万</td>
			             <td align="center">
			             	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=map.get("CUST_ID")%>);">
			         			<IMG id="detailsImage<%=map.get("CUST_ID")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display<%=map.get("CUST_ID")%>" name="detailsFlag_display<%=map.get("CUST_ID")%>" value="0">             
			             </td>		
						<td align="center">
						<%if (input_operator.hasFunc(menu_id, 101) && modi_flag==1 ) {%>
							<a href="javascript:#" onclick="javascript:showInfo(<%=map.get("CUST_ID")%>,<%=map.get("CUST_TYPE")%>);return false; ">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--编辑-->
							</a><%} %>
						</td>
						<td align="center">
							<a href="javascript:#" onclick="javascript:contactInfo(<%=map.get("CUST_ID")%>,<%=map.get("CUST_TYPE")%>);return false; ">
								<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--联系人-->
							</a>
						</td>
						<td align="center">
							<a href="javascript:#" onclick="javascript:joinGroup('<%=map.get("CUST_ID")%>');return false; ">
								<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--加入群组-->
							</a>
						</td>
						<!--td align="center">
							<a href="javascript:#" onclick="javascript:disableAllBtn(true);printInfo(<-%=map.get("CUST_ID")%>,<-%=map.get("CUST_TYPE")%>);return false; ">
								<img src="/images/print.gif" width="16" height="16" title="打印客户信息" />
							</a>
						</td-->	
					</tr>

					<tr id="detailsTr<%=map.get("CUST_ID")%>" style="display: none">
						<td align="center" bgcolor="#FFFFFF" colspan="16" >
							<div id="detailsDiv<%=map.get("CUST_ID")%>" style="overflow-y:auto;" align="center">
								<table id="detailsTable<%=map.get("CUST_ID")%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> ：</td><!--公司电话-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("O_TEL"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.mobile",clientLocale)%> ：</td><!--手机号码-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("MOBILE"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center"><%=LocalUtilis.language("class.email",clientLocale)%> ：</td><!--电子邮件-->
										<td  width="*"><%=Utility.trimNull(map.get("E_MAIL"))%></td>
									</tr>
									<tr style="background:F7F7F7;">
										<td  width="120px" align="center"><%=LocalUtilis.language("class.postcode",clientLocale)%> ：</td><!--邮政编码-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_CODE"))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td  width="120px" align="center"><%=LocalUtilis.language("class.postAddress3",clientLocale)%> ：</td><!--通讯地址-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>  
					<%iCurrent++;
					iCount++;
					}%>

					<%int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 10);
					for (; iCurrent < page_size; iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
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
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="16"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%></td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>
<script language="javascript">
showWaitting(0);
//document.body.onbeforeunload = function (){
//									showWaitting(1);
//								};
</script>

</BODY>
</HTML>
<%customer.remove();%>