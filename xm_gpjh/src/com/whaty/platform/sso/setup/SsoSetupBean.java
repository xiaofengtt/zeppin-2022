/**
 * 
 */
package com.whaty.platform.sso.setup;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.PlatformConfig;
import com.whaty.platform.config.SubSystemSetup;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.setup.PlatformSetupBean;
import com.whaty.platform.sso.config.SsoConfig;

/**
 * @author chenjian
 * 
 */
public class SsoSetupBean extends PlatformSetupBean {

	private SsoDatabaseSetup ssoDatabaseSetup;

	private SsoConfig ssoConfig;

	public SsoSetupBean() throws PlatformException {
		super();

	}

	public void init(ServletContext servletContext) throws PlatformException {
		PlatformConfig config = new PlatformConfig(servletContext
				.getRealPath("/")
				+ "WEB-INF" + File.separator + "config" + File.separator);
		config.getConfig();
		this.setConfig(config);
		SsoConfig ssoConfig = new SsoConfig(this.getConfig());
		ssoConfig.getConfig();
		this.setSsoConfig(ssoConfig);

	}

	public SsoConfig getSsoConfig() {
		return ssoConfig;
	}

	public void setSsoConfig(SsoConfig ssoConfig) {
		this.ssoConfig = ssoConfig;
	}

	public SsoDatabaseSetup getSsoDatabaseSetup() {
		return ssoDatabaseSetup;
	}

	public void setSsoDatabaseSetup(SsoDatabaseSetup ssoDatabaseSetup) {
		this.ssoDatabaseSetup = ssoDatabaseSetup;
	}

	public void creatDatabaseSetup(String dataBaseType)
			throws PlatformException {
		this.setSsoDatabaseSetup(SsoDatabaseFactory.creat(dataBaseType));
	}

	public void creatDatabase(Map params) throws PlatformException {
		this.getSsoDatabaseSetup().setParams(params);
		this.getSsoDatabaseSetup().creatDb(this);
	}
	
	public void creatDatabaseSystem(Map params) throws PlatformException {
		this.getSsoDatabaseSetup().setParams(params);
		this.getSsoDatabaseSetup().creatDBSystem(this);
	}
	
	public void creatDatabaseData(Map params) throws PlatformException {
		this.getSsoDatabaseSetup().setParams(params);
		this.getSsoDatabaseSetup().creatDBData(this);
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
		this.getSsoConfig().setConfig();
		this.updatePlatformSetupConfig(true);
	}

	public boolean getIsInited() {
		if (this.getSsoConfig() == null || this.getConfig() == null)
			return false;
		else
			return true;
	}
	
	public void updatePlatformSetupConfig(boolean setuped) throws PlatformException {
		PlatformConfig platformConfig = this.getConfig();
		List subSystemList = platformConfig.getSubSystemSetup();
		for(int i=0; i<subSystemList.size(); i++) {
			SubSystemSetup subSystem = (SubSystemSetup)subSystemList.get(i);
			if(subSystem.getName().equalsIgnoreCase(SubSystemType.SSO)) {
				subSystem.setSetuped(setuped);
			}
		}
		
		platformConfig.setSubSystemSetup(subSystemList);
		platformConfig.setConfig();
	}

}
