package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.worldmall.entity.FrontUserEdit;

public class FrontUserEditVO implements Serializable {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8041524874793678156L;
	
	private String uuid;
	private String frontUser;
	private String type;
	private String infoBefore;
	private String status;
	private String infoAfter;
	private Timestamp createtime;
	private String reason;
	
	private String operator;
	private Timestamp operattime;
	
	private String nickName;
	private Integer frontUserShowId;
	
	private String infoBeforeUrl;
	private String infoAfterUrl;
	
	
	public FrontUserEditVO(){
		
	}
	
	public FrontUserEditVO(FrontUserEdit frontUserEdit){
		this.uuid = frontUserEdit.getUuid();
		this.frontUser = frontUserEdit.getFrontUser();
		this.type = frontUserEdit.getType();
		this.infoBefore = frontUserEdit.getInfoBefore();
		this.infoAfter = frontUserEdit.getInfoAfter();
		this.reason = frontUserEdit.getReason();
		this.createtime = frontUserEdit.getCreatetime();
		this.operator = frontUserEdit.getOperator();
		this.operattime = frontUserEdit.getOperattime();
		this.status = frontUserEdit.getStatus();
		
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

	public Integer getFrontUserShowId() {
		return frontUserShowId;
	}

	public void setFrontUserShowId(Integer frontUserShowId) {
		this.frontUserShowId = frontUserShowId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfoBefore() {
		return infoBefore;
	}

	public void setInfoBefore(String infoBefore) {
		this.infoBefore = infoBefore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfoAfter() {
		return infoAfter;
	}

	public void setInfoAfter(String infoAfter) {
		this.infoAfter = infoAfter;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Timestamp getOperattime() {
		return operattime;
	}

	public void setOperattime(Timestamp operattime) {
		this.operattime = operattime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getInfoBeforeUrl() {
		return infoBeforeUrl;
	}

	public void setInfoBeforeUrl(String infoBeforeUrl) {
		this.infoBeforeUrl = infoBeforeUrl;
	}

	public String getInfoAfterUrl() {
		return infoAfterUrl;
	}

	public void setInfoAfterUrl(String infoAfterUrl) {
		this.infoAfterUrl = infoAfterUrl;
	}
}
