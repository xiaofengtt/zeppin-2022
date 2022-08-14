package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserBlacklist;

public class FrontUserBlacklistVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9016598432721869479L;
	
	private String uuid;
	private String frontUser;
	private FrontUserVO frontUserVO;
	private Integer showId;
    private String nickname;
    private String reason;
    private Timestamp createtime;
    
	public FrontUserBlacklistVO() {
		super();
	}
    
	public FrontUserBlacklistVO(FrontUserBlacklist fubl){
		this.uuid = fubl.getUuid();
		this.frontUser = fubl.getFrontUser();
		this.showId = fubl.getShowId();
		this.nickname = fubl.getNickname();
		this.reason = fubl.getReason();
		this.createtime = fubl.getCreatetime();
	}
	
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

	public FrontUserVO getFrontUserVO() {
		return frontUserVO;
	}

	public void setFrontUserVO(FrontUserVO frontUserVO) {
		this.frontUserVO = frontUserVO;
	}
}