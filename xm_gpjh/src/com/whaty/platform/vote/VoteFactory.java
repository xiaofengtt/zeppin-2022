package com.whaty.platform.vote;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.vote.user.VoteManagerPriv;

import com.whaty.util.log.Log;

public abstract class VoteFactory {
	private static String className = "com.whaty.platform.database.oracle.standard.vote.OracleVoteFactory";

	private static Object initLock = new Object();

	private static VoteFactory factory;

	/** Creates a new instance of UserFactory */
	public VoteFactory() {
	}

	/**
	 * ����PlatformFactoryʵ��
	 * 
	 * @return PlatformFactoryʵ��
	 */
	public static VoteFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("VoteFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						Log.setDebug("new instance of factory");
						factory = (VoteFactory) c.newInstance();
					} catch (Exception e) {
						System.err.println("Failed to load VoteFactory class "
								+ className + ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	public abstract VoteManage creatVoteManage(VoteManagerPriv amanagerpriv);

	public abstract VoteManagerPriv getVoteManagerPriv(java.lang.String id);

	public abstract VoteNormalManage creatVoteNormalManage();
	
	/**
	 * 根据平台权限构建投票操作权限
	 */
	public abstract VoteManagerPriv getVoteManagerPriv(ManagerPriv priv);
}
