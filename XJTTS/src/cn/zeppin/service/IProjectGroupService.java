package cn.zeppin.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.entity.Project;
import cn.zeppin.entity.ProjectApply;
import cn.zeppin.entity.ProjectGroup;

public interface IProjectGroupService extends
	IBaseService<ProjectGroup, Integer>
{

    public List<ProjectGroup> getList();
    
    /**
     * 多阶段项目入库操作
     * @param project--被继承的项目
     * @param lstProjectApplys--被继承的申报记录
     * @param params--其他需要设置的参数
     * @return
     */
    public String addStageProject(Project project, List<ProjectApply> lstProjectApplys, Map<String, Object> params);

}
