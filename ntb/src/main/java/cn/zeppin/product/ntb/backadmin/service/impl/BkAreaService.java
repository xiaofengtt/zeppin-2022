/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IBkAreaDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class BkAreaService extends BaseService implements IBkAreaService {

	@Autowired
	private IBkAreaDAO  bkAreaDAO;

	
	@Override
	public BkArea get(String uuid) {
		return bkAreaDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public BkArea insert(BkArea area) {
		return bkAreaDAO.insert(area);
	}

	@Override
	public BkArea delete(BkArea area) {
		return bkAreaDAO.delete(area);
	}

	@Override
	public BkArea update(BkArea area) {
		return bkAreaDAO.update(area);
	}

	/**
	 * 获取所有功能信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return bkAreaDAO.getAll(resultClass);
	}
	
	/**
	 * 根据参数结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return bkAreaDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return bkAreaDAO.getCount(inputParams);
	}
	
	/**
	 * 获取地区全称
	 * @param inputParams
	 * @return 
	 */
	@Override
	public List<String> getFullName(String uuid) {
		
		ArrayList<String> nameList = new ArrayList<String>();
		
		BkArea area = bkAreaDAO.get(uuid);
		if(area != null){
			nameList.add(area.getName());
			
			while(area.getLevel() > 1){
				area = bkAreaDAO.get(area.getPid());
				nameList.add(0, area.getName());
			}
		}
		
		return nameList;
	}
}
