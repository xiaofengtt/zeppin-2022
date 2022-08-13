package com.whaty.platform.entity.basic;


/**
 * �꼶����
 * @author chenjian
 */
public abstract class Grade implements com.whaty.platform.Items
{
   private String id = "";
   private String name = "";
   private String code = ""; //新加入，与bjsy2统一
   private String begin_date = "";   
   
   
   public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

/**
   Access method for the id property.
   
   @return   the current value of the id property
    */
   public String getId() 
   {
      return id;
   }
   
   /**
   Sets the value of the id property.
   
   @param aId the new value of the id property
    */
   public void setId(String aId) 
   {
      id = aId;
   }
   
   /**
   Access method for the name property.
   
   @return   the current value of the name property
    */
   public String getName() 
   {
      return name;
   }
   
   /**
   Sets the value of the name property.
   
   @param aName the new value of the name property
    */
   public void setName(String aName) 
   {
      name = aName;
   }
   
   /**
    * ���� begin_date �Ļ�ȡ������
    * @return ���� begin_date ��ֵ��
    */
   public String getBegin_date() {
       return begin_date;
   }
   
   /**
    * ���� begin_date �����÷�����
    * @param begin_date ���� begin_date ����ֵ��
    */
   public void setBegin_date(String aBegin_date) {
       begin_date = aBegin_date;
   }

    /**
     * ���꼶�����Ƿ���ѧ��
     * @return 0Ϊû�У�����0����
     */
    public abstract int isHasStudents();
 
}
