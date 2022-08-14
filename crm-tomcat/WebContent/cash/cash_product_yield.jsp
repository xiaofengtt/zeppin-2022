<%@ page contentType="text/html; charset=GBK" import="enfo.crm.cash.Argument,enfo.crm.cash.Format,enfo.crm.cash.*,java.math.*,java.text.*,org.jfree.data.category.*,java.util.*,enfo.crm.tools.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%	
	//��ȡurl�е�ֵ
	Integer endDate = Utility.parseInt(request.getParameter("endDate"),new Integer(0));//�������ڣ�ѡ������ڣ�
	Integer custId = Utility.parseInt(request.getParameter("custId"),new Integer(0));//�ͻ�ID
	Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));//��ƷID
	Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));//�Ӳ�ƷID
	String custNo = Utility.trimNull(request.getParameter("custNo"));
	String custName = Utility.trimNull(request.getParameter("custName"));
	String cardId = Utility.trimNull(request.getParameter("cardId"));//֤������
	Integer custType = Utility.parseInt(request.getParameter("custType"), new Integer(0));
	Integer serviceMan = Utility.parseInt(request.getParameter("serviceMan"),new Integer(0));
	String custTel = Utility.trimNull(request.getParameter("custTel"));
	String address = Utility.trimNull(request.getParameter("address"));
	String benAccount = Utility.trimNull(request.getParameter("benAccount"));//�����˺�
	Integer beginDate = new Integer(0);//��ʼ����
	//������Ϣ
	String chartTitle = "";//���߱���
	String series = "�����껯������";//��������
	String x = "";
	String y = "";
	DefaultCategoryDataset linedataset = new DefaultCategoryDataset();//��������
	String hqDate = "";//���ߺ�������
	BigDecimal share1wGain = null;//ÿ�������
	BigDecimal day7yiled= null;//�����껯������
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
	
	List list = cash.ProductFundYield(vo);
	Map map = null;
	BigDecimal maxDay7yiled = null;
	BigDecimal minDay7yiled = null;
	String[] categories = new String[10];
	BigDecimal[] values = new BigDecimal[10];
	for (int i=1;i<11;i++){
		categories[i-1]="1900010"+i;
		values[i-1]=new BigDecimal(0);
	}
	if(null != list && list.size() != 0){
		for(int i = 0; i<list.size(); i++){
			map = (Map)list.get(i);
			
			hqDate = Utility.trimNull(map.get("HQ_DATE")); 
			hqDate = Format.formatDate(Integer.parseInt(hqDate)); 
			share1wGain =  Utility.parseDecimal(Utility.trimNull(map.get("SHARE1W_GAIN")), new BigDecimal(0.00),10,"1");
			day7yiled = Utility.parseDecimal(Utility.trimNull(map.get("DAY7_YIELD")), new BigDecimal(0.00),10,"1").multiply(new BigDecimal(100));
			linedataset.addValue(day7yiled,series,hqDate);
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
			/*��������*/
			window.onload = function(){
					initQueryCondition();
			};

			function queryHistoryDate(){
				var endDate = document.getElementById("endDate").value;
   				if(endDate==""){
					alert("δѡ������");
				}else{
					document.theform.submit();
				}
			}
			
			function queryCust(){
				document.getElementById("theform").action="cash_cust_choose.jsp";
				document.theform.submit();
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="post" action="cash_product_yield.jsp">
			<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
				<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
			  		<tr>
					   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
					   <td align="right">
			   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
						</td>
					</tr>
				</table>

				<table>
					<tr>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--�ͻ����-->
						<td valign="bottom" align="left">
							<input name="custNo" value="<%=custNo %>" onkeydown="javascript:nextKeyPress(this)">
						</td>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
						<td valign="bottom" align="left">
							<input name="custName" value="<%=custName %>" onkeydown="javascript:nextKeyPress(this)" maxlength="100">
						</td>
					</tr>
					<tr>
						<td align="right">�ͻ���� :</td>
						<td>
							<select size="1" name="custType" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getCustTypeOptions(custType)%>
							</select>
						</td>
						<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td> <!-- �ͻ����� -->
						<td>
							<select size="1" name="serviceMan" onkeydown="javascript:nextKeyPress(this)">
								<%=Argument.getManager_Value(serviceMan)%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">�绰 :</td>
						<td>
							<input name="custTel" value="<%=custTel %>" onkeydown="javascript:nextKeyPress(this)"/>
						</td>
						<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--֤������-->
						<td valign="bottom" align="left">
							<input name="cardId" value="<%=cardId %>" onkeydown="javascript:nextKeyPress(this)"/>
						</td>
					</tr>
					<tr>
						<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--��ϵ��ַ-->
						<td colspan="3"><input name="post_address" value="" onkeydown="javascript:nextKeyPress(this)" size="63"></td>
					</tr>
					<tr>
						<td align="center" colspan="4">
							<button class="xpButton3" accessKey=o name="btnQuery" onclick="javascript:queryCust();">ȷ��(<u>O</u>)</button>
						</td>
					</tr>
				</table>
			</div>

			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">�ͻ���ѯ(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<!-- 
			<div style="width: 850px;margin-left: 120px;">
				<div align="center" style="font-size: 50px;">�ֽ����1��</div>
				<div align="center" style="font-size: 35px;">�����껯����������ͼ(%)</div>
				<div>
					<div style="width: 422px;float: left;">
						<div align="left" style="font-size: 22px;margin-top: 40px;">ÿ������棨Ԫ��</div>
						<div align="center" style="font-size: 35px;"><%=df1.format(share1wGain)%></div>
						<div align="left" style="font-size: 22px;margin-top: 40px;">�����껯������</div>
						<div align="center" style="font-size: 35px;"><%=df2.format(day7yiled)%>%</div>
					</div>
					<div style="width: 422px;float: right;"><img src="<%=graphURL%>"/></div>
				</div>
			</div>
			 -->
			<div style="width: 850px;margin-left: 120px;margin-top: 80px;">
				<div align="center" style="font-size: 50px;">�ֽ����1��</div>
				<div>
					<div style="width: 300px;float: left;">
						<div align="left" style="font-size: 22px;margin-top: 40px;">ÿ������棨Ԫ��</div>
						<div align="center" style="font-size: 35px;"><%=df1.format(share1wGain)%></div>
						<div align="left" style="font-size: 22px;margin-top: 40px;">�����껯������</div>
						<div align="center" style="font-size: 35px;"><%=df2.format(day7yiled)%>%</div>
					</div>
					<div id="echart" style="width: 450px;height: 300px;"></div>
				</div>
				
			</div>	
			<script type="text/javascript">
				require.config({
					paths: {
						echarts: '<%=request.getContextPath()%>/cash/dist'
					}
				});
				// ʹ��
				require([
					'echarts',
					'echarts/chart/line' //��ͬ��ͼ�ε��벻ͬ�İ�,����line ,����ͼbar �ȵ�
					],
					function (ec) {
						// ����׼���õ�dom����ʼ��echartsͼ��
						var myChart = ec.init(document.getElementById('echart')); 
						var option = {
							//ͼ����� 
							title: { 
								text: "�����껯����������ͼ(%)", //������ 
								x: "center"
							}, 
							//ͼ������ 
							legend: { 
								data: ['������'], //������Ҫ��series�ڵ�ÿһ�����ݵ�nameֵ����һ��
								x: "right",
								y: "top" 
							}, 
							tooltip: {
								trigger: 'axis',
								axisPointer: {
									type: 'none'
								}
							},
							//X������ 
							xAxis: [ 
								{ 
									type: 'category',
									//boundaryGap : false,
									data: ['<%=categories[0]%>', '<%=categories[1]%>', '<%=categories[2]%>', '<%=categories[3]%>', '<%=categories[4]%>', '<%=categories[5]%>', '<%=categories[6]%>', '<%=categories[7]%>', '<%=categories[8]%>', '<%=categories[9]%>'],
									name: "����",
									axisLabel:{
										rotate: 45,
										interval: 0,
										margin: 2
									}
								} 
							], 
							//Y������ 
							yAxis: [ 
								{ 
									type: 'value', 
									splitArea: { show: true }, 
									name:"������",
									axisLabel : {
										formatter: '{value} %'
									}
								} 
							], 
							//ͼ��Series������������ 
							series: [ 
								{ 
									name: '������', 
									type: 'line', 
									data: [<%=values[0]%>, <%=values[1]%>, <%=values[2]%>, <%=values[3]%>, <%=values[4]%>, <%=values[5]%>, <%=values[6]%>, <%=values[7]%>, <%=values[8]%>, <%=values[9]%>],
									markLine : {
										data : [
											{type : 'average', name: '������'}
										]
									}
 								}
							]
						}
						 // Ϊecharts����������� 
               			 myChart.setOption(option);
					}
				);
			</script>
				
		</form>
	</body>
</html>