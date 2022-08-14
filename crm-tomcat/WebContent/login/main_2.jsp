<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//该页面是演示页面
int display_flag=Utility.parseInt(Utility.trimNull(request.getParameter("display_flag")),0);
Integer begin_date = new Integer(Utility.getCurrentDate());
Integer end_date = new Integer(Utility.getCurrentDate());
Integer LOG0001=(Integer)session.getAttribute("LOG0001");
String[] totalColumn = null;
//声明对象
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();
ApplyreachLocal applyLoal = EJBFactory.getApplyreach();
ApplyreachVO appl_vo = new ApplyreachVO();
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO active_vo = new ActivityVO();
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO activityTask_vo = new ActivityTaskVO();
ActivityTaskVO activityTask_vo2 = new ActivityTaskVO();
//输入查询参数
vo.setOp_code(input_operatorCode);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
IPageList insideServiceTasksList = local.listInsideServiceTasks(vo,1,10);
//在售产品预约信息
pre_vo.setStatus("110202");
pre_vo.setInput_man(input_operatorCode);
List preList  = preContract.getProductPreList(pre_vo);
//待审核
appl_vo.setBook_code(new Integer(1));
appl_vo.setCheck_flag(new Integer(1));
appl_vo.setInput_man(input_operatorCode);
IPageList applyreachCheckTaskList = applyLoal.listAll(appl_vo,totalColumn,1,5);
//待处理营销活动
active_vo.setManage_code(input_operatorCode);
active_vo.setActive_flag(new Integer(0));
IPageList activeList = activityLocal.pageList_query(active_vo,totalColumn,1,5);

activityTask_vo.setExecutor(input_operatorCode);
activityTask_vo.setTaskFlag(new Integer(1));
IPageList activeTaskList = activityTaskLocal.pageList_query(activityTask_vo,totalColumn,1,5);

activityTask_vo2.setManagerCode(input_operatorCode);
activityTask_vo2.setTaskFlag(new Integer(2));
IPageList activeTaskList2 = activityTaskLocal.pageList_query(activityTask_vo2,totalColumn,1,5);
//即将到期客户
IPageList pageList = local.listCustDue(input_operatorCode,1,5);
List ExpireCustomerList = pageList.getRsList();
%>

<html>
<head>
<title><%=LocalUtilis.language("main.menu.myDesk",clientLocale)%> </title><!--首页工作台-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html;charset=GBK" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/loginService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/menuService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext2.0/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/ext-all.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/Portal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/PortalColumn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext2.0/protal/Portlet.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext2.0/protal/portal.css" />

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
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
	.listclass{
		background-color:#FFFFFF;
		border: 1px solid #A9CEF7;
	}
	.listclass li
	{
		color:#999999;
		text-align:left;
		background-image:url(../images/array.gif);
		background-position:10px 10px;
		background-repeat:no-repeat;
		padding:5px 0px 0px 25px;
		background-color:#FFFFFF;
		font-size:12px;
		font-family:Verdana, Arial, Helvetica, sans-serif;
		font-style:normal;
		font-weight:normal;
		line-height:15px;
		padding-bottom:5px;
	
	}
</style>
<script language="javascript">
function showPrpductPre(){
	var url = "<%=request.getContextPath()%>/marketing/sell/product_pre_total.jsp";
	window.location.href = url;
}
//直接预约
function reservAddInfo(pre_product_id){
	location = "<%=request.getContextPath()%>/marketing/sell/direct_reserve_add.jsp?pre_product_id="+pre_product_id;
}
function showProductInfo(preproduct_id){
	var url = "<%=request.getContextPath()%>/wiki/product_info_view.jsp?preproduct_id="+preproduct_id;
	window.open(url);
}	
function loadUrl(menu_id,url){
	loginService.alertString(menu_id,'<%=LOG0001.toString()%>',<%=input_operatorCode%>,{callback: function(data){location=url;}});
}
//查看产品预约统计信息
function showTeamInfo(pre_product_id, pre_product_name, pre_money){
	showModalDialog('<%=request.getContextPath()%>/marketing/sell/product_pre_total_team.jsp?pre_product_id=' + pre_product_id + '&pre_product_name=' + pre_product_name + '&pre_money=' + pre_money, '', 'dialogWidth:1000px;dialogHeight:450px;status:0;help:0')
}
function callAaction(tel){
	if(sl_confirm("确定要拨打电话")){//您要拨打的电话号码是
		document.getElementById("phoneNumber").value = tel;
		var url = "<%=request.getContextPath()%>/callcenter/callingRecord.jsp";
		document.theform.action = url;
		document.theform.submit();
	}
}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0"	marginheight="0">
<form name="theform" method="POST" >
<input type="hidden" name="display_flag" id="display_flag" value="<%=display_flag%>" />
<input type="hidden" name="phoneNumber" id="phoneNumber" value='' />
	<div id="context_1">
		 <%	
			List insideList = insideServiceTasksList.getRsList();
			Map insideMap = null;
			int inside_len = 5<insideList.size()?5:insideList.size();
			
			if(inside_len==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.customerAffairs",clientLocale)%> 。</div>
			<!--最近没有待处理客户事务-->
		<% } else{%>
			<ul class="listclass">
			<% for(int i=0;i<inside_len;i++){
					insideMap = (Map)insideList.get(i);
			%>	
				<li><a href="javascript:loadUrl('<%=insideMap.get("MENU_ID")%>','<%=request.getContextPath()%>'+'<%=insideMap.get("URL")%>')" class="a2"><font color="red"><%=insideMap.get("ServiceTitle")%></font></a><font color="red">(<%=insideMap.get("CustomerCount")%>)</font></li>
				<%}%>
			</ul>
		<%}%>	
	</div>
	<div id="context_2">
		<%			 
			Map preMap = null;
			int pre_len = 5<preList.size()?5:preList.size();
			if(pre_len ==0){
		%>
			<div align="left" style="margin-left:20px;margin-top:10px;">没有在售产品预约信息 。</div>
		<% }else{%>
			<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
				<tr class="tr0" bgcolor="#E0EEEE">
					<td align="center" width="*">产品名称</td>
					<td align="center" width="*">发行规模</td>
					<td align="center" width="*">预约开始时间</td>
					<td align="center" width="*">预约截止时间</td>
					<td align="center" width="*">发行方式</td>
					<td align="center" width="*">已预约规模</td>
					<td align="center" width="*">到账规模</td>
					<td align="center" width="*">小额份数</td>
					<td align="center" width="*">剩余预约规模</td>
					<td align="center" width="*">大额预约(有效数/总数)</td>
					<td align="center" width="*">操作</td>
				</tr>
		<%
			for(int i=0;i<pre_len;i++){
				preMap = (Map)preList.get(i);
				Integer preproduct_id = Utility.parseInt(Utility.trimNull(preMap.get("PREPRODUCT_ID")),new Integer(0));
				double pre_max_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_MONEY")),new BigDecimal(0),2,"1").doubleValue();
				double pre_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_FACT_MONEY")),new BigDecimal(0),2,"1").doubleValue();
				double rg_money = Utility.parseDecimal(Utility.trimNull(preMap.get("RG_MONEY")),new BigDecimal(0),2,"1").doubleValue();
				double available_money = pre_max_money - pre_money;
				int direct_sale = Utility.parseInt(Utility.trimNull(preMap.get("DIRECT_SALE")),0);
				BigDecimal max_ratio = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(preMap.get("MAX_RATIO"))), new BigDecimal(0)).setScale(6, BigDecimal.ROUND_HALF_UP);
				BigDecimal stand_ratio = new BigDecimal(0.800000);
		%>
				<tr class="tr1">
					<td align="center" width="*"><a href="#" class="a2" title="查看产品信息" onclick="javascript:showProductInfo('<%=preproduct_id%>');" ><%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%></a></td>
					<td align="right" width="*"><%=Format.formatMoney(pre_max_money)%></td>
					<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_START_DATE")),new Integer(0)))%></td>
					<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_END_DATE")),new Integer(0)))%></td>
					<td align="center" width="*"><%if(direct_sale == 1) out.print("代销");else if(direct_sale == 2) out.print("直销");else if(direct_sale == 3) out.print("直销&代销");%></td>
					<td align="right" width="*"><a href="#" class="a2" title="查看产品预约统计信息" onclick="javascript:showTeamInfo('<%=preproduct_id%>','<%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%>','<%=Utility.trimNull(Format.formatMoney(pre_money))%>');" ><font color="<%=max_ratio.compareTo(stand_ratio) == 1 ? "red" : "" %>"><%=Format.formatMoney(pre_money)%></font></a></td>
					<td align="right" width="*"><%=Format.formatMoney(rg_money)%></td>
					<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("LESS300_NUMBER")),new Integer(0))%></td>
					<td align="right" width="*"><%=Format.formatMoney(available_money)%></td>
					<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_VALID_COUNT")),0)%> / <%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_COUNT")),0)%></td>
					<td align="center" width="*">
       					<a href="#" class="a2" onclick="javascript:reservAddInfo('<%=preproduct_id %>');" title="产品预约">预约</a>
					</td>
				</tr> 
		<%}%>
	</table>
	<%}%>
		<div align="right"><a href="#" class="a2" onclick="javascript:window.location.reload();">刷新</a> | <a href="#" class="a2"	 onclick="javascript:showPrpductPre();">更多>></a></div>
	</div>
	<div id="context_3">	
		<img src="<%=request.getContextPath()%>/images/yanshi/cunliang2.png" width="100%" height="200px" title="存量客户信息统计 "/>
	</div>
	<div id="context_4">
		 <table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">客户名称</td>
				<td align="center" width="*">存量金额</td>
				<td align="center" width="*">到期产品</td>
				<td align="center" width="*">到期日期</td>
				<td align="center" width="*">累计认购金额</td>
			</tr>
		<%
           int custmer_len = 5<ExpireCustomerList.size()?5:ExpireCustomerList.size();		
			for(int cu=0;cu<custmer_len;cu++){
                Map map1 = (Map)ExpireCustomerList.get(cu);
				//customerVo = (customerVo)ExpireCustomerList.get(i);
				String cust_name=String.valueOf(Utility.trimNull(map1.get("CUST_NAME")));
				//String cust_name = customerVo.getCust_name();//客户名称
				double ben_amount = Utility.parseDecimal(Utility.trimNull(map1.get("BEN_AMOUNT")),new BigDecimal(0),2,"1").doubleValue();//存量金额
              //double ben_amount = Utility.parseDecimal(Utility.trimNull(customerVo.getBen_amount()),new BigDecimal(0),2,"1").doubleValue();//存量金额
                String product_name=String.valueOf(Utility.trimNull(map1.get("PRODUCT_NAME")));
				String ben_end_date=String.valueOf(Utility.trimNull(map1.get("EN_END_DATE")));
//              String product_name = customerVo.getLast_product_name();//到期产品
               // String ben_end_date = customerVo;//到期日期
				double rg_money = Utility.parseDecimal(Utility.trimNull(map1.get("RG_MONEY")),new BigDecimal(0),2,"1").doubleValue();//累计认购金额

		%>
	       <tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*"><%=cust_name%></td>
				<td align="center" width="*"><%=Format.formatMoney(ben_amount)%></td>
				<td align="center" width="*"><%=product_name%></td>
				<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(ben_end_date,new Integer(0)))%></td>
				<td align="center" width="*"><%=Format.formatMoney(rg_money)%></td>
			</tr>
			<%} %>
		 </table>
	</div>
	<div id="context_5">
		<div style="width:65%;float:left">
		 <table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">排名</td>
				<td align="center" width="*">团队名称</td>
				<td align="center" width="*">团队负责人</td>
				<td align="center" width="*">本年累计</td>
				<td align="center" width="*">本月累计</td>
				<td align="center" width="*">绩效得分</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">1</td>
				<td align="center" width="*">销售团队1</td>
				<td align="center" width="*">高环</td>
				<td align="center" width="*">1500000000</td>
				<td align="center" width="*">100000000</td>
				<td align="center" width="*">90</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">2</td>
				<td align="center" width="*">销售团队2</td>
				<td align="center" width="*">关纯民</td>
				<td align="center" width="*">100000000</td>
				<td align="center" width="*">80000000</td>
				<td align="center" width="*">85</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">3</td>
				<td align="center" width="*">销售团队3</td>
				<td align="center" width="*">崔贵重</td>
				<td align="center" width="*">800000000</td>
				<td align="center" width="*">60000000</td>
				<td align="center" width="*">80</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">4</td>
				<td align="center" width="*">销售团队4</td>
				<td align="center" width="*">刘洋</td>
				<td align="center" width="*">500000000</td>
				<td align="center" width="*">50000000</td>
				<td align="center" width="*">70</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">5</td>
				<td align="center" width="*">销售团队5</td>
				<td align="center" width="*">姚红略</td>
				<td align="center" width="*">400000000</td>
				<td align="center" width="*">40000000</td>
				<td align="center" width="*">65</td>
			</tr>
		 </table>
		</div>
		<DIV style="width:34%;float:right">
			<img src="<%=request.getContextPath()%>/images/yanshi/team.png" width="100%"   title="团队饼图 "/>
		</DIV>
	</div>
	<div id="context_6">
		<table id="table3" border="1" cellspacing="1" cellpadding="3" class="tableProduct" width="100%" >
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">序号</td>
				<td align="center" width="*">客户名称</td>
				<td align="center" width="*">来电号码</td>
				<td align="center" width="*">拨打时间</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*">回拨</td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">1</td>
				<td align="center" width="*">未知</td>
				<td align="center" width="*">136******11</td>
				<td align="center" width="*">2012年7月1日 10:10</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*"><a href="javascript:callAaction('13675810931');"><img src="/images/icon-phone.gif"/></a></td>
			</tr>
				<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">2</td>
				<td align="center" width="*">高环</td>
				<td align="center" width="*">135******11</td>
				<td align="center" width="*">2012年7月1日 10:15</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*"><a href="javascript:callAaction('13675810931');"><img src="/images/icon-phone.gif"/></a></td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">3</td>
				<td align="center" width="*">未知</td>
				<td align="center" width="*">138******11</td>
				<td align="center" width="*">2012年7月1日 10:20</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*"><a href="javascript:callAaction('13675810931');"><img src="/images/icon-phone.gif"/></a></td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">4</td>
				<td align="center" width="*">崔贵重</td>
				<td align="center" width="*">139******21</td>
				<td align="center" width="*">2012年7月1日 10:25</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*"><a href="javascript:callAaction('13675810931');"><img src="/images/icon-phone.gif"/></a></td>
			</tr>
			<tr class="tr0" bgcolor="#E0EEEE">
				<td align="center" width="*">5</td>
				<td align="center" width="*">未知</td>
				<td align="center" width="*">158******33</td>
				<td align="center" width="*">2012年7月1日 10:30</td>
				<td align="center" width="*">...</td>
				<td align="center" width="*"><a href="javascript:callAaction('13675810931');"><img src="/images/icon-phone.gif"/></a></td>
			</tr>
		</table>
	</div>
	<div id="context_7">
		<% 
			List applyCheckList = applyreachCheckTaskList.getRsList();
			Map applyCheckMap = null;
			Integer apply_serial_no = new Integer(0);
			int appl_len = 5<applyCheckList.size()?5:applyCheckList.size();
				
			if(appl_len==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;">
				<%=LocalUtilis.language("main.message.auditingPurchaseContract",clientLocale)%> 。
				<!--最近没有待审核申购合同-->
			</div>
		<%  }
			else{
		%>
			<ul>	
		<%	
			for(int i=0;i<appl_len;i++){
				applyCheckMap = (Map)applyCheckList.get(i);
				apply_serial_no = Utility.parseInt(Utility.trimNull(applyCheckMap.get("SERIAL_NO")),new Integer(0));
		%>
				<li>
					<a href="#" onclick="javascript:applyCheckQuery(<%=apply_serial_no%>);" class="a2"><font color="red">
						[待审核申购合同]<%=Utility.trimNull(applyCheckMap.get("CONTRACT_SUB_BH"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("PRODUCT_NAME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(applyCheckMap.get("CUST_NAME"))%></font>
					</a>
				</li>
			<%}%>
			</ul>
		<%}%>
		<% 
			TaskInfoLocal taskInfoLocal=EJBFactory.getTaskInfo();
			TaskInfoVO taskInfoVO=new TaskInfoVO();
			taskInfoVO.setOp_code(input_operatorCode);
			taskInfoVO.setRead_flag(new Integer(346));
			taskInfoVO.setProduct_id(new Integer(0));
			taskInfoVO.setRead_flag1(new Integer(1));
			IPageList taskInfoList=taskInfoLocal.queryOpinfo(taskInfoVO,1,5);
			List taskInfoList1 = taskInfoList.getRsList();
			Map taskInfoMap = null;
			int taskInfoList_len = 5<taskInfoList1.size()?5:taskInfoList1.size();
			String title="";	
			if(taskInfoList_len==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.noSubscribeToCheck",clientLocale)%> 。</div><!--最近没有待审核的认购和到账信息-->
		<%  }
			else{
		%>
			<ul>	
		<%								
			  for(int i=0;i<taskInfoList_len;i++){
				 taskInfoMap = (Map)taskInfoList1.get(i);
				 title = Utility.trimNull(taskInfoMap.get("TITLE"));
					
				// if(title.length()>61){
				//	title = title.substring(0,60);
				 //}	
		%>
				<li>
					[认购、到账审核]<a href="#" onclick="javascript:urlFundsCheck('<%=title%>','<%=title%>');" class="a2">
						<font color="red"><%=title%></font>
					</a>
				</li>
			<%}%>
		   </ul>
		<%}%>
	</div>
	<div id="context_8">
		<% 
			List activeList_1 = activeList.getRsList();
			Map activeMap = null;
			String start_date = "";
			int active_len = 5<activeList_1.size()?5:activeList_1.size();
				
			if(active_len==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.myActivity",clientLocale)%> 。</div><!--最近没有活动记录-->
		<%  }
			else{
		%>
			<ul>	
		<%								
			  for(int i=0;i<active_len;i++){
				 activeMap = (Map)activeList_1.get(i);
				 start_date = Utility.trimNull(activeMap.get("START_DATE"));
					
				 if(start_date.length()>0){
					start_date = start_date.substring(0,16);
				 }	
		%>
				<li>[我的活动]<%=start_date%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeMap.get("ACTIVE_THEME"))%></li>
			<%}%>
		   </ul>
		<%}%>
		<% 
			List activeTaskList_1 = activeTaskList.getRsList();
			Map activeTaskMap = null;
			int activeTask_len = 5<activeTaskList_1.size()?5:activeTaskList_1.size();
			
			if(activeTask_len==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;">
				<%=LocalUtilis.language("main.message.yourActivityTask",clientLocale)%> 。
				<!--最近没有待处理活动任务-->
			</div>
		<%  }
			else{
		%>
			<ul>	
		<%			
			for(int i=0;i<activeTask_len;i++){
				activeTaskMap = (Map)activeTaskList_1.get(i);
		%>
				<li>		
					[待处理活动任务]<a href="#" onclick="javascript:activeTaskDealAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">					
						<%=Utility.trimNull(activeTaskMap.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap.get("ActiveTaskTitle"))%></font>
					</a>
				</li>
			<%}%>
		</ul>
		<%}%>
		<% 
			List activeTaskList_2 = activeTaskList2.getRsList();
			Map activeTaskMap2 = null;
			int activeTask_len2 = 5<activeTaskList_2.size()?5:activeTaskList_2.size();
			
			if(activeTask_len2==0){
		%>		
			<div align="left" style="margin-left:20px;margin-top:10px;"><%=LocalUtilis.language("main.message.auditingActivityTask",clientLocale)%> 。</div>
			<!--最近没有待审核活动任务-->
		<%  }
			else{
		%>
			<ul>	
		<%		
			for(int i=0;i<activeTask_len2;i++){
				activeTaskMap2 = (Map)activeTaskList_2.get(i);
		%>
				[待审核活动任务]<li>	
					<a href="#" onclick="javascript:activeTaskDealCheckAction(<%=Utility.parseInt(Utility.trimNull(activeTaskMap2.get("Serial_no")),new Integer(0))%>);" class="a2"><font color="red">								
						<%=Utility.trimNull(activeTaskMap2.get("ACTIVE_THEME"))%>&nbsp;&nbsp;-&nbsp;&nbsp;<%=Utility.trimNull(activeTaskMap2.get("ActiveTaskTitle"))%></font>				
					</a>
				</li>
			<%}%>
		   </ul>
		<%}%>
	</div>
</form>


<script language="javascript">
	Ext.onReady(function(){	
	    var tools = [{
	        id:'gear',
	        handler: function(){
	            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
	        }
	    },{
	        id:'close',
	        handler: function(e, target, panel){
	            panel.ownerCt.remove(panel, true);
	        }
	    }];

		var top=new Ext.BoxComponent({
		// el属性就是外面所对应的DIV
		        el:"context_2",
		        height:200
		    });


	    var viewport = new Ext.Viewport({
	        layout:'border',
	        items:[{
					region:"north",					
					title:"在售产品预约情况",
					margins:'0 0 0 0',
				 	items:[top],
					height:100
				}, {
	            xtype:'portal',
	            region:'center',
	            margins:'0 0 0 0',
	            items:[{
	                columnWidth:.66,
	                style:'padding:5px 0 10px 10px',
	                items:[{
	                    title: '即将到期客户',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_4"))
	                },{
	                    title: ' ',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_3"))
	                },{
	                    title: '销售团队统计',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_5"))
	                }]
	            },{
	                columnWidth:.33,
	                style:'padding:5px 0 10px 10px',
					items:[{
	                    title: '待处理客户事务',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_1"))
	                },{
	                    title: '来电未处理',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_6"))
	                },{
	                    title: '待处理营销任务',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_7"))
	                },{
	                    title: '待处理营销活动',
	                    layout:'fit',
	                    tools: tools,
	                    contentEl:Ext.get(document.getElementById("context_8"))
	                }]
				}]
	        }]
	    });
	});
</script>
</body>
</html>