/*
 * �������� 2011-9-20
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author mengyu
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class RatingVO {
	
	private Integer subject_id;		//��ĿID,����
	private String subject_no;			//��Ŀ���
	private String subject_name;		//��Ŀ����
	private String summary;			//����
	
	private Integer rating_id;         //����ID
	private String rating_no;          //�������
	private String rating_name;        //��������
	private Integer include_top;       //�Ƿ���ͷ1�ǣ�2��
	private Integer include_end;       //�Ƿ���β1�ǣ�2��
	private Integer score_lower;       //��ֵ����
	private Integer score_upper;       //��ֵ����
	
	private Integer operand_id;        //������ID(�����)
	private String operand_no;         //���������
	private String operand_name;       //����������
	private Integer scoring;           //�˹���ϵͳ����� 1�˹���2ϵͳ
	private Integer source;            //��Դ(���˹�)1�����˹������2�ֹ����
	
	private Integer manual_id;			//������ID
	private String operation_value;    //ѡ��ֵ
	private Integer score;             //�÷�
	
	private String operand_xh;               //���
	private String source_table;             //������Դ��
	private String source_field;             //������Դ�ֶ�
	private BigDecimal top_threshold;        //ͷ�ٽ�ֵ
	private BigDecimal end_threshold;        //β�ٽ�ֵ
	private String true_value;               //Ϊ��ȡֵ
	private String false_value;              //Ϊ��ȡֵ
	private Integer add_sub_items;           //�Ӽ���
	private BigDecimal weight;               //Ȩ��
	private BigDecimal multiple;             //����

	private Integer true_false_value;		  //���ֵ
	
	private Integer cust_id;              //�ͻ�ID
	private String cust_no;
	private String cust_name;
	private Integer scoring_date;         //�������
	private Integer cust_source;          //��ֵ
	private Integer regulation;           //����������
	private Integer end_date;             //��ֹ����
	private Integer operand_v_id;			//ϵͳ���ȡֵ����
	
	private Integer operand_c_id;			//ϵͳ�����������
	private Integer current_source;        //��ǰ��ֵ
	private Integer currnet_date;          //��ǰ����
	
	private Integer rating_date;       //��������

	private Integer input_man;			//����Ա
	private String cust_all_id;
	private Integer cust_type;
	private String px_name;//�����ֶ�
	
	public String getPx_name() {
		return px_name;
	}
	public void setPx_name(String pxName) {
		px_name = pxName;
	}
	public Integer getCust_type() {
		return cust_type;
	}
	public void setCust_type(Integer custType) {
		cust_type = custType;
	}
	public String getCust_all_id() {
		return cust_all_id;
	}
	public void setCust_all_id(String custAllId) {
		cust_all_id = custAllId;
	}
	/**
	 * @return ���� add_sub_items��
	 */
	public Integer getAdd_sub_items() {
		return add_sub_items;
	}
	/**
	 * @param add_sub_items Ҫ���õ� add_sub_items��
	 */
	public void setAdd_sub_items(Integer add_sub_items) {
		this.add_sub_items = add_sub_items;
	}
	/**
	 * @return ���� current_source��
	 */
	public Integer getCurrent_source() {
		return current_source;
	}
	/**
	 * @param current_source Ҫ���õ� current_source��
	 */
	public void setCurrent_source(Integer current_source) {
		this.current_source = current_source;
	}
	/**
	 * @return ���� currnet_date��
	 */
	public Integer getCurrnet_date() {
		return currnet_date;
	}
	/**
	 * @param currnet_date Ҫ���õ� currnet_date��
	 */
	public void setCurrnet_date(Integer currnet_date) {
		this.currnet_date = currnet_date;
	}
	/**
	 * @return ���� cust_id��
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id Ҫ���õ� cust_id��
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return ���� cust_source��
	 */
	public Integer getCust_source() {
		return cust_source;
	}
	/**
	 * @param cust_source Ҫ���õ� cust_source��
	 */
	public void setCust_source(Integer cust_source) {
		this.cust_source = cust_source;
	}
	/**
	 * @return ���� end_date��
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date Ҫ���õ� end_date��
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return ���� end_threshold��
	 */
	public BigDecimal getEnd_threshold() {
		return end_threshold;
	}
	/**
	 * @param end_threshold Ҫ���õ� end_threshold��
	 */
	public void setEnd_threshold(BigDecimal end_threshold) {
		this.end_threshold = end_threshold;
	}
	/**
	 * @return ���� false_value��
	 */
	public String getFalse_value() {
		return false_value;
	}
	/**
	 * @param false_value Ҫ���õ� false_value��
	 */
	public void setFalse_value(String false_value) {
		this.false_value = false_value;
	}
	/**
	 * @return ���� include_end��
	 */
	public Integer getInclude_end() {
		return include_end;
	}
	/**
	 * @param include_end Ҫ���õ� include_end��
	 */
	public void setInclude_end(Integer include_end) {
		this.include_end = include_end;
	}
	/**
	 * @return ���� include_top��
	 */
	public Integer getInclude_top() {
		return include_top;
	}
	/**
	 * @param include_top Ҫ���õ� include_top��
	 */
	public void setInclude_top(Integer include_top) {
		this.include_top = include_top;
	}
	/**
	 * @return ���� multiple��
	 */
	public BigDecimal getMultiple() {
		return multiple;
	}
	/**
	 * @param multiple Ҫ���õ� multiple��
	 */
	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}
	/**
	 * @return ���� operand_id��
	 */
	public Integer getOperand_id() {
		return operand_id;
	}
	/**
	 * @param operand_id Ҫ���õ� operand_id��
	 */
	public void setOperand_id(Integer operand_id) {
		this.operand_id = operand_id;
	}
	/**
	 * @return ���� operand_name��
	 */
	public String getOperand_name() {
		return operand_name;
	}
	/**
	 * @param operand_name Ҫ���õ� operand_name��
	 */
	public void setOperand_name(String operand_name) {
		this.operand_name = operand_name;
	}
	/**
	 * @return ���� operand_no��
	 */
	public String getOperand_no() {
		return operand_no;
	}
	/**
	 * @param operand_no Ҫ���õ� operand_no��
	 */
	public void setOperand_no(String operand_no) {
		this.operand_no = operand_no;
	}
	/**
	 * @return ���� operand_xh��
	 */
	public String getOperand_xh() {
		return operand_xh;
	}
	/**
	 * @param operand_xh Ҫ���õ� operand_xh��
	 */
	public void setOperand_xh(String operand_xh) {
		this.operand_xh = operand_xh;
	}
	/**
	 * @return ���� operation_value��
	 */
	public String getOperation_value() {
		return operation_value;
	}
	/**
	 * @param operation_value Ҫ���õ� operation_value��
	 */
	public void setOperation_value(String operation_value) {
		this.operation_value = operation_value;
	}
	/**
	 * @return ���� rating_date��
	 */
	public Integer getRating_date() {
		return rating_date;
	}
	/**
	 * @param rating_date Ҫ���õ� rating_date��
	 */
	public void setRating_date(Integer rating_date) {
		this.rating_date = rating_date;
	}
	/**
	 * @return ���� rating_id��
	 */
	public Integer getRating_id() {
		return rating_id;
	}
	/**
	 * @param rating_id Ҫ���õ� rating_id��
	 */
	public void setRating_id(Integer rating_id) {
		this.rating_id = rating_id;
	}
	/**
	 * @return ���� rating_name��
	 */
	public String getRating_name() {
		return rating_name;
	}
	/**
	 * @param rating_name Ҫ���õ� rating_name��
	 */
	public void setRating_name(String rating_name) {
		this.rating_name = rating_name;
	}
	/**
	 * @return ���� rating_no��
	 */
	public String getRating_no() {
		return rating_no;
	}
	/**
	 * @param rating_no Ҫ���õ� rating_no��
	 */
	public void setRating_no(String rating_no) {
		this.rating_no = rating_no;
	}
	/**
	 * @return ���� regulation��
	 */
	public Integer getRegulation() {
		return regulation;
	}
	/**
	 * @param regulation Ҫ���õ� regulation��
	 */
	public void setRegulation(Integer regulation) {
		this.regulation = regulation;
	}
	/**
	 * @return ���� score��
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score Ҫ���õ� score��
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * @return ���� score_lower��
	 */
	public Integer getScore_lower() {
		return score_lower;
	}
	/**
	 * @param score_lower Ҫ���õ� score_lower��
	 */
	public void setScore_lower(Integer score_lower) {
		this.score_lower = score_lower;
	}
	/**
	 * @return ���� score_upper��
	 */
	public Integer getScore_upper() {
		return score_upper;
	}
	/**
	 * @param score_upper Ҫ���õ� score_upper��
	 */
	public void setScore_upper(Integer score_upper) {
		this.score_upper = score_upper;
	}
	/**
	 * @return ���� scoring��
	 */
	public Integer getScoring() {
		return scoring;
	}
	/**
	 * @param scoring Ҫ���õ� scoring��
	 */
	public void setScoring(Integer scoring) {
		this.scoring = scoring;
	}
	/**
	 * @return ���� scoring_date��
	 */
	public Integer getScoring_date() {
		return scoring_date;
	}
	/**
	 * @param scoring_date Ҫ���õ� scoring_date��
	 */
	public void setScoring_date(Integer scoring_date) {
		this.scoring_date = scoring_date;
	}
	/**
	 * @return ���� source��
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * @param source Ҫ���õ� source��
	 */
	public void setSource(Integer source) {
		this.source = source;
	}
	/**
	 * @return ���� source_field��
	 */
	public String getSource_field() {
		return source_field;
	}
	/**
	 * @param source_field Ҫ���õ� source_field��
	 */
	public void setSource_field(String source_field) {
		this.source_field = source_field;
	}
	/**
	 * @return ���� source_table��
	 */
	public String getSource_table() {
		return source_table;
	}
	/**
	 * @param source_table Ҫ���õ� source_table��
	 */
	public void setSource_table(String source_table) {
		this.source_table = source_table;
	}
	/**
	 * @return ���� subject_id��
	 */
	public Integer getSubject_id() {
		return subject_id;
	}
	/**
	 * @param subject_id Ҫ���õ� subject_id��
	 */
	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}
	/**
	 * @return ���� subject_name��
	 */
	public String getSubject_name() {
		return subject_name;
	}
	/**
	 * @param subject_name Ҫ���õ� subject_name��
	 */
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	/**
	 * @return ���� subject_no��
	 */
	public String getSubject_no() {
		return subject_no;
	}
	/**
	 * @param subject_no Ҫ���õ� subject_no��
	 */
	public void setSubject_no(String subject_no) {
		this.subject_no = subject_no;
	}
	/**
	 * @return ���� summary��
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary Ҫ���õ� summary��
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return ���� top_threshold��
	 */
	public BigDecimal getTop_threshold() {
		return top_threshold;
	}
	/**
	 * @param top_threshold Ҫ���õ� top_threshold��
	 */
	public void setTop_threshold(BigDecimal top_threshold) {
		this.top_threshold = top_threshold;
	}

	/**
	 * @return ���� true_false_value��
	 */
	public Integer getTrue_false_value() {
		return true_false_value;
	}
	/**
	 * @param true_false_value Ҫ���õ� true_false_value��
	 */
	public void setTrue_false_value(Integer true_false_value) {
		this.true_false_value = true_false_value;
	}
	/**
	 * @return ���� true_value��
	 */
	public String getTrue_value() {
		return true_value;
	}
	/**
	 * @param true_value Ҫ���õ� true_value��
	 */
	public void setTrue_value(String true_value) {
		this.true_value = true_value;
	}
	/**
	 * @return ���� weight��
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight Ҫ���õ� weight��
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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
	 * @return ���� manual_id��
	 */
	public Integer getManual_id() {
		return manual_id;
	}
	/**
	 * @param manual_id Ҫ���õ� manual_id��
	 */
	public void setManual_id(Integer manual_id) {
		this.manual_id = manual_id;
	}
	
	
	/**
	 * @return ���� cust_name��
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name Ҫ���õ� cust_name��
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return ���� cust_no��
	 */
	public String getCust_no() {
		return cust_no;
	}
	/**
	 * @param cust_no Ҫ���õ� cust_no��
	 */
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	
	
	/**
	 * @return ���� operand_c_id��
	 */
	public Integer getOperand_c_id() {
		return operand_c_id;
	}
	/**
	 * @param operand_c_id Ҫ���õ� operand_c_id��
	 */
	public void setOperand_c_id(Integer operand_c_id) {
		this.operand_c_id = operand_c_id;
	}
	/**
	 * @return ���� operand_v_id��
	 */
	public Integer getOperand_v_id() {
		return operand_v_id;
	}
	/**
	 * @param operand_v_id Ҫ���õ� operand_v_id��
	 */
	public void setOperand_v_id(Integer operand_v_id) {
		this.operand_v_id = operand_v_id;
	}
}
