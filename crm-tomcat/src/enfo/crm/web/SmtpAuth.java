/*
 * �������� 2010-3-8
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.web;

/**
 * @author taochen
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
