/*
 * �������� 2009-12-21
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * �ͻ��ּ�����VO
 * @author dingyj
 * @since 2009-12-21
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustClassDefineVO {

	private Integer class_define_id; //�ּ�ID

	private String class_define_name; //����

	private String summary; //����
	
	private Integer default_value; //Ĭ��ֵ,������TCustClassDetail�е�һ����ϸIDֵ

	private Integer parent_id; //��ǰ�ּ��ĸ��ڵ�

	private Integer parent_value; //��ǰ�ּ��и��ڵ�ʱ��ָ�����ڵ���ϸֵΪ��ֵ�ǣ���ǰ�ּ�������Ч

	private Integer level_id; //��α��

	private Integer canmodi; //�Ƿ���޸� 1�� 2��
	
	private Integer input_man; //����Ա
	
	private Integer CD_no;//�ͻ�������2�ͻ�����
	
	public Integer getCD_no() {
		return CD_no;
	}

	public void setCD_no(Integer cDNo) {
		CD_no = cDNo;
	}

	/**
	 * @return
	 */
	public Integer getCanmodi() {
		return canmodi;
	}

	/**
	 * @return
	 */
	public Integer getClass_define_id() {
		return class_define_id;
	}

	/**
	 * @return
	 */
	public String getClass_define_name() {
		return class_define_name;
	}

	/**
	 * @return
	 */
	public Integer getLevel_id() {
		return level_id;
	}

	/**
	 * @return
	 */
	public Integer getParent_id() {
		return parent_id;
	}

	/**
	 * @return
	 */
	public Integer getParent_value() {
		return parent_value;
	}

	/**
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param integer
	 */
	public void setCanmodi(Integer integer) {
		canmodi = integer;
	}

	/**
	 * @param integer
	 */
	public void setClass_define_id(Integer integer) {
		class_define_id = integer;
	}

	/**
	 * @param string
	 */
	public void setClass_define_name(String string) {
		class_define_name = string;
	}

	/**
	 * @param integer
	 */
	public void setLevel_id(Integer integer) {
		level_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setParent_id(Integer integer) {
		parent_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setParent_value(Integer integer) {
		parent_value = integer;
	}

	/**
	 * @param string
	 */
	public void setSummary(String string) {
		summary = string;
	}

	/**
	 * @return
	 */
	public Integer getDefault_value() {
		return default_value;
	}

	/**
	 * @param integer
	 */
	public void setDefault_value(Integer integer) {
		default_value = integer;
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
