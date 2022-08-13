package com.whaty.platform.entity.dao.hibernate.recruit;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PrRecExamStuCourse;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrRecExamStuCourseDAO;

public class PrRecExamStuCourseHibernateDAO extends AbstractEntityHibernateDao<PrRecExamStuCourse,String>
		implements PrRecExamStuCourseDAO {

	public PrRecExamStuCourseHibernateDAO(){
		this.entityClass=PrRecExamStuCourse.class;
    	this.table=entityClass.getName();
    	this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	
	/**
	 * 从学生表PeRecStudent和关系表PR_REC_COURSE_MAJOR_EDUTYPE中获取：学生id和课程id
	 */
	public List getStudentAndCourseId(){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
//		String hql="select m.name,e.name from PeMajor m,PeEdutype e where m.id=1";
//		String hql="select s.id,r.peRecExamcourse.id from PeRecStudent s,PrRecCourseMajorEdutype r where s.eduType=r.peEdutype.id and s.studyMajor1=r.peMajor.id";
		String hql="select s.id, r.peRecExamcourse.id,r.peRecExamcourse.name" +
				" from PeRecStudent s, PrRecCourseMajorEdutype r, PrRecExamStu es, PeRecExam pici" +
				" where s.prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.id = r.peEdutype.id" +
				" and s.prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.id = r.peMajor.id" +
				" and s.id = es.peRecStudent.id" +
				" and s.status = '5'   " +
				" and es.peRecExam.id = pici.id" +
				" and pici.flagActive = 1" +
				" order by s.id";
		List list = session.createQuery(hql).list();
		session.clear();
  		session.close();
	 	return list;
//		 try {
//			 	List list = session.createQuery(hql).list();
//			 	return list;
//			   } catch (Exception e){
//				   
//			   		} 
//			   	 finally {
//			   		 session.clear();
//			   		 session.close();
//			   }
 

	}
	
	public List getPaperBags(final DetachedCriteria detachedCriteria){
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);
				return criteria.list();
			}
		});
	}
	
	public List getRoomNos(String sitemanagerId,String courseId){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		String sql="select distinct es.room_no from pr_rec_exam_stu_course  esc,pr_rec_exam_stu es, pe_rec_student s, pe_sitemanager site, pe_rec_exam pici, pr_rec_exam_course_time ect, pe_rec_examcourse ec where esc.fk_rec_exam_stu_id = es.id   and es.fk_rec_stu_id = s.id   and s.fk_inputter_id = site.id   and esc.fk_rec_exam_course_time_id = ect.id   and ect.fk_rec_examcourse_id = ec.id   and es.fk_rec_exam_id = pici.id   and pici.flag_active = 1   and ec.id = '"+courseId+"'   and site.id = '"+sitemanagerId+"'";
		List list=session.createSQLQuery(sql).list();
		session.clear();
		session.close();
	 	return list;
	}
	
	public void removeActiveStudents(){
//		Session session = getHibernateTemplate().getSessionFactory().openSession();
//		
//		return 0;
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		String sql="delete from pr_rec_exam_stu_course esc where esc.fk_rec_exam_stu_id in(select es.id from pr_rec_exam_stu es,pe_rec_exam pici where es.fk_rec_exam_id=pici.id and pici.flag_active=1)";
		session.createSQLQuery(sql).executeUpdate();
		session.clear();
		session.close();
//		Session session = getHibernateTemplate().getSessionFactory().openSession();
		
//		return (Integer) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					public Object doInHibernate(Session session) {
//
//						Integer i = new Integer(0);
//						String sql="delete from pr_rec_exam_stu_course esc where esc.fk_rec_exam_stu_id in(select es.id from pr_rec_exam_stu es,pe_rec_exam pici where es.fk_rec_exam_id=pici.id and pici.flag_active='1')";
//						Query query = session.createSQLQuery(sql);
//
//						try {
//							i = query.executeUpdate();
//						} catch (Exception e) {
//							
//
//						}
//
//						return i;
//					}
//				});
	}
	
}