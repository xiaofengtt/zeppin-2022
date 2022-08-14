package cn.product.score.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.product.score.entity.GuessingMatch;

public class GuessingMatchVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String category;
	private String infoMatch;
	private Timestamp time;
	private Object[] guessingTypeArray;
	private MatchVO matchDetail;
	private List<GuessingMatchTypeVO> oddsDetail;
	private String remark;
	private String status;
	private Integer sort;
	private String creator;
	private String creatorName;
	private Timestamp createtime;
	
	public GuessingMatchVO(GuessingMatch gm){
		this.uuid = gm.getUuid();
		this.category = gm.getCategory();
		this.infoMatch = gm.getInfoMatch();
		this.time = gm.getTime();
		this.remark = gm.getRemark();
		this.status = gm.getStatus();
		this.sort = gm.getSort();
		this.creator = gm.getCreator();
		this.createtime = gm.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getInfoMatch() {
		return infoMatch;
	}

	public void setInfoMatch(String infoMatch) {
		this.infoMatch = infoMatch;
	}

	public Object[] getGuessingTypeArray() {
		return guessingTypeArray;
	}

	public void setGuessingTypeArray(Object[] guessingTypeArray) {
		this.guessingTypeArray = guessingTypeArray;
	}

	public MatchVO getMatchDetail() {
		return matchDetail;
	}

	public void setMatchDetail(MatchVO matchDetail) {
		this.matchDetail = matchDetail;
	}

	public List<GuessingMatchTypeVO> getOddsDetail() {
		return oddsDetail;
	}

	public void setOddsDetail(List<GuessingMatchTypeVO> oddsDetail) {
		this.oddsDetail = oddsDetail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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