<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.affair.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
String return_url = request.getHeader("Referer"); //
String from = Utility.trimNull(request.getParameter("from"));
boolean from_main = from.equals("main");

//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
boolean bSuccess = false;
//获取页面传递变量
Integer pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("pre_product_id")),new Integer(0));
Integer customer_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("customer_cust_id")),new Integer(0));
Integer cc_cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cc_cust_id")),new Integer(0));
Integer cust_id = null;
if (cc_cust_id.intValue()>0)
	cust_id = cc_cust_id;
else
	cust_id = customer_cust_id;

//声明变量
//--1.预约信息

BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0));
Integer	link_man = Utility.parseInt(Utility.trimNull(request.getParameter("link_man")),input_operatorCode);
Integer	valid_days = Utility.parseInt(Utility.trimNull(request.getParameter("valid_days")),new Integer(0));
String	pre_type = Utility.trimNull(request.getParameter("pre_type"));
Integer	pre_num = Utility.parseInt(Utility.trimNull(request.getParameter("pre_num")),new Integer(1));
Integer preDate = Utility.parseInt(Utility.trimNull(request.getParameter("pre_date")),new Integer(0));
Integer expRegDate = Utility.parseInt(Utility.trimNull(request.getParameter("exp_reg_date")),new Integer(0));
String	summary = Utility.trimNull(request.getParameter("summary"));
String customer_cust_source = Utility.trimNull(request.getParameter("customer_cust_source"));//客户来源
String channel_type = Utility.trimNull(request.getParameter("channel_type"));
BigDecimal channel_fare = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("channel_fare"))), null);
String per_code = Utility.trimNull(request.getParameter("per_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal huge_money = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(request.getParameter("huge_money"))), new BigDecimal(0));
String card_type = Utility.trimNull(request.getParameter("card_type"));
String card_type1 = Utility.trimNull(request.getParameter("card_type1"));
String pre_level = Utility.trimNull(request.getParameter("pre_level"));
Integer team_id = Utility.parseInt(request.getParameter("team_id"), new Integer(0));

//--2.客户信息
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
String smsContent = "";
//获取对象
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm(); 
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//客户
PreContractCrmVO pre_vo = new PreContractCrmVO();
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();

//客户信息详细
if(cust_id.intValue()>0){
	//客户单个值		
	c_vo.setCust_id(cust_id);
	//c_vo.setInput_man(input_operatorCode);
	List rsList_cust = customer.listByControl(c_vo);			
	if(rsList_cust.size()>0){
		Map map_cust = (Map)rsList_cust.get(0);

		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.trimNull(map_cust.get("CARD_TYPE"));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		//保密信息
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));
	}		
}

if(request.getMethod().equals("POST")){
	c_vo = new CustomerVO();

	if(cust_id.intValue() == 0){
		//新增客户信息
		String customer_cust_name = Utility.trimNull(request.getParameter("customer_cust_name"));
		Integer is_deal_picker = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal_picker")), new Integer(0));
		cust_tel = Utility.trimNull(request.getParameter("cust_tel"));
		card_type = Utility.trimNull(request.getParameter("card_type"));
		String country = Utility.trimNull(request.getParameter("country"));
		if ("".equals(card_type) || card_type.equals("0")) card_type=  "110801";//默认身份证
		String card_ids = Utility.trimNull(request.getParameter("card_id"));
		if ("".equals(card_ids)) card_ids = " ";//默认空值
		c_vo.setCust_name(customer_cust_name);
		c_vo.setCust_type(is_deal_picker);
		c_vo.setCust_tel(cust_tel);
		c_vo.setCard_type(is_deal_picker.intValue() == 1 ? card_type : card_type1);
		c_vo.setCard_id(card_ids);
		c_vo.setInput_man(input_operatorCode);
		c_vo.setService_man(link_man);
		c_vo.setComplete_flag(new Integer(2)); //客户信息不完整
		c_vo.setCountry(country);
		c_vo.setPrint_deploy_bill(new Integer(1));//默认打印对账单
		c_vo.setPrint_post_info(new Integer(1));
		cust_id =  customer.append(c_vo);
	
		if (cust_id.intValue()>0){
			//客户单个值		
			cust_vo.setCust_id(cust_id);
			List rsList_cust = customer.listByControl(cust_vo);
					
			if (rsList_cust.size()>0) {
				Map map_cust = (Map)rsList_cust.get(0);
					
				cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
				cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
				cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
				service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
				legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
				contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
				post_code = Utility.trimNull(map_cust.get("POST_CODE"));
				post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
				card_type = Utility.trimNull(map_cust.get("CARD_TYPE"));
				cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
				is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
				//保密信息
				card_id = Utility.trimNull(map_cust.get("CARD_ID"));
				o_tel = Utility.trimNull(map_cust.get("O_TEL"));
				h_tel = Utility.trimNull(map_cust.get("H_TEL"));
				mobile = Utility.trimNull(map_cust.get("MOBILE"));
				bp = Utility.trimNull(map_cust.get("BP"));
				e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
				
				h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
				h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
				h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
				h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
				h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
				h_bp = Utility.trimNull(map_cust.get("H_BP"));
			}		
		}
	}

	//客户信息数据打包
	c_vo.setCust_id(cust_id);
	c_vo.setCust_no(cust_no);
	c_vo.setCust_name(cust_name);
	c_vo.setCust_type(cust_type);
	c_vo.setCard_id(h_card_id);
	if(! card_type.toString().equals(""))
		c_vo.setCard_type(card_type.toString());
	else
		c_vo.setCard_type(card_type1);	
	c_vo.setLegal_man(legal_man);
	c_vo.setContact_man(contact_man);
	c_vo.setPost_address(post_address);
	c_vo.setPost_code(post_code);
	c_vo.setMobile(h_mobile);
	c_vo.setE_mail(h_e_mail);
	c_vo.setService_man(service_man);
	c_vo.setInput_man(input_operatorCode);
	//同步客户信息
	customer_inst.cope_customers(c_vo);
	//预约信息打包
	pre_vo.setPre_product_id(pre_product_id);
	pre_vo.setCust_id(cust_id);
	pre_vo.setPre_money(pre_money);
	pre_vo.setLink_man(link_man);
	pre_vo.setValid_days(valid_days);
	pre_vo.setPre_type(pre_type);
	pre_vo.setPre_num(pre_num);
	pre_vo.setSummary(summary);
	pre_vo.setInput_man(input_operatorCode);
	pre_vo.setPre_date(preDate);
	pre_vo.setExp_reg_date(expRegDate);
	pre_vo.setCust_source(customer_cust_source);
	pre_vo.setTest_code(Utility.parseInt(per_code,null)); 
	pre_vo.setChannel_type(channel_type);
	pre_vo.setChannel_fare(channel_fare);
	pre_vo.setPre_level(pre_level);
	pre_vo.setTeam_id(team_id);

	//保存预约信息	 
	preContract.append(pre_vo);
	//北京信托-预约金额大于产品的最大预约金额时发生短信
	//if(pre_money.compareTo(huge_money) >= 1){
		//客服主管角色
	 //	int role_id = Argument.getSyscontrolValue("AROLE98");
		//if(role_id != 0){
			//角色下的员工循环发送短信
			//Map map = new HashMap();
			//List list = Argument.getOperatorListByRoleId(new Integer(role_id));
			//if(list != null && list.size() != 0){
				//for(int j=0; j<list.size(); j++){
					//map = (Map)list.get(j);
					//smsContent = Utility.trimNull(map.get("OP_NAME")) + "您好！客户：" + cust_name + "已经预约成功；产品名称："+product_name+"；产品大额预约金额："+Format.formatMoney(huge_money)+"；预约金额：" + Format.formatMoney(pre_money) + "元；联系电话：" +cust_tel+"。请予以关注！";
					
					//if(!"".equals(Utility.trimNull(map.get("MOBILE"))) && Utility.trimNull(map.get("MOBILE")).length() == 11){
						//Argument.sendSMSSimple(input_operatorCode+"",Utility.trimNull(map.get("MOBILE")),smsContent,new Integer(1),"待发",new Integer(0),input_operatorCode);
					//}
				//}
			//}
		//}
	//}

	bSuccess = true;
	return_url = Utility.trimNull(request.getParameter("return_url")); //
}

TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();
List list = tmanagerteams_Bean.list_queryOp(input_operatorCode);
if(list.size() > 0) {
	Map map = (Map) list.get(0);
	team_id =  Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
}

preContract.remove();
customer_inst.remove();
customer.remove();

boolean notNeedQuota = Argument.getSyscontrolValue("NEED_QUOTA")==2; // 是否需要设置销售配额
boolean isZt = user_id.intValue()==22;// 中投？
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.bespeakAdd",clientLocale)%></TITLE><!--预约新增-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script language=javascript>
<%if (bSuccess && from_main) {%>
	sl_alert("已成功保存!");
	location.href= '<%=return_url%>';
<%}%>

function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.action="direct_reserve_add.jsp";
		document.theform.method="post";
		document.theform.submit();
	}
}

/*返回*/
function CancelAction(){
	history.back();
}

/*下面是新增客户时用到的函数*/
function validateForm(form){
	if((form.customer_cust_id.value == "" || form.customer_cust_id.value == 0)){
		if(!sl_check(form.customer_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//客户名称
		if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")) return false;//客户类型
		if(!sl_checkNum(form.cust_tel, "联系电话", 20, 1)) return false;//联系电话
	}
	if(!sl_checkChoice(form.pre_product_id, "<%=LocalUtilis.language("class.product",clientLocale)%> ")) return false;//产品
	if(!sl_checkDecimal(form.pre_money, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 1)) return false;//预约金额
	if(!sl_checkDecimal(form.input_info3, "<%=LocalUtilis.language("class.factPreNum2",clientLocale)%> ", 13, 3, 0)) return false;

<%if (! notNeedQuota) {%>
	if(form.pre_money.value - form.input_info3.value > 0){
		sl_alert("预约金额不能大于当前可预约金额!");
		return false;
	}
	if(form.pre_money.value < 3000000 && (form.cust_type.value ==1 || form.is_deal_picker.value ==1)){
		if(parseInt(form.pre_num.value) > parseInt(form.input_info4.value)){
			sl_alert("小额预约份数不能大于当前可预约份数！");
			return null;
		}
	}
<%}%>

	if(!sl_checkChoice(form.pre_num, "<%=LocalUtilis.language("class.preNum2",clientLocale)%> "))	return false;//预约份数
<%if (! isZt) {%>
	if(!sl_checkChoice(form.pre_type, "<%=LocalUtilis.language("class.preType",clientLocale)%> "))	return false;//预约方式
<%}%>
	//if(!sl_checkChoice(form.customer_cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> "))	return false;//客户来源
	if(!sl_checkDecimal(document.theform.channel_fare, "渠道费用", 13, 3, 0))return false;
	if(document.theform.channel_fare.value != "")
		if(!sl_checkChoice(form.channel_type, "渠道类别"))	return false;
	if(!sl_checkChoice(form.link_man, "<%=LocalUtilis.language("class.contact",clientLocale)%> "))	return false;//联系人
	if(!sl_checkDate(document.theform.pre_date_picker,"<%=LocalUtilis.language("class.preDate",clientLocale)%> ")){//预约日期
		return false;
	}
	syncDatePicker(form.pre_date_picker, form.pre_date);
	
	if(document.theform.exp_reg_date_picker.value!=""){
		if(!sl_checkDate(document.theform.exp_reg_date_picker,"预计认购日期")){//预计认购日期
			return false;
		}
		syncDatePicker(form.exp_reg_date_picker, form.exp_reg_date);
	}
	//alert("'"+form.pre_end_date.value+"'");
	if(/*form.pre_end_date.value!="" && */form.exp_reg_date.value>form.pre_end_date.value){
		sl_alert("预计认购日期不能晚于截止日期!");
		return false;
	}
<%if (! isZt) {%>
	if (form.pre_level.value=="") {
		sl_alert("请选择预约级别!");
		return false;
	}
<%}%>

	//北京信托预约配额控制
	var pre_salemoney = document.getElementById("pre_salemoney").value;
	var quotamoney = document.getElementById("quotamoney").value;
	var pre_qualifiednum = document.getElementById("pre_qualifiednum").value;
	var quota_qualified_num = document.getElementById("quota_qualified_num").value;
	var pre_money = document.getElementById("pre_money").value;
	var current_money = document.getElementById("current_money").value;
	var current_num = document.getElementById("current_num").value;

	//if(form.channel_type.value.substring(0,6) != "550003")
		//document.theform.channel_fare.value = "";
	var choose = document.getElementById("pre_product_id");
	document.getElementById("product_name").value = choose.options[choose.selectedIndex].text;
	return sl_check_update();
}

/*刷新页面*/
function refreshPage(){
	disableAllBtn(true);
	syncDatePicker(document.theform.pre_date_picker, document.theform.pre_date);
	document.theform.action="direct_reserve_add.jsp";
	document.theform.method="get";
	document.theform.submit();
}

/*黑名单筛查*/
function checkBlack(){
	var button_save = document.getElementById("btnSave");
	var button_check_black = document.getElementById("btnCheckBlack");
	var accountManager = document.getElementById("accountManager").value;//客户经理
	var customerType = document.getElementById("customerType").value;//客户类型
	var customerSource = document.getElementById("customerSource").value;//客户来源
	var customer_post_address = document.getElementsByName("customer_post_address")[0].value;//(邮寄)地址
	
	var index = document.getElementsByName("country")[0].selectedIndex;
	var nationality = document.getElementsByName("country")[0].options[index].value;//客户国籍
	var nationalityText = document.getElementsByName("country")[0].options[index].text;//客户国籍文本
	var customer_sex_name = document.getElementById("customer_sex_name").value;//性别
	var customer_birthday_picker = document.getElementById("customer_birthday_picker").value;//出生日期	
	if(customer_birthday_picker!=""){
	var year = customer_birthday_picker.substring(0,4)+"-";
	var month = customer_birthday_picker.substring(4,6)+"-";
	var day = customer_birthday_picker.substring(6,8);
	customer_birthday_picker = year+month+day;	
	}
	//alert(customer_sex_name);
	//alert(customer_birthday_picker);
	//alert(customer_post_address);
	//alert(accountManager);
	//alert(customerType);
	//alert(customerSource);
	//var cust_name = document.theform.cust_name.value;	
	var   cust_name=document.getElementById("kehumingcheng");
	if(cust_name.value==""){
	alert("必须选择客户名称才能进行黑名单筛查");
	return false;
	}
	//alert(cust_name.value)	;
	button_save.style.visibility="visible";
	var ret = showModalDialog('<%=request.getContextPath()%>/marketing/purchase_info_check_black.jsp?cust_name=' + cust_name.value+'&accountManager='+accountManager+'&customerType='+customerType+'&customer_post_address='+customer_post_address+'&nationalityText='+nationalityText+"&customer_sex_name="+customer_sex_name+"&customer_birthday_picker="+customer_birthday_picker+"&nationality="+nationality, '', 'dialogWidth:1200px;dialogHeight:800px;status:0;help:0');
	return false;
}
/*过滤

/*客户信息*/
function getMarketingCustomer(prefix,readonly){
	cust_id = getElement(prefix, "cust_id").value;	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?select_flag=2&prefix='+ prefix+ '&cust_id=' + cust_id+'&readonly='+readonly ;
	
	v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');		
	if (v) {
		//showMarketingCustomer(prefix, v);
		document.theform.customer_cust_id.value =  v[7];
		refreshPage();
	}
	return v != null;
}
/*显示中文*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		pre_money_cn.innerText = " (<%=LocalUtilis.language("message.10000",clientLocale)%> )";//万
	else
		pre_money_cn.innerText = "(" + numToChinese(temp) + ")";
}

function showCnMoney1(value,name){
	if (trim(value) == "")
		name.innerText = "";
	else
		name.innerText = "(" + numToChinese(value) + ")";
}

/*页面初始化*/
window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		if(v_bSuccess=="true"){		
			window.returnValue = 1;
			window.close();
		}else{
			if(document.theform.pre_product_id.value > 0){
				showPreinfo(document.theform.pre_product_id.value);
			}
		}
		setManagerList('<%=Utility.parseInt(team_id,new Integer(0))%>','<%=input_operatorCode%>');
	};

/*获取已预约数目 已预约金额*/
function showPreinfo(pre_product_id){
	//产品信息
	utilityService.getPreInfoByPreId(pre_product_id,0,{callback: function(data){
		document.theform.product_total_money.value = data[0];	
		document.theform.pre_micro_num.value = data[1];
		document.theform.pre_total_num.value = data[1]+" / "+data[2];
		document.theform.pre_total_money.value = data[2];
		document.theform.pre_end_date.value = data[3];	
		document.theform.pre_end_date_display.value = formatDate(data[3], '-');
		document.theform.pre_total_money.value = data[4];
		document.theform.product_sy_money.value = data[5];
		document.theform.huge_money.value = data[6];
		document.theform.expre_end_time.value = data[7];
		document.theform.pre_valid_days.value = data[8];
	}});
	//配额信息
	getTempMountByProductIdAndServerMan();

	getProvFlag(pre_product_id);	
}

/*根据产品ID和预约登记人获取改团队的配额信息*/
function getTempMountByProductIdAndServerMan(){
	var pre_product_id = document.theform.pre_product_id.value;
	var link_man = 0;
	var team_id = document.theform.team_id.value;
	if(pre_product_id != 0 && (team_id != "" || link_man != "")){
		//alert(pre_product_id+"--"+link_man+"--"+team_id);
		utilityService.getTempMountByProductIdAndServerMan(pre_product_id,link_man,team_id,{callback: function(data){
			var num1 = data.PRE_SALEMONEY_STR;
			if(num1 == "")
				num1 = "0.00";
			var num2 = data.QUOTAMONEY_STR;
			if(num2 == "")
				num2 = "0.00";
			document.getElementById("input_info1").value = num1+"/"+num2;
			document.getElementById("input_info2").value = data.PRE_QUALIFIEDNUM+"/"+data.QUOTA_QUALIFIED_NUM;
			document.getElementById("input_info3").value = data.CURRENT_MONEY_STR;
			document.getElementById("input_info4").value = data.CURRENT_NUM;
		
			document.getElementById("pre_salemoney").value = data.PRE_SALEMONEY;
			document.getElementById("quotamoney").value = data.QUOTAMONEY;
			document.getElementById("pre_qualifiednum").value = data.PRE_QUALIFIEDNUM;
			document.getElementById("quota_qualified_num").value = data.QUOTA_QUALIFIED_NUM;
			document.getElementById("current_money").value = data.CURRENT_MONEY;
			document.getElementById("current_num").value = data.CURRENT_NUM;
		}});
	}
}

//设置渠道类别
function setChannelType(obj){
	var channel_type = obj.value;
	if(channel_type != ""){
		if(channel_type.substring(0,6) == "550003"){
			document.getElementById("channel_td1").style.display = "";
			document.getElementById("channel_td2").style.display = "";
		}else{
			document.getElementById("channel_td1").style.display = "none";
			document.getElementById("channel_td2").style.display = "none";
		}
	}
}

//查询客户信息
function queryCustInfo(){
	var query_cust_name = document.theform.query_cust_name.value;
	var query_card_id = document.theform.query_card_id.value;
	var query_cust_tel_val = document.theform.query_cust_tel_val.value;
	var input_operatorCode = document.theform.input_operatorCode.value;
	if(query_cust_name == "" && query_card_id == "" && query_cust_tel_val == ""){
		sl_alert("请输入查询条件：客户名称、证件号码或者联系电话！");
		return;
	}
	utilityService.getCustomerBy(query_cust_name,query_card_id,input_operatorCode,query_cust_tel_val,matchCustCallBack);
}

function matchCustCallBack(data){
	var obj_op = document.getElementById("query_result");  
    DWRUtil.removeAllOptions(obj_op);   
    DWRUtil.addOptions(obj_op,{'':'<%=LocalUtilis.language("message.select2",clientLocale)%> '});   //请选择
    DWRUtil.addOptions(obj_op,data);
}

//客户详细信息
function queryCustomerinfo(obj){
	if(obj.value != ""){
		ccService.getCustInfoById(obj.value,refreshCustInfoCallBack);
	}else{
		document.theform.customer_cust_name.style.display = "";
		document.theform.is_deal_picker.style.display = "";
		document.theform.cust_tel.style.display = "";
		document.theform.card_type.style.display = "";
		document.theform.card_id.style.display = "";
       // document.theform.query_cust_tel.style.display = "";
       // document.theform.mobile.style.display = "";
		document.theform.input_value1.style.display = "none";
		document.theform.input_value2.style.display = "none";
		document.theform.input_value3.style.display = "none";
		document.theform.input_value4.style.display = "none";
		document.theform.input_value5.style.display = "none";
       	//document.theform.input_value11.style.display = "none";
        //document.theform.input_value12.style.display = "none";
		//document.getElementById("tel_tr_id").style.display = "none";
		document.getElementById("service_man").style.display = "none";
		document.getElementById("service_man1").style.display = "none";
		document.getElementById("country_display").style.display = "";
		document.getElementById("country_display1").style.display = "";
		document.theform.input_value1.value = "";
		document.theform.input_value2.value = "";
		document.theform.input_value3.value = "";
		document.theform.input_value4.value = "";
		document.theform.input_value5.value = "";
		document.theform.input_value6.value = "";
       	//document.theform.input_value11.value = "";
		//document.theform.input_value12.value = "";
      	//document.theform.input_value13.value = "";
		//document.theform.input_value14.value = "";
		document.theform.customer_cust_id.value = "";
		document.getElementById("span_id1").innerText = "*联系电话:";
	}
}

function refreshCustInfoCallBack(data){
	var custJsonStr = data;
	custJson = eval(custJsonStr);
	if(custJson.length>0){
		document.theform.customer_cust_name.style.display = "none";
		document.theform.is_deal_picker.style.display = "none";
		document.theform.cust_tel.style.display = "none";
		document.theform.card_type.style.display = "none";
		document.theform.card_id.style.display = "none";
        //document.theform.query_cust_tel.style.display = "none";
       	//document.theform.mobile.style.display = "none";
		document.theform.input_value1.style.display = "";
		document.theform.input_value2.style.display = "";
		document.theform.input_value3.style.display = "";
		document.theform.input_value4.style.display = "";
		document.theform.input_value5.style.display = "";
        //document.theform.input_value11.style.display = "";
        //document.theform.input_value12.style.display = "";
		//document.getElementById("tel_tr_id").style.display = "";
		document.getElementById("service_man").style.display = "";
		document.getElementById("service_man1").style.display = "";
		document.getElementById("country_display").style.display = "none";
		document.getElementById("country_display1").style.display = "none";
		document.theform.input_value1.value = custJson[0].CUST_NAME;
		document.theform.input_value2.value = custJson[0].CUST_TYPE_NAME;
		document.theform.input_value3.value = custJson[0].CUST_SOURCE_NAME;
		document.theform.input_value4.value = custJson[0].CARD_TYPE_NAME;
		document.theform.input_value5.value = custJson[0].CARD_ID;
		document.theform.input_value6.value = custJson[0].SERVICE_MAN_NAME;
		document.getElementsByName("customer_post_address")[0].value = custJson[0].POST_ADDRESS;//邮寄地址
		document.getElementsByName("country")[0].value = custJson[0].COUNTRY;//国别
		document.getElementById("customer_sex_name").value = custJson[0].SEX_NAME;//性别
		document.getElementById("customer_birthday_picker").value = custJson[0].BIRTHDAY;//出生日期
       // document.theform.input_value11.value = custJson[0].H_TEL;
       // document.theform.input_value12.value = custJson[0].MOBILE;
       // document.theform.input_value13.value = custJson[0].O_TEL;
       // document.theform.input_value14.value = custJson[0].BP;
		document.theform.customer_cust_id.value = custJson[0].CUST_ID;
		document.theform.cust_type.value = custJson[0].CUST_TYPE;
		document.getElementById("span_id1").innerText = "*客户来源:";
	}else{
		document.theform.customer_cust_name.style.display = "";
		document.theform.is_deal_picker.style.display = "";
		document.theform.cust_tel.style.display = "";
		document.theform.card_type.style.display = "";
		document.theform.card_id.style.display = "";
       // document.theform.query_cust_tel.style.display = "";
        //document.theform.mobile.style.display = "";
		document.theform.input_value1.style.display = "none";
		document.theform.input_value2.style.display = "none";
		document.theform.input_value3.style.display = "none";
		document.theform.input_value4.style.display = "none";
		document.theform.input_value5.style.display = "none";
        //document.theform.input_value11.style.display = "none";
       // document.theform.input_value12.style.display = "none";
		//document.getElementById("tel_tr_id").style.display = "none";
		document.getElementById("service_man").style.display = "none";
		document.getElementById("service_man1").style.display = "none";
		document.theform.input_value1.value = "";
		document.theform.input_value2.value = "";
		document.theform.input_value3.value = "";
		document.theform.input_value4.value = "";
		document.theform.input_value5.value = "";
		document.theform.input_value6.value = "";
        //document.theform.input_value11.value = "";
        //document.theform.input_value12.value = "";
       // document.theform.input_value13.value = "";
        //document.theform.input_value14.value = "";
		document.theform.customer_cust_id.value = "";
		document.getElementById("span_id1").innerText = "*联系电话:";
	}
}
//显示证件类型
function changeType(value){
	if(value == 1){
		document.getElementById("card_type").style.display = "";
		document.getElementById("card_type1").style.display ="none";
	}else{
		document.getElementById("card_type").style.display = "none";
		document.getElementById("card_type1").style.display = "";
	}
}

/*显示账号位数*/
function showAcctNum(value){
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

/*通过团队过滤客户经理列表*/
function setManagerList(team_id,manager_id){
	//if(team_id != "" && team_id.length > 0){
	getTempMountByProductIdAndServerMan();
	utilityService.getManagerList(team_id,manager_id,backList);
	//}
}

/*通过团队过滤客户经理列表 回调函数*/
function backList(data){
	document.getElementById("manager_list").innerHTML = "<select size='1'  name='link_man' style='width: 158px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
}

function getProvFlag(preproduct_id) {
	utilityService.getPreproductProvFlag(preproduct_id, gotProvFlag);
}

function gotProvFlag(json) {
	var preproduct_id = document.theform.pre_product_id.value;
	var arr = eval(json);

	document.theform.prov_flag.options.length = 0;
	document.theform.prov_level.options.length = 0;

	for (var i=0; i<arr.length; i++)
		document.theform.prov_flag.options.add(new Option(arr[i].PROV_FLAG_NAME, arr[i].PROV_FLAG));
	
	if (document.theform.prov_flag.options.length > 0) {
		document.theform.prov_flag.options.selectedIndex = 0;
		getProvLevel(preproduct_id, document.theform.prov_flag.options[0].value);
	}
}

function getProvLevel(preproduct_id, prov_flag) {
	utilityService.getPreproductProvLevel(preproduct_id, prov_flag, <%=input_operatorCode%>, gotProvLevel);
}

function gotProvLevel(json) {
	var arr = eval(json);
	document.theform.prov_level.options.length = 0;
	for (var i=0; i<arr.length; i++)
		document.theform.prov_level.options.add(new Option(arr[i].PROV_LEVEL_NAME, arr[i].PROV_LEVEL));

	if (document.theform.prov_level.options.length > 0)
		document.theform.prov_level.options.selectedIndex = 0;
}

function autoSetGainLevel(preproduct_id, money) {
	if (preproduct_id==0 || money == "") return;
	utilityService.getPreproductProvLevel(preproduct_id, 0, <%=input_operatorCode%>, autoSetGainLevel_callback);
}

function autoSetGainLevel_callback(json) {
	var money = document.theform.pre_money.value;
	var arr = eval(json);
	var i;
	var matched = [];
	for (i=0; i<arr.length; i++) 
		if (money>=arr[i].LOWER_LIMIT && money<=arr[i].UPPER_LIMIT) matched.push(i);

	if (matched.length==0) { // 找不到匹配的
		sl_alert("没有可以与预约金额匹配的收益级别!");

		document.theform.prov_flag_display.value = "";
		document.theform.prov_level_display.value = "";

	} else /*if (matched.length==1)*/ {
		i = matched[0];

		var prov_flag = document.theform.prov_flag;		
		for (var j=0; j<prov_flag.options.length; j++) {
			if (prov_flag.options[j].value == arr[i].PROV_FLAG) {
				prov_flag.selectedIndex = j;
				break;
			}			
		}

		var prov_level = document.theform.prov_level;
		for (var j=0; j<prov_level.options.length; j++) {
			if (prov_level.options[j].value == arr[i].PROV_LEVEL) {
				prov_level.selectedIndex = j;
				break;
			}
		}

		document.theform.prov_flag_display.value = arr[i].PROV_FLAG_NAME;
		document.theform.prov_level_display.value = arr[i].PROV_LEVEL_NAME;

	}/* else { // matched.length>1
		sl_alert("有多个可以与预约金额匹配的收益级别,请选择!"); 
	}*/
}
</script>
</HEAD>
<BODY class="BODY  body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" onsubmit="javascript: return validateForm(this);" encType="application/x-www-form-urlencoded" method = "GET">
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/><!--新增成功标志-->
<input type="hidden" name="customer_cust_id" value="<%=cust_id%>"/>
<input type="hidden" name="cust_type" value="<%=cust_type%>"/>
<input type="hidden" name="pre_micro_num" value="">
<input type="hidden" name="pre_salemoney" id="pre_salemoney" value="">
<input type="hidden" name="quotamoney" id="quotamoney" value="">
<input type="hidden" name="pre_qualifiednum" id="pre_qualifiednum" value="">
<input type="hidden" name="quota_qualified_num" id="quota_qualified_num" value="">
<input type="hidden" name="current_money" id="current_money" value="">
<input type="hidden" name="current_num" id="current_num" value="">
<input type="hidden" name="huge_money" id="huge_money" value="">
<input type="hidden" name="customer_sex_name" id="customer_sex_name" value=""/>
<input type="hidden" name="customer_birthday_picker" id="customer_birthday_picker" value=""/>
<input type="hidden" name="product_name" id="product_name" value="">
<input type="hidden" name="input_operatorCode" id="input_operatorCode" value="<%=input_operatorCode%>">
<input type="hidden" name="return_url" id="return_url" value="<%=return_url%>">
<input type="hidden" name="from" value="<%=from%>"/>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.salesManagerment",clientLocale)%>&nbsp;>&nbsp;<%=LocalUtilis.language("menu.bespeak",clientLocale)%> </b></font><!--销售管理>>预约-->
</div>
<!--客户选择-->
<div vAlign=top align=left class="direct-panel ">
<fieldset>
<legend><b style="color: blue;">客户信息</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0 class="table-popup">
	<tr>
		<td colspan="4"><b>&nbsp;&nbsp;&nbsp;客户查询</b></td>
	</tr>
	<tr >	
		<td align="right" colspan="1">客户名称：</td>
		<td colspan="1"><input maxlength="100" name="query_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);"></td>
		<td align="right" colspan="1">证件号码：</td>
		<td><input maxlength="100" name="query_card_id" size="27" onkeydown="javascript:nextKeyPress(this);"></td>
		
	</tr>
	<tr>
		<td align="right" colspan="1">查询结果：</td>	
		<td colspan="1">
			<select style="width:158px;" id="query_result" name="query_result"  onclick="javascript:queryCustomerinfo(this);"></select>
		</td>
		<td align="right" colspan="1">联系电话：</td>
		<td colspan="1"><input maxlength="100" name="query_cust_tel_val" size="27" onkeydown="javascript:nextKeyPress(this);"><button type="button"  class="xpbutton3" onclick="javascript:queryCustInfo();">查询</button></td>

	</tr>
<tr>
	<td align="right" >*客户名称:</td>
	<td>
		<input maxlength="100" name="customer_cust_name" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name %>" />
		<input maxlength="100" readonly="readonly" class="edline" name="input_value1" size="27" style="display: none;" id="kehumingcheng"/>
		<input type="hidden" name = "customer_post_address" value=""/><!--邮寄地址 -->
	</td>
	<td align="right" id="country_display"><%=LocalUtilis.language("class.countryName",clientLocale)%>:</td><!--国别-->
	<td id="country_display1">
		<select size="1"  name="country"  id="country" style="width: 158px" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getDictParamOptions(9997,"9997CHN")%>
		</select>
	</td>
	<td align="right" style="display: none;" id="service_man"><%=LocalUtilis.language("class.accountManager",clientLocale)%>:</td><!--客户经理--> 
	<td style="display: none;" id="service_man1">
		<input maxlength="100" readonly="readonly" class="edline" name="input_value6" size="27" id="accountManager">
	</td>
</tr>
<tr>
	<td align="right" width="20%">客户类型:</td>
	<td width="25%">
		<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px" onchange="javascript:changeType(this.value);">	
			<option value="0">请选择</option>
			<option value="1" <%=cust_type.intValue() == 1 ? "selected" : ""%>>个人</option>
			<option value="2" <%=cust_type.intValue() == 2 ? "selected" : ""%>>机构</option>
		</select>
		<input readonly="readonly" class="edline" name="input_value2" size="27" style="display: none;" id="customerType">
	</td>
	<td align="right" width="20%"><span id="span_id1">联系电话:</span></td>
	<td width="25%">
		<input maxlength="100" name="cust_tel" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_tel %>">
		<input maxlength="100" readonly="readonly" class="edline" name="input_value3" size="27" style="display: none;" id="customerSource">
	</td>
</tr>
<tr>
	<td align="right">证件类型:</td>
	<td >
		<select onkeydown="javascript:nextKeyPress(this)" id ="card_type" name="card_type" style="WIDTH: 160px">
			<%=Argument.getCardTypeOptions2(cust_type+"")%>
		</select>
		<select onkeydown="javascript:nextKeyPress(this)" id ="card_type1"name="card_type1" style="WIDTH: 160px;display:none" >
			<%=Argument.getCardTypeJgOptions2(card_type)%>
		</select>	
		<input maxlength="100" readonly="readonly" class="edline" name="input_value4" size="27" style="display: none;">
	</td>
	<td align="right">证件号码:</td>
	<td >
		<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=card_id %>" onkeyup="javascript:showAcctNum(this.value)">
		<input maxlength="100" readonly="readonly" class="edline "name="input_value5" size="27" style="display: none;">
		<span id="bank_acct_num" class="span"></span>
	</td>
</tr>
<!--  
	<tr>
		<td align="right">家庭电话:</td>
		<td >
	        <input maxlength="100" name="query_cust_tel" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
			<input maxlength="100" readonly="readonly" class="edline" name="input_value11" size="27" style="display: none;">
		</td>
		<td align="right">手机号码1:</td>
		<td >
	        <input maxlength="100" name="mobile" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
			<input maxlength="100" readonly="readonly" class="edline" name="input_value12" size="27" style="display: none;">
		</td>
	</tr>
	<tr style="display: none;" id="tel_tr_id">
		<td align="right">公司电话:</td>
		<td >
			<input maxlength="100" readonly="readonly" class="edline" name="input_value13" size="27">
		</td>
		<td align="right">手机号码2:</td>
		<td >
			<input maxlength="100" readonly="readonly" class="edline" name="input_value14" size="27">
		</td>
	</tr>
-->
</table>
</fieldset>
<fieldset>
<legend><b style="color: blue;">产品信息</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0 class="table-popup">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
			<td colspan="3">
				<select size="1" id="pre_product_id" name="pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onChange="javascript:showPreinfo(this.value);">
					<%=Argument.getPreProductListOptions(pre_product_id,"","110202",input_operatorCode)%>
				</select>
			</td>
 			
		</tr>
		<tr>
			<td align="right" width="20%">产品总规模:</td>
			<td width="25%"><input readonly class="edline" name="product_total_money" size="27" value=""></td>
			<td align="right" width="20%">截止日期:</td>
			<td width="25%"><input readonly class="edline" name="pre_end_date_display" size="27"/><input type="hidden" name="pre_end_date"/></td>
		</tr>
		<tr style="display: none;">
			<td align="right">小额份数/累计份数:</td>
			<td><input readonly class="edline" name="pre_total_num"  size="27" value=""></td>
			<td align="right">截止日期:</td>
			<td><input readonly class="edline" name="pre_end_date1"  size="27" value=""></td>
		</tr>	
		<tr style="display: none;">
			<td align="right">已预约规模:</td>
			<td><input readonly class="edline" name="pre_total_money" size="27" value=""/></td>
			<td align="right">可预约规模:</td>
			<td><input readonly class="edline" name="product_sy_money" size="27" value=""/></td>
		</tr>

		<tr style="display:<%=notNeedQuota?"none":""%>">
			<td align="right">已预约金额/团队金额配额:</td>
			<td><input readonly class="edline" name="input_info1" id="input_info1" size="35" value=""/></td>
			<td align="right">已预约小额份数/合格投资人数量配额:</td>
			<td><input readonly class="edline" name="input_info2" id="input_info2" size="27" value=""/></td>
		</tr>
		<tr style="display:<%=notNeedQuota?"none":""%>">
			<td align="right">当前可预约金额:</td>
			<td><input readonly class="edline" name="input_info3" id="input_info3" size="27" value=""/></td>
			<td align="right">当前可小额预约份数:</td>
			<td><input readonly class="edline" name="input_info4" id="input_info4" size="27" value=""/></td>
		</tr>

		<tr>
			<td align="right">*预约团队:</td><!--预约登记人-->
			<td><select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 158px" onchange="javascript:setManagerList(this.value,'<%=input_operatorCode%>');">
				<%=Argument.getManagerTeams(team_id)%>
				</select>
			</td>
			<td align="right">*预约登记人:</td><!--预约登记人-->
			<td>
				<span id="manager_list">
					<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 158px">
						<option value="">请选择</option>
					</select>
				</span>
			</td>
		</tr>
		<tr>
			<td align="right" width="20%">预约截止时间:</td>
			<td width="25%"><input readonly class="edline" name="expre_end_time" size="27"/></td>
			<td align="right" width="20%">预约有效天数:</td>
			<td width="25%"><input readonly class="edline" name="pre_valid_days"  size="27"/></td>
		</tr>
</TABLE>
</fieldset>
<fieldset>
<legend><b style="color: blue;">预约信息</b></legend>
<table cellSpacing=0 cellPadding=3 width="100%" border=0 class="table-popup">
		<tr>
			<td align="right" width="20%">*<%=LocalUtilis.language("class.factPreNum2",clientLocale)%>:</td><!--预约金额-->
			<td width="25%">
				<input name="pre_money" id="pre_money" size="27" value="" onkeydown="javascript:nextKeyPress(this)" 
					onkeyup="javascript:showCnMoney(this.value)" onchange="javascript:autoSetGainLevel(document.theform.pre_product_id.value,this.value);"/>
				<span id="pre_money_cn" class="span">&nbsp;</span><!--万-->
			</td>
			<td align="right" width="20%">*<%=LocalUtilis.language("class.preNum2",clientLocale)%>:</td><!--预约份数-->
			<td width="25%"><input readonly class="edline"  name="pre_num" onkeydown="javascript:nextKeyPress(this)" size="27" value="<%=pre_num%>"/></td>
			<!-- <td>
				<select name="pre_num" >
					<%=Argument.getPreNumOptions(pre_num)%>
				</select>
			 -->
			</td>
		</tr>
		<tr>
			<td align="right"><%=isZt?"":"*"%><%=LocalUtilis.language("class.preType",clientLocale)%>:</td><!--预约方式-->
			<td>
				<select size="1" name="pre_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getPreTypeOptions(pre_type)%>
				</select>
			</td>
			<td align="right"><%=isZt?"":"*"%>预约级别:</td><!--预约级别-->
			<td>
				<select size="1" name="pre_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getDictParamOptions(1182,pre_level)%>
				</select>
			</td>
		</tr>		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.preDate",clientLocale)%>:</td><!--预约日期-->
			<td><INPUT TYPE="text" NAME="pre_date_picker" id="pre_date_picker" class=selecttext size="27"
			<%if(preDate==null||preDate.intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
			<%}else{%>value="<%=Format.formatDateLine(preDate)%>"<%}%> >
			<INPUT TYPE="hidden" NAME="pre_date"   value=""></td>
			<td align="right">预计认购日期:</td><!--预计认购日期-->
			<td>
				<INPUT TYPE="text" NAME="exp_reg_date_picker" id="exp_reg_date_picker" class=selecttext size="27"
				<%if(expRegDate==null||expRegDate.intValue()==0){%>
					value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>
					value="<%=Format.formatDateLine(expRegDate)%>"
				<%}%> >
				<INPUT TYPE="hidden" NAME="exp_reg_date" value="">
			</td>
			<script language="javascript">
				laydate({elem:'#pre_date_picker',format:'YYYY-MM-DD',istime:false});
				laydate({elem:'#exp_reg_date_picker',format:'YYYY-MM-DD',istime:false});
			</script>
		</tr>
		<tr>
			<td align="right">受益优先级:</td>
			<td>
				<input readonly class="edline" name="prov_flag_display" onkeydown="javascript:nextKeyPress(this)" size="27"/>
				<select size="1" name="prov_flag" onkeydown="javascript:nextKeyPress(this)" 
					onchange="javascript:getProvLevel(document.theform.pre_product_id.value,this.value);" style="WIDTH: 160px;display:none"></select>
			</td>
			<td align="right">收益级别:</td>
			<td>
				<input readonly class="edline" name="prov_level_display" onkeydown="javascript:nextKeyPress(this)" size="27"/>
				<select size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px;display:none"></select>
			</td>
		</tr>
		<tr>
			<td align="right">渠道类别:</td>
			<td>
				<select size="1" name="channel_type" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 160px">
					<%=Argument.getDictParamOptions(5500, channel_type)%>
				</select>
			</td>
			<td align="right" id="channel_td1">渠道费用:</td>
			<td id="channel_td2">
				<input type="text" name="channel_fare" value="<%=Format.formatMoney(channel_fare)%>" size="27" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney1(this.value,channel_fare_cn)"><span id="channel_fare_cn" class="span">&nbsp;</span>
			</td>
		</tr>		
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%>:</td><!--备注-->
			<td colspan="3"><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="80"><%=summary%></textarea></td>
		</tr>
</table>
</fieldset>
</div>
<br>
<div align="right" style="margin-right:5px">
	<% if(user_id.intValue()==2){%><!--北京实际的那边的值是2 -->
	<button class="xpbutton3" id="btnCheckBlack" name="btnCheckBlack" onclick="javascript:checkBlack();">黑名单筛查</button>
	<input type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();" style="visibility: hidden" value = "<%=LocalUtilis.language("message.save",clientLocale)%>(S)">
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<%}else{%>
	<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;<!--保存-->
	<%}%>
	<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" 
		onclick="javascript:<%=from_main?("location.href='"+return_url+"';"):"window.close();"%>"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	<!--返回-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>