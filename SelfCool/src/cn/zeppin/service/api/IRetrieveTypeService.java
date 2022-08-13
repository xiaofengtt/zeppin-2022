package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.RetrieveType;


public interface IRetrieveTypeService {
	
	/**
	 * 通过ID获取分类科目检索类型
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/> 
	 * @param retrieveTypeId
	 * @return RetrieveType
	 */
	public RetrieveType getRetrieveTypeById(Integer id);
	
	/**
	 * 通过Name获取类别
	 * @param name
	 * @return
	 */
	public RetrieveType getRetrieveTypeByName(String name);
	
	/**
	 * 添加分类科目检索类型
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/> 
	 * @param retrieveType
	 */
	public void addRetrieveType(RetrieveType retrieveType);

	/**
	 * 删除分类科目检索类型
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/> 
	 * @param retrieveType
	 */
	public void deleteRetrieveType(RetrieveType retrieveType);

	/**
	 * 修改分类科目检索类型
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/> 
	 * @param retrieveType
	 */
	public void updateRetrieveType(RetrieveType retrieveType);
	
	/**
	 * 获得分类科目检索类型的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  searchMap
	 * @return int
	 */
	public int searchRetrieveTypeCount(Map<String, Object> searchMap);

	/**
	 * 获取分类科目检索类型列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<RetrieveType>
	 */
	public List<RetrieveType> searchRetrieveTypes(Map<String, Object> searchMap, String sorts, int offset, int pagesize);

}
