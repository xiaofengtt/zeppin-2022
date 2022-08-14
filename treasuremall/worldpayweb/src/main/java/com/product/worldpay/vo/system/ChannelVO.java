package com.product.worldpay.vo.system;

import java.io.Serializable;
import java.util.Map;

import com.product.worldpay.entity.Channel;
import com.product.worldpay.util.JSONUtils;

public class ChannelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8469367797725826743L;
	
	private String uuid;
	private String name;
	private String code;
	private String currency;
    private String data;
    private Map<String, Object> dataMap;
    private String type;
    private String status;
	
	public ChannelVO() {
		super();
	}
	
	public ChannelVO (Channel channel) {
		this.uuid = channel.getUuid();
		this.name = channel.getName();
		this.code = channel.getCode();
		this.currency = channel.getCurrency();
		this.data = channel.getData();
		this.dataMap = JSONUtils.json2map(channel.getData());
		this.type = channel.getType();
		this.status = channel.getStatus();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}