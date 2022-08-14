/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsControllerDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsController;


@Repository
public class TSsControllerDAO extends BaseDAO<TSsController, String> implements ITSsControllerDAO {
	
	@Override
	public TSsController insert(TSsController t) {
		return super.insert(t);
	}
	
	@Override
	public TSsController delete(TSsController t) {
		return super.delete(t);
	}
	
	@Override
	public TSsController update(TSsController t) {
		return super.update(t);
	}
	
	@Override
	public TSsController get(String id) {
		return super.get(id);
	}
	
	/**
	 * 获取所有功能
	 * @return
	 */
	public List<TSsController> getAll(){
		return super.getByHQL(" from TSsController t order by t.sort");
	}
}
