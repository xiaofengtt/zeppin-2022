/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAliQrcodeDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IAliQrcodeService;
import cn.zeppin.product.ntb.core.entity.AliQrcode;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author hehe
 *
 */
@Service
public class AliQrcodeService extends BaseService implements IAliQrcodeService {

	@Autowired
	private IAliQrcodeDAO aliQrcodeDao;

	
	@Override
	public AliQrcode insert(AliQrcode investorIdcardImg) {
		return aliQrcodeDao.insert(investorIdcardImg);
	}

	@Override
	public AliQrcode delete(AliQrcode investorIdcardImg) {
//		investorIdcardImg.setStatus(InvestorIdcardImgStatus.DELETED);
		return aliQrcodeDao.delete(investorIdcardImg);
	}

	@Override
	public AliQrcode update(AliQrcode investorIdcardImg) {
		return aliQrcodeDao.update(investorIdcardImg);
	}

	@Override
	public AliQrcode get(String uuid) {
		return aliQrcodeDao.get(uuid);
	}
	
	/**
	 * 根据参数查询aliQrcode结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return aliQrcodeDao.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return aliQrcodeDao.getCount(inputParams);
	}

	@Override
	public List<Entity> getList(Map<String, String> inputParams,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return aliQrcodeDao.getList(inputParams, resultClass);
	}

}
