package cn.zeppin.product.ntb.qcb.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * 用户每日充值提现统计查询
 * @author Administrator
 *
 */
public class QcbTransferCountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String createtime;
	private BigDecimal pay;
	private BigDecimal income;
	
	public QcbTransferCountVO(){
		
	}
	
	
	public QcbTransferCountVO(String createtime, BigDecimal pay,
			BigDecimal income) {
		super();
		this.createtime = createtime;
		this.pay = pay;
		this.income = income;
	}


	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}


	
	public BigDecimal getPay() {
		return pay;
	}
	


	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}
	


	public BigDecimal getIncome() {
		return income;
	}
	


	public void setIncome(BigDecimal income) {
		this.income = income;
	}
}
