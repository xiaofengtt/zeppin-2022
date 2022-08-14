package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.tools.Utility;

@Component(value="intrustBenifitor")
@Scope("prototype")
public class IntrustBenifitorBean extends IntrustBusiBean implements IntrustBenifitorLocal {

	 private String acct_chg_reason;

	    private String acct_user_name;

	    private java.math.BigDecimal back_amount;

	    private java.lang.String bank_acct;

	    //////YZJ////2008-08-21
	    private String bank_acct_type;//银行账号类型

	    private java.lang.String bank_id;

	    private java.lang.String bank_name;

	    private java.lang.String bank_sub_name;

	    private java.math.BigDecimal ben_amount;

	    private java.math.BigDecimal ben_amount2;

	    private String ben_card_no;

	    private Integer ben_count; //合同数 2006-12-25 caiyuan

	    private java.lang.Integer ben_date;

	    ////////////yingxj20050224
	    private java.lang.Integer ben_end_date;

	    private Integer ben_lost_date;

	    private Integer ben_lost_flag;

	    private BigDecimal ben_money;

	    private Integer ben_serial_no;

	    private String ben_status;

	    private String ben_status_name;

	    private Integer ben_unfrozen_date;

	    private java.lang.Integer book_code;

	    private Integer card_lost_date;

	    private Integer card_lost_flag;

	    private java.lang.Integer check_flag;

	    private java.lang.Integer check_man;

	    private java.lang.String contract_bh;

	    private java.lang.String contract_sub_bh;

	    ///////////////////////

	    //添加两个属性 2005-7-29 陶然 begin
	    private String cust_acct_name;

	    private java.lang.Integer cust_id;

	    private String cust_name;

	    private String cust_no;

	    /**
	     * add by tsg 2008-03-10 客户类型
	     */
	    private Integer cust_type;

	    private java.lang.Integer end_date;

	    //end
	    private java.math.BigDecimal exchange_amount;

	    private Integer firbid_flag;

	    private Integer flag;

	    private BigDecimal frozen_money; //冻结金额

	    private java.math.BigDecimal frozen_tmp;

	    private java.lang.Integer function_id;

	    private String ht_status_name;

	    private java.lang.Integer input_man;

	    private java.sql.Timestamp input_time;

	    private Integer is_transferential;

	    private java.lang.String jk_type;

	    private java.lang.String jk_type_name;

	    private java.lang.Integer list_id;

	    private Integer modi_bank_date;

	    private Integer modi_check_man;

	    private java.sql.Timestamp modi_check_time;

	    private Integer modi_man;

	    private java.sql.Timestamp modi_time;

	    private String new_acct_name;

	    private String new_bank_acct;

	    private String new_bank_id;

	    private String new_bank_sub_name;

	    private String new_status;

	    private String old_acct_name;

	    private String old_bank_acct;

	    private String old_bank_id;

	    private String old_bank_sub_name;

	    private String old_status;

	    private Integer period_unit;

	    ////////yingxj20041125
	    private String product_code;

	    private String product_exp_rate;

	    private java.lang.Integer product_id;

	    private String product_name;

	    private Integer product_period;

	    private String product_qx;

	    private java.lang.String prov_level;

	    private java.lang.String prov_level_name;

	    /////////////谭鸿20050426添加end

	    //////////////////200608016

	    private Integer qs_date;

	    ////////////

	    /////////////谭鸿20050426添加en
	    private java.math.BigDecimal remain_amount;

	    private java.math.BigDecimal rg_money;

	    private java.lang.Integer serial_no;

	    private String st_chg_reason;

	    private java.lang.Integer start_date;

	    private java.lang.String summary;

	    private String sy_address;

	    private String sy_card_id;

	    private String sy_card_type_name;

	    private String sy_cust_name;

	    private String sy_cust_no;

	    private String temp_acct_name;

	    private java.lang.String temp_bank_acct;

	    private java.lang.String temp_bank_id;

	    private java.lang.String temp_bank_sub_name;

	    private java.lang.String temp_status;

	    private java.math.BigDecimal to_amount;

	    ///////////杨帆20050913添加end
	    private String trans_type;//转让类别 捐赠，转让，继承

	    private Integer transfer_flag;

	    private Integer valid_period;

	    private String wt_address;

	    private String wt_card_id;

	    private String wt_card_type_name;

	    private String wt_cust_name;

	    private Integer bonus_flag;//收益分配方式

	    private Integer temp_bonus_flag;

	    //20100309 lym
	    private java.lang.Integer fee_tpye;

	    private String fee_tpye_name;

	    private java.math.BigDecimal fee_money;

	    private java.lang.Integer fee_date;
	    
	    private BigDecimal to_money;
	    
	    private String cust_tel;
	    
	    private String mobile;

	    private Integer modi_acct_check_flag;
	    
	    private java.math.BigDecimal bonus_rate;//转份额比例 add by liugang 20110216
	    private java.math.BigDecimal temp_bonus_rate;
	    
	    private Integer prov_flag;//1：优先，2：一般，3：劣后
	    
	    private String prov_level_name_temp;
	    
	    //====2011-4-19 qmh_add=============
	    private java.lang.Integer sub_product_id;
	    //客户名称@，起始日期@，结束日期@，期初份额，期初金额，增加份额，增加金额，减少份额，减少金额，期末份额，期末金额
	    private java.math.BigDecimal qc_fe;//期初份额
	    
	    private java.math.BigDecimal qc_je;//期初金额
	    
	    private java.math.BigDecimal add_je;//增加金额
	    
	    private java.math.BigDecimal  dec_je;//减少金额
	    
	    private java.math.BigDecimal add_fe;//增加份额
	    
	    private java.math.BigDecimal dec_fe ;//减少份额
	    
	    private java.math.BigDecimal qm_je;//期末金额
	    
	    private java.math.BigDecimal qm_fe;//期末份额
	    
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProv_level_name_temp(java.lang.String)
		 */
		@Override
		public void setProv_level_name_temp(String prov_level_name_temp) {
			this.prov_level_name_temp = prov_level_name_temp;
		}
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#append()
		 */
	    @Override
		public void append() throws Exception {
	        Object[] params = new Object[18];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = cust_id;
	        params[4] = ben_amount;
	        params[5] = prov_level;
	        params[6] = jk_type;
	        params[7] = bank_id;
	        params[8] = bank_acct;
	        params[9] = input_man;
	        params[10] = bank_sub_name;
	        params[11] = valid_period;
	        params[12] = ben_date;
	        params[13] = acct_user_name;
	        params[14] = this.ben_amount;
	        params[15] = bank_acct_type;
	        params[16] = bonus_flag;
	        params[17] = bonus_rate;
	        try {
	            serial_no = (Integer) super
	                    .cudEx(
	                            "{?=call SP_ADD_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params, 20, Types.INTEGER);
	        } catch (Exception e) {
	            throw new BusiException("受益信息添加失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#check1()
		 */
	    @Override
		public void check1() throws Exception {
	        Object[] params = new Object[3];
	        params[0] = serial_no;
	        params[1] = check_flag;
	        params[2] = input_man;
	        try {
	            super.update("{?=call SP_CHECK_TBENIFITOR_ACCT(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("受益人账号修改审核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#checkFrozenBenRight()
		 */
	    @Override
		public void checkFrozenBenRight() throws Exception {

	        Object[] params = new Object[6];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(list_id, new Integer(0));
	        params[4] = Utility.parseInt(check_flag, new Integer(0));
	        params[5] = input_man;

	        try {
	            super.update(
	                    "{?=call SP_CHECK_TBENIFITOR_FROZEN_RIGHT (?,?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException(e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#delete()
		 */
	    @Override
		public void delete() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;
	        try {
	            super.delete("{?=call SP_DEL_TBENIFITOR (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("受益信息删除失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#frozenBenRight(int)
		 */
	    @Override
		public void frozenBenRight(int flag) throws Exception {
	        String sqlStrtem = "{?=call SP_LOST_TBENIFITOR (?,?,?)}";
	        if (flag == 2)
	            sqlStrtem = "{?=call SP_UNLOST_TBENIFITOR (?,?,?)}";
	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = summary;
	        params[2] = input_man;
	        try {
	            super.update(sqlStrtem, params);
	        } catch (Exception e) {
	            throw new BusiException(e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listbySqlSYRAll()
		 */
	    @Override
		public void listbySqlSYRAll() throws Exception {
	        Object[] params = new Object[12];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = Utility.trimNull(contract_bh);
	        params[4] = Utility.trimNull(contract_sub_bh);
	        params[5] = Utility.trimNull(sy_cust_no);
	        params[6] = Utility.trimNull(sy_card_id);
	        params[7] = Utility.trimNull(sy_cust_name);
	        params[8] = Utility.parseInt(cust_type, new Integer(0));
	        params[9] = Utility.trimNull(prov_level);
	        params[10] = Utility.trimNull(ben_status);
	        params[11] = Utility.parseInt(input_man, new Integer(0));
	        super.query(
	                "{call SP_QUERY_TBENIFITOR_QUERY(?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextSYRMessage()
		 */
	    @Override
		public boolean getNextSYRMessage() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            this.serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            this.contract_bh = rowset.getString("CONTRACT_BH");
	            this.contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            this.cust_name = rowset.getString("CUST_NAME");
	            this.to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            this.ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            this.ben_date = new Integer(rowset.getInt("BEN_DATE"));
	            this.frozen_money = rowset.getBigDecimal("FROZEN_MONEY");
	            this.ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            this.product_name = rowset.getString("PRODUCT_NAME");
	            this.list_id = new Integer(rowset.getInt("LIST_ID"));
	            this.cust_id = new Integer(rowset.getInt("CUST_ID"));
	            this.to_money = rowset.getBigDecimal("TO_MONEY");
	        }
	        return b;
	    }
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextSYRMessageList()
		 */
		@Override
		public List getNextSYRMessageList()throws Exception{
				List list = new ArrayList();
				while(super.getNext()){
					Map map = new HashMap();
					map.put("PRODUCT_ID", rowset.getString("PRODUCT_ID"));
					map.put("SUB_PRODUCT_ID", rowset.getString("SUB_PRODUCT_ID"));//2011-4-19 add
					//map.put("BOOK_CODE",rowset.getString("PRODUCT_ID"));//2011-4-19 add
					map.put("SERIAL_NO", rowset.getString("SERIAL_NO"));
					map.put("CONTRACT_BH", rowset.getString("CONTRACT_BH"));
					map.put("CONTRACT_SUB_BH", rowset.getString("CONTRACT_SUB_BH"));
					map.put("CUST_NAME", rowset.getString("CUST_NAME"));
					map.put("TO_MONEY", rowset.getString("TO_MONEY"));
					map.put("TO_AMOUNT", rowset.getString("TO_AMOUNT"));
					map.put("BEN_AMOUNT", rowset.getString("BEN_AMOUNT"));
					map.put("BEN_DATE", rowset.getString("BEN_DATE"));
					map.put("FROZEN_MONEY", rowset.getString("FROZEN_MONEY"));
					map.put("BEN_STATUS_NAME", rowset.getString("BEN_STATUS_NAME"));
					map.put("PRODUCT_NAME", rowset.getString("PRODUCT_NAME"));
					map.put("LIST_ID", rowset.getString("LIST_ID"));
					map.put("CUST_ID", rowset.getString("CUST_ID"));
					list.add(map);
				}
				return list;
			}
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextSYRMessageList1()
		 */
		@Override
		public List getNextSYRMessageList1()throws Exception{
			List list = new ArrayList();
			while(super.getNext()){
				Map map = new HashMap();
				map.put("PRODUCT_ID", rowset.getString("PRODUCT_ID"));
				map.put("SERIAL_NO", rowset.getString("SERIAL_NO"));
				map.put("CONTRACT_BH", rowset.getString("CONTRACT_BH"));
				map.put("CONTRACT_SUB_BH", rowset.getString("CONTRACT_SUB_BH"));
				map.put("CUST_NAME", rowset.getString("CUST_NAME"));
				map.put("TO_MONEY", rowset.getString("RG_MONEY"));
				map.put("TO_AMOUNT", rowset.getString("TO_AMOUNT"));
				map.put("BEN_AMOUNT", rowset.getString("BEN_AMOUNT"));
				map.put("BEN_MONEY", rowset.getString("BEN_MONEY"));
				map.put("BEN_DATE", rowset.getString("BEN_DATE"));
				map.put("FROZEN_MONEY", rowset.getString("FROZEN_MONEY"));
				map.put("BEN_STATUS_NAME", rowset.getString("BEN_STATUS_NAME"));
				map.put("PRODUCT_NAME", rowset.getString("PRODUCT_NAME"));
				map.put("LIST_ID", rowset.getString("LIST_ID"));
				//map.put("CUST_ID", rowset.getString("CUST_ID"));
				list.add(map);
			}
			return list;
		}

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#transForNto1()
		 */
	    @Override
		public String transForNto1() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(product_id, new Integer(0));
	        params[1] = contract_bh;
	        super.query("{call SP_QUERY_TBENIFITOR_PLZR (?,?)}", params);

	        StringBuffer strSerial_no = new StringBuffer();
	        while (getNextNew3()) {
	            strSerial_no.append(this.serial_no);
	            strSerial_no.append("$");
	        }
	        rowset.beforeFirst();//游标移回
	        //Utility.debugln("all="+strSerial_no.toString());
	        return strSerial_no.toString();
	    }

	    protected void validate() throws BusiException {
	        super.validate();
	        if (book_code.intValue() <= 0)
	            throw new BusiException("帐套代码不合法.");
	        /*
	         * if (prov_flag.intValue() <= 0) throw new
	         * BusiException("优先受益权标志不合法.");
	         */
	        if (cust_id.intValue() <= 0)
	            throw new BusiException("客户ID号不合法.");

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#list()
		 */
	    @Override
		public void list() throws Exception {
	        query("");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listBenifitorbyht()
		 */
	    @Override
		public void listBenifitorbyht() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(product_id, new Integer(1));
	        params[1] = contract_bh;
	        super.query("{call SP_QUERY_TBENIFITOR_BYHT(?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listBenifitors()
		 */
	    @Override
		public void listBenifitors() throws Exception {
	        Object[] params = new Object[9];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = new Integer(0);
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = contract_bh;
	        params[4] = Utility.parseInt(cust_id, new Integer(0));
	        params[5] = input_man;
	        params[6] = cust_name;
	        params[7] = prov_level;
	        params[8] = cust_no;
	        super.query("{call SP_QUERY_TBENIFITOR(?,?,?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listByCustId(java.lang.Integer)
		 */
	    @Override
		public void listByCustId(Integer cust_id) throws Exception {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(cust_id, new Integer(0));
	        params[1] = input_man;
	        super.query("{call SP_QUERY_TCONTRACT_CUST_ID (?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listChangeDetail(java.lang.Integer, java.lang.Integer, java.lang.Integer)
		 */
	    @Override
		public void listChangeDetail(Integer book_code, Integer cust_id,
	            Integer product_id) throws Exception {
	        Object[] params = new Object[3];
	        params[0] = book_code;
	        params[1] = cust_id;
	        params[2] = product_id;
	        super.query("{call SP_QUERY_TCUSTBENCHANGES (?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listDetail(int)
		 */
	    @Override
		public void listDetail(int in_flag) throws Exception {
	        Object[] params = new Object[14];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(list_id, new Integer(0));
	        params[4] = cust_no;
	        params[5] = cust_name;
	        params[6] = prov_level;
	        params[7] = input_man;
	        params[8] = Utility.parseInt(cust_id, new Integer(0));
	        params[9] = sy_cust_no;
	        params[10] = ben_status;
	        params[11] = new Integer(in_flag);
	        params[12] = contract_sub_bh;
	        params[13] = cust_type;
	        super
	                .query(
	                        "{call SP_QUERY_TBENIFITOR_DETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listLostDetail(int)
		 */
	    @Override
		public void listLostDetail(int lostflag) throws Exception {
	        Object[] params = new Object[13];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(list_id, new Integer(0));
	        params[4] = ben_card_no;
	        params[5] = cust_name;
	        params[6] = prov_level;
	        params[7] = input_man;
	        params[8] = Utility.parseInt(cust_id, new Integer(0));
	        params[9] = new Integer(lostflag);
	        params[10] = cust_no;
	        params[11] = Utility.parseInt(ben_lost_flag, new Integer(0));
	        params[12] = contract_sub_bh;
	        
	        super
	                .query(
	                        "{call SP_QUERY_TBENIFITOR_LOST_DETAIL (?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#listStatus()
		 */
	    @Override
		public void listStatus() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        super.query("{call SP_QUERY_TBENIFITOR_STATUS (?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#load()
		 */

	    @Override
		public void load() throws Exception {
	        Object[] params = new Object[12];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = serial_no;
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = contract_bh;
	        params[4] = Utility.parseInt(cust_id, new Integer(0));
	        params[5] = input_man;
	        params[6] = "";
	        params[7] = "";
	        params[8] = "";
	        params[9] = new Integer(0);
	        params[10] = "";
	        params[11] = contract_sub_bh;
	        super.query("{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	        this.flag = new Integer(1);
	        getNext();
	    }
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#loadModiBenAcctDetial()
		 */

	    @Override
		public void loadModiBenAcctDetial() throws Exception {
	        Object[] params = new Object[1];
	        params[0] = ben_serial_no;
	        super.query("{call SP_QUERY_TMODIBENACCTDETAIL_BYID (?)}", params);
	    }
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextModiBenDetailList()
		 */
		@Override
		public List getNextModiBenDetailList() throws Exception {
			List list = new ArrayList();
			while(super.getNext()) {
				Map map = new HashMap();
				map.put("SERIAL_NO", rowset.getObject("SERIAL_NO"));
				map.put("BEN_SERIAL_NO", rowset.getObject("BEN_SERIAL_NO"));
				map.put("OLD_BANK_ID", rowset.getObject("OLD_BANK_ID"));
				map.put("OLD_BANK_SUB_NAME", rowset.getObject("OLD_BANK_SUB_NAME"));
				map.put("OLD_BANK_ACCT", rowset.getObject("OLD_BANK_ACCT"));
				map.put("OLD_ACCT_NAME", rowset.getObject("OLD_ACCT_NAME"));
				map.put("NEW_ACCT_NAME", rowset.getObject("NEW_ACCT_NAME"));
				map.put("NEW_BANK_ID", rowset.getObject("NEW_BANK_ID"));
				map.put("NEW_BANK_SUB_NAME", rowset.getObject("NEW_BANK_SUB_NAME"));
				map.put("NEW_BANK_ACCT", rowset.getObject("NEW_BANK_ACCT"));
				map.put("MODI_MAN", rowset.getObject("MODI_MAN"));
				map.put("MODI_CHECK_MAN", rowset.getObject("MODI_CHECK_MAN"));
				map.put("MODI_TIME", rowset.getObject("MODI_TIME"));
				map.put("MODI_CHECK_TIME", rowset.getObject("MODI_CHECK_TIME"));
				map.put("MODI_BANK_DATE", rowset.getObject("MODI_BANK_DATE"));
				list.add(map);
			}
			return list;
		}

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#lostBenInfo(int)
		 */
	    @Override
		public void lostBenInfo(int flag) throws Exception {
	        Object[] params = new Object[11];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(list_id, new Integer(0));
	        params[4] = ben_status;
	        params[5] = input_man;
	        params[6] = ben_lost_flag;
	        params[7] = ben_lost_date;
	        params[8] = new Integer(flag);
	        params[9] = input_man;
	        params[10] = st_chg_reason;
	        try {
	            super
	                    .update(
	                            "{?=call SP_TBENIFITOR_LOST_RIGHT (?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException(e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#modi_prov_level()
		 */

	    @Override
		public void modi_prov_level() throws Exception {
	        Object[] params = new Object[3];
	        params[0] = serial_no;
	        params[1] = prov_level;
	        params[2] = input_man;
	        try {
	            super.append("{?=call SP_MODI_TBENIFITOR_LEVEL(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("受益级别修改失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#query()
		 */
	    @Override
		public void query() throws Exception {
	        Object[] params = new Object[12];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = contract_bh;
	        params[4] = cust_id;
	        params[5] = input_man;
	        params[6] = cust_name;
	        params[7] = prov_level;
	        params[8] = cust_no;
	        params[9] = Utility.parseInt(list_id, new Integer(0));
	        params[10] = ben_status;
	        params[11] = contract_sub_bh;
	        super.query("{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#query(java.lang.String)
		 */
	    @Override
		public void query(String sQuery) throws Exception {
	        int custid = Utility.parseInt(sQuery, 0);
	        Object[] params = new Object[12];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = new Integer(0);
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = contract_bh;
	        params[4] = new Integer(custid);
	        params[5] = input_man;
	        params[6] = cust_name;
	        params[7] = prov_level;
	        params[8] = cust_no;
	        params[9] = Utility.parseInt(list_id, new Integer(0));
	        params[10] = ben_status;
	        params[11] = contract_sub_bh;
	        super.query("{call SP_QUERY_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#query_syfymx()
		 */
	    @Override
		public void query_syfymx() throws Exception {
	        Object[] params = new Object[6];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(cust_id, new Integer(0));
	        params[4] = Utility.parseInt(list_id, new Integer(0));
	        params[5] = input_man;
	        super
	                .query("{call SP_QUERY_BENIFITOR_FEEDETAIL (?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQ_S()
		 */
	    @Override
		public boolean getQ_S() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            cust_name = rowset.getString("CUST_NAME");
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            fee_tpye = new Integer(rowset.getInt("FEE_TYPE"));
	            fee_tpye_name = rowset.getString("FEE_TYPE_NAME");
	            fee_money = rowset.getBigDecimal("FEE_MONEY");
	            fee_date = (Integer) rowset.getObject("FEE_DATE");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#QueryBenifitor()
		 */
	    @Override
		public void QueryBenifitor() throws Exception {
	        Object[] params = new Object[3];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = Utility.parseInt(cust_id, new Integer(0));
	        super.query("{call SP_QUERY_TBENIFITOR_CUST_ID (?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#queryModi()
		 */
	    @Override
		public void queryModi() throws Exception {
	        Object[] params = new Object[10];
	        params[0] = function_id;
	        params[1] = book_code;
	        params[2] = serial_no;
	        params[3] = contract_bh;
	        params[4] = cust_no;
	        params[5] = Utility.parseInt(product_id, new Integer(0));
	        params[6] = product_name;
	        params[7] = input_man;
	        params[8] = cust_name;
	        params[9] = contract_sub_bh;
	        super.query(
	                "{call SP_QUERY_TBENIFITOR_MODIUNCHECK (?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#queryModiAcctDetail()
		 */
	    @Override
		public void queryModiAcctDetail() throws Exception {
	        Object[] params = new Object[11];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = cust_name;
	        params[4] = old_bank_id;
	        params[5] = old_bank_acct;
	        params[6] = new_bank_id;
	        params[7] = new_bank_acct;
	        params[8] = input_man;
	        params[9] = cust_no;
	        params[10] = contract_sub_bh;
	        super.query(
	                "{call SP_QUERY_TMODIBENACCTDETAIL(?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#queryModiStatusDetail()
		 */
	    @Override
		public void queryModiStatusDetail() throws Exception {
	        Object[] params = new Object[6];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = cust_name;
	        params[4] = input_man;
	        params[5] = cust_no;
	        super
	                .query("{call SP_QUERY_TMODIBENSTATUSDETAIL(?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#save()
		 */
	    @Override
		public void save() throws Exception {
	        Object[] params = new Object[15];
	        params[0] = serial_no;
	        params[1] = ben_amount;
	        params[2] = prov_level;
	        params[3] = bank_id;
	        params[4] = bank_acct;
	        params[5] = jk_type;
	        params[6] = input_man;
	        params[7] = bank_sub_name;
	        params[8] = valid_period;
	        params[9] = ben_date;
	        params[10] = acct_user_name;
	        params[11] = ben_money;
	        params[12] = bank_acct_type;
	        params[13] = bonus_flag;
	        params[14] = bonus_rate;
	        try {
	            super
	                    .append(
	                            "{?=call SP_MODI_TBENIFITOR (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("受益信息保存失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#save1()
		 */
	    @Override
		public void save1() throws Exception {
	        Object[] params = new Object[11];
	        params[0] = serial_no;
	        params[1] = bank_id;
	        params[2] = bank_sub_name;
	        params[3] = bank_acct;
	        params[4] = input_man;
	        params[5] = cust_acct_name;
	        params[6] = Utility.parseInt(modi_bank_date, Utility.getCurrentDate());
	        params[7] = acct_chg_reason;
	        params[8] = bank_acct_type;
	        params[9] = bonus_flag;
	        params[10] = bonus_rate;
	        try {
	            super.update(
	                    "{?=call SP_MODI_TBENIFITOR_BANK (?,?,?,?,?,?,?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("受益人账号修改失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNext()
		 */
	    @Override
		public boolean getNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            contract_bh = rowset.getString("CONTRACT_BH");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_money = rowset.getBigDecimal("BEN_MONEY");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            ben_end_date = (Integer) rowset.getObject("BEN_END_DATE");
	            prov_level = rowset.getString("PROV_LEVEL");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            summary = rowset.getString("SUMMARY");
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            ben_status = rowset.getString("BEN_STATUS");
	            product_name = rowset.getString("PRODUCT_NAME");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            remain_amount = rowset.getBigDecimal("TO_AMOUNT");
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            transfer_flag = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            temp_bank_id = rowset.getString("TEMP_BANK_ID");
	            temp_bank_sub_name = rowset.getString("TEMP_BANK_SUB_NAME");
	            temp_bank_acct = rowset.getString("TEMP_BANK_ACCT");
	            temp_acct_name = rowset.getString("TEMP_ACCT_NAME");
	            cust_acct_name = rowset.getString("CUST_ACCT_NAME");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            modi_bank_date = (Integer) rowset.getObject("MODI_BANK_DATE");
	            acct_chg_reason = rowset.getString("ACCT_CHG_REASON");
	            bank_acct_type = rowset.getString("BANK_ACCT_TYPE");
	            this.sy_card_id = rowset.getString("CARD_ID");
	            //update by guiFeng 09.02.17
	            if (flag != null && flag.intValue() != 1) {
	                product_code = rowset.getString("PRODUCT_CODE"); //转让打印需要
	            }
	            bonus_flag = (Integer) rowset.getObject("BONUS_FLAG");
	            temp_bonus_flag = (Integer) rowset.getObject("TEMP_BONUS_FLAG");
	            modi_acct_check_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("MODI_ACCT_CHECK_FLAG")),new Integer(0));
	            cust_tel = rowset.getString("CUST_TEL");
	            mobile = rowset.getString("MOBILE");
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            valid_period = Utility.parseInt(Utility.trimNull(rowset.getObject("VALID_PERIOD")),new Integer(0));
	            period_unit = Utility.parseInt(Utility.trimNull(rowset.getObject("PERIOD_UNIT")),new Integer(0));
	            bonus_rate = rowset.getBigDecimal("BONUS_RATE");
	            temp_bonus_rate = rowset.getBigDecimal("TEMP_BONUS_RATE");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNext_ChangeDetail()
		 */
	    @Override
		public boolean getNext_ChangeDetail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            exchange_amount = rowset.getBigDecimal("CHANGE_AMOUNT");//变更份额;
	            ben_amount = rowset.getBigDecimal("CHANGE_MONEY");//变更金额;

	            back_amount = rowset.getBigDecimal("NAV_PRICE");//净值;
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");//合同编号(合同前缀+数字)
	            qs_date = new Integer(rowset.getInt("CHANGE_DATE"));//合同签署日期
	            ben_status_name = rowset.getString("CHANGE_TYPE_NAME");
	            prov_level = rowset.getString("SY_TYPE");//赎回类别
	            prov_level_name = rowset.getString("SY_TYPE_NAME");//赎回类别名称
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNext_cust()
		 */
	    @Override
		public boolean getNext_cust() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            rg_money = rowset.getBigDecimal("RG_MONEY");//合同认购金额;
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");//合同编号(合同前缀+数字)
	            qs_date = new Integer(rowset.getInt("QS_DATE"));//合同签署日期
	            end_date = new Integer(rowset.getInt("END_DATE")); //合同到期日期
	            ht_status_name = rowset.getString("HT_STATUS_NAME");//合同状态说明
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNext2()
		 */
	    @Override
		public boolean getNext2() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            contract_bh = rowset.getString("CONTRACT_BH");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            ben_end_date = (Integer) rowset.getObject("BEN_END_DATE");
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            cust_acct_name = rowset.getString("CUST_ACCT_NAME");
	            ben_status = rowset.getString("BEN_STATUS");
	            product_name = rowset.getString("PRODUCT_NAME");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            exchange_amount = rowset.getBigDecimal("EXCHANGE_AMOUNT");
	            back_amount = rowset.getBigDecimal("BACK_AMOUNT");
	            prov_level = rowset.getString("PROV_LEVEL");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            summary = rowset.getString("SUMMARY");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            period_unit = new Integer(rowset.getInt("PERIOD_UNIT"));
	            transfer_flag = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNext3()
		 */
	    @Override
		public boolean getNext3() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            contract_bh = rowset.getString("CONTRACT_BH");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            frozen_tmp = rowset.getBigDecimal("FROZEN_TMP");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            ben_end_date = (Integer) rowset.getObject("BEN_END_DATE");
	            prov_level = rowset.getString("PROV_LEVEL");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            summary = rowset.getString("SUMMARY");
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            ben_status = rowset.getString("BEN_STATUS");
	            product_name = rowset.getString("PRODUCT_NAME");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            remain_amount = rowset.getBigDecimal("TO_AMOUNT");
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            transfer_flag = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextbyht()
		 */
	    @Override
		public boolean getNextbyht() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            contract_bh = rowset.getString("CONTRACT_BH");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            remain_amount = rowset.getBigDecimal("TO_AMOUNT");
	            cust_name = rowset.getString("CUST_NAME");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextDetail()
		 */
	    @Override
		public boolean getNextDetail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            wt_cust_name = rowset.getString("WT_CUST_NAME");
	            wt_card_type_name = rowset.getString("WT_CARD_TYPE_NAME");
	            wt_card_id = rowset.getString("WT_CARD_ID");
	            wt_address = rowset.getString("WT_ADDRESS");
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            cust_name = rowset.getString("SY_CUST_NAME");
	            sy_card_type_name = rowset.getString("SY_CARD_TYPE_NAME");
	            sy_card_id = rowset.getString("SY_CARD_ID");
	            sy_address = rowset.getString("SY_ADDRESS");
	            ben_amount = rowset.getBigDecimal("SY_MONEY");
	            ben_date = (Integer) rowset.getObject("SY_DATE");
	            bank_name = rowset.getString("SY_BANK_NAME");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            bank_acct = rowset.getString("SY_BANK_ACCT");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            cust_id = new Integer(rowset.getInt("SY_CUST_ID"));
	            ben_status = rowset.getString("BEN_STATUS");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            product_qx = rowset.getString("PRODUCT_QX");
	            product_period = new Integer(rowset.getInt("PRODUCT_PERIOD"));
	            product_exp_rate = rowset.getString("PRODUCT_EXP_RATE");
	            is_transferential = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));
	            ben_unfrozen_date = new Integer(rowset.getInt("BEN_UNFROZEN_DATE"));
	            frozen_tmp = rowset.getBigDecimal("FROZEN_TMP");
	            frozen_money = rowset.getBigDecimal("FROZEN_MONEY");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            cust_tel = rowset.getString("CUST_TEL");
	            mobile = rowset.getString("MOBILE");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextDetail1()
		 */
	    @Override
		public boolean getNextDetail1() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            wt_cust_name = rowset.getString("WT_CUST_NAME");
	            wt_card_type_name = rowset.getString("WT_CARD_TYPE_NAME");
	            wt_card_id = rowset.getString("WT_CARD_ID");
	            wt_address = rowset.getString("WT_ADDRESS");
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            cust_name = rowset.getString("SY_CUST_NAME");
	            sy_cust_no = rowset.getString("SY_CUST_NO");
	            sy_card_type_name = rowset.getString("SY_CARD_TYPE_NAME");
	            sy_card_id = rowset.getString("SY_CARD_ID");
	            sy_address = rowset.getString("SY_ADDRESS");
	            ben_amount = rowset.getBigDecimal("SY_MONEY");
	            ben_money = rowset.getBigDecimal("BEN_MONEY");
	            ben_date = (Integer) rowset.getObject("SY_DATE");
	            bank_name = rowset.getString("SY_BANK_NAME");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            bank_acct = rowset.getString("SY_BANK_ACCT");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            cust_id = new Integer(rowset.getInt("SY_CUST_ID"));
	            ben_status = rowset.getString("BEN_STATUS");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            product_qx = rowset.getString("PRODUCT_QX");
	            product_period = new Integer(rowset.getInt("PRODUCT_PERIOD"));
	            product_exp_rate = rowset.getString("PRODUCT_EXP_RATE");
	            is_transferential = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));
	            to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            frozen_money = rowset.getBigDecimal("FROZEN_MONEY");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextLost()
		 */
	    @Override
		public boolean getNextLost() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            sy_card_type_name = rowset.getString("CARD_TYPE_NAME");
	            sy_card_id = rowset.getString("CARD_ID");
	            sy_address = rowset.getString("POST_ADDRESS");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_money = rowset.getBigDecimal("BEN_MONEY");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            bank_name = rowset.getString("BANK_NAME");
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            ben_status = rowset.getString("BEN_STATUS");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            product_period = new Integer(rowset.getInt("PRODUCT_PERIOD"));
	            is_transferential = new Integer(rowset.getInt("IS_TRANSFERENTIAL"));

	            ben_lost_flag = new Integer(rowset.getInt("BEN_LOST_FLAG"));
	            ben_lost_date = new Integer(rowset.getInt("BEN_LOST_DATE"));
	            card_lost_flag = new Integer(rowset.getInt("CARD_LOST_FLAG"));
	            card_lost_date = new Integer(rowset.getInt("CARD_LOST_DATE"));
	            ben_card_no = rowset.getString("BEN_CARD_NO");
	            st_chg_reason = rowset.getString("ST_CHG_REASON");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextModi()
		 */
	    @Override
		public boolean getNextModi() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.serial_no = (Integer) rowset.getObject("SERIAL_NO");
	            this.cust_id = (Integer) rowset.getObject("CUST_ID");
	            this.list_id = (Integer) rowset.getObject("LIST_ID");
	            this.contract_bh = rowset.getString("CONTRACT_BH");
	            this.contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            this.product_id = (Integer) rowset.getObject("PRODUCT_ID");
	            this.product_name = rowset.getString("PRODUCT_NAME");
	            this.cust_acct_name = rowset.getString("CUST_ACCT_NAME");
	            this.cust_name = rowset.getString("CUST_NAME");
	            this.sy_card_id = rowset.getString("CARD_ID");

	            this.bank_id = rowset.getString("BANK_ID");
	            this.bank_name = rowset.getString("BANK_NAME");
	            this.bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            this.bank_acct = rowset.getString("BANK_ACCT");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextModiDetail()
		 */
	    @Override
		public boolean getNextModiDetail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {

	            serial_no = (Integer) rowset.getObject("SERIAL_NO");
	            product_id = (Integer) rowset.getObject("PRODUCT_ID"); //汤双根
	            // 07/03/07
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            cust_id = (Integer) rowset.getObject("CUST_ID");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            list_id = (Integer) rowset.getObject("LIST_ID");
	            ben_serial_no = (Integer) rowset.getObject("BEN_SERIAL_NO");
	            old_bank_id = rowset.getString("OLD_BANK_ID");
	            old_bank_sub_name = rowset.getString("OLD_BANK_SUB_NAME");
	            old_bank_acct = rowset.getString("OLD_BANK_ACCT");
	            temp_acct_name = rowset.getString("OLD_ACCT_NAME");
	            cust_acct_name = rowset.getString("NEW_ACCT_NAME");

	            new_bank_id = rowset.getString("NEW_BANK_ID");
	            new_bank_sub_name = rowset.getString("NEW_BANK_SUB_NAME");
	            new_bank_acct = rowset.getString("NEW_BANK_ACCT");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            modi_man = (Integer) rowset.getObject("MODI_MAN");
	            modi_check_man = (Integer) rowset.getObject("MODI_CHECK_MAN");
	            modi_time = rowset.getTimestamp("MODI_TIME");
	            modi_check_time = rowset.getTimestamp("MODI_CHECK_TIME");
	            modi_bank_date = (Integer) rowset.getObject("MODI_BANK_DATE");
	        }
	        return b;
	    }

	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextModiDetailList()
		 */
		@Override
		public List getNextModiDetailList() throws Exception {
			List list = new ArrayList();
			while(super.getNext()) {
				Map map = new HashMap();
				map.put("SERIAL_NO", rowset.getObject("SERIAL_NO"));
				map.put("PRODUCT_ID", rowset.getObject("PRODUCT_ID"));
				map.put("PRODUCT_CODE", rowset.getObject("PRODUCT_CODE"));
				map.put("PRODUCT_NAME", rowset.getObject("PRODUCT_NAME"));
				map.put("CONTRACT_BH", rowset.getObject("CONTRACT_BH"));
				map.put("CUST_ID", rowset.getObject("CUST_ID"));
				map.put("CUST_NAME", rowset.getObject("CUST_NAME"));
				map.put("CUST_NO", rowset.getObject("CUST_NO"));
				map.put("LIST_ID", rowset.getObject("LIST_ID"));
				map.put("BEN_SERIAL_NO", rowset.getObject("BEN_SERIAL_NO"));
				map.put("OLD_BANK_ID", rowset.getObject("OLD_BANK_ID"));
				map.put("OLD_BANK_SUB_NAME", rowset.getObject("OLD_BANK_SUB_NAME"));
				map.put("OLD_BANK_ACCT", rowset.getObject("OLD_BANK_ACCT"));
				map.put("OLD_ACCT_NAME", rowset.getObject("OLD_ACCT_NAME"));
				map.put("NEW_ACCT_NAME", rowset.getObject("NEW_ACCT_NAME"));
				map.put("NEW_BANK_ID", rowset.getObject("NEW_BANK_ID"));
				map.put("NEW_BANK_SUB_NAME", rowset.getObject("NEW_BANK_SUB_NAME"));
				map.put("NEW_BANK_ACCT", rowset.getObject("NEW_BANK_ACCT"));
				map.put("CONTRACT_SUB_BH", rowset.getObject("CONTRACT_SUB_BH"));
				map.put("BEN_AMOUNT", rowset.getObject("BEN_AMOUNT"));
				map.put("MODI_MAN", rowset.getObject("MODI_MAN"));
				map.put("MODI_CHECK_MAN", rowset.getObject("MODI_CHECK_MAN"));
				map.put("MODI_TIME", rowset.getObject("MODI_TIME"));
				map.put("MODI_CHECK_TIME", rowset.getObject("MODI_CHECK_TIME"));
				map.put("MODI_BANK_DATE", rowset.getObject("MODI_BANK_DATE"));
				list.add(map);
			}
			return list;
		}
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextModiStatusDetail()
		 */
	    @Override
		public boolean getNextModiStatusDetail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = (Integer) rowset.getObject("SERIAL_NO");
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            list_id = (Integer) rowset.getObject("LIST_ID");
	            ben_serial_no = (Integer) rowset.getObject("BEN_SERIAL_NO");
	            old_bank_id = rowset.getString("OLD_STATUS");
	            old_bank_sub_name = rowset.getString("OLD_STATUS_NAME");
	            new_bank_id = rowset.getString("NEW_STATUS");
	            new_bank_sub_name = rowset.getString("NEW_STATUS_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");

	            modi_man = (Integer) rowset.getObject("MODI_MAN");
	            modi_check_man = (Integer) rowset.getObject("MODI_CHECK_MAN");
	            modi_time = rowset.getTimestamp("MODI_TIME");
	            modi_check_time = rowset.getTimestamp("MODI_CHECK_TIME");
	            //modi_bank_date=(Integer) rowset.getObject("MODI_BANK_DATE");
	        }
	        return b;

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextNew3()
		 */
	    @Override
		public boolean getNextNew3() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            contract_bh = rowset.getString("CONTRACT_BH");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            ben_end_date = (Integer) rowset.getObject("BEN_END_DATE");
	            cust_name = rowset.getString("CUST_NAME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextStatus()
		 */
	    @Override
		public boolean getNextStatus() throws Exception {
	        boolean b = super.getNext();
	        if (b) {

	            ben_count = new Integer(rowset.getInt("BEN_COUNT"));
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_status = rowset.getString("BEN_STATUS");
	            ben_status_name = rowset.getString("BEN_STATUS_NAME");

	        }
	        return b;
	    }
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#QueryBenifitorProv()
		 */

	    @Override
		public void QueryBenifitorProv() throws Exception {
	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_sub_bh;
	        params[3] = cust_no;
	        params[4] = cust_name;
	        params[5] = Utility.parseInt(prov_flag, new Integer(0));
	        params[6] = prov_level;
	        params[7] = Utility.parseInt(check_flag, new Integer(0));
	        super.query("{call SP_QUERY_TBENIFITOR_PROV(?,?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextBenifitorProv()
		 */
	    @Override
		public boolean getNextBenifitorProv() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	        	serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	        	cust_id = new Integer(rowset.getInt("PRODUCT_ID"));
	        	contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	        	list_id =  new Integer(rowset.getInt("LIST_ID"));
	        	cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
	            ben_date = (Integer) rowset.getObject("BEN_DATE");
	            prov_flag = new Integer(rowset.getInt("PROV_FLAG"));
	            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
	            prov_level_name_temp = rowset.getString("PROV_LEVEL_NAME_TEMP");
	        }
	        return b;
	    }
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#checkBenifitorProv()
		 */
	    @Override
		public void checkBenifitorProv() throws Exception {
	        Object[] params = new Object[4];
	        params[0] = book_code;
	        params[1] = serial_no;
	        params[2] = input_man;
	        params[3] = check_flag;
	        try {
	            super.update(
	                    "{?=call SP_CHECK_TBENIFITOR_LEVEL(?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("收益级别审核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getHbenifitor()
		 */
	    @Override
		public void getHbenifitor() throws Exception {
	        Object[] params = new Object[6];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = Utility.parseInt(book_code, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = Utility.parseInt(sub_product_id, new Integer(0));
	        params[4] = "";
	        params[5] = "";
	        try {
	            super.query(
	                    "{call SP_QUERY_HBENIFITOR(?,?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("查询对应收益人的历史份额失败: " + e.getMessage());
	        }    	
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNextHbenifitor()
		 */
	    @Override
		public boolean getNextHbenifitor() throws Exception {
	        boolean b = super.getNext();
	        if (b) {//客户名称，起始日期，结束日期，期初份额，期初金额，增加份额，增加金额，减少份额，减少金额，期末份额，期末金额
	        	cust_name = rowset.getString("CUST_NAME");
	        	start_date = new Integer(rowset.getInt("START_DATE"));
	        	end_date = new Integer(rowset.getInt("END_DATE"));
	        	qc_fe =  rowset.getBigDecimal("BEN_AMOUNT0");
	        	qc_je = rowset.getBigDecimal("BEN_MONEY0");
	            add_fe = rowset.getBigDecimal("ADD_AMOUNT");
	            add_je = rowset.getBigDecimal("ADD_MONEY");
	            dec_fe = rowset.getBigDecimal("REDUCE_AMOUNT");
	            dec_je = rowset.getBigDecimal("REDUCE_MONEY");
	            qm_fe = rowset.getBigDecimal("BEN_AMOUNT");
	            qm_je = rowset.getBigDecimal("BEN_MONEY");
	        }
	        return b;
	    }    
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getAcct_chg_reason()
		 */
	    @Override
		public String getAcct_chg_reason() {
	        return acct_chg_reason;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setAcct_chg_reason(java.lang.String)
		 */
	    @Override
		public void setAcct_chg_reason(String acct_chg_reason) {
	        this.acct_chg_reason = acct_chg_reason;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getAcct_user_name()
		 */
	    @Override
		public String getAcct_user_name() {
	        return acct_user_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setAcct_user_name(java.lang.String)
		 */
	    @Override
		public void setAcct_user_name(String acct_user_name) {
	        this.acct_user_name = acct_user_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBank_acct()
		 */
	    @Override
		public java.lang.String getBank_acct() {
	        return bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBank_acct(java.lang.String)
		 */
	    @Override
		public void setBank_acct(java.lang.String bank_acct) {
	        this.bank_acct = bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBank_acct_type()
		 */
	    @Override
		public String getBank_acct_type() {
	        return bank_acct_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBank_acct_type(java.lang.String)
		 */
	    @Override
		public void setBank_acct_type(String bank_acct_type) {
	        this.bank_acct_type = bank_acct_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBank_id()
		 */
	    @Override
		public java.lang.String getBank_id() {
	        return bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBank_id(java.lang.String)
		 */
	    @Override
		public void setBank_id(java.lang.String bank_id) {
	        this.bank_id = bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBank_name()
		 */
	    @Override
		public java.lang.String getBank_name() {
	        return bank_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBank_name(java.lang.String)
		 */
	    @Override
		public void setBank_name(java.lang.String bank_name) {
	        this.bank_name = bank_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBank_sub_name()
		 */
	    @Override
		public java.lang.String getBank_sub_name() {
	        return bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBank_sub_name(java.lang.String)
		 */
	    @Override
		public void setBank_sub_name(java.lang.String bank_sub_name) {
	        this.bank_sub_name = bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_amount()
		 */
	    @Override
		public java.math.BigDecimal getBen_amount() {
	        return ben_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setBen_amount(java.math.BigDecimal ben_amount) {
	        this.ben_amount = ben_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_card_no()
		 */
	    @Override
		public String getBen_card_no() {
	        return ben_card_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_card_no(java.lang.String)
		 */
	    @Override
		public void setBen_card_no(String ben_card_no) {
	        this.ben_card_no = ben_card_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_count()
		 */
	    @Override
		public Integer getBen_count() {
	        return ben_count;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_count(java.lang.Integer)
		 */
	    @Override
		public void setBen_count(Integer ben_count) {
	        this.ben_count = ben_count;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_date()
		 */
	    @Override
		public java.lang.Integer getBen_date() {
	        return ben_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_date(java.lang.Integer)
		 */
	    @Override
		public void setBen_date(java.lang.Integer ben_date) {
	        this.ben_date = ben_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_end_date()
		 */
	    @Override
		public java.lang.Integer getBen_end_date() {
	        return ben_end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_end_date(java.lang.Integer)
		 */
	    @Override
		public void setBen_end_date(java.lang.Integer ben_end_date) {
	        this.ben_end_date = ben_end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_lost_date()
		 */
	    @Override
		public Integer getBen_lost_date() {
	        return ben_lost_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_lost_date(java.lang.Integer)
		 */
	    @Override
		public void setBen_lost_date(Integer ben_lost_date) {
	        this.ben_lost_date = ben_lost_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_lost_flag()
		 */
	    @Override
		public Integer getBen_lost_flag() {
	        return ben_lost_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_lost_flag(java.lang.Integer)
		 */
	    @Override
		public void setBen_lost_flag(Integer ben_lost_flag) {
	        this.ben_lost_flag = ben_lost_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_money()
		 */
	    @Override
		public BigDecimal getBen_money() {
	        return ben_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_money(java.math.BigDecimal)
		 */
	    @Override
		public void setBen_money(BigDecimal ben_money) {
	        this.ben_money = ben_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_serial_no()
		 */
	    @Override
		public Integer getBen_serial_no() {
	        return ben_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_serial_no(java.lang.Integer)
		 */
	    @Override
		public void setBen_serial_no(Integer ben_serial_no) {
	        this.ben_serial_no = ben_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_status()
		 */
	    @Override
		public String getBen_status() {
	        return ben_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_status(java.lang.String)
		 */
	    @Override
		public void setBen_status(String ben_status) {
	        this.ben_status = ben_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_status_name()
		 */
	    @Override
		public String getBen_status_name() {
	        return ben_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_status_name(java.lang.String)
		 */
	    @Override
		public void setBen_status_name(String ben_status_name) {
	        this.ben_status_name = ben_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_unfrozen_date()
		 */
	    @Override
		public Integer getBen_unfrozen_date() {
	        return ben_unfrozen_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBen_unfrozen_date(java.lang.Integer)
		 */
	    @Override
		public void setBen_unfrozen_date(Integer ben_unfrozen_date) {
	        this.ben_unfrozen_date = ben_unfrozen_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBonus_flag()
		 */
	    @Override
		public Integer getBonus_flag() {
	        return bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBonus_flag(java.lang.Integer)
		 */
	    @Override
		public void setBonus_flag(Integer bonus_flag) {
	        this.bonus_flag = bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBook_code()
		 */
	    @Override
		public java.lang.Integer getBook_code() {
	        return book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBook_code(java.lang.Integer)
		 */
	    @Override
		public void setBook_code(java.lang.Integer book_code) {
	        this.book_code = book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCard_lost_date()
		 */
	    @Override
		public Integer getCard_lost_date() {
	        return card_lost_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCard_lost_date(java.lang.Integer)
		 */
	    @Override
		public void setCard_lost_date(Integer card_lost_date) {
	        this.card_lost_date = card_lost_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCard_lost_flag()
		 */
	    @Override
		public Integer getCard_lost_flag() {
	        return card_lost_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCard_lost_flag(java.lang.Integer)
		 */
	    @Override
		public void setCard_lost_flag(Integer card_lost_flag) {
	        this.card_lost_flag = card_lost_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCheck_flag()
		 */
	    @Override
		public java.lang.Integer getCheck_flag() {
	        return check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCheck_flag(java.lang.Integer)
		 */
	    @Override
		public void setCheck_flag(java.lang.Integer check_flag) {
	        this.check_flag = check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCheck_man()
		 */
	    @Override
		public java.lang.Integer getCheck_man() {
	        return check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCheck_man(java.lang.Integer)
		 */
	    @Override
		public void setCheck_man(java.lang.Integer check_man) {
	        this.check_man = check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getContract_bh()
		 */
	    @Override
		public java.lang.String getContract_bh() {
	        return contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setContract_bh(java.lang.String)
		 */
	    @Override
		public void setContract_bh(java.lang.String contract_bh) {
	        this.contract_bh = contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getContract_sub_bh()
		 */
	    @Override
		public java.lang.String getContract_sub_bh() {
	        return contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setContract_sub_bh(java.lang.String)
		 */
	    @Override
		public void setContract_sub_bh(java.lang.String contract_sub_bh) {
	        this.contract_sub_bh = contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_acct_name()
		 */
	    @Override
		public String getCust_acct_name() {
	        return cust_acct_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_acct_name(java.lang.String)
		 */
	    @Override
		public void setCust_acct_name(String cust_acct_name) {
	        this.cust_acct_name = cust_acct_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_id()
		 */
	    @Override
		public java.lang.Integer getCust_id() {
	        return cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_id(java.lang.Integer)
		 */
	    @Override
		public void setCust_id(java.lang.Integer cust_id) {
	        this.cust_id = cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_name()
		 */
	    @Override
		public String getCust_name() {
	        return cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_name(java.lang.String)
		 */
	    @Override
		public void setCust_name(String cust_name) {
	        this.cust_name = cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_no()
		 */
	    @Override
		public String getCust_no() {
	        return cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_no(java.lang.String)
		 */
	    @Override
		public void setCust_no(String cust_no) {
	        this.cust_no = cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_type()
		 */
	    @Override
		public Integer getCust_type() {
	        return cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_type(java.lang.Integer)
		 */
	    @Override
		public void setCust_type(Integer cust_type) {
	        this.cust_type = cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getEnd_date()
		 */
	    @Override
		public java.lang.Integer getEnd_date() {
	        return end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setEnd_date(java.lang.Integer)
		 */
	    @Override
		public void setEnd_date(java.lang.Integer end_date) {
	        this.end_date = end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFee_date()
		 */
	    @Override
		public java.lang.Integer getFee_date() {
	        return fee_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFee_date(java.lang.Integer)
		 */
	    @Override
		public void setFee_date(java.lang.Integer fee_date) {
	        this.fee_date = fee_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFee_money()
		 */
	    @Override
		public java.math.BigDecimal getFee_money() {
	        return fee_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFee_money(java.math.BigDecimal)
		 */
	    @Override
		public void setFee_money(java.math.BigDecimal fee_money) {
	        this.fee_money = fee_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFee_tpye()
		 */
	    @Override
		public java.lang.Integer getFee_tpye() {
	        return fee_tpye;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFee_tpye(java.lang.Integer)
		 */
	    @Override
		public void setFee_tpye(java.lang.Integer fee_tpye) {
	        this.fee_tpye = fee_tpye;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFee_tpye_name()
		 */
	    @Override
		public String getFee_tpye_name() {
	        return fee_tpye_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFee_tpye_name(java.lang.String)
		 */
	    @Override
		public void setFee_tpye_name(String fee_tpye_name) {
	        this.fee_tpye_name = fee_tpye_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFirbid_flag()
		 */
	    @Override
		public Integer getFirbid_flag() {
	        return firbid_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFirbid_flag(java.lang.Integer)
		 */
	    @Override
		public void setFirbid_flag(Integer firbid_flag) {
	        this.firbid_flag = firbid_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFlag()
		 */
	    @Override
		public Integer getFlag() {
	        return flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFlag(java.lang.Integer)
		 */
	    @Override
		public void setFlag(Integer flag) {
	        this.flag = flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFrozen_money()
		 */
	    @Override
		public BigDecimal getFrozen_money() {
	        return frozen_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFrozen_money(java.math.BigDecimal)
		 */
	    @Override
		public void setFrozen_money(BigDecimal frozen_money) {
	        this.frozen_money = frozen_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getFrozen_tmp()
		 */
	    @Override
		public java.math.BigDecimal getFrozen_tmp() {
	        return frozen_tmp;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFrozen_tmp(java.math.BigDecimal)
		 */
	    @Override
		public void setFrozen_tmp(java.math.BigDecimal frozen_tmp) {
	        this.frozen_tmp = frozen_tmp;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getHt_status_name()
		 */
	    @Override
		public String getHt_status_name() {
	        return ht_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setHt_status_name(java.lang.String)
		 */
	    @Override
		public void setHt_status_name(String ht_status_name) {
	        this.ht_status_name = ht_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getInput_man()
		 */
	    @Override
		public java.lang.Integer getInput_man() {
	        return input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setInput_man(java.lang.Integer)
		 */
	    @Override
		public void setInput_man(java.lang.Integer input_man) {
	        this.input_man = input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getInput_time()
		 */
	    @Override
		public java.sql.Timestamp getInput_time() {
	        return input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setInput_time(java.sql.Timestamp)
		 */
	    @Override
		public void setInput_time(java.sql.Timestamp input_time) {
	        this.input_time = input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getIs_transferential()
		 */
	    @Override
		public Integer getIs_transferential() {
	        return is_transferential;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setIs_transferential(java.lang.Integer)
		 */
	    @Override
		public void setIs_transferential(Integer is_transferential) {
	        this.is_transferential = is_transferential;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getJk_type()
		 */
	    @Override
		public java.lang.String getJk_type() {
	        return jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setJk_type(java.lang.String)
		 */
	    @Override
		public void setJk_type(java.lang.String jk_type) {
	        this.jk_type = jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getJk_type_name()
		 */
	    @Override
		public java.lang.String getJk_type_name() {
	        return jk_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setJk_type_name(java.lang.String)
		 */
	    @Override
		public void setJk_type_name(java.lang.String jk_type_name) {
	        this.jk_type_name = jk_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getList_id()
		 */
	    @Override
		public java.lang.Integer getList_id() {
	        return list_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setList_id(java.lang.Integer)
		 */
	    @Override
		public void setList_id(java.lang.Integer list_id) {
	        this.list_id = list_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_bank_date()
		 */
	    @Override
		public Integer getModi_bank_date() {
	        return modi_bank_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_bank_date(java.lang.Integer)
		 */
	    @Override
		public void setModi_bank_date(Integer modi_bank_date) {
	        this.modi_bank_date = modi_bank_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_check_man()
		 */
	    @Override
		public Integer getModi_check_man() {
	        return modi_check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_check_man(java.lang.Integer)
		 */
	    @Override
		public void setModi_check_man(Integer modi_check_man) {
	        this.modi_check_man = modi_check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_check_time()
		 */
	    @Override
		public java.sql.Timestamp getModi_check_time() {
	        return modi_check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_check_time(java.sql.Timestamp)
		 */
	    @Override
		public void setModi_check_time(java.sql.Timestamp modi_check_time) {
	        this.modi_check_time = modi_check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_man()
		 */
	    @Override
		public Integer getModi_man() {
	        return modi_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_man(java.lang.Integer)
		 */
	    @Override
		public void setModi_man(Integer modi_man) {
	        this.modi_man = modi_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_time()
		 */
	    @Override
		public java.sql.Timestamp getModi_time() {
	        return modi_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_time(java.sql.Timestamp)
		 */
	    @Override
		public void setModi_time(java.sql.Timestamp modi_time) {
	        this.modi_time = modi_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNew_bank_acct()
		 */
	    @Override
		public String getNew_bank_acct() {
	        return new_bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setNew_bank_acct(java.lang.String)
		 */
	    @Override
		public void setNew_bank_acct(String new_bank_acct) {
	        this.new_bank_acct = new_bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNew_bank_id()
		 */
	    @Override
		public String getNew_bank_id() {
	        return new_bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setNew_bank_id(java.lang.String)
		 */
	    @Override
		public void setNew_bank_id(String new_bank_id) {
	        this.new_bank_id = new_bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getNew_bank_sub_name()
		 */
	    @Override
		public String getNew_bank_sub_name() {
	        return new_bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setNew_bank_sub_name(java.lang.String)
		 */
	    @Override
		public void setNew_bank_sub_name(String new_bank_sub_name) {
	        this.new_bank_sub_name = new_bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getOld_bank_acct()
		 */
	    @Override
		public String getOld_bank_acct() {
	        return old_bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setOld_bank_acct(java.lang.String)
		 */
	    @Override
		public void setOld_bank_acct(String old_bank_acct) {
	        this.old_bank_acct = old_bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getOld_bank_id()
		 */
	    @Override
		public String getOld_bank_id() {
	        return old_bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setOld_bank_id(java.lang.String)
		 */
	    @Override
		public void setOld_bank_id(String old_bank_id) {
	        this.old_bank_id = old_bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getOld_bank_sub_name()
		 */
	    @Override
		public String getOld_bank_sub_name() {
	        return old_bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setOld_bank_sub_name(java.lang.String)
		 */
	    @Override
		public void setOld_bank_sub_name(String old_bank_sub_name) {
	        this.old_bank_sub_name = old_bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getPeriod_unit()
		 */
	    @Override
		public Integer getPeriod_unit() {
	        return period_unit;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setPeriod_unit(java.lang.Integer)
		 */
	    @Override
		public void setPeriod_unit(Integer period_unit) {
	        this.period_unit = period_unit;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_code()
		 */
	    @Override
		public String getProduct_code() {
	        return product_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_code(java.lang.String)
		 */
	    @Override
		public void setProduct_code(String product_code) {
	        this.product_code = product_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_exp_rate()
		 */
	    @Override
		public String getProduct_exp_rate() {
	        return product_exp_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_exp_rate(java.lang.String)
		 */
	    @Override
		public void setProduct_exp_rate(String product_exp_rate) {
	        this.product_exp_rate = product_exp_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_id()
		 */
	    @Override
		public java.lang.Integer getProduct_id() {
	        return product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_id(java.lang.Integer)
		 */
	    @Override
		public void setProduct_id(java.lang.Integer product_id) {
	        this.product_id = product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_name()
		 */
	    @Override
		public String getProduct_name() {
	        return product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_name(java.lang.String)
		 */
	    @Override
		public void setProduct_name(String product_name) {
	        this.product_name = product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_period()
		 */
	    @Override
		public Integer getProduct_period() {
	        return product_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_period(java.lang.Integer)
		 */
	    @Override
		public void setProduct_period(Integer product_period) {
	        this.product_period = product_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProduct_qx()
		 */
	    @Override
		public String getProduct_qx() {
	        return product_qx;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProduct_qx(java.lang.String)
		 */
	    @Override
		public void setProduct_qx(String product_qx) {
	        this.product_qx = product_qx;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProv_level()
		 */
	    @Override
		public java.lang.String getProv_level() {
	        return prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProv_level(java.lang.String)
		 */
	    @Override
		public void setProv_level(java.lang.String prov_level) {
	        this.prov_level = prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProv_level_name()
		 */
	    @Override
		public java.lang.String getProv_level_name() {
	        return prov_level_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProv_level_name(java.lang.String)
		 */
	    @Override
		public void setProv_level_name(java.lang.String prov_level_name) {
	        this.prov_level_name = prov_level_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQs_date()
		 */
	    @Override
		public Integer getQs_date() {
	        return qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setQs_date(java.lang.Integer)
		 */
	    @Override
		public void setQs_date(Integer qs_date) {
	        this.qs_date = qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getRemain_amount()
		 */
	    @Override
		public java.math.BigDecimal getRemain_amount() {
	        return remain_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setRemain_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setRemain_amount(java.math.BigDecimal remain_amount) {
	        this.remain_amount = remain_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getRg_money()
		 */
	    @Override
		public java.math.BigDecimal getRg_money() {
	        return rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setRg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setRg_money(java.math.BigDecimal rg_money) {
	        this.rg_money = rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSerial_no()
		 */
	    @Override
		public java.lang.Integer getSerial_no() {
	        return serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSerial_no(java.lang.Integer)
		 */
	    @Override
		public void setSerial_no(java.lang.Integer serial_no) {
	        this.serial_no = serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSt_chg_reason()
		 */
	    @Override
		public String getSt_chg_reason() {
	        return st_chg_reason;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSt_chg_reason(java.lang.String)
		 */
	    @Override
		public void setSt_chg_reason(String st_chg_reason) {
	        this.st_chg_reason = st_chg_reason;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getStart_date()
		 */
	    @Override
		public java.lang.Integer getStart_date() {
	        return start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setStart_date(java.lang.Integer)
		 */
	    @Override
		public void setStart_date(java.lang.Integer start_date) {
	        this.start_date = start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSummary()
		 */
	    @Override
		public java.lang.String getSummary() {
	        return summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSummary(java.lang.String)
		 */
	    @Override
		public void setSummary(java.lang.String summary) {
	        this.summary = summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSy_address()
		 */
	    @Override
		public String getSy_address() {
	        return sy_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSy_address(java.lang.String)
		 */
	    @Override
		public void setSy_address(String sy_address) {
	        this.sy_address = sy_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSy_card_id()
		 */
	    @Override
		public String getSy_card_id() {
	        return sy_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSy_card_id(java.lang.String)
		 */
	    @Override
		public void setSy_card_id(String sy_card_id) {
	        this.sy_card_id = sy_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSy_card_type_name()
		 */
	    @Override
		public String getSy_card_type_name() {
	        return sy_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSy_card_type_name(java.lang.String)
		 */
	    @Override
		public void setSy_card_type_name(String sy_card_type_name) {
	        this.sy_card_type_name = sy_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSy_cust_name()
		 */
	    @Override
		public String getSy_cust_name() {
	        return sy_cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSy_cust_name(java.lang.String)
		 */
	    @Override
		public void setSy_cust_name(String sy_cust_name) {
	        this.sy_cust_name = sy_cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSy_cust_no()
		 */
	    @Override
		public String getSy_cust_no() {
	        return sy_cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSy_cust_no(java.lang.String)
		 */
	    @Override
		public void setSy_cust_no(String sy_cust_no) {
	        this.sy_cust_no = sy_cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_acct_name()
		 */
	    @Override
		public String getTemp_acct_name() {
	        return temp_acct_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTemp_acct_name(java.lang.String)
		 */
	    @Override
		public void setTemp_acct_name(String temp_acct_name) {
	        this.temp_acct_name = temp_acct_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_bonus_flag()
		 */
	    @Override
		public Integer getTemp_bonus_flag() {
	        return temp_bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTemp_bonus_flag(java.lang.Integer)
		 */
	    @Override
		public void setTemp_bonus_flag(Integer temp_bonus_flag) {
	        this.temp_bonus_flag = temp_bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_status()
		 */
	    @Override
		public java.lang.String getTemp_status() {
	        return temp_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTemp_status(java.lang.String)
		 */
	    @Override
		public void setTemp_status(java.lang.String temp_status) {
	        this.temp_status = temp_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTo_amount()
		 */
	    @Override
		public java.math.BigDecimal getTo_amount() {
	        return to_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTo_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setTo_amount(java.math.BigDecimal to_amount) {
	        this.to_amount = to_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTrans_type()
		 */
	    @Override
		public String getTrans_type() {
	        return trans_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTrans_type(java.lang.String)
		 */
	    @Override
		public void setTrans_type(String trans_type) {
	        this.trans_type = trans_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTransfer_flag()
		 */
	    @Override
		public Integer getTransfer_flag() {
	        return transfer_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTransfer_flag(java.lang.Integer)
		 */
	    @Override
		public void setTransfer_flag(Integer transfer_flag) {
	        this.transfer_flag = transfer_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getValid_period()
		 */
	    @Override
		public Integer getValid_period() {
	        return valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setValid_period(java.lang.Integer)
		 */
	    @Override
		public void setValid_period(Integer valid_period) {
	        this.valid_period = valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getWt_address()
		 */
	    @Override
		public String getWt_address() {
	        return wt_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setWt_address(java.lang.String)
		 */
	    @Override
		public void setWt_address(String wt_address) {
	        this.wt_address = wt_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getWt_card_id()
		 */
	    @Override
		public String getWt_card_id() {
	        return wt_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setWt_card_id(java.lang.String)
		 */
	    @Override
		public void setWt_card_id(String wt_card_id) {
	        this.wt_card_id = wt_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getWt_card_type_name()
		 */
	    @Override
		public String getWt_card_type_name() {
	        return wt_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setWt_card_type_name(java.lang.String)
		 */
	    @Override
		public void setWt_card_type_name(String wt_card_type_name) {
	        this.wt_card_type_name = wt_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getWt_cust_name()
		 */
	    @Override
		public String getWt_cust_name() {
	        return wt_cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setWt_cust_name(java.lang.String)
		 */
	    @Override
		public void setWt_cust_name(String wt_cust_name) {
	        this.wt_cust_name = wt_cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBack_amount()
		 */
	    @Override
		public java.math.BigDecimal getBack_amount() {
	        return back_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBen_amount2()
		 */
	    @Override
		public java.math.BigDecimal getBen_amount2() {
	        return ben_amount2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getExchange_amount()
		 */
	    @Override
		public java.math.BigDecimal getExchange_amount() {
	        return exchange_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_bank_acct()
		 */
	    @Override
		public java.lang.String getTemp_bank_acct() {
	        return temp_bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_bank_id()
		 */
	    @Override
		public java.lang.String getTemp_bank_id() {
	        return temp_bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_bank_sub_name()
		 */
	    @Override
		public java.lang.String getTemp_bank_sub_name() {
	        return temp_bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setFunction_id(java.lang.Integer)
		 */
	    @Override
		public void setFunction_id(java.lang.Integer function_id) {
	        this.function_id = function_id;
	    }
	    
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTo_money()
		 */
	    @Override
		public BigDecimal getTo_money() {
	        return to_money;
	    }
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTo_money(java.math.BigDecimal)
		 */
	    @Override
		public void setTo_money(BigDecimal to_money) {
	        this.to_money = to_money;
	    }
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getCust_tel()
		 */
		@Override
		public String getCust_tel() {
			return cust_tel;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setCust_tel(java.lang.String)
		 */
		@Override
		public void setCust_tel(String cust_tel) {
			this.cust_tel = cust_tel;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getMobile()
		 */
		@Override
		public String getMobile() {
			return mobile;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setMobile(java.lang.String)
		 */
		@Override
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getModi_acct_check_flag()
		 */
		@Override
		public Integer getModi_acct_check_flag() {
			return modi_acct_check_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setModi_acct_check_flag(java.lang.Integer)
		 */
		@Override
		public void setModi_acct_check_flag(Integer modi_acct_check_flag) {
			this.modi_acct_check_flag = modi_acct_check_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getBonus_rate()
		 */
		@Override
		public java.math.BigDecimal getBonus_rate() {
			return bonus_rate;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setTemp_bonus_rate(java.math.BigDecimal)
		 */
		@Override
		public void setTemp_bonus_rate(java.math.BigDecimal temp_bonus_rate) {
			this.temp_bonus_rate = temp_bonus_rate;
		}
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getTemp_bonus_rate()
		 */
		@Override
		public java.math.BigDecimal getTemp_bonus_rate() {
			return temp_bonus_rate;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setBonus_rate(java.math.BigDecimal)
		 */
		@Override
		public void setBonus_rate(java.math.BigDecimal bonus_rate) {
			this.bonus_rate = bonus_rate;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProv_flag()
		 */
		@Override
		public Integer getProv_flag() {
			return prov_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setProv_flag(java.lang.Integer)
		 */
		@Override
		public void setProv_flag(Integer prov_flag) {
			this.prov_flag = prov_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getProv_level_name_temp()
		 */
		@Override
		public String getProv_level_name_temp() {
			return prov_level_name_temp;
		}
		/**
		 * @ejb.interface-method view-type = "local"
		 * @param prov_level_name_temp 要设置的 prov_level_name_temp。
		 */
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getAdd_fe()
		 */
		@Override
		public java.math.BigDecimal getAdd_fe() {
			return add_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setAdd_fe(java.math.BigDecimal)
		 */
		@Override
		public void setAdd_fe(java.math.BigDecimal add_fe) {
			this.add_fe = add_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getAdd_je()
		 */
		@Override
		public java.math.BigDecimal getAdd_je() {
			return add_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setAdd_je(java.math.BigDecimal)
		 */
		@Override
		public void setAdd_je(java.math.BigDecimal add_je) {
			this.add_je = add_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getDec_fe()
		 */
		@Override
		public java.math.BigDecimal getDec_fe() {
			return dec_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setDec_fe(java.math.BigDecimal)
		 */
		@Override
		public void setDec_fe(java.math.BigDecimal dec_fe) {
			this.dec_fe = dec_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getDec_je()
		 */
		@Override
		public java.math.BigDecimal getDec_je() {
			return dec_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setDec_je(java.math.BigDecimal)
		 */
		@Override
		public void setDec_je(java.math.BigDecimal dec_je) {
			this.dec_je = dec_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQc_fe()
		 */
		@Override
		public java.math.BigDecimal getQc_fe() {
			return qc_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setQc_fe(java.math.BigDecimal)
		 */
		@Override
		public void setQc_fe(java.math.BigDecimal qc_fe) {
			this.qc_fe = qc_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQc_je()
		 */
		@Override
		public java.math.BigDecimal getQc_je() {
			return qc_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setQc_je(java.math.BigDecimal)
		 */
		@Override
		public void setQc_je(java.math.BigDecimal qc_je) {
			this.qc_je = qc_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQm_fe()
		 */
		@Override
		public java.math.BigDecimal getQm_fe() {
			return qm_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setQm_fe(java.math.BigDecimal)
		 */
		@Override
		public void setQm_fe(java.math.BigDecimal qm_fe) {
			this.qm_fe = qm_fe;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getQm_je()
		 */
		@Override
		public java.math.BigDecimal getQm_je() {
			return qm_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setQm_je(java.math.BigDecimal)
		 */
		@Override
		public void setQm_je(java.math.BigDecimal qm_je) {
			this.qm_je = qm_je;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#getSub_product_id()
		 */
		@Override
		public java.lang.Integer getSub_product_id() {
			return sub_product_id;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustBenifitorLocal#setSub_product_id(java.lang.Integer)
		 */
		@Override
		public void setSub_product_id(java.lang.Integer sub_product_id) {
			this.sub_product_id = sub_product_id;
		}	
}