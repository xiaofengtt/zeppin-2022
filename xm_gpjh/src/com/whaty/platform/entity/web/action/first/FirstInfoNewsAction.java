package com.whaty.platform.entity.web.action.first;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeJianzhang;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * 网站前台新闻发布
 * 
 * @author 李冰
 * 
 */
public class FirstInfoNewsAction extends MyBaseAction {
    
	private String type; // 获取新闻类型、
	private List<PeBulletin> Bulletin;
	private List<PeInfoNews> wydt; // 网院动态
	private List<PeInfoNews> wjdt; // 网教动态
	private List<PeInfoNews> zcfg; // 政策法规
	private PeInfoNews peInfoNews; // 用于显示新闻的详细内容
	private PeJianzhang jianzhang; // 活动的招生简章，用于首页查看
	private String searchTitle; // 用于新闻列表页面的搜索功能
	private PeBulletin peBulletin; // 用于显示公告详细信息

	// 以下字段用于分页显示
	private List<PeInfoNews> newsList; // 新闻列表
	private List<PeInfoNews> tpnewsList; // 图片新闻列表
	private List<PeInfoNews> bzfc; // 组长风采
	private List<PeInfoNews> jsfc; // 教师风采
	private List<PeInfoNews> pxdt; // 培训动态
	private int begin; // 列表开始的位置
	private int number; // 每页要显示的信息个数
	private int curPage; // 当前页
	private int nextPage; // 下一页
	private int prePage; // 上一页
	private int totalPage; // 总页数
	private int totalCount; // 新闻总数
    private boolean flag_sjtj=false;
    private boolean flag_pxsfc=false;
	/**
	 * 左侧列表显示的新闻
	 * 
	 * @return
	 */
	public String toLeft() {
		this.setWydt(this.leftNews("网院动态"));
		this.setWjdt(this.leftNews("网教动态"));
		this.setZcfg(this.leftNews("政策法规"));
		return "left";
	}

	/**
	 * 根据新闻类型返回前三条新闻
	 * 
	 * @param type
	 *            新闻类型
	 * @return
	 */
	private List<PeInfoNews> leftNews(String type) {
		List<PeInfoNews> news = new ArrayList();
		DetachedCriteria dcWYDT = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcWYDT.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		dctype.add(Restrictions.eq("name", type));
		DetachedCriteria dcFlagNewsStatus = dcWYDT.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcWYDT.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		dcWYDT.addOrder(Order.desc("reportDate"));

		try {

			List<PeInfoNews> list = this.getGeneralService().getList(dcWYDT);
			
			for (int i = 0; i < list.size(); i++) {
				if (i > 2) {
					break;
				}
				//this.newsTitle(list.get(i), 13);
				this.listIsNew(list.get(i));
				news.add(list.get(i));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return news;
	}

	/**
	 * 对新闻标题保留合适的长度
	 * 
	 * @param peInfoNews
	 *            需处理的新闻
	 * @param length
	 *            保留标题长度
	 */
	private void newsTitle(PeInfoNews peInfoNews, int length) {
		String title = peInfoNews.getTitle();
		if (title.length() > length) {
			title = title.substring(0, length) + "...";
			peInfoNews.setTitle(title);
		}

	}
	
	/**
	 * 截取新闻内容长度
	 * 
	 * @param peInfoNews
	 * @param length
	 */
	private void newsContent(PeInfoNews peInfoNews, int length) {
		String content = peInfoNews.getNote();
		if (content.length() > length) {
			content = content.substring(0, length) + "...";
			peInfoNews.setNote(content);
		}

	}

	/**
	 * 查看新闻详细内容页面。点击次数+1
	 * 
	 * @return
	 */
	public String toInfoNews() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "infoNews";
	}
	
	public String toInfoDetail() {
		try {
			PeInfoNews news = (PeInfoNews) this.getGeneralService().getById(
					this.getBean().getId());
			// 点击次数+1
			news.setReadCount(news.getReadCount() + 1);

			this.getGeneralService().save(news);

			this.setPeInfoNews(news);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "infoDetail";
	}

	/**
	 * 新闻列表页面
	 * 
	 * @return
	 */
	public String toNewsList() {
		/**
		 * 设定number
		 */
		if (this.getNumber() <= 0) {
			this.setNumber(10);
		}
		/**
		 * 首先设定默认开始页为第一页
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 设定beain
		 */
		if (this.getCurPage() > 1) {

			int temp = (this.getCurPage() - 1) * this.getNumber();
			this.setBegin(temp);
		} else {
			this.setBegin(0);
		}
		
		Page infoPage = new Page(null, 0, 0, 0);
		// 设置查询条件
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcNews.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		if (this.getType() != null && this.getType().length() != 0) {
			dctype.add(Restrictions.eq("id", this.getType()));
		}
		
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		if (this.getSearchTitle() != null
				&& this.getSearchTitle().length() != 0) {
			dcNews.add(Restrictions.like("title", this.getSearchTitle(),
					MatchMode.ANYWHERE));
		}
		//System.out.println("title:"+this.getSearchTitle());
		dcNews.addOrder(Order.desc("reportDate"));

		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		dcNews.setProjection(infoProjectionList);
		// 取出新闻
		try {
			infoPage = this.getGeneralService().getByPage(dcNews,
					this.getNumber(), this.getBegin());
		} catch (EntityException e) {
			// 
			e.printStackTrace();
		}
		List<PeInfoNews> peInfoNewsList = new ArrayList();
		List tempList = infoPage.getItems();
		int num = (this.getCurPage() - 1) * this.getNumber() + 1;
		// 将取出的新闻设置到list中
		for (int i = 0; i < tempList.size(); i++) {
			PeInfoNews instance = new PeInfoNews();
			instance.setId(((Object[]) tempList.get(i))[0].toString());
			instance.setTitle(((Object[]) tempList.get(i))[1].toString());
			instance
					.setReportDate(((Timestamp) ((Object[]) tempList.get(i))[2]));
			instance.setReadCount(Long
					.parseLong(((Object[]) tempList.get(i))[3].toString()));
			this.listIsNew(instance);
			instance.setConfirmManagerId(num + ""); // 设置序号，暂保持到ConfirmManagerId中
			num++;
			peInfoNewsList.add(instance);
		}
		this.setNewsList(peInfoNewsList);
		/**
		 * 求出总页数
		 */
		double tempTotalPage = (double) infoPage.getTotalCount()
				/ (double) this.getNumber();
		
		/**
		 * 设置总页数
		 */
		if (tempTotalPage <= 1.0) {
			this.setTotalPage(1);
		} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
			this.setTotalPage(2);
		} else {
			if (infoPage.getTotalCount() % this.getNumber() > 0) {
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber()
						+ 1);
			} else
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber());
		}
		// 设置新闻总数
		this.setTotalCount(infoPage.getTotalCount());
		if (this.getCurPage() < 2) {
			this.setPrePage(-1); // 当前页面为1时，页面不再显示上一页和首页

			if (this.getTotalPage() < 2) {
				this.setTotalPage(1);
				this.setNextPage(-1); // 当总页数为1的时候，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		} else {
			this.setPrePage(this.getCurPage() - 1);
			if (this.getCurPage() >= this.getTotalPage()) {
				this.setNextPage(-1); // 当前页如果是最大页码数，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		}
		return "newstest";
	}

	/*
	 * 判断报道时间，最近一天的显示new
	 * 
	 * param peInfoNews
	 */
	private void listIsNew(PeInfoNews peInfoNews) {
		Date now = new Date();
		Long time = now.getTime() - peInfoNews.getReportDate().getTime();
		if (time <= 172800000) {
			// 暂定new的备注为new
			peInfoNews.setIsNew("1");
		} else {
			peInfoNews.setIsNew("");
		}
	}

	/**
	 * 获取新闻记录列表
	 * 
	 * @param newsType 新闻类型
	 * @param length 页面显示记录长度
	 * @param count 页面显示记录条数
	 * @param order 排序方案（0：id，1：title，2：reportDate，3：readCount）
	 * @return
	 */
	public List<PeInfoNews> getNewsInfoList(String newsType,int length,int count,int order){
		
		List nList = null;
		List<PeInfoNews> pList = new ArrayList<PeInfoNews>();
		String ord = "title";
		if(order == 0) {
			ord = "id";
		} else if(order == 1) {
			ord = "title";
		} else if(order == 2) {
			ord = "reportDate";
		} else if(order == 3) {
			ord = "readCount";
		}
		
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dcType = dcNews.createCriteria("peInfoNewsType","peInfoNewsType");
		dcType.add(Restrictions.eq("id", newsType));
		
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		
		dcNews.addOrder(Order.desc(ord));
		
		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("submitDate"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		infoProjectionList.add(Projections.property("pictrue"));
		dcNews.setProjection(infoProjectionList);
		
		try {
			Page p = this.getGeneralService().getByPage(dcNews, count, 0);
			nList = p.getItems();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Object obj : nList) {
				PeInfoNews news = new PeInfoNews();
				news.setId((String)(((Object[])obj)[0]));
				news.setTitle((String)(((Object[])obj)[1]));
				if((((Object[])obj)[2]) != null) {
					news.setSubmitDate(sdf.parse(sdf.format(((Object[])obj)[2])));
				} else {
					news.setSubmitDate(null);
				}
				if((((Object[])obj)[3]) != null) {
					news.setReportDate(sdf.parse(sdf.format(((Object[])obj)[3])));
				} else {
					news.setReportDate(null);
				}
				if((((Object[])obj)[4]) != null) {
					news.setReadCount((Long)(((Object[])obj)[4]));
				} else {
					news.setReadCount(Long.valueOf(0));
				}
				
				news.setPictrue((String)(((Object[])obj)[5]));
				
				pList.add(news);
			}
			this.cutTitle(pList, length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pList;
	}
	
	/**
	 * 获取新闻记录列表
	 * 
	 * @param newsType 新闻类型
	 * @return
	 */
	public List<PeInfoNews> getNewsInfoList(String newsType){
		return getNewsInfoList(newsType,25,6,1);
	}
	public boolean getFlagList(String str){
		if(this.getNewsContentList(str, 4,150,1,2).size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 获取新闻记录列表，包括新闻内容
	 *
	 * @param newsType 新闻类型
	 * @param length 页面显示记录长度
	 * @param count 页面显示记录条数
	 * @param order 排序方案（0：id，1：title，2：reportDate，3：readCount）
	 * @param cLength 新闻内容长度
	 * @return
	 */
	public List<PeInfoNews> getNewsContentList(String newsType,int tLength,int cLength,int count,int order){

		List nList = null;
		List<PeInfoNews> pList = new ArrayList<PeInfoNews>();
		String ord = "title";
		if(order == 0) {
			ord = "id";
		} else if(order == 1) {
			ord = "title";
		} else if(order == 2) {
			ord = "reportDate";
		} else if(order == 3) {
			ord = "readCount";
		}
		
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dcType = dcNews.createCriteria("peInfoNewsType","peInfoNewsType");
		dcType.add(Restrictions.eq("id", newsType));
		
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		
		dcNews.addOrder(Order.desc(ord));
		
		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("submitDate"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		infoProjectionList.add(Projections.property("pictrue"));
		infoProjectionList.add(Projections.property("note"));
		dcNews.setProjection(infoProjectionList);
		
		try {
			Page p = this.getGeneralService().getByPage(dcNews, count, 0);
			nList = p.getItems();
			String s = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(Object obj : nList) {
				PeInfoNews news = new PeInfoNews();
				news.setId((String)(((Object[])obj)[0]));
				news.setTitle((String)(((Object[])obj)[1]));
				if((((Object[])obj)[2]) != null) {
					news.setSubmitDate(sdf.parse(sdf.format(((Object[])obj)[2])));
				} else {
					news.setSubmitDate(null);
				}
				if((((Object[])obj)[3]) != null) {
					news.setReportDate(sdf.parse(sdf.format(((Object[])obj)[3])));
				} else {
					news.setReportDate(null);
				}
				if((((Object[])obj)[4]) != null) {
					news.setReadCount((Long)(((Object[])obj)[4]));
				} else {
					news.setReadCount(Long.valueOf(0));
				}
				
				news.setPictrue((String)(((Object[])obj)[5]));
				
				news.setNote((String)(((Object[])obj)[6]));
				pList.add(news);
			}
			
			this.cutTitle(pList, tLength);
			
			this.cutContent(pList, cLength);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pList;
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
	
	/**
	 * 截取列表中新闻标题长度
	 * 
	 * @param list
	 * @param length
	 */
	private void cutContent(List<PeInfoNews> list, int length) {
		for (PeInfoNews o : list) {
			this.newsContent(o, length);
		}
	}
	
	public String toIndex() {
		return "index";
	}
	/**
	 * 用于临时登陆
	 * @return
	 */
	public String toLogin() {
		return "tmplogin";
	}

	private void NoteTitle(PeBulletin peBulletin, int length) {
		String title = peBulletin.getTitle();
		if (title.length() > length) {
			title = title.substring(0, length) + "...";
			peBulletin.setTitle(title);
		}
	}

	private void NoteIsNew(PeBulletin peBulletin) {
		String title = peBulletin.getTitle();
		Date now = new Date();
		Long time = now.getTime() - peBulletin.getPublishDate().getTime();
		if (time <= 86400000) {
			// 暂定new的备注为new
//			peBulletin.setNote("new");
		}
	}

	/**
	 * 新闻搜索
	 * 
	 * @return
	 */
	public String search() {

		/**
		 * 设定number
		 */
		if (this.getNumber() <= 0) {
			this.setNumber(10);
		}

		/**
		 * 首先设定默认开始页为第一页
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 设定beain
		 */
		if (this.getCurPage() > 1) {

			int temp = (this.getCurPage() - 1) * this.getNumber();
			System.out.println(this.getCurPage() + "*" + this.getNumber() + "="
					+ temp);
			this.setBegin(temp);
		} else {
			this.setBegin(0);
		}
		
		Page infoPage = new Page(null, 0, 0, 0);
		// 设置查询条件
		DetachedCriteria dcNews = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcNews.createCriteria("peInfoNewsType",
				"peInfoNewsType");
		if (this.getType() != null && this.getType().length() != 0) {
			dctype.add(Restrictions.eq("id", this.getType()));
		}
		DetachedCriteria dcFlagNewsStatus = dcNews.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcNews.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		if (this.getSearchTitle() != null
				&& this.getSearchTitle().length() != 0) {
			dcNews.add(Restrictions.like("title", this.getSearchTitle(),
					MatchMode.ANYWHERE));
		}
		dcNews.addOrder(Order.desc("reportDate"));

		ProjectionList infoProjectionList = Projections.projectionList();
		infoProjectionList.add(Projections.property("id"));
		infoProjectionList.add(Projections.property("title"));
		infoProjectionList.add(Projections.property("reportDate"));
		infoProjectionList.add(Projections.property("readCount"));
		dcNews.setProjection(infoProjectionList);

		// 取出新闻
		try {
			infoPage = this.getGeneralService().getByPage(dcNews,
					this.getNumber(), this.getBegin());
			System.out.println("infoPage");
			System.out.println(infoPage);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PeInfoNews> peInfoNewsList = new ArrayList();
		List tempList = infoPage.getItems();
		int num = (this.getCurPage() - 1) * this.getNumber() + 1;
		// 将取出的新闻设置到list中
		for (int i = 0; i < tempList.size(); i++) {
			PeInfoNews instance = new PeInfoNews();
			instance.setId(((Object[]) tempList.get(i))[0].toString());
			instance.setTitle(this.replaceTitle(((Object[]) tempList.get(i))[1]
					.toString()));
			instance
					.setReportDate(((Timestamp) ((Object[]) tempList.get(i))[2]));
			instance.setReadCount(Long
					.parseLong(((Object[]) tempList.get(i))[3].toString()));
			this.listIsNew(instance);
			instance.setConfirmManagerId(num + ""); // 设置序号，暂保持到ConfirmManagerId中
			num++;
			peInfoNewsList.add(instance);
		}
		this.setNewsList(peInfoNewsList);

		/**
		 * 求出总页数
		 */
		double tempTotalPage = (double) infoPage.getTotalCount()
				/ (double) this.getNumber();
		/**
		 * 设置总页数
		 */
		if (tempTotalPage <= 1.0) {
			this.setTotalPage(1);
		} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
			this.setTotalPage(2);
		} else {
			if (infoPage.getTotalCount() % this.getNumber() > 0) {
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber()
						+ 1);
			} else
				this.setTotalPage(infoPage.getTotalCount() / this.getNumber());
		}
		// 设置新闻总数
		this.setTotalCount(infoPage.getTotalCount());

		if (this.getCurPage() < 2) {
			this.setPrePage(-1); // 当前页面为1时，页面不再显示上一页和首页

			if (this.getTotalPage() < 2) {
				this.setTotalPage(1);
				this.setNextPage(-1); // 当总页数为1的时候，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		} else {
			this.setPrePage(this.getCurPage() - 1);
			if (this.getCurPage() >= this.getTotalPage()) {
				this.setNextPage(-1); // 当前页如果是最大页码数，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		}

		return "newstest";
	}

	/**
	 * 将title中搜索的关键字高亮显示
	 * 
	 * @param str
	 * @return
	 */
	private String replaceTitle(String str) {
		String title = str.replaceAll(this.getSearchTitle(),
				"<font color=\"red\" >" + this.getSearchTitle() + "</font>");
		return title;
	}

	public String turnToJianzhang() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeJianzhang.class);
		dc.add(Restrictions.eq("flagActive", "1"));
		try {
			List<PeJianzhang> list = this.getGeneralService().getList(dc);
			if (list != null && !list.isEmpty()) {
				this.setJianzhang(list.get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "jianzhang";
	}
	
	public String  newstest() {
		DetachedCriteria dcWYDT = DetachedCriteria.forClass(PeInfoNews.class);
		DetachedCriteria dctype = dcWYDT.createCriteria("peInfoNewsType",
				"peInfoNewsType", DetachedCriteria.LEFT_JOIN);
		dctype.add(Restrictions.eq("name", "政策新闻"));
		DetachedCriteria dcFlagNewsStatus = dcWYDT.createCriteria(
				"enumConstByFlagNewsStatus", "enumConstByFlagNewsStatus");
		DetachedCriteria dcFlagIsactive = dcWYDT.createCriteria(
				"enumConstByFlagIsactive", "enumConstByFlagIsactive");
		dcFlagNewsStatus.add(Restrictions.eq("code", "1"));
		dcFlagIsactive.add(Restrictions.eq("code", "1"));
		dcWYDT.addOrder(Order.desc("reportDate"));
		try {
			this.setNewsList(this.getGeneralService().getList(dcWYDT));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "newstest";
	}

	public String allNews() {
		
		return "allnews";
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
		this.servletPath = "/entity/first/firstInfoNews";
	}

	public void setBean(PeInfoNews peInfoNews) {
		super.superSetBean(peInfoNews);

	}

	public PeInfoNews getBean() {
		return (PeInfoNews) super.superGetBean();
	}

	public List<PeInfoNews> getWydt() {
		return wydt;
	}

	public void setWydt(List<PeInfoNews> wydt) {
		this.wydt = wydt;
	}

	public List<PeInfoNews> getWjdt() {
		return wjdt;
	}

	public void setWjdt(List<PeInfoNews> wjdt) {
		this.wjdt = wjdt;
	}

	public List<PeInfoNews> getZcfg() {
		return zcfg;
	}

	public void setZcfg(List<PeInfoNews> zcfg) {
		this.zcfg = zcfg;
	}

	public PeInfoNews getPeInfoNews() {
		return peInfoNews;
	}

	public void setPeInfoNews(PeInfoNews peInfoNews) {
		this.peInfoNews = peInfoNews;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public List<PeInfoNews> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<PeInfoNews> newsList) {
		this.newsList = newsList;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	public PeJianzhang getJianzhang() {
		return jianzhang;
	}

	public void setJianzhang(PeJianzhang jianzhang) {
		this.jianzhang = jianzhang;
	}

	public List<PeBulletin> getBulletin() {
		return Bulletin;
	}

	public void setBulletin(List<PeBulletin> bulletin) {
		Bulletin = bulletin;
	}

	public PeBulletin getPeBulletin() {
		return peBulletin;
	}

	public void setPeBulletin(PeBulletin peBulletin) {
		this.peBulletin = peBulletin;
	}

	public List<PeInfoNews> getTpnewsList() {
		return tpnewsList;
	}

	public void setTpnewsList(List<PeInfoNews> tpnewsList) {
		this.tpnewsList = tpnewsList;
	}

	public List<PeInfoNews> getBzfc() {
		return bzfc;
	}

	public List<PeInfoNews> getJsfc() {
		return jsfc;
	}

	public void setBzfc(List<PeInfoNews> bzfc) {
		this.bzfc = bzfc;
	}

	public void setJsfc(List<PeInfoNews> jsfc) {
		this.jsfc = jsfc;
	}

	public List<PeInfoNews> getPxdt() {
		return pxdt;
	}

	public void setPxdt(List<PeInfoNews> pxdt) {
		this.pxdt = pxdt;
	}
	
}
