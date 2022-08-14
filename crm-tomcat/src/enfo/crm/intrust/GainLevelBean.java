 
package enfo.crm.intrust;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;

@Component(value="gainLevel")
@Scope("prototype")
public class GainLevelBean extends IntrustBusiBean implements GainLevelLocal {
	
	private Integer serial_no ;//记录号
	
	private Integer product_id ;//产品ID
	
	private Integer sub_product_id ;//子产品ID
	
	private Integer prov_flag ;//优先级
	
	private String prov_level ;//收益级别编号
	
	private String prov_level_name;//收益级别名称
	
	private BigDecimal lower_limit ;//份额下限
	
	private BigDecimal upper_limit ;//份额下限
	
	private String summary ; //备注
	
	private Integer input_man ;//操作员
	
	private static final String addSql = "{?=CALL SP_ADD_TGAINLEVEL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";//新增收益级别信息
	
	private static final String updateSql = "{?=CALL SP_MODI_TGAINLEVEL(?,?,?,?,?,?,?,?,?,?)}" ;//修改收益级别信息
	
	private static final String delSql = "{?=CALL SP_DEL_TGAINLEVEL(?,?)}" ;//删除收益级别信息
	
	private static final String querySql = "{CALL SP_QUERY_TGAINLEVEL(?,?,?,?,?)}" ;//查询收益级别信息
	
	private static final String queryFlagSql = "{CALL SP_QUERY_TGAINLEVELFLAG(?,?)}" ;//查询已设置的优先级
	
	private static final String gainLevelSql = "{?=CALL SP_CAL_TGAINDETAIL_LEVEL(?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String gainCalEndSql = "{?=CALL SP_CALEND_TGAINDETAIL_LEVEL(?,?,?,?,?,?,?,?,?,?)}";
	
	private static final String queryGainLevelSql = "{CALL SP_QUERY_TGAINDETAIL_LEVEL(?,?,?,?,?,?,?,?,?,?,?)}";

	private String prov_flag_name;

	private Integer sy_date;

	private Integer daysayear;

	private Integer cal_type;

	private BigDecimal sy_money;

	private Integer book_code;

	private String sy_type;

	private Integer check_flag;

	private Integer last_gain_date;

	private BigDecimal bond_tax;

	private BigDecimal sy_amount;

	private String cust_name;

	private String contract_bh;
	
	private Integer start_date ;
	
	private Integer end_date ;
	
	private Integer days ;
	
	private String sy_type_name;
	
	private Integer asfund_flag ;
	
	private Integer gain_flag;
	
	private Integer cust_type;
	
	private BigDecimal sy_rate;
	
	private Integer sy_start_date;
	
	private BigDecimal security_money ;
	
	private BigDecimal float_gain;
	
	private BigDecimal exp_rate; // 预期收益率
	
	private String product_code;
	
	private String product_name;
	
	private BigDecimal gain_rate;
	
	private String sub_product_name;
	
	private Integer open_date;

	private Integer redeem_start_date;

	private Integer redeem_end_date;
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#add()
	 */
	@Override
	public void add() throws Exception {
		Object[] param = new Object[14];
		param[0] = Utility.parseInt(product_id, new Integer(0));
		param[1] = Utility.parseInt(sub_product_id, new Integer(0));
		param[2] = Utility.parseInt(prov_flag, new Integer(0));
		param[3] = Utility.trimNull(prov_level);
		param[4] = Utility.trimNull(prov_level_name);
		param[5] = Utility.parseBigDecimal(lower_limit, new BigDecimal(0));
		param[6] = Utility.parseBigDecimal(upper_limit, new BigDecimal(0));
		param[7] = Utility.trimNull(summary);
		param[8] = Utility.parseInt(input_man, new Integer(0));
		param[9] = Utility.parseInt(cust_type, new Integer(0));

		param[10] = open_date;
		param[11] = redeem_start_date;
		param[12] = redeem_end_date;

		param[13] = Utility.parseBigDecimal(exp_rate, new BigDecimal(0))
				.multiply(new BigDecimal("0.01")); // 注意要乘以0.01
		try {
			super.append(addSql, param);
		} catch (Exception e) {
			throw new BusiException("新增收益级别失败:" + e.getMessage());
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#update()
	 */	
	@Override
	public void update() throws Exception{
		Object[] param = new Object[10];
		param[0] = Utility.parseInt(serial_no ,new Integer(0)) ;
		param[1] = Utility.parseInt(prov_flag , new Integer(0));
		param[2] = Utility.trimNull(prov_level);
		param[3] = Utility.trimNull(prov_level_name);
		param[4] = Utility.parseBigDecimal(lower_limit , new BigDecimal(0));
		param[5] = Utility.parseBigDecimal(upper_limit , new BigDecimal(0));
		param[6] = Utility.trimNull(summary) ;
		param[7] = Utility.parseInt(input_man , new Integer(0));
		param[8] = Utility.parseInt(cust_type , new Integer(0));
		param[9] =  Utility.parseBigDecimal(exp_rate, new BigDecimal(0))
					.multiply(new BigDecimal("0.01")); // 注意要乘以0.01
		try{
			super.update(updateSql , param);
		}catch(Exception e){
			throw new BusiException("修改收益级别失败:"+ e.getMessage()) ;
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#delete()
	 */	
	@Override
	public void delete() throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(serial_no , new Integer(0));
		param[1] = Utility.parseInt(input_man , new Integer(0));
		try{
			super.delete(delSql , param) ;
		}catch(Exception e){
			throw new BusiException("删除收益级别失败:" + e.getMessage());
		}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#query()
	 */	
	@Override
	public void query() throws Exception{
		Object[] param = new Object[5];
		param[0] = Utility.parseInt(serial_no, new Integer(0));
		param[1] = Utility.parseInt(product_id, new Integer(0));
		param[2] = Utility.parseInt(sub_product_id, new Integer(0));
		param[3] = Utility.parseInt(prov_flag,new Integer(0));
		param[4] = Utility.trimNull(prov_level);
		//try{
			super.query(querySql,param) ;
		//}catch(Exception e){
		//	throw new BusiException("查询失败=" + e.getMessage());
		//}
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#queryLevelFlag()
	 */	
	@Override
	public void queryLevelFlag() throws Exception{
		Object[] param = new Object[2];
		param[0] = Utility.parseInt(product_id, new Integer(0));
		param[1] = Utility.parseInt(sub_product_id, new Integer(0));
		//try{
			super.query("{CALL SP_QUERY_TGAINLEVELFLAG(?,?)}",param) ;
		//}catch(Exception e){
		//	throw new BusiException("查询失败=" + e.getMessage());
		//}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#gainLevelCal(java.lang.Integer)
	 */
	@Override
	public void gainLevelCal(Integer flag) throws Exception{
		String proStr = null;
		if(flag.intValue()==1){
			proStr=gainLevelSql ;
		}else if(flag.intValue() == 2){
			proStr=gainCalEndSql ;
		}else{
			throw new BusiException("计算失败");
		}
		
		Object[] param = new Object[10];
		
		param[0] = Utility.parseInt(product_id, new Integer(0));
		param[1] = Utility.parseInt(sub_product_id, new Integer(0));
		param[2] = Utility.parseInt(sy_start_date,new Integer(0));//收益起始日期
		param[3] = Utility.parseInt(sy_date,new Integer(0));
		param[4] = Utility.parseInt(daysayear,new Integer(0));
		param[5] = Utility.parseInt(input_man,new Integer(0));
		param[6] = Utility.parseInt(gain_flag,new Integer(0));
		param[7] = Utility.parseBigDecimal(sy_rate,new BigDecimal(0)).multiply(new BigDecimal("0.01"));//实际收益率
		param[8] = Utility.parseInt(prov_flag,new Integer(0));
		param[9] = Utility.trimNull(prov_level);

		try{
			super.cud(proStr , param) ;
		}catch(Exception e){
			throw new BusiException("计算失败=" + e.getMessage());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#queryGainLevelCal()
	 */
	
	@Override
	public BigDecimal[] queryGainLevelCal() throws Exception{
				
		Connection conn = IntrustDBManager.getConnection();
		
		String message = "";
		CallableStatement stmt = conn.prepareCall(queryGainLevelSql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		stmt.setInt(1, book_code.intValue());
		stmt.setInt(2, product_id.intValue());
		stmt.setInt(3, sub_product_id.intValue());
		stmt.setString(4, sy_type);
		stmt.setInt(5, check_flag.intValue());
		stmt.setInt(6, sy_date.intValue());
		stmt.setInt(7, input_man.intValue());;
		stmt.setInt(8, Utility.parseInt(prov_flag,new Integer(0)).intValue());
		stmt.setString(9, prov_level);;
		stmt.registerOutParameter(10, Types.DECIMAL);
		stmt.registerOutParameter(11,Types.DECIMAL);
		ResultSet rslist = null;
		BigDecimal[] returnValue = new BigDecimal[2];
		try {
			rslist= stmt.executeQuery();
			rowset.close();
            rowset.populate(rslist);
            returnValue[0] = stmt.getBigDecimal(10);
            returnValue[1] = stmt.getBigDecimal(11);
		} catch (SQLException e) {
			throw new BusiException("查询失败：" +e.getMessage());
		}finally {
            rslist.close();
            stmt.close();
            conn.close();
        }
        countRows();
        countPages();
		return returnValue;
		
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getNextGainLevelCal()
	 */
	@Override
	public boolean getNextGainLevelCal() throws Exception{
		 boolean b = super.getNext();
	       if (b) {
			contract_bh = rowset.getString("CONTRACT_SUB_BH");
			cust_name = rowset.getString("CUST_NAME");
			sy_amount = rowset.getBigDecimal("SY_AMOUNT");
			sy_money =  rowset.getBigDecimal("SY_MONEY2");
			bond_tax =  rowset.getBigDecimal("BOND_TAX");
			last_gain_date = (Integer)rowset.getObject("LAST_GAIN_DATE");
			sy_date = (Integer)rowset.getObject("SY_DATE");
			serial_no = (Integer)rowset.getObject("SERIAL_NO");
			sy_type_name = rowset.getString("SY_TYPE_NAME");
			sy_type = rowset.getString("SY_TYPE");
			
			security_money = rowset.getBigDecimal("SECURITY_MONEY");
			float_gain =  rowset.getBigDecimal("FLOAT_GAIN");
		}
		
		return b;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#gainLevelCheck()
	 */
	@Override
	public void gainLevelCheck() throws Exception{
		
		Object[] param = new Object[6];
		
		param[0] = Utility.parseInt(product_id, new Integer(0));
		param[1] = Utility.parseInt(sub_product_id, new Integer(0));
		param[2] = Utility.parseInt(sy_date,new Integer(0));
		param[3] = Utility.parseInt(sy_type , new Integer(0));
		param[4] = Utility.parseInt(input_man,new Integer(0));
		param[5] = Utility.parseInt(check_flag,new Integer(0));
		
		try{
			super.cud("{?=CALL SP_CHECK_TGAINDETAIL_LEVEL(?,?,?,?,?,?)}" , param) ;
		}catch(Exception e){
			throw new BusiException("收益级别分配审核失败:" + e.getMessage());
		}
	}	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#queryGainDetail()
	 */
	@Override
	public void queryGainDetail() throws Exception{
		
		Object[] param = new Object[1];
		
		param[0] = Utility.parseInt(serial_no, new Integer(0));
		
		try{
			super.query("{CALL SP_QUERY_TGAINDETAILLIST(?)}" , param) ;
		}catch(Exception e){
			throw new BusiException("中间收益时间段明细查询失败:" + e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getNextGainDetailList()
	 */
	@Override
	public boolean getNextGainDetailList() throws Exception{
		 boolean b = super.getNext();
	     if (b) {
			prov_level_name = rowset.getString("PROV_LEVEL_NAME");
			prov_flag_name = rowset.getString("PROV_FLAG_NAME");
			sy_amount = rowset.getBigDecimal("SY_AMOUNT");
			sy_money =  rowset.getBigDecimal("SY_MONEY");
			start_date =  (Integer)rowset.getObject("START_DATE");
			end_date = (Integer)rowset.getObject("END_DATE");
			days = (Integer)rowset.getObject("DAYS");
			bond_tax = rowset.getBigDecimal("SY_RATE");
			daysayear = (Integer)rowset.getObject("DAYSAYEAR");
			
			security_money = rowset.getBigDecimal("SECURITY_MONEY");
			float_gain =  rowset.getBigDecimal("FLOAT_GAIN");
	     }
		
	     return b;
	}
	
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getNextLevel()
	 */
    @Override
	public boolean getNextLevel() throws Exception {
        boolean b = super.getNext();
        if (b) {
        	serial_no = Utility.parseInt(new Integer(rowset
                    .getInt("SERIAL_NO")), new Integer(0));
        	product_id = Utility.parseInt(new Integer(rowset.getInt("PRODUCT_ID")),new Integer(0)) ;
        	sub_product_id = Utility.parseInt(new Integer(rowset.getInt("SUB_PRODUCT_ID")),new Integer(0)) ;
        	prov_level = Utility.trimNull(rowset.getString("PROV_LEVEL")) ;
        	prov_level_name = Utility.trimNull(rowset.getString("PROV_LEVEL_NAME")) ;
        	summary = rowset.getString("SUMMARY") ;
        	lower_limit = Utility.parseBigDecimal(rowset
                    .getBigDecimal("LOWER_LIMIT"), new BigDecimal(0));
        	upper_limit = Utility.parseBigDecimal(rowset
                    .getBigDecimal("UPPER_LIMIT"), new BigDecimal(0));
        	prov_flag = Utility.parseInt(new Integer(rowset.getInt("PROV_FLAG")) , new Integer(0));
        	asfund_flag = Utility.parseInt(new Integer(rowset.getInt("ASFUND_FLAG")) , new Integer(0));
        	gain_flag = Utility.parseInt(new Integer(rowset.getInt("GAIN_FLAG")) , new Integer(0));
        	cust_type = Utility.parseInt(new Integer(rowset.getInt("CUST_TYPE")),new Integer(0));
        	product_code = Utility.trimNull(rowset.getString("PRODUCT_CODE"));
        	product_name = Utility.trimNull(rowset.getString("PRODUCT_NAME"));
        	gain_rate = Utility.parseBigDecimal(rowset
                    .getBigDecimal("GAIN_RATE"), new BigDecimal(0));
        	sub_product_name = Utility.trimNull("("+Utility.trimNull(rowset.getString("SUB_PRODUCT_NAME"))+")");
        }
        return b ;
    } 
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getNextProvFlag()
	 */
    @Override
	public boolean getNextProvFlag() throws Exception{
    	
    	boolean b = super.getNext();
    	if(b){
    		prov_flag = (Integer)rowset.getObject("PROV_FLAG");
    		prov_flag_name = rowset.getString("PROV_FLAG_NAME");
    		
    	}
    	return b;
    	
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#queryProvLevel()
	 */	
	@Override
	public void queryProvLevel() throws Exception{
		Object[] param = new Object[3];
		param[0] = Utility.parseInt(product_id, new Integer(0));
		param[1] = Utility.parseInt(sub_product_id, new Integer(0));
		param[2] = Utility.parseInt(prov_flag, new Integer(0));
		try{
			super.query("{CALL SP_QUERY_TGAINLEVELPROV(?,?,?)}" , param) ;
		}catch(Exception e){
			throw new BusiException("查询失败=" + e.getMessage());
		}
	}	
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getNextProvLevel()
	 */
    @Override
	public boolean getNextProvLevel() throws Exception{
    	
    	boolean b = super.getNext();
    	if(b){
    		prov_level = rowset.getString("PROV_LEVEL");
    		prov_level_name = rowset.getString("PROV_LEVEL_NAME");
    		
    	}
    	return b;
    	
    }
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getInput_man()
	 */
	@Override
	public Integer getInput_man() {
		return input_man;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setInput_man(java.lang.Integer)
	 */
	@Override
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getLower_limit()
	 */
	@Override
	public BigDecimal getLower_limit() {
		return lower_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setLower_limit(java.math.BigDecimal)
	 */
	@Override
	public void setLower_limit(BigDecimal lower_limit) {
		this.lower_limit = lower_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProduct_id()
	 */
	@Override
	public Integer getProduct_id() {
		return product_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProduct_id(java.lang.Integer)
	 */
	@Override
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProv_flag()
	 */
	@Override
	public Integer getProv_flag() {
		return prov_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProv_flag(java.lang.Integer)
	 */
	@Override
	public void setProv_flag(Integer prov_flag) {
		this.prov_flag = prov_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProv_level()
	 */
	@Override
	public String getProv_level() {
		return prov_level;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProv_level(java.lang.String)
	 */
	@Override
	public void setProv_level(String prov_level) {
		this.prov_level = prov_level;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProv_level_name()
	 */
	@Override
	public String getProv_level_name() {
		return prov_level_name;
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProv_level_name(java.lang.String)
	 */
	@Override
	public void setProv_level_name(String prov_level_name) {
		this.prov_level_name = prov_level_name;
	}	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSerial_no()
	 */
	@Override
	public Integer getSerial_no() {
		return serial_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSerial_no(java.lang.Integer)
	 */
	@Override
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSub_product_id()
	 */
	@Override
	public Integer getSub_product_id() {
		return sub_product_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSub_product_id(java.lang.Integer)
	 */
	@Override
	public void setSub_product_id(Integer sub_product_id) {
		this.sub_product_id = sub_product_id;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSummary()
	 */
	@Override
	public String getSummary() {
		return summary;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSummary(java.lang.String)
	 */
	@Override
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getUpper_limit()
	 */
	@Override
	public BigDecimal getUpper_limit() {
		return upper_limit;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setUpper_limit(java.math.BigDecimal)
	 */
	@Override
	public void setUpper_limit(BigDecimal upper_limit) {
		this.upper_limit = upper_limit;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProv_flag_name()
	 */
	@Override
	public String getProv_flag_name() {
		return prov_flag_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProv_flag_name(java.lang.String)
	 */
	@Override
	public void setProv_flag_name(String prov_flag_name) {
		this.prov_flag_name = prov_flag_name;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getCal_type()
	 */
	@Override
	public Integer getCal_type() {
		return cal_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setCal_type(java.lang.Integer)
	 */
	@Override
	public void setCal_type(Integer cal_type) {
		this.cal_type = cal_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getDaysayear()
	 */
	@Override
	public Integer getDaysayear() {
		return daysayear;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setDaysayear(java.lang.Integer)
	 */
	@Override
	public void setDaysayear(Integer daysayear) {
		this.daysayear = daysayear;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_date()
	 */
	@Override
	public Integer getSy_date() {
		return sy_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_date(java.lang.Integer)
	 */
	@Override
	public void setSy_date(Integer sy_date) {
		this.sy_date = sy_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_money()
	 */
	@Override
	public BigDecimal getSy_money() {
		return sy_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_money(java.math.BigDecimal)
	 */
	@Override
	public void setSy_money(BigDecimal sy_money) {
		this.sy_money = sy_money;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getBook_code()
	 */
	@Override
	public Integer getBook_code() {
		return book_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setBook_code(java.lang.Integer)
	 */
	@Override
	public void setBook_code(Integer book_code) {
		this.book_code = book_code;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getCheck_flag()
	 */
	@Override
	public Integer getCheck_flag() {
		return check_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setCheck_flag(java.lang.Integer)
	 */
	@Override
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_type()
	 */
	@Override
	public String getSy_type() {
		return sy_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_type(java.lang.String)
	 */
	@Override
	public void setSy_type(String sy_type) {
		this.sy_type = sy_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getBond_tax()
	 */
	@Override
	public BigDecimal getBond_tax() {
		return bond_tax;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setBond_tax(java.math.BigDecimal)
	 */
	@Override
	public void setBond_tax(BigDecimal bond_tax) {
		this.bond_tax = bond_tax;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getContract_bh()
	 */
	@Override
	public String getContract_bh() {
		return contract_bh;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setContract_bh(java.lang.String)
	 */
	@Override
	public void setContract_bh(String contract_bh) {
		this.contract_bh = contract_bh;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getCust_name()
	 */
	@Override
	public String getCust_name() {
		return cust_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setCust_name(java.lang.String)
	 */
	@Override
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getLast_gain_date()
	 */
	@Override
	public Integer getLast_gain_date() {
		return last_gain_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setLast_gain_date(java.lang.Integer)
	 */
	@Override
	public void setLast_gain_date(Integer last_gain_date) {
		this.last_gain_date = last_gain_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_amount()
	 */
	@Override
	public BigDecimal getSy_amount() {
		return sy_amount;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_amount(java.math.BigDecimal)
	 */
	@Override
	public void setSy_amount(BigDecimal sy_amount) {
		this.sy_amount = sy_amount;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getDays()
	 */
	@Override
	public Integer getDays() {
		return days;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setDays(java.lang.Integer)
	 */
	@Override
	public void setDays(Integer days) {
		this.days = days;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getEnd_date()
	 */
	@Override
	public Integer getEnd_date() {
		return end_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setEnd_date(java.lang.Integer)
	 */
	@Override
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getStart_date()
	 */
	@Override
	public Integer getStart_date() {
		return start_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setStart_date(java.lang.Integer)
	 */
	@Override
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_type_name()
	 */
	@Override
	public String getSy_type_name() {
		return sy_type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_type_name(java.lang.String)
	 */
	@Override
	public void setSy_type_name(String sy_type_name) {
		this.sy_type_name = sy_type_name;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getAsfund_flag()
	 */
	@Override
	public Integer getAsfund_flag() {
		return asfund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setAsfund_flag(java.lang.Integer)
	 */
	@Override
	public void setAsfund_flag(Integer asfund_flag) {
		this.asfund_flag = asfund_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getGain_flag()
	 */
	@Override
	public Integer getGain_flag() {
		return gain_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setGain_flag(java.lang.Integer)
	 */
	@Override
	public void setGain_flag(Integer gain_flag) {
		this.gain_flag = gain_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getCust_type()
	 */
	@Override
	public Integer getCust_type() {
		return cust_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setCust_type(java.lang.Integer)
	 */
	@Override
	public void setCust_type(Integer cust_type) {
		this.cust_type = cust_type;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_rate()
	 */
	@Override
	public BigDecimal getSy_rate() {
		return sy_rate;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_rate(java.math.BigDecimal)
	 */
	@Override
	public void setSy_rate(BigDecimal sy_rate) {
		this.sy_rate = sy_rate;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSy_start_date()
	 */
	@Override
	public Integer getSy_start_date() {
		return sy_start_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSy_start_date(java.lang.Integer)
	 */
	@Override
	public void setSy_start_date(Integer sy_start_date) {
		this.sy_start_date = sy_start_date;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getFloat_gain()
	 */
	@Override
	public BigDecimal getFloat_gain() {
		return float_gain;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setFloat_gain(java.math.BigDecimal)
	 */
	@Override
	public void setFloat_gain(BigDecimal float_gain) {
		this.float_gain = float_gain;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSecurity_money()
	 */
	@Override
	public BigDecimal getSecurity_money() {
		return security_money;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSecurity_money(java.math.BigDecimal)
	 */
	@Override
	public void setSecurity_money(BigDecimal security_money) {
		this.security_money = security_money;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProduct_code()
	 */
	@Override
	public String getProduct_code() {
		return product_code;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProduct_code(java.lang.String)
	 */
	@Override
	public void setProduct_code(String productCode) {
		product_code = productCode;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getProduct_name()
	 */
	@Override
	public String getProduct_name() {
		return product_name;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setProduct_name(java.lang.String)
	 */
	@Override
	public void setProduct_name(String productName) {
		product_name = productName;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getGain_rate()
	 */
	@Override
	public BigDecimal getGain_rate() {
		return gain_rate;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setGain_rate(java.math.BigDecimal)
	 */
	@Override
	public void setGain_rate(BigDecimal gain_rate) {
		this.gain_rate = gain_rate;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getSub_product_name()
	 */
	@Override
	public String getSub_product_name() {
		return sub_product_name;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setSub_product_name(java.lang.String)
	 */
	@Override
	public void setSub_product_name(String subProductName) {
		sub_product_name = subProductName;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getExp_rate()
	 */
	@Override
	public BigDecimal getExp_rate() {
		return exp_rate;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setExp_rate(java.math.BigDecimal)
	 */
	@Override
	public void setExp_rate(BigDecimal exp_rate) {
		this.exp_rate = exp_rate;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getOpen_date()
	 */
	@Override
	public Integer getOpen_date() {
		return open_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setOpen_date(java.lang.Integer)
	 */
	@Override
	public void setOpen_date(Integer open_date) {
		this.open_date = open_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getRedeem_end_date()
	 */
	@Override
	public Integer getRedeem_end_date() {
		return redeem_end_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setRedeem_end_date(java.lang.Integer)
	 */
	@Override
	public void setRedeem_end_date(Integer redeem_end_date) {
		this.redeem_end_date = redeem_end_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#getRedeem_start_date()
	 */
	@Override
	public Integer getRedeem_start_date() {
		return redeem_start_date;
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.GainLevelLocal#setRedeem_start_date(java.lang.Integer)
	 */
	@Override
	public void setRedeem_start_date(Integer redeem_start_date) {
		this.redeem_start_date = redeem_start_date;
	}
}	