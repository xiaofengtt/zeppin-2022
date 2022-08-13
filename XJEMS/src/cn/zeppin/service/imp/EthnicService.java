package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.IEthnicDao;
import cn.zeppin.entity.Ethnic;
import cn.zeppin.service.api.IEthnicService;

public class EthnicService implements IEthnicService {

    private IEthnicDao ethnicDao;

    public IEthnicDao getEthnicDao()
    {
    	return ethnicDao;
    }

    public void setEthnicDao(IEthnicDao ethnicDao)
    {
    	this.ethnicDao = ethnicDao;
    }

    @Override
    public List<Ethnic> getEthnicByWight()
    {
    	return this.ethnicDao.getList();
    }

	@Override
	public Ethnic getById(short id) {
		// TODO Auto-generated method stub
		return this.ethnicDao.get(id);
	}

	@Override
	public Ethnic add(Ethnic ethnic) {
		// TODO Auto-generated method stub
		return this.ethnicDao.save(ethnic);
	}

	@Override
	public void update(Ethnic ethnic) {
		// TODO Auto-generated method stub
		this.ethnicDao.update(ethnic);
	}

	@Override
	public void delById(Ethnic ethnic) {
		// TODO Auto-generated method stub
		this.ethnicDao.delete(ethnic);
	}


}
