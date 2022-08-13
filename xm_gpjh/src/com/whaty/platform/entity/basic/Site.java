/*
 * Site.java
 *
 * Created on 2004��10��19��, ����3:31
 */

package com.whaty.platform.entity.basic;

/**
 * У�����Ķ���
 * @author  chenjian
 */
public abstract class Site implements com.whaty.platform.Items{
    
    private String id = "";
    
    private String code =""; //新加入,为与bjsy2统一
    
    private String name = "";
    
    private String address = "";
    
    private String email = "";
    
    private String manager = "";
    
    private String linkman = "";
    
    private String phone = "";
    
    private String fax = "";
    
    private String found_date = "";
    
    private String status = "";
    
    private String note = "";
    
    private String zip_code = "";
    
    private String URL = "";
    
    private String recruit_status = "";
    
    private String index_show = "";
    
    private String diqu_id = "";
    
    private String right = "";
    
    private String site_company = "" ;
    
    private String site_type = "" ;
    
    private String corporation = "" ;
    
    private String first_student_date = "" ;
    /**
     * ���� address �Ļ�ȡ������
     * @return ���� address ��ֵ��
     */
    public java.lang.String getAddress() {
        return address;
    }
    
    /**
     * ���� address �����÷�����
     * @param address ���� address ����ֵ��
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }
    
    /**
     * ���� email �Ļ�ȡ������
     * @return ���� email ��ֵ��
     */
    public java.lang.String getEmail() {
        return email;
    }
    
    /**
     * ���� email �����÷�����
     * @param email ���� email ����ֵ��
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }
    
    /**
     * ���� fax �Ļ�ȡ������
     * @return ���� fax ��ֵ��
     */
    public java.lang.String getFax() {
        return fax;
    }
    
    /**
     * ���� fax �����÷�����
     * @param fax ���� fax ����ֵ��
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }
    
    /**
     * ���� found_date �Ļ�ȡ������
     * @return ���� found_date ��ֵ��
     */
    public java.lang.String getFound_date() {
        return found_date;
    }
    
    /**
     * ���� found_date �����÷�����
     * @param found_date ���� found_date ����ֵ��
     */
    public void setFound_date(java.lang.String found_date) {
        this.found_date = found_date;
    }
    
    /**
     * ���� id �Ļ�ȡ������
     * @return ���� id ��ֵ��
     */
    public java.lang.String getId() {
        return id;
    }
    
    /**
     * ���� id �����÷�����
     * @param id ���� id ����ֵ��
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }
    
    /**
     * ���� linkman �Ļ�ȡ������
     * @return ���� linkman ��ֵ��
     */
    public java.lang.String getLinkman() {
        return linkman;
    }
    
    /**
     * ���� linkman �����÷�����
     * @param linkman ���� linkman ����ֵ��
     */
    public void setLinkman(java.lang.String linkman) {
        this.linkman = linkman;
    }
    
    /**
     * ���� manager �Ļ�ȡ������
     * @return ���� manager ��ֵ��
     */
    public java.lang.String getManager() {
        return manager;
    }
    
    /**
     * ���� manager �����÷�����
     * @param manager ���� manager ����ֵ��
     */
    public void setManager(java.lang.String manager) {
        this.manager = manager;
    }
    
    /**
     * ���� name �Ļ�ȡ������
     * @return ���� name ��ֵ��
     */
    public java.lang.String getName() {
        return name;
    }
    
    /**
     * ���� name �����÷�����
     * @param name ���� name ����ֵ��
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    /**
     * ���� note �Ļ�ȡ������
     * @return ���� note ��ֵ��
     */
    public java.lang.String getNote() {
        return note;
    }
    
    /**
     * ���� note �����÷�����
     * @param note ���� note ����ֵ��
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }
    
    /**
     * ���� phone �Ļ�ȡ������
     * @return ���� phone ��ֵ��
     */
    public java.lang.String getPhone() {
        return phone;
    }
    
    /**
     * ���� phone �����÷�����
     * @param phone ���� phone ����ֵ��
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }
    
    /**
     * ���� recruit_status �Ļ�ȡ������
     * @return ���� recruit_status ��ֵ��
     */
    public java.lang.String getRecruit_status() {
        return recruit_status;
    }
    
    /**
     * ���� recruit_status �����÷�����
     * @param recruit_status ���� recruit_status ����ֵ��
     */
    public void setRecruit_status(java.lang.String recruit_status) {
        this.recruit_status = recruit_status;
    }
    
    /**
     * ���� status �Ļ�ȡ������
     * @return ���� status ��ֵ��
     */
    public java.lang.String getStatus() {
        return status;
    }
    
    /**
     * ���� status �����÷�����
     * @param status ���� status ����ֵ��
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }
    
    /**
     * ���� zip_code �Ļ�ȡ������
     * @return ���� zip_code ��ֵ��
     */
    public java.lang.String getZip_code() {
        return zip_code;
    }
    
    /**
     * ���� zip_code �����÷�����
     * @param zip_code ���� zip_code ����ֵ��
     */
    public void setZip_code(java.lang.String zip_code) {
        this.zip_code = zip_code;
    }

    /**
     * �ý�ѧվid�Ƿ����
     * @return 0Ϊ�����ڣ�����0�����
     */
    public abstract int isIdExist();

    /**
     * �ý�ѧվ�µ�ѧ����
     * @return 0Ϊû�У�����0����
     */
    public abstract int studentNum();

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public String getIndex_show() {
		return index_show;
	}

	public void setIndex_show(String index_show) {
		this.index_show = index_show;
	}

	public String getDiqu_id() {
		return diqu_id;
	}

	public void setDiqu_id(String diqu_id) {
		this.diqu_id = diqu_id;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getFirst_student_date() {
		return first_student_date;
	}

	public void setFirst_student_date(String first_student_date) {
		this.first_student_date = first_student_date;
	}

	public String getSite_company() {
		return site_company;
	}

	public void setSite_company(String site_company) {
		this.site_company = site_company;
	}

	public String getSite_type() {
		return site_type;
	}

	public void setSite_type(String site_type) {
		this.site_type = site_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
	
    
}
