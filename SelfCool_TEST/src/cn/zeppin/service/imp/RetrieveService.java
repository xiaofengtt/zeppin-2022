package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IRetrieveDAO;
import cn.zeppin.entity.Retrieve;
import cn.zeppin.service.api.IRetrieveService;
import cn.zeppin.utility.Dictionary;

public class RetrieveService implements IRetrieveService {

	
	private IRetrieveDAO retrieveDAO;
	
	public IRetrieveDAO getRetrieveDAO() {
		return retrieveDAO;
	}

	public void setRetrieveDAO(IRetrieveDAO retrieveDAO) {
		this.retrieveDAO = retrieveDAO;
	}

	/**
	 * 通过ID获取分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年7月14日 下午5:58:07 <br/>
	 * @param retrieveID
	 * @return Retrieve
	 */
	@Override
	public Retrieve getRetrieveById(Integer id) {
		return this.getRetrieveDAO().get(id);
	}

	/**
	 * 添加分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年6月24日 下午2:28:30 <br/>
	 * @param retrieve
	 * @return Retrieve
	 */
	@Override
	public void addRetrieve(Retrieve retrieve) {
		this.getRetrieveDAO().save(retrieve);
	}

	/**
	 * 删除分类科目检索范围，这里对分类科目检索范围仅做逻辑删除
	 * 
	 * @author Clark
	 * @date: 2014年7月15日 下午8:14:30 <br/>
	 * @param retrieve
	 * @return Retrieve
	 */
	@Override
	public void deleteRetrieve(Retrieve retrieve) {
		retrieve.setStatus(Dictionary.RETRIEVE_STATUS_CLOSED);
		this.getRetrieveDAO().update(retrieve);
	}

	/**
	 * 修改分类科目检索范围
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午5:48:37 <br/>
	 * @param retrieve
	 * @return Retrieve
	 */
	@Override
	public void updateRetrieve(Retrieve retrieve) {
		this.getRetrieveDAO().update(retrieve);
	}

	/**
	 * 搜索分类科目检索范围数量
	 * 
	 * @author Clark
	 * @date: 2014年7月20日 下午7:43:05 <br/>
	 * @param searchMap
	 * @return count
	 */
	@Override
	public int searchRetrieveCount(Map<String, Object> searchMap) {
		return getRetrieveDAO().searchRetrieveCount(searchMap);
	}

	/**
	 * 搜索分类科目检索范围
	 * 
	 * @author Clark
	 * @param currentUser
	 * @date: 2014年7月20日 下午7:43:13 <br/>
	 * @param searchMap
	 * @param sorts
	 * @param offset
	 * @param pagesize
	 * @return List<Retrieve>
	 */
	@Override
	public List<Retrieve> searchRetrieves(Map<String, Object> searchMap, String sorts, int offset, int pagesize) {
		return getRetrieveDAO().searchRetrieves(searchMap, sorts, offset, pagesize);
	}



}
