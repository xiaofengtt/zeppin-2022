package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
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
import sun.jdbc.rowset.CachedRowSet;

@Component(value="intrustProduct")
@Scope("prototype")
public class IntrustProductBean extends IntrustBusiBean implements IntrustProductLocal {

	private java.lang.Integer serial_no;

    private java.lang.String city_name;

    private java.lang.Integer book_code;

    private java.lang.Integer product_id;

    private java.lang.String product_code;

    private java.lang.String product_name;

    private java.lang.String product_jc;

    private java.lang.Integer item_id;

    private java.lang.String currency_id;

    private java.lang.Integer pre_num;

    private java.math.BigDecimal pre_money;

    private java.lang.Integer pre_start_date;

    private java.lang.Integer pre_end_date;

    private java.math.BigDecimal pre_max_rate;

    private java.lang.Integer pre_max_num;

    private java.math.BigDecimal pre_max_money;

    private java.lang.Integer start_date;

    private java.lang.Integer end_date;

    private java.lang.Integer start_date2;

    private java.lang.Integer end_date2;

    private java.lang.Integer valid_period;

    private java.lang.String pre_code;

    private java.lang.String product_status;

    private java.lang.String product_status_name;

    private java.lang.Integer admin_manager;

    private java.lang.Integer info_period;

    private java.lang.Integer open_flag;

    private java.lang.String open_flag_name;

    private java.lang.String tg_bank_id;

    private java.lang.String tg_bank_name;

    private java.lang.String tg_bank_acct;

    private java.lang.Integer extend_flag;

    private java.lang.String intrust_type;

    private java.lang.String intrust_type_name;

    private java.math.BigDecimal fx_fee;

    private java.lang.Integer depart_id;
    
    private java.lang.String depart_name;

    private java.lang.String fpfs;

    private java.lang.String fpfs_name;

    private java.math.BigDecimal manage_fee;

    private java.math.BigDecimal manage_rate;

    private java.math.BigDecimal exp_rate1;

    private java.math.BigDecimal exp_rate2;

    private java.lang.String summary;

    private java.lang.Integer fact_pre_num;

    private java.math.BigDecimal fact_pre_money;

    private java.lang.Integer fact_num;

    private java.lang.Integer fact_person_num;

    private java.math.BigDecimal fact_money;

    private java.math.BigDecimal total_money;

    private java.math.BigDecimal total_amount;

    private java.math.BigDecimal nav_price;

    private java.math.BigDecimal zjye;

    private java.lang.Integer input_man;

    private java.sql.Timestamp input_time;

    private java.lang.String item_code;

    private java.math.BigDecimal gain_money;

    private java.lang.Integer intrust_flag1;

    private java.lang.Integer intrust_flag2;

    private java.lang.String intrust_type1;

    private java.lang.String intrust_type1_name;

    private java.lang.String intrust_type2;

    private java.lang.String intrust_type2_name;

    private java.math.BigDecimal min_money;

    private BigDecimal tax_rate;

    private java.lang.Integer check_flag;

    private java.lang.Integer gr_count;

    private java.math.BigDecimal gr_money;

    private java.lang.Integer jg_count;

    private java.math.BigDecimal jg_money;

    private java.lang.String tg_bank_sub_name;

    private java.lang.String tg_bank_sub_id;

    private java.lang.Integer sub_check_flag;

    private Integer period_unit;

    //北国投20050111
    private java.lang.Integer ben_period;

    private java.lang.String prov_level;

    private java.lang.String prov_level_name;

    private java.math.BigDecimal ben_amount;

    private java.lang.Integer contract_num;

    private java.lang.Integer ben_num;

    private int ben_period_temp;

    private Integer end_flag;

    //新华

    private java.lang.Integer product_from;

    private java.lang.Integer last_post_date;

    private java.lang.Integer sl_flag;

    private java.lang.Integer all_flag;

    private java.lang.Integer flag; //1成立2不成立

    //	新华信托20051012
    private java.math.BigDecimal amount_min; //产品规模从

    private java.math.BigDecimal amount_max; //产品规模至

    private java.lang.Integer trade_date; //时间

    private java.lang.String trade_type; //类型

    private java.lang.String trade_type_name;

    private java.lang.String description;

    private java.math.BigDecimal trade_rate;

    private java.lang.Integer admin_manager2; //时间

    private java.lang.Integer matain_manager; //时间

    private java.lang.Integer change_wt_flag; //时间

    private java.lang.Integer intrust_flag3; //时间

    private java.lang.Integer intrust_flag4; //时间

    private java.lang.String entity_type;

    private java.lang.String entity_type_name;

    private java.lang.String deal_type;

    private java.lang.String deal_type_name;

    //修改信息
    private java.lang.String field_name;

    private java.lang.String field_cn_name;

    private java.lang.String old_field_info;

    private java.lang.String new_field_info;

    private java.lang.String quality_level;

    private java.lang.String quality_level_name;

    private java.lang.String product_info;

    private String productstatusName;

    //	  add by jinxr 2007/4/26
    private String busi_name;

    private String contract_sub_bh;

    private String cust_name;

    private java.math.BigDecimal ht_money;

    private java.math.BigDecimal cw_money;

    private java.math.BigDecimal ben_money;

    private java.math.BigDecimal gr_amount;

    private java.math.BigDecimal jg_amount;

    private Integer valid_period2;

    private Integer sub_flag;

    /**
     * add by tsg 2007-11-13
     */

    private String DBdriver;

    //2008-07-30 YZJ
    private String bg_bank_id; //保管行ID

    private String bg_bank_name; //保管行名字

    private Integer old_end_date;

    private Integer new_end_date;

    private Integer business_end_flag;

    private Integer business_end_date;

    private Integer fact_end_date;

    //20090324 add by nizh 预计收益分配时间
    private Integer task_date;

    //	20090331 add by nizh 投向类型 贷款、投资、融资 合同录入的时候使用
    private String invest_type;

    //
    private String sAdmin_man;

    private String sAdmin_man2;

    private String sMatain_man;

    private BigDecimal balance_3101; //受托规模

    private BigDecimal balance_all; //资产

    private BigDecimal balance_lr; //利润

    private Integer gr_num; //个人数

    private Integer jg_num; //机构数

    private Integer time_flag; //参数类别: 1201(TINEEGERPARAM 参数)

    private Integer sub_man; //核算会计

    private String sub_man_name;

    private Integer busi_flag;

    private Integer op_code;

    private Timestamp trade_time;

    private Integer check_man;

    private Integer current_month; //当前会计周期

    private Integer hq_date;

    private BigDecimal nav_price1;

    private BigDecimal nav_price2;

    private String tg_acct_name;

    private String op_name;

    private BigDecimal curr_fact_money;

    private String intrust_flag1_name;

    private String[] total_fact_money;

    private java.math.BigDecimal busi_nav_price;

    private java.math.BigDecimal prov_level_a_money;//优先受益金额

    private java.math.BigDecimal prov_level_b_money;//一般受益金额

    private Integer org_count;//机构数量

    private Integer person_count;//个人数量
    
    private java.math.BigDecimal person_money;//个人认购金额 add by liug 20100817

    private java.math.BigDecimal org_money;//机构认购金额 add by liug 20100817
    
    private java.math.BigDecimal original_money; //产品初始募集金额 
    
    private Integer with_bank_flag;
    
    private java.math.BigDecimal qualified_count;//合格投资人数量
    
    private java.math.BigDecimal qualified_money;//合格投资人金额
    
    private java.math.BigDecimal qualified_amount;//合格投资人份额
	
    private java.math.BigDecimal min_buy_limit;//合同最低金额
    private java.math.BigDecimal max_buy_limit;//合同最高金额
    
    private Integer trust_fee_period;
	/**
     * 子产品ID
     */
    private Integer sub_product_id;

    /**
     * 期数
     */
    private Integer list_id;

    /**
     * 期数名称
     */
    private String list_name;

    /**
     * 起始收益率
     */
    private BigDecimal ext_rate1;

    /**
     * 截止收益率
     */
    private BigDecimal ext_rate2;

    /**
     * 净值精度
     */
    private Integer nav_float_num;

    private BigDecimal trade_tax_rate;//2009-12-09 ADD YZJ

    private BigDecimal fee;//2009-12-09 ADD YZJ

    /**
     * 管理类型
     */
    private String managetype_name;

    //国民 合同名称
    private String trust_contract_name;

    //20100308 lym
    private String bank_name;

    private String acct_name;

    private String bank_acct;

    private Integer manager_type;

    private BigDecimal result_standard;
    
    private Integer share_flag;
    
    private Integer coperate_type;// 合作方式：1:银信合作;2:证信合作;3:私募基金合作;4:信政合作
    
    private String gov_prov_pegional;
    private String gov_pegional;

    private String sub_product_code;
    
    private Integer sub_fund_flag;//资金独立运用标志 0否1是
    
    private Integer prov_flag;//优先受益标志:1优先 2一般 3劣后
    
    private Integer deal_flag;
    
    //-------- 2011-01-20 qmh add----
    
	private Integer qualified_flag ;
	
	private Integer qualified_num ;//合同份数
	
	private Integer asfund_flag ;
    
    private static final String listSql2 = "{call SP_QUERY_TPRODUCT_HTTOTAL (?,?,?,?,?,?,?)}";

    private static final String querySelf = "{call SP_QUERY_TPRODUCT_SELF (?,?,?)} ";

    private static final String updateSelf = "{?=call SP_MODI_TPRODUCT_FLAG2 (?,?,?,?,?)}";

    private static final String addSelf = "{?= call  SP_ADD_TPRODUCT_FLAG2(?,?,?,?,?,?)}";


    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listSubProduct()
	 */
    @Override
	public void listSubProduct() throws Exception {
        Object[] params = new Object[6];
        params[0] = Utility.parseInt(product_id, new Integer(0));
        params[1] = Utility.parseInt(sub_product_id, new Integer(0));
        params[2] = Utility.parseInt(list_id, new Integer(0));
        params[3] = Utility.trimNull(list_name);
        params[4] = Utility.trimNull(product_code);
        params[5] = Utility.trimNull(product_name);
        super.query("{CALL SP_QUERY_TSUBPRODUCT(?,?,?,?,?,?)}", params);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextSubProduct()
	 */
    @Override
	public boolean getNextSubProduct() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = Utility.parseInt(new Integer(rowset
                    .getInt("PRODUCT_ID")), new Integer(0));
            sub_product_id = Utility.parseInt(new Integer(rowset
                    .getInt("SUB_PRODUCT_ID")), new Integer(0));
            list_id = Utility.parseInt(new Integer(rowset.getInt("LIST_ID")),
                    new Integer(0));
            list_name = Utility.trimNull(rowset.getString("LIST_NAME"));
            prov_level = Utility.trimNull(rowset.getString("PROV_LEVEL"));
            prov_level_name = Utility.trimNull(rowset
                    .getString("PROV_LEVEL_NAME"));
            pre_num = Utility.parseInt(new Integer(rowset.getInt("PRE_NUM")),
                    new Integer(0));
            pre_money = Utility.parseBigDecimal(rowset
                    .getBigDecimal("PRE_MONEY"), new BigDecimal(0));
            min_money = Utility.parseBigDecimal(rowset
                    .getBigDecimal("MIN_MONEY"), new BigDecimal(0));
            exp_rate1 = Utility.parseBigDecimal(rowset
                    .getBigDecimal("EXP_RATE1"), new BigDecimal(0));
            exp_rate2 = Utility.parseBigDecimal(rowset
                    .getBigDecimal("EXP_RATE2"), new BigDecimal(0));
            start_date = Utility.parseInt(new Integer(rowset
                    .getInt("START_DATE")), new Integer(0));
            end_date = Utility.parseInt(new Integer(rowset.getInt("END_DATE")),
                    new Integer(0));
            fact_end_date = Utility.parseInt(new Integer(rowset
                    .getInt("FACT_END_DATE")), new Integer(0));
            valid_period = Utility.parseInt(new Integer(rowset
                    .getInt("VALID_PERIOD")), new Integer(0));
            period_unit = Utility.parseInt(new Integer(rowset
                    .getInt("PERIOD_UNIT")), new Integer(0));
            check_flag = Utility.parseInt(new Integer(rowset
                    .getInt("CHECK_FLAG")), new Integer(0));
            start_date = Utility.parseInt(new Integer(rowset
                    .getInt("START_DATE")), new Integer(0));
            result_standard = Utility.parseBigDecimal(rowset
                    .getBigDecimal("RESULT_STANDARD"), new BigDecimal(0));
            product_name = rowset.getString("PRODUCT_NAME");
            period_unit = Utility.parseInt(Utility.trimNull(rowset
                    .getString("PERIOD_UNIT")), new Integer(0));
            //sub_product_code = rowset.getString("SUBPRODUCT_CODE");
            prov_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("PROV_FLAG")),new Integer(1));
            min_buy_limit = Utility.parseBigDecimal(rowset.getBigDecimal("MIN_BUY_LIMIT"), new BigDecimal(0));
            max_buy_limit = Utility.parseBigDecimal(rowset.getBigDecimal("MAX_BUY_LIMIT"), new BigDecimal(0));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#addSubProduct()
	 */
    @Override
	public void addSubProduct() throws BusiException {
        Object[] param = new Object[16];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.trimNull(list_name);
        param[2] = Utility.trimNull(prov_level);
        param[3] = Utility.trimNull(pre_num);
        param[4] = Utility.parseBigDecimal(pre_money, new BigDecimal(0));
        param[5] = Utility.parseBigDecimal(min_money, new BigDecimal(0));
        param[6] = Utility.parseBigDecimal(exp_rate1, new BigDecimal(0));
        param[7] = Utility.parseBigDecimal(exp_rate2, new BigDecimal(0));
        param[8] = Utility.parseInt(valid_period, new Integer(0));
        param[9] = Utility.parseInt(period_unit, new Integer(0));
        param[10] = Utility.parseInt(input_man, new Integer(0));
        param[11] = Utility.parseBigDecimal(result_standard, new BigDecimal(0));
        param[12] = Utility.parseInt(start_date, new Integer(0));
        param[13] = Utility.parseInt(prov_flag, new Integer(0));
        param[14] = Utility.parseBigDecimal(min_buy_limit, new BigDecimal(0));
        param[15] = Utility.parseBigDecimal(max_buy_limit, new BigDecimal(0));
        try {
            super.append(
                    "{?=CALL SP_ADD_TSUBPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    param);
        } catch (Exception e) {
            throw new BusiException("新增子产品失败:" + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#modiSubProduct()
	 */
    @Override
	public void modiSubProduct() throws BusiException {
		Object[] param = new Object[16];
		param[0] = Utility.parseInt(sub_product_id, new Integer(0));
		param[1] = Utility.trimNull(list_name);
		param[2] = Utility.trimNull(prov_level);
		param[3] = Utility.trimNull(pre_num);
		param[4] = Utility.parseBigDecimal(pre_money, new BigDecimal(0));
		param[5] = Utility.parseBigDecimal(min_money, new BigDecimal(0));
		param[6] = Utility.parseBigDecimal(exp_rate1, new BigDecimal(0));
		param[7] = Utility.parseBigDecimal(exp_rate2, new BigDecimal(0));
		param[8] = Utility.parseInt(valid_period, new Integer(0));
		param[9] = Utility.parseInt(period_unit, new Integer(0));
		param[10] = Utility.parseInt(input_man, new Integer(0));
		param[11] = Utility.parseBigDecimal(result_standard, new BigDecimal(0));
		param[12] = Utility.parseInt(start_date, new Integer(0));
		param[13] = Utility.parseInt(prov_flag, new Integer(0));
        param[14] = Utility.parseBigDecimal(min_buy_limit, new BigDecimal(0));
        param[15] = Utility.parseBigDecimal(max_buy_limit, new BigDecimal(0));
		try {
			super.cud(
				"{?=CALL SP_MODI_TSUBPRODUCT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
				param);
		} catch (Exception e) {
			throw new BusiException("修改子产品失败:" + e.getMessage());
		}

	}

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#delSubProduct()
	 */
    @Override
	public void delSubProduct() throws BusiException {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(sub_product_id, new Integer(0));
        param[1] = Utility.parseInt(input_man, new Integer(0));
        try {
            super.delete("{?=CALL SP_DEL_TSUBPRODUCT(?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("子产品信息失败" + e.getMessage());
        }

    }

    protected void validate() throws BusiException {
        super.validate();

        if (product_name == null || product_name.equals(""))
            throw new BusiException("产品名称不合法.");
        if (product_jc == null || product_jc.equals(""))
            throw new BusiException("产品简称不合法.");
        if (item_id == null || item_id.equals(""))
            throw new BusiException("项目名称不合法.");
        if (pre_code.length() > 10)
            throw new BusiException("合同编号前缀不合法.");
        if (product_status.equals("") || product_status.length() > 10)
            throw new BusiException("产品状态不合法.");
        if (tg_bank_id.equals(""))
            throw new BusiException("资金托管银行不合法.");
        if (tg_bank_acct.length() > 40)
            throw new BusiException("资金托管账户不合法.");
        if (fpfs.equals(""))
            throw new BusiException("管理费分配方式不合法.");
        if (summary.length() > 400)
            throw new BusiException("备注信息不合法.");
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#appendSelf()
	 */
    @Override
	public void appendSelf() throws BusiException {
        Object[] param = new Object[5];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = this.product_name;
        param[2] = this.currency_id;
        param[3] = Utility.parseInt(this.start_date, new Integer(0));
        param[4] = Utility.parseInt(this.input_man, new Integer(0));

        try {
            product_id = (Integer) super.cudEx(addSelf, param, 7,
                    java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("添加自营产品失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#updateSelf()
	 */
    @Override
	public void updateSelf() throws BusiException {

        Object[] param = new Object[5];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = this.product_name;
        param[2] = this.currency_id;
        param[3] = Utility.parseInt(this.start_date, new Integer(0));
        param[4] = Utility.parseInt(this.input_man, new Integer(0));
        try {
            super.update(updateSelf, param);
        } catch (Exception e) {
            throw new BusiException("修改自营产品失败！" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#loadSelf()
	 */
    @Override
	public void loadSelf() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(this.book_code, new Integer(0));
        param[1] = this.product_name;
        param[2] = Utility.parseInt(this.input_man, new Integer(0));
        super.query(querySelf, param);
        this.getNextSelf();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#querySelf(java.lang.String)
	 */
    @Override
	public void querySelf(String sQuery) throws Exception {
        Object[] param = new Object[3];
        param[0] = this.book_code;
        param[1] = sQuery;
        param[2] = this.input_man;
        super.query(querySelf, param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listSelf()
	 */
    @Override
	public void listSelf() throws Exception {
        this.querySelf("");
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextSelf()
	 */
    @Override
	public boolean getNextSelf() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.book_code = new Integer(rowset.getInt("book_code"));
            this.product_code = rowset.getString("Product_code");
            this.product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            this.product_name = rowset.getString("PRODUCT_NAME");
            this.start_date = new Integer(rowset.getInt("START_DATE"));
            this.product_status = rowset.getString("PRODUCT_STATUS");
            this.product_status_name = rowset.getString("PRODUCT_STATUS_NAME");
            this.currency_id = rowset.getString("CURRENCY_ID");
            this.check_flag = new Integer(rowset.getInt("check_flag"));
        }

        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#append()
	 */
    @Override
	public void append() throws BusiException {
        Object[] oparams = new Object[43];
        oparams[0] = book_code;
        oparams[1] = product_name;
        oparams[2] = product_jc;
        oparams[3] = item_id;
        oparams[4] = pre_num;
        oparams[5] = pre_money;
        oparams[6] = min_money;
        if (pre_max_rate != null)
            oparams[7] = pre_max_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[7] = new BigDecimal(0);
        oparams[8] = pre_start_date;
        oparams[9] = pre_end_date;
        oparams[10] = valid_period;
        oparams[11] = pre_code;
        oparams[12] = info_period;
        oparams[13] = open_flag;
        oparams[14] = tg_bank_id;
        oparams[15] = tg_bank_acct;
        oparams[16] = extend_flag;
        oparams[17] = depart_id;
        oparams[18] = fpfs;
        oparams[19] = manage_fee;
        if (manage_rate != null)
            oparams[20] = manage_rate.multiply(new BigDecimal(0.01)).setScale(6, 1);
        else
            oparams[20] = new BigDecimal(0);
        oparams[21] = summary;
        oparams[22] = input_man;
        if (tax_rate != null)
            oparams[23] = tax_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[23] = new BigDecimal(0);
        oparams[24] = tg_bank_sub_id;
        oparams[25] = Utility.parseInt(ben_period, 0);
        oparams[26] = product_from;
        oparams[27] = period_unit;
        oparams[28] = Utility.parseInt(admin_manager, 0);
        oparams[29] = Utility.parseInt(admin_manager2, 0);
        oparams[30] = Utility.parseInt(matain_manager, 0);
        oparams[31] = Utility.parseInt(change_wt_flag, 0);
        oparams[32] = bg_bank_id;
        oparams[33] = check_man;
        oparams[34] = Utility.parseInt(end_date, null);
        oparams[35] = tg_acct_name;
        oparams[36] = sub_flag;
        oparams[37] = nav_float_num;
        oparams[38] = Utility.parseBigDecimal(trade_tax_rate, new BigDecimal(0));
        oparams[39] = trust_contract_name;
        oparams[40] = Utility.parseInt(share_flag,new Integer(0));
        oparams[41] = Utility.parseInt(sub_fund_flag,new Integer(0));
        oparams[42] = Utility.parseInt(trust_fee_period,new Integer(0));
        try {
            product_id = (Integer) super
                    .cudEx(
                            "{?=call SP_ADD_TPRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                            oparams, 35, java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("产品信息添加失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#save()
	 */
    @Override
	public void save() throws BusiException {
        Object[] oparams = new Object[43];
        oparams[0] = product_id;
        oparams[1] = product_name;
        oparams[2] = product_jc;
        oparams[3] = pre_num;
        oparams[4] = pre_money;
        oparams[5] = min_money;
        if (pre_max_rate != null)
            oparams[6] = pre_max_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[6] = new BigDecimal(0);
        oparams[7] = pre_start_date;
        oparams[8] = pre_end_date;
        oparams[9] = valid_period;
        oparams[10] = pre_code;
        oparams[11] = info_period;
        oparams[12] = open_flag;
        oparams[13] = tg_bank_id;
        oparams[14] = tg_bank_acct;
        oparams[15] = extend_flag;
        oparams[16] = depart_id;
        oparams[17] = fpfs;
        oparams[18] = manage_fee;
        if (manage_rate != null)
            oparams[19] = manage_rate.multiply(new BigDecimal(0.01)).setScale(6, 1);
        else
            oparams[19] = new BigDecimal(0);
        oparams[20] = summary;
        oparams[21] = input_man;
        if (tax_rate != null)
            oparams[22] = tax_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[22] = new BigDecimal(0);
        oparams[23] = tg_bank_sub_id;
        oparams[24] = Utility.parseInt(ben_period, 0);
        oparams[25] = product_from;
        oparams[26] = period_unit;
        oparams[27] = Utility.parseInt(admin_manager, 0);
        oparams[28] = Utility.parseInt(admin_manager2, 0);
        oparams[29] = Utility.parseInt(matain_manager, 0);
        oparams[30] = Utility.parseInt(change_wt_flag, 0);
        oparams[31] = Utility.parseInt(last_post_date, 0);
        oparams[32] = bg_bank_id;
        oparams[33] = check_man;
        oparams[34] = Utility.parseInt(end_date, null);
        oparams[35] = tg_acct_name;
        oparams[36] = sub_flag;
        oparams[37] = nav_float_num;
        oparams[38] = Utility.parseBigDecimal(trade_tax_rate, new BigDecimal(0));
        oparams[39] = trust_contract_name;
        oparams[40] = Utility.parseInt(share_flag,new Integer(0));
        oparams[41] = Utility.parseInt(sub_fund_flag,new Integer(0));
        oparams[42] = Utility.parseInt(trust_fee_period,new Integer(0));
        try {
            super.update(
                            "{?=call SP_MODI_TPRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                            oparams);
        } catch (Exception e) {
            throw new BusiException("产品信息保存失败: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#save1()
	 */
    @Override
	public void save1() throws BusiException {
        Object[] oparams = new Object[43];
        oparams[0] = product_id;
        oparams[1] = product_name;
        oparams[2] = product_jc;
        oparams[3] = pre_num;
        oparams[4] = pre_money;
        oparams[5] = min_money;
        if (pre_max_rate != null)
            oparams[6] = pre_max_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[6] = new BigDecimal(0);
        oparams[7] = pre_start_date;
        oparams[8] = pre_end_date;
        oparams[9] = valid_period;
        oparams[10] = pre_code;
        oparams[11] = info_period;
        oparams[12] = open_flag;
        oparams[13] = tg_bank_id;
        oparams[14] = tg_bank_acct;
        oparams[15] = extend_flag;
        oparams[16] = depart_id;
        oparams[17] = fpfs;
        oparams[18] = manage_fee;
        if (manage_rate != null)
            oparams[19] = manage_rate.multiply(new BigDecimal(0.01)).setScale(6, 1);
        else
            oparams[19] = new BigDecimal(0);
        oparams[20] = summary;
        oparams[21] = input_man;
        if (tax_rate != null)
            oparams[22] = tax_rate.multiply(new BigDecimal(0.01)).setScale(4, 1);
        else
            oparams[22] = new BigDecimal(0);
        oparams[23] = tg_bank_sub_id;
        oparams[24] = Utility.parseInt(ben_period, 0);
        oparams[25] = product_from;
        oparams[26] = period_unit;
        oparams[27] = Utility.parseInt(admin_manager, 0);
        oparams[28] = Utility.parseInt(admin_manager2, 0);
        oparams[29] = Utility.parseInt(matain_manager, 0);
        oparams[30] = Utility.parseInt(change_wt_flag, 0);
        oparams[31] = Utility.parseInt(last_post_date, 0);
        oparams[32] = bg_bank_id;
        oparams[33] = check_man;
        oparams[34] = Utility.parseInt(end_date, null);
        oparams[35] = tg_acct_name;
        oparams[36] = sub_flag;
        oparams[37] = nav_float_num;
        oparams[38] = Utility.parseBigDecimal(trade_tax_rate, new BigDecimal(0));
        oparams[39] = trust_contract_name;
        oparams[40] = Utility.parseInt(share_flag,new Integer(0));
        oparams[41] = Utility.parseInt(sub_fund_flag,new Integer(0));
        oparams[42] = Utility.parseInt(trust_fee_period,new Integer(0));
        try {
            super.update(
                            "{?=call SP_MODI_TPRODUCT1 (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                            oparams);
        } catch (Exception e) {
            throw new BusiException("产品信息保存失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#delete()
	 */
    @Override
	public void delete() throws BusiException {

        try {
            Object[] param = new Object[2];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));

            super.delete("{?=call SP_DEL_TPRODUCT (?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品信息删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#checkProduct()
	 */
    @Override
	public void checkProduct() throws BusiException {

        try {
            Object[] param = new Object[4];
            param[0] = Utility.parseInt(book_code, new Integer(0));
            param[1] = Utility.parseInt(product_id, new Integer(0));
            param[2] = Utility.parseInt(check_flag, new Integer(0));
            param[3] = Utility.parseInt(input_man, new Integer(0));

            super.cud("{?=call SP_CHECK_TPRODUCT(?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品信息审核失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#uncheckProduct()
	 */
    @Override
	public void uncheckProduct() throws Exception {

        Object[] param = new Object[5];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = product_code;
        param[2] = product_name;
        param[3] = Utility.parseInt(input_man, new Integer(0));
        param[4] = check_flag;//20100825

        super.query("{call SP_QUERY_TPRODUCT_UNCHECK(?,?,?,?,?)}", param);

    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#uncheckProductDetailList()
	 */
    @Override
	public void uncheckProductDetailList() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = product_id;
        param[2] = Utility.parseInt(deal_flag, new Integer(1));
        super.query("{call SP_QUERY_TPRODUCTDETAIL1(?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#updatePeriod()
	 */
    @Override
	public void updatePeriod() throws BusiException {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(end_date, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_MODI_TPRODUCT_PERIOD(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("信托期限展期失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#updateLevel()
	 */
    @Override
	public void updateLevel() throws BusiException {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = quality_level;
            param[2] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_MODI_TPRODUCT_QUALITY(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("信托等级失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#load()
	 */
    @Override
	public void load() throws Exception {
        this.listall();
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            min_money = rowset.getBigDecimal("min_money");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            period_unit = (Integer) rowset.getObject("period_unit");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            depart_name = rowset.getString("DEPART_NAME");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            tg_bank_sub_id = rowset.getString("tg_bank_sub_id");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            admin_manager2 = (Integer) rowset.getObject("ADMIN_MANAGER2");
            matain_manager = (Integer) rowset.getObject("MATAIN_MANAGER");
            change_wt_flag = (Integer) rowset.getObject("CHANGE_WT_FLAG");
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            intrust_flag2 = (Integer) rowset.getObject("INTRUST_FLAG2");
            intrust_type1 = rowset.getString("intrust_type1");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            intrust_type2_name = rowset.getString("intrust_type2_name");
            intrust_flag3 = (Integer) rowset.getObject("INTRUST_FLAG3");
            intrust_flag4 = (Integer) rowset.getObject("INTRUST_FLAG4");
            entity_type = rowset.getString("entity_type");
            entity_type_name = rowset.getString("entity_type_name");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");

            gain_money = rowset.getBigDecimal("gain_money");
            ben_period = (Integer) rowset.getObject("ben_period");
            if (DBdriver != null && DBdriver.equals("2"))
                product_info = Utility.getClobtoString(rowset.getClob("PRODUCT_INFO"));
            else
                product_info = rowset.getString("PRODUCT_INFO");
            bg_bank_id = rowset.getString("BG_BANK_ID");
            bg_bank_name = rowset.getString("BG_BANK_NAME");
            sub_man = (Integer) rowset.getObject("SUB_MAN");
            check_man = (Integer) rowset.getObject("CHECK_MAN");
            tg_acct_name = rowset.getString("TG_ACCT_NAME");
            sub_flag = rowset.getBoolean("SUB_FLAG") ? new Integer(1): new Integer(0);
            check_flag = Utility.parseInt(Utility.trimNull(rowset.getObject("CHECK_FLAG")), new Integer(0));
            nav_float_num = Utility.parseInt(Utility.trimNull(rowset.getObject("NAV_FLOAT_NUM")), new Integer(0));
            trade_tax_rate = rowset.getBigDecimal("TRADE_TAX_RATE");
            this.managetype_name = rowset.getString("MANAGERTYPE_NAME");
            this.trust_contract_name = rowset.getString("TRUST_CONTRACT_NAME");
            this.share_flag = (Integer)rowset.getObject("SHARE_FLAG");
            sub_fund_flag = rowset.getBoolean("SUB_FUND_FLAG") ? new Integer(1): new Integer(0);
            trust_fee_period = (Integer)rowset.getObject("TRUST_FEE_PERIOD");
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listall2()
	 */
    @Override
	public void listall2() throws Exception {

        Object[] param = new Object[1];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_ID_2 (?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#load2()
	 */
    @Override
	public void load2() throws Exception {
        this.listall2();
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            min_money = rowset.getBigDecimal("min_money");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            period_unit = (Integer) rowset.getObject("period_unit");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            tg_bank_sub_id = rowset.getString("tg_bank_sub_id");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            admin_manager2 = (Integer) rowset.getObject("ADMIN_MANAGER2");
            matain_manager = (Integer) rowset.getObject("MATAIN_MANAGER");
            change_wt_flag = (Integer) rowset.getObject("CHANGE_WT_FLAG");
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            intrust_flag2 = (Integer) rowset.getObject("INTRUST_FLAG2");
            intrust_type1 = rowset.getString("intrust_type1");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            intrust_type2_name = rowset.getString("intrust_type2_name");
            intrust_flag3 = (Integer) rowset.getObject("INTRUST_FLAG3");
            intrust_flag4 = (Integer) rowset.getObject("INTRUST_FLAG4");
            entity_type = rowset.getString("entity_type");
            entity_type_name = rowset.getString("entity_type_name");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");

            gain_money = rowset.getBigDecimal("gain_money");
            ben_period = (Integer) rowset.getObject("ben_period");
            if (DBdriver != null && DBdriver.equals("2"))
                product_info = Utility.getClobtoString(rowset
                        .getClob("PRODUCT_INFO"));
            else
                product_info = rowset.getString("PRODUCT_INFO");
            bg_bank_id = rowset.getString("BG_BANK_ID");
            bg_bank_name = rowset.getString("BG_BANK_NAME");
            sub_man = (Integer) rowset.getObject("SUB_MAN");
            check_man = (Integer) rowset.getObject("CHECK_MAN");
            tg_acct_name = rowset.getString("TG_ACCT_NAME");
            sub_flag = rowset.getBoolean("SUB_FLAG") ? new Integer(1)
                    : new Integer(0);
            check_flag = Utility.parseInt(Utility.trimNull(rowset
                    .getObject("CHECK_FLAG")), new Integer(0));
            nav_float_num = Utility.parseInt(Utility.trimNull(rowset
                    .getObject("NAV_FLOAT_NUM")), new Integer(0));
            trade_tax_rate = rowset.getBigDecimal("TRADE_TAX_RATE");
            original_money = rowset.getBigDecimal("ORIGINAL_MONEY");
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#saveAdmin_manager()
	 */
    @Override
	public void saveAdmin_manager() throws BusiException {
        try {
            Object[] param = new Object[6];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(admin_manager, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));
            param[3] = Utility.parseInt(admin_manager2, new Integer(0));
            param[4] = Utility.parseInt(matain_manager, new Integer(0));
            param[5] = Utility.parseInt(depart_id, new Integer(0));
            super.update("{?=call SP_MODI_TPRODUCT_ADMIN_MANAGER (?,?,?,?,?,?)}",
                    param);

        } catch (Exception e) {
            throw new BusiException("信托经理设置失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#cancel()
	 */
    @Override
	public void cancel() throws BusiException {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(end_date, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_CANCEL_TPRODUCT (?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品不成立失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#start()
	 */
    @Override
	public void start() throws BusiException {
        try {
            Object[] param = new Object[4];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(start_date, new Integer(0));
            param[2] = Utility.parseBigDecimal(fx_fee, new BigDecimal(0));
            param[3] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_START_TPRODUCT (?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品成立失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#disStart()
	 */
    @Override
	public void disStart() throws BusiException {
        int ret;
        try {

            Connection conn = IntrustDBManager.getConnection();
            CallableStatement stmt = conn.prepareCall("");
            stmt.registerOutParameter(1, java.sql.Types.INTEGER);
            stmt.setInt(2, product_id.intValue());
            stmt.setInt(3, start_date.intValue());
            stmt.setBigDecimal(4, fx_fee);
            stmt.setInt(5, input_man.intValue());
            stmt.executeUpdate();
            ret = stmt.getInt(1);
        } catch (Exception e) {
            throw new BusiException("产品成立失败: " + e.getMessage());
        }
        if (ret != 100) {
            throw new BusiException(ret);
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#list()
	 */
    @Override
	public void list() throws Exception {
        query(null);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#list1()
	 */
    @Override
	public void list1() throws BusiException {

        try {

            Connection conn = IntrustDBManager.getConnection();
            CallableStatement stmt = conn.prepareCall(listSql2,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, product_id == null ? 0 : product_id.intValue());
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.registerOutParameter(3, Types.VARCHAR);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.registerOutParameter(6, Types.INTEGER);
            stmt.registerOutParameter(7, Types.VARCHAR);
            stmt.execute();
            gr_count = new Integer(stmt.getInt(2));
            gr_money = stmt.getBigDecimal(3);
            jg_count = new Integer(stmt.getInt(4));
            jg_money = stmt.getBigDecimal(5);
            qualified_count = new BigDecimal(stmt.getInt(6));
            qualified_money = stmt.getBigDecimal(7);
        } catch (Exception e) {
            throw new BusiException("数据读取失败" + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listModiDetail1()
	 */
    @Override
	public void listModiDetail1() throws BusiException {
        try {
            Object[] param = new Object[3];
            param[0] = Utility.parseInt(book_code, new Integer(0));
            param[1] = Utility.parseInt(product_id, new Integer(0));
            param[2] = Utility.parseInt(deal_flag, new Integer(2));
            super.query("{call SP_QUERY_TPRODUCTDETAIL1(?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("产品修改信息查询失败" + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listModiDetail()
	 */
    @Override
	public void listModiDetail() throws BusiException {
        try {
            Object[] param = new Object[2];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(check_flag, new Integer(0));
            super.query("{call SP_QUERY_TPRODUCTDETAIL(?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("产品修改信息查询失败" + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#checkModiDetail()
	 */
    @Override
	public void checkModiDetail() throws BusiException {
        try {
            Object[] param = new Object[2];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = Utility.parseInt(check_flag, new Integer(0));
            super.update("{?=call SP_CHECK_TPRODUCT1(?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("产品信息修改记录审核失败" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#beforeFirst()
	 */
    @Override
	public void beforeFirst() throws Exception {
        rowset.beforeFirst();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextModiDetail()
	 */
    @Override
	public boolean getNextModiDetail() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            field_name = rowset.getString("FIELD_NAME");
            field_cn_name = rowset.getString("FIELD_CN_NAME");
            old_field_info = rowset.getString("OLD_FIELD_INFO");
            new_field_info = rowset.getString("NEW_FIELD_INFO");
            input_man = new Integer(rowset.getInt("INPUT_MAN"));
            input_time = rowset.getTimestamp("INPUT_TIME");
            product_name = rowset.getString("PRODUCT_NAME");
            op_name = rowset.getString("OP_NAME");
            check_flag = Utility.parseInt(Utility.trimNull(rowset.getString("CHECK_FLAG")),new Integer(0));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNext(java.lang.String)
	 */
    @Override
	public boolean getNext(String DBdriver) throws Exception {
        boolean b = super.getNext();

        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            min_money = rowset.getBigDecimal("min_money");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            period_unit = (Integer) rowset.getObject("period_unit");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            admin_manager2 = (Integer) rowset.getObject("ADMIN_MANAGER2");
            matain_manager = (Integer) rowset.getObject("MATAIN_MANAGER");
            change_wt_flag = (Integer) rowset.getObject("CHANGE_WT_FLAG");
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            intrust_flag2 = (Integer) rowset.getObject("INTRUST_FLAG2");
            intrust_type1 = rowset.getString("intrust_type1");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            intrust_type2_name = rowset.getString("intrust_type2_name");
            intrust_flag3 = (Integer) rowset.getObject("INTRUST_FLAG3");
            intrust_flag4 = (Integer) rowset.getObject("INTRUST_FLAG4");
            entity_type = rowset.getString("entity_type");
            entity_type_name = rowset.getString("entity_type_name");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");

            if (DBdriver != null && DBdriver.equals("2"))
                product_info = Utility.getClobtoString(rowset
                        .getClob("PRODUCT_INFO"));
            else
                product_info = rowset.getString("PRODUCT_INFO");

            gain_money = rowset.getBigDecimal("gain_money");
            ben_period = (Integer) rowset.getObject("ben_period");
            business_end_flag = (Integer) rowset.getObject("BUSINESS_END_FLAG");
            business_end_date = (Integer) rowset.getObject("BUSINESS_END_DATE");
            fact_end_date = (Integer) rowset.getObject("FACT_END_DATE");
            check_man = Utility.parseInt(Utility.trimNull(rowset.getObject("CHECK_MAN")),new Integer(0));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextLevel(java.lang.String)
	 */
    @Override
	public boolean getNextLevel(String DBdriver) throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");

            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            period_unit = (Integer) rowset.getObject("period_unit");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            admin_manager2 = (Integer) rowset.getObject("ADMIN_MANAGER2");
            matain_manager = (Integer) rowset.getObject("MATAIN_MANAGER");
            change_wt_flag = (Integer) rowset.getObject("CHANGE_WT_FLAG");
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            intrust_flag2 = (Integer) rowset.getObject("INTRUST_FLAG2");
            intrust_type1 = rowset.getString("intrust_type1");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            intrust_type2_name = rowset.getString("intrust_type2_name");
            intrust_flag3 = (Integer) rowset.getObject("INTRUST_FLAG3");
            intrust_flag4 = (Integer) rowset.getObject("INTRUST_FLAG4");
            entity_type = rowset.getString("entity_type");
            entity_type_name = rowset.getString("entity_type_name");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");
            if (DBdriver != null && DBdriver.equals("2"))
                product_info = Utility.getClobtoString(rowset
                        .getClob("PRODUCT_INFO"));
            else
                product_info = rowset.getString("PRODUCT_INFO");

            gain_money = rowset.getBigDecimal("gain_money");
            ben_period = (Integer) rowset.getObject("ben_period");

            quality_level = rowset.getString("QUALITY_LEVEL");
            quality_level_name = rowset.getString("QUALITY_LEVEL_NAME");
            min_money = rowset.getBigDecimal("MIN_MONEY");
            trade_tax_rate = rowset.getBigDecimal("TRADE_TAX_RATE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextInfo2(java.lang.String)
	 */
    @Override
	public boolean getNextInfo2(String DBdriver) throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");

            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            period_unit = (Integer) rowset.getObject("period_unit");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            admin_manager2 = (Integer) rowset.getObject("ADMIN_MANAGER2");
            matain_manager = (Integer) rowset.getObject("MATAIN_MANAGER");
            change_wt_flag = (Integer) rowset.getObject("CHANGE_WT_FLAG");
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            intrust_flag2 = (Integer) rowset.getObject("INTRUST_FLAG2");
            intrust_type1 = rowset.getString("intrust_type1");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            intrust_type2_name = rowset.getString("intrust_type2_name");
            intrust_flag3 = (Integer) rowset.getObject("INTRUST_FLAG3");
            intrust_flag4 = (Integer) rowset.getObject("INTRUST_FLAG4");
            entity_type = rowset.getString("entity_type");
            entity_type_name = rowset.getString("entity_type_name");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");
            if (DBdriver != null && DBdriver.equals("2"))
                product_info = Utility.getClobtoString(rowset
                        .getClob("PRODUCT_INFO"));
            else
                product_info = rowset.getString("PRODUCT_INFO");

            gain_money = rowset.getBigDecimal("gain_money");
            ben_period = (Integer) rowset.getObject("ben_period");

            quality_level = rowset.getString("QUALITY_LEVEL");
            quality_level_name = rowset.getString("QUALITY_LEVEL_NAME");
            min_money = rowset.getBigDecimal("MIN_MONEY");
            trade_tax_rate = rowset.getBigDecimal("TRADE_TAX_RATE");
            this.managetype_name = rowset.getString("MANAGERTYPE_NAME");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNext_kemu()
	 */
    @Override
	public boolean getNext_kemu() throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
            period_unit = (Integer) rowset.getObject("period_unit");
            sub_man = (Integer) rowset.getObject("SUB_MAN");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextmore()
	 */
    @Override
	public boolean getNextmore() throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            check_flag = (Integer) rowset.getObject("CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextmore2()
	 */
    @Override
	public boolean getNextmore2() throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            sub_check_flag = (Integer) rowset.getObject("SUB_CHECK_FLAG");
            check_flag = (Integer) rowset.getObject("CHECK_FLAG");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            product_from = (Integer) rowset.getObject("IS_LOCAL");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextOnce()
	 */
    @Override
	public boolean getNextOnce() throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
            gr_count = (Integer) rowset.getObject("GR_NUM");
            gr_money = rowset.getBigDecimal("GR_MONEY");
            jg_count = (Integer) rowset.getObject("JG_NUM");
            jg_money = rowset.getBigDecimal("JG_MONEY");

            this.entity_type = rowset.getString("ENTITY_TYPE");
            this.entity_type_name = rowset.getString("ENTITY_TYPE_NAME");
            deal_type = rowset.getString("DEAL_TYPE");
            deal_type_name = rowset.getString("DEAL_TYPE_NAME");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextOnceAllList()
	 */
	@Override
	public List getNextOnceAllList() throws Exception {
		List list = new ArrayList();
		while (super.getNext()) {
			Map map = new HashMap();
			map.put("BOOK_CODE", rowset.getObject("BOOK_CODE"));
			map.put("PRODUCT_ID", rowset.getObject("PRODUCT_ID"));
			map.put("PRODUCT_CODE", rowset.getObject("PRODUCT_CODE"));
			map.put("PRODUCT_NAME", rowset.getObject("PRODUCT_NAME"));
			map.put("PRODUCT_JC", rowset.getObject("PRODUCT_JC"));
			map.put("ITEM_ID", rowset.getObject("ITEM_ID"));
			map.put("CURRENCY_ID", rowset.getObject("CURRENCY_ID"));
			map.put("PRE_NUM", rowset.getObject("PRE_NUM"));
			map.put("PRE_MONEY", rowset.getObject("PRE_MONEY"));
			map.put("PRE_START_DATE", rowset.getObject("PRE_START_DATE"));
			map.put("PRE_END_DATE", rowset.getObject("PRE_END_DATE"));
			map.put("PRE_MAX_RATE", rowset.getObject("PRE_MAX_RATE"));
			map.put("PRE_MAX_NUM", rowset.getObject("PRE_MAX_NUM"));
			map.put("PRE_MAX_MONEY", rowset.getObject("PRE_MAX_MONEY"));
			map.put("START_DATE", rowset.getObject("START_DATE"));
			map.put("END_DATE", rowset.getObject("END_DATE"));
			map.put("VALID_PERIOD", rowset.getObject("VALID_PERIOD"));
			map.put("PRE_CODE", rowset.getObject("PRE_CODE"));
			map.put("PRODUCT_STATUS", rowset.getObject("PRODUCT_STATUS"));
			map.put("PRODUCT_STATUS_NAME", rowset
					.getObject("PRODUCT_STATUS_NAME"));
			map.put("ADMIN_MANAGER", rowset.getObject("ADMIN_MANAGER"));
			map.put("INFO_PERIOD", rowset.getObject("INFO_PERIOD"));
			map.put("OPEN_FLAG", rowset.getObject("OPEN_FLAG"));
			map.put("OPEN_FLAG_NAME", rowset.getObject("OPEN_FLAG_NAME"));
			map.put("TG_BANK_ID", rowset.getObject("TG_BANK_ID"));
			map.put("TG_BANK_NAME", rowset.getObject("TG_BANK_NAME"));
			map.put("TG_BANK_ACCT", rowset.getObject("TG_BANK_ACCT"));
			map.put("EXTEND_FLAG", rowset.getObject("EXTEND_FLAG"));
			map.put("INTRUST_TYPE", rowset.getObject("INTRUST_TYPE"));
			map.put("INTRUST_TYPE_NAME", rowset.getObject("INTRUST_TYPE_NAME"));
			map.put("INTRUST_TYPE1_NAME", rowset
					.getObject("INTRUST_TYPE1_NAME"));
			map.put("FX_FEE", rowset.getObject("FX_FEE"));
			map.put("DEPART_ID", rowset.getObject("DEPART_ID"));
			map.put("FPFS", rowset.getObject("FPFS"));
			map.put("FPFS_NAME", rowset.getObject("FPFS_NAME"));
			map.put("MANAGE_FEE", rowset.getObject("MANAGE_FEE"));
			map.put("MANAGE_RATE", rowset.getObject("MANAGE_RATE"));
			map.put("EXP_RATE1", rowset.getObject("EXP_RATE1"));
			map.put("EXP_RATE2", rowset.getObject("EXP_RATE2"));
			map.put("SUMMARY", rowset.getObject("SUMMARY"));
			map.put("FACT_PRE_NUM", rowset.getObject("FACT_PRE_NUM"));
			map.put("FACT_PRE_MONEY", rowset.getObject("FACT_PRE_MONEY"));
			map.put("FACT_NUM", rowset.getObject("FACT_NUM"));
			map.put("FACT_PERSON_NUM", rowset.getObject("FACT_PERSON_NUM"));
			map.put("FACT_MONEY", rowset.getObject("FACT_MONEY"));
			map.put("TOTAL_MONEY", rowset.getObject("TOTAL_MONEY"));
			map.put("TOTAL_AMOUNT", rowset.getObject("TOTAL_AMOUNT"));
			map.put("NAV_PRICE", rowset.getObject("NAV_PRICE"));
			map.put("ZJYE", rowset.getObject("ZJYE"));
			map.put("INPUT_MAN", rowset.getObject("INPUT_MAN"));
			map.put("INPUT_TIME", rowset.getObject("INPUT_TIME"));
			map.put("ITEM_CODE", rowset.getObject("ITEM_CODE"));
			map.put("TAX_RATE", rowset.getObject("TAX_RATE"));
			map.put("TG_BANK_SUB_NAME", rowset.getObject("TG_BANK_SUB_NAME"));
			map.put("GR_NUM", rowset.getObject("GR_NUM"));
			map.put("GR_MONEY", rowset.getObject("GR_MONEY"));
			map.put("JG_NUM", rowset.getObject("JG_NUM"));
			map.put("JG_MONEY", rowset.getObject("JG_MONEY"));
			map.put("ENTITY_TYPE", rowset.getObject("ENTITY_TYPE"));
			map.put("ENTITY_TYPE_NAME", rowset.getObject("ENTITY_TYPE_NAME"));
			map.put("DEAL_TYPE", rowset.getObject("DEAL_TYPE"));
			map.put("DEAL_TYPE_NAME", rowset.getObject("DEAL_TYPE_NAME"));
			map.put("CURRENCY_NAME", rowset.getObject("CURRENCY_NAME"));
			map.put("BG_BANK_NAME", rowset.getObject("BG_BANK_NAME"));
			map.put("MANAGERTYPE", rowset.getObject("MANAGERTYPE"));
			map.put("NATRUST_TYPE_NAME", rowset.getObject("NATRUST_TYPE_NAME"));
			map.put("NATRUST_TYPE_SUB_NAME", rowset.getObject("NATRUST_TYPE_SUB_NAME"));
			map.put("ADMIN_MANAGER2", rowset.getObject("ADMIN_MANAGER2"));
			map.put("MATAIN_MANAGER", rowset.getObject("MATAIN_MANAGER"));
			map.put("INTRUST_FLAG3", rowset.getObject("INTRUST_FLAG3"));
			map.put("INTRUST_FLAG4", rowset.getObject("INTRUST_FLAG4"));
			map.put("ENTITY_TYPE_NAME", rowset.getObject("ENTITY_TYPE_NAME"));
			map.put("DEAL_TYPE_NAME", rowset.getObject("DEAL_TYPE_NAME"));
			map.put("PRODUCT_INFO", rowset.getObject("PRODUCT_INFO"));
			map.put("QUALITY_LEVEL_NAME", rowset.getObject("QUALITY_LEVEL_NAME"));
			map.put("BUSINESS_END_FLAG", rowset.getObject("BUSINESS_END_FLAG"));
			map.put("BUSINESS_END_DATE", rowset.getObject("BUSINESS_END_DATE"));
			map.put("TG_ACCT_NAME", rowset.getObject("TG_ACCT_NAME"));
			map.put("TRUST_CONTRACT_NAME", rowset.getObject("TRUST_CONTRACT_NAME"));
			map.put("LAST_GAIN_DATE", rowset.getObject("LAST_GAIN_DATE"));
			map.put("BEN_PERIOD", rowset.getObject("BEN_PERIOD"));
			map.put("CURRENT_MONTH", rowset.getObject("CURRENT_MONTH"));
			map.put("START_MONTH", rowset.getObject("START_MONTH"));
			map.put("LAST_POST_DATE", rowset.getObject("LAST_POST_DATE"));
			map.put("IS_LOCAL", rowset.getObject("IS_LOCAL"));
			map.put("CHECK_FLAG", rowset.getObject("CHECK_FLAG"));
			map.put("CHECK_MAN", rowset.getObject("CHECK_MAN"));
			map.put("GAIN_MONEY", rowset.getObject("GAIN_MONEY"));
			map.put("LIST_ID", rowset.getObject("LIST_ID"));
			map.put("INTRUST_TYPE2_NAME", rowset.getObject("INTRUST_TYPE2_NAME"));
			map.put("INTRUST_FLAG1", rowset.getObject("INTRUST_FLAG1"));
			map.put("INTRUST_FLAG2", rowset.getObject("INTRUST_FLAG2"));
			map.put("MIN_MONEY", rowset.getObject("MIN_MONEY"));
			map.put("PERIOD_UNIT", rowset.getObject("PERIOD_UNIT"));
			map.put("QUALIFIED_COUNT", rowset.getObject("QUALIFIED_COUNT"));
			map.put("QUALIFIED_MONEY", rowset.getObject("QUALIFIED_MONEY"));
			map.put("QUALIFIED_AMOUNT", rowset.getObject("QUALIFIED_AMOUNT"));

			list.add(map);
		}
		return list;
	}

    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextOnce1()
	 */
    @Override
	public boolean getNextOnce1() throws Exception {
        boolean b = super.getNext();
        if (b) {
            book_code = (Integer) rowset.getObject("book_code");
            product_id = (Integer) rowset.getObject("product_id");
            product_code = rowset.getString("product_code");
            product_name = rowset.getString("product_name");
            product_jc = rowset.getString("product_jc");
            item_id = (Integer) rowset.getObject("item_id");
            currency_id = rowset.getString("currency_id");
            pre_num = (Integer) rowset.getObject("pre_num");
            pre_money = rowset.getBigDecimal("pre_money");
            pre_start_date = (Integer) rowset.getObject("pre_start_date");
            pre_end_date = (Integer) rowset.getObject("pre_end_date");
            if (rowset.getBigDecimal("pre_max_rate") != null)
                pre_max_rate = rowset.getBigDecimal("pre_max_rate").multiply(
                        new BigDecimal(100));
            pre_max_num = (Integer) rowset.getObject("pre_max_num");
            pre_max_money = rowset.getBigDecimal("pre_max_money");
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");
            valid_period = (Integer) rowset.getObject("valid_period");
            pre_code = rowset.getString("pre_code");
            product_status = rowset.getString("product_status");
            product_status_name = rowset.getString("product_status_name");
            admin_manager = (Integer) rowset.getObject("admin_manager");
            info_period = (Integer) rowset.getObject("info_period");
            open_flag = (Integer) rowset.getObject("open_flag");
            open_flag_name = rowset.getString("open_flag_name");
            tg_bank_id = rowset.getString("tg_bank_id");
            tg_bank_name = rowset.getString("tg_bank_name");
            tg_bank_acct = rowset.getString("tg_bank_acct");
            extend_flag = (Integer) rowset.getObject("extend_flag");
            intrust_type = rowset.getString("intrust_type");
            intrust_type_name = rowset.getString("intrust_type_name");
            intrust_type1_name = rowset.getString("intrust_type1_name");
            fx_fee = rowset.getBigDecimal("fx_fee");
            depart_id = (Integer) rowset.getObject("depart_id");
            fpfs = rowset.getString("fpfs");
            fpfs_name = rowset.getString("fpfs_name");
            manage_fee = rowset.getBigDecimal("manage_fee");
            if (rowset.getBigDecimal("manage_rate") != null)
                manage_rate = rowset.getBigDecimal("manage_rate").multiply(
                        new BigDecimal(100));
            else
                manage_rate = rowset.getBigDecimal("manage_rate");
            exp_rate1 = rowset.getBigDecimal("exp_rate1");
            exp_rate2 = rowset.getBigDecimal("exp_rate2");
            summary = rowset.getString("summary");
            fact_pre_num = (Integer) rowset.getObject("fact_pre_num");
            fact_pre_money = rowset.getBigDecimal("fact_pre_money");
            fact_num = (Integer) rowset.getObject("fact_num");
            fact_person_num = (Integer) rowset.getObject("fact_person_num");
            fact_money = rowset.getBigDecimal("fact_money");
            total_money = rowset.getBigDecimal("total_money");
            total_amount = rowset.getBigDecimal("total_amount");
            nav_price = rowset.getBigDecimal("nav_price");
            zjye = rowset.getBigDecimal("zjye");
            input_man = (Integer) rowset.getObject("input_man");
            input_time = rowset.getTimestamp("input_time");
            item_code = rowset.getString("item_code");
            if (rowset.getBigDecimal("tax_rate") != null)
                tax_rate = rowset.getBigDecimal("tax_rate").multiply(
                        new BigDecimal(100));
            else
                tax_rate = rowset.getBigDecimal("tax_rate");
            tg_bank_sub_name = rowset.getString("tg_bank_sub_name");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#parseQuery(java.lang.String)
	 */
    @Override
	public String[] parseQuery(String srcQuery) {
        String intrust_type = "";
        String currency_id = "";
        String product_status = "";
        String product_code = "";
        String product_name = "";

        if ((srcQuery != null) && (!srcQuery.equals(""))) {
            int index = srcQuery.indexOf("$");
            intrust_type = srcQuery.substring(0, index);
            int index2 = srcQuery.indexOf("$", index + 1);
            currency_id = srcQuery.substring(index + 1, index2);
            int index3 = srcQuery.indexOf("$", index2 + 1);
            product_status = srcQuery.substring(index2 + 1, index3);
            int index4 = srcQuery.indexOf("$", index3 + 1);
            product_code = srcQuery.substring(index3 + 1, index4);
            product_name = srcQuery.substring(index4 + 1, srcQuery.length());
        }

        return (new String[] { intrust_type, currency_id, product_status,
                product_code, product_name });

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#query(java.lang.String)
	 */
    @Override
	public void query(String sQuery) throws Exception {
        String[] strQuery = parseQuery(sQuery);
        Object[] param = new Object[12];
        param[0] = book_code;
        param[1] = strQuery[3];
        param[2] = new Integer(0);
        param[3] = strQuery[2];
        param[4] = strQuery[0];
        param[5] = strQuery[1];
        param[6] = operator;
        param[7] = strQuery[4];
        param[8] = product_from;
        param[9] = depart_id;
        param[10] = Utility.parseInt(open_flag, 0);
        param[11] = intrust_flag1;//----2011-4-21 add---
        super.query("{call SP_QUERY_TPRODUCT_VALID(?,?,?,?,?,?,?,?,?,?,?,?)}",
                param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listPublishCheckProduct()
	 */
    @Override
	public void listPublishCheckProduct() throws Exception {
        Object[] param = new Object[6];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = product_code;
        param[3] = product_name;
        param[4] = Utility.parseInt(operator, new Integer(0));
        param[5] = Utility.parseInt(flag, new Integer(1));

        super.query("{call SP_QUERY_TPRODUCT_FOR_FINACHECK (?,?,?,?,?,?)}",
                param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#checkPublishProduct()
	 */
    @Override
	public void checkPublishProduct() throws BusiException {

        try {
            Object[] param = new Object[5];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(start_date, new Integer(0));
            param[2] = Utility.parseBigDecimal(fx_fee, new BigDecimal(0));
            param[3] = Utility.parseInt(input_man, new Integer(0));
            param[4] = Utility.parseInt(flag, new Integer(1));

            super.cud("{?=call SP_CHECK_START_TPRODUCT(?,?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品成立/不成立审核失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#unCheckPublishProduct()
	 */
    @Override
	public void unCheckPublishProduct() throws BusiException {
        try {
            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            param[2] = Utility.parseInt(flag, new Integer(0));

            super.cud("{?=call SP_UNCHECK_START_TPRODUCT(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品成立审核恢复失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#query2(java.lang.String)
	 */
    @Override
	public void query2(String item_id2) throws Exception {
        Object[] param = new Object[28];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = item_id2;
        param[2] = new Integer(0);
        param[3] = "110202";
        param[4] = null;
        param[5] = null;
        param[6] = Utility.parseInt(operator, new Integer(0));
        param[7] = product_name;
        param[8] = new Integer(0);
        param[9] = Utility.parseInt(intrust_flag1, new Integer(0));
        param[10] = Utility.parseInt(intrust_flag2, new Integer(0));
        param[11] = intrust_type1;
        param[12] = intrust_type2;
        param[13] = Utility.parseInt(start_date, new Integer(0));
        param[14] = Utility.parseInt(end_date, new Integer(0));
        param[15] = Utility.parseBigDecimal(amount_min, new BigDecimal(0));
        param[16] = Utility.parseBigDecimal(amount_max, new BigDecimal(0));
        param[17] = null;
        param[18] = null;

        param[19] = null;
        param[20] = null;
        param[21] = null;
        //param[22] = Utility.parseBigDecimal(fact_money,new BigDecimal(0));
        param[22] = Utility.parseInt(check_flag, new Integer(0));
        param[23] = null;
        param[24] = null;
        param[25] = null;
        param[26] = null;
        param[27] = null;
        super
                .query(
                        "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                        param);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#query_toreverse(java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public void query_toreverse(Integer book_code1,Integer input_man1) throws Exception {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(book_code1, new Integer(0));
        param[1] = Utility.parseInt(input_man1, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_SPECIAL_REVERSE (?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#start_reverse()
	 */
    @Override
	public void start_reverse() throws BusiException {
        try {
            Object[] param = new Object[2];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            super.cud("{?=call SP_START_TPRODUCT_SPECIAL_REVERSE(?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品特殊成立恢复恢复失败: " + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryright()
	 */
    @Override
	public void queryright() throws Exception {
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = product_code;
        param[2] = product_name;
        param[3] = Utility.parseInt(operator, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_RIGHT(?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextright()
	 */
    @Override
	public boolean getNextright() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            product_code = rowset.getString("PRODUCT_CODE");
            product_name = rowset.getString("PRODUCT_NAME");
            currency_id = rowset.getString("CURRENCY_ID");
            admin_manager = new Integer(rowset.getInt("ADMIN_MANAGER"));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listall()
	 */
    @Override
	public void listall() throws Exception {

        Object[] param = new Object[1];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_ID (?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextdate()
	 */
    @Override
	public boolean getNextdate() throws Exception {
        boolean b = super.getNext();
        if (b) {
            start_date = (Integer) rowset.getObject("start_date");
            end_date = (Integer) rowset.getObject("end_date");

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#query3(java.lang.String)
	 */
    @Override
	public void query3(String product_code2) throws Exception {
        Object[] param = new Object[29];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = product_code2;
        param[2] = Utility.parseInt(product_id, new Integer(0));
        param[3] = product_status;
        param[4] = intrust_type;
        param[5] = currency_id;
        param[6] = Utility.parseInt(operator, new Integer(0));
        param[7] = product_name;
        param[8] = Utility.parseInt(product_from, new Integer(0));
        param[9] = Utility.parseInt(intrust_flag1, new Integer(0));
        param[10] = Utility.parseInt(intrust_flag2, new Integer(0));
        param[11] = intrust_type1;
        param[12] = intrust_type2;
        param[13] = Utility.parseInt(start_date, new Integer(0));
        param[14] = Utility.parseInt(end_date, new Integer(0));
        param[15] = Utility.parseBigDecimal(amount_min, new BigDecimal(0));
        param[16] = Utility.parseBigDecimal(amount_max, new BigDecimal(0));
        param[17] = Utility.parseInt(admin_manager, new Integer(0));
        param[18] = Utility.parseInt(depart_id, new Integer(0));

        param[19] = Utility.parseInt(intrust_flag3, new Integer(0));
        param[20] = Utility.parseInt(intrust_flag4, new Integer(0));
        param[21] = Utility.parseBigDecimal(exp_rate1, new BigDecimal(0));
        //param[22] = Utility.parseBigDecimal(fact_money,new BigDecimal(0));
        param[22] = Utility.parseInt(check_flag, new Integer(0));
        param[23] = Utility.parseInt(valid_period, new Integer(0));
        param[24] = Utility.parseInt(valid_period2, new Integer(0));
        param[25] = Utility.trimNull(fpfs);
        param[26] = Utility.parseInt(open_flag, new Integer(0));
        param[27] = Utility.parseInt(this.manager_type, new Integer(0));
        param[28] = Utility.parseInt(coperate_type,new Integer(0));
        super
                .query(
                        "{call SP_QUERY_TPRODUCT (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                        param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#query4(java.lang.String)
	 */
    @Override
	public void query4(String product_code2) throws Exception {
        Object[] param = new Object[8];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = product_code2;
        param[2] = product_name;
        param[3] = Utility.parseInt(start_date, new Integer(0));
        param[4] = Utility.parseInt(end_date, new Integer(0));
        param[5] = Utility.parseInt(input_man, new Integer(0));
        param[6] = Utility.parseInt(start_date2, new Integer(0));
        param[7] = Utility.parseInt(end_date2, new Integer(0));

        super.query("{call SP_QUERY_TPRODUCT_DATE (?,?,?,?,?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryEnd()
	 */
    @Override
	public void queryEnd() throws Exception {
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(input_man, new Integer(0));
        param[2] = Utility.parseInt(product_id, new Integer(0));
        param[3] = product_name;

        super.query("{call SP_QUERY_TPRODUCT_END(?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryEndUnCheck()
	 */
    @Override
	public void queryEndUnCheck() throws Exception {
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(input_man, new Integer(0));
        param[2] = Utility.parseInt(product_id, new Integer(0));
        param[3] = product_name;
        super.query("{call SP_QUERY_TPRODUCT_END_UNCHECK(?,?,?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryPost()
	 */
    @Override
	public void queryPost() throws Exception {
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = Utility.parseInt(input_man, new Integer(0));
        param[3] = product_name;

        super.query("{call SP_QUERY_PRODUCT_POST (?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryUnPost()
	 */
    @Override
	public void queryUnPost() throws Exception {

        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = Utility.parseInt(input_man, new Integer(0));
        param[3] = product_name;

        super.query("{call SP_QUERY_PRODUCT_UNPOST (?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getUnpostNext()
	 */
    @Override
	public boolean getUnpostNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = (Integer) rowset.getObject("PRODUCT_ID");
            product_name = rowset.getString("PRODUCT_NAME");
            product_code = rowset.getString("PRODUCT_CODE");
            product_status = rowset.getString("PRODUCT_STATUS");
            product_status_name = rowset.getString("PRODUCT_STATUS_NAME");
            last_post_date = (Integer) rowset.getObject("LAST_POST_DATE");
            currency_id = rowset.getString("CURRENCY_ID");
            product_status_name = rowset.getString("PRODUCT_STATUS_NAME");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryTrans()
	 */
    @Override
	public void queryTrans() throws Exception {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));

        super.query("{call SP_QUERY_TRANSTPRODUCT (?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#end()
	 */
    @Override
	public void end() throws BusiException {

        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            param[2] = Utility.parseInt(end_date, new Integer(0));

            super.update("{?=call SP_END_TPRODUCT (?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品结束处理失败: " + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#endUnCheck()
	 */
    @Override
	public void endUnCheck() throws BusiException {
        try {
            Object[] param = new Object[2];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_END_TPRODUCT_UNCHECK (?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品结束恢复处理失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProvLevel()
	 */
    @Override
	public void queryProvLevel() throws Exception {

        Object[] param = new Object[3];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = Utility.parseInt(input_man, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_PROVLEVEL (?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#ifExistProject(java.lang.Integer, java.lang.Integer)
	 */

    @Override
	public Integer ifExistProject(Integer project_flag, Integer book_code) throws BusiException {
        Object[] oparams = new Object[2];
        oparams[0] = project_flag;
        oparams[1] = book_code;
        try {
            Integer exist = (Integer) super.cudEx(
                    "{?=call SP_IFEXISTS_PROJECTS(?,?,?)}", oparams, 4,
                    java.sql.Types.INTEGER);
            return exist;
        } catch (Exception e) {
            throw new BusiException("事务标志判断失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#ifExistProBlem(java.lang.Integer)
	 */

    @Override
	public Integer ifExistProBlem(Integer project_flag) throws BusiException {
        Object[] oparams = new Object[2];
        oparams[0] = product_id;
        oparams[1] = project_flag;
        try {
            Integer exist = (Integer) super.cudEx(
                    "{?=call SP_IFEXISTS_TPROBLEMS(?,?,?)}", oparams, 4,
                    java.sql.Types.INTEGER);
            return exist;
        } catch (Exception e) {
            throw new BusiException("事务标志判断失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#appendUpdateperiodSP()
	 */
    @Override
	public void appendUpdateperiodSP() throws BusiException {
        Object[] params = new Object[2];
        params[0] = product_id;
        params[1] = input_man;
        try {
            super.update("{?=call SP_ADD_TPRODUCTPERIOD_TPROBLEMS(?,?)}",
                    params);
        } catch (Exception e) {
            throw new BusiException("添加产品展期审批失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#appendProductQualitySP()
	 */
    @Override
	public void appendProductQualitySP() throws BusiException {
        Object[] params = new Object[2];
        params[0] = product_id;
        params[1] = input_man;
        try {
            super.update("{?=call SP_ADD_TPRODUCTQUALITY_TPROBLEMS(?,?)}",
                    params);
        } catch (Exception e) {
            throw new BusiException("添加质量级别调整审批失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProvLevel()
	 */
    @Override
	public boolean getNextProvLevel() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = (Integer) rowset.getObject("PRODUCT_ID");
            product_name = rowset.getString("PRODUCT_NAME");
            prov_level = rowset.getString("PROV_LEVEL");
            prov_level_name = rowset.getString("PROV_LEVEL_NAME");
            ben_amount = rowset.getBigDecimal("BEN_AMOUNT");
            contract_num = (Integer) rowset.getObject("CONTRACT_NUM");
            ben_num = (Integer) rowset.getObject("BEN_NUM");
            gr_count = (Integer) rowset.getObject("GR_NUM");
            gr_money = rowset.getBigDecimal("GR_MONEY");
            jg_count = (Integer) rowset.getObject("JG_NUM");
            jg_money = rowset.getBigDecimal("JG_MONEY");
            ben_money = rowset.getBigDecimal("BEN_MONEY");
            gr_amount = rowset.getBigDecimal("GR_AMOUNT");
            jg_amount = rowset.getBigDecimal("JG_AMOUNT");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listProudctForTrans()
	 */
    @Override
	public void listProudctForTrans() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = product_name;

        super.query("{call SP_QUERY_TSUBCODE_FOR_TRANS (?,?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listProudctForTrans4003()
	 */
    @Override
	public void listProudctForTrans4003() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = product_name;

        super.query("{call SP_QUERY_TSUBCODE_FOR_TRANS4003 (?,?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProudctForTrans()
	 */
    @Override
	public boolean getNextProudctForTrans() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_name = rowset.getString("SUB_NAME");
            product_code = rowset.getString("SUB_CODE3");
            currency_id = rowset.getString("SUB_CODE2");

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listcheck()
	 */
    @Override
	public void listcheck() throws Exception {

        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = product_name;
        param[3] = Utility.parseInt(input_man, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_SUB_UNCHECK (?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listcheckAcc()
	 */
    @Override
	public void listcheckAcc() throws Exception {

        Object[] param = new Object[4];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = product_name;
        param[3] = Utility.parseInt(input_man, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCT_SUB_ACCOUNT (?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#check()
	 */
    @Override
	public void check() throws BusiException {

        try {

            Object[] param = new Object[4];
            param[0] = Utility.parseInt(book_code, new Integer(0));
            param[1] = Utility.parseInt(product_id, new Integer(0));
            param[2] = Utility.parseInt(sub_check_flag, new Integer(0));
            param[3] = Utility.parseInt(input_man, new Integer(0));

            super.cud("{?=call SP_CHECK_TPRODUCT_SUBJECT (?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品科目设置失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextCell()
	 */
    @Override
	public boolean getNextCell() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_code = rowset.getString("PRODUCT_CODE");
            product_name = rowset.getString("PRODUCT_NAME");
            sl_flag = (Integer) rowset.getObject("SL_FLAG");
            all_flag = (Integer) rowset.getObject("ALL_FLAG");
            product_id = (Integer) rowset.getObject("PRODUCT_ID");

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#querycity()
	 */
    @Override
	public void querycity() throws Exception {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(serial_no, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCTCITY(?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextcity()
	 */
    @Override
	public boolean getNextcity() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = (Integer) rowset.getObject("PRODUCT_ID");
            serial_no = (Integer) rowset.getObject("SERIAL_NO");
            city_name = rowset.getString("CITY_NAME");
            gov_prov_pegional = rowset.getString("GOV_PROV_REGIONAL");
			gov_pegional = rowset.getString("GOV_REGIONAL");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryPeriodDate(java.lang.Integer)
	 */
    @Override
	public void queryPeriodDate(Integer task_type) throws Exception {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(task_type, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCTGAINDATE(?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextPeriodDate()
	 */
    @Override
	public boolean getNextPeriodDate() throws Exception {
        boolean b = super.getNext();
        if (b) {
            task_date = (Integer) rowset.getObject("TASK_DATE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductPurpose(java.lang.Integer)
	 */
    @Override
	public void queryProductPurpose(Integer invest_flag) throws Exception {
        Object[] param = new Object[2];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(invest_flag, new Integer(0));
        super.query("{call SP_QUERY_PRODUCT_PURPOSE(?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductPurpose()
	 */
    @Override
	public boolean getNextProductPurpose() throws Exception {
        boolean b = super.getNext();
        if (b) {
            invest_type = rowset.getString("INVEST_TYPE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProductPurpose(java.lang.Integer)
	 */
    @Override
	public String getProductPurpose(Integer invest_flag) throws Exception {
        queryProductPurpose(invest_flag);
        if (getNextProductPurpose()) {
            return invest_type;
        }
        return "";
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#adddeletecity()
	 */
    @Override
	public void adddeletecity() throws BusiException {
        try {
            Object[] param = new Object[5];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = city_name;
            param[2] = Utility.parseInt(input_man, new Integer(0));
            param[3] = Utility.trimNull(gov_prov_pegional);
            param[4] = Utility.trimNull(gov_pegional);
            super.cud("{?=call SP_ADD_TPRODUCTCITY(?,?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品推荐地新增/删除失败: " + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#querytz()
	 */
    @Override
	public void querytz() throws Exception {

        Object[] param = new Object[1];
        param[0] = Utility.parseInt(product_id, new Integer(0));

        super.query("{call SP_QUERY_TPRODUCT_TZ(?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNexttz()
	 */
    @Override
	public boolean getNexttz() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.busi_name = rowset.getString("BUSI_NAME");
            this.contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
            this.cust_name = rowset.getString("CUST_NAME");
            this.ht_money = rowset.getBigDecimal("HT_MONEY");
            this.cw_money = rowset.getBigDecimal("CW_MONEY");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryDate()
	 */
    @Override
	public void queryDate() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(serial_no, new Integer(0));
        param[2] = trade_type;

        super.query("{call SP_QUERY_TPRODUCTDATE(?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#addDeleteDate()
	 */
    @Override
	public void addDeleteDate() throws BusiException {
        try {
            String strSql = "{?=call SP_ADD_TPRODUCTDATE(?,?,?,?,?,?,?,?)}";

            Object[] param = new Object[8];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = Utility.parseInt(product_id, new Integer(0));
            param[2] = Utility.parseInt(trade_date, new Integer(0));
            param[3] = trade_type;
            param[4] = trade_type_name;
            param[5] = description;
            param[6] = Utility.parseBigDecimal(trade_rate, new BigDecimal(0));
            param[7] = Utility.parseInt(input_man, new Integer(0));

            super.append(strSql, param);

        } catch (Exception e) {
            throw new BusiException("产品时间信息新增/删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductDate()
	 */
    @Override
	public boolean getNextProductDate() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = (Integer) rowset.getObject("PRODUCT_ID");
            serial_no = (Integer) rowset.getObject("SERIAL_NO");
            trade_date = (Integer) rowset.getObject("TRADE_DATE");
            trade_type = rowset.getString("TRADE_TYPE");
            trade_type_name = rowset.getString("TRADE_TYPE_NAME");
            description = rowset.getString("DESCRIPTION");
            trade_rate = rowset.getBigDecimal("TRADE_RATE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#modiPeriod()
	 */
    @Override
	public void modiPeriod() throws BusiException {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(end_date, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));
            super.update("{?=call SP_MODI_TPRODUCTPERIOD(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("信托期限展期失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#checkPeriod()
	 */
    @Override
	public void checkPeriod() throws BusiException {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = Utility.parseInt(check_flag, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));
            super.update("{?=call SP_CHECK_TPRODUCTPERIOD(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("信托期限展期失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryPeriod()
	 */
    @Override
	public void queryPeriod() throws Exception {
        Object[] param = new Object[3];
        param[0] = Utility.parseInt(serial_no, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = Utility.parseInt(check_flag, new Integer(0));
        super.query("{call SP_QUERY_TPRODUCTPERIOD(?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#loadPeriod()
	 */
    @Override
	public void loadPeriod() throws Exception {
        this.queryPeriod();
        this.getNextPeriod();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextPeriod()
	 */
    @Override
	public boolean getNextPeriod() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_id = (Integer) rowset.getObject("PRODUCT_ID");
            product_code = rowset.getString("PRODUCT_CODE");
            product_name = rowset.getString("PRODUCT_NAME");
            serial_no = (Integer) rowset.getObject("SERIAL_NO");
            old_end_date = (Integer) rowset.getObject("OLD_END_DATE");
            new_end_date = (Integer) rowset.getObject("NEW_END_DATE");

            currency_id = rowset.getString("CURRENCY_ID");
            fact_num = (Integer) rowset.getObject("FACT_NUM");
            fact_money = rowset.getBigDecimal("FACT_MONEY");
            start_date = (Integer) rowset.getObject("START_DATE");
            end_date = (Integer) rowset.getObject("END_DATE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryBusinessEnd()
	 */
    @Override
	public void queryBusinessEnd() throws Exception {
        Object[] param = new Object[6];
        param[0] = Utility.parseInt(book_code, new Integer(0));
        param[1] = Utility.parseInt(input_man, new Integer(0));
        param[2] = Utility.parseInt(product_id, new Integer(0));
        param[3] = product_name;
        param[4] = Utility.parseInt(end_flag, new Integer(0));
        param[5] = Utility.parseInt(flag, new Integer(0));
        super
                .query("{call SP_QUERY_TPRODUCT_END_BUSINESS(?,?,?,?,?,?)}",
                        param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#endBusiness()
	 */
    @Override
	public void endBusiness() throws BusiException {

        try {

            Object[] param = new Object[4];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            param[2] = Utility.parseInt(end_date, new Integer(0));
            param[3] = Utility.parseInt(end_flag, new Integer(0));
            super.update("{?=call SP_END_TPRODUCT_BUSINESS (?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品结束处理失败: " + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#endUnBusinessCheck()
	 */
    @Override
	public void endUnBusinessCheck() throws BusiException {
        try {
            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            param[2] = Utility.parseInt(end_flag, new Integer(0));

            super.update("{?=call SP_END_TPRODUCT_BUSINESS_UNCHECK (?,?,?)}",
                    param);

        } catch (Exception e) {
            throw new BusiException("产品结束恢复处理失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductDetail()
	 */
    @Override
	public void queryProductDetail() throws Exception {
        Object[] params = new Object[15];
        params[0] = book_code;
        params[1] = this.input_man;
        params[2] = this.flag;
        params[3] = this.item_id;
        params[4] = this.product_status;
        params[5] = this.intrust_type;
        params[6] = this.product_from;
        params[7] = this.intrust_flag1;
        params[8] = this.intrust_flag4;
        params[9] = this.intrust_type1;
        params[10] = this.intrust_type2;
        params[11] = this.depart_id;
        params[12] = this.fpfs;
        params[13] = this.open_flag;
        params[14] = this.manager_type;
        super.query("{call SP_QUERY_TMYPRODUCT_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductMessage()
	 */
    @Override
	public CachedRowSet queryProductMessage() throws Exception {
        super.list();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rslist = null;
        try {
            conn = IntrustDBManager.getConnection();
            stmt = conn.prepareCall("{call SP_QUERY_TMYPRODUCT_LIST(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setInt(1, Utility.parseInt(book_code,new Integer(0)).intValue());
            stmt.setInt(2, Utility.parseInt(input_man,new Integer(0)).intValue());
            stmt.setInt(3, Utility.parseInt(flag,new Integer(0)).intValue());
            stmt.setInt(4, Utility.parseInt(item_id,new Integer(0)).intValue());

            stmt.setString(5, product_status);
            stmt.setString(6, intrust_type);
            stmt.setInt(7, Utility.parseInt(product_from,new Integer(0)).intValue());
            stmt.setInt(8, Utility.parseInt(intrust_flag1,new Integer(0)).intValue());
            stmt.setInt(9, Utility.parseInt(intrust_flag4,new Integer(0)).intValue());
            stmt.setString(10, intrust_type1);
            stmt.setString(11, intrust_type2);
            stmt.setInt(12, Utility.parseInt(depart_id,new Integer(0)).intValue());
            stmt.setString(13, fpfs);
            stmt.setInt(14, Utility.parseInt(open_flag,new Integer(0)).intValue());
            stmt.setInt(15, Utility.parseInt(manager_type,new Integer(0)).intValue());      
            
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
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductDetail()
	 */
    @Override
	public boolean getNextProductDetail() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            this.product_code = rowset.getString("PRODUCT_CODE");
            this.product_name = rowset.getString("PRODUCT_NAME");
            this.start_date = new Integer(rowset.getInt("START_DATE"));
            this.end_date = new Integer(rowset.getInt("END_DATE"));
            this.sAdmin_man = rowset.getString("ADMIN_MAN");
            this.sAdmin_man2 = rowset.getString("ADMIN_MAN2");
            this.sMatain_man = rowset.getString("MATAIN_MAN");
            this.balance_3101 = rowset.getBigDecimal("BALANCE_3101");
            this.balance_all = rowset.getBigDecimal("BALANCE_ALL");
            this.balance_lr = rowset.getBigDecimal("BALANCE_LR");
            this.gr_num = new Integer(rowset.getInt("GR_NUM"));
            this.jg_num = new Integer(rowset.getInt("JG_NUM"));
            this.sub_man_name = rowset.getString("SUB_MAN_NAME");
            this.current_month = new Integer(rowset.getInt("CURRENT_MONTH"));
            this.fact_money = (BigDecimal) rowset.getObject("FACT_MONEY");
            this.product_status = rowset.getString("PRODUCT_STATUS");
            this.product_status_name = rowset.getString("PRODUCT_STATUS_NAME");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductTimeDetail()
	 */
    @Override
	public void queryProductTimeDetail() throws Exception {
        Object[] params = new Object[6];
        params[0] = book_code;
        params[1] = time_flag;
        params[2] = start_date;
        params[3] = end_date;
        params[4] = this.input_man;
        params[5] = with_bank_flag;
        super.query("{call SP_QUERY_TMYPRODUCT_TIMELIST(?,?,?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductTimeDetail()
	 */
    @Override
	public boolean getNextProductTimeDetail() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            this.product_code = rowset.getString("PRODUCT_CODE");
            this.product_name = rowset.getString("PRODUCT_NAME");
            this.trade_date = new Integer(rowset.getInt("TRADE_DATE"));

        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#saveSub_man()
	 */
    @Override
	public void saveSub_man() throws BusiException {
        try {
            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(sub_man, new Integer(0));
            param[2] = Utility.parseInt(input_man, new Integer(0));
            super.update("{?=call SP_MODI_TPRODUCT_SUB_MAN (?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("核算会计设置失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductLog()
	 */
    @Override
	public void queryProductLog() throws Exception {
        Object[] params = new Object[1];
        params[0] = product_id;
        super.query("{call SP_QUERY_TPRODUCTLOG(?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductLog()
	 */
    @Override
	public boolean getNextProductLog() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            this.busi_flag = new Integer(rowset.getInt("BUSI_FLAG"));
            this.busi_name = rowset.getString("BUSI_NAME");
            this.op_code = new Integer(rowset.getInt("OP_CODE"));
            this.trade_time = rowset.getTimestamp("TRADE_TIME");
            this.summary = rowset.getString("SUMMARY");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#modiProductCity()
	 */
    @Override
	public void modiProductCity()throws Exception{
    	Object[] params = new Object[4];
    	params[0] = Utility.parseInt(serial_no,new Integer(0));
    	params[1] = Utility.trimNull(gov_prov_pegional);
    	params[2] = Utility.trimNull(gov_pegional);
    	params[3] = Utility.parseInt(input_man,new Integer(0));
    	super.cud("{?= call SP_MODI_TPRODUCTCITY(?,?,?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#deleteProductCity()
	 */
    @Override
	public void deleteProductCity()throws Exception{
    	Object[] params = new Object[2];
    	params[0] = Utility.parseInt(serial_no,new Integer(0));
    	params[1] = Utility.parseInt(input_man,new Integer(0));
    	super.cud("{?= call SP_DEL_TPRODUCTCITY(?,?)}",params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductNav()
	 */
    @Override
	public void queryProductNav() throws Exception {
        Object[] params = new Object[4];
        params[0] = product_id;
        params[1] = start_date;
        params[2] = end_date;
        params[3] = period_unit;
        super.query("{call SP_QUERY_CPNAV_ONE(?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductNav()
	 */
    @Override
	public boolean getNextProductNav() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.hq_date = new Integer(rowset.getInt("HQ_DATE"));
            this.nav_price = rowset.getBigDecimal("NAV_PRICE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductNav2()
	 */
    @Override
	public void queryProductNav2() throws Exception {
        Object[] params = new Object[1];
        params[0] = product_id;
        super.query("{call SP_QUERY_CPNAV_MORE(?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductNav2()
	 */
    @Override
	public boolean getNextProductNav2() throws Exception {
        boolean b = super.getNext();
        if (b) {
            this.hq_date = new Integer(rowset.getInt("HQ_DATE"));
            this.nav_price1 = rowset.getBigDecimal("NAV_PRICE1");
            this.nav_price2 = rowset.getBigDecimal("NAV_PRICE2");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductCust()
	 */
    @Override
	public void queryProductCust() throws Exception {

        Object[] params = new Object[3];
        params[0] = book_code;
        params[1] = product_id;

        params[2] = input_man;
        super.query("{call SP_QUERY_TPRODUCT_CUST (?,?,?)}", params);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductCust()
	 */
    @Override
	public boolean getNextProductCust() throws Exception {
        boolean b = super.getNext();
        if (b) {
            intrust_flag1 = (Integer) rowset.getObject("INTRUST_FLAG1");
            fact_money = rowset.getBigDecimal("FACT_MONEY");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryChart()
	 */
    @Override
	public void queryChart() throws Exception {
        Object[] params = new Object[4];
        params[0] = Utility.parseInt(flag, new Integer(0));
        params[1] = Utility.parseInt(start_date, new Integer(0));
        params[2] = Utility.parseInt(end_date, new Integer(0));
        params[3] = Utility.parseInt(input_man, new Integer(0));
        super.query("{call SP_STAT_PRODUCTSCALE(?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getChartMessage(java.lang.Integer)
	 */
    @Override
	public boolean getChartMessage(Integer flag) throws Exception {
        total_fact_money = new String[2];
        boolean b = super.getNext();
        if (b) {
            if (flag.intValue() == 101) {
                op_code = new Integer(rowset.getInt("OP_CODE"));
                op_name = rowset.getString("OP_NAME");
                curr_fact_money = rowset.getBigDecimal("CURR_FACT_MONEY");
                fact_money = rowset.getBigDecimal("FACT_MONEY");
                curr_fact_money = rowset.getBigDecimal("CURR_FACT_MONEY");
                fact_money = rowset.getBigDecimal("FACT_MONEY");
                if (curr_fact_money != null)
                    total_fact_money[0] = curr_fact_money.toString();
                else
                    total_fact_money[0] = "0.00";

                if (fact_money != null)
                    total_fact_money[1] = fact_money.toString();
                else
                    total_fact_money[1] = "0.00";
            }
            if (flag.intValue() == 102) {
                intrust_flag1 = new Integer(rowset.getInt("INTRUST_FLAG1"));
                intrust_flag1_name = rowset.getString("INTRUST_FLAG1_NAME");
                fact_money = rowset.getBigDecimal("FACT_MONEY");
            }
            if (flag.intValue() == 103) {//add by zhangmy 20100224
                end_date = new Integer(rowset.getInt("YEAR"));
                fact_money = rowset.getBigDecimal("FACT_MONEY");
            }
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listForBusiNavPrice()
	 */
    @Override
	public void listForBusiNavPrice() throws Exception {

        Object[] param = new Object[3];
        param[0] = Utility.parseInt(book_code, new Integer(1));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = Utility.parseInt(trade_date, new Integer(0));
        super.query("{call SP_QUERY_NAVPRICEINFO_DATE (?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#loadForBusiNavPrice()
	 */
    @Override
	public void loadForBusiNavPrice() throws Exception {
        this.listForBusiNavPrice();
        boolean b = super.getNext();
        if (b) {
            busi_nav_price = rowset.getBigDecimal("BUSI_NAV_PRICE");
            nav_price = rowset.getBigDecimal("NAV_PRICE");
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryTrade_tax_rate()
	 */
    @Override
	public void queryTrade_tax_rate() throws Exception {
        Object[] param = new Object[1];
        param[0] = new Integer(9);
        super.query("{call SP_QUERY_TBASETAXRATE(?)}", param);
        boolean b = super.getNext();
        if (b) {
            fee = rowset.getBigDecimal("FEE");
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#listProductNotice()
	 */

    @Override
	public void listProductNotice() throws Exception {
        Object[] param = new Object[1];
        param[0] = Utility.parseInt(product_id, new Integer(0));

        super.query("{call SP_QUERY_TPRODUCT_PUBLISH_NOTICE(?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextNotice()
	 */
    @Override
	public boolean getNextNotice() throws Exception {
        boolean b = super.getNext();
        if (b) {
            product_name = rowset.getString("PRODUCT_NAME");
            start_date = (Integer) rowset.getObject("START_DATE");
            total_money = (BigDecimal) rowset.getObject("TOTAL_MONEY");
            prov_level_a_money = (BigDecimal) rowset
                    .getObject("PROV_LEVEL_A_MONEY");
            prov_level_b_money = (BigDecimal) rowset
                    .getObject("PROV_LEVEL_B_MONEY");
            org_count = (Integer) rowset.getObject("ORG_COUNT");
            person_count = (Integer) rowset.getObject("PERSON_COUNT");
            bank_name = rowset.getString("BANK_NAME");
            acct_name = rowset.getString("TG_ACCT_NAME");
            bank_acct = rowset.getString("TG_BANK_ACCT");
        }
        return b;
    }
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductOp()
	 */
	@Override
	public void queryProductOp() throws Exception {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(book_code, new Integer(0));
		params[1] = Utility.trimNull(product_code);
		params[2] = Utility.trimNull(product_name);
		params[3] = Utility.parseInt(input_man, new Integer(0));
		super.query("{CALL SP_QUERY_TPRODUCTOP(?,?,?,?)}", params);

	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#delProductOp()
	 */
	@Override
	public void delProductOp() throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(serial_no, new Integer(0));
		params[1] = Utility.parseInt(product_id, new Integer(0));
		params[2] = Utility.parseInt(op_code, new Integer(0));
		params[3] = Utility.parseInt(input_man, new Integer(0));

		try {
			super.delete("{?=CALL SP_DEL_TPRODUCTOP(?,?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("删除运用管理责任人失败" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#addProductOp()
	 */
	@Override
	public void addProductOp() throws BusiException {
		Object[] params = new Object[4];
		params[0] = Utility.parseInt(book_code, new Integer(0));
		params[1] = Utility.parseInt(product_id, new Integer(0));
		params[2] = Utility.parseInt(op_code, new Integer(0));
		params[3] = Utility.parseInt(input_man, new Integer(0));

		try {
			super.append("{?=CALL SP_ADD_TPRODUCTOP(?,?,?,?)}", params);
		} catch (Exception e) {
			throw new BusiException("指定产品责任人" + e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryOpPosses()
	 */
	@Override
	public void queryOpPosses() throws Exception {
		Object[] params = new Object[1];
		params[0] = Utility.parseInt(product_id, new Integer(0));
		super.query("{CALL SP_QUERY_TPRODUCTOP_POSSES(?)}", params);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOpNext()
	 */
	@Override
	public boolean getOpNext() throws Exception {
		boolean b = super.getNext();
		if (b) {
			op_code =
				Utility.parseInt(rowset.getString("OP_CODE"), new Integer(0));
			op_name = rowset.getString("OP_NAME");
		}
		return b;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#subProductCheck()
	 */
	@Override
	public void subProductCheck() throws Exception {
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(sub_product_id, new Integer(0));
		param[1] = Utility.parseInt(input_man, new Integer(0));
		super.cud("{?= call SP_CHECK_TSUBPRODUCT(?,?)}", param);
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#product_zjcl()
	 */
    @Override
	public void product_zjcl() throws BusiException {

        Object[] param = new Object[9];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(start_date, new Integer(0));
        param[2] = Utility.parseInt(person_count, new Integer(0));        
        param[3] = Utility.parseBigDecimal(person_money, new BigDecimal(0));
        param[4] = Utility.parseInt(org_count, new Integer(0));        
        param[5] = Utility.parseBigDecimal(org_money, new BigDecimal(0));  
        param[6] = Utility.parseBigDecimal(qualified_count, new BigDecimal(0));        
        param[7] = Utility.parseBigDecimal(qualified_money, new BigDecimal(0));
        param[8] = Utility.parseInt(input_man, new Integer(0));
        try {
            super.update("{?= call SP_START_TPRODUCT_SPECIAL(?,?,?,?,?,?,?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("产品直接成立失败！" + e.getMessage());
        }
    }
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#queryProductLimit()
	 */   
    @Override
	public void queryProductLimit() throws Exception{
        Object[] param = new Object[1];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        try {
            super.query("{call SP_QUERY_TPRODUCTLIMIT(?)}", param);
        } catch (Exception e) {
            throw new BusiException("查询产品销售设置失败！" + e.getMessage());
        }
    }
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNextProductLimit()
	 */
    @Override
	public boolean getNextProductLimit()throws Exception{
    	boolean b = super.getNext();
    	if(b){
            product_id = (Integer)rowset.getObject("PRODUCT_ID");
            book_code = (Integer) rowset.getObject("BOOK_CODE");
            qualified_flag = (Integer) rowset.getObject("QUALIFIED_FLAG");
            qualified_money = (BigDecimal) rowset
                    .getObject("QUALIFIED_MONEY");
            qualified_num = (Integer) rowset
                    .getObject("QUALIFIED_NUM");
            asfund_flag = (Integer) rowset.getObject("ASFUND_FLAG");
    	}
    	return b;
    }
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#updateProductLimit()
	 */      
    @Override
	public void updateProductLimit() throws Exception {
        Object[] param = new Object[6];
        param[0] = Utility.parseInt(product_id, new Integer(0));
        param[1] = Utility.parseInt(qualified_flag, new Integer(0));
        param[2] = Utility.parseInt(qualified_num, new Integer(0));
        param[3] = Utility.parseBigDecimal(qualified_money, new BigDecimal(0));
        param[4] = Utility.parseInt(asfund_flag, new Integer(0));
        param[5] = Utility.parseInt(input_man, new Integer(0));
        
        try {
            super.append("{?=call SP_ADD_TPRODUCTLIMIT(?,?,?,?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("新增修改产品销售设置失败！" + e.getMessage());
        }
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAcct_name()
	 */
    @Override
	public String getAcct_name() {
        return acct_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAcct_name(java.lang.String)
	 */
    @Override
	public void setAcct_name(String acct_name) {
        this.acct_name = acct_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAdmin_manager()
	 */
    @Override
	public java.lang.Integer getAdmin_manager() {
        return admin_manager;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAdmin_manager(java.lang.Integer)
	 */
    @Override
	public void setAdmin_manager(java.lang.Integer admin_manager) {
        this.admin_manager = admin_manager;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAdmin_manager2()
	 */
    @Override
	public java.lang.Integer getAdmin_manager2() {
        return admin_manager2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAdmin_manager2(java.lang.Integer)
	 */
    @Override
	public void setAdmin_manager2(java.lang.Integer admin_manager2) {
        this.admin_manager2 = admin_manager2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAll_flag()
	 */
    @Override
	public java.lang.Integer getAll_flag() {
        return all_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAll_flag(java.lang.Integer)
	 */
    @Override
	public void setAll_flag(java.lang.Integer all_flag) {
        this.all_flag = all_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAmount_max()
	 */
    @Override
	public java.math.BigDecimal getAmount_max() {
        return amount_max;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAmount_max(java.math.BigDecimal)
	 */
    @Override
	public void setAmount_max(java.math.BigDecimal amount_max) {
        this.amount_max = amount_max;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAmount_min()
	 */
    @Override
	public java.math.BigDecimal getAmount_min() {
        return amount_min;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAmount_min(java.math.BigDecimal)
	 */
    @Override
	public void setAmount_min(java.math.BigDecimal amount_min) {
        this.amount_min = amount_min;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBalance_3101()
	 */
    @Override
	public BigDecimal getBalance_3101() {
        return balance_3101;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBalance_3101(java.math.BigDecimal)
	 */
    @Override
	public void setBalance_3101(BigDecimal balance_3101) {
        this.balance_3101 = balance_3101;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBalance_all()
	 */
    @Override
	public BigDecimal getBalance_all() {
        return balance_all;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBalance_all(java.math.BigDecimal)
	 */
    @Override
	public void setBalance_all(BigDecimal balance_all) {
        this.balance_all = balance_all;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBalance_lr()
	 */
    @Override
	public BigDecimal getBalance_lr() {
        return balance_lr;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBalance_lr(java.math.BigDecimal)
	 */
    @Override
	public void setBalance_lr(BigDecimal balance_lr) {
        this.balance_lr = balance_lr;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBank_acct()
	 */
    @Override
	public String getBank_acct() {
        return bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBank_acct(java.lang.String)
	 */
    @Override
	public void setBank_acct(String bank_acct) {
        this.bank_acct = bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBank_name()
	 */
    @Override
	public String getBank_name() {
        return bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBank_name(java.lang.String)
	 */
    @Override
	public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBen_amount()
	 */
    @Override
	public java.math.BigDecimal getBen_amount() {
        return ben_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBen_amount(java.math.BigDecimal)
	 */
    @Override
	public void setBen_amount(java.math.BigDecimal ben_amount) {
        this.ben_amount = ben_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBen_money()
	 */
    @Override
	public java.math.BigDecimal getBen_money() {
        return ben_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBen_money(java.math.BigDecimal)
	 */
    @Override
	public void setBen_money(java.math.BigDecimal ben_money) {
        this.ben_money = ben_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBen_num()
	 */
    @Override
	public java.lang.Integer getBen_num() {
        return ben_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBen_num(java.lang.Integer)
	 */
    @Override
	public void setBen_num(java.lang.Integer ben_num) {
        this.ben_num = ben_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBen_period()
	 */
    @Override
	public java.lang.Integer getBen_period() {
        return ben_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBen_period(java.lang.Integer)
	 */
    @Override
	public void setBen_period(java.lang.Integer ben_period) {
        this.ben_period = ben_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBen_period_temp()
	 */
    @Override
	public int getBen_period_temp() {
        return ben_period_temp;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBen_period_temp(int)
	 */
    @Override
	public void setBen_period_temp(int ben_period_temp) {
        this.ben_period_temp = ben_period_temp;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBg_bank_id()
	 */
    @Override
	public String getBg_bank_id() {
        return bg_bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBg_bank_id(java.lang.String)
	 */
    @Override
	public void setBg_bank_id(String bg_bank_id) {
        this.bg_bank_id = bg_bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBg_bank_name()
	 */
    @Override
	public String getBg_bank_name() {
        return bg_bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBg_bank_name(java.lang.String)
	 */
    @Override
	public void setBg_bank_name(String bg_bank_name) {
        this.bg_bank_name = bg_bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBook_code()
	 */
    @Override
	public java.lang.Integer getBook_code() {
        return book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBook_code(java.lang.Integer)
	 */
    @Override
	public void setBook_code(java.lang.Integer book_code) {
        this.book_code = book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBusi_flag()
	 */
    @Override
	public Integer getBusi_flag() {
        return busi_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBusi_flag(java.lang.Integer)
	 */
    @Override
	public void setBusi_flag(Integer busi_flag) {
        this.busi_flag = busi_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBusi_name()
	 */
    @Override
	public String getBusi_name() {
        return busi_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBusi_name(java.lang.String)
	 */
    @Override
	public void setBusi_name(String busi_name) {
        this.busi_name = busi_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBusi_nav_price()
	 */
    @Override
	public java.math.BigDecimal getBusi_nav_price() {
        return busi_nav_price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBusi_nav_price(java.math.BigDecimal)
	 */
    @Override
	public void setBusi_nav_price(java.math.BigDecimal busi_nav_price) {
        this.busi_nav_price = busi_nav_price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBusiness_end_date()
	 */
    @Override
	public Integer getBusiness_end_date() {
        return business_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBusiness_end_date(java.lang.Integer)
	 */
    @Override
	public void setBusiness_end_date(Integer business_end_date) {
        this.business_end_date = business_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getBusiness_end_flag()
	 */
    @Override
	public Integer getBusiness_end_flag() {
        return business_end_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setBusiness_end_flag(java.lang.Integer)
	 */
    @Override
	public void setBusiness_end_flag(Integer business_end_flag) {
        this.business_end_flag = business_end_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getChange_wt_flag()
	 */
    @Override
	public java.lang.Integer getChange_wt_flag() {
        return change_wt_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setChange_wt_flag(java.lang.Integer)
	 */
    @Override
	public void setChange_wt_flag(java.lang.Integer change_wt_flag) {
        this.change_wt_flag = change_wt_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCheck_flag()
	 */
    @Override
	public java.lang.Integer getCheck_flag() {
        return check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCheck_flag(java.lang.Integer)
	 */
    @Override
	public void setCheck_flag(java.lang.Integer check_flag) {
        this.check_flag = check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCheck_man()
	 */
    @Override
	public Integer getCheck_man() {
        return check_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCheck_man(java.lang.Integer)
	 */
    @Override
	public void setCheck_man(Integer check_man) {
        this.check_man = check_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCity_name()
	 */
    @Override
	public java.lang.String getCity_name() {
        return city_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCity_name(java.lang.String)
	 */
    @Override
	public void setCity_name(java.lang.String city_name) {
        this.city_name = city_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getContract_num()
	 */
    @Override
	public java.lang.Integer getContract_num() {
        return contract_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setContract_num(java.lang.Integer)
	 */
    @Override
	public void setContract_num(java.lang.Integer contract_num) {
        this.contract_num = contract_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getContract_sub_bh()
	 */
    @Override
	public String getContract_sub_bh() {
        return contract_sub_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setContract_sub_bh(java.lang.String)
	 */
    @Override
	public void setContract_sub_bh(String contract_sub_bh) {
        this.contract_sub_bh = contract_sub_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCurr_fact_money()
	 */
    @Override
	public BigDecimal getCurr_fact_money() {
        return curr_fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCurr_fact_money(java.math.BigDecimal)
	 */
    @Override
	public void setCurr_fact_money(BigDecimal curr_fact_money) {
        this.curr_fact_money = curr_fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCurrency_id()
	 */
    @Override
	public java.lang.String getCurrency_id() {
        return currency_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCurrency_id(java.lang.String)
	 */
    @Override
	public void setCurrency_id(java.lang.String currency_id) {
        this.currency_id = currency_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCurrent_month()
	 */
    @Override
	public Integer getCurrent_month() {
        return current_month;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCurrent_month(java.lang.Integer)
	 */
    @Override
	public void setCurrent_month(Integer current_month) {
        this.current_month = current_month;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCust_name()
	 */
    @Override
	public String getCust_name() {
        return cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCust_name(java.lang.String)
	 */
    @Override
	public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCw_money()
	 */
    @Override
	public java.math.BigDecimal getCw_money() {
        return cw_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCw_money(java.math.BigDecimal)
	 */
    @Override
	public void setCw_money(java.math.BigDecimal cw_money) {
        this.cw_money = cw_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDBdriver()
	 */
    @Override
	public String getDBdriver() {
        return DBdriver;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDBdriver(java.lang.String)
	 */
    @Override
	public void setDBdriver(String bdriver) {
        DBdriver = bdriver;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDeal_type()
	 */
    @Override
	public java.lang.String getDeal_type() {
        return deal_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDeal_type(java.lang.String)
	 */
    @Override
	public void setDeal_type(java.lang.String deal_type) {
        this.deal_type = deal_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDeal_type_name()
	 */
    @Override
	public java.lang.String getDeal_type_name() {
        return deal_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDeal_type_name(java.lang.String)
	 */
    @Override
	public void setDeal_type_name(java.lang.String deal_type_name) {
        this.deal_type_name = deal_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDepart_id()
	 */
    @Override
	public java.lang.Integer getDepart_id() {
        return depart_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDepart_id(java.lang.Integer)
	 */
    @Override
	public void setDepart_id(java.lang.Integer depart_id) {
        this.depart_id = depart_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDescription()
	 */
    @Override
	public java.lang.String getDescription() {
        return description;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDescription(java.lang.String)
	 */
    @Override
	public void setDescription(java.lang.String description) {
        this.description = description;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getEnd_date()
	 */
    @Override
	public java.lang.Integer getEnd_date() {
        return end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setEnd_date(java.lang.Integer)
	 */
    @Override
	public void setEnd_date(java.lang.Integer end_date) {
        this.end_date = end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getEnd_date2()
	 */
    @Override
	public java.lang.Integer getEnd_date2() {
        return end_date2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setEnd_date2(java.lang.Integer)
	 */
    @Override
	public void setEnd_date2(java.lang.Integer end_date2) {
        this.end_date2 = end_date2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getEnd_flag()
	 */
    @Override
	public Integer getEnd_flag() {
        return end_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setEnd_flag(java.lang.Integer)
	 */
    @Override
	public void setEnd_flag(Integer end_flag) {
        this.end_flag = end_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getEntity_type()
	 */
    @Override
	public java.lang.String getEntity_type() {
        return entity_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setEntity_type(java.lang.String)
	 */
    @Override
	public void setEntity_type(java.lang.String entity_type) {
        this.entity_type = entity_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getEntity_type_name()
	 */
    @Override
	public java.lang.String getEntity_type_name() {
        return entity_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setEntity_type_name(java.lang.String)
	 */
    @Override
	public void setEntity_type_name(java.lang.String entity_type_name) {
        this.entity_type_name = entity_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getExp_rate1()
	 */
    @Override
	public java.math.BigDecimal getExp_rate1() {
        return exp_rate1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setExp_rate1(java.math.BigDecimal)
	 */
    @Override
	public void setExp_rate1(java.math.BigDecimal exp_rate1) {
        this.exp_rate1 = exp_rate1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getExp_rate2()
	 */
    @Override
	public java.math.BigDecimal getExp_rate2() {
        return exp_rate2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setExp_rate2(java.math.BigDecimal)
	 */
    @Override
	public void setExp_rate2(java.math.BigDecimal exp_rate2) {
        this.exp_rate2 = exp_rate2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getExt_rate1()
	 */
    @Override
	public BigDecimal getExt_rate1() {
        return ext_rate1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setExt_rate1(java.math.BigDecimal)
	 */
    @Override
	public void setExt_rate1(BigDecimal ext_rate1) {
        this.ext_rate1 = ext_rate1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getExt_rate2()
	 */
    @Override
	public BigDecimal getExt_rate2() {
        return ext_rate2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setExt_rate2(java.math.BigDecimal)
	 */
    @Override
	public void setExt_rate2(BigDecimal ext_rate2) {
        this.ext_rate2 = ext_rate2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getExtend_flag()
	 */
    @Override
	public java.lang.Integer getExtend_flag() {
        return extend_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setExtend_flag(java.lang.Integer)
	 */
    @Override
	public void setExtend_flag(java.lang.Integer extend_flag) {
        this.extend_flag = extend_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_end_date()
	 */
    @Override
	public Integer getFact_end_date() {
        return fact_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_end_date(java.lang.Integer)
	 */
    @Override
	public void setFact_end_date(Integer fact_end_date) {
        this.fact_end_date = fact_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_money()
	 */
    @Override
	public java.math.BigDecimal getFact_money() {
        return fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_money(java.math.BigDecimal)
	 */
    @Override
	public void setFact_money(java.math.BigDecimal fact_money) {
        this.fact_money = fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_num()
	 */
    @Override
	public java.lang.Integer getFact_num() {
        return fact_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_num(java.lang.Integer)
	 */
    @Override
	public void setFact_num(java.lang.Integer fact_num) {
        this.fact_num = fact_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_person_num()
	 */
    @Override
	public java.lang.Integer getFact_person_num() {
        return fact_person_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_person_num(java.lang.Integer)
	 */
    @Override
	public void setFact_person_num(java.lang.Integer fact_person_num) {
        this.fact_person_num = fact_person_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_pre_money()
	 */
    @Override
	public java.math.BigDecimal getFact_pre_money() {
        return fact_pre_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_pre_money(java.math.BigDecimal)
	 */
    @Override
	public void setFact_pre_money(java.math.BigDecimal fact_pre_money) {
        this.fact_pre_money = fact_pre_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFact_pre_num()
	 */
    @Override
	public java.lang.Integer getFact_pre_num() {
        return fact_pre_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFact_pre_num(java.lang.Integer)
	 */
    @Override
	public void setFact_pre_num(java.lang.Integer fact_pre_num) {
        this.fact_pre_num = fact_pre_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFee()
	 */
    @Override
	public BigDecimal getFee() {
        return fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFee(java.math.BigDecimal)
	 */
    @Override
	public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getField_cn_name()
	 */
    @Override
	public java.lang.String getField_cn_name() {
        return field_cn_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setField_cn_name(java.lang.String)
	 */
    @Override
	public void setField_cn_name(java.lang.String field_cn_name) {
        this.field_cn_name = field_cn_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getField_name()
	 */
    @Override
	public java.lang.String getField_name() {
        return field_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setField_name(java.lang.String)
	 */
    @Override
	public void setField_name(java.lang.String field_name) {
        this.field_name = field_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFlag()
	 */
    @Override
	public java.lang.Integer getFlag() {
        return flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFlag(java.lang.Integer)
	 */
    @Override
	public void setFlag(java.lang.Integer flag) {
        this.flag = flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFpfs()
	 */
    @Override
	public java.lang.String getFpfs() {
        return fpfs;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFpfs(java.lang.String)
	 */
    @Override
	public void setFpfs(java.lang.String fpfs) {
        this.fpfs = fpfs;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFpfs_name()
	 */
    @Override
	public java.lang.String getFpfs_name() {
        return fpfs_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFpfs_name(java.lang.String)
	 */
    @Override
	public void setFpfs_name(java.lang.String fpfs_name) {
        this.fpfs_name = fpfs_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getFx_fee()
	 */
    @Override
	public java.math.BigDecimal getFx_fee() {
        return fx_fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setFx_fee(java.math.BigDecimal)
	 */
    @Override
	public void setFx_fee(java.math.BigDecimal fx_fee) {
        this.fx_fee = fx_fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGain_money()
	 */
    @Override
	public java.math.BigDecimal getGain_money() {
        return gain_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGain_money(java.math.BigDecimal)
	 */
    @Override
	public void setGain_money(java.math.BigDecimal gain_money) {
        this.gain_money = gain_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGr_amount()
	 */
    @Override
	public java.math.BigDecimal getGr_amount() {
        return gr_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGr_amount(java.math.BigDecimal)
	 */
    @Override
	public void setGr_amount(java.math.BigDecimal gr_amount) {
        this.gr_amount = gr_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGr_count()
	 */
    @Override
	public java.lang.Integer getGr_count() {
        return gr_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGr_count(java.lang.Integer)
	 */
    @Override
	public void setGr_count(java.lang.Integer gr_count) {
        this.gr_count = gr_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGr_money()
	 */
    @Override
	public java.math.BigDecimal getGr_money() {
        return gr_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGr_money(java.math.BigDecimal)
	 */
    @Override
	public void setGr_money(java.math.BigDecimal gr_money) {
        this.gr_money = gr_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGr_num()
	 */
    @Override
	public Integer getGr_num() {
        return gr_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGr_num(java.lang.Integer)
	 */
    @Override
	public void setGr_num(Integer gr_num) {
        this.gr_num = gr_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getHq_date()
	 */
    @Override
	public Integer getHq_date() {
        return hq_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setHq_date(java.lang.Integer)
	 */
    @Override
	public void setHq_date(Integer hq_date) {
        this.hq_date = hq_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getHt_money()
	 */
    @Override
	public java.math.BigDecimal getHt_money() {
        return ht_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setHt_money(java.math.BigDecimal)
	 */
    @Override
	public void setHt_money(java.math.BigDecimal ht_money) {
        this.ht_money = ht_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getInfo_period()
	 */
    @Override
	public java.lang.Integer getInfo_period() {
        return info_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setInfo_period(java.lang.Integer)
	 */
    @Override
	public void setInfo_period(java.lang.Integer info_period) {
        this.info_period = info_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getInput_man()
	 */
    @Override
	public java.lang.Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(java.lang.Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getInput_time()
	 */
    @Override
	public java.sql.Timestamp getInput_time() {
        return input_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setInput_time(java.sql.Timestamp)
	 */
    @Override
	public void setInput_time(java.sql.Timestamp input_time) {
        this.input_time = input_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_flag1()
	 */
    @Override
	public java.lang.Integer getIntrust_flag1() {
        return intrust_flag1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_flag1(java.lang.Integer)
	 */
    @Override
	public void setIntrust_flag1(java.lang.Integer intrust_flag1) {
        this.intrust_flag1 = intrust_flag1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_flag1_name()
	 */
    @Override
	public String getIntrust_flag1_name() {
        return intrust_flag1_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_flag1_name(java.lang.String)
	 */
    @Override
	public void setIntrust_flag1_name(String intrust_flag1_name) {
        this.intrust_flag1_name = intrust_flag1_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_flag2()
	 */
    @Override
	public java.lang.Integer getIntrust_flag2() {
        return intrust_flag2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_flag2(java.lang.Integer)
	 */
    @Override
	public void setIntrust_flag2(java.lang.Integer intrust_flag2) {
        this.intrust_flag2 = intrust_flag2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_flag3()
	 */
    @Override
	public java.lang.Integer getIntrust_flag3() {
        return intrust_flag3;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_flag3(java.lang.Integer)
	 */
    @Override
	public void setIntrust_flag3(java.lang.Integer intrust_flag3) {
        this.intrust_flag3 = intrust_flag3;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_flag4()
	 */
    @Override
	public java.lang.Integer getIntrust_flag4() {
        return intrust_flag4;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_flag4(java.lang.Integer)
	 */
    @Override
	public void setIntrust_flag4(java.lang.Integer intrust_flag4) {
        this.intrust_flag4 = intrust_flag4;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type()
	 */
    @Override
	public java.lang.String getIntrust_type() {
        return intrust_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type(java.lang.String)
	 */
    @Override
	public void setIntrust_type(java.lang.String intrust_type) {
        this.intrust_type = intrust_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type_name()
	 */
    @Override
	public java.lang.String getIntrust_type_name() {
        return intrust_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type_name(java.lang.String)
	 */
    @Override
	public void setIntrust_type_name(java.lang.String intrust_type_name) {
        this.intrust_type_name = intrust_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type1()
	 */
    @Override
	public java.lang.String getIntrust_type1() {
        return intrust_type1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type1(java.lang.String)
	 */
    @Override
	public void setIntrust_type1(java.lang.String intrust_type1) {
        this.intrust_type1 = intrust_type1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type1_name()
	 */
    @Override
	public java.lang.String getIntrust_type1_name() {
        return intrust_type1_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type1_name(java.lang.String)
	 */
    @Override
	public void setIntrust_type1_name(java.lang.String intrust_type1_name) {
        this.intrust_type1_name = intrust_type1_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type2()
	 */
    @Override
	public java.lang.String getIntrust_type2() {
        return intrust_type2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type2(java.lang.String)
	 */
    @Override
	public void setIntrust_type2(java.lang.String intrust_type2) {
        this.intrust_type2 = intrust_type2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getIntrust_type2_name()
	 */
    @Override
	public java.lang.String getIntrust_type2_name() {
        return intrust_type2_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setIntrust_type2_name(java.lang.String)
	 */
    @Override
	public void setIntrust_type2_name(java.lang.String intrust_type2_name) {
        this.intrust_type2_name = intrust_type2_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getInvest_type()
	 */
    @Override
	public String getInvest_type() {
        return invest_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setInvest_type(java.lang.String)
	 */
    @Override
	public void setInvest_type(String invest_type) {
        this.invest_type = invest_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getItem_code()
	 */
    @Override
	public java.lang.String getItem_code() {
        return item_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setItem_code(java.lang.String)
	 */
    @Override
	public void setItem_code(java.lang.String item_code) {
        this.item_code = item_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getItem_id()
	 */
    @Override
	public java.lang.Integer getItem_id() {
        return item_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setItem_id(java.lang.Integer)
	 */
    @Override
	public void setItem_id(java.lang.Integer item_id) {
        this.item_id = item_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getJg_amount()
	 */
    @Override
	public java.math.BigDecimal getJg_amount() {
        return jg_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setJg_amount(java.math.BigDecimal)
	 */
    @Override
	public void setJg_amount(java.math.BigDecimal jg_amount) {
        this.jg_amount = jg_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getJg_count()
	 */
    @Override
	public java.lang.Integer getJg_count() {
        return jg_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setJg_count(java.lang.Integer)
	 */
    @Override
	public void setJg_count(java.lang.Integer jg_count) {
        this.jg_count = jg_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getJg_money()
	 */
    @Override
	public java.math.BigDecimal getJg_money() {
        return jg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setJg_money(java.math.BigDecimal)
	 */
    @Override
	public void setJg_money(java.math.BigDecimal jg_money) {
        this.jg_money = jg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getJg_num()
	 */
    @Override
	public Integer getJg_num() {
        return jg_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setJg_num(java.lang.Integer)
	 */
    @Override
	public void setJg_num(Integer jg_num) {
        this.jg_num = jg_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getLast_post_date()
	 */
    @Override
	public java.lang.Integer getLast_post_date() {
        return last_post_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setLast_post_date(java.lang.Integer)
	 */
    @Override
	public void setLast_post_date(java.lang.Integer last_post_date) {
        this.last_post_date = last_post_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getList_id()
	 */
    @Override
	public Integer getList_id() {
        return list_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setList_id(java.lang.Integer)
	 */
    @Override
	public void setList_id(Integer list_id) {
        this.list_id = list_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getList_name()
	 */
    @Override
	public String getList_name() {
        return list_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setList_name(java.lang.String)
	 */
    @Override
	public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getManage_fee()
	 */
    @Override
	public java.math.BigDecimal getManage_fee() {
        return manage_fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setManage_fee(java.math.BigDecimal)
	 */
    @Override
	public void setManage_fee(java.math.BigDecimal manage_fee) {
        this.manage_fee = manage_fee;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getManage_rate()
	 */
    @Override
	public java.math.BigDecimal getManage_rate() {
        return manage_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setManage_rate(java.math.BigDecimal)
	 */
    @Override
	public void setManage_rate(java.math.BigDecimal manage_rate) {
        this.manage_rate = manage_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getManager_type()
	 */
    @Override
	public Integer getManager_type() {
        return manager_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setManager_type(java.lang.Integer)
	 */
    @Override
	public void setManager_type(Integer manager_type) {
        this.manager_type = manager_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getMatain_manager()
	 */
    @Override
	public java.lang.Integer getMatain_manager() {
        return matain_manager;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setMatain_manager(java.lang.Integer)
	 */
    @Override
	public void setMatain_manager(java.lang.Integer matain_manager) {
        this.matain_manager = matain_manager;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getMin_money()
	 */
    @Override
	public java.math.BigDecimal getMin_money() {
        return min_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setMin_money(java.math.BigDecimal)
	 */
    @Override
	public void setMin_money(java.math.BigDecimal min_money) {
        this.min_money = min_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNav_float_num()
	 */
    @Override
	public Integer getNav_float_num() {
        return nav_float_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNav_float_num(java.lang.Integer)
	 */
    @Override
	public void setNav_float_num(Integer nav_float_num) {
        this.nav_float_num = nav_float_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNav_price()
	 */
    @Override
	public java.math.BigDecimal getNav_price() {
        return nav_price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNav_price(java.math.BigDecimal)
	 */
    @Override
	public void setNav_price(java.math.BigDecimal nav_price) {
        this.nav_price = nav_price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNav_price1()
	 */
    @Override
	public BigDecimal getNav_price1() {
        return nav_price1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNav_price1(java.math.BigDecimal)
	 */
    @Override
	public void setNav_price1(BigDecimal nav_price1) {
        this.nav_price1 = nav_price1;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNav_price2()
	 */
    @Override
	public BigDecimal getNav_price2() {
        return nav_price2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNav_price2(java.math.BigDecimal)
	 */
    @Override
	public void setNav_price2(BigDecimal nav_price2) {
        this.nav_price2 = nav_price2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNew_end_date()
	 */
    @Override
	public Integer getNew_end_date() {
        return new_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNew_end_date(java.lang.Integer)
	 */
    @Override
	public void setNew_end_date(Integer new_end_date) {
        this.new_end_date = new_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getNew_field_info()
	 */
    @Override
	public java.lang.String getNew_field_info() {
        return new_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setNew_field_info(java.lang.String)
	 */
    @Override
	public void setNew_field_info(java.lang.String new_field_info) {
        this.new_field_info = new_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOld_end_date()
	 */
    @Override
	public Integer getOld_end_date() {
        return old_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOld_end_date(java.lang.Integer)
	 */
    @Override
	public void setOld_end_date(Integer old_end_date) {
        this.old_end_date = old_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOld_field_info()
	 */
    @Override
	public java.lang.String getOld_field_info() {
        return old_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOld_field_info(java.lang.String)
	 */
    @Override
	public void setOld_field_info(java.lang.String old_field_info) {
        this.old_field_info = old_field_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOp_code()
	 */
    @Override
	public Integer getOp_code() {
        return op_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOp_code(java.lang.Integer)
	 */
    @Override
	public void setOp_code(Integer op_code) {
        this.op_code = op_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOp_name()
	 */
    @Override
	public String getOp_name() {
        return op_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOp_name(java.lang.String)
	 */
    @Override
	public void setOp_name(String op_name) {
        this.op_name = op_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOpen_flag()
	 */
    @Override
	public java.lang.Integer getOpen_flag() {
        return open_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOpen_flag(java.lang.Integer)
	 */
    @Override
	public void setOpen_flag(java.lang.Integer open_flag) {
        this.open_flag = open_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOpen_flag_name()
	 */
    @Override
	public java.lang.String getOpen_flag_name() {
        return open_flag_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOpen_flag_name(java.lang.String)
	 */
    @Override
	public void setOpen_flag_name(java.lang.String open_flag_name) {
        this.open_flag_name = open_flag_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOrg_count()
	 */
    @Override
	public Integer getOrg_count() {
        return org_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOrg_count(java.lang.Integer)
	 */
    @Override
	public void setOrg_count(Integer org_count) {
        this.org_count = org_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPeriod_unit()
	 */
    @Override
	public Integer getPeriod_unit() {
        return period_unit;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPeriod_unit(java.lang.Integer)
	 */
    @Override
	public void setPeriod_unit(Integer period_unit) {
        this.period_unit = period_unit;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPerson_count()
	 */
    @Override
	public Integer getPerson_count() {
        return person_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPerson_count(java.lang.Integer)
	 */
    @Override
	public void setPerson_count(Integer person_count) {
        this.person_count = person_count;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_code()
	 */
    @Override
	public java.lang.String getPre_code() {
        return pre_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_code(java.lang.String)
	 */
    @Override
	public void setPre_code(java.lang.String pre_code) {
        this.pre_code = pre_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_end_date()
	 */
    @Override
	public java.lang.Integer getPre_end_date() {
        return pre_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_end_date(java.lang.Integer)
	 */
    @Override
	public void setPre_end_date(java.lang.Integer pre_end_date) {
        this.pre_end_date = pre_end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_max_money()
	 */
    @Override
	public java.math.BigDecimal getPre_max_money() {
        return pre_max_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_max_money(java.math.BigDecimal)
	 */
    @Override
	public void setPre_max_money(java.math.BigDecimal pre_max_money) {
        this.pre_max_money = pre_max_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_max_num()
	 */
    @Override
	public java.lang.Integer getPre_max_num() {
        return pre_max_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_max_num(java.lang.Integer)
	 */
    @Override
	public void setPre_max_num(java.lang.Integer pre_max_num) {
        this.pre_max_num = pre_max_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_max_rate()
	 */
    @Override
	public java.math.BigDecimal getPre_max_rate() {
        return pre_max_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_max_rate(java.math.BigDecimal)
	 */
    @Override
	public void setPre_max_rate(java.math.BigDecimal pre_max_rate) {
        this.pre_max_rate = pre_max_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_money()
	 */
    @Override
	public java.math.BigDecimal getPre_money() {
        return pre_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_money(java.math.BigDecimal)
	 */
    @Override
	public void setPre_money(java.math.BigDecimal pre_money) {
        this.pre_money = pre_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_num()
	 */
    @Override
	public java.lang.Integer getPre_num() {
        return pre_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_num(java.lang.Integer)
	 */
    @Override
	public void setPre_num(java.lang.Integer pre_num) {
        this.pre_num = pre_num;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPre_start_date()
	 */
    @Override
	public java.lang.Integer getPre_start_date() {
        return pre_start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPre_start_date(java.lang.Integer)
	 */
    @Override
	public void setPre_start_date(java.lang.Integer pre_start_date) {
        this.pre_start_date = pre_start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_code()
	 */
    @Override
	public java.lang.String getProduct_code() {
        return product_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_code(java.lang.String)
	 */
    @Override
	public void setProduct_code(java.lang.String product_code) {
        this.product_code = product_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_from()
	 */
    @Override
	public java.lang.Integer getProduct_from() {
        return product_from;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_from(java.lang.Integer)
	 */
    @Override
	public void setProduct_from(java.lang.Integer product_from) {
        this.product_from = product_from;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_id()
	 */
    @Override
	public java.lang.Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(java.lang.Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_info()
	 */
    @Override
	public java.lang.String getProduct_info() {
        return product_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_info(java.lang.String)
	 */
    @Override
	public void setProduct_info(java.lang.String product_info) {
        this.product_info = product_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_jc()
	 */
    @Override
	public java.lang.String getProduct_jc() {
        return product_jc;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_jc(java.lang.String)
	 */
    @Override
	public void setProduct_jc(java.lang.String product_jc) {
        this.product_jc = product_jc;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_name()
	 */
    @Override
	public java.lang.String getProduct_name() {
        return product_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_name(java.lang.String)
	 */
    @Override
	public void setProduct_name(java.lang.String product_name) {
        this.product_name = product_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_status()
	 */
    @Override
	public java.lang.String getProduct_status() {
        return product_status;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_status(java.lang.String)
	 */
    @Override
	public void setProduct_status(java.lang.String product_status) {
        this.product_status = product_status;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProduct_status_name()
	 */
    @Override
	public java.lang.String getProduct_status_name() {
        return product_status_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProduct_status_name(java.lang.String)
	 */
    @Override
	public void setProduct_status_name(java.lang.String product_status_name) {
        this.product_status_name = product_status_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProductstatusName()
	 */
    @Override
	public String getProductstatusName() {
        return productstatusName;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProductstatusName(java.lang.String)
	 */
    @Override
	public void setProductstatusName(String productstatusName) {
        this.productstatusName = productstatusName;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProv_level()
	 */
    @Override
	public java.lang.String getProv_level() {
        return prov_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProv_level(java.lang.String)
	 */
    @Override
	public void setProv_level(java.lang.String prov_level) {
        this.prov_level = prov_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProv_level_a_money()
	 */
    @Override
	public java.math.BigDecimal getProv_level_a_money() {
        return prov_level_a_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProv_level_a_money(java.math.BigDecimal)
	 */
    @Override
	public void setProv_level_a_money(java.math.BigDecimal prov_level_a_money) {
        this.prov_level_a_money = prov_level_a_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProv_level_b_money()
	 */
    @Override
	public java.math.BigDecimal getProv_level_b_money() {
        return prov_level_b_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProv_level_b_money(java.math.BigDecimal)
	 */
    @Override
	public void setProv_level_b_money(java.math.BigDecimal prov_level_b_money) {
        this.prov_level_b_money = prov_level_b_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProv_level_name()
	 */
    @Override
	public java.lang.String getProv_level_name() {
        return prov_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProv_level_name(java.lang.String)
	 */
    @Override
	public void setProv_level_name(java.lang.String prov_level_name) {
        this.prov_level_name = prov_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQuality_level()
	 */
    @Override
	public java.lang.String getQuality_level() {
        return quality_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQuality_level(java.lang.String)
	 */
    @Override
	public void setQuality_level(java.lang.String quality_level) {
        this.quality_level = quality_level;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQuality_level_name()
	 */
    @Override
	public java.lang.String getQuality_level_name() {
        return quality_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQuality_level_name(java.lang.String)
	 */
    @Override
	public void setQuality_level_name(java.lang.String quality_level_name) {
        this.quality_level_name = quality_level_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSAdmin_man()
	 */
    @Override
	public String getSAdmin_man() {
        return sAdmin_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSAdmin_man(java.lang.String)
	 */
    @Override
	public void setSAdmin_man(String admin_man) {
        sAdmin_man = admin_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSAdmin_man2()
	 */
    @Override
	public String getSAdmin_man2() {
        return sAdmin_man2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSAdmin_man2(java.lang.String)
	 */
    @Override
	public void setSAdmin_man2(String admin_man2) {
        sAdmin_man2 = admin_man2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSerial_no()
	 */
    @Override
	public java.lang.Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(java.lang.Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSl_flag()
	 */
    @Override
	public java.lang.Integer getSl_flag() {
        return sl_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSl_flag(java.lang.Integer)
	 */
    @Override
	public void setSl_flag(java.lang.Integer sl_flag) {
        this.sl_flag = sl_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSMatain_man()
	 */
    @Override
	public String getSMatain_man() {
        return sMatain_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSMatain_man(java.lang.String)
	 */
    @Override
	public void setSMatain_man(String matain_man) {
        sMatain_man = matain_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getStart_date()
	 */
    @Override
	public java.lang.Integer getStart_date() {
        return start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setStart_date(java.lang.Integer)
	 */
    @Override
	public void setStart_date(java.lang.Integer start_date) {
        this.start_date = start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getStart_date2()
	 */
    @Override
	public java.lang.Integer getStart_date2() {
        return start_date2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setStart_date2(java.lang.Integer)
	 */
    @Override
	public void setStart_date2(java.lang.Integer start_date2) {
        this.start_date2 = start_date2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_check_flag()
	 */
    @Override
	public java.lang.Integer getSub_check_flag() {
        return sub_check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_check_flag(java.lang.Integer)
	 */
    @Override
	public void setSub_check_flag(java.lang.Integer sub_check_flag) {
        this.sub_check_flag = sub_check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_flag()
	 */
    @Override
	public Integer getSub_flag() {
        return sub_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_flag(java.lang.Integer)
	 */
    @Override
	public void setSub_flag(Integer sub_flag) {
        this.sub_flag = sub_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_man()
	 */
    @Override
	public Integer getSub_man() {
        return sub_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_man(java.lang.Integer)
	 */
    @Override
	public void setSub_man(Integer sub_man) {
        this.sub_man = sub_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_man_name()
	 */
    @Override
	public String getSub_man_name() {
        return sub_man_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_man_name(java.lang.String)
	 */
    @Override
	public void setSub_man_name(String sub_man_name) {
        this.sub_man_name = sub_man_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_product_id()
	 */
    @Override
	public Integer getSub_product_id() {
        return sub_product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_product_id(java.lang.Integer)
	 */
    @Override
	public void setSub_product_id(Integer sub_product_id) {
        this.sub_product_id = sub_product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSummary()
	 */
    @Override
	public java.lang.String getSummary() {
        return summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSummary(java.lang.String)
	 */
    @Override
	public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTask_date()
	 */
    @Override
	public Integer getTask_date() {
        return task_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTask_date(java.lang.Integer)
	 */
    @Override
	public void setTask_date(Integer task_date) {
        this.task_date = task_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTax_rate()
	 */
    @Override
	public BigDecimal getTax_rate() {
        return tax_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTax_rate(java.math.BigDecimal)
	 */
    @Override
	public void setTax_rate(BigDecimal tax_rate) {
        this.tax_rate = tax_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_acct_name()
	 */
    @Override
	public String getTg_acct_name() {
        return tg_acct_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_acct_name(java.lang.String)
	 */
    @Override
	public void setTg_acct_name(String tg_acct_name) {
        this.tg_acct_name = tg_acct_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_bank_acct()
	 */
    @Override
	public java.lang.String getTg_bank_acct() {
        return tg_bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_bank_acct(java.lang.String)
	 */
    @Override
	public void setTg_bank_acct(java.lang.String tg_bank_acct) {
        this.tg_bank_acct = tg_bank_acct;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_bank_id()
	 */
    @Override
	public java.lang.String getTg_bank_id() {
        return tg_bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_bank_id(java.lang.String)
	 */
    @Override
	public void setTg_bank_id(java.lang.String tg_bank_id) {
        this.tg_bank_id = tg_bank_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_bank_name()
	 */
    @Override
	public java.lang.String getTg_bank_name() {
        return tg_bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_bank_name(java.lang.String)
	 */
    @Override
	public void setTg_bank_name(java.lang.String tg_bank_name) {
        this.tg_bank_name = tg_bank_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_bank_sub_id()
	 */
    @Override
	public java.lang.String getTg_bank_sub_id() {
        return tg_bank_sub_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_bank_sub_id(java.lang.String)
	 */
    @Override
	public void setTg_bank_sub_id(java.lang.String tg_bank_sub_id) {
        this.tg_bank_sub_id = tg_bank_sub_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTg_bank_sub_name()
	 */
    @Override
	public java.lang.String getTg_bank_sub_name() {
        return tg_bank_sub_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTg_bank_sub_name(java.lang.String)
	 */
    @Override
	public void setTg_bank_sub_name(java.lang.String tg_bank_sub_name) {
        this.tg_bank_sub_name = tg_bank_sub_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTime_flag()
	 */
    @Override
	public Integer getTime_flag() {
        return time_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTime_flag(java.lang.Integer)
	 */
    @Override
	public void setTime_flag(Integer time_flag) {
        this.time_flag = time_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTotal_amount()
	 */
    @Override
	public java.math.BigDecimal getTotal_amount() {
        return total_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTotal_amount(java.math.BigDecimal)
	 */
    @Override
	public void setTotal_amount(java.math.BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTotal_fact_money()
	 */
    @Override
	public String[] getTotal_fact_money() {
        return total_fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTotal_fact_money(java.lang.String[])
	 */
    @Override
	public void setTotal_fact_money(String[] total_fact_money) {
        this.total_fact_money = total_fact_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTotal_money()
	 */
    @Override
	public java.math.BigDecimal getTotal_money() {
        return total_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTotal_money(java.math.BigDecimal)
	 */
    @Override
	public void setTotal_money(java.math.BigDecimal total_money) {
        this.total_money = total_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_date()
	 */
    @Override
	public java.lang.Integer getTrade_date() {
        return trade_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_date(java.lang.Integer)
	 */
    @Override
	public void setTrade_date(java.lang.Integer trade_date) {
        this.trade_date = trade_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_rate()
	 */
    @Override
	public java.math.BigDecimal getTrade_rate() {
        return trade_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_rate(java.math.BigDecimal)
	 */
    @Override
	public void setTrade_rate(java.math.BigDecimal trade_rate) {
        this.trade_rate = trade_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_tax_rate()
	 */
    @Override
	public BigDecimal getTrade_tax_rate() {
        return trade_tax_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_tax_rate(java.math.BigDecimal)
	 */
    @Override
	public void setTrade_tax_rate(BigDecimal trade_tax_rate) {
        this.trade_tax_rate = trade_tax_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_time()
	 */
    @Override
	public Timestamp getTrade_time() {
        return trade_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_time(java.sql.Timestamp)
	 */
    @Override
	public void setTrade_time(Timestamp trade_time) {
        this.trade_time = trade_time;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_type()
	 */
    @Override
	public java.lang.String getTrade_type() {
        return trade_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_type(java.lang.String)
	 */
    @Override
	public void setTrade_type(java.lang.String trade_type) {
        this.trade_type = trade_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrade_type_name()
	 */
    @Override
	public java.lang.String getTrade_type_name() {
        return trade_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrade_type_name(java.lang.String)
	 */
    @Override
	public void setTrade_type_name(java.lang.String trade_type_name) {
        this.trade_type_name = trade_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrust_contract_name()
	 */
    @Override
	public String getTrust_contract_name() {
        return trust_contract_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrust_contract_name(java.lang.String)
	 */
    @Override
	public void setTrust_contract_name(String trust_contract_name) {
        this.trust_contract_name = trust_contract_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getValid_period()
	 */
    @Override
	public java.lang.Integer getValid_period() {
        return valid_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setValid_period(java.lang.Integer)
	 */
    @Override
	public void setValid_period(java.lang.Integer valid_period) {
        this.valid_period = valid_period;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getValid_period2()
	 */
    @Override
	public Integer getValid_period2() {
        return valid_period2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setValid_period2(java.lang.Integer)
	 */
    @Override
	public void setValid_period2(Integer valid_period2) {
        this.valid_period2 = valid_period2;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getZjye()
	 */
    @Override
	public java.math.BigDecimal getZjye() {
        return zjye;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setZjye(java.math.BigDecimal)
	 */
    @Override
	public void setZjye(java.math.BigDecimal zjye) {
        this.zjye = zjye;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getManagetype_name()
	 */
    @Override
	public String getManagetype_name() {
        return managetype_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getResult_standard()
	 */
    @Override
	public BigDecimal getResult_standard() {
        return result_standard;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setResult_standard(java.math.BigDecimal)
	 */
    @Override
	public void setResult_standard(BigDecimal result_standard) {
        this.result_standard = result_standard;
    }
    
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getShare_flag()
	 */
	@Override
	public Integer getShare_flag() {
		return share_flag;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setShare_flag(java.lang.Integer)
	 */
	@Override
	public void setShare_flag(Integer share_flag) {
		this.share_flag = share_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getCoperate_type()
	 */
	@Override
	public Integer getCoperate_type() {
		return coperate_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setCoperate_type(java.lang.Integer)
	 */
	@Override
	public void setCoperate_type(Integer coperate_type) {
		this.coperate_type = coperate_type;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGov_pegional()
	 */
	@Override
	public String getGov_pegional() {
		return gov_pegional;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGov_pegional(java.lang.String)
	 */
	@Override
	public void setGov_pegional(String gov_pegional) {
		this.gov_pegional = gov_pegional;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getGov_prov_pegional()
	 */
	@Override
	public String getGov_prov_pegional() {
		return gov_prov_pegional;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setGov_prov_pegional(java.lang.String)
	 */
	@Override
	public void setGov_prov_pegional(String gov_prov_pegional) {
		this.gov_prov_pegional = gov_prov_pegional;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOrg_money()
	 */
	@Override
	public java.math.BigDecimal getOrg_money() {
		return org_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOrg_money(java.math.BigDecimal)
	 */
	@Override
	public void setOrg_money(java.math.BigDecimal org_money) {
		this.org_money = org_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getPerson_money()
	 */
	@Override
	public java.math.BigDecimal getPerson_money() {
		return person_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setPerson_money(java.math.BigDecimal)
	 */
	@Override
	public void setPerson_money(java.math.BigDecimal person_money) {
		this.person_money = person_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_product_code()
	 */
	@Override
	public String getSub_product_code() {
		return sub_product_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_product_code(java.lang.String)
	 */
	@Override
	public void setSub_product_code(String sub_product_code) {
		this.sub_product_code = sub_product_code;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getOriginal_money()
	 */
    @Override
	public java.math.BigDecimal getOriginal_money() {
		return original_money;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setOriginal_money(java.math.BigDecimal)
	 */
	@Override
	public void setOriginal_money(java.math.BigDecimal originalMoney) {
		original_money = originalMoney;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getWith_bank_flag()
	 */
	@Override
	public Integer getWith_bank_flag() {
		return with_bank_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setWith_bank_flag(java.lang.Integer)
	 */
	@Override
	public void setWith_bank_flag(Integer with_bank_flag) {
		this.with_bank_flag = with_bank_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getSub_fund_flag()
	 */
	@Override
	public Integer getSub_fund_flag() {
		return sub_fund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setSub_fund_flag(java.lang.Integer)
	 */
	@Override
	public void setSub_fund_flag(Integer sub_fund_flag) {
		this.sub_fund_flag = sub_fund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getProv_flag()
	 */
	@Override
	public Integer getProv_flag() {
		return prov_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setProv_flag(java.lang.Integer)
	 */
	@Override
	public void setProv_flag(Integer prov_flag) {
		this.prov_flag = prov_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDeal_flag()
	 */
	@Override
	public Integer getDeal_flag() {
		return deal_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDeal_flag(java.lang.Integer)
	 */
	@Override
	public void setDeal_flag(Integer deal_flag) {
		this.deal_flag = deal_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQualified_count()
	 */
	@Override
	public java.math.BigDecimal getQualified_count() {
		return qualified_count;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQualified_count(java.math.BigDecimal)
	 */
	@Override
	public void setQualified_count(java.math.BigDecimal qualified_count) {
		this.qualified_count = qualified_count;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQualified_money()
	 */
	@Override
	public java.math.BigDecimal getQualified_money() {
		return qualified_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQualified_money(java.math.BigDecimal)
	 */
	@Override
	public void setQualified_money(java.math.BigDecimal qualified_money) {
		this.qualified_money = qualified_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQualified_amount()
	 */
	@Override
	public java.math.BigDecimal getQualified_amount() {
		return qualified_amount;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQualified_amount(java.math.BigDecimal)
	 */
	@Override
	public void setQualified_amount(java.math.BigDecimal qualified_amount) {
		this.qualified_amount = qualified_amount;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getMax_buy_limit()
	 */
	@Override
	public java.math.BigDecimal getMax_buy_limit() {
		return max_buy_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setMax_buy_limit(java.math.BigDecimal)
	 */
	@Override
	public void setMax_buy_limit(java.math.BigDecimal max_buy_limit) {
		this.max_buy_limit = max_buy_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getMin_buy_limit()
	 */
	@Override
	public java.math.BigDecimal getMin_buy_limit() {
		return min_buy_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setMin_buy_limit(java.math.BigDecimal)
	 */
	@Override
	public void setMin_buy_limit(java.math.BigDecimal min_buy_limit) {
		this.min_buy_limit = min_buy_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getTrust_fee_period()
	 */
	@Override
	public Integer getTrust_fee_period() {
		return trust_fee_period;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setTrust_fee_period(java.lang.Integer)
	 */
	@Override
	public void setTrust_fee_period(Integer trust_fee_period) {
		this.trust_fee_period = trust_fee_period;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getDepart_name()
	 */
	@Override
	public String getDepart_name() {
		return depart_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setDepart_name(java.lang.String)
	 */
	@Override
	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getAsfund_flag()
	 */
	@Override
	public Integer getAsfund_flag() {
		return asfund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setAsfund_flag(java.lang.Integer)
	 */
	@Override
	public void setAsfund_flag(Integer asfund_flag) {
		this.asfund_flag = asfund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQualified_flag()
	 */
	@Override
	public Integer getQualified_flag() {
		return qualified_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQualified_flag(java.lang.Integer)
	 */
	@Override
	public void setQualified_flag(Integer qualified_flag) {
		this.qualified_flag = qualified_flag;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#getQualified_num()
	 */
	@Override
	public Integer getQualified_num() {
		return qualified_num;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductLocal#setQualified_num(java.lang.Integer)
	 */
	@Override
	public void setQualified_num(Integer qualified_num) {
		this.qualified_num = qualified_num;
	}
}