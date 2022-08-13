package cn.zeppin.service.api;

import java.util.List;

import cn.zeppin.entity.InvigilationTemplate;

/**
 * 监考责任书 模板
 * 
 * @author geng
 *
 */
public interface IInvigilationTemplateService {
	/**
	 * 获取监考模板列表数据 ，支持分页
	 * @param offset
	 * @param length
	 * @return
	 */
	List<InvigilationTemplate> getInvigilationTemplateList(int offset, int length);

	/**
	 * 添加监考模板数据
	 * @param invigilationTemplate
	 * @return
	 */
	InvigilationTemplate add(InvigilationTemplate invigilationTemplate);

	/**
	 * 删除监考模板数据
	 * @param invigilationTemplate
	 */
	InvigilationTemplate delete(InvigilationTemplate invigilationTemplate);
	
	/**
	 *  数据总数
	 * @return
	 */
	int getCount();
	
	/**
	 *  通过id获取
	 * @param id
	 * @return
	 */
	public InvigilationTemplate getById(int id);
	
	public void update(InvigilationTemplate template);
}
