package cn.zeppin.service;

import java.util.List;

import cn.zeppin.entity.Area;

public interface IAreaService extends IBaseService<Area, Integer>
{

    public List<Area> getParentCodeArea(String code);

    public List<Area> getLevelArea(short level);

    public Area getAreaByCode(String code);

    /**
     * @category 获取所有父节点
     * @param code
     * @return
     * @author sj
     */
    public List<String> getParentNodes(String code);

    public List<Area> getAllChildByCode(String code);

}
