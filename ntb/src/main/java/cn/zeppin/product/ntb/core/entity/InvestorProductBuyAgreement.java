/**
 * 
 */
package cn.zeppin.product.ntb.core.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.zeppin.product.ntb.core.entity.base.BaseEntity;

/**
 * @author Clark.R 2016年9月19日
 *
 * 尝试不用Hibernate的外键对象原因（所有外键字段全部以基本型数据而不是对象体现）
 * 1、外键对象全部读取效率低，高效率的lazy load配置复杂
 * 2、利于构建基于Uuid的缓存层的实现（全部用dao调用实现关联对象读取）
 * 3、对象Json序列化涉及到对象循环引用引起的死循环的问题
 * 4、Control层返回对象基本不用多级对象直接生成的Json格式，Json返回值格式以扁平化结构为佳
 * 5、复杂查询（尤其是含有统计数据的返回值列表）一般都是自定义的返回值格式
 * 
 * 
 * @description 【数据对象】投资人理财产品购买记录
 */

@Entity
@Table(name = "investor_product_buy_agreement")
public class InvestorProductBuyAgreement extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6121638623124350691L;
	
	private String uuid;
	private String investor;
	
	private String records;
	private String name;
	
	private Timestamp createtime;
	private String type;
	
	private String status;
	
	private String scode;
	
	private String url;
	
	public class InvestorProductBuyAgreementStatus{
		public final static String FAIL = "fail";
		public final static String CONFIRMING = "confirming";
		public final static String SUCCESS = "success";
		public final static String FINISHED = "finished";
	}
	
	public class InvestorProductBuyAgreementType{
		public final static String BANKPRODUCT = "bankproduct";//银行理财产品
	}
	
	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "investor", nullable = false, length = 36)
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@Column(name = "records", nullable = false, length = 36)
	public String getRecords() {
		return records;
	}
	

	public void setRecords(String records) {
		this.records = records;
	}
	
	@Column(name = "name", nullable = false, length = 200)
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}
	

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "scode", nullable = false, length = 36)
	public String getScode() {
		return scode;
	}
	

	public void setScode(String code) {
		this.scode = code;
	}

	@Column(name = "url", nullable = false, length = 300)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
