package cn.zeppin.product.ntb.backadmin.vo;

import java.math.BigDecimal;

import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * 用户每日充值提现统计查询
 * @author Administrator
 *
 */
public class InvestorTransferCountVO implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7063644119578448205L;
	
	private String createtime;
	private String type;
	private BigDecimal recharge;
	private BigDecimal withdraw;
	
	public InvestorTransferCountVO(){
		
	}
	
	public InvestorTransferCountVO(String createtime, String type, BigDecimal recharge, BigDecimal withdraw){
		this.createtime = createtime;
		this.type = type;
		this.recharge = recharge;
		this.withdraw = withdraw;
	}
	
	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getRecharge() {
		return recharge;
	}

	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	}

	public BigDecimal getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(BigDecimal withdraw) {
		this.withdraw = withdraw;
	}

}
