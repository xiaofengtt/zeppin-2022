/*
 * Page.java
 *
 * Created on 2004年11月19日, 下午1:28
 */

package com.whaty.platform.util;

/**
 * 用来控制翻页处理的类
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
     * 属性 pageSize 的获取方法。
     * @return 属性 pageSize 的值。
     */
    public int getPageSize() {
        return pageSize;
    }
    
    /**
     * 属性 pageSize 的设置方法。
     * @param pageSize 属性 pageSize 的新值。
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    /**
     * 设置每页显示条目数
     * @param pageSize 字符串形式的每页显示条目数
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
     * 属性 pageInt 的获取方法。
     * @return 属性 pageInt 的值。
     */
    public int getPageInt() {
        return pageInt;
    }
    
    /**
     * 属性 pageInt 的设置方法。
     * @param pageInt 属性 pageInt 的新值。
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
     * 设置当前页码
     * @param pageInt 字符串形式的当前页码
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
      * 属性 totalnum 的获取方法。
      * @return 属性 totalnum 的值。
      */
     public int getTotalnum() {
         return totalnum;
     }
     
     /**
      * 属性 totalnum 的设置方法。
      * @param totalnum 属性 totalnum 的新值。
      */
     public void setTotalnum(int totalnum) {
         this.totalnum = totalnum;
     }
     
     /**
      * 得到当前页下一页的页码
      * @return 下一页页码
      */     
     public int getPageNext() {
         int i=pageInt+1;
         if(i>getMaxPage())
             i=getMaxPage();
         return i;
     }
     
     /**
      * 得到当前页上一页的页码，如果当前页为1，返回1
      * @return 上一页的页码
      */     
     public int getPagePre() {
         int i=pageInt-1;
         if(i<1)
             i=1;
         return i;
     }
     
     /**
      * 得到最大页码
      * @return 最大页码数
      */     
     public int getMaxPage() {
    	 if(((totalnum + pageSize - 1) / pageSize)==0)
    		 return 1;
    	 else
    		 return((totalnum + pageSize - 1) / pageSize);
     }
     
}
