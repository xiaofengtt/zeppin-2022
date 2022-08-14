/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

/**
 * @author elegantclack
 *
 */
@Service
public class MobileCodeService extends BaseService implements IMobileCodeService {

	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	
	@Override
	public MobileCode insert(MobileCode mobileCode) {
		return mobileCodeDAO.insert(mobileCode);
	}

	@Override
	public MobileCode delete(MobileCode mobileCode) {
		mobileCode.setStatus(MobileCodeStatus.DELETED);
		return mobileCodeDAO.update(mobileCode);
	}

	@Override
	public MobileCode update(MobileCode mobileCode) {
		return mobileCodeDAO.update(mobileCode);
	}

	@Override
	public MobileCode get(String uuid) {
		return mobileCodeDAO.get(uuid);
	}
	
	/**
	 * 根据参数查询结果列表(带分页、排序),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return mobileCodeDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * 根据参数查询结果个数
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return mobileCodeDAO.getCount(inputParams);
	}

	@Override
	public void insertMobileCode(MobileCode mc) {
		// TODO Auto-generated method stub
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", mc.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);

		// 如果之前存在验证码，设置验证码失效
		if (lstMobileCode != null && lstMobileCode.size() > 0) {
			for(Entity entity: lstMobileCode){
				MobileCode code = (MobileCode)entity;
				code.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeDAO.update(code);
			}
		}
		this.mobileCodeDAO.insert(mc);
	}
}
