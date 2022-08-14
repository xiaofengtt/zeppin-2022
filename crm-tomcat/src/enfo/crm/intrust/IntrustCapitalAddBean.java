package enfo.crm.intrust;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.tools.Utility;

@Component(value="intrustCapitalAdd")
@Scope("prototype")
public class IntrustCapitalAddBean extends IntrustBusiBean implements IntrustCapitalAddLocal {

	private Integer bookcode;

    private Integer capital_type;

    private String capital_type_name;

    private Integer include_parent;

    private Integer input_man;

    private String field_caption;

    private Integer serial_no;

    private Integer ci_serial_no;

    private String field_content;

    // add by tsg 2007-12-18
    private Integer book_code;

    private Integer product_id;

    private Integer check_flag;

    private Integer start_date;

    private Integer end_date;

    private String cust_name;

    private String capital_name;

    private BigDecimal amount;

    private Integer post_date;

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#save(java.lang.Integer)
	 */
    @Override
	public void save(Integer input_man) throws Exception {
        validate();

        Object[] params = new Object[3];
        params[0] = serial_no;
        params[1] = field_caption;
        params[2] = input_man;

        try {
            super.update("{?=call SP_MODI_TCAPITALADD(?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#saveInfo()
	 */
    @Override
	public void saveInfo() throws Exception {
        validate();
        Object[] params = new Object[3];
        params[0] = serial_no;
        params[1] = field_content;
        params[2] = input_man;
        try {
            super.update("{?=call SP_MODI_TCAPITALADDINFO(?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#appendInfo()
	 */
    @Override
	public void appendInfo() throws Exception {
        validate();
        Object[] oparams = new Object[4];
        oparams[0] = ci_serial_no;
        oparams[1] = field_caption;
        oparams[2] = field_content;
        oparams[3] = input_man;
        try {
            serial_no = (Integer) super.cudEx(
                    "{?=call SP_ADD_TCAPITALADDINFO(?,?,?,?,?)}", oparams, 6,
                    java.sql.Types.INTEGER);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素添加失败:" + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#append(java.lang.Integer)
	 */
    @Override
	public void append(Integer input_man) throws Exception {
        validate();

        Object[] params = new Object[4];
        params[0] = bookcode;
        params[1] = capital_type;
        params[2] = field_caption;
        params[3] = input_man;

        try {
            super.update("{?=call SP_ADD_TCAPITALADD(?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素添加失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#delete(java.lang.Integer)
	 */
    @Override
	public void delete(Integer input_man) throws Exception {
        validate();
        Object[] params = new Object[2];
        params[0] = serial_no;
        params[1] = input_man;

        try {
            super.update("{?=call SP_DEL_TCAPITALADD(?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#deleteAddInfo()
	 */
    @Override
	public void deleteAddInfo() throws Exception {
        validate();
        Object[] params = new Object[2];
        params[0] = ci_serial_no;
        params[1] = input_man;
        try {
            super.update("{?=call SP_DEL_TCAPITALALLADDINFO(?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("资产自定义要素删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#load(java.lang.Integer)
	 */
    @Override
	public void load(Integer input_man) throws Exception {

        Object[] params = new Object[6];
        params[0] = bookcode;
        params[1] = Utility.parseInt(serial_no, new Integer(0));
        params[2] = Utility.parseInt(capital_type, new Integer(0));
        params[3] = capital_type_name;
        params[4] = Utility.parseInt(include_parent, new Integer(0));
        params[5] = input_man;

        super.query("{call SP_QUERY_TCAPITALADD(?,?,?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#listInfo()
	 */
    @Override
	public void listInfo() throws Exception {

        Object[] params = new Object[4];
        params[0] = Utility.parseInt(ci_serial_no, new Integer(0));
        params[1] = Utility.parseInt(serial_no, new Integer(0));
        params[2] = input_man;
        params[3] = field_caption;

        super.query("{call SP_QUERY_TCAPITALADDINFO(?,?,?,?)}", params);
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#checkCapitalAddInfo()
	 */
    @Override
	public void checkCapitalAddInfo() throws Exception {
        validate();
        Object[] params = new Object[4];
        params[0] = Utility.parseInt(serial_no, new Integer(0));
        params[1] = Utility.parseInt(post_date, new Integer(0));
        params[2] = Utility.parseInt(check_flag, new Integer(0));
        params[3] = Utility.parseInt(input_man, new Integer(0));
        ;
        try {
            super.update("{?=call SP_CHECK_TCAPITALINFO_ONE(?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("审核追加财产资本信息失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#listCapitalAddInfo()
	 */
    @Override
	public void listCapitalAddInfo() throws Exception {

        Object[] params = new Object[4];
        params[0] = Utility.parseInt(book_code, new Integer(0));
        params[1] = Utility.parseInt(serial_no, new Integer(0));
        params[2] = Utility.parseInt(product_id, new Integer(0));
        params[3] = Utility.parseInt(check_flag, new Integer(0));
        try {
            super.query("{call SP_QUERY_TCAPITALINFO_ADD(?,?,?,?)}", params);
        } catch (Exception e) {
            throw new BusiException("查询追加财产资本信息失败：" + e.getMessage());
        }

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getNextCapitalAddInfo()
	 */
    @Override
	public boolean getNextCapitalAddInfo() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            cust_name = rowset.getString("CUST_NAME");
            capital_type_name = rowset.getString("CAPITAL_TYPE_NAME");
            capital_name = rowset.getString("CAPITAL_NAME");
            start_date = new Integer(rowset.getInt("START_DATE"));
            end_date = new Integer(rowset.getInt("END_DATE"));
            amount = rowset.getBigDecimal("MONEY");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getNextInfo()
	 */
    @Override
	public boolean getNextInfo() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            ci_serial_no = new Integer(rowset.getInt("CI_SERIAL_NO"));
            field_caption = rowset.getString("FIELD_CAPTION");
            field_content = rowset.getString("FIELD_CONTENT");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getNext()
	 */
    @Override
	public boolean getNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            capital_type = new Integer(rowset.getInt("CAPITAL_TYPE"));
            field_caption = rowset.getString("FIELD_CAPTION");
            capital_type_name = rowset.getString("CAPITAL_TYPE_NAME");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getAmount()
	 */
    @Override
	public BigDecimal getAmount() {
        return amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setAmount(java.math.BigDecimal)
	 */
    @Override
	public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getBook_code()
	 */
    @Override
	public Integer getBook_code() {
        return book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setBook_code(java.lang.Integer)
	 */
    @Override
	public void setBook_code(Integer book_code) {
        this.book_code = book_code;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getBookcode()
	 */
    @Override
	public Integer getBookcode() {
        return bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setBookcode(java.lang.Integer)
	 */
    @Override
	public void setBookcode(Integer bookcode) {
        this.bookcode = bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCapital_name()
	 */
    @Override
	public String getCapital_name() {
        return capital_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCapital_name(java.lang.String)
	 */
    @Override
	public void setCapital_name(String capital_name) {
        this.capital_name = capital_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCapital_type()
	 */
    @Override
	public Integer getCapital_type() {
        return capital_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCapital_type(java.lang.Integer)
	 */
    @Override
	public void setCapital_type(Integer capital_type) {
        this.capital_type = capital_type;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCapital_type_name()
	 */
    @Override
	public String getCapital_type_name() {
        return capital_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCapital_type_name(java.lang.String)
	 */
    @Override
	public void setCapital_type_name(String capital_type_name) {
        this.capital_type_name = capital_type_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCheck_flag()
	 */
    @Override
	public Integer getCheck_flag() {
        return check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCheck_flag(java.lang.Integer)
	 */
    @Override
	public void setCheck_flag(Integer check_flag) {
        this.check_flag = check_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCi_serial_no()
	 */
    @Override
	public Integer getCi_serial_no() {
        return ci_serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCi_serial_no(java.lang.Integer)
	 */
    @Override
	public void setCi_serial_no(Integer ci_serial_no) {
        this.ci_serial_no = ci_serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getCust_name()
	 */
    @Override
	public String getCust_name() {
        return cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setCust_name(java.lang.String)
	 */
    @Override
	public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getEnd_date()
	 */
    @Override
	public Integer getEnd_date() {
        return end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setEnd_date(java.lang.Integer)
	 */
    @Override
	public void setEnd_date(Integer end_date) {
        this.end_date = end_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getField_caption()
	 */
    @Override
	public String getField_caption() {
        return field_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setField_caption(java.lang.String)
	 */
    @Override
	public void setField_caption(String field_caption) {
        this.field_caption = field_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getField_content()
	 */
    @Override
	public String getField_content() {
        return field_content;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setField_content(java.lang.String)
	 */
    @Override
	public void setField_content(String field_content) {
        this.field_content = field_content;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getInclude_parent()
	 */
    @Override
	public Integer getInclude_parent() {
        return include_parent;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setInclude_parent(java.lang.Integer)
	 */
    @Override
	public void setInclude_parent(Integer include_parent) {
        this.include_parent = include_parent;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getInput_man()
	 */
    @Override
	public Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getPost_date()
	 */
    @Override
	public Integer getPost_date() {
        return post_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setPost_date(java.lang.Integer)
	 */
    @Override
	public void setPost_date(Integer post_date) {
        this.post_date = post_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getProduct_id()
	 */
    @Override
	public Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#getStart_date()
	 */
    @Override
	public Integer getStart_date() {
        return start_date;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustCapitalAddLocal#setStart_date(java.lang.Integer)
	 */
    @Override
	public void setStart_date(Integer start_date) {
        this.start_date = start_date;
    }
}