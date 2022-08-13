package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.EductionBackground;
import cn.zeppin.entity.Ethnic;

public interface IEductionBackgroundService extends
	IBaseService<EductionBackground, Integer>
{
    public EductionBackground geteEductionBackgroundById(String id);

    public List<EductionBackground> getEductionBackgroundByWight();
}
