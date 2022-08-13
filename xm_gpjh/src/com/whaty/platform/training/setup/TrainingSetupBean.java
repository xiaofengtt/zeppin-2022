/**
 * 
 */
package com.whaty.platform.training.setup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.setup.PlatformSetupBean;
import com.whaty.platform.training.config.TrainingConfig;

/**
 * @author chenjian
 *
 */
public class TrainingSetupBean extends PlatformSetupBean {

	private TrainingDatabaseSetup TrainingDatabaseSetup;
	
	
	private TrainingConfig TrainingConfig;
	
	
	
	public TrainingSetupBean() throws PlatformException {
		super();
		
	}
	
	 public void init(ServletContext servletContext) throws PlatformException 
    {
    	PlatformConfig config=new PlatformConfig(servletContext.getRealPath("/")
    			+"WEB-INF"+File.separator+"config"+File.separator);
    	config.getConfig();
    	this.setConfig(config);
    	this.setTrainingConfig(new TrainingConfig(this.getConfig()));
    	
    }
	 
	 
	public TrainingConfig getTrainingConfig() {
		return TrainingConfig;
	}

	public void setTrainingConfig(TrainingConfig TrainingConfig) {
		this.TrainingConfig = TrainingConfig;
	}

	public TrainingDatabaseSetup getTrainingDatabaseSetup() {
		return TrainingDatabaseSetup;
	}

	public void setTrainingDatabaseSetup(TrainingDatabaseSetup TrainingDatabaseSetup) {
		this.TrainingDatabaseSetup = TrainingDatabaseSetup;
	}

	public void creatDatabaseSetup(String dataBaseType) throws PlatformException
	{
		this.setTrainingDatabaseSetup(TrainingDatabaseFactory.creat(dataBaseType));
	}
	
	public void creatDatabase(Map params) throws PlatformException
	{
		this.getTrainingDatabaseSetup().setParams(params);
		this.getTrainingDatabaseSetup().creatDb(this);
	}
	
	public Map getSupportDatabase() throws PlatformException
	{
		Map databaseTypeMap=new HashMap();
		List supportDatabase=SupportDatabaseType.types();
		for(int i=0;i<supportDatabase.size();i++)
		{
			databaseTypeMap.put((String)supportDatabase.get(i),SupportDatabaseType.typeShow((String)supportDatabase.get(i)));
		}
		return databaseTypeMap;
	}
	
	public void writeConfigFile() throws PlatformException
    {
    	this.getTrainingConfig().setConfig();
    }
	
	public boolean getIsInited() {
		if(this.getTrainingConfig()==null || this.getConfig()==null)
			return false;
		else
			return true;
	}
}
