/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationDAO;
import cn.zeppin.product.itic.backadmin.dao.api.IToperatorMobileDAO;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorMobileService;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;

/**
 * @author L
 *
 */
@Service
public class ToperatorMobileService extends BaseService implements IToperatorMobileService {

	@Autowired
	private IToperatorMobileDAO  toperatorMobileDAO;
	
	@Autowired
	private ITnumberRelationDAO  tnumberRelationDAO;

	
	@Override
	public ToperatorMobile get(Integer uuid) {
		return toperatorMobileDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public ToperatorMobile insert(ToperatorMobile area) {
		return toperatorMobileDAO.insert(area);
	}

	@Override
	public ToperatorMobile delete(ToperatorMobile area) {
		return toperatorMobileDAO.delete(area);
	}

	@Override
	public ToperatorMobile update(ToperatorMobile area) {
		return toperatorMobileDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toperatorMobileDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return toperatorMobileDAO.getCount(inputParams);
	}

	@Override
	public List<ToperatorMobile> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return toperatorMobileDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public Integer getCountForNumPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return toperatorMobileDAO.getCountForNumPage(inputParams);
	}

	@Override
	public List<Entity> getListForNumPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return toperatorMobileDAO.getListForNumPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}
	
	@Override
	public void insertProcess(ToperatorMobile tm){
		// TODO Auto-generated method stub
		this.tnumberRelationDAO.dailyInsert(tm);
		this.tnumberRelationDAO.dailyUpdate(tm.getFkToperator());
	}
}
