/**
 * Copyright (c) 2014, Zeppin All Rights Reserved.
 * cn.zeppin.service.imp
 * ItemTypeService
 */
package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IItemTypeDAO;
import cn.zeppin.dao.api.ISubjectItemTypeDAO;
import cn.zeppin.entity.ItemType;
import cn.zeppin.service.api.IItemTypeService;

/**
 * @author Clark
 * 上午11:11:38
 */
public class ItemTypeService implements IItemTypeService {
	
	private IItemTypeDAO itemTypeDAO;
	private ISubjectItemTypeDAO subjectItemTypeDAO;
	
	public IItemTypeDAO getItemTypeDAO() {
		return itemTypeDAO;
	}

	public void setItemTypeDAO(IItemTypeDAO itemTypeDAO) {
		this.itemTypeDAO = itemTypeDAO;
	}

	public ISubjectItemTypeDAO getSubjectItemTypeDAO() {
		return subjectItemTypeDAO;
	}

	public void setSubjectItemTypeDAO(ISubjectItemTypeDAO subjectItemTypeDAO) {
		this.subjectItemTypeDAO = subjectItemTypeDAO;
	}

	/**
	 * 添加题型信息
	 * @author Administrator
	 * @date: 2014年7月14日 下午12:56:25 <br/> 
	 * @param itemType
	 */
	@Override
	public ItemType addItemType(ItemType itemType) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().save(itemType);
	}

	/**
	 * 通过名称获取题型（主要用于对重名做判断）
	 * @author Clark
	 * @date 2014年7月29日上午9:16:01
	 * @param name
	 * @return itemType
	 */
	@Override
	public ItemType getItemTypeByName(String name) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().getItemTypeByName(name);
	}

	/**
	 * 通过ID获取题型
	 * @author Clark
	 * @date 2014年7月29日上午9:16:11
	 * @param typeID
	 * @return
	 */
	@Override
	public ItemType getItemTypeById(Integer typeID) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().get(typeID);
	}

	/**
	 * 修改题型信息
	 * @author Clark
	 * @date 2014年7月29日上午9:16:21
	 * @param itemType
	 * @return
	 */
	@Override
	public ItemType updateItemType(ItemType itemType) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().update(itemType);
	}

	/**
	 * 删除题型信息
	 * @author Clark
	 * @date 2014年7月29日上午10:00:46
	 * @param itemType
	 * @return
	 */
	@Override
	public ItemType deleteItemType(ItemType itemType) {
		// TODO Auto-generated method stub
		//先删除学科与题型的关系表的数据
		this.getSubjectItemTypeDAO().deleteByItemType(itemType);
		ItemType result = this.getItemTypeDAO().delete(itemType);
		return result;
	}

	/**
	 * 通过条件查询题型列表
	 * @author Clark
	 * @date 2014年7月29日上午11:09:14
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<ItemType>
	 */
	@Override
	public List<ItemType> searchItemType(Map<String, Object> searchMap,
			String sorts, int offset, int pagesize) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().searchItemType(searchMap, sorts, offset, pagesize);
	}

	/**
	 * 通过条件查询题型数量
	 * @author Clark
	 * @date 2014年7月29日上午11:09:55
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchItemTypeCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return this.getItemTypeDAO().searchItemTypeCount(searchMap);
	}

}
