package com.whaty.platform.info.web.action;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.Project;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.dialect.InformixDialect;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeRecruitplan;
import com.whaty.platform.entity.bean.PeStudent;
import com.whaty.platform.entity.service.recruit.PeStudentService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.info.bean.InfoNews;
import com.whaty.platform.info.bean.InfoNewsType;
import com.whaty.platform.info.service.InfoNewsService;
import com.whaty.platform.sso.bean.SsoUser;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
/**
 * 实现平台首页各种信息的显示，信息列表页面分页功能的实现
 * @author zqf
 * 审核人：
 */
public class FirstPageInfoListAction extends InfoBaseAction {
	
	private InfoNewsService infoNewsService;
	private PeStudentService peStudentService;
	private String type;						//获取新闻类型
	private String id;							//获取页面所传某一新闻具体id
	
	private String leftHtm;						//用来确定页面左边的frame
	
	private List<InfoNews> infos;				//用于存放二级显示的新闻信息，如：专业简介等
	private List<InfoNews> xyxwList;			//用于存放首页显示的学院新闻列表
	private Page infoPage;						//用于分页显示
	private String property;					//通知公告的范围条件
	private InfoNews infonews;					//获得详细信息
	
	private List<InfoNews> popNews;				//用于首页的弹出新闻

	//以下字段用于分页显示
	private String start; 				// 列表开始的位置
	private String limit; 				// 每页要显示的信息个数
	private int curPage;				//当前页
	private int nextPage;				//下一页
	private int prePage;				//上一页
	private int totalPage;				//总页数
	private int pageNo;					//从输入框中获取的页码数
	
	private int isLimit;				//判断请求是否由设定页面信息个数所发出的，1是，0否
	
	//以下字段用于首页对于浏览有限制，需进行登陆后才能浏览
	
	private String reg_no;
	private String pass_word;
	private String operateresult;			//登陆操作结果信息
	
	
	
	public InfoNews getInfonews() {
		return infonews;
	}

	public void setInfonews(InfoNews infonews) {
		this.infonews = infonews;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public String getReg_no() {
		return reg_no;
	}

	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public int getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(int isLimit) {
		this.isLimit = isLimit;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		if(limit == null || "".equals(limit)){
			return "10";
		}
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public Page getInfoPage() {
		return infoPage;
	}

	public void setInfoPage(Page infoPage) {
		this.infoPage = infoPage;
	}

	public InfoNewsService getInfoNewsService() {
		return infoNewsService;
	}

	public void setInfoNewsService(InfoNewsService infoNewsService) {
		this.infoNewsService = infoNewsService;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeftHtm() {
		return leftHtm;
	}

	public void setLeftHtm(String leftHtm) {
		this.leftHtm = leftHtm;
	}

	public List<InfoNews> getInfos() {
		return infos;
	}

	public void setInfos(List<InfoNews> infos) {
		this.infos = infos;
	}

	public List<InfoNews> getPopNews() {
		return popNews;
	}

	public void setPopNews(List<InfoNews> popNews) {
		this.popNews = popNews;
	}

	/**
	 * 获取首页显示数据
	 * @return
	 */
	public String getInfo(){
		
		List<InfoNews> info = new ArrayList();
		
		/**
		 * 首先获取其父新闻类型，用于页面左边的frame显示
		 */
		DetachedCriteria dcType = DetachedCriteria.forClass(InfoNewsType.class);
		dcType.createCriteria("infoNewsType", "infoNewsType");
		try{
			dcType.add(Restrictions.eq("id", this.getType()));
		}catch(Exception e){
			
		}
		
		List<InfoNewsType> types = this.getInfoNewsService().getInfoNewsList(dcType);
		try{
			this.setLeftHtm(types.get(0).getInfoNewsType().getId());
		}catch(Exception e){
			
		}
		
		/**
		 * 获取新闻信息
		 */
		DetachedCriteria dcInfoNews = DetachedCriteria.forClass(InfoNews.class);
		
		DetachedCriteria dcInfoNewsType = dcInfoNews.createCriteria("infoNewsType", "infoNewsType");
		
		dcInfoNews.add(Restrictions.eq("isactive", new Long(1)));				//只显示活动状态的新闻
		
		dcInfoNews.add(Restrictions.eq("isconfirm", new Long(1)));				//只显示审核过的新闻
		
		/**
		 * 当新闻类型没有传过来的时候，不再进行数据库查询，直接返回空的数据
		 */
		try{
			dcInfoNewsType.add(Restrictions.eq("id",this.getType()));
		}catch(Exception e){
			
			this.setInfos(info);
			return "info";
		}
		
		dcInfoNews.addOrder(Order.desc("istop"));									//置顶的在前
		
		dcInfoNews.addOrder(Order.desc("reportDate"));										//发布时间晚的在前
		
		try{
			info = this.getInfoNewsService().getInfoNewsList(dcInfoNews);
		}catch(Exception e){
			
		}
		
		try{
			//页面显示日期格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			for(int i = 0; i < info.size(); i++){
				info.get(i).setDisplayDate(format.format(info.get(i).getReportDate()));
			}
		}catch(Exception e){
			
		}
		
		this.setInfos(info);
		
		return "info";
	}

	/**
	 * 用于首页显示信息列表
	 * @return
	 */
	public String getInfoList(){
		
		/**
		 * 判定请求是否由设置每页信息个数所发出的，如果是，则将start设置为0，curPage=1
		 */
		
		ActionContext axt = ActionContext.getContext();
		
		if(this.getIsLimit() == 1){
			this.setStart("0");
			this.setCurPage(1);
			
			/**
			 * 将limit放到session里
			 */
			axt.getSession().put("limit",this.getLimit());
		}
		
		this.setLimit((String)axt.getSession().get("limit"));
		
		/**
		 * 由输入框中输入页码后点击提交进行页面跳转（所跳转页面的合法性校验在页面进行）
		 */
		if(this.getPageNo() != 0){
			this.setCurPage(this.getPageNo());
		}
		
		/**
		 * 首先设定默认开始页为第一页
		 */
		if(this.getCurPage() == 0){
			this.setCurPage(1);
		}
		/**
		 * 设定start
		 */
		if(this.getCurPage() > 1){
			Integer temp = new Integer(0);
			temp = (this.getCurPage()-1)*Integer.parseInt(this.getLimit());
			this.setStart(temp.toString());
		}else
			this.setStart("0");
		
		
		Page infoPage  = new Page(null,0,0,0);
		
		/**
		 * 首先获取其父新闻类型，用于页面左边的frame显示
		 */
		DetachedCriteria dcType = DetachedCriteria.forClass(InfoNewsType.class);
		dcType.createCriteria("infoNewsType", "infoNewsType");
		try{
			dcType.add(Restrictions.eq("id", this.getType()));
		}catch(Exception e){
			
		}
		
		List<InfoNewsType> types = this.getInfoNewsService().getInfoNewsList(dcType);
		try{
			this.setLeftHtm(types.get(0).getInfoNewsType().getId());
		}catch(Exception e){
			
		}
		
		/**
		 * 获取新闻列表
		 */
		DetachedCriteria dcInfoNews = DetachedCriteria.forClass(InfoNews.class);
		
		DetachedCriteria dcInfoNewsType = dcInfoNews.createCriteria("infoNewsType", "infoNewsType");
		
		dcInfoNews.add(Restrictions.eq("isactive", new Long(1)));				//只显示活动状态的新闻
		
		dcInfoNews.add(Restrictions.eq("isconfirm", new Long(1)));				//只显示审核过的新闻
		
		/**
		 * 当新闻类型没有传过来的时候，不再进行数据库查询，直接返回空的数据
		 */
		try{
			dcInfoNewsType.add(Restrictions.eq("id",this.getType()));
		}catch(Exception e){
			
			this.setInfoPage(infoPage);
			return "error";
		}
		
		dcInfoNews.addOrder(Order.desc("istop"));									//置顶的在前
		
		dcInfoNews.addOrder(Order.desc("reportDate"));										//发布时间晚的在前
		if(property!=null && !property.equals("bylw")){
			dcInfoNews.add(Restrictions.like("propertystring", property,MatchMode.ANYWHERE));
		}
		
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(us != null && us.getSsoUser() != null && us.getSsoUser().getId() != null){
			PeStudent stu = this.getPeStudentService().getById(us.getSsoUser().getId());
			
			if(stu != null){
				try {
					String site_id = stu.getPeSite().getId();
					String major_id = stu.getPeMajor().getId();
					String edutype_id = stu.getPeEdutype().getId();
					String grade_id = stu.getPeGrade().getId();
					
					dcInfoNews.add(Restrictions.or(Restrictions.isNull("propertystring"), Restrictions.or(Restrictions.not(Restrictions.like("propertystring", "sites",MatchMode.ANYWHERE)), Restrictions.and(Restrictions.like("propertystring", "sites",MatchMode.ANYWHERE), Restrictions.like("propertystring", site_id,MatchMode.ANYWHERE)))));
					
					dcInfoNews.add(Restrictions.or(Restrictions.isNull("propertystring"), Restrictions.or(Restrictions.not(Restrictions.like("propertystring", "majors",MatchMode.ANYWHERE)), Restrictions.and(Restrictions.like("propertystring", "majors",MatchMode.ANYWHERE), Restrictions.like("propertystring", major_id,MatchMode.ANYWHERE)))));
					
					dcInfoNews.add(Restrictions.or(Restrictions.isNull("propertystring"), Restrictions.or(Restrictions.not(Restrictions.like("propertystring", "edu_types",MatchMode.ANYWHERE)), Restrictions.and(Restrictions.like("propertystring", "edu_types",MatchMode.ANYWHERE), Restrictions.like("propertystring", edutype_id,MatchMode.ANYWHERE)))));

					dcInfoNews.add(Restrictions.or(Restrictions.isNull("propertystring"), Restrictions.or(Restrictions.not(Restrictions.like("propertystring", "grades",MatchMode.ANYWHERE)), Restrictions.and(Restrictions.like("propertystring", "grades",MatchMode.ANYWHERE), Restrictions.like("propertystring", grade_id,MatchMode.ANYWHERE)))));
					
				} catch (RuntimeException e) {
					
				}
			}
		}
		
		dcInfoNews.addOrder(Order.desc("reportDate"));
		
		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		dcInfoNews.setProjection(infoProjectionList);
		
		try{
			infoPage = this.getInfoNewsService().getInfoNewsByPage(dcInfoNews, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			
		}catch(Exception e){
			
		} 
		InfoNews instance = null;
		List<InfoNews> newsList = new ArrayList();
		List tempList = infoPage.getItems();
		try{
			for(int i = 0; i < tempList.size(); i++){
				instance = new InfoNews();
				instance.setId(((Object[])tempList.get(i))[0].toString());
				instance.setTitle(((Object[])tempList.get(i))[1].toString());
				instance.setReportDate(((Timestamp)((Object[])tempList.get(i))[2]));
				newsList.add(instance);
			}
		}catch(Exception e){
			
		}
		infoPage.setItems(newsList);
		try{
			
			//页面显示日期格式
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

			if(infoPage.getItems().size() > 0){
				for(int i = 0; i<infoPage.getItems().size(); i++){
					//对新闻标题长度进行限制，设置页面显示的日期
					/**
					 * 对标题带有格式的新闻先进行格式的处理
					 */
					String title = ((InfoNews)infoPage.getItems().get(i)).getTitle();
					String fontStyle = "";
					if(title.startsWith("<font")){
						int firstStyleEnd = title.indexOf(">");
						fontStyle = title.substring(0, firstStyleEnd + 1);
						int secondStyleStart = title.indexOf("<", firstStyleEnd);
						title = title.substring(firstStyleEnd + 1, secondStyleStart);
					}
					
					if(title.length()>35){
						title = title.substring(0, 32)+"...";
					}
					if(fontStyle.length() > 0){
						title = fontStyle + title;
						title += "</font>";
					}
					((InfoNews)infoPage.getItems().get(i)).setTitle(title);
					((InfoNews)infoPage.getItems().get(i)).setDisplayDate(format.format(((InfoNews)infoPage.getItems().get(i)).getReportDate()));
				}
			}
			
		}catch(Exception e){
			
		}
		this.setInfoPage(infoPage);
		
		/**
		 * 求出总页数
		 */
		double tempTotalPage = infoPage.getTotalCount()/Double.parseDouble(this.getLimit());
		
		/**
		 * 设置总页数
		 */
		if(tempTotalPage <= 1.0){
			this.setTotalPage(1);
		}else if(tempTotalPage > 1.0 && tempTotalPage < 2.0){
			this.setTotalPage(2);
		}else{
			if(infoPage.getTotalCount()%Integer.parseInt(this.getLimit()) > 0){
				this.setTotalPage(infoPage.getTotalCount()/Integer.parseInt(this.getLimit())+1);
			}else
				this.setTotalPage(infoPage.getTotalCount()/Integer.parseInt(this.getLimit()));
		}
		
		if(this.getCurPage() < 2){
			this.setPrePage(-1);					//当前页面为1时，页面不再显示上一页和首页
			
			if(this.getTotalPage()<2){
				this.setTotalPage(1);
				this.setNextPage(-1);				//当总页数为1的时候，不再显示下一页和末页
			}
			else
				this.setNextPage(this.getCurPage()+1);
		}else{
			this.setPrePage(this.getCurPage()-1);
			if(this.getCurPage() >= this.getTotalPage()){
				this.setNextPage(-1);							//当前页如果是最大页码数，不再显示下一页和末页
			}else
				this.setNextPage(this.getCurPage()+1);
		}
		if(property!=null && property.equals("teacher")){
			return "teacherAnnounce";
		}
		if(this.getProperty() != null && this.getProperty().equals("student")){
			return "studentAnnounce";
		}
		if(this.getProperty() != null && this.getProperty().equals("bylw")){
			return "bylw";
		}
		return "infoList";
	}
	
	/**
	 * 获取某一新闻的详细信息
	 * @return
	 */
	public String getDetail(){
		List<InfoNews> info = new ArrayList();
		
		/**
		 * 首先获取其父新闻类型，用于页面左边的frame显示
		 */
		DetachedCriteria dcType = DetachedCriteria.forClass(InfoNews.class);
		DetachedCriteria dc = dcType.createCriteria("infoNewsType", "infoNewsType");
		try{
			dcType.add(Restrictions.eq("id", this.getId()));
		}catch(Exception e){
			
		}
		
		List<InfoNews> types = this.getInfoNewsService().getInfoNewsList(dcType);
		try{
			this.setLeftHtm(types.get(0).getInfoNewsType().getInfoNewsType().getId());
		}catch(Exception e){
			
		}
		
		DetachedCriteria dcInfoNews = DetachedCriteria.forClass(InfoNews.class);
		/**
		 * 当新闻id没有传过来的时候，不再进行数据库查询，直接返回空的数据
		 */
		try{
			dcInfoNews.add(Restrictions.eq("id",this.getId()));
		}catch(Exception e){
			
			this.setInfos(info);
			return "detail";
		}
//		dcInfoNews.add(Restrictions.eq("isconfirm", new Long(1)));				//只显示审核过的新闻
//		dcInfoNews.add(Restrictions.eq("isactive", new Long(1)));
		
		try{
			info = this.getInfoNewsService().getInfoNewsList(dcInfoNews);
		}catch(Exception e){
			
		}
		//页面显示日期格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			for(int i = 0; i < info.size(); i++){
				info.get(i).setDisplayDate(format.format(info.get(i).getReportDate()));
			}
		}catch(Exception e){
			
		}
		
		this.setInfos(info);
		return "detail";
	}
	
	/**
	 * 获取首页的学院通告信息列表
	 * @return
	 */
	public String getFirst(){
		
		String sitemanager = "";
		
		try{
			sitemanager = ServletActionContext.getRequest().getParameter("sitemanager").toString();
		}catch(Exception e){
			
		}
		
		if(sitemanager != null && !"".equals(sitemanager)){
			//用于验证所传信息采集人的loginId是否存在
			String sql_check = " select s.login_id from pe_sitemanager t,sso_user s where t.id = s.id and s.login_id = '"+sitemanager.trim()+"' ";
			
			List list = new ArrayList();
			try {
				list = this.getPeStudentService().getBySQL(sql_check);
			} catch (Exception e) {
			}
			if(list.size() > 0){
				ActionContext.getContext().getSession().put("sitemanager", sitemanager);
			}
		}
		
		List<InfoNews> popNews = new ArrayList();
		
		this.setInfos(this.getFirstNews("654", 6));		//学院通告
		this.setXyxwList(this.getFirstNews("980", 4));	//学院新闻
		
		DetachedCriteria dcPopNews = DetachedCriteria.forClass(InfoNews.class);
		dcPopNews.add(Restrictions.eq("ispop", new Long(1)));
		dcPopNews.add(Restrictions.eq("isconfirm", new Long(1)));
		dcPopNews.addOrder(Order.desc("reportDate"));
		ProjectionList popProjectionList = Projections.projectionList();
		popProjectionList.add(Projections.property("id"));
		dcPopNews.setProjection(popProjectionList);
		try{
			popNews = this.getInfoNewsService().getInfoNewsList(dcPopNews);
		}catch(Exception e){
			
		}
		this.setPopNews(popNews);
		
		return "first";
	}
	
	/**
	 * 通过所传递的newsType_id查询出用于首页显示的新闻列表
	 * @param newsType_id
	 * @param limit
	 * @return
	 */
	public List<InfoNews> getFirstNews(String newsType_id,int limit){
		
		List<InfoNews> info = new ArrayList();
		List<InfoNews> tempList = new ArrayList();			//存储处理过的信息列表
		
		DetachedCriteria dcInfoNews = DetachedCriteria.forClass(InfoNews.class);
		
		DetachedCriteria dcInfoNewsType = dcInfoNews.createCriteria("infoNewsType", "infoNewsType");
		
		dcInfoNewsType.add(Restrictions.eq("id",newsType_id));
		
		dcInfoNews.add(Restrictions.eq("isactive", new Long(1)));				//只显示活动状态的新闻
		
		dcInfoNews.add(Restrictions.eq("isconfirm", new Long(1)));				//只显示审核过的新闻
		
		dcInfoNews.addOrder(Order.desc("istop"));									//置顶的在前
		
		dcInfoNews.addOrder(Order.desc("reportDate"));
		
		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		dcInfoNews.setProjection(infoProjectionList);
		Page infoPage = null;
		try{
			infoPage = this.getInfoNewsService().getInfoNewsByPage(dcInfoNews, limit, 0);
		}catch(Exception e){
			
		}
		InfoNews instance = null;
		
		List localList = infoPage.getItems();
		try{
			for(int i = 0; i < localList.size(); i++){
				instance = new InfoNews();
				instance.setId(((Object[])localList.get(i))[0].toString());
				instance.setTitle(((Object[])localList.get(i))[1].toString());
				instance.setReportDate(((Timestamp)((Object[])localList.get(i))[2]));
				info.add(instance);
			}
		}catch(Exception e){
			
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			if(info.size() > 0){
				for(int i=0;i<info.size();i++){
					//对新闻标题长度进行限制,并在新闻末尾加上时间
					
					String fontStyle = "";
					/**
					 * 对于带有字体格式的对标题先进行处理
					 */
					
					String title = info.get(i).getTitle();
					if(title.startsWith("<font")){
						int firstStyleEnd = title.indexOf(">");
						fontStyle = title.substring(0, firstStyleEnd + 1);
						int secondStyleStart = title.indexOf("<", firstStyleEnd);
						title = title.substring(firstStyleEnd + 1, secondStyleStart);
					}
					
					if(title.length()>26){
						title = title.substring(0, 26)+"...";
					}
					title += "["+format.format(info.get(i).getReportDate())+"]";
					if(fontStyle.length() > 0){
						title = fontStyle + title;
						title += "</font>";
					}
					info.get(i).setTitle(title);
					tempList.add(info.get(i));
				}
			}
		}catch(Exception e){
			
		}
		
		return tempList;
	}
	
	/**
	 * 获取首页弹出新闻信息
	 * @return
	 */
	public String getFirstPopNews(){
		
		List<InfoNews> popNews = new ArrayList();
		
		DetachedCriteria dcPopNews = DetachedCriteria.forClass(InfoNews.class);
		dcPopNews.add(Restrictions.eq("ispop", new Long(1)));
		dcPopNews.add(Restrictions.eq("isactive", new Long(1)));
		dcPopNews.add(Restrictions.eq("isconfirm", new Long(1)));
		dcPopNews.addOrder(Order.desc("submitDate"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			popNews = this.getInfoNewsService().getInfoNewsList(dcPopNews);
		}catch(Exception e){
			
		}
		try{
			for(int i = 0; i < popNews.size(); i++){
				popNews.get(i).setDisplayDate(format.format(popNews.get(i).getReportDate()));
			}
		}catch(Exception e){
			
		}
		this.setPopNews(popNews);
		
		return "popnews";
	}
	
	/**
	 * 招生简章信息显示
	 * @return
	 */
	public String getZhaoShengJianZhang(){
		
		List<InfoNews> infos = new ArrayList();
		InfoNews info = new InfoNews();
		
		/**
		 * 为了重用info.jsp，要设置以下两个参数
		 */
		this.setLeftHtm("720");
		this.setType("zsjz");
		
		DetachedCriteria dcPeRecruitplan = DetachedCriteria.forClass(PeRecruitplan.class);
		dcPeRecruitplan.add(Restrictions.eq("flagActive", "1"));
		
		List<PeRecruitplan> recruitplanList = new ArrayList();
		
		try{
			recruitplanList = this.getInfoNewsService().getInfoNewsList(dcPeRecruitplan);
		}catch(Exception e){
			
		}
		
		if(recruitplanList.size() > 0){
			info.setBody(recruitplanList.get(0).getJianzhang());
		}else{
			info.setBody("当前没有活动的招生批次！");
		}
		infos.add(info);
		this.setInfos(infos);
		return "info";
	}
	
	/**
	 * 对首页页面的浏览受限制的页面所添加的登陆窗口
	 * @return
	 */
	public String browselogin(){
		
		DetachedCriteria dcSsoUser = DetachedCriteria.forClass(SsoUser.class);
		
		dcSsoUser.add(Restrictions.eq("loginId", this.getReg_no()));
		
		dcSsoUser.add(Restrictions.eq("password", this.getPass_word()));
		
		List<SsoUser> userList = new ArrayList();
		
		try{
			userList = this.getInfoNewsService().getInfoNewsList(dcSsoUser);
		}catch(Exception e){
			
		}
		
		if(userList.size() > 0){
			/**
			 * 在一次会话过程中只需登陆一次，登陆成功后，只要session不消失，可避过登陆页面，
			 * 直接跳到信息列表页面
			 */
			ActionContext axt = ActionContext.getContext();
			axt.getSession().put("firstpagelogin", "1");
			return this.getInfoList();
		}else{
			this.setOperateresult("用户名和密码错误！请重新登陆！");
		}
		
		return "browselogin";
	}
	public String getAnnounceDetial(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		if(id!=null){
			infonews = this.getInfoNewsService().getInfoNewsById(id);
			if(infonews.getReportDate() != null){
				infonews.setDisplayDate(format.format(infonews.getReportDate()));
			}
			if(property != null && property.equals("teacher")){
				return "teacherDetial";
			}
			if(this.getProperty() != null && this.getProperty().equals("student")){
				return "studentDetial";
			}
		}
			return "error";
		
		
	}

	public List<InfoNews> getXyxwList() {
		return xyxwList;
	}

	public void setXyxwList(List<InfoNews> xyxwList) {
		this.xyxwList = xyxwList;
	}

	public PeStudentService getPeStudentService() {
		return peStudentService;
	}

	public void setPeStudentService(PeStudentService peStudentService) {
		this.peStudentService = peStudentService;
	}
}
