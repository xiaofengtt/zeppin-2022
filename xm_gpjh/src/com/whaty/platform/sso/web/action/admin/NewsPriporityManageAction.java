package com.whaty.platform.sso.web.action.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.bean.InfoUserRight;
import com.whaty.platform.info.exception.InfoException;
import com.whaty.platform.info.service.InfoNewsService;
import com.whaty.platform.info.service.InfoNewsTypeService;
import com.whaty.platform.info.service.InfoUserRightService;
import com.whaty.platform.sso.exception.SsoException;

public class NewsPriporityManageAction extends MyBaseAction {

	
	private String id;
	private String name;
	private String type;
	private String parent_id;
	private String parent_name;
	private String up_id ;//父类型的父类型id
	private InfoNewsService infoNewsService;
	private InfoNewsTypeService infoNewsTypeService;
	private InfoUserRightService infoUserRightService;
	
	public InfoUserRightService getInfoUserRightService() {
		return infoUserRightService;
	}

	public void setInfoUserRightService(InfoUserRightService infoUserRightService) {
		this.infoUserRightService = infoUserRightService;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public InfoNewsService getInfoNewsService() {
		return infoNewsService;
	}

	public void setInfoNewsService(InfoNewsService infoNewsService) {
		this.infoNewsService = infoNewsService;
	}

	public InfoNewsTypeService getInfoNewsTypeService() {
		return infoNewsTypeService;
	}

	public void setInfoNewsTypeService(InfoNewsTypeService infoNewsTypeService) {
		this.infoNewsTypeService = infoNewsTypeService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	 * 显示新闻权限
	 */
	@SuppressWarnings("unchecked")
	public String getChangeNewsRight(){
		
		
		DetachedCriteria dc = DetachedCriteria.forClass(InfoNewsType.class);
		dc.createCriteria("infoNewsType", "parent",CriteriaSpecification.LEFT_JOIN);
		if(parent_id == null || parent_id.equals("") || parent_id.equals("null")){
			parent_id = "0";
		}
		dc.add(Restrictions.eq("infoNewsType.id", parent_id));
		
		//获得新闻类型
		List newtypeList = getInfoNewsTypeService().getInfoNewsTypeList(dc);
		
		
		//获得权限内的新闻类型
		DetachedCriteria dt = DetachedCriteria.forClass(InfoUserRight.class);
		dt.createAlias("ssoUser", "ssoUser");
		dt.createCriteria("infoNewsType", "infoNewsType").createAlias("infoNewsType", "parent");
		dt.add(Restrictions.eq("ssoUser.id", getId()));
		
		List rightList = getInfoUserRightService().getList(dt);
		Iterator it = rightList.iterator();
		List list = new ArrayList();
		while(it.hasNext()){
			String tId = ((InfoUserRight)it.next()).getInfoNewsType().getId();
			list.add(tId);
		}
		
		//以parent_id为id的新闻类型
		InfoNewsType newstype = getInfoNewsTypeService().getInfoNewsTypeById(getParent_id());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("newtypeList", newtypeList);
		request.setAttribute("list", list);
		request.setAttribute("newstype", newstype);
		
		return "change_news_right";
		
	}
	
	private InfoNewsType newstype;
	private List typeList ;
	/**
	 * 设置用户新闻类型权限
	 * @return
	 */
	public String updateInfoUserRight()throws SsoException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String[] page_news_type_ids = request.getParameterValues("page_news_type_ids");
		String[] news_type_ids = request.getParameterValues("listMultiAction");
		List pagelist = new ArrayList();
		List typelist = new ArrayList();
		for(String id :page_news_type_ids){
			pagelist.add(id);
		}
		if(news_type_ids != null){
			for(String id :news_type_ids){
				typelist.add(id);
			}
		}
		
		
		int count = 0;
		try{
			count = getInfoUserRightService().updateInfoUserRight(getId(), pagelist, typelist);
		}catch(InfoException e){
			throw new SsoException(this.getText("error.sso.errorupdateinfouserright"));
		}
		
		return "showpage";
		
	}
	
	
	/**
	 * 转向添加新闻类型页面
	 */
	public String showNewsTypeAdd(){
		
		DetachedCriteria dc = DetachedCriteria.forClass(InfoNewsType.class);
		dc.createCriteria("infoNewsType","father",CriteriaSpecification.LEFT_JOIN);
//		dc.add(Restrictions.eq("", arg1))
		
		List list = getInfoNewsTypeService().getInfoNewsTypeList(dc);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		this.setTypeList(list);
		
		return "info_type_add";
	}
	
	/**
	 * 添加新闻类型
	 * @return
	 */
	public String addNewsType()throws SsoException{
		
		InfoNewsType newsType = getInfoNewsTypeService().getInfoNewsTypeById(getNewstype().getInfoNewsType().getId());
		InfoNewsType addType = new InfoNewsType();
		BeanUtils.copyProperties(getNewstype(), addType);
		addType.setInfoNewsType(newsType);
		try{
			addType = getInfoNewsTypeService().save(addType);
		}catch(InfoException e){
			throw new SsoException(this.getText("error.sso.erroraddnewstype"));
		}
		return "showpage";
	}
	
	/**
	 * 修改新闻类型
	 * @return
	 */
	public String updateNewsType()throws SsoException{
		InfoNewsType newsType = getInfoNewsTypeService().getInfoNewsTypeById(getNewstype().getInfoNewsType().getId());
		InfoNewsType editType = getInfoNewsTypeService().getInfoNewsTypeById(getNewsTypeId());
		editType.setInfoNewsType(newsType);
		editType.setName(getNewstype().getName());
		editType.setTag(getNewstype().getTag());
		editType.setNote(getNewstype().getNote());
		editType.setStatus(getNewstype().getStatus());
		try{
			editType = getInfoNewsTypeService().save(editType);
		}catch(InfoException e){
			throw new SsoException(this.getText("error.sso.errorupdatenewstype"));
		}
		return "showpage";
		
	}
	
	private String newsTypeId;
	
	public String showEdit(){
		DetachedCriteria dc = DetachedCriteria.forClass(InfoNewsType.class);
		dc.createCriteria("infoNewsType","father",CriteriaSpecification.LEFT_JOIN);
		
		List list = getInfoNewsTypeService().getInfoNewsTypeList(dc);
		this.setTypeList(list);
		this.setNewstype(getInfoNewsTypeService().getInfoNewsTypeById(getNewsTypeId()));
		return "info_type_edit";
	}
	
	public String getNewsTypeId() {
		return newsTypeId;
	}

	public void setNewsTypeId(String newsTypeId) {
		this.newsTypeId = newsTypeId;
	}

	public InfoNewsType getNewstype() {
		return newstype;
	}

	public void setNewstype(InfoNewsType newstype) {
		this.newstype = newstype;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getUp_id() {
		return up_id;
	}

	public void setUp_id(String up_id) {
		this.up_id = up_id;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

}
