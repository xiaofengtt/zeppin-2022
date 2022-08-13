/**
 * 
 */
package cn.zeppin.action.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.sso.UserSession;
import cn.zeppin.entity.Organization;
import cn.zeppin.entity.TrainingAdmin;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.service.ITrainingAdminService;
import cn.zeppin.utility.DictionyMap.ROLEENUM;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 页面头部
 * 
 * @author sj
 * 
 */
public class HeadAction extends ActionSupport {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private HttpSession session;

	private String currentTime;
	private String username;
	private String userType;
	private Organization organization;
	private IOrganizationService iOrganizationService;
	private ITrainingAdminService iTrainingAdminService;
	private Integer changePwd;

	public void initServlert() {
		request = ServletActionContext.getRequest();
		session = request.getSession();
	}

	public String init() {
		initServlert();

		UserSession us = (UserSession) session.getAttribute("usersession");

		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 E", Locale.CHINA);
		this.currentTime = df.format(dt);
		this.username = us.getName();
		organization = iOrganizationService.get(us.getOrganization());
		short roleid = us.getRole();
		ROLEENUM ROLE = ROLEENUM.valueof(roleid);

		if (ROLE == ROLEENUM.SUPERADMIN) {
			this.username = "王洋";
			this.userType = "超级管理员";
		} else if (ROLE == ROLEENUM.TRAINING) {
			this.userType = "承训单位管理员";
			TrainingAdmin trainingAdmin = iTrainingAdminService.get(us.getId());
			String oname = trainingAdmin.getTrainingCollege().getName();
			organization = new Organization();
			organization.setName(oname);
		} else if (ROLE == ROLEENUM.ADMIN) {
			this.userType = "管理员";
		} else if (ROLE == ROLEENUM.PROJECTEXPERT){
			this.userType = "评审专家";
		}

		//20210119新增密码修改强制提醒
		//密码长度小于8位的，需要强制修改密码
		this.changePwd=us.getChangePwd();
		System.out.println("----------------------------------"+changePwd);
		return "init";
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(Integer changePwd) {
		this.changePwd = changePwd;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the iOrganizationService
	 */
	public IOrganizationService getiOrganizationService() {
		return iOrganizationService;
	}

	/**
	 * @param iOrganizationService
	 *            the iOrganizationService to set
	 */
	public void setiOrganizationService(IOrganizationService iOrganizationService) {
		this.iOrganizationService = iOrganizationService;
	}

	/**
	 * @return the iTrainingAdminService
	 */
	public ITrainingAdminService getiTrainingAdminService() {
		return iTrainingAdminService;
	}

	/**
	 * @param iTrainingAdminService
	 *            the iTrainingAdminService to set
	 */
	public void setiTrainingAdminService(ITrainingAdminService iTrainingAdminService) {
		this.iTrainingAdminService = iTrainingAdminService;
	}

}
