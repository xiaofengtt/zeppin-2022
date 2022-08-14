package enfo.crm.intrust;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;
import enfo.crm.tools.Utility;

@Component(value="intrustProductAdd")
@Scope("prototype")
public class IntrustProductAddBean extends IntrustBusiBean implements IntrustProductAddLocal {

	private Integer bookcode;

    private Integer input_man;

    private Integer serial_no;

    private String field_caption;

    private String field_value;

    private Integer product_id;

    private String product_info;

    private Integer tb_flag; //对应表：1 TPRODUCT表 2 TCUSTOMERINFO表

    private String tb_name;//tb_flag对应的表的名字

    private String summary;//字段说明

    private Integer is_chiose;//单选标志

    private Integer df_serial_no;//对应要素表ID

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#saveProduct_info()
	 */
    @Override
	public void saveProduct_info() throws Exception {
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = product_info;
            param[2] = Utility.parseInt(input_man, new Integer(0));

            super.update("{?=call SP_MODI_TPRODUCT_INFO(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品简介修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#save()
	 */
    @Override
	public void save() throws Exception {
        try {

            Object[] param = new Object[5];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = field_caption;
            param[2] = Utility.parseInt(is_chiose, null);
            param[3] = Utility.parseInt(input_man, new Integer(0));
            param[4] = summary;

            super.append("{?=call SP_MODI_TPRODUCTADD(?,?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("自定义要素修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#saveInfo()
	 */
    @Override
	public void saveInfo() throws Exception {
        validate();
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = field_value;
            param[2] = Utility.parseInt(input_man, new Integer(0));

            super.append("{?=call SP_MODI_TPRODUCTADDINFO(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("自定义要素修改失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#appendInfo()
	 */
    @Override
	public void appendInfo() throws Exception {
        validate();
        try {
            Object[] param = new Object[6];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(df_serial_no, null);
            param[2] = field_caption;
            param[3] = field_value;
            param[4] = Utility.parseInt(input_man, new Integer(0));
            param[5] = Utility.parseInt(tb_flag, new Integer(1));
            super.append("{?=call SP_ADD_TPRODUCTADDINFO(?,?,?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("自定义要素添加失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#append()
	 */
    @Override
	public void append() throws Exception {
        validate();
        try {
            Object[] param = new Object[6];
            param[0] = Utility.parseInt(bookcode, new Integer(0));
            param[1] = field_caption;
            param[2] = Utility.parseInt(input_man, new Integer(0));
            param[3] = Utility.parseInt(tb_flag, new Integer(1));
            param[4] = Utility.parseInt(is_chiose, null);
            param[5] = summary;
            super.append("{?=call SP_ADD_TPRODUCTADD(?,?,?,?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("自定义要素添加失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#delete()
	 */
    @Override
	public void delete() throws Exception {
        validate();
        try {

            Object[] param = new Object[2];
            param[0] = Utility.parseInt(serial_no, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));

            super.delete("{?=call SP_DEL_TPRODUCTADD(?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("产品自定义要素删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#deleteAddInfo()
	 */
    @Override
	public void deleteAddInfo() throws Exception {
        validate();
        try {

            Object[] param = new Object[3];
            param[0] = Utility.parseInt(product_id, new Integer(0));
            param[1] = Utility.parseInt(input_man, new Integer(0));
            param[2] = Utility.parseInt(tb_flag, new Integer(0)); //默认改为0什么都不删除，避免未传值时误删除产品自定义要素
            super.delete("{?=call SP_DEL_TPRODUCTADDINFO(?,?,?)}", param);

        } catch (Exception e) {
            throw new BusiException("自定义要素删除失败: " + e.getMessage());
        }
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#list()
	 */
    @Override
	public void list() throws Exception {
        Object[] param = new Object[4];
        param[0] = Utility.parseInt(serial_no, new Integer(0));
        param[1] = Utility.parseInt(bookcode, new Integer(0));
        param[2] = field_caption;
        param[3] = Utility.parseInt(tb_flag, new Integer(1));
        super.query("{call SP_QUERY_TPRODUCTADD(?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#listInfo()
	 */
    @Override
	public void listInfo() throws Exception {

        Object[] param = new Object[4];
        param[0] = Utility.parseInt(serial_no, new Integer(0));
        param[1] = Utility.parseInt(product_id, new Integer(0));
        param[2] = field_caption;
        param[3] = Utility.parseInt(tb_flag, new Integer(1));

        super.query("{call SP_QUERY_TPRODUCTADDINFO(?,?,?,?)}", param);

    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getNextInfo()
	 */
    @Override
	public boolean getNextInfo() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            product_id = new Integer(rowset.getInt("PRODUCT_ID"));
            field_caption = rowset.getString("FIELD_CAPTION");
            field_value = rowset.getString("FIELD_VALUE");
            tb_flag = new Integer(rowset.getInt("TB_FLAG"));
            summary = rowset.getString("SUMMARY");
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getNext()
	 */
    @Override
	public boolean getNext() throws Exception {
        boolean b = super.getNext();
        if (b) {
            serial_no = new Integer(rowset.getInt("SERIAL_NO"));
            bookcode = new Integer(rowset.getInt("book_code"));
            field_caption = rowset.getString("FIELD_CAPTION");
            tb_flag = new Integer(rowset.getInt("TB_FLAG"));
            tb_name = rowset.getString("TB_NAME");
            summary = rowset.getString("SUMMARY");
            is_chiose = new Integer(rowset.getInt("IS_CHOICE"));
        }
        return b;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getBookcode()
	 */
    @Override
	public Integer getBookcode() {
        return bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setBookcode(java.lang.Integer)
	 */
    @Override
	public void setBookcode(Integer bookcode) {
        this.bookcode = bookcode;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getDf_serial_no()
	 */
    @Override
	public Integer getDf_serial_no() {
        return df_serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setDf_serial_no(java.lang.Integer)
	 */
    @Override
	public void setDf_serial_no(Integer df_serial_no) {
        this.df_serial_no = df_serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getField_caption()
	 */
    @Override
	public String getField_caption() {
        return field_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setField_caption(java.lang.String)
	 */
    @Override
	public void setField_caption(String field_caption) {
        this.field_caption = field_caption;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getField_value()
	 */
    @Override
	public String getField_value() {
        return field_value;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setField_value(java.lang.String)
	 */
    @Override
	public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getInput_man()
	 */
    @Override
	public Integer getInput_man() {
        return input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setInput_man(java.lang.Integer)
	 */
    @Override
	public void setInput_man(Integer input_man) {
        this.input_man = input_man;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getIs_chiose()
	 */
    @Override
	public Integer getIs_chiose() {
        return is_chiose;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setIs_chiose(java.lang.Integer)
	 */
    @Override
	public void setIs_chiose(Integer is_chiose) {
        this.is_chiose = is_chiose;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getProduct_id()
	 */
    @Override
	public Integer getProduct_id() {
        return product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setProduct_id(java.lang.Integer)
	 */
    @Override
	public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getProduct_info()
	 */
    @Override
	public String getProduct_info() {
        return product_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setProduct_info(java.lang.String)
	 */
    @Override
	public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getSerial_no()
	 */
    @Override
	public Integer getSerial_no() {
        return serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setSerial_no(java.lang.Integer)
	 */
    @Override
	public void setSerial_no(Integer serial_no) {
        this.serial_no = serial_no;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getSummary()
	 */
    @Override
	public String getSummary() {
        return summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setSummary(java.lang.String)
	 */
    @Override
	public void setSummary(String summary) {
        this.summary = summary;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getTb_flag()
	 */
    @Override
	public Integer getTb_flag() {
        return tb_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setTb_flag(java.lang.Integer)
	 */
    @Override
	public void setTb_flag(Integer tb_flag) {
        this.tb_flag = tb_flag;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#getTb_name()
	 */
    @Override
	public String getTb_name() {
        return tb_name;
    }

    /* (non-Javadoc)
	 * @see enfo.crm.intrust.IntrustProductAddLocal#setTb_name(java.lang.String)
	 */
    @Override
	public void setTb_name(String tb_name) {
        this.tb_name = tb_name;
    }
}