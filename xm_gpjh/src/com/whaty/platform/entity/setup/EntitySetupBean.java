/**
 * 
 */
package com.whaty.platform.entity.setup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.config.SubSystemSetup;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.entity.config.EntityConfig;
import com.whaty.platform.setup.PlatformSetupBean;

/**
 * @author chenjian
 * 
 */
public class EntitySetupBean extends PlatformSetupBean {
	private EntityDatabaseSetup EntityDatabaseSetup;

	private EntityConfig EntityConfig;

	public EntitySetupBean() throws PlatformException {
		super();

	}

	public void init(ServletContext servletContext) throws PlatformException {
		PlatformConfig config = new PlatformConfig(servletContext
				.getRealPath("/")
				+ "WEB-INF" + File.separator + "config" + File.separator);
		config.getConfig();
		this.setConfig(config);
		EntityConfig entityConfig = new EntityConfig(this.getConfig());
		entityConfig.getConfig();
		this.setEntityConfig(entityConfig);

	}

	public EntityConfig getEntityConfig() {
		return EntityConfig;
	}

	public void setEntityConfig(EntityConfig EntityConfig) {
		this.EntityConfig = EntityConfig;
	}

	public EntityDatabaseSetup getEntityDatabaseSetup() {
		return EntityDatabaseSetup;
	}

	public void setEntityDatabaseSetup(EntityDatabaseSetup EntityDatabaseSetup) {
		this.EntityDatabaseSetup = EntityDatabaseSetup;
	}

	public void creatDatabaseSetup(String dataBaseType)
			throws PlatformException {
		this.setEntityDatabaseSetup(EntityDatabaseFactory.creat(dataBaseType));
	}

	public void creatDatabase(Map params) throws PlatformException {
		this.getEntityDatabaseSetup().setParams(params);
		this.getEntityDatabaseSetup().creatDb(this);
	}
	
	public void creatDatabaseSystem(Map params) throws PlatformException {
		this.getEntityDatabaseSetup().setParams(params);
		this.getEntityDatabaseSetup().creatDBSystem(this);
	}
	
	public void creatDatabaseData(Map params) throws PlatformException {
		this.getEntityDatabaseSetup().setParams(params);
		this.getEntityDatabaseSetup().creatDBData(this);
	}

	public Map getSupportDatabase() throws PlatformException {
		Map databaseTypeMap = new HashMap();
		List supportDatabase = SupportDatabaseType.types();
		for (int i = 0; i < supportDatabase.size(); i++) {
			databaseTypeMap.put((String) supportDatabase.get(i),
					SupportDatabaseType.typeShow((String) supportDatabase
							.get(i)));
		}
		return databaseTypeMap;
	}

	public void writeConfigFile() throws PlatformException {
		this.getEntityConfig().setConfig();
		this.updatePlatformSetupConfig(true);
	}

	public boolean getIsInited() {
		if (this.getEntityConfig() == null || this.getConfig() == null)
			return false;
		else
			return true;
	}
	
	public void updatePlatformSetupConfig(boolean setuped) throws PlatformException {
		PlatformConfig platformConfig = this.getConfig();
		List subSystemList = platformConfig.getSubSystemSetup();
		for(int i=0; i<subSystemList.size(); i++) {
			SubSystemSetup subSystem = (SubSystemSetup)subSystemList.get(i);
			if(subSystem.getName().equalsIgnoreCase(SubSystemType.ENTITY)) {
				subSystem.setSetuped(setuped);
			}
		}
		
		platformConfig.setSubSystemSetup(subSystemList);
		platformConfig.setConfig();
	}
}
