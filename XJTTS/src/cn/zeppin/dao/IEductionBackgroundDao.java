package cn.zeppin.dao;

import java.util.List;

import cn.zeppin.entity.EductionBackground;

public interface IEductionBackgroundDao extends
		IBaseDao<EductionBackground, Short> {

	public List<EductionBackground> getEductionBackgroundByWight();
}
