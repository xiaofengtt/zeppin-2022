package cn.product.worldmall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.product.worldmall.entity.InternationalRateHistory;
import cn.product.worldmall.entity.rate.DailyCurrencyRate;
import cn.product.worldmall.util.JSONUtils;
import cn.product.worldmall.util.Utlity;

public class InternationalRateHistoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6118433685632757993L;
	
	private String uuid;
	private String status;
	private Timestamp createtime;
	private Timestamp updatetime;
	private String dataInfo;
	private String dailyDate;
	private List<DailyCurrencyRate> dataInfoMapList;
	
    
	public InternationalRateHistoryVO(InternationalRateHistory irh){
		this.uuid = irh.getUuid();
		this.status = irh.getStatus();
		this.createtime = irh.getCreatetime();
		this.updatetime = irh.getUpdatetime();
		this.dataInfo = irh.getDataInfo();
		this.dailyDate = irh.getDailyDate();
		if(!Utlity.checkStringNull(irh.getDataInfo())) {
			this.dataInfoMapList = JSONUtils.json2list(irh.getDataInfo(), DailyCurrencyRate.class);
		}
	}


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getDataInfo() {
		return dataInfo;
	}

	public void setDataInfo(String dataInfo) {
		this.dataInfo = dataInfo;
	}

	public String getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(String dailyDate) {
		this.dailyDate = dailyDate;
	}


	public List<DailyCurrencyRate> getDataInfoMapList() {
		return dataInfoMapList;
	}


	public void setDataInfoMapList(List<DailyCurrencyRate> dataInfoMapList) {
		this.dataInfoMapList = dataInfoMapList;
	}
}