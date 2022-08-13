package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IRetrieveTypeDAO;
import cn.zeppin.entity.RetrieveType;
import cn.zeppin.service.api.IRetrieveTypeService;
import cn.zeppin.utility.Dictionary;

public class RetrieveTypeService implements IRetrieveTypeService {

	
	private IRetrieveTypeDAO retrieveTypeDAO;
	
	public IRetrieveTypeDAO getRetrieveTypeDAO() {
		return retrieveTypeDAO;
	}

	public void setRetrieveTypeDAO(IRetrieveTypeDAO retrieveTypeDAO) {
		this.retrieveTypeDAO = retrieveTypeDAO;
	}

	/**
	 * 通过ID获取分类科目检索类型
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/>
	 * @param RetrieveTypeID
	 * @return RetrieveType
	 */
	@Override
	public RetrieveType getRetrieveTypeById(Integer id) {
		return this.getRetrieveTypeDAO().get(id);
	}
	
	/**
	 * 通过Name获取类别
	 * @param name
	 * @return
	 */
	public RetrieveType getRetrieveTypeByName(String name){
		return this.getRetrieveTypeDAO().getRetrieveTypeByName(name);
	}

	/**
	 * 添加分类科目检索类型
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/>
	 * @param retrieveType
	 * @return RetrieveType
	 */
	@Override
	public void addRetrieveType(RetrieveType retrieveType) {
		getRetrieveTypeDAO().save(retrieveType);
	}

	/**
	 * 删除分类科目检索类型，这里对分类科目检索类型仅做逻辑删除
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/>
	 * @param retrieveType
	 * @return RetrieveType
	 */
	@Override
	public void deleteRetrieveType(RetrieveType retrieveType) {
		retrieveType.setStatus(Dictionary.RETRIEVE_TYPE_STATUS_CLOSED);
		this.getRetrieveTypeDAO().update(retrieveType);
	}

	/**
	 * 修改分类科目检索类型
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/>
	 * @param retrieveType
	 * @return RetrieveType
	 */
	@Override
	public void updateRetrieveType(RetrieveType retrieveType) {
		this.getRetrieveTypeDAO().update(retrieveType);
	}

	/**
	 * 搜索分类科目检索类型数量
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/>
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchRetrieveTypeCount(Map<String, Object> searchMap) {
		return getRetrieveTypeDAO().searchRetrieveTypeCount(searchMap);
	}

	/**
	 * 搜索分类科目检索类型
	 * 
	 * @author Clark
	 * @param currentUser
	 * @date: 2014年7月20日 下午7:43:13 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<RetrieveType>
	 */
	@Override
	public List<RetrieveType> searchRetrieveTypes(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		return getRetrieveTypeDAO().searchRetrieveTypes(searchMap, sorts, offset, pagesize);
	}

}
