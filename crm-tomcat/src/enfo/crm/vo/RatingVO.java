/*
 * 创建日期 2011-9-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author mengyu
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class RatingVO {
	
	private Integer subject_id;		//科目ID,主键
	private String subject_no;			//科目编号
	private String subject_name;		//科目名称
	private String summary;			//描述
	
	private Integer rating_id;         //评级ID
	private String rating_no;          //评级编号
	private String rating_name;        //评级名称
	private Integer include_top;       //是否算头1是，2否
	private Integer include_end;       //是否算尾1是，2否
	private Integer score_lower;       //分值下限
	private Integer score_upper;       //分值上限
	
	private Integer operand_id;        //操作数ID(打分项)
	private String operand_no;         //操作数编号
	private String operand_name;       //操作数名称
	private Integer scoring;           //人工（系统）打分 1人工，2系统
	private Integer source;            //来源(仅人工)1定义人工打分项2手工打分
	
	private Integer manual_id;			//操作数ID
	private String operation_value;    //选项值
	private Integer score;             //得分
	
	private String operand_xh;               //序号
	private String source_table;             //数据来源表
	private String source_field;             //数据来源字段
	private BigDecimal top_threshold;        //头临界值
	private BigDecimal end_threshold;        //尾临界值
	private String true_value;               //为真取值
	private String false_value;              //为假取值
	private Integer add_sub_items;           //加减项
	private BigDecimal weight;               //权重
	private BigDecimal multiple;             //倍数

	private Integer true_false_value;		  //真假值
	
	private Integer cust_id;              //客户ID
	private String cust_no;
	private String cust_name;
	private Integer scoring_date;         //打分日期
	private Integer cust_source;          //分值
	private Integer regulation;           //比上期增减
	private Integer end_date;             //截止日期
	private Integer operand_v_id;			//系统打分取值主键
	
	private Integer operand_c_id;			//系统打分条件主键
	private Integer current_source;        //当前分值
	private Integer currnet_date;          //当前日期
	
	private Integer rating_date;       //评级日期

	private Integer input_man;			//操作员
	private String cust_all_id;
	private Integer cust_type;
	private String px_name;//排序字段
	
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
	 * @return 返回 add_sub_items。
	 */
	public Integer getAdd_sub_items() {
		return add_sub_items;
	}
	/**
	 * @param add_sub_items 要设置的 add_sub_items。
	 */
	public void setAdd_sub_items(Integer add_sub_items) {
		this.add_sub_items = add_sub_items;
	}
	/**
	 * @return 返回 current_source。
	 */
	public Integer getCurrent_source() {
		return current_source;
	}
	/**
	 * @param current_source 要设置的 current_source。
	 */
	public void setCurrent_source(Integer current_source) {
		this.current_source = current_source;
	}
	/**
	 * @return 返回 currnet_date。
	 */
	public Integer getCurrnet_date() {
		return currnet_date;
	}
	/**
	 * @param currnet_date 要设置的 currnet_date。
	 */
	public void setCurrnet_date(Integer currnet_date) {
		this.currnet_date = currnet_date;
	}
	/**
	 * @return 返回 cust_id。
	 */
	public Integer getCust_id() {
		return cust_id;
	}
	/**
	 * @param cust_id 要设置的 cust_id。
	 */
	public void setCust_id(Integer cust_id) {
		this.cust_id = cust_id;
	}
	/**
	 * @return 返回 cust_source。
	 */
	public Integer getCust_source() {
		return cust_source;
	}
	/**
	 * @param cust_source 要设置的 cust_source。
	 */
	public void setCust_source(Integer cust_source) {
		this.cust_source = cust_source;
	}
	/**
	 * @return 返回 end_date。
	 */
	public Integer getEnd_date() {
		return end_date;
	}
	/**
	 * @param end_date 要设置的 end_date。
	 */
	public void setEnd_date(Integer end_date) {
		this.end_date = end_date;
	}
	/**
	 * @return 返回 end_threshold。
	 */
	public BigDecimal getEnd_threshold() {
		return end_threshold;
	}
	/**
	 * @param end_threshold 要设置的 end_threshold。
	 */
	public void setEnd_threshold(BigDecimal end_threshold) {
		this.end_threshold = end_threshold;
	}
	/**
	 * @return 返回 false_value。
	 */
	public String getFalse_value() {
		return false_value;
	}
	/**
	 * @param false_value 要设置的 false_value。
	 */
	public void setFalse_value(String false_value) {
		this.false_value = false_value;
	}
	/**
	 * @return 返回 include_end。
	 */
	public Integer getInclude_end() {
		return include_end;
	}
	/**
	 * @param include_end 要设置的 include_end。
	 */
	public void setInclude_end(Integer include_end) {
		this.include_end = include_end;
	}
	/**
	 * @return 返回 include_top。
	 */
	public Integer getInclude_top() {
		return include_top;
	}
	/**
	 * @param include_top 要设置的 include_top。
	 */
	public void setInclude_top(Integer include_top) {
		this.include_top = include_top;
	}
	/**
	 * @return 返回 multiple。
	 */
	public BigDecimal getMultiple() {
		return multiple;
	}
	/**
	 * @param multiple 要设置的 multiple。
	 */
	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}
	/**
	 * @return 返回 operand_id。
	 */
	public Integer getOperand_id() {
		return operand_id;
	}
	/**
	 * @param operand_id 要设置的 operand_id。
	 */
	public void setOperand_id(Integer operand_id) {
		this.operand_id = operand_id;
	}
	/**
	 * @return 返回 operand_name。
	 */
	public String getOperand_name() {
		return operand_name;
	}
	/**
	 * @param operand_name 要设置的 operand_name。
	 */
	public void setOperand_name(String operand_name) {
		this.operand_name = operand_name;
	}
	/**
	 * @return 返回 operand_no。
	 */
	public String getOperand_no() {
		return operand_no;
	}
	/**
	 * @param operand_no 要设置的 operand_no。
	 */
	public void setOperand_no(String operand_no) {
		this.operand_no = operand_no;
	}
	/**
	 * @return 返回 operand_xh。
	 */
	public String getOperand_xh() {
		return operand_xh;
	}
	/**
	 * @param operand_xh 要设置的 operand_xh。
	 */
	public void setOperand_xh(String operand_xh) {
		this.operand_xh = operand_xh;
	}
	/**
	 * @return 返回 operation_value。
	 */
	public String getOperation_value() {
		return operation_value;
	}
	/**
	 * @param operation_value 要设置的 operation_value。
	 */
	public void setOperation_value(String operation_value) {
		this.operation_value = operation_value;
	}
	/**
	 * @return 返回 rating_date。
	 */
	public Integer getRating_date() {
		return rating_date;
	}
	/**
	 * @param rating_date 要设置的 rating_date。
	 */
	public void setRating_date(Integer rating_date) {
		this.rating_date = rating_date;
	}
	/**
	 * @return 返回 rating_id。
	 */
	public Integer getRating_id() {
		return rating_id;
	}
	/**
	 * @param rating_id 要设置的 rating_id。
	 */
	public void setRating_id(Integer rating_id) {
		this.rating_id = rating_id;
	}
	/**
	 * @return 返回 rating_name。
	 */
	public String getRating_name() {
		return rating_name;
	}
	/**
	 * @param rating_name 要设置的 rating_name。
	 */
	public void setRating_name(String rating_name) {
		this.rating_name = rating_name;
	}
	/**
	 * @return 返回 rating_no。
	 */
	public String getRating_no() {
		return rating_no;
	}
	/**
	 * @param rating_no 要设置的 rating_no。
	 */
	public void setRating_no(String rating_no) {
		this.rating_no = rating_no;
	}
	/**
	 * @return 返回 regulation。
	 */
	public Integer getRegulation() {
		return regulation;
	}
	/**
	 * @param regulation 要设置的 regulation。
	 */
	public void setRegulation(Integer regulation) {
		this.regulation = regulation;
	}
	/**
	 * @return 返回 score。
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score 要设置的 score。
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * @return 返回 score_lower。
	 */
	public Integer getScore_lower() {
		return score_lower;
	}
	/**
	 * @param score_lower 要设置的 score_lower。
	 */
	public void setScore_lower(Integer score_lower) {
		this.score_lower = score_lower;
	}
	/**
	 * @return 返回 score_upper。
	 */
	public Integer getScore_upper() {
		return score_upper;
	}
	/**
	 * @param score_upper 要设置的 score_upper。
	 */
	public void setScore_upper(Integer score_upper) {
		this.score_upper = score_upper;
	}
	/**
	 * @return 返回 scoring。
	 */
	public Integer getScoring() {
		return scoring;
	}
	/**
	 * @param scoring 要设置的 scoring。
	 */
	public void setScoring(Integer scoring) {
		this.scoring = scoring;
	}
	/**
	 * @return 返回 scoring_date。
	 */
	public Integer getScoring_date() {
		return scoring_date;
	}
	/**
	 * @param scoring_date 要设置的 scoring_date。
	 */
	public void setScoring_date(Integer scoring_date) {
		this.scoring_date = scoring_date;
	}
	/**
	 * @return 返回 source。
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * @param source 要设置的 source。
	 */
	public void setSource(Integer source) {
		this.source = source;
	}
	/**
	 * @return 返回 source_field。
	 */
	public String getSource_field() {
		return source_field;
	}
	/**
	 * @param source_field 要设置的 source_field。
	 */
	public void setSource_field(String source_field) {
		this.source_field = source_field;
	}
	/**
	 * @return 返回 source_table。
	 */
	public String getSource_table() {
		return source_table;
	}
	/**
	 * @param source_table 要设置的 source_table。
	 */
	public void setSource_table(String source_table) {
		this.source_table = source_table;
	}
	/**
	 * @return 返回 subject_id。
	 */
	public Integer getSubject_id() {
		return subject_id;
	}
	/**
	 * @param subject_id 要设置的 subject_id。
	 */
	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}
	/**
	 * @return 返回 subject_name。
	 */
	public String getSubject_name() {
		return subject_name;
	}
	/**
	 * @param subject_name 要设置的 subject_name。
	 */
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	/**
	 * @return 返回 subject_no。
	 */
	public String getSubject_no() {
		return subject_no;
	}
	/**
	 * @param subject_no 要设置的 subject_no。
	 */
	public void setSubject_no(String subject_no) {
		this.subject_no = subject_no;
	}
	/**
	 * @return 返回 summary。
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary 要设置的 summary。
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return 返回 top_threshold。
	 */
	public BigDecimal getTop_threshold() {
		return top_threshold;
	}
	/**
	 * @param top_threshold 要设置的 top_threshold。
	 */
	public void setTop_threshold(BigDecimal top_threshold) {
		this.top_threshold = top_threshold;
	}

	/**
	 * @return 返回 true_false_value。
	 */
	public Integer getTrue_false_value() {
		return true_false_value;
	}
	/**
	 * @param true_false_value 要设置的 true_false_value。
	 */
	public void setTrue_false_value(Integer true_false_value) {
		this.true_false_value = true_false_value;
	}
	/**
	 * @return 返回 true_value。
	 */
	public String getTrue_value() {
		return true_value;
	}
	/**
	 * @param true_value 要设置的 true_value。
	 */
	public void setTrue_value(String true_value) {
		this.true_value = true_value;
	}
	/**
	 * @return 返回 weight。
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight 要设置的 weight。
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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
	 * @return 返回 manual_id。
	 */
	public Integer getManual_id() {
		return manual_id;
	}
	/**
	 * @param manual_id 要设置的 manual_id。
	 */
	public void setManual_id(Integer manual_id) {
		this.manual_id = manual_id;
	}
	
	
	/**
	 * @return 返回 cust_name。
	 */
	public String getCust_name() {
		return cust_name;
	}
	/**
	 * @param cust_name 要设置的 cust_name。
	 */
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	/**
	 * @return 返回 cust_no。
	 */
	public String getCust_no() {
		return cust_no;
	}
	/**
	 * @param cust_no 要设置的 cust_no。
	 */
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
	}
	
	
	/**
	 * @return 返回 operand_c_id。
	 */
	public Integer getOperand_c_id() {
		return operand_c_id;
	}
	/**
	 * @param operand_c_id 要设置的 operand_c_id。
	 */
	public void setOperand_c_id(Integer operand_c_id) {
		this.operand_c_id = operand_c_id;
	}
	/**
	 * @return 返回 operand_v_id。
	 */
	public Integer getOperand_v_id() {
		return operand_v_id;
	}
	/**
	 * @param operand_v_id 要设置的 operand_v_id。
	 */
	public void setOperand_v_id(Integer operand_v_id) {
		this.operand_v_id = operand_v_id;
	}
}
