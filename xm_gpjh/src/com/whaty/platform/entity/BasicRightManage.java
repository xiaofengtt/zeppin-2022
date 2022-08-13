package com.whaty.platform.entity;

import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.right.RightGroup;
import com.whaty.platform.entity.user.Manager;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.entity.user.SiteManager;
import com.whaty.platform.util.Page;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;

public abstract class BasicRightManage {

	public abstract int addOffice(String name) throws PlatformException;

	public abstract int updateOffice(String id, String name)
			throws PlatformException;

	public abstract int deleteOffice(String id) throws PlatformException;

	public abstract int addRightModel(String office_id, String model_id,
			String name, String status) throws PlatformException;

	public abstract int updateRightModel() throws PlatformException;

	public abstract int deleteRightModel(String id) throws PlatformException;

	public abstract int addRight(String model_id, String right_id, String name,
			String status) throws PlatformException;

	public abstract int deleteRight(String right_id, String model_id)
			throws PlatformException;

	public abstract int addRightGroup(String name) throws PlatformException;

	public abstract int updateRightGroup(String id, String name)
			throws PlatformException;

	public abstract int deleteRightGroup(String id) throws PlatformException;

	public abstract int deleteRightAdmin(String login_id)
			throws PlatformException;

	public abstract int deleteRightSiteAdmin(String login_id)
			throws PlatformException;

	public abstract List getOffices() throws PlatformException;

	public abstract List getManagerOffices() throws PlatformException;

	public abstract List getSiteOffices() throws PlatformException;

	public abstract List getOfficeRightModel(String office_id)
			throws PlatformException;

	public abstract Hashtable getModelGroupHash(String office_id)
			throws PlatformException;

	public abstract int changeGroupRights(String rightGroup_id,
			String office_id, String[] checkgroup) throws PlatformException;

	public abstract Hashtable getRightsHash(String rightGroup_id)
			throws PlatformException;

	public abstract List getModelRights(String model_id)
			throws PlatformException;

	public abstract List getRightModels() throws PlatformException;

	public abstract List getSiteRightModels() throws PlatformException;

	public abstract List getRightGroup() throws PlatformException;

	public abstract List getRightGroupMember(String group_id)
			throws PlatformException;

	public abstract List getModelGroups(String manager_id)
			throws PlatformException;

	public abstract List getModelRights(String manager_id, String modelgroup)
			throws PlatformException;

	public abstract List getModelRights2(String manager_id, String modelgroup)
		throws PlatformException;

	
	public abstract List getSiteModelRights(String submanager_id,
			String modelgroup) throws PlatformException;

	public abstract List getSiteModelRights2(String submanager_id,
			String modelgroup) throws PlatformException;


	public abstract int deleteRightGroupMember(String id)
			throws PlatformException;

	public abstract List getManagerList() throws PlatformException;

	public abstract List getSiteManagerList(Page page, String loginId,
			String name, String siteId, String groupId)
			throws PlatformException;

	public abstract List getSiteList() throws PlatformException;

	public abstract List getGradeList() throws PlatformException;

	public abstract List getMajorList() throws PlatformException;

	public abstract List getEduTypeList() throws PlatformException;

	public abstract int addAdmin(String login_id, String name, String password,
			String status, String type, String mobilephone)
			throws PlatformException;

	public abstract int addAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract String getPassword(String login_id)
			throws PlatformException;

	public abstract Manager getAdmin(String id) throws PlatformException;

	public abstract SiteManager getSiteAdmin(String id)
			throws PlatformException;

	public abstract int updateAdmin(String login_id, String name,
			String password, String mobile, String status)
			throws PlatformException;

	public abstract int updateAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract int addSiteAdmin(String login_id, String name,
			String password, String status, String type, String site_id)
			throws PlatformException;

	public abstract int addSiteAdmin(String login_id, String name,
			String password, String mobilephone, String status, String type,
			String site_id, String group_id) throws PlatformException;

	public abstract int addSiteAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract int putSiteAdminRight(String admin_id)
			throws PlatformException;

	public abstract int showSiteAdminRight(String login_id)
			throws PlatformException;

	public abstract int updateSiteAdmin(String login_id, String name,
			String password, String mobilephone, String status, String site_id)
			throws PlatformException;

	public abstract int updateSiteAdmin(HttpServletRequest request)
			throws PlatformException;

	public abstract int updateAdminMobile(String login_id, String mobilephone)
			throws PlatformException;

	public abstract int updateGroup(String id, String group_id)
			throws PlatformException;

	public abstract int updateRight(String id, List right)
			throws PlatformException;

	public abstract int IsExist(String group_id, String right_id)
			throws PlatformException;

	public abstract int updateRangeGroupRights(String id, String site,
			String grade, String major, String edutype)
			throws PlatformException;

	public abstract int updateRangeAdminRights(String id, String site,
			String grade, String major, String edutype)
			throws PlatformException;

	public abstract RightGroup getRightGroup(String id)
			throws PlatformException;

	/**
	 * 首页在线报名学生
	 * 
	 * @return 1为成功,0为不成功
	 * @throws NoRightException
	 */
	/**
	 * 获取当前活动批次
	 * 
	 * @return
	 * @throws PlatformException
	 */
	public abstract RecruitBatch getActiveRecruitBatch()
			throws PlatformException;

	public abstract int addRecruitStudent(String name, String password,
			String gender, String folk, String birthday, String zzmm,
			String edu, String site_id, String edutype_id, String major_id,
			String card_type, String card_no, String hometown, String[] email,
			String postalcode, String address, String[] mobilephone,
			String[] phone, String considertype, String status,
			String premajor_status, String school_name, String school_code,
			String graduate_year, String graduate_cardno)
			throws NoRightException, PlatformException;

	public abstract RecruitStudent getRecruitInfo(String card_no,
			String password, String batchId) throws PlatformException;

	public abstract int updateRecruitStudentInfo(String id, String name,
			String password, String gender, String folk, String birthday,
			String zzmm, String edu, String site_id, String edutype_id,
			String major_id, String card_type, String card_no, String hometown,
			String[] email, String postalcode, String address,
			String[] mobilephone, String[] phone, String considertype,
			String status, String premajor_status, String school_name,
			String school_code, String graduate_year, String graduate_cardno)
			throws PlatformException;

	public abstract String updateRecruitStudentInfo(String regNo,
			String siteId, String majorId) throws PlatformException;

	public abstract RecruitStudent getRecruitInfo(String id)
			throws PlatformException;

	public abstract RecruitStudent getRecruitInfo1(String regNo)
			throws PlatformException;

	public abstract RecruitStudent getRecruitInfo2(String cardNO)
			throws PlatformException;

	public abstract int checkRecruitExist(String cardNO)
			throws PlatformException;

	public abstract RecruitStudent getRecruitInfo2(String name, String cardNo,
			String batchId) throws PlatformException;

	public abstract Site getSiteInfo(String id) throws PlatformException;

	/**
	 * 获取当前招生批次
	 */
	public abstract List getActiveBatch();

	public abstract int regNewStudent(String studentId, String regNo)
			throws PlatformException;

	public abstract int regNewStudent(String studentId, String regNo,
			String ssoUserId) throws PlatformException;

	public abstract int checkNewStudentEleByFee(String regNo, String ssoUserId)
			throws PlatformException;

	public abstract ManagerPriv getManagerPriv() throws PlatformException;

}
