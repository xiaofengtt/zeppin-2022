package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.hibernate.Session;

import com.whaty.platform.entity.bean.PrRecExamStu;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrRecExamStuDAO;

public class PrRecExamStuHibernateDAO extends AbstractEntityHibernateDao<PrRecExamStu,String>
		implements PrRecExamStuDAO {
	public PrRecExamStuHibernateDAO(){
		this.entityClass=PrRecExamStu.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	public List getRoomNo(String pesitemanager){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		String sql = "select distinct room_no room_no_ from pr_rec_exam_stu es,Pe_Rec_Student s,Pe_Sitemanager site,Pe_Rec_Exam pici where es.fk_rec_stu_id=s.id and s.fk_inputter_id=site.id and es.fk_rec_exam_id=pici.id and pici.flag_active=1 and es.room_no is not null and site.name='"+pesitemanager+"' order by room_no";
		List list=session.createSQLQuery(sql).list();
		session.clear();
		session.close();
	 	return list;
	}
}
