package cn.zeppin.product.score.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.zeppin.product.score.entity.MobileCode;
import cn.zeppin.product.score.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.score.mapper.MobileCodeMapper;
import cn.zeppin.product.score.service.MobileCodeService;

/**
 * 
 */
@Service("mobileCodeService")
public class MobileCodeServiceImpl implements MobileCodeService{
	
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
