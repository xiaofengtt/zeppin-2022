/*
 * CourseItem.java
 *
 * Created on 2004��11��26��, ����2:31
 */

package com.whaty.platform.entity.basic;
import java.util.Hashtable;
import java.util.List;
/**
 *
 * @author  Administrator
 */


public abstract class CourseItem{
    
	
	private String id="";
	private String dayi="";
	private String gonggao="";
	private String taolun="";
	private String kaoshi="";
	private String zuoye="";
	private String ziyuan="";
	private String zxdayi="";
	private String smzuoye="";
	private String zice="";
	private String pingjia="";
	private String daohang="";
	private String daoxue="";
	private String shiyan="";
	private String zfx="";
	private String boke="";
	
    private Hashtable item;
    
    private Course course;
    
    /** Creates a new instance of CourseItem */
    public CourseItem() {
    }
    
    /**
     * ���� item �Ļ�ȡ������
     * @return ���� item ��ֵ��
     */
    public java.util.Hashtable getItem() {
        return item;
    }
    
       
    /**
     * ���� course �Ļ�ȡ������
     * @return ���� course ��ֵ��
     */
    public Course getCourse() {
        return course;
    }
    
    public abstract void setAndUnsetItem(java.lang.String itemId);
    
    
    public abstract List getItemById(String id);
    

	public String getDayi() {
		return dayi;
	}

	public void setDayi(String dayi) {
		this.dayi = dayi;
	}

	public String getGonggao() {
		return gonggao;
	}

	public void setGonggao(String gonggao) {
		this.gonggao = gonggao;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKaoshi() {
		return kaoshi;
	}

	public void setKaoshi(String kaoshi) {
		this.kaoshi = kaoshi;
	}

	public String getTaolun() {
		return taolun;
	}

	public void setTaolun(String taolun) {
		this.taolun = taolun;
	}

	public String getZiyuan() {
		return ziyuan;
	}

	public void setZiyuan(String ziyuan) {
		this.ziyuan = ziyuan;
	}

	public String getZuoye() {
		return zuoye;
	}

	public void setZuoye(String zuoye) {
		this.zuoye = zuoye;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getDaohang() {
		return daohang;
	}

	public void setDaohang(String daohang) {
		this.daohang = daohang;
	}

	public String getDaoxue() {
		return daoxue;
	}

	public void setDaoxue(String daoxue) {
		this.daoxue = daoxue;
	}

	public String getPingjia() {
		return pingjia;
	}

	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}

	public String getSmzuoye() {
		return smzuoye;
	}

	public void setSmzuoye(String smzuoye) {
		this.smzuoye = smzuoye;
	}

	public String getZice() {
		return zice;
	}

	public void setZice(String zice) {
		this.zice = zice;
	}

	public String getZxdayi() {
		return zxdayi;
	}

	public void setZxdayi(String zxdayi) {
		this.zxdayi = zxdayi;
	}

	public String getBoke() {
		return boke;
	}

	public void setBoke(String boke) {
		this.boke = boke;
	}

	public String getShiyan() {
		return shiyan;
	}

	public void setShiyan(String shiyan) {
		this.shiyan = shiyan;
	}

	public String getZfx() {
		return zfx;
	}

	public void setZfx(String zfx) {
		this.zfx = zfx;
	}
       
}
