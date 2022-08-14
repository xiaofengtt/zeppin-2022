package cn.product.worldmall.entity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * 黑名单
 */
public class FrontUserBlacklist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3241219271295596206L;
	
	private String uuid;
	private String frontUser;
	private Integer showId;
    private String nickname;
    private String reason;
    private Timestamp createtime;
    
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFrontUser() {
		return frontUser;
	}
	public void setFrontUser(String frontUser) {
		this.frontUser = frontUser;
	}
	public Integer getShowId() {
		return showId;
	}
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
    
}