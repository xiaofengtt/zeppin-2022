/*
 * �������� 2010-1-12
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class AmCustInfoVO {

	private Integer serial_no;
	private Integer cust_id;
	private String cbsc; //�ͻ���Ӫ��Χ	NVARCHAR(256) 
	private String crft; //�ͻ�ע���ʽ����(9902)
	private BigDecimal crfd; //�ͻ�ע���ʽ� DECIMAL(20,3)	
	private String ctrn; //�ͻ�˰��Ǽ�֤����	NVARCHAR(128)	

	private String crnm; //�ͻ���������������	NVARCHAR(128)	
	private String crit; //�ͻ��������������֤������(1108/2108)
	private String crid; //�ͻ��������������֤������  VARCHAR(30)
	private Integer crvt; //�ͻ����˴������֤����Ч����

	private String pcnm; //����������	 NVARCHAR(128)
	private String pitp; //���������֤������(1108/2108)
	private String picd; //���������֤������	VARCHAR(30)
	private Integer pivt; //���������֤����Ч����

	private Integer card_valid_date; //�ͻ����֤����Ч����
	private String country; //�ͻ�����9901
	private String jg_cust_type; //������� 9921 ����cust_type=2ʱ��Ч
	private Integer input_man;
	private String post_address;
	private String cust_tel;
	private String post_address2;
	private String card_type;
	private String card_id;
	private String voc_type;

	private String cogc; //��ϴǮ��֯��������
	private Integer cogc_vd; //��֯����֤����Ч����
    
	/**
	 * @return
	 */
	public String getCard_id() {
		return card_id;
	}

	/**
	 * @return
	 */
	public String getCard_type() {
		return card_type;
	}

	/**
	 * @return
	 */
	public Integer getCard_valid_date() {
		return card_valid_date;
	}

	/**
	 * @return
	 */
	public String getCbsc() {
		return cbsc;
	}

	/**
	 * @return
	 */
	public String getCogc() {
		return cogc;
	}

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return
	 */
	public BigDecimal getCrfd() {
		return crfd;
	}

	/**
	 * @return
	 */
	public String getCrft() {
		return crft;
	}

	/**
	 * @return
	 */
	public String getCrid() {
		return crid;
	}

	/**
	 * @return
	 */
	public String getCrit() {
		return crit;
	}

	/**
	 * @return
	 */
	public String getCrnm() {
		return crnm;
	}

	/**
	 * @return
	 */
	public Integer getCrvt() {
		return crvt;
	}

	/**
	 * @return
	 */
	public String getCtrn() {
		return ctrn;
	}

	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public String getCust_tel() {
		return cust_tel;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @return
	 */
	public String getJg_cust_type() {
		return jg_cust_type;
	}

	/**
	 * @return
	 */
	public String getPcnm() {
		return pcnm;
	}

	/**
	 * @return
	 */
	public String getPicd() {
		return picd;
	}

	/**
	 * @return
	 */
	public String getPitp() {
		return pitp;
	}

	/**
	 * @return
	 */
	public Integer getPivt() {
		return pivt;
	}

	/**
	 * @return
	 */
	public String getPost_address() {
		return post_address;
	}

	/**
	 * @return
	 */
	public String getPost_address2() {
		return post_address2;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @return
	 */
	public String getVoc_type() {
		return voc_type;
	}

	/**
	 * @param string
	 */
	public void setCard_id(String string) {
		card_id = string;
	}

	/**
	 * @param string
	 */
	public void setCard_type(String string) {
		card_type = string;
	}

	/**
	 * @param integer
	 */
	public void setCard_valid_date(Integer integer) {
		card_valid_date = integer;
	}

	/**
	 * @param string
	 */
	public void setCbsc(String string) {
		cbsc = string;
	}

	/**
	 * @param string
	 */
	public void setCogc(String string) {
		cogc = string;
	}

	/**
	 * @param string
	 */
	public void setCountry(String string) {
		country = string;
	}

	/**
	 * @param decimal
	 */
	public void setCrfd(BigDecimal decimal) {
		crfd = decimal;
	}

	/**
	 * @param string
	 */
	public void setCrft(String string) {
		crft = string;
	}

	/**
	 * @param string
	 */
	public void setCrid(String string) {
		crid = string;
	}

	/**
	 * @param string
	 */
	public void setCrit(String string) {
		crit = string;
	}

	/**
	 * @param string
	 */
	public void setCrnm(String string) {
		crnm = string;
	}

	/**
	 * @param integer
	 */
	public void setCrvt(Integer integer) {
		crvt = integer;
	}

	/**
	 * @param string
	 */
	public void setCtrn(String string) {
		ctrn = string;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param string
	 */
	public void setCust_tel(String string) {
		cust_tel = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param string
	 */
	public void setJg_cust_type(String string) {
		jg_cust_type = string;
	}

	/**
	 * @param string
	 */
	public void setPcnm(String string) {
		pcnm = string;
	}

	/**
	 * @param string
	 */
	public void setPicd(String string) {
		picd = string;
	}

	/**
	 * @param string
	 */
	public void setPitp(String string) {
		pitp = string;
	}

	/**
	 * @param integer
	 */
	public void setPivt(Integer integer) {
		pivt = integer;
	}

	/**
	 * @param string
	 */
	public void setPost_address(String string) {
		post_address = string;
	}

	/**
	 * @param string
	 */
	public void setPost_address2(String string) {
		post_address2 = string;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @param string
	 */
	public void setVoc_type(String string) {
		voc_type = string;
	}

    
	/**
	 * @return ���� cogc_vd��
	 */
	public Integer getCogc_vd() {
		return cogc_vd;
	}
	/**
	 * @param cogc_vd Ҫ���õ� cogc_vd��
	 */
	public void setCogc_vd(Integer cogc_vd) {
		this.cogc_vd = cogc_vd;
	}
}
