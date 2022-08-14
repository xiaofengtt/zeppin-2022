/*
 * 创建日期 2010-3-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.web;

/**
 * @author taochen
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SmtpAuth extends   javax.mail.Authenticator{
	private String user,password; 

	public void setUserinfo(String getuser,String getpassword){ 
	  user = getuser; 
	  password = getpassword; 
	} 
	protected javax.mail.PasswordAuthentication getPasswordAuthentication(){ 
		return new javax.mail.PasswordAuthentication(user,password); 
	} 
}
