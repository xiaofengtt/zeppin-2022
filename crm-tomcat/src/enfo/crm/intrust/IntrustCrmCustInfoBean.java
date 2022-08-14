package enfo.crm.intrust;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.IntrustBusiBean;

@Component(value="intrustCrmCustInfo")
@Scope("prototype")
public class IntrustCrmCustInfoBean extends IntrustBusiBean implements IntrustCrmCustInfoLocal {

	 private String cust_no;

	    private String cust_name;

	    private Integer service_man;

	    private String card_type;

	    private String card_id;

	    private Integer cust_type;

	    private String legal_man;

	    private String contact_man;

	    private String post_address;

	    private String post_code;

	    private String e_mail;

	    private String mobile;

	    private Integer input_man;

	    private String modiSql = "UPDATE Client SET STATUS = 3 , proStatus = 0 WHERE CLIENTNO = ?";//修改crm客户信息status为正常

	    //	查询crm客户信息
	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#listCrmCust()
		 */
	    @Override
		public void listCrmCust() throws Exception {

	        String querySql = "SELECT * FROM Client " + "WHERE  CLIENTNO LIKE '%"
	                + cust_no + "%'" + "AND CLIENTNAME LIKE '%" + cust_name + "%'"
	                + "AND CERTIFICATENO LIKE '%" + card_id + "%'";
	        System.out.println("querySql = " + querySql);
	        super.listBySql(querySql);
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#modiFlag()
		 */
	    @Override
		public void modiFlag() throws Exception {
	        Object[] params = new Object[1];
	        params[0] = cust_no;
	        try {
	            super.update(modiSql, params);
	        } catch (Exception e) {
	            throw new BusiException("修改客户已读标志失败: " + e.getMessage());
	        }
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#load()
		 */
	    @Override
		public void load() throws Exception {
	        String querySql = "SELECT * FROM Client WHERE  CLIENTNO ='" + cust_no
	                + "'";
	        super.listBySql(querySql);
	        getNext();
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getNext()
		 */
	    @Override
		public boolean getNext() throws Exception {
	        boolean b = super.getNext();
	        if (b) {
	            cust_no = rowset.getString("CLIENTNO");//客户编号
	            cust_name = rowset.getString("CLIENTNAME");//客户名称
	            card_type = rowset.getString("CERTIFICATETYPE");//证件类型
	            card_id = rowset.getString("CERTIFICATENO");//证件号码
	            cust_type = new Integer(rowset.getInt("CLIENTTYPE"));//客户类型
	            contact_man = rowset.getString("LINKMAN");//联系人
	            legal_man = rowset.getString("RIGHTMAN");//法定代表人
	            post_address = rowset.getString("POSTADDR");
	            post_code = rowset.getString("POSTCODE");
	            e_mail = rowset.getString("EMAIL");
	            mobile = rowset.getString("MOBILE");

	        }
	        return b;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getCard_id()
		 */
	    @Override
		public String getCard_id() {
	        return card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setCard_id(java.lang.String)
		 */
	    @Override
		public void setCard_id(String card_id) {
	        this.card_id = card_id;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getCard_type()
		 */
	    @Override
		public String getCard_type() {
	        return card_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setCard_type(java.lang.String)
		 */
	    @Override
		public void setCard_type(String card_type) {
	        this.card_type = card_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getContact_man()
		 */
	    @Override
		public String getContact_man() {
	        return contact_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setContact_man(java.lang.String)
		 */
	    @Override
		public void setContact_man(String contact_man) {
	        this.contact_man = contact_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getCust_name()
		 */
	    @Override
		public String getCust_name() {
	        return cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setCust_name(java.lang.String)
		 */
	    @Override
		public void setCust_name(String cust_name) {
	        this.cust_name = cust_name;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getCust_no()
		 */
	    @Override
		public String getCust_no() {
	        return cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setCust_no(java.lang.String)
		 */
	    @Override
		public void setCust_no(String cust_no) {
	        this.cust_no = cust_no;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getCust_type()
		 */
	    @Override
		public Integer getCust_type() {
	        return cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setCust_type(java.lang.Integer)
		 */
	    @Override
		public void setCust_type(Integer cust_type) {
	        this.cust_type = cust_type;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getE_mail()
		 */
	    @Override
		public String getE_mail() {
	        return e_mail;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setE_mail(java.lang.String)
		 */
	    @Override
		public void setE_mail(String e_mail) {
	        this.e_mail = e_mail;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getInput_man()
		 */
	    @Override
		public Integer getInput_man() {
	        return input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setInput_man(java.lang.Integer)
		 */
	    @Override
		public void setInput_man(Integer input_man) {
	        this.input_man = input_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getLegal_man()
		 */
	    @Override
		public String getLegal_man() {
	        return legal_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setLegal_man(java.lang.String)
		 */
	    @Override
		public void setLegal_man(String legal_man) {
	        this.legal_man = legal_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getMobile()
		 */
	    @Override
		public String getMobile() {
	        return mobile;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setMobile(java.lang.String)
		 */
	    @Override
		public void setMobile(String mobile) {
	        this.mobile = mobile;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getPost_address()
		 */
	    @Override
		public String getPost_address() {
	        return post_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setPost_address(java.lang.String)
		 */
	    @Override
		public void setPost_address(String post_address) {
	        this.post_address = post_address;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getPost_code()
		 */
	    @Override
		public String getPost_code() {
	        return post_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setPost_code(java.lang.String)
		 */
	    @Override
		public void setPost_code(String post_code) {
	        this.post_code = post_code;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#getService_man()
		 */
	    @Override
		public Integer getService_man() {
	        return service_man;
	    }

	    /* (non-Javadoc)
		 * @see enfo.crm.intrust.IntrustCrmCustInfoLocal#setService_man(java.lang.Integer)
		 */
	    @Override
		public void setService_man(Integer service_man) {
	        this.service_man = service_man;
	    }
	}