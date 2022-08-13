package com.whaty.platform.entity.user;

import com.whaty.platform.util.Address;
import com.whaty.util.string.WhatyStrManage;

/**
 * @author chenjian ����������ƽ̨�û�����ͨ��Ϣ
 * 
 */
public class HumanNormalInfo {
	
    private String txt_Reg_No = null; //ѧ��
    
    private String study_no;// �����
	
	private String birthday = null; // ����

	private String gender = null; // �Ա�

	private String idcard = null; // ���֤����

	private String cardType = null; // ֤������

	private String degree = null; // ѧλ

	private String edu = null; // ѧ��

	private String folk = null; // ����

	private String hometown = null; // ����

	private String workplace = null; // ��λ

	private String position = null; // ְ��

	private String title = null; // ͷ��

	private String zzmm = null; // ������ò

	private String graduated_major = null; // ��ҵרҵ

	private String graduated_time = null; // ��ҵʱ��
   
	private String graduated_Sch_Id = null; //��ҵѧУID
	
	private String graduated_sch = null; // ��ҵѧУ

	private String[] phone = null; // �绰

	private String[] mobilePhone = null; // ����绰

	private String[] email = null; // �����ʼ�

	private String[] fax = null; // ����

	private Address work_address = null; // �����ַ

	private Address home_address = null; // ��ͥ��ַ

	private String note = null; // ��ע

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getFolk() {
		return folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGraduated_major() {
		return graduated_major;
	}

	public void setGraduated_major(String graduated_major) {
		this.graduated_major = graduated_major;
	}

	public String getGraduated_sch() {
		return graduated_sch;
	}

	public void setGraduated_sch(String graduated_sch) {
		this.graduated_sch = graduated_sch;
	}

	public String getGraduated_time() {
		return graduated_time;
	}

	public void setGraduated_time(String graduated_time) {
		this.graduated_time = graduated_time;
	}

	public Address getHome_address() {
		if (home_address == null)
			home_address = new Address();
		return home_address;
	}

	public void setHome_address(Address home_address) {
		this.home_address = home_address;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		WhatyStrManage strManage = new WhatyStrManage(idcard);
		this.idcard = strManage.ToDBC();
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getWork_address() {
		if (work_address == null)
			work_address = new Address();
		return work_address;
	}

	public void setWork_address(Address work_address) {
		this.work_address = work_address;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getZzmm() {
		return zzmm;
	}

	public void setZzmm(String zzmm) {
		this.zzmm = zzmm;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String[] getEmail() {
		return email;
	}

	public void setEmail(String[] email) {
		this.email = email;
	}

	public String[] getFax() {
		return fax;
	}

	public void setFax(String[] fax) {
		this.fax = fax;
	}

	public String[] getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String[] mobilePhone) {
		String[] mPhone = null;
		if (mobilePhone != null) {
			mPhone = new String[mobilePhone.length];
			WhatyStrManage strManage = new WhatyStrManage();
			for (int i = 0; i < mobilePhone.length; i++) {
				strManage.setString(mobilePhone[i]);
				mPhone[i] = strManage.ToDBC();
			}
		}
		this.mobilePhone = mPhone;
	}

	public String[] getPhone() {
		return phone;
	}

	public void setPhone(String[] phone) {
		this.phone = phone;
	}

	public String getPhones() {
		String phones = "";
		if (this.getPhone() != null && this.getPhone().length > 0) {
			for (int i = 0; i < this.getPhone().length; i++) {
				phones = phones + this.getPhone()[i] + ",";
			}
			if (phones.length() > 1)
				return phones.substring(0, phones.length() - 1);
			else
				return "";
		} else {
			return "";
		}
	}

	public String getMobilePhones() {
		String mobilePhones = "";
		if (this.getMobilePhone() != null && this.getMobilePhone().length > 0) {
			for (int i = 0; i < this.getMobilePhone().length; i++) {
				mobilePhones = mobilePhones + this.getMobilePhone()[i] + ",";
			}
			if (mobilePhones.length() > 1)
				return mobilePhones.substring(0, mobilePhones.length() - 1);
			else
				return "";
		} else {
			return "";
		}
	}

	public String getEmails() {
		String emails = "";
		if (this.getEmail() != null && this.getEmail().length > 0) {
			for (int i = 0; i < this.getEmail().length; i++) {
				emails = emails + this.getEmail()[i] + ",";
			}
			if (emails.length() > 1)
				return emails.substring(0, emails.length() - 1);
			else
				return "";
		} else {
			return "";
		}
	}

	public String getFaxs() {
		String faxs = "";
		if (this.getFax() != null && this.getFax().length > 0) {
			for (int i = 0; i < this.getFax().length; i++) {
				faxs = faxs + this.getFax()[i] + ",";
			}
			if (faxs.length() > 1)
				return faxs.substring(0, faxs.length() - 1);
			else
				return "";
		} else {
			return "";
		}
	}

	public String getTxt_Reg_No() {
		return txt_Reg_No;
	}

	public void setTxt_Reg_No(String txt_Reg_No) {
		this.txt_Reg_No = txt_Reg_No;
	}

	public String getGraduated_Sch_Id() {
		return graduated_Sch_Id;
	}

	public void setGraduated_Sch_Id(String graduated_Sch_Id) {
		this.graduated_Sch_Id = graduated_Sch_Id;
	}

	public String getStudy_no() {
		return study_no;
	}

	public void setStudy_no(String study_no) {
		this.study_no = study_no;
	}
}
