/**
 * 
 */
package com.whaty.platform.sso.setup;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.sso.setup.database.Oracle8SsoDatabaseSetup;
import com.whaty.platform.sso.setup.database.Oracle9SsoDatabaseSetup;

/**
 * @author chenjian
 *
 */
public class SsoDatabaseFactory {
	
	public static SsoDatabaseSetup creat(String ssoDatabaseType) throws PlatformException
	{
		if(ssoDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE9))
		{
			return new Oracle9SsoDatabaseSetup();
		}
		if(ssoDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE8))
		{
			return new Oracle8SsoDatabaseSetup();
		}
		else
		{
			throw new PlatformException("unSupport this database type!");
		}
	}

}
