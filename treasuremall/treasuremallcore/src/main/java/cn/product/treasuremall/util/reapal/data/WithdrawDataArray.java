package cn.product.treasuremall.util.reapal.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class WithdrawDataArray implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4358310568897110866L;

	private List<WithdrawData> content;
	private BigDecimal price;
	private Integer count;
	private String trans_time;
	private String batch_no;

	public WithdrawDataArray() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WithdrawDataArray(List<WithdrawData> content) {
		super();
		this.content = content;
		BigDecimal price = BigDecimal.ZERO;
		for(WithdrawData wd : content){
			price = price.add(wd.getPrice());
		}
		this.price = price;
		this.count = content.size();
	}

	public List<WithdrawData> getContent() {
		return content;
	}

	public void setContent(List<WithdrawData> content) {
		this.content = content;
		BigDecimal price = BigDecimal.ZERO;
		for(WithdrawData wd : content){
			price = price.add(wd.getPrice());
		}
		this.price = price;
		this.count = content.size();
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	

	public Integer getCount() {
		return count;
	}
	

	public void setCount(Integer count) {
		this.count = count;
	}
	

	public String getTrans_time() {
		return trans_time;
	}
	

	public void setTrans_time(String trans_time) {
		this.trans_time = trans_time;
	}
	

	public String getBatch_no() {
		return batch_no;
	}
	

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	

	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		for(WithdrawData wd : content){
			str.append(wd.toString()+"|");
		}
		str.delete(str.length() - 1, str.length());
		return str.toString();
	}
	
}
