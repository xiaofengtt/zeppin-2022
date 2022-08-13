package cn.zeppin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zeppin.dao.IOrgaCateMapDao;
import cn.zeppin.entity.FunCategory;
import cn.zeppin.entity.OrgaCateMap;
import cn.zeppin.entity.OrganizationLevel;
import cn.zeppin.service.IOrgaCateMapService;

@SuppressWarnings({"unchecked","rawtypes"})
public class OrgaCateMapServiceImpl extends BaseServiceImpl<OrgaCateMap, Integer> implements IOrgaCateMapService {

	private IOrgaCateMapDao orgaCategoryDao;

	public IOrgaCateMapDao getOrgaCategoryDao() {
		return orgaCategoryDao;
	}

	public void setOrgaCategoryDao(IOrgaCateMapDao orgaCategoryDao) {
		this.orgaCategoryDao = orgaCategoryDao;
	}

	@Override
	public OrgaCateMap add(OrgaCateMap t) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.add(t);
	}

	@Override
	public OrgaCateMap update(OrgaCateMap t) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.update(t);
	}

	@Override
	public void delete(OrgaCateMap t) {
		// TODO Auto-generated method stub
		orgaCategoryDao.delete(t);
	}

	@Override
	public OrgaCateMap load(Integer id) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.load(id);
	}

	@Override
	public OrgaCateMap get(Integer id) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.get(id);
	}

	@Override
	public List<OrgaCateMap> loadAll() {
		// TODO Auto-generated method stub
		return orgaCategoryDao.loadAll();
	}

	@Override
	public List<OrgaCateMap> findAll() {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findByHSQL(querySql);
	}

	@Override
	public List<OrgaCateMap> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListForPage(hql, offset, length);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		orgaCategoryDao.executeHSQL(hql);
	}

	@Override
	public List<OrgaCateMap> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		orgaCategoryDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public List<OrgaCateMap> findByRoleId(short roleId, int level) {
		// TODO Auto-generated method stub
		return orgaCategoryDao.findByRoleId(roleId, level);
		
	}

	@Override
	public List<OrgaCateMap> getListByParams(Map<String, Object> params,
			String sort, int offset, int length) {
		// TODO Auto-generated method stub
		return this.orgaCategoryDao.getListByParams(params, sort, offset, length);
	}

	@Override
	public int getCountByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.orgaCategoryDao.getCountByParams(params);
	}

	/**
	 * 批量更新子菜单
	 */
	@Override
	public void updateMore(OrgaCateMap ocm) {
		// TODO Auto-generated method stub
		if(ocm != null){
			Map<String, Object> params = new HashMap<String, Object>();
//			params.put("status", 1);
			params.put("role", ocm.getRoleid());
			if(ocm.getOrganizationLevel() != null){
				params.put("level", ocm.getOrganizationLevel().getId());
			}
//			params.put("funCategory", ocm.getFunCategory());
			params.put("parent", ocm.getFunCategory().getId());
			List<OrgaCateMap> record = this.orgaCategoryDao.getListByParams(params, null, -1, -1);
			if(record != null && record.size() > 0){
				for(OrgaCateMap ocmc: record){
					ocmc.setStatus(ocm.getStatus());
					this.orgaCategoryDao.update(ocmc);
				}
			}
			this.orgaCategoryDao.update(ocm);
		}
	}

	/**
	 * 批量添加子菜单
	 */
	@Override
	public void addMore(Map<String, Object> params) {
		// TODO Auto-generated method stub
		if(params != null){
			if(params.containsKey("children")){
				String role = "";
				if(params.containsKey("role")){
					role = params.get("role").toString();
				}
				OrganizationLevel ol = null;
				if(params.containsKey("level")){
					ol = (OrganizationLevel)params.get("level");
				}
				
				List<FunCategory> list = (List<FunCategory>)params.get("children");
				String parent = list.get(0).getFunCategory().getId()+"";
				for(FunCategory fc : list){
					//判断功能状态，如果是已删除或已停用 则改便状态为正常,避免重复添加入库
					params.clear();
					params.put("funCategory", fc.getId());
					params.put("role", role);
					if(ol != null){
						params.put("level", ol.getId());
					}
					params.put("status", -2);
					List<OrgaCateMap> lst = this.orgaCategoryDao.getListByParams(params, null, -1, -1);
					if(lst != null && lst.size() > 0){
						OrgaCateMap ocm = lst.get(0);
						if(ocm.getStatus() != 1){
							ocm.setStatus((short)1);
							this.orgaCategoryDao.update(ocm);
						}
					}else{
						OrgaCateMap ocm = new OrgaCateMap();
						ocm.setFunCategory(fc);
						ocm.setName(fc.getName());
						ocm.setOrganizationLevel(ol);
						ocm.setRoleid(Short.parseShort(role));
						ocm.setStatus((short)1);
						this.orgaCategoryDao.add(ocm);
					}
				}
				
				//判断所属一级功能状态，如果是已删除或已停用 则改便状态为正常
				params.clear();
				params.put("funCategory", parent);
				params.put("role", role);
				if(ol != null){
					params.put("level", ol.getId());
				}
				params.put("status", -2);
				List<OrgaCateMap> lst = this.orgaCategoryDao.getListByParams(params, null, -1, -1);
				if(lst != null && lst.size() > 0){
					OrgaCateMap ocm = lst.get(0);
					if(ocm.getStatus() != 1){
						ocm.setStatus((short)1);
						this.orgaCategoryDao.update(ocm);
					}
				}
			}
			
		}
	}

}
