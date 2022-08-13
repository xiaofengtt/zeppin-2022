package com.whaty.platform.sso.web.action.admin;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeEdutype;
import com.whaty.platform.entity.bean.PeGrade;
import com.whaty.platform.entity.bean.PeMajor;
import com.whaty.platform.entity.bean.PeSite;
import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeEdutypeService;
import com.whaty.platform.entity.service.basic.PeGradeService;
import com.whaty.platform.entity.service.basic.PeMajorService;
import com.whaty.platform.entity.service.basic.PeSiteService;
import com.whaty.platform.entity.service.priority.PeSitemanagerService;
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
import com.whaty.platform.sso.web.action.SsoConstant;

public class SiteManagerPriorityManageAction extends MyBaseAction {
	
	private PeSitemanagerService peSitemanagerService;
	
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
	
	private EnumConstService enumConstService;
	
	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
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

	public PePriCategoryService getPePriCategoryService() {
		return pePriCategoryService;
	}

	public void setPePriCategoryService(PePriCategoryService pePriCategoryService) {
		this.pePriCategoryService = pePriCategoryService;
	}

	public PePriRoleService getPePriRoleService() {
		return pePriRoleService;
	}

	public void setPePriRoleService(PePriRoleService pePriRoleService) {
		this.pePriRoleService = pePriRoleService;
	}

	public PeSitemanagerService getPeSitemanagerService() {
		return peSitemanagerService;
	}

	public void setPeSitemanagerService(PeSitemanagerService peSitemanagerService) {
		this.peSitemanagerService = peSitemanagerService;
	}

	public PeSiteService getPeSiteService() {
		return peSiteService;
	}

	public void setPeSiteService(PeSiteService peSiteService) {
		this.peSiteService = peSiteService;
	}

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

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
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
		// TODO Auto-generated method stub

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
	 * 显示分站管理员列表
	 */
	public String showManagerList(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class)
			.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",CriteriaSpecification.LEFT_JOIN);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createAlias("peSite", "peSite",CriteriaSpecification.LEFT_JOIN);
//		dc.add(Restrictions.eq("groupId", SsoConstant.SSO_SITEMANAGER_GROUP_SIR));
		dc.addOrder(Order.asc("peSite.name"));
		dc.addOrder(Order.desc("id"));
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List list = getPeSitemanagerService().getList(dc);
		request.setAttribute("list", list);
		
		return "showright_site_admin";
	}
	
	private PeSitemanager peManager;
	private String managerId;
	private List<PeSite> list;
	
	/**
	 * 添加修改
	 */
	public String addUpdateManager()throws SsoException{
		PeSitemanager pe = null;
		try{
			pe = getPeManager();
			pe = getPeSitemanagerService().save(pe);
		}catch(EntityException e){
			throw new SsoException(e.getMessage());
		}
		return "right_site_admin";
	}
	
	/**
	 * 转到添加管理员页面;
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showManagerInfo(){
		DetachedCriteria d = DetachedCriteria.forClass(PeSite.class);
		List list = getPeSiteService().getList(d);
		setList(list);
		
		return "add_admin";
	}
	
	
	/**
	 * 转到修改管理员页面 
	 * @return
	 */
	public String showEditManager(){
		PeSitemanager pe = null;
		pe = getPeSitemanagerService().getById(getManagerId());
		setPeManager(pe);
		
		DetachedCriteria d = DetachedCriteria.forClass(PeSite.class);
		List list = getPeSiteService().getList(d);
		setList(list);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		String siteId = request.getParameter("siteId");
		request.setAttribute("type", type);
		request.setAttribute("siteId", siteId);
		
		return "edit_siteadmin";
	}
	
	/**
	 * 更新管理员信息
	 * @return
	 */
	public String updateInfo()throws SsoException{
		PeSitemanager pe = null;
		try{
			
			pe = getPeSitemanagerService().updateInfo(getPeManager());
		}catch(EntityException e){
			throw new SsoException(this.getText("error.sso.erroreditsitemanager")+e.getMessage());
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		if("sub".equals(type)){
			return "show_submanager_list";
		}else{
			return "right_site_admin";
		}
	}
	
	
	/**
	 * 删除管理员
	 * @return
	 */
	
	public String deleteInfo()throws SsoException{
		try{
			PeSitemanager pe = new PeSitemanager();
			pe.setId(getManagerId());
			getPeSitemanagerService().delete(pe);
		}catch(Exception e){
			throw new SsoException(this.getText("error.sso.errordeletesitemanager"));
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		if("sub".equals(type)){
			return "show_submanager_list";
		}else{
			return "right_site_admin";
		}
	}
	
//	=========================批量上传电话号码
	private String uploadContentType;

	private File upload;

	private String fileName;
	
	/**
	 * 批量上传手机号码
	 */
	public String updateMobileBatch()throws SsoException{
		try{
			int count = getPeSitemanagerService().updateMobileBatch(getUpload());
			
		}catch(EntityException e){
			throw new SsoException(e.getMessage());
		}
		this.addActionMessage(this.getText("test.add")+this.getText("test.success"));
		return "mobile_batch";
	}
	
//	===================导出
	/**
	 * 导出站长信息
	 */
	public String export(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class)
		.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",CriteriaSpecification.LEFT_JOIN);
		dc.createCriteria("ssoUser","ssoUser").createCriteria("pePriRole", "pePriRole",CriteriaSpecification.LEFT_JOIN);;
		dc.createAlias("peSite", "peSite",CriteriaSpecification.LEFT_JOIN);
//		dc.add(Restrictions.eq("groupId", SsoConstant.SSO_SITEMANAGER_GROUP_SIR));
		dc.addOrder(Order.asc("peSite.name"));
			
		List list = getPeSitemanagerService().getList(dc);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("list", list);
		
		return "siteadmin_excel_new";
	}
	
	private String siteId;
	
	/**
	 * 下属管理员列表
	 * @return
	 */
	public String showSubManagerList(){
		DetachedCriteria dc = DetachedCriteria.forClass(PeSitemanager.class)
		.createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",CriteriaSpecification.LEFT_JOIN);
		dc.createCriteria("ssoUser","ssoUser");
		dc.createAlias("peSite", "peSite",CriteriaSpecification.LEFT_JOIN);
		dc.add(Restrictions.eq("groupId", SsoConstant.SSO_SITEMANAGER_GROUP_PERSON));
		dc.add(Restrictions.eq("peSite.id", getSiteId()));
		dc.addOrder(Order.desc("id"));
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List list = getPeSitemanagerService().getList(dc);
		
		PeSite site = getPeSiteService().getById(getSiteId());
		request.setAttribute("list", list);
		request.setAttribute("site", site);
		
		return "submanager_list";
	}
	
	
	//====================================设置管理员分组
	/**
	 * 
	 */
	/**
	 *  设置所属权限组;
	 */
	public String changeAdminGroup(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
		
		List list = getPePriRoleService().getList(dc);
		request.getSession().setAttribute("list", list);
		
		PeSitemanager manager = getPeSitemanagerService().getById(getManagerId());
		request.getSession().setAttribute("manager", manager);
		
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return "change_site_group_new";
		
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
		
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		if("sub".equals(type)){
			return "show_submanager_list";
		}else{
			return "right_site_admin";
		}
		
		
	}
	
	private String sub; //判断是否为分站管理员，与分站 站长区分
	
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
		PeSitemanager manager = getPeSitemanagerService().getById(getManagerId());
		
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
			getPeSitemanagerService().updateRangeRight(sites, edutype, major, grade, getManagerId());
		}catch(EntityException e){
			logger.error(e);
			throw new SsoException(this.getText("error.sso.errorupdaterangeright"));
		}
		
		return "show_range_right";
			
	}

	
	
	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public PeSitemanager getPeManager() {
		return peManager;
	}

	public void setPeManager(PeSitemanager peManager) {
		this.peManager = peManager;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	public List<PeSite> getList() {
		return list;
	}

	public void setList(List<PeSite> list) {
		this.list = list;
	}
	
	
	

	
}
