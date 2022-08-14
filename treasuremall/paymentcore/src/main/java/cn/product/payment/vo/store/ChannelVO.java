package cn.product.payment.vo.store;

import java.io.Serializable;

import cn.product.payment.entity.Channel;

public class ChannelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8373684187185401657L;
	
	private String uuid;
	private String name;
	private String code;
    private String type;
    private String status;
	
	public ChannelVO() {
		super();
	}
	
	public ChannelVO (Channel channel) {
		this.uuid = channel.getUuid();
		this.name = channel.getName();
		this.code = channel.getCode();
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