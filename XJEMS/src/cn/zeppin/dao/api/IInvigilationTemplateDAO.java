package cn.zeppin.dao.api;

import java.util.List;

import cn.zeppin.entity.InvigilationTemplate;

/**
 * 监考责任书 模板
 * 
 * @author geng
 */
public interface IInvigilationTemplateDAO extends IBaseDAO<InvigilationTemplate, Integer> {
	public List<InvigilationTemplate> getList(int offset, int length);
	
	public int getCount();
}
