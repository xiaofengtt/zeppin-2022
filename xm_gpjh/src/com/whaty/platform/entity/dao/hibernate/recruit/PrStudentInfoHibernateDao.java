package com.whaty.platform.entity.dao.hibernate.recruit;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.whaty.platform.entity.bean.PrStudentInfo;
import com.whaty.platform.entity.dao.hibernate.AbstractEntityHibernateDao;
import com.whaty.platform.entity.dao.recruit.PrStudentInfoDao;

public class PrStudentInfoHibernateDao extends AbstractEntityHibernateDao<PrStudentInfo,String>
		implements PrStudentInfoDao {

	public PrStudentInfoHibernateDao() {
			this.entityClass=PrStudentInfo.class;
			this.table=entityClass.getName();
			this.DELETE_BY_IDS="delete from "+table+" where id in(:ids)";
	}
	
	/**
	 * 根据学号查找学生
	 */
	public PrStudentInfo getByRegNo(String regNo){
		List list = this.getHibernateTemplate().find("from PrStudentInfo st where st.peStudent.regNo = ?",regNo);
		if(list != null && list.size()>0){
			return (PrStudentInfo)list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据招生表中学生id
	 */
	public PrStudentInfo getByRecId(String id){
		List list = this.getHibernateTemplate().find("from PrStudentInfo st where st.peStudent.recruitStuId = ?",id);
		if(list != null && list.size()>0){
			return (PrStudentInfo)list.get(0);
		}else{
			return null;
		}
	}
	
	
	/**
 	 *  获得当前最大学号（除8和9开头）
 	 */
 	public String getMaxStuRegNo(){
 		return (String) getHibernateTemplate().execute(new HibernateCallback(){
 				public Object doInHibernate(Session s){
 					
 					String sql = "select n.regNo from PeStudent n  where n.regNo is not null and n.regNo>='000000' and n.regNo<'800000' and length(n.regNo)='6' order by n.regNo desc";
 					Query query = s.createQuery(sql);
 					List list = query.setMaxResults(1).list();
 					if(list != null && list.size()>0){
 						return (String)list.get(0);
 					}else{
 						return null;
 					}
 				}
 		});
 	}
		
}
