package cn.zeppin.product.ntb.backadmin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAdvertDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IAdvertService;
import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.service.base.BaseService;

@Service
public class AdvertService extends BaseService implements IAdvertService{
	@Autowired
	private IAdvertDAO advertDAO;

	@Override
	public Advert insert(Advert t) {
		advertDAO.updateStatusToInvalid();
		return advertDAO.insert(t);
	}

	@Override
	public Advert delete(Advert t) {
		return advertDAO.delete(t);
	}

	@Override
	public Advert update(Advert t) {
		return advertDAO.update(t);
	}

	@Override
	public Advert get(String uuid) {
		return advertDAO.get(uuid);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		return advertDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return advertDAO.getCount(inputParams);
	}
}
