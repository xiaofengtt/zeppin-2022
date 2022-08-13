/**
 * 
 */
package com.whaty.platform.training.setup;

import java.util.Map;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author chenjian
 *
 */
public abstract class TrainingDatabaseSetup {

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

	public abstract void creatDb(TrainingSetupBean trainingSetupBean) throws PlatformException;
	
	public abstract boolean testDatabase() throws PlatformException;
	
}
