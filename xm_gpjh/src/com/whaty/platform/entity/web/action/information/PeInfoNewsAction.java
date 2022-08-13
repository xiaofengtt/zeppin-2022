package com.whaty.platform.entity.web.action.information;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageInputStream;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeInfoNewsType;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.PeTrainee;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;
/**
 * 新闻管理
 * @author 李冰
 *
 */
public class PeInfoNewsAction extends MyBaseAction {
	private PeInfoNews news;
	private List<PeInfoNewsType> newList; //类型
	private String nid;
	private String note;
	private String title;
	private String types;
	
	private String summary;
	private String stats;
	
	
	//上传文件
	private File upload;
	private String uploadContentType;
	private String uploadFileName;
	

	@Override
	public void initGrid() {
		this.getGridConfig().setTitle(this.getText("新闻管理"));
		this.getGridConfig().setCapability(false, true, false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("新闻标题"), "title", true, true, true, "TextField", true, 100);
		this.getGridConfig().addColumn(this.getText("新闻类型"),"peInfoNewsType.name");
		this.getGridConfig().addColumn(this.getText("新闻图片"),"pictrue",false,true, false, "File", true, 100);
		this.getGridConfig().addColumn(this.getText("内容简介"),"summary",false, true, true, "TextField", false,500);
		this.getGridConfig().addColumn(this.getText("报道时间"), "reportDate",true , false, true , "");
		this.getGridConfig().addColumn(this.getText("发布人"), "peManager.name" ,true ,false,true , "");
		this.getGridConfig().addColumn(this.getText("审核时间"), "submitDate",true , false, true , "");
		this.getGridConfig().addColumn(this.getText("审核人"), "confirmManagerId",false , false, true , "");
		this.getGridConfig().addColumn(this.getText("审核状态"), "enumConstByFlagNewsStatus.name",
			false, false, true, "");
		this.getGridConfig().addColumn(this.getText("活动状态"), "enumConstByFlagIsactive.name",
			false, false, true, "");
		this.getGridConfig().addRenderFunction(this.getText("修改"),
				"<a href=\"peInfoNews_updateInfoNews.action?bean.id=${value}\">修改</a>",
				"id");
		this.getGridConfig().addColumn(this.getText("新闻内容"), "note", false,
				true, false, "TextEditor", true, 1000000);
		this.getGridConfig().addRenderFunction(this.getText("查看详情"),
						"<a href=\"peInfoNews_viewDetail.action?bean.id=${value}\" target=\"_blank\">查看详细信息</a>",
						"id");
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us!=null) {
			String userLoginType = us.getUserLoginType();
			SsoUser ssoUser = us.getSsoUser();
			if (userLoginType.equals("3")) {
				DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
				dc.createCriteria("ssoUser", "ssoUser").add(Restrictions.eq("id", ssoUser.getId()));
				List<PeManager> peManagerList = new ArrayList<PeManager>();
				try {
					 peManagerList = this.getGeneralService().getList(dc);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String manager = peManagerList.get(0).getName();
				this.getGridConfig().addMenuFunction(this.getText("审核通过"),
								"enumConstByFlagNewsStatus.id", 
								this.getMyListService().getEnumConstByNamespaceCode("FlagNewsStatus", "1").getId(),
								"confirmManagerId" ,manager);
				this.getGridConfig().addMenuFunction(this.getText("审核不通过"),
						"enumConstByFlagNewsStatus.id", 
						this.getMyListService().getEnumConstByNamespaceCode("FlagNewsStatus", "0").getId(),
						"confirmManagerId" ,manager);
			}
		}
		this.getGridConfig().addMenuFunction(this.getText("设为活动"),
						"enumConstByFlagIsactive.id" ,
						this.getMyListService().getEnumConstByNamespaceCode("FlagIsactive", "1").getId());
		this.getGridConfig().addMenuFunction(this.getText("设为非活动"),
						"enumConstByFlagIsactive.id" , 
						this.getMyListService().getEnumConstByNamespaceCode("FlagIsactive", "0").getId());
	}
	
	@Override
	public void checkBeforeAdd() throws EntityException{
		UserSession us = (UserSession)ServletActionContext
				.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String userLoginType = us.getUserLoginType();
		SsoUser ssoUser = us.getSsoUser();
		if (!userLoginType.equals("3")) {
			throw new EntityException("无操作权限");
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeManager.class);
		dc.createCriteria("ssoUser", "ssoUser").add(Restrictions.eq("id", ssoUser.getId()));
		List<PeManager> peManagerList = this.getGeneralService().getList(dc);
		this.getBean().setPeManager(peManagerList.get(0));
		this.getBean().setReportDate(new Date());
		this.getBean().setEnumConstByFlagIsactive(
				this.getMyListService().getEnumConstByNamespaceCode("FlagIsactive", "0"));
		this.getBean().setEnumConstByFlagNewsStatus(
				this.getMyListService().getEnumConstByNamespaceCode("FlagNewsStatus", "0"));
		this.getBean().setReadCount(0L);
	}
	
	public void checkBeforeUpdate() throws EntityException{
		if (this.getBean().getEnumConstByFlagNewsStatus().getCode().equals("1")){
			throw new EntityException("只能修改未审核状态的新闻！");
		}

	}

	/**
	 * 审核操作设置审核时间
	 */
	public void checkBeforeUpdateColumn(List idList) throws EntityException{
		String str = this.getColumn();
		if (str.indexOf("enumConstByFlagNewsStatus.id,confirmManagerId") >= 0) {
			this.setColumn(str+",submitDate");
			this.setValue(this.getValue()+ ","+new Date().getTime());
		}
	
	}
	public void checkBeforeDelete(List idList) throws EntityException{
		PeInfoNews pe=null;
		this.getGeneralService().getGeneralDao().setEntityClass(PeInfoNews.class);
		for(int i=0;i<idList.size();i++){
			pe=(PeInfoNews) this.getGeneralService().getById(idList.get(i).toString());
			if(pe.getEnumConstByFlagIsactive()!=null){
				if(!pe.getEnumConstByFlagIsactive().getCode().equals("0")){
					throw new EntityException("该新闻处于活动状态不能删除");
				}
			}
		}
	}
	public String viewDetail() {
		try {
			this.setNews((PeInfoNews)this.getGeneralService().getById(this.getBean().getId()));	
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "detail";
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeInfoNews.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/information/peInfoNews";
	}
	public void setBean(PeInfoNews instance) {
		super.superSetBean(instance);

	}
	
	public String showAddNews() {
		DetachedCriteria newdc = DetachedCriteria.forClass(PeInfoNewsType.class);
		newdc.add(Restrictions.not(Restrictions.eq("id", "ZXBM")));
		try {
			this.setNewList(this.getGeneralService().getList(newdc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "newList";
	}
	
	public String updateInfoNews(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		if ((userSession == null)||(!userLoginType.equals("3"))) {
			stats="nostats";
			return "addNews";
		}
		try {
			DetachedCriteria newdc = DetachedCriteria.forClass(PeInfoNewsType.class);
			newdc.add(Restrictions.not(Restrictions.eq("id", "ZXBM")));
			this.setNewList(this.getGeneralService().getList(newdc));
			DetachedCriteria infodc = DetachedCriteria.forClass(PeInfoNews.class);
			infodc.createAlias("enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
			infodc.add(Restrictions.eq("id", this.getBean().getId()));
			PeInfoNews infoNews=(PeInfoNews)this.getGeneralService().getList(infodc).get(0);
			String coid = infoNews.getEnumConstByFlagNewsStatus().getCode();
			if(coid.equals("1")){
				stats="nocode";
				return "addNews";
			}
			this.setNews((PeInfoNews)this.getGeneralService().getById(this.getBean().getId()));	
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "modifyNews";
	}
	
	public String modifyInfoNews(){
		String pic ="";
		try {
			PeInfoNews peInfoNews =(PeInfoNews)this.getGeneralService().getById(nid);
			DetachedCriteria ndc = DetachedCriteria.forClass(PeInfoNewsType.class);
			ndc.add(Restrictions.eq("id", types));
			PeInfoNewsType infoNewsType = (PeInfoNewsType)this.getGeneralService().getList(ndc).get(0);
			
			if(upload!=null){
				pic =this.uploadexe(upload, uploadFileName);
				peInfoNews.setPictrue(pic);
			}
			peInfoNews.setPeInfoNewsType(infoNewsType);
			
			peInfoNews.setTitle(title);
			peInfoNews.setNote(note);
			peInfoNews.setSummary(summary);
			this.getGeneralService().save(peInfoNews);
			stats="modifyok";
		} catch (EntityException e) {
			stats="modify";
		}
		return "addNews";
	}
	
	protected String uploadexe(File file,String filename){
		String link ="";
		try{
		String savePath = "/incoming/photo/";
		link = savePath+filename;
		String linkTemp = link;
		int afterFileName = 0;
		
		while(true){
			if(new File(ServletActionContext.getRequest().getRealPath(linkTemp)).isFile()){
				int point = (link.lastIndexOf(".")>0 ? link.lastIndexOf("."):link.length());
				linkTemp = link.substring(0, point)+"["+String.valueOf(afterFileName)+"]"+link.substring(point);
				afterFileName++;
				continue;
			}
			break;
		}
		
		FileOutputStream fos=new FileOutputStream(ServletActionContext.getRequest().getRealPath(linkTemp));
		FileInputStream fis=new FileInputStream(file);
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=fis.read(buffer))>0){
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
		}catch (Exception e) {
			
		}
		return link;
		
	}
	
	
	public String addInfoNews(){
	
	
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		
		PeInfoNews infoNews = new PeInfoNews();
		
		if ((userSession == null)||(!userLoginType.equals("3"))) {
			stats="nostats";
			return "addNews";
		}
		
		String pic ="";
		try {
			
		if(upload!=null){
			pic= this.uploadexe(upload, uploadFileName);
			infoNews.setPictrue(pic);
		}
			DetachedCriteria pemdc = DetachedCriteria.forClass(PeManager.class);
			pemdc.add(Restrictions.eq("ssoUser", userSession.getSsoUser()));
			
			PeManager manager =(PeManager)this.getGeneralService().getList(pemdc).get(0);
			
			EnumConst enumConstByFlagNewsStatus = this.getMyListService().getEnumConstByNamespaceCode("FlagNewsStatus", "0");
			EnumConst enumConstByFlagIsactive = this.getMyListService().getEnumConstByNamespaceCode("FlagIsactive", "0");
			
			PeInfoNewsType infoNewsType = null;
			List<PeInfoNewsType> list = new ArrayList();
			DetachedCriteria ndc = DetachedCriteria.forClass(PeInfoNewsType.class);
			ndc.add(Restrictions.eq("id", types));
				try {
					list = this.getGeneralService().getList(ndc);
					if(list.size()>0){
						infoNewsType = list.get(0);
						
					}
				} catch (EntityException e1) {
					stats="failuer";
				}
			infoNews.setTitle(title);
			infoNews.setNote(note);
			infoNews.setPeInfoNewsType(infoNewsType);
			infoNews.setSummary(summary);
			
			Date currentTime = new Date();
			   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			   String dateString = formatter.format(currentTime);
			   Date currentTime_2 = formatter.parse(dateString);
			   java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime()); 
			
			infoNews.setPeManager(manager);
			infoNews.setReportDate(d2);
			infoNews.setReadCount(0L);
			infoNews.setEnumConstByFlagIsactive(enumConstByFlagIsactive);
			infoNews.setEnumConstByFlagNewsStatus(enumConstByFlagNewsStatus);
			
			this.getGeneralService().save(infoNews);
			stats ="success";
		} catch (EntityException e) {
			stats="failuer";
			e.printStackTrace();
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return "addNews";
	}

	public PeInfoNews getBean() {
		return (PeInfoNews) super.superGetBean();
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeInfoNews.class);
		dc.createCriteria("peInfoNewsType", "peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dc.createAlias("enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		dc.createCriteria("peManager", "peManager",
				DetachedCriteria.LEFT_JOIN);
		return dc;
	}
	
	
	
	public PeInfoNews getNews() {
		return news;
	}

	public void setNews(PeInfoNews news) {
		this.news = news;
	}

	public List<PeInfoNewsType> getNewList() {
		return newList;
	}

	public void setNewList(List<PeInfoNewsType> newList) {
		this.newList = newList;
	}

	public String getNote() {
		return note;
	}

	public String getTitle() {
		return title;
	}

	public String getTypes() {
		return types;
	}

	public String getSummary() {
		return summary;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}
}
