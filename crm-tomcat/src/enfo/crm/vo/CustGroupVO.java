/*
 * �������� 2009-12-1
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * �ͻ�Ⱥ������ӦCustGroupVO����
 * @author dingyj
 * @since 2009-12-1
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class CustGroupVO {
	
	private Integer groupId;//����ID
	private String groupName;//��������
	private Integer leftId;//��ID
	private Integer rightId;//��ID
	private Integer levelId;//���
	private Integer inputMan;//����Ա
	
	/**
	 * @return
	 */
	public Integer getGroupId() {
		return groupId;
	}

	/**
	 * @return
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @return
	 */
	public Integer getLeftId() {
		return leftId;
	}

	/**
	 * @return
	 */
	public Integer getLevelId() {
		return levelId;
	}

	/**
	 * @return
	 */
	public Integer getRightId() {
		return rightId;
	}

	/**
	 * @param integer
	 */
	public void setGroupId(Integer integer) {
		groupId = integer;
	}

	/**
	 * @param string
	 */
	public void setGroupName(String string) {
		groupName = string;
	}

	/**
	 * @param integer
	 */
	public void setLeftId(Integer integer) {
		leftId = integer;
	}

	/**
	 * @param integer
	 */
	public void setLevelId(Integer integer) {
		levelId = integer;
	}

	/**
	 * @param integer
	 */
	public void setRightId(Integer integer) {
		rightId = integer;
	}

	/**
	 * @return
	 */
	public Integer getInputMan() {
		return inputMan;
	}

	/**
	 * @param integer
	 */
	public void setInputMan(Integer integer) {
		inputMan = integer;
	}

}
