/*
 * 创建日期 2011-12-9
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class FaqsVO {
	private Integer serial_no;  //编号
	
	private String faq_title; //标题
	
	private String faq_keywords; //关键字
	
	private String faq_content; //内容
	
	private Timestamp input_time; //录入时间
	
	private Timestamp update_time; //更新时间
	
	private Integer input_man; //录入人
	
	private Integer match_times; //被检索到的次数
	
	private Integer support_times; //支持次数
	
	private Integer oppose_times; //反对次数
	
	private String wiki_class;//知识库文章分类
	
	private String search_key;//搜索关键字
	
	private String sortfield;//排序
	
	private String faq_class_no;//分类序号
	
	private String faq_class_name;//分类名称
	

	/**
	 * @return 返回 search_key。
	 */
	public String getSearch_key() {
		return search_key;
	}
	/**
	 * @param search_key 要设置的 search_key。
	 */
	public void setSearch_key(String search_key) {
		this.search_key = search_key;
	}
	/**
	 * @return 返回 sortfield。
	 */
	public String getSortfield() {
		return sortfield;
	}
	/**
	 * @param sortfield 要设置的 sortfield。
	 */
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}
	/**
	 * @return 返回 wiki_class。
	 */
	public String getWiki_class() {
		return wiki_class;
	}
	/**
	 * @param wiki_class 要设置的 wiki_class。
	 */
	public void setWiki_class(String wiki_class) {
		this.wiki_class = wiki_class;
	}
	/**
	 * @return 返回 faq_content。
	 */
	public String getFaq_content() {
		return faq_content;
	}
	/**
	 * @param faq_content 要设置的 faq_content。
	 */
	public void setFaq_content(String faq_content) {
		this.faq_content = faq_content;
	}
	/**
	 * @return 返回 faq_keywords。
	 */
	public String getFaq_keywords() {
		return faq_keywords;
	}
	/**
	 * @param faq_keywords 要设置的 faq_keywords。
	 */
	public void setFaq_keywords(String faq_keywords) {
		this.faq_keywords = faq_keywords;
	}
	/**
	 * @return 返回 faq_title。
	 */
	public String getFaq_title() {
		return faq_title;
	}
	/**
	 * @param faq_title 要设置的 faq_title。
	 */
	public void setFaq_title(String faq_title) {
		this.faq_title = faq_title;
	}
	/**
	 * @return 返回 input_man。
	 */
	public Integer getInput_man() {
		return input_man;
	}
	/**
	 * @param input_man 要设置的 input_man。
	 */
	public void setInput_man(Integer input_man) {
		this.input_man = input_man;
	}
	/**
	 * @return 返回 input_time。
	 */
	public Timestamp getInput_time() {
		return input_time;
	}
	/**
	 * @param input_time 要设置的 input_time。
	 */
	public void setInput_time(Timestamp input_time) {
		this.input_time = input_time;
	}
	/**
	 * @return 返回 match_times。
	 */
	public Integer getMatch_times() {
		return match_times;
	}
	/**
	 * @param match_times 要设置的 match_times。
	 */
	public void setMatch_times(Integer match_times) {
		this.match_times = match_times;
	}
	/**
	 * @return 返回 oppose_times。
	 */
	public Integer getOppose_times() {
		return oppose_times;
	}
	/**
	 * @param oppose_times 要设置的 oppose_times。
	 */
	public void setOppose_times(Integer oppose_times) {
		this.oppose_times = oppose_times;
	}
	/**
	 * @return 返回 serial_no。
	 */
	public Integer getSerial_no() {
		return serial_no;
	}
	/**
	 * @param serial_no 要设置的 serial_no。
	 */
	public void setSerial_no(Integer serial_no) {
		this.serial_no = serial_no;
	}
	/**
	 * @return 返回 support_times。
	 */
	public Integer getSupport_times() {
		return support_times;
	}
	/**
	 * @param support_times 要设置的 support_times。
	 */
	public void setSupport_times(Integer support_times) {
		this.support_times = support_times;
	}
	/**
	 * @return 返回 update_time。
	 */
	public Timestamp getUpdate_time() {
		return update_time;
	}
	/**
	 * @param update_time 要设置的 update_time。
	 */
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	/**
	 * @return 返回 faq_class_name。
	 */
	public String getFaq_class_name() {
		return faq_class_name;
	}
	/**
	 * @param faq_class_name 要设置的 faq_class_name。
	 */
	public void setFaq_class_name(String faq_class_name) {
		this.faq_class_name = faq_class_name;
	}
	/**
	 * @return 返回 faq_class_no。
	 */
	public String getFaq_class_no() {
		return faq_class_no;
	}
	/**
	 * @param faq_class_no 要设置的 faq_class_no。
	 */
	public void setFaq_class_no(String faq_class_no) {
		this.faq_class_no = faq_class_no;
	}
}
