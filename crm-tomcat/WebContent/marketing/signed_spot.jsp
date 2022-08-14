<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%
//获得页面传递变量
Integer query_flag = Utility.parseInt(Utility.trimNull(request.getParameter("query_flag")),new Integer(0));//1表示证件读取查询 0/2表示页面查询
String q_productCode= Utility.trimNull(request.getParameter("q_productCode"));
Integer q_pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_pre_product_id")),new Integer(0));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
if (query_flag.intValue()==1 && !"".equals(Utility.trimNull(session.getAttribute("name"))))
	q_cust_name = session.getAttribute("name").toString();

Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));
Integer begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_date")),new Integer(0));
BigDecimal pre_money1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money1")),new BigDecimal(0));
BigDecimal pre_money2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money2")),new BigDecimal(0));
String pre_status = Utility.trimNull(request.getParameter("pre_status"),"111301");
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_money1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("channel_money1")),new BigDecimal(0));
BigDecimal channel_money2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("channel_money2")),new BigDecimal(0));
String q_pre_level = Utility.trimNull(request.getParameter("q_pre_level"));
int is_contract_flow = Argument.getSyscontrolValue("CONTRACT_FLOW");
String s_page_reserve = "";
if (! Utility.trimNull(session.getAttribute("name")).equals(""))
	s_page_reserve = "/marketing/sell/direct_reserve_add_sign.jsp";  //如果当前有识别的证件信息，使用具有搜索功能的页面新建预约
else
	s_page_reserve = "/marketing/sell/direct_reserve_add.jsp";

//页面辅助参数
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,10);
int iCount = 0;
String options = Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode);
options = options.replaceAll("\"", "'");

String sl_status = Utility.trimNull(request.getParameter("sl_status"),"");
String sl_types = Utility.trimNull(request.getParameter("sl_type"),"");
//帐套暂时设置
input_bookCode = new Integer(1);

sUrl += "&q_pre_product_id="+q_pre_product_id
				+"&q_product_name="+q_product_name
				+"&q_cust_name="+q_cust_name	
				+"&team_id="+team_id
				+"&begin_date="+begin_date
				+"&end_date="+end_date
				+"&begin_date="+begin_date
				+"&end_date="+end_date
				+"&pre_money1="+pre_money1
				+"&pre_money2="+pre_money2
				+"&pre_status="+pre_status
				+"&start_date="+start_date
				+"&end_date="+end_date
				+"&pre_money1="+pre_money1
				+"&pre_money2="+pre_money2
				+"&team_id="+team_id
				+"&pre_status="+pre_status
				+"&channel_type="+channel_type
				+"&channel_money1="+channel_money1
				+"&channel_money2="+channel_money2
				+"&q_pre_level="+q_pre_level
				+"&sl_status="+sl_status
				+"&sl_type="+sl_types;

//获得对象及结果集
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
PreContractCrmVO vo = new PreContractCrmVO(); 
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
ContractLocal contract = EJBFactory.getContract();

vo.setPre_product_id(q_pre_product_id);
vo.setProduct_name(q_product_name);
vo.setCust_name(q_cust_name);
vo.setInput_man(input_operatorCode);
vo.setDate1(start_date);
vo.setDate2(end_date);
vo.setMoney1(pre_money1);
vo.setMoney2(pre_money2);
vo.setOnly_my_team(new Integer(1));
vo.setTeam_id(team_id);
vo.setPre_status(pre_status);
vo.setChannel_type(channel_type);
vo.setChannel_money1(channel_money1);
vo.setChannel_money2(channel_money2);
vo.setPre_level(q_pre_level);

if("".equals(sl_status)){
	vo.setSl_status(null);
} else {
	vo.setSl_status(Integer.parseInt(sl_status));
}
System.out.println(sl_types);
if("".equals(sl_types)){
	vo.setSl_type(null);
} else {
	vo.setSl_type(sl_types);
}

String[] totalColumn = {"PRE_MONEY","RG_MONEY","VALID_PRE_MONEY"};

IPageList pageList = null;
if(UNQUERY.intValue()==0)
    pageList = preContract.queryPreContractCrm_m(vo,totalColumn,t_sPage,t_sPagesize);
else
    pageList = new JdbcPageList();
List list = pageList.getRsList();
if(list == null) list = new ArrayList();
boolean print_flag = ! list.isEmpty();

String scan_card_info = Utility.trimNull(session.getAttribute("name")).toString()+"-"+Utility.trimNull(session.getAttribute("card_id")).toString();

preContract.remove();
local.remove();

boolean first_money_scene = Argument.getSyscontrolValue("FIRST_MONEY_SCENE")==1;
 %>

<HTML>
<HEAD>
<TITLE>现场签约</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />
<style type="text/css">
	#cover{
	/* for FF */
	opacity: 0.3;
	/* for FF lower than 3.5 */
	-moz-opacity: 0.3;
	/* for IE */
	filter: alpha(opacity=30);
	
	background-color: #ffffff;

	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	
	overflow: auto;
	
	display: none;
}
	#msger{
	background-color: white;
	border: 1px solid #ccccff;
	
	position: absolute;
	top: 70%;
	left: 50%;
	
	margin-top: -250px;
	margin-left: -250px;

	width: 300px;
	height: 80px;
	
	display: none;
	z-index:99;
	text-align: center;

}
	#selectOptions{
	background-color: white;
	border: 1px solid #ccccff;
	
	position: absolute;
	top: 70%;
	left: 50%;
	
	margin-top: -250px;
	margin-left: -250px;

	width: 300px;
	height: 80px;
	
	display: none;
	z-index:99;
	text-align: center;

}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

/*合格人投资确认打印*/
function printStandard(){
    var str = checkedValue(document.theform.chcx);
    if(str == null || str == "") {
        sl_alert("请选择要打印的机构或个人");
        return;
    }

    var strs = str.split("|"); //字符分割   
	var url = "";
    if (strs[2]=="个人")
        url = "sign/per.jsp?cust_id="+strs[1];
    else if (strs[2]=="机构")
        url ="sign/org.jsp?cust_id="+strs[1];
    
	var scan_card_info = document.theform.scan_card_info.value;
	if(scan_card_info != "-") {
		var sys_cust_info = strs[3] + "-" + strs[4];
		if(sl_confirm("将证件信息["+scan_card_info+"]同步至crm系统客户["+ sys_cust_info+"]")) {
			window.open(url);				 
		}else{
			return false;
		}
	}else{
		window.open(url); 
	}    
}

/*缴款凭证打印*/
function printCash(serial_no){
	var url="/marketing/sign/cash.jsp?q_premoneydetail_serial_no="+serial_no;
	window.open(url); 
}

/*银行卡打印*/
function printBankCard(serial_no){
	var url="/marketing/sign/print_bank_card.jsp?q_premoneydetail_serial_no="+serial_no;
	window.open(url); 
}

/*新建预约*/
function newInfo(){
	if (showModalDialog('<%=s_page_reserve%>', '', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')){
		sl_update_ok();
		if (sl_confirm("是否继续预约"))
			newInfo();
		else
			location.reload();
	}
}

/*设置产品*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
	}
	
	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;
        
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			if(document.theform.q_pre_product_id.options[i].text.substring(0,j)==value){
				document.theform.q_pre_product_id.options[i].selected=true;
				prodid = document.theform.q_pre_product_id.options[i].value;
				break;
			}	
		}
		
		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	if (!sl_checkDecimal(document.theform.pre_money1, "", 13, 3, 0)) return false;
	if (!sl_checkDecimal(document.theform.pre_money2, "", 13, 3, 0)) return false;
	location.search = "?page=1&pagesize="+ document.theform.pagesize.value
						+ '&q_pre_product_id=' + document.theform.q_pre_product_id.value
						+ '&q_cust_name=' + document.theform.q_cust_name.value
						+ '&start_date=' + document.theform.start_date.value
						+ '&end_date=' + document.theform.end_date.value
						+ '&pre_money1=' + document.theform.pre_money1.value
						+ '&pre_money2=' + document.theform.pre_money2.value
						+ '&pre_status=' + document.theform.pre_status.value
						+ '&team_id=' + document.theform.team_id.value
						+ '&channel_type=' + document.theform.channel_type.value
						+ '&channel_money1=' + document.theform.channel_money1.value
						+ '&channel_money2=' + document.theform.channel_money2.value
						+ '&q_pre_level=' + document.theform.q_pre_level.value
						+ "&query_flag=2"
						+ "&sl_status="+document.theform.sl_status.value
						+ "&sl_type="+document.theform.sl_type.value;
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

function getQueryParams() {
	return "page=<%=t_sPage%>&pagesize="+ document.theform.pagesize.value
						+ '&q_pre_product_id=' + document.theform.q_pre_product_id.value
						+ '&q_cust_name=' + document.theform.q_cust_name.value
						+ '&start_date=' + document.theform.start_date.value
						+ '&end_date=' + document.theform.end_date.value
						+ '&pre_money1=' + document.theform.pre_money1.value
						+ '&pre_money2=' + document.theform.pre_money2.value
						+ '&pre_status=' + document.theform.pre_status.value
						+ '&team_id=' + document.theform.team_id.value
						+ '&channel_type=' + document.theform.channel_type.value
						+ '&channel_money1=' + document.theform.channel_money1.value
						+ '&channel_money2=' + document.theform.channel_money2.value
						+ '&q_pre_level=' + document.theform.q_pre_level.value
						+ "&query_flag=2"
						+ "&sl_status="+document.theform.sl_status.value
						+ "&sl_type="+document.theform.sl_type.value;
}

var signContract = {};

/*签约功能*/
function signAction(pre_serial_no,bind_product_id,isSign,contract_no,contract_state,preproduct_id){  
	//if(bind_product_id==0){
	//	sl_alert("未绑定业务系统项目，无法签约");
	//}else 
	//alert('pre_serial_no:'+pre_serial_no+'bind_product_id:'+bind_product_id+'isSign:'+isSign+'contract_no:'+contract_no+'contract_state'+contract_state);
	if(contract_no!=''){
		signContract.pre_serial_no = pre_serial_no;
		signContract.bind_product_id = bind_product_id;
		document.getElementById("cover").style.display = "block";				
		document.getElementById("selectOptions").style.display = "block";
				
	}else{
		if(<%=is_contract_flow%>==1&&(contract_no=='' || contract_state != '1030')){
			if(confirm("未领取认购号，是否领取认购号")){
				signContract.pre_serial_no = pre_serial_no;			
				document.getElementById("cover").style.display = "block";
				document.getElementById("msger").style.display = "block";		
			}
		}else{
			//alert("已领取认购号： "+ contract_no);
			if (showModalDialog('signed_spot_trans.jsp?pre_serial_no='+pre_serial_no+'&product_id='+bind_product_id+'&preproduct_id='+preproduct_id, 
				'', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')){
				sl_update_ok();
				location.reload();
			}
	    }
	}
}

function getChoose(){
	document.getElementById("cover").style.display = "none";
	document.getElementById("selectOptions").style.display = "none";
	var selectValue = document.getElementById("selectValue").value;
	var pre_serial_no = signContract.pre_serial_no;
	var bind_product_id = signContract.bind_product_id;
	if(selectValue == 'getContract'){
		document.getElementById("cover").style.display = "block";
		document.getElementById("msger").style.display = "block";
	}else{
		if(bind_product_id==0){
			sl_alert("未绑定业务系统项目，无法签约");
			return;
		}
		if (showModalDialog('signed_spot_trans.jsp?pre_serial_no='+pre_serial_no+'&product_id='+bind_product_id, 
			'', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')) {
			sl_update_ok();
			location.reload();
		}
	}
}

function disChose(){
	document.getElementById("cover").style.display = "none";
	document.getElementById("msger").style.display = "none";
	document.getElementById("cover").style.display = "none";
	document.getElementById("selectOptions").style.display = "none";
}

function showNum(){
	document.getElementById("cover").style.display = "none";
	document.getElementById("msger").style.display = "none";
	var contractNum = document.getElementById("contractNum").value;
	var pre_serial_no = signContract.pre_serial_no;
	//popWindow('/project/getContractNo/get_contract_no.jsp?serial_no=' + pre_serial_no + '&contractNum='+ contractNum);
	var returnV = checkLogic("CRMBusinessCheck","getPreContractFlow",pre_serial_no+"@@"+contractNum+"@@work");
	//showModalDialog('/project/getContractNo/get_contract_no.jsp?serial_no=' + pre_serial_no + '&contractNum='+ contractNum, '', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0');
	//showModalDialog('/project/flow/checkerror_info.jsp?check_flag=DiscInfoCheck&object_type=WorkLogFlow&object_id=' + pre_serial_no + '&contractNum='+ contractNum, '', 'dialogWidth:560px;dialogHeight:450px;status:0;help:0');
	var returnValue=actionFlow("FlowUtil","initFlow",returnV+"@@Contract@@"+"ContractFlow"+"@@"+"<%=input_operatorCode%>");
	if(returnValue=="success"){
		sl_alert("合同领取流程任务生成成功！")
		location.reload();
	}
}

function getContractBh(){
	var str = checkedValue(document.theform.money_serial_no);
	if(str == null || str == "") {
        sl_alert("请选择缴款信息!");
        return;
    }
	if(sl_confirm("提交合同领取流程")) {
		var money_serial_no_str = "";
		var money_serial_no = document.getElementsByName("money_serial_no");
		for (var i=0; i<money_serial_no.length; i++)
			if (money_serial_no[i].checked)
				money_serial_no_str += money_serial_no[i].value +"$";
			
		disableAllBtn(true);
		alert(money_serial_no_str);
		var returnValue = checkLogic("CRMBusinessCheck","getPreContractFlow",money_serial_no_str);
		var returnValue=actionFlow("FlowUtil","initFlow",money_serial_no_str+"@@Contract@@"+"WorkLogFlow"+"@@"+"<%=input_operatorCode%>");
		if(returnValue=="success"){
			sl_alert("合同领取流程任务生成成功！")
			location.reload();
		}
	}
}

/*修改*/
function showInfo(serial_no){
	if(showModalDialog('sell/reserve_edit1.jsp?serial_no='+ serial_no,'', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

/*证件扫描*/
function card_discern(){
	var url = "card/card_discern_trance.jsp";
	dialog("","iframe:"+url,"950px","480px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}

/*身份证阅读-二代证读卡*/
function card_read(){
	var url = "card/card_discern3_trance.jsp";
	dialog("","iframe:"+url,"950px","480px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}

/*手持设备读取*/
function card_wireless(){
	var url = "card/wireless.jsp";
	dialog("","iframe:"+url,"950px","450px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}

/*身份证审核*/
function card_check(){
	var str=checkedValue(document.theform.chcx);
    if(str == "") {
		sl_alert("请勾选要进行证件核查的预约客户");
		return;
	}

	var strs = str.split("|"); //字符分割         
	var url="card/card_discern4.jsp?cust_id="+strs[1];
	      
	if(strs[2]=="个人"){
		dialog("","iframe:"+url,"950px","485px","show","<%=request.getContextPath()%>"); 
		showWaitting(0);
	}else if(strs[2]=="机构"){
	    alert("系统暂时只支持个人客户身份证信息核查，不支持机构证件核查");
	}         
}

//新建到账处理
function newPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money,cust_id,product_id,sub_product_id,cust_type){
<%if (first_money_scene) {%>
	var url = "capital_enter.jsp";//"sell/pre_money_detail_add2.jsp";
	if (showModalDialog(url+'?pre_serial_no='+serial_no+'&cust_name='+cust_name+'&cust_type='+cust_type+'&product_name='+product_name
						+'&pre_money='+pre_money+'&rg_money='+rg_money+"&cust_id="+cust_id+"&product_id="+product_id+"&sub_product_id="+sub_product_id, 
		'', 'dialogWidth:480px;dialogHeight:400px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
<%}else{%>
	var url = "sell/pre_money_detail_add.jsp";
	if (showModalDialog(url+'?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money, 
		'', 'dialogWidth:480px;dialogHeight:400px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
<%}%>
}

//新建双录录入
function newPreDoubleRecordInfoInfo(serial_no, cust_name, product_name, pre_money, rg_money){
	var url = "sell/pre_double_record_info_add.jsp";
	if (showModalDialog(url+'?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money, 
		'', 'dialogWidth:480px;dialogHeight:400px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//修改双录录入信息
function modiPreDoubleRecordInfoInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){

	if (showModalDialog('/marketing/sell/pre_double_record_info_update.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 	'', 'dialogWidth:380px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//审核双录录入信息
function checkPreDoubleRecordInfoInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){

	if (showModalDialog('/marketing/sell/pre_double_record_info_check.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 	'', 'dialogWidth:380px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//审核双录录入信息
function uncheckPreDoubleRecordInfoInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){

	if (showModalDialog('/marketing/sell/pre_double_record_info_uncheck.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 	'', 'dialogWidth:380px;dialogHeight:360px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//到账审核通过
function checkMoneyData(serial_no){
	if (confirm("确定审核通过？")) {
		location.href = 'sell/sub_capital_entering_check_do.jsp?from=signed_spot&checkflag=1&serial_no='+serial_no
						+'&'+getQueryParams();
	}
}

function makeContract(serial_no,cust_id,product_id,sub_product_id) {
	if (confirm("确定生成合同？")) {
		location.href= "sell/sub_capital_make_contract_action.jsp?from=signed_spot&action_type=1&serial_no_list="+serial_no
						+"&cust_id="+cust_id+"&productId="+product_id+"&sub_product_id2="+sub_product_id+"&qs="+encodeURIComponent(getQueryParams());
	}
}

function completeContract(serialno, checkflag,ht_status,bank_id) {
	location.href = 'sell/subscribe_edit2.jsp?from=signed_spot&serial_no='+serialno+'&check_flag='+checkflag
					+'&bank_id='+bank_id+'&sQuery=&purchase_mode_flag=1&qs='+encodeURIComponent(getQueryParams());
}

function checkContract(serial_no) {
	location = 'sell/purchase_mend_check_action.jsp?serial_no='+serial_no
				+"&from=signed_spot&qs="+encodeURIComponent(getQueryParams());
}

//修改到账处理: TPreMoneyDetail
function showPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){
	if (print_count>0 && !sl_confirm("修改到账信息（提示：该到账记录已经打印过！）")) return;

	if (showModalDialog('/marketing/sell/pre_money_detail_update.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 	'', 'dialogWidth:380px;dialogHeight:320px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//修改到账处理: TMoneyData
function editMoneyData(serial_no, cust_name, product_name, pre_money, rg_money){
	//if (print_count>0 && !sl_confirm("修改到账信息（提示：该到账记录已经打印过！）")) return;

	if (showModalDialog('/marketing/capital_enter.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money,
		 	'', 'dialogWidth:480px;dialogHeight:400px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//退款处理
function refundPreMoneyInfo(serial_no, cust_name, product_name, pre_money, rg_money, print_count){
	if (print_count>0 && !sl_confirm("退款处理（提示：该到账记录已经打印过！）")) return;

	if (showModalDialog('/marketing/sell/pre_money_detail_refund.jsp?serial_no='+serial_no+'&cust_name='+cust_name+'&product_name='+product_name+'&pre_money='+pre_money+'&rg_money='+rg_money, 
			'', 'dialogWidth:380px;dialogHeight:320px;status:0;help:0')) {
		sl_update_ok();
		location.reload();
	}
}

//删除到账信息
function delPreMoneyInfo(serial_no,print_count){
	var msg="删除到账记录";
	if (print_count>0) msg += "（提示：该到账记录已经打印过！）";

	if(sl_confirm(msg))
		location = "/marketing/sell/pre_money_detail_del.jsp?from=signed_spot&serial_no="+serial_no+"&"+getQueryParams();
}

function delMoneyData(serial_no){
	var msg="删除到账记录";
	//if (print_count>0) msg += "（提示：该到账记录已经打印过！）";

	if(sl_confirm(msg))
		location = "/marketing/sell/sub_capital_enter_remove.jsp?from=signed_spot&serial_no="+serial_no+"&"+getQueryParams();
}

/*明细*/
function setiteminfor(tr10,tablePro,flagdisplay,imagex){
	i= flagdisplay.value;
    if (i==0){
		tablePro.style.display="";
      	tr10.style.display="";
      	flagdisplay.value=1;
      	imagex.src='<%=request.getContextPath()%>/images/chevronUp.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    } else if(i==1){
       	tablePro.style.display="none";
        tr10.style.display="none";
      	flagdisplay.value=0;
      	imagex.src='<%=request.getContextPath()%>/images/chevronDown.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    }
}

/*双录信息*/
function setDoubleRecordInfo(tr10,tablePro,flagdisplay,imagex){
	i= flagdisplay.value;
    if (i==0){
		tablePro.style.display="";
      	tr10.style.display="";
      	flagdisplay.value=1;
      	imagex.src='<%=request.getContextPath()%>/images/chevronUp.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    } else if(i==1){
       	tablePro.style.display="none";
        tr10.style.display="none";
      	flagdisplay.value=0;
      	imagex.src='<%=request.getContextPath()%>/images/chevronDown.gif';
      	imagex.style.width = "16px";
      	imagex.style.height = "16px";
    }
}
//查看客户信息
function getCustomer(cust_id,cust_type_name){
	var url = '/marketing/customerInfo2.jsp?cust_id='+ cust_id + '&cust_type_name=' + cust_type_name;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:620px;status:0;help:0;');
}

/*修改*/
function showBankInfo(cust_id){
	window.open('/marketing/sign/cust_bank_list.jsp?cust_id='+ cust_id,'newwindow'
				,'height=500,width=800,top=100,left=250,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no') 
}


/**产品名称检索*/
function searchProductName(value){
	var list = [];
	var list1 = [];

	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='q_pre_product_id' onkeydown='javascript:nextKeyPress(this)'  class='productname' style='width: 340px;'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.q_pre_product_id.options.length;i++){
			var j = document.theform.q_pre_product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.q_pre_product_id.options[i].text);
				list1.push(document.theform.q_pre_product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.product_name.value="";
			document.theform.q_pre_product_id.options[0].selected=true;
		}else{
			document.theform.q_pre_product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.q_pre_product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.q_pre_product_id.focus();
	}else{
		document.theform.q_pre_product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type="hidden" name="scan_card_info" value="<%=scan_card_info%>">
<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
		   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>

	<table>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!--产品编号-->
			<td>
				<input type="text" name="q_productCode" value="<%= q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="16">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
			</td>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td><!--产品名称-->
			<td>
				<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td><!--产品名称-->
			<td colspan="3" id="select_id">
				<select size="1" name="q_pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width: 340px;">
					<%=Argument.getPreProductListOptions(q_pre_product_id,"","110299",input_operatorCode)%>
				</select>		
			</td>
		</tr>
		<tr>
			<td align="right" width="20%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!--客户名称-->
			<td align="left" width="30%">
				<input type="text" name="q_cust_name" value="<%=q_cust_name%>" size="20">
			</td>
			<td align="right" width="20%">渠道类别:</td>
			<td align="left" width="30%">
				<select size="1" name="channel_type" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getDictParamOptions(5500, channel_type)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">渠道金额:</td>
			<td width="30%">
				<input type="text" name="channel_money1" value="<%=Format.formatMoney(channel_money1)%>" size="20">
			</td>
			<td align="center" width="20%">到</td>
			<td align="left" width="30%">
				<input type="text" name="channel_money2" value="<%=Format.formatMoney(channel_money2)%>" size="20">
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">认购开始日期:</td>
			<td width="30%">
				<INPUT TYPE="text" NAME="start_date_picker" value="<%=Format.formatDateLine(start_date)%>" size="16">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="start_date" value="">	
			</td>
			<td align="right" width="20%">认购结束日期:</td>
			<td align="left" width="30%">
				<INPUT TYPE="text" NAME="end_date_picker" value="<%=Format.formatDateLine(end_date)%>" size="16">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
				<INPUT TYPE="hidden" NAME="end_date" value="">
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">预约金额:</td>
			<td width="30%">
				<input type="text" name="pre_money1" value="<%=Format.formatMoney(pre_money1)%>" size="20">
			</td>
			<td align="center" width="20%">到</td>
			<td align="left" width="30%">
				<input type="text" name="pre_money2" value="<%=Format.formatMoney(pre_money2)%>" size="20">
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">预约状态:</td>
			<td width="30%">
				<select size="1" name="pre_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getDictParamOptions(1113, pre_status)%>
				</select>
			</td>
			<td align="right" width="20%">团队名称:</td>
			<td align="left" width="30%">
				<select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getManagerTeams(team_id)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">预约级别:</td>
			<td width="30%">
				<select size="1" name="q_pre_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getDictParamOptions(1182, q_pre_level)%>
				</select>
			</td>
			<td align="right" width="20%">双录信息状态:</td>
			<td align="left" width="30%">
				<select size="1" name="sl_status" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getDoubleRecordStatusOptions(sl_status)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">双录模式:</td>
			<td align="left" width="30%">
				<select size="1" name="sl_type" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;">
					<%=Argument.getDoubleRecordTypeOptions(sl_types)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">				
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确认-->		
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div style="float:left;">&nbsp;<font size="3" color="#FF0000">
<%
if (session.getAttribute("name")!=null && !session.getAttribute("name").toString().equals("")) {
	out.print("当前证件："+session.getAttribute("name").toString()+" - "+session.getAttribute("card_id").toString());
}
%></font>
	</div>
	<div align="right" class="btn-wrapper">
		
        <button type="button"  class="xpbutton3"   id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询--></button>&nbsp;&nbsp;&nbsp; 
		<button type="button"  class="xpbutton4"  name="btnCertIden" title="手持设备读卡"	onclick="javascript:card_wireless();">手持设备读卡</button>&nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3"   name="btnCertIden" title="二代证读卡"	onclick="javascript:card_read();" style="display:none;">二代证读卡</button>&nbsp;&nbsp;&nbsp;
		<button type="button"  class="xpbutton3"  name="btnCertIden" title="证件扫描"	onclick="javascript:card_discern();">证件扫描</button>&nbsp;&nbsp;&nbsp;
        <button type="button"  class="xpbutton3"  name="btnCertIden" title="证件核查"	onclick="javascript:card_check();">证件核查</button>&nbsp;&nbsp;&nbsp;
	    <button type="button"  class="xpbutton5"  id="btnStardar" name="btnStardar" title="合格投资人表打印" onClick="javascript:printStandard();">合格投资人表打印</button>&nbsp;&nbsp;
		<button type="button"  class="xpbutton3"  accessKey=n name="btnNew" title="新建预约" onclick="javascript:newInfo();">新建预约</button><!--新建预约-->
   </div>	
</div>
<div id="cover"> 
	
</div>
<div id="msger">
	<table>
		<tr>
			<td align="right">领取认购号数量：</td>
			<td align="right"><input type="text" id="contractNum" name="contractNum" value="1">个</td>
		</tr>
		<tr>
			<td align="center" colspan="1"><button type="button"  class="xpbutton3" onclick="javascript:showNum();">确认 (<u>O</u>)</button></td>
			<td align="center" colspan="1"><button type="button"  class="xpbutton3" onclick="javascript:disChose();">取消 (<u>O</u>)</button></td>
		</tr>
	</table>
</div>
<div id="selectOptions">
	<table>
		<tr>
			<td align="right">选择操作：</td>
			<td>
			<select id="selectValue">
				  <option value ="getContract">领取合同</option>
				  <option value ="signContract">签约</option>
			</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="1"><button type="button"  class="xpbutton3" onclick="javascript:getChoose();">确认 (<u>O</u>)</button></td>
			<td align="center" colspan="1"><button type="button"  class="xpbutton3" onclick="javascript:disChose();">取消 (<u>O</u>)</button></td>
		</tr>
	</table>
</div>


<div>
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class="trh">
			<td>
				<%=LocalUtilis.language("class.custName4",clientLocale)%> 
			</td><!--客户姓名-->	
			<td align="center" >银行账户信息</td>
			<td align="center" width="*">产品名称</td>
			<td align="center" sort="num">预约金额</td>
			<td align="center" sort="num">到账金额 </td>
			<td align="center" width="*">预约日期</td>
			<td align="center" width="*">预计认购日期</td>
			<td align="center" >预约登记人 </td><!--预约登记人-->
			<td align="center" ><%=LocalUtilis.language("login.telephone",clientLocale)%> </td><!--联系电话-->
			<td align="center" ><%=LocalUtilis.language("class.preStatus",clientLocale)%> </td><!--状态-->
			<td align="center" >已签约金额</td>
            <td align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
			<td align="center" >到账明细</td>
			<td align="center" >到账处理</td>
			<td align="center" >双录明细</td>
			<td align="center" >双录录入</td>
			<td align="center" >签约</td>		
		</tr>
<%
BigDecimal pre_money = new BigDecimal(0);
BigDecimal rg_money = new BigDecimal(0);
Integer rg_num_total = new Integer(0);
Integer pre_num_total = new Integer(0); 

Iterator iterator = list.iterator();
while(iterator.hasNext()){
		iCount++;
		Map map = (Map)iterator.next();
		Integer preproduct_id = Utility.parseInt(Utility.trimNull(map.get("PREPRODUCT_ID")),new Integer(0));
		Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
		Integer cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
		String cust_name = Utility.trimNull(map.get("CUST_NAME"));
		String product_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
		String cust_type_name =  Utility.trimNull(map.get("CUST_TYPE_NAME"));
		//String cust_type =  Utility.trimNull(map.get("CUST_TYPE"));
		String pre_code = Utility.trimNull(map.get("PRE_CODE"));

		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1");
		rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")), new BigDecimal(0.00),2,"1");
		Integer link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		String h_tel = Utility.trimNull(map.get("H_TEL"));
		String o_tel = Utility.trimNull(map.get("O_TEL"));
		String mobile = Utility.trimNull(map.get("MOBILE"));
		String bp = Utility.trimNull(map.get("BP"));
		Integer preDate = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));
		Integer expRegDate = Utility.parseInt(Utility.trimNull(map.get("EXP_REG_DATE")),new Integer(0));
		pre_status = Utility.trimNull(map.get("PRE_STATUS"));
		String pre_status_name = Utility.trimNull(map.get("PRE_STATUS_NAME"));
		String summary = Utility.trimNull(map.get("SUMMARY"));		
		String inputTime =  Utility.trimNull(map.get("INPUT_TIME")).substring(0,16);
		String custSourceName = Utility.trimNull(map.get("CUST_SOURCE_NAME"));
		BigDecimal valid_pre_money = Utility.parseDecimal(Utility.trimNull(map.get("VALID_PRE_MONEY")), new BigDecimal(0.00),2,"1");
		Integer bind_product_id = Utility.parseInt(Utility.trimNull(map.get("BIND_PRODUCT_ID")),new Integer(0)); //
		Integer sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0)); //
		String card_id =  Utility.trimNull(map.get("CARD_ID"));
		String contract_no = Utility.trimNull(map.get("CONTRACT_NO"));
		String contract_state = Utility.trimNull(map.get("CONTRACT_STATE"));
		int isSign = pre_money.equals(rg_money)? 1: 0; //判断是否满足签约条件

		BigDecimal rg_money_total = Utility.parseDecimal(Utility.trimNull(map.get("HT_RG_MONEY")),new BigDecimal(0));

		List l = new ArrayList();
		BigDecimal moneyTotal = new BigDecimal(0.0);
		Integer firstData = new Integer(0);

		if (! first_money_scene) {//正常流程
			enfo.crm.vo.PreMoneyDetailVO preVo = new enfo.crm.vo.PreMoneyDetailVO();
			preVo.setPre_serial_no(serial_no);
			preVo.setTeam_id(new Integer(0));
			l = local.queryPreMoneyDetail(preVo);		
			for (int i=0; i<l.size(); i++) {
				Map preMap = (Map)l.get(i);
				String contract_bh = Utility.trimNull(preMap.get("CONTRACT_BH"));
				BigDecimal rg_money2 = Utility.parseDecimal(Utility.trimNull(preMap.get("RG_MONEY")),new BigDecimal(0));	
			}
		} else {
			PreContractCrmVO pre_vo = new PreContractCrmVO();
			pre_vo.setPre_serial_no(serial_no);
			pre_vo.setInput_man(input_operatorCode);
			l = preContract.queryMoneyData(pre_vo);		
			for (int i=0; i<l.size(); i++) {
				Map m = (Map)l.get(i);
				if (i==0) firstData = (Integer)m.get("SERIAL_NO");
	
				moneyTotal = moneyTotal.add((BigDecimal)m.get("DZ_MONEY"));
			}
		}
		Integer flow_status = Utility.parseInt((Integer)map.get("FLOW_STATUS"), new Integer(0));

		ContractVO conVo = new ContractVO();
		conVo.setBook_code(input_bookCode);
		conVo.setCust_id(cust_id);
		conVo.setProduct_id(bind_product_id);
		List cons = contract.queryContractByCustID(conVo);
		Long conSn = new Long(0);
		Integer checkFlag = new Integer(0);
		String htStatus = "";
		String bankId = "";
		if (cons.size()>0) {
			Map conMap = (Map)cons.get(0);
			conSn = (Long)conMap.get("SERIAL_NO");
			checkFlag = (Integer)conMap.get("CHECK_FLAG");
			htStatus = (String)conMap.get("HT_STATUS");
			bankId = (String)conMap.get("BANK_ID");
		}	

		boolean first_money_check = Argument.getSyscontrolValue("FIRST_MONEY_CHECK")==1;	
		
		//判断是否需要录入双录信息
		PreDoubleRecordInfoLocal preDrlocal = EJBFactory.getPreDoubleRecordInfo();
		PreDoubleRecordInfoVO preDrvo = new PreDoubleRecordInfoVO();
		preDrvo.setPre_serial_no(serial_no);
		List preList = preDrlocal.queryPreDoubleRecordInfo(preDrvo);
		
		boolean isDoubleRecord = false; 
		Integer statusStr = -1;
		String sl_date = "";
		Integer sl_type = 0;
		
		if(preList != null && preList.size()>0){
			isDoubleRecord = true;
			Map preMap = (Map)preList.get(0);
			statusStr = Integer.parseInt(preMap.get("STATUS") == null ? "-1" : preMap.get("STATUS").toString());
		}
		
%>
<tr class="tr<%=(iCount%2)%>">
		<td class="tdh">
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="chcx"	value="<%=serial_no%>|<%=cust_id%>|<%=cust_type_name%>|<%=cust_name%>|<%=card_id%>" class="selectAllBox"></td>
					<td width="90%" align="left"><a href="#" onclick="javascript:getCustomer(<%=cust_id%>,'<%=cust_type_name %>')"><%=cust_name%></a></td>
				</tr>
			</table>
		</td>
		<td align="center" >		
	       	<a href="#" onclick="javascript:showBankInfo('<%=cust_id%>');">
           		<img border="0" src="<%=request.getContextPath()%>/images/azsy.gif" width="16" height="16">
           	</a>
		</td>
		<td align="left" ><%=product_name%></td>
		<td align="right" ><%=Format.formatMoney(pre_money)%></td>
		<td align="right" ><%=Format.formatMoney(first_money_scene?moneyTotal:rg_money)%></td>
		<td align="center" ><%=Format.formatDateLine(preDate)%></td>
		<td align="center" ><%=Format.formatDateLine(expRegDate)%></td>
		<td align="center" ><%=Argument.getOperator_Name(link_man)%></td>
		<td align="center" ><input type="text" class="ednone" value="<%=Utility.trimNull(Argument.getALLTEL(h_tel,o_tel,mobile,bp))%>" readonly></td>
		<td align="center" ><%=pre_status_name%></td>
		<td align="center" ><%=Format.formatMoney(rg_money_total)%></td>	
		<td align="center" >	
			<%if(!"111304".equals(pre_status)) {%>		
	       	<a href="#" onclick="javascript:showInfo('<%=serial_no%>');">
           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
           	</a>
			<%} %>
		</td>
		<td align="center" >
			<%if(!"111304".equals(pre_status) && l.size()>0) {%>	
				<img id="image<%=serial_no%>" style="cursor: pointer; width: 16px; height: 16px;" 
						src="<%=request.getContextPath()%>/images/chevronDown.gif" title="到账明细" 
						onclick="javascript:setiteminfor(tr<%=serial_no%>_0,tablePro<%=serial_no%>,document.theform.flag<%=serial_no%>,document.theform.image<%=serial_no%>);"/>
	        	<input type="hidden" name="flag<%=serial_no%>" value="0"/>
			<%} %>
		</td>
		<td align="center" >
		<%if (!"111304".equals(pre_status)) {
			   if (flow_status.intValue()==0 || flow_status.intValue()==1 || flow_status.intValue()==2) {
			%>					
		       	<a href="#" onclick="javascript:newPreMoneyInfo('<%=serial_no%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>',<%=cust_id%>,<%=bind_product_id%>,<%=sub_product_id%>,<%=cust_type%>);">
					<img src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16" title="到账处理"/>
				</a>
			<% } else if (flow_status.intValue()==3 && first_money_check) { %>
				<a href="#" onclick="javascript:checkMoneyData(<%=firstData%>);">
					<img src="<%=request.getContextPath()%>/images/FUNC20077.gif" width="16" height="16" title="到账审核"/>
				</a>
			<%
			   }
		} %>
		</td>
		<td align="center" >
			<%if(isDoubleRecord) {%>
				<%if (isDoubleRecord) { %>		
					<img id="imageDri<%=serial_no%>" style="cursor: pointer; width: 16px; height: 16px;" 
							src="<%=request.getContextPath()%>/images/chevronDown.gif" title="双录信息" 
							onclick="javascript:setDoubleRecordInfo(dri<%=serial_no%>_0,tableProDri<%=serial_no%>,document.theform.flagDri<%=serial_no%>,document.theform.imageDri<%=serial_no%>);"/>
		        	<input type="hidden" name="flagDri<%=serial_no%>" value="0"/>
		        	<% } else { %>
					无
				<% } %>
			<%} %>
		</td>
		<td align="center" >
			<%if (!isDoubleRecord) { %>	
		       	<a href="#" onclick="javascript:newPreDoubleRecordInfoInfo('<%=serial_no%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">
					<img src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16" title="双录录入"/>
				</a>
			<% } else { %>
				<%if (statusStr == 0) { %>	
			       	<a href="#" onclick="javascript:newPreDoubleRecordInfoInfo('<%=serial_no%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">
						<img src="<%=request.getContextPath()%>/images/FUNC20076.gif" width="16" height="16" title="双录录入"/>
					</a>
				<% } else if(statusStr == -1) { %>
					待审核
				<% } else if(statusStr == 1) { %>
					已通过
				<% } else { %>
					状态错误
				<% } %>
			<% } %>
		</td>
		
		<td align="center" >	
	    <%if (!"111304".equals(pre_status) && rg_money.doubleValue() > 0 && rg_money_total.doubleValue() < rg_money.doubleValue()) {
			if (flow_status.intValue()==0 || flow_status.intValue()==1 || flow_status.intValue()==2) {%>
			<a href="#" onclick="javascript:signAction('<%=serial_no%>','<%=bind_product_id%>','<%=isSign%>','<%=contract_no %>','<%=contract_state %>','<%=preproduct_id %>');">
	       		<img border="0" src="<%=request.getContextPath()%>/images/edit3.gif" width="16" height="16">
	       	</a>
			<%} else if (flow_status.intValue()==4 && first_money_scene){ %>
			<a href="#" onclick="javascript:makeContract('<%=firstData%>','<%=cust_id%>','<%=bind_product_id%>','<%=sub_product_id%>');">
	       		<img border="0" src="<%=request.getContextPath()%>/images/edit3.gif" width="16" height="16" title="合同生成">
	       	</a>
			<%} else if (flow_status.intValue()==5 && conSn.longValue()>0 && first_money_scene) { %>
			<a href="#" onclick="javascript:completeContract('<%=conSn%>','<%=checkFlag%>','<%=htStatus%>','<%=bankId%>');">
	       		<img border="0" src="<%=request.getContextPath()%>/images/edit3.gif" width="16" height="16" title="合同补全">
	       	</a>
			<%} else if (flow_status.intValue()==6 && conSn.longValue()>0 && first_money_scene) { %>
			<a href="#" onclick="javascript:completeContract('<%=conSn%>','<%=checkFlag%>','<%=htStatus%>','<%=bankId%>');" 
					style="text-decoration:none;">
	       		<img border="0" src="<%=request.getContextPath()%>/images/edit3.gif" width="16" height="16" title="合同补全">
	       	</a>
			<%if (input_operator.hasFunc(menu_id, 103)){ %>
			<a href="#" onclick="javascript:checkContract('<%=conSn%>');" style="text-decoration:none;">
	       		<img border="0" src="<%=request.getContextPath()%>/images/FUNC20077.gif" width="16" height="16" title="补全审核">
	       	</a>
			<%}}
		} %>
		</td>
	</tr>
<!-- start 到账处理信息 -->
	<tr id="tr<%=serial_no%>_0" style="display: none" bgcolor="#FFFF99">
		<td class="tdh" colspan="17">
			<table id="tablePro<%=serial_no%>" bgcolor="#000000" style="display: none" border="0" cellpadding="2" cellspacing="1" width="100%">
				<tr>
					<td align="center" bgcolor="#FFFFFF">到帐时间</td>
					<td align="center" bgcolor="#FFFFFF">到帐金额</td>
					<td align="center" bgcolor="#FFFFFF">退款日期</td>
					<td align="center" bgcolor="#FFFFFF">退款金额</td>
					<td align="center" bgcolor="#FFFFFF">缴款方式</td>
					<td align="center" bgcolor="#FFFFFF">编辑</td>
					<td align="center" bgcolor="#FFFFFF">删除</td>
					<td align="center" bgcolor="#FFFFFF">退款</td>
					<td align="center" bgcolor="#FFFFFF">打印缴款凭证</td>
					<td align="center" bgcolor="#FFFFFF">打印银行卡</td>					
				</tr>
				<!-- 详细 -->
				<%
			if(Utility.trimNull(map.get("PRE_STATUS")).equals("111301")) {				
				for(int j=0; j<l.size(); j++){
					Map preMap = (Map)l.get(j);	
					boolean isMoneyData = Utility.trimNull(preMap.get("SOURCE")).equals("TMONEYDATA");

					String jk_type = Utility.trimNull(preMap.get("JK_TYPE"));	
					/*String dz_time = Utility.trimNull(preMap.get("DZ_TIME"));
					int idx = dz_time.lastIndexOf(':');
					if (idx>=0) dz_time = dz_time.substring(0, idx); */
			%>
				<tr>
					<td bgcolor="#FFFFFF"><%=/*dz_time*/Utility.trimNull(preMap.get("DZ_TIME")).substring(0,19)%></td>
					<td bgcolor="#FFFFFF" align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(preMap.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
					<td bgcolor="#FFFFFF"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("REFUND_DATE")),new Integer(0)))%></td>
					<td bgcolor="#FFFFFF" align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(preMap.get("REFUND_MONEY")), new BigDecimal(0.00),2,"1")) %></td>
					<td bgcolor="#FFFFFF">
						<%=Utility.trimNull(preMap.get("JK_TYPE_NAME")) %>
						<input type="hidden" name="jk_type_<%=serial_no%>_<%=j%>" id="jk_type_<%=serial_no%>_<%=j%>" value="<%=jk_type%>">
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<a href="#"
					<%if (first_money_scene) { //先资金流程%>
							onclick="javascript:editMoneyData('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">						 	
					<%} else /*if (flow_status.intValue()==3)*/ { %>
		       				onclick="javascript:showPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
					<%} %>
							<img src="<%=request.getContextPath()%>/images/edit.gif" width="16" height="16" title="编辑到账信息"/>
						</a>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<a href="#" 
					<%if (! isMoneyData) {%>
							onclick="javascript:delPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
					<%} else if (false/*flow_status.intValue()==3*/) { %>
		       				onclick="javascript:delMoneyData('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>');">
					<%} %>
							<img src="<%=request.getContextPath()%>/images/icon9.gif" width="16" height="16" title="删除到账信息"/>
						</a>
					</td>
					<td bgcolor="#FFFFFF" align="center">
					<%if (! isMoneyData) { %>
		       			<a href="#" onclick="javascript:refundPreMoneyInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>',<%=Utility.parseInt(Utility.trimNull(preMap.get("PRINT_COUNT")),new Integer(0))%>);">
							<img src="<%=request.getContextPath()%>/images/auditing.gif" width="16" height="16" title="退款处理信息"/>
						</a>
					<%} %>
					</td>
					<td bgcolor="#FFFFFF" align="center">
					<%if (! isMoneyData) { %>
		       			<a href="#" onclick="javascript:printCash('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>');">
							<img src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16" title="打印缴款凭证"/>
						</a>
					<%} %>
					</td>
					<td bgcolor="#FFFFFF" align="center">
					<%if (! isMoneyData) { %>
		       			<a href="#" onclick="javascript:printBankCard('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>');">
							<img src="<%=request.getContextPath()%>/images/print.gif" width="16" height="16" title="打印银行卡"/>
						</a>
					<%} %>
					</td>
				</tr>
				<%}}%>
			</table>
		</td>
	</tr>		
	<!-- end 到账处理信息 -->
<!-- start 双录录入信息 -->
	<tr id="dri<%=serial_no%>_0" style="display: none" bgcolor="#FFFF99">
		<td class="tdh" colspan="17">
			<table id="tableProDri<%=serial_no%>" bgcolor="#000000" style="display: none" border="0" cellpadding="2" cellspacing="1" width="100%">
				<tr>
					<td align="center" bgcolor="#FFFFFF">双录时间</td>
					<td align="center" bgcolor="#FFFFFF">双录模式</td>
					<td align="center" bgcolor="#FFFFFF">录入人</td>
					<td align="center" bgcolor="#FFFFFF">录入时间</td>
					<td align="center" bgcolor="#FFFFFF">审核状态</td>
					<td align="center" bgcolor="#FFFFFF">审核人</td>
					<td align="center" bgcolor="#FFFFFF">审核时间</td>
					<td align="center" bgcolor="#FFFFFF">编辑</td>	
					<td align="center" bgcolor="#FFFFFF">审核</td>				
				</tr>
				<!-- 详细 -->
				<%
			if(Utility.trimNull(map.get("PRE_STATUS")).equals("111301")) {
				if(preList != null && preList.size()>0){
					isDoubleRecord = true;
					Map preMap = (Map)preList.get(0);
					for(int n = 0; n < preList.size(); n++){
						preMap = (Map)preList.get(n);
					
						sl_date = preMap.get("SL_TIME").toString();
						sl_type = Integer.parseInt(preMap.get("RECORD_TYPE") == null ? "0":preMap.get("RECORD_TYPE").toString());
						boolean isModi = false;
						boolean isCheck = false;
						Integer statusDri = Integer.parseInt(preMap.get("STATUS") == null ? "-1" : preMap.get("STATUS").toString());
						if(statusDri == -1){
							isModi = true;
							isCheck = true;
						}
						String statusName = preMap.get("STATUS_NAME") == null ? "无" : preMap.get("STATUS_NAME").toString();
						String sl_type_name = preMap.get("RECORD_TYPE_NAME") == null ? "0":preMap.get("RECORD_TYPE_NAME").toString();
						String input_time = preMap.get("INPUT_TIME") == null ? "" : preMap.get("INPUT_TIME").toString();
						String input_man = preMap.get("INPUT_MAN") == null ? "" : preMap.get("INPUT_MAN").toString();
						String input_man_name = "";
						if(!"".equals(input_man)){
							input_man_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(input_man),new Integer(0)));
						}
						input_time = input_time.substring(0,input_time.lastIndexOf("."));
						String check_time = preMap.get("CHECKER_TIME") == null ? "" : preMap.get("CHECKER_TIME").toString();
						if(!"".equals(check_time)){
							check_time = check_time.substring(0,check_time.lastIndexOf("."));
						}
						String checker = preMap.get("CHECKER") == null ? "" : preMap.get("CHECKER").toString();
						String checker_name = "";
						if(!"".equals(checker)){
							checker_name = Argument.getOperator_Name(Utility.parseInt(Utility.trimNull(checker),new Integer(0)));
						}
								
				%>
					<tr>
						<td bgcolor="#FFFFFF"><%=sl_date%></td>
						<td bgcolor="#FFFFFF" align="right"><%=sl_type_name %></td>
						<td bgcolor="#FFFFFF"><%=input_man_name%></td>
						<td bgcolor="#FFFFFF"><%=input_time%></td>
						<td bgcolor="#FFFFFF" align="right"><%=statusName %></td>
						<td bgcolor="#FFFFFF"><%=checker_name%></td>
						<td bgcolor="#FFFFFF"><%=check_time%></td>
						<td bgcolor="#FFFFFF" align="center">
						<%if (isModi) { //可编辑%>
								<a href="#" onclick="javascript:modiPreDoubleRecordInfoInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">						 	
								<img src="<%=request.getContextPath()%>/images/edit.gif" width="16" height="16" title="编辑双录录入信息"/></a>
						<%} else { %>
			       				 
						<%} %>
						</td>
						<td bgcolor="#FFFFFF" align="center">
						<%if (input_operator.hasFunc(menu_id, 205)){ %>	
						<%if (isCheck) { //可审核%>
								<a href="#" onclick="javascript:checkPreDoubleRecordInfoInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">					 	
								<img src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16" title="审核双录录入信息"/></a>
						<%} else { %>
			       				<a href="#" onclick="javascript:uncheckPreDoubleRecordInfoInfo('<%=Utility.trimNull(preMap.get("SERIAL_NO"))%>','<%=cust_name%>','<%=product_name%>','<%=pre_money%>','<%=rg_money%>');">					 	
								撤销审核</a>
						<%}} %>
						</td>
					</tr>
				<%}}%>
				<%}%>
			</table>
		</td>
	</tr>		
	<!-- end 双录录入信息 -->
<%
}
for(int i=0;i<(t_sPagesize-iCount);i++){%>  
   <tr class="tr<%=i%2%>">
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
<%}%>
<%if(pageList.getTotalSize()!=0){%>	
	<tr class="trbottom">
		<td class="tdh" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
		<td align="center"></td>
		<td align="center"></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(pageList.getTotalValue("PRE_MONEY")))%></td>
		<td align="right" ><%=Utility.trimNull(Format.formatMoney(pageList.getTotalValue("RG_MONEY")))%></td>
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
	</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%contract.remove();%>