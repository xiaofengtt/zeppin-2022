package enfo.crm.intrust;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustDBManager;
import enfo.crm.tools.Utility;

@Component(value="purconfig")
@Scope("prototype")
public class PurconfigBean extends enfo.crm.dao.IntrustBusiBean implements PurconfigLocal {

    private Integer serial_no;

    private Integer product_id;

    private Integer rg_start_money;

    private Integer rg_end_money;

    private Integer fee_type;

    private BigDecimal fee_rate;

    private BigDecimal fee_amount;

    private Integer input_man;

    private String description;

    private BigDecimal value; //比例或者金额

    private String jsfs; //计算方式

    //设置产品的费用计算类别 1 内扣法 2 外扣法
    private Integer purchanse_fee_type;

    private Integer fee_money;

    private Integer between_month;

    private Integer start_date;

    private Integer end_date;

    private BigDecimal rg_money;

    //2008-07-30 YZJ
    private BigDecimal gs_rate;//公司占比;

    private Integer cust_id;

    private BigDecimal to_money;

    private Integer sy_date;

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#listPurConfig()
	 */
    @Override
	public void listPurConfig() throws Exception {
        Object[] param = new Object[5];
        param[0] = serial_no;
        param[1] = product_id;
        param[2] = fee_type;
        param[3] = input_man;
        param[4] = rg_money;
        super.query("{call SP_QUERY_TPURCONFIG(?,?,?,?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#listPurConfigType()
	 */
    @Override
	public void listPurConfigType() throws Exception {
        Object[] param = new Object[2];
        param[0] = product_id;
        param[1] = input_man;
        super.query("{call SP_QUERY_TPURCONFIG_TYPE(?,?)}", param);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#load()
	 */
    @Override
	public void load() throws Exception {
        Object[] param = new Object[3];
        param[0] = serial_no;
        param[1] = fee_type;
        param[2] = input_man;
        super.query("{call SP_QUERY_TPURCONFIG(?,0,?,?)}", param);
        getNext();
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#append()
	 */
    @Override
	public void append() throws Exception {
        try {
            Object[] param = new Object[12];
            param[0] = product_id;
            param[1] = fee_type;
            param[2] = rg_start_money;
            param[3] = rg_end_money;
            param[4] = Utility.parseBigDecimal(fee_rate, null);
            param[5] = Utility.parseBigDecimal(fee_amount, null);
            param[6] = description;
            param[7] = between_month;
            param[8] = start_date;
            param[9] = end_date;
            param[10] = Utility.parseBigDecimal(gs_rate, null);
            param[11] = input_man;
            super.append("{?=call SP_ADD_TPURCONFIG(?,?,?,?,?,?,?,?,?,?,?,?)}",
                    param);
        } catch (Exception e) {
            throw new BusiException("添加申购、认购费率详细信息失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#updateFreeType()
	 */
    @Override
	public void updateFreeType() throws Exception {
        try {
            Object[] param = new Object[3];
            param[0] = product_id;
            param[1] = purchanse_fee_type;
            param[2] = input_man;
            super.update("{?=call SP_MODI_TPRODUCT_FEETYPE(?,?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("计算费率类别设置失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#updatePurConfig()
	 */
    @Override
	public void updatePurConfig() throws Exception {
        try {
            Object[] param = new Object[12];
            param[0] = serial_no;
            param[1] = fee_type;
            param[2] = rg_start_money;
            param[3] = rg_end_money;
            param[4] = Utility.parseBigDecimal(fee_rate, null);
            param[5] = Utility.parseBigDecimal(fee_amount, null);
            param[6] = description;
            param[7] = between_month;
            param[8] = start_date;
            param[9] = end_date;
            param[10] = Utility.parseBigDecimal(gs_rate, null);
            param[11] = input_man;

            super.update(
                    "{?=call SP_MODI_TPURCONFIG(?,?,?,?,?,?,?,?,?,?,?,?)}",
                    param);
        } catch (Exception e) {
            throw new BusiException("修改申购、认购费率详细信息失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#delPurConfig()
	 */
    @Override
	public void delPurConfig() throws Exception {
        try {
            Object[] param = new Object[2];
            param[0] = serial_no;
            param[1] = input_man;
            super.update("{?=call SP_DEL_TPURCONFIG(?,?)}", param);
        } catch (Exception e) {
            throw new BusiException("删除费率失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getNext()
	 */
    @Override
	public boolean getNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            rg_start_money = new Integer(rowset.getInt("RG_START_MONEY"));
            rg_end_money = new Integer(rowset.getInt("RG_END_MONEY"));
            fee_type = new Integer(rowset.getInt("FEE_TYPE"));
            input_man = new Integer(rowset.getInt("INPUT_MAN"));
            fee_rate = rowset.getBigDecimal("FEE_RATE");
            fee_amount = rowset.getBigDecimal("FEE_AMOUNT");
            description = rowset.getString("DESCRIPTION");
            between_month = new Integer(rowset.getInt("BETWEEN_MONTH"));
            start_date = new Integer(rowset.getInt("START_DATE"));
            end_date = new Integer(rowset.getInt("END_DATE"));
            gs_rate = rowset.getBigDecimal("GS_RATE");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getNextFeeType()
	 */
    @Override
	public boolean getNextFeeType() throws Exception {
        boolean b = super.getNext();
        if (b) {
            purchanse_fee_type = new Integer(rowset
                    .getInt("purchanse_fee_type"));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#queryPurconfig()
	 */
    @Override
	public void queryPurconfig() throws Exception {
        try {
            Connection conn = IntrustDBManager.getConnection();
            CallableStatement stmt = conn.prepareCall(
                    "{call SP_INNER_CAL_PRODUCTFEE(?,?,?,?,?,?,?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            stmt.setInt(1, product_id.intValue());
            stmt.setInt(2, cust_id.intValue());
            stmt.setInt(3, fee_type.intValue());
            stmt.setBigDecimal(4, to_money);
            stmt.setInt(5, 0);
            stmt.registerOutParameter(6, Types.DECIMAL);
            stmt.registerOutParameter(7, Types.DECIMAL);
            stmt.execute();
            this.fee_amount = stmt.getBigDecimal(6);
            this.gs_rate = stmt.getBigDecimal(7);

            //DBManager.handleResultCode(stmt.getInt(1));
        } catch (Exception e) {
            throw new BusiException("参数返回失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getBetween_month()
	 */
    @Override
	public Integer getBetween_month() {
        return between_month;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setBetween_month(java.lang.Integer)
	 */
    @Override
	public void setBetween_month(Integer between_month) {
        this.between_month = between_month;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getCust_id()
	 */
    @Override
	public Integer getCust_id() {
        return cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setCust_id(java.lang.Integer)
	 */
    @Override
	public void setCust_id(Integer cust_id) {
        this.cust_id = cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getDescription()
	 */
    @Override
	public String getDescription() {
        return description;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setDescription(java.lang.String)
	 */
    @Override
	public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getEnd_date()
	 */
    @Override
	public Integer getEnd_date() {
        return end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setEnd_date(java.lang.Integer)
	 */
    @Override
	public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getFee_amount()
	 */
    @Override
	public BigDecimal getFee_amount() {
        return fee_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setFee_amount(java.math.BigDecimal)
	 */
    @Override
	public void setFee_amount(BigDecimal fee_amount) {
        this.fee_amount = fee_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getFee_money()
	 */
    @Override
	public Integer getFee_money() {
        return fee_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setFee_money(java.lang.Integer)
	 */
    @Override
	public void setFee_money(Integer fee_money) {
        this.fee_money = fee_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getFee_rate()
	 */
    @Override
	public BigDecimal getFee_rate() {
        return fee_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setFee_rate(java.math.BigDecimal)
	 */
    @Override
	public void setFee_rate(BigDecimal fee_rate) {
        this.fee_rate = fee_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getFee_type()
	 */
    @Override
	public Integer getFee_type() {
        return fee_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setFee_type(java.lang.Integer)
	 */
    @Override
	public void setFee_type(Integer fee_type) {
        this.fee_type = fee_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getGs_rate()
	 */
    @Override
	public BigDecimal getGs_rate() {
        return gs_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setGs_rate(java.math.BigDecimal)
	 */
    @Override
	public void setGs_rate(BigDecimal gs_rate) {
        this.gs_rate = gs_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getInput_man()
	 */
    @Override
	public Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getProduct_id()
	 */
    @Override
	public Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getPurchanse_fee_type()
	 */
    @Override
	public Integer getPurchanse_fee_type() {
        return purchanse_fee_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setPurchanse_fee_type(java.lang.Integer)
	 */
    @Override
	public void setPurchanse_fee_type(Integer purchanse_fee_type) {
        this.purchanse_fee_type = purchanse_fee_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getRg_end_money()
	 */
    @Override
	public Integer getRg_end_money() {
        return rg_end_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setRg_end_money(java.lang.Integer)
	 */
    @Override
	public void setRg_end_money(Integer rg_end_money) {
        this.rg_end_money = rg_end_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getRg_money()
	 */
    @Override
	public BigDecimal getRg_money() {
        return rg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setRg_money(java.math.BigDecimal)
	 */
    @Override
	public void setRg_money(BigDecimal rg_money) {
        this.rg_money = rg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getRg_start_money()
	 */
    @Override
	public Integer getRg_start_money() {
        return rg_start_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setRg_start_money(java.lang.Integer)
	 */
    @Override
	public void setRg_start_money(Integer rg_start_money) {
        this.rg_start_money = rg_start_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getStart_date()
	 */
    @Override
	public Integer getStart_date() {
        return start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setStart_date(java.lang.Integer)
	 */
    @Override
	public void setStart_date(Integer start_date) {
        this.start_date = start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getSy_date()
	 */
    @Override
	public Integer getSy_date() {
        return sy_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setSy_date(java.lang.Integer)
	 */
    @Override
	public void setSy_date(Integer sy_date) {
        this.sy_date = sy_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getTo_money()
	 */
    @Override
	public BigDecimal getTo_money() {
        return to_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#setTo_money(java.math.BigDecimal)
	 */
    @Override
	public void setTo_money(BigDecimal to_money) {
        this.to_money = to_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getJsfs()
	 */
    @Override
	public String getJsfs() {
        return jsfs;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.PurconfigLocal#getValue()
	 */
    @Override
	public BigDecimal getValue() {
        return value;
    }
}