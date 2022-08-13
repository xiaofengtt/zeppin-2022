package com.whaty.platform.info.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.ConfirmManager;
import com.whaty.platform.info.bean.InfoNews;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.bean.SubmitManager;
import com.whaty.platform.info.exception.InfoException;
import com.whaty.platform.info.service.InfoNewsService;
import com.whaty.platform.info.service.InfoNewsTypeService;

/**
 * 
 * @author lwx 2008-6-20
 * @email  <p>liweixin@whaty.com</p>
 *
 */
public class InfoNewsManageAction extends InfoBaseAction {
	
//	 list返回给extjs的json字符串
	private String jsonString;

	// ----extjs 分页程序提交的request参数
	private String start; // 列表开始的位置
	private String limit; // 每页要显示的条目
	private String sort; // 排序的对应列
	private String dir; // 升序asc，降序desc
	private String newsIds;  //删除对应的教师id的json表达式
	// ------------------------------
	
	//--------新闻
	private String id;
	private String isactive; // 活动状态
	private String istop; // 置顶状态
	private String isconfirm; // 审核状态
	private String newsType; // 新闻类型
	private String reportDate; // 新闻报道日期
	private String title; // 标题
	private String shortTitle; //短标题
	private String reporter; //新闻来源
	private String submitDate; //提交日期
	private String body; //内容
	private String selStatus; //设置状态
	
	//-------------------------------
	
	//业务层方法
	private InfoNewsService infoNewsService;
	private InfoNewsTypeService infoNewsTypeService;
	
	
	public String getSelStatus() {
		return selStatus;
	}
	public void setSelStatus(String selStatus) {
		this.selStatus = selStatus;
	}
	public String getNewsIds() {
		return newsIds;
	}
	public void setNewsIds(String newsIds) {
		this.newsIds = newsIds;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getIsconfirm() {
		return isconfirm;
	}
	public void setIsconfirm(String isconfirm) {
		this.isconfirm = isconfirm;
	}
	public String getIstop() {
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}
	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
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
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	/**
	 * 获得新闻列表
	 */
	public String listByPage()throws InfoException{
		
		//增加查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(InfoNews.class).createAlias("infoNewsType","infoNewsType");
		
		if (this.getDir() != null && this.getSort() != null
				&& this.getDir().equalsIgnoreCase("desc")) {
			//if("infoNewsType.name".equals(this.getSort()))
			//	detachedCriteria.addOrder(Order.desc())
			detachedCriteria.addOrder(Order.desc(this.getSort()));
		} else {
			detachedCriteria.addOrder(Order.asc(this.getSort()));
		}
		if(this.getIsconfirm()!=null && !"".equals(this.getIsconfirm())){
			detachedCriteria.add(Restrictions.eq("isconfirm",Long.parseLong(this.getIsconfirm())));
		}
		if(this.getIstop() !=null && !"".equals(this.getIstop())){
			detachedCriteria.add(Restrictions.eq("istop",Long.parseLong(this.getIstop())));
		}
		if(this.getIsactive() !=null && !"".equals(this.getIsactive())){
			detachedCriteria.add(Restrictions.eq("isactive",Long.parseLong(this.getIsactive())));
		}
		if(this.getReportDate()!=null && !"".equals(this.getReportDate())){
			detachedCriteria.add(Restrictions.eq("reportDate",this.getReportDate()));
		}
		if(this.getTitle() !=null && !"".equals(this.getTitle())){
			detachedCriteria.add(Restrictions.or(Restrictions.like("title",this.getTitle(),MatchMode.ANYWHERE),Restrictions.like("shortTitle",this.getTitle(),MatchMode.ANYWHERE)));
		}
		if(this.getNewsType()!=null && !"".equals(this.getNewsType())){
			detachedCriteria.add(Restrictions.eq("infoNewsType.id",this.getNewsType()));
		}
		

		
		Page page = this.infoNewsService.getInfoNewsByPage(detachedCriteria,Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
//		List newsList = this.infoNewsService.getInfoNewsByPage(detachedCriteria,Integer.parseInt(this.getLimit()),Integer.parseInt(this.getStart()));
		
//		获得总条数与列表;
		String strCount = "";
//		int totalCount = this.infoNewsService.getInfoNewsTotalCount(detachedCriteria);
		int totalCount = page.getTotalCount();
		if(totalCount>0)
			strCount = (new Integer(totalCount)).toString();
		
		//json处理
		Map map = new HashMap();
		map.put("totalCount",strCount);
//		map.put("infonews",newsList);
		map.put("infonews",page.getItems());
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		logger.info("json string: "+jsonString);
		
		return "listjson";
	}
	
	/**
	 * 添加
	 */
	public String add()throws InfoException{
		InfoNews news = new InfoNews();
		InfoNewsType type = new InfoNewsType();
		type = this.infoNewsTypeService.getInfoNewsTypeById(this.getNewsType());
		news.setTitle(this.getTitle());
		news.setShortTitle(this.getShortTitle());
		news.setInfoNewsType(type);
		news.setIstop(Long.parseLong(this.getIstop()));
		news.setIsactive(Long.parseLong(this.getIsactive()));
		news.setReporter(this.getReporter());
//		news.setSubmitDate(this.getSubmitDate());
		news.setBody(this.getBody());
		
		InfoNews  n = this.infoNewsService.save(news);
		
		Map map = new HashMap();
		if(n != null && n.getId() !=null){
			map.put("success","true");
			map.put("info", n.getId());
			
			logger.info("添加新闻成功! id= "+n.getId());
		}else{
			map.put("failure","true");
			map.put("errorInfo", "add error");
			
			logger.error("添加新闻失败!");
		}
		
		//json处理
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		return "listjson";
	}
	
	/**
	 * 修改
	 */
	public String edit()throws InfoException{
		InfoNews news = new InfoNews();
		news.setId(this.getId());
		InfoNewsType type = new InfoNewsType();
		type = this.infoNewsTypeService.getInfoNewsTypeById(this.getNewsType());
		news.setTitle(this.getTitle());
		news.setShortTitle(this.getShortTitle());
		news.setInfoNewsType(type);
		news.setIstop(Long.parseLong(this.getIstop()));
		news.setIsactive(Long.parseLong(this.getIsactive()));
		news.setReporter(this.getReporter());
//		news.setReportDate(this.getSubmitDate());
//		news.setSubmitDate(this.getSubmitDate());
		SubmitManager sub = new SubmitManager();
		sub.setSubmitManagerId("");
		sub.setSubmitManagerName("");
		news.setSubmitManager(sub);
		ConfirmManager con = new ConfirmManager();
		con.setConfirmManagerId("");
		con.setConfirmManagerName("");
		news.setConfirmManager(con);
		news.setPicLink("");
		news.setPropertystring("");
		news.setBody(this.getBody());
		
		InfoNews  n = this.infoNewsService.save(news);
		
		Map map = new HashMap();
		if(n != null && n.getId() !=null){
			map.put("success","true");
			map.put("info", n.getId());
			
			logger.info("修改新闻成功! id= "+n.getId());
		}else{
			map.put("failure","true");
			map.put("errorInfo", "add error");
			
			logger.error("修改新闻失败!");
		}
		
		//json处理
		JSONObject obj = JSONObject.fromObject(map);
		jsonString = obj.toString();
		
		logger.info("json: "+jsonString);
		
		return "listjson";
	}
	
	/**
	 * 通过id获得详细信息
	 */
	public String getDetailById()  throws EntityException{

		InfoNews news = this.getInfoNewsService().getInfoNewsById(this.getId());
		Map map = new HashMap();
		List items = new ArrayList();
		items.add(news);
		map.put("infonews", items);
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		
		logger.info("detail: "+jsonString);
		return "listjson";
	}
	
	/**
	 * 删除教师
	 */
	public String delete()  throws EntityException{
		Map map = new HashMap();
		if (this.getNewsIds() != null && this.getNewsIds().length()>0) {
			String str =this.getNewsIds();	
			if(str!=null && str.length()>0)
			{
				String[] ids=str.split(",");
				List idList=new ArrayList();
				for(int i=0;i<ids.length;i++)
				{
					idList.add(ids[i]);
					this.infoNewsService.deleteInfoNewsByIds(idList);
					map.put("success","true");
					map.put("info",this.getNewsIds() );
				}
			}
			else
			{
				map.put("failure","true");
				map.put("errorInfo", "delete error");
			}
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		return "listjson";
	}
	
	/**
	 * 设置状态
	 */
	public String setNewsStatus(){
		Map map = new HashMap();
		if (this.getNewsIds() != null && this.getNewsIds().length()>0) {
			String str =this.getNewsIds();	
			if(str!=null && str.length()>0)
			{
				String[] ids=str.split(",");
				List idList=new ArrayList();
				for(int i=0;i<ids.length;i++)
				{
					idList.add(ids[i]);
					if("confirm".equals(this.getSelStatus()))
							this.infoNewsService.updateConfirmStatusByIds(idList);
					else if("top".equals(this.getSelStatus()))
						this.infoNewsService.updateTopStatusByIds(idList);
					else if("pop".equals(this.getSelStatus()))
						this.infoNewsService.updatePopStatusByIds(idList);
					else if("active".equals(this.getSelStatus()))
						this.infoNewsService.updateActiveStatusByIds(idList);
					map.put("success","true");
					map.put("info",this.getNewsIds() );
				}
			}
			else
			{
				map.put("failure","true");
				map.put("errorInfo", "delete error");
			}
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		jsonString = jsonObject.toString();
		return "listjson";
	}
	
}
