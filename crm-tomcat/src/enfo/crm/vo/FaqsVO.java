/*
 * �������� 2011-12-9
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class FaqsVO {
	private Integer serial_no;  //���
	
	private String faq_title; //����
	
	private String faq_keywords; //�ؼ���
	
	private String faq_content; //����
	
	private Timestamp input_time; //¼��ʱ��
	
	private Timestamp update_time; //����ʱ��
	
	private Integer input_man; //¼����
	
	private Integer match_times; //���������Ĵ���
	
	private Integer support_times; //֧�ִ���
	
	private Integer oppose_times; //���Դ���
	
	private String wiki_class;//֪ʶ�����·���
	
	private String search_key;//�����ؼ���
	
	private String sortfield;//����
	
	private String faq_class_no;//�������
	
	private String faq_class_name;//��������
	

	/**
	 * @return ���� search_key��
	 */
	public String getSearch_key() {
		return search_key;
	}
	/**
	 * @param search_key Ҫ���õ� search_key��
	 */
	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}
	/**
	 * @return ���� sortfield��
	 */
	public String getSortfield() {
		return sortfield;
	}
	/**
	 * @param sortfield Ҫ���õ� sortfield��
	 */
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}
	/**
	 * @return ���� wiki_class��
	 */
	public String getWiki_class() {
		return wiki_class;
	}
	/**
	 * @param wiki_class Ҫ���õ� wiki_class��
	 */
	public void setWiki_class(String wiki_class) {
		this.wiki_class = wiki_class;
	}
	/**
	 * @return ���� faq_content��
	 */
	public String getFaq_content() {
		return faq_content;
	}
	/**
	 * @param faq_content Ҫ���õ� faq_content��
	 */
	public void setFaq_content(String faq_content) {
		this.faq_content = faq_content;
	}
	/**
	 * @return ���� faq_keywords��
	 */
	public String getFaq_keywords() {
		return faq_keywords;
	}
	/**
	 * @param faq_keywords Ҫ���õ� faq_keywords��
	 */
	public void setFaq_keywords(String faq_keywords) {
		this.faq_keywords = faq_keywords;
	}
	/**
	 * @return ���� faq_title��
	 */
	public String getFaq_title() {
		return faq_title;
	}
	/**
	 * @param faq_title Ҫ���õ� faq_title��
	 */
	public void setFaq_title(String faq_title) {
		this.faq_title = faq_title;
	}
	/**
	 * @return ���� input_man��
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man Ҫ���õ� input_man��
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return ���� input_time��
	 */
	public Timestamp getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time Ҫ���õ� input_time��
	 */
	public void setInput_time(Timestamp input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return ���� match_times��
	 */
	public Integer getMatch_times() {
		return match_times;
	}
	/**
	 * @param match_times Ҫ���õ� match_times��
	 */
	public void setMatch_times(Integer match_times) {
		this.match_times = match_times;
	}
	/**
	 * @return ���� oppose_times��
	 */
	public Integer getOppose_times() {
		return oppose_times;
	}
	/**
	 * @param oppose_times Ҫ���õ� oppose_times��
	 */
	public void setOppose_times(Integer oppose_times) {
		this.oppose_times = oppose_times;
	}
	/**
	 * @return ���� serial_no��
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no Ҫ���õ� serial_no��
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return ���� support_times��
	 */
	public Integer getSupport_times() {
		return support_times;
	}
	/**
	 * @param support_times Ҫ���õ� support_times��
	 */
	public void setSupport_times(Integer support_times) {
		this.support_times = support_times;
	}
	/**
	 * @return ���� update_time��
	 */
	public Timestamp getUpdate_time() {
		return update_time;
	}
	/**
	 * @param update_time Ҫ���õ� update_time��
	 */
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	/**
	 * @return ���� faq_class_name��
	 */
	public String getFaq_class_name() {
		return faq_class_name;
	}
	/**
	 * @param faq_class_name Ҫ���õ� faq_class_name��
	 */
	public void setFaq_class_name(String faq_class_name) {
		this.faq_class_name = faq_class_name;
	}
	/**
	 * @return ���� faq_class_no��
	 */
	public String getFaq_class_no() {
		return faq_class_no;
	}
	/**
	 * @param faq_class_no Ҫ���õ� faq_class_no��
	 */
	public void setFaq_class_no(String faq_class_no) {
		this.faq_class_no = faq_class_no;
	}
}
