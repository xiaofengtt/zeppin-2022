<%@ page contentType="text/html; charset=GBK" import="java.math.*,java.text.*,java.util.*,enfo.crm.cash.*,org.jfree.data.category.*,enfo.crm.tools.*,enfo.crm.cash.Format"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	//获取url中的值
	Integer endDate = Utility.parseInt(request.getParameter("endDate"),new Integer(0));//结束日期（选择的日期）
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//客户ID
	Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//产品ID
	Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//子产品ID
	String benAccount = Utility.trimNull(request.getParameter("benAccount"));//收益账号
	Integer beginDate = new Integer(0);//开始日期
	//
	String qcustNo = Utility.trimNull(request.getParameter("qcustNo"));
	String qcustName = Utility.trimNull(request.getParameter("qcustName"));
	String qcardId = Utility.trimNull(request.getParameter("qcardId"));//证件号码
	Integer qcustType = Utility.parseInt(request.getParameter("qcustType"), new Integer(0));
	Integer qserviceMan = Utility.parseInt(request.getParameter("qserviceMan"),new Integer(0));
	String qcustTel = Utility.trimNull(request.getParameter("qcustTel"));
	String qaddress = Utility.trimNull(request.getParameter("qaddress"));
	String qUrl = "?custNo=" + qcustNo + "&custName=" + qcustName + "&custType=" + qcustType +  "&serviceMan=" + qserviceMan + "&custTel=" + qcustTel + "&cardId=" + qcardId + "&address=" + qaddress;
	//曲线信息
	String chartTitle = "";//曲线标题
	String series = "七日年化收益率";//曲线名称
	String x = "";
	String y = "";
	DefaultCategoryDataset linedataset = new DefaultCategoryDataset();//曲线数据
	String hqDate = "";//曲线横轴日期
	BigDecimal share1wGain = null;//每万份收益
	BigDecimal day7yiled= null;//七日年化收益率
	BigDecimal benGain = null;
	BigDecimal totalGain = null;
	BigDecimal currentBen = null;
	BigDecimal totalBenGain = null;
	DecimalFormat df1 = new DecimalFormat("######0.0000");
	DecimalFormat df2 = new DecimalFormat("######0.00");

	CashBean cash = new CashBean();
	CashVo vo = new CashVo();

	if(endDate == null || endDate.intValue() ==0){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		endDate = new Integer(sdf.format(calendar.getTime()));
	}
	vo.setBeginDate(beginDate);
	vo.setCustId(custId);
	vo.setEndDate(endDate);
	vo.setInputMan(input_operatorCode);
	vo.setProductId(productId);
	vo.setSubProductId(subProductId);
	vo.setBenAccount(benAccount);
	List list = cash.CustFundYield(vo);
	Map map = null;
	List acctList = cash.getCustBank(vo);
	Map acctMap = null;
	BigDecimal maxDay7yiled = null;
	BigDecimal minDay7yiled = null;
	//echarts
	String[] categories = new String[10];
	BigDecimal[] values = new BigDecimal[10];

	if(list != null && list.size() != 0){
		for(int i = 0; i<list.size(); i++){
			map = (Map)list.get(i);
			hqDate = Utility.trimNull(map.get("HQ_DATE")); 
			hqDate = Format.formatDate(Integer.parseInt(hqDate)); 
			benGain = Utility.parseDecimal(Utility.trimNull(map.get("BEN_GAIN")), new BigDecimal(0.00),2,"1");
			totalGain = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_GAIN")), new BigDecimal(0.00),2,"1");
			currentBen = Utility.parseDecimal(Utility.trimNull(map.get("CURR_BEN")), new BigDecimal(0.00),2,"1");
			totalBenGain = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_BEN_GAIN")), new BigDecimal(0.00),2,"1");
			share1wGain = Utility.parseDecimal(Utility.trimNull(map.get("SHARE1W_GAIN")), new BigDecimal(0.00),10,"1");
			day7yiled = Utility.parseDecimal(Utility.trimNull(map.get("DAY7_YIELD")), new BigDecimal(0.00),10,"1").multiply(new BigDecimal(100));

			linedataset.addValue(day7yiled,series,hqDate);

			//echarts
			categories[i] = hqDate;
			values[i] = day7yiled;
		}
	}
	String filename = WebChart.createLineChart(chartTitle,x,y,linedataset,request);
	String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>

		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/cash/dist/echarts.js"></script>
		<script type="text/javascript">
			
			function queryHistoryDate(){
				var endDate = document.getElementById("endDate").value;
   				if(endDate==""){
					alert("未选择日期");
				}else{
					document.theform.submit();
				}
			}
	
			function selectBenAccount(){
				document.theform.submit();
			}

			function goback() {
				location = "cash_cust_choose.jsp" + document.theform.qUrl.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="post" action="cash_cust_yield.jsp">
			<input type="hidden" name="custId" value="<%=custId %>"/>
			<input type="hidden" name="qcustNo" value="<%=qcustNo %>"/>
			<input type="hidden" name="qcustName" value="<%=qcustName %>"/>
			<input type="hidden" name="qcustType" value="<%=qcustType %>"/>
			<input type="hidden" name="qserviceMan" value="<%=qserviceMan %>"/>
			<input type="hidden" name="qcustTel" value="<%=qcustTel %>"/>
			<input type="hidden" name="qcardId" value="<%=qcardId %>"/>
			<input type="hidden" name="qUrl" value="<%=qUrl%>" />
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:goback();">返回 (<u>B</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>
<!--
			<div style="width: 850px;margin-left: 120px;">
				<div align="center" style="font-size: 50px;">现金聚利1期</div>
				<div align="right" style="font-size: 35px;">七日年化收益率走势图(%)</div>
				<div align="right" style="font-size: 15px;">
					选择受益账户:
					<select name="benAccount" id="benAccount" onchange="selectBenAccount()">
						<option value=" ">--请选择--</option>
						<%for(int i = 0; i < acctList.size(); i++){ 
							acctMap = (Map)acctList.get(i);
						%>		
						<option value="<%=Utility.trimNull(acctMap.get("BANK_ACCT")) %>" 
							<%if((benAccount).equals(Utility.trimNull(acctMap.get("BANK_ACCT"))))out.print("selected"); %>>
							<%=Utility.trimNull(acctMap.get("TYPE_CONTENT"))%>-<%=Utility.trimNull(acctMap.get("BANK_ACCT"))%>
						</option>
						<%} %>
					</select>
				</div>
				<div align="right" style="font-size: 15px;">
					选择历史日期:
					<input class="Wdate" id="endDate" name="endDate" type="text" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d',dateFmt:'yyyyMMdd'})" />&nbsp;&nbsp;
					<button class="xpbutton2" id="btnSave" name="btnSave" onclick="javascript:queryHistoryDate();">确定</button>
				</div>
				<div>
					<div style="width: 422px;float: left;">
						<div align="left" style="font-size: 22px;">昨日收益（元）</div>
						<div align="center" style="font-size: 35px;border-bottom: 1px solid gray;"><%=df2.format(benGain) %></div>
						<div align="left" style="font-size: 22px;">累计收益（元）</div>
						<div align="center" style="font-size: 35px;border-bottom: 1px solid gray;"><%=df2.format(totalGain) %></div>
						<div align="left" style="font-size: 22px;width: 50%;float: left;border-right: 1px solid gray;color: gray;">当前本金（元）</div>
						<div align="left" style="font-size: 22px;width: 50%;float: right;color: gray;">可赎回金额（元）</div>
						<div align="center" style="font-size: 30px;width: 50%;float: left;border-right: 1px solid gray;border-bottom: 1px solid gray;"><%=df2.format(currentBen)%></div>
						<div align="center" style="font-size: 30px;width: 50%;float: right;border-bottom: 1px solid gray;"><%=df2.format(totalBenGain)%></div>
						
						<div align="left" style="font-size: 22px;width: 50%;float: left;border-right: 1px solid gray;color: gray;">每万份收益（元）</div>
						<div align="left" style="font-size: 22px;width: 50%;float: right;color: gray;">七日年化收益率</div>
						<div align="center" style="font-size: 30px;width: 50%;float: left;border-right: 1px solid gray;border-bottom: 1px solid gray;"><%=df1.format(share1wGain)%></div>
						<div align="center" style="font-size: 30px;width: 50%;float: right;border-bottom: 1px solid gray;"><%=df2.format(day7yiled)%>%</div>
					</div>
					<div style="width: 422px;float: right;"><img src="<%=graphURL%>"/></div>
				</div>
			</div>
-->
			<div style="width: 850px;margin-left: 120px;">
				<div align="center" style="font-size: 50px;">现金聚利1期</div>
				<div align="right" style="font-size: 15px;">
					选择受益账户:
					<select name="benAccount" id="benAccount" onchange="selectBenAccount()">
						<option value=" ">--请选择--</option>
						<%for(int i = 0; i < acctList.size(); i++){ 
							acctMap = (Map)acctList.get(i);
						%>		
						<option value="<%=Utility.trimNull(acctMap.get("BANK_ACCT")) %>" 
							<%if((benAccount).equals(Utility.trimNull(acctMap.get("BANK_ACCT"))))out.print("selected"); %>>
							<%=Utility.trimNull(acctMap.get("TYPE_CONTENT"))%>-<%=Utility.trimNull(acctMap.get("BANK_ACCT"))%>
						</option>
						<%} %>
					</select>
				</div>
				<div align="right" style="font-size: 15px;">
					选择历史日期:
					<input class="Wdate" id="endDate" name="endDate" type="text" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d',dateFmt:'yyyyMMdd'})" />&nbsp;&nbsp;
					<button class="xpbutton2" id="btnSave" name="btnSave" onclick="javascript:queryHistoryDate();">确定</button>
				</div>
				<div>
					<div style="width: 422px;float: left;">
						<div align="left" style="font-size: 22px;">昨日收益（元）</div>
						<div align="center" style="font-size: 35px;border-bottom: 1px solid gray;"><%=df2.format(benGain) %></div>
						<div align="left" style="font-size: 22px;">累计收益（元）</div>
						<div align="center" style="font-size: 35px;border-bottom: 1px solid gray;"><%=df2.format(totalGain) %></div>
						<div align="left" style="font-size: 22px;width: 50%;float: left;border-right: 1px solid gray;color: gray;">当前本金（元）</div>
						<div align="left" style="font-size: 22px;width: 50%;float: right;color: gray;">可赎回金额（元）</div>
						<div align="center" style="font-size: 30px;width: 50%;float: left;border-right: 1px solid gray;border-bottom: 1px solid gray;"><%=df2.format(currentBen)%></div>
						<div align="center" style="font-size: 30px;width: 50%;float: right;border-bottom: 1px solid gray;"><%=df2.format(totalBenGain)%></div>
						
						<div align="left" style="font-size: 22px;width: 50%;float: left;border-right: 1px solid gray;color: gray;">每万份收益（元）</div>
						<div align="left" style="font-size: 22px;width: 50%;float: right;color: gray;">七日年化收益率</div>
						<div align="center" style="font-size: 30px;width: 50%;float: left;border-right: 1px solid gray;border-bottom: 1px solid gray;"><%=df1.format(share1wGain)%></div>
						<div align="center" style="font-size: 30px;width: 50%;float: right;border-bottom: 1px solid gray;"><%=df2.format(day7yiled)%>%</div>
					</div>
					<div id="echart" style="width: 422px;height: 300px;"></div>
					<script type="text/javascript">
						require.config({
							paths: {
								echarts: '<%=request.getContextPath()%>/cash/dist'
							}
						});
						// 使用
						require([
							'echarts',
							'echarts/chart/line'
							],
							function (ec) {
								// 基于准备好的dom，初始化echarts图表
								var myChart = ec.init(document.getElementById('echart')); 
								var option = {
									//图表标题 
									title: { 
										text: "七日年化收益率走势图(%)", //正标题 
										x: "center"
									}, 
									//图例配置 
									legend: { 
										data: ['收益率'], //这里需要与series内的每一组数据的name值保持一致
										x: "right",
										y: "top" 
									}, 
									tooltip: {
										trigger: 'axis',
										axisPointer: {
											type: 'none'
										}
									},
									//轴配置 
									xAxis: [ 
										{ 
											type: 'category',
											//boundaryGap : false,
											data: ['<%=categories[0]%>', '<%=categories[1]%>', '<%=categories[2]%>', '<%=categories[3]%>', '<%=categories[4]%>', '<%=categories[5]%>', '<%=categories[6]%>', '<%=categories[7]%>', '<%=categories[8]%>', '<%=categories[9]%>'],
											name: "日期",
											axisLabel:{
												rotate: 45,
												interval: 0,
												margin: 2
											}
										} 
									], 
									//Y轴配置 
									yAxis: [ 
										{ 
											type: 'value', 
											splitArea: { show: true }, 
											name:"收益率",
											axisLabel : {
												formatter: '{value} %'
											}
										} 
									], 
									//图表Series数据序列配置 
									series: [ 
										{ 
											name: '收益率', 
											type: 'line', 
											data: [<%=values[0]%>, <%=values[1]%>, <%=values[2]%>, <%=values[3]%>, <%=values[4]%>, <%=values[5]%>, <%=values[6]%>, <%=values[7]%>, <%=values[8]%>, <%=values[9]%>],
											markLine : {
												data : [
													{type : 'average', name: '收益率'}
												]
											}
		 								}
									]
								}
								 // 为echarts对象加载数据 
		               			 myChart.setOption(option);
							}
						);
					</script>
				</div>
			</div>

		</form>
	</body>
</html>