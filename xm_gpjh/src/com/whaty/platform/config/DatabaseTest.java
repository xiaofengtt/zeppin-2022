/**
 * 
 */
package com.whaty.platform.config;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.util.log.Log;

/**����ϵͳ�Ƿ�֧�ָ���ݿ⣬��Ҫ�Ǽ��JDBC���Ƿ�װ
 * @author chenjian
 *
 */
public class DatabaseTest {
	
	public static boolean isSupported(String databaseType) throws  PlatformException
	{
		if(databaseType.equals(DatabaseType.ORACLE9))
		{
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				
			} catch (ClassNotFoundException e) {
				Log.setInfo("no jdbc driver of "+databaseType);
				return false;
			}
			return true;
		}
		else if(databaseType.equals(DatabaseType.ORACLE8))
		{
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				
			} catch (ClassNotFoundException e) {
				Log.setInfo("no jdbc driver of "+databaseType);
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	 
	}

}
