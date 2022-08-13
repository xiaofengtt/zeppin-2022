/** 
 * Project Name:CETV_TEST 
 * File Name:UserTextbookcapterDegreeDAO.java 
 * Package Name:cn.zeppin.dao.imp 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */
package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IUserTextbookcapterDegreeDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.UserTextbookcapterDegree;

/**
 * ClassName: UserTextbookcapterDegreeDAO <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2014年10月14日 下午3:29:52 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.7
 */
public class UserTextbookcapterDegreeDAO extends HibernateTemplateDAO<UserTextbookcapterDegree, Long> implements IUserTextbookcapterDegreeDAO {
	
	
	/**
	 * 添加
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:21 <br/> 
	 * @param utbDegree
	 */
	public void addUserTextbookcapterDegree(UserTextbookcapterDegree utbDegree){
		this.save(utbDegree);
	}
	
	/**
	 * 计算数量
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:32 <br/> 
	 * @param map
	 * @return
	 */
	public int getUserTextbookcapterDegreeCount(Map<String, Object> map){
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from UserTextbookcapterDegree utbd where 1=1 ");
		if(map.get("user.id")!=null){
			sb.append(" and utbd.user=").append(map.get("user.id"));
		}
		
		if(map.get("textbook.id")!=null){
			sb.append(" and utbd.textbookCapter.textbook=").append(map.get("textbook.id"));
		}
		
		return ((Long) this.getResultByHQL(sb.toString())).intValue();
	}

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:46 <br/> 
	 * @param map
	 * @param sort
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<UserTextbookcapterDegree> getUserTextbookcapterDegreeByMap(Map<String, Object> map, String sorts, int offset, int length){
		
		StringBuilder sb = new StringBuilder();
		sb.append("from UserTextbookcapterDegree utbd where 1=1 ");
		if(map.get("user.id")!=null){
			sb.append(" and utbd.user=").append(map.get("user.id"));
		}
		
		if(map.get("textbook.id")!=null){
			sb.append(" and utbd.textbookCapter.textbook=").append(map.get("textbook.id"));
		}
		
		// 排序
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray) {
				sb.append(comma);
				sb.append("utbd.").append(sort);
				comma = ",";
			}
		}
		else{
			sb.append(" order by utbd.recordtime desc");
		}
		
		return this.getByHQL(sb.toString(), offset, length);
	}
	
	/**
	 * by id 
	 * @author Administrator
	 * @date: 2014年10月23日 下午3:01:57 <br/> 
	 * @param id
	 * @return
	 */
	public UserTextbookcapterDegree getUserTextbookcapterDegreeById(Long id){
		return this.get(id);
	}
	
}
