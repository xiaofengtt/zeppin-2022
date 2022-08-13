package cn.zeppin.service.imp;

import cn.zeppin.dao.api.ISsoKnowledgeDegreeDAO;
import cn.zeppin.entity.SsoKnowledgeDegree;
import cn.zeppin.service.api.ISsoKnowledgeDegreeService;

public class SsoKnowledgeDegreeService implements ISsoKnowledgeDegreeService {

	private ISsoKnowledgeDegreeDAO ssoKnowledgeDegreeDAO;

	public ISsoKnowledgeDegreeDAO getSsoKnowledgeDegreeDAO() {
		return ssoKnowledgeDegreeDAO;
	}

	public void setSsoKnowledgeDegreeDAO(ISsoKnowledgeDegreeDAO ssoKnowledgeDegreeDAO) {
		this.ssoKnowledgeDegreeDAO = ssoKnowledgeDegreeDAO;
	}

	@Override
	public void addSsoKnowledgeDegree(SsoKnowledgeDegree skd) {
		this.getSsoKnowledgeDegreeDAO().save(skd);
	}

	@Override
	public void updateSsoKnowledgeDegree(SsoKnowledgeDegree skd) {
		this.getSsoKnowledgeDegreeDAO().update(skd);
	}
}
