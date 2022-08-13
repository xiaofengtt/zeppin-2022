/**
 * 
 */
package com.whaty.platform.sso.setup;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 * 
 */
public abstract class SsoDatabaseSetup {
	public static String FAILEDTOCONNECT = "fail to connect";

	public static String USERCREATERROR = "user creat error";

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

	public abstract void creatDb(SsoSetupBean ssoSetupBean)
			throws PlatformException;

	public abstract void creatDBSystem(SsoSetupBean ssoSetupBean)
			throws PlatformException;

	public abstract void creatDBData(SsoSetupBean ssoSetupBean)
			throws PlatformException;

	public abstract boolean testDatabase() throws PlatformException;

}
