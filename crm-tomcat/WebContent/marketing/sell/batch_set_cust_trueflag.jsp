<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
boolean from_cust = "cust_info_list".equals(request.getParameter("from"));

String qs = Utility.getQueryString(request, 
	new String[]{"q_productCode", "q_pre_flag", "q_productId", 
		"q_check_flag", "q_cust_name", "q_card_id", "query_contract_bh",
		"max_rg_money", "min_rg_money", "q_cust_type", "q_class_detail_id",
		"q_group_id", "q_product_name", "q_managerID", "q_sub_productId"
		, "page", "pagesize"
		, "name", "no", "product_id", "product_name", "is_link", "card_id"
		, "is_deal", "cust_type", "group_id", "q_money_source", "q_money_source_name"
		, "q_country", "cust_source", "invest_field", "mobile", "post_address", "true_flag", "serv_man" });

CustomerLocal cust = EJBFactory.getCustomer();

Integer true_flag = Utility.parseInt(request.getParameter("true_flag"), new Integer(1));

CustomerVO vo = new CustomerVO();
vo.setTrue_flag(true_flag);
vo.setInput_man(input_operatorCode);

String[] cust_ids = request.getParameterValues("cust_id");
if (cust_ids!=null) {
	for (int i=0; i<cust_ids.length; i++) {
		Integer cust_id = Utility.parseInt(cust_ids[i], new Integer(0));
		if (cust_id.intValue()<=0) continue;
		
		vo.setCust_id(cust_id);
		cust.modiCustTrueFlag(vo);

		/*CustomerVO vo = new CustomerVO();
		vo.setCust_id(cust_id);
		vo.setInput_man(input_operatorCode);
		List list = cust.listProcAll(vo);
		Map map;
		if (list.size()>0)
			map = (Map) list.get(0);
		else 
			continue;

		CustomerVO cust_vo = new CustomerVO();

		Integer imageShowFlag = new Integer(0);
		if (map.get("ImageIdentification")!=null) imageShowFlag = new Integer(1);

		byte [] bx = new byte[0];
		InputStream ins = new ByteArrayInputStream(bx);
		if(ins.available()==0&&cust_id.intValue()>0&&imageShowFlag.intValue()>0){	
			String filePathName = "c:\\temp\\" +"Cust_"+cust_id+".jpg";
			File myFile = new java.io.File(filePathName);
			if(myFile.exists()){
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
		cust_vo.setCust_no((String)map.get("CUST_NO"));
		cust_vo.setCust_name((String)map.get("CUST_NAME"));
		cust_vo.setCust_tel((String)map.get("CUST_TEL"));
		cust_vo.setPost_address((String)map.get("POST_ADDRESS"));
		cust_vo.setPost_code((String)map.get("POST_CODE"));
		cust_vo.setCard_type((String)map.get("CARD_TYPE"));
		cust_vo.setCard_id((String)map.get("CARD_ID"));
		cust_vo.setAge((Integer)map.get("AGE"));
		cust_vo.setSex((Integer)map.get("SEX"));
		cust_vo.setO_tel((String)map.get("O_TEL"));
		cust_vo.setH_tel((String)map.get("H_TEL"));
		cust_vo.setMobile((String)map.get("MOBILE"));
		cust_vo.setBp((String)map.get("BP"));
		cust_vo.setFax((String)map.get("FAX"));
		cust_vo.setE_mail((String)map.get("E_MAIL"));
		cust_vo.setWt_flag((Integer)map.get("WT_FLAG"));
		cust_vo.setCust_type((Integer)map.get("CUST_TYPE"));
		cust_vo.setCust_level((String)map.get("CUST_LEVEL"));
		cust_vo.setTouch_type((String)map.get("TOUCH_TYPE"));
		cust_vo.setCust_source((String)map.get("CUST_SOURCE"));
		cust_vo.setSummary((String)map.get("SUMMARY"));
		cust_vo.setCheck_man((Integer)map.get("CHECK_MAN"));
		cust_vo.setInput_man(input_operatorCode);
		cust_vo.setLegal_man((String)map.get("LEGAL_MAN"));
		cust_vo.setLegal_address((String)map.get("LEGAL_ADDRESS"));
		cust_vo.setBirthday((Integer)map.get("BIRTHDAY"));
		cust_vo.setPost_address2((String)map.get("POST_ADDRESS2"));
		cust_vo.setPost_code2((String)map.get("POST_CODE2"));
		cust_vo.setPrint_deploy_bill((Integer)map.get("PRINT_DEPLOY_BILL"));
		cust_vo.setPrint_post_info((Integer)map.get("PRINT_POST_INFO"));
		cust_vo.setIs_link((Integer)map.get("IS_LINK"));
		cust_vo.setVip_date((Integer)map.get("VIP_DATE"));
		cust_vo.setVip_card_id((String)map.get("VIP_CARD_ID"));
		cust_vo.setHgtzr_bh((String)map.get("HGTZR_BH"));
		cust_vo.setVoc_type((String)map.get("ZYHY_TYPE"));
		cust_vo.setCard_valid_date((Integer)map.get("CARD_VALID_DATE"));
		cust_vo.setJg_cust_type((String)map.get("JG_CUST_TYPE"));
		cust_vo.setContact_man((String)map.get("CONTACT_MAN"));
		cust_vo.setPotenital_money((BigDecimal)map.get("POTENTIAL_MONGY"));
		cust_vo.setGov_prov_regional((String)map.get("GOV_PROV_REGIONAL"));
		cust_vo.setGov_regional((String)map.get("GOV_REGIONAL"));
		cust_vo.setCountry((String)map.get("COUNTRY"));
		cust_vo.setMoney_source((String)map.get("MONEY_SOURCE"));
		cust_vo.setMoney_source_name((String)map.get("MONEY_SOURCE_NAME"));
	
		//增加客户经理维护
		cust_vo.setService_man((Integer)map.get("SERVICE_MAN"));		
		cust_vo.setRecommended((String)map.get("RECOMMENDED"));
		
		//财务及偏好信息
		cust_vo.setPersonal_income(Utility.parseDecimal((String)map.get("PERSONAL_INCOME"),new BigDecimal(0)));
		cust_vo.setHousehold_income(Utility.parseDecimal((String)map.get("HOUSEHOLD_INCOME"),new BigDecimal(0)));
		cust_vo.setFeeding_num_people(Utility.parseInt((String)map.get("FEEDING_NUM_PEOPLE"),new Integer(0)));
		cust_vo.setMain_source(Utility.trimNull((String)map.get("MAIN_SOURCE")));
		cust_vo.setOther_source(Utility.trimNull((String)map.get("OTHER_SOURCE")));
		cust_vo.setHouse_position(Utility.trimNull((String)map.get("HOUSE_POSITION")));
		cust_vo.setHouse_property(Utility.parseInt(Utility.trimNull((String)map.get("HOUSE_PROPERTY")),new Integer(0)));
		cust_vo.setHouse_area(Utility.parseInt(Utility.trimNull((String)map.get("HOUSE_AREA")),new Integer(0)));
		cust_vo.setPlat_envaluate(Utility.trimNull((String)map.get("PLAT_ENVALUATE")));
		cust_vo.setMarket_appraisal(Utility.parseDecimal((String)map.get("MARKET_APPRAISAL"),new BigDecimal(0)));
		cust_vo.setVehicle_num(Utility.parseInt((String)map.get("VEHICLE_NUM"),new Integer(0)));
		cust_vo.setVehicle_make(Utility.trimNull((String)map.get("VEHICLE_MAKE")));
		cust_vo.setVehicle_type(Utility.trimNull((String)map.get("VEHICLE_TYPE")));
		cust_vo.setCredit_type(Utility.trimNull((String)map.get("CREDIT_TYPE")));//贷款种类
		cust_vo.setCredit_num(Utility.parseInt((String)map.get("CREDIT_NUM"),new Integer(0)));
		cust_vo.setCredit_start_date(Utility.parseInt((String)map.get("CREDIT_START_DATE"),new Integer(0)));
		cust_vo.setCredit_end_date(Utility.parseInt((String)map.get("CREDIT_END_DATE"),new Integer(0)));
		cust_vo.setOther_investment_status(Utility.trimNull((String)map.get("OTHER_INVESTMENT_STATUS")));
		cust_vo.setType_pref(Utility.trimNull((String)map.get("TYPE_PREF")));
		cust_vo.setTime_limit_pref(Utility.trimNull((String)map.get("TIME_LIMIT_PREF")));
		cust_vo.setInvest_pref(Utility.trimNull((String)map.get("INVEST_PREF")));
		cust_vo.setHobby_pref(Utility.trimNull((String)map.get("HOBBY_PREF_ALL")));
		cust_vo.setService_pref(Utility.trimNull((String)map.get("SERVICE_PREF")));
		cust_vo.setContact_pref(Utility.trimNull((String)map.get("CONTACT_PREF")));
		cust_vo.setRisk_pref(Utility.trimNull((String)map.get("RISK_PREF")));
	
		//客户详细信息
		cust_vo.setHead_office_cust_id((String)map.get("HEAD_OFFICE_CUST_ID"));
		cust_vo.setStature((Integer)map.get("STATURE"));
		cust_vo.setWeight((Integer)map.get("WEIGHT"));
		cust_vo.setSpouse_name((String)map.get("SPOUSE_NAME"));
		cust_vo.setSpouse_info((String)map.get("SPOUSE_INFO"));
		cust_vo.setChildren_name((String)map.get("CHILDREN_NAME"));
		cust_vo.setChildren_info((String)map.get("CHILDREN_INFO"));
		cust_vo.setNation(Utility.trimNull((String)map.get("NATION")));
		cust_vo.setMarital(Utility.parseInt((String)map.get("MARITAL"),new Integer(0)));
		cust_vo.setHealth(Utility.trimNull((String)map.get("HEALTH")));
		cust_vo.setEducation(Utility.trimNull((String)map.get("EDUCATION")));
		cust_vo.setPost(Utility.trimNull((String)map.get("POST")));
		cust_vo.setHolderofanoffice(Utility.trimNull((String)map.get("HOLDEROFANOFFICE")));
		cust_vo.setCompany_character(Utility.trimNull((String)map.get("COMPANY_CHARACTER")));
		cust_vo.setCompany_staff(Utility.parseInt((String)map.get("COMPANY_STAFF"),new Integer(0)));
		cust_vo.setBocom_staff(Utility.parseInt((String)map.get("BOCOM_STAFF"),new Integer(0)));
		cust_vo.setCompany_unit(Utility.trimNull((String)map.get("COMPANY_UNIT")));
		cust_vo.setCompany_depart(Utility.trimNull((String)map.get("COMPANY_DEPART")));
		cust_vo.setCompany_position(Utility.trimNull((String)map.get("COMPANY_POSITION")));
	
		cust_vo.setCust_aum(Utility.trimNull((String)map.get("CUST_AUM")));
		cust_vo.setCust_age_group(Utility.trimNull((String)map.get("CUST_AGE_GROUP")));
		cust_vo.setInves_experinc(Utility.trimNull((String)map.get("INVES_EXPERINC")));
		cust_vo.setCust_source_explain(Utility.trimNull((String)map.get("CUST_SOURCE_EXPLAIN")));
	
		cust_vo.setTrue_flag(true_flag);//客户真实性标记 默认为1未核查
	
		if(user_id.intValue() == 22)
			cust_vo.setChange_flag(1) ;//中投修改客户需要审核
		else
			cust_vo.setChange_flag(-1);//
	
		
		if(is_deal.intValue()==1){
			customer_inst.cope_customers(cust_vo);
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			cust_local.modify(cust_vo);
		}
		else if(is_deal.intValue()==2){
			cust_vo.setComplete_flag(new Integer(2)); //客户信息不完整
			cust_local.modify2(cust_vo);
		}*/
		
	}	
}

cust.remove();
%>
<SCRIPT LANGUAGE="javascript">
	location.href = "<%=from_cust?"/client/clientinfo/client_info_list":"subscribe_list"%>.jsp?<%=qs%>";
</SCRIPT>