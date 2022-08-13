package cn.zeppin.dao.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IMedalDAO;
import cn.zeppin.dao.base.HibernateTemplateDAO;
import cn.zeppin.entity.Medal;

public class MedalDAO extends HibernateTemplateDAO<Medal,Integer> implements IMedalDAO {
	
	/**
	 * 添加
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:06:41 <br/>
	 * @param medal
	 */
	public void addMedal(Medal medal){
		this.save(medal);
	}

	/**
	 * 获取个数
	 * 
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:37:20 <br/>
	 * @param map
	 * @return
	 */
	public int getMedalCount(Map<String, Object> map){
		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Medal m where 1=1 ");
		
		if(map.get("type")!=null){
			sb.append(" and m.type=").append(map.get("type"));
		}
		
		if(map.get("user.id")!=null){
			sb.append(" and m.user=").append(map.get("user.id"));
		}
		
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

	/**
	 * 获取列表
	 * @author Administrator
	 * @date: 2014年11月4日 上午11:38:26 <br/> 
	 * @param map
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Medal> getMedals(Map<String, Object> map, int offset, int length){
		
		StringBuilder sb = new StringBuilder();
		sb.append("from Medal m where 1=1 ");
		
		if(map.get("type")!=null){
			sb.append(" and m.type=").append(map.get("type"));
		}
		
		if(map.get("user.id")!=null){
			sb.append(" and m.user=").append(map.get("user.id"));
		}
		
		return this.getByHQL(sb.toString(), offset, length);
	}
	
}
