/**
 * 
 */
package com.whaty.platform.entity.setup;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.setup.database.Oracle8EntityDatabaseSetup;
import com.whaty.platform.entity.setup.database.Oracle9EntityDatabaseSetup;


/**
 * @author chenjian
 *
 */
public class EntityDatabaseFactory {
	
	public static EntityDatabaseSetup creat(String entityDatabaseType) throws PlatformException
	{
		if(entityDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE9))
		{
			return new Oracle9EntityDatabaseSetup();
		}
		if(entityDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE8))
		{
			return new Oracle8EntityDatabaseSetup();
		}
		else
		{
			throw new PlatformException("unSupport this database type!");
		}
	}

}
