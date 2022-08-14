package cn.zeppin.service.imp;

import java.util.List;

import cn.zeppin.dao.api.IInvigilationTemplateDAO;
import cn.zeppin.entity.InvigilationTemplate;
import cn.zeppin.service.api.IInvigilationTemplateService;

/**
 * 监考责任书 模板
 * 
 * @author geng
 *
 */
public class InvigilationTemplateService implements IInvigilationTemplateService {
	private IInvigilationTemplateDAO invigilationTemplateDAO;

	public IInvigilationTemplateDAO getInvigilationTemplateDAO() {
		return invigilationTemplateDAO;
	}

	public void setInvigilationTemplateDAO(IInvigilationTemplateDAO invigilationTemplateDAO) {
		this.invigilationTemplateDAO = invigilationTemplateDAO;
	}

	@Override
	public List<InvigilationTemplate> getInvigilationTemplateList(int offset, int length) {
		return invigilationTemplateDAO.getList(offset, length);
	}

	@Override
	public InvigilationTemplate add(InvigilationTemplate invigilationTemplate) {
		return invigilationTemplateDAO.save(invigilationTemplate);
	}

	@Override
	public InvigilationTemplate delete(InvigilationTemplate invigilationTemplate) {
		return invigilationTemplateDAO.delete(invigilationTemplate);
	}
	

	@Override
	public int getCount() {
		return this.getInvigilationTemplateDAO().getCount();
	}
	
	@Override
	public InvigilationTemplate getById(int id) {
		return this.getInvigilationTemplateDAO().get(id);
	}

	@Override
	public void update(InvigilationTemplate template) {
		this.getInvigilationTemplateDAO().update(template);
	}
}
