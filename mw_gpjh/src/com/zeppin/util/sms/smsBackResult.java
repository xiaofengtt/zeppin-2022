/**
 * 
 */
package com.zeppin.util.sms;

/**
 * @author suijing
 * 
 *
 */
public enum smsBackResult
{
 
  success(0),
  errChecking(1001),
  moneyShort(1002),
  errPara(1003),
  errOther(1004);
  
  public int valueString;
  smsBackResult(int value)
  {
  	// TODO 自动生成的构造函数存根
	  this.valueString=value;
  }	  
}
