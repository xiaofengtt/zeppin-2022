/**
 * 
 */
package com.zeppin.entiey;

/**
 * @author sj
 * 
 */
public class AccessoryExt
{
    String userName;
    Accessory accessory;
    String homworkState;

    /**
     * @param name
     */
    public void setUserName(String name)
    {
	// TODO Auto-generated method stub
    	this.userName = name;
    }

    /**
     * @param accessory
     */
    public void setAccessory(Accessory accessory)
    {
	// TODO Auto-generated method stub
    	this.accessory = accessory;
    }

    /**
     * @param string
     */
    public void setHomworkState(String string)
    {
	// TODO Auto-generated method stub
    	this.homworkState = string;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
	return userName;
    }

    /**
     * @return the accessory
     */
    public Accessory getAccessory()
    {
	return accessory;
    }

    /**
     * @return the homworkState
     */
    public String getHomworkState()
    {
	return homworkState;
    }

}
