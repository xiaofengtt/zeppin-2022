/**
 * 
 */
package com.zeppin.util.cryptogram;

/**
 * @author suijing
 * 
 */
public class generatePwdServ
{
	private IGeneratePwd igp=new generatePwd();
   
	public IGeneratePwd getIgp()
	{
		return igp;
	}

	public void setIgp(IGeneratePwd igp)
	{
		this.igp = igp;
	}
	
	public String getpwd() throws InterruptedException
	{
		
		return igp.getPwd();
	}
	
	public String getOraSting(String pwd) throws Exception
	{
		return igp.getOralString(pwd);
	}
}
