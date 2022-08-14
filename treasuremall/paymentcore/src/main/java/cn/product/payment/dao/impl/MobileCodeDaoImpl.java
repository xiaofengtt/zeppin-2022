package cn.product.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.product.payment.dao.MobileCodeDao;
import cn.product.payment.entity.MobileCode;
import cn.product.payment.entity.MobileCode.MobileCodeStatus;
import cn.product.payment.mapper.MobileCodeMapper;

/**
 * 
 */
@Component
public class MobileCodeDaoImpl implements MobileCodeDao{
	
	@Autowired
	private MobileCodeMapper mobileCodeMapper;
	
	@Override
	public Integer getCountByParams(Map<String, Object> params) {
		return mobileCodeMapper.getCountByParams(params);
	}
	
	@Override
	public List<MobileCode> getListByParams(Map<String, Object> params) {
		return mobileCodeMapper.getListByParams(params);
	}
    
    @Override
	public MobileCode get(String key) {
		return mobileCodeMapper.selectByPrimaryKey(key);
	}

	@Override
	public int insert(MobileCode mobileCode) {
		return mobileCodeMapper.insert(mobileCode);
	}

	@Override
	public int delete(String key) {
		return mobileCodeMapper.deleteByPrimaryKey(key);
	}

	@Override
	public int update(MobileCode mobileCode) {
		return mobileCodeMapper.updateByPrimaryKey(mobileCode);
	}

	@Override
	@Transactional
	public void insertMobileCode(MobileCode mc) {
		Map<String, Object> inputParams = new HashMap<String, Object>();
		inputParams.put("mobile", mc.getMobile());
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<MobileCode> lstMobileCode = this.mobileCodeMapper.getListByParams(inputParams);

		// 如果之前存在验证码，设置验证码失效
		if (lstMobileCode != null && lstMobileCode.size() > 0) {
			for(MobileCode code: lstMobileCode){
				code.setStatus(MobileCodeStatus.DISABLE);
				this.mobileCodeMapper.updateByPrimaryKey(code);
			}
		}
		this.mobileCodeMapper.insert(mc);
	}
}
