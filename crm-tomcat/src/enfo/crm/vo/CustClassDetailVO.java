/*
 * �������� 2009-12-21
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * �ͻ��ּ�����֮��ϸVO
 * @author dingyj
 * @since 2009-12-21
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustClassDetailVO {

	private Integer class_define_id; //�ּ�ID
	
	private String class_define_name; //�ּ�����
	
	private Integer class_detail_id; //�ּ���ϸID
	
	private String class_detail_name; //�ּ���ϸ����
	
	private Integer class_minvalue; //��������Сֵ
	
	private Integer class_maxvalue; //���������ֵ
	
	private Integer canmodi; //�Ƿ���޸� 1�� 2��
	
	private Integer input_man; //����Ա
	
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
	public Integer getClass_detail_id() {
		return class_detail_id;
	}

	/**
	 * @return
	 */
	public String getClass_detail_name() {
		return class_detail_name;
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
	public void setClass_detail_id(Integer integer) {
		class_detail_id = integer;
	}

	/**
	 * @param string
	 */
	public void setClass_detail_name(String string) {
		class_detail_name = string;
	}

	/**
	 * @return
	 */
	public Integer getClass_maxvalue() {
		return class_maxvalue;
	}

	/**
	 * @return
	 */
	public Integer getClass_minvalue() {
		return class_minvalue;
	}

	/**
	 * @param integer
	 */
	public void setClass_maxvalue(Integer integer) {
		class_maxvalue = integer;
	}

	/**
	 * @param integer
	 */
	public void setClass_minvalue(Integer integer) {
		class_minvalue = integer;
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
