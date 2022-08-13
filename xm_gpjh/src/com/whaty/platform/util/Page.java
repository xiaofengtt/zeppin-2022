/*
 * Page.java
 *
 * Created on 2004��11��19��, ����1:28
 */

package com.whaty.platform.util;

/**
 * �������Ʒ�ҳ�������
 * @author Chen Jian
 */
public class Page {
    
    private int pageSize = 10;
    
    private int pageInt = 1;
    
    private int totalnum = 0;
    
    /** Creates a new instance of Page */
    public Page() {
    }
    
    /**
     * ���� pageSize �Ļ�ȡ������
     * @return ���� pageSize ��ֵ��
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * ���� pageSize �����÷�����
     * @param pageSize ���� pageSize ����ֵ��
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * ����ÿҳ��ʾ��Ŀ��
     * @param pageSize �ַ�����ʽ��ÿҳ��ʾ��Ŀ��
     */    
    public void setPageSize(String pageSize) {
        int tempSize;
        if(pageSize == null || pageSize.length() == 0 || pageSize.equals("null"))
            tempSize=10;
        else
            tempSize= Integer.parseInt(pageSize);
        setPageSize(tempSize);
    }
    
    /**
     * ���� pageInt �Ļ�ȡ������
     * @return ���� pageInt ��ֵ��
     */
    public int getPageInt() {
        return pageInt;
    }
    
    /**
     * ���� pageInt �����÷�����
     * @param pageInt ���� pageInt ����ֵ��
     */
    public void setPageInt(int pageInt) {
        this.pageInt = pageInt;
        if (totalnum <= (this.pageInt - 1) * this.pageSize)
	{
		this.pageInt =this.pageInt - 1;
		if(this.pageInt < 1)
		{
			this.pageInt = 1;
		}
	}
        if(this.pageInt > getMaxPage())
		this.pageInt = getMaxPage();
    }
    /**
     * ���õ�ǰҳ��
     * @param pageInt �ַ�����ʽ�ĵ�ǰҳ��
     */    
     public void setPageInt(String pageInt) {
        int tempInt=1;
        if(pageInt == null || pageInt.length() == 0 || pageInt.equals("null"))
            tempInt=1;
        else
            tempInt = Integer.parseInt(pageInt);
            setPageInt(tempInt);
    }
    
     /**
      * ���� totalnum �Ļ�ȡ������
      * @return ���� totalnum ��ֵ��
      */
     public int getTotalnum() {
         return totalnum;
     }
     
     /**
      * ���� totalnum �����÷�����
      * @param totalnum ���� totalnum ����ֵ��
      */
     public void setTotalnum(int totalnum) {
         this.totalnum = totalnum;
     }
     
     /**
      * �õ���ǰҳ��һҳ��ҳ��
      * @return ��һҳҳ��
      */     
     public int getPageNext() {
         int i=pageInt+1;
         if(i>getMaxPage())
             i=getMaxPage();
         return i;
     }
     
     /**
      * �õ���ǰҳ��һҳ��ҳ�룬�����ǰҳΪ1������1
      * @return ��һҳ��ҳ��
      */     
     public int getPagePre() {
         int i=pageInt-1;
         if(i<1)
             i=1;
         return i;
     }
     
     /**
      * �õ����ҳ��
      * @return ���ҳ����
      */     
     public int getMaxPage() {
    	 if(((totalnum + pageSize - 1) / pageSize)==0)
    		 return 1;
    	 else
    		 return((totalnum + pageSize - 1) / pageSize);
     }
     
}
