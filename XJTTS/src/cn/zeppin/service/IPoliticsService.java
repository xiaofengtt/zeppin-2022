package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Ethnic;
import cn.zeppin.entity.Politics;

public interface IPoliticsService extends IBaseService<Politics, Integer>
{

    public Politics getPoliticsById(String id);
    
    public List<Politics> getPoliticsByWight();

}
