/*
 * �������� 2007-5-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author caiyuan
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public interface ConnectionCallback {
	
	public Object doInConnection(Connection conn)	throws SQLException;

}
