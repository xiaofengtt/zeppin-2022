package cn.zeppin.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import cn.zeppin.dao.IOrganizationDao;
import cn.zeppin.entity.Organization;
import cn.zeppin.service.IOrganizationService;
import cn.zeppin.utility.DictionyMap;

public class OrganizationServiceImpl extends BaseServiceImpl<Organization, Integer> implements IOrganizationService {

	private IOrganizationDao organizationDao;

	public IOrganizationDao getOrganizationDao() {
		return organizationDao;
	}

	public void setOrganizationDao(IOrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	@Override
	public Organization add(Organization t) {
		// TODO Auto-generated method stub
		return organizationDao.add(t);
	}

	@Override
	public Organization update(Organization t) {
		// TODO Auto-generated method stub
		return organizationDao.update(t);
	}

	@Override
	public void delete(Organization t) {
		// TODO Auto-generated method stub
		organizationDao.delete(t);
	}

	@Override
	public Organization load(Integer id) {
		// TODO Auto-generated method stub
		return organizationDao.load(id);
	}

	@Override
	public Organization get(Integer id) {
		// TODO Auto-generated method stub
		return organizationDao.get(id);
	}

	@Override
	public List<Organization> loadAll() {
		// TODO Auto-generated method stub
		return organizationDao.loadAll();
	}

	@Override
	public List<Organization> findAll() {
		// TODO Auto-generated method stub
		return organizationDao.findAll();
	}

	@Override
	public List<Object> findByHSQL(String querySql) {
		// TODO Auto-generated method stub
		return organizationDao.findByHSQL(querySql);
	}

	@Override
	public List<Organization> getListForPage(String hql, int offset, int length) {
		// TODO Auto-generated method stub
		return organizationDao.getListForPage(hql, offset, length);
	}

	@Override
	public List<Organization> getListForPage(String hql, int offset, int length, Object[] objects) {
		// TODO Auto-generated method stub
		return organizationDao.getListForPage(hql, offset, length, objects);
	}

	@Override
	public void executeHSQL(String hql) {
		// TODO Auto-generated method stub
		organizationDao.executeHSQL(hql);
	}

	@Override
	public List<Organization> getListByHSQL(String hql) {
		// TODO Auto-generated method stub
		return organizationDao.getListByHSQL(hql);
	}

	@Override
	public List executeSQL(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		return organizationDao.getListBySQL(sql, objParas);
	}

	@Override
	public void executeSQLUpdate(String sql, Object[] objParas) {
		// TODO Auto-generated method stub
		organizationDao.executeSQLUpdate(sql, objParas);
	}

	@Override
	public List getListPage(String sql, int offset, int length, Object[] objParas) {
		// TODO Auto-generated method stub
		return organizationDao.getListPage(sql, offset, length, objParas);
	}

	@Override
	public int checkUserInfo(Object[] pars) {
		return this.organizationDao.checkUserInfo(pars);
	}

	@Override
	public List<Object> getLevelOrganization(int pid) {
		
		return this.organizationDao.getLevelOrganization(pid);
	}

	@Override
	public int getOrgHasChild(int pid) {
		return this.organizationDao.getOrgHasChild(pid);
	}

	@Override
	/**
	 * 获取 组织架构功能列表
	 */
	public Hashtable<String, Object> getOrganizationsListPage(HttpServletRequest request) {

		Hashtable<String, Object> ht = new Hashtable<>();

		// 起始页
		String ist = (String) request.getParameter("jtStartIndex");
		if (ist == null || ist.equals("") || ist.equals("NaN")) {
			ist = "0";
		}
		// 显示的条数
		String ien = (String) request.getParameter("jtPageSize");
		if (ien == null || ien.equals("")) {
			ien = DictionyMap.maxPageSize + "";
		}

		int start = Integer.parseInt(ist);
		int limit = DictionyMap.maxPageSize;
		limit = Integer.parseInt(ien);

		// 排序
		String sort = request.getParameter("jtSorting");
		String pid = request.getParameter("pid");
		String oid = request.getParameter("oid");

		String hql = " from Organization t where 1=1 ";
		
		if(oid != null && !"".equals(oid)){
			Organization o = this.organizationDao.get(Integer.parseInt(oid));
			pid = o.getOrganization().getId()+"";
			hql += " and t.id=" + oid;
		}
		
		if (pid == null || pid.equals("0") || pid.equals("")) {
			hql += " and t.organization is null ";
		} else {
			hql += " and t.organization=" + pid;
		}
		
		
		hql += " and t.status>0";//正常和停用状态的机构    0-删除的机构

		// 以后其他搜索参数
		// 排序 参数
		if (sort != null && !sort.equals("")) {
			String[] sortarray = sort.split(" ");
			String sortname = sortarray[0];
			String sorttype = sortarray[1];
			hql += " order by t." + sortname + " " + sorttype;
		}else{
			hql += " order by t.creattime desc";
		}

		String hqlCount = "select count(*) " + hql;

		List<Object> licount = this.findByHSQL(hqlCount);
		int records = Integer.parseInt(licount.get(0).toString());
		ht.put("records", records);

		List<Organization> li = this.getListForPage(hql, start, limit);

		ht.put("data", li);

		return ht;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.zeppin.service.IOrganizationService#getAllChildByOrganization(cn.zeppin
	 * .entity.Organization)
	 */
//	@Override
//	public List<Organization> getAllChildByOrganization(Organization organization) {
//		// TODO Auto-generated method stub
//		List<Organization> lstOrganizations = new ArrayList<>();
//		String hqlString = "";
//
//		lstOrganizations = this.organizationDao.getListByHSQL(hqlString);
//		return lstOrganizations;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.zeppin.service.IOrganizationService#getAllChildId(int)
	 */

	@Override
	/*
	 * public void getAllChildSchool(Organization organization,
	 * List<Organization> lstOrganizations) { // TODO Auto-generated method stub
	 * if (!organization.getIsschool() &&
	 * organization.getOrganizationLevel().getId() != 4) { String hqlString =
	 * "from Organization where pid=" + organization.getId(); List<Organization>
	 * lstT = new ArrayList<>(); lstT =
	 * this.organizationDao.getListByHSQL(hqlString); for (Organization ot :
	 * lstT) { getAllChildSchool(ot, lstOrganizations); } } else {
	 * lstOrganizations.add(organization); } }
	 */
    /**
     * 递归取下级组织结构树，返回全数据列表
     */
	public List<Organization> getSubOrganization(Organization organization) { // TODO
		return this.getOrganizationDao().getALLSubOrganizations(organization);
	}
//  之前的方法
//	public List<Organization> getSubOrganization(Organization organization) { // TODO
//		List<Organization> results = new ArrayList<Organization>();														
//		Iterator<Organization> lst = organization.getOrganizations().iterator();
//		results.addAll(organization.getOrganizations());
//		while (lst.hasNext()) {
//			Organization org = lst.next();
//			if (org!=null && !org.getIsschool()){
//				results.addAll(getSubOrganization(org));
//			}
//		} 
//		return results;
//	}
//    
	/**
	 * 通过多个父级部门，获取所属派出学校列表
	 * @param parentList 父级部门列表
	 * @author Clark 2014.05.27
	 * @return List<Organization>学校列表 
	 */
	@Override
	public List<Organization> getAllChildSchool(Organization organization) {
		// TODO Auto-generated method stub
		List<Organization> result = this.getOrganizationDao().getAllChildSchool(organization);
		return result;
	}

	/**
	 * 通过PID查找下级部门列表
	 * @param organization
	 * @return
	 */
	@Override
	public List<Organization> getOrganizationByPid(Integer pid) {
		// TODO Auto-generated method stub
		Organization parent = this.get(pid);
		return this.getOrganizationDao().getSubOrganizations(parent);
	}
	
	public void addOrganization(Organization organization){
		this.getOrganizationDao().addOrganization(organization);
	}

}
