/**
 * 
 */
package com.whaty.platform.entity.setup;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class EntityDatabaseSetup {
	public static String FAILEDTOCONNECT="fail to connect"; 
	
	public static String USERCREATERROR="user creat error";
	
	private String DatabaseType;
	
	private Map params;
	
	public String getDatabaseType() {
		return DatabaseType;
	}

	public void setDatabaseType(String databaseType) {
		DatabaseType = databaseType;
	}


	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public abstract void creatDb(EntitySetupBean entitySetupBean) throws PlatformException;
	
	public abstract void creatDBSystem(EntitySetupBean entitySetupBean)	throws PlatformException;
	
	public abstract void creatDBData(EntitySetupBean entitySetupBean) throws PlatformException; 
	
	public abstract boolean testDatabase() throws PlatformException;
	
}
