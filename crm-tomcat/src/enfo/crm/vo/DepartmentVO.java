/*
 * �������� 2008-5-26
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author Administrator
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class DepartmentVO {

	private Integer depart_id;
	private String depart_name;
	private String depart_jc;//���ż��
	private Integer parent_id;//�ϼ����ű��
	private String manager_man;//���Ÿ�����
	private String leader_man;//�߹��쵼
	
	private Integer bottom_flag;//��Ͳ��ű�־ 1�� 2��
	private Integer input_man;//����Ա
	
	/**
	 * @return
	 */
	public Integer getDepart_id() {
		return depart_id;
	}

	/**
	 * @return
	 */
	public String getDepart_jc() {
		return depart_jc;
	}

	/**
	 * @return
	 */
	public String getDepart_name() {
		return depart_name;
	}
	
	/**
	 * @return
	 */
	public String getLeader_man() {
		return leader_man;
	}

	/**
	 * @return
	 */
	public String getManager_man() {
		return manager_man;
	}

	/**
	 * @return
	 */
	public Integer getParent_id() {
		return parent_id;
	}

	/**
	 * @param integer
	 */
	public void setDepart_id(Integer integer) {
		depart_id = integer;
	}

	/**
	 * @param string
	 */
	public void setDepart_jc(String string) {
		depart_jc = string;
	}

	/**
	 * @param string
	 */
	public void setDepart_name(String string) {
		depart_name = string;
	}

	/**
	 * @param string
	 */
	public void setLeader_man(String string) {
		leader_man = string;
	}

	/**
	 * @param string
	 */
	public void setManager_man(String string) {
		manager_man = string;
	}

	/**
	 * @param integer
	 */
	public void setParent_id(Integer integer) {
		parent_id = integer;
	}

	/**
	 * @return
	 */
	public Integer getBottom_flag() {
		return bottom_flag;
	}

	/**
	 * @param integer
	 */
	public void setBottom_flag(Integer integer) {
		bottom_flag = integer;
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
