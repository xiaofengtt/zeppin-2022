package com.whaty.platform.entity.bean;



/**
 * EntityCourseItemId entity. @author MyEclipse Persistence Tools
 */

public class EntityCourseItemId extends com.whaty.platform.entity.bean.AbstractBean implements java.io.Serializable {


    // Fields    

     private String id;
     private String dayi;
     private String gonggao;
     private String taolun;
     private String kaoshi;
     private String zuoye;
     private String ziyuan;
     private String zxdayi;
     private String smzuoye;
     private String zice;
     private String pingjia;
     private String daohang;
     private String daoxue;
     private String shiyan;
     private String zfx;
     private String boke;


    // Constructors

    /** default constructor */
    public EntityCourseItemId() {
    }

	/** minimal constructor */
    public EntityCourseItemId(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public EntityCourseItemId(String id, String dayi, String gonggao, String taolun, String kaoshi, String zuoye, String ziyuan, String zxdayi, String smzuoye, String zice, String pingjia, String daohang, String daoxue, String shiyan, String zfx, String boke) {
        this.id = id;
        this.dayi = dayi;
        this.gonggao = gonggao;
        this.taolun = taolun;
        this.kaoshi = kaoshi;
        this.zuoye = zuoye;
        this.ziyuan = ziyuan;
        this.zxdayi = zxdayi;
        this.smzuoye = smzuoye;
        this.zice = zice;
        this.pingjia = pingjia;
        this.daohang = daohang;
        this.daoxue = daoxue;
        this.shiyan = shiyan;
        this.zfx = zfx;
        this.boke = boke;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getDayi() {
        return this.dayi;
    }
    
    public void setDayi(String dayi) {
        this.dayi = dayi;
    }

    public String getGonggao() {
        return this.gonggao;
    }
    
    public void setGonggao(String gonggao) {
        this.gonggao = gonggao;
    }

    public String getTaolun() {
        return this.taolun;
    }
    
    public void setTaolun(String taolun) {
        this.taolun = taolun;
    }

    public String getKaoshi() {
        return this.kaoshi;
    }
    
    public void setKaoshi(String kaoshi) {
        this.kaoshi = kaoshi;
    }

    public String getZuoye() {
        return this.zuoye;
    }
    
    public void setZuoye(String zuoye) {
        this.zuoye = zuoye;
    }

    public String getZiyuan() {
        return this.ziyuan;
    }
    
    public void setZiyuan(String ziyuan) {
        this.ziyuan = ziyuan;
    }

    public String getZxdayi() {
        return this.zxdayi;
    }
    
    public void setZxdayi(String zxdayi) {
        this.zxdayi = zxdayi;
    }

    public String getSmzuoye() {
        return this.smzuoye;
    }
    
    public void setSmzuoye(String smzuoye) {
        this.smzuoye = smzuoye;
    }

    public String getZice() {
        return this.zice;
    }
    
    public void setZice(String zice) {
        this.zice = zice;
    }

    public String getPingjia() {
        return this.pingjia;
    }
    
    public void setPingjia(String pingjia) {
        this.pingjia = pingjia;
    }

    public String getDaohang() {
        return this.daohang;
    }
    
    public void setDaohang(String daohang) {
        this.daohang = daohang;
    }

    public String getDaoxue() {
        return this.daoxue;
    }
    
    public void setDaoxue(String daoxue) {
        this.daoxue = daoxue;
    }

    public String getShiyan() {
        return this.shiyan;
    }
    
    public void setShiyan(String shiyan) {
        this.shiyan = shiyan;
    }

    public String getZfx() {
        return this.zfx;
    }
    
    public void setZfx(String zfx) {
        this.zfx = zfx;
    }

    public String getBoke() {
        return this.boke;
    }
    
    public void setBoke(String boke) {
        this.boke = boke;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EntityCourseItemId) ) return false;
		 EntityCourseItemId castOther = ( EntityCourseItemId ) other; 
         
		 return ( (this.getId()==castOther.getId()) || ( this.getId()!=null && castOther.getId()!=null && this.getId().equals(castOther.getId()) ) )
 && ( (this.getDayi()==castOther.getDayi()) || ( this.getDayi()!=null && castOther.getDayi()!=null && this.getDayi().equals(castOther.getDayi()) ) )
 && ( (this.getGonggao()==castOther.getGonggao()) || ( this.getGonggao()!=null && castOther.getGonggao()!=null && this.getGonggao().equals(castOther.getGonggao()) ) )
 && ( (this.getTaolun()==castOther.getTaolun()) || ( this.getTaolun()!=null && castOther.getTaolun()!=null && this.getTaolun().equals(castOther.getTaolun()) ) )
 && ( (this.getKaoshi()==castOther.getKaoshi()) || ( this.getKaoshi()!=null && castOther.getKaoshi()!=null && this.getKaoshi().equals(castOther.getKaoshi()) ) )
 && ( (this.getZuoye()==castOther.getZuoye()) || ( this.getZuoye()!=null && castOther.getZuoye()!=null && this.getZuoye().equals(castOther.getZuoye()) ) )
 && ( (this.getZiyuan()==castOther.getZiyuan()) || ( this.getZiyuan()!=null && castOther.getZiyuan()!=null && this.getZiyuan().equals(castOther.getZiyuan()) ) )
 && ( (this.getZxdayi()==castOther.getZxdayi()) || ( this.getZxdayi()!=null && castOther.getZxdayi()!=null && this.getZxdayi().equals(castOther.getZxdayi()) ) )
 && ( (this.getSmzuoye()==castOther.getSmzuoye()) || ( this.getSmzuoye()!=null && castOther.getSmzuoye()!=null && this.getSmzuoye().equals(castOther.getSmzuoye()) ) )
 && ( (this.getZice()==castOther.getZice()) || ( this.getZice()!=null && castOther.getZice()!=null && this.getZice().equals(castOther.getZice()) ) )
 && ( (this.getPingjia()==castOther.getPingjia()) || ( this.getPingjia()!=null && castOther.getPingjia()!=null && this.getPingjia().equals(castOther.getPingjia()) ) )
 && ( (this.getDaohang()==castOther.getDaohang()) || ( this.getDaohang()!=null && castOther.getDaohang()!=null && this.getDaohang().equals(castOther.getDaohang()) ) )
 && ( (this.getDaoxue()==castOther.getDaoxue()) || ( this.getDaoxue()!=null && castOther.getDaoxue()!=null && this.getDaoxue().equals(castOther.getDaoxue()) ) )
 && ( (this.getShiyan()==castOther.getShiyan()) || ( this.getShiyan()!=null && castOther.getShiyan()!=null && this.getShiyan().equals(castOther.getShiyan()) ) )
 && ( (this.getZfx()==castOther.getZfx()) || ( this.getZfx()!=null && castOther.getZfx()!=null && this.getZfx().equals(castOther.getZfx()) ) )
 && ( (this.getBoke()==castOther.getBoke()) || ( this.getBoke()!=null && castOther.getBoke()!=null && this.getBoke().equals(castOther.getBoke()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getId() == null ? 0 : this.getId().hashCode() );
         result = 37 * result + ( getDayi() == null ? 0 : this.getDayi().hashCode() );
         result = 37 * result + ( getGonggao() == null ? 0 : this.getGonggao().hashCode() );
         result = 37 * result + ( getTaolun() == null ? 0 : this.getTaolun().hashCode() );
         result = 37 * result + ( getKaoshi() == null ? 0 : this.getKaoshi().hashCode() );
         result = 37 * result + ( getZuoye() == null ? 0 : this.getZuoye().hashCode() );
         result = 37 * result + ( getZiyuan() == null ? 0 : this.getZiyuan().hashCode() );
         result = 37 * result + ( getZxdayi() == null ? 0 : this.getZxdayi().hashCode() );
         result = 37 * result + ( getSmzuoye() == null ? 0 : this.getSmzuoye().hashCode() );
         result = 37 * result + ( getZice() == null ? 0 : this.getZice().hashCode() );
         result = 37 * result + ( getPingjia() == null ? 0 : this.getPingjia().hashCode() );
         result = 37 * result + ( getDaohang() == null ? 0 : this.getDaohang().hashCode() );
         result = 37 * result + ( getDaoxue() == null ? 0 : this.getDaoxue().hashCode() );
         result = 37 * result + ( getShiyan() == null ? 0 : this.getShiyan().hashCode() );
         result = 37 * result + ( getZfx() == null ? 0 : this.getZfx().hashCode() );
         result = 37 * result + ( getBoke() == null ? 0 : this.getBoke().hashCode() );
         return result;
   }   





}