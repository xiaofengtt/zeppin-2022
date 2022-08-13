/**
 * 
 */
package com.whaty.platform.training.setup;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.training.setup.database.Oracle8TrainingDatabaseSetup;
import com.whaty.platform.training.setup.database.Oracle9TrainingDatabaseSetup;

/**
 * @author chenjian
 *
 */
public class TrainingDatabaseFactory {
	
	public static TrainingDatabaseSetup creat(String trainingDatabaseType) throws PlatformException
	{
		if(trainingDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE9))
		{
			return new Oracle9TrainingDatabaseSetup();
		}
		if(trainingDatabaseType.equalsIgnoreCase(SupportDatabaseType.ORACLE8))
		{
			return new Oracle8TrainingDatabaseSetup();
		}
		else
		{
			throw new PlatformException("unSupport this database type!");
		}
	}

}
