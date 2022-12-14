<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ page import="enfo.crm.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
    //CustomerinfoLocal customer = EJBFactory.getCustomerinfo();
	CustomerLocal customer = EJBFactory.getCustomer();
	CustomerVO vo = new CustomerVO();

    //UploadFileWithFileupload file = null;
	DocumentFile2 file = null;
    int returnVal = 0;
	try{
	    file = new DocumentFile2(pageContext);//new UploadFileWithFileupload(request);
		file.parseRequest();
        Integer book_code = input_bookCode;
        String cust_name = DwrDecoder.unescape(Utility.trimNull(file.getParameter("cust_name")));  
        String cust_tel  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("mobile")));      
        String post_address = DwrDecoder.unescape(Utility.trimNull(file.getParameter("post_address")));
        String post_code = DwrDecoder.unescape(Utility.trimNull(file.getParameter("post_code")));     
        String card_type = DwrDecoder.unescape(Utility.trimNull(file.getParameter("card_type")));  
        String card_id = DwrDecoder.unescape(Utility.trimNull(file.getParameter("card_id")));      
        Integer age  = Utility.parseInt(file.getParameter("age"),null);       
        Integer sex  = Utility.parseInt(file.getParameter("sex"),null);             
        String o_tel = DwrDecoder.unescape(Utility.trimNull(file.getParameter("o_tel")));      
        String h_tel = DwrDecoder.unescape(Utility.trimNull(file.getParameter("h_tel")));           
        String mobile = DwrDecoder.unescape(Utility.trimNull(file.getParameter("mobile")));          
        String bp = DwrDecoder.unescape(Utility.trimNull(file.getParameter("bp")));           
        String fax = DwrDecoder.unescape(Utility.trimNull(file.getParameter("fax")));              
        String e_mail = DwrDecoder.unescape(Utility.trimNull(file.getParameter("e_mail")));         
        Integer cust_type = Utility.parseInt(file.getParameter("cust_type"),null);    
        String touch_type = DwrDecoder.unescape(Utility.trimNull(file.getParameter("touch_type")));   
        String cust_source = DwrDecoder.unescape(Utility.trimNull(file.getParameter("cust_source")));     
        String summary = DwrDecoder.unescape(Utility.trimNull(file.getParameter("summary")));
        Integer input_man = input_operatorCode;
        String cust_no = DwrDecoder.unescape(Utility.trimNull(file.getParameter("cust_no")));      
        String legal_man = DwrDecoder.unescape(Utility.trimNull(file.getParameter("legal_man")));     
        String legal_address = DwrDecoder.unescape(Utility.trimNull(file.getParameter("legal_address")));    
        Integer birthday = Utility.parseInt(file.getParameter("birthday"),null);     
        String post_address2 = DwrDecoder.unescape(Utility.trimNull(file.getParameter("post_address2")));    
        String post_code2 = DwrDecoder.unescape(Utility.trimNull(file.getParameter("post_code2")));   
        Integer print_deploy_bill = Utility.parseInt(file.getParameter("print_deploy_bill"),null);   
        Integer print_post_info = Utility.parseInt(file.getParameter("print_post_info"),null);    
        Integer is_link = Utility.parseInt(file.getParameter("is_link"),null);    
        Integer service_man = Utility.parseInt(file.getParameter("service_man"),null);        
        String vip_card_id = DwrDecoder.unescape(Utility.trimNull(file.getParameter("vip_card_id")));     
        Integer vip_date = Utility.parseInt(file.getParameter("vip_date"),null);  
        String hgtzr_bh = DwrDecoder.unescape(Utility.trimNull(file.getParameter("hgtzr_bh")));      
        String voc_type = DwrDecoder.unescape(Utility.trimNull(file.getParameter("voc_type")));     
        Integer card_valid_date = Utility.parseInt(file.getParameter("card_valid_date"),null);
        String country = DwrDecoder.unescape(Utility.trimNull(file.getParameter("country")));       
        String jg_cust_type = DwrDecoder.unescape(Utility.trimNull(file.getParameter("jg_cust_type")));      
        String contact_man = DwrDecoder.unescape(Utility.trimNull(file.getParameter("contact_man")));        
        String money_source = DwrDecoder.unescape(Utility.trimNull(file.getParameter("money_source")));
        String fact_controller = DwrDecoder.unescape(Utility.trimNull(file.getParameter("fact_controller")));    
        Integer link_type = Utility.parseInt(file.getParameter("link_type"),null);
        java.math.BigDecimal link_gd_money = Utility.parseDecimal(file.getParameter("link_gd_money"),null);   
        String grade_level = DwrDecoder.unescape(Utility.trimNull(file.getParameter("grade_level")));      
        Integer complete_flag = Utility.parseInt(file.getParameter("complete_flag"),null);    
        String gov_prov_regional = DwrDecoder.unescape(Utility.trimNull(file.getParameter("gov_prov_regional")));   
        String gov_regional = DwrDecoder.unescape(Utility.trimNull(file.getParameter("gov_regional")));     
        String legal_tel = DwrDecoder.unescape(Utility.trimNull(file.getParameter("legal_tel")));    
        String legal_mobile = DwrDecoder.unescape(Utility.trimNull(file.getParameter("legal_mobile")));       
        String issued_org = DwrDecoder.unescape(Utility.trimNull(file.getParameter("issued_org")));    
        String industry_post = DwrDecoder.unescape(Utility.trimNull(file.getParameter("industry_post")));   
        String cust_industry = DwrDecoder.unescape(Utility.trimNull(file.getParameter("cust_industry")));   
        String cust_corp_nature = DwrDecoder.unescape(Utility.trimNull(file.getParameter("cust_corp_nature")));   
        String corp_branch = DwrDecoder.unescape(Utility.trimNull(file.getParameter("corp_branch")));
        String service_man_tel = DwrDecoder.unescape(Utility.trimNull(file.getParameter("service_man_tel")));     
        String grade_score = DwrDecoder.unescape(Utility.trimNull(file.getParameter("grade_score")));     
        String fc_card_type = DwrDecoder.unescape(Utility.trimNull(file.getParameter("fc_card_type")));    
        String fc_card_id = DwrDecoder.unescape(Utility.trimNull(file.getParameter("fc_card_id")));    
        Integer fc_card_valid_date = Utility.parseInt(file.getParameter("fc_card_valid_date"),null);
        String summary1 = DwrDecoder.unescape(Utility.trimNull(file.getParameter("summary1")));  
        String summary2 = DwrDecoder.unescape(Utility.trimNull(file.getParameter("summary2")));
        String legal_post_code = DwrDecoder.unescape(Utility.trimNull(file.getParameter("legal_post_code")));
        String trans_name  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("trans_name")));
        String trans_mobile  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("trans_mobile")));
        String trans_tel  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("trans_tel")));
        String trans_address  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("trans_address")));
        String trans_post_code  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("trans_post_code")));
        String register_address  = DwrDecoder.unescape(Utility.trimNull(file.getParameter("register_address")));
        String register_post_code = DwrDecoder.unescape(Utility.trimNull(file.getParameter("register_post_code")));


        vo.setBook_code(book_code);
        vo.setCust_name(cust_name);
        vo.setCust_tel(cust_tel);
        vo.setPost_address(post_address);
        vo.setPost_code(post_code);
        vo.setCard_type(card_type);
        vo.setCard_id(card_id);
        vo.setAge(age);
        vo.setSex(sex);
        vo.setO_tel(o_tel);
        vo.setH_tel(h_tel);
        vo.setMobile(mobile);
        vo.setBp(bp);
        vo.setFax(fax);
        vo.setE_mail(e_mail);
        vo.setCust_type(cust_type);
        vo.setTouch_type(touch_type);
        vo.setCust_source(cust_source);
        vo.setSummary(summary);
        vo.setInput_man(input_man);
        vo.setCust_no(cust_no);
        vo.setLegal_man(legal_man);
        vo.setLegal_address(legal_address);
        vo.setBirthday(birthday);
        vo.setPost_address2(post_address2);
        vo.setPost_code2(post_code2);
        vo.setPrint_deploy_bill(print_deploy_bill);
        vo.setPrint_post_info(print_post_info);
        vo.setIs_link(is_link);
        vo.setService_man(service_man);
        vo.setVip_card_id(vip_card_id);
        vo.setVip_date(vip_date);
        vo.setHgtzr_bh(hgtzr_bh);
        vo.setVoc_type(voc_type);
        vo.setCard_valid_date(card_valid_date);
        vo.setCountry(country);
        vo.setJg_cust_type(jg_cust_type);
        vo.setContact_man(contact_man);
        vo.setMoney_source(money_source);
        vo.setFact_controller(fact_controller);

        vo.setLink_type(link_type);
        vo.setLink_gd_money(link_gd_money);
        vo.setGrade_level(grade_level);
        vo.setComplete_flag(complete_flag);
        vo.setGov_prov_regional(gov_prov_regional);
        vo.setGov_regional(gov_regional);
        vo.setLegal_tel(legal_tel);
        vo.setLegal_mobile(legal_mobile);
        vo.setIssued_org(issued_org);
        vo.setIndustry_post(industry_post);
        vo.setCust_industry(cust_industry);
        vo.setCust_corp_nature(cust_corp_nature);
        vo.setCorp_branch(corp_branch);
        vo.setService_man_tel(service_man_tel);
        vo.setGrade_score(grade_score);
        vo.setFc_card_type(fc_card_type);
        vo.setFc_card_id(fc_card_id);
        vo.setFc_card_valid_date(fc_card_valid_date);
        vo.setSummary1(summary1);
        vo.setSummary2(summary2);
        vo.setLegal_post_code(legal_post_code);
        vo.setTrans_name (trans_name );
        vo.setTrans_tel (trans_tel );
        vo.setTrans_mobile (trans_mobile );
        vo.setTrans_address (trans_address );
        vo.setTrans_post_code (trans_post_code );
        vo.setRegister_address (register_address );
        vo.setRegister_post_code(register_post_code);     
        
        Integer cust_id = customer.append_jh(vo);
        
        returnVal++; 
        file.uploadAttchment(new Integer(4), cust_id,"",null,null,input_operatorCode); 
        returnVal++;      
        out.clear();        
        response.getWriter().write(""+returnVal);
	}catch(Exception e){
	    System.err.println(e.getMessage());
		out.clear();        
	    response.getWriter().write(returnVal+"#"+e.getMessage());
	}finally{
	    customer.remove();
	}
	
%>