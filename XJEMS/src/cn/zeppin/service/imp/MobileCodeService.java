package cn.zeppin.service.imp;

import java.util.List;
import java.util.Map;

import cn.zeppin.dao.api.IMobileCodeDAO;
import cn.zeppin.entity.MobileCode;
import cn.zeppin.service.api.IMobileCodeService;
import cn.zeppin.utility.Dictionary;

public class MobileCodeService implements IMobileCodeService {

	private IMobileCodeDAO mobileCodeDAO;
	
	
	public IMobileCodeDAO getMobileCodeDAO() {
		return mobileCodeDAO;
	}

	public void setMobileCodeDAO(IMobileCodeDAO mobileCodeDAO) {
		this.mobileCodeDAO = mobileCodeDAO;
	}

	@Override
	public void addMobileCode(MobileCode mobileCode) {
		// TODO Auto-generated method stub
		this.mobileCodeDAO.save(mobileCode);
	}

	@Override
	public void updateMobileCode(MobileCode mobileCode) {
		// TODO Auto-generated method stub
		this.mobileCodeDAO.update(mobileCode);
	}

	@Override
	public void deleteMobileCode(MobileCode mobileCode) {
		// TODO Auto-generated method stub
		mobileCode.setStatus(Dictionary.MOBILECODE_STATUS_INVALID);
		this.mobileCodeDAO.update(mobileCode);
	}

	@Override
	public MobileCode getMobileCodeById(Integer id) {
		// TODO Auto-generated method stub
		return this.mobileCodeDAO.get(id);
	}

	@Override
	public int getMobileCodeCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.mobileCodeDAO.getMobileCodeCountByParams(params);
	}

	@Override
	public MobileCode getRecordByUuid(String uuid) {
		// TODO Auto-generated method stub
		return this.mobileCodeDAO.getRecordByUuid(uuid);
	}

	@Override
	public List<MobileCode> getMobileCodeByParams(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.mobileCodeDAO.getMobileCodeByParams(params);
	}

}
