package cn.zeppin.service.api;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Retrieve;

public interface IRetrieveService {
	
	/**
	 * 通过ID获取分类科目检索范围
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/> 
	 * @param retrieveId
	 * @return Retrieve
	 */
	public Retrieve getRetrieveById(Integer id);
	
	/**
	 * 添加分类科目检索范围
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/> 
	 * @param retrieve
	 */
	public void addRetrieve(Retrieve retrieve);

	/**
	 * 删除分类科目检索范围
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/> 
	 * @param retrieve
	 */
	public void deleteRetrieve(Retrieve retrieve);

	/**
	 * 修改分类科目检索范围
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/> 
	 * @param retrieve
	 */
	public void updateRetrieve(Retrieve retrieve);
	
	/**
	 * 获得分类科目检索范围的数量
	 * @author Clark
	 * @date: 2014年6月22日 下午4:36:57 <br/> 
	 * @param  searchMap
	 * @return int
	 */
	public int searchRetrieveCount(Map<String, Object> searchMap);

	/**
	 * 获取分类科目检索范围列表
	 * @author Clark
	 * @date: 2014年7月20日 下午7:53:47 <br/> 
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Retrieve>
	 */
	public List<Retrieve> searchRetrieves(Map<String, Object> searchMap, String sorts, int offset, int pagesize);

}
