package com.whaty.platform.entity.dao.hibernate.priority;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PeSitemanager;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.priority.PeSitemanagerDao;

public class PeSitemanagerHibernateDao extends AbstractEntityHibernateDao<PeSitemanager,String>
		implements PeSitemanagerDao {
	public PeSitemanagerHibernateDao(){
		this.entityClass=PeSitemanager.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	public Integer getTotalCount(final DetachedCriteria detachedCriteria){
		return (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
						public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria.getExecutableCriteria(session);
						
						CriteriaImpl impl = (CriteriaImpl) criteria;
						 //先把Projection和OrderBy条件取出来,清空两者来执行Count操作
                     Projection projection = impl.getProjection();
                     
						Integer totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult());
						
						 // 将之前的Projection和OrderBy条件重新设回去
	                        criteria.setProjection(projection);
	                        if (projection == null) {
	                            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
	                        }
						return totalCount;
					}
				}, true);
	}
	
	public List listInfo(String siteid, String sitename, String exampici, String sort, String dir){
		
		Session session = getHibernateTemplate().getSessionFactory().openSession();
//		String hql="select site.id,site.name,count(stu.peSitemanager.id) from PrRecExamStu as es inner join es.peRecExam as exam inner join es.peRecStudent as stu right join stu.peSitemanager as site where exam.flagActive=1 group by site.id,site.name";
//		String hql="select site.id,site.name,count(stu.peSitemanager.id) from PrRecExamStu as es inner join es.peRecStudent as stu right join stu.peSitemanager as site inner join es.peRecExam as exam with exam.flagActive=1 group by site.id,site.name";

		String sql="";
		
		sql +="select site.id siteid, site.name sitename, count(stu_.fk_inputter_id) statistics from pe_sitemanager site    left join (       select stu.fk_inputter_id           from pe_rec_student stu           inner join pr_rec_exam_stu es on stu.id = es.fk_rec_stu_id           inner join pe_rec_exam exam on exam.id = es.fk_rec_exam_id ";
		
		if(exampici != null && !"".equals(exampici)){
			sql +=" where exam.name like '%"+exampici+"%' ";
		}
		
		sql += " ) stu_ on site.id = stu_.fk_inputter_id where 1=1";

		if(siteid != null) {
			sql += " and site.id like '%" + siteid + "%'";
		}
		
		if(sitename!=null && !"".equals(sitename)){
			sql += " and site.name like '%" + sitename + "%'";
		}
		
//		if(exampici != null &&!"".equals(exampici)){
//			sql += " and "
//		}
		
		sql += " group by site.id, site.name";
		
		if (sort != null) {
			sql += " order by " + sort;

			if (dir != null && dir.equalsIgnoreCase("desc"))
				sql += " desc";
		}
					

//		String sql="select site.id, site.name, count(stu_.fk_inputter_id) from pe_sitemanager site    left join (       select stu.fk_inputter_id           from pe_rec_student stu           inner join pr_rec_exam_stu es on stu.id = es.fk_rec_stu_id           inner join pe_rec_exam exam on exam.id = es.fk_rec_exam_id           where exam.flag_active = '1'    ) stu_ on site.id = stu_.fk_inputter_id    group by site.id, site.name";
//		String id="003";
//		String sql="select site.id, site.name, count(stu_.fk_inputter_id) from pe_sitemanager site    left join (       select stu.fk_inputter_id           from pe_rec_student stu           inner join pr_rec_exam_stu es on stu.id = es.fk_rec_stu_id           inner join pe_rec_exam exam on exam.id = es.fk_rec_exam_id           where exam.flag_active = '1'    ) stu_ on site.id = stu_.fk_inputter_id  where site.id like '"+id+"'   group by site.id, site.name";
		List list=session.createSQLQuery(sql).list();
		session.clear();
		session.close();
	 	return list;
		
	}
}
