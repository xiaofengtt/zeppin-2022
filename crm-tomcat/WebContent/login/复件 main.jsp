<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*,enfo.crm.cont.*,enfo.crm.service.*" %>
<%@ include file="/includes/operator.inc" %>
<%
int display_flag=Utility.parseInt(Utility.trimNull(request.getParameter("display_flag")),0);
int subflag=Utility.parseInt(request.getParameter("subflag"),0);
Integer begin_date = new Integer(Utility.getCurrentDate());
Integer end_date = new Integer(Utility.getCurrentDate());
String[] totalColumn = null;
String workbench = "";

int timer_id = Utility.parseInt(request.getParameter("timer_id"),0);
//声明对象
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
ProductLocal productLocal = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();
MeetingLocal meetingLocal = EJBFactory.getMeeting();
MeetingVO m_vo = new MeetingVO();
ApplyreachLocal applyLoal = EJBFactory.getApplyreach();
ApplyreachVO appl_vo = new ApplyreachVO();
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO active_vo = new ActivityVO();
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO activityTask_vo = new ActivityTaskVO();
ActivityTaskVO activityTask_vo2 = new ActivityTaskVO();
ChannelLocal channel_local = EJBFactory.getChannel();
ChannelVO channel_vo = new ChannelVO();
TcustmanagersVO marketing_vo = new TcustmanagersVO();
ContractManagementLocal contractM = EJBFactory.getContractManagement();

Map workConfigMap =null,workDetailMap=null;
String workDetailSql="",workCondtion="",work_no="",temSql="",whoeSql="",selectValue="",menuId="",fromValue="",detailSql="",tempName="",name="",orderNo="",jsp_name="",workCount="",workCountSql="",tempSql="",showSql="";
int iNumber=0,iCount=0;
enfo.crm.project.BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
List workConfigList = null;
String[][] workShowCount = null;
List workDetailList = null;
if(Argument.getSyscontrolValue("FLOW_CONTROL") == 1)
	workConfigList= businessLogicLocal.listAllWork(String.valueOf(input_operatorCode),"","CRM");
if(workConfigList != null){
	workShowCount = new String[workConfigList.size()][2];
	for(int i=0;i<workConfigList.size();i++){
		workConfigMap=(Map) workConfigList.get(i);
		work_no = Utility.trimNull(workConfigMap.get("WORK_NO"));
		workCountSql = Utility.trimNull(workConfigMap.get("WORK_COUNT"));
		workDetailSql = Utility.trimNull(workConfigMap.get("WORK_HEAD"));
		workCondtion = Utility.trimNull(workConfigMap.get("WORK_CONDITION"));
		jsp_name = Utility.trimNull(workConfigMap.get("JSP_NAME"));
		menuId = Utility.trimNull(workConfigMap.get("MENU_ID"));
		 workCountSql = workCountSql + workCondtion;
	  	workDetailSql = workDetailSql+workCondtion;
		workCountSql = workCountSql.replaceAll("#USER#",String.valueOf(input_operatorCode));
		workDetailSql = workDetailSql.replaceAll("#USER#",String.valueOf(input_operatorCode));
		if(i==0){
			tempSql = "select " + "("+workCountSql+") as W"+work_no+"";
		    showSql =showSql + tempSql;
			selectValue = workDetailSql.substring(0,workDetailSql.indexOf(" from "));
			fromValue = workDetailSql.substring(workDetailSql.indexOf(" from "),workDetailSql.length());
			detailSql = selectValue+" as TITLE_NAME,"+"'W"+work_no+"' as ORDER_NO "+ fromValue;
		}else{
			tempSql = "("+workCountSql+") as W"+work_no+"";
		    showSql = showSql + "," +tempSql;
			selectValue = workDetailSql.substring(0,workDetailSql.indexOf(" from "));
			fromValue = workDetailSql.substring(workDetailSql.indexOf(" from "),workDetailSql.length());
			detailSql = detailSql+" union all "+selectValue+" as TITLE_NAME,"+"'W"+work_no+"' as ORDER_NO "+ fromValue;
		}
		workShowCount[i][0] = "W"+work_no+""; 
	}

	List workCountList = businessLogicLocal.listWorkCount(showSql);
	for (int i=0;i<workCountList.size();i++){ 
	     Map workCountMap = (Map)workCountList.get(i);
	    for(int j=0;j<workShowCount.length;j++)
	    	workShowCount[j][1] = Utility.trimNull(workCountMap.get(workShowCount[j][0]));	    
	}
	workDetailList = businessLogicLocal.listWorkCount(detailSql);	
}

int screWidth = Utility.parseInt(Utility.trimNull(request.getParameter("screenAvailWidth")),1024);//从index.js里获取屏幕分辨率
int charSum = 35;//首页各条提示信息的最多显示字数

//输入查询参数
vo.setOp_code(input_operatorCode);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);

IPageList insideServiceTasksList = local.listInsideServiceTasks(vo,1,10);
IPageList outsideServiceTasksList = local.listOutsideServiceTasks(vo,1,10);
IPageList scheDuleServiceTasksList = local.listScheDule(vo,1,10);

p_vo.setProduct_status("110202");
p_vo.setIntrust_flag1(new Integer(2));
IPageList productNormalTaskList = productLocal.productList(p_vo,totalColumn,1,5);

m_vo.setAttend_man(input_operatorName);
IPageList meetingTaskList = meetingLocal.pageList_query(m_vo,totalColumn,1,5);

appl_vo.setBook_code(new Integer(1));
appl_vo.setCheck_flag(new Integer(1));
appl_vo.setInput_man(input_operatorCode);
IPageList applyreachCheckTaskList = applyLoal.listAll(appl_vo,totalColumn,1,5);
//
active_vo.setManage_code(input_operatorCode);
active_vo.setActive_flag(new Integer(0));
IPageList activeList = activityLocal.pageList_query(active_vo,totalColumn,1,5);

activityTask_vo.setExecutor(input_operatorCode);
activityTask_vo.setTaskFlag(new Integer(1));
IPageList activeTaskList = activityTaskLocal.pageList_query(activityTask_vo,totalColumn,1,5);

activityTask_vo2.setManagerCode(input_operatorCode);
activityTask_vo2.setTaskFlag(new Integer(2));
IPageList activeTaskList2 = activityTaskLocal.pageList_query(activityTask_vo2,totalColumn,1,5);

channel_vo.setSerial_no(new Integer(0));
List channel_list = channel_local.queryServicePlan(channel_vo);

marketing_vo.setProduct_id(new Integer(0));
marketing_vo.setLink_man(input_operatorCode);
marketing_vo.setInput_man(input_operatorCode);
List marketing = local.queryProductMarketing1(marketing_vo);

workbench = local.getWorkBench(input_operatorCode);
Integer LOG0001=(Integer)session.getAttribute("LOG0001");

PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();
pre_vo.setStatus("110202");
pre_vo.setInput_man(input_operatorCode);

//设置呼叫中心环境
String callcenter_mode = "";
if (user_id.intValue() == 15) callcenter_mode = "_jianxin";
%>
<html>
<head>
<title><%=LocalUtilis.language("main.menu.myDesk",clientLocale)%> </title><!--首页工作台-->
<meta http-equiv="Content-Type" content="text/html;charset=GBK" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
<link type="text/css" href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.7.2.custom.css" rel="stylesheet" />	
<link type="text/css" href="<%=request.getContextPath()%>/includes/jQuery/inettuts/inettuts.css" rel="stylesheet" />	
<link type="text/css" href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" rel="stylesheet" />	
<style>	
	.tel-num-text{
		height:35px;
		width:220px;
		max-width:220px;
		line-height:40px;
		font-size:25px;
		font-weight : bold;
		color:#3366ff;
		text-align:right;
	}
	.errmsgtext{
		font-size:12px;
		font-weight : bold;
		color:red;
	}
	.tableProduct{
		border-collapse:collapse;
		border:solid #D1EEEE
		border-width:1px 1px 1px 1px;
	} 
	.tableProduct td{border:solid #D1EEEE;border-width:1px 1px 0px 0px;padding:1px 2px 1px 1px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-all.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/loginService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/menuService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/callcenter/callcenter<%=callcenter_mode%>.js"></script>
<script language="vbscript" src="<%=request.getContextPath()%>/includes/callcenter/callcenter.vbs"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script language=javascript>
	//$('<style type="text/css">.column{visibility:hidden;}</style>').appendTo('head');
	var j$ = jQuery.noConflict();
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/inettuts/cookie.jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/inettuts/inettuts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<%if(user_id.intValue() == 15){ %>
    <%@ include file="/includes/callcenter/callcenter.inc" %>
<%}%>
<script language=javascript>
	var busyorfree;
	var leavingorback;
	var timer_id = <%=timer_id%>;
	//var myPhone = j$(window.top.document).find('#phone')[0];
	initMyPhone();

<%if (user_id.intValue()!=22/*中投*/) { %>
	$(function(){
		j$("#main-callcenter-accordion").accordion({ header: "h3",fillSpace: true}); 
	});
<%}%>
	
	Ext.onReady(function(){
		initMyPhone();
		if(Ext.get("callLink") != null ){
			Ext.get("callLink").on('click',function(e){
				document.parentWindow.parent.document.getElementById("callcenterLink").onclick();
			});
		}
		if(Ext.get("freeLink") != null ){
			Ext.get("freeLink").on('click',busyFree);
		}
		if(Ext.get("leavingLink") != null ){
			Ext.get("leavingLink").on('click',leavingBack);
		}
		if(j$("#display_flag").val()!=1){
			j$("#accordion").css("visibility","hidden");
		}else{			
			showWorkBench();
			iNettuts.init();
		}
	});
	
	function calloutConfirm(btn,text){
		if(btn=='ok'){
			var telnum = /^[0-9]*$/;
			if(!telnum.test(text)){
				//警告 您输入的电话号码 不符合规则，请检查后重新输入
				Ext.MessageBox.alert('<%=LocalUtilis.language("message.warn",clientLocale)%> ','<%=LocalUtilis.language("index.callcenter.yourTel",clientLocale)%> '+text+'<%=LocalUtilis.language("index.callcenter.confirmRuls",clientLocale)%> ');
			}else{
				//确认 您要拨打的电话号码是 请在摘机后单击确认按钮
				Ext.MessageBox.confirm(
					'<%=LocalUtilis.language("message.ok",clientLocale)%>','<%=LocalUtilis.language("index.callcenter.yourCallTel",clientLocale)%>:'+text+'，<%=LocalUtilis.language("index.callcenter.confirmPickTel",clientLocale)%> ',
					function(btn){
						if(btn=='yes'){
							callout('<%=cc_extension%>',text);
						}	
					}
				);
			}
		}
	}
	
	function busyFree(e){		
		if(busyorfree==1){
			j$('#freeLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/free.jpg' );
			j$('#freeLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.clickToBusy",clientLocale)%> ');//单击示忙
			setFree('<%=cc_extension%>','<%=input_operatorCode%>');
			busyorfree = 0;
		}else{
			j$('#freeLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/busy.jpg' );
			j$('#freeLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.clickToFree",clientLocale)%> ');//单击示闲
			setBusy('<%=cc_extension%>','<%=input_operatorCode%>');
			busyorfree = 1;	
		}
		//document.parentWindow.parent.document.getElementById("freeLink").onclick();
		document.parentWindow.parent.busyFree(e);
	}
	
	function leavingBack(e){
		if (leavingorback==1) {
			j$('#leavingLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/back.jpg' );
			j$('#leavingLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.toLeaveStatus",clientLocale)%> ');//置为离开状态
			setFree('<%=cc_extension%>','<%=input_operatorCode%>');
			leavingorback = 0;	
		} else {
			j$('#leavingLink img').attr( 'src', '<%=request.getContextPath()%>/images/callcenter/leaving.jpg' );
			j$('#leavingLink img').attr( 'title', '<%=LocalUtilis.language("index.callcenter.toBackStatus",clientLocale)%> ');//置为回来状态
			setBusy('<%=cc_extension%>','<%=input_operatorCode%>');
			leavingorback = 1;
		}
		//document.parentWindow.parent.document.getElementById("leavingLink").onclick();busyFree
		document.parentWindow.parent.leavingBack(e);
	}
	
	function loadUrl(menu_id,url){
		loginService.alertString(menu_id,'<%=LOG0001.toString()%>',<%=input_operatorCode%>,{callback: function(data){location=url;}});
	}
	
	function productQuery(product_id,item_id){	
		showModalDialog('<%=request.getContextPath()%>/marketing/base/product_list_detail.jsp?product_id='+product_id+'&item_id='+item_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
	}
	
	function transferCheck(serial_no,manager_before,manager_now){	
		var returnValue=showModalDialog('<%=request.getContextPath()%>/affair/base/client_handover_check_one.jsp?serial_no='+serial_no+'&manager_before='+manager_before+'&manager_now='+manager_now, '','dialogWidth:360px;dialogHeight:300px;status:0;help:0');
		if (returnValue) window.location.reload();
	}
		
	function calendarQuery(serial_no){
		var url = "<%=request.getContextPath()%>/affair/time/calendar_info.jsp?serial_no="+serial_no;
		showModalDialog(url,'','dialogWidth:450px;dialogHeight:300px;status:0;help:0');
	}

	function moneyTaskQuery(record_id,task_type){
		var url="";
		if(task_type==1){
			url = "<%=request.getContextPath()%>/contractManagement/tcoMoneyTaskInfo_contract.jsp?record_id="+record_id;
		}else{
			url = "<%=request.getContextPath()%>/contractManagement/tcoMoneyTaskInfo_maintain.jsp?record_id="+record_id;
		}
		var ret=showModalDialog(url,'','dialogWidth:900px;dialogHeight:200px;status:0;help:0');
		if(ret==1){
			window.location.reload();
		}
	}

	function applyCheckQuery(serial_no){
		var url ="<%=request.getContextPath()%>/marketing/sell/apply_purchase_check_info.jsp?serial_no="+serial_no;
		window.location.href=url;
	}
	
	function activeTaskDealAction(serial_no){
		var url ="<%=request.getContextPath()%>/marketing/activity/activity_task_deal_action.jsp?transFlag=3&serial_no="+serial_no;
		window.location.href=url;
	}
	
	//领导查询页面
	function product_delation() {
		var url ="<%=request.getContextPath()%>/client/analyse/product_sale_total.jsp?";
		window.location.href=url;
	}

	function activeTaskDealCheckAction(serial_no) {
		var url ="<%=request.getContextPath()%>/marketing/activity/activity_task_check_action.jsp?transFlag=2&serial_no="+serial_no;
		window.location.href=url;
	}
	
	//认购、到账审核URL
	function urlFundsCheck() {
		var url = '<%=request.getContextPath()%>/marketing/sell/funds_check_list.jsp?menu_id=30219&first_flag=1&parent_id=0';
		window.location.href=url;
	}

	function channelAction(channel_id,serial_no){
		var url = "<%=request.getContextPath()%>/client/channel/channel_action.jsp?channel_id="+channel_id;
		var surl = "<%=request.getContextPath()%>/client/channel/service_plan_action.jsp?serial_no="+serial_no;

		showModalDialog(url,'', 'dialogWidth:750px;dialogHeight:300px;status:0;help:0');
		showModalDialog(surl,'', 'dialogWidth:750px;dialogHeight:400px;status:0;help:0');
	}

	function productMarketingAction(product_id,link_man){
		var url = "<%=request.getContextPath()%>/client/analyse/product_marketing_total.jsp";
		window.location.href=url;
	}
	
	function showDialog(){
		dialog("<%=LocalUtilis.language("main.message.setWorkBench",clientLocale)%> ","id:setContent","550px","auto","show","<%=request.getContextPath()%>"); //设置常用模块
	}

	function showPrpductPre(){
		var url = "<%=request.getContextPath()%>/marketing/sell/product_pre_total.jsp";
		//var url = "<%=request.getContextPath()%>/marketing/sell/reserve_list.jsp";
		window.location.href = url;
	}

	function showProductInfo(preproduct_id){
		var url = "<%=request.getContextPath()%>/wiki/product_info_view.jsp?preproduct_id="+preproduct_id;
		window.open(url);
	}

	function showWorkBench(){
		var workbench = document.getElementById("workbench").value;
		var workbenchArray = workbench.split("|");
		for(var i=0;i<workbenchArray.length;i++){
			var workbenchId = workbenchArray[i];
			var workbenchCheckId = "workbench_"+workbenchId;
			
			if(workbenchArray[i] == "widget99" )//显示在售产品预约情况
				document.getElementById("product").style.display = "";

			if (document.getElementById(workbenchId))
				document.getElementById(workbenchId).style.display = "";	
			
			if (document.getElementById(workbenchCheckId))
				document.getElementById(workbenchCheckId).checked = true;
		}
	<%if(Argument.getSyscontrolValue("FLOW_CONTROL") == 1){%>
		//显示待处理流程
		if (document.getElementById("widget98"))
			document.getElementById("widget98").style.display = "";
		if (document.getElementById("workbench_widget98"))
			document.getElementById("workbench_widget98").checked = true;
	<%}%>
	}
	
	/*常用工作台模块设置*/
	function setAction(){
		var input_operatorCode = document.getElementById("input_operatorCode").value;
		var workbenchArray = document.getElementsByName("workbenchOptions");
		var len = workbenchArray.length;
		var i = len/2;
		var v_workbench = "";

		for(;i<len;i++){
			if(workbenchArray[i].checked){
				var v_value = workbenchArray[i].value;
				v_workbench = v_workbench + v_value +"|";
			}
		}
		
		v_workbench = v_workbench.substring(0,v_workbench.length-1);
		
		if(v_workbench.length==0){
			sl_alert("<%=LocalUtilis.language("main.message.chooseWorkBench",clientLocale)%> ");//请选择常用工作台！
			return false;
		}
		
		if(sl_confirm("<%=LocalUtilis.language("message.save",clientLocale)%> ")){//保存
			loginService.modiWorkBench(input_operatorCode,v_workbench,
				{callback: 	function(data){
								if(data=="100"){
									sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
									window.location.reload();
								}
							}
				});
		}
	}

//查看产品预约统计信息
function showTeamInfo(pre_product_id, pre_product_name, pre_money){
	var url = '<%=request.getContextPath()%>/marketing/sell/product_pre_total_team.jsp?pre_product_id=' + pre_product_id + '&pre_product_name=' + pre_product_name + '&pre_money=' + pre_money;
	showModalDialog(url, '', 'dialogWidth:1000px;dialogHeight:600px;status:0;help:0');
}

//直接预约
function reservAddInfo(pre_product_id){
	location = "<%=request.getContextPath()%>/marketing/sell/direct_reserve_add.jsp?from=main&pre_product_id="+pre_product_id;
}

//----------------------进入对应页面-----------------//
function fn1(work_no,surl){
    loginService.showTask(work_no,'1',<%=input_operatorCode%>,function(){location = surl;} );
}

//---------显示流程---------
function showInfo(object_id) {
	showModalDialog('/project/flow/flowobject_tab.jsp?object_id='+object_id+'&object_type=WorkLog&flow_no=WorkLogFlow&node_no=100&show_flag=ViewOpinion&edit_right=no', '', 'dialogWidth:1000px;dialogHeight:760px;status:0;help:0');
}

function showMsg(serial_no) {
	var retval = showModalDialog('msg_detail.jsp?serial_no='+serial_no,'', 'dialogWidth:400px;dialogHeight:180px;status:0;help:0');
	if (retval==2) {
		var obj = document.getElementById("li_msg"+serial_no);
		if (obj) obj.style.display = "none";
	}
}
</script>
</head>
<body style="overflow:auto;" class="body">
<input type="hidden" name="workbench" id="workbench" value="<%=workbench%>" />
<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>" />
<input type="hidden" name="display_flag" id="display_flag" value="<%=display_flag%>" />

<div id="accordion" style="margin-left:5px;height:98%;width:100%;">
	<div id="product" style="margin-left:5px;height:30%;width:99%;display:none">
		<ul id="product1" class="column9">
		 	<li class="widget9" id="widget99" style="display:none">
	            <div class="widget-head">
	                <h2><img src="/images/home1.gif" />&nbsp;在售产品预约情况</h2>&nbsp;&nbsp;&nbsp;</font>
	            </div>
	            <div class="widget-content" style="overflow:scroll;">
				<%
				List preList  = preContract.getProductPreList(pre_vo); 
				int pre_len = 5<preList.size()?5:preList.size();
				if (pre_len ==0) { %>
					<div align="left" style="margin-left:20px;margin-top:10px;">没有在售产品预约信息 。</div>
				<%} else {%>
					<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
						<tr class="tr0" bgcolor="#E0EEEE">
							<td align="center" width="*"><font size="1">产品名称</font></td>
							<td align="center" width="*"><font size="1">发行规模</font></td>
							<td align="center" width="*"><font size="1">开始时间</font></td>
							<td align="center" width="*"><font size="1">截止时间</font></td>
							<td align="center" width="*"><font size="1">发行方式</font></td>
							<td align="center" width="*"><font size="1">已预约规模</font></td>
							<td align="center" width="*"><font size="1">到账规模</font></td>
							<td align="center" width="*"><font size="1">在途资金规模</font></td>
							<td align="center" width="*"><font size="1">销售平均成本</font></td>	
							<td align="center" width="*"><font size="1">小额份数</font></td>
							<td align="center" width="*"><font size="1">剩余预约规模</font></td>
							<td align="center" width="*"><font size="1">大额(有效数/总数)</font></td>
							<td align="center" width="*"><font size="1">操作</font></td>
						</tr>
				<%	for(int i=0;i<pre_len;i++){
						Map preMap = (Map)preList.get(i);
						Integer preproduct_id = Utility.parseInt(Utility.trimNull(preMap.get("PREPRODUCT_ID")),new Integer(0));
						double pre_max_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_MONEY")),new BigDecimal(0),2,"1").doubleValue();
						double pre_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_FACT_MONEY")),new BigDecimal(0),2,"1").doubleValue();
						double rg_money = Utility.parseDecimal(Utility.trimNull(preMap.get("RG_MONEY")),new BigDecimal(0),2,"1").doubleValue();
						double onway_money = Utility.parseDecimal(Utility.trimNull(preMap.get("ONWAY_MONEY")),new BigDecimal(0),2,"1").doubleValue();
						double sell_cost = Utility.parseDecimal(Utility.trimNull(preMap.get("SELL_COST")),new BigDecimal(0),2,"1").doubleValue();
						double available_money = pre_max_money - pre_money;
						int direct_sale = Utility.parseInt(Utility.trimNull(preMap.get("DIRECT_SALE")),0);
						BigDecimal max_ratio = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(preMap.get("MAX_RATIO"))), new BigDecimal(0)).setScale(6, BigDecimal.ROUND_HALF_UP);
						BigDecimal stand_ratio = new BigDecimal(0.800000); %>
						<tr class="tr1">
							<td align="left"><a href="#" class="a2" title="查看产品信息" onclick="javascript:showProductInfo('<%=preproduct_id%>');" ><%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%></a></td>
							<td align="right" width="*"><%=Format.formatMoney(pre_max_money)%></td>
							<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_START_DATE")),new Integer(0)))%></td>
							<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_END_DATE")),new Integer(0)))%></td>
							<td align="center" width="*"><%if(direct_sale == 1) out.print("代销");else if(direct_sale == 2) out.print("直销");else if(direct_sale == 3) out.print("直销&代销");%></td>
							<td align="right" width="*"><a href="#" class="a2" title="查看产品预约统计信息" onclick="javascript:showTeamInfo('<%=preproduct_id%>','<%=java.net.URLEncoder.encode(Utility.trimNull(preMap.get("PREPRODUCT_NAME")))%>','<%=Utility.trimNull(Format.formatMoney(pre_money))%>');" ><font color="<%=max_ratio.compareTo(stand_ratio) == 1 ? "red" : "" %>"><%=Format.formatMoney(pre_money)%></font></a></td>
							<td align="right" width="*"><%=Format.formatMoney(rg_money)%></td>
							<td align="right" width="*"><%=Format.formatMoney(onway_money)%></td>
							<td align="right" width="*"><%=Format.formatMoney(sell_cost)%></td>
							<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("LESS300_NUMBER")),new Integer(0))%></td>
							<td align="right" width="*"><%=Format.formatMoney(available_money)%></td>
							<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_VALID_COUNT")),0)%> / <%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_COUNT")),0)%></td>
							<td align="center" width="*">
		       					<a href="#" class="a2" onclick="javascript:reservAddInfo('<%=preproduct_id %>');" title="产品预约">预约</a>
							</td>
						</tr> 
				<%	}%>
					</table>
				<%}%>
					<div align="right"><a href="#" class="a2" onclick="javascript:window.location.reload();">刷新</a> | <a href="#" class="a2"	 onclick="javascript:showPrpductPre();">更多>></a></div>
	            </div>
	        </li>
		</ul>
	</div>
<%out.flush(); %>
	<div id="main-tasks" style="position:absolute;height:98%;">
		 <div id="columns">	        
	        <ul id="column1" class="column">
				
				<li class="widget" id="widget98" <%if(Argument.getSyscontrolValue("FLOW_CONTROL") != 1){ %> style="display:none"<%} %>> 
					<div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;待处理流程任务</h2>
	                </div>
					<div class="widget-content"> 
	              		 <table  style="width:100%;border:5px;cellspacing:0;cellpadding:0;position:relative">
				<%
				int countToShow_1 = 0;
				if (workConfigList != null) {
					for (int i=0; i<workConfigList.size(); i++) {
						iNumber = 0; 
						workConfigMap = (Map)workConfigList.get(i);
						work_no = Utility.trimNull(workConfigMap.get("WORK_NO"));
			    		menuId = Utility.trimNull(workConfigMap.get("MENU_ID"));
			    		jsp_name = Utility.trimNull(workConfigMap.get("JSP_NAME"));
			    		workCount=workShowCount[i][1];
			    		if (Integer.parseInt(workCount)>0) { 
							for(int j=0;j<workDetailList.size();j++){
								workDetailMap = (Map)workDetailList.get(j);
	    						name=Utility.trimNull(workDetailMap.get("TITLE_NAME"));//用于鼠标放上去后title中的全称显示
	    						tempName=name;//列表显示
	    						orderNo=Utility.trimNull(workDetailMap.get("ORDER_NO"));
	    						iNumber ++;
								countToShow_1 ++;
								if (tempName.length() >charSum) 	tempName = tempName.substring(0,charSum-1)+"…";
								
								if (iNumber>5) break;							
						 %>
							<tr style="font-size:13">
					
								<td style="width:13px;align:left;valign:bottom;height:18"><div style="height:16px"></div></td>
								<td class="infotd undertd" align="left" valign="bottom" nowrap="nowrap" >
									
									<a title="<%=name%>" href="<%=jsp_name %>" class="a2"><font color="red"><%=tempName%></font></a>	
								</td>
						
							</tr>	
				<%			}
						}
					}
					if (countToShow_1==0){%>
					<div align="left" style="margin-left:20px;margin-top:10px;">最近没有流程任务 。</div>
				<%	} 	
				} %>
			   			</table>
					</div>
	            </li>
	            <li class="widget" id="intro" style="display:none">
	                <div class="widget-head">
	                    <h2><img src="/images/home1.gif" />&nbsp;<%=LocalUtilis.language("main.menu.mySchedule",clientLocale)%></h2><!--我的日程-->
	                </div>
	                <div class="widget-content">	           
					<%
					List scheDuleList = scheDuleServiceTasksList.getRsList();
					Integer scheDule_serial_no = new Integer(0);
					int sche_len = 5<scheDuleList.size()?5:scheDuleList.size();						
					if (sche_len==0){						%>
						<!--您今天没有日程安排-->
						<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.noSchedule",clientLocale)%> 。</div>
					<%}else{%>
						<ul>
					<%	for (int i=0; i<sche_len; i++) {
							Map scheDuleMap = (Map)scheDuleList.get(i);
							scheDule_serial_no = Utility.parseInt(Utility.trimNull(scheDuleMap.get("SERIAL_NO")),new Integer(0));
							String schedule_type  =  Utility.trimNull(scheDuleMap.get("SCHEDULE_TYPE"));
							Integer df_serial_no = Utility.parseInt(Utility.trimNull(scheDuleMap.get("DF_SERIAL_NO")),new Integer(0));
							String content  =  Utility.trimNull(scheDuleMap.get("CONTENT"));
							if ("304290".equals(schedule_type)) { 
								if (df_serial_no.intValue() != 0) { %>
							<li>
								<a href="#" onclick="javascript:showInfo('<%=df_serial_no%>');" class="a2"><font color="red">
									<%=scheDuleMap.get("START_DATE").toString().substring(0,16)%>，<%=content %></font>
								</a>
							</li>
					<%			}
							}else{ %>
							<li>
								<a href="#" onclick="javascript:calendarQuery('<%=scheDule_serial_no%>');" class="a2"><font color="red">
									<%=scheDuleMap.get("START_DATE").toString().substring(0,16)%>，
									<%=LocalUtilis.language("main.message.youhave",clientLocale)%> <%=scheDuleMap.get("SCHEDULE_NAME")%></font>
									<!--您有一个-->
								</a>
					<%		} %>										
							</li>
					<%	}%>
						</ul>
					<%}%>							
	                </div>
	            </li>
<%out.flush(); %>
	            <li class="widget" id="widget20" style="display:none">
	                <div class="widget-head">
	                    <h2><img src="/images/home1.gif" />&nbsp;我的消息 </h2>
	                </div>
	                <div class="widget-content">	           
					<%
					OperatorVO op = new OperatorVO();
					op.setSerial_no(new Integer(0));
					op.setTo_op_code(input_operatorCode);
					op.setOp_code(input_operatorCode);
					op.setIs_msg_read(new Integer(1)); // not read
					List msgList = local.listSysMessage(op, 1, -1).getRsList();							
					if (msgList.size()==0) { %>
						<div align="left" style="margin-left:20px;margin-top:10px;">您没有收到任何消息 。</div>
					<%} else {	%>
							<ul>
					<% 	for (int i=0; i<msgList.size(); i++) {
							Map msg = (Map)msgList.get(i);
							Integer serial_no = (Integer)msg.get("SERIAL_NO");
							String title  =  Utility.trimNull(msg.get("TITLE")); %>	
								<li id="li_msg<%=serial_no%>">
									<a href="javascript:showMsg(<%=serial_no%>);" class="a2"><font color="red">
										<%=msg.get("INPUT_TIME").toString().substring(0,16)%>，<%=title%></font>
									</a>
								</li>
					<%	}%>
							</ul>		
					<%} %>												
	                </div>
	            </li>

	             <li class="widget" id="widget2" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home2.gif" />&nbsp;<%=LocalUtilis.language("main.menu.promotionProduct",clientLocale)%></h2><!--最新推介期产品-->
	                </div>
	                <div class="widget-content">
					<%
					List productlist = productNormalTaskList.getRsList();
					int p_len = 5<productlist.size()?5:productlist.size();						
					if (p_len==0){ %>		
						<div align="left" style="margin-left:20px;margin-top:10px;">
							<%=LocalUtilis.language("main.message.noPromotion",clientLocale)%> 。</div><!--最近没有推介期产品-->
					<%}else{ %>
						<ul>
					<%	for (int i=0; i<p_len; i++) {
							Map productMap = (Map)productlist.get(i);
							Integer item_id =Utility.parseInt(Utility.trimNull(productMap.get("ITEM_ID")),new Integer(0)) ;
							Integer product_id =Utility.parseInt(Utility.trimNull(productMap.get("SPEC_PRODUCT_ID")),new Integer(0)); %>	
							<li>
					<%		if (product_id.intValue()!=0){ %>
								<a href="#" onclick="javascript:productQuery('<%=product_id%>','<%=item_id%>');" class="a2"><font color="red">
									<%=productMap.get("PRODUCT_CODE")%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=productMap.get("PRODUCT_NAME")%>	</font>		
								</a>	
					<%		}else{ %>
								<%=productMap.get("PRODUCT_CODE")%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=productMap.get("PRODUCT_NAME")%>		
					<%		} %>
							</li>
					<%	}%>
						</ul>
					<%}%>		
	                </div>
	            </li>
	            <li class="widget" id="widget3" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home3.gif" />&nbsp;<%=LocalUtilis.language("class.productMarketing",clientLocale)%></h2><!--在售产品统计 -->
	                </div>	                	  
                	<% 
            		int iCurrent = 0;
					int marketing_len = 5<marketing.size()?5:marketing.size();				
					if (marketing_len==0) { %>
					<div class="widget-content">
						<div align="left" style="margin-top:10px;margin-left:20px;">
							<%=LocalUtilis.language("loginedtop.currentuser",clientLocale)%><%=LocalUtilis.language("message.nothing",clientLocale)%><%=LocalUtilis.language("class.salesInfo",clientLocale)%> </div>
							<!--当前用户无销售信息-->
					<%}else{
						BigDecimal pre_money  = new BigDecimal(0);
					   	BigDecimal rg_money  = new BigDecimal(0);
					   	BigDecimal my_rg_money  = new BigDecimal(0); %>
						<div style="color#fcfdfd">
							<ul>
								<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					 <tr class="trh" bgcolor=#fcfdfd>
						<td align="center" width="*"><%=LocalUtilis.language("class.productID",clientLocale)%> </td><!--产品编号 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.preMoney",clientLocale)%></td><!--预计发行金额-->
						<td align="center" width="*"><%=LocalUtilis.language("class.rgMoney",clientLocale)%></td><!--已售金额-->
						<td align="center" width="*"><%=LocalUtilis.language("class.linkMan2",clientLocale)%><%=LocalUtilis.language("class.toMoney3",clientLocale)%></td><!--我的已售金额-->
					</tr>
					<%	for (int i=0;i<marketing.size();i++) {
							Map marketingMap = (Map)marketing.get(i);
							pre_money = Utility.parseDecimal(Utility.trimNull(marketingMap.get("PRE_MONEY")),new BigDecimal(0),2,"1");
							rg_money = Utility.parseDecimal(Utility.trimNull(marketingMap.get("RG_MONEY")),new BigDecimal(0),2,"1");
							my_rg_money = Utility.parseDecimal(Utility.trimNull(marketingMap.get("MY_TO_MONEY")),new BigDecimal(0),2,"1");%>
					 <tr class="tr0" ondblclick="javascript:productMarketingAction(<%=marketingMap.get("PRODUCT_ID")%>,<%=marketingMap.get("LINK_MAN")%>)">
						<td align="center" width="*"><font color="red"><%=marketingMap.get("PRODUCT_CODE")%></font></td>
						<td align="right" width="*"><font color="red"><%=Format.formatMoney(pre_money)%>&nbsp;&nbsp;</font></td>
						<td align="right" width="*"><font color="red"><%=Format.formatMoney(rg_money)%>&nbsp;&nbsp;</font></td>
						<td align="right" width="*"><font color="red"><%=Format.formatMoney(my_rg_money)%>&nbsp;&nbsp;</font></td>
					</tr>
					<%		iCurrent++; 
						}
						for (int i=0; i < 3; i++) { %>
						<tr class="tr0">
							<td></td><td></td><td></td><td></td>
						</tr>
					<%	} %>
					</table>
							</ul>
					<%}%>
	                </div>
	            </li>
				<li class="widget" id="widget10" style="display:none" >
	                <div class="widget-head">
	                    <h2><img src="/images/FUNC20076.gif" />&nbsp;<%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("message.servicePlans",clientLocale)%></h2><!--渠道维护计划-->
	                </div>
	                <div class="widget-content">
					<%
					if (channel_list.size()==0){ %>
						<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("message.servicePlans",clientLocale)%> 。</div>
						<!--最近没有渠道维护计划-->
					<%}else{ %>
						<ul>
					<% 	for (int i=0; i<channel_list.size(); i++) {
							Map channelMap = (Map)channel_list.get(i);
			                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			                Integer serial_no = Utility.parseInt(Utility.trimNull(channelMap.get("SERIAL_NO")),new Integer(0));
			                Integer channel_id = Utility.parseInt(Utility.trimNull(channelMap.get("CHANNEL_ID")),new Integer(0));
							String plan_time = Utility.trimNull(channelMap.get("PLAN_TIME"));
							String service_content = Utility.trimNull(channelMap.get("SERVICE_CONTENT"));
							Date plan_Date = sdf.parse(plan_time);
							long p_date=((new Date()).getTime() - plan_Date.getTime())/(24*60*60*1000);
							if (p_date > -4 && p_date < 1 && service_content.equals("")){ %>
							<li>
								<a href="#" onclick="javascript:channelAction(<%=channel_id%>,<%=serial_no %>);" class="a2"><font color="red">
									<%=Utility.trimNull(channelMap.get("CHANNEL_NAME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(channelMap.get("PLAN_CONTENT"))%></font>
								</a>
							</li>
					<%		}
						}%>
					   </ul>
					<%}%>
	               </div>
	            </li>
			<%if (Argument.getSyscontrolValue("DT_eTrust")!=0) { %>
			  <li class="widget" id="widget13" style="display:none">  
	                <div class="widget-head">
	                    <h2><%=LocalUtilis.language("main.menu.selfAffairs",clientLocale)%> </h2><!--待处理自助事务-->
	                </div>
	                <div class="widget-content">	                	
						<% 
						List outsideList = outsideServiceTasksList.getRsList();
						int outside_len = 5<outsideList.size()?5:outsideList.size();						
						if(outside_len==0){ %>
							<div align="left" style="margin-top:10px;margin-left:20px;"><%=LocalUtilis.language("main.message.selfAffairs",clientLocale)%> </div>
							<!--此处将显示待处理自助事务-->
						<%}else{ %>
							<ul>
						<% 	for(int i=0;i<outsideList.size();i++){
								Map outsideMap = (Map)outsideList.get(i); %>
								<li><a href="#" class="a2" onclick="javascript:window.open('<%=outsideMap.get("URL")%>')"><font color="red"><%=outsideMap.get("TipTitle")%></font></a><font color="red">(<%=outsideMap.get("number")%>)</font></li>
						<% 	}%>
							</ul>
						<%}%>		                    
	                </div>
	            </li>
			<%}%>	            
	            <li class="widget" id="widget14" style="display:none">
	                <div class="widget-head">
	                    <h2><img src="/images/home1.gif" />&nbsp;收款计划提醒</h2>
	                </div>
	                <div class="widget-content">	           
					<%
					TcoMoneyTaskLocal tcoMoneyTaskLocal=EJBFactory.getTcoMoneyTask();
					TcoMoneyTaskVO  tcoMoneyTaskVO=new TcoMoneyTaskVO();
					tcoMoneyTaskVO.setInput_man(input_operatorCode);
					//tcoMoneyTaskLocal.createMoneyTask(tcoMoneyTaskVO);
					List tcoMoneyTaskList=tcoMoneyTaskLocal.queryByList(tcoMoneyTaskVO);
					int tcoMoneyTaskLength=5<tcoMoneyTaskList.size()?5:tcoMoneyTaskList.size();
					if (tcoMoneyTaskLength==0) { %>
						<div align="left" style="margin-left:20px;margin-top:10px;">您今天没有收款计划提醒。</div>
					<%}else{ %>
						<ul>
					<%	for(int i=0;i<tcoMoneyTaskLength;i++){
							Map tcoMoneyTaskMap = (Map)tcoMoneyTaskList.get(i);
							Integer tcoMoneyTask_serial_no = Utility.parseInt(Utility.trimNull(tcoMoneyTaskMap.get("SERIAL_NO")),new Integer(0));
							String record_id = Utility.trimNull(tcoMoneyTaskMap.get("RECORD_ID"));
							String task_type = Utility.trimNull(tcoMoneyTaskMap.get("TASK_TYPE")); %>	
							<li>
								<a href="#" onclick="javascript:moneyTaskQuery('<%=record_id%>','<%=task_type%>');" class="a2"><font color="red">
									<%=tcoMoneyTaskMap.get("TITLE")%>:<%=tcoMoneyTaskMap.get("INFO_STR")%></font>
								</a>
							</li>
					<%	}%>
						</ul>
					<%}%>							
	                </div>
	            </li>
	        </ul>
			
	        <ul id="column2" class="column">
	        	<li class="widget" id="widget4" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home4.gif" />&nbsp;<%=LocalUtilis.language("main.menu.customerAffairs",clientLocale)%></h2><!--待处理客户事务-->
	                </div>
	                <div class="widget-content">
					<%	
					List insideList = insideServiceTasksList.getRsList();
					int inside_len = 5<insideList.size()?5:insideList.size();							
					if(inside_len==0){	%>		
						<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.customerAffairs",clientLocale)%> 。</div><!--最近没有待处理客户事务-->
					<%} else {	%>
							<ul>
					<%	for(int i=0; i<inside_len; i++) {
							Map insideMap = (Map)insideList.get(i); %>	
							<li><a href="javascript:loadUrl('<%=insideMap.get("MENU_ID")%>','<%=request.getContextPath()%>'+'<%=insideMap.get("URL")%>'+'&transFlag=3')" class="a2"><font color="red"><%=insideMap.get("ServiceTitle")%></font></a><font color="red">(<%=insideMap.get("CustomerCount")%>)</font></li>
					<%	}%>
						</ul>
					<%}%>
	              	</div>
	            </li>
	        	            
	            <li class="widget" id="widget5" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home5.gif" />&nbsp;<%=LocalUtilis.language("main.menu.myMeetings",clientLocale)%> </h2>
						<!--我的会议-->
	                </div>
	                <div class="widget-content">	             	
						<% 
						List meetList = meetingTaskList.getRsList();
						int m_len = 5<meetList.size()?5:meetList.size();							
						if(m_len==0){ %>		
							<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.myMeetings",clientLocale)%><!--最近没有会议记录-->。</div>
						<%} else { %>
							<ul>	
						<% 	for (int i=0;i<m_len;i++){
								Map meetMap = (Map)meetList.get(i);	%>
								<li><a href="<%=request.getContextPath()%>/marketing/base/meeting_list.jsp?menu_id=30103" class="a2"><font color="red">[<%=Utility.trimNull(meetMap.get("MEETING_TYPE_NAME"))%>]&nbsp;&nbsp;<%=Utility.trimNull(meetMap.get("MEETING_THEME"))%></font></a></li>
						<%	}%>
						 </ul>
						<%}%>
	                </div>
	            </li>
	            
	            <li class="widget" id="widget6" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;<%=LocalUtilis.language("main.menu.myActivity",clientLocale)%> </h2><!--我的活动-->
	                </div>
	                <div class="widget-content">
						<% 
						List activeList_1 = activeList.getRsList();
						int active_len = 5<activeList_1.size()?5:activeList_1.size();							
						if (active_len==0){	%>		
							<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.myActivity",clientLocale)%> 。</div><!--最近没有活动记录-->
						<%}else{%>
							<ul>	
						<% 	for (int i=0; i<active_len; i++) {
								 Map activeMap = (Map)activeList_1.get(i);
								 String start_date = Utility.trimNull(activeMap.get("START_DATE"));									
								 if (start_date.length()>0) start_date = start_date.substring(0,16); %>
								<li><%=start_date%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeMap.get("ACTIVE_THEME"))%></li>
						<%	}%>
						   </ul>
						<%}%>
	               </div>
	            </li>
				<li class="widget" id="widget11" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;<%=LocalUtilis.language("class.subscribeCheck",clientLocale)%> </h2><!--认购、到账审核-->
	                </div>
	                <div class="widget-content"> 
						<% 
						TaskInfoLocal taskInfoLocal=EJBFactory.getTaskInfo();
						TaskInfoVO taskInfoVO=new TaskInfoVO();
						taskInfoVO.setOp_code(input_operatorCode);
						taskInfoVO.setRead_flag(new Integer(346));
						taskInfoVO.setProduct_id(new Integer(0));
						taskInfoVO.setRead_flag1(new Integer(1));
						IPageList taskInfoList=taskInfoLocal.queryOpinfo(taskInfoVO,1,5);
						List taskInfoList1 = taskInfoList.getRsList();
						int taskInfoList_len = 5<taskInfoList1.size()?5:taskInfoList1.size();
						if (taskInfoList_len==0) {%>		
							<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.noSubscribeToCheck",clientLocale)%> 。</div><!--最近没有待审核的认购和到账信息-->
						<%}else{%>
							<ul>
						<% 	for (int i=0; i<taskInfoList_len; i++) {
								 Map taskInfoMap = (Map)taskInfoList1.get(i);
								 String title = Utility.trimNull(taskInfoMap.get("TITLE"));									
								// if(title.length()>61){
								//	title = title.substring(0,60);
								 //}%>
								<li>
									<a href="#" onclick="javascript:urlFundsCheck('<%=title%>','<%=title%>');" class="a2">
										<font color="red"><%=title%></font>
									</a>
								</li>
						<%	}%>
						   </ul>
						<%}%>
	               </div>
	            </li>

					   
	        </ul>

	        <ul id="column3" class="column">
	            <li class="widget" id="widget7" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home7.gif" />&nbsp;<%=LocalUtilis.language("main.menu.auditingPurchaseContract",clientLocale)%> </h2><!--待审核申购合同-->
	                </div>
	                <div class="widget-content">
						<% 
						List applyCheckList = applyreachCheckTaskList.getRsList();
						int appl_len = 5<applyCheckList.size()?5:applyCheckList.size();							
						if (appl_len==0) { %>		
							<div align="left" style="margin-left:20px;margin-top:10px;">
								<%=LocalUtilis.language("main.message.auditingPurchaseContract",clientLocale)%> 。
								<!--最近没有待审核申购合同-->
							</div>
						<%}else{%>
							<ul>	
						<% 	for (int i=0; i<appl_len; i++) {
								Map applyCheckMap = (Map)applyCheckList.get(i);
								Integer apply_serial_no = Utility.parseInt(Utility.trimNull(applyCheckMap.get("SERIAL_NO")),new Integer(0)); %>
								<li>
									<a href="#" onclick="javascript:applyCheckQuery(<%=apply_serial_no%>);" class="a2"><font color="red">
										<%=Utility.trimNull(applyCheckMap.get("CONTRACT_SUB_BH"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("PRODUCT_NAME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("CUST_NAME"))%></font>
									</a>
								</li>
						<%	}%>
							</ul>
						<%}%>
	                </div>
	            </li>
	            
	            <li class="widget" id="widget8" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home8.gif" />&nbsp;<%=LocalUtilis.language("main.main.yourActivityTask",clientLocale)%> </h2><!--待处理活动任务-->
	                </div>
	                <div class="widget-content">
						<% 
						List activeTaskList_1 = activeTaskList.getRsList();
						int activeTask_len = 5<activeTaskList_1.size()?5:activeTaskList_1.size();							
						if(activeTask_len==0){%>		
							<div align="left" style="margin-left:20px;margin-top:10px;">
								<%=LocalUtilis.language("main.message.yourActivityTask",clientLocale)%> 。
								<!--最近没有待处理活动任务-->
							</div>
						<%}else{%>
							<ul>	
						<%	for(int i=0;i<activeTask_len;i++){
								Map activeTaskMap = (Map)activeTaskList_1.get(i); %>
								<li>		
									<a href="#" onclick="javascript:activeTaskDealAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">					
										<%=Utility.trimNull(activeTaskMap.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap.get("ActiveTaskTitle"))%></font>
									</a>
								</li>
						<%	}%>
						</ul>
						<%}%>
	                </div>
	            </li>	   
	         <li class="widget" id="widget9" style="display:none">  

	                <div class="widget-head">
	                    <h2><img src="/images/home9.gif" />&nbsp;<%=LocalUtilis.language("main.menu.auditingActivityTask",clientLocale)%> </h2><!--待审核活动任务-->
	                </div>
	                <div class="widget-content">
					<% 
					List activeTaskList_2 = activeTaskList2.getRsList();
					int activeTask_len2 = 5<activeTaskList_2.size()?5:activeTaskList_2.size();					
					if (activeTask_len2==0) { %>		
						<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.auditingActivityTask",clientLocale)%>。</div><!--最近没有待审核活动任务-->
					<%}else{%>
						<ul>	
					<% 	for (int i=0; i<activeTask_len2; i++){
							Map activeTaskMap2 = (Map)activeTaskList_2.get(i); %>
							<li>	
								<a href="#" onclick="javascript:activeTaskDealCheckAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap2.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">								
									<%=Utility.trimNull(activeTaskMap2.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap2.get("ActiveTaskTitle"))%></font>				
								</a>
							</li>
					<%	}%>
					   </ul>
					<%}%>
	               </div>
	            </li>
				<li class="widget" id="widget12" style="display:none">  
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;<%=LocalUtilis.language("message.CustomerTransferCheck",clientLocale)%> </h2><!--客户移交审核-->
	                </div>
	                <div class="widget-content">    
						<% 
						//客户移交审核用到的变量
						TcustmanagerchangesVO  custManagerChangesVO=new TcustmanagerchangesVO();
						custManagerChangesVO.setSerial_no(new Integer(0));
						custManagerChangesVO.setManagername_before("");
						custManagerChangesVO.setManagername_now("");
						custManagerChangesVO.setCheck_flag(new Integer(1));
						custManagerChangesVO.setInput_man(input_operatorCode);
						custManagerChangesVO.setFlag1(new Integer(1));
						enfo.crm.affair.CustManagerChangesLocal custManagerChangesLocal=EJBFactory.getCustManagerChanges();
						IPageList custManagerChangesList=custManagerChangesLocal.pagelist_query(custManagerChangesVO,totalColumn,1,5);
						List custManagerChangesList1 = custManagerChangesList.getRsList();
						int custManagerChangesList1_len = 5<activeList_1.size()?5:custManagerChangesList1.size();
						if (custManagerChangesList1_len==0) { %>		
							<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.noCustomerTransferToCheck",clientLocale)%> 。</div><!--最近没有待审核的客户移交信息-->
						<%}else{%>
							<ul>	
						<% 	for(int i=0;i<custManagerChangesList1_len;i++){
								 Map custManagerChangesMap = (Map)custManagerChangesList1.get(i);
								 String serial_no = Utility.trimNull(custManagerChangesMap.get("SERIAL_NO"));
								 String manager_before = Utility.trimNull(custManagerChangesMap.get("MANAGERNAME_BEFORE"));
								 String manager_now = Utility.trimNull(custManagerChangesMap.get("MANAGERNAME_NOW")); %>
								<li><a href="#" onclick="javascript:transferCheck('<%=serial_no%>','<%=manager_before%>','<%=manager_now%>');" class="a2"><font color="red"><%=LocalUtilis.language("class.beforeTurnOverManager",clientLocale)%><!--移交前客户经理 -->&nbsp;:&nbsp;<%=manager_before%>&nbsp;-&nbsp;<%=LocalUtilis.language("class.afterTurnOverManager",clientLocale)%><!--移交后客户经理 -->&nbsp;:&nbsp;<%=manager_now%></font></a></li>
						<%	}%>                                    
						   </ul>
						<%}%>
	               </div>
	            </li>

				<li class="widget" id="widget16" style="display:none"> 
					<!-- 2012-04-24 交银由于要让领导查处页面，在首页显示，而替换此功能。暂时  -->
					<div class="widget-head">
		                    <h2><img src="/images/home6.gif" />&nbsp;产品销售统计分析</h2>
		            </div>
		            <div class="widget-content">  
						<div align="left" style="margin-left:20px;margin-top:10px;">
						<a href="#" onclick="javascript:product_delation();" class="a2">
							<font color="red">产品销售统计分析明细</font>
						</a>
						</div>
					</div>
				</li>
				
				<li class="widget" id="widget15" style="display:none"> 
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;近一周团队销售统计(单位:万元)</h2>
	                </div>
	                <div class="widget-content">    
					<% 
					PreContractCrmLocal preContract_total = EJBFactory.getPreContractCrm(); 
					PreContractCrmVO pre_total_vo = new PreContractCrmVO();
					pre_total_vo.setQuery_date(new Integer(Utility.getCurrentDate()));
					pre_total_vo.setInput_man(input_operatorCode);
					List preContract_total_list =  preContract_total.getProductPreTotal(pre_total_vo);
					if (preContract_total_list.size()==0) { %>		
						<div align="left" style="margin-left:20px;margin-top:10px;">暂无销售统计信息</div> 
					<%}else{
						String[] xValue = new String[preContract_total_list.size()];
						String[] ytemName = new String[preContract_total_list.size()];
						for (int i=0; i<preContract_total_list.size(); i++) {	
							Map map = (Map)preContract_total_list.get(i);
							ytemName[i] = Utility.trimNull(map.get("TEAM_NAME"));
							xValue[i] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0)).divide(new BigDecimal(10000),2));
						}
						FusionCharts Chart = new FusionCharts();
						FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
						
						String XMLStr2 = ChartCreator.generateBar2D(ytemName,xValue,"","","");							 
						String chartHTMLCode2 = Chart.createChartHTML(request.getContextPath()+"/includes/charts/Bar2D.swf","",XMLStr2,"",315,135,false);	
					%>
						<%=chartHTMLCode2%>
					<%}%>                                    
	               </div>
	            </li>
				<!-- 新增合同处理消息开始 -->
				<% List list = contractM.queryContractStatResult(input_operatorCode); %>
				<li class="widget" id="widget17" style="display:<%if(list.size()==0) out.print("none"); %>"> 
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;系统消息</h2>
	                </div>
	                <div class="widget-content">    
					<%if (list.size()==0){ %>
						<div align="left" style="margin-left:20px;margin-top:10px;">无消息</div> 
					<%}else{%>
						<ul>	
					<% 	for (int i=0; i<list.size(); i++) {
							Map contractStatM = (Map)list.get(i); %>
						<li> <font color="red"><%=contractStatM.get("PRODUCT_NAME")%>&nbsp;：&nbsp;处理合同数：<%=contractStatM.get("CONTRACT_NUMBER") %>&nbsp;&nbsp;</font></li>
					<%	}%>                                    
					   </ul>
					<%}%>
	               </div>
	            </li>
				<!-- 新增合同处理消息结束 -->
<%
	int daysBeforeDue = Argument.getValueOfTSysControlByFlagType("REMIND_DAYS_BEFORE_PRODUCT_END", "-1");
	if (daysBeforeDue>=0) {  %>
				<li class="widget" id="widget18" style="display:none"> 
	                <div class="widget-head">
	                    <h2><img src="/images/home6.gif" />&nbsp;将到期产品</h2>
	                </div>
	                <div class="widget-content">    
					<% 
					ProductVO pvo = new ProductVO();
					pvo.setService_man(input_operatorCode);
					pvo.setDaysBeforeDue(new Integer(daysBeforeDue));
					pvo.setInput_man(input_operatorCode);
					List dueProductList =  productLocal.queryDueProduct(pvo);
					if (dueProductList.size()==0) { %>		
						<div align="left" style="margin-left:20px;margin-top:10px;">没有将到期产品</div> 
					<%} else {%>
							<ul>
					<%	for (int i=0; i<dueProductList.size(); i++) {	
							Map map = (Map)dueProductList.get(i);
							String custName = (String)map.get("CUST_NAME");
							String productName = (String)map.get("PRODUCT_NAME");
							Integer endDate = (Integer)map.get("END_DATE"); %>
								<li><%=custName + " - " + productName + " - " + Format.formatDateLine(endDate)%></li>
					<%}%>
							</ul>
				   <%}%>                                
	               </div>
	            </li>
<%	} %>
			</ul>
			
	    </div>
	    <div id="setMenu">
			<button class="xpbutton2" id="btnSave" onclick="javascript:showDialog();"> <%=LocalUtilis.language("index.msg.set",clientLocale)%> </button><!--设置-->
		</div>
	</div>

<%if (user_id.intValue()!=22/*中投*/) { %>
	<div id="main-callcenter"style="display:none">
		<div id="main-callcenter-accordion">
			<div>
				<h3><a href="#">CallCenter</a></h3>
				<div>
					<!--拨打电话-->
					<a id="callLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/call.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.callTel",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<!--多方会议-->
					<a id="meettingLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/meetting.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.manyMeetings",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<!--单击离开-->
					<a id="leavingLink" href="#"><img src="<%=request.getContextPath()%>/images/callcenter/back.jpg" width="78" height="78" title="<%=LocalUtilis.language("index.callcenter.clickToLeave",clientLocale)%> "/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
			</div>
		</div>
	</div>
<%} %>
</div>

<div id="setContent" style="display:none;">
	<table cellspacing="2" cellpadding="2" width="500px">
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget99" class="selectAllBox" value="widget99">
				&nbsp;&nbsp;在售产品预约情况
			</td>
		<%if(Argument.getSyscontrolValue("FLOW_CONTROL") == 1){%>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget98" class="selectAllBox" value="widget98"/>
				&nbsp;&nbsp;事务流程
			</td>			
		<%} else { %>
			<td align="left">&nbsp;&nbsp;</td>
		<%} %>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_intro" class="selectAllBox" value="intro"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.mySchedule",clientLocale)%> <!--我的日程-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget20" class="selectAllBox" value="widget20"/>&nbsp;&nbsp; 我的消息
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget2" class="selectAllBox" value="widget2"/>
				&nbsp;&nbsp; <%=LocalUtilis.language("main.menu.promotionProduct",clientLocale)%> <!--最新推介期产品-->
			</td>
			<td align="left" colspan="2"><!-- 团队预约信息统计 -->
				<input type="checkbox" name="workbenchOptions" id="workbench_widget15" class="selectAllBox" value="widget15">
				&nbsp;&nbsp;近一周团队销售统计
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget4" class="selectAllBox" value="widget4"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.customerAffairs",clientLocale)%> <!--待处理客户事务-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget5" class="selectAllBox" value="widget5"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.myMeetings",clientLocale)%> <!--我的会议-->
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget6" class="selectAllBox" value="widget6"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.myActivity",clientLocale)%> <!--我的活动-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget7" class="selectAllBox" value="widget7"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.auditingPurchaseContract",clientLocale)%> <!--待审核申购合同-->
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget8" class="selectAllBox" value="widget8"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.main.yourActivityTask",clientLocale)%> <!--待处理活动任务-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget9" class="selectAllBox" value="widget9"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.auditingActivityTask",clientLocale)%> <!--待审核活动任务-->
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget10" class="selectAllBox" value="widget10"/>&nbsp;&nbsp;
				<%=LocalUtilis.language("message.channels",clientLocale)%><%=LocalUtilis.language("message.servicePlans",clientLocale)%> <!--渠道维护计划-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget11" class="selectAllBox" value="widget11">
				&nbsp;&nbsp;<%=LocalUtilis.language("class.subscribeCheck",clientLocale)%> <!--认购、到账审核-->
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget12" class="selectAllBox" value="widget12"/>&nbsp;&nbsp;
				<%=LocalUtilis.language("message.CustomerTransferCheck",clientLocale)%><!--客户移交审核-->
			</td>
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget14" class="selectAllBox" value="widget14">
				&nbsp;&nbsp;收款计划提醒 
			</td>
		</tr>
		<%if(user_id.intValue() != 2 ) {%>
		<tr bgcolor="#FFFFFF">			
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget3" class="selectAllBox" value="widget3"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("class.productMarketing",clientLocale)%> <!--在售产品销售统计-->
			</td>
			<td align="left">&nbsp;&nbsp;</td>
		</tr>
		<%} %>
		<%if(Argument.getSyscontrolValue("DT_eTrust")!=0){ %>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget13" class="selectAllBox" value="widget13"/>
				&nbsp;&nbsp;<%=LocalUtilis.language("main.menu.selfAffairs",clientLocale)%> <!--待处理自助事务-->
			</td>
			<td align="left">&nbsp;&nbsp;</td>
		</tr>
		<%}%>
		<%if(user_id.intValue() == 8 ) {%>
		<tr bgcolor="#FFFFFF">			
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget16" class="selectAllBox" value="widget16"/>
				&nbsp;&nbsp;产品销售统计分析 <!--在售产品销售统计-->
			</td>
			<td align="left">&nbsp;&nbsp;</td>		
		</tr>
		<%} %>
		<%if(Argument.getValueOfTSysControlByFlagType("REMIND_DAYS_BEFORE_PRODUCT_END", "-1")>=0){ %>
		<tr bgcolor="#FFFFFF">
			<td align="left">
				<input type="checkbox" name="workbenchOptions" id="workbench_widget18" class="selectAllBox" value="widget18"/>
				&nbsp;&nbsp;将到期产品
			</td>
			<td align="left">&nbsp;&nbsp;</td>
		</tr>
		<%}%>
	</table>
	<div style="float:right;margin-top:5px;margin-bottom:5px;margin-right:25px;">
		<button class="xpbutton2" id="btnSave" onclick="javascript:setAction();"><%=LocalUtilis.language("message.save",clientLocale)%><!--保存--></button>	
	</div>
</div>

</body>
</html>
<%
local.remove();
productLocal.remove();
meetingLocal.remove();
applyLoal.remove();
activityLocal.remove();
activityTaskLocal.remove();
channel_local.remove();
taskInfoLocal.remove();
custManagerChangesLocal.remove();
tcoMoneyTaskLocal.remove();
preContract.remove();
contractM.remove();
%>