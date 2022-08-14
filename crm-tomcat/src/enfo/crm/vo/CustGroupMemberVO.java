/*
 * 创建日期 2009-12-1
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.vo;

import java.sql.Timestamp;

/**
 * 客户成员群组对象对应CustGroupMemberVO对象
 * @author dingyj
 * @since 2009-12-1
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CustGroupMemberVO {

	private Integer serial_no;
	private Integer group_id; //群组编号
	private Integer cust_id; //客户ID
	private Integer insertMan; //操作员
	private Timestamp insertTime; //操作时间

	/**
	 * @return
	 */
	public Integer getCust_id() {
		return cust_id;
	}

	/**
	 * @return
	 */
	public Integer getInsertMan() {
		return insertMan;
	}

	/**
	 * @return
	 */
	public Timestamp getInsertTime() {
		return insertTime;
	}

	/**
	 * @return
	 */
	public Integer getSerial_no() {
		return serial_no;
	}

	/**
	 * @param integer
	 */
	public void setCust_id(Integer integer) {
		cust_id = integer;
	}

	/**
	 * @param integer
	 */
	public void setInsertMan(Integer integer) {
		insertMan = integer;
	}

	/**
	 * @param timestamp
	 */
	public void setInsertTime(Timestamp timestamp) {
		insertTime = timestamp;
	}

	/**
	 * @param integer
	 */
	public void setSerial_no(Integer integer) {
		serial_no = integer;
	}

	/**
	 * @return
	 */
	public Integer getGroup_id() {
		return group_id;
	}

	/**
	 * @param integer
	 */
	public void setGroup_id(Integer integer) {
		group_id = integer;
	}

}
