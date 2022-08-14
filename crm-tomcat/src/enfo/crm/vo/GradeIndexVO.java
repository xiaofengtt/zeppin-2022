/*
 * �������� 2009-11-25
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ���ֱ�׼�����ӦGradeIndexVO����
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class GradeIndexVO {

	private Integer index_id;
	private Integer grade_id;//������ϵID
	private Integer xh; //���
	private String index_type; //���
	private String index_type_name; //�������
	private String index_name; //ϸ������
	private Integer value_flag; //ֵ��Դ��1�ֹ�¼��2 ͨ������õ�3 ������
	private String value_unit; //��λ
	private String value_info; //ֵ����˵��
	private BigDecimal st_value; //��׼ֵ
	private BigDecimal zb_right; //Ȩ��
	private String df_gs; //�÷ֹ�ʽ
	private BigDecimal df_max; //���÷�
	private BigDecimal df_min; //��С�÷�
	private String df_info; //�÷ּ���˵��
	private Integer input_man; //ϸ��������Ա
	private Timestamp input_time; //ʱ��
	private Integer valid_flag; //�Ƿ���Ч
	private Integer op_code; //����Ա
	/**
	 * @return
	 */
	public String getDf_gs() {
		return df_gs;
	}

	/**
	 * @return
	 */
	public String getDf_info() {
		return df_info;
	}

	/**
	 * @return
	 */
	public BigDecimal getDf_max() {
		return df_max;
	}

	/**
	 * @return
	 */
	public BigDecimal getDf_min() {
		return df_min;
	}

	/**
	 * @return
	 */
	public Integer getIndex_id() {
		return index_id;
	}

	/**
	 * @return
	 */
	public String getIndex_name() {
		return index_name;
	}

	/**
	 * @return
	 */
	public String getIndex_type() {
		return index_type;
	}

	/**
	 * @return
	 */
	public String getIndex_type_name() {
		return index_type_name;
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
	public Timestamp getInput_time() {
		return input_time;
	}

	/**
	 * @return
	 */
	public BigDecimal getSt_value() {
		return st_value;
	}

	/**
	 * @return
	 */
	public Integer getValid_flag() {
		return valid_flag;
	}

	/**
	 * @return
	 */
	public Integer getValue_flag() {
		return value_flag;
	}

	/**
	 * @return
	 */
	public String getValue_info() {
		return value_info;
	}

	/**
	 * @return
	 */
	public String getValue_unit() {
		return value_unit;
	}

	/**
	 * @return
	 */
	public Integer getXh() {
		return xh;
	}

	/**
	 * @return
	 */
	public BigDecimal getZb_right() {
		return zb_right;
	}

	/**
	 * @param string
	 */
	public void setDf_gs(String string) {
		df_gs = string;
	}

	/**
	 * @param string
	 */
	public void setDf_info(String string) {
		df_info = string;
	}

	/**
	 * @param decimal
	 */
	public void setDf_max(BigDecimal decimal) {
		df_max = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setDf_min(BigDecimal decimal) {
		df_min = decimal;
	}

	/**
	 * @param integer
	 */
	public void setIndex_id(Integer integer) {
		index_id = integer;
	}

	/**
	 * @param string
	 */
	public void setIndex_name(String string) {
		index_name = string;
	}

	/**
	 * @param string
	 */
	public void setIndex_type(String string) {
		index_type = string;
	}

	/**
	 * @param string
	 */
	public void setIndex_type_name(String string) {
		index_type_name = string;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInput_time(Timestamp timestamp) {
		input_time = timestamp;
	}

	/**
	 * @param decimal
	 */
	public void setSt_value(BigDecimal decimal) {
		st_value = decimal;
	}

	/**
	 * @param integer
	 */
	public void setValid_flag(Integer integer) {
		valid_flag = integer;
	}

	/**
	 * @param integer
	 */
	public void setValue_flag(Integer integer) {
		value_flag = integer;
	}

	/**
	 * @param string
	 */
	public void setValue_info(String string) {
		value_info = string;
	}

	/**
	 * @param string
	 */
	public void setValue_unit(String string) {
		value_unit = string;
	}

	/**
	 * @param integer
	 */
	public void setXh(Integer integer) {
		xh = integer;
	}

	/**
	 * @param decimal
	 */
	public void setZb_right(BigDecimal decimal) {
		zb_right = decimal;
	}

	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @param integer
	 */
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @return
	 */
	public Integer getOp_code() {
		return op_code;
	}

	/**
	 * @param integer
	 */
	public void setOp_code(Integer integer) {
		op_code = integer;
	}

}
