/*
 * �������� 2009-11-25
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * ������׼�����ӦGradeStandardVO����
 * @author dingyj
 * @since 2009-11-25
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class GradeStandardVO {

	private Integer serial_no; //���
	private Integer grade_id; //������ϵID
	private String grade_level; //������׼
	private String grade_level_name; //������׼����
	private String grade_level_sub; //��չ��
	private Integer min_value; //���ֵ
	private Integer max_value; //��Сֵ
	private String grade_info; //��������
	private Integer input_man; //ϸ��������Ա
	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @return
	 */
	public String getGrade_info() {
		return grade_info;
	}

	/**
	 * @return
	 */
	public String getGrade_level() {
		return grade_level;
	}

	/**
	 * @return
	 */
	public String getGrade_level_name() {
		return grade_level_name;
	}

	/**
	 * @return
	 */
	public String getGrade_level_sub() {
		return grade_level_sub;
	}

	/**
	 * @return
	 */
	public Integer getMax_value() {
		return max_value;
	}

	/**
	 * @return
	 */
	public Integer getMin_value() {
		return min_value;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setGrade_id(Integer integer) {
		grade_id = integer;
	}

	/**
	 * @param string
	 */
	public void setGrade_info(String string) {
		grade_info = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level(String string) {
		grade_level = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level_name(String string) {
		grade_level_name = string;
	}

	/**
	 * @param string
	 */
	public void setGrade_level_sub(String string) {
		grade_level_sub = string;
	}

	/**
	 * @param integer
	 */
	public void setMax_value(Integer integer) {
		max_value = integer;
	}

	/**
	 * @param integer
	 */
	public void setMin_value(Integer integer) {
		min_value = integer;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getInput_man() {
		return input_man;
	}

	/**
	 * @param integer
	 */
	public void setInput_man(Integer integer) {
		input_man = integer;
	}

}
