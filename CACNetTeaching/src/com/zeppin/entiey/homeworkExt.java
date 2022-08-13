/**
 * 
 */
package com.zeppin.entiey;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class homeworkExt
{
    Homework homework;
    List<Accessory> lstAccessories = new ArrayList<Accessory>();// 作业表
    String isEnd;// 是否大作业
    List<AccessoryExt> lstAccessoriex = new ArrayList<AccessoryExt>();

    /**
     * @return the homework
     */
    public Homework getHomework()
    {
	return homework;
    }

    /**
     * @param homework
     *            the homework to set
     */
    public void setHomework(Homework homework)
    {
	this.homework = homework;
    }

    /**
     * @return the lstAccessories
     */
    public List<Accessory> getLstAccessories()
    {
	return lstAccessories;
    }

    /**
     * @param lstAccessories
     *            the lstAccessories to set
     */
    public void setLstAccessories(List<Accessory> lstAccessories)
    {
	this.lstAccessories = lstAccessories;
    }

    /**
     * @return the isEnd
     */
    public String getIsEnd()
    {
	return isEnd;
    }

    /**
     * @param isEnd
     *            the isEnd to set
     */
    public void setIsEnd(String isEnd)
    {
	this.isEnd = isEnd;
    }

    /**
     * @return
     */
    public List<AccessoryExt> getLstAccessoriex()
    {
	// TODO Auto-generated method stub
	return lstAccessoriex;
    }

    /**
     * @param lstAccessoriex
     *            the lstAccessoriex to set
     */
    public void setLstAccessoriex(List<AccessoryExt> lstAccessoriex)
    {
	this.lstAccessoriex = lstAccessoriex;
    }

}
