package cn.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;

import cn.product.score.entity.FrontUserHistory;
import cn.product.score.entity.FrontUserHistoryCheck;
import cn.product.score.util.JSONUtils;
import cn.product.score.util.Utlity;

public class FrontUserHistoryCheckDetailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8052316184218898258L;
	
	private String uuid;
	private String frontUserHistory;
	private String type;
	private String transType;
	private FrontUserHistoryVO value;
	private FrontUserHistoryVO old;
	private String reason;
	private String proof;
	private String proofUrl;
	private String status;
	private Timestamp submittime;
	private String checker;
	private String checkerName;
	private Timestamp checktime;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public FrontUserHistoryCheckDetailVO(FrontUserHistoryCheck fuhc){
		this.uuid = fuhc.getUuid();
		this.frontUserHistory = fuhc.getFrontUserHistory();
		this.transType = fuhc.getTransType();
		this.type = fuhc.getType();
		this.value = new FrontUserHistoryVO(JSONUtils.json2obj(fuhc.getValue(), FrontUserHistory.class));
		if(!Utlity.checkStringNull(fuhc.getOld())){
			this.old = new FrontUserHistoryVO(JSONUtils.json2obj(fuhc.getOld(), FrontUserHistory.class));
		}
		this.reason = fuhc.getReason();
		this.proof = fuhc.getProof();
		this.status = fuhc.getStatus();
		this.submittime = fuhc.getSubmittime();
		this.checker = fuhc.getChecker();
		this.checktime = fuhc.getChecktime();
		this.creator = fuhc.getCreator();
		this.createtime = fuhc.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFrontUserHistory() {
		return frontUserHistory;
	}

	public void setFrontUserHistory(String frontUserHistory) {
		this.frontUserHistory = frontUserHistory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public FrontUserHistoryVO getValue() {
		return value;
	}

	public void setValue(FrontUserHistoryVO value) {
		this.value = value;
	}

	public FrontUserHistoryVO getOld() {
		return old;
	}

	public void setOld(FrontUserHistoryVO old) {
		this.old = old;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getProof() {
		return proof;
	}

	public void setProof(String proof) {
		this.proof = proof;
	}

	public String getProofUrl() {
		return proofUrl;
	}

	public void setProofUrl(String proofUrl) {
		this.proofUrl = proofUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Timestamp submittime) {
		this.submittime = submittime;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public Timestamp getChecktime() {
		return checktime;
	}

	public void setChecktime(Timestamp checktime) {
		this.checktime = checktime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}