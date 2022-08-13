/**
 * 
 */
package com.whaty.platform.training.user;

import com.whaty.platform.User;

/**
 * @author chenjian
 *
 */
public class TrainingUser extends User {
	
	private String trainingUserType;
	
	private String nickName;
	
	private String phone;
	
	private String mobilePhone;

	public String getNickName() {
		if(nickName == null || nickName.length() ==0)
		return "不详";
		else return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getTrainingUserType() {
		if(trainingUserType == null || trainingUserType.length() ==0)
			return "不详";	
		return trainingUserType;
	}

	public void setTrainingUserType(String trainingUserType) {
		this.trainingUserType = trainingUserType;
	}

	public String getMobilePhone() {
		if(mobilePhone == null || mobilePhone.length() ==0)
			return "不详";	
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPhone() {
		if(phone == null || phone.length() ==0)
			return "不详";		
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
