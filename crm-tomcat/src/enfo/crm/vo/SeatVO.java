/*
 * �������� 2009-12-19
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.vo;

/**
 * @author tao
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class SeatVO {
	private Integer managerID;
	private Integer extension;
	private Integer startDate;
	private Integer endDate;
	private Integer inputman;
	private Integer statFlag;
	
	/**
	 * @return
	 */
	public Integer getEndDate() {
		return endDate;
	}

	/**
	 * @return
	 */
	public Integer getExtension() {
		return extension;
	}

	/**
	 * @return
	 */
	public Integer getInputman() {
		return inputman;
	}

	/**
	 * @return
	 */
	public Integer getManagerID() {
		return managerID;
	}

	/**
	 * @return
	 */
	public Integer getStartDate() {
		return startDate;
	}

	/**
	 * @param integer
	 */
	public void setEndDate(Integer integer) {
		endDate = integer;
	}

	/**
	 * @param integer
	 */
	public void setExtension(Integer integer) {
		extension = integer;
	}

	/**
	 * @param integer
	 */
	public void setInputman(Integer integer) {
		inputman = integer;
	}

	/**
	 * @param integer
	 */
	public void setManagerID(Integer integer) {
		managerID = integer;
	}

	/**
	 * @param integer
	 */
	public void setStartDate(Integer integer) {
		startDate = integer;
	}

	/**
	 * @return
	 */
	public Integer getStatFlag() {
		return statFlag;
	}

	/**
	 * @param integer
	 */
	public void setStatFlag(Integer integer) {
		statFlag = integer;
	}

}
