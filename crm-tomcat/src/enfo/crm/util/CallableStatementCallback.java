/*
 * �������� 2007-5-14
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.util;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 * @author caiyuan
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public interface CallableStatementCallback {
	
	Object doInCallableStatement(CallableStatement cs) throws SQLException;
}
