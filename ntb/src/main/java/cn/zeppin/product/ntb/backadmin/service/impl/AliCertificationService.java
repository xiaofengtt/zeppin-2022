/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAliCertificationDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IAliCertificationService;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class AliCertificationService extends BaseService implements IAliCertificationService {

	@Autowired
	private IAliCertificationDAO aliCertificationDao;

	
	@Override
	public AliCertification insert(AliCertification investorIdcardImg) {
		return aliCertificationDao.insert(investorIdcardImg);
	}

	@Override
	public AliCertification delete(AliCertification investorIdcardImg) {
//		investorIdcardImg.setStatus(InvestorIdcardImgStatus.DELETED);
		return aliCertificationDao.delete(investorIdcardImg);
	}

	@Override
	public AliCertification update(AliCertification investorIdcardImg) {
		return aliCertificationDao.update(investorIdcardImg);
	}

	@Override
	public AliCertification get(String uuid) {
		return aliCertificationDao.get(uuid);
	}
	
	/**
	 * 根据参数查询aliCertification结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return aliCertificationDao.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return aliCertificationDao.getCount(inputParams);
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return aliCertificationDao.getList(inputParams, resultClass);
	}

}
