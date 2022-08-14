/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsSyncDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsSync;

@Repository
public class TSsSyncDAO extends BaseDAO<TSsSync, String> implements ITSsSyncDAO {
	
	@Override
	public TSsSync insert(TSsSync t){
		return super.insert(t);
	}
	
	@Override
	public TSsSync delete(TSsSync t){
		return super.delete(t);
	}
	
	@Override
	public TSsSync update(TSsSync t){
		return super.update(t);
	}
	
	@Override
	public TSsSync get(String id) {
		return super.get(id);
	}
}
