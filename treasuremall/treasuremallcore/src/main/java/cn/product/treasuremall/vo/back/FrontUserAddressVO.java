package cn.product.treasuremall.vo.back;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import cn.product.treasuremall.entity.FrontUserAddress;

public class FrontUserAddressVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 268270256059968391L;
	private String uuid;
	private String frontUser;
	private String receiver;
	private String area;
	private String areaScode;
	private List<String> areaNameList;
	private String address;
	private String phone;
	private Boolean isDefault;
	private Timestamp createtime;
    
	public FrontUserAddressVO(FrontUserAddress fua){
		this.uuid = fua.getUuid();
		this.frontUser = fua.getFrontUser();
		this.receiver = fua.getReceiver();
		this.area = fua.getArea();
		this.address = fua.getAddress();
		this.phone = fua.getPhone();
		this.isDefault = fua.getIsDefault();
		this.createtime = fua.getCreatetime();
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

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<String> getAreaNameList() {
		return areaNameList;
	}

	public void setAreaNameList(List<String> areaNameList) {
		this.areaNameList = areaNameList;
	}

	public String getAreaScode() {
		return areaScode;
	}

	public void setAreaScode(String areaScode) {
		this.areaScode = areaScode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}