package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;

@Component(value="intrustCapitalInfo")
@Scope("prototype")
public class IntrustCapitalInfoBean extends IntrustBusiBean implements IntrustCapitalInfoLocal {

	 private static final String AddSQL = "{?=call SP_ADD_TCAPITALINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//1109

	    private static final String ModiSQL = "{?=call SP_MODI_TCAPITALINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//1109

	    private static final String DelSQL = "{?=call SP_DEL_TCAPITALINFO(?,?)}";

	    private static final String appendChgsSql = "{?=call SP_ADD_TCAPITALCHANGES(?,?,?,?,?,?,?,?,?)}";

	    private static final String saveChgsSql = "{?=call SP_MODI_TCAPITALCHANGES(?,?,?,?,?,?,?)}";

	    private static final String listChangesSql = "{call SP_QUERY_TCAPITALCHANGES (?,?,?,?,?,?,?,?,?)}";

	    private static final String CopySQL = "{?=call SP_COPY_TCAPITALINFO(?,?,?)}";

	    //固定资产卡片表 by wangc 20061213
	    private static final String addfixassetSql = "{?=call SP_ADD_TFIXASSET(?,?,?,?,?,?,?,?,?)}";

	    private static final String modifixassetSql = "{?=call SP_MODI_TFIXASSET(?,?,?,?,?,?)}";

	    private static final String adddepreciationSql = "{?=call SP_ADD_TDEPRECIATION(?,?,?,?)}";

	    private static final String checkdepreciationSql = "{?=call SP_CHECK_TDEPRECIATION(?,?,?)}";

	    private Integer serial_no = new Integer(0); //	Int

	    private Integer book_code = new Integer(1); //	Int 帐

	    private Integer product_id = new Integer(0); //	Int 产品

	    private Integer capitaloper_type = new Integer(0);

	    //	Int 投资类型，即业务类别TCAPITALOPERTYPE表的SERIAL_NO
	    private String capitaloper_type_name = ""; //	VARCHAR 24投资类型名称

	    private Integer capital_type = new Integer(0);

	    //资产类型，即资产类别TCAPITALTYPE表的SERIAL_NO
	    private String capital_type_name = ""; //资产类型名称

	    private String capital_use = ""; //资产性质用途

	    private String capital_use_name = ""; //资产性质用途名称

	    private String busi_id = ""; //业务类别

	    private String contract_bh = ""; //合同编号

	    private String capital_name = ""; //资产名称

	    private String cust_name = ""; //资产所有者

	    private String capital_manager = ""; //资产管理者

	    private java.math.BigDecimal price; //购买单价

	    private java.math.BigDecimal money; //购买单价

	    private Integer cust_id = new Integer(0); //	Int 产品

	    private java.math.BigDecimal market_price; //市场单价

	    private java.math.BigDecimal market_money; //市场总价

	    private java.math.BigDecimal pg_money; //资产评估价值

	    private String pg_method = ""; //资产评估方法

	    private String insurance = ""; //资产参加保险情况

	    private Integer to_ci_serial_no; //替换被替换后，记录替换该资产的新的资产的序号

	    private String summary = ""; //备注

	    private String capital_no = ""; //

	    private String base_capital_no = ""; //

	    private String status; //资产状态

	    private String status_name; //资产状态名称

	    private Integer input_man;

	    private java.sql.Timestamp input_time;

	    private Integer check_flag;

	    private Integer check_man = new Integer(0); //

	    private java.sql.Timestamp check_time;

	    private String product_name;

	    private String contract_sub_bh;

	    private Integer start_dat;

	    private Integer end_date;

	    private java.math.BigDecimal ht_money; //仅回购使用，罚息利率

	    private java.math.BigDecimal amount; //仅回购使用，罚息利率

	    //	资产替换begin
	    private java.math.BigDecimal fx_rate; //仅回购使用，罚息利率

	    private java.math.BigDecimal fx_money; //仅回购使用，罚息利率

	    private String change_type; //变动类型（即因何种业务而变动），取(3120)

	    private String change_type_name; //变动类型名称

	    private Integer change_date = new Integer(0); //变动日期

	    private Integer fx_days = new Integer(0); //仅回购使用，罚息天数

	    private Integer ci_serial_no = new Integer(0); //仅回购使用，罚息天数

	    private Integer fx_date;

	    //资产替换end
	    //修改资产列表
	    private String field_name;

	    private String field_cn_name;

	    private String old_field_info;

	    private String new_field_info;

	    private String cust_id2;

	    private Integer copynum;

	    private String unit; //资产数量单位

	    //wangc 20061206 固定资产卡片表
	    private Integer card_id;

	    private Integer start_date;

	    private Integer can_use_year;

	    private Integer use_year;

	    private BigDecimal init_balance;

	    private BigDecimal current_balance;

	    private BigDecimal end_balance;

	    private BigDecimal end_rate;

	    private String dep_type = "";

	    private String dep_type_name = "";

	    private Integer last_dep_date;

	    private BigDecimal dep_month;

	    private BigDecimal dep_total;

	    private Integer dep_status;

	    private Integer start_date1;

	    private Integer start_date2;

	    private Integer trade_month;

	    private BigDecimal dep_balance;

	    private Integer rec_no;

	    private Integer trade_date;

	    private java.math.BigDecimal trade_amount;

	    private String in_flag; //收入类别

	    private String in_flag_name;

	    private String sub_code; //贷方科目

	    private String zclb_bh;//资产类别编号

	    private Integer btzr_cust_id;

	    private Integer zc_flag;

	    private String dbfs_type;

	    private Integer tcapitalinfo_serial_no;

	    private java.math.BigDecimal dbzr_rate;
	    
	    private String telphone;//电话
	    
	    private String capital_jg;//资产登记机构,20101109
	    
	    private Integer capital_date;//资产登记日期,20101109

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#checkCapincome()
		 */
	    @Override
		public void checkCapincome() throws BusiException {
	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = sub_code;
	        params[2] = input_man;
	        try {
	            super.update("{?=call SP_CHECK_TCAPINCOME(?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("资产经营收入审核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#delCapincome()
		 */
	    @Override
		public void delCapincome() throws BusiException {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = Utility.parseInt(input_man, new Integer(0));
	        try {
	            super.update("{?=call SP_DEL_TCAPINCOME(?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("资产经营收入删除失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#modiCapincome()
		 */
	    @Override
		public void modiCapincome() throws BusiException {
	        Object[] params = new Object[5];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = Utility.parseInt(trade_date, new Integer(0));
	        params[2] = Utility.parseBigDecimal(trade_amount, new BigDecimal(0));
	        params[3] = in_flag;
	        params[4] = Utility.parseInt(input_man, new Integer(0));
	        try {
	            super.update("{?=call SP_MODI_TCAPINCOME(?,?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("资产经营收入修改失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#appendCapincome()
		 */
	    @Override
		public void appendCapincome() throws BusiException {

	        Object[] params = new Object[5];
	        params[0] = Utility.parseInt(rec_no, new Integer(0));
	        params[1] = Utility.parseInt(start_date, new Integer(0));
	        params[2] = Utility.parseBigDecimal(trade_amount, new BigDecimal(0));
	        params[3] = in_flag;
	        params[4] = Utility.parseInt(input_man, new Integer(0));

	        try {
	            super.append("{?=call SP_ADD_TCAPINCOME(?,?,?,?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("资产经营收入录入失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#Querycapincome()
		 */
	    @Override
		public void Querycapincome() throws Exception {

	        Object[] params = new Object[7];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(rec_no, new Integer(0));
	        params[3] = Utility.parseInt(start_date1, new Integer(0));
	        params[4] = Utility.parseInt(start_date2, new Integer(0));
	        params[5] = Utility.parseInt(in_flag, new Integer(0));
	        params[6] = Utility.parseInt(check_flag, new Integer(0));
	        super.query("{call SP_QUERY_TCAPINCOME(?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNextFixAsset()
		 */
	    @Override
		public boolean getNextFixAsset() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.capital_no = rowset.getString("capital_no");
	            this.capital_name = rowset.getString("capital_name");
	            this.card_id = new Integer(rowset.getInt("CARD_ID"));
	            this.start_date = new Integer(rowset.getInt("START_DATE"));
	            this.can_use_year = new Integer(rowset.getInt("CAN_USE_YEAR"));
	            this.use_year = new Integer(rowset.getInt("USE_YEAR"));
	            this.init_balance = rowset.getBigDecimal("INIT_BALANCE");
	            this.current_balance = rowset.getBigDecimal("CURRENT_BALANCE");
	            this.end_balance = rowset.getBigDecimal("END_BALANCE");
	            this.end_rate = rowset.getBigDecimal("END_RATE");
	            this.dep_type = rowset.getString("DEP_TYPE");
	            this.dep_type_name = rowset.getString("DEP_TYPE_NAME");
	            this.last_dep_date = new Integer(rowset.getInt("LAST_DEP_DATE"));
	            this.dep_month = rowset.getBigDecimal("DEP_MONTH");
	            this.dep_total = rowset.getBigDecimal("DEP_TOTAL");
	            this.dep_status = new Integer(rowset.getInt("DEP_STATUS"));
	            this.summary = rowset.getString("SUMMARY");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNext2()
		 */
	    @Override
		public boolean getNext2() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.capital_name = rowset.getString("capital_name");
	            this.capital_no = rowset.getString("capital_no");
	            this.card_id = new Integer(rowset.getInt("CARD_ID"));
	            this.start_date = new Integer(rowset.getInt("START_DATE"));
	            this.can_use_year = new Integer(rowset.getInt("CAN_USE_YEAR"));
	            this.use_year = new Integer(rowset.getInt("USE_YEAR"));
	            this.init_balance = rowset.getBigDecimal("INIT_BALANCE");
	            this.current_balance = rowset.getBigDecimal("CURRENT_BALANCE");
	            this.end_balance = rowset.getBigDecimal("END_BALANCE");
	            this.end_rate = rowset.getBigDecimal("END_RATE");
	            this.dep_type = rowset.getString("DEP_TYPE");
	            this.dep_type_name = rowset.getString("DEP_TYPE_NAME");
	            this.last_dep_date = new Integer(rowset.getInt("LAST_DEP_DATE"));
	            this.dep_month = rowset.getBigDecimal("DEP_MONTH");
	            this.dep_total = rowset.getBigDecimal("DEP_TOTAL");
	            this.dep_status = new Integer(rowset.getInt("DEP_STATUS"));
	            this.summary = rowset.getString("SUMMARY");
	            this.trade_month = new Integer(rowset.getInt("trade_month"));
	            this.dep_balance = rowset.getBigDecimal("dep_balance");
	            this.check_flag = new Integer(rowset.getInt("check_flag"));
	            this.serial_no = new Integer(rowset.getInt("serial_no"));
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNextDeprecationDetail()
		 */
	    @Override
		public boolean getNextDeprecationDetail() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.start_date = new Integer(rowset.getInt("trade_date"));
	            this.current_balance = rowset.getBigDecimal("CURRENT_BALANCE");
	            this.trade_month = new Integer(rowset.getInt("trade_month"));
	            this.dep_balance = rowset.getBigDecimal("dep_balance");
	            this.check_flag = new Integer(rowset.getInt("check_flag"));
	            this.serial_no = new Integer(rowset.getInt("serial_no"));
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNextCapincome()
		 */
	    @Override
		public boolean getNextCapincome() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            this.rec_no = new Integer(rowset.getInt("REC_NO"));
	            this.trade_date = new Integer(rowset.getInt("trade_date"));
	            this.trade_amount = rowset.getBigDecimal("trade_amount");
	            this.in_flag = rowset.getString("in_flag"); //收入类别1914
	            this.in_flag_name = rowset.getString("in_flag_name");
	            this.check_flag = new Integer(rowset.getInt("check_flag"));
	            this.capital_no = rowset.getString("capital_no");
	            this.capital_name = rowset.getString("capital_name");
	            this.product_id = new Integer(rowset.getInt("product_id"));
	            this.contract_bh = rowset.getString("contract_bh");
	            this.product_name = rowset.getString("product_name");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#Querydeprecation()
		 */
	    @Override
		public void Querydeprecation() throws Exception {

	        Object[] params = new Object[4];
	        params[0] = Utility.parseInt(card_id, new Integer(0));
	        params[1] = capital_name;
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = Utility.parseInt(check_flag, new Integer(0));

	        super.query("{call SP_QUERY_TDEPRECIATION(?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#Querydeprecationdetail()
		 */
	    @Override
		public void Querydeprecationdetail() throws Exception {

	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(card_id, new Integer(0));
	        params[1] = Utility.parseInt(check_flag, new Integer(0));

	        super.query("{call SP_QUERY_TDEPRECIATION_DETAIL(?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#queryFixAsset()
		 */
	    @Override
		public void queryFixAsset() throws Exception {

	        Object[] params = new Object[4];
	        params[0] = Utility.parseInt(card_id, new Integer(0));
	        params[1] = Utility.parseInt(start_date1, new Integer(0));
	        params[2] = Utility.parseInt(start_date2, new Integer(0));
	        params[3] = Utility.parseInt(dep_status, new Integer(0));

	        super.query("{call SP_QUERY_TFIXASSET(?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#appendDepreciation()
		 */
	    @Override
		public void appendDepreciation() throws BusiException {

	        Object[] params = new Object[4];
	        params[0] = Utility.parseInt(book_code, new Integer(0));
	        params[1] = Utility.parseInt(card_id, new Integer(0));
	        params[2] = Utility.parseInt(start_date, new Integer(0));
	        params[3] = Utility.parseInt(input_man, new Integer(0));

	        try {
	            super.update(adddepreciationSql, params);
	        } catch (Exception e) {
	            throw new BusiException("计提固定资产折旧失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#checkDepreciation()
		 */
	    @Override
		public void checkDepreciation() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = Utility.parseInt(check_flag, new Integer(0));
	        params[2] = Utility.parseInt(input_man, new Integer(0));

	        try {
	            super.update(checkdepreciationSql, params);
	        } catch (Exception e) {
	            throw new BusiException("计提固定资产折旧审核失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#appendFixAsset()
		 */
	    @Override
		public void appendFixAsset() throws BusiException {

	        Object[] params = new Object[9];
	        params[0] = Utility.parseInt(card_id, new Integer(0));
	        params[1] = Utility.parseInt(start_date, new Integer(0));
	        params[2] = Utility.parseInt(can_use_year, new Integer(0));
	        params[3] = Utility.parseBigDecimal(end_rate, new BigDecimal(0));
	        params[4] = dep_type;
	        params[5] = Utility.parseInt(last_dep_date, new Integer(0));
	        params[6] = Utility.parseBigDecimal(dep_month, new BigDecimal(0));
	        params[7] = summary;
	        params[8] = input_man;

	        try {
	            super.update(addfixassetSql, params);
	        } catch (Exception e) {
	            throw new BusiException("新建固定资产登记卡片失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#modiFixAsset()
		 */
	    @Override
		public void modiFixAsset() throws BusiException {

	        Object[] params = new Object[6];
	        params[0] = Utility.parseInt(card_id, new Integer(0));
	        params[1] = Utility.parseInt(can_use_year, new Integer(0));
	        params[2] = Utility.parseBigDecimal(end_rate, new BigDecimal(0));
	        params[3] = dep_type;
	        params[4] = summary;
	        params[5] = input_man;

	        try {
	            super.update(modifixassetSql, params);
	        } catch (Exception e) {
	            throw new BusiException("设置固定资产登记卡片失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#append()
		 */
	    @Override
		public void append() throws Exception {
	        super.append();
	        try {
	            Connection conn = IntrustDBManager.getConnection();
	            CallableStatement stmt = conn.prepareCall(AddSQL,
	                    ResultSet.TYPE_SCROLL_INSENSITIVE,
	                    ResultSet.CONCUR_READ_ONLY);
	            stmt.setInt(2, book_code.intValue());
	            if (product_id != null)
	                stmt.setInt(3, product_id.intValue());
	            else
	                stmt.setInt(3, 0);
	            stmt.setInt(4, capitaloper_type.intValue());
	            stmt.setInt(5, capital_type.intValue());
	            stmt.setString(6, capital_use);
	            stmt.setString(7, busi_id);
	            stmt.setString(8, contract_bh);

	            stmt.setString(9, capital_name);
	            if (cust_id != null)
	                stmt.setInt(10, cust_id.intValue());
	            else
	                stmt.setInt(10, 0);

	            stmt.setString(11, capital_manager);
	            stmt.setBigDecimal(12, this.price);
	            stmt.setBigDecimal(13, this.money);
	            stmt.setBigDecimal(14, this.market_price);
	            stmt.setBigDecimal(15, this.market_money);

	            stmt.setBigDecimal(16, this.pg_money);
	            stmt.setString(17, this.pg_method);
	            stmt.setString(18, this.insurance);
	            stmt.setString(19, this.summary);

	            stmt.setInt(20, this.input_man.intValue());
	            stmt.setString(21, this.capital_no);
	            stmt.setBigDecimal(22, this.amount);

	            stmt.setString(23, this.unit);
	            if (start_date != null)
	                stmt.setInt(25, start_date.intValue());
	            else
	                stmt.setInt(25, 0);
	            if (end_date != null)
	                stmt.setInt(26, end_date.intValue());
	            else
	                stmt.setInt(26, 0);
	            stmt.setInt(27, btzr_cust_id == null ? 0 : btzr_cust_id.intValue());
	            stmt.setString(28, capital_jg);//20101109
	            if (capital_date != null)//20101109
	                stmt.setInt(29, capital_date.intValue());
	            else
	                stmt.setInt(29, 0);
	            

	            stmt.registerOutParameter(1, Types.INTEGER);
	            stmt.registerOutParameter(24, Types.INTEGER);
	            stmt.execute();

	            this.serial_no = new Integer(stmt.getInt(24));
	            IntrustDBManager.handleResultCode(stmt.getInt(1));
	            //this.commitTran();
	        } catch (Exception e) {
	            //this.rollTran();
	            throw new BusiException("录入失败: " + e.getMessage());
	        }
	        //		Object[] params = new Object[26];
	        //		params[0] = Utility.parseInt(card_id,new Integer(0));
	        //		params[1] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[2] = Utility.parseBigDecimal(end_rate,new BigDecimal(0));
	        //		params[3] = dep_type;
	        //		params[4] = summary;
	        //		params[5] = input_man;
	        //		params[6] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[7] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[8] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[9] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[10] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[11] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[12] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[13] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[14] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[15] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[16] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[17] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[18] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[19] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[20] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[21] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[22] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[23] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[24] = Utility.parseInt(can_use_year,new Integer(0));
	        //		params[25] = Utility.parseInt(can_use_year,new Integer(0));
	        //		
	        //
	        //		
	        //		try
	        //		{
	        //			super.update(AddSQL,params);
	        //		}
	        //		catch (Exception e)
	        //		{
	        //			throw new BusiException("录入失败: " + e.getMessage());
	        //		}
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#save()
		 */
	    @Override
		public void save() throws BusiException {

	        Object[] params = new Object[27];
	        params[0] = serial_no;
	        params[1] = product_id;
	        params[2] = capitaloper_type;
	        params[3] = capital_type;
	        params[4] = capital_use;
	        params[5] = busi_id;
	        params[6] = contract_bh;
	        params[7] = capital_name;
	        params[8] = cust_id;
	        params[9] = capital_manager;
	        params[10] = price;
	        params[11] = money;
	        params[12] = market_price;
	        params[13] = market_money;
	        params[14] = pg_money;
	        params[15] = pg_method;
	        params[16] = insurance;
	        params[17] = summary;
	        params[18] = input_man;
	        params[19] = capital_no;
	        params[20] = amount;
	        params[21] = unit;
	        params[22] = Utility.parseInt(start_date, new Integer(0));
	        params[23] = Utility.parseInt(end_date, new Integer(0));
	        params[24] = new Integer(btzr_cust_id == null ? 0 : btzr_cust_id
	                .intValue());
	        params[25] = capital_jg;//20101109
	        params[26] = Utility.parseInt(capital_date, new Integer(0));//1109

	        try {
	            super.update(ModiSQL, params);
	        } catch (Exception e) {
	            throw new BusiException("资产信息修改失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#delete()
		 */
	    @Override
		public void delete() throws BusiException {

	        Object[] params = new Object[2];
	        params[0] = serial_no;
	        params[1] = input_man;

	        try {
	            super.delete(DelSQL, params);
	        } catch (Exception e) {
	            throw new BusiException("资产信息删除失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#Copy()
		 */
	    @Override
		public void Copy() throws BusiException {

	        Object[] params = new Object[3];
	        params[0] = serial_no;
	        params[1] = copynum;
	        params[2] = input_man;

	        try {
	            super.update(CopySQL, params);
	        } catch (Exception e) {
	            throw new BusiException("资产信息复制失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#list()
		 */
	    @Override
		public void list() throws Exception {

	        Object[] params = new Object[15];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = capital_type_name;
	        params[4] = capitaloper_type_name;
	        params[5] = capital_name;
	        params[6] = busi_id;
	        params[7] = contract_bh;
	        params[8] = Utility.parseInt(check_flag, new Integer(0));
	        params[9] = status;
	        params[10] = input_man;
	        params[11] = capital_no;
	        params[12] = zc_flag;
	        params[13] = capital_use;
	        params[14] = contract_sub_bh;
	        super.query(
	                "{call SP_QUERY_TCAPITALINFO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#list_bzr()
		 */
	    @Override
		public void list_bzr() throws Exception {

	        Object[] params = new Object[8];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = cust_name;
	        params[3] = busi_id;
	        params[4] = dbfs_type;
	        params[5] = input_man;
	        params[6] = contract_sub_bh;
	        params[7] = Utility.parseInt(serial_no, new Integer(0));
	        super.query("{call SP_QUERY_TDKDBCUSTINFO_TOTAL(?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#list2()
		 */
	    @Override
		public void list2() throws Exception {
	        Object[] params = new Object[10];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = capitaloper_type_name;
	        params[4] = capital_name;
	        params[5] = busi_id;
	        params[6] = contract_bh;
	        params[7] = input_man;
	        params[8] = capital_no;
	        params[9] = Utility.parseInt(check_flag, new Integer(0));
	        super.query("{call SP_QUERY_TCAPITALINFO_FIX(?,?,?,?,?,?,?,?,?,?)}",
	                params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#historyData()
		 */
	    @Override
		public void historyData() throws Exception {

	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = input_man;

	        super.query("{call SP_QUERY_TCAPITALINFODETAIL(?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#listContractCapital()
		 */
	    @Override
		public void listContractCapital() throws Exception {
	        Object[] params = new Object[8];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = busi_id;
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = product_name;
	        params[4] = contract_bh;
	        params[5] = capital_no;
	        params[6] = capital_name;
	        params[7] = input_man;

	        super.query("{call SP_QUERY_CONTRACT(?,?,?,?,?,?,?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#listCapitalModiDetail()
		 */
	    @Override
		public void listCapitalModiDetail() throws Exception {
	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = input_man;
	        super.query("{call SP_QUERY_TCAPITALINFODETAIL(?,?)}", params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapitalModiDetailNext()
		 */
	    @Override
		public boolean getCapitalModiDetailNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            ci_serial_no = new Integer(rowset.getInt("CI_SERIAL_NO"));

	            field_name = rowset.getString("FIELD_NAME");
	            field_cn_name = rowset.getString("FIELD_CN_NAME");
	            old_field_info = rowset.getString("OLD_FIELD_INFO");
	            new_field_info = rowset.getString("new_FIELD_INFO");

	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            ;
	            this.input_time = rowset.getTimestamp("INPUT_TIME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#listNoownerCapital()
		 */
	    @Override
		public void listNoownerCapital() throws Exception {

	        String listSQL2 = "{call SP_QUERY_TCAPITALINFO_NOOWNER(?,?,?,?,?,?)}";
	        Object[] params = new Object[6];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = busi_id;
	        params[4] = capital_no;
	        params[5] = input_man;

	        super.query(listSQL2, params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#listByContractCapital()
		 */
	    @Override
		public void listByContractCapital() throws Exception {

	        String listSQL2 = "{call SP_QUERY_TCAPITALINFO_BYCONTRACT(?,?,?,?,?,?,?)}";
	        Object[] params = new Object[7];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(product_id, new Integer(0));
	        params[2] = busi_id;
	        params[3] = contract_bh;
	        params[4] = capital_no;
	        params[5] = base_capital_no;
	        params[6] = input_man;

	        super.query(listSQL2, params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getContractCapitalNext()
		 */
	    @Override
		public boolean getContractCapitalNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            product_name = rowset.getString("PRODUCT_NAME");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
	            cust_name = rowset.getString("CUST_NAME");
	            cust_id2 = rowset.getString("CUST_ID");
	            start_dat = new Integer(rowset.getInt("START_DATE"));
	            ;
	            end_date = new Integer(rowset.getInt("END_DATE"));

	            ht_money = rowset.getBigDecimal("HT_MONEY");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNextBzr()
		 */
	    @Override
		public boolean getNextBzr() throws Exception {
			boolean b = super.getNext();
			if (b) {
				cust_id = new Integer(rowset.getInt("CUST_ID"));
				cust_name = rowset.getString("CUST_NAME");
				//dbfs_type = rowset.getString("DBFS_TYPE");				
				telphone = rowset.getString("TELPHONE");
			}
			return b;
		}
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNext()
		 */
	    @Override
		public boolean getNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            capitaloper_type = new Integer(rowset.getInt("CAPITALOPER_TYPE"));
	            capitaloper_type_name = rowset.getString("CAPITALOPER_TYPE_NAME");
	            capital_type = new Integer(rowset.getInt("CAPITAL_TYPE"));
	            capital_type_name = rowset.getString("CAPITAL_TYPE_NAME");
	            capital_use = rowset.getString("CAPITAL_USE");
	            capital_use_name = rowset.getString("CAPITAL_USE_NAME");
	            busi_id = rowset.getString("BUSI_ID");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            capital_name = rowset.getString("CAPITAL_NAME");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            cust_name = rowset.getString("CUST_NAME");
	            capital_manager = rowset.getString("CAPITAL_MANAGER");
	            price = rowset.getBigDecimal("PRICE");
	            money = rowset.getBigDecimal("MONEY");
	            market_price = rowset.getBigDecimal("MARKET_PRICE");
	            market_money = rowset.getBigDecimal("MARKET_MONEY");
	            pg_money = rowset.getBigDecimal("PG_MONEY");
	            pg_method = rowset.getString("PG_METHOD");
	            insurance = rowset.getString("INSURANCE");
	            summary = rowset.getString("SUMMARY");
	            to_ci_serial_no = new Integer(rowset.getInt("TO_CI_SERIAL_NO"));
	            status = rowset.getString("STATUS");
	            status_name = rowset.getString("STATUS_NAME");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            capital_no = rowset.getString("CAPITAL_NO");
	            base_capital_no = rowset.getString("BASE_CAPITAL_NO");
	            amount = rowset.getBigDecimal("AMOUNT");
	            unit = rowset.getString("UNIT");
	            start_date = new Integer(rowset.getInt("start_date"));
	            end_date = new Integer(rowset.getInt("end_date"));
	            btzr_cust_id = new Integer(rowset.getInt("BTZ_CUST_ID"));
	            zclb_bh = rowset.getString("ZCLB_BH");
	            contract_sub_bh = rowset.getString("CONTRACT_SUB_BH");
				product_name = rowset.getString("CAPITAL_JG");
				capital_jg = rowset.getString("PRODUCT_NAME");
				capital_date = new Integer(rowset.getInt("capital_date"));//1109
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNextCapital2()
		 */
	    @Override
		public boolean getNextCapital2() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
	            capitaloper_type = new Integer(rowset.getInt("CAPITALOPER_TYPE"));
	            capitaloper_type_name = rowset.getString("CAPITALOPER_TYPE_NAME");
	            capital_type = new Integer(rowset.getInt("CAPITAL_TYPE"));
	            capital_type_name = rowset.getString("CAPITAL_TYPE_NAME");
	            capital_use = rowset.getString("CAPITAL_USE");
	            capital_use_name = rowset.getString("CAPITAL_USE_NAME");
	            busi_id = rowset.getString("BUSI_ID");
	            contract_bh = rowset.getString("CONTRACT_BH");
	            capital_name = rowset.getString("CAPITAL_NAME");
	            cust_id = new Integer(rowset.getInt("CUST_ID"));
	            cust_name = rowset.getString("CUST_NAME");
	            capital_manager = rowset.getString("CAPITAL_MANAGER");
	            price = rowset.getBigDecimal("PRICE");
	            money = rowset.getBigDecimal("MONEY");
	            market_price = rowset.getBigDecimal("MARKET_PRICE");
	            market_money = rowset.getBigDecimal("MARKET_MONEY");
	            pg_money = rowset.getBigDecimal("PG_MONEY");
	            pg_method = rowset.getString("PG_METHOD");
	            insurance = rowset.getString("INSURANCE");
	            summary = rowset.getString("SUMMARY");
	            to_ci_serial_no = new Integer(rowset.getInt("TO_CI_SERIAL_NO"));
	            status = rowset.getString("STATUS");
	            status_name = rowset.getString("STATUS_NAME");
	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            input_time = rowset.getTimestamp("INPUT_TIME");
	            check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            capital_no = rowset.getString("CAPITAL_NO");
	            base_capital_no = rowset.getString("BASE_CAPITAL_NO");
	            amount = rowset.getBigDecimal("AMOUNT");
	            unit = rowset.getString("UNIT");
	            
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#appendChanges()
		 */
	    @Override
		public void appendChanges() throws BusiException {

	        Object[] params = new Object[9];
	        params[0] = ci_serial_no;
	        params[1] = change_type;
	        params[2] = Utility.parseInt(change_date, new Integer(0));
	        params[3] = to_ci_serial_no;
	        params[4] = cust_id;
	        params[5] = this.price;
	        params[6] = this.money;
	        params[7] = this.summary;
	        params[8] = this.input_man;

	        try {
	            super.update(appendChgsSql, params);
	        } catch (Exception e) {
	            throw new BusiException("科目创建失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#saveChanges()
		 */
	    @Override
		public void saveChanges() throws BusiException {

	        Object[] params = new Object[7];
	        params[0] = serial_no;
	        params[1] = this.change_date;
	        params[2] = Utility.parseInt(cust_id, new Integer(0));
	        params[3] = this.price;
	        params[4] = this.money;
	        params[5] = this.summary;
	        params[6] = this.input_man;

	        try {
	            super.update(saveChgsSql, params);
	        } catch (Exception e) {
	            throw new BusiException("科目创建失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#deleteChanges()
		 */
	    @Override
		public void deleteChanges() throws BusiException {

	        //			//IN(输入参数) IN_BOOK_CODE INT 帐套
	        //			//IN_serial_no INT 序列号

	        Object[] params = new Object[2];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = this.input_man;

	        try {
	            super.update("{?=call SP_DEL_TCAPITALCHANGES(?,?)}", params);
	        } catch (Exception e) {
	            throw new BusiException("删除失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#checkChanges()
		 */
	    @Override
		public void checkChanges() throws BusiException {

	        String spsql = "{?=call SP_CHECK_TCAPITALCHANGES(?,?,?,?)}";
	        Object[] params = new Object[4];
	        params[0] = Utility.parseInt(serial_no, new Integer(0));
	        params[1] = Utility.parseInt(check_flag, new Integer(0));
	        params[2] = this.input_man;
	        params[3] = Utility.parseInt(change_date, Utility.getCurrentDate());

	        try {
	            super.update(spsql, params);
	        } catch (Exception e) {
	            throw new BusiException("审核失败: " + e.getMessage());
	        }

	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#listChanges()
		 */
	    @Override
		public void listChanges() throws Exception {

	        Object[] params = new Object[9];
	        params[0] = Utility.parseInt(book_code, new Integer(1));
	        params[1] = Utility.parseInt(serial_no, new Integer(0));
	        params[2] = Utility.parseInt(product_id, new Integer(0));
	        params[3] = contract_bh;
	        params[4] = Utility.parseInt(ci_serial_no, new Integer(0));
	        params[5] = capital_name;
	        params[6] = change_type;
	        params[7] = Utility.parseInt(check_flag, new Integer(0));
	        params[8] = Utility.parseInt(input_man, new Integer(0));

	        super.query(listChangesSql, params);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getChangesNext()
		 */
	    @Override
		public boolean getChangesNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            this.serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            this.ci_serial_no = new Integer(rowset.getInt("CI_SERIAL_NO"));
	            this.capital_name = rowset.getString("CAPITAL_NAME");
	            this.change_type = rowset.getString("CHANGE_TYPE");
	            this.change_type_name = rowset.getString("CHANGE_TYPE_NAME");
	            this.change_date = new Integer(rowset.getInt("CHANGE_DATE"));
	            this.cust_id = new Integer(rowset.getInt("CUST_ID"));
	            this.price = rowset.getBigDecimal("PRICE");
	            this.money = rowset.getBigDecimal("MONEY");
	            this.to_ci_serial_no = new Integer(rowset.getInt("TO_CI_SERIAL_NO"));
	            //this.fx_date=new Integer(rowset.getInt("FX_DATE"));
	            this.fx_money = rowset.getBigDecimal("FX_MONEY");
	            this.fx_rate = rowset.getBigDecimal("FX_RATE");
	            this.fx_days = new Integer(rowset.getInt("FX_DAYS"));
	            this.input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            this.check_flag = new Integer(rowset.getInt("CHECK_FLAG"));
	            this.input_time = rowset.getTimestamp("INPUT_TIME");
	            this.summary = rowset.getString("SUMMARY");
	            this.check_man = new Integer(rowset.getInt("CHECK_MAN"));
	            this.check_time = rowset.getTimestamp("CHECK_TIME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCustDetailNext()
		 */
	    @Override
		public boolean getCustDetailNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            book_code = new Integer(rowset.getInt("BOOK_CODE"));
	            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
	            ci_serial_no = new Integer(rowset.getInt("CI_SERIAL_NO"));

	            field_name = rowset.getString("FIELD_NAME");
	            field_cn_name = rowset.getString("FIELD_CN_NAME");
	            old_field_info = rowset.getString("OLD_FIELD_INFO");
	            new_field_info = rowset.getString("new_FIELD_INFO");

	            input_man = new Integer(rowset.getInt("INPUT_MAN"));
	            ;
	            this.input_time = rowset.getTimestamp("INPUT_TIME");
	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getAmount()
		 */
	    @Override
		public java.math.BigDecimal getAmount() {
	        return amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setAmount(java.math.BigDecimal)
		 */
	    @Override
		public void setAmount(java.math.BigDecimal amount) {
	        this.amount = amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getBase_capital_no()
		 */
	    @Override
		public String getBase_capital_no() {
	        return base_capital_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setBase_capital_no(java.lang.String)
		 */
	    @Override
		public void setBase_capital_no(String base_capital_no) {
	        this.base_capital_no = base_capital_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getBook_code()
		 */
	    @Override
		public Integer getBook_code() {
	        return book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setBook_code(java.lang.Integer)
		 */
	    @Override
		public void setBook_code(Integer book_code) {
	        this.book_code = book_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getBtzr_cust_id()
		 */
	    @Override
		public Integer getBtzr_cust_id() {
	        return btzr_cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setBtzr_cust_id(java.lang.Integer)
		 */
	    @Override
		public void setBtzr_cust_id(Integer btzr_cust_id) {
	        this.btzr_cust_id = btzr_cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getBusi_id()
		 */
	    @Override
		public String getBusi_id() {
	        return busi_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setBusi_id(java.lang.String)
		 */
	    @Override
		public void setBusi_id(String busi_id) {
	        this.busi_id = busi_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCan_use_year()
		 */
	    @Override
		public Integer getCan_use_year() {
	        return can_use_year;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCan_use_year(java.lang.Integer)
		 */
	    @Override
		public void setCan_use_year(Integer can_use_year) {
	        this.can_use_year = can_use_year;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_manager()
		 */
	    @Override
		public String getCapital_manager() {
	        return capital_manager;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_manager(java.lang.String)
		 */
	    @Override
		public void setCapital_manager(String capital_manager) {
	        this.capital_manager = capital_manager;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_name()
		 */
	    @Override
		public String getCapital_name() {
	        return capital_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_name(java.lang.String)
		 */
	    @Override
		public void setCapital_name(String capital_name) {
	        this.capital_name = capital_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_no()
		 */
	    @Override
		public String getCapital_no() {
	        return capital_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_no(java.lang.String)
		 */
	    @Override
		public void setCapital_no(String capital_no) {
	        this.capital_no = capital_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_type()
		 */
	    @Override
		public Integer getCapital_type() {
	        return capital_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_type(java.lang.Integer)
		 */
	    @Override
		public void setCapital_type(Integer capital_type) {
	        this.capital_type = capital_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_type_name()
		 */
	    @Override
		public String getCapital_type_name() {
	        return capital_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_type_name(java.lang.String)
		 */
	    @Override
		public void setCapital_type_name(String capital_type_name) {
	        this.capital_type_name = capital_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_use()
		 */
	    @Override
		public String getCapital_use() {
	        return capital_use;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_use(java.lang.String)
		 */
	    @Override
		public void setCapital_use(String capital_use) {
	        this.capital_use = capital_use;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_use_name()
		 */
	    @Override
		public String getCapital_use_name() {
	        return capital_use_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_use_name(java.lang.String)
		 */
	    @Override
		public void setCapital_use_name(String capital_use_name) {
	        this.capital_use_name = capital_use_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapitaloper_type()
		 */
	    @Override
		public Integer getCapitaloper_type() {
	        return capitaloper_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapitaloper_type(java.lang.Integer)
		 */
	    @Override
		public void setCapitaloper_type(Integer capitaloper_type) {
	        this.capitaloper_type = capitaloper_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapitaloper_type_name()
		 */
	    @Override
		public String getCapitaloper_type_name() {
	        return capitaloper_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapitaloper_type_name(java.lang.String)
		 */
	    @Override
		public void setCapitaloper_type_name(String capitaloper_type_name) {
	        this.capitaloper_type_name = capitaloper_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCard_id()
		 */
	    @Override
		public Integer getCard_id() {
	        return card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCard_id(java.lang.Integer)
		 */
	    @Override
		public void setCard_id(Integer card_id) {
	        this.card_id = card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getChange_date()
		 */
	    @Override
		public Integer getChange_date() {
	        return change_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setChange_date(java.lang.Integer)
		 */
	    @Override
		public void setChange_date(Integer change_date) {
	        this.change_date = change_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getChange_type()
		 */
	    @Override
		public String getChange_type() {
	        return change_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setChange_type(java.lang.String)
		 */
	    @Override
		public void setChange_type(String change_type) {
	        this.change_type = change_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getChange_type_name()
		 */
	    @Override
		public String getChange_type_name() {
	        return change_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setChange_type_name(java.lang.String)
		 */
	    @Override
		public void setChange_type_name(String change_type_name) {
	        this.change_type_name = change_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCheck_flag()
		 */
	    @Override
		public Integer getCheck_flag() {
	        return check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCheck_flag(java.lang.Integer)
		 */
	    @Override
		public void setCheck_flag(Integer check_flag) {
	        this.check_flag = check_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCheck_man()
		 */
	    @Override
		public Integer getCheck_man() {
	        return check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCheck_man(java.lang.Integer)
		 */
	    @Override
		public void setCheck_man(Integer check_man) {
	        this.check_man = check_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCheck_time()
		 */
	    @Override
		public java.sql.Timestamp getCheck_time() {
	        return check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCheck_time(java.sql.Timestamp)
		 */
	    @Override
		public void setCheck_time(java.sql.Timestamp check_time) {
	        this.check_time = check_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCi_serial_no()
		 */
	    @Override
		public Integer getCi_serial_no() {
	        return ci_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCi_serial_no(java.lang.Integer)
		 */
	    @Override
		public void setCi_serial_no(Integer ci_serial_no) {
	        this.ci_serial_no = ci_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getContract_bh()
		 */
	    @Override
		public String getContract_bh() {
	        return contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setContract_bh(java.lang.String)
		 */
	    @Override
		public void setContract_bh(String contract_bh) {
	        this.contract_bh = contract_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getContract_sub_bh()
		 */
	    @Override
		public String getContract_sub_bh() {
	        return contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setContract_sub_bh(java.lang.String)
		 */
	    @Override
		public void setContract_sub_bh(String contract_sub_bh) {
	        this.contract_sub_bh = contract_sub_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCopynum()
		 */
	    @Override
		public Integer getCopynum() {
	        return copynum;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCopynum(java.lang.Integer)
		 */
	    @Override
		public void setCopynum(Integer copynum) {
	        this.copynum = copynum;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCurrent_balance()
		 */
	    @Override
		public BigDecimal getCurrent_balance() {
	        return current_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCurrent_balance(java.math.BigDecimal)
		 */
	    @Override
		public void setCurrent_balance(BigDecimal current_balance) {
	        this.current_balance = current_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCust_id()
		 */
	    @Override
		public Integer getCust_id() {
	        return cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCust_id(java.lang.Integer)
		 */
	    @Override
		public void setCust_id(Integer cust_id) {
	        this.cust_id = cust_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCust_id2()
		 */
	    @Override
		public String getCust_id2() {
	        return cust_id2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCust_id2(java.lang.String)
		 */
	    @Override
		public void setCust_id2(String cust_id2) {
	        this.cust_id2 = cust_id2;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCust_name()
		 */
	    @Override
		public String getCust_name() {
	        return cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCust_name(java.lang.String)
		 */
	    @Override
		public void setCust_name(String cust_name) {
	        this.cust_name = cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_balance()
		 */
	    @Override
		public BigDecimal getDep_balance() {
	        return dep_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_balance(java.math.BigDecimal)
		 */
	    @Override
		public void setDep_balance(BigDecimal dep_balance) {
	        this.dep_balance = dep_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_month()
		 */
	    @Override
		public BigDecimal getDep_month() {
	        return dep_month;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_month(java.math.BigDecimal)
		 */
	    @Override
		public void setDep_month(BigDecimal dep_month) {
	        this.dep_month = dep_month;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_status()
		 */
	    @Override
		public Integer getDep_status() {
	        return dep_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_status(java.lang.Integer)
		 */
	    @Override
		public void setDep_status(Integer dep_status) {
	        this.dep_status = dep_status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_total()
		 */
	    @Override
		public BigDecimal getDep_total() {
	        return dep_total;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_total(java.math.BigDecimal)
		 */
	    @Override
		public void setDep_total(BigDecimal dep_total) {
	        this.dep_total = dep_total;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_type()
		 */
	    @Override
		public String getDep_type() {
	        return dep_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_type(java.lang.String)
		 */
	    @Override
		public void setDep_type(String dep_type) {
	        this.dep_type = dep_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDep_type_name()
		 */
	    @Override
		public String getDep_type_name() {
	        return dep_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDep_type_name(java.lang.String)
		 */
	    @Override
		public void setDep_type_name(String dep_type_name) {
	        this.dep_type_name = dep_type_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getEnd_balance()
		 */
	    @Override
		public BigDecimal getEnd_balance() {
	        return end_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setEnd_balance(java.math.BigDecimal)
		 */
	    @Override
		public void setEnd_balance(BigDecimal end_balance) {
	        this.end_balance = end_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getEnd_date()
		 */
	    @Override
		public Integer getEnd_date() {
	        return end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setEnd_date(java.lang.Integer)
		 */
	    @Override
		public void setEnd_date(Integer end_date) {
	        this.end_date = end_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getEnd_rate()
		 */
	    @Override
		public BigDecimal getEnd_rate() {
	        return end_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setEnd_rate(java.math.BigDecimal)
		 */
	    @Override
		public void setEnd_rate(BigDecimal end_rate) {
	        this.end_rate = end_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getField_cn_name()
		 */
	    @Override
		public String getField_cn_name() {
	        return field_cn_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setField_cn_name(java.lang.String)
		 */
	    @Override
		public void setField_cn_name(String field_cn_name) {
	        this.field_cn_name = field_cn_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getField_name()
		 */
	    @Override
		public String getField_name() {
	        return field_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setField_name(java.lang.String)
		 */
	    @Override
		public void setField_name(String field_name) {
	        this.field_name = field_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getFx_date()
		 */
	    @Override
		public Integer getFx_date() {
	        return fx_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setFx_date(java.lang.Integer)
		 */
	    @Override
		public void setFx_date(Integer fx_date) {
	        this.fx_date = fx_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getFx_days()
		 */
	    @Override
		public Integer getFx_days() {
	        return fx_days;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setFx_days(java.lang.Integer)
		 */
	    @Override
		public void setFx_days(Integer fx_days) {
	        this.fx_days = fx_days;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getFx_money()
		 */
	    @Override
		public java.math.BigDecimal getFx_money() {
	        return fx_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setFx_money(java.math.BigDecimal)
		 */
	    @Override
		public void setFx_money(java.math.BigDecimal fx_money) {
	        this.fx_money = fx_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getFx_rate()
		 */
	    @Override
		public java.math.BigDecimal getFx_rate() {
	        return fx_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setFx_rate(java.math.BigDecimal)
		 */
	    @Override
		public void setFx_rate(java.math.BigDecimal fx_rate) {
	        this.fx_rate = fx_rate;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getHt_money()
		 */
	    @Override
		public java.math.BigDecimal getHt_money() {
	        return ht_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setHt_money(java.math.BigDecimal)
		 */
	    @Override
		public void setHt_money(java.math.BigDecimal ht_money) {
	        this.ht_money = ht_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getIn_flag()
		 */
	    @Override
		public String getIn_flag() {
	        return in_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setIn_flag(java.lang.String)
		 */
	    @Override
		public void setIn_flag(String in_flag) {
	        this.in_flag = in_flag;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getIn_flag_name()
		 */
	    @Override
		public String getIn_flag_name() {
	        return in_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setIn_flag_name(java.lang.String)
		 */
	    @Override
		public void setIn_flag_name(String in_flag_name) {
	        this.in_flag_name = in_flag_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getInit_balance()
		 */
	    @Override
		public BigDecimal getInit_balance() {
	        return init_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setInit_balance(java.math.BigDecimal)
		 */
	    @Override
		public void setInit_balance(BigDecimal init_balance) {
	        this.init_balance = init_balance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getInput_man()
		 */
	    @Override
		public Integer getInput_man() {
	        return input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setInput_man(java.lang.Integer)
		 */
	    @Override
		public void setInput_man(Integer input_man) {
	        this.input_man = input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getInput_time()
		 */
	    @Override
		public java.sql.Timestamp getInput_time() {
	        return input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setInput_time(java.sql.Timestamp)
		 */
	    @Override
		public void setInput_time(java.sql.Timestamp input_time) {
	        this.input_time = input_time;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getInsurance()
		 */
	    @Override
		public String getInsurance() {
	        return insurance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setInsurance(java.lang.String)
		 */
	    @Override
		public void setInsurance(String insurance) {
	        this.insurance = insurance;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getLast_dep_date()
		 */
	    @Override
		public Integer getLast_dep_date() {
	        return last_dep_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setLast_dep_date(java.lang.Integer)
		 */
	    @Override
		public void setLast_dep_date(Integer last_dep_date) {
	        this.last_dep_date = last_dep_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getMarket_money()
		 */
	    @Override
		public java.math.BigDecimal getMarket_money() {
	        return market_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setMarket_money(java.math.BigDecimal)
		 */
	    @Override
		public void setMarket_money(java.math.BigDecimal market_money) {
	        this.market_money = market_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getMarket_price()
		 */
	    @Override
		public java.math.BigDecimal getMarket_price() {
	        return market_price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setMarket_price(java.math.BigDecimal)
		 */
	    @Override
		public void setMarket_price(java.math.BigDecimal market_price) {
	        this.market_price = market_price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getMoney()
		 */
	    @Override
		public java.math.BigDecimal getMoney() {
	        return money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setMoney(java.math.BigDecimal)
		 */
	    @Override
		public void setMoney(java.math.BigDecimal money) {
	        this.money = money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getNew_field_info()
		 */
	    @Override
		public String getNew_field_info() {
	        return new_field_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setNew_field_info(java.lang.String)
		 */
	    @Override
		public void setNew_field_info(String new_field_info) {
	        this.new_field_info = new_field_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getOld_field_info()
		 */
	    @Override
		public String getOld_field_info() {
	        return old_field_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setOld_field_info(java.lang.String)
		 */
	    @Override
		public void setOld_field_info(String old_field_info) {
	        this.old_field_info = old_field_info;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getPg_method()
		 */
	    @Override
		public String getPg_method() {
	        return pg_method;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setPg_method(java.lang.String)
		 */
	    @Override
		public void setPg_method(String pg_method) {
	        this.pg_method = pg_method;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getPg_money()
		 */
	    @Override
		public java.math.BigDecimal getPg_money() {
	        return pg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setPg_money(java.math.BigDecimal)
		 */
	    @Override
		public void setPg_money(java.math.BigDecimal pg_money) {
	        this.pg_money = pg_money;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getPrice()
		 */
	    @Override
		public java.math.BigDecimal getPrice() {
	        return price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setPrice(java.math.BigDecimal)
		 */
	    @Override
		public void setPrice(java.math.BigDecimal price) {
	        this.price = price;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getProduct_id()
		 */
	    @Override
		public Integer getProduct_id() {
	        return product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setProduct_id(java.lang.Integer)
		 */
	    @Override
		public void setProduct_id(Integer product_id) {
	        this.product_id = product_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getProduct_name()
		 */
	    @Override
		public String getProduct_name() {
	        return product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setProduct_name(java.lang.String)
		 */
	    @Override
		public void setProduct_name(String product_name) {
	        this.product_name = product_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getRec_no()
		 */
	    @Override
		public Integer getRec_no() {
	        return rec_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setRec_no(java.lang.Integer)
		 */
	    @Override
		public void setRec_no(Integer rec_no) {
	        this.rec_no = rec_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getSerial_no()
		 */
	    @Override
		public Integer getSerial_no() {
	        return serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setSerial_no(java.lang.Integer)
		 */
	    @Override
		public void setSerial_no(Integer serial_no) {
	        this.serial_no = serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getStart_dat()
		 */
	    @Override
		public Integer getStart_dat() {
	        return start_dat;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStart_dat(java.lang.Integer)
		 */
	    @Override
		public void setStart_dat(Integer start_dat) {
	        this.start_dat = start_dat;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getStart_date()
		 */
	    @Override
		public Integer getStart_date() {
	        return start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStart_date(java.lang.Integer)
		 */
	    @Override
		public void setStart_date(Integer start_date) {
	        this.start_date = start_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getStatus()
		 */
	    @Override
		public String getStatus() {
	        return status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStatus(java.lang.String)
		 */
	    @Override
		public void setStatus(String status) {
	        this.status = status;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getStatus_name()
		 */
	    @Override
		public String getStatus_name() {
	        return status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStatus_name(java.lang.String)
		 */
	    @Override
		public void setStatus_name(String status_name) {
	        this.status_name = status_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getSub_code()
		 */
	    @Override
		public String getSub_code() {
	        return sub_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setSub_code(java.lang.String)
		 */
	    @Override
		public void setSub_code(String sub_code) {
	        this.sub_code = sub_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getSummary()
		 */
	    @Override
		public String getSummary() {
	        return summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setSummary(java.lang.String)
		 */
	    @Override
		public void setSummary(String summary) {
	        this.summary = summary;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTo_ci_serial_no()
		 */
	    @Override
		public Integer getTo_ci_serial_no() {
	        return to_ci_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTo_ci_serial_no(java.lang.Integer)
		 */
	    @Override
		public void setTo_ci_serial_no(Integer to_ci_serial_no) {
	        this.to_ci_serial_no = to_ci_serial_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTrade_amount()
		 */
	    @Override
		public java.math.BigDecimal getTrade_amount() {
	        return trade_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTrade_amount(java.math.BigDecimal)
		 */
	    @Override
		public void setTrade_amount(java.math.BigDecimal trade_amount) {
	        this.trade_amount = trade_amount;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTrade_date()
		 */
	    @Override
		public Integer getTrade_date() {
	        return trade_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTrade_date(java.lang.Integer)
		 */
	    @Override
		public void setTrade_date(Integer trade_date) {
	        this.trade_date = trade_date;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTrade_month()
		 */
	    @Override
		public Integer getTrade_month() {
	        return trade_month;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTrade_month(java.lang.Integer)
		 */
	    @Override
		public void setTrade_month(Integer trade_month) {
	        this.trade_month = trade_month;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getUnit()
		 */
	    @Override
		public String getUnit() {
	        return unit;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setUnit(java.lang.String)
		 */
	    @Override
		public void setUnit(String unit) {
	        this.unit = unit;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getUse_year()
		 */
	    @Override
		public Integer getUse_year() {
	        return use_year;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setUse_year(java.lang.Integer)
		 */
	    @Override
		public void setUse_year(Integer use_year) {
	        this.use_year = use_year;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getZclb_bh()
		 */
	    @Override
		public String getZclb_bh() {
	        return zclb_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setZclb_bh(java.lang.String)
		 */
	    @Override
		public void setZclb_bh(String zclb_bh) {
	        this.zclb_bh = zclb_bh;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStart_date1(java.lang.Integer)
		 */
	    @Override
		public void setStart_date1(Integer start_date1) {
	        this.start_date1 = start_date1;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setStart_date2(java.lang.Integer)
		 */
	    @Override
		public void setStart_date2(Integer start_date2) {
	        this.start_date2 = start_date2;
	    }
	    
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getZc_flag()
		 */
		@Override
		public Integer getZc_flag() {
			return zc_flag;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setZc_flag(java.lang.Integer)
		 */
		@Override
		public void setZc_flag(Integer integer) {
			zc_flag = integer;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDbfs_type(java.lang.String)
		 */
		@Override
		public void setDbfs_type(String string) {
			dbfs_type = string;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDbfs_type()
		 */
		@Override
		public String getDbfs_type() {
			return dbfs_type;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTcapitalinfo_serial_no()
		 */
		@Override
		public Integer getTcapitalinfo_serial_no() {
			return tcapitalinfo_serial_no;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTcapitalinfo_serial_no(java.lang.Integer)
		 */
		@Override
		public void setTcapitalinfo_serial_no(Integer integer) {
			tcapitalinfo_serial_no = integer;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getDbzr_rate()
		 */
		@Override
		public java.math.BigDecimal getDbzr_rate() {
			return dbzr_rate;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setDbzr_rate(java.math.BigDecimal)
		 */
		@Override
		public void setDbzr_rate(java.math.BigDecimal decimal) {
			dbzr_rate = decimal;
		}

		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getTelphone()
		 */
		@Override
		public String getTelphone() {
			return telphone;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setTelphone(java.lang.String)
		 */
		@Override
		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
		
		
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_date()
		 */
		@Override
		public Integer getCapital_date() {
			return capital_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_date(java.lang.Integer)
		 */
		@Override
		public void setCapital_date(Integer capital_date) {
			this.capital_date = capital_date;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#getCapital_jg()
		 */
		@Override
		public String getCapital_jg() {
			return capital_jg;
		}
		/* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCapitalInfoLocal#setCapital_jg(java.lang.String)
		 */
		@Override
		public void setCapital_jg(String capital_jg) {
			this.capital_jg = capital_jg;
		}
}