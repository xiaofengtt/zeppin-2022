/*
 * UserFactory.java
 *
 * Created on 2004��11��25��, ����12:03
 */

package com.whaty.platform.entity;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.fee.ChargeLevelManage;
import com.whaty.platform.entity.fee.FeeManage;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.entity.user.SiteTeacherPriv;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.user.TeacherPriv;
import com.whaty.platform.info.InfoManage;
import com.whaty.platform.info.user.InfoManagerPriv;
import com.whaty.platform.sms.SmsManage;
import com.whaty.platform.sms.SmsManagerPriv;
import com.whaty.platform.sso.SsoManagerPriv;

/**
 * ƽ̨��ڵĳ��󹤳�
 * 
 * @author chenjian
 */
public abstract class PlatformFactory {

	private static String className = "com.whaty.platform.database.oracle.standard.entity.OraclePlatformFactory";

	private static Object initLock = new Object();

	private static PlatformFactory factory;

	/** Creates a new instance of UserFactory */
	public PlatformFactory() {
	}

	/**
	 * ����PlatformFactoryʵ��
	 * 
	 * @return PlatformFactoryʵ��
	 */
	public static PlatformFactory getInstance() {
		if (factory == null) {
			synchronized (initLock) {
				if (factory == null) {
					String classNameProp = GlobalProperties
							.getUserFactoryClass("PlatformFactory.className");
					if (classNameProp != null) {
						className = classNameProp;
					}
					try {
						// Load the class and create an instance.
						Class c = Class.forName(className);
						factory = (PlatformFactory) c.newInstance();
					} catch (Exception e) {
						System.err
								.println("Failed to load PlatformFactory class "
										+ className
										+ ".system function normally.");
						
						return null;
					}
				}
			}
		}
		return factory;
	}

	/**
	 * ��������ݹ������
	 * 
	 * @param basicmanagepriv
	 * @return BasicEntityManage����
	 */
	public abstract BasicUserManage creatBasicUserManage(
			ManagerPriv amanagerpriv);

	public abstract BasicSiteUserManage creatBasicSiteUserManage(
			SiteManagerPriv smanagerpriv);

	public abstract BasicSiteEntityManage creatBasicSiteEntityManage(
			SiteManagerPriv smanagerpriv);

	public abstract BasicSiteActivityManage creatBasicSiteActivityManage(
			SiteManagerPriv smanagerpriv);

	public abstract BasicSiteScoreManage creatBasicSiteScoreManage(
			SiteManagerPriv smanagerpriv);

	public abstract BasicSiteEduManage creatBasicSiteEduManage(
			SiteManagerPriv smanagerpriv);
	
	public abstract EntityTestPriv getEntityTestPriv(ManagerPriv amanagerpriv);
	
	public abstract EntityTestPriv getEntityTestPriv(SiteManagerPriv amanagerpriv);
	
	public abstract EntityTestManage getEntityTestManage(EntityTestPriv testPriv);

	public abstract BasicEduManage creatBasicEduManage(ManagerPriv amanagerpriv);

	public abstract StudentOperationManage creatStudentOperationManage(
			StudentPriv studentpriv);

	public abstract StudentOperationManage creatStudentOperationManage();

	public abstract TeacherOperationManage creatTeacherOperationManage(
			TeacherPriv teacherpriv);

	public abstract SiteManagerOperationManage creatSiteManagerOperationManage(
			SiteManagerPriv sitemanagerpriv);

	public abstract PlatformManage createPlatformManage();

	public abstract ManagerPriv getManagerPriv(java.lang.String id);
	
	public abstract ManagerPriv getManagerPriv();

	public abstract StudentPriv getStudentPriv(java.lang.String id);

	public abstract TeacherPriv getTeacherPriv(java.lang.String id);

	public abstract SiteTeacherPriv getSiteTeacherPriv(java.lang.String id)
			throws Exception;

	public abstract SiteManagerPriv getSiteManagerPriv(java.lang.String id);

	public abstract SsoManagerPriv getSsoManagerPriv(java.lang.String id);

	public abstract BasicEntityManage creatBasicEntityManage(
			ManagerPriv managerPriv)throws PlatformException;

	public abstract BasicRecruitManage creatBasicRecruitManage(
			ManagerPriv managerPriv);

	public abstract BasicRightManage creatBasicRightManage();

	public abstract InfoManage creatInfoManage();

	public abstract BasicActivityManage creatBasicActivityManage(
			ManagerPriv managerpriv);

	public abstract BasicScoreManage creatBasicScoreManage(
			ManagerPriv managerpriv);
	
	public abstract BasicScoreManage creatBasicScoreManage();
	
	public abstract BasicActivityManage creatBasicActivityManage();

	public abstract BasicSiteRecruitManage creatBasicSiteRecruitManage(
			SiteManagerPriv smanagerPriv);

	public abstract SiteTeacherOperationManage creatSiteTeacherOperationManage(
			SiteTeacherPriv steacherPriv);

	public abstract ChargeLevelManage createChargeLevelManage(
			ManagerPriv managerpriv);

	public abstract ChargeLevelManage createChargeLevelManage(
			SiteManagerPriv managerpriv);

	public abstract ChargeLevelManage createStudentChargeLevelManage(
			StudentPriv studentpriv);

	//public abstract FeeManage createFeeManage(ManagerPriv managerpriv);

	//public abstract FeeManage createFeeManage(SiteManagerPriv managerpriv);

	public abstract FeeManage createStudentFeeManage(StudentPriv studentpriv);

	public abstract FeeManage createStudentFeeManage();
	
	public abstract SmsManage createSmsManage(SmsManagerPriv smsPriv);
	
	public abstract BasicMailInfoManage creatBasicMailManage(
			ManagerPriv managerPriv);
	
	public abstract BasicGraduateDesignManage creatBasicGraduateDesignManage(ManagerPriv managerPriv);
	
	public abstract BasicSiteGraduateDesignManage creatBasicSiteGraduateDesignManage(SiteManagerPriv siteManagerPriv);
	
	public abstract BasicEntityManage creatBasicEntityManage(
			InfoManagerPriv infoManagerPriv);
	
	public abstract BasicUserManage creatBasicUserManage(
			InfoManagerPriv infoManagerPriv);
	public abstract BasicRecruitManage creatBasicRecruitManage(
			InfoManagerPriv infoManagerPriv);
	
	public abstract ChargeLevelManage getNormalChargeLevelManage();
    
}