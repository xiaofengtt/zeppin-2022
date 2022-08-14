package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;

@Component(value="intrustContract")
@Scope("prototype")
public class IntrustContractBean extends IntrustBusiBean implements IntrustContractLocal {

	 private java.lang.Integer book_code;

	    private java.lang.Integer serial_no;

	    private java.lang.String product_code;

	    private java.lang.String product_name;

	    private java.lang.String currency_id;

	    private java.lang.Integer cust_id;

	    private java.lang.String pre_code;

	    private java.lang.String contract_bh;

	    private java.lang.Integer link_man;

	    private java.lang.Integer valid_period;

	    private java.lang.Integer qs_date;

	    private java.lang.Integer start_date;

	    private java.lang.Integer end_date;

	    private java.math.BigDecimal rg_money;

	    private java.math.BigDecimal to_money;

	    private java.math.BigDecimal to_amount;

	    private java.lang.String jk_type;

	    private java.lang.String jk_type_name;

	    private java.lang.Integer jk_status;

	    private java.lang.Integer jk_date;

	    private java.lang.String bank_id;

	    private java.lang.String bank_name;

	    private java.lang.String bank_sub_name;

	    private java.lang.String bank_acct;

	    private java.lang.String gain_acct;

	    private java.lang.Integer input_man;

	    private java.sql.Timestamp input_time;

	    private java.lang.Integer zj_check_flag;

	    private java.lang.Integer zj_check_man;

	    private java.lang.Integer check_flag;

	    private java.lang.String trans_flag;

	    private java.lang.String trans_flag_name;

	    private java.lang.Integer last_trans_date;

	    private java.lang.String ht_status;

	    private java.lang.String ht_status_name;

	    private java.lang.Integer service_man;

	    private java.lang.String summary;

	    private java.lang.Integer product_id;

	    private java.lang.String cust_no;

	    private java.lang.String cust_name;

	    private java.lang.String card_id;

	    private java.lang.String currency_name;

	    private java.lang.String check_flag_name;

	    private java.math.BigDecimal pre_money;

	    private java.lang.Integer temp_end_date;

	    private java.math.BigDecimal temp_rg_money;

	    private java.lang.Integer only_thisproduct = new Integer(0); //参数暂时没有使用

	    private java.lang.Integer cust_type = new Integer(0);

	    //
	    private java.lang.String benifitor_name;

	    private java.lang.String benifitor_card_id;

	    private java.lang.String str_qs_date;

	    private java.lang.String str_jk_date;

	    private java.lang.String str_valid_period;

	    private java.lang.String acct_user;

	    private java.lang.Integer check_man;

	    private java.sql.Timestamp check_time;

	    private java.lang.Integer pre_flag; //预约标志

	    private java.lang.Integer contract_id;

	    private java.lang.Integer list_id;

	    private int ben_rec_no;

	    //20050114 By CaiYuan
	    private String prov_level; //受益级别
	    private String prov_level_name;//受益级别名称

	    //	20050514 By 谭鸿
	    private String entity_name; //财产名称

	    private String entity_type; //财产类别

	    private String entity_type_name; //财产名称

	    private String contract_sub_bh; //财产实际合同编号

	    private java.math.BigDecimal entity_price; //财产价格

	    private java.lang.Integer pz_flag;//财产信托合同生成凭证标志

	    private java.lang.Integer city_serialno;

	    private java.lang.Integer terminate_date;//终止日期

	    private String city_name;

	    private java.math.BigDecimal max_rg_money;

	    private java.math.BigDecimal min_rg_money;

	    private Integer df_cust_id;

	    private int self_ben_flag;

	    private String touch_type;

	    private String touch_type_name;

	    private int fee_jk_type;//费用缴款方式：1从本金扣，2另外交,0不交

	    private String bank_acct_type;//银行账号类型（9920）

	    private BigDecimal rg_money2;

	    private BigDecimal rg_fee_rate;

	    private BigDecimal rg_fee_money;

	    private BigDecimal jk_total_money;

	    //2009-08-14
	    private String wtr_cust_type_name;

	    private String wtr_contact_methed;

	    private String wtr_address;

	    private String wtr__post;

	    private String wtr_card_type_name;

	    private String wtr_lpr;

	    private String wtr_telephone;

	    private String wtr_handset_number;

	    private String wtr_Email;

	    private String benifitor_cust_type_name;

	    private String benifitor_contact_methed;

	    private String benifitor_address;

	    private String benifitor_post;

	    private String benifitor_card_type_name;

	    private String benifitor_lpr;

	    private String benifitor_telephone;

	    private String benifitor_handset_number;

	    private String benifitor_Email;

	    private String property_type_name;

	    private String property_name;

	    private Integer bonus_flag;//收益分配方式

	    private Integer sub_product_id;
	    
	    private Integer contract_zjflag;//--合同性质：1 资金合同 2 财产合同 0 全部

	    private Integer with_bank_flag;//是否银信合作 1是 0 否 add by liug 20101123
	    
	    private String ht_bank_id;//合同银行id add by liug 20101208
	    
	    private String ht_bank_sub_name;//合同银行下级支行名称 add by liug 20101208
	    
	    private String ht_bank_name;//合同银行名称
	    
	    private Integer with_security_flag;//是否证信合作
	    
	    private Integer with_private_flag;//是否私募基金合作
	    private BigDecimal min_buy_limit;//子产品合同最低金额
	    private BigDecimal max_buy_limit;//子产品合同最高金额
	    
	    private BigDecimal bonus_rate;//转份额比例 add by liugang 20110216
	    
	    private String product_list_id;
	    
	    private Integer prov_flag;
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProduct_list_id()
		 */
		@Override
		public String getProduct_list_id() {
			return product_list_id;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProduct_list_id(java.lang.String)
		 */
		@Override
		public void setProduct_list_id(String product_list_id) {
			this.product_list_id = product_list_id;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getContract_zjflag()
		 */
		@Override
		public Integer getContract_zjflag() {
			return contract_zjflag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setContract_zjflag(java.lang.Integer)
		 */
		@Override
		public void setContract_zjflag(Integer contract_zjflag) {
			this.contract_zjflag = contract_zjflag;
		}
	    protected void validate() throws BusiException {
	        super.validate();
	        if (book_code.intValue() <= 0)
	            throw new BusiException("帐套代码不合法.");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#appendNoPre()
		 */
	    @Override
		public void appendNoPre() throws BusiException {
	        int ret;
	        Connection conn = null;
	        CallableStatement stmt = null;
	        
	        try {
	            super.append();
	            conn = IntrustDBManager.getConnection();
	            stmt = conn.prepareCall(
	                            "{?=call SP_ADD_TCONTRACT_NOPRE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            ResultSet.TYPE_SCROLL_INSENSITIVE,
	                            ResultSet.CONCUR_READ_ONLY);
	            stmt.registerOutParameter(1, java.sql.Types.INTEGER);

	            stmt.setInt(2, book_code.intValue());
	            stmt.setInt(3, cust_id.intValue());
	            if (link_man != null)
	                stmt.setInt(4, link_man.intValue());
	            else
	                stmt.setInt(4, input_man.intValue());
	            stmt.setInt(5, product_id.intValue());
	            stmt.setBigDecimal(6, rg_money);
	            stmt.setString(7, jk_type);
	            stmt.setString(8, bank_id);
	            stmt.setString(9, bank_acct);
	            if (valid_period != null)
	                stmt.setInt(10, valid_period.intValue());
	            else
	                stmt.setInt(10, 0);
	            if (service_man != null)
	                stmt.setInt(11, service_man.intValue());
	            else
	                stmt.setInt(11, input_man.intValue());
	            stmt.setString(12, summary);
	            stmt.setInt(13, input_man.intValue());
	            stmt.setString(14, contract_bh);
	            stmt.setInt(15, qs_date.intValue());
	            stmt.setInt(16, jk_date.intValue());
	            stmt.setString(17, bank_sub_name);
	            stmt.setBigDecimal(18, entity_price);
	            stmt.setString(19, entity_name);
	            stmt.setString(20, entity_type);
	            stmt.setString(21, contract_sub_bh);
	            if (city_serialno != null)
	                stmt.setInt(22, city_serialno.intValue());
	            else
	                stmt.setInt(22, 0);
	            stmt.registerOutParameter(23, java.sql.Types.INTEGER);
	            stmt.registerOutParameter(24, java.sql.Types.VARCHAR);
	            stmt.setInt(25, self_ben_flag);
	            stmt.setString(26, touch_type);
	            stmt.setString(27, touch_type_name);
	            stmt.setString(28, gain_acct);
	            stmt.setInt(29, fee_jk_type);
	            stmt.setString(30, bank_acct_type);
	            if (start_date != null)
	                stmt.setInt(31, start_date.intValue());
	            else
	                stmt.setInt(31, 0);
	            if (end_date != null)
	                stmt.setInt(32, end_date.intValue());
	            else
	                stmt.setInt(32, 0);
	            stmt.setInt(33, bonus_flag.intValue());
	            stmt.setInt(34, Utility.parseInt(Utility.trimNull(sub_product_id),0));
	            stmt.setInt(35, Utility.parseInt(Utility.trimNull(with_bank_flag),0));
	            stmt.setString(36, Utility.trimNull(ht_bank_id));
	            stmt.setString(37,Utility.trimNull(ht_bank_sub_name));
	            stmt.setInt(38, 0);
	            stmt.setInt(39, Utility.parseInt(Utility.trimNull(with_security_flag),0));
	            stmt.setInt(40, Utility.parseInt(Utility.trimNull(with_private_flag),0));
	            stmt.setBigDecimal(41, bonus_rate);
	            if(prov_flag!=null)
	            	stmt.setInt(42,prov_flag.intValue());
	            else
	            	stmt.setInt(42,1);
	            stmt.executeUpdate();
	            ret = stmt.getInt(1);
	            serial_no = new Integer(stmt.getInt(23));
	            contract_bh = stmt.getString(24);

	            IntrustDBManager.handleResultCode(ret);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusiException("认购合同新建失败: " + e.getMessage());
	        } finally{
	            try {
					stmt.close();
					conn.close();   
				} catch (SQLException e) {
					 throw new BusiException("数据库关闭异常: " + e.getMessage());
				}   	
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#append()
		 */
	    @Override
		public void append() throws BusiException {
	        int ret;
	        try {
	        	super.append();
	            Connection conn = IntrustDBManager.getConnection();
	            CallableStatement stmt = conn
	                    .prepareCall(
	                            "{?=call SP_ADD_TCONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            ResultSet.TYPE_SCROLL_INSENSITIVE,
	                            ResultSet.CONCUR_READ_ONLY);
	            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
	            stmt.setInt(2, product_id.intValue());
	            stmt.setString(3, pre_code);
	            stmt.setBigDecimal(4, rg_money);
	            stmt.setString(5, jk_type);
	            stmt.setString(6, bank_id);
	            stmt.setString(7, bank_acct);
	            stmt.setInt(8, valid_period.intValue());
	            stmt.setInt(9, service_man.intValue());
	            stmt.setString(10, summary);
	            stmt.setInt(11, input_man.intValue());
	            stmt.setInt(12, check_man.intValue());
	            stmt.setString(13, contract_bh);
	            stmt.setInt(14, qs_date.intValue());
	            stmt.setInt(15, jk_date.intValue());
	            stmt.setString(16, bank_sub_name);
	            stmt.setInt(17, city_serialno.intValue());
	            stmt.registerOutParameter(18, java.sql.Types.INTEGER);
	            stmt.registerOutParameter(19, java.sql.Types.VARCHAR);
	            stmt.setInt(20, self_ben_flag);
	            stmt.setString(21, touch_type);
	            stmt.setString(22, touch_type_name);
	            stmt.setString(23, gain_acct);
	            stmt.setInt(24, fee_jk_type);
	            stmt.setString(25, contract_sub_bh);
	            stmt.setString(26, bank_acct_type);
	            stmt.setInt(27, bonus_flag.intValue());
	            stmt.setInt(28, Utility.parseInt(Utility.trimNull(sub_product_id),0));
	            stmt.setInt(29, Utility.parseInt(Utility.trimNull(with_bank_flag),0));
	            stmt.setString(30, Utility.trimNull(ht_bank_id));
	            stmt.setString(31,Utility.trimNull(ht_bank_sub_name));
	            stmt.setInt(32, 0);
	            stmt.setInt(33, Utility.parseInt(Utility.trimNull(with_security_flag),0));
	            stmt.setInt(34, Utility.parseInt(Utility.trimNull(with_private_flag),0));
	            stmt.setBigDecimal(35, bonus_rate);
	            stmt.executeUpdate();
	            ret = stmt.getInt(1);
	            serial_no = new Integer(stmt.getInt(18));
	            contract_bh = stmt.getString(19);
	            stmt.close();
	            conn.close();
	            IntrustDBManager.handleResultCode(ret);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusiException("认购合同新建失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#delete()
		 */
	    @Override
		public void delete() throws BusiException {
	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;
	        try {
	            super.update("{?=call SP_DEL_TCONTRACT (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("认购合同删除失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#load()
		 */
	    @Override
		public void load() throws Exception {
	        Object[] params = new Object[1];
	        params[0] = serial_no;
	        super.query("{call SP_QUERY_TCONTRACT_LOAD(?)}", params);
	        setAllAttributes();
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#save()
		 */
	    @Override
		public void save() throws BusiException {

	        Object[] params = new Object[37];
	        params[0] = serial_no;
	        params[1] = rg_money;
	        params[2] = jk_type;
	        params[3] = bank_id;
	        params[4] = bank_acct;
	        params[5] = valid_period;
	        params[6] = Utility.parseInt(service_man, new Integer(0));
	        params[7] = summary;
	        params[8] = input_man;
	        params[9] = check_man;
	        params[10] = qs_date;
	        params[11] = jk_date;
	        params[12] = Utility.parseInt(link_man, new Integer(0));
	        params[13] = product_id;
	        params[14] = bank_sub_name;
	        params[15] = contract_bh;
	        params[16] = entity_price;
	        params[17] = entity_name;
	        params[18] = entity_type;
	        params[19] = contract_sub_bh;
	        params[20] = Utility.parseInt(city_serialno, new Integer(0));
	        params[21] = touch_type;
	        params[22] = touch_type_name;
	        params[23] = gain_acct;
	        params[24] = new Integer(fee_jk_type);//费用缴款方式：1从本金扣，2另外交,0不交
	        params[25] = bank_acct_type;
	        params[26] = start_date;
	        params[27] = end_date;
	        params[28] = bonus_flag;
	        params[29] = Utility.parseInt(Utility.trimNull(with_bank_flag),new Integer(0));
	        params[30] = Utility.trimNull(ht_bank_id);
	        params[31] = Utility.trimNull(ht_bank_sub_name);
	        params[32] = new Integer(0);
	        params[33] = Utility.parseInt(Utility.trimNull(with_security_flag),new Integer(0));
	        params[34] = Utility.parseInt(Utility.trimNull(with_private_flag),new Integer(0));
	        params[35] = bonus_rate;
	        params[36] = prov_flag;
	        
	        try {
	            super.update(
	                            "{?=call SP_MODI_TCONTRACT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                            params);
	        } catch (Exception e) {
	            throw new BusiException("认购合同保存失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkEntity()
		 */
	    @Override
		public void checkEntity() throws BusiException {

	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_TCONTRACT_ENTIRY (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("审核失败！: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkEntityPZ()
		 */
	    @Override
		public void checkEntityPZ() throws BusiException {
	        Object[] params = new Object[5];
	        params[0] = serial_no;
	        params[1] = entity_type;
	        params[2] = input_man;
	        params[3] = Utility.parseInt(start_date, new Integer(0));
	        params[4] = Utility.parseInt(check_flag, new Integer(0));

	        try {
	            super.update("{?=call SP_CHECK_TCONTRACT_ENTIRY_PZ (?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("审核失败！: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkContract()
		 */
	    @Override
		public void checkContract() throws BusiException {

	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_TCONTRACT_BACK(?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("审核失败！: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryContractByCustID()
		 */
	    @Override
		public void queryContractByCustID() throws Exception {
	        Object[] params = new Object[3];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = Utility.parseInt(cust_id, new Integer(0));
	        super.query("{call SP_QUERY_TCONTRACT_BYCUSTID(?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#appendLose()
		 */
	    @Override
		public void appendLose() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = input_man;

	        try {
	            super.update("{?=call SP_LOSE_TCONTRACT(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同挂失失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#unLose()
		 */
	    @Override
		public void unLose() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = input_man;

	        try {
	            super.update("{?=call SP_UNLOSE_TCONTRACT(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同解挂失败！: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#Frozen()
		 */
	    @Override
		public void Frozen() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = input_man;

	        try {
	            super.update("{?=call SP_FROZEN_TCONTRACT(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同冻结失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#UnFrozen()
		 */
	    @Override
		public void UnFrozen() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = input_man;

	        try {
	            super.update("{?=call SP_UNFROZEN_TCONTRACT(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同解冻失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkFrozen(int)
		 */
	    @Override
		public void checkFrozen(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_FROZEN_TCONTRACT(?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同冻结复核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkUnFrozen(int)
		 */
	    @Override
		public void checkUnFrozen(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_UNFROZEN_TCONTRACT(?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("合同解冻复核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#appendContinueContract()
		 */
	    @Override
		public void appendContinueContract() throws BusiException {

	        Object[] params = new Object[8];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = end_date;
	        params[3] = rg_money;
	        params[4] = qs_date;
	        params[5] = summary;
	        params[6] = input_man;
	        params[7] = new Integer(0);

	        try {
	            super.update("{?=call SP_CONTINUE_TCONTRACT(?,?,?,?,?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("合同续签失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#stopContract()
		 */
	    @Override
		public void stopContract() throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = input_man;
	        params[3] = terminate_date;

	        try {
	            super.update("{?=call SP_STOP_TCONTRACT(?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同中止失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkLose(int)
		 */
	    @Override
		public void checkLose(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_LOSE_TCONTRACT(?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同挂失复核失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkUnlose(int)
		 */
	    @Override
		public void checkUnlose(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_UNLOSE_TCONTRACT(?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同解挂复核失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkContinueContract(int)
		 */
	    @Override
		public void checkContinueContract(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_CONTINUE_TCONTRACT(?,?,?,?)}",
	                    params);
	        } catch (Exception e) {
	            throw new BusiException("合同续签复核失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#checkStopContract(int)
		 */
	    @Override
		public void checkStopContract(int flag) throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = product_id;
	        params[1] = contract_bh;
	        params[2] = new Integer(flag);
	        params[3] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_STOP_TCONTRACT(?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("合同中止复核失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#list()
		 */
	    @Override
		public void list() throws Exception {
	        query(" $  $ ");
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryLoseValideContract(java.lang.String)
		 */
	    @Override
		public void queryLoseValideContract(String sQuery) throws Exception {

	        String[] paras = Utility.splitString(sQuery + " ", "$");
	        Object[] params = new Object[7];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = "";
	        params[3] = input_man;
	        params[4] = cust_no;
	        params[5] = cust_name;
	        params[6] = paras[1].trim();

	        super.query("{call SP_QUERY_TCONTRACT_LOSE (?,?,?,?,?,?,?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryEndContract(java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer)
		 */
	    @Override
		public void queryEndContract(Integer productid, String contractsubbh,
	            Integer startdate, Integer enddate) throws Exception {

	        Object[] params = new Object[7];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(productid, new Integer(0));
	        params[2] = "";
	        params[3] = Utility.parseInt(startdate, new Integer(0));
	        params[4] = Utility.parseInt(enddate, new Integer(0));
	        params[5] = input_man;
	        params[6] = contractsubbh;

	        super.query("{call SP_QUERY_TCONTRACT_END (?,?,?,?,?,?,?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryNewContract(java.lang.String)
		 */
	    @Override
		public void queryNewContract(String sQuery) throws Exception {
	        queryContract(sQuery, 1);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#query(java.lang.String)
		 */
	    @Override
		public void query(String sQuery) throws Exception {
	        queryContract(sQuery + " ", 0);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryPurchanseContract(java.lang.String, java.lang.String)
		 */
	    @Override
		public void queryPurchanseContract(String custName, String cardId)
	            throws Exception {

	        //update by tsg 2008-01-03 SP_QUERY_TCONTRACT_COMM一更新
	        //Object[] params = new Object[11];
	        Object[] params = new Object[13];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        ;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = custName;
	        params[4] = cardId;
	        params[5] = Utility.parseInt(input_man, new Integer(0));
	        ;
	        params[6] = Utility.parseInt(pre_flag, new Integer(0));
	        params[7] = min_rg_money;
	        params[8] = max_rg_money;
	        params[9] = product_name;
	        params[10] = Utility.parseInt(check_flag, new Integer(0));
	        params[11] = contract_sub_bh;
	        params[12] = Utility.parseInt(contract_zjflag,new Integer(0));
	        super.query("{call SP_QUERY_TCONTRACT_COMM(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryEntityContract()
		 */
	    @Override
		public void queryEntityContract() throws Exception {

	        Object[] params = new Object[10];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = product_code;
	        params[3] = contract_bh;
	        params[4] = Utility.parseInt(check_flag, new Integer(0));
	        params[5] = Utility.parseInt(input_man, new Integer(0));
	        params[6] = entity_type;
	        params[7] = cust_name;
	        params[8] = Utility.parseInt(pz_flag, new Integer(0));
	        params[9] = contract_sub_bh;

	        super.query("{call SP_QUERY_TCONTRACT_ENTITY (?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryContract(java.lang.String, int)
		 */
	    @Override
		public void queryContract(String sQuery, int flag) throws Exception {

	        String[] paras = Utility.splitString(sQuery + " ", "$");
	        Object[] params = new Object[9];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = paras[1].trim();
	        params[3] = "";
	        params[4] = new Integer(flag);
	        params[5] = input_man;
	        params[6] = paras[2].trim();
	        params[7] = Utility.parseInt(contract_zjflag, new Integer(0));
	        params[8] = product_name;
	        super.query("{call SP_QUERY_TCONTRACT (?,?,?,?,?,?,?,?,?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryContract()
		 */
	    @Override
		public void queryContract() throws Exception {
	        Object[] params = new Object[8];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = product_code;
	        params[3] = contract_bh;
	        params[4] = Utility.parseInt(check_flag, new Integer(0));
	        params[5] = Utility.parseInt(input_man, new Integer(0));
	        params[6] = contract_sub_bh;
	        params[7] = Utility.parseInt(contract_zjflag, new Integer(0));
	        super.query("{call SP_QUERY_TCONTRACT (?,?,?,?,?,?,?,?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#listByCustId(java.lang.Integer)
		 */
	    @Override
		public void listByCustId(Integer cust_id) throws Exception {

	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(cust_id, new Integer(0));
	        params[1] = input_man;

	        super.query("{call SP_QUERY_TCONTRACT_CUST_ID (?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryLose(java.lang.String)
		 */
	    //产品编号$合同编号$复核标志（4查出未复核和未科长复核的 0 查出所有）
	    @Override
		public void queryLose(String sQuery) throws Exception {

	        String[] paras = Utility.splitString(sQuery, "$");
	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = "";
	        params[3] = new Integer(Utility.parseInt(paras[2].trim(), 0));
	        params[4] = input_man;
	        params[5] = cust_no;
	        params[6] = cust_name;
	        params[7] = paras[1].trim();

	        super.query("{call SP_QUERY_LOSE_TCONTRACT (?,?,?,?,?,?,?,?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryUnLose(java.lang.String)
		 */
	    @Override
		public void queryUnLose(String sQuery) throws Exception {
	        Object[] params = new Object[6];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = Utility.parseInt(check_flag, new Integer(0));
	        params[4] = input_man;
	        params[5] = contract_sub_bh;
	        super.query("{call SP_QUERY_UNLOSE_TCONTRACT (?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryFrozen()
		 */
	    @Override
		public void queryFrozen() throws Exception {

	        Object[] params = new Object[6];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = input_man;
	        params[4] = cust_no;
	        params[5] = cust_name;
	        super.query("{call SP_QUERY_TCONTRACT_FROZEN (?,?,?,?,?,?)}", params);

	    }

	    //flag为冻结标志
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryUnFrozen(int)
		 */
	    @Override
		public void queryUnFrozen(int flag) throws Exception {

	        Object[] params = new Object[7];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = new Integer(flag);
	        params[4] = input_man;
	        params[5] = cust_no;
	        params[6] = cust_name;
	        super.query("{call SP_QUERY_FROZEN_TCONTRACT (?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryUnFrozenCheck(int)
		 */
	    @Override
		public void queryUnFrozenCheck(int flag) throws Exception {

	        Object[] params = new Object[5];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = new Integer(flag);
	        params[4] = input_man;
	        super.query("{call SP_QUERY_UNFROZEN_TCONTRACT (?,?,?,?,?)}", params);
	    }

	    //	产品编号$合同编号$复核标志（1查出未复核 0 查出所有）
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryContinueContract(java.lang.String)
		 */
	    @Override
		public void queryContinueContract(String sQuery) throws Exception {

	        String[] paras = Utility.splitString(sQuery, "$");
	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = "";
	        params[3] = new Integer(Utility.parseInt(paras[2].trim(), 0));
	        params[4] = input_man;
	        params[5] = cust_no;
	        params[6] = cust_name;
	        params[7] = paras[1].trim();
	        super.query("{call SP_QUERY_CONTINUE_TCONTRACT (?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    //	产品编号$合同编号$复核标志（4查出未复核和未科长复核的 0 查出所有）
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryStop(java.lang.String)
		 */
	    @Override
		public void queryStop(String sQuery) throws Exception {

	        String[] paras = Utility.splitString(sQuery, "$");
	        Object[] params = new Object[8];
	        params[0] = book_code;
	        params[1] = new Integer(Utility.parseInt(paras[0].trim(), 0));
	        params[2] = "";
	        params[3] = new Integer(Utility.parseInt(paras[2].trim(), 0));
	        params[4] = input_man;
	        params[5] = cust_no;
	        params[6] = cust_name;
	        params[7] = paras[1].trim();
	        super.query("{call SP_QUERY_STOP_TCONTRACT (?,?,?,?,?,?,?,?)}", params);

	    }

	    //	客户认购合同查询
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#listAll()
		 */
	    @Override
		public void listAll() throws Exception {
	        Object[] params = new Object[16];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = contract_bh;
	        params[3] = cust_name;
	        params[4] = card_id;
	        params[5] = input_man;
	        params[6] = only_thisproduct;
	        params[7] = cust_type;
	        params[8] = prov_level;
	        params[9] = cust_no;
	        params[10] = min_rg_money;
	        params[11] = max_rg_money;
	        params[12] = product_name;
	        params[13] = city_name;
	        params[14] = Utility.parseInt(service_man, new Integer(0));
	        params[15] = contract_sub_bh;
	        super
	                .query(
	                        "{call  SP_QUERY_TCONTRACT_ALL (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                        params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#listHistory()
		 */
	    @Override
		public void listHistory() throws Exception {

	        Object[] params = new Object[1];
	        params[0] = serial_no;

	        super.query("{call  SP_QUERY_TCONTRACTLIST (?)}", params);

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#listContractList()
		 */
	    @Override
		public void listContractList() throws Exception {

	        Object[] params = new Object[5];
	        params[0] = Utility.parseInt(product_id, new Integer(0));
	        params[1] = contract_bh;
	        params[2] = ht_status;
	        params[3] = Utility.parseInt(list_id, new Integer(0));
	        params[4] = contract_sub_bh;
	        super.query("{call SP_QUERY_TCONTRACTLIST_DETAIL(?,?,?,?,?)}", params);
	    }

	    //用在purchase3.jsp里
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNext3()
		 */
	    @Override
		public boolean getNext3() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            currency_id = rowset.getString("CURRENCY_ID");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            pre_code = rowset.getString("PRE_CODE");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            link_man = new Integer(rowset.getInt("LINK_MAN"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            qs_date = new Integer(rowset.getInt("QS_DATE"));
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            to_money = rowset.getBigDecimal("TO_MONEY");
	            to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            jk_status = new Integer(rowset.getInt("JK_STATUS"));
	            jk_date = new Integer(rowset.getInt("JK_DATE"));
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            gain_acct = rowset.getString("GAIN_ACCT");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");

	            zj_check_flag = new Integer(rowset.getInt("ZJ_CHECK_FLAG"));
	            zj_check_man = new Integer(rowset.getInt("ZJ_CHECK_MAN"));

	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            pre_flag = new Integer(rowset.getInt("PRE_FLAG"));
	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");
	            last_trans_date = new Integer(rowset.getInt("LAST_TRANS_DATE"));
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            service_man = new Integer(rowset.getInt("SERVICE_MAN"));
	            summary = rowset.getString("SUMMARY");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            card_id = rowset.getString("CARD_ID");
	            currency_name = Argument.getCurrencyName(currency_id);
	            check_flag_name = Argument.getCheckFlagName(check_flag);
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");

	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");

	            entity_name = rowset.getString("ENTITY_NAME");
	            entity_type = rowset.getString("ENTITY_TYPE");
	            entity_type_name = rowset.getString("ENTITY_TYPE_NAME");
	            entity_price = rowset.getBigDecimal("ENTITY_PRICE");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            terminate_date = new Integer(rowset.getInt("TERMINATE_DATE"));
	            df_cust_id = new Integer(rowset.getInt("DF_CUST_ID"));

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNext()
		 */
	    @Override
		public boolean getNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            currency_id = rowset.getString("CURRENCY_ID");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            pre_code = rowset.getString("PRE_CODE");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            link_man = new Integer(rowset.getInt("LINK_MAN"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            qs_date = new Integer(rowset.getInt("QS_DATE"));
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            to_money = rowset.getBigDecimal("TO_MONEY");
	            to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            jk_status = new Integer(rowset.getInt("JK_STATUS"));
	            jk_date = new Integer(rowset.getInt("JK_DATE"));
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            gain_acct = rowset.getString("GAIN_ACCT");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");

	            zj_check_flag = new Integer(rowset.getInt("ZJ_CHECK_FLAG"));
	            zj_check_man = new Integer(rowset.getInt("ZJ_CHECK_MAN"));

	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            pre_flag = new Integer(rowset.getInt("PRE_FLAG"));
	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");
	            last_trans_date = new Integer(rowset.getInt("LAST_TRANS_DATE"));
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            service_man = new Integer(rowset.getInt("SERVICE_MAN"));
	            summary = rowset.getString("SUMMARY");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            card_id = rowset.getString("CARD_ID");
	            currency_name = Argument.getCurrencyName(currency_id);
	            check_flag_name = Argument.getCheckFlagName(check_flag);
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");

	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");

	            entity_name = rowset.getString("ENTITY_NAME");
	            entity_type = rowset.getString("ENTITY_TYPE");
	            entity_type_name = rowset.getString("ENTITY_TYPE_NAME");
	            entity_price = rowset.getBigDecimal("ENTITY_PRICE");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            terminate_date = new Integer(rowset.getInt("TERMINATE_DATE"));
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextALL()
		 */
	    @Override
		public boolean getNextALL() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            ht_status = rowset.getString("HT_STATUS");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_name = rowset.getString("PRODUCT_NAME");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            qs_date = new Integer(rowset.getInt("QS_DATE"));
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            cust_name = rowset.getString("CUST_NAME");
	            card_id = rowset.getString("CARD_ID");
	            check_flag_name = Argument.getCheckFlagName(check_flag);
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getQueryContractByCustIDNext()
		 */
	    @Override
		public boolean getQueryContractByCustIDNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            qs_date = new Integer(rowset.getInt("QS_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");

	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            ht_status = rowset.getString("HT_STATUS");

	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));

	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextALList()
		 */
	    @Override
		public List getNextALList() throws Exception {
	        List list = new ArrayList();
	        while (super.getNext()) {
	            Map map = new HashMap();
	            map.put("SERIAL_NO", rowset.getObject("SERIAL_NO"));
	            map.put("CHECK_FLAG", rowset.getObject("CHECK_FLAG"));
	            map.put("HT_STATUS", rowset.getObject("HT_STATUS"));
	            map.put("CONTRACT_BH", rowset.getObject("CONTRACT_BH"));
	            map.put("PRODUCT_ID", rowset.getObject("PRODUCT_ID"));
	            map.put("PRODUCT_NAME", rowset.getObject("PRODUCT_NAME"));
	            map.put("CUST_ID", rowset.getObject("CUST_ID"));
	            map.put("RG_MONEY", rowset.getObject("RG_MONEY"));
	            map.put("QS_DATE", rowset.getObject("QS_DATE"));
	            map.put("HT_STATUS_NAME", rowset.getObject("HT_STATUS_NAME"));
	            map.put("TRANS_FLAG_NAME", rowset.getObject("TRANS_FLAG_NAME"));
	            map.put("CUST_NO", rowset.getObject("CUST_NO"));
	            map.put("CUST_NAME", rowset.getObject("CUST_NAME"));
	            map.put("CARD_ID", rowset.getObject("CARD_ID"));
	            //check_flag_name = Argument.getCheckFlagName(check_flag);
	            map.put("CONTRACT_SUB_BH", rowset.getObject("CONTRACT_SUB_BH"));
	            list.add(map);
	        }
	        return list;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNext1()
		 */
	    @Override
		public boolean getNext1() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            contract_id = new Integer(rowset.getInt("CONTRACT_ID"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));

	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            summary = rowset.getString("SUMMARY");
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");
	            ben_rec_no = rowset.getInt("BEN_REC_NO");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextList()
		 */
	    @Override
		public boolean getNextList() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            contract_id = new Integer(rowset.getInt("CONTRACT_ID"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));

	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            summary = rowset.getString("SUMMARY");
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");

	            contract_bh = rowset.getString("CONTRACT_BH");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            product_name = rowset.getString("PRODUCT_NAME");
	            cust_name = rowset.getString("CUST_NAME");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	        }
	        return b;
	    }

	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextLists()
		 */
		@Override
		public List getNextLists() throws Exception {
			List list = new ArrayList();
			while (super.getNext()) {
				Map map = new HashMap();
				map.put("CONTRACT_ID", rowset.getObject("CONTRACT_ID"));
				map.put("SERIAL_NO", rowset.getObject("SERIAL_NO"));
				map.put("INPUT_MAN", rowset.getObject("INPUT_MAN"));
				map.put("INPUT_TIME", rowset.getObject("INPUT_TIME"));
				map.put("HT_STATUS", rowset.getObject("HT_STATUS"));
				map.put("HT_STATUS_NAME", rowset.getObject("HT_STATUS_NAME"));
				map.put("SUMMARY", rowset.getObject("SUMMARY"));
				map.put("CHECK_MAN", rowset.getObject("CHECK_MAN"));
				map.put("CHECK_TIME", rowset.getObject("CHECK_TIME"));
				map.put("CONTRACT_BH", rowset.getObject("CONTRACT_BH"));
				map.put("LIST_ID", rowset.getObject("LIST_ID"));
				map.put("PRODUCT_NAME", rowset.getObject("PRODUCT_NAME"));
				map.put("CUST_NAME", rowset.getObject("CUST_NAME"));
				map.put("CONTRACT_SUB_BH", rowset.getObject("CONTRACT_SUB_BH"));
				list.add(map);
			}
			return list;
		}
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextList2()
		 */
	    @Override
		public boolean getNextList2() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            contract_id = new Integer(rowset.getInt("CONTRACT_ID"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));

	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            summary = rowset.getString("SUMMARY");
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");

	            contract_bh = rowset.getString("CONTRACT_BH");
	            list_id = new Integer(rowset.getInt("LIST_ID"));
	            product_name = rowset.getString("PRODUCT_NAME");
	            cust_name = rowset.getString("CUST_NAME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEndContractNext()
		 */
	    @Override
		public boolean getEndContractNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_no = rowset.getString("cust_no");
	            cust_name = rowset.getString("cust_NAME");
	            currency_id = rowset.getString("CURRENCY_ID");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            product_name = rowset.getString("product_NAME");
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            currency_name = Argument.getCurrencyName(currency_id);
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#check()
		 */
	    @Override
		public void check() throws BusiException {

	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;

	        try {
	            super.update("{?=call SP_CHECK_TCONTRACT (?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("审核失败！ " + e.getMessage());
	        }

	    }

	    /**
	     * @throws Exception
	     */
	    protected void setAllAttributes() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_code = rowset.getString("PRODUCT_CODE");
	            product_name = rowset.getString("PRODUCT_NAME");
	            currency_id = rowset.getString("CURRENCY_ID");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            pre_code = rowset.getString("PRE_CODE");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            link_man = new Integer(rowset.getInt("LINK_MAN"));
	            valid_period = new Integer(rowset.getInt("VALID_PERIOD"));
	            qs_date = new Integer(rowset.getInt("QS_DATE"));
	            start_date = new Integer(rowset.getInt("START_DATE"));
	            end_date = new Integer(rowset.getInt("END_DATE"));
	            rg_money = rowset.getBigDecimal("RG_MONEY");
	            to_money = rowset.getBigDecimal("TO_MONEY");
	            to_amount = rowset.getBigDecimal("TO_AMOUNT");
	            jk_type = rowset.getString("JK_TYPE");
	            jk_type_name = rowset.getString("JK_TYPE_NAME");
	            jk_status = new Integer(rowset.getInt("JK_STATUS"));
	            jk_date = new Integer(rowset.getInt("JK_DATE"));
	            bank_id = rowset.getString("BANK_ID");
	            bank_name = rowset.getString("BANK_NAME");
	            bank_sub_name = rowset.getString("BANK_SUB_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");
	            gain_acct = rowset.getString("GAIN_ACCT");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");

	            zj_check_flag = new Integer(rowset.getInt("ZJ_CHECK_FLAG"));
	            zj_check_man = new Integer(rowset.getInt("ZJ_CHECK_MAN"));

	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            pre_flag = new Integer(rowset.getInt("PRE_FLAG"));
	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");
	            last_trans_date = new Integer(rowset.getInt("LAST_TRANS_DATE"));
	            ht_status = rowset.getString("HT_STATUS");
	            ht_status_name = rowset.getString("HT_STATUS_NAME");
	            service_man = new Integer(rowset.getInt("SERVICE_MAN"));
	            summary = rowset.getString("SUMMARY");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_no = rowset.getString("CUST_NO");
	            card_id = rowset.getString("CARD_ID");
	            currency_name = Argument.getCurrencyName(currency_id);
	            check_flag_name = Argument.getCheckFlagName(check_flag);
	            check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            check_time = rowset.getTimestamp("CHECK_TIME");

	            trans_flag = rowset.getString("TRANS_FLAG");
	            trans_flag_name = rowset.getString("TRANS_FLAG_NAME");

	            entity_name = rowset.getString("ENTITY_NAME");
	            entity_type = rowset.getString("ENTITY_TYPE");
	            entity_type_name = rowset.getString("ENTITY_TYPE_NAME");
	            entity_price = rowset.getBigDecimal("ENTITY_PRICE");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            terminate_date = new Integer(rowset.getInt("TERMINATE_DATE"));
	            if (pre_code != null)
	                pre_money = getPrecontract_premoney(pre_code);
	            else
	                pre_money = null;
	            temp_end_date = new Integer(rowset.getInt("TEMP_END_DATE"));
	            temp_rg_money = rowset.getBigDecimal("TEMP_RG_MONEY");
	            city_serialno = new Integer(rowset.getInt("CITY_SERIAL_NO"));
	            city_name = rowset.getString("CITY_NAME");
	            terminate_date = new Integer(rowset.getInt("TERMINATE_DATE"));
	            touch_type = rowset.getString("TOUCH_TYPE");
	            touch_type_name = rowset.getString("TOUCH_TYPE_NAME");
	            fee_jk_type = rowset.getInt("FEE_JK_TYPE");
	            bank_acct_type = rowset.getString("BANK_ACCT_TYPE");

	            rg_money2 = rowset.getBigDecimal("RG_MONEY2");
	            rg_fee_rate = rowset.getBigDecimal("RG_FEE_RATE");
	            rg_fee_money = rowset.getBigDecimal("RG_FEE_MONEY");
	            jk_total_money = rowset.getBigDecimal("JK_TOTAL_MONEY");
	            bonus_flag = (Integer) rowset.getObject("BONUS_FLAG");
	            sub_product_id = (Integer) rowset.getObject("SUB_PRODUCT_ID");
	            with_bank_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("WITH_BANK_FLAG")),new Integer(0));
	            ht_bank_id = rowset.getString("HT_BANK_ID");
	            ht_bank_name = rowset.getString("HT_BANK_NAME");
	            ht_bank_sub_name = rowset.getString("HT_BANK_SUB_NAME");
	            with_security_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("WITH_SECURITY_FLAG")),new Integer(0));
	            with_private_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("WITH_PRIVATE_FLAG")),new Integer(0));
	            
	            this.min_buy_limit = rowset.getBigDecimal("MIN_BUY_LIMIT");
				this.max_buy_limit = rowset.getBigDecimal("MAX_BUY_LIMIT");			
				this.bonus_rate = rowset.getBigDecimal("BONUS_RATE");
				this.prov_level_name = rowset.getString("PROV_LEVEL_NAME");
				this.prov_flag = (Integer)rowset.getObject("PROV_FLAG");
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getPrecontract_premoney(java.lang.String)
		 */
	    @Override
		public java.math.BigDecimal getPrecontract_premoney(String precode)
	            throws BusiException {
	        Object[] oparams = new Object[3];
	        oparams[0] = book_code;
	        oparams[1] = Utility.parseInt(product_id, new Integer(0));
	        oparams[2] = pre_code;
	        try {
	            java.math.BigDecimal premoney = (BigDecimal) super.cudEx(
	                    "{?=call SP_QUERY_TPRECONTRACT_MONEY(?,?,?,?)}", oparams,
	                    5, java.sql.Types.DECIMAL);
	            return premoney;
	        } catch (Exception e) {
	            throw new BusiException("预约金额读取失败:" + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#isExistSameContractBH(java.lang.Integer)
		 */
	    @Override
		public Integer isExistSameContractBH(Integer contract_type)
	            throws BusiException {
	        Object[] oparams = new Object[4];
	        oparams[0] = book_code;
	        oparams[1] = contract_type;
	        oparams[2] = product_id;
	        oparams[3] = contract_sub_bh;
	        try {
	            Integer exist = (Integer) super.cudEx(
	                    "{?=call SP_REGEDIT_CONTRACT_BH(?,?,?,?,?)}", oparams, 6,
	                    java.sql.Types.INTEGER);
	            return exist;
	        } catch (Exception e) {
	            throw new BusiException("合同编号读取失败:" + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#Print(java.lang.Integer)
		 */
	    @Override
		public void Print(Integer cust_id) throws Exception {

	        String printSql = "SELECT DISTINCT B.* FROM TBENIFITOR A,TCONTRACT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CONTRACT_BH = B.CONTRACT_BH AND A.CUST_ID ="
	                + cust_id;
	        super.listByCommonSql(printSql);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#list2(java.lang.Integer)
		 */
	    @Override
		public void list2(Integer input_man) throws Exception {

	        Object[] params = new Object[6];
	        params[0] = book_code;
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = product_name;
	        params[3] = contract_bh;
	        params[4] = input_man;
	        params[5] = contract_sub_bh;

	        super.query("{call SP_QUERY_TCONTRACT_BACK(?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getNextPrint()
		 */
	    @Override
		public boolean getNextPrint() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            product_name = rowset.getString("PRODUCT_NAME");
	            bank_acct = rowset.getString("BANK_ACCT");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#queryOldTemp(java.lang.String, java.lang.String, java.lang.String, int)
		 */
	    @Override
		public void queryOldTemp(String product_name, String contract_bh,
	            String cust_name,int dealFlag) throws Exception {

	        Object[] params = new Object[3];
	        params[0] = product_name;
	        params[1] = contract_bh;
	        params[2] = cust_name;
	        
	        String querySQL = "{call SP_QUERY_OLD(?,?,?)}";
	        if(dealFlag==2){
	        	querySQL = "{call SP_QUERY_OLD_CHANGE(?,?,?)}";
	        }
	        super.query(querySQL, params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getOldTempNext()
		 */
	    @Override
		public boolean getOldTempNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            product_name = rowset.getString("项目名称");
	            contract_bh = rowset.getString("合同编号");
	            str_qs_date = rowset.getString("合同签订日期");
	            str_jk_date = rowset.getString("缴款日期");
	            str_valid_period = rowset.getString("合同期限");
	            rg_money = Utility.parseDecimal(rowset.getString("合同金额").trim(),
	                    new BigDecimal(0));
	            cust_name = rowset.getString("委托人");
	            card_id = rowset.getString("委托人证件编号");
	            benifitor_name = rowset.getString("受益人");
	            benifitor_card_id = rowset.getString("受益人证件编号");

	            bank_acct = rowset.getString("银行账号");
	            bank_name = rowset.getString("开户银行名称");
	            bank_sub_name = rowset.getString("支行名称");

	            acct_user = rowset.getString("开户户名");

	            //2009-08-14 显示明细增加
	            end_date = Utility.parseInt(rowset.getString("合同截止日期"),
	                    new Integer(0));
	            wtr_cust_type_name = rowset.getString("委托人类型");
	            wtr_contact_methed = rowset.getString("委托人联系方式");
	            wtr_address = rowset.getString("委托人地址");
	            wtr__post = rowset.getString("委托人邮编");
	            wtr_card_type_name = rowset.getString("委托人证件名称");
	            wtr_lpr = rowset.getString("委托人法人代表");
	            wtr_telephone = rowset.getString("委托人固定电话");
	            wtr_handset_number = rowset.getString("委托人手机");
	            wtr_Email = rowset.getString("委托人电子邮件");

	            benifitor_cust_type_name = rowset.getString("受益人类型");
	            benifitor_contact_methed = rowset.getString("受益人联系方式");
	            benifitor_address = rowset.getString("受益人地址");
	            benifitor_post = rowset.getString("受益人邮编");
	            benifitor_card_type_name = rowset.getString("受益人证件名称");
	            benifitor_lpr = rowset.getString("受益人法人代表");
	            benifitor_telephone = rowset.getString("受益人固定电话");
	            benifitor_handset_number = rowset.getString("受益人手机");
	            benifitor_Email = rowset.getString("受益人电子邮件");

	            property_type_name = rowset.getString("财产类别");
	            property_name = rowset.getString("财产名称");
	            jk_type_name = rowset.getString("缴款方式");
	            product_list_id = rowset.getString("产品期数");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getAcct_user()
		 */
	    @Override
		public java.lang.String getAcct_user() {
	        return acct_user;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setAcct_user(java.lang.String)
		 */
	    @Override
		public void setAcct_user(java.lang.String acct_user) {
	        this.acct_user = acct_user;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBank_acct()
		 */
	    @Override
		public java.lang.String getBank_acct() {
	        return bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBank_acct(java.lang.String)
		 */
	    @Override
		public void setBank_acct(java.lang.String bank_acct) {
	        this.bank_acct = bank_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBank_acct_type()
		 */
	    @Override
		public String getBank_acct_type() {
	        return bank_acct_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBank_acct_type(java.lang.String)
		 */
	    @Override
		public void setBank_acct_type(String bank_acct_type) {
	        this.bank_acct_type = bank_acct_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBank_id()
		 */
	    @Override
		public java.lang.String getBank_id() {
	        return bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBank_id(java.lang.String)
		 */
	    @Override
		public void setBank_id(java.lang.String bank_id) {
	        this.bank_id = bank_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBank_name()
		 */
	    @Override
		public java.lang.String getBank_name() {
	        return bank_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBank_name(java.lang.String)
		 */
	    @Override
		public void setBank_name(java.lang.String bank_name) {
	        this.bank_name = bank_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBank_sub_name()
		 */
	    @Override
		public java.lang.String getBank_sub_name() {
	        return bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBank_sub_name(java.lang.String)
		 */
	    @Override
		public void setBank_sub_name(java.lang.String bank_sub_name) {
	        this.bank_sub_name = bank_sub_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBen_rec_no()
		 */
	    @Override
		public int getBen_rec_no() {
	        return ben_rec_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBen_rec_no(int)
		 */
	    @Override
		public void setBen_rec_no(int ben_rec_no) {
	        this.ben_rec_no = ben_rec_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_address()
		 */
	    @Override
		public String getBenifitor_address() {
	        return benifitor_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_address(java.lang.String)
		 */
	    @Override
		public void setBenifitor_address(String benifitor_address) {
	        this.benifitor_address = benifitor_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_card_id()
		 */
	    @Override
		public java.lang.String getBenifitor_card_id() {
	        return benifitor_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_card_id(java.lang.String)
		 */
	    @Override
		public void setBenifitor_card_id(java.lang.String benifitor_card_id) {
	        this.benifitor_card_id = benifitor_card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_card_type_name()
		 */
	    @Override
		public String getBenifitor_card_type_name() {
	        return benifitor_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_card_type_name(java.lang.String)
		 */
	    @Override
		public void setBenifitor_card_type_name(String benifitor_card_type_name) {
	        this.benifitor_card_type_name = benifitor_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_contact_methed()
		 */
	    @Override
		public String getBenifitor_contact_methed() {
	        return benifitor_contact_methed;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_contact_methed(java.lang.String)
		 */
	    @Override
		public void setBenifitor_contact_methed(String benifitor_contact_methed) {
	        this.benifitor_contact_methed = benifitor_contact_methed;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_cust_type_name()
		 */
	    @Override
		public String getBenifitor_cust_type_name() {
	        return benifitor_cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_cust_type_name(java.lang.String)
		 */
	    @Override
		public void setBenifitor_cust_type_name(String benifitor_cust_type_name) {
	        this.benifitor_cust_type_name = benifitor_cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_Email()
		 */
	    @Override
		public String getBenifitor_Email() {
	        return benifitor_Email;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_Email(java.lang.String)
		 */
	    @Override
		public void setBenifitor_Email(String benifitor_Email) {
	        this.benifitor_Email = benifitor_Email;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_handset_number()
		 */
	    @Override
		public String getBenifitor_handset_number() {
	        return benifitor_handset_number;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_handset_number(java.lang.String)
		 */
	    @Override
		public void setBenifitor_handset_number(String benifitor_handset_number) {
	        this.benifitor_handset_number = benifitor_handset_number;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_lpr()
		 */
	    @Override
		public String getBenifitor_lpr() {
	        return benifitor_lpr;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_lpr(java.lang.String)
		 */
	    @Override
		public void setBenifitor_lpr(String benifitor_lpr) {
	        this.benifitor_lpr = benifitor_lpr;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_name()
		 */
	    @Override
		public java.lang.String getBenifitor_name() {
	        return benifitor_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_name(java.lang.String)
		 */
	    @Override
		public void setBenifitor_name(java.lang.String benifitor_name) {
	        this.benifitor_name = benifitor_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_post()
		 */
	    @Override
		public String getBenifitor_post() {
	        return benifitor_post;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_post(java.lang.String)
		 */
	    @Override
		public void setBenifitor_post(String benifitor_post) {
	        this.benifitor_post = benifitor_post;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBenifitor_telephone()
		 */
	    @Override
		public String getBenifitor_telephone() {
	        return benifitor_telephone;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBenifitor_telephone(java.lang.String)
		 */
	    @Override
		public void setBenifitor_telephone(String benifitor_telephone) {
	        this.benifitor_telephone = benifitor_telephone;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBonus_flag()
		 */
	    @Override
		public Integer getBonus_flag() {
	        return bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBonus_flag(java.lang.Integer)
		 */
	    @Override
		public void setBonus_flag(Integer bonus_flag) {
	        this.bonus_flag = bonus_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBook_code()
		 */
	    @Override
		public java.lang.Integer getBook_code() {
	        return book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBook_code(java.lang.Integer)
		 */
	    @Override
		public void setBook_code(java.lang.Integer book_code) {
	        this.book_code = book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCard_id()
		 */
	    @Override
		public java.lang.String getCard_id() {
	        return card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCard_id(java.lang.String)
		 */
	    @Override
		public void setCard_id(java.lang.String card_id) {
	        this.card_id = card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCheck_flag()
		 */
	    @Override
		public java.lang.Integer getCheck_flag() {
	        return check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCheck_flag(java.lang.Integer)
		 */
	    @Override
		public void setCheck_flag(java.lang.Integer check_flag) {
	        this.check_flag = check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCheck_flag_name()
		 */
	    @Override
		public java.lang.String getCheck_flag_name() {
	        return check_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCheck_flag_name(java.lang.String)
		 */
	    @Override
		public void setCheck_flag_name(java.lang.String check_flag_name) {
	        this.check_flag_name = check_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCheck_man()
		 */
	    @Override
		public java.lang.Integer getCheck_man() {
	        return check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCheck_man(java.lang.Integer)
		 */
	    @Override
		public void setCheck_man(java.lang.Integer check_man) {
	        this.check_man = check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCheck_time()
		 */
	    @Override
		public java.sql.Timestamp getCheck_time() {
	        return check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCheck_time(java.sql.Timestamp)
		 */
	    @Override
		public void setCheck_time(java.sql.Timestamp check_time) {
	        this.check_time = check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCity_name()
		 */
	    @Override
		public String getCity_name() {
	        return city_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCity_name(java.lang.String)
		 */
	    @Override
		public void setCity_name(String city_name) {
	        this.city_name = city_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCity_serialno()
		 */
	    @Override
		public java.lang.Integer getCity_serialno() {
	        return city_serialno;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCity_serialno(java.lang.Integer)
		 */
	    @Override
		public void setCity_serialno(java.lang.Integer city_serialno) {
	        this.city_serialno = city_serialno;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getContract_bh()
		 */
	    @Override
		public java.lang.String getContract_bh() {
	        return contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setContract_bh(java.lang.String)
		 */
	    @Override
		public void setContract_bh(java.lang.String contract_bh) {
	        this.contract_bh = contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getContract_id()
		 */
	    @Override
		public java.lang.Integer getContract_id() {
	        return contract_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setContract_id(java.lang.Integer)
		 */
	    @Override
		public void setContract_id(java.lang.Integer contract_id) {
	        this.contract_id = contract_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getContract_sub_bh()
		 */
	    @Override
		public String getContract_sub_bh() {
	        return contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setContract_sub_bh(java.lang.String)
		 */
	    @Override
		public void setContract_sub_bh(String contract_sub_bh) {
	        this.contract_sub_bh = contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCurrency_id()
		 */
	    @Override
		public java.lang.String getCurrency_id() {
	        return currency_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCurrency_id(java.lang.String)
		 */
	    @Override
		public void setCurrency_id(java.lang.String currency_id) {
	        this.currency_id = currency_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCurrency_name()
		 */
	    @Override
		public java.lang.String getCurrency_name() {
	        return currency_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCurrency_name(java.lang.String)
		 */
	    @Override
		public void setCurrency_name(java.lang.String currency_name) {
	        this.currency_name = currency_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCust_id()
		 */
	    @Override
		public java.lang.Integer getCust_id() {
	        return cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCust_id(java.lang.Integer)
		 */
	    @Override
		public void setCust_id(java.lang.Integer cust_id) {
	        this.cust_id = cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCust_name()
		 */
	    @Override
		public java.lang.String getCust_name() {
	        return cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCust_name(java.lang.String)
		 */
	    @Override
		public void setCust_name(java.lang.String cust_name) {
	        this.cust_name = cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCust_no()
		 */
	    @Override
		public java.lang.String getCust_no() {
	        return cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCust_no(java.lang.String)
		 */
	    @Override
		public void setCust_no(java.lang.String cust_no) {
	        this.cust_no = cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getCust_type()
		 */
	    @Override
		public java.lang.Integer getCust_type() {
	        return cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setCust_type(java.lang.Integer)
		 */
	    @Override
		public void setCust_type(java.lang.Integer cust_type) {
	        this.cust_type = cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getDf_cust_id()
		 */
	    @Override
		public Integer getDf_cust_id() {
	        return df_cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setDf_cust_id(java.lang.Integer)
		 */
	    @Override
		public void setDf_cust_id(Integer df_cust_id) {
	        this.df_cust_id = df_cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEnd_date()
		 */
	    @Override
		public java.lang.Integer getEnd_date() {
	        return end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setEnd_date(java.lang.Integer)
		 */
	    @Override
		public void setEnd_date(java.lang.Integer end_date) {
	        this.end_date = end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEntity_name()
		 */
	    @Override
		public String getEntity_name() {
	        return entity_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setEntity_name(java.lang.String)
		 */
	    @Override
		public void setEntity_name(String entity_name) {
	        this.entity_name = entity_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEntity_price()
		 */
	    @Override
		public java.math.BigDecimal getEntity_price() {
	        return entity_price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setEntity_price(java.math.BigDecimal)
		 */
	    @Override
		public void setEntity_price(java.math.BigDecimal entity_price) {
	        this.entity_price = entity_price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEntity_type()
		 */
	    @Override
		public String getEntity_type() {
	        return entity_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setEntity_type(java.lang.String)
		 */
	    @Override
		public void setEntity_type(String entity_type) {
	        this.entity_type = entity_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getEntity_type_name()
		 */
	    @Override
		public String getEntity_type_name() {
	        return entity_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setEntity_type_name(java.lang.String)
		 */
	    @Override
		public void setEntity_type_name(String entity_type_name) {
	        this.entity_type_name = entity_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getFee_jk_type()
		 */
	    @Override
		public int getFee_jk_type() {
	        return fee_jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setFee_jk_type(int)
		 */
	    @Override
		public void setFee_jk_type(int fee_jk_type) {
	        this.fee_jk_type = fee_jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getGain_acct()
		 */
	    @Override
		public java.lang.String getGain_acct() {
	        return gain_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setGain_acct(java.lang.String)
		 */
	    @Override
		public void setGain_acct(java.lang.String gain_acct) {
	        this.gain_acct = gain_acct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getHt_status()
		 */
	    @Override
		public java.lang.String getHt_status() {
	        return ht_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setHt_status(java.lang.String)
		 */
	    @Override
		public void setHt_status(java.lang.String ht_status) {
	        this.ht_status = ht_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getHt_status_name()
		 */
	    @Override
		public java.lang.String getHt_status_name() {
	        return ht_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setHt_status_name(java.lang.String)
		 */
	    @Override
		public void setHt_status_name(java.lang.String ht_status_name) {
	        this.ht_status_name = ht_status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getInput_man()
		 */
	    @Override
		public java.lang.Integer getInput_man() {
	        return input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setInput_man(java.lang.Integer)
		 */
	    @Override
		public void setInput_man(java.lang.Integer input_man) {
	        this.input_man = input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getInput_time()
		 */
	    @Override
		public java.sql.Timestamp getInput_time() {
	        return input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setInput_time(java.sql.Timestamp)
		 */
	    @Override
		public void setInput_time(java.sql.Timestamp input_time) {
	        this.input_time = input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getJk_date()
		 */
	    @Override
		public java.lang.Integer getJk_date() {
	        return jk_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setJk_date(java.lang.Integer)
		 */
	    @Override
		public void setJk_date(java.lang.Integer jk_date) {
	        this.jk_date = jk_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getJk_status()
		 */
	    @Override
		public java.lang.Integer getJk_status() {
	        return jk_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setJk_status(java.lang.Integer)
		 */
	    @Override
		public void setJk_status(java.lang.Integer jk_status) {
	        this.jk_status = jk_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getJk_total_money()
		 */
	    @Override
		public BigDecimal getJk_total_money() {
	        return jk_total_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setJk_total_money(java.math.BigDecimal)
		 */
	    @Override
		public void setJk_total_money(BigDecimal jk_total_money) {
	        this.jk_total_money = jk_total_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getJk_type()
		 */
	    @Override
		public java.lang.String getJk_type() {
	        return jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setJk_type(java.lang.String)
		 */
	    @Override
		public void setJk_type(java.lang.String jk_type) {
	        this.jk_type = jk_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getJk_type_name()
		 */
	    @Override
		public java.lang.String getJk_type_name() {
	        return jk_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setJk_type_name(java.lang.String)
		 */
	    @Override
		public void setJk_type_name(java.lang.String jk_type_name) {
	        this.jk_type_name = jk_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getLast_trans_date()
		 */
	    @Override
		public java.lang.Integer getLast_trans_date() {
	        return last_trans_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setLast_trans_date(java.lang.Integer)
		 */
	    @Override
		public void setLast_trans_date(java.lang.Integer last_trans_date) {
	        this.last_trans_date = last_trans_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getLink_man()
		 */
	    @Override
		public java.lang.Integer getLink_man() {
	        return link_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setLink_man(java.lang.Integer)
		 */
	    @Override
		public void setLink_man(java.lang.Integer link_man) {
	        this.link_man = link_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getList_id()
		 */
	    @Override
		public java.lang.Integer getList_id() {
	        return list_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setList_id(java.lang.Integer)
		 */
	    @Override
		public void setList_id(java.lang.Integer list_id) {
	        this.list_id = list_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getMax_rg_money()
		 */
	    @Override
		public java.math.BigDecimal getMax_rg_money() {
	        return max_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setMax_rg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setMax_rg_money(java.math.BigDecimal max_rg_money) {
	        this.max_rg_money = max_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getMin_rg_money()
		 */
	    @Override
		public java.math.BigDecimal getMin_rg_money() {
	        return min_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setMin_rg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setMin_rg_money(java.math.BigDecimal min_rg_money) {
	        this.min_rg_money = min_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getOnly_thisproduct()
		 */
	    @Override
		public java.lang.Integer getOnly_thisproduct() {
	        return only_thisproduct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setOnly_thisproduct(java.lang.Integer)
		 */
	    @Override
		public void setOnly_thisproduct(java.lang.Integer only_thisproduct) {
	        this.only_thisproduct = only_thisproduct;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getPre_code()
		 */
	    @Override
		public java.lang.String getPre_code() {
	        return pre_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setPre_code(java.lang.String)
		 */
	    @Override
		public void setPre_code(java.lang.String pre_code) {
	        this.pre_code = pre_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getPre_flag()
		 */
	    @Override
		public java.lang.Integer getPre_flag() {
	        return pre_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setPre_flag(java.lang.Integer)
		 */
	    @Override
		public void setPre_flag(java.lang.Integer pre_flag) {
	        this.pre_flag = pre_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getPre_money()
		 */
	    @Override
		public java.math.BigDecimal getPre_money() {
	        return pre_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setPre_money(java.math.BigDecimal)
		 */
	    @Override
		public void setPre_money(java.math.BigDecimal pre_money) {
	        this.pre_money = pre_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProduct_code()
		 */
	    @Override
		public java.lang.String getProduct_code() {
	        return product_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProduct_code(java.lang.String)
		 */
	    @Override
		public void setProduct_code(java.lang.String product_code) {
	        this.product_code = product_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProduct_id()
		 */
	    @Override
		public java.lang.Integer getProduct_id() {
	        return product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProduct_id(java.lang.Integer)
		 */
	    @Override
		public void setProduct_id(java.lang.Integer product_id) {
	        this.product_id = product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProduct_name()
		 */
	    @Override
		public java.lang.String getProduct_name() {
	        return product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProduct_name(java.lang.String)
		 */
	    @Override
		public void setProduct_name(java.lang.String product_name) {
	        this.product_name = product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProperty_name()
		 */
	    @Override
		public String getProperty_name() {
	        return property_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProperty_name(java.lang.String)
		 */
	    @Override
		public void setProperty_name(String property_name) {
	        this.property_name = property_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProperty_type_name()
		 */
	    @Override
		public String getProperty_type_name() {
	        return property_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProperty_type_name(java.lang.String)
		 */
	    @Override
		public void setProperty_type_name(String property_type_name) {
	        this.property_type_name = property_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProv_level()
		 */
	    @Override
		public String getProv_level() {
	        return prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProv_level(java.lang.String)
		 */
	    @Override
		public void setProv_level(String prov_level) {
	        this.prov_level = prov_level;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getPz_flag()
		 */
	    @Override
		public java.lang.Integer getPz_flag() {
	        return pz_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setPz_flag(java.lang.Integer)
		 */
	    @Override
		public void setPz_flag(java.lang.Integer pz_flag) {
	        this.pz_flag = pz_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getQs_date()
		 */
	    @Override
		public java.lang.Integer getQs_date() {
	        return qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setQs_date(java.lang.Integer)
		 */
	    @Override
		public void setQs_date(java.lang.Integer qs_date) {
	        this.qs_date = qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getRg_fee_money()
		 */
	    @Override
		public BigDecimal getRg_fee_money() {
	        return rg_fee_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setRg_fee_money(java.math.BigDecimal)
		 */
	    @Override
		public void setRg_fee_money(BigDecimal rg_fee_money) {
	        this.rg_fee_money = rg_fee_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getRg_fee_rate()
		 */
	    @Override
		public BigDecimal getRg_fee_rate() {
	        return rg_fee_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setRg_fee_rate(java.math.BigDecimal)
		 */
	    @Override
		public void setRg_fee_rate(BigDecimal rg_fee_rate) {
	        this.rg_fee_rate = rg_fee_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getRg_money()
		 */
	    @Override
		public java.math.BigDecimal getRg_money() {
	        return rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setRg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setRg_money(java.math.BigDecimal rg_money) {
	        this.rg_money = rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getRg_money2()
		 */
	    @Override
		public BigDecimal getRg_money2() {
	        return rg_money2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setRg_money2(java.math.BigDecimal)
		 */
	    @Override
		public void setRg_money2(BigDecimal rg_money2) {
	        this.rg_money2 = rg_money2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getSelf_ben_flag()
		 */
	    @Override
		public int getSelf_ben_flag() {
	        return self_ben_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setSelf_ben_flag(int)
		 */
	    @Override
		public void setSelf_ben_flag(int self_ben_flag) {
	        this.self_ben_flag = self_ben_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getSerial_no()
		 */
	    @Override
		public java.lang.Integer getSerial_no() {
	        return serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setSerial_no(java.lang.Integer)
		 */
	    @Override
		public void setSerial_no(java.lang.Integer serial_no) {
	        this.serial_no = serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getService_man()
		 */
	    @Override
		public java.lang.Integer getService_man() {
	        return service_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setService_man(java.lang.Integer)
		 */
	    @Override
		public void setService_man(java.lang.Integer service_man) {
	        this.service_man = service_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getStart_date()
		 */
	    @Override
		public java.lang.Integer getStart_date() {
	        return start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setStart_date(java.lang.Integer)
		 */
	    @Override
		public void setStart_date(java.lang.Integer start_date) {
	        this.start_date = start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getStr_jk_date()
		 */
	    @Override
		public java.lang.String getStr_jk_date() {
	        return str_jk_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setStr_jk_date(java.lang.String)
		 */
	    @Override
		public void setStr_jk_date(java.lang.String str_jk_date) {
	        this.str_jk_date = str_jk_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getStr_qs_date()
		 */
	    @Override
		public java.lang.String getStr_qs_date() {
	        return str_qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setStr_qs_date(java.lang.String)
		 */
	    @Override
		public void setStr_qs_date(java.lang.String str_qs_date) {
	        this.str_qs_date = str_qs_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getStr_valid_period()
		 */
	    @Override
		public java.lang.String getStr_valid_period() {
	        return str_valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setStr_valid_period(java.lang.String)
		 */
	    @Override
		public void setStr_valid_period(java.lang.String str_valid_period) {
	        this.str_valid_period = str_valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getSub_product_id()
		 */
	    @Override
		public Integer getSub_product_id() {
	        return sub_product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setSub_product_id(java.lang.Integer)
		 */
	    @Override
		public void setSub_product_id(Integer sub_product_id) {
	        this.sub_product_id = sub_product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getSummary()
		 */
	    @Override
		public java.lang.String getSummary() {
	        return summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setSummary(java.lang.String)
		 */
	    @Override
		public void setSummary(java.lang.String summary) {
	        this.summary = summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTemp_end_date()
		 */
	    @Override
		public java.lang.Integer getTemp_end_date() {
	        return temp_end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTemp_end_date(java.lang.Integer)
		 */
	    @Override
		public void setTemp_end_date(java.lang.Integer temp_end_date) {
	        this.temp_end_date = temp_end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTemp_rg_money()
		 */
	    @Override
		public java.math.BigDecimal getTemp_rg_money() {
	        return temp_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTemp_rg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setTemp_rg_money(java.math.BigDecimal temp_rg_money) {
	        this.temp_rg_money = temp_rg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTerminate_date()
		 */
	    @Override
		public java.lang.Integer getTerminate_date() {
	        return terminate_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTerminate_date(java.lang.Integer)
		 */
	    @Override
		public void setTerminate_date(java.lang.Integer terminate_date) {
	        this.terminate_date = terminate_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTo_amount()
		 */
	    @Override
		public java.math.BigDecimal getTo_amount() {
	        return to_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTo_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setTo_amount(java.math.BigDecimal to_amount) {
	        this.to_amount = to_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTo_money()
		 */
	    @Override
		public java.math.BigDecimal getTo_money() {
	        return to_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTo_money(java.math.BigDecimal)
		 */
	    @Override
		public void setTo_money(java.math.BigDecimal to_money) {
	        this.to_money = to_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTouch_type()
		 */
	    @Override
		public String getTouch_type() {
	        return touch_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTouch_type(java.lang.String)
		 */
	    @Override
		public void setTouch_type(String touch_type) {
	        this.touch_type = touch_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTouch_type_name()
		 */
	    @Override
		public String getTouch_type_name() {
	        return touch_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTouch_type_name(java.lang.String)
		 */
	    @Override
		public void setTouch_type_name(String touch_type_name) {
	        this.touch_type_name = touch_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTrans_flag()
		 */
	    @Override
		public java.lang.String getTrans_flag() {
	        return trans_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTrans_flag(java.lang.String)
		 */
	    @Override
		public void setTrans_flag(java.lang.String trans_flag) {
	        this.trans_flag = trans_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getTrans_flag_name()
		 */
	    @Override
		public java.lang.String getTrans_flag_name() {
	        return trans_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setTrans_flag_name(java.lang.String)
		 */
	    @Override
		public void setTrans_flag_name(java.lang.String trans_flag_name) {
	        this.trans_flag_name = trans_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getValid_period()
		 */
	    @Override
		public java.lang.Integer getValid_period() {
	        return valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setValid_period(java.lang.Integer)
		 */
	    @Override
		public void setValid_period(java.lang.Integer valid_period) {
	        this.valid_period = valid_period;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr__post()
		 */
	    @Override
		public String getWtr__post() {
	        return wtr__post;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr__post(java.lang.String)
		 */
	    @Override
		public void setWtr__post(String wtr__post) {
	        this.wtr__post = wtr__post;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_address()
		 */
	    @Override
		public String getWtr_address() {
	        return wtr_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_address(java.lang.String)
		 */
	    @Override
		public void setWtr_address(String wtr_address) {
	        this.wtr_address = wtr_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_card_type_name()
		 */
	    @Override
		public String getWtr_card_type_name() {
	        return wtr_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_card_type_name(java.lang.String)
		 */
	    @Override
		public void setWtr_card_type_name(String wtr_card_type_name) {
	        this.wtr_card_type_name = wtr_card_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_contact_methed()
		 */
	    @Override
		public String getWtr_contact_methed() {
	        return wtr_contact_methed;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_contact_methed(java.lang.String)
		 */
	    @Override
		public void setWtr_contact_methed(String wtr_contact_methed) {
	        this.wtr_contact_methed = wtr_contact_methed;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_cust_type_name()
		 */
	    @Override
		public String getWtr_cust_type_name() {
	        return wtr_cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_cust_type_name(java.lang.String)
		 */
	    @Override
		public void setWtr_cust_type_name(String wtr_cust_type_name) {
	        this.wtr_cust_type_name = wtr_cust_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_Email()
		 */
	    @Override
		public String getWtr_Email() {
	        return wtr_Email;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_Email(java.lang.String)
		 */
	    @Override
		public void setWtr_Email(String wtr_Email) {
	        this.wtr_Email = wtr_Email;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_handset_number()
		 */
	    @Override
		public String getWtr_handset_number() {
	        return wtr_handset_number;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_handset_number(java.lang.String)
		 */
	    @Override
		public void setWtr_handset_number(String wtr_handset_number) {
	        this.wtr_handset_number = wtr_handset_number;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_lpr()
		 */
	    @Override
		public String getWtr_lpr() {
	        return wtr_lpr;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_lpr(java.lang.String)
		 */
	    @Override
		public void setWtr_lpr(String wtr_lpr) {
	        this.wtr_lpr = wtr_lpr;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWtr_telephone()
		 */
	    @Override
		public String getWtr_telephone() {
	        return wtr_telephone;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWtr_telephone(java.lang.String)
		 */
	    @Override
		public void setWtr_telephone(String wtr_telephone) {
	        this.wtr_telephone = wtr_telephone;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getZj_check_flag()
		 */
	    @Override
		public java.lang.Integer getZj_check_flag() {
	        return zj_check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setZj_check_flag(java.lang.Integer)
		 */
	    @Override
		public void setZj_check_flag(java.lang.Integer zj_check_flag) {
	        this.zj_check_flag = zj_check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getZj_check_man()
		 */
	    @Override
		public java.lang.Integer getZj_check_man() {
	        return zj_check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setZj_check_man(java.lang.Integer)
		 */
	    @Override
		public void setZj_check_man(java.lang.Integer zj_check_man) {
	        this.zj_check_man = zj_check_man;
	    }
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWith_bank_flag()
		 */
		@Override
		public Integer getWith_bank_flag() {
			return with_bank_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWith_bank_flag(java.lang.Integer)
		 */
		@Override
		public void setWith_bank_flag(Integer with_bank_flag) {
			this.with_bank_flag = with_bank_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getHt_bank_id()
		 */
		@Override
		public String getHt_bank_id() {
			return ht_bank_id;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setHt_bank_id(java.lang.String)
		 */
		@Override
		public void setHt_bank_id(String ht_bank_id) {
			this.ht_bank_id = ht_bank_id;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getHt_bank_sub_name()
		 */
		@Override
		public String getHt_bank_sub_name() {
			return ht_bank_sub_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setHt_bank_sub_name(java.lang.String)
		 */
		@Override
		public void setHt_bank_sub_name(String ht_bank_sub_name) {
			this.ht_bank_sub_name = ht_bank_sub_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getHt_bank_name()
		 */
		@Override
		public String getHt_bank_name() {
			return ht_bank_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setHt_bank_name(java.lang.String)
		 */
		@Override
		public void setHt_bank_name(String ht_bank_name) {
			this.ht_bank_name = ht_bank_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWith_private_flag()
		 */
		@Override
		public Integer getWith_private_flag() {
			return with_private_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWith_private_flag(java.lang.Integer)
		 */
		@Override
		public void setWith_private_flag(Integer with_private_flag) {
			this.with_private_flag = with_private_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getWith_security_flag()
		 */
		@Override
		public Integer getWith_security_flag() {
			return with_security_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setWith_security_flag(java.lang.Integer)
		 */
		@Override
		public void setWith_security_flag(Integer with_security_flag) {
			this.with_security_flag = with_security_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getMax_buy_limit()
		 */
		@Override
		public BigDecimal getMax_buy_limit() {
			return max_buy_limit;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setMax_buy_limit(java.math.BigDecimal)
		 */
		@Override
		public void setMax_buy_limit(BigDecimal max_buy_limit) {
			this.max_buy_limit = max_buy_limit;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getMin_buy_limit()
		 */
		@Override
		public BigDecimal getMin_buy_limit() {
			return min_buy_limit;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setMin_buy_limit(java.math.BigDecimal)
		 */
		@Override
		public void setMin_buy_limit(BigDecimal min_buy_limit) {
			this.min_buy_limit = min_buy_limit;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getBonus_rate()
		 */
		@Override
		public BigDecimal getBonus_rate() {
			return bonus_rate;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setBonus_rate(java.math.BigDecimal)
		 */
		@Override
		public void setBonus_rate(BigDecimal bonus_rate) {
			this.bonus_rate = bonus_rate;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProv_level_name()
		 */
		@Override
		public String getProv_level_name() {
			return prov_level_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProv_level_name(java.lang.String)
		 */
		@Override
		public void setProv_level_name(String prov_level_name) {
			this.prov_level_name = prov_level_name;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#getProv_flag()
		 */
		@Override
		public Integer getProv_flag() {
			return prov_flag;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustContractLocal#setProv_flag(java.lang.Integer)
		 */
		@Override
		public void setProv_flag(Integer prov_flag) {
			this.prov_flag = prov_flag;
		}
}