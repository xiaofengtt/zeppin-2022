/*
 * �������� 2011-8-23
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
package enfo.crm.vo;

import java.math.BigDecimal;

/**
 * @author Administrator
 *
 * TODO Ҫ���Ĵ����ɵ�����ע�͵�ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */
public class TcoMaintainMoneyDetailVO {
	private Integer moneymx_id;
	private Integer maintain_id;
	private Integer arrive_time;
	private BigDecimal   arrive_money;
	private String arrive_summary;
	private Integer input_man;
	private Integer input_time;
	private Integer isAllArriveFlag;
	public Integer getMoneymx_id() {
		return moneymx_id;
	}
	public void setMoneymx_id(Integer moneymxId) {
		moneymx_id = moneymxId;
	}
	
	public Integer getMaintain_id() {
		return maintain_id;
	}
	public void setMaintain_id(Integer maintainId) {
		maintain_id = maintainId;
	}
	public Integer getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(Integer arriveTime) {
		arrive_time = arriveTime;
	}
	public BigDecimal getArrive_money() {
		return arrive_money;
	}
	public void setArrive_money(BigDecimal arriveMoney) {
		arrive_money = arriveMoney;
	}
	public String getArrive_summary() {
		return arrive_summary;
	}
	public void setArrive_summary(String arriveSummary) {
		arrive_summary = arriveSummary;
	}
	public Integer getInput_man() {
		return input_man;
	}
	public void setInput_man(Integer inputMan) {
		input_man = inputMan;
	}
	public Integer getInput_time() {
		return input_time;
	}
	public void setInput_time(Integer inputTime) {
		input_time = inputTime;
	}
	public Integer getIsAllArriveFlag() {
		return isAllArriveFlag;
	}
	public void setIsAllArriveFlag(Integer isAllArriveFlag) {
		this.isAllArriveFlag = isAllArriveFlag;
	}
	
}
