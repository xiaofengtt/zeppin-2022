package com.whaty.platform.entity.web.action.first;

import java.sql.Timestamp;
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
 * 缃戠珯鍓嶅彴鍏憡鍙戝竷
 * @author 鏉庡啺
 *
 */
public class FirstBulletinAction extends MyBaseAction  {
	
	private PeBulletin bulletin;
	private List<PeBulletin> pebulletin;
	private String searchTitle; 
	//鍒嗛〉鍙傛暟
	private int begin; // 鍒楄〃寮�濮嬬殑浣嶇疆
	private int number; // 姣忛〉瑕佹樉绀虹殑淇℃伅涓暟
	private int curPage; // 褰撳墠椤�
	private int nextPage; // 涓嬩竴椤�
	private int prePage; // 涓婁竴椤�
	private int totalPage; // 鎬婚〉鏁�
	private int totalCount; // 鏂伴椈鎬绘暟
	

	       
	public PeBulletin getBulletin() {
		return bulletin;
	}


	public void setBulletin(PeBulletin bulletin) {
		this.bulletin = bulletin;  
	}


	public String viewDetail(){
		try {
		bulletin = (PeBulletin)this.getGeneralService().getPeBulletin(this.getBean().getId());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "bulletin";
	}
	
	public String allBulletin(){
	    return "allbulletin";	
	}
	
	
	public String toBulletinList(){
		/**
		 * 璁惧畾number
		 */
		if (this.getNumber() <= 0) {
			this.setNumber(10);
		}
		/**
		 * 棣栧厛璁惧畾榛樿寮�濮嬮〉涓虹涓�椤�
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 璁惧畾beain
		 */
		if (this.getCurPage() > 1) {

			int temp = (this.getCurPage() - 1) * this.getNumber();
			this.setBegin(temp);
		} else {
			this.setBegin(0);
		}

		Page infoPage = new Page(null, 0, 0, 0);
		DetachedCriteria bulletindc = DetachedCriteria.forClass(PeBulletin.class);
		bulletindc.createCriteria("enumConstByFlagIstop", "enumConstByFlagIstop");
		DetachedCriteria FlagIsvalid = bulletindc.createCriteria(
				"enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		bulletindc.add(Restrictions
				.like("scopeString", "index", MatchMode.ANYWHERE));
		FlagIsvalid.add(Restrictions.eq("code", "1"));
		if(searchTitle!=null && this.getSearchTitle().length() != 0){
			bulletindc.add(Restrictions.like("title", searchTitle,MatchMode.ANYWHERE));
		}
		bulletindc.addOrder(Order.desc("enumConstByFlagIstop.code")).addOrder(
				Order.desc("publishDate"));
		try {
			infoPage = this.getGeneralService().getByPage(bulletindc,
					this.getNumber(), this.getBegin());
		} catch (EntityException e) {
			e.printStackTrace();
		}
		List<PeBulletin> peBulletinList = new ArrayList();
		List tempList = infoPage.getItems();
		int num = (this.getCurPage() - 1) * this.getNumber() + 1;
		// 将取出的公告设置到list中
		for (int i = 0; i < tempList.size(); i++) {
			peBulletinList.add((PeBulletin)tempList.get(i));
		}
		this.setPebulletin(peBulletinList);
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
		// 设置公告总数
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
		return "toBulletinList";
	}
	
	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBulletin.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/first/firstBulletin";
	}
	
	public PeBulletin getBean(){
		return (PeBulletin)superGetBean();
	}
	
	public void setBean(PeBulletin bulletin){
		super.superSetBean(bulletin);
	}


	public List<PeBulletin> getPebulletin() {
		return pebulletin;
	}


	public void setPebulletin(List<PeBulletin> pebulletin) {
		this.pebulletin = pebulletin;
	}


	public int getBegin() {
		return begin;
	}


	public int getNumber() {
		return number;
	}


	public int getCurPage() {
		return curPage;
	}


	public int getNextPage() {
		return nextPage;
	}


	public int getPrePage() {
		return prePage;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setBegin(int begin) {
		this.begin = begin;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}


	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
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
}
