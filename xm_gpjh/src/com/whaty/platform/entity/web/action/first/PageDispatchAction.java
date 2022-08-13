package com.whaty.platform.entity.web.action.first;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeInfoNewsType;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PageDispatchAction extends MyBaseAction {

	private String type = "";
	private String type_name = "";
	private String id = "";
	private String searchVal = "";

	private PeInfoNews news = null;
	private List<PeInfoNews> newsList = null;
	private List trainingLevel = null;

	private static int TITLE_LENTH = 30;
	private static int PAGE_SIZE = 15;
	private int totalPage = 1;
	private int totalSize = 0;
	private int curPage = 1;

	public String dispatch() {

		if (type.equals("ZXBM")) {
			type_name = "在线报名";
			getTraingTypeList();
			return "regist";
		}

		this.setTotalSize(0);
		this.setTotalPage(1);
		this.putTypeName();
		this.getPageNews();
		return "index";
	}

	public String search() {

		this.setType("SSJG");
		this.setType_name("搜索结果");
		this.setTotalSize(0);
		this.setTotalPage(1);
		this.getPageNews();
		return "index";
	}

	public String show() {

		this.putTypeName();
		getNewsById();
		return "show";
	}

	private void getNewsById() {

		try {
			news = (PeInfoNews) this.getGeneralService().getById(id);
			if(news!=null){
				news.setReadCount((news.getReadCount() == null ? 0 : news.getReadCount())+1);
				news=(PeInfoNews) this.getGeneralService().save(news);
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取新闻列表，包括分页功能
	 * 
	 */
	public void getPageNews() {
		newsList = new ArrayList<PeInfoNews>();
		List nList = null;

		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		if("SSJG".equals(type)) {
			dcNews.add(Restrictions.like("title", searchVal, MatchMode.ANYWHERE));
		} else {
			DetachedCriteria dcType = dcNews.createCriteria("peInfoNewsType","peInfoNewsType");
			dcType.add(Restrictions.eq("id", type));
		}
		
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));

		dcNews.addOrder(Order.desc("reportDate"));

		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		dcNews.setProjection(infoProjectionList);

		try {
			if (totalSize == 0) {
				nList = this.getGeneralService().getList(dcNews);
				this.setTotalSize(nList.size());
				if(totalSize == 0) {
					this.setTotalPage(1);
				} else if(totalSize % PAGE_SIZE == 0) {
					this.setTotalPage(totalSize / PAGE_SIZE);
				} else {
					this.setTotalPage(totalSize / PAGE_SIZE + 1);
				}
			}

			Page page = this.getGeneralService().getByPage(dcNews, PAGE_SIZE,
					(curPage - 1) * PAGE_SIZE);
			nList = page.getItems();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			for (Object obj : nList) {
				PeInfoNews news = new PeInfoNews();
				news.setId((String) (((Object[]) obj)[0]));
				news.setTitle((String) (((Object[]) obj)[1]));
				if ((((Object[]) obj)[2]) != null) {
					news.setReportDate(sdf.parse(sdf.format(((Object[]) obj)[2])));
				} else {
					news.setReportDate(null);
				}

				newsList.add(news);
			}
			
			this.cutTitle(newsList, TITLE_LENTH);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void putTypeName() {
		
		DetachedCriteria dcType = DetachedCriteria.forClass(PeInfoNewsType.class);
		dcType.add(Restrictions.eq("id", type));
		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("name"));
		dcType.setProjection(infoProjectionList);
		try {
			List list = this.getGeneralService().getList(dcType);
			
			if(list != null && list.size() > 0) {
				this.setType_name((String)list.get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 截取列表中新闻标题长度
	 * 
	 * @param list
	 * @param length
	 */
	private void cutTitle(List<PeInfoNews> list, int length) {
		for (PeInfoNews o : list) {
			this.newsTitle(o, length);
		}
	}

	private void newsTitle(PeInfoNews peInfoNews, int length) {
		String title = peInfoNews.getTitle();
		if (title.length() > length) {
			title = title.substring(0, length) + "...";
			peInfoNews.setTitle(title);
		}

	}
	
	/**
	 * 获取培训级别
	 * 
	 */
	private void getTraingTypeList() {
		trainingLevel = new ArrayList();
		DetachedCriteria dcLevel = DetachedCriteria.forClass(EnumConst.class);
		dcLevel.add(Restrictions.eq("namespace", "TrainingType"));
		
		try {
			trainingLevel = this.getGeneralService().getList(dcLevel);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
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

	@Override
	public void initGrid() {

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeInfoNews.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/first/pageDispatch";
	}

	public PeInfoNews getNews() {
		return news;
	}

	public void setNews(PeInfoNews news) {
		this.news = news;
	}

	public List<PeInfoNews> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<PeInfoNews> newsList) {
		this.newsList = newsList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public List getTrainingLevel() {
		return trainingLevel;
	}

	public void setTrainingLevel(List trainingLevel) {
		this.trainingLevel = trainingLevel;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
}
