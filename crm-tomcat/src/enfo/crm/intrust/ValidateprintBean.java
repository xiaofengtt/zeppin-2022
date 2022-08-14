package enfo.crm.intrust;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.dao.IPageList;
import enfo.crm.tools.Utility;

@Component(value="validateprint")
public class ValidateprintBean extends enfo.crm.dao.IntrustBusiExBean implements ValidateprintLocal {

    private Integer serial_no;

    private String contract_bh;
    
    private String contract_sub_bh;

    private Integer cust_id;

    private Integer chg_type;//1认购 2申购 3赎回 4 份额转让

    private String chg_type_name;

    private BigDecimal appl_amout;

    private BigDecimal fee_money;

    private BigDecimal price;

    private BigDecimal chg_money;

    private BigDecimal chg_amount;

    private BigDecimal after_money;

    private BigDecimal after_amount;

    private Integer sq_date;

    private Integer dz_date;

    private Integer hk_date;

    private String jk_type;

    private String jk_type_name;

    private Integer input_man;

    private Integer product_id;

    private String cust_name;

    private Integer start_date;

    private Integer end_date;

    private Integer ben_start_date;

    private BigDecimal fee_rate;
    
    private Integer print_flag;
    
    private String product_name;
    
    private Integer sub_product_id;
    
    private Integer check_flag;

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#listAll(int, int)
	 */
    @Override
	public IPageList listAll(int pageIndex, int pageSize) throws BusiException {
        IPageList pageList = null;
        Object[] params = new Object[12];
        params[0] = Utility.parseInt(serial_no, null);
        params[1] = Utility.parseInt(product_id, null);
        params[2] = contract_bh;
        params[3] = cust_name;
        params[4] = Utility.parseInt(chg_type, null);
        params[5] = Utility.parseInt(start_date, null);
        params[6] = Utility.parseInt(end_date, null);
        params[7] = Utility.parseInt(input_man, null);
        
        params[8] = "";
        params[9] = Utility.parseInt(sub_product_id, new Integer(0));
        params[10] = Utility.parseInt(print_flag, null);
        params[11] = Utility.trimNull(product_name);
        pageList = CrmDBManager.listProcPage(
                "{call SP_QUERY_TBENAMOUNTDETAIL_CRM(?,?,?,?,?,?,?,?,?,?,?,?)}", params,
                pageIndex, pageSize);
        return pageList;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#listAllFz(int, int)
	 */
    @Override
	public IPageList listAllFz(int pageIndex, int pageSize) throws BusiException {
        Object[] params = new Object[11];
        params[0] = serial_no;
        params[1] = product_id;
        params[2] = sub_product_id;        
        params[3] = Utility.trimNull(product_name);
        params[4] = cust_id;
        params[5] = Utility.trimNull(cust_name);
        params[6] = Utility.trimNull(contract_bh);
        params[7] = start_date;
        params[8] = end_date;
        params[9] = Utility.parseInt(check_flag, new Integer(2)); // --资金到账审核标志：1.未审核；9.信托经理已审核，财务未审核；2.财务已审核
        params[10] = input_man;
        return CrmDBManager.listProcPage("{call SP_QUERY_TMONEYDETAIL_4CUST(?,?,?,?,?,?,?,?,?,?,?)}", params, pageIndex, pageSize);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#listAll1(int, int)
	 */
    @Override
	public IPageList listAll1(int pageIndex, int pageSize) throws BusiException {
        IPageList pageList = null;
        Object[] params = new Object[10];
        params[0] = Utility.parseInt(serial_no, null);
        params[1] = Utility.parseInt(product_id, null);
        params[2] = contract_sub_bh;
        params[3] = cust_name;
        params[4] = Utility.parseInt(chg_type, null);
        params[5] = Utility.parseInt(start_date, null);
        params[6] = Utility.parseInt(end_date, null);
        params[7] = Utility.parseInt(input_man, null);
        params[8] = Utility.parseInt(print_flag, null);
        params[9] = Utility.trimNull(product_name);
        pageList = CrmDBManager.listProcPage(
                "{call SP_QUERY_HCUSTZJBD(?,?,?,?,?,?,?,?,?,?)}", params,
                pageIndex, pageSize);
        return pageList;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#listBySql()
	 */
    @Override
	public List listBySql() throws BusiException {
        List list = null;
        Object[] params = new Object[8];
        params[0] = Utility.parseInt(serial_no, null);
        params[1] = Utility.parseInt(product_id, null);
        params[2] = contract_bh;
        params[3] = cust_name;
        params[4] = Utility.parseInt(chg_type, null);
        params[5] = Utility.parseInt(start_date, null);
        params[6] = Utility.parseInt(end_date, null);
        params[7] = Utility.parseInt(input_man, null);
        list = CrmDBManager.listBySql(
                "{call SP_QUERY_TBENAMOUNTDETAIL_CRM(?,?,?,?,?,?,?,?)}", params);
        return list;
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#listBySql1()
	 */
    @Override
	public List listBySql1() throws BusiException {
        List list = null;
        Object[] params = new Object[8];
        params[0] = Utility.parseInt(serial_no, null);
        params[1] = Utility.parseInt(product_id, null);
        params[2] = contract_sub_bh;
        params[3] = cust_name;
        params[4] = Utility.parseInt(chg_type, null);
        params[5] = Utility.parseInt(start_date, null);
        params[6] = Utility.parseInt(end_date, null);
        params[7] = Utility.parseInt(input_man, null);
        list = CrmDBManager.listBySql(
                "{call SP_QUERY_HCUSTZJBD(?,?,?,?,?,?,?,?)}", params);
        return list;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#append()
	 */
    @Override
	public void append() throws BusiException {
        Object[] params = new Object[16];
        params[0] = Utility.parseInt(product_id, null);
        params[1] = contract_bh;
        params[2] = cust_id;
        params[3] = Utility.parseInt(chg_type, null);
        params[4] = Utility.parseBigDecimal(appl_amout, null);
        params[5] = Utility.parseBigDecimal(fee_money, null);
        params[6] = Utility.parseBigDecimal(price, null);
        params[7] = Utility.parseBigDecimal(chg_money, null);
        params[8] = Utility.parseBigDecimal(chg_amount, null);
        params[9] = Utility.parseBigDecimal(after_money, null);
        params[10] = Utility.parseBigDecimal(after_amount, null);
        params[11] = Utility.parseInt(sq_date, null);
        params[12] = Utility.parseInt(dz_date, null);
        params[13] = Utility.parseInt(hk_date, null);
        params[14] = Utility.parseInt(jk_type, null);
        params[15] = Utility.parseInt(input_man, null);
        super
                .cudProc(
                        "{?= call SP_ADD_TBENAMOUNTDETAIL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                        params);
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#updatePrintFlag(java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public void updatePrintFlag(Integer print_flag,Integer serial_no)throws BusiException{
    	super.executeSql("UPDATE TBENAMOUNTDETAIL SET PRINT_FLAG = "+print_flag + " WHERE SERIAL_NO =" +serial_no);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#updatePrintFlag1(java.lang.Integer, java.lang.Integer)
	 */
    @Override
	public void updatePrintFlag1(Integer print_flag,Integer serial_no)throws BusiException{
    	super.executeSql("UPDATE INTRUSTHistory..HCUSTZJBD SET PRINT_FLAG = "+print_flag + " WHERE SERIAL_NO =" +serial_no);
    	
    }
    
    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getAfter_amount()
	 */
    @Override
	public BigDecimal getAfter_amount() {
        return after_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setAfter_amount(java.math.BigDecimal)
	 */
    @Override
	public void setAfter_amount(BigDecimal after_amount) {
        this.after_amount = after_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getAfter_money()
	 */
    @Override
	public BigDecimal getAfter_money() {
        return after_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setAfter_money(java.math.BigDecimal)
	 */
    @Override
	public void setAfter_money(BigDecimal after_money) {
        this.after_money = after_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getAppl_amout()
	 */
    @Override
	public BigDecimal getAppl_amout() {
        return appl_amout;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setAppl_amout(java.math.BigDecimal)
	 */
    @Override
	public void setAppl_amout(BigDecimal appl_amout) {
        this.appl_amout = appl_amout;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getBen_start_date()
	 */
    @Override
	public Integer getBen_start_date() {
        return ben_start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setBen_start_date(java.lang.Integer)
	 */
    @Override
	public void setBen_start_date(Integer ben_start_date) {
        this.ben_start_date = ben_start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getChg_amount()
	 */
    @Override
	public BigDecimal getChg_amount() {
        return chg_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setChg_amount(java.math.BigDecimal)
	 */
    @Override
	public void setChg_amount(BigDecimal chg_amount) {
        this.chg_amount = chg_amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getChg_money()
	 */
    @Override
	public BigDecimal getChg_money() {
        return chg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setChg_money(java.math.BigDecimal)
	 */
    @Override
	public void setChg_money(BigDecimal chg_money) {
        this.chg_money = chg_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getChg_type()
	 */
    @Override
	public Integer getChg_type() {
        return chg_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setChg_type(java.lang.Integer)
	 */
    @Override
	public void setChg_type(Integer chg_type) {
        this.chg_type = chg_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getChg_type_name()
	 */
    @Override
	public String getChg_type_name() {
        return chg_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setChg_type_name(java.lang.String)
	 */
    @Override
	public void setChg_type_name(String chg_type_name) {
        this.chg_type_name = chg_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getContract_bh()
	 */
    @Override
	public String getContract_bh() {
        return contract_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setContract_bh(java.lang.String)
	 */
    @Override
	public void setContract_bh(String contract_bh) {
        this.contract_bh = contract_bh;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getCust_id()
	 */
    @Override
	public Integer getCust_id() {
        return cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setCust_id(java.lang.Integer)
	 */
    @Override
	public void setCust_id(Integer cust_id) {
        this.cust_id = cust_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getCust_name()
	 */
    @Override
	public String getCust_name() {
        return cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setCust_name(java.lang.String)
	 */
    @Override
	public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getDz_date()
	 */
    @Override
	public Integer getDz_date() {
        return dz_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setDz_date(java.lang.Integer)
	 */
    @Override
	public void setDz_date(Integer dz_date) {
        this.dz_date = dz_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getEnd_date()
	 */
    @Override
	public Integer getEnd_date() {
        return end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setEnd_date(java.lang.Integer)
	 */
    @Override
	public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getFee_money()
	 */
    @Override
	public BigDecimal getFee_money() {
        return fee_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setFee_money(java.math.BigDecimal)
	 */
    @Override
	public void setFee_money(BigDecimal fee_money) {
        this.fee_money = fee_money;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getFee_rate()
	 */
    @Override
	public BigDecimal getFee_rate() {
        return fee_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setFee_rate(java.math.BigDecimal)
	 */
    @Override
	public void setFee_rate(BigDecimal fee_rate) {
        this.fee_rate = fee_rate;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getHk_date()
	 */
    @Override
	public Integer getHk_date() {
        return hk_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setHk_date(java.lang.Integer)
	 */
    @Override
	public void setHk_date(Integer hk_date) {
        this.hk_date = hk_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getInput_man()
	 */
    @Override
	public Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getJk_type()
	 */
    @Override
	public String getJk_type() {
        return jk_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setJk_type(java.lang.String)
	 */
    @Override
	public void setJk_type(String jk_type) {
        this.jk_type = jk_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getJk_type_name()
	 */
    @Override
	public String getJk_type_name() {
        return jk_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setJk_type_name(java.lang.String)
	 */
    @Override
	public void setJk_type_name(String jk_type_name) {
        this.jk_type_name = jk_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getPrice()
	 */
    @Override
	public BigDecimal getPrice() {
        return price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setPrice(java.math.BigDecimal)
	 */
    @Override
	public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getProduct_id()
	 */
    @Override
	public Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getSq_date()
	 */
    @Override
	public Integer getSq_date() {
        return sq_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setSq_date(java.lang.Integer)
	 */
    @Override
	public void setSq_date(Integer sq_date) {
        this.sq_date = sq_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getStart_date()
	 */
    @Override
	public Integer getStart_date() {
        return start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setStart_date(java.lang.Integer)
	 */
    @Override
	public void setStart_date(Integer start_date) {
        this.start_date = start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getPrint_flag()
	 */
	@Override
	public Integer getPrint_flag() {
		return print_flag;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setPrint_flag(java.lang.Integer)
	 */
	@Override
	public void setPrint_flag(Integer printFlag) {
		print_flag = printFlag;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getProduct_name()
	 */
	@Override
	public String getProduct_name() {
		return product_name;
	}

	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setProduct_name(java.lang.String)
	 */
	@Override
	public void setProduct_name(String productName) {
		product_name = productName;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getSub_product_id()
	 */
	@Override
	public Integer getSub_product_id() {
		return sub_product_id;
	}

	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setSub_product_id(java.lang.Integer)
	 */
	@Override
	public void setSub_product_id(Integer subProductId) {
		sub_product_id = subProductId;
	}
       
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getContract_sub_bh()
	 */
	@Override
	public String getContract_sub_bh() {
		return contract_sub_bh;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setContract_sub_bh(java.lang.String)
	 */
	@Override
	public void setContract_sub_bh(String contract_sub_bh) {
		this.contract_sub_bh = contract_sub_bh;
	}
	
	
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#getCheck_flag()
	 */
	@Override
	public Integer getCheck_flag() {
		return check_flag;
	}
	/* (non-Javadoc)
	 * @see enfo.crm.intrust.ValidateprintLocal#setCheck_flag(java.lang.Integer)
	 */
	@Override
	public void setCheck_flag(Integer check_flag) {
		this.check_flag = check_flag;
	}
}