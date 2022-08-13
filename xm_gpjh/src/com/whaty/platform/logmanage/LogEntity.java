/**
 * 
 */
package com.whaty.platform.logmanage;

/**
 * @author wq
 *
 */
public abstract class LogEntity {
	private String id;
	private String userid;
	private String operate_time;
	private String behavior;
	private String status;
	private String notes;
	private String logtype;
	private String priority;
	/**
	 * @return Returns the behavior.
	 */
	public String getBehavior() {
		return behavior;
	}
	/**
	 * @param behavior The behavior to set.
	 */
	public void setBehavior(String behavior) {
		this.behavior = behavior;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the logtype.
	 */
	public String getLogtype() {
		return logtype;
	}
	/**
	 * @param logtype The logtype to set.
	 */
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	/**
	 * @return Returns the notes.
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes The notes to set.
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return Returns the operate_time.
	 */
	public String getOperate_time() {
		return operate_time;
	}
	/**
	 * @param operate_time The operate_time to set.
	 */
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	/**
	 * @return Returns the priority.
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority The priority to set.
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the userid.
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid The userid to set.
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
