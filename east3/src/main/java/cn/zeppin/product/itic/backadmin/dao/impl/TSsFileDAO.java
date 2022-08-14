/**
 * 
 */
package cn.zeppin.product.itic.backadmin.dao.impl;

import org.springframework.stereotype.Repository;

import cn.zeppin.product.itic.backadmin.dao.api.ITSsFileDAO;
import cn.zeppin.product.itic.core.dao.base.BaseDAO;
import cn.zeppin.product.itic.core.entity.TSsFile;

@Repository
public class TSsFileDAO extends BaseDAO<TSsFile, String> implements ITSsFileDAO {
	
	@Override
	public TSsFile insert(TSsFile t){
		return super.insert(t);
	}
	
	@Override
	public TSsFile delete(TSsFile t){
		return super.delete(t);
	}
	
	@Override
	public TSsFile update(TSsFile t){
		return super.update(t);
	}
	
	@Override
	public TSsFile get(String id) {
		return super.get(id);
	}
}
