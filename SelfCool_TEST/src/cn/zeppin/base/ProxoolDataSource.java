/**
 * 
 */
package cn.zeppin.base;

/**
 * @author Clark
 *
 */
public class ProxoolDataSource extends org.logicalcobwebs.proxool.ProxoolDataSource {

	public void setHouseKeepingSleepTime(long houseKeepingSleepTime) {
		super.setHouseKeepingSleepTime((int) houseKeepingSleepTime);
	}

	public void setMaximumConnectionLifetime(long maximumConnectionLifetime) {
		super.setMaximumConnectionLifetime((int) maximumConnectionLifetime);
	}

	public void setOverloadWithoutRefusalLifetime(long overloadWithoutRefusalLifetime) {
		super.setOverloadWithoutRefusalLifetime((int) overloadWithoutRefusalLifetime);
	}

	public void setRecentlyStartedThreshold(long recentlyStartedThreshold) {
		super.setRecentlyStartedThreshold((int) recentlyStartedThreshold);
	}

}
