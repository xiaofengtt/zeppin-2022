<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.service.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<jsp:directive.page import="com.enfo.webservice.client.SAPClient"/>
<%@ page import="com.jspsmart.upload.SmartUpload,java.io.*"%>
<%@ include file="/includes/operator.inc" %>
<%-- 机构客户的新建 --%>
<%--1.CARD_ID与H_CARD_ID值一样，但是CARD_ID 可能被加密，H_CARD_ID为完整的未加密信息，H是hidden的意思，其他EMAIL、MOBEIL等都是这个意思 --%>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
boolean edit = cust_id.intValue()>0;

String iQuery = request.getParameter("iQuery");
Integer showFlag =  Utility.parseInt(Utility.trimNull(request.getParameter("showFlag")),new Integer(edit && user_id.intValue()==15/*建信*/?2/*联系信息*/:1));
Integer imageShowFlag = new Integer(0);
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal2")),new Integer(0));

//页面变量
boolean isCcb =user_id.intValue() == 15/*15建信*/;
boolean bSuccess = false;
int iCount = 0;
List list = new ArrayList();
Map map = new HashMap();
String prompt = "";
Integer new_cust_id = null;
//图片保存辅助变量
byte[] bx = new byte[0];
java.io.InputStream ins = new java.io.ByteArrayInputStream(bx);
java.io.InputStream in = null;

//获得对象
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();

String returnV = ""; //cust_local.listByAll(2);
if(user_id.intValue() == 17/*方正*/ || user_id.intValue()==22/*中投*/ || user_id.intValue()==999) { //方正东亚需求在界面录入客户名称时动态判断，开启该功能时数据量大时明显会慢很多
	returnV = cust_local.listByAll(2);
}

//将图片转换成二进制流在保存
SmartUpload su = new SmartUpload();
if (request.getMethod().equals("POST")){
	// 上传初始化
	su.initialize(pageContext);
	su.setTotalMaxFileSize(50000000);
	su.setAllowedFilesList("jpg"); //允许的格式
	su.upload();

	cust_id = Utility.parseInt(su.getRequest().getParameter("cust_id"),new Integer(0));
	edit = cust_id.intValue()>0;

	is_deal = Utility.parseInt(Utility.trimNull(su.getRequest().getParameter("is_deal2")),new Integer(0));
	showFlag = Utility.parseInt(Utility.trimNull(su.getRequest().getParameter("showFlag")),new Integer(1));
	imageShowFlag = Utility.parseInt(Utility.trimNull(su.getRequest().getParameter("imageShowFlag")),new Integer(0));
	iQuery = su.getRequest().getParameter("iQuery");

	Integer cust_flag = Utility.parseInt(Utility.trimNull(request.getParameter("cust_flag")),new Integer(2));
	Integer true_flag = Utility.parseInt(su.getRequest().getParameter("true_flag"), new Integer(1));
	//String temp = Utility.trimNull(true_flag) + Utility.trimNull(cust_flag);
	//cust_flag = Utility.parseInt(temp,new Integer(11));
	Integer forbid_flag = Utility.parseInt(su.getRequest().getParameter("forbid_flag"), new Integer(0));
	String _cust_flag = true_flag.toString() + cust_flag.toString()+ is_deal.toString() + forbid_flag.toString();
	/* 客户标识：第1位：客户的真实性核查[1未核查(默认)，2真实，3待核查(已经核实过但不确定结果)];
		第2位：证件号码进行唯一性校验[2不校验，1校验(默认)];     第3位：是否事实客户[2潜在客户(默认)，1事实客户]
		第4位:是否禁购客户[1是0否] */

	// 逐一提取上传文件信息，同时可保存文件。
	for (int i = 0; i < su.getFiles().getCount(); i++) {
		com.jspsmart.upload.File file = su.getFiles().getFile(i);
		// 若文件不存在则继续
		if (file.isMissing()) continue;

		int iFileSize = file.getSize();
		if (iFileSize == 0) continue;

		String fieldName = file.getFieldName();//file.getFileName(); // 
		if (fieldName.equals("imageIdentification")) {
			if (iFileSize > 5* 1024 * 1024)
				throw new BusiException("文件大小不能超过5M！");
	
			bx = new byte[file.getSize()];
			for (int ii = 0; ii != file.getSize(); ii++) {
				bx[ii] = file.getBinaryData(ii);
			}
			ins = new java.io.ByteArrayInputStream(bx);
			break; //
		}
	}

	//如果没有变更图片，且有原图片的，从服务器本地指定路径读入
	if(ins.available()==0&&cust_id.intValue()>0&&imageShowFlag.intValue()>0){
		String filePathName = "c:\\temp\\Cust_"+cust_id+".jpg";
		java.io.File myFile = new java.io.File(filePathName);
		if (myFile.exists()) {
			java.io.FileInputStream fileInStream = new java.io.FileInputStream(filePathName);
			java.io.DataInputStream dataInStream = new java.io.DataInputStream(new java.io.BufferedInputStream(fileInStream));
	
			bx = new byte[fileInStream.available()];
	
			while(dataInStream.read(bx)!=1){
				ins = new java.io.ByteArrayInputStream(bx);
				break;
			}
		}
	}
	//保存输入流
	cust_vo.setInputStream(ins);
	//保存输入信息
	cust_vo.setCust_id(cust_id);
	cust_vo.setBook_code(input_bookCode);
	cust_vo.setCust_no(su.getRequest().getParameter("cust_no"));
	cust_vo.setCust_name(su.getRequest().getParameter("cust_name"));
	if(su.getRequest().getParameter("h_tel")!=null&&!su.getRequest().getParameter("h_tel").equals(""))
		cust_vo.setCust_tel(su.getRequest().getParameter("h_tel"));
	else if(su.getRequest().getParameter("o_tel")!=null&&!su.getRequest().getParameter("o_tel").equals(""))
		cust_vo.setCust_tel(su.getRequest().getParameter("o_tel"));
	cust_vo.setPost_address(su.getRequest().getParameter("post_address"));
	cust_vo.setPost_code(su.getRequest().getParameter("post_code"));
	cust_vo.setCard_type(su.getRequest().getParameter("card_type"));
	cust_vo.setCard_id(su.getRequest().getParameter("card_id"));
	cust_vo.setAge(new Integer(Utility.parseInt(su.getRequest().getParameter("age"), 0)));
	cust_vo.setSex(new Integer(Utility.parseInt(su.getRequest().getParameter("sex"), 1)));
	cust_vo.setO_tel(su.getRequest().getParameter("o_tel"));
	cust_vo.setH_tel(su.getRequest().getParameter("h_tel"));
	cust_vo.setMobile(su.getRequest().getParameter("mobile"));
	cust_vo.setBp(su.getRequest().getParameter("bp"));
	cust_vo.setFax(su.getRequest().getParameter("fax"));
	cust_vo.setE_mail(su.getRequest().getParameter("e_mail"));
	cust_vo.setWt_flag(new Integer(2));   //这个参数已经作废，所以随便传个值进去
	cust_vo.setCust_type(new Integer(2));
	cust_vo.setCust_level(su.getRequest().getParameter("cust_level"));
	cust_vo.setTouch_type(su.getRequest().getParameter("touch_type"));
	cust_vo.setCust_source(su.getRequest().getParameter("cust_source"));
	cust_vo.setSummary(su.getRequest().getParameter("summary"));
	cust_vo.setCheck_man(new Integer(Utility.parseInt(su.getRequest().getParameter("check_man"), 0)));
	cust_vo.setInput_man(input_operatorCode);
	cust_vo.setLegal_man(Utility.trimNull(su.getRequest().getParameter("legal_man")));
	cust_vo.setLegal_address(Utility.trimNull(su.getRequest().getParameter("legal_address")));
	cust_vo.setBirthday(Utility.parseInt(su.getRequest().getParameter("birthday"),null));
	cust_vo.setPost_address2(su.getRequest().getParameter("post_address2"));
	cust_vo.setPost_code2(su.getRequest().getParameter("post_code2"));
	cust_vo.setPrint_deploy_bill(new Integer(Utility.parseInt(su.getRequest().getParameter("print_deploy_bill"), 0)));
	cust_vo.setPrint_post_info(new Integer(Utility.parseInt(su.getRequest().getParameter("print_post_info"), 0)));
	cust_vo.setIs_link(Utility.parseInt(su.getRequest().getParameter("is_link"), new Integer(0)));
	cust_vo.setVip_date(Utility.parseInt(su.getRequest().getParameter("vip_date"),new Integer(0)));
	cust_vo.setVip_card_id(Utility.trimNull(su.getRequest().getParameter("vip_card_id")));
	cust_vo.setHgtzr_bh(Utility.trimNull(su.getRequest().getParameter("hgtzr_bh")));
	cust_vo.setVoc_type(Utility.trimNull(su.getRequest().getParameter("zyhy_type")));
	cust_vo.setCard_valid_date(Utility.parseInt(su.getRequest().getParameter("card_valid_date"),null));
	cust_vo.setCountry(Utility.trimNull(su.getRequest().getParameter("country")));
	cust_vo.setJg_cust_type(Utility.trimNull(su.getRequest().getParameter("jg_cust_type"))); // 页面上没有这个表单元素，取代此的是east_jg_type
	cust_vo.setContact_man(Utility.trimNull(su.getRequest().getParameter("contact_man")));
	cust_vo.setPotenital_money(
		Utility.parseDecimal( Utility.trimNull(su.getRequest().getParameter("potential_mongy")),new BigDecimal(0.00),2,"1")
	);
	cust_vo.setGov_prov_regional(Utility.trimNull(su.getRequest().getParameter("gov_prov_regional")));
	cust_vo.setGov_regional(Utility.trimNull(su.getRequest().getParameter("gov_regional")));
	cust_vo.setCountry(Utility.trimNull(su.getRequest().getParameter("country")));
	cust_vo.setMoney_source(Utility.trimNull(su.getRequest().getParameter("money_source")));
	cust_vo.setMoney_source_name(Utility.trimNull(su.getRequest().getParameter("money_source_name")));
	cust_vo.setEast_jg_type(Utility.trimNull(su.getRequest().getParameter("jg_type"))); // East_jg_type
	cust_vo.setJg_wtrlx2(Utility.trimNull(su.getRequest().getParameter("jg_wtrlx2")));

	//增加客户经理维护
	cust_vo.setService_man(Utility.parseInt(su.getRequest().getParameter("service_man"),new Integer(0)));
	cust_vo.setRecommended(Utility.trimNull(su.getRequest().getParameter("recommended")));

	//财务及偏好信息
	cust_vo.setPersonal_income(Utility.parseDecimal(su.getRequest().getParameter("personal_income"),new BigDecimal(0)));
	cust_vo.setHousehold_income(Utility.parseDecimal(su.getRequest().getParameter("household_income"),new BigDecimal(0)));
	cust_vo.setFeeding_num_people(Utility.parseInt(su.getRequest().getParameter("feeding_num_people"),new Integer(0)));
	cust_vo.setMain_source(Utility.trimNull(su.getRequest().getParameter("main_source")));
	cust_vo.setOther_source(Utility.trimNull(su.getRequest().getParameter("other_source")));
	cust_vo.setHouse_position(Utility.trimNull(su.getRequest().getParameter("house_position")));
	cust_vo.setHouse_property(Utility.parseInt(Utility.trimNull(su.getRequest().getParameter("house_property")),new Integer(0)));
	cust_vo.setHouse_area(Utility.parseInt(Utility.trimNull(su.getRequest().getParameter("house_area")),new Integer(0)));
	cust_vo.setPlat_envaluate(Utility.trimNull(su.getRequest().getParameter("plat_envaluate")));
	cust_vo.setMarket_appraisal(Utility.parseDecimal(su.getRequest().getParameter("market_appraisal"),new BigDecimal(0)));
	cust_vo.setVehicle_num(Utility.parseInt(su.getRequest().getParameter("vehicle_num"),new Integer(0)));
	cust_vo.setVehicle_make(Utility.trimNull(su.getRequest().getParameter("vehicle_make")));
	cust_vo.setVehicle_type(Utility.trimNull(su.getRequest().getParameter("vehicle_type")));
	cust_vo.setCredit_type(Utility.trimNull(su.getRequest().getParameter("credit_type")));//贷款种类
	cust_vo.setCredit_num(Utility.parseInt(su.getRequest().getParameter("credit_num"),new Integer(0)));
	cust_vo.setCredit_start_date(Utility.parseInt(su.getRequest().getParameter("credit_start_date"),new Integer(0)));
	cust_vo.setCredit_end_date(Utility.parseInt(su.getRequest().getParameter("credit_end_date"),new Integer(0)));
	cust_vo.setOther_investment_status(Utility.trimNull(su.getRequest().getParameter("other_investment_status")));
	cust_vo.setType_pref(Utility.trimNull(su.getRequest().getParameter("type_pref")));
	cust_vo.setTime_limit_pref(Utility.trimNull(su.getRequest().getParameter("time_limit_pref")));
	cust_vo.setInvest_pref(Utility.trimNull(su.getRequest().getParameter("invest_pref")));
	cust_vo.setHobby_pref(Utility.trimNull(su.getRequest().getParameter("hobby_pref")));
	cust_vo.setService_pref(Utility.trimNull(su.getRequest().getParameter("service_pref")));
	cust_vo.setContact_pref(Utility.trimNull(su.getRequest().getParameter("contact_pref")));
	cust_vo.setRisk_pref(Utility.trimNull(su.getRequest().getParameter("risk_pref")));

	cust_vo.setFree_cash_flow(Utility.parseDecimal(Utility.trimNull(su.getRequest().getParameter("free_cash_flow")),new BigDecimal(0)));
	cust_vo.setBurend_of_debt(Utility.parseDecimal(Utility.trimNull(su.getRequest().getParameter("burend_of_debt")),new BigDecimal(0)));

	//客户详细信息
	cust_vo.setHead_office_cust_id(Utility.trimNull(su.getRequest().getParameter("head_office_cust_id")));
	cust_vo.setStature(Utility.parseInt(su.getRequest().getParameter("stature"),new Integer(0)));
	cust_vo.setWeight(Utility.parseInt(su.getRequest().getParameter("weight"),new Integer(0)));
	cust_vo.setSpouse_name(Utility.trimNull(su.getRequest().getParameter("spouse_name")));
	cust_vo.setSpouse_info(Utility.trimNull(su.getRequest().getParameter("spouse_info")));
	cust_vo.setChildren_name(Utility.trimNull(su.getRequest().getParameter("children_name")));
	cust_vo.setChildren_info(Utility.trimNull(su.getRequest().getParameter("children_info")));
	cust_vo.setNation(Utility.trimNull(su.getRequest().getParameter("nation")));
	cust_vo.setMarital(Utility.parseInt(su.getRequest().getParameter("marital"),new Integer(0)));
	cust_vo.setHealth(Utility.trimNull(su.getRequest().getParameter("health")));
	cust_vo.setEducation(Utility.trimNull(su.getRequest().getParameter("education")));
	cust_vo.setPost(Utility.trimNull(su.getRequest().getParameter("post")));
	cust_vo.setHolderofanoffice(Utility.trimNull(su.getRequest().getParameter("holderofanoffice")));
	cust_vo.setCompany_character(Utility.trimNull(su.getRequest().getParameter("company_character")));
	cust_vo.setCompany_staff(Utility.parseInt(su.getRequest().getParameter("company_staff"),new Integer(0)));
	cust_vo.setBocom_staff(Utility.parseInt(su.getRequest().getParameter("bocom_staff"),new Integer(0)));

	cust_vo.setClient_quale(Utility.trimNull(su.getRequest().getParameter("client_quale")));
	cust_vo.setRegistered_place(Utility.trimNull(su.getRequest().getParameter("registered_place")));
	cust_vo.setRegistered_capital(Utility.parseDecimal(su.getRequest().getParameter("registered_capital"),new BigDecimal(0)));
	cust_vo.setEmployee_num(Utility.parseInt(su.getRequest().getParameter("employee_num"),new Integer(0)));//员工数
	cust_vo.setHolding(Utility.trimNull(su.getRequest().getParameter("holding")));//控股相关信息

	//cust_vo.setTrue_flag(true_flag);//客户标识：第1位：客户的真实性核查[1未核查(默认)，2真实，3待核查(已经核实过但不确定结果)];第2位：证件号码进行唯一性校验[2不校验，1校验(默认)];
 	cust_vo.setCust_flag(_cust_flag);
	/* 客户标识：第1位：客户的真实性核查[1未核查(默认)，2真实，3待核查(已经核实过但不确定结果)];
		第2位：证件号码进行唯一性校验[2不校验，1校验(默认)];     第3位：是否事实客户[2潜在客户(默认)，1事实客户]
		第4位:是否禁购客户[1是0否] */

	//cust_id或为0表示添加，则修改
	if (cust_id.intValue() == 0){
		if(is_deal.intValue()==1){
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			new_cust_id =  cust_local.append(cust_vo);
			//cust_vo.setCust_id(new_cust_id);
			//customer_inst.cope_customers(cust_vo);
		}
		else if(is_deal.intValue()==2){
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			new_cust_id =  cust_local.append2(cust_vo);
		}

		cust_id = new_cust_id;
		
		bSuccess = true;
	}
	else{
		if(is_deal.intValue()==1){
			if (user_id.intValue()!=15) customer_inst.cope_customers(cust_vo); //建信CRM的客户信息不同步给INTRUST
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			cust_local.modify(cust_vo);
		}
		else if(is_deal.intValue()==2){
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			cust_local.modify2(cust_vo);
		}
		
		bSuccess = true;
	}

	bSuccess = false;
	cust_vo.setCust_id(cust_id); //
	cust_vo.setBusiness_code(Utility.trimNull(su.getRequest().getParameter("business_code")));
	cust_vo.setOrgan_code(Utility.trimNull(su.getRequest().getParameter("organ_code")));
	cust_vo.setTax_reg_code(Utility.trimNull(su.getRequest().getParameter("tax_reg_code")));
	cust_vo.setLegal_name(Utility.trimNull(su.getRequest().getParameter("legal_name")));
	cust_vo.setLegal_card_id(Utility.trimNull(su.getRequest().getParameter("legal_card_id")));
	cust_vo.setAgent_name(Utility.trimNull(su.getRequest().getParameter("agent_name")));
	cust_vo.setAgent_card_id(Utility.trimNull(su.getRequest().getParameter("agent_card_id")));
	cust_vo.setPartner_card_id(Utility.trimNull(su.getRequest().getParameter("partner_card_id")));
	cust_vo.setManaging_scope(Utility.trimNull(su.getRequest().getParameter("managing_scope")));
	cust_vo.setLegal_card_type(Utility.trimNull(su.getRequest().getParameter("legal_card_type")));
	cust_vo.setLegal_card_valid_date(Utility.parseInt(su.getRequest().getParameter("legal_card_valid_date"), new Integer(0)));
	cust_vo.setLegal_tel(Utility.trimNull(su.getRequest().getParameter("legal_tel")));
	cust_vo.setAgent_card_type(Utility.trimNull(su.getRequest().getParameter("agent_card_type")));
	cust_vo.setAgent_card_valid_date(Utility.parseInt(su.getRequest().getParameter("agent_card_valid_date"), new Integer(0)));
	cust_vo.setAgent_tel(Utility.trimNull(su.getRequest().getParameter("agent_tel")));
	cust_local.updateEntCustInfo(cust_vo);
	bSuccess = true;

	// 逐一提取上传文件信息，同时可保存文件。 
	for (int i = 0; i < su.getFiles().getCount(); i++) {
		com.jspsmart.upload.File file = su.getFiles().getFile(i);
		// 若文件不存在则继续 
		if (file.isMissing()) continue;
		int iFileSize = file.getSize();		
		if (iFileSize == 0)	 continue;

		String fieldName = file.getFieldName();
		if (fieldName.equals("forbid_file")) {
			if (iFileSize > 10*1024*1024)
				throw new BusiException("文件大小不能超过10M！");

			String strFolder = Utility.replaceAll(Argument.getDictContentIntrust("800101")," ",""); 
			if ("".equals(strFolder)) strFolder = "c:\\uploadfiles\\";		
			strFolder = Utility.replaceAll(strFolder,"\\","//");			
			File dir = new File(strFolder);
			if (!dir.isDirectory()) dir.mkdirs();
			if (dir.isDirectory()) {
				su.save(strFolder, SmartUpload.SAVE_PHYSICAL); //
				
				int fileno = dir.list().length + 1;
				String newFileName = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date())
										+ "-" + fileno+ "."	+ file.getFileExt();
				String oldFileName = file.getFileName();
			
				//重命名
				File old = new File(strFolder+oldFileName);
				String parent = old.getParent();
				File _new = new File(parent + "//" + newFileName);
				old.renameTo(_new);

				newFileName = strFolder + newFileName;

				AttachmentToCrmLocal attachment = EJBFactory.getAttachmentToCrm();
				AttachmentVO vo = new AttachmentVO();					
				vo.setDf_talbe_id(new Integer(20));
				vo.setDf_table_name("TCustomers");
				vo.setDf_serial_no(cust_id);
				vo.setSave_name(newFileName);
				vo.setOrigin_name(oldFileName);
				vo.setFile_size(new Integer(iFileSize));
				vo.setDescription("");
				vo.setInput_man(input_operatorCode);
				attachment.append(vo);
				attachment.remove();
			}

			break; //
		}
	}
}

Map entCustInfoMap = new HashMap();
Map contact1 = new HashMap();
Map contact2 = new HashMap();
//cust_id不等于0，则查询查询单条客户信息
if (cust_id != null && cust_id.intValue() != 0){
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	list = cust_local.listProcAll(cust_vo);

	if(list.size()>0){
		map = (Map) list.get(0);
		if(map.get("ImageIdentification")!=null){
			imageShowFlag = new Integer(1);
		}
	}
	
	list = cust_local.loadEntCustInfo(cust_vo);
	if (list.size()>0)
		entCustInfoMap = (Map) list.get(0);	
}

if (request.getMethod().equals("POST")){
	//华澳客户同步
	if(is_deal.intValue()==1&&Argument.getSyscontrolValue("HUAAO1")==1){
		CustomerService cust_service = new CustomerService();
		cust_service.sendCustToSAP(cust_id,input_operatorCode);
	}
}

cust_local.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.clientInfoNewEnd",clientLocale)%></TITLE>
<!--机构客户信息-->
<BASE TARGET="_self">
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/CustomerService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<SCRIPT LANGUAGE="javascript" src="<%=request.getContextPath()%>/includes/autopromot.js"></SCRIPT>
<style>
h1 {
	text-align:center;
	font-size:2.2em;
}
#divc {
	border:1px solid #555;
	background-color:white;
}
.des {
	width:500px;
	background-color:lightyellow;
	border:1px solid #555;
	padding:25px;
	margin-top:25px;
}
.mouseover {
	color:#ffffff;
	background-color:highlight;
	width:100%;
	cursor:default;
}
.mouseout {
	color:#000;
	width:100%;
	background-color:#ffffff;
	cursor:default;
}
</style>
<script language=javascript>
<%if (bSuccess) { %>
	sl_update_ok();
	<%if (cust_id.intValue()==0) {%>
		if(confirm("<%=LocalUtilis.language("message.note",clientLocale)%>：\n\n<%=LocalUtilis.language("message.noteTip",clientLocale)%>?"))
		//"系统提示：\n\n您是否继续新建机构信息?"
		{
			history.back();
		} else {
			//op_return();
			window.returnValue = 1;
			window.close();
		}
	<% } else { %>
		//op_return();
		window.returnValue = 1;
		window.close();
	<%}
}%>

//组合返回的条件
function op_return() {
	var tempArray = '<%=iQuery%>'.split('$');
	location.href = "client_info_list.jsp?no=" + tempArray[0] + "&name=" + tempArray[1] + "&product_id=" + tempArray[2]
					+ "&is_link=" + tempArray[3] + "&card_id=" + tempArray[4] 
					+ "&page="+ tempArray[5] + "&pagesize=" + tempArray[6]
					+ "&is_deal="+ tempArray[7] + "&cust_type=" + tempArray[8]
					+ "&true_flag="+ tempArray[9] + "&serv_man=" + tempArray[10]
					+ "&min_times="+ tempArray[11] + "&max_times=" + tempArray[12]
					+ "&min_total_money="+ tempArray[13] + "&max_total_money=" + tempArray[14]
					+ "&rg_start_date="+ tempArray[15] + "&rg_end_date=" + tempArray[16];
}

function validateForm(form) {
	syncDatePicker(document.theform.legal_card_valid_date_picker, document.theform.legal_card_valid_date); //
	syncDatePicker(document.theform.agent_card_valid_date_picker, document.theform.agent_card_valid_date); //
	if(!sl_check(form.cust_name, "<%=LocalUtilis.language("class.organizationName",clientLocale)%> ", 100, 1))	return false;//机构名称
	if(!sl_checkChoice(form.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")){return false;}//客户类型
	
<%if( user_id.intValue()==24/*厦门*/){%>
	var cust_name = document.theform.cust_name.value;
	CustomerService.findSameCustInfo1(cust_name,1,0,"",dwrCallbackInfo1);
<%}
else if (!edit || user_id.intValue()==15/*建信*/) {%>
	var cust_id = document.theform.cust_id.value;
	var cust_name = document.theform.cust_name.value;
	var card_id = document.theform.card_id.value;
	CustomerService.findSameCustInfo(cust_name,0,cust_id,card_id,dwrCallbackInfo);

<%} else {%>
	continueCreate();
<%}%>
}

function dwrCallbackInfo1(data) {
	if(data!="") {
//如果有权限，允许保存
		if(<%= (input_operator.hasFunc(menu_id, 104)) %>){
			continueCreate();
		} else{
			alert(data);
			return false;
		}
	} else
		continueCreate();
}

function dwrCallbackInfo(data) {
	var cust_id = <%=cust_id%>;
	if (data!="") {
		if (cust_id==0) {
			if (confirm(data))
				continueCreate();
		} else
			continueCreate();

	} else {
		continueCreate();
	}
}

function continueCreate() {
	<%if (isCcb) {%>
	getElement("name:post_address").value 
		= (getElement("name:province1").value==""?"":getSelText("name:province1"))
		 +(getElement("name:district1").value==""?"":getSelText("name:district1"))
		 +getElement("name:town_detail1").value;
	//alert(getElement("name:post_address").value);
	getElement("name:city1").value 
		= getElement("name:district1").value==""? ""
				: getElement("name:district1").value.substring(0,8)+"00";
	//alert(getElement("name:city1").value);

	getElement("name:post_address2").value 
		= (getElement("name:province2").value==""?"":getSelText("name:province2"))
		 +(getElement("name:district2").value==""?"":getSelText("name:district2"))
		 +getElement("name:town_detail2").value;
	//alert(getElement("name:post_address2").value);
	getElement("name:city2").value 
		= getElement("name:district2").value==""? ""
				: getElement("name:district2").value.substring(0,8)+"00";
	//alert(getElement("name:city2").value);
<%}%>
	var saveFlag = true;
	var showFlag = document.getElementById("showFlag").value;
	var i ;
	var is_deal_flag = document.theform.is_deal_picker.value;
	for(var folderNo=1; saveFlag&&folderNo<3; folderNo++) {
	<%if (!edit || user_id.intValue()!=15/*建信*/) {%>
		show(folderNo);
	
		if (is_deal_flag==1) saveFlag = checkfolder(folderNo);
	<%}%>
	}

	//show(folder_no);
	if (saveFlag && sl_confirm("<%=LocalUtilis.language("message.inputInfoSave",clientLocale)%> ")) //保存输入信息
		document.theform.submit();
}

function checkfolder(folder_no){
	form = document.theform;
	var is_deal = document.getElementById("is_deal2").value;

	if (folder_no == 1) {
		if (is_deal==1 && !sl_checkChoice(form.card_type, "<%=LocalUtilis.language("class.customerCardType",clientLocale)%> ")) return false;//证件类型
		if (form.card_type.value!=0 && !sl_check(form.card_id, "<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ", 40, 1)) return false;//证件号码
		if (is_deal==1 && !sl_checkChoice(form.zyhy_type, "<%=LocalUtilis.language("class.industryType",clientLocale)%> ")) return false;//行业类别
		<%if (user_id.intValue()==17){%>//方正要求证件有效期必填:customer_card_valid_date
		if(form.card_valid_date_picker.value==""){
			alert("请填写证件有效期");
			form.card_valid_date_picker.focus();
			return false;
		}
		<%}%>
		if (document.theform.card_valid_date_picker.value!=""){
			if (! sl_checkDate(document.theform.card_valid_date_picker,"证件有效期 ")) return false;
			syncDatePicker(form.card_valid_date_picker, form.card_valid_date);
		}
		if(!sl_checkChoice(form.service_man, "<%=LocalUtilis.language("class.accountManager",clientLocale)%> ")) return false;//客户经理
		if(!sl_checkChoice(form.cust_source, "<%=LocalUtilis.language("class.customerSource",clientLocale)%> ")) return false;	//客户来源
		if(!sl_checkDecimal(document.theform.potential_mongy,"<%=LocalUtilis.language("class.potentialMongy",clientLocale)%> ",13,2,0)) return false;//投资潜力
		if(document.theform.vip_date_picker.value!=""){
			if(!sl_checkDate(document.theform.vip_date_picker,"<%=LocalUtilis.language("class.customerVipDatePicker",clientLocale)%> ")) return false;//VIP发卡日期
			syncDatePicker(form.vip_date_picker, form.vip_date);
		}

		if(!sl_checkDecimal(form.free_cash_flow),"自由现金流",16,3,0) return false;
		if(!sl_checkDecimal(form.burend_of_debt,"债务压力"),16,3,0) return false;

		form.is_link.value= form.is_link.checked? 1: 0;
		form.print_deploy_bill.value= form.print_deploy_bill.checked? 1: 0;
		form.print_post_info.value= form.print_post_info.checked? 1: 0;
	}
	else if(folder_no ==2){
			if(!sl_checkChoice(form.touch_type, "<%=LocalUtilis.language("class.serviceWay",clientLocale)%> "))	return false;//联系方式

			if(form.touch_type.value=="110901"){
			<%if(user_id.intValue()==1){%>
				if(!sl_checkNum(form.o_tel, "<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ", 30, 1)) return false;//公司电话
				if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 30, 1))	return false;//手机号码
			<%}else{%>
				if(form.h_tel.value.length==0&&form.o_tel.value.length==0&&form.mobile.value.length==0&&form.bp.value.length==0){
					sl_alert("<%=LocalUtilis.language("message.telMoreThanOne",clientLocale)%> ");//请至少填一个联系电话
					return false;
				}

				if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 30, 0))	return false;//手机号码
				if(!sl_checkNum(form.bp, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 30, 0))	return false;//手机号码
			<%}%>
			}
			if(form.touch_type.value=="110902"){
				if(!sl_check(form.post_address, "<%=LocalUtilis.language("class.postAddress",clientLocale)%> ", 60, 1))	return false;//联系地址
				if(!sl_check(form.post_code, "<%=LocalUtilis.language("class.postCode2",clientLocale)%> ", 60, 1)) return false;//邮寄编码
			}
			if(form.touch_type.value=="110903"){
				if(!sl_check(form.e_mail, "Email", 30, 1))	return false;
			}
			if(form.touch_type.value=="110904"){
				if(!sl_check(form.fax, "<%=LocalUtilis.language("class.fax",clientLocale)%> ", 60, 1)) return false;//传真
			}
			if(form.touch_type.value=="110905"){
				if(!sl_checkNum(form.mobile, "<%=LocalUtilis.language("class.mobile",clientLocale)%> ", 30, 1))	return false;//手机号码
			}

			if(form.e_mail.value.length!=0){
				if(!isEmail(form.e_mail,"Email"))	return false;
			}
			if(!sl_checkNum(form.post_code, "<%=LocalUtilis.language("class.postcode",clientLocale)%> ", 6, 0))	return false;//邮政编码
	}
	else if(folder_no ==3){
		if(!sl_checkNum(form.feeding_num_people, "<%=LocalUtilis.language("class.houseHold",clientLocale)%> ", 60, 0)) return false;//供养人数

		if(!sl_checkNum(form.house_area, "<%=LocalUtilis.language("class.estateArea",clientLocale)%> ", 60, 0))	return false;//房产面积
		if(!sl_checkNum(form.vehicle_num, "<%=LocalUtilis.language("class.vehiclesQuantity",clientLocale)%> ", 60, 0)) return false;//车辆数量
		if(!sl_checkNum(form.credit_num, "<%=LocalUtilis.language("class.loanNumber",clientLocale)%> ", 60, 0))	return false;//贷款数量

		if((is_deal==1)||(document.theform.credit_start_date_picker.value!="")){
			if(!sl_checkDate(document.theform.credit_start_date_picker,"<%=LocalUtilis.language("class.beginDate",clientLocale)%> ")) return false;//起始日期
			syncDatePicker(form.credit_start_date_picker, form.credit_start_date);
		}
		if((is_deal==1)||(document.theform.credit_end_date_picker.value!="")){
			if(!sl_checkDate(document.theform.credit_end_date_picker,"<%=LocalUtilis.language("class.endDate3",clientLocale)%> ")) return false;//截止日期
			syncDatePicker(form.credit_end_date_picker, form.credit_end_date);
		}
	}
	else if(folder_no ==4){
		form.bocom_staff.value= form.bocom_staff.checked? 1: 0;
		form.company_staff.value= form.company_staff.checked? 1: 0;

		if(!sl_checkNum(form.stature, "<%=LocalUtilis.language("class.stature",clientLocale)%> ", 3, 0)) return false;//身高
		if(!sl_checkNum(form.weight, "<%=LocalUtilis.language("class.weight",clientLocale)%> ", 3, 0)) return false;//体重
		if(!sl_checkNum(form.head_office_cust_id, "<%=LocalUtilis.language("class.headOfficeCustomerID",clientLocale)%> ", 60, 0)) return false;//总行客户号
	}

	return true;
}

function showAcctNum(value) {
	if (trim(value) == "")
		bank_acct_num.innerText = "";
	else
		bank_acct_num.innerText = "(" + showLength(value) + " 位 )";
}

function changeStyle(obj,current_valid_period){	
	if (obj.length>=1) {
		var a="";
		for(var i=0; i<obj.length; i++){
			if (obj[i].checked)	a += obj[i].value;
			current_valid_period.value = a;
		}
	}else{
		current_valid_period.value = obj.checked? obj.value: "";
	}
}

function setiteminfor(tr10,tablePro,flagdisplay,imagex){
    var i= flagdisplay.value;
    if(i==0){
      tablePro.style.display="";
      tr10.style.display="";
      flagdisplay.value=1;
      imagex.src='<%=request.getContextPath()%>/images/up_enabled.gif';
    }
    else if(i==1){
      tablePro.style.display="none";
      tr10.style.display="none";
      flagdisplay.value=0;
      imagex.src='<%=request.getContextPath()%>/images/down_enabled.gif';
    }
}

//检验图片上传的格式
function checkFileType(){
	var custFile = document.getElementById("imageIdentification");
	var filePath = custFile.value;
	var dotNdx = filePath.lastIndexOf('.');
	var exetendName = filePath.slice(dotNdx + 1).toLowerCase();
	if((exetendName == "jpg")) {
 		return true;
	}else{
		custFile.select()
  		document.execCommand("Delete");
   		custFile.focus();
		sl_alert("<%=LocalUtilis.language("message.invalidImgFormat",clientLocale)%> ！");//图片的格式无效，请选择您要上传的JPG文件
		return false;
	}
}

function show(parm){
	//document.getElementById("showFlag").value = parm;

   for (var i=1; i<=5; i++) {
		if (document.getElementById("d"+i))	
			document.getElementById("d"+i).background = 
					i==parm? '<%=request.getContextPath()%>/images/head_00_01.gif'
						   : '<%=request.getContextPath()%>/images/headdark_00_01.gif';
	    if (document.getElementById("r"+i))
			document.getElementById("r"+i).style.display = i==parm? "": 'none';
	}
}

/*显示中文钱数*/
function showCnMoney(value){
	if (trim(value) == "")
		reg_money_cn.innerText = "";
	else
		reg_money_cn.innerText = "(" + numToChinese(value) + ")";
}

window.onload = function(){
	if (document.theform.cust_id.value > 0) 
		test(document.theform.cust_name);
	
	show('<%=showFlag%>');
	var isDeal = document.getElementById("is_deal2").value;
	var h_gov_prov_regional = document.getElementById("h_gov_prov_regional").value;

	if(isDeal==1){
		document.getElementById("is_deal_picker").disabled=true;
	}
	if(h_gov_prov_regional!= "" && h_gov_prov_regional.length > 0){
		document.getElementById("gov_prov_regional").value  = h_gov_prov_regional;
		setGovRegional();
	}
	initradio('marital',<%=Utility.parseInt(Utility.trimNull(map.get("MARITAL")),new Integer(0))%>);
	initradio('house_property',<%=Utility.parseInt(Utility.trimNull(map.get("HOUSE_PROPERTY")),new Integer(0))%>);
}

function changeIsDeal(){
	var temp = document.getElementById("is_deal_picker").value;
	document.getElementById("is_deal2").value = temp;
}

/*通过省市过滤相关的行政区域*/
function setGovRegional(){
	var obj = document.getElementById("gov_prov_regional") ;
	if (obj.value!="" && obj.value.length>0)
		utilityService.getGovRegional(9999, obj.value, "",backGovRegional);
}

/*通过省市过滤相关的行政区域 回调函数*/
function backGovRegional(data){
	document.getElementById("gov_regional_span").innerHTML = "<select size='1'  name='gov_regional' style='width: 200px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
	var h_gov_regional = document.getElementById("h_gov_regional").value;
	if (h_gov_regional!="" && h_gov_regional.length>0)
		document.getElementById("gov_regional").value  = h_gov_regional;
}
/*通过省市过滤相关的行政区域*/
function resetGovRegional(value,selected,span_name,sel_name){
	if (value!=""){
		DWREngine.setAsync(false); // sync
		utilityService.getGovRegional(9999, value, selected
			, function(data) {
				document.getElementById(span_name).innerHTML 
					= "<select size='1' name='"+sel_name+"' style='width:150px' onkeydown='javascript:nextKeyPress(this)'>"+data+"</select>";
			  }
		);
		DWREngine.setAsync(true); // async
	}
}
/*多选资金来源*/
function chooseAction(){
	var money_source = document.getElementById("money_source").value;
	var url = "<%=request.getContextPath()%>/client/money_source_check.jsp?money_source="+money_source;
	var ret = showModalDialog(url,'','dialogWidth:450px;dialogHeight:350px;status:0;help:0');
	if (ret) {
		document.getElementById("money_source").value = ret[0];
		document.getElementById("money_source_name").value = ret[1];
	}
}

function initradio(radioName,radioValue){
    var radioObjects = document.getElementsByName(radioName);
    for (var i=0; i<radioObjects.length; i++)
        radioObjects[i].checked = radioObjects[i].value==radioValue;
}

/*显示中文钱数*/
function showCnMoney(value,name){
	if (trim(value) == "")
		name.innerText = " ";
	else
		name.innerText = "(" + numToChinese(value) + ")";
}

function showNum(value) {
	if (trim(value) == "")
		head_office_cust_id_num.innerText = "";
	else
		head_office_cust_id_num.innerText = "(" + showLength(value) + " 位 )";
}

window.onerror = function() { return true; };

function test(elem){
<%if(user_id.intValue() == 17/*方正*/ || user_id.intValue()==22/*中投*/ 
		|| user_id.intValue()==999) {//方正东亚需求在界面录入客户名称时动态判断，开启该功能时数据量大时明显会慢很多 %>	
	jsAutoInstance.handleEvent(elem, event);
<%}%>
}

function focused(elem) {
<%if(user_id.intValue()==17/*方正*/ || user_id.intValue()==22/*中投*/ || user_id.intValue()==999) { //方正东亚需求在界面录入客户名称时动态判断，开启该功能时数据量大时明显会慢很多%>
	jsAutoInstance.display();
<%}%>
}

function blured(elem) {
<%if(user_id.intValue()==17/*方正*/ || user_id.intValue()==22/*中投*/ || user_id.intValue()==999) { //方正东亚需求在界面录入客户名称时动态判断，开启该功能时数据量大时明显会慢很多%>
	jsAutoInstance.hide();
<%}%>
}

//失去焦点后验证客户名称是否重复
function blured2(){
	var cust_name = document.theform.cust_name.value;
	if(cust_name==""){
		return ;
	}
	<%if(user_id.intValue()==24/*厦门*/) {%>
	CustomerService.findSameCustInfo1(cust_name,1,0,"",dwrCallbackInfo2);
	<%}%>
}
function dwrCallbackInfo2(data) {
	if(data!="") {
		alert(data);
	}else{
		return ;
	}
}

function searchServman() {
	var servman = document.theform.service_man;
	for (var i=1; i<servman.options.length; i++) {
		if (servman.options[i].text.indexOf(document.theform.servman_name.value) >= 0) {
			servman.selectedIndex = i;
			break;
		}
	}
	if (i==servman.options.length)
		sl_alert("找不到名字带有\""+document.theform.servman_name.value+"\"字的客户经理！");
}

function changeCustType(cust_type) {
	if (cust_type=="1") {
		document.getElementById("reload").href = "client_info_new.jsp?cust_id=<%=cust_id%>"; 
		document.getElementById("reload").click();
	} else if (cust_type=="2") {
		document.getElementById("reload").href = "clinet_info_new_end.jsp?cust_id=<%=cust_id%>"; 
		document.getElementById("reload").click();
	}
}
function getElement(elem) {
    try {
        return elem.indexOf("name:")==0? 
                    document.getElementsByName(elem.substring("name:".length, elem.length))[0]
                : elem.indexOf("id:")==0? 
                    document.getElementById(elem.substring("id:".length, elem.length))
                :   document.getElementById(elem);
    } catch (err) {
        return null;
    }
}

function getSelText(name) {
	var sel = getElement(name);
	try {
		return sel.options[sel.selectedIndex].text;
	} catch (err) {
		return undefined;
	}
}

function forbid(checked) {
	getElement("forbidFileTr").style.display = checked?"":"none";	
	getElement("name:forbid_file").disabled = !checked;
}

function DownloadAttachment(attachmentId) {
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

function deleteAttachment(btn, id){
    if (confirm("确定删除附件吗?")) { 
		utilityService.deleteAttachment(id, <%=input_operatorCode%>
		   , function(ok) {
				if (! ok) {
					sl_alert("删除附件失败！");
					return;
				}

				var table = getElement("attaTable");
				table.deleteRow(btn.parentNode.parentNode.rowIndex);
				if (table.rows.length==0) {
					var tr = table.insertRow(table.rows.length);
					var td = tr.insertCell(0);
					td.innerHTML = '<input type="file" name="forbid_file" size="70"/>';
				}
			} );		
    }
}
</script>
</HEAD>
<BODY  class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="clinet_info_new_end.jsp" enctype="multipart/form-data">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="cust_no" value="<%=Utility.trimNull(map.get("CUST_NO"))%>">
<input type="hidden" name="iQuery" value="<%=iQuery%>">
<input type="hidden" name="showFlag"	id="showFlag" value="<%= showFlag%>" />
<input type="hidden" name="strvalue">
<input type="hidden" name="strserial">
<input type="hidden" name="pinyinwhole"	value="">
<input type="hidden" name="custimg" value="<%=ins%>">
<input type="hidden" name="imageShowFlag"	id="imageShowFlag" value="<%= imageShowFlag%>" />
<input type="hidden" name="h_gov_prov_regional"  value="<%=Utility.trimNull(map.get("GOV_PROV_REGIONAL"))%>">
<input type="hidden" name="h_gov_regional" value="<%=Utility.trimNull(map.get("GOV_REGIONAL"))%>">

<div>
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.customerInfomation",clientLocale)%> - <%=LocalUtilis.language("class.jgCount",clientLocale)%> </b></font><!--客户信息 - 机构-->
</div>
<br>
<div id="divc">
	<!--this is the autocomplete container.-->
</div>
<SCRIPT LANGUAGE="JavaScript">
var jsAutoInstance = new jsAuto("divc");
var returnV  = '<%=returnV%>';
jsAutoInstance.item(returnV);
</SCRIPT>
<a style="display:none" href="clinet_info_new_end.jsp?cust_id=" id="reload"></a>
<div style="margin-left:40px">
	<font size="2" face="宋体"><%=LocalUtilis.language("class.customerName",clientLocale)%> ：</font><!--客户名称-->

<%if (edit && user_id.intValue()==15/*建信*/) { %>
	<input disabled onkeydown="javascript:nextKeyPress(this)" maxlength="200" size="45"	
		value="<%=Utility.trimNull(map.get("CUST_NAME"))%>" onkeyup="javascript:test(this);" onfocus="javascript:focused(this)" onblur="javascript:blured(this)"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<font size="2" face="宋体"><%=LocalUtilis.language("class.custType",clientLocale)%> ：</font><!--客户类型-->
	<select disabled onchange="javascript:changeCustType(this.value)"><%=Argument.getCustTypeOptions2(new Integer(2))%></select>

	<input type="hidden" name="cust_name" value="<%=Utility.trimNull(map.get("CUST_NAME"))%>"/>
	<input type="hidden" name="cust_type" value="2"/>
<%} else {%>
	<input name="cust_name"	onkeydown="javascript:nextKeyPress(this)" maxlength="200" size="45"	
		value="<%=Utility.trimNull(map.get("CUST_NAME"))%>" onkeyup="javascript:test(this);" onfocus="javascript:focused(this)" onblur="javascript:blured2()"/>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<font  size="2" face="宋体"><%=LocalUtilis.language("class.custType",clientLocale)%> ：</font><!--客户类型-->
	<select name="cust_type" onchange="javascript:changeCustType(this.value)"><%=Argument.getCustTypeOptions2(new Integer(2))%></select>
<%}%>
	&nbsp;&nbsp;<select name="is_deal_picker" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:120px" onchange="javascript:changeIsDeal();">
		<%=Argument.getWTCustOptions(Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0)))%>
	</select>
	<input name="is_deal2" id="is_deal2"  type="hidden" value="<%=Utility.trimNull(map.get("IS_DEAL"))%>"  />
</div>

<div align="left"  style="margin-left:10px;margin-bottom:10px;">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline">
		<TBODY>
			<TR>
				<TD vAlign=top>&nbsp;</TD>
			<%if (!edit || user_id.intValue()!=15/*建信*/) { %>
				<TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD><!--基本信息-->
				<TD id="d5" style="background-repeat: no-repeat" onclick=show(5) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;其他证件 </TD>
			<%} %>
				<TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>
				    &nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> </TD><!--联系方式-->

			<%if (!edit || user_id.intValue()!=15/*建信*/) { %>
				<!--财务信息-->
                <TD id="d3" style="background-repeat: no-repeat;display: none;" onclick=show(3) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("intrsut.home.financialposition",clientLocale)%> </TD>
                <!--客户详细信息-->
                <TD id="d4" style="background-repeat: no-repeat;display: none;" onclick=show(4) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInfo",clientLocale)%> </TD>
			<%} %>
			</TR>
		</TBODY>
	</TABLE>
</div>

<div id="r1" align="center" style="display:none;margin-right:10px;height:350px;">
	<table border="0" width="100%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right" width="150px"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardType",clientLocale)%> ：</td><!--证件类型-->
			<td>
				<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="card_type" style="WIDTH: 120px">
					<%=Argument.getCardTypeJgOptions2(String.valueOf(Utility.trimNull(map.get("CARD_TYPE"),"")))%>
				</select>
			</td>
			<td align="right" width="120px"><span style="color:red">*</span><%=LocalUtilis.language("class.customerCardID",clientLocale)%> ：</td><!--证件号码-->
			<td><INPUT name="card_id" onkeyup="javascript:showAcctNum(this.value)"
					   maxlength="40" value='<%=Utility.trimNull(map.get("H_CARD_ID"))%>' size="20">
				<span id="bank_acct_num" class="span"></span>
				<!-- 唯一性校验
				<input type="checkbox" name="cust_flag" value="1" checked=checked> -->
			</td>
		</tr>
		<tr>
			<td align="right">&nbsp;<span style="color:red">*</span><%=LocalUtilis.language("class.vocType",clientLocale)%> ：</td><!--行业-->
			<td>
				<select size="1" onkeydown="javascript:nextKeyPress(this)"  name="zyhy_type" style="WIDTH: 225px" >
					<%=Argument.getJghyOptions2(Utility.trimNull(map.get("VOC_TYPE"),""))%>
				</select>
			</td>
			<td align="right"><%if (user_id.intValue()==17){/*方正要求证件有效期必填*/%><span style="color:red">*</span><%} %><%=LocalUtilis.language("class.tradeDate",clientLocale)%>：</td>
			<td align="left">
				<input type="text" name="card_valid_date_picker" size="21" maxlength="8" value="<%=Utility.trimNull(map.get("CARD_VALID_DATE"))%>">
				<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.card_valid_date_picker,theform.card_valid_date_picker.value,this);"
					   onkeydown="javascript:CalAge();nextKeyPress(this)"> 
				<INPUT TYPE="hidden" NAME="card_valid_date" value="" />&nbsp;&nbsp;
				<button class="xpbutton3" onclick="javascript:document.theform.card_valid_date_picker.value='2500-01-01';">长期有效</button>
			</td>
		</tr>
		<tr>
			<td align="right">&nbsp;<span style="color:red">*</span>机构类型 ：</td><!--机构类型2109-->
			<td>
				<select size="1" name="jg_type">
					<%=Argument.getDictParamOptions(2109,Utility.trimNull(map.get("EAST_JG_TYPE")))%>
				</select>
			</td>
			<td align="right">委托人类型 ：</td>
			<td align="left">
				<SELECT name="jg_wtrlx2">
					<%=Argument.getJgWtrlx2List((String)map.get("JG_WTRLX2"))%>
				</SELECT>
			</td>
		</tr>
		<tr>

			<td align="right">&nbsp;&nbsp;<%=LocalUtilis.language("class.corporate",clientLocale)%> ：</td><!--法人姓名-->
			<td ><INPUT name="legal_man" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="40" value='<%=Utility.trimNull(map.get("LEGAL_MAN"))%>'></td>
			<td align="right">&nbsp;<span style="color:red">*</span><%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</td><!--客户经理-->
			<td>
				<select size="1" name="service_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")), input_operatorCode))%>
				</select>
				&nbsp;&nbsp;&nbsp;<input type="text" name="servman_name" size="10"/>
				&nbsp;&nbsp;&nbsp;<button class="searchbutton" onclick="javascript:searchServman();" /></button>
			</td>

		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> ：</td><!--合格投资人编号-->
			<td><input onkeydown="javascript:nextKeyPress(this)" name="hgtzr_bh" size="20" maxlength="40" value="<%=Utility.trimNull(map.get("HGTZR_BH"))%>"></td>
			<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.customerSource",clientLocale)%> ：</td><!--客户来源-->
			<td>
				<select onkeydown="javascript:nextKeyPress(this)" size="1" name="cust_source" style="WIDTH:125px">
					<%=Argument.getCustomerSourceOptions(Utility.trimNull(map.get("CUST_SOURCE")))%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> ：</td><!--VIP卡编号-->
			<td><input onkeydown="javascript:nextKeyPress(this)" name="vip_card_id" size="20" maxlength="40" value="<%=Utility.trimNull(map.get("VIP_CARD_ID"))%>"></td>
			<td align="right"><%=LocalUtilis.language("class.customerVipDatePicker",clientLocale)%> ：</td><!--VIP发卡日期-->
			<td>
				<INPUT TYPE="text"  NAME="vip_date_picker" class=selecttext <%if(map.get("VIP_DATE")==null){%> value=""
				<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("VIP_DATE")),new Integer(0)))%>"<%}%> onkeydown="javascript:nextKeyPress(this)">
				<INPUT TYPE="button" value="▼" class=selectbtn tabindex="13"
					onclick="javascript:CalendarWebControl.show(theform.vip_date_picker,theform.vip_date_picker.value,this);" />
				<INPUT TYPE="hidden" NAME="vip_date" value="" />
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.regional",clientLocale)%> ：</td><!--所属地区-->
			<td colspan="3">
				<select size="1"  name="gov_prov_regional"  id="gov_prov_regional" style="width: 125px" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:setGovRegional();">
					<%=Argument.getCustodianNameLis(new Integer(9999), "", new Integer(0),"")%>
				</select>
				<span id="gov_regional_span"></span><!-- 省级行政区域过滤出来的相关区域 -->
			</td>

		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.countryName",clientLocale)%> ：</td><!-- 国别 -->
			<td>
				<select size="1"  name="country"  id="country" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getDictParamOptions(9997,Utility.trimNull(map.get("COUNTRY"),"9997CHN"))%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.moneySource",clientLocale)%> ：</td><!-- 资金来源 -->
			<td>
				<!--
				<select size="1"  name="money_source"  id="money_source" style="width: 125px" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getDictParamOptions(2012,Utility.trimNull(map.get("MONEY_SOURCE")))%>
				</select> -->

				<input type="text" name="money_source_name" id="money_source_name" size="27"  value="<%=Utility.trimNull(map.get("MONEY_SOURCE_NAME"))%>" onkeydown="javascript:nextKeyPress(this)" readonly/>
				<input type="hidden" name="money_source" id="money_source" value="<%=Utility.trimNull(map.get("MONEY_SOURCE"))%>" />
				<!-- 选择 -->&nbsp;&nbsp;
                <button class="xpbutton2" id="btnChoMoneySource" name="btnChoMoneySource" onclick="javascript:chooseAction();"><%=LocalUtilis.language("message.choose",clientLocale)%> </button>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.referee",clientLocale)%>：</td> <!--推荐人-->
			<td>
				<input type="text" name="recommended" size="30" value="<%=Utility.trimNull(map.get("RECOMMENDED")) %>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.potentialMongy",clientLocale)%> ：</td><!--投资潜力-->
			<td>
				<!--投资潜力-->
				<input type="text"
					   name="potential_mongy"
					   onkeydown="javascript:nextKeyPress(this)"
					   onblur="javascript:sl_checkDecimal(form.potential_mongy,'<%=enfo.crm.tools.LocalUtilis.language("class.potentialMongy",clientLocale)%>',13,2,0);showCnMoney(this.value)"
					   value="<%=Utility.parseDecimal( Utility.trimNull(map.get("POTENTIAL_MONEY")),new BigDecimal(0.00),2,"1")%>">
				<span id="reg_money_cn" class="span"></span>
			</td>
		</tr>
		<tr>
				<td align="right">自由现金流 ：</td>
				<td><input name="free_cash_flow" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("FREE_CASH_FLOW")),new BigDecimal(0))%>" /></td>
				<td align="right">债务压力 ：</td>
				<td><input name="burend_of_debt" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("BUREND_OF_DEBT"),"0")%>" />%</td>
			</tr>
		<tr>
			<TD align="right"><%=LocalUtilis.language("class.imageIdentification",clientLocale)%> ：</TD><!--证件扫描图片-->
			<TD colspan="3"><INPUT name="imageIdentification" type="file" size="70"
								   value="" id="imageIdentification" onchange="javascript:checkFileType();"></TD>
		</tr>

		<%//if (cust_id != null && !"".equals(cust_id) && cust_id.intValue() != 0 && (in != null && !"".equals(in))) {%>
		<%if (cust_id != null && !"".equals(cust_id) && cust_id.intValue() != 0 && (imageShowFlag.intValue()>0)) {%>
			<tr>
				<td align="right">&nbsp;</td>
				<td colspan="3">
					<iframe src ="show_client_image.jsp?cust_id=<%=cust_id%>" name="sonIframe" 	frameborder="no" border="0" height="350" width="500"></iframe>
				</td>
			</tr>
		<%}%>

		<TR>
			<TD valign="top" align="right"><%=LocalUtilis.language("class.summary",clientLocale)%> ：</TD><!--备注信息-->
			<TD colspan="3"><TEXTAREA rows="5" name="summary"
				onkeydown="javascript:nextKeyPress(this)" cols="100"><%=Utility.trimNull(map.get("SUMMARY"))%></TEXTAREA>
			</TD>
		</TR>
		<tr>
			<td colspan="4">
				<table width="100%">
					<tr>
						<td align="right"><%=LocalUtilis.language("class.isLink2",clientLocale)%> ：</td><!--关联标志-->
						<td><input class="flatcheckbox"  type="checkbox" name="is_link" value='0'
							<%if(map.get("IS_LINK")!=null){if(Integer.valueOf(String.valueOf(map.get("IS_LINK"))).intValue()==1) out.print("checked");}%> />
						</td>
					<%if(cust_id.intValue()> 0) {%>
						<td align="right"><%=LocalUtilis.language("message.printCustomerStatement",clientLocale)%> :</td><!--打印客户对帐单-->
						<td><input class="flatcheckbox" type="checkbox" name="print_deploy_bill" value="0"
							<%if(Utility.parseInt(Utility.trimNull(map.get("PRINT_DEPLOY_BILL")),new Integer(0)).intValue()==1){out.print("checked");}%> />
						</td>
						<td align="right"><%=LocalUtilis.language("message.customerPrintPostInfo",clientLocale)%> :</td><!--打印披露信息-->
						<td><input class="flatcheckbox" type="checkbox" name="print_post_info" value="0"
							<%if(Utility.parseInt(Utility.trimNull(map.get("PRINT_POST_INFO")),new Integer(0)).intValue()==1){out.print("checked");}%> />
						</td>
					<%}else {%>
						<td align="right"><%=LocalUtilis.language("message.printCustomerStatement",clientLocale)%> :</td><!--打印客户对帐单,新建时候默认选中 -->
						<td><input class="flatcheckbox" type="checkbox" name="print_deploy_bill" value="1" checked/>
						</td>
						<td align="right"><%=LocalUtilis.language("message.customerPrintPostInfo",clientLocale)%> :</td><!--打印披露信息,新建时候默认选中-->
						<td><input class="flatcheckbox" type="checkbox" name="print_post_info" value="1" checked />
						</td>
					<%} %>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<TD align="right">是否禁购 ：</TD>
			<TD colspan="3">
				<input type="checkbox" name="forbid_flag" onclick="javascript:forbid(this.checked);" value="1"
					<%=Utility.trimNull(map.get("STATUS")).equals("112806")?"checked":""%> />
			</TD>
		</tr>
		<tr id="forbidFileTr" style="display:<%=Utility.trimNull(map.get("STATUS")).equals("112806")?"":"none"%>">
			<TD align="right">禁购相关证明上传 ：</TD>
			<TD colspan="3">
			<%
			AttachmentToCrmLocal attaLocal = EJBFactory.getAttachmentToCrm();
			AttachmentVO attaVo = new AttachmentVO();
			attaVo.setDf_talbe_id(new Integer(20));
			attaVo.setDf_serial_no(new Integer(edit?cust_id.intValue():-1));
			List attaList =	attaLocal.load(attaVo);
			if (attaList.isEmpty()) {
			 %>
				<input type="file" name="forbid_file" size="70"/>
			<%
			} else {%>
				<table id="attaTable">
				<%for (int i=0; i<attaList.size(); i++) {
					Map atta = (Map)attaList.get(i); 
					String attaId = Utility.trimNull(atta.get("ATTACHMENTID"));%>			
				<tr><td>
					<a href="#" onclick="javascript:DownloadAttachment(<%=attaId%>)"><%=atta.get("ORIGIN_NAME")%></a>
					&nbsp;<button class="xpbutton2" onclick="javascript:deleteAttachment(this,<%=attaId%>);">删除</button>
					<%=i<attaList.size()-1?"<br/>":""%>
				</td></tr>
			<%	}%>
				</table>
			<%}	
			attaLocal.remove();
			 %>
			</TD>
		</tr>
	</table>
</div>

<div  id="r5" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="100%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right">经营范围 ：</td>
			<td><INPUT name="managing_scope" onkeydown="javascript:nextKeyPress(this)" size="40" value='<%=Utility.trimNull(entCustInfoMap.get("Managing_Scope")).trim()%>'></td>	
			<td align="right">企业营业执照编号 ：</td>
			<td><INPUT name="business_code" onkeydown="javascript:nextKeyPress(this)" size="40" value='<%=Utility.trimNull(entCustInfoMap.get("BUSINESS_CODE")).trim()%>'></td>			
		</tr>
		<tr>
			<td align="right">税务登记证 ：</td>
			<td><INPUT name="tax_reg_code" onkeydown="javascript:nextKeyPress(this)" size="40" value='<%=Utility.trimNull(entCustInfoMap.get("TAX_REG_CODE")).trim()%>'></td>
			<td align="right">组织机构代码 ：</td>
			<td><input name="organ_code" onkeydown="javascript:nextKeyPress(this)"  size="40" value='<%=Utility.trimNull(entCustInfoMap.get("ORGAN_CODE")).trim()%>'></td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<td align="right">法人姓名 ：</td>
			<td><INPUT name="legal_name" onkeydown="javascript:nextKeyPress(this)" size="20" value='<%=Utility.trimNull(entCustInfoMap.get("LEGAL_NAME")).trim()%>'></td>
			<td align="right">法人电话 ：</td>
			<td><input name="legal_tel" onkeydown="javascript:nextKeyPress(this)"  size="25" value='<%=Utility.trimNull(entCustInfoMap.get("LEGAL_TEL")).trim()%>'></td>
		</tr>
		<tr>
			<td align="right">法人证件类型 ：</td>
			<td><select name="legal_card_type" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getCardTypeOptions2(Utility.trimNull(entCustInfoMap.get("LEGAL_CARD_TYPE")))%>
				</select>
			</td>
			<td align="right">法人证件号码 ：</td>
			<td><input name="legal_card_id" onkeydown="javascript:nextKeyPress(this)"  size="40" value='<%=Utility.trimNull(entCustInfoMap.get("LEGAL_CARD_ID")).trim()%>'></td>
		</tr>
		<tr>
			<td align="right">法人证件有效期限 ：</td>
			<td colspan="3">
				<input type="text" name="legal_card_valid_date_picker" class=selecttext style="width: 99px;" 
					value="<%=Format.formatDateLine((Integer)entCustInfoMap.get("LEGAL_CARD_VALID_DATE"))%>">
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.legal_card_valid_date_picker,theform.legal_card_valid_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="legal_card_valid_date" id="legal_card_valid_date" />
			</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<td align="right">代理人姓名 ：</td>
			<td><INPUT name="agent_name" onkeydown="javascript:nextKeyPress(this)" size="20" value='<%=Utility.trimNull(entCustInfoMap.get("AGENT_NAME")).trim()%>'></td>
			<td align="right">代理人电话 ：</td>
			<td><input name="agent_tel" onkeydown="javascript:nextKeyPress(this)"  size="40" value='<%=Utility.trimNull(entCustInfoMap.get("AGENT_TEL")).trim()%>'></td>
		</tr>
		<tr>
			<td align="right">代理人证件类型 ：</td>
			<td><select name="agent_card_type" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getCardTypeOptions2(Utility.trimNull(entCustInfoMap.get("AGENT_CARD_TYPE")))%>
				</select>
			</td>
			<td align="right">代理人证件号码 ：</td>
			<td><input name="agent_card_id" onkeydown="javascript:nextKeyPress(this)"  size="40" value='<%=Utility.trimNull(entCustInfoMap.get("AGENT_CARD_ID")).trim()%>'></td>
		</tr>
		<tr>
			<td align="right">代理人证件有效期限 ：</td>
			<td colspan="3">
				<input type="text" name="agent_card_valid_date_picker" class=selecttext style="width: 99px;" 
					value="<%=Format.formatDateLine((Integer)entCustInfoMap.get("AGENT_CARD_VALID_DATE"))%>">
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.agent_card_valid_date_picker,theform.agent_card_valid_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="agent_card_valid_date" id="agent_card_valid_date" />
			</td>
		</tr>
		<tr><td colspan="4">&nbsp;</td></tr>	
		<tr>
			<td align="right">合伙人有效身份证明文件编码 ：</td>
			<td colspan="3"><INPUT name="partner_card_id" onkeydown="javascript:nextKeyPress(this)" size="40" value='<%=Utility.trimNull(entCustInfoMap.get("PARTNER_CARD_ID")).trim()%>'></td>
		</tr>
	</table>
</div>

<div  id="r2" align="center" style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="90%" cellspacing="3" cellpadding="3">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.contact",clientLocale)%> ：</td><!--联系人-->
			<td><INPUT name="contact_man" onkeydown="javascript:nextKeyPress(this)" size="20" value='<%=Utility.trimNull(map.get("CONTACT_MAN")).trim()%>'></td>
			<td align="right"><%=LocalUtilis.language("class.phone",clientLocale)%> ：</td><!--联系人电话-->
			<td><input name="h_tel" onkeydown="javascript:nextKeyPress(this)"  size="20" value='<%=Utility.trimNull(map.get("H_H_TEL")).trim()%>'></td>
		</tr>

		<tr>
			<td align="right"><span style="color:red">*</span><%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</td><!--联系方式-->
			<td>
				<select name="touch_type" style="WIDTH: 120px" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getTouchTypeOptions(Utility.trimNull(map.get("TOUCH_TYPE")))%>
				</select>
			</td>
			<td align="right">Email：</td>
			<td><INPUT name="e_mail" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("H_E_MAIL")).trim()%>"></td>
		</tr>

		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> ：</td><!--手机-->
			<td><input name="mobile" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("H_MOBILE")).trim()%>" /></td>
			<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2：</td><!--手机2-->
			<td><INPUT name="bp" onkeydown="javascript:nextKeyPress(this)" size="20"  value="<%=Utility.trimNull(map.get("H_BP")).trim()%>" /></td>
		</tr>

		<tr>
			<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> ：</td><!--公司电话-->
			<td><INPUT name="o_tel" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("H_O_TEL")).trim()%>" /></td>
			<td  align="right"><%=LocalUtilis.language("class.fax",clientLocale)%> ：</td><!--传真-->
			<td><input name="fax" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("H_FAX")).trim()%>" /></td>
		</tr>

		<tr>
			<td align="right" vAlign="top"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> ：</td><!--邮寄地址-->
			<td>
				<%if (isCcb) { %>
					省份 <select name="province1" onchange="javascript:resetGovRegional(this.value,'','gov_regional1_span','district1');">
							<%=Argument.getCustodianNameLis(new Integer(9999),"", new Integer(0), Utility.trimNull(contact1.get("PROVINCE")))%>
						 </select>
					&nbsp;&nbsp;市县
						<span id="gov_regional1_span">
							<select size='1' name='district1' style='width:150px' onkeydown='javascript:nextKeyPress(this)'>
							<%if (Utility.trimNull(contact1.get("PROVINCE")).equals("")) { %>
								<option value="">请选择</option>
							<%} else { %>
								<%=Argument.getCustodianNameLis(new Integer(9999),Utility.trimNull(contact1.get("PROVINCE")), new Integer(0), Utility.trimNull(contact1.get("DISTRICT")))%>
							<%} %>
							</select>
						</span>
						 <input type="hidden" name="city1"/>
					<br/>详细街道地址 <input type="text" name="town_detail1" size="50" value="<%=Utility.trimNull(contact1.get("TOWN_DETAIL"))%>"/>
					<TEXTAREA rows="3" name="post_address" onkeydown="javascript:nextKeyPress(this)" cols="50" style="display:none"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></TEXTAREA>
				<%} else { %>
					<TEXTAREA rows="3" name="post_address" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></TEXTAREA>
				<%} %>
			</td>
			<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> ：</td><!--邮政编码-->
			<td><input name="post_code" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(map.get("POST_CODE")).trim()%>"></td>
		</tr>

		<tr>
			<td align="right" vAlign="top"><%=LocalUtilis.language("class.postAddress2",clientLocale)%> 2：</td><!--邮寄地址-->
			<td>
				<%if (isCcb) { %>
					省份 <select name="province2" onchange="javascript:resetGovRegional(this.value,'','gov_regional2_span','district2');">
							<%=Argument.getCustodianNameLis(new Integer(9999),"", new Integer(0), Utility.trimNull(contact2.get("PROVINCE")))%>
						 </select>
					&nbsp;&nbsp;市县
						 <span id="gov_regional2_span">
							 <select size='1' name='district2' style='width:150px' onkeydown='javascript:nextKeyPress(this)'>
							 <%if (Utility.trimNull(contact2.get("PROVINCE")).equals("")) { %>
								<option value="">请选择</option>
							 <%} else { %>
								<%=Argument.getCustodianNameLis(new Integer(9999),Utility.trimNull(contact2.get("PROVINCE")), new Integer(0), Utility.trimNull(contact2.get("DISTRICT")))%>
							 <%} %>
							 </select>
						 </span>
						 <input type="hidden" name="city2" value="<%=Utility.trimNull(contact2.get("CITY"))%>"/>
					<br/>详细街道地址 <input type="text" name="town_detail2" size="50" value="<%=Utility.trimNull(contact2.get("TOWN_DETAIL"))%>"/>
					<TEXTAREA rows="3" name="post_address2" onkeydown="javascript:nextKeyPress(this)" cols="50" style="display:none"><%=Utility.trimNull(map.get("POST_ADDRESS2"))%></TEXTAREA>
				<%} else { %>
					<TEXTAREA rows="3" name="post_address2" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("POST_ADDRESS2"))%></TEXTAREA>
				<%} %>
			</td>
			<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> 2：</td><!--邮政编码2-->
			<td><input name="post_code2" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength="6" value="<%=Utility.trimNull(map.get("POST_CODE2")).trim()%>"></td>
		</tr>

		<tr style="display:<%=isCcb?"none":""%>">
			<td align="right">真实性：</td>
			<td colspan="3"><select name="true_flag"><%=Argument.getCustInfoTrueFlagList(Utility.parseInt((Integer)map.get("TRUE_FLAG"), new Integer(1)))%></select></td>
		</tr>
	</table>
</div>
<div  id="r3" align="left"  style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr>
		<td>
			<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
			<tr>
				<td><b><%=LocalUtilis.language("message.einkommenStatus",clientLocale)%> </b></td>  <!--收支状况-->
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.houseHold",clientLocale)%>:</td><!--供养人数-->
				<td colspan = "3"><input name="feeding_num_people" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("FEEDING_NUM_PEOPLE"))%>" /></td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.personalIncome",clientLocale)%>:</td><!--个人年收入-->
				<td><input name="personal_income" onkeydown="javascript:nextKeyPress(this)" onblur="javascript:showCnMoney(this.value,personal_income_cn)"
					 size="20" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("PERSONAL_INCOME")),new BigDecimal(0))%>" />
					 <span id="personal_income_cn" class="span"></span>
				</td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.householdIncome",clientLocale)%>:</td><!--家庭年收入-->
				<td><INPUT name="household_income" onkeydown="javascript:nextKeyPress(this)" size="20" onblur="javascript:showCnMoney(this.value,household_income_cn)"
					 value="<%=Utility.parseDecimal(Utility.trimNull(map.get("HOUSEHOLD_INCOME")),new BigDecimal(0))%>" />
					<span id="household_income_cn" class="span"></span>
				</td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.mainSourceFinancial",clientLocale)%>:</td><!--主要经济来源-->
				<td><input name="main_source" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("MAIN_SOURCE"))%>" /></td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.otherSourceFinancial",clientLocale)%>:</td><!--其他经济来源-->
				<td><INPUT name="other_source" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("OTHER_SOURCE"))%>" /></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
			<tr>
				<td><b><%=LocalUtilis.language("message.estateStatus",clientLocale)%> </b></td>  <!--房产状况-->
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.estateArea",clientLocale)%>:</td><!--面积-->
				<td><INPUT name="house_area" onkeydown="javascript:nextKeyPress(this)" size="34" value="<%=Utility.trimNull(map.get("HOUSE_AREA"))%>" />
					<span class="span">(M<sup>2</sup>)</span>
				</td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.estateAttribute",clientLocale)%>:</td><!--房产属性（1商、2住）-->
				<td>
					<input type = "radio" value = "1"  name = "house_property" />商<input type = "radio" value = "2"  name = "house_property" />住
				</td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.estateLocation",clientLocale)%>:</td><!--房产位置-->
				<td><INPUT name="house_position" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("HOUSE_POSITION"))%>" /></td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.marketAppraisal",clientLocale)%>:</td><!--市场估价-->
				<td><input name="market_appraisal" onkeydown="javascript:nextKeyPress(this)" onblur="javascript:showCnMoney(this.value,market_appraisal_cn)"
					 size="28" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("MARKET_APPRAISAL")),new BigDecimal(0))%>" />
					<span id="market_appraisal_cn" class="span"></span>
				</td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.platEnvaluate",clientLocale)%>:</td><!--地段评价-->
				<td colspan = "3">
					<TEXTAREA rows="2" name="plat_envaluate" onkeydown="javascript:nextKeyPress(this)" cols="120"><%=Utility.trimNull(map.get("PLAT_ENVALUATE"))%></TEXTAREA>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
			<tr>
				<td><b><%=LocalUtilis.language("message.vehicleStatus",clientLocale)%> </b></td>  <!--车辆状况-->
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.vehiclesQuantity",clientLocale)%>:</td><!--车辆数量-->
				<td colspan = "3"><input name="vehicle_num" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("VEHICLE_NUM"))%>" /></td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.brand",clientLocale)%>:</td><!--品牌-->
				<td><INPUT name="vehicle_make" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("VEHICLE_MAKE"))%>" /></td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.vehicleType",clientLocale)%>:</td><!--型号-->
				<td><input name="vehicle_type" onkeydown="javascript:nextKeyPress(this)" size="40" value="<%=Utility.trimNull(map.get("VEHICLE_TYPE"))%>" /></td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
			<tr>
				<td><b><%=LocalUtilis.language("message.loanStatus",clientLocale)%> </b></td>  <!--贷款状况-->
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.loanType",clientLocale)%>:</td><!--贷款种类-->
				<td colspan = "3"><INPUT name="credit_type" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("CREDIT_TYPE"))%>" /></td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.beginDate",clientLocale)%>:</td><!--起始日-->
				<td>
					<INPUT TYPE="text" NAME="credit_start_date_picker" class=selecttext
					<%if(Utility.parseInt(Utility.trimNull(map.get("CREDIT_START_DATE")),new Integer(0)).intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("CREDIT_START_DATE")),new Integer(0)))%>"<%}%> >
					<INPUT TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.credit_start_date_picker,theform.credit_start_date_picker.value,this);"
					tabindex="13">
					<INPUT TYPE="hidden" NAME="credit_start_date" value="<%=Utility.parseInt(Utility.trimNull(map.get("CREDIT_START_DATE")),new Integer(0))%>">
				</td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.loanNumber",clientLocale)%>:</td><!--贷款数量-->
				<td ><input name="credit_num" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("CREDIT_NUM"))%>" /></td>
			</tr>
			<tr>
				<td align="right" width="100px"><%=LocalUtilis.language("class.endDate3",clientLocale)%>:</td><!--终止日-->
				<td>
					<INPUT TYPE="text" NAME="credit_end_date_picker" class=selecttext
					<%if(Utility.parseInt(Utility.trimNull(map.get("CREDIT_END_DATE")),new Integer(0)).intValue()==0){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
					<%}else{%>value="<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("CREDIT_END_DATE")),new Integer(0)))%>"<%}%> >
					<INPUT TYPE="button" value="▼" class=selectbtn
					onclick="javascript:CalendarWebControl.show(theform.credit_end_date_picker,theform.credit_end_date_picker.value,this);"
					tabindex="13">
					<INPUT TYPE="hidden" NAME="credit_end_date" value="<%=Utility.parseInt(Utility.trimNull(map.get("CREDIT_END_DATE")),new Integer(0))%>">
				</td>
				<td align="right" width="100px"><%=LocalUtilis.language("class.otherInvestmentStatus",clientLocale)%>:</td><!--其他投资状况-->
				<td>
					<select name="other_investment_status" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getOtherInvestmentOptions(Utility.trimNull(map.get("OTHER_INVESTMENT_STATUS")))%>
					</select>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	</table>
</div>
<div  id="r4" align="left"  style="display:none;margin-left:10px;margin-right:10px;height:350px;">
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.stature",clientLocale)%>:</td><!--身高-->
				<td><input name="stature" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("STATURE"))%>" />
					<span id="statures" class="span">(CM)</span>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.weight",clientLocale)%>:</td><!--体重-->
				<td><input name="weight" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("WEIGHT"))%>" />
					<span id="weights" class="span">(KG)</span>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.nation",clientLocale)%>:</td><!--民族-->
				<td>
					<select name="nation" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getNationOptions(Utility.trimNull(map.get("NATION")))%>
					</select>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.ismarried",clientLocale)%>:</td><!--婚姻状况-->
				<td>
					<input type = "radio" value = "1"  name = "marital"/><%=LocalUtilis.language("class.married",clientLocale)%>
					<input type = "radio" value = "2"  name = "marital" /><%=LocalUtilis.language("class.spinsterhood",clientLocale)%>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.healthType",clientLocale)%>:</td><!--健康状况-->
				<td>
					<select name="health" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getHealthOptions(Utility.trimNull(map.get("HEALTH")))%>
					</select>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.educationDegree",clientLocale)%>:</td><!--教育程度-->
				<td>
					<select name="education" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getEducationOptions(Utility.trimNull(map.get("EDUCATION")))%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.headOfficeCustomerID",clientLocale)%>:</td><!--总行客户号-->
				<td>
					<input name="head_office_cust_id" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showNum(this.value)"
					size="20" value="<%=Utility.trimNull(map.get("HEAD_OFFICE_CUST_ID"))%>">
				<span id="head_office_cust_id_num" class="span"></span>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("message.unit",clientLocale)%><%=LocalUtilis.language("class.jdrType2Name",clientLocale)%>:</td><!--单位性质-->
				<td >
					<select name="company_character" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getCompanyCharacterOptions(Utility.trimNull(map.get("COMPANY_CHARACTER")))%>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.duty",clientLocale)%>:</td><!--职务-->
				<td><input name="post" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("POST"))%>"></td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.holderOfAnOffice",clientLocale)%>:</td><!-- 职称 -->
				<td><INPUT name="holderofanoffice" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("HOLDEROFANOFFICE"))%>"></td>
			</tr>
			<tr>
				<td align="right" width="150px"><input class="flatcheckbox" type="checkbox" name="company_staff" value="0"
					<%if(Utility.parseInt(Utility.trimNull(map.get("COMPANY_STAFF")),new Integer(0)).intValue()==1){out.print("checked");}%> />
				</td>
				<td align="left" vAlign="top"><%=LocalUtilis.language("class.CompanyEmployees",clientLocale)%></td><!--是否为公司员工-->
				<td align="right" width="150px"><input class="flatcheckbox" type="checkbox" name="bocom_staff" value="0"
					<%if(Utility.parseInt(Utility.trimNull(map.get("BOCOM_STAFF")),new Integer(0)).intValue()==1){out.print("checked");}%> />
				</td>
				<td align="left"><%=LocalUtilis.language("class.BOCOMEmployees",clientLocale)%></td><!--是否为交行员工-->
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.spouse",clientLocale)%>:</td><!--配偶姓名-->
				<td><input name="spouse_name" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("SPOUSE_NAME"))%>" /></td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.childrenName",clientLocale)%>:</td><!--子女姓名-->
				<td><INPUT name="children_name" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("CHILDREN_NAME"))%>" /></td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("class.spouseInfo",clientLocale)%>:</td><!--配偶信息-->
				<td>
					<TEXTAREA rows="3" name="spouse_info" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("SPOUSE_INFO"))%></TEXTAREA>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.childrenInfo",clientLocale)%>:</td><!--子女信息-->
				<td>
					<TEXTAREA rows="3" name="children_info" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("CHILDREN_INFO"))%></TEXTAREA>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px"><%=LocalUtilis.language("message.customer",clientLocale)%><%=LocalUtilis.language("class.jdrType2Name",clientLocale)%>:</td><!--客户性质(机构)(1164)-->
				<td>
					<select name="client_quale" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
						<%=Argument.getCompanyCharacterOptions(Utility.trimNull(map.get("CLIENT_QUALE")))%>
					</select>
				</td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.holdingRelatedInfo",clientLocale)%></td><!--控股相关信息机构)-->
				<td>
					<TEXTAREA rows="3" name="holding" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("HOLDING"))%></TEXTAREA>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px" vAlign="top"><%=LocalUtilis.language("message.operator",clientLocale)%><%=LocalUtilis.language("intrsut.home.quantity",clientLocale)%></td><!--员工数量(机构)-->
				<td><input name="employee_num" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=Utility.trimNull(map.get("EMPLOYEE_NUM"))%>"></td>
				<td align="right" width="150px"><%=LocalUtilis.language("class.registeredFund",clientLocale)%>:</td><!--注册资金(机构)-->
				<td><input name="registered_capital" onkeydown="javascript:nextKeyPress(this)"  onblur="javascript:showCnMoney(this.value,registered_capital_cn)"
					 size="20"  value="<%=Utility.parseDecimal(Utility.trimNull(map.get("REGISTERED_CAPITAL")),new BigDecimal(0.00))%>">
					<span id="registered_capital_cn" class="span"></span>
				</td>
			</tr>
			<tr>
				<td align="right" width="150px" vAlign="top"><%=LocalUtilis.language("class.registAddress",clientLocale)%>:</td><!--注册地(机构)-->
				<td colspan = "3"><input name="registered_place" onkeydown="javascript:nextKeyPress(this)" size="50"  value="<%=Utility.trimNull(map.get("REGISTERED_PLACE"))%>"></td>
			</tr>
			<tr>
				<td colspan = "4">
				<table  border="0" width="100%" cellspacing="0" cellpadding="3" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
					<tr>
						<td><b><%=LocalUtilis.language("message.preferenceInformation",clientLocale)%> </b></td>  <!--偏好信息-->
					</tr>
					<tr>
						<td align="right" width="150px"><%=LocalUtilis.language("class.typePreference",clientLocale)%>:</td><!--类型偏好(1166)-->
						<td>
							<select name="type_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getTypePrefOptions(Utility.trimNull(map.get("TYPE_PREF")))%>
							</select>
						</td>
						<td align="right" width="150px"><%=LocalUtilis.language("class.timeLimitPreference",clientLocale)%>:</td><!--期限偏好(1170)-->
						<td>
							<select name="time_limit_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getTimeLimitPrefOptions(Utility.trimNull(map.get("TIME_LIMIT_PREF")))%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" width="150px"><%=LocalUtilis.language("class.investPreference",clientLocale)%>:</td><!--投向偏好(1167)-->
						<td>
							<select name="invest_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getInvestPrefOptions(Utility.trimNull(map.get("INVEST_PREF")))%>
							</select>
						</td>
						<td align="right" width="150px"><%=LocalUtilis.language("class.hobbyPreference",clientLocale)%>:</td><!--兴趣偏好(1168)-->
						<td>
							<select name="hobby_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getHobbyPrefOptions(Utility.trimNull(map.get("HOBBY_PREF")))%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" width="150px"><%=LocalUtilis.language("class.servicePreference",clientLocale)%>:</td><!--服务偏好(1169)-->
						<td>
							<select name="service_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getServicePrefOptions(Utility.trimNull(map.get("SERVICE_PREF")))%>
							</select>
						</td>
						<td align="right" width="150px"><%=LocalUtilis.language("class.contactPreference",clientLocale)%>:</td><!--联系方式偏好(1109)-->
						<td>
							<select name="contact_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getTouchTypeOptions(Utility.trimNull(map.get("CONTACT_PREF")))%>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right" width="150px"><%=LocalUtilis.language("class.riskPreference",clientLocale)%>:</td><!--风险偏好(1172)-->
						<td colspan = "3">
							<select name="risk_pref" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
								<%=Argument.getRiskPrefOptions(Utility.trimNull(map.get("RISK_PREF")))%>
							</select>
						</td>
					</tr>
				</table>
				</td>
			</tr>
	</table>
</div>
<div align="right" style="margin-right:5px;margin-top:5px;">
	<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
	</button><!--保存-->
	&nbsp;&nbsp;
	<button class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>B</u>)
	</button><!--关闭-->
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
