/**
 * 
 */
package com.whaty.platform.entity.setup;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.config.DatabaseType;

/**
 * @author chenjian
 *
 */
public class SupportDatabaseType extends DatabaseType{

	public static List  types()
	{
		ArrayList list=new ArrayList();
		list.add(DatabaseType.ORACLE9);
		list.add(DatabaseType.ORACLE8);
		return list;
	}
	
}
