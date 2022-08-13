/**
 * 
 */
package cn.zeppin.project.chinamobile.media.web.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.CommodityDisplay;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICommodityDAO;
import cn.zeppin.project.chinamobile.media.web.dao.api.ICommodityDisplayDAO;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityService;
import cn.zeppin.project.chinamobile.media.web.service.base.BaseService;
import cn.zeppin.project.chinamobile.media.web.vo.CommodityDisplayVO;

/**
 * @author Clark.R 2016年3月29日
 *
 */
@Service
public class CommodityService extends BaseService implements ICommodityService {
	
	@Autowired
	private ICommodityDAO commodityDAO;
	
	@Autowired
	private ICommodityDisplayDAO commodityDisplayDAO;

	public Commodity insert(Commodity commodity) {
		return this.commodityDAO.insert(commodity);
	}

	public Commodity delete(Commodity commodity) {
		return this.commodityDAO.delete(commodity);
	}

	public Commodity update(Commodity commodity) {
		return this.commodityDAO.update(commodity);
	}

	public Commodity get(String id) {
		return this.commodityDAO.get(id);
	}

	public List<Commodity> getAll() {
		return this.commodityDAO.getAll();
	}

	@Override
	public Integer getCountByParams(HashMap<String, String> searchMap) {
		// TODO Auto-generated method stub
		return this.commodityDAO.getCountByParams(searchMap);
	}

	@Override
	public List<Commodity> getListByParams(HashMap<String, String> searchMap,
			String sorts, Integer offset, Integer length) {
		// TODO Auto-generated method stub
		return this.commodityDAO.getListByParams(searchMap, sorts, offset, length);
	}

	@Override
	public Integer getChildCount(String id) {
		// TODO Auto-generated method stub
		return this.commodityDAO.getChildCount(id);
	}

	@Override
	public List<Entity> getListByParams(Commodity commodity, String sorts,
			Integer offset, Integer length, @SuppressWarnings("rawtypes") Class resultClass) {
		// TODO Auto-generated method stub
		return this.commodityDAO.getListByParams(commodity, sorts, offset, length, resultClass);
	}

	@Override
	public Integer getCountByParams(Commodity commodity) {
		// TODO Auto-generated method stub
		return this.commodityDAO.getCountByParams(commodity);
	}

	@Override
	public Commodity insert(Commodity commodity, String[] displays,
			String userId) {
		// TODO Auto-generated method stub
		Commodity com = null;
		try {
			/*
			 * 分析360展示图片文件
			 */
			for(String display : displays){
				com = this.commodityDAO.insert(commodity);
				CommodityDisplay cd = new CommodityDisplay();
				cd.setCommodity(com.getId());
				cd.setCreator(userId);
				cd.setCreatetime(new Timestamp((new Date()).getTime()));
				String[] strs = display.split("_");
				cd.setDisplayImage(strs[0]);
				cd.setDisplayIndex(Integer.parseInt(strs[1]));
				this.commodityDisplayDAO.insert(cd);
			}
			
			
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		return com;
	}

	@Override
	public Commodity update(Commodity commodity, String[] displays, String userId) {
		// TODO Auto-generated method stub
		Commodity com = null;
		try {
			com = this.commodityDAO.update(commodity);
			
			/*
			 * 先删除原来的记录，再增加新的记录,如果没有记录，直接添加
			 */
			List<Entity> list = this.commodityDisplayDAO.getListByCommodity(commodity, CommodityDisplayVO.class);
			if(list != null && list.size() > 0){
				for(Entity entity:list){
					CommodityDisplayVO cdvo = (CommodityDisplayVO)entity;
					
					this.commodityDisplayDAO.delete(this.commodityDisplayDAO.get(cdvo.getId()));
					
				}
			}
			/*
			 * 分析360展示图片文件
			 */
			for(String display : displays){
				CommodityDisplay cd = new CommodityDisplay();
				cd.setCommodity(com.getId());
				cd.setCreator(userId);
				cd.setCreatetime(new Timestamp((new Date()).getTime()));
				String[] strs = display.split("_");
				cd.setDisplayImage(strs[0]);
				cd.setDisplayIndex(Integer.parseInt(strs[1]));
				this.commodityDisplayDAO.insert(cd);
			}
			
			
		} catch (Exception e) {
			
			// TODO: handle exception
			e.printStackTrace();
		}
		return com;
	}

}
