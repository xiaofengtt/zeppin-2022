/*
 * �������� 2009-11-24
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * ������ϵ�����ӦGradeInfoVO����
 * @author dingyj
 * @since 2009-11-24
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class GradeInfoVO {

	private Integer grade_id;
	private String grade_bh;
	private Integer grade_type;
	private Integer grade_sub_type;
	private String grade_name;

	/**
	 * @return
	 */
	public String getGrade_bh() {
		return grade_bh;
	}

	/**
	 * @return
	 */
	public Integer getGrade_id() {
		return grade_id;
	}

	/**
	 * @return
	 */
	public String getGrade_name() {
		return grade_name;
	}

	/**
	 * @return
	 */
	public Integer getGrade_sub_type() {
		return grade_sub_type;
	}

	/**
	 * @return
	 */
	public Integer getGrade_type() {
		return grade_type;
	}

	/**
	 * @param string
	 */
	public void setGrade_bh(String string) {
		grade_bh = string;
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
	public void setGrade_name(String string) {
		grade_name = string;
	}

	/**
	 * @param integer
	 */
	public void setGrade_sub_type(Integer integer) {
		grade_sub_type = integer;
	}

	/**
	 * @param integer
	 */
	public void setGrade_type(Integer integer) {
		grade_type = integer;
	}
}
