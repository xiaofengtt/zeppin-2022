/*
 * 创建日期 2011-4-26
 *
 * 
 */

package enfo.crm.vo;

import java.util.Date;

public class CustClassLogListVO{
	
	private java.lang.Integer cust_id;
	private java.lang.String cust_no;
	private java.lang.String cust_name;
	private Integer previousdetail_id;
	private String  previousdetail_name;
	private Integer classdetail_id;
	private String  classdetail_name;
	private Integer insertman;
	private Date    inserttime;
	private Integer checkman;
	private Date    checktime;
	
	/**
	 * @return
	 */
	public java.lang.Integer getCust_id() {
		return cust_id;
	}
	
	/**
	 * @return
	 */
	public java.lang.String getCust_no() {
		return cust_no;
	}
	
	/**
	 * @return
	 */
	public java.lang.String getCust_name() {
		return cust_name;
	}
	
	/**
	 * @return
	 */
	public java.lang.Integer getPreviousdetail_id() {
		return previousdetail_id;
	}
	
	/**
	 * @return
	 */
	public java.lang.String getPreviousdetail_name() {
		return previousdetail_name;
	}
	
	/**
	 * @return
	 */
	public java.lang.Integer getClassdetail_id() {
		return classdetail_id;
	}
	/**
	 * @return
	 */
	public java.lang.String getcClassdetail_name() {
		return classdetail_name;
	}
	
	/**
	 * @return
	 */
	public java.lang.Integer getInsertman() {
		return insertman;
	}
	
	/**
	 *  @return
	 */
	public Date getInserttime(){
		return inserttime;
	}
	
	/**
	 * @return
	 */
	public java.lang.Integer getChecktman() {
		return checkman;
	}
	
	/**
	 *  @return
	 */
	public Date getChecktime(){
		return checktime;
	}
	/**
	 * @param integer
	 */
	public void  setCust_id(Integer integer) {
		 cust_id=integer;
	}
	
	/**
	 * @param string
	 */
	public void setCust_no(String string) {
		 cust_no=string;
	}
	
	/**
	 * @param string
	 */
	public void setCust_name(String string) {
		 cust_name =  string;
	}
	
	/**
	 * @param integer
	 */
	public void setPreviousdetail_id(Integer integer) {
		 previousdetail_id = integer;
	}
	
	/**
	 * @param string
	 */
	public void setPreviousdetail_name(String string) {
		 previousdetail_name = string;
	}
	
	/**
	 * @param integer
	 */
	public void setClassdetail_id(Integer integer) {
		classdetail_id = integer;
	}
	/**
	 * @param string
	 */
	public void setcClassdetail_name(String string) {
		classdetail_name = string;
	}
	
	/**
	 * @param integer
	 */
	public void setInsertman(Integer integer) {
		 insertman = integer;
	}
	
	/**
	 *  @param date
	 */
	public void setInserttime(Date date){
		 inserttime = date ;
	}
	
	/**
	 * @param integer
	 */
	public void setChecktman(Integer integer) {
		 checkman = integer;
	}
	
	/**
	 *  @param date
	 */
	public void setChecktime(Date date){
		checktime = date;
	}
}