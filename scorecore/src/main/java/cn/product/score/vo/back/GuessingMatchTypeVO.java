package cn.product.score.vo.back;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import cn.product.score.entity.GuessingMatchOdds;
import cn.product.score.entity.GuessingMatchType;

public class GuessingMatchTypeVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529678434857703117L;
	
	private String uuid;
	private String guessingMatch;
	private String oddsType;
	private String type;
	private BigDecimal maxMoney;
	private Boolean flagSingle;
	private Timestamp endtime;
	private List<GuessingMatchOdds> oddsList;
	private String status;
	private String creator;
	private Timestamp createtime;
	
	public GuessingMatchTypeVO(GuessingMatchType gmt){
		this.uuid = gmt.getUuid();
		this.guessingMatch = gmt.getGuessingMatch();
		this.oddsType = gmt.getOddsType();
		this.type = gmt.getType();
		this.maxMoney = gmt.getMaxMoney();
		this.flagSingle = gmt.getFlagSingle();
		this.endtime = gmt.getEndtime();
		this.status = gmt.getStatus();
		this.creator = gmt.getCreator();
		this.createtime = gmt.getCreatetime();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGuessingMatch() {
		return guessingMatch;
	}

	public void setGuessingMatch(String guessingMatch) {
		this.guessingMatch = guessingMatch;
	}

	public String getOddsType() {
		return oddsType;
	}

	public void setOddsType(String oddsType) {
		this.oddsType = oddsType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Boolean getFlagSingle() {
		return flagSingle;
	}

	public void setFlagSingle(Boolean flagSingle) {
		this.flagSingle = flagSingle;
	}

	public Timestamp getEndtime() {
		return endtime;
	}

	public void setEndtime(Timestamp endtime) {
		this.endtime = endtime;
	}

	public List<GuessingMatchOdds> getOddsList() {
		return oddsList;
	}

	public void setOddsList(List<GuessingMatchOdds> oddsList) {
		this.oddsList = oddsList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}