/*
 * �������� 2004-2-15
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.web;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class UserInfo extends HttpServlet implements HttpSessionListener
{
	public static ArrayList userList = new ArrayList();

	public void sessionCreated(HttpSessionEvent se)
	{
		userList.add(se.getSession());
		System.out.println("session created ok!");
	}
	
	public void sessionDestroyed(HttpSessionEvent se)
	{
		userList.remove(se.getSession());
		System.out.println("session is lost!!");
	}

	public static void verifyCount()
	{
		for (int i = userList.size() - 1; i >= 0; i--)
			if (userList.get(i) == null)
				userList.remove(i);
	}

}