package com.whaty.platform.sso.web.action.admin;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeEdutypeService;
import com.whaty.platform.entity.service.basic.PeGradeService;
import com.whaty.platform.entity.service.basic.PeMajorService;
import com.whaty.platform.entity.service.basic.PeSiteService;
import com.whaty.platform.entity.service.priority.PeManagerService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.bean.PePriRole;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.exception.SsoException;
import com.whaty.platform.sso.service.PePriCategoryService;
import com.whaty.platform.sso.service.PePriRoleService;
import com.whaty.platform.sso.service.PrPriManagerEdutypeService;
import com.whaty.platform.sso.service.PrPriManagerGradeService;
import com.whaty.platform.sso.service.PrPriManagerMajorService;
import com.whaty.platform.sso.service.PrPriManagerSiteService;
import com.whaty.platform.sso.service.SsoUserService;

public class ManagerPriorityManageAction extends MyBaseAction {
	
	private PeManagerService peManagerService;
	
	private PePriCategoryService pePriCategoryService;
	
	private PePriRoleService pePriRoleService;
	
	private SsoUserService ssoUserService;
	
	private PeSiteService peSiteService;
	
	private PeEdutypeService peEdutypeService;
	
	private PeMajorService peMajorService;
	
	private PeGradeService peGradeService;
	
	private PrPriManagerSiteService prPriManagerSiteService;
	
	private PrPriManagerEdutypeService prPriManagerEdutypeService;
	
	private PrPriManagerMajorService prPriManagerMajorService;
	
	private PrPriManagerGradeService prPriManagerGradeService;
	
	public PrPriManagerEdutypeService getPrPriManagerEdutypeService() {
		return prPriManagerEdutypeService;
	}

	public void setPrPriManagerEdutypeService(
			PrPriManagerEdutypeService prPriManagerEdutypeService) {
		this.prPriManagerEdutypeService = prPriManagerEdutypeService;
	}

	public PrPriManagerGradeService getPrPriManagerGradeService() {
		return prPriManagerGradeService;
	}

	public void setPrPriManagerGradeService(
			PrPriManagerGradeService prPriManagerGradeService) {
		this.prPriManagerGradeService = prPriManagerGradeService;
	}

	public PrPriManagerMajorService getPrPriManagerMajorService() {
		return prPriManagerMajorService;
	}

	public void setPrPriManagerMajorService(
			PrPriManagerMajorService prPriManagerMajorService) {
		this.prPriManagerMajorService = prPriManagerMajorService;
	}

	public PrPriManagerSiteService getPrPriManagerSiteService() {
		return prPriManagerSiteService;
	}

	public void setPrPriManagerSiteService(
			PrPriManagerSiteService prPriManagerSiteService) {
		this.prPriManagerSiteService = prPriManagerSiteService;
	}

	public PeEdutypeService getPeEdutypeService() {
		return peEdutypeService;
	}

	public void setPeEdutypeService(PeEdutypeService peEdutypeService) {
		this.peEdutypeService = peEdutypeService;
	}

	public PeGradeService getPeGradeService() {
		return peGradeService;
	}

	public void setPeGradeService(PeGradeService peGradeService) {
		this.peGradeService = peGradeService;
	}

	public PeMajorService getPeMajorService() {
		return peMajorService;
	}

	public void setPeMajorService(PeMajorService peMajorService) {
		this.peMajorService = peMajorService;
	}

	public PeSiteService getPeSiteService() {
		return peSiteService;
	}

	public void setPeSiteService(PeSiteService peSiteService) {
		this.peSiteService = peSiteService;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public PePriRoleService getPePriRoleService() {
		return pePriRoleService;
	}

	public void setPePriRoleService(PePriRoleService pePriRoleService) {
		this.pePriRoleService = pePriRoleService;
	}

	public PePriCategoryService getPePriCategoryService() {
		return pePriCategoryService;
	}

	public void setPePriCategoryService(PePriCategoryService pePriCategoryService) {
		this.pePriCategoryService = pePriCategoryService;
	}

	public PeManagerService getPeManagerService() {
		return peManagerService;
	}

	public void setPeManagerService(PeManagerService peManagerService) {
		this.peManagerService = peManagerService;
	}

	@Override
	public Map add() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		this.setServletPath("/sso/admin/managerOper");

	}

	@Override
	public Page list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map updateColumn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 显示管理员列表
	 */
	public String showManagerList(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser","ssoUser").createAlias("pePriRole", "pePriRole",CriteriaSpecification.LEFT_JOIN);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List managerList = getPeManagerService().getList(dc);
		request.setAttribute("managerList", managerList);
		
		
		return "manager_list";
	}
	
	
	private String managerId;
	
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/**
	 *  设置所属权限组;
	 */
	public String changeAdminGroup(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		
		List list = getPePriRoleService().getList(dc);
		request.getSession().setAttribute("list", list);
		
		PeManager manager = getPeManagerService().getById(getManagerId());
		request.getSession().setAttribute("manager", manager);
		
		return "change_admin_group_new";
	}
	
	/**
	 * 更新管理员权限组
	 */
	public String updateAdminGroup()throws SsoException{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String checkedId = request.getParameter("admin_id");
		PePriRole role = getPePriRoleService().getById(checkedId);
		
		SsoUser user = getSsoUserService().getById(managerId);
		user.setPePriRole(role);
		
		try{
			user = getSsoUserService().save(user);
		}catch(SsoException e){
			throw new SsoException(this.getText("error.sso.changeAdminGroup"));
		}
		
		return "update_admin_group";
		
	}
	
	
	//=============================================添加管理员权限范围
	/**
	 *   转向添加权限范围
	 */
	public String showRangeRight(){
		
		//获得 站点，年级，专业，层次
		DetachedCriteria dcSite = DetachedCriteria.forClass(PeSite.class);
		List sites = getPeSiteService().getList(dcSite);
		
		DetachedCriteria dcEdutype = DetachedCriteria.forClass(PeEdutype.class);
		List edutype = getPeEdutypeService().getList(dcEdutype);
		
		DetachedCriteria dcMajor = DetachedCriteria.forClass(PeMajor.class);
		List major = getPeMajorService().getList(dcMajor);
		
		DetachedCriteria dcGrade = DetachedCriteria.forClass(PeGrade.class);
		List grade = getPeGradeService().getList(dcGrade);
		
//		//获得权限内站点年级专业层次
//		DetachedCriteria chSite = DetachedCriteria.forClass(PrPriManagerSite.class);
//		List checkedSites = getPrPriManagerSiteService().getList(chSite);
//		
		PeManager manager = getPeManagerService().getById(getManagerId());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("sites", sites);
		request.setAttribute("edutype", edutype);
		request.setAttribute("major", major);
		request.setAttribute("grade", grade);
		
		setPeManager(manager);
		
		
		return "change_range_right";
	}
	
	/**
	 *  添加权限范围
	 */
	public String updateRangeRight()throws SsoException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String[] sites = request.getParameterValues("site");
		String[] edutype = request.getParameterValues("edutype");
		String[] major = request.getParameterValues("major");
		String[] grade = request.getParameterValues("grade");
		
		try{
			getPeManagerService().updateRangeRight(sites, edutype, major, grade, getManagerId());
		}catch(EntityException e){
			logger.error(e);
			throw new SsoException(this.getText("error.sso.errorupdaterangeright"));
		}
		
		return "show_range_right";
	}
	
	
	
	
	//================================================添加管理员操作
	private PeManager peManager;
	
	/**
	 * 添加修改
	 */
	public String addUpdateManager()throws SsoException{
		PeManager pe = null;
		try{
			pe = getPeManagerService().save(getPeManager());
		}catch(EntityException e){
			throw new SsoException(e.getMessage());
		}
		
		return "update_admin_group";
	}
	
	/**
	 * 转到添加管理员页面;
	 * @return
	 */
	public String showManagerInfo(){
		return "add_superadmin";
	}
	
	/**
	 * 转到修改管理员页面 
	 * @return
	 */
	public String showEditManager(){
		PeManager pe = null;
		pe = getPeManagerService().getById(getManagerId());
		setPeManager(pe);
		return "edit_superadmin";
	}
	
	/**
	 * 更新管理员信息
	 * @return
	 */
	public String updateInfo()throws SsoException{
		PeManager pe = null;
		try{
			pe = getPeManagerService().updateInfo(getPeManager());
		}catch(Exception e){
			throw new SsoException(this.getText("error.sso.erroreditmanager")+e.getMessage());
		}
		return "update_admin_group";
	}
	
	/**
	 * 删除管理员
	 * @return
	 */
	
	public String deleteInfo()throws SsoException{
		try{
			PeManager pe = new PeManager();
			pe.setId(getManagerId());
			getPeManagerService().delete(pe);
		}catch(Exception e){
			throw new SsoException(this.getText("error.sso.errordeletemanager"));
		}
		return "update_admin_group";
	}
	
	
	//=========================批量上传电话号码
	private String uploadContentType;

	private File upload;

	private String fileName;
	
	/**
	 * 批量上传手机号码
	 */
	public String updateMobileBatch()throws SsoException{
		try{
			int count = getPeManagerService().updateMobileBatch(getUpload());
			
		}catch(EntityException e){
			throw new SsoException(e.getMessage());
		}
		this.addActionMessage(this.getText("test.add")+this.getText("test.success"));
		return "mobile_batch";
	}
	
	
	//===================导出
	/**
	 * 导出管理员信息
	 */
	public String export(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("ssoUser","ssoUser").createAlias("enumConstByUserType", "enumConstByUserType",CriteriaSpecification.LEFT_JOIN)
			.createCriteria("pePriRole", "pePriRole",CriteriaSpecification.LEFT_JOIN);
		dc.addOrder(Order.asc("pePriRole.name"));
		dc.addOrder(Order.asc("name"));
		List list = getPeManagerService().getList(dc);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("list", list);
		
		return "admin_excel_new";
	}
	

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public PeManager getPeManager() {
		return peManager;
	}

	public void setPeManager(PeManager peManager) {
		this.peManager = peManager;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}
	
	

}
