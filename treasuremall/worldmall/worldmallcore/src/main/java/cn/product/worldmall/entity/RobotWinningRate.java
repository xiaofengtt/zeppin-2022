package cn.product.worldmall.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Id;

public class RobotWinningRate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6118360992516495607L;
	
	@Id
    private String uuid;
    private String gameType;
    
    private BigDecimal goodsPriceMin;
    private BigDecimal goodsPriceMax;
    
    private BigDecimal winningRate;
    
    private String status;
    
    private Timestamp createtime;
    
    public class RobotWinningRateStatus {
		public final static String NORMAL = "normal";
		public final static String DISABLE = "disable";
		public final static String DELETE = "delete";
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public BigDecimal getGoodsPriceMin() {
		return goodsPriceMin;
	}

	public void setGoodsPriceMin(BigDecimal goodsPriceMin) {
		this.goodsPriceMin = goodsPriceMin;
	}

	public BigDecimal getGoodsPriceMax() {
		return goodsPriceMax;
	}

	public void setGoodsPriceMax(BigDecimal goodsPriceMax) {
		this.goodsPriceMax = goodsPriceMax;
	}

	public BigDecimal getWinningRate() {
		return winningRate;
	}

	public void setWinningRate(BigDecimal winningRate) {
		this.winningRate = winningRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

    
}