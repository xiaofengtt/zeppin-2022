/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICommodityDAO;
import cn.zeppin.project.chinamobile.media.web.dao.base.BaseDAO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Repository
public class CommodityDAO extends BaseDAO<Commodity, String> implements ICommodityDAO {


	/**
	 * 通过参数取count
	 */
	public Integer getCountByParams(HashMap<String,String> searchMap){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Commodity where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and parent='").append(searchMap.get("parent")+"'");
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status='").append(searchMap.get("status")+"'");
		}
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
	
	/**
	 * 通过参数取列表
	 */
	public List<Commodity> getListByParams(HashMap<String,String> searchMap, String sorts, Integer offset, Integer length){
		StringBuilder sb = new StringBuilder();
		sb.append("from Commodity where 1=1");
		if (searchMap.get("parent") != null && !searchMap.get("parent").equals("")){
			sb.append(" and parent='").append(searchMap.get("parent")+"'");
		}
		if (searchMap.get("level") != null && !searchMap.get("level").equals("")){
			sb.append(" and level=").append(searchMap.get("level"));
		}
		if (searchMap.get("name") != null && !searchMap.get("name").equals("")){
			sb.append(" and name like '%").append(searchMap.get("name")).append("%'");
		}
		if (searchMap.get("status") != null && !searchMap.get("status").equals("")){
			sb.append(" and status='").append(searchMap.get("status")+"'");
		}
		
		if (sorts != null && sorts.length() > 0) {
			String[] sortArray = sorts.split(",");
			sb.append(" order by ");
			String comma = "";
			for (String sort : sortArray){
				sb.append(comma);
				sb.append(" ").append(sort);
				comma = ",";
			}
		}
		if(offset!=null && length!=null){
			return this.getByHQL(sb.toString(), offset, length);
		}else{
			return this.getByHQL(sb.toString());
		}
	}

	/**
	 * 获取子分类数量
	 */
	public Integer getChildCount(String id){
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Commodity where parent='").append(id+"'");
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}

	@Override
	public List<Entity> getListByParams(Commodity commodity, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		sb.append("select com.id as id,com.name as name,com.cover as cover,com.price as price,com.original_price as originalPrice,su.name as creator,com.createtime as createtime from commodity com,sys_user su where 1=1 and com.creator=su.id");
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			sb.append(" and com.id='"+commodity.getId()+"'");
		}
		if(commodity.getName() != null && !"".equals(commodity.getName())){
			sb.append(" and com.name like'%"+commodity.getName()+"%'");
		}
		if(commodity.getPrice() != null && commodity.getPrice().equals(BigDecimal.ZERO)){
			sb.append(" and com.price="+commodity.getPrice());
		}
		if(commodity.getOriginalPrice() != null && commodity.getOriginalPrice().equals(BigDecimal.ZERO)){
			sb.append(" and com.original_price="+commodity.getOriginalPrice());
		}
		if(commodity.getStatus() != null && !"".equals(commodity.getStatus())){
			sb.append(" and com.status='"+commodity.getStatus()+"'");
		}
		
		if (sorts != null && sorts.length() > 0) {
			sb.append(" order by com." + sorts);
		}
		
		List<Entity> results = this.getBySQL(sb.toString(), offset, length, null, resultClass);
		
		return results;
	}

	@Override
	public Integer getCountByParams(Commodity commodity) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from Commodity com,User su where 1=1 and com.creator=su.id");
		
		if(commodity.getId() != null && !"".equals(commodity.getId())){
			sb.append(" and com.id='"+commodity.getId()+"'");
		}
		if(commodity.getName() != null && !"".equals(commodity.getName())){
			sb.append(" and com.name like'%"+commodity.getName()+"%'");
		}
		if(commodity.getPrice() != null && commodity.getPrice().equals(BigDecimal.ZERO)){
			sb.append(" and com.price="+commodity.getPrice());
		}
		if(commodity.getOriginalPrice() != null && commodity.getOriginalPrice().equals(BigDecimal.ZERO)){
			sb.append(" and com.original_price="+commodity.getOriginalPrice());
		}
		if(commodity.getStatus() != null && !"".equals(commodity.getStatus())){
			sb.append(" and com.status='"+commodity.getStatus()+"'");
		}
		
		
		return Integer.valueOf(this.getResultByHQL(sb.toString()).toString());
	}
}
