/*
 * �������� 2014-6-18
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

/**
 * @author zhou
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */

import java.math.BigDecimal;

public class PropertySurveyVO {
	private java.lang.Integer serial_no;//���
	private java.lang.Integer cust_id;//�ͻ�ID
	private java.lang.Integer survey_date;//��������
	private java.lang.String survey_man;//������
	private java.lang.String cust_name;//�ͻ�����
	private java.lang.String link_address;//��ϵ��ַ
	private java.lang.String link_phone;//��ϵ�绰
	private java.lang.String gr_nation;//����
	private java.lang.String profession;//ְҵ ��ҵ
	private java.lang.String business;//��Ӫ��Χ(������Ч)
	private BigDecimal capital;//ע���ʽ�(������Ч)
	private java.lang.String swdjhm;//˰��ǼǺ���(������Ч)
	private java.lang.String fr_name;//����������(������)����(������Ч)
	private java.lang.String fz_card_type;//����:���������ˣ������ˣ����֤������ ����:���֤������
	private java.lang.String fz_card_type_name;//����:���������ˣ������ˣ����֤���������� ����:���֤����������
	private java.lang.String fz_card_id;//����:���������ˣ������ˣ����֤������ ����:���֤������
	private java.lang.Integer fz_card_yxq;//����:���������ˣ������ˣ����֤����Ч�� ����:���֤����Ч����
	private java.lang.String gd_name;//�عɹɶ���ʵ�ʿ���������/����(������Ч)
	private java.lang.String gd_card_type;//�عɹɶ���ʵ�ʿ��������֤������(������Ч)
	private java.lang.String gd_card_type_name;//�عɹɶ���ʵ�ʿ��������֤����������(������Ч)
	private java.lang.String gd_card_id;//�عɹɶ���ʵ�ʿ��������֤������(������Ч)
	private java.lang.Integer gd_card_yxq;//�عɹɶ���ʵ�ʿ��������֤����Ч��(������Ч)
	private java.lang.String bl_name;//��Ȩҵ�����������
	private java.lang.String bl_card_type;//��Ȩҵ����������֤������
	private java.lang.String bl_card_type_name;//��Ȩҵ����������֤����������
	private java.lang.String bl_card_id;//��Ȩҵ����������֤������
	private java.lang.Integer bl_card_yxq;//��Ȩҵ����������֤����Ч��
	private java.lang.String property_source;//���вƲ���Դ 2013(INTRUST:1167)
	private java.lang.Integer khfxdj;//�ͻ����յȼ� 1�� 2�� 3��
	private java.lang.Integer modi_no;//�޸Ĵ���
	private java.lang.Integer modi_man;//�޸���
	private java.sql.Timestamp modi_time;//�޸�ʱ��
	private java.lang.Integer input_man;//����Ա
	private java.sql.Timestamp input_time;//����ʱ��
	
	
	/**
	 * @return ���� bl_card_id��
	 */
	public java.lang.String getBl_card_id() {
		return bl_card_id;
	}
	/**
	 * @param bl_card_id Ҫ���õ� bl_card_id��
	 */
	public void setBl_card_id(java.lang.String bl_card_id) {
		this.bl_card_id = bl_card_id;
	}
	/**
	 * @return ���� bl_card_type��
	 */
	public java.lang.String getBl_card_type() {
		return bl_card_type;
	}
	/**
	 * @param bl_card_type Ҫ���õ� bl_card_type��
	 */
	public void setBl_card_type(java.lang.String bl_card_type) {
		this.bl_card_type = bl_card_type;
	}
	/**
	 * @return ���� bl_card_type_name��
	 */
	public java.lang.String getBl_card_type_name() {
		return bl_card_type_name;
	}
	/**
	 * @param bl_card_type_name Ҫ���õ� bl_card_type_name��
	 */
	public void setBl_card_type_name(java.lang.String bl_card_type_name) {
		this.bl_card_type_name = bl_card_type_name;
	}
	/**
	 * @return ���� bl_card_yxq��
	 */
	public java.lang.Integer getBl_card_yxq() {
		return bl_card_yxq;
	}
	/**
	 * @param bl_card_yxq Ҫ���õ� bl_card_yxq��
	 */
	public void setBl_card_yxq(java.lang.Integer bl_card_yxq) {
		this.bl_card_yxq = bl_card_yxq;
	}
	/**
	 * @return ���� bl_name��
	 */
	public java.lang.String getBl_name() {
		return bl_name;
	}
	/**
	 * @param bl_name Ҫ���õ� bl_name��
	 */
	public void setBl_name(java.lang.String bl_name) {
		this.bl_name = bl_name;
	}
	/**
	 * @return ���� business��
	 */
	public java.lang.String getBusiness() {
		return business;
	}
	/**
	 * @param business Ҫ���õ� business��
	 */
	public void setBusiness(java.lang.String business) {
		this.business = business;
	}
	/**
	 * @return ���� capital��
	 */
	public BigDecimal getCapital() {
		return capital;
	}
	/**
	 * @param capital Ҫ���õ� capital��
	 */
	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}
	/**
	 * @return ���� cust_id��
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(java.lang.Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� cust_name��
	 */
	public java.lang.String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(java.lang.String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return ���� fr_name��
	 */
	public java.lang.String getFr_name() {
		return fr_name;
	}
	/**
	 * @param fr_name Ҫ���õ� fr_name��
	 */
	public void setFr_name(java.lang.String fr_name) {
		this.fr_name = fr_name;
	}
	/**
	 * @return ���� fz_card_id��
	 */
	public java.lang.String getFz_card_id() {
		return fz_card_id;
	}
	/**
	 * @param fz_card_id Ҫ���õ� fz_card_id��
	 */
	public void setFz_card_id(java.lang.String fz_card_id) {
		this.fz_card_id = fz_card_id;
	}
	/**
	 * @return ���� fz_card_type��
	 */
	public java.lang.String getFz_card_type() {
		return fz_card_type;
	}
	/**
	 * @param fz_card_type Ҫ���õ� fz_card_type��
	 */
	public void setFz_card_type(java.lang.String fz_card_type) {
		this.fz_card_type = fz_card_type;
	}
	/**
	 * @return ���� fz_card_type_name��
	 */
	public java.lang.String getFz_card_type_name() {
		return fz_card_type_name;
	}
	/**
	 * @param fz_card_type_name Ҫ���õ� fz_card_type_name��
	 */
	public void setFz_card_type_name(java.lang.String fz_card_type_name) {
		this.fz_card_type_name = fz_card_type_name;
	}
	/**
	 * @return ���� fz_card_yxq��
	 */
	public java.lang.Integer getFz_card_yxq() {
		return fz_card_yxq;
	}
	/**
	 * @param fz_card_yxq Ҫ���õ� fz_card_yxq��
	 */
	public void setFz_card_yxq(java.lang.Integer fz_card_yxq) {
		this.fz_card_yxq = fz_card_yxq;
	}
	/**
	 * @return ���� gd_card_id��
	 */
	public java.lang.String getGd_card_id() {
		return gd_card_id;
	}
	/**
	 * @param gd_card_id Ҫ���õ� gd_card_id��
	 */
	public void setGd_card_id(java.lang.String gd_card_id) {
		this.gd_card_id = gd_card_id;
	}
	/**
	 * @return ���� gd_card_type��
	 */
	public java.lang.String getGd_card_type() {
		return gd_card_type;
	}
	/**
	 * @param gd_card_type Ҫ���õ� gd_card_type��
	 */
	public void setGd_card_type(java.lang.String gd_card_type) {
		this.gd_card_type = gd_card_type;
	}
	/**
	 * @return ���� gd_card_type_name��
	 */
	public java.lang.String getGd_card_type_name() {
		return gd_card_type_name;
	}
	/**
	 * @param gd_card_type_name Ҫ���õ� gd_card_type_name��
	 */
	public void setGd_card_type_name(java.lang.String gd_card_type_name) {
		this.gd_card_type_name = gd_card_type_name;
	}
	/**
	 * @return ���� gd_card_yxq��
	 */
	public java.lang.Integer getGd_card_yxq() {
		return gd_card_yxq;
	}
	/**
	 * @param gd_card_yxq Ҫ���õ� gd_card_yxq��
	 */
	public void setGd_card_yxq(java.lang.Integer gd_card_yxq) {
		this.gd_card_yxq = gd_card_yxq;
	}
	/**
	 * @return ���� gd_name��
	 */
	public java.lang.String getGd_name() {
		return gd_name;
	}
	/**
	 * @param gd_name Ҫ���õ� gd_name��
	 */
	public void setGd_name(java.lang.String gd_name) {
		this.gd_name = gd_name;
	}
	/**
	 * @return ���� gr_nation��
	 */
	public java.lang.String getGr_nation() {
		return gr_nation;
	}
	/**
	 * @param gr_nation Ҫ���õ� gr_nation��
	 */
	public void setGr_nation(java.lang.String gr_nation) {
		this.gr_nation = gr_nation;
	}
	/**
	 * @return ���� input_man��
	 */
	public java.lang.Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(java.lang.Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� input_time��
	 */
	public java.sql.Timestamp getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time Ҫ���õ� input_time��
	 */
	public void setInput_time(java.sql.Timestamp input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return ���� khfxdj��
	 */
	public java.lang.Integer getKhfxdj() {
		return khfxdj;
	}
	/**
	 * @param khfxdj Ҫ���õ� khfxdj��
	 */
	public void setKhfxdj(java.lang.Integer khfxdj) {
		this.khfxdj = khfxdj;
	}
	/**
	 * @return ���� link_address��
	 */
	public java.lang.String getLink_address() {
		return link_address;
	}
	/**
	 * @param link_address Ҫ���õ� link_address��
	 */
	public void setLink_address(java.lang.String link_address) {
		this.link_address = link_address;
	}
	/**
	 * @return ���� link_phone��
	 */
	public java.lang.String getLink_phone() {
		return link_phone;
	}
	/**
	 * @param link_phone Ҫ���õ� link_phone��
	 */
	public void setLink_phone(java.lang.String link_phone) {
		this.link_phone = link_phone;
	}
	/**
	 * @return ���� modi_man��
	 */
	public java.lang.Integer getModi_man() {
		return modi_man;
	}
	/**
	 * @param modi_man Ҫ���õ� modi_man��
	 */
	public void setModi_man(java.lang.Integer modi_man) {
		this.modi_man = modi_man;
	}
	/**
	 * @return ���� modi_no��
	 */
	public java.lang.Integer getModi_no() {
		return modi_no;
	}
	/**
	 * @param modi_no Ҫ���õ� modi_no��
	 */
	public void setModi_no(java.lang.Integer modi_no) {
		this.modi_no = modi_no;
	}
	/**
	 * @return ���� modi_time��
	 */
	public java.sql.Timestamp getModi_time() {
		return modi_time;
	}
	/**
	 * @param modi_time Ҫ���õ� modi_time��
	 */
	public void setModi_time(java.sql.Timestamp modi_time) {
		this.modi_time = modi_time;
	}
	/**
	 * @return ���� profession��
	 */
	public java.lang.String getProfession() {
		return profession;
	}
	/**
	 * @param profession Ҫ���õ� profession��
	 */
	public void setProfession(java.lang.String profession) {
		this.profession = profession;
	}
	/**
	 * @return ���� property_source��
	 */
	public java.lang.String getProperty_source() {
		return property_source;
	}
	/**
	 * @param property_source Ҫ���õ� property_source��
	 */
	public void setProperty_source(java.lang.String property_source) {
		this.property_source = property_source;
	}
	/**
	 * @return ���� serial_no��
	 */
	public java.lang.Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(java.lang.Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� survey_date��
	 */
	public java.lang.Integer getSurvey_date() {
		return survey_date;
	}
	/**
	 * @param survey_date Ҫ���õ� survey_date��
	 */
	public void setSurvey_date(java.lang.Integer survey_date) {
		this.survey_date = survey_date;
	}
	/**
	 * @return ���� survey_man��
	 */
	public java.lang.String getSurvey_man() {
		return survey_man;
	}
	/**
	 * @param survey_man Ҫ���õ� survey_man��
	 */
	public void setSurvey_man(java.lang.String survey_man) {
		this.survey_man = survey_man;
	}
	/**
	 * @return ���� swdjhm��
	 */
	public java.lang.String getSwdjhm() {
		return swdjhm;
	}
	/**
	 * @param swdjhm Ҫ���õ� swdjhm��
	 */
	public void setSwdjhm(java.lang.String swdjhm) {
		this.swdjhm = swdjhm;
	}
}
