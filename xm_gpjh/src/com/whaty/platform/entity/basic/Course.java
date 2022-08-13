package com.whaty.platform.entity.basic;

import com.whaty.platform.Exception.PlatformException;
/**
 * �γ̶���
 *
 *@author chenjian
 */
public abstract class Course implements com.whaty.platform.Items
{
   protected String id = "";
   protected String name = "";
   protected float credit = 0;
   protected float course_time = 0;
   protected float masterScore=0;
   protected String scoreDes="";
   protected String major_id="";
   protected String major_name="";
   protected String exam_type = "";
   protected String course_type = "";
   protected String teaching_type = "";
   protected String course_status = "";
   protected String ref_book = "";
   protected String note = "";
   protected String standard_fee = "";
   protected String drift_fee = "";
   protected String text_book = "";
   protected String text_book_price = "";
   protected String redundance0 = "";
   protected String redundance1 = "";
   protected String redundance2 = "";
   protected String redundance3 = "";
   protected String redundance4 = "";
   protected String status = "";
   
   protected String ispaper = ""; //bjsy2 加入标识是否为论文
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
   Access method for the credit property.
   
   @return   the current value of the credit property
    */
   public float getCredit() 
   {
      return credit;
   }
   
   /**
   Access method for the credit property.
   
   @return   the current value of the credit property
    */
   public String getCreditString() 
   {
      return java.lang.Float.toString(credit);
   }
   
   /**
   Sets the value of the credit property.
   
   @param aCredit the new value of the credit property
    */
   public void setCredit(float aCredit) 
   {
      credit = aCredit;
   }
   
   /**
   Sets the value of the credit property.
   
   @param aCredit the new value of the credit property
    */
   public void setCredit(String aCredit) throws NumberFormatException
   {
	  try{
		  credit = java.lang.Float.parseFloat(aCredit);
	  }
	  catch(NumberFormatException e){
		  throw e;
	  }
   }
   
   /**
    * ���� course_time �Ļ�ȡ������
    * @return ���� course_time ��ֵ��
    */
   public float getCourse_time() {
       return course_time;
   }
   
   /**
    * ���� course_time �Ļ�ȡ������
    * @return ���� course_time ��ֵ��
    */
   public String getCourse_timeString() {
       return java.lang.Float.toString(course_time);
   }
   
   /**
    * ���� course_time �����÷�����
    * @param course_time ���� course_time ����ֵ��
    */
   public void setCourse_time(float course_time) {
       this.course_time = course_time;
   }
   
   /**
    * ���� course_time �����÷�����
    * @param course_time ���� course_time ����ֵ��
    */
   public void setCourse_time(String course_time) throws NumberFormatException{
       try{
 		  this.course_time = java.lang.Float.parseFloat(course_time);
 	  }
 	  catch(NumberFormatException e){
 		  throw e;
 	  }
   }
   
   /**
   Access method for the exam_type property.
   
   @return   the current value of the exam_type property
    */
   public String getExam_type() 
   {
      return exam_type;
   }
   
   /**
   Sets the value of the exam_type property.
   
   @param aExam_type the new value of the exam_type property
    */
   public void setExam_type(String aExam_type) 
   {
      exam_type = aExam_type;
   }
   
   /**
   Access method for the course_type property.
   
   @return   the current value of the course_type property
    */
   public String getCourse_type() 
   {
      return course_type;
   }
   
   /**
   Sets the value of the course_type property.
   
   @param aCourse_type the new value of the course_type property
    */
   public void setCourse_type(String aCourse_type) 
   {
      course_type = aCourse_type;
   }
   
   /**
   Access method for the teaching_type property.
   
   @return   the current value of the teaching_type property
    */
   public String getTeaching_type() 
   {
      return teaching_type;
   }
   
   /**
   Sets the value of the teaching_type property.
   
   @param aTeaching_type the new value of the teaching_type property
    */
   public void setTeaching_type(String aTeaching_type) 
   {
      teaching_type = aTeaching_type;
   }
   
   /**
   Access method for the course_status property.
   
   @return   the current value of the course_status property
    */
   public String getCourse_status() 
   {
      return course_status;
   }
   
   /**
   Sets the value of the course_status property.
   
   @param aCourse_status the new value of the course_status property
    */
   public void setCourse_status(String aCourse_status) 
   {
      course_status = aCourse_status;
   }
   
      
   /**
   Access method for the ref_book property.
   
   @return   the current value of the ref_book property
    */
   public String getRef_book() 
   {
      return ref_book;
   }
   
   /**
   Sets the value of the ref_book property.
   
   @param aRef_book the new value of the ref_book property
    */
   public void setRef_book(String aRef_book) 
   {
      ref_book = aRef_book;
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
    * ���� standard_fee �Ļ�ȡ������
    * @return ���� standard_fee ��ֵ��
    */
   public java.lang.String getStandard_fee() {
       return standard_fee;
   }
   
   /**
    * ���� standard_fee �����÷�����
    * @param standard_fee ���� standard_fee ����ֵ��
    */
   public void setStandard_fee(String standard_fee) {
       this.standard_fee = standard_fee;
   }
   
   /**
    * ���� drift_fee �Ļ�ȡ������
    * @return ���� drift_fee ��ֵ��
    */
   public java.lang.String getDrift_fee() {
       return drift_fee;
   }
   
   /**
    * ���� drift_fee �����÷�����
    * @param drift_fee ���� drift_fee ����ֵ��
    */
   public void setDrift_fee(String drift_fee) {
       this.drift_fee = drift_fee;
   }
   
   /**
   Access method for the text_book property.
   
   @return   the current value of the text_book property
    */
   public String getText_book() 
   {
      return text_book;
   }
   
   /**
   Sets the value of the text_book property.
   
   @param aText_book the new value of the text_book property
    */
   public void setText_book(String aText_book) 
   {
      text_book = aText_book;
   }
   
   /**
   Sets the value of the Text_book_price property.
   
   @param atext_book_price the new value of the Text_book_price property
    */
   public void setText_book_price(String atext_book_price) 
   {
	   text_book_price = atext_book_price;
   }
   
   /**
   Access method for the Text_book_price property.
   
   @return   the current value of the Text_book_price property
    */
   public String getText_book_price() 
   {
      return text_book_price;
   }
   
   /**
    * ���� redundance0 �Ļ�ȡ������
    * @return ���� redundance0 ��ֵ��
    */
   public java.lang.String getRedundance0() {
       return drift_fee;
   }
   
   /**
    * ���� redundance0 �����÷�����
    * @param redundance0 ���� redundance0 ����ֵ��
    */
   public void setRedundance0(String redundance0) {
       this.redundance0 = redundance0;
   }
   
   /**
    * ���� redundance1 �Ļ�ȡ������
    * @return ���� redundance1 ��ֵ��
    */
   public java.lang.String getRedundance1() {
       return redundance1;
   }
   
   /**
    * ���� redundance1 �����÷�����
    * @param redundance1 ���� redundance1 ����ֵ��
    */
   public void setRedundance1(String redundance1) {
       this.redundance1 = redundance1;
   }
   
   /**
    * ���� redundance2 �Ļ�ȡ������
    * @return ���� redundance2 ��ֵ��
    */
   public java.lang.String getRedundance2() {
       return redundance2;
   }
   
   /**
    * ���� redundance2 �����÷�����
    * @param redundance2 ���� redundance2 ����ֵ��
    */
   public void setRedundance2(String redundance2) {
       this.redundance2 = redundance2;
   }
   
   /**
    * ���� Redundance3 �Ļ�ȡ������
    * @return ���� Redundance3 ��ֵ��
    */
   public java.lang.String getRedundance3() {
       return redundance3;
   }
   
   /**
    * ���� redundance3 �����÷�����
    * @param redundance3 ���� redundance3 ����ֵ��
    */
   public void setRedundance3(String redundance3) {
       this.redundance3 = redundance3;
   }
   
   /**
    * ���� redundance4 �Ļ�ȡ������
    * @return ���� redundance4 ��ֵ��
    */
   public java.lang.String getRedundance4() {
       return redundance4;
   }
   
   /**
    * ���� redundance4 �����÷�����
    * @param redundance4 ���� redundance4 ����ֵ��
    */
   public void setRedundance4(String redundance4) {
       this.redundance4 = redundance4;
   }
   
   /**
 * @return
public abstract List getSemesters(); 
 */  
  
   /**
    * Ϊ�γ���ӿμ�
 * @param coursewares
 * @return ��ӿμ�����
public abstract int addCoursewares(List coursewares) ;
   
   public abstract List getCoursewares();
 */
   
   /**
    * ɾ��γ�����Ŀμ�
    * @param coursewares
    
   public abstract void removeCoursewares(List coursewares);
*/

    /**
     * �жϿγ̱���Ƿ����
     * @return 0��ʾ�����ڣ�����0��ʾ����
     */
    public abstract int isIdExist();

    /**
     * �жϿγ��Ƿ����ڿ��ογ�
     * @return 0��ʾ�����ڣ�����0��ʾ�ÿγ��Ѿ�����
     */
    public abstract int isOpenCourse();

	public float getMasterScore() {
		return masterScore;
	}

	public void setMasterScore(float masterScore) {
		this.masterScore = masterScore;
	}

	public String getScoreDes() {
		return scoreDes;
	}

	public void setScoreDes(String scoreDes) {
		this.scoreDes = scoreDes;
	}

	public String getMajor_id() {
		return major_id;
	}

	public void setMajor_id(String major_id) {
		this.major_id = major_id;
	}

	public String getMajor_name() {
		return major_name;
	}

	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public abstract int addTeachbookToCourse(String[] teachbookIdSet) throws PlatformException;

	public abstract int removeTeachBookFromCourse(String[] teachbookIdSet) throws PlatformException;

	public String getIspaper() {
		return ispaper;
	}

	public void setIspaper(String ispaper) {
		this.ispaper = ispaper;
	}
}
