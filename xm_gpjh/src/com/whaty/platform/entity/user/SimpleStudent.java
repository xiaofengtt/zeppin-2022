/*
 * SimpleStudent.java
 *
 * Created on 2005��5��25��, ����1:52
 */

package com.whaty.platform.entity.user;



/**
 * �򻯵�ѧ�������Ϣ
 * @author Ligang
 */
public  class SimpleStudent extends EntityUser{
    
    /** Creates a new instance of SimpleStudent */
    public SimpleStudent() {
    }

    private String reg_no = "";

    private String major_id = "";

    private String idCardNo = "";

    private String major_name = "";

    private String edutype_id = "";

    private String edutype_name = "";

    private String site_id = "";

    private String site_name = "";

    private String grade_id = "";

    private String grade_name = "";

    private String gender = "";
    
    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getMajor_id() {
        return major_id;
    }

    public void setMajor_id(String major_id) {
        this.major_id = major_id;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getEdutype_id() {
        return edutype_id;
    }

    public void setEdutype_id(String edutype_id) {
        this.edutype_id = edutype_id;
    }

    public String getEdutype_name() {
        return edutype_name;
    }

    public void setEdutype_name(String edutype_name) {
        this.edutype_name = edutype_name;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getGrade_name() {
        return grade_name;
    }

    public void setGrade_name(String grade_name) {
        this.grade_name = grade_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

	

    
    
}
