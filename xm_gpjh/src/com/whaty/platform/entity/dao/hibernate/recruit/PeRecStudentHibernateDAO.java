package com.whaty.platform.entity.dao.hibernate.recruit;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PeRecStudent;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PeRecStudentDAO;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

/**
 * Data access object (DAO) for domain model class PeRecStudent.
 * 
 * @see com.whaty.platform.entity.bean.recruit.PeRecStudent
 * @author MyEclipse Persistence Tools
 */

public class PeRecStudentHibernateDAO extends AbstractEntityHibernateDao<PeRecStudent, String> implements PeRecStudentDAO {

	public PeRecStudentHibernateDAO() {
		this.entityClass=PeRecStudent.class;
		this.table=entityClass.getName();
		this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}

	public List getCount(String sql) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		List list=session.createSQLQuery(sql).list();
		session.clear();
		session.close();
	 	return list;
	} 
	
	
	/**
	 *根据学生id，状态更新学生信息，学生录取。
	 */
	public int updateRecStudent(final String id,final String status,final String studyMajroMark,final String regNo)throws EntityException{
		
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						
						int count = 0;
						String sql = "update " + table + " n set n.regNo = ?,n.status = ? ,n.studyMajroMark= ? where n.id = ?";
						Query query = s.createQuery(sql);
						query.setString(0,regNo);
						query.setString(1,status);
						query.setString(2,studyMajroMark);
						query.setString(3,id);
						
						count = query.executeUpdate();
						return count;
					}
				});
	}
	
	
 	/**
	 * 根据站点id，录取状态获得 考试学生列表，总分大于等于站点分数，单科成绩大于等于单科分数线
	 */
	public Page getExamRecStudentList(final String siteId,final String status,final int pageSize,final int start,final String sort){
		return (Page)getHibernateTemplate().execute(new HibernateCallback(){
 				public Object doInHibernate(Session s){
 					String where ="";
 					String sta = "";
 					String order  = "";
 					if(siteId !=null && !"".equals(siteId))
 						where = "  and pesite.id = ? ";
 					if("7".equals(status)){
 						sta = " and  pestu.status ='7' ";
 					}else if("5".equals(status)){
 						sta = " and  pestu.status ='5' ";
 					}else if("6".equals(status)){
 						sta = " and  pestu.status ='6' ";
 					}else{
 						sta = " and  pestu.status in (5,6,7) ";
 					}
 					if(sort != null && !"".equals(sort)){
 						order = " order by pestu."+sort;
 					}
 					
 					String sql = "select distinct pestu.id,pestu.name,pestu.gender,pestu.card_type,pestu.card_no, "+
 	              " pestu.folk,pestu.zzmm,pestu.study_major1,pestu.recruit_major_mark,pestu.status,pemajor.name as pemajor_name "+
 	             " from "+
 	            "  (select scoreinfo.id "+
 	                   "         from (       "+
 	                 "           select st.id as id, sum(pr.score) as totalscore, site.scoreline  "+
 	                "               from pr_rec_exam_stu_course pr,  "+
 	                "                pr_rec_exam_stu        stu, "+
 	                "                         pe_rec_student         st, "+
 	                "                       pr_rec_plan_major_site site  "+
 	                "               where site.id = st.fk_rec_major_site_id "+
 	               "                   and st.id = stu.fk_rec_stu_id  "+
 	               "               and stu.id = pr.fk_rec_exam_stu_id  "+
 	               "                group by st.id, site.scoreline) scoreinfo "+
 	                "                ,pe_rec_student student,pr_rec_plan_major_site si,pe_site site  "+
 	                 "       where scoreinfo.totalscore>=scoreinfo.scoreline  "+
 	                  "     and student.id=scoreinfo.id and student.fk_rec_major_site_id=si.id and si.fk_site_id = site.id and student.noexam='0') sitepass,"+
 	                 "      (" +
// 	                 "select st.id "+
// 	              "  from pe_rec_student st, pr_rec_exam_stu_course pe, pr_rec_exam_stu stu, "+
// 	             "   pr_rec_exam_course_time ti "+
// 	            "   where st.id = stu.fk_rec_stu_id "+
// 	            "     and stu.id = pe.fk_rec_exam_stu_id and pe.fk_rec_exam_course_time_id = ti.id and  pe.score>=nvl(ti.scoreline,0)" +
// 	           
 	                " select  m.id,m.talcount,n.talcount from (select st.id, count(*) as talcount "+
 	               " from pe_rec_student          st,                                            "+
 	               " pr_rec_exam_stu_course  pe,                                                 "+
 	               " pr_rec_exam_stu         stu,                                                "+
 	               " pr_rec_exam_course_time ti                                                  "+
 	               " where st.id = stu.fk_rec_stu_id                                             "+
 	               " and stu.id = pe.fk_rec_exam_stu_id                                          "+
 	               " and pe.fk_rec_exam_course_time_id = ti.id                                   "+
 	               " group by st.id) m,                                                          "+
 	               " (select st.id,count(*) as talcount                                          "+
 	               " from pe_rec_student          st,                                            "+
 	               " pr_rec_exam_stu_course  pe,                                                 "+
 	               " pr_rec_exam_stu         stu,                                                "+
 	               " pr_rec_exam_course_time ti                                                  "+
 	               " where st.id = stu.fk_rec_stu_id                                             "+
 	               " and stu.id = pe.fk_rec_exam_stu_id                                          "+
 	               " and pe.fk_rec_exam_course_time_id = ti.id                                   "+
 	               " and pe.score >= nvl(ti.scoreline, 0)                                        "+
 	               " group by st.id) n                                                           "+
 	               " where                                                                       "+
 	               " m.talcount=n.talcount                                                       "+
 	               " and                                                                         "+
 	               " m.id=n.id                                                                   "+
 	            ") coursepass, "+
 	             "    pe_rec_student pestu, pr_rec_plan_major_site prsite,pe_site pesite,pe_major pemajor,pr_rec_plan_major_edutype pre "+
 	            "      where prsite.fk_rec_plan_major_edutype_id=pre.id and pre.fk_major_id=pemajor.id and sitepass.id=coursepass.id and pestu.id=sitepass.id and pestu.fk_rec_major_site_id=prsite.id and prsite.fk_site_id=pesite.id  "+sta + where +order;
 					
// 					int c = sql.indexOf("from");
 					String sql2 = "select count(*) from ("+sql+")";
 					Query querycount = s.createSQLQuery(sql2);
 					
 					
 					Query query = s.createSQLQuery(sql);
 					if(siteId !=null && !"".equals(siteId)){
 						query.setString(0,siteId);
 						querycount.setString(0,siteId);
 					}
 							
 					int count = ((BigDecimal)querycount.list().get(0)).intValue();
 					List list = query.setFirstResult(start).setMaxResults(pageSize).list();
 					Page page = new Page(list,count,pageSize,start);
 					return page;
 				}
 							
		});
	}
}