package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;
import enfo.crm.vo.CustomerInfoVO;

@Component(value="intrustCustomerInfo")
@Scope("prototype")
public class IntrustCustomerInfoBean extends IntrustBusiBean implements IntrustCustomerInfoLocal {

	 private java.lang.Integer cust_id;

	    private java.lang.Integer book_code;

	    private java.lang.String cust_no;

	    private java.lang.String cust_name;

	    private java.lang.String card_type;

	    private java.lang.String card_type_name;

	    private java.lang.String card_id;

	    private java.lang.Integer cust_type;

	    private java.lang.String cust_type_name;

	    private java.lang.Integer wt_flag;

	    private java.lang.String wt_flag_name;

	    private java.lang.String cust_level;

	    private java.lang.String cust_level_name;

	    private java.lang.String touch_type;

	    private java.lang.String touch_type_name;

	    private java.lang.String cust_source;

	    private java.lang.String cust_source_name;

	    private java.lang.Integer input_man;

	    private java.sql.Timestamp input_time;

	    private java.lang.Integer check_flag;

	    private java.lang.String status;

	    private java.lang.String status_name;

	    private String cust_tel;

	    private String post_address;

	    private String post_code;

	    private Integer age;

	    private Integer sex;

	    private String sex_name;

	    private String o_tel;

	    private String h_tel;

	    private String mobile;

	    private String bp;

	    private String fax;

	    private String e_mail;

	    private Integer rg_times;

	    private java.math.BigDecimal total_money;

	    private java.math.BigDecimal current_money;

	    private java.math.BigDecimal ben_amount;

	    private Integer last_rg_date;

	    private Integer check_man;

	    private Integer modi_man;

	    private Integer modi_time;

	    private Integer cancel_man;

	    private Integer cancel_time;

	    private String summary;

	    private String contract_bh;

	    private Integer product_id;

	    private java.math.BigDecimal curr_to_amount;

	    private String legal_man; //法人姓名

	    private String legal_address; //法人地址

	    private String prov_level;

	    private Integer birthday;

	    private int birthday_temp; //临时变量,因为NULL值没有.intValue()

	    private String post_address2 = "";

	    private String post_code2 = "";

	    private Integer print_deploy_bill; //--是否打印客户对帐单（收益情况）

	    private Integer print_post_info; // --是否打印披露信息

	    private Integer is_link = new Integer(0); //是否关联方

	    private String last_product_name; //--最近购买产品名称

	    private java.math.BigDecimal exchange_amount; //-转让金额

	    private java.math.BigDecimal back_amount; //-兑付金额

	    private Integer from_cust_id;

	    private Integer to_cust_id;

	    private Integer service_man;

	    private String vip_card_id;

	    private Integer vip_date;

	    private String hgtzr_bh;

	    private Integer tzr_flag;

	    private String voc_type;

	    private String voc_type_name;

	    private int modi_flag;

	    private String contact_man;//联系人

	    private Integer card_valid_date;//客户身份证 有效期限 8位日期表示

	    private String country;//客户国籍（9901）

	    private String jg_cust_type;//机构类别（9921），仅在cust_type=2 机构时有效)

	    ///投资人资格标志设置//

	    //MONEY_SOURCE 资金来源
	    private String money_source;

	    private String grade_level;

	    private String fact_controller;//实际控制人
	    
	    private Integer link_type; //关联类型(1302)

	    private BigDecimal link_gd_money;//股东投资入股信托公司金额
	    
	    private String grade_level_name;
	    
	    private Integer complete_flag; 
	    
	    //--------------2011-4-21 add ---------
	    private Integer product_code ;//产品编号
	    
	    private BigDecimal rg_money ;//认购金额
	    
	    private Integer ben_date ;//受益起始日期
	    
	    private Integer ben_end_date ;//受益结束日期
	    
	    private Integer flag ;//投资人类别
	  
	    private String product_name ;

	    protected void validate() throws BusiException {
	        super.validate();

	        if (book_code.intValue() <= 0)
	            throw new BusiException("帐套代码不合法.");
	        //cust_no改成自动生成
	        //if (cust_no.equals("") || cust_no.length() > 8)
	        //	throw new BusiException("客户编号不合法.");
	        //if (cust_name.equals("") || cust_name.length() > 100)
	        //	throw new BusiException("客户姓名不合法.");
	        //if (card_type.equals("") || card_type.length() > 10)
	        //	throw new BusiException("证件类型不合法.");
	        //if (card_id.equals("") || card_id.length() > 40)
	        //	throw new BusiException("证件号码不合法.");
	        //if (cust_type.intValue() <= 0)
	        //	throw new BusiException("客户类别不合法.");
	        //if (wt_flag.intValue() <= 0)
	        //	throw new BusiException("委托人标志不合法.");
	        //if (cust_level.equals("") || cust_level.length() > 10)
	        //	throw new BusiException("客户级别不合法.");
	        //if (touch_type.equals("") || touch_type.length() > 10)
	        //	throw new BusiException("联系方式不合法.");
	        //if (cust_source.equals("") || cust_source.length() > 10)
	        //	throw new BusiException("客户来源不合法.");
	        //if (input_man.intValue() <= 0)
	        //	throw new BusiException("录入员不合法.");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#append()
		 */
	    @Override
		public void append() throws Exception {
	        Object[] oparams = new Object[44];
	        oparams[0] = book_code;
	        oparams[1] = cust_name;
	        oparams[2] = cust_tel;
	        oparams[3] = post_address;
	        oparams[4] = post_code;
	        oparams[5] = card_type;
	        oparams[6] = card_id;
	        oparams[7] = Utility.parseInt(age, new Integer(0));
	        oparams[8] = Utility.parseInt(sex, new Integer(0));
	        oparams[9] = o_tel;
	        oparams[10] = h_tel;
	        oparams[11] = mobile;
	        oparams[12] = bp;
	        oparams[13] = fax;
	        oparams[14] = e_mail;
	        oparams[15] = cust_type;
	        oparams[16] = touch_type;
	        oparams[17] = cust_source;
	        oparams[18] = summary;
	        oparams[19] = input_man;
	        oparams[20] = cust_no;
	        oparams[21] = legal_man;
	        oparams[22] = legal_address;
	        oparams[23] = Utility.parseInt(birthday, new Integer(0));
	        oparams[24] = post_address2;
	        oparams[25] = post_code2;
	        oparams[26] = Utility.parseInt(print_deploy_bill, new Integer(0));
	        oparams[27] = Utility.parseInt(print_post_info, new Integer(0));
	        oparams[28] = Utility.parseInt(is_link, new Integer(0));
	        oparams[29] = Utility.parseInt(service_man, new Integer(0));
	        oparams[30] = vip_card_id;
	        oparams[31] = Utility.parseInt(vip_date, new Integer(0));
	        oparams[32] = hgtzr_bh;
	        oparams[33] = voc_type;
	        oparams[34] = Utility.parseInt(card_valid_date, null);
	        oparams[35] = country;
	        oparams[36] = jg_cust_type;
	        oparams[37] = contact_man;
	        oparams[38] = money_source;
	        oparams[39] = fact_controller;
	        oparams[40] = Utility.parseInt(link_type, new Integer(0));
	        oparams[41] = Utility.parseBigDecimal(link_gd_money, new BigDecimal(0));
	        oparams[42] = grade_level;
	        oparams[43] = Utility.parseInt(complete_flag, new Integer(2));	
	        
	        try {
	            cust_id = (Integer) super
	                    .cudEx(
	                            "{?=call SP_ADD_TCUSTOMERINFO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            oparams, 32, java.sql.Types.INTEGER);
	        } catch (Exception e) {
	            throw new BusiException("客户基本信息新建失败:" + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#hb()
		 */
	    @Override
		public void hb() throws Exception {

	        String hbSql = "{?=call SP_HB_TCUSTOMERINFO(?,?,?)}";
	        Object[] params = new Object[3];
	        params[0] = cust_id;
	        params[1] = to_cust_id;
	        params[2] = input_man;
	        try {
	            super.update(hbSql, params);
	        } catch (Exception e) {
	            throw new BusiException("合并失败！: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#delete()
		 */
	    @Override
		public void delete() throws Exception {

	        Object[] params = new Object[2];
	        params[0] = cust_id;
	        params[1] = input_man;

	        try {
	            super.update("{?=call SP_DEL_TCUSTOMERINFO  (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("客户基本信息删除失败！: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#load()
		 */
	    @Override
		public void load() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = cust_id;
	        params[1] = input_man;
	        super.query("{call SP_QUERY_TCUSTOMERINFO_LOAD (?,?)}", params);
	        getNextForLoad();
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextForLoad()
		 */
	    @Override
		public boolean getNextForLoad() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = (Integer) rowset.getObject("BOOK_CODE");
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_tel = rowset.getString("CUST_TEL");
	            post_address = rowset.getString("POST_ADDRESS");
	            post_code = rowset.getString("POST_CODE");
	            card_type = rowset.getString("CARD_TYPE");
	            card_type_name = rowset.getString("CARD_TYPE_NAME");
	            card_id = rowset.getString("CARD_ID");
	            age = (Integer) rowset.getObject("AGE");
	            sex = (Integer) rowset.getObject("SEX");
	            sex_name = rowset.getString("SEX_NAME");
	            o_tel = rowset.getString("O_TEL");
	            h_tel = rowset.getString("H_TEL");
	            mobile = rowset.getString("MOBILE");
	            bp = rowset.getString("BP");
	            fax = rowset.getString("FAX");
	            e_mail = rowset.getString("E_MAIL");
	            cust_type = (Integer) rowset.getObject("CUST_TYPE");
	            cust_type_name = rowset.getString("CUST_TYPE_NAME");
	            wt_flag = (Integer) rowset.getObject("WT_FLAG");
	            wt_flag_name = rowset.getString("WT_FLAG_NAME");
	            cust_level = rowset.getString("CUST_LEVEL");
	            cust_level_name = rowset.getString("CUST_LEVEL_NAME");
	            touch_type = rowset.getString("TOUCH_TYPE");
	            touch_type_name = rowset.getString("TOUCH_TYPE_NAME");
	            cust_source = rowset.getString("CUST_SOURCE");
	            cust_source_name = rowset.getString("CUST_SOURCE_NAME");
	            rg_times = (Integer) rowset.getObject("RG_TIMES");
	            total_money = rowset.getBigDecimal("TOTAL_MONEY");
	            current_money = rowset.getBigDecimal("CURRENT_MONEY");
	            last_rg_date = (Integer) rowset.getObject("LAST_RG_DATE");
	            input_man = (Integer) rowset.getObject("INPUT_MAN");
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = (Integer) rowset.getObject("CHECK_FLAG");
	            check_man = (Integer) rowset.getObject("CHECK_MAN");
	            modi_man = (Integer) rowset.getObject("MODI_MAN");
	            cancel_man = (Integer) rowset.getObject("CANCEL_MAN");
	            cancel_time = (Integer) rowset.getObject("CANCEL_TIME");
	            status = rowset.getString("STATUS");
	            status_name = rowset.getString("STATUS_NAME");
	            summary = rowset.getString("SUMMARY");
	            legal_man = rowset.getString("LEGAL_MAN");
	            legal_address = rowset.getString("LEGAL_ADDRESS");
	            birthday = (Integer) rowset.getObject("BIRTHDAY");
	            post_address2 = rowset.getString("POST_ADDRESS2");
	            post_code2 = rowset.getString("POST_CODE2");
	            print_deploy_bill = (Integer) rowset.getObject("PRINT_DEPLOY_BILL");
	            print_post_info = (Integer) rowset.getObject("PRINT_POST_INFO");
	            service_man = new Integer(rowset.getString("SERVICE_MAN"));
	            is_link = (Integer) rowset.getObject("IS_LINK");
	            vip_card_id = rowset.getString("VIP_CARD_ID");
	            vip_date = (Integer) rowset.getObject("VIP_DATE");
	            hgtzr_bh = rowset.getString("HGTZR_BH");
	            voc_type = rowset.getString("VOC_TYPE");
	            voc_type_name = rowset.getString("VOC_TYPE_NAME");

	            modi_flag = rowset.getInt("MODI_FLAG");//只有完全查看权限的人才能修改客户信息:1完全，2部份
	            card_valid_date = (Integer) rowset.getObject("CARD_VALID_DATE");
	            country = rowset.getString("COUNTRY");
	            jg_cust_type = rowset.getString("JG_CUST_TYPE");

	            contact_man = rowset.getString("CONTACT_MAN");
	            money_source = rowset.getString("MONEY_SOURCE");
	            fact_controller = rowset.getString("FACT_CONTROLLER");
	            link_type = (Integer)rowset.getObject("LINK_TYPE");
	            link_gd_money = rowset.getBigDecimal("LINK_GD_MONEY");
	            
	            grade_level = rowset.getString("GRADE_LEVEL"); 
	            grade_level_name = rowset.getString("GRADE_LEVEL_NAME");
	            complete_flag = Utility.parseInt(rowset.getString("COMPLETE_FLAG"), new Integer(2));
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#save()
		 */
	    @Override
		public void save() throws Exception {

	        Object[] params = new Object[44];
	        params[0] = cust_id;
	        params[1] = cust_name;
	        params[2] = cust_tel;
	        params[3] = post_address;
	        params[4] = post_code;
	        params[5] = card_type;
	        params[6] = card_id;
	        params[7] = Utility.parseInt(age, new Integer(0));
	        params[8] = Utility.parseInt(sex, new Integer(0));
	        params[9] = o_tel;
	        params[10] = h_tel;
	        params[11] = mobile;
	        params[12] = bp;
	        params[13] = fax;
	        params[14] = e_mail;
	        params[15] = Utility.parseInt(cust_type, new Integer(0));
	        params[16] = touch_type;
	        params[17] = cust_source;
	        params[18] = summary;
	        params[19] = input_man;
	        params[20] = cust_no;
	        params[21] = legal_man;
	        params[22] = legal_address;
	        params[23] = Utility.parseInt(birthday, new Integer(0));
	        params[24] = post_address2;
	        params[25] = post_code2;
	        params[26] = Utility.parseInt(print_deploy_bill, new Integer(0));
	        params[27] = Utility.parseInt(print_post_info, new Integer(0));
	        params[28] = Utility.parseInt(is_link, new Integer(0));
	        params[29] = Utility.parseInt(service_man, input_man);
	        params[30] = vip_card_id;
	        params[31] = Utility.parseInt(vip_date, input_man);
	        params[32] = hgtzr_bh;
	        params[33] = voc_type;
	        params[34] = Utility.parseInt(card_valid_date, null);
	        params[35] = country;
	        params[36] = jg_cust_type;
	        params[37] = contact_man;
	        params[38] = money_source;
	        params[39] = fact_controller;
	        params[40] = Utility.parseInt(link_type, new Integer(0));
	        params[41] = Utility.parseBigDecimal(link_gd_money, new BigDecimal(0));
	        params[42] = grade_level;
	        params[43] = Utility.parseInt(complete_flag, new Integer(2));	
	        try {
	            super
	                    .append(
	                            "{?=call SP_MODI_TCUSTOMERINFO (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("客户基本信息保存失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#check()
		 */
	    @Override
		public void check() throws Exception {

	        Object[] params = new Object[2];
	        params[0] = cust_id;
	        params[1] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_TCUSTOMERINFO (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("客户信息审核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#CheckCardID()
		 */
	    @Override
		public void CheckCardID() throws Exception {

	        Object[] params = new Object[2];
	        params[0] = book_code;
	        params[1] = card_id;

	        //super.query("SELECT * FROM TCUSTOMERINFO WHERE BOOK_CODE = ? AND
	        // CARD_ID = ? AND STATUS<>112805",params);
	        super.query("{call SP_QUERY_TCUSTOMERINFO_CARD_ID(?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#UpdateCardID()
		 */
	    @Override
		public void UpdateCardID() throws Exception {

	        Object[] params = new Object[3];
	        params[0] = cust_id;
	        params[1] = card_id;
	        params[2] = input_man;

	        try {
	            super
	                    .update("{?=call SP_MODI_TCUSTOMERINFO_CARDID(?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("证件号码更新失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#list()
		 */
	    @Override
		public void list() throws Exception {
	        query(" $ $ $0");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#querybyCustNo(java.lang.String)
		 */
	    @Override
		public void querybyCustNo(String custNo) throws Exception {
	        query(" $ " + custNo + " $ " + "$0");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryNewCust(java.lang.String)
		 */
	    @Override
		public void queryNewCust(String custName) throws Exception {
	        query(" $ $ " + custName + " $1");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#query(java.lang.String)
		 */
	    @Override
		public void query(String sQuery) throws Exception {

	        String[] paras = Utility.splitString(sQuery, "$");

	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = paras[1].trim();
	        params[3] = paras[2].trim();
	        params[4] = new Integer(Utility.parseInt(paras[3].trim(), 0));
	        params[5] = Utility.parseInt(input_man, new Integer(0));
	        params[6] = Utility.parseInt(product_id, new Integer(0));
	        params[7] = prov_level;

	        super.query("{call SP_QUERY_TCUSTOMERINFO1 (?,?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryByCustNo()
		 */
	    @Override
		public void queryByCustNo() throws Exception {

	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = cust_no;
	        params[2] = card_id;
	        params[3] = vip_card_id;
	        params[4] = hgtzr_bh;
	        params[5] = cust_name;
	        params[6] = is_link;
	        params[7] = input_man;
	        super.query("{call SP_QUERY_TCUSTOMERINFO_NO (?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryCustByName(java.lang.Integer)
		 */
	    @Override
		public void queryCustByName(Integer flag) throws Exception {

	        Object[] params = new Object[3];
	        params[0] = cust_name;
	        params[1] = cust_id;
	        params[2] = flag;

	        super.query("{call SP_QUERY_TCUSTOMERINFO_NAME (?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextForList()
		 */
	    @Override
		public boolean getNextForList() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            card_type = rowset.getString("CARD_TYPE");
	            card_type_name = rowset.getString("CARD_TYPE_NAME");
	            card_id = rowset.getString("CARD_ID");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryRepeatCustomers(int, int)
		 */
	    @Override
		public void queryRepeatCustomers(int repeattime, int flag) throws Exception {

	        Object[] params = new Object[5];
	        params[0] = new Integer(repeattime);
	        params[1] = new Integer(flag);
	        params[2] = input_man;
	        params[3] = card_id;
	        params[4] = book_code;

	        super.query("{call SP_QUERY_TCUSTOMERINFO_REPEAT_CARD(?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getRepeatCustomersNext()
		 */
	    @Override
		public boolean getRepeatCustomersNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            card_type = rowset.getString("CARD_TYPE");
	            card_type_name = rowset.getString("CARD_TYPE_NAME");
	            card_id = rowset.getString("CARD_ID");
	            cust_type = (Integer) rowset.getObject("CUST_TYPE");
	            cust_type_name = rowset.getString("CUST_TYPE_NAME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryCustinfoForPost(java.lang.String, java.lang.String, java.lang.String)
		 */
	    @Override
		public void queryCustinfoForPost(String busi_id, String start_acct,
	            String end_acct) throws Exception {

	        Object[] params = new Object[4];
	        params[0] = book_code;
	        params[1] = busi_id;
	        params[2] = start_acct;
	        params[3] = end_acct;

	        super.query("{call SP_QUERY_TENTCUSTPOSTINFO(?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCustPostNext()
		 */
	    @Override
		public boolean getCustPostNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            post_address = rowset.getString("ADDRESS");
	            post_code = rowset.getString("POSTCODE");
	            cust_name = rowset.getString("CUST_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	        }
	        return b;
	    }

	    //	参数：CUST_ID$CUST_NO$CUST_NAME$CUST_SOURCE$CARD_TYPE$CARD_ID$TOUCH_TYPE$MIN_TIMES$MAX_TIMES
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#advanceQuery(java.lang.String)
		 */
	    @Override
		public void advanceQuery(String sQuery) throws Exception {
	        String[] paras = Utility.splitString(sQuery + " ", "$");
	        Object[] params = new Object[35];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = Utility.trimNull(paras[1].trim());
	        params[3] = Utility.trimNull(paras[2].trim());
	        params[4] = Utility.trimNull(paras[3].trim());
	        params[5] = Utility.trimNull(paras[4].trim());
	        params[6] = Utility.trimNull(paras[5].trim());
	        params[7] = Utility.trimNull(paras[6].trim());
	        params[8] = new Integer(Utility.parseInt(paras[7].trim(), 0));
	        params[9] = new Integer(Utility.parseInt(paras[8].trim(), 0));
	        params[10] = operator;
	        params[11] = paras[9].trim();
	        params[12] = paras[10].trim();
	        params[13] = new Integer(Utility.parseInt(paras[11].trim(), 0));
	        params[14] = new Integer(Utility.parseInt(paras[12].trim(), 0));
	        params[15] = paras[13].trim();
	        params[16] = new Integer(Utility.parseInt(paras[14].trim(), 0));
	        params[17] = paras[15].trim();
	        params[18] = Utility.parseDecimal(paras[16].trim(), new BigDecimal(0));
	        params[19] = Utility.parseDecimal(paras[17].trim(), new BigDecimal(0));
	        params[20] = new Integer(Utility.parseInt(paras[18].trim(), 0));
	        params[21] = Utility.trimNull(paras[19].trim());
	        params[22] = new Integer(Utility.parseInt(paras[20].trim(),
	                birthday_temp));
	        params[23] = new Integer(Utility.parseInt(paras[21].trim(), 0));
	        params[24] = new Integer(Utility.parseInt(paras[22].trim(), 0));
	        params[25] = prov_level;
	        params[26] = Utility.parseDecimal(paras[25].trim(), new BigDecimal(0));
	        params[27] = Utility.parseDecimal(paras[26].trim(), new BigDecimal(0));
	        params[28] = Utility.trimNull(paras[27].trim());
	        params[29] = Utility.trimNull(paras[28].trim());
	        params[30] = Utility.trimNull(paras[29].trim());
	        params[31] = Utility.parseInt(paras[30].trim(), null);
	        params[32] = new Integer(Utility.parseInt(paras[31].trim(), 0));
	        params[33] = new Integer(Utility.parseInt(paras[32].trim(), 0));
	        params[34] = Utility.parseInt(complete_flag, new Integer(0));

	        super
	                .query(
	                        "{call SP_QUERY_TCUSTOMERINFO2 (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                        params);
	    }

	    //	CREATE PROCEDURE SP_QUERY_TCUSTOMERINFO2 @IN_BOOK_CODE INT,
	    //											 @IN_CUST_ID INT,
	    //											 @IN_CUST_NO VARCHAR(8),
	    //											 @IN_CUST_NAME VARCHAR(100),
	    //											 @IN_CUST_SOURCE VARCHAR(10),
	    //											 @IN_CARD_TYPE VARCHAR(10),
	    //											 @IN_CARD_ID VARCHAR(20),
	    //											 @IN_TOUCH_TYPE VARCHAR(10),
	    //											 @IN_MIN_TIMES INT,
	    //											 @IN_MAX_TIMES INT,
	    //											 @IN_INPUT_MAN INT,
	    //											 @IN_TEL VARCHAR(20) = NULL,
	    //											 @IN_ADDRESS VARCHAR(60) = NULL,
	    //											 @IN_CUST_TYPE INT = NULL,
	    //											 @IN_PRODUCT_ID INT = NULL,
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#advanceQuery2()
		 */
	    @Override
		public void advanceQuery2() throws Exception {
	        Object[] params = new Object[15];
	        params[0] = book_code;
	        params[1] = new Integer(0);
	        params[2] = Utility.trimNull(cust_no);
	        params[3] = Utility.trimNull(cust_name);
	        params[4] = Utility.trimNull("");
	        params[5] = Utility.trimNull("");
	        params[6] = Utility.trimNull(card_id);
	        params[7] = Utility.trimNull("");
	        params[8] = new Integer(0);
	        params[9] = new Integer(0);
	        params[10] = input_man;
	        params[11] = "";
	        params[12] = "";
	        params[13] = "";
	        params[14] = Utility.parseInt(product_id, new Integer(0));
	        super
	                .query(
	                        "{call SP_QUERY_TCUSTOMERINFO2 (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#advanceQuery_1(java.lang.String)
		 */
	    @Override
		public sun.jdbc.rowset.CachedRowSet advanceQuery_1(String sQuery)
	            throws Exception {

	        String[] paras = Utility.splitString(sQuery + " ", "$");
	        Connection conn = null;
	        CallableStatement stmt = null;
	        ResultSet rslist = null;
	        try {
	            conn = IntrustDBManager.getConnection();
	            stmt = conn
	                    .prepareCall(
	                            "{call SP_QUERY_TCUSTOMERINFO2 (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            ResultSet.TYPE_SCROLL_INSENSITIVE,
	                            ResultSet.CONCUR_READ_ONLY);
	            stmt.setInt(1, book_code.intValue());
	            stmt.setInt(2, Utility.parseInt(paras[0].trim(), 0));
	            stmt.setString(3, Utility.trimNull(paras[1].trim()));
	            stmt.setString(4, Utility.trimNull(paras[2].trim()));
	            stmt.setString(5, Utility.trimNull(paras[3].trim()));
	            stmt.setString(6, Utility.trimNull(paras[4].trim()));
	            stmt.setString(7, Utility.trimNull(paras[5].trim()));
	            stmt.setString(8, Utility.trimNull(paras[6].trim()));
	            stmt.setInt(9, Utility.parseInt(paras[7].trim(), 0));
	            stmt.setInt(10, Utility.parseInt(paras[8].trim(), 0));
	            stmt.setInt(11, input_man.intValue());
	            stmt.setString(12, Utility.trimNull(paras[9].trim()));
	            stmt.setString(13, Utility.trimNull(paras[10].trim()));
	            stmt.setInt(14, Utility.parseInt(paras[11].trim(), 0));
	            stmt.setInt(15, Utility.parseInt(paras[12].trim(), 0));
	            stmt.setString(16, Utility.trimNull(paras[13].trim()));
	            stmt.setInt(17, Utility.parseInt(paras[14].trim(), 0));
	            stmt.setString(18, Utility.trimNull(paras[15].trim()));
	            stmt.setBigDecimal(19, Utility.parseDecimal(paras[16].trim(),
	                    new BigDecimal(0)));
	            stmt.setBigDecimal(20, Utility.parseDecimal(paras[17].trim(),
	                    new BigDecimal(0)));
	            stmt.setInt(21, Utility.parseInt(paras[18].trim(), 0));
	            stmt.setString(22, Utility.trimNull(paras[19].trim()));
	            stmt.setInt(23, Utility.parseInt(paras[20].trim(), birthday_temp));
	            stmt.setInt(24, Utility.parseInt(paras[21].trim(), 0));
	            stmt.setInt(25, Utility.parseInt(paras[22].trim(), 0));
	            stmt.setString(26, prov_level);
	            stmt.setBigDecimal(27, Utility.parseDecimal(paras[25].trim(),
	                    new BigDecimal(0)));
	            stmt.setBigDecimal(28, Utility.parseDecimal(paras[26].trim(),
	                    new BigDecimal(0)));
	            stmt.setString(29, Utility.trimNull(paras[27].trim()));
	            stmt.setString(30, Utility.trimNull(paras[28].trim()));
	            stmt.setString(31, Utility.trimNull(paras[29].trim()));
	            rslist = stmt.executeQuery();
	            rowset.close();
	            rowset.populate(rslist);
	        } finally {
	            if (rslist != null)
	                rslist.close();
	            if (stmt != null)
	                stmt.close();
	            if (conn != null)
	                conn.close();
	        }
	        return rowset;

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextForQuery2()
		 */
	    @Override
		public boolean getNextForQuery2() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            card_id = rowset.getString("CARD_ID");
	            cust_tel = rowset.getString("CUST_TEL");
	            e_mail = rowset.getString("E_MAIL");
	            status_name = rowset.getString("STATUS_NAME");
	            last_rg_date = (Integer) rowset.getObject("LAST_RG_DATE");
	            curr_to_amount = rowset.getBigDecimal("CURR_TO_AMOUNT");
	            total_money = rowset.getBigDecimal("TOTAL_MONEY");
	            current_money = rowset.getBigDecimal("CURRENT_MONEY");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            exchange_amount = rowset.getBigDecimal("EXCHANGE_AMOUNT");
	            back_amount = rowset.getBigDecimal("BACK_AMOUNT");
	            cust_type = (Integer) rowset.getObject("CUST_TYPE");
	            this.grade_level = rowset.getString("GRADE_LEVEL_NAME");
	            this.card_type_name = rowset.getString("CARD_TYPE_NAME");
	            complete_flag = Utility.parseInt(rowset.getString("COMPLETE_FLAG"), new Integer(2));
	            
	        }
	        return b;
	    }
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextForQuery3()
		 */
		@Override
		public List getNextForQuery3() throws Exception{
			List list = new ArrayList();
			while(super.getNext()){
				Map map = new HashMap();
				map.put("CUST_ID", rowset.getObject("CUST_ID"));
				map.put("CUST_NO", rowset.getObject("CUST_NO"));
				map.put("CUST_NAME", rowset.getObject("CUST_NAME"));
				map.put("SEX", rowset.getObject("SEX"));
				map.put("BIRTHDAY", rowset.getObject("BIRTHDAY"));
				map.put("CUST_LEVEL_NAME", rowset.getObject("CUST_LEVEL_NAME"));
				map.put("POST_ADDRESS", rowset.getObject("POST_ADDRESS"));
				map.put("CARD_ID", rowset.getObject("CARD_ID"));
				map.put("CUST_TEL", rowset.getObject("CUST_TEL"));
				map.put("E_MAIL", rowset.getObject("E_MAIL"));
				map.put("MOBILE", rowset.getObject("MOBILE"));
				map.put("BP", rowset.getObject("BP"));
				map.put("STATUS_NAME", rowset.getObject("STATUS_NAME"));
				map.put("LAST_RG_DATE", rowset.getObject("LAST_RG_DATE"));
				map.put("CURR_TO_AMOUNT", rowset.getObject("CURR_TO_AMOUNT"));
				map.put("TOTAL_MONEY", rowset.getObject("TOTAL_MONEY"));
				map.put("CURRENT_MONEY", rowset.getObject("CURRENT_MONEY"));
				map.put("BEN_AMOUNT", rowset.getObject("BEN_AMOUNT"));
				map.put("EXCHANGE_AMOUNT", rowset.getObject("EXCHANGE_AMOUNT"));
				map.put("BACK_AMOUNT", rowset.getObject("BACK_AMOUNT"));
				map.put("SERVICE_MAN", rowset.getObject("SERVICE_MAN"));
				map.put("CUST_TYPE", rowset.getObject("CUST_TYPE"));
				map.put("MODI_FLAG", rowset.getObject("MODI_FLAG"));
				map.put("GRADE_LEVEL", rowset.getObject("GRADE_LEVEL"));
				map.put("GRADE_LEVEL_NAME", rowset.getObject("GRADE_LEVEL_NAME"));
				map.put("CARD_TYPE_NAME", rowset.getObject("CARD_TYPE_NAME"));
				map.put("POST_ADDRESS2", rowset.getObject("POST_ADDRESS2"));
				map.put("POST_CODE2", rowset.getObject("POST_CODE2"));
				list.add(map);
			}
			return list;
		}

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextForEmail()
		 */
	    @Override
		public boolean getNextForEmail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            e_mail = rowset.getString("E_MAIL");
	            mobile = rowset.getString("MOBILE");
	            bp = rowset.getString("BP");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNext()
		 */
	    @Override
		public boolean getNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = (Integer) rowset.getObject("BOOK_CODE");
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_tel = rowset.getString("CUST_TEL");
	            post_address = rowset.getString("POST_ADDRESS");
	            post_code = rowset.getString("POST_CODE");
	            card_type = rowset.getString("CARD_TYPE");
	            card_type_name = rowset.getString("CARD_TYPE_NAME");
	            card_id = rowset.getString("CARD_ID");
	            age = (Integer) rowset.getObject("AGE");
	            sex = (Integer) rowset.getObject("SEX");
	            sex_name = rowset.getString("SEX_NAME");
	            o_tel = rowset.getString("O_TEL");
	            h_tel = rowset.getString("H_TEL");
	            mobile = rowset.getString("MOBILE");
	            bp = rowset.getString("BP");
	            fax = rowset.getString("FAX");
	            e_mail = rowset.getString("E_MAIL");
	            cust_type = (Integer) rowset.getObject("CUST_TYPE");
	            cust_type_name = rowset.getString("CUST_TYPE_NAME");
	            wt_flag = (Integer) rowset.getObject("WT_FLAG");
	            wt_flag_name = rowset.getString("WT_FLAG_NAME");
	            cust_level = rowset.getString("CUST_LEVEL");
	            cust_level_name = rowset.getString("CUST_LEVEL_NAME");
	            touch_type = rowset.getString("TOUCH_TYPE");
	            touch_type_name = rowset.getString("TOUCH_TYPE_NAME");
	            cust_source = rowset.getString("CUST_SOURCE");
	            cust_source_name = rowset.getString("CUST_SOURCE_NAME");
	            rg_times = (Integer) rowset.getObject("RG_TIMES");
	            total_money = rowset.getBigDecimal("TOTAL_MONEY");
	            current_money = rowset.getBigDecimal("CURRENT_MONEY");
	            last_rg_date = (Integer) rowset.getObject("LAST_RG_DATE");
	            input_man = (Integer) rowset.getObject("INPUT_MAN");
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = (Integer) rowset.getObject("CHECK_FLAG");
	            check_man = (Integer) rowset.getObject("CHECK_MAN");
	            modi_man = (Integer) rowset.getObject("MODI_MAN");
	            cancel_man = (Integer) rowset.getObject("CANCEL_MAN");
	            cancel_time = (Integer) rowset.getObject("CANCEL_TIME");
	            status = rowset.getString("STATUS");
	            status_name = rowset.getString("STATUS_NAME");
	            summary = rowset.getString("SUMMARY");
	            legal_man = rowset.getString("LEGAL_MAN");
	            legal_address = rowset.getString("LEGAL_ADDRESS");
	            is_link = (Integer) rowset.getObject("IS_LINK");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setProperties(javax.servlet.ServletRequest, java.lang.String)
		 */
	    @Override
		public void setProperties(javax.servlet.ServletRequest request,
	            String prefix) throws Exception {
	        cust_name = request.getParameter(prefix + "_cust_name");
	        cust_no = request.getParameter(prefix + "_cust_no");
	        cust_tel = request.getParameter(prefix + "_cust_tel");
	        card_id = request.getParameter(prefix + "_card_id");

	        post_address = request.getParameter(prefix + "_post_address");
	        post_code = request.getParameter(prefix + "_post_code");

	        post_address2 = request.getParameter(prefix + "_post_address2");
	        post_code2 = request.getParameter(prefix + "_post_code2");

	        card_type = request.getParameter(prefix + "_card_type");
	        card_type_name = request.getParameter(prefix + "_card_type_name");
	        o_tel = request.getParameter(prefix + "_o_tel");
	        h_tel = request.getParameter(prefix + "_h_tel");
	        mobile = request.getParameter(prefix + "_mobile");
	        bp = request.getParameter(prefix + "_bp");
	        sex_name = request.getParameter(prefix + "_sex_name");
	        fax = request.getParameter(prefix + "_fax");
	        e_mail = request.getParameter(prefix + "_e_mail");
	        cust_level = request.getParameter(prefix + "_cust_level");
	        touch_type = request.getParameter(prefix + "_touch_type");

	        cust_source = request.getParameter(prefix + "_cust_source");
	        summary = request.getParameter(prefix + "_summary");
	        legal_man = request.getParameter(prefix + "_legal_man");
	        legal_address = request.getParameter(prefix + "_legal_address");
	        vip_card_id = request.getParameter(prefix + "_vip_card_id");
	        hgtzr_bh = request.getParameter(prefix + "_hgtzr_bh");
	        cust_type = Utility.parseInt(request
	                .getParameter(prefix + "_cust_type"), new Integer(1));
	        is_link = Utility.parseInt(request.getParameter(prefix + "_is_link"),
	                new Integer(0));
	        print_deploy_bill = Utility.parseInt(request.getParameter(prefix
	                + "_print_deploy_bill"), new Integer(0));
	        print_post_info = Utility.parseInt(request.getParameter(prefix
	                + "_print_post_info"), new Integer(0));
	        age = Utility.parseInt(request.getParameter(prefix + "_age"),
	                new Integer(0));
	        sex = Utility.parseInt(request.getParameter(prefix + "_sex"),
	                new Integer(0));
	        wt_flag = Utility.parseInt(request.getParameter(prefix + "_wt_flag"),
	                new Integer(0));
	        birthday = Utility.parseInt(request.getParameter(prefix + "_birthday"),
	                new Integer(Utility.getCurrentDate()));
	        service_man = Utility.parseInt(request.getParameter(prefix
	                + "_service_man"), new Integer(0));
	        vip_date = Utility.parseInt(request.getParameter(prefix + "_vip_date"),
	                new Integer(0));
	        voc_type = Utility
	                .trimNull(request.getParameter(prefix + "_zyhy_type"));
	        
	        link_type = Utility.parseInt(request.getParameter(prefix + "_link_type"),new Integer(0));
	        link_gd_money = Utility.parseDecimal(request.getParameter(prefix + "_link_gd_money"),new BigDecimal(0));
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#listCustByServiceMan()
		 */

	    @Override
		public void listCustByServiceMan() throws BusiException {
	        Object[] params = new Object[7];
	        params[0] = book_code;
	        params[1] = Utility.trimNull(cust_no);
	        params[2] = Utility.trimNull(cust_name);
	        params[3] = Utility.trimNull(card_id);
	        params[4] = Utility.parseInt(product_id, new Integer(0));
	        params[5] = Utility.parseInt(service_man, new Integer(0));
	        params[6] = Utility.parseInt(input_man, new Integer(0));

	        try {
	            super.query("{call SP_QUERY_TCUSTOMERINFO_SERVICE(?,?,?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("根据客户经理查询客户信息失败:" + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextCustByServiceMan()
		 */
	    @Override
		public boolean getNextCustByServiceMan() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            card_id = rowset.getString("CARD_ID");
	            service_man = (Integer) rowset.getObject("SERVICE_MAN");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#modiServiceManByCustId()
		 */

	    @Override
		public void modiServiceManByCustId() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(cust_id, new Integer(0));
	        params[1] = Utility.parseInt(service_man, new Integer(0));
	        params[2] = Utility.parseInt(input_man, new Integer(0));
	        try {
	            super.update("{?=call SP_MODI_TCUSTOMERINFO_SERVICE_MAN(?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("修改客户经理信息失败:" + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#syncCrmCust()
		 */
	    @Override
		public void syncCrmCust() throws Exception {

	        Object[] params = new Object[14];
	        params[0] = book_code;
	        params[1] = cust_no;
	        params[2] = cust_name;
	        params[3] = card_type;
	        params[4] = card_id;
	        params[5] = cust_type;
	        params[6] = legal_man;
	        params[7] = contact_man;
	        params[8] = post_address;
	        params[9] = post_code;
	        params[10] = e_mail;
	        params[11] = mobile;
	        params[12] = service_man;
	        params[13] = input_man;
	        try {
	            super
	                    .update(
	                            "{?=call SP_SYNC_CRM_TCUSTOMERINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("同步客户信息失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#modiGradeLevelByCustId()
		 */

	    @Override
		public void modiGradeLevelByCustId() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(cust_id, new Integer(0));
	        params[1] = Utility.trimNull(grade_level);
	        params[2] = Utility.parseInt(modi_man, new Integer(0));
	        try {
	            super.update("{?=call SP_MODI_TCUSTOMERINFO_GRADE_LEVEL(?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("修改客户评级信息失败:" + e.getMessage());
	        }

	    }
	    
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#importCustomer(enfo.crm.vo.CustomerInfoVO)
		 */
		@Override
		public void importCustomer(CustomerInfoVO vo) throws BusiException{
			try {
				Object[] params= new Object[17];
				String sql = "{?=call SP_IMPORT_CUSTOMERS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			
				params[0] = vo.getCust_name();
				params[1] = vo.getCust_type_name();
				params[2] = vo.getCard_type_name();
				params[3] = vo.getCard_id();
				params[4] = vo.getPost_address();
				params[5] = vo.getPost_code();
				params[6] = vo.getMobile();
				params[7] = vo.getCust_tel();
				params[8] = vo.getE_mail();
				params[9] = vo.getContact_man();
				params[10] = vo.getContact_man();
				params[11] = vo.getContact_man();
				params[12] = vo.getTouch_type_name();
				params[13] = vo.getCust_source_name();
				params[14] = vo.getAge();
				params[15] = vo.getContact_man();
				params[16] = vo.getInput_man();

				super.update(sql,params);
			} catch (Exception e) {
				throw new BusiException("导入客户信息失败:" + e.getMessage());
			}		
		}
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#queryQualified()
		 */
		@Override
		public void queryQualified() throws Exception{
	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = Utility.parseInt(flag, new Integer(0));
	        try {
	        	super.query("{call SP_QUERY_QUALIFIED(?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("合格投资人查询失败:" + e.getMessage());
	        }
		}

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getNextQualified()
		 */
		@Override
		public boolean getNextQualified() throws Exception{
			boolean b = super.getNext() ;
			if(b){
	        	product_code = Utility.parseInt(new Integer(rowset
	                    .getInt("PRODUCT_CODE")), new Integer(0));
	        	product_name = Utility.trimNull(rowset.getString("PRODUCT_NAME")) ;
	        	contract_bh = Utility.trimNull(rowset.getString("CONTRACT_BH"));
	        	cust_name = Utility.trimNull(rowset.getString("CUST_NAME"));
	        	cust_type_name = rowset.getString("CUST_TYPE_NAME") ;
	        	ben_amount = Utility.parseBigDecimal(rowset
	                    .getBigDecimal("BEN_AMOUNT"), new BigDecimal(0));
	        	ben_date = Utility.parseInt(new Integer(rowset.getInt("BEN_DATE")) , new Integer(0));
	        	ben_end_date = Utility.parseInt(new Integer(rowset.getInt("BEN_END_DATE")) , new Integer(0));
	        	rg_money = Utility.parseBigDecimal(rowset
	                    .getBigDecimal("RG_MONEY"), new BigDecimal(0));
	        	
			}
			return b ;
		}

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getAge()
		 */
	    @Override
		public Integer getAge() {
	        return age;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setAge(java.lang.Integer)
		 */
	    @Override
		public void setAge(Integer age) {
	        this.age = age;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBen_amount()
		 */
	    @Override
		public java.math.BigDecimal getBen_amount() {
	        return ben_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBen_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setBen_amount(java.math.BigDecimal ben_amount) {
	        this.ben_amount = ben_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBirthday()
		 */
	    @Override
		public Integer getBirthday() {
	        return birthday;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBirthday(java.lang.Integer)
		 */
	    @Override
		public void setBirthday(Integer birthday) {
	        this.birthday = birthday;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBook_code()
		 */
	    @Override
		public java.lang.Integer getBook_code() {
	        return book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBook_code(java.lang.Integer)
		 */
	    @Override
		public void setBook_code(java.lang.Integer book_code) {
	        this.book_code = book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBp()
		 */
	    @Override
		public String getBp() {
	        return bp;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBp(java.lang.String)
		 */
	    @Override
		public void setBp(String bp) {
	        this.bp = bp;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCancel_man()
		 */
	    @Override
		public Integer getCancel_man() {
	        return cancel_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCancel_man(java.lang.Integer)
		 */
	    @Override
		public void setCancel_man(Integer cancel_man) {
	        this.cancel_man = cancel_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCancel_time()
		 */
	    @Override
		public Integer getCancel_time() {
	        return cancel_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCancel_time(java.lang.Integer)
		 */
	    @Override
		public void setCancel_time(Integer cancel_time) {
	        this.cancel_time = cancel_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCard_id()
		 */
	    @Override
		public java.lang.String getCard_id() {
	        return card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCard_id(java.lang.String)
		 */
	    @Override
		public void setCard_id(java.lang.String card_id) {
	        this.card_id = card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCard_type()
		 */
	    @Override
		public java.lang.String getCard_type() {
	        return card_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCard_type(java.lang.String)
		 */
	    @Override
		public void setCard_type(java.lang.String card_type) {
	        this.card_type = card_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCard_type_name()
		 */
	    @Override
		public java.lang.String getCard_type_name() {
	        return card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCard_type_name(java.lang.String)
		 */
	    @Override
		public void setCard_type_name(java.lang.String card_type_name) {
	        this.card_type_name = card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCard_valid_date()
		 */
	    @Override
		public Integer getCard_valid_date() {
	        return card_valid_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCard_valid_date(java.lang.Integer)
		 */
	    @Override
		public void setCard_valid_date(Integer card_valid_date) {
	        this.card_valid_date = card_valid_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCheck_flag()
		 */
	    @Override
		public java.lang.Integer getCheck_flag() {
	        return check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCheck_flag(java.lang.Integer)
		 */
	    @Override
		public void setCheck_flag(java.lang.Integer check_flag) {
	        this.check_flag = check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCheck_man()
		 */
	    @Override
		public Integer getCheck_man() {
	        return check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCheck_man(java.lang.Integer)
		 */
	    @Override
		public void setCheck_man(Integer check_man) {
	        this.check_man = check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getContact_man()
		 */
	    @Override
		public String getContact_man() {
	        return contact_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setContact_man(java.lang.String)
		 */
	    @Override
		public void setContact_man(String contact_man) {
	        this.contact_man = contact_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getContract_bh()
		 */
	    @Override
		public String getContract_bh() {
	        return contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setContract_bh(java.lang.String)
		 */
	    @Override
		public void setContract_bh(String contract_bh) {
	        this.contract_bh = contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCountry()
		 */
	    @Override
		public String getCountry() {
	        return country;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCountry(java.lang.String)
		 */
	    @Override
		public void setCountry(String country) {
	        this.country = country;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCurrent_money()
		 */
	    @Override
		public java.math.BigDecimal getCurrent_money() {
	        return current_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCurrent_money(java.math.BigDecimal)
		 */
	    @Override
		public void setCurrent_money(java.math.BigDecimal current_money) {
	        this.current_money = current_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_id()
		 */
	    @Override
		public java.lang.Integer getCust_id() {
	        return cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_id(java.lang.Integer)
		 */
	    @Override
		public void setCust_id(java.lang.Integer cust_id) {
	        this.cust_id = cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_level()
		 */
	    @Override
		public java.lang.String getCust_level() {
	        return cust_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_level(java.lang.String)
		 */
	    @Override
		public void setCust_level(java.lang.String cust_level) {
	        this.cust_level = cust_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_level_name()
		 */
	    @Override
		public java.lang.String getCust_level_name() {
	        return cust_level_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_level_name(java.lang.String)
		 */
	    @Override
		public void setCust_level_name(java.lang.String cust_level_name) {
	        this.cust_level_name = cust_level_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_name()
		 */
	    @Override
		public java.lang.String getCust_name() {
	        return cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_name(java.lang.String)
		 */
	    @Override
		public void setCust_name(java.lang.String cust_name) {
	        this.cust_name = cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_no()
		 */
	    @Override
		public java.lang.String getCust_no() {
	        return cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_no(java.lang.String)
		 */
	    @Override
		public void setCust_no(java.lang.String cust_no) {
	        this.cust_no = cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_source()
		 */
	    @Override
		public java.lang.String getCust_source() {
	        return cust_source;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_source(java.lang.String)
		 */
	    @Override
		public void setCust_source(java.lang.String cust_source) {
	        this.cust_source = cust_source;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_source_name()
		 */
	    @Override
		public java.lang.String getCust_source_name() {
	        return cust_source_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_source_name(java.lang.String)
		 */
	    @Override
		public void setCust_source_name(java.lang.String cust_source_name) {
	        this.cust_source_name = cust_source_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_tel()
		 */
	    @Override
		public String getCust_tel() {
	        return cust_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_tel(java.lang.String)
		 */
	    @Override
		public void setCust_tel(String cust_tel) {
	        this.cust_tel = cust_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_type()
		 */
	    @Override
		public java.lang.Integer getCust_type() {
	        return cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_type(java.lang.Integer)
		 */
	    @Override
		public void setCust_type(java.lang.Integer cust_type) {
	        this.cust_type = cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCust_type_name()
		 */
	    @Override
		public java.lang.String getCust_type_name() {
	        return cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setCust_type_name(java.lang.String)
		 */
	    @Override
		public void setCust_type_name(java.lang.String cust_type_name) {
	        this.cust_type_name = cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getE_mail()
		 */
	    @Override
		public String getE_mail() {
	        return e_mail;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setE_mail(java.lang.String)
		 */
	    @Override
		public void setE_mail(String e_mail) {
	        this.e_mail = e_mail;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getFact_controller()
		 */
	    @Override
		public String getFact_controller() {
	        return fact_controller;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setFact_controller(java.lang.String)
		 */
	    @Override
		public void setFact_controller(String fact_controller) {
	        this.fact_controller = fact_controller;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getFax()
		 */
	    @Override
		public String getFax() {
	        return fax;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setFax(java.lang.String)
		 */
	    @Override
		public void setFax(String fax) {
	        this.fax = fax;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getGrade_level()
		 */
	    @Override
		public String getGrade_level() {
	        return grade_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setGrade_level(java.lang.String)
		 */
	    @Override
		public void setGrade_level(String grade_level) {
	        this.grade_level = grade_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getH_tel()
		 */
	    @Override
		public String getH_tel() {
	        return h_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setH_tel(java.lang.String)
		 */
	    @Override
		public void setH_tel(String h_tel) {
	        this.h_tel = h_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getHgtzr_bh()
		 */
	    @Override
		public String getHgtzr_bh() {
	        return hgtzr_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setHgtzr_bh(java.lang.String)
		 */
	    @Override
		public void setHgtzr_bh(String hgtzr_bh) {
	        this.hgtzr_bh = hgtzr_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getInput_man()
		 */
	    @Override
		public java.lang.Integer getInput_man() {
	        return input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setInput_man(java.lang.Integer)
		 */
	    @Override
		public void setInput_man(java.lang.Integer input_man) {
	        this.input_man = input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getInput_time()
		 */
	    @Override
		public java.sql.Timestamp getInput_time() {
	        return input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setInput_time(java.sql.Timestamp)
		 */
	    @Override
		public void setInput_time(java.sql.Timestamp input_time) {
	        this.input_time = input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getIs_link()
		 */
	    @Override
		public Integer getIs_link() {
	        return is_link;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setIs_link(java.lang.Integer)
		 */
	    @Override
		public void setIs_link(Integer is_link) {
	        this.is_link = is_link;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getJg_cust_type()
		 */
	    @Override
		public String getJg_cust_type() {
	        return jg_cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setJg_cust_type(java.lang.String)
		 */
	    @Override
		public void setJg_cust_type(String jg_cust_type) {
	        this.jg_cust_type = jg_cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLast_rg_date()
		 */
	    @Override
		public Integer getLast_rg_date() {
	        return last_rg_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setLast_rg_date(java.lang.Integer)
		 */
	    @Override
		public void setLast_rg_date(Integer last_rg_date) {
	        this.last_rg_date = last_rg_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLegal_address()
		 */
	    @Override
		public String getLegal_address() {
	        return legal_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setLegal_address(java.lang.String)
		 */
	    @Override
		public void setLegal_address(String legal_address) {
	        this.legal_address = legal_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLegal_man()
		 */
	    @Override
		public String getLegal_man() {
	        return legal_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setLegal_man(java.lang.String)
		 */
	    @Override
		public void setLegal_man(String legal_man) {
	        this.legal_man = legal_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getMobile()
		 */
	    @Override
		public String getMobile() {
	        return mobile;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setMobile(java.lang.String)
		 */
	    @Override
		public void setMobile(String mobile) {
	        this.mobile = mobile;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getModi_flag()
		 */
	    @Override
		public int getModi_flag() {
	        return modi_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setModi_flag(int)
		 */
	    @Override
		public void setModi_flag(int modi_flag) {
	        this.modi_flag = modi_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getModi_man()
		 */
	    @Override
		public Integer getModi_man() {
	        return modi_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setModi_man(java.lang.Integer)
		 */
	    @Override
		public void setModi_man(Integer modi_man) {
	        this.modi_man = modi_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getModi_time()
		 */
	    @Override
		public Integer getModi_time() {
	        return modi_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setModi_time(java.lang.Integer)
		 */
	    @Override
		public void setModi_time(Integer modi_time) {
	        this.modi_time = modi_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getMoney_source()
		 */
	    @Override
		public String getMoney_source() {
	        return money_source;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setMoney_source(java.lang.String)
		 */
	    @Override
		public void setMoney_source(String money_source) {
	        this.money_source = money_source;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getO_tel()
		 */
	    @Override
		public String getO_tel() {
	        return o_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setO_tel(java.lang.String)
		 */
	    @Override
		public void setO_tel(String o_tel) {
	        this.o_tel = o_tel;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPost_address()
		 */
	    @Override
		public String getPost_address() {
	        return post_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPost_address(java.lang.String)
		 */
	    @Override
		public void setPost_address(String post_address) {
	        this.post_address = post_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPost_address2()
		 */
	    @Override
		public String getPost_address2() {
	        return post_address2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPost_address2(java.lang.String)
		 */
	    @Override
		public void setPost_address2(String post_address2) {
	        this.post_address2 = post_address2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPost_code()
		 */
	    @Override
		public String getPost_code() {
	        return post_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPost_code(java.lang.String)
		 */
	    @Override
		public void setPost_code(String post_code) {
	        this.post_code = post_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPost_code2()
		 */
	    @Override
		public String getPost_code2() {
	        return post_code2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPost_code2(java.lang.String)
		 */
	    @Override
		public void setPost_code2(String post_code2) {
	        this.post_code2 = post_code2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPrint_deploy_bill()
		 */
	    @Override
		public Integer getPrint_deploy_bill() {
	        return print_deploy_bill;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPrint_deploy_bill(java.lang.Integer)
		 */
	    @Override
		public void setPrint_deploy_bill(Integer print_deploy_bill) {
	        this.print_deploy_bill = print_deploy_bill;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getPrint_post_info()
		 */
	    @Override
		public Integer getPrint_post_info() {
	        return print_post_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setPrint_post_info(java.lang.Integer)
		 */
	    @Override
		public void setPrint_post_info(Integer print_post_info) {
	        this.print_post_info = print_post_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getProduct_id()
		 */
	    @Override
		public Integer getProduct_id() {
	        return product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setProduct_id(java.lang.Integer)
		 */
	    @Override
		public void setProduct_id(Integer product_id) {
	        this.product_id = product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getProv_level()
		 */
	    @Override
		public String getProv_level() {
	        return prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setProv_level(java.lang.String)
		 */
	    @Override
		public void setProv_level(String prov_level) {
	        this.prov_level = prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getRg_times()
		 */
	    @Override
		public Integer getRg_times() {
	        return rg_times;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setRg_times(java.lang.Integer)
		 */
	    @Override
		public void setRg_times(Integer rg_times) {
	        this.rg_times = rg_times;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getService_man()
		 */
	    @Override
		public Integer getService_man() {
	        return service_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setService_man(java.lang.Integer)
		 */
	    @Override
		public void setService_man(Integer service_man) {
	        this.service_man = service_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getSex()
		 */
	    @Override
		public Integer getSex() {
	        return sex;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setSex(java.lang.Integer)
		 */
	    @Override
		public void setSex(Integer sex) {
	        this.sex = sex;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getSex_name()
		 */
	    @Override
		public String getSex_name() {
	        return sex_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setSex_name(java.lang.String)
		 */
	    @Override
		public void setSex_name(String sex_name) {
	        this.sex_name = sex_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getStatus()
		 */
	    @Override
		public java.lang.String getStatus() {
	        return status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setStatus(java.lang.String)
		 */
	    @Override
		public void setStatus(java.lang.String status) {
	        this.status = status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getStatus_name()
		 */
	    @Override
		public java.lang.String getStatus_name() {
	        return status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setStatus_name(java.lang.String)
		 */
	    @Override
		public void setStatus_name(java.lang.String status_name) {
	        this.status_name = status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getSummary()
		 */
	    @Override
		public String getSummary() {
	        return summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setSummary(java.lang.String)
		 */
	    @Override
		public void setSummary(String summary) {
	        this.summary = summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getTotal_money()
		 */
	    @Override
		public java.math.BigDecimal getTotal_money() {
	        return total_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setTotal_money(java.math.BigDecimal)
		 */
	    @Override
		public void setTotal_money(java.math.BigDecimal total_money) {
	        this.total_money = total_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getTouch_type()
		 */
	    @Override
		public java.lang.String getTouch_type() {
	        return touch_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setTouch_type(java.lang.String)
		 */
	    @Override
		public void setTouch_type(java.lang.String touch_type) {
	        this.touch_type = touch_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getTouch_type_name()
		 */
	    @Override
		public java.lang.String getTouch_type_name() {
	        return touch_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setTouch_type_name(java.lang.String)
		 */
	    @Override
		public void setTouch_type_name(java.lang.String touch_type_name) {
	        this.touch_type_name = touch_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getVip_card_id()
		 */
	    @Override
		public String getVip_card_id() {
	        return vip_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setVip_card_id(java.lang.String)
		 */
	    @Override
		public void setVip_card_id(String vip_card_id) {
	        this.vip_card_id = vip_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getVip_date()
		 */
	    @Override
		public Integer getVip_date() {
	        return vip_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setVip_date(java.lang.Integer)
		 */
	    @Override
		public void setVip_date(Integer vip_date) {
	        this.vip_date = vip_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getVoc_type()
		 */
	    @Override
		public String getVoc_type() {
	        return voc_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setVoc_type(java.lang.String)
		 */
	    @Override
		public void setVoc_type(String voc_type) {
	        this.voc_type = voc_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getVoc_type_name()
		 */
	    @Override
		public String getVoc_type_name() {
	        return voc_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setVoc_type_name(java.lang.String)
		 */
	    @Override
		public void setVoc_type_name(String voc_type_name) {
	        this.voc_type_name = voc_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getWt_flag()
		 */
	    @Override
		public java.lang.Integer getWt_flag() {
	        return wt_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setWt_flag(java.lang.Integer)
		 */
	    @Override
		public void setWt_flag(java.lang.Integer wt_flag) {
	        this.wt_flag = wt_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getWt_flag_name()
		 */
	    @Override
		public java.lang.String getWt_flag_name() {
	        return wt_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setWt_flag_name(java.lang.String)
		 */
	    @Override
		public void setWt_flag_name(java.lang.String wt_flag_name) {
	        this.wt_flag_name = wt_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBack_amount()
		 */
	    @Override
		public java.math.BigDecimal getBack_amount() {
	        return back_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getCurr_to_amount()
		 */
	    @Override
		public java.math.BigDecimal getCurr_to_amount() {
	        return curr_to_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getExchange_amount()
		 */
	    @Override
		public java.math.BigDecimal getExchange_amount() {
	        return exchange_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLast_product_name()
		 */
	    @Override
		public String getLast_product_name() {
	        return last_product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setTo_cust_id(java.lang.Integer)
		 */
	    @Override
		public void setTo_cust_id(Integer to_cust_id) {
	        this.to_cust_id = to_cust_id;
	    }
	    

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLink_gd_money()
		 */
		@Override
		public BigDecimal getLink_gd_money() {
			return link_gd_money;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setLink_gd_money(java.math.BigDecimal)
		 */
		@Override
		public void setLink_gd_money(BigDecimal link_gd_money) {
			this.link_gd_money = link_gd_money;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getLink_type()
		 */
		@Override
		public Integer getLink_type() {
			return link_type;
		}
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setLink_type(java.lang.Integer)
		 */
		@Override
		public void setLink_type(Integer link_type) {
			this.link_type = link_type;
		}
		
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getGrade_level_name()
		 */
		@Override
		public String getGrade_level_name() {
			return grade_level_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setGrade_level_name(java.lang.String)
		 */
		@Override
		public void setGrade_level_name(String grade_level_name) {
			this.grade_level_name = grade_level_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getComplete_flag()
		 */
		@Override
		public Integer getComplete_flag() {
			return complete_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setComplete_flag(java.lang.Integer)
		 */
		@Override
		public void setComplete_flag(Integer complete_flag) {
			this.complete_flag = complete_flag;
		}
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBen_date()
		 */
		@Override
		public Integer getBen_date() {
			return ben_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBen_date(java.lang.Integer)
		 */
		@Override
		public void setBen_date(Integer ben_date) {
			this.ben_date = ben_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getBen_end_date()
		 */
		@Override
		public Integer getBen_end_date() {
			return ben_end_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setBen_end_date(java.lang.Integer)
		 */
		@Override
		public void setBen_end_date(Integer ben_end_date) {
			this.ben_end_date = ben_end_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getFlag()
		 */
		@Override
		public Integer getFlag() {
			return flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setFlag(java.lang.Integer)
		 */
		@Override
		public void setFlag(Integer flag) {
			this.flag = flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getProduct_code()
		 */
		@Override
		public Integer getProduct_code() {
			return product_code;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setProduct_code(java.lang.Integer)
		 */
		@Override
		public void setProduct_code(Integer product_code) {
			this.product_code = product_code;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getProduct_name()
		 */
		@Override
		public String getProduct_name() {
			return product_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setProduct_name(java.lang.String)
		 */
		@Override
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#getRg_money()
		 */
		@Override
		public BigDecimal getRg_money() {
			return rg_money;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCustomerInfoLocal#setRg_money(java.math.BigDecimal)
		 */
		@Override
		public void setRg_money(BigDecimal rg_money) {
			this.rg_money = rg_money;
		}
	}