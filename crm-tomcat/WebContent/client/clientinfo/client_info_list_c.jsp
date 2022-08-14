<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String file_name = Utility.trimNull(request.getParameter("file_name"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer is_link = Utility.parseInt(request.getParameter("is_link"),new Integer(0));//�Ƿ������
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
Integer true_flag = Utility.parseInt(request.getParameter("true_flag"), new Integer(0));
Integer serv_man = request.getParameter("serv_man")==null?null:Utility.parseInt(request.getParameter("serv_man"), new Integer(0)); // 
Integer min_times = Utility.parseInt(request.getParameter("min_times"),new Integer(0));
Integer max_times = Utility.parseInt(request.getParameter("max_times"),new Integer(0));
BigDecimal min_total_money = Utility.parseDecimal(request.getParameter("min_total_money"), new BigDecimal(0.0));
BigDecimal max_total_money = Utility.parseDecimal(request.getParameter("max_total_money"), new BigDecimal(0.0));
BigDecimal ben_amount_min = Utility.parseDecimal(request.getParameter("ben_amount_min"), new BigDecimal(0.0));
BigDecimal ben_amount_max = Utility.parseDecimal(request.getParameter("ben_amount_max"), new BigDecimal(0.0));
Integer rg_start_date = Utility.parseInt(request.getParameter("rg_start_date"),new Integer(0));
Integer rg_end_date = Utility.parseInt(request.getParameter("rg_end_date"),new Integer(0));
String cust_level = Utility.trimNull(request.getParameter("cust_level"));

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status);
options = options.replaceAll("\"", "'");

String sl_status = Utility.trimNull(request.getParameter("sl_status"),"-1");
//-----------------------����ͻ�����--------------------------
if(request.getMethod().equals("POST")){
	enfo.crm.web.DocumentFile file = new enfo.crm.web.DocumentFile(pageContext);
	file.parseRequest();
	if (file.uploadFile("c:\\temp")){ 
	    if (file.uploadFile("c:\\temp")){	
			input_flag = file.readExcel(pageContext,"c:\\temp",3000,input_operatorCode);	
		}
	}	
}

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

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
vo.setTrue_flag(true_flag);
vo.setService_man(serv_man);
vo.setMin_times(min_times);
vo.setMax_times(max_times);
vo.setMin_total_money(min_total_money);
vo.setMax_total_money(max_total_money);
vo.setBen_amount_min(ben_amount_min);
vo.setBen_amount_max(ben_amount_max);
vo.setRg_begin_date(rg_start_date);
vo.setRg_end_date(rg_end_date);
vo.setCust_level(cust_level);

if (user_id.intValue()==1) vo.setOrderby("A.CUST_ID DESC");	
if("".equals(sl_status)){
	vo.setSl_status(-1);
} else {
	vo.setSl_status(Integer.parseInt(sl_status));
}
//ҳ�����
int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
IPageList pageList = first_flag!=1? customer.listProcAllExt_C(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8))
								  : new JdbcPageList();
Iterator it = first_flag!=1? pageList.getRsList().iterator(): new ArrayList().iterator();

sUrl += "&name=" + name + "&no=" + no + "&product_id=" + product_id + "&is_link=" + is_link 
					+ "&card_id=" + card_id + "&is_deal=" + is_deal + "&cust_type=" + cust_type
					+ "&true_flag=" + true_flag + "&serv_man="+serv_man +"&min_times="+min_times
					+ "&max_times="+max_times+"&min_total_money="+min_total_money
					+ "&max_total_money="+max_total_money+"&rg_start_date"+rg_start_date 
					+ "&rg_end_date"+rg_end_date+ "&cust_level"+cust_level+"&sl_status="+sl_status;
customer.remove();
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
var user_id = <%=user_id%>;

window.onload = function(){
		initQueryCondition();
		var input_flag = document.getElementById("input_flag").value;
		if(input_flag>0){
			//����ɹ�  //������  //������
	        alert('<%=LocalUtilis.language("message.importOk",clientLocale)+"��"+LocalUtilis.language("message.totalImport",clientLocale)%> '+input_flag+'<%=LocalUtilis.language("message.data",clientLocale)%> ��');
			document.getElementById("input_flag").value = 0;
		}
		var cust_type = document.getElementById("cust_type").value;
		var tdId = "td"+cust_type;
		document.getElementById(tdId).bgColor ="#99FFFF" ;
	};

function getCondition() {
	if(!document.theform.is_link.checked)
		document.theform.is_link.value = 0;//�Ƿ������δѡ��Ϊ0
	syncDatePicker(theform.rg_start_date_picker, theform.rg_start_date);
	syncDatePicker(theform.rg_end_date_picker, theform.rg_end_date);
	var iQuery = document.theform.no.value 
					+ "$" + document.theform.name.value 
					+ "$" + document.theform.product_id.value
					+ "$" + document.theform.is_link.value
					+ "$" + document.theform.card_id.value
					+ "$<%=sPage%>" 
					+ "$" + document.theform.pagesize.value
					+ "$" + document.theform.is_deal.value
					+ "$" + document.theform.cust_type.value
					+ "$" + document.theform.true_flag.value
					+ "$" + document.theform.serv_man.value
					+ "$" + document.theform.min_times.value
					+ "$" + document.theform.max_times.value
					+ "$" + document.theform.min_total_money.value
					+ "$" + document.theform.max_total_money.value
					+ "$" + document.theform.rg_start_date.value
					+ "$" + document.theform.rg_end_date.value;
	return iQuery;
}

function getCondition2() {
	if (! document.theform.is_link.checked)
		document.theform.is_link.value = 0;//�Ƿ������δѡ��Ϊ0

	syncDatePicker(theform.rg_start_date_picker, theform.rg_start_date);
	syncDatePicker(theform.rg_end_date_picker, theform.rg_end_date);

	return 'page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&name=' + document.theform.name.value 
				+ '&no=' + document.theform.no.value
				+ '&product_id=' + document.theform.product_id.value
				+ '&product_name=' + document.theform.product_name.value
				+ '&is_link=' + document.theform.is_link.value
				+ "&card_id=" + document.theform.card_id.value
				+ "&is_deal=" + document.theform.is_deal.value
				+ "&cust_type=" + document.theform.cust_type.value
				+ "&group_id=" + document.theform.group_id.value
				+ "&q_money_source=" + document.theform.q_money_source.value
				+ "&q_money_source_name=" + document.theform.q_money_source_name.value
				+ "&q_country=" + document.theform.q_country.value
				+ "&cust_source=" + document.theform.cust_source.value
				+ "&invest_field=" + document.theform.invest_field.value
				+ "&mobile=" + document.theform.mobile.value
				+ "&post_address=" + document.theform.post_address.value
				+ "&true_flag=" + document.theform.true_flag.value
				+ "&serv_man=" + document.theform.serv_man.value
				+ "&min_times=" + document.theform.min_times.value
				+ "&max_times=" + document.theform.max_times.value
				+ "&min_total_money=" + document.theform.min_total_money.value
				+ "&max_total_money=" + document.theform.max_total_money.value
				+ "&rg_start_date=" + document.theform.rg_start_date.value
				+ "&rg_end_date=" + document.theform.rg_end_date.value;
}

/**�ͻ���Ϣ�༭**/
function showInfo(cust_id,cust_type) {
	var iQuery = getCondition();
	var ret = 0;

	if (user_id==21/*���21*/) {
		if (cust_type==1)
			location.href = "customer_info_jh_modi.jsp?cust_id="+cust_id+"&"+getCondition2();
		else
			location.href = "customer_info_ent_jh_modi.jsp?cust_id="+cust_id+"&"+getCondition2();
		
		return; // �����٣������ִ�к������
	}
	
	if (cust_type == 2)
		ret = showModalDialog('clinet_info_new_end.jsp?cust_id='+cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	else if (cust_type == 1)
		ret = showModalDialog('client_info_new.jsp?cust_id='+cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	
	if (ret==1)	location.reload();
}

/**�ͻ���Ϣ���**/
function newInfo(cust_type) { //1���� 2����
	var iQuery = getCondition();

	if (user_id==21/*���21*/) {
		if (cust_type==1)
			location.href = "customer_info_jh_add.jsp?"+getCondition2();
		else 
			location.href = "customer_info_ent_jh_add.jsp?"+getCondition2();
		
		return; // �����٣������ִ�к������
	}

	var ret = 0;
	
	if (cust_type == 1) 
		ret = showModalDialog('client_info_new.jsp','','dialogWidth:850px;dialogHeight:650px;status:0;help:0');	
	else if (cust_type == 2)
		ret = showModalDialog('clinet_info_new_end.jsp','','dialogWidth:850px;dialogHeight:650px;status:0;help:0');	
	
	if (ret==1) location.reload();
}

/**��ϵ��**/
function contactInfo(cust_id, cust_type){
//	return false;
var iQuery = getCondition();
	location = "client_contacts_list.jsp?cust_id="+cust_id+"&cust_type="+cust_type+"&contactType=1"+"&iQuery=" + iQuery;

}

function StartQuery() {
	refreshPage();
}

function refreshPage() {	
	disableAllBtn(true);
	if(!document.theform.is_link.checked)
		document.theform.is_link.value = 0;//�Ƿ������δѡ��Ϊ0

	syncDatePicker(theform.rg_start_date_picker, theform.rg_start_date);
	syncDatePicker(theform.rg_end_date_picker, theform.rg_end_date);
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value 
				+'&name=' + document.theform.name.value 
				+ '&no=' + document.theform.no.value
				+ '&product_id=' + document.theform.product_id.value
				+ '&product_name=' + document.theform.product_name.value
				+ '&is_link=' + document.theform.is_link.value
				+ "&card_id=" + document.theform.card_id.value
				+ "&is_deal=" + document.theform.is_deal.value
				+ "&cust_type=" + document.theform.cust_type.value
				+ "&group_id=" + document.theform.group_id.value
				+ "&q_money_source=" + document.theform.q_money_source.value
				+ "&q_money_source_name=" + document.theform.q_money_source_name.value
				+ "&q_country=" + document.theform.q_country.value
				+ "&cust_source=" + document.theform.cust_source.value
				+ "&invest_field=" + document.theform.invest_field.value
				+ "&mobile=" + document.theform.mobile.value
				+ "&post_address=" + document.theform.post_address.value
				+ "&true_flag=" + document.theform.true_flag.value
				+ "&serv_man=" + document.theform.serv_man.value
				+ "&min_times=" + document.theform.min_times.value
				+ "&max_times=" + document.theform.max_times.value
				+ "&min_total_money=" + document.theform.min_total_money.value
				+ "&max_total_money=" + document.theform.max_total_money.value
				+ "&ben_amount_min=" + document.theform.ben_amount_min.value
				+ "&ben_amount_max=" + document.theform.ben_amount_max.value
				+ "&rg_start_date=" + document.theform.rg_start_date.value
				+ "&rg_end_date=" + document.theform.rg_end_date.value
				+ "&cust_level=" + document.theform.cust_level.value
				+ "&sl_status="+document.theform.sl_status.value;
}

function setProduct(value) {
	prodid=0;
	if (event.keyCode == 13 && value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value) {
	prodid=0;
	if (value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��');//����Ĳ�Ʒ��Ų�����
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
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
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

/*��ӡ�ͻ���Ϣ*/
function printInfo(cust_id, cust_type) {	
	if(cust_type == 1)						
		location = 'person_info_print.jsp?cust_id='+cust_id;
	else
		location = 'ent_info_print.jsp?cust_id='+cust_id;
}

/*���빦��*/
function inputAction(){
	var result = showModalDialog('client_info_import.jsp','','dialogWidth:600px;dialogHeight:500px;status:0;help:0');
	if(result){
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
		
/*�鿴��ϸ*/
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
		
	}else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
/*�鿴��ϸ*/
function setTcustomerConnectioninfor(serial_no){
	var v_tr =  "detailsTr1"+serial_no;
	var v_table = "detailsTable1"+serial_no;
	var v_flag = "detailsFlag_display1"+serial_no;
// 	var v_div = "detailsDiv1"+serial_no;
	var v_image = "detailsImage1"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
// 		if(document.getElementById(v_div).offsetHeight>200){
// 			document.getElementById(v_div).style.height=200;
// 		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}

/*��ѡ�ʽ���Դ*/
function chooseAction(){
	var money_source = document.getElementById("q_money_source").value;
	var url = "<%=request.getContextPath()%>/client/money_source_check.jsp?money_source="+money_source;
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');	
	if(ret){
		document.getElementById("q_money_source").value = ret[0];
		document.getElementById("q_money_source_name").value = ret[1];
	}
}

function showStatistics(custId) {
	showModalDialog("<%=request.getContextPath()%>/client/clientinfo/cust_contract_stat.jsp?custId="+custId, 
					'','dialogWidth:800px;dialogHeight:600px;status:0;help:0');
}

/*�鿴�ͻ���ϸ*/
function showCustInfo(custid) {
	location.href = '<%=request.getContextPath()%>/client/clientinfo/client_query_info.jsp?cust_id='+custid+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value;
}

function setTrueFlag() {
	if (typeof(document.theform.cust_id) == "undefined") {
		alert("��ѡ��ͻ���");
		return;
	}   
	if (document.theform.cust_id==null) 
		return;

	var n = 0;
	if (document.theform.cust_id.length==null) {
		if (document.theform.cust_id.checked) n++;			
	} else {	
		for (var i=0; i<document.theform.cust_id.length; i++)
			if (document.theform.cust_id[i].checked) n++;
	}

	if (n==0) {
		sl_alert("��ѡ��ͻ���");
		return;
	}

	var retval = showModalDialog('/marketing/sell/choose_cust_trueflag.jsp', '','dialogWidth:300px;dialogHeight:140px;status:0;help:0');
	if (retval!=0) {
		disableAllBtn(true);
		document.theform.action = "/marketing/sell/batch_set_cust_trueflag.jsp?page=<%=sPage%>&from=cust_info_list&true_flag="+retval;
		document.theform.method = "post";
		document.theform.submit();
	}
}

function sortTable__2() {
	var start = new Date;
	event.cancelBubble = true;
	var the_obj = event.srcElement;
	if (the_obj.tagName != "TD") return;

	var tab = this.parentElement.parentElement;
	if (tab.tagName=='TBODY') tab = tab.parentElement;
	if (tab.tagName != "TABLE") return;

	window.status = "���ڶ����ݽ������������Ժ�...";

	if(the_obj.mode == undefined) the_obj.mode = false;

	var len = tab.rows.length;

	if (len > 0 && tab.rows[len-1].cells[0].innerText.indexOf("��") >= 0) len--;
	
	if(the_obj.sort == "num")
		table_sort_num__2(tab, the_obj.cellIndex, the_obj.mode, len);
	else
		table_sort_string(tab, the_obj.cellIndex, the_obj.mode, len);
	the_obj.mode = !the_obj.mode;

	window.status = "";
}

function table_sort_num__2(obj, n, mode, len) {
	var i, j;
	var tab = obj;
	multi = (tab.sort == "multi" || tab.sort == "MULTI");
	if (!multi)
		return table_sort_num_fast__2(obj, n, mode, len);

	for(i = 1; i < len - 1; i++) {
		if (tab.rows[i].cells[0].innerText == "") continue;
		if (tab.rows[i].className == "") continue;

		for(j = i + 1;j < len; j++) {
			if (tab.rows[j].cells[0].innerText == "") continue;
			if (tab.rows[j].className == "") continue;
			if (mode) {
				if (sl_parseFloat(tab.rows[i].cells[n].innerText) < sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi) swapHistoryRow(tab, i, j, len);
				}
			} else {
				if (sl_parseFloat(tab.rows[i].cells[n].innerText) > sl_parseFloat(tab.rows[j].cells[n].innerText)) {
					tab.rows[i].swapNode(tab.rows[j]);
					if (multi) swapHistoryRow(tab, i, j, len);
				}
			}
		}
	}
}

function table_sort_num_fast__2(the_tab, col, mode, len) {
	var tab_arr = new Array();
	for (var i=1; i<len; i+=2) {
		if (the_tab.rows[i].cells[col]==null || the_tab.rows[i].cells[col].innerText == "")
			continue;
		tab_arr.push(new Array(the_tab.rows[i].cells[col].innerText, the_tab.rows[i], the_tab.rows[i+1]));
	}
	function SortArr(mode) {
		return function (arr1, arr2) {
			var flag;
			var a,b;
			a = sl_parseFloat(arr1[0]);
			b = sl_parseFloat(arr2[0]);
			//flag=mode?b.localeCompare(a):a.localeCompare(b);//flag=mode?(a<b?1:(a>b?-1:0)):(a>b?1:(a<b?-1:0));
			if (mode)
				return b-a;
			else
				return a-b;
			//return flag;
		};
	}
	tab_arr.sort(SortArr(mode));
		
	for(i=0;i<tab_arr.length;i++) {
		the_tab.firstChild.appendChild(tab_arr[i][1]);
		the_tab.firstChild.appendChild(tab_arr[i][2]);
	}
	for(i = 1; i < the_tab.rows.length - tab_arr.length*2; i++)
		the_tab.firstChild.appendChild(the_tab.rows[1]);
}
function showSurvey(cust_id){
	var url = "/client/cust_property_survey.jsp?cust_id="+cust_id;
	showModalDialog(url,'','dialogWidth:590px;dialogHeight:730px;status:0;help:0');	
}
/**�ͻ���Ϣ�༭**/
function showInfo2(cust_id,cust_name) {
	var url = '/client/clientinfo/customers_connection_info_add.jsp';
	if (showModalDialog(url+'?cust_id='+cust_id+'&cust_name='+cust_name, 
			'', 'dialogWidth:540px;dialogHeight:400px;status:0;help:0')) {
			sl_update_ok();
			location.reload();
	}
	//showModalDialog("/client/clientinfo/customers_connection_info_add.jsp?cust_id="+cust_id+"&cust_name="+cust_name,'','dialogWidth:700px;dialogHeight:500px;status:0;help:0');
}

//�޸Ŀͻ���ϵ��ʽ������Ϣ
function modiInfo(serial_no, cust_name){

	if (showModalDialog('/client/clientinfo/customers_connection_info_update.jsp?serial_no='+serial_no+'&cust_name='+cust_name,
		 	'', 'dialogWidth:540px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//ȷ�Ͽͻ���ϵ��ʽ������Ϣ
function checkInfo(serial_no, cust_name){

	if (showModalDialog('/client/clientinfo/customers_connection_info_check.jsp?serial_no='+serial_no+'&cust_name='+cust_name,
		 	'', 'dialogWidth:540px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//��˿ͻ���ϵ��ʽ������Ϣ
function recheckInfo(serial_no, cust_name, cust_id){

	if (showModalDialog('/client/clientinfo/customers_connection_info_recheck.jsp?cust_id='+cust_id+'&serial_no='+serial_no+'&cust_name='+cust_name,
		 	'', 'dialogWidth:540px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//�鿴�ͻ���ϵ��ʽ������Ϣ
function loadInfo(serial_no, cust_name){

	if (showModalDialog('/client/clientinfo/customers_connection_info_query.jsp?serial_no='+serial_no+'&cust_name='+cust_name,
		 	'', 'dialogWidth:540px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="">
<input type="hidden" name="input_flag" id="input_flag" value="<%= input_flag%>" />
<input type="hidden" name="file_name" id="file_name"value="">
<input type="hidden" name="fieldDatas" value="">
<input type="hidden" name="fieldMethod" value="">
<input type="hidden" name="fieldDataType" value="">
<input type="hidden" id="cust_type" name="cust_type" value="<%=cust_type%>" />
<div id="queryCondition" class="qcMain"	style="display: none; width: 600px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();return false;"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
		<td valign="bottom" align="left">
			<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td valign="bottom" align="right">��Ʒ���� :</td>
		<td valign="bottom" align="left">
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td><!--��Ʒѡ��-->
		<td align="left" colspan=3 id="select_id">
			<SELECT name="product_id"	class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
		<td valign="bottom" align="left">
			<input name="no" value='<%=no%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
		<td valign="bottom" align="left">
			<input name="name" value='<%=name%>' onkeydown="javascript:nextKeyPress(this)" size="18" maxlength="100">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%> :</td><!-- ���� -->
		<td>
			<select size="1"  name="q_country"  id="q_country" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(9997,q_country)%>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
		<td valign="bottom" align="left">
			<input name="card_id" value='<%=card_id%>' onkeydown="javascript:nextKeyPress(this)" size="18">
		</td>
	</tr>
	<tr>
		<td align="right">�绰 :</td>
		<td><input name="mobile" value="<%=mobile%>" onkeydown="javascript:nextKeyPress(this)" ></td>
		<td align="right">��ַ :</td>
		<td><input name="post_address" value="<%=post_address %>" onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale)%> :</td><!-- �ʽ���Դ -->
		<td colspan="3">
			<input type="text" name="money_source_name" id="q_money_source_name" size="27"  value="<%=q_money_source_name%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
			<input type="hidden" name="money_source" id="q_money_source" value="<%=q_money_source%>" /> 
			<!-- ѡ�� -->&nbsp;&nbsp;
            <button type="button"  class="xpbutton2" id="btnChoMoneySource" name="btnChoMoneySource" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
		</td>
	</tr>
	<tr>
		<td valign="bottom"  align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%> :</td><!--�Ƿ������-->
		 <td valign="bottom"  align="left">
		 	<input type="checkbox" name="is_link" value="1" <%if(is_link.intValue()==1) out.print("checked");%> class="flatcheckbox">
		 </td>
		<td align="right">CRMͶ������ :</td>
		<td>
			<select size="1" name="invest_field" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1139,invest_field)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">�ͻ���ʵ�� :</td>
		<td>
			<select size="1" name="true_flag" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustInfoTrueFlagList(true_flag)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td> <!-- �ͻ����� -->
		<td>
			<select size="1" name="serv_man" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getManager_Value(serv_man)%>
			</select>
		</td>
	</tr>

	<tr>
		<td align="right">�Ϲ����� �� :</td>
		<td>
			<input type="text" name="min_times" value="<%=min_times%>" size="3"/>
		</td>
		<td align="right">�Ϲ����� ֹ :</td>
		<td>
			<input type="text" name="max_times" value="<%=max_times%>" size="3"/>
		</td>
	</tr>
	<tr>
		<td align="right">�ۼ��Ϲ���� �� :</td>
		<td>
			<input type="text" name="min_total_money" value="<%=min_total_money%>"/>
		</td>
		<td align="right">�ۼ��Ϲ���� ֹ :</td>
		<td>
			<input type="text" name="max_total_money" value="<%=max_total_money%>"/>
		</td>
	</tr>
	<tr>
		<td align="right">��ǰ���н�� �� :</td>
		<td>
			<input type="text" name="ben_amount_min" value="<%=ben_amount_min%>"/>
		</td>
		<td align="right">��ǰ���н�� ֹ :</td>
		<td>
			<input type="text" name="ben_amount_max" value="<%=ben_amount_max%>"/>
		</td>
	</tr>
	<tr>
		<td align="right">�Ϲ����� �� :</td>
		<td>
			<input type="text" name="rg_start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(rg_start_date)%>">
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_start_date_picker,theform.rg_start_date_picker.value,this);">
			<input type="hidden" name="rg_start_date"/>
		</td>
		<td align="right">�Ϲ����� ֹ :</td>
		<td>
			<input type="text" name="rg_end_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(rg_end_date)%>">
			<input type="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.rg_end_date_picker,theform.rg_end_date_picker.value,this);">
			<input type="hidden" name="rg_end_date"/>
		</td>
	</tr>
	<tr>
		<td align="right" width="20%">�ͻ���ϵ��ʽ�޸�����״̬:</td>
		<td align="left" width="30%">
			<select size="1" name="sl_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
				<%=Argument.getCustomersInfoRecordStatusOptions(sl_status)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--ȷ��-->
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
						<td colspan=2 class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></td>
					</tr>
					<tr>
						<td align="right">		
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" 
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- ��ѯ -->
								&nbsp;&nbsp;&nbsp;
<%-- 						<%if (input_operator.hasFunc(menu_id, 100)) {%> --%>
<!-- 							�½����� -->
<%--                             <button type="button"  class="xpbutton5" accessKey=n name="btnNew"  title='<%=LocalUtilis.language("message.newOrganization",clientLocale)%> ' --%>
<%-- 								onclick="javascript:newInfo(2);"><%=LocalUtilis.language("message.newOrganization",clientLocale)%> </button> --%>
<!-- 								&nbsp;&nbsp;&nbsp; -->
<%-- 						<%}%>							 --%>
<%-- 						<%if (input_operator.hasFunc(menu_id, 100)) {%> --%>
<!--                             �½����� -->
<%-- 							<button type="button"  class="xpbutton5" accessKey=n name="btnNew"  title=<%=LocalUtilis.language("message.newPersonal",clientLocale)%>  --%>
<%-- 								onclick="javascript:newInfo(1);"><%=LocalUtilis.language("message.newPersonal",clientLocale)%> </button> --%>
<!-- 							&nbsp;&nbsp;&nbsp; -->
<%-- 						<%}%> --%>
<%-- 						<%if (input_operator.hasFunc(menu_id, 102)) {// modi: �������ÿͻ���α%>  --%>
<!-- 							<button type="button"  class="xpbutton5" title="��α����"  onclick="javascript:setTrueFlag();">��α���� </button> -->
<!-- 							&nbsp;&nbsp;&nbsp; -->
<%-- 						<%} %> --%>
<!-- 							����ͻ�--><!-- ���� -->
<%-- 						<%if (input_operator.hasFunc(menu_id, 100)) {%> --%>
<%--                             <button type="button"  class="xpbutton3" accessKey=n name="btnNew"  title='<%=LocalUtilis.language("message.importCust",clientLocale)%>'	 --%>
<%--                             		onclick="javascript:inputAction();"><%=LocalUtilis.language("message.import",clientLocale)%> </button> --%>
<!-- 							&nbsp;&nbsp;&nbsp; -->
<%-- 						<%} %> --%>
<%-- 						<%if (input_operator.hasFunc(menu_id, 101)) {%> --%>
<!-- 							ע�� -->
<%--                             <button type="button"  class="xpbutton3" name="btnCancel"  title='<%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> ' --%>
<!-- 								onclick="javascript:if(confirmCancel(document.theform.cust_id)){ disableAllBtn(true); document.theform.submit();}"> -->
<%-- 								<%=LocalUtilis.language("index.msg.cancellation",clientLocale)%> </button> --%>
<%-- 						<%}%> --%>
						</div>
						</td>
					</tr>
				</table>
				<table cellSpacing="1" cellPadding="2" style="margin-top:5px" class="table-select" >
						<tr style="background:F7F7F7;" style="margin-top:5px">
							<td width="50px" align="center" id="td0" <%if (cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
							<!--ȫ��-->
							<td width="50px" align="center" id="td1" <%if (cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeCustType(1);" class="a2"><%=LocalUtilis.language("class.personal",clientLocale)%></a></font></td>
							<!--����-->
							<td width="50px" align="center" id="td2" <%if (cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="΢���ź�"><a href="#" onclick="javascript:changeCustType(2);" class="a2"><%=LocalUtilis.language("message.organization",clientLocale)%></a></font></td>
							<!--����-->
							<td align="center" id="td3">
<%if (user_id.intValue()==21){/*�������CRMҪ���ѡ��*/%>
								<select style="width:147px" name="cust_level" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:StartQuery();">
									<%=Argument.getDictParamOptions2(1111, cust_level)%>
								</select>
								<INPUT type="hidden" name="is_deal">
<%}else{ %>
								<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:StartQuery();">	
									<%=Argument.getWTCustOptions(is_deal)%>
								<%if (input_operator.hasFunc(menu_id, 103)) {%>
									<option value="3">ע���ͻ�</option>
								<%}%>
								</select>
								<INPUT type="hidden" name="cust_level">
<%} %>							
							</td>
							<td align="center" id="td4">
								<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
									<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
								</select>
							</td>
							<td align="center" id="td5">
								<select style="width:120px" name="cust_source" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:StartQuery();">
									<%=Argument.getDictParamOptions2(1110, cust_source)%>
								</select>
							</td>
						</tr> 
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
								<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
								<%=LocalUtilis.language("class.ID",clientLocale)%>
						 </td><!--���-->
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--����-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--֤������-->
<%if (user_id.intValue()==21){/*�������CRMҪ��������*/%>
						<td align="center">�ͻ��ȼ�</td>
<%}else{%>
						<td align="center"><%=LocalUtilis.language("class.custType",clientLocale)%> </td><!--�ͻ�����-->
<%}%>
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--�ͻ����-->
						<td align="center"><%=LocalUtilis.language("class.customerSource",clientLocale)%></td><!--�ͻ���Դ-->
						<td align="center"><%=LocalUtilis.language("class.moneySource",clientLocale)%></td><!--�ʽ���Դ-->
						<td align="center"><%=LocalUtilis.language("class.gradeLevel",clientLocale)%> </td><!--���յȼ�-->	
						<td align="center"><%=LocalUtilis.language("class.countryName",clientLocale)%></td><!--����-->		
						<td align="center" style="ie2:expression(onclick=sortTable__2)" sort="num">�ۼƹ�����</td>
						<td align="center" style="ie2:expression(onclick=sortTable__2)" sort="num">�ۼƹ������</td>
						<td align="center" style="ie2:expression(onclick=sortTable__2)" sort="num">ƽ��������</td>
						<td align="center"><%=LocalUtilis.language("message.contactInfo",clientLocale)%></td><!--��ϵ��Ϣ-->			
						<td align="center">�޸���ϸ</td><!--�޸���ϸ-->	
						<td align="center">�Ƿ�ȷ��/���</td><!--�޸���ϸ-->	
					</tr>
			<%
				int iCount = 0;
				int iCurrent = 0;
				int modi_flag = 0;
				BigDecimal totalMoneyAll = null;
				long totalCount = 0;
				long avgMoney = 0;
				String is_deal_name ="";
				while (it.hasNext()) {
					Map map = (Map) it.next();
					Integer is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
					if (user_id.intValue()==21) is_deal_name=Utility.trimNull(map.get("CUST_LEVEL_NAME"));
					else is_deal_name = Argument.getWTName(is_deal2);
					int auth_flag = Utility.parseInt(Utility.trimNull(map.get("AUTH_FLAG")),0);
					if (auth_flag!=2)
						modi_flag = Utility.parseInt(Utility.trimNull(map.get("MODI_FLAG")),0);
					else
						modi_flag = -1;
						
					totalMoneyAll = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY_ALL")),new BigDecimal(0));
					totalCount = Utility.parseInt(Utility.trimNull(map.get("TOTAL_COUNT")),0);
					if (totalCount!=0)
						avgMoney = totalMoneyAll.longValue()/10000/totalCount;
					else
						avgMoney = 0; 
					String cust_id = Utility.trimNull(map.get("CUST_ID"));
					//�ж��Ƿ���Ҫ¼��˫¼��Ϣ
					CustomerConnectionLocal preDrlocal = EJBFactory.getCustomerConnection();
					CustomerConnectionVO preDrvo = new CustomerConnectionVO();
					preDrvo.setCust_id(Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),0));
					if(input_operator.hasFunc(menu_id, 100) || input_operator.hasFunc(menu_id, 101)){
						preDrvo.setFlagCheck(1);
					}
					preDrvo.setInput_man(input_operatorCode);
					List preList = preDrlocal.queryCustomerConnection(preDrvo);
					
					boolean isDoubleRecord = false; 
					String recordInfo = "��";
					Integer statusStr = -1;
					if(preList != null && preList.size()>0){
						isDoubleRecord = true;
						Map preMap = (Map)preList.get(0);
						statusStr = Integer.parseInt(preMap.get("STATUS") == null ? "0" : preMap.get("STATUS").toString());
						for(int n = 0; n < preList.size(); n++){
							preMap = (Map)preList.get(n);
							statusStr = Integer.parseInt(preMap.get("STATUS") == null ? "0" : preMap.get("STATUS").toString());
							if(statusStr == 1 || statusStr == 3){
								recordInfo = "��";
							}
						}
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
						<td align="left"><input type="text" style="direction:rtl;width:130px" class="ednone" value="<%=Utility.trimNull(map.get("CARD_ID"))%>" readonly></td>
						<td align="center"><%=is_deal_name%></td>
						<td align=center><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>
						<td align="center"><input type="text" class="ednone" style="width:80px" value="<%=Utility.trimNull(map.get("MONEY_SOURCE_NAME"))%>" readonly></td>
						<td align="center">&nbsp;<%=Utility.trimNull(map.get("GRADE_LEVEL_NAME"))%></td>	
						<td align="left"><%=Utility.trimNull(map.get("COUNTRY_NAME"))%></td>		
						<td align="center">
							<%if(user_id.intValue()==25){%>
							<a href="javascript:showStatistics(<%=map.get("CUST_ID")%>)"><%=totalMoneyAll.longValue()/10000%> ��</a>
							<%}else{
								out.print(totalMoneyAll.longValue()/10000+" ��");
							}%>
						</td>	
						<td align="center"><%=totalCount%></td>
						<td align="center"><%=avgMoney %> ��</td>
			            <td align="center">
			             	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=map.get("CUST_ID")%>);return false;">
			         			<IMG id="detailsImage<%=map.get("CUST_ID")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display<%=map.get("CUST_ID")%>" name="detailsFlag_display<%=map.get("CUST_ID")%>" value="0">             
			            </td>
			            <td align="center">
			             	<button type="button"  class="xpbutton2" name="" onclick="javascript:setTcustomerConnectioninfor(<%=map.get("CUST_ID")%>);return false;">
			         			<IMG id="detailsImage1<%=map.get("CUST_ID")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display1<%=map.get("CUST_ID")%>" name="detailsFlag_display<%=map.get("CUST_ID")%>" value="0">              
			             </td>	
			             <td align="center"><%=recordInfo%></td>	
					</tr>

					<tr id="detailsTr<%=map.get("CUST_ID")%>" style="display: none">
						<td align="center" bgcolor="#FFFFFF" colspan="16" >
							<div id="detailsDiv<%=map.get("CUST_ID")%>" style="overflow-y:auto;" align="center">
								<table id="detailsTable<%=map.get("CUST_ID")%>" class="tablelinecolor-inner" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> ��</td><!--��˾�绰-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("O_TEL"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.mobile",clientLocale)%> ��</td><!--�ֻ�����-->
										<td  width="*"><a href="#" class="a2" onclick="javascript:callCust(<%=map.get("CUST_ID")%>)"><%=Utility.trimNull(map.get("MOBILE"))%></a></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td class="text-right"><%=LocalUtilis.language("class.email",clientLocale)%> ��</td><!--�����ʼ�-->
										<td  width="*"><%=Utility.trimNull(map.get("E_MAIL"))%></td>
									</tr>
									<tr style="background:F7F7F7;">
										<td  width="120px" class="text-right"><%=LocalUtilis.language("class.postcode",clientLocale)%> ��</td><!--��������-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_CODE"))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td  width="120px" class="text-right"><%=LocalUtilis.language("class.postAddress3",clientLocale)%> ��</td><!--ͨѶ��ַ-->
										<td  width="*"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
									</tr>
								</table>
							</div>
						</td>
					</tr> 
					<!-- start ��Ϣ -->
					<tr id="detailsTr1<%=map.get("CUST_ID")%>" style="display: none" bgcolor="#FFFF99">
						<td class="tdh" colspan="17">
							<table id="detailsTable1<%=map.get("CUST_ID")%>" bgcolor="#000000" style="display: none" border="0" cellpadding="2" cellspacing="1" width="100%">
								<tr>
									<td align="center" bgcolor="#FFFFFF">�޸ĺ��ֻ���</td>
									<td align="center" bgcolor="#FFFFFF">�Ǽ�ʱ��</td>
									<td align="center" bgcolor="#FFFFFF">¼����</td>
									<td align="center" bgcolor="#FFFFFF">״̬</td>
									<td align="center" bgcolor="#FFFFFF">ȷ����</td>
									<td align="center" bgcolor="#FFFFFF">ȷ��ʱ��</td>
									<td align="center" bgcolor="#FFFFFF">�����</td>
									<td align="center" bgcolor="#FFFFFF">���ʱ��</td>
									<td align="center" bgcolor="#FFFFFF">�༭</td>
									<td align="center" bgcolor="#FFFFFF">�鿴</td>	
									<td align="center" bgcolor="#FFFFFF">ȷ��</td>
									<td align="center" bgcolor="#FFFFFF">���</td>
								</tr>
								<!-- ��ϸ -->
								<%
								if(preList != null && preList.size()>0){
									isDoubleRecord = true;
									Map preMap = (Map)preList.get(0);
									for(int n = 0; n < preList.size(); n++){
										preMap = (Map)preList.get(n);
									
										String n_mobile = preMap.get("N_MOBILE") == null ? "" : preMap.get("N_MOBILE").toString();
										boolean isModi = false;
										boolean isCheck = false;
										boolean isRecheck = false;
										Integer statusDri = Integer.parseInt(preMap.get("STATUS") == null ? "1" : preMap.get("STATUS").toString());
										if(statusDri == 1){
											isModi = true;
											isCheck = true;
										}  
										if (statusDri == 3) {
											isRecheck = true;
										}
										
										String statusName = preMap.get("STATUS_NAME") == null ? "��" : preMap.get("STATUS_NAME").toString();
										String input_time = preMap.get("INPUT_TIME") == null ? "" : preMap.get("INPUT_TIME").toString();
										String input_man = preMap.get("INPUT_MAN") == null ? "" : preMap.get("INPUT_MAN").toString();
										String input_man_name = "";
										if(!"".equals(input_man)){
											input_man_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(input_man),new Integer(0)));
										}
										input_time = input_time.substring(0,input_time.lastIndexOf("."));
										String check_time = preMap.get("CHECK_TIME") == null ? "" : preMap.get("CHECK_TIME").toString();
										if(!"".equals(check_time)){
											check_time = check_time.substring(0,check_time.lastIndexOf("."));
										}
										String checker = preMap.get("CHECKER") == null ? "" : preMap.get("CHECKER").toString();
										String checker_name = "";
										if(!"".equals(checker)){
											checker_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(checker),new Integer(0)));
										}
										
										String re_check_time = preMap.get("RE_CHECK_TIME") == null ? "" : preMap.get("RE_CHECK_TIME").toString();
										if(!"".equals(re_check_time)){
											re_check_time = re_check_time.substring(0,re_check_time.lastIndexOf("."));
										}
										String re_checker = preMap.get("RE_CHECKER") == null ? "" : preMap.get("RE_CHECKER").toString();
										String re_checker_name = "";
										if(!"".equals(re_checker)){
											re_checker_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(re_checker),new Integer(0)));
										}
												
								%>
									<tr>
										<td bgcolor="#FFFFFF"><%=n_mobile%></td>
										<td bgcolor="#FFFFFF" align="right"><%=input_time %></td>
										<td bgcolor="#FFFFFF"><%=input_man_name%></td>
										<td bgcolor="#FFFFFF"><%=statusName%></td>
										<td bgcolor="#FFFFFF"><%=checker_name%></td>
										<td bgcolor="#FFFFFF"><%=check_time%></td>
										<td bgcolor="#FFFFFF"><%=re_checker_name%></td>
										<td bgcolor="#FFFFFF"><%=re_check_time%></td>
										<td bgcolor="#FFFFFF" align="center">
										<%if (isModi) { //�ɱ༭%>
												<a href="#" onclick="javascript:modiInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=map.get("CUST_NAME")%>');">						 	
												<img src="<%=request.getContextPath()%>/images/edit.gif" width="16" height="16" title="�༭�ͻ���ϵ��ʽ�޸���Ϣ"/></a>
										<%} else { %>
							       				 
										<%} %>
										</td>
										<td bgcolor="#FFFFFF" align="center">
											<a href="#" onclick="javascript:loadInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=map.get("CUST_NAME")%>');">						 	
											<img src="<%=request.getContextPath()%>/images/search.gif" width="16" height="16" title="�鿴�ͻ���ϵ��ʽ�޸���Ϣ"/></a>
										</td>										
										<td bgcolor="#FFFFFF" align="center">
										<%if (input_operator.hasFunc(menu_id, 100)){ %>	
										<%if (isCheck) { //��ȷ��%>
												<a href="#" onclick="javascript:checkInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=map.get("CUST_NAME")%>');">					 	
												<img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title="ȷ�Ͽͻ���ϵ��ʽ�޸���Ϣ"/></a>
										<%} else { %>
							       				
										<%}} %>
										</td>
										<td bgcolor="#FFFFFF" align="center">
										<%if (input_operator.hasFunc(menu_id, 101)){ %>	
										<%if (isRecheck) { //�����%>
												<a href="#" onclick="javascript:recheckInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=map.get("CUST_NAME")%>','<%=cust_id%>');">					 	
												<img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title="��˿ͻ���ϵ��ʽ�޸���Ϣ"/></a>
										<%} else { %>
							       				
										<%}} %>
										</td>
									</tr>
								<%}}%>
							</table>
						</td>
					</tr>		
					<!-- end ��Ϣ --> 
				<%	iCurrent++;
					iCount++;
				}

				for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){ %>
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
					</tr>
				<%}%>
					<tr class="trbottom">
						<!--�ϼ�--><!--��-->
                        <td class="tdh" align="left" colspan="<%if (user_id.intValue()==25){/*��������*/out.print(17);}else{out.print(16);} %>"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
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