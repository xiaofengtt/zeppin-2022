package cn.product.payment.vo.system;

import java.io.Serializable;
import java.util.Map;

import cn.product.payment.entity.Channel;
import cn.product.payment.util.JSONUtils;

public class ChannelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8469367797725826743L;
	
	private String uuid;
	private String name;
	private String code;
    private String data;
    private Map<String, Object> dataMap;
    private String callbackUrl;
    private String type;
    private String status;
	
	public ChannelVO() {
		super();
	}
	
	public ChannelVO (Channel channel) {
		this.uuid = channel.getUuid();
		this.name = channel.getName();
		this.code = channel.getCode();
		this.data = channel.getData();
		this.dataMap = JSONUtils.json2map(channel.getData());
		this.callbackUrl = channel.getCallbackUrl();
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

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
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