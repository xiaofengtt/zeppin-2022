/**
 * 
 */
package com.whaty.platform.config;

import java.util.HashMap;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**该类用来描述本系统使用数据库安装时候的基本参数
 * @author chenjian
 *
 */
public class DatabaseDefaultParams {


	public static Map getDbDefaultParams(String databaseType) throws  PlatformException
	{
		if(databaseType.equals(DatabaseType.ORACLE9))
		{
			Map defaultParams=new HashMap();
			defaultParams.put("systemId", "system");
			defaultParams.put("dbUserId","whaty");
			defaultParams.put("dbConnStr","jdbc:oracle:thin:@localhost:1521:orcl");
			defaultParams.put("userTableSpace","users");
			defaultParams.put("indexTableSpace", "users");
			defaultParams.put("tempTableSpace", "temp");
			return defaultParams;
		}
		else if(databaseType.equals(DatabaseType.ORACLE8))
		{
			Map defaultParams=new HashMap();
			defaultParams.put("systemId", "system");
			defaultParams.put("systemPwd", "manager");
			defaultParams.put("whatyDbUserId","whaty");
			defaultParams.put("whatyDbUserPwd","whaty");
			defaultParams.put("connStr","jdbc:oracle:thin:@localhost:1521:orcl");
			defaultParams.put("userTableSpace","users");
			defaultParams.put("tempTableSpace", "temp");
			return defaultParams;
		}
		else if(databaseType.equals(DatabaseType.MYSQL5))
		{
			Map defaultParams=new HashMap();
			return defaultParams;
		}
		else
		{
			return new HashMap();
		}
	}
	
}
