<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.customer.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量

String viewStr = "CONTRACT_SUB_BH$CUST_NO$CUST_NAME$CARD_ID$PRODUCT_NAME$RG_MONEY$CHANNEL_NAME$QS_DATE";
String totalValueStr = "RG_MONEY";
Integer v_op_code = Utility.parseInt(Utility.trimNull(request.getParameter("v_op_code")),input_operatorCode);
Integer v_setView = Utility.parseInt(Utility.trimNull(request.getParameter("v_setView")),new Integer(0));

String tempView = Argument.getMyMenuViewStr(menu_id,v_op_code);
if(tempView!=null&&!tempView.equals("")){
	viewStr = tempView;
}
//获得对象
StringBuffer custIdSAll = new StringBuffer();
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo2 = new CustomerVO();
Map fieldsMap = Argument.getMenuViewMap(menu_id,viewStr);
if(fieldsMap == null||fieldsMap.isEmpty()){
	fieldsMap = new HashMap();
	viewStr = "";
}


String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
Integer q_pre_flag= Utility.parseInt(request.getParameter("q_pre_flag"),new Integer(0));
Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),overall_product_id);
Integer q_check_flag=Utility.parseInt(request.getParameter("q_check_flag"),new Integer(0));//默认 全部
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_card_id = Utility.trimNull(request.getParameter("q_card_id"));
String query_contract_bh = Utility.trimNull(request.getParameter("query_contract_bh"));
BigDecimal min_rg_money = Utility.parseDecimal(request.getParameter("min_rg_money"), new BigDecimal(0),2,"1");//最低登记额度
BigDecimal max_rg_money = Utility.parseDecimal(request.getParameter("max_rg_money"), new BigDecimal(0),2,"1");//最高登记额度
Integer q_cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_cust_type")),new Integer(0));
Integer q_group_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_group_id")),new Integer(0));
Integer q_class_detail_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_class_detail_id")),new Integer(0));
Integer q_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_channel_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
Integer q_managerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_managerID")),new Integer(0));
Integer q_sub_productId = Utility.parseInt(Utility.trimNull(request.getParameter("q_sub_productId")),new Integer(0));
Integer q_team_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_team_id")),new Integer(0));

Integer q_contract_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_contract_type")),new Integer(0));

//帐套暂时设置
input_bookCode = new Integer(1);

//页面辅助参数
boolean print_flag = false;//打印标志
String tempUrl = "";
String[] totalColumn = {"RG_MONEY","TO_AMOUNT"};
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
List list = null;
Map map = null;

//url设置
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_pre_flag="+q_pre_flag;
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_check_flag="+q_check_flag;
tempUrl = tempUrl+"&q_cust_name="+q_cust_name;
tempUrl = tempUrl+"&q_card_id="+q_card_id;
tempUrl = tempUrl+"&query_contract_bh="+query_contract_bh;
tempUrl = tempUrl+"&max_rg_money="+max_rg_money;
tempUrl = tempUrl+"&min_rg_money="+min_rg_money;
tempUrl = tempUrl+"&q_cust_type="+q_cust_type;
tempUrl = tempUrl+"&q_class_detail_id="+q_class_detail_id;
tempUrl = tempUrl+"&q_group_id="+q_group_id;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
tempUrl = tempUrl+"&q_managerID="+q_managerID;
tempUrl = tempUrl+"&q_sub_productId="+q_sub_productId;
tempUrl = tempUrl+"&q_contract_type=" + q_contract_type;
tempUrl = tempUrl+"&q_team_id="+q_team_id+"&ifhavaAll=1";
sUrl = sUrl + tempUrl;

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setProduct_code(q_productCode);
vo.setPre_flag(q_pre_flag);
vo.setProduct_id(q_productId);
vo.setCheck_flag(q_check_flag);
vo.setCust_name(q_cust_name);
vo.setCard_id(q_card_id);
vo.setContract_sub_bh(query_contract_bh);
vo.setMax_rg_money2(max_rg_money);
vo.setMin_rg_money2(min_rg_money);
vo.setCust_type(q_cust_type);
vo.setClassdetail_id(q_class_detail_id);
vo.setCust_group_id(q_group_id);
vo.setChannel_id(q_channel_id);
vo.setProduct_name(q_product_name);
vo.setManagerID(q_managerID);
vo.setSub_product_id(q_sub_productId);
vo.setTeam_id(q_team_id);
vo.setContract_type(q_contract_type);
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
if(first_flag != 1)
	pageList = contract.queryPurchanseContract_crm(vo,totalColumn,t_sPage,t_sPagesize);
if(pageList != null)
	list = pageList.getRsList();

int rgCustCount = 0;
if(pageList != null && list.size()>0){
	print_flag = true;
}
if(first_flag != 1){
	List listAll = contract.statLess300Contract(q_productId);/*contract.queryPurchanseContract_crm(vo);
	for(int i=0;i<listAll.size();i++){
		Map mapAll = (Map)listAll.get(i);
		int cust_type = Utility.parseInt(Utility.trimNull(mapAll.get("CUST_TYPE")),0);
		BigDecimal rgMoney = Utility.parseDecimal(Utility.trimNull(mapAll.get("RG_MONEY")),new BigDecimal(0.00),2,"1");
		if(cust_type==1 && rgMoney.intValue()<3000000){
			rgCustCount++;
		}
	}*/
	Map mapAll = (Map)listAll.get(0);
	rgCustCount = Utility.parseInt(Utility.trimNull(mapAll.get("RG_STAT")),0);
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.subscription",clientLocale)%> </TITLE><!--认购-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/tuomin.js"></SCRIPT>

<style type="text/css">
html{font-size:14px;}
body { background:#fff; padding: 0;margin: 0; color:#666; font: 14px/150%  "Lucida Grande", Lucida Sans Unicode, Hiragino Sans GB, WenQuanYi Micro Hei, Verdana, Aril, sans-serif;-webkit-text-size-adjust: none; -webkit-font-smoothing: antialiased;}
ul{margin:0; padding:0;}
li{list-style: none;}
a{blr:expression(this.onFocus=this.blur()); cursor: pointer;text-decoration: none;}
a,input,button{outline:none!important;}
a {text-decoration: none!important; }
embed{display:none;}

.popup-window{position: absolute; z-index: 10; top:35%; left:50%; width:300px; height:200px; margin-left: -150px; border-radius: 5px; border: 1px solid #ccc; background-color: #f5f5f5;}
.popup-window .popup-title{background-color: #eee; padding:10px; border-bottom: 1px solid #ccc; border-radius: 5px 5px 0 0;}
.popup-window .popup-body{padding:10px;}
.popup-window .popup-body p{font-size: 16px; margin-bottom:30px ; text-align: center;}
.popup-window .popup-body .btn-group{font-size: 0; width: 200px; margin: 0 auto;}
.popup-window .popup-body .btn-group li{display:inline-block; text-align: center; width: 50%; float: left; padding:0 10px;}
.popup-window .popup-body .btn-group li div{padding:8px 30px; font-size: 16px; background-color: #1b90ef; border-radius: 5px; border: none; color:#fff;}
</style>
<script language=javascript>

/*启动加载*/
window.onload = function(){
	selectProduct(<%=q_productId%>);
	initQueryCondition();
	var q_cust_type = document.getElementById("q_cust_type").value;
	var tdId = "td"+q_cust_type;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function getSQuery(){
	var sQuery = document.theform.q_productId.value ;
	var sQuery = sQuery + "$" +document.theform.q_productCode.value;
	var sQuery = sQuery + "$" +document.theform.q_cust_name.value;
	var sQuery = sQuery + "$" +document.theform.query_contract_bh.value;
	var sQuery = sQuery + "$" +document.theform.q_pre_flag.value;
	if (document.theform.pagesize!=null) {
		var sQuery = sQuery + "$" +document.theform.pagesize.value;
	}else{
		var sQuery = sQuery + "$" +" ";

	}
	var sQuery = sQuery + "$" +document.theform.q_check_flag.value;
	var sQuery = sQuery + "$" +document.theform.q_product_name.value;
	var sQuery = sQuery + "$" +document.theform.q_managerID.value;
	var sQuery = sQuery + "$" +document.theform.q_team_id.value;
	var sQuery =sQuery + "$" + document.theform.q_card_id.value;
	var sQuery =sQuery + "$" + document.theform.q_channel_id.value;
	var sQuery =sQuery + "$" + document.theform.q_contract_type.value;
	var sQuery =sQuery + "$" +<%=t_sPage%>;
	return sQuery;
}
/*修改2*/
function showInfo2(serialno, checkflag,ht_status,bank_id,pre_flag){
	var sQuery = getSQuery();
	disableAllBtn(true);
	if(pre_flag==1){
		location = 'subscribe_edit1.jsp?serial_no='+serialno+'&bank_id='+bank_id+'&check_flag='+checkflag+'&ht_status='+ht_status+'&sQuery=' + sQuery;
	}
	else{
		location = 'subscribe_edit2.jsp?serial_no='+serialno+'&check_flag='+checkflag+'&bank_id='+bank_id+'&sQuery=' + sQuery;
	}

}

/*查询功能*/
function StartQuery(){
	refreshPage();
}
var sQuery_res = "";
/*新增*/
function newInfo(){
	disableAllBtn(true);
	var sQuery = getSQuery();
<%if (user_id.intValue()==5){/*中泰信托要求所有的认购都要通过预约*/%>
	location = 'subscribe_add1.jsp?page=1&sQuery=' + sQuery;
<%}else{%>
	if(sl_vb_comfirm('<%=LocalUtilis.language("message.systemVailidation",clientLocale)%>：\n\n<%=LocalUtilis.language("message.appointement",clientLocale)%> ？')){//系统确认//您是否已经预约
		location = 'subscribe_add1.jsp?page=1&sQuery=' + sQuery;
	}
	else{
		location = 'subscribe_add2.jsp?page=1&sQuery=' + sQuery;
	} 
}
function chosen(flag) {
	document.getElementById("ispopupWindow").style.display = "none";
 	if(flag == 1){//系统确认//您是否已经预约
		location = 'subscribe_add1.jsp?page=1&sQuery=' + sQuery_res;
	}
<%}%>
}

/*查看受益人*/
function showBenifiter(contract_id){
	disableAllBtn(true);
	var sQuery = getSQuery();
	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id='+contract_id+'&page=1&sQuery=' + sQuery;
}

/*修改*/
function showInfo(serialno, checkflag,ht_status,pre_flag){
	disableAllBtn(true);
	var sQuery = getSQuery();

	if(pre_flag==1){
		location = 'subscribe_edit1.jsp?serial_no='+serialno+'&check_flag='+checkflag+'&ht_status='+ht_status+'&sQuery=' + sQuery;
	}
	else{
		location = 'subscribe_edit2.jsp?serial_no='+serialno+'&check_flag='+checkflag+'&sQuery=' + sQuery;
	}
}



/*删除功能*/
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action="subscribe_del.jsp"
		document.theform.submit();
	}
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	if (document.theform.pagesize!=null) {
		var url = "subscribe_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;
	} else {
		var url = "subscribe_list.jsp?page=1";
	}
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&q_pre_flag=' + document.theform.q_pre_flag.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_card_id=' + document.theform.q_card_id.value;
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	var url = url + '&max_rg_money=' +  document.theform.max_rg_money.value;
	var url = url + '&min_rg_money=' +  document.theform.min_rg_money.value;
	var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
	var url = url + '&q_group_id=' +document.theform.q_group_id.value;
	var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	var url = url + '&q_channel_id=' +document.theform.q_channel_id.value;
	var url = url + '&q_product_name='+document.theform.q_product_name.value;
	var url = url + '&q_managerID=' +document.theform.q_managerID.value;
	var url = url + '&q_sub_productId=' +document.theform.q_sub_productId.value;
	var url = url + '&q_team_id=' +document.theform.q_team_id.value;
	var url = url + '&q_contract_type=' + document.theform.q_contract_type.value;
	location = url;
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	if(event.keyCode == 13 && value != ""){
        var j = value.length;

		if (escape(value).indexOf("%u") >= 0) //存在有中文的情况按产品名称检索
		{
			for(i=0;i<document.theform.q_productId.options.length;i++){
				if(document.theform.q_productId.options[i].text.indexOf(value) >= 0)
				{
					document.theform.q_productId.options[i].selected=true;
					prodid = document.theform.q_productId.options[i].value;
					break;
				}
			}
		}else{
			for(i=0;i<document.theform.q_productId.options.length;i++){
				if(document.theform.q_productId.options[i].text.substring(0,j)==value){
					document.theform.q_productId.options[i].selected=true;
					prodid = document.theform.q_productId.options[i].value;
					break;
				}
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		StartQuery();
	}
	
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value != ""){
		var j = value.length;
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.indexOf(value) >= 0)
			{
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		document.theform.q_productCode.focus();
	}
}

//打印
function print(serial_no,tmp_id){
	disableAllBtn(true);
	<%if(user_id.intValue()==1){%>
  		location = 'subscribe_print2.jsp?serial_no='+serial_no;
  	<%}else if (user_id.intValue()==24){%>//厦门信托
		if (tmp_id==0){
			alert('打印模板未设置，请联系相关人员到销售参数中设置');
			return false;
		}else{
			location = 'contract_print.jsp?serial_no='+serial_no+'&template_id='+tmp_id;
		}
	<%}
	else{%>
  		location = 'subscribe_print.jsp?serial_no='+serial_no;
  	<%}%>
}

/*导出*/
function writefile(){
	var url = "subscribe_outExcel.jsp?bookcode=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&page=1&pagesize="+ document.theform.pagesize.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&q_pre_flag=' + document.theform.q_pre_flag.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_card_id=' + document.theform.q_card_id.value;
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	var url = url + '&max_rg_money=' +  document.theform.max_rg_money.value;
	var url = url + '&min_rg_money=' +  document.theform.min_rg_money.value;
	var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
	var url = url + '&q_group_id=' +document.theform.q_group_id.value;
	var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	var url = url + '&q_channel_id=' +document.theform.q_channel_id.value;
	var url = url + '&q_product_name' +document.theform.q_product_name.value;
	var url = url + '&q_managerID' +document.theform.q_managerID.value;
	location = url;

	if(sl_confirm("<%=LocalUtilis.language("message.exportData",clientLocale)%> ")){//导出数据
		location = url;
	}

	showWaitting(0);
}

function changeCustType(flag){
	document.getElementById("q_cust_type").value=flag;
	refreshPage();
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}

//查询子产品
function selectProduct(product_id){
	if(product_id>=0){
		utilityService.getSubProductFlag(product_id,function(data){
				var arrayL = data.split("$");
				var sub_product_flag = arrayL[0];
				if(sub_product_flag==1){
					document.getElementById("sub_product_flag").style.display = "";
					utilityService.getSubProductOptionS(product_id,0,function(data1){
						$("sub_product_flag").innerHTML = "<select size='1' name='q_sub_productId' onkeydown='javascript:nextKeyPress(this)' class='productname' onchange='javascript:StartQuery();'>"+
						data1+"</select>&nbsp;&nbsp;";
					});
				}else{
					document.getElementById("sub_product_flag").style.display = "none";
				}	
			}
		);
	}else{
		sl_alert("产品不存在,请重新选择!");
		return false;
	}
}

//--------------提交审批功能-----------------//
function referflow(identity_code)
{   
   var object_id=identity_code;
   
	 if("1"=="1")
   {
	   if(confirm("确定要提交吗？"))
		{
			disableAllBtn(true);
			var returnValue=actionFlow("FlowUtil","initFlow",object_id+"@@"+"ContractStamp"+"@@"+"ContractStampFlow"+"@@"+"<%=input_operatorCode%>");
			if(returnValue=="success"){
				alert("流程任务生成成功！")
				location.reload();
			}
		}else {
			location.reload();
		}
	}
}
//查看产品信息
function showProduct(product_id){	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}

function setCustTrueFlag() {
	if (document.theform.serial_no==null) 
		return;

	var n = 0;
	if (document.theform.serial_no.length==null) { 
		if (document.theform.serial_no.checked) n ++;			
		document.theform.cust_id.checked = document.theform.serial_no.checked;
	} else {		
		for (var i=0; i<document.theform.serial_no.length; i++) {
			if (document.theform.serial_no[i].checked) n++;
			document.theform.cust_id[i].checked = document.theform.serial_no[i].checked;
		}		
	}

	if (n==0) {
		sl_alert("请选择记录！");
		return;
	}

	var retval = showModalDialog('/marketing/sell/choose_cust_trueflag.jsp', '','dialogWidth:100px;dialogHeight:50px;status:0;help:0');
	if (retval!=null && retval!=0) {
		disableAllBtn(true);
		document.theform.action = "batch_set_cust_trueflag.jsp?page=<%=t_sPage%>&true_flag="+retval;
		document.theform.method = "post";
		document.theform.submit();
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="subscribe_del.jsp">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
  	 <button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   </td>
  </tr>
</table>

<table width="100%">
	<tr>
		<td align="right"><%=LocalUtilis.language("class.applyFlag",clientLocale)%>:</td><!--认购方式-->
		<td align="left">
			<select size="1" name="q_pre_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px">
				<%=Argument.getPreFlagOptions(q_pre_flag)%>
			</select>
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.contractID",clientLocale)%>:</td><!--合同编号-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="25" value="<%=query_contract_bh%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
		<td><input   name="q_cust_name" value="<%=q_cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="25"/></td>
		<td  align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%>:</td><!--证件号码-->
		<td  align="left">
			<input type="text" name="q_card_id" value="<%=q_card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="25">
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getCheckFlagOptions(q_check_flag)%>
			</select>
		</td>
		<td align="right">渠道来源:</td><!--渠道来源-->
		<td>
			<select size="1" name="q_channel_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 150px">
				<%=Argument.getChannelOptions(input_bookCode,"","",q_channel_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td>
		<td >
			<input type="text" name="q_product_name" size="25" value="<%=Utility.trimNull(q_product_name)%>"/>
		</td>
		<td  align="right">客户经理:</td>
		<td  align="left">
			<select size="1" name="q_managerID" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getManager_Value(q_managerID)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">团队:</td>
		<td align="left">
			<select size="1" name="q_team_id" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 150px">
				<%=Argument.getTeamName(q_team_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align=right>合同分类 : </td>
		<td>
			<select size="1" name="q_contract_type" onkeydown="javascript:nextKeyPress(this)" style="width:150px;">
				<option value="0" <%if(q_contract_type.intValue() == 0){%>selected<%}%> >请选择</option>
				<option value="1" <%if(q_contract_type.intValue() == 1){%>selected<%}%> >前台销售人员合同</option>
				<option value="2" <%if(q_contract_type.intValue() == 2){%>selected<%}%> >产品部门合同</option>
				<option value="3" <%if(q_contract_type.intValue() == 3){%>selected<%}%> >代销合同</option>
			</select>
		</td>
	</tr>

	<tr>
		<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%>:</td><!--登记额度-->
		<!--从-->
        <td colspan="3">
        		<%=LocalUtilis.language("message.start",clientLocale)%>
        		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="13" name="min_rg_money" size="29"
				value="<%=Utility.trimNull(min_rg_money)%>" />&nbsp;&nbsp;
                <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->&nbsp;&nbsp;
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="13" name="max_rg_money" size="29"
				value="<%=Utility.trimNull(max_rg_money)%>" />
		</td>
	</tr>
	<tr>
		<td  align="center" colspan=4>
		<button type="button"   class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<div>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<!--客户分析--><!--客户综合查询-->
            <td colspan="2" class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("message.subscription",clientLocale)%> </b></font><!--销售管理--><!--认购--></td>
		</tr>
		<tr>
			<td align=right>
			<div class="btn-wrapper">
			<font color="bulue" size="3">小于3百万的客户数：</font><font color="red" size="4"><%=rgCustCount%>个</font>
				<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)<!--查询--></button>&nbsp;&nbsp;&nbsp;

				<!--显示设定-->
		      	<%if(v_setView.intValue()!=1){%>
				<button type="button"   class="xpbutton5" onclick="javascript:setView()"><%=LocalUtilis.language("message.displaySet",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<%}%>
				<%if (input_operator.hasFunc(menu_id, 100)) {%>
				<!--新建-->
		        <button type="button" class="xpbutton3" accessKey=n name="btnNew" title='<%=LocalUtilis.language("message.new",clientLocale)%>' onclick="javascript:newInfo();">
					<%=LocalUtilis.language("message.new",clientLocale)%>(<u>N</u>)</button>&nbsp;&nbsp;&nbsp;
				<%}%>
				<%if (input_operator.hasFunc(menu_id, 101)) {%>
				<!-- 删除 -->
		        <button type="button"   class="xpbutton3" accessKey=d name="btnCancel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>'	onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();">
					<%=LocalUtilis.language("message.delete",clientLocale)%>(<u>D</u>)</button>&nbsp;&nbsp;&nbsp;
				<%}%>
				<button type="button"  class="xpbutton4" <%if(!print_flag)out.print("disabled");%> name="btnOk" title="<%=LocalUtilis.language("menu.generateXSL&Export",clientLocale)%> " Onclick="javascript:writefile();"><%=LocalUtilis.language("message.exportData",clientLocale)%> </button>
				&nbsp;&nbsp;&nbsp;<!--生成XSL文件并导出--><!--导出数据-->
				<%if (input_operator.hasFunc(menu_id, 112)) {%>
				<!-- 设置客户真实性 -->
		        <button type="button"   class="xpbutton5" title='设置客户真实性'	onclick="javascript:setCustTrueFlag();">设置客户真实性</button>
				<%}%>
			</div>
			</td>
		</tr>
	</table>
	
</div>

<div id="headDiv" style="margin-top:5px">

<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC" class="table-select" >
		<tr style="background:F7F7F7;">
			<td width="50px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"  ><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
			<!--全部-->
			<td width="50px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"  ><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
			<!--个人-->
			<td width="50px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑" ><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
			<!--机构-->
			<td>
				<select size="1" name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
					<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
				</select>
			</td>
			<td id="sub_product_flag"style="display:none">
				<select size="1" id="q_sub_productId" name="q_sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
					<option value="<%=q_sub_productId%>">请选择 </option>				
				</select>
			</td>
			<td>
				<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
				<button type="button"   class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);StartQuery();" />
			</td>
			<!--产品-->
			<td align="center" id="td4">
				<select name="q_group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
				</select>
			</td>
			<!--客户群组-->
			<td align="center" id="td5">
				<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
					<option>请选择关注度</option>
					<%=Argument.getCustClassOption(new Integer(30),q_class_detail_id)%>
				</select>
			</td>
			<!--关注度-->
		</tr>
</table>
</div>
<br/>
<%if(user_id.intValue()==16){ %><div style="overflow:auto;width:1100;height: 300;"><%} %>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" <%if(user_id.intValue()==16){ %>style="table-layout:fixed"<%} %>>
	<tr class=trh>
		<%String[] fieldsArr =Utility.splitString(viewStr,"$");
		for(int j=0;j<fieldsArr.length;j++){
			String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
			String setWith = "";
			if(user_id.intValue()==16){
				if("产品名称".equals(setWithName))
					setWith = "330px;";
				else if("客户名称".equals(setWithName))
					setWith = "120px;";
				else if("合同实际编号".equals(setWithName)||"银行账号".equals(setWithName)||"证件号码".equals(setWithName))
					setWith = "180px;";
				else if("签署日期".equals(setWithName)||"受益级别".equals(setWithName)||"缴款方式".equals(setWithName))
					setWith = "70px;";
				else
					setWith = "100px;";
			}
			if(j==0){
		%>
			<td align="center" width="<%=Utility.trimNull(setWith)%>"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">
			<%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%>
			</td>
		<%
			}else{
		%>
				<td align="center" width="<%=Utility.trimNull(setWith)%>"><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td>
		<%
			}
		}%>
		<td align="center" width="35px;"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
		<td align="center" width="75px;"><%=LocalUtilis.language("class.qCustName",clientLocale)%> </td><!--受益人姓名-->
		<td align="center" width="35px;"><%=LocalUtilis.language("message.print",clientLocale)%> </td><!--打印-->
		<%if(user_id.intValue()==10002||user_id.intValue()==999){%>
		<td align="center" width="70">合同用印</td><!--合同用印提交-->
		<%} %>
	</tr>

<%
int iCount = 0;
int iCurrent = 0;
Iterator iterator = null;
if(list != null)
	iterator = list.iterator();
String ht_status = "";
Integer pre_flag;
Integer check_flag;
String bank_id;
while(iterator!= null && iterator.hasNext())
{
	map = (Map) iterator.next();
	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	ht_status = Utility.trimNull(map.get("HT_STATUS"));
	pre_flag = Utility.parseInt(Utility.trimNull(map.get("PRE_FLAG")),new Integer(0));
	check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
	bank_id  = Utility.trimNull(map.get("BANK_ID"));
	custIdSAll.append(Utility.trimNull(map.get("CUST_ID")));//将获得客户ID组合成字符串
	Integer template_id = Utility.parseInt(Utility.trimNull(map.get("CONTRACT_PRT_TEMPLATE")),new Integer(0));
	if(iCount + 1 < pageList.getRsList().size())
		custIdSAll.append(",");

%>
        <tr  class="tr<%=(iCurrent % 2)%>">
		<%
		for(int j=0;j<fieldsArr.length;j++){
			if(j==0){
		%>
			<td class="tdh" align="center" height="48">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="9%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox">
						<input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>" style="display:none"/></td>
					<td width="91%" align="left"><%=(map.get(fieldsArr[j]) == null && "TO_MONEY".equals(fieldsArr[j])) ? "0.00": map.get(fieldsArr[j])%></td>
				</tr>
			</table>
			</td>
		<%
			}else{
				int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
				switch (iType){
					case 1:
		%>
				<td align="left" height="48" > 
					<%if ("PRODUCT_NAME".equals(fieldsArr[j])){//加入产品的连接%>
					<a href="#" onclick="javascript:showProduct('<%=map.get("PRODUCT_ID")%>');"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></a>
					<%}else{out.print(Utility.trimNull(map.get(fieldsArr[j])));
					}
					%></td>
		<%
					break;
					case 2:
		%>
					<td align="right" height="48"><%=Format.formatMoney(((BigDecimal)map.get(fieldsArr[j])))%></td>
			<%
						break;
						case 3:
			%>
					<td align="center" height="48"><%=Format.formatDateLine(((Integer)map.get(fieldsArr[j])))%></td>
			<%
						break;
						case 4:
						break;
						default:
						break;
					}
				}
			}
			%>

		<td align="center">
			<%if (input_operator.hasFunc(menu_id, 102)) {
				if(bank_id!=null&&bank_id.equals("")){%>
               	 <!--<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<!%=serial_no%>,<!%=check_flag%>,<!%=ht_status%>,<!%=pre_flag%>);">&gt;&gt;</button>-->
	             <a href="javascript:showInfo(<%=serial_no%>,<%=check_flag%>,<%=ht_status%>,<%=pre_flag%>);">
	           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
	           	 </a>
				<%}
				else{%>
               	<!--<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo2(<!%=serial_no%>,<!%=check_flag%>,<!%=ht_status%>,<!%=bank_id%>,<!%=pre_flag%>);">&gt;&gt;</button>-->
               	<a href="javascript:showInfo2(<%=serial_no%>,<%=check_flag%>,<%=ht_status%>,<%=bank_id%>,<%=pre_flag%>);">
	           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
	           	 </a>
			<%}
		  }%>
		</td>

		<td align="center">
		<%if (input_operator.hasFunc(menu_id, 102)){%>
			<!--<button type="button"  class="xpbutton2" name="btnBenifitor" title="查看受益人信息"  onclick="javascript:showBenifiter(<%=serial_no%>);">&gt;&gt;</button>-->
			  <a href="javascript:showBenifiter(<%=serial_no%>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/user.gif" width="16" height="16">
           	 </a>
		<%}%>
		</td>
		<td align="center">
		<%if (input_operator.hasFunc(menu_id, 102)){%>
			 <a href="#" onclick="javascript:print(<%=serial_no%>,<%=template_id %>);">
           		<img border="0" src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16">
           	 </a>
		<%}%>
		</td>

	<%
	if (user_id.intValue()==10002||user_id.intValue()==999) {
		String s=ConfigUtil.getSqlResult("select stamp_state from intrust..tcontract where serial_no= '"+serial_no+"'");
		if ("".equals(s)) {%>
		<td align="center">
			<%if (input_operator.hasFunc(menu_id, 102)){%>
			 <a href="javascript:referflow(<%=serial_no%>);">				
	       		<img border="0" src="<%=request.getContextPath()%>/images/go.gif" width="16" height="16">				
	       	 </a>
			<%}%>
		</td>
		<%} else if("1020".equals(s)){%>
		<td align="center" height="48" class="tdh">已提交</td>
		<%}  else if("1030".equals(s)){%>
		<td align="center" height="48" class="tdh">已盖章</td>
		<%} 
	}%>
					</tr>

					<%
					iCount++;
					iCurrent++;
					}
					int page_size = (pageList!=null)?pageList.getPageSize():Utility.parseInt(sPagesize, 8);
					for (; iCurrent < page_size; iCurrent++)
					{
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<%for(int j=0;j<fieldsArr.length;j++){
					%>
						<td align="center" ></td>
					<%
					}
					%>
						<td></td>
						<td></td>
						<td></td>
						<%if(user_id.intValue()==10002||user_id.intValue()==999){%>
						<td></td>
						<%} %>
					</tr>
					<%
					}
					%>
					<tr class="trbottom">
					<%
					for(int j=0;j<fieldsArr.length;j++){
						if(j==0){
					%>
						<!--合计--><!--项-->
                        <td class="tdh" align="left" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
					<%
						}else{
							if("RG_MONEY".indexOf(fieldsArr[j])==-1 && "TO_AMOUNT".indexOf(fieldsArr[j])==-1){
					%>
							<td align="center" >-</td>
					<%
							}else{
					%>
						<td align="right" ><font color="red"><%=(pageList!=null)?Format.formatMoney(pageList.getTotalValue(fieldsArr[j])):""%></font></td>
					<%
							}
						}
					}
					%>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<td align="center" >-</td>
						<%if(user_id.intValue()==10002||user_id.intValue()==999){%>
						<td align="center" >-</td>
						<%}%>
					</tr>
				</table>
				<br>
<%if(user_id.intValue()==16){ %></div><%} %>
<br>
<div class="page-link">
	<%=(pageList!=null)?pageList.getPageLink(sUrl,clientLocale):""%>
<input type="hidden" name="t2" value="<%=custIdSAll%>">
</div>
	<div id="ispopupWindow" class="popup-window" style="display:none">
		<div class="popup-title">系统确认</div>
		<div class="popup-body">
			<p>您是否已经预约？</p>
			<ul class="btn-group">
				<li><div  onclick="javascript:chosen(1);">是</div></li>
				<li><div  onclick="javascript:chosen(0);">否</div></li>
			</ul>
		</div>
	</div>
<%contract.remove();%>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
