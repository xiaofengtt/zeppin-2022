<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));

CustomerLocal cust_local = EJBFactory.getCustomer();//客户Bean
CustomerVO cust_vo = new CustomerVO();

cust_vo.setCust_id(cust_id);
cust_vo.setInput_man(input_operatorCode);
List cust_list = cust_local.listProcAll(cust_vo);
Map map = new HashMap();
if (cust_list.size()>0)
	map = (Map)cust_list.get(0);

// 客户电话 选择的优先顺序：mobile > o_tel > h_tel > mobile2	
String tel = Utility.trimNull(map.get("MOBILE"));
if (tel.equals("")) {
	tel = Utility.trimNull(map.get("O_TEL"));
	if (tel.equals("")) {
		tel = Utility.trimNull(map.get("H_TEL"));
		if (tel.equals("")) {
			tel = Utility.trimNull(map.get("MOBILE2"));
			if (tel.equals("")) 
				tel = Utility.trimNull(map.get("CUST_TEL"));
		}						
	}						
}

String cust_level_name = Utility.trimNull(request.getParameter("cust_level_name"));//Utility.trimNull(map.get("CUST_LEVEL_NAME"));
String is_deal_name = Utility.trimNull(request.getParameter("is_deal_name"));//Utility.trimNull(map.get("IS_DEAL_NAME"));
String money = Format.formatMoney(((BigDecimal)map.get("CURRENT_MONEY")));
if (money.lastIndexOf('.')>=0)
	money = money.substring(0, money.lastIndexOf('.'));

cust_local.remove();
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE></TITLE>
<STYLE type="text/css">
.tb_border{ border:1px solid #000;/*黑色1像素粗边框*/}
.tb_border td{border:none;/*这个是单元格，不给他要边框*/}

.a_plain {
	font-size:12px;
	color:black; 
	text-decoration:none;
}
</STYLE>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/prototype/prototype.css"/>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery.ztree-2.6.min.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
var user_id = <%=user_id%>;

/**客户信息编辑**/
function editInfo(cust_id,cust_type) {
	//var iQuery = getCondition();
	var ret = 0;

	if (user_id==21/*金汇21*/) {
		if (cust_type==1) {
			location.href = "/client/clientinfo/customer_info_jh_modi.jsp?cust_id="+cust_id;//+"&"+getCondition2();
		} else {
			location.href = "/client/clientinfo/customer_info_ent_jh_modi.jsp?cust_id="+cust_id;//+"&"+getCondition2();
		}
		return; // 不可少，否则会执行后续语句
	}
	
	if(cust_type == 2){
		ret = showModalDialog('/client/clientinfo/clinet_info_new_end.jsp?cust_id=' + cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	}else if(cust_type == 1){
		ret = showModalDialog('/client/clientinfo/client_info_new.jsp?cust_id=' + cust_id,'','dialogWidth:850px;dialogHeight:650px;status:0;help:0');
	}
	
	if(ret==1){
		window.location.reload();
	}
	
}

function lockCust(cust_id) {
	if (sl_confirm('注销')){ 
		disableAllBtn(true); 
		location.href = "/client/clientinfo/customer_remove.jsp?cust_id="+cust_id;
	}
}

function newContact(cust_id) {
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contact_new.jsp?cust_id="+cust_id;
	showModalDialog(url,'','dialogWidth:900px;dialogHeight:420px;status:0;help:0;scroll:0');
}

function joinGroup(target_custid){
	var url = "/client/clientinfo/client_info_group.jsp?cust_id="+target_custid;
	showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');	
}

/**联系人**/
function contactInfo(cust_id, cust_type)
{
	//var iQuery = getCondition();
	location = "/client/clientinfo/client_contacts_list.jsp?cust_id="+cust_id+"&cust_type="+cust_type+"&contactType=4&iQuery=<%=cust_level_name+"$"+is_deal_name%>";
}

function addLog(){
	var form = document.theform;
	
	if (form.step_flag.value=="") {
		sl_alert("请选择工作进展！");
		form.step_flag.focus();
		return;
	}

	if (form.info_level.value=="") {
		sl_alert("请选择信息等级！");
		form.info_level.focus();
		return;
	}

	if (form.serviceInfo.value=="") {
		sl_alert("请输入服务项目！");
		form.serviceInfo.focus();
		return;
	}

	if (form.subject.value=="") {
		sl_alert("请输入交流主题！");
		form.subject.focus();
		return;
	}

	if (form.serviceSummary.value=="") {
		sl_alert("请输入服务项目详细描述！");
		form.serviceSummary.focus();
		return;
	}

	if (form.scontent.value=="") {
		sl_alert("请输入交流内容！");
		form.scontent.focus();
		return;
	}

	$.ajax({
	  type: 'POST',
	  url: "service_add.jsp",
	  contentType: "application/x-www-form-urlencoded;charset=utf-8", <%--解决提交的中文乱码问题--%>
	  data: {
			custId: <%=cust_id%>,
			custNo: "<%=map.get("CUST_NO")%>",
			//serviceTime: "",
			serviceInfo: form.serviceInfo.value,
			serviceSummary: form.serviceSummary.value,
			scontent: form.scontent.value,
			subject: form.subject.value,
			step_flag: form.step_flag.value,
			info_level: form.info_level.value,
			executor: <%=input_operatorCode%>,
			service_man2: <%=input_operatorCode%>		
	  },
	  success: function(data){
			location.reload();
	  },
	 /* error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus + " " + errorThrown);
	  },*/
	  dataType: "text"
	});
}

function showProduct(product_id) {	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}

function showContractInfo(serialno) {
	 showModalDialog( '/marketing/synthesis/purchase_query_info.jsp?serial_no='+serialno, '', 'dialogWidth:800px;dialogHeight:540px;status:0;help:0');
}

function showBenifitorInfo(serialno) {
	 location.href = "/marketing/synthesis/benifiter_query.jsp";
}

function showDeployDetail(cust_id, product_id, contract_sub_bh) {
	var url = '/client/clientinfo/deploy_detail.jsp?cust_id='+cust_id+'&product_id='+product_id	+'&contract_sub_bh='+contract_sub_bh;
	showModalDialog(url, '','dialogWidth:700px;dialogHeight:500px;status:0;help:0');
}

/*打印信封*/
function printInfo(cust_id) {
	window.open('/client/analyse/print.jsp?cust_id='+cust_id, '', 'left=250,top=250,width=638px,height=300px,scrollbars=yes,resizable=no, ');
}

function sendMail(cust_id) {
	if (showModalDialog('/client/analyse/sendmail.jsp?checkflag=yes&cust_id='+cust_id,'','dialogWidth:800px;dialogHeight:580px;status:0;help:0')) {
		sl_alert("<%=LocalUtilis.language("message.mailSendOk",clientLocale)%> ！");//邮件发送成功
	}
}

function sendSMS(cust_id) {
	if(showModalDialog('/client/analyse/sendsms.jsp?cust_id='+cust_id,'','dialogWidth:800px;dialogHeight:580px;status:0;help:0')) {
		//sl_alert("<%=LocalUtilis.language("message.mailSendOk",clientLocale)%> ！");//邮件发送成功
	}
}

/*打印客户对账单*/
function printcustInfo(cust_id) {
	var url = "";
	if (user_id == 2) //北国投格式
		url += "/client/analyse/printclient_bgt.jsp";
	else
		url += "/client/analyse/printclient.jsp";

	//syncDatePicker(document.theform.startdate_picker, document.theform.startdate);
	//syncDatePicker(document.theform.enddate_picker, document.theform.enddate);
	//url += '?cust_id='+cust_id+'&startdate='+document.theform.startdate.value+'&enddate='+document.theform.enddate.value;
	url += '?cust_id='+cust_id+'&startdate=0&enddate=0';

	window.open(url ,'','left=250, top=120,width=800px,height=500px,scrollbars=yes,resizable=no, ');
}

/*打印客户快递单 20110830 交银用*/
function printcustExpressInfo(cust_id) {
	var url = "/client/analyse/printclient_ems.jsp?cust_id="+ cust_id+"&ret=proto&cust_level_name=<%=cust_level_name%>&is_deal_name=<%=is_deal_name%>";
	location = url;	
}
</script>
</HEAD>
<BODY>
<form name="theform">

<P></P>

<table width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed">
<tr><!--   -->
<td colspan="2" width="100%">
 
<table style="border-collapse:collapse;">
	<tr>
		<td width="134px"><img src="/includes/proto/tb_head1.jpg"/></td>
		<td width="100%" style="background-image: url('/includes/proto/tb_head2.jpg');background-repeat: repeat-x" align="right">
			<a href="javascript:printInfo(<%=cust_id%>);" class="a_imgbtn">打印信封</a>
			&nbsp;&nbsp;<a href="javascript:printcustExpressInfo(<%=cust_id%>);" class="a_imgbtn">打印EMS</a>
			&nbsp;&nbsp;<a href="javascript:printcustInfo(<%=cust_id%>);" class="a_imgbtn">打印对账单</a>
			&nbsp;&nbsp;<a href="javascript:sendMail(<%=cust_id%>);" class="a_imgbtn">发邮件</a>
			&nbsp;&nbsp;<a href="javascript:sendSMS(<%=cust_id%>);" class="a_imgbtn">发短信</a>
			&nbsp;&nbsp;<a href="javascript:history.back();" class="a_imgbtn">返 回</a>
		</td>
	</tr>

	<tr>
<td colspan="2">
	<table width="100%" class="tb_border" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc">
		<tr>
		<td align="left" width="102px"><div hight="104px"  align="left"> <img src="/includes/proto/tb_head3.jpg" ><div></td>
		<td align="left">
			<table >
				<tr>
				<td><span style="font-size: 13px;"><b><%=Utility.trimNull(map.get("CUST_NAME"))%></b>
						&nbsp;&nbsp;&nbsp;&nbsp;</span><a style="font-size: 13px;color:#006699" href="javascript:editInfo(<%=map.get("CUST_ID")%>,<%=map.get("CUST_TYPE")%>);">编辑</a>
						&nbsp;&nbsp;&nbsp;<a style="font-size: 13px;color:#006699" href="javascript:lockCust(<%=map.get("CUST_ID")%>);">注销</a></td>	
				</tr>
				<tr>
					<td><span style="font-size: 13px;">&nbsp;</span></td>
				</tr>
				<tr>		
					<td><span style="font-size: 13px; color:#666666">存量金额： <%=money%>&nbsp;&nbsp;<span style="font-weight: bold; color:blue"><%=cust_level_name%></span></span></td>
				</tr>
				<tr>
					<td><span style="font-size: 13px; color:#666666">最近认购： <%=Format.formatDateLine(((Integer)map.get("LAST_RG_DATE")))%>&nbsp;&nbsp;<b><%=is_deal_name%></b></span></td>
				</tr>				
			</table>
		</td>
		<td width="*">
			<table width="100%">
			<tr>
				<td style="width:5px"><img src="/includes/proto/tb_head4.jpg" >&nbsp;&nbsp;&nbsp;&nbsp;</td>

				<td style="width:300px" align="left">
					<table>
						<tr>
						<td width="215px">
							<span style="font-size: 13px; color:#666666">电话： <%=tel%></span><br/>
							<span style="font-size: 13px; color:#666666">地址： <%=Utility.trimNull(map.get("POST_ADDRESS"))%></span>
						</td>
						<td align="right">
							<table style="table-layout:auto">
								<tr>
									<td><a style="font-size: 13px;color:#006699" href="javascript:contactInfo(<%=cust_id%>,<%=map.get("CUST_TYPE")%>);">更多联系人</a></td>
								</tr>
								<tr>
									<td><a style="font-size: 13px;color:#006699" href="javascript:joinGroup(<%=cust_id%>);">客户群组</a></td>
								</tr>
								<tr>
									<td ><a style="font-size: 13px;color:#006699" href="/client/clientinfo/client_query_info.jsp?cust_id=<%=cust_id%>&showflag=0">其他</a></td>
								</tr>
							</table>	
						</td>
						</tr>
					</table>
				</td>

				<td style="width:5px"><img src="/includes/proto/tb_head4.jpg" >&nbsp;&nbsp;&nbsp;&nbsp;</td>

				<td width="*">
					<table width="100%">
						<tr><td width="75%">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td><span style="font-size: 13px;color:#666666">电话: 来电: 0次　　　去电: 0次</span></td>
								</tr>
								<tr>
									<td><span style="font-size: 13px;color:#666666">短信: 上行: 0次　　　下行: 0次</span></td>
								</tr>
								<tr>
									<td><span style="font-size: 13px;color:#666666">邮件: 上行: 0次　　　下行: 0次</span></td>
								</tr>		
								<tr>
									<td><span style="font-size: 13px;color:#666666">活动: 0次　　　　　　 拜访: 0次</span></td>
								</tr>				
							</table>
						</td>
						<td align="right" width="*">
							<table width="100%">
								<tr>
									<td width="100%"><a style="font-size: 13px;color:#006699;" href="javascript:void(0);">受益账户变更</a></td>
								</tr>
								<tr>
									<td width="100%"><a style="font-size: 13px;color:#006699" href="javascript:void(0);">受益权转让</a></td>
								</tr>					
							</table>
						</td></tr>
					</table>
				</td>
			</tr>
			</table>
		</td>
		</tr>
	</table>
</td>
	</tr>

</table>

</td>
</tr>

<tr>
<td valign="top" width="550px">	
	<table width="550px">
		<tr>
			<td width="550px"><span style="font-size: 13px;font-weight:bold">添加与该客户的往来记录</span></td>
		</tr>
		<tr>
			<td width="550px">
				<table style="width:550px; height:250px; border:1px solid #cccccc">
					<tr>
						<td align="right" style="width:100px">工作进展:</td>
					    <td>
					        <select size="1" name="step_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					            <%=Argument.getDictParamOptions(1117,"0")%>
					        </select>
					    </td>							
					    <td  align="right">信息等级:</td>
					    <td>
					        <select size="1" name="info_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					            <%=Argument.getDictParamOptions(1118,"0")%>
					        </select>
					    </td>
					</tr>
					<tr>
					    <td align="right" style="width:100px">服务项目:</td>
					    <td colspan="3"><INPUT type="text" name="serviceInfo" size="30" value=""></td>		
					</tr>								
					<tr>
					    <td align="right" valign="top">服务项目详细描述:</td>
					    <td colspan="3" valign="top"><textarea rows="3" name="serviceSummary" cols="80"></textarea>
					    </td>
					</tr>
					<tr>
					    <td align="right" style="width:100px">交流主题:</td>
					    <td colspan="3"><input name="subject" maxlength="60" size="80" onkeydown="javascript:nextKeyPress(this)" value=""></td>
					</tr>
					<tr>
					    <td align="right" valign="top">交流内容:</td>
					    <td colspan="3" valign="top"><textarea rows="3" name="scontent" cols="80"></textarea>
					    </td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="550px">
			<table width="550px">
				<tr>				
				<td align="right" width="550px"><%-- %>img src="/includes/proto/tb_head5.jpg" --%>
					<a href="javascript:addLog();" class="a_imgbtn">发 表</a>
				</td>
				</tr>
			</table>
			</td>
		</tr>
<%
int i;

CustomerVO vo = new CustomerVO();
CustomerLocal local = EJBFactory.getCustomer();
vo.setCust_name("");
vo.setCust_no((String)map.get("CUST_NO"));
vo.setService_info("");
vo.setData_flag("");
vo.setService_man(new Integer(0));
vo.setExecutor(new Integer(0));
vo.setInput_man(input_operatorCode);

List list  = local.getCustomerMaintenanceRecord(vo,1,-1).getRsList(); // get all
local.remove();

Object[] array = list.toArray();
/*Arrays.sort(array, new Comparator() {
	public int compare(Object arg0, Object arg1) {
		java.sql.Timestamp d0 = (java.sql.Timestamp)((Map)arg0).get("SERVICE_TIME");
		java.sql.Timestamp d1 = (java.sql.Timestamp)((Map)arg1).get("SERVICE_TIME");		
			
		if (d0.getTime() < d1.getTime() ) 
			return 1;
		else if (d0.getTime() > d1.getTime())
			return -1;
		else 
			return 0;
	}

});
*/
for (i=0; i<array.length; i++) {
	Map comm_map = (Map)array[i];
	String serv_time = Utility.trimNull(comm_map.get("SERVICE_TIME"));
	if (serv_time.lastIndexOf(':')>=0) 
		serv_time = serv_time.substring(0, serv_time.lastIndexOf(':'));
	
 %>
		<tr>
			<td width="550px">
				<table width="550px">
					<tr>
						<td align="left" valign="top" width="50px"><img src="/includes/proto/tb_head6.jpg"/></td>
									
						<td width="500px">
							<table width="500px">
								<tr>					
									<td align="left" width="100px">
										<span style="font-size: 13px; color:#006699"><%=comm_map.get("SERVICE_MAN_NAME")%></span>
										<%-- %>font  style="font-size: 13px; color: silver;">&nbsp;&nbsp;&nbsp;盈丰软件-研发部-产品专员</font--%>
									</td>
									<td align="right" width="376px"><span style="font-size: 13px; color: silver;"><%=serv_time%></span></td>				
								</tr>
								<tr>
									<td colspan="2">
										<table style="width:480px; height:300px; border: 1px solid  #cccccc; background-color:#f5f5f5">
											<tr>
											    <td align="right" valign="center" style="width:100px;height:50px">服务项目:</td>
											    <td valign="center"><%=Utility.trimNull(comm_map.get("SERVICE_INFO"))%></td>		
											</tr>	
											<tr>
											    <td align="right" valign="top" style="width:100px;height:100px">服务项目详细描述:</td>
											    <td valign="top"><%=Utility.trimNull(comm_map.get("SERVICE_SUMMARY"))%></td>
											</tr>		
											<tr>
											    <td align="right" valign="center" style="width:100px;height:50px">交流主题:</td>
											    <td valign="center"><%=Utility.trimNull(comm_map.get("SUBJECT"))%></td>
											</tr>								
											<tr>
											    <td align="right" valign="top" style="width:100px;height:100px">交流内容:</td>
											    <td valign="top"><%=Utility.trimNull(comm_map.get("CONTENT"))%></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>					
				</table>
			</td>
		</tr>
<%} %>
	</table>	

</td>

<td valign="top" width="*">
	<table width="100%">
		<tr><td width="100%">			
			<table width="100%" class="tb_border" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc;">
				<tr style="background-color: #eeeeee">
					<td style="height: 30px;width: 200px;text-align: left;" >
						<span style="font-size: 12px;font-weight:bold">　·认购清单</span>
					</td>
					<td align="right" width="*"><a class="a_plain" href="javascript:void(0);">more..</a></td>
				</tr>
				<tr><td colspan="2">
					<div style="overflow-y:auto;height:110px">
					<table>				
	<%
	ContractLocal contract_local = EJBFactory.getContract();//合同Bean
	ContractVO contract_vo = new ContractVO();
	contract_vo.setProduct_id(new Integer(0));
	contract_vo.setBook_code(new Integer(1));
	contract_vo.setCust_id(cust_id);
	List contract_list = contract_local.queryContractByCustID(contract_vo);
	contract_local.remove();

	array = contract_list.toArray();
	Arrays.sort(array, new Comparator() {
		public int compare(Object arg0, Object arg1) {
			Integer d0 = (Integer)((Map)arg0).get("QS_DATE");
			Integer d1 = (Integer)((Map)arg1).get("QS_DATE");			
				
			return -(d0.intValue()-d1.intValue());
		}
	
	});
	for(i=0; i<array.length; i++){
		Map contract_map = (Map)array[i];
		Integer query_product_id = Utility.parseInt(Utility.trimNull(contract_map.get("PRODUCT_ID")),new Integer(0));
		Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
		
		String product_jc = Argument.getProductJC(query_product_id);
		String rg_money = Format.formatMoney(((BigDecimal)contract_map.get("RG_MONEY")));
		if (rg_money.lastIndexOf('.')>=0)
			rg_money = rg_money.substring(0, rg_money.lastIndexOf('.'));
	 %>
					<tr>
						<td colspan="2" width="100%" style="height: 20px; font-size: 12px; color:#666666">
							<%=Format.formatDateLine(((Integer)contract_map.get("QS_DATE")))%>&nbsp;&nbsp;
							<a class="a_plain" href="javascript:showProduct(<%=query_product_id%>);"><%=product_jc%></a>&nbsp;&nbsp;<%=rg_money%>
						    &nbsp;&nbsp;<a href="javascript:showContractInfo(<%=contract_map.get("SERIAL_NO")%>)"><img style="border:0" src="/images/index-bt-arrow03.jpg"/></a>
						</td>
					</tr>
	<%} 
%>
				</table>		
				</div>	
			</td></tr>
			</table>		
		</td></tr><!-- <tr><td width="100%"> -->

		<tr>
		<td width="100%">
			<table width="100%" class="tb_border"  cellpadding="0" cellspacing="0" style="border:1px solid #cccccc">
			<tr style="background-color: #eeeeee">
				<td style="height: 30px;width: 200px;text-align: left;">
					<span style="font-size: 12px;font-weight:bold">　·受益权对账单</span>
				</td>
				<td align="right"><a class="a_plain" href="javascript:void(0);">more..</a></td>
			</tr>
			<tr><td colspan="2">
					<div style="overflow-y:auto;height:110px">
					<table>
<%
BigDecimal amount = new BigDecimal(0.000);
BigDecimal to_amount = new BigDecimal(0.000);//认购金额
BigDecimal exchange_amount = new BigDecimal(0.000);//转让金额
BigDecimal back_amount = new BigDecimal(0.000);//兑付金额
BigDecimal ben_amount = new BigDecimal(0.000);//受益金额
Integer serial_no = new Integer(0);

BenifitorLocal benifitor=EJBFactory.getBenifitor();
BenifitorVO benifitor_vo = new BenifitorVO();
benifitor_vo.setBook_code(new Integer(1));
benifitor_vo.setCust_id(cust_id);
benifitor_vo.setProduct_id(new Integer(0));

List ben_list = benifitor.QueryBenifitor(benifitor_vo);
benifitor.remove();

array = ben_list.toArray();
Arrays.sort(array, new Comparator() {
	public int compare(Object arg0, Object arg1) {
		Integer d0 = (Integer)((Map)arg0).get("BEN_DATE");
		Integer d1 = (Integer)((Map)arg1).get("BEN_DATE");			
			
		return -(d0.intValue()-d1.intValue());
	}

});

String period_unit = "";//租赁类型1短期租赁2长期租赁
for(i=0; i<array.length; i++) {
	Map ben_map = (Map)array[i];

	if(ben_map.get("TO_AMOUNT") != null)
		to_amount = to_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("TO_AMOUNT"))));	
	if(ben_map.get("EXCHANGE_AMOUNT") != null)
		exchange_amount = exchange_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("EXCHANGE_AMOUNT"))));	
	if(ben_map.get("BACK_AMOUNT") != null)
		back_amount = back_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BACK_AMOUNT"))));	
	if(ben_map.get("BEN_AMOUNT") != null)
		ben_amount = ben_amount.add(Utility.stringToDouble(Utility.trimNull(ben_map.get("BEN_AMOUNT"))));	
	period_unit=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(ben_map.get("PERIOD_UNIT")), new Integer(0)));
	//根据客户购买产品和合同编号获得客户合同信息
	contract_vo.setProduct_id(Utility.parseInt(Utility.trimNull(ben_map.get("PRODUCT_ID")), new Integer(0)));
	Integer query_product_id = Utility.parseInt(contract_vo.getProduct_id(),new Integer(0));
	Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
	
	String product_jc = Argument.getProductJC((Integer)ben_map.get("PRODUCT_ID"));
	String ben_money = Format.formatMoney(((BigDecimal)ben_map.get("BEN_AMOUNT")));
	if (ben_money.lastIndexOf('.')>=0)
		ben_money = ben_money.substring(0, ben_money.lastIndexOf('.'));
%>
			<tr>
				<td colspan="2" width="100%" style="height: 20px; font-size: 12px; color:#666666">
					<%=Format.formatDateLine(((Integer)ben_map.get("BEN_DATE")))%>&nbsp;&nbsp;
					<a class="a_plain" href="javascript:showProduct(<%=query_product_id%>);"><%=product_jc%></a>&nbsp;&nbsp;<%=ben_money%>
					&nbsp;&nbsp;<a href="javascript:showBenifitorInfo()"><img style="border:0" src="/images/index-bt-arrow03.jpg"/></a>
				</td>
			</tr>
<%} %>
			</table>
			</div>
			</td></tr>
			</table>

		</td></tr><!-- <tr><td width="100%"> -->

		<tr>
		<td width="100%">
			<table width="100%" class="tb_border"  cellpadding="0" cellspacing="0" style="border:1px solid #cccccc">
			<tr style="background-color: #eeeeee">
				<td style="height: 30px;width: 200px;text-align: left;">
					<span style="font-size: 12px;font-weight:bold">　·收益汇总</span>
				</td>
				<td align="right"><a href="javascript:void(0);" class="a_plain">more..</a></td>
			</tr>
			<tr><td colspan="2">
					<div style="overflow-y:auto;height:110px">
					<table>
<%
to_amount = new BigDecimal("0.000");
java.math.BigDecimal Bond1_amount=new BigDecimal("0.000");//发行期利息
java.math.BigDecimal Gain1_amount=new BigDecimal("0.000");//中间收益
java.math.BigDecimal Gain2_amount=new BigDecimal("0.000");//到期收益
java.math.BigDecimal Bond2_amount=new BigDecimal("0.000");//兑付期利息
java.math.BigDecimal To_money2_amount=new BigDecimal("0.000");//到期本金
java.math.BigDecimal Bond_tax_amount=new BigDecimal("0.000");//扣税
java.math.BigDecimal Total_money_amount=new BigDecimal("0.000");//合计金额

DeployLocal deploy_local = EJBFactory.getDeploy();
DeployVO deploy_vo = new DeployVO();
deploy_vo.setInput_man(input_operatorCode);
deploy_vo.setCust_id(cust_id);
deploy_vo.setProduct_id(new Integer(0));

List deploy_list = deploy_local.queryDeployByCustId(deploy_vo);
deploy_local.remove();

for(i=0; i<deploy_list.size(); i++) {
	Map deploy_map = (Map)deploy_list.get(i);
	Integer query_product_id = Utility.parseInt(Utility.trimNull(deploy_map.get("PRODUCT_ID")),new Integer(0));
	String query_product_name = Utility.trimNull(deploy_map.get("PRODUCT_NAME")); 
	Integer pre_product_id = Argument.getPreProduct_id(query_product_id);
	if(deploy_map.get("BOND1") != null)
		Bond1_amount=Bond1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND1"))));
	if(deploy_map.get("GAIN1") !=null)
		Gain1_amount=Gain1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN1"))));
	if(deploy_map.get("GAIN2") !=null)
		Gain1_amount=Gain1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("GAIN2"))));
	if(deploy_map.get("BOND2") != null)
		Bond1_amount=Bond1_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND2"))));
	if(deploy_map.get("TO_MONEY2") != null)
		To_money2_amount=To_money2_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("TO_MONEY2"))));
	if(deploy_map.get("BOND_TAX") != null)
		Bond_tax_amount=Bond_tax_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("BOND_TAX"))));
	if(deploy_map.get("TOTAL_MONEY") != null)
		Total_money_amount=Total_money_amount.add(Utility.stringToDouble(Utility.trimNull(deploy_map.get("TOTAL_MONEY"))));

	String product_jc = Argument.getProductJC(query_product_id);
	String total_money = Format.formatMoney(((BigDecimal)deploy_map.get("TOTAL_MONEY")));
	if (total_money.lastIndexOf('.')>=0)
		total_money = total_money.substring(0, total_money.lastIndexOf('.'));

	String contract_sub_bh = Utility.trimNull(deploy_map.get("CONTRACT_SUB_BH"));
%>
			<tr>
				<td colspan="2" width="100%" style="height: 20px; font-size: 12px; color:#666666">
					<a class="a_plain" href="javascript:showProduct(<%=query_product_id%>);"><%=product_jc%></a>&nbsp;&nbsp;<%=total_money%>
					&nbsp;&nbsp;<a href="javascript:showDeployDetail(<%=cust_id%>,<%=query_product_id%>,'<%=contract_sub_bh%>')"><img style="border:0" src="/images/index-bt-arrow03.jpg"/></a>
				</td>
			</tr>
<%} 
%>
			</table>
			</div>
			</td></tr>
			</table>
		</td></tr>

		<tr>
		<td width="100%">
			<table width="100%" class="tb_border" cellpadding="0" cellspacing="0" style="border:1px solid #cccccc">
			<tr style="background-color: #eeeeee"> 
				<td style="height: 30px;width: 200px;text-align: left;">
					<span style="font-size: 12px;font-weight:bold">　·非信托产品</span>
				</td>
				<td align="right"><a class="a_plain" href="javascript:void(0);">more..</a></td>
			</tr>
			<tr>
				<td colspan="2" width="100%" style="height: 100px; font-size: 12px; color:#666666">&nbsp;</td>
			</tr>
			</table>			
		</td>
		</tr>
	</table>

</td>
</tr>

</table>

</form>
</BODY>
</HTML>
