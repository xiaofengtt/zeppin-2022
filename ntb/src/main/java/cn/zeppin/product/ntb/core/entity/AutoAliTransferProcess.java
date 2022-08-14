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
 * @author hehe 2016年2月8日
 * @description 【数据对象】支付宝账单定时处理任务记录
 */

@Entity
@Table(name = "auto_ali_transfer_process")
public class AutoAliTransferProcess extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3698148359983723617L;
	
	private String uuid;
	private Integer processcount;
	private Timestamp processtime;
	private String status;
	private Timestamp createtime;
	private Integer processindex;
	
	public class AutoAliTransferProcessStatus {
		public final static String NORMAL = "normal";
		public final static String FAIL = "fail";
		public final static String DISABLE = "disable";
	}
	
	public AutoAliTransferProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false, length = 36)
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "process_count", nullable = false, length = 11)
	public Integer getProcesscount() {
		return processcount;
	}

	public void setProcesscount(Integer processcount) {
		this.processcount = processcount;
	}

	@Column(name = "process_time", nullable = false)
	public Timestamp getProcesstime() {
		return processtime;
	}

	public void setProcesstime(Timestamp processtime) {
		this.processtime = processtime;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createtime", nullable = false)
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "process_index", nullable = false, length = 11)
	public Integer getProcessindex() {
		return processindex;
	}

	public void setProcessindex(Integer processindex) {
		this.processindex = processindex;
	}

}
